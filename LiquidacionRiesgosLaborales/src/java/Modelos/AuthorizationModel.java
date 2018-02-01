package Modelos;

import Beans.ResumenMovimientos;
import Beans.Movimiento;
import Common.clsFecha;
import Common.clsConexion;
import Common.EnvioMail;
import Modelos.ModeloLiquidation;

import java.io.*;
import java.sql.*;

import java.util.Map;
import java.util.HashMap;
import java.util.Vector;
import java.util.ArrayList;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import net.sf.jasperreports.engine.*;

public class AuthorizationModel {

    //Variable que almacena el nombre del fideicomitente.
    public static String nombre_cliente = "";
    //Variable que almacena el nombre que se le dará al pdf al adjuntar en mail.
    public static String nombre_reporte = "";
    //Variable que almacena todos los movimientos de un lote.
    public static Vector movimientos_cp = new Vector();
    //Variable que almacena todos los movimientos a eliminar en la cancelación parcial.
    public static ArrayList elimina_movs = new ArrayList();

    public static String getNombre_cliente() {
        return nombre_cliente;
    }

    public static void setNombre_cliente(String nombre_cliente) {
        AuthorizationModel.nombre_cliente = nombre_cliente;
    }

    public static String getNombre_reporte() {
        return nombre_reporte;
    }

    public static void setNombre_reporte(String nombre_reporte) {
        AuthorizationModel.nombre_reporte = nombre_reporte;
    }

    public static Vector getMovimientos_cp() {
        return movimientos_cp;
    }

    public static void setMovimientos_cp(Vector movimientos_cp) {
        AuthorizationModel.movimientos_cp = movimientos_cp;
    }

    public static ArrayList getElimina_movs() {
        return elimina_movs;
    }

    public static void setElimina_movs(ArrayList elimina_movs) {
        AuthorizationModel.elimina_movs = elimina_movs;
    }

    /**
     * Método que proporciona el conjunto de fideicomitentes que cuentan con
     * lotes pendientes por operar con el status que se le pasa como parámetro.
     *
     * @param String status: Status
     * @return Vector clientes: Clientes con el status que se especifica.
     */
    public static Vector getClaveFideicomitentes(String status) {
        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        Vector clientes = new Vector();
        String cliente = "";
        String MySql = "";
        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select distinct(contratos.clave_contrato)";
            MySql += " from contratos contratos, movimientos_h movimientos ";
            MySql += " where contratos.clave_contrato= movimientos.clave_contrato ";
            MySql += " and contratos.status = 'A' ";
            MySql += " and movimientos.status ='" + status + "' ";
            MySql += " order by contratos.clave_contrato asc ";
//            System.out.println("getClaveFideicomitentes:" + MySql);
            clientes.add("  -----Seleccione-----  ");
            ResultSet rstSQLServer = statement.executeQuery(MySql);
            while (rstSQLServer.next()) {
                cliente = rstSQLServer.getString(1);
                clientes.add(cliente);
            }
            rstSQLServer.close();
            statement.close();
            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            clientes = null;
            System.out.println("AuthorizationModel-getClaveFideicomitentes:" + e.getMessage());
            if (connection != null) {
                conn.Desconecta(connection);
            }
        }
        return clientes;
    }
    
    public static boolean generaConsultaSaldos(String fecha) {
        boolean correcto = false;
        Vector vector_info = new Vector();
        String nombre_archivo = "saldos";

        Writer writer = null;

        try {
            writer = new OutputStreamWriter(new FileOutputStream("temp/" + nombre_archivo + ".csv"), "UTF-8");
            writer.write("FECHA,FIDEICOMISO,CUENTA_ORIGEN,SALDO \r\n");
            Calendar c = Calendar.getInstance();
            int anio = Integer.parseInt(fecha.substring(0,4));
            int mes = Integer.parseInt(fecha.substring(5,7));
            int dia = Integer.parseInt(fecha.substring(8,10));
            c.set(anio,
                    mes-1,
                    dia,
                    23,
                    59,
                    59);

            String fecha_corta = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
            clsConexion conn = new clsConexion();
            Connection connection = null;
            Statement statement = null;
            String MySql = "";
            String fecha_limite = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(c.getTime());
            System.out.println(fecha_limite);
            Vector<String> contratos = new Vector<String>();
            Vector<String> cuenta_origen = new Vector<String>();
            Vector<String> saldos = new Vector<String>();
            try {
                connection = conn.ConectaSQLServer();
                statement = connection.createStatement();
                statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

                MySql = " 	SELECT clave_contrato,cuenta_origen,saldo"
                        + "		FROM contratos"
                        //                        + "		WHERE status='A'"
                        + "		ORDER BY clave_contrato ";

                ResultSet rstSQLServer = statement.executeQuery(MySql);
                while (rstSQLServer.next()) {
                    String contrato = rstSQLServer.getString("clave_contrato");
                    contratos.add(contrato);
                    cuenta_origen.add(rstSQLServer.getString("cuenta_origen"));
                    saldos.add(rstSQLServer.getString("saldo"));
                }
                int index = 0;

                for (index = 0; index < contratos.size(); index++) {
                    connection = conn.ConectaSQLServer();
                    statement = connection.createStatement();
                    statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

                    MySql = " select top 1 * from "
                            + " EC_" + contratos.get(index) + " "
                            + " where fecha<='" + fecha_limite + "' "
                            + " order by fecha desc ";
                    ResultSet rstSQLServer2 = statement.executeQuery(MySql);
                    if (rstSQLServer2.next()) {
                        Float saldo = rstSQLServer2.getFloat("saldo");
                        writer.write(fecha_corta + "," + contratos.get(index) + "," + cuenta_origen.get(index) + "," + saldo + "\r\n");
                    }
                    else {
                        writer.write(fecha_corta + "," + contratos.get(index) + "," + cuenta_origen.get(index) + "," + saldos.get(index) + "\r\n");
                    }
                }

                statement.close();

                if (connection != null) {
                    conn.Desconecta(connection);
                }
                writer.close();
                correcto = true;
            } catch (Exception e) {
                if (connection != null) {
                    conn.Desconecta(connection);
                }
                System.out.println("ModelUpdate-getInfoContrato:" + e.toString());
            }
        } catch (FileNotFoundException ex) {
            System.out.println("FileNotFoundException:" + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("IOException:" + ex.getMessage());
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
            }
        }

        return correcto;
    }

    public static String[] getDatos_cliente(String clave_contrato) {
        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String datos[] = new String[3];
        double saldo = -1D;
        String MySql = "";
        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select nombre_cliente,domicilio_fiscal,saldo";
            MySql += " from contratos ";
            MySql += " where clave_contrato='" + clave_contrato + "' ";
            MySql += " and contratos.status = 'A' ";
//            System.out.println("getDatos_cliente:" + clave_contrato);
            ResultSet rstSQLServer = statement.executeQuery(MySql);
            while (rstSQLServer.next()) {
                datos[0] = rstSQLServer.getString(1);
                datos[1] = rstSQLServer.getString(2);
                saldo = rstSQLServer.getDouble(3);
                datos[2] = new DecimalFormat("0.00").format(saldo);
//                System.out.println("Se encontro: " + datos[0] + "|" + datos[1] + "|" + datos[2]);
            }
            rstSQLServer.close();
            statement.close();
            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            datos = null;
            System.out.println("AuthorizationModel-getDatos_cliente:" + e.getMessage());
            if (connection != null) {
                conn.Desconecta(connection);
            }
        }
        return datos;
    }

    public static boolean actualizaArchivo() {
        boolean correcto = false;

        Writer writer = null;
        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String MySql = "";

        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " SELECT TABLE_NAME FROM information_schema.TABLES esquema, contratos contratos ";
            MySql += " where TABLE_NAME like 'EC_FID%' ";
            MySql += " and clave_contrato like 'FID%' ";
//            MySql += " and TABLE_NAME=CONCAT('EC_',clave_contrato) ";
            MySql += " and TABLE_NAME=('EC_' + clave_contrato) ";
            MySql += " and id_codes!=0 ";
            MySql += " order by TABLE_NAME ";

            ResultSet rstSQLServer = statement.executeQuery(MySql);
            writer = new OutputStreamWriter(new FileOutputStream("temp/consulta.csv"), "UTF-8");
            while (rstSQLServer.next()) {
                String nombre_tabla = rstSQLServer.getString("TABLE_NAME");
                Vector<String> movimientos = getMovimientosFiduciarios(nombre_tabla, "", "");
                if (movimientos != null && !movimientos.isEmpty()) {
//                    System.out.println("----------- Movimientos:" + nombre_tabla + " ---------------");                        
                    for (int i = 0; i < movimientos.size(); i++) {
//                        System.out.println(movimientos.get(i));
                        writer.write(nombre_tabla.substring(3) + "," + movimientos.get(i) + "\r\n");
                    }
                }
            }
            writer.close();
            correcto = true;

        } catch (Exception e) {
            correcto = false;
            if (connection != null) {
                conn.Desconecta(connection);
            }
            System.out.println("AuthorizationModel-actualizaArchivo:" + e.toString());
        }

        return correcto;
    }

    public static boolean generaArchivoDeConsulta(Vector vector_info, String nombre_archivo) {
        boolean correcto = false;

        Writer writer = null;

        try {
            writer = new OutputStreamWriter(new FileOutputStream("temp/" + nombre_archivo + ".csv"), "UTF-8");
            if (nombre_archivo.equals("consulta_ejecutiva")) {
                writer.write("FIDEICOMISO,APORTACIONES,RESTITUCIONES,LIQUIDACIONES,HONORARIOS,I.V.A. \r\n");
                Vector totales = (Vector) vector_info.get(vector_info.size() - 1);
                vector_info.remove(vector_info.size() - 1);
                for (int i = 0; i < vector_info.size(); i++) {
                    String registro = vector_info.get(i).toString();
                    writer.write(registro.substring(1, registro.length() - 1) + "\r\n");
                }
                writer.write("\r\n" + "TOTAL," + totales.get(1) + "," + totales.get(3) + ",");
                writer.write(totales.get(5) + "," + totales.get(7) + ",");
                writer.write(totales.get(9) + "");
            } else if (nombre_archivo.equals("movimientos_detalle")) {
                writer.write("FIDEICOMISO,CUENTA DE ORIGEN,FECHA,CONCEPTO,CARGO,ABONO,SALDO \r\n");
                for (int i = 0; i < vector_info.size(); i++) {
                    Vector vector_info_interno = (Vector) vector_info.get(i);
                    for (int ij = 0; ij < vector_info_interno.size(); ij++) {
                        String registro = vector_info_interno.get(ij).toString();
                        writer.write(registro + "\r\n");
                    }
                }
            } else {
                writer.write("FIDEICOMISO,FECHA,CONCEPTO,CARGO,ABONO,SALDO \r\n");
                for (int i = 0; i < vector_info.size(); i++) {
                    String registro = vector_info.get(i).toString();
                    writer.write(registro + "\r\n");
                }
            }
            writer.close();
            correcto = true;
        } catch (Exception e) {
            System.out.println("AuthorizationModel-generaArchivoDeConsulta:" + e.toString());
        }

        return correcto;
    }

    public static Vector<String> getMovimientosFiduciarios(String nombre_tabla, String fecha_ini, String fecha_fin) {
        Vector<String> movimientos = new Vector<String>();
        String registro = "";

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String MySql = "";

        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            MySql = " select fecha,concepto,cargo,abono,saldo from " + nombre_tabla;
            if (!"".equals(fecha_ini) && !"".equals(fecha_fin)) {
                MySql += " where fecha>='" + fecha_ini + " 00:00:00.000' ";
                MySql += " and fecha<='" + fecha_fin + " 23:59:59.990' ";
            }
            MySql += " order by fecha ";

            ResultSet rstSQLServer = statement.executeQuery(MySql);
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MMM-yyyy");

            while (rstSQLServer.next()) {
                registro = formatoFecha.format(rstSQLServer.getDate("fecha")) + ",";
                registro += rstSQLServer.getString("concepto") + ",";
                registro += "" + rstSQLServer.getDouble("cargo") + ",";
                registro += "" + rstSQLServer.getDouble("abono") + ",";
                registro += "" + rstSQLServer.getDouble("saldo");
                movimientos.add(nombre_tabla.substring(3) + "," + registro);
//                    System.out.println(nombre_tabla +"," + registro);
            }

        } catch (Exception e) {
            if (connection != null) {
                conn.Desconecta(connection);
            }
            System.out.println("ModelUpdate-getInfoContrato:" + e.toString());
        }

        return movimientos;
    }

    public static Vector<String> getMovimientosFiduciariosCtaOrigen(String nombre_tabla, String cuenta_origen, String fecha_ini, String fecha_fin) {
        Vector<String> movimientos = new Vector<String>();
        String registro = "";

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String MySql = "";

        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            MySql = " select fecha,concepto,cargo,abono,saldo from " + nombre_tabla;
            if (!"".equals(fecha_ini) && !"".equals(fecha_fin)) {
                MySql += " where fecha>='" + fecha_ini + " 00:00:00.000' ";
                MySql += " and fecha<='" + fecha_fin + " 23:59:59.990' ";
            }
            MySql += " order by fecha ";

            ResultSet rstSQLServer = statement.executeQuery(MySql);
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MMM-yyyy");

            while (rstSQLServer.next()) {
                registro = formatoFecha.format(rstSQLServer.getDate("fecha")) + ",";
                registro += rstSQLServer.getString("concepto") + ",";
                registro += "" + rstSQLServer.getDouble("cargo") + ",";
                registro += "" + rstSQLServer.getDouble("abono") + ",";
                registro += "" + rstSQLServer.getDouble("saldo");
                movimientos.add(nombre_tabla.substring(3) + "," + cuenta_origen + "," + registro);
//                    System.out.println(nombre_tabla +"," + registro);
            }

        } catch (Exception e) {
            if (connection != null) {
                conn.Desconecta(connection);
            }
            System.out.println("ModelUpdate-getInfoContrato:" + e.toString());
        }

        return movimientos;
    }

    public static Vector getRegistroFidEjecutiva(String nombre_tabla, String nombre_cliente, String fecha_ini, String fecha_fin) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        Vector<Double> datos = new Vector<Double>();
        Vector registro = new Vector();
        Double aportacion_mes = -1.0D;
        Double restitucion_mes = -1.0D;
        Double liquidacion_mes = -1.0D;
        Double honorario_mes = -1.0D;
        Double iva_mes = -1.0D;
//        Double saldo = -1.0D;
        Integer no_movimientos = 0;

        String MySql = "";

        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
///////Comienza el calculo de aportacion_mes
            MySql += " select sum(cast(abono as float)) as aportacion_mes ";
            MySql += " from " + nombre_tabla + " ";
            MySql += " where concepto='APORTACION A PATRIMONIO' ";
            MySql += " and fecha>='" + fecha_ini + " 00:00:00.000' ";
            MySql += " and fecha<='" + fecha_fin + " 23:59:59.900' ";
            ResultSet rstSQLServer = statement.executeQuery(MySql);
            int delimita = 0;
            while (rstSQLServer.next()) {
                delimita++;
                aportacion_mes = rstSQLServer.getDouble(1);
//                System.out.println("aportacion=" + aportacion_mes );
            }
//            if(delimita!=1){}
///////Comienza el calculo de restitucion_mes
            MySql = " select sum(cast(abono as float)) as restitucion_mes ";
            MySql += " from " + nombre_tabla + " ";
            MySql += " where concepto='RESTITUCION DE PATRIMONIO' ";
            MySql += " and fecha>='" + fecha_ini + " 00:00:00.000' ";
            MySql += " and fecha<='" + fecha_fin + " 23:59:59.000' ";
            rstSQLServer = statement.executeQuery(MySql);
            delimita = 0;
            while (rstSQLServer.next()) {
                delimita++;
                restitucion_mes = rstSQLServer.getDouble(1);
//                System.out.println("restitucion_mes=" + restitucion_mes);
            }
///////Comienza el calculo de liquidacion_mes
            MySql = " select sum(cast(cargo as float)) as liquidacion_mes ";
            MySql += " from " + nombre_tabla + " ";
            MySql += " where concepto='ORDEN DE LIQUIDACION' ";
            MySql += " and fecha>='" + fecha_ini + " 00:00:00.000' ";
            MySql += " and fecha<='" + fecha_fin + " 23:59:59.000' ";
            rstSQLServer = statement.executeQuery(MySql);
            delimita = 0;
            while (rstSQLServer.next()) {
                delimita++;
                liquidacion_mes = rstSQLServer.getDouble(1);
//                System.out.println("liquidacion_mes=" + liquidacion_mes);
            }

///////Comienza el calculo de honorario_mes
            MySql = " select sum(cast(cargo as float)) as honorario_mes ";
            MySql += " from " + nombre_tabla + " ";
            MySql += " where concepto='HONORARIOS FIDUCIARIOS' ";
            MySql += " and fecha>='" + fecha_ini + " 00:00:00.000' ";
            MySql += " and fecha<='" + fecha_fin + " 23:59:59.000' ";
            rstSQLServer = statement.executeQuery(MySql);
            delimita = 0;
            while (rstSQLServer.next()) {
                delimita++;
                honorario_mes = rstSQLServer.getDouble(1);
//                System.out.println("honorario_mes=" + honorario_mes);
            }
///////Comienza el calculo de liquidacion_mes
            MySql = " select sum(cast(cargo as float)) as iva_mes ";
            MySql += " from " + nombre_tabla + " ";
            MySql += " where concepto='I.V.A. DE HONORARIOS FIDUCIARIOS' ";
            MySql += " and fecha>='" + fecha_ini + " 00:00:00.000' ";
            MySql += " and fecha<='" + fecha_fin + " 23:59:59.000' ";
            rstSQLServer = statement.executeQuery(MySql);
            delimita = 0;
            while (rstSQLServer.next()) {
                delimita++;
                iva_mes = rstSQLServer.getDouble(1);
//                System.out.println("iva_mes=" + iva_mes);
            }

///////Comienza el conteo de movimientos fiduciarios
//            MySql = " select concepto ";
//            MySql += " from " + nombre_tabla + " ";
//	    MySql += " where fecha>='"+ fecha_ini +" 00:00:00.000' ";
//            MySql += " and fecha<='"+ fecha_fin +" 23:59:59.000' ";  
//            rstSQLServer = statement.executeQuery(MySql);
//            delimita=0;
//            while (rstSQLServer.next()) {
//                no_movimientos++;
//            }                        
            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }

//            if(no_movimientos<=0){
//            return new Vector();
//            }
            if (aportacion_mes != null && aportacion_mes >= 0) {
                datos.add(new BigDecimal(aportacion_mes).setScale(2, RoundingMode.HALF_UP).doubleValue());
            } else {
                datos.add(0D);
            }

            if (restitucion_mes != null && restitucion_mes >= 0) {
                datos.add(new BigDecimal(restitucion_mes).setScale(2, RoundingMode.HALF_UP).doubleValue());
            } else {
                datos.add(0D);
            }

            if (liquidacion_mes != null && liquidacion_mes >= 0) {
                datos.add(new BigDecimal(liquidacion_mes).setScale(2, RoundingMode.HALF_UP).doubleValue());
            } else {
                datos.add(0D);
            }

            if (honorario_mes != null && honorario_mes >= 0) {
                datos.add(new BigDecimal(honorario_mes).setScale(2, RoundingMode.HALF_UP).doubleValue());
            } else {
                datos.add(0D);
            }

            if (iva_mes != null && iva_mes >= 0) {
                datos.add(new BigDecimal(iva_mes).setScale(2, RoundingMode.HALF_UP).doubleValue());
            } else {
                datos.add(0D);
            }

//            saldo = datos.get(0) + datos.get(1) - datos.get(2) - datos.get(3) - datos.get(4);
//            datos.add(new BigDecimal(saldo).setScale(2, RoundingMode.HALF_UP).doubleValue());
            registro.add(nombre_tabla.substring(3));
//            registro.add(nombre_cliente);            //Si se coloca esta linea, al descargar el excel, no se ordenan las columnas correctamente
            registro.add(datos.get(0));
            registro.add(datos.get(1));
            registro.add(datos.get(2));
            registro.add(datos.get(3));
            registro.add(datos.get(4));
//            registro.add(datos.get(5));

        } catch (Exception e) {
            registro = null;
            System.out.println("AuthorizationModel:getRegistroFidEjecutiva()" + e.toString());
        }
        return registro;
    }

    public static Vector getMovimientosFiduciariosUniversoPeriodo(String fecha_ini, String fecha_fin) {
        Vector movimientosUniverso = new Vector();

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String MySql = "";

        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " SELECT TABLE_NAME FROM information_schema.TABLES esquema, contratos contratos ";
            MySql += " where TABLE_NAME like 'EC_FID%' ";
            MySql += " and clave_contrato like 'FID%' ";
//            MySql += " and TABLE_NAME=CONCAT('EC_',clave_contrato) ";
            MySql += " and TABLE_NAME=('EC_' + clave_contrato) ";
            MySql += " and id_codes!=0 ";
            MySql += " order by TABLE_NAME ";

            ResultSet rstSQLServer = statement.executeQuery(MySql);
            while (rstSQLServer.next()) {
                String nombre_tabla = rstSQLServer.getString("TABLE_NAME");
                Vector<String> movimientos = getMovimientosFiduciarios(nombre_tabla, fecha_ini, fecha_fin);
                if (movimientos != null && !movimientos.isEmpty()) {
                    System.out.println("Se encontraron " + movimientos.size() + " resultados.");
                    for (String string : movimientos) {
                        movimientosUniverso.add(string);
                    }
                }
            }

        } catch (Exception e) {
            if (connection != null) {
                conn.Desconecta(connection);
            }
            System.out.println("AuthorizationModel-actualizaArchivo:" + e.toString());
        }

        return movimientosUniverso;
    }

    public static Vector getConsultaEjecutiva(String cliente, String cuenta_origen, String fecha_ini, String fecha_fin) {
        Vector movimientosUniverso = new Vector();
        Double aportaciones_total = 0D;
        Double restituciones_total = 0D;
        Double liquidaciones_total = 0D;
        Double honorarios_total = 0D;
        Double iva_total = 0D;
//    Double saldo_total = 0D;

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String MySql = "";

        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " SELECT TABLE_NAME,contratos.nombre_cliente FROM information_schema.TABLES esquema, contratos contratos ";
            MySql += " where TABLE_NAME like 'EC_FID%' ";
            MySql += " and clave_contrato like 'FID%' ";
//            MySql += " and TABLE_NAME=CONCAT('EC_',clave_contrato) ";
            MySql += " and TABLE_NAME=('EC_' + clave_contrato) ";
            MySql += " and contratos.id_codes!=0 ";
            if (cuenta_origen != null && !"".equals(cuenta_origen) && !"Todo".equals(cuenta_origen)) {
                MySql += " and contratos.cuenta_origen='" + cuenta_origen + "' ";
            }
            if (cliente != null && !"".equals(cliente) && !"Todo".equals(cliente)) {
                MySql += " and contratos.clave_contrato='" + cliente + "' ";
            }
            MySql += " order by TABLE_NAME ";

            ResultSet rstSQLServer = statement.executeQuery(MySql);
            int i = 0;
            while (rstSQLServer.next()) {
                i++;
                String nombre_tabla = rstSQLServer.getString("TABLE_NAME");
                String nombre_cliente = rstSQLServer.getString("nombre_cliente");
                Vector registro = getRegistroFidEjecutiva(nombre_tabla, nombre_cliente, fecha_ini, fecha_fin);
                if (registro != null && !registro.isEmpty()) {
//                        System.out.println("registro(" + i +") -> " + registro);
                    aportaciones_total += Double.parseDouble(registro.get(1).toString());
                    restituciones_total += Double.parseDouble(registro.get(2).toString());
                    liquidaciones_total += Double.parseDouble(registro.get(3).toString());
                    honorarios_total += Double.parseDouble(registro.get(4).toString());
                    iva_total += Double.parseDouble(registro.get(5).toString());
//                        saldo_total += Double.parseDouble(registro.get(6).toString());
                    movimientosUniverso.add(registro);
                }
            }
            Vector registro = new Vector();
            registro.add("Aportaciones");
            registro.add(aportaciones_total);
            registro.add("Restituciones");
            registro.add(restituciones_total);
            registro.add("Liquidaciones");
            registro.add(liquidaciones_total);
            registro.add("Honorarios");
            registro.add(honorarios_total);
            registro.add("I.V.A.");
            registro.add(iva_total);
//            registro.add("Saldo");
//            registro.add(saldo_total);
            movimientosUniverso.add(registro);
//            System.out.println("registro(" + i +") -> " + registro);

        } catch (Exception e) {
            if (connection != null) {
                conn.Desconecta(connection);
            }
            System.out.println("AuthorizationModel-getConsultaEjecutiva:" + e.toString());
        }

        return movimientosUniverso;
    }

    public static Vector getConsultaEjecutivaDetalle(String cliente, String cuenta_origen, String fecha_ini, String fecha_fin) {
        Vector movimientosUniverso = new Vector();

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String MySql = "";

        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " SELECT TABLE_NAME,cuenta_origen as CTA_ORIGEN FROM information_schema.TABLES esquema, contratos contratos ";
            MySql += " where TABLE_NAME like 'EC_FID%' ";
            MySql += " and clave_contrato like 'FID%' ";
//            MySql += " and TABLE_NAME=CONCAT('EC_',clave_contrato) ";
            MySql += " and TABLE_NAME=('EC_' + clave_contrato) ";
            MySql += " and contratos.id_codes!=0 ";
            if (cuenta_origen != null && !"".equals(cuenta_origen) && !"Todo".equals(cuenta_origen)) {
                MySql += " and contratos.cuenta_origen='" + cuenta_origen + "' ";
            }
            if (cliente != null && !"".equals(cliente) && !"Todo".equals(cliente)) {
                MySql += " and contratos.clave_contrato='" + cliente + "' ";
            }
            MySql += " order by TABLE_NAME ";

            ResultSet rstSQLServer = statement.executeQuery(MySql);
            int i = 0;
            while (rstSQLServer.next()) {
                i++;
                String nombre_tabla = rstSQLServer.getString("TABLE_NAME");
                String cta_origenSQL = rstSQLServer.getString("CTA_ORIGEN");
//                    Vector registro =getRegistroFidEjecutiva(nombre_tabla,fecha_ini,fecha_fin);
                Vector registros = getMovimientosFiduciariosCtaOrigen(nombre_tabla, cta_origenSQL, fecha_ini, fecha_fin);
                if (registros != null && !registros.isEmpty()) {
//                        System.out.println("registro(" + i +") -> " + registro);
                    movimientosUniverso.add(registros);
                }
            }
//            System.out.println("registro(" + i +") -> " + registro);

        } catch (Exception e) {
            if (connection != null) {
                conn.Desconecta(connection);
            }
            System.out.println("AuthorizationModel-actualizaArchivo:" + e.toString());
        }

        return movimientosUniverso;
    }

    /**
     * Método que proporciona el conjunto de fideicomitentes que cuentan con
     * lotes pendientes por operar con el status que se le pasa como parámetro.
     *
     * @param String status: Status
     * @return Vector clientes: Clientes con el status que se especifica.
     */
    public static Vector getAllClaveFideicomitentes() {
        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        Vector clientes = new Vector();
        String cliente = "";
        String MySql = "";
        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select clave_contrato ";
            MySql += " from contratos ";
            MySql += " where status ='A' ";
            MySql += " order by clave_contrato asc ";
//            System.out.println("getClaveFideicomitentes:" + MySql);
            clientes.add("  -----Seleccione-----  ");
            ResultSet rstSQLServer = statement.executeQuery(MySql);
            while (rstSQLServer.next()) {
                cliente = rstSQLServer.getString(1);
                clientes.add(cliente);
            }
            rstSQLServer.close();
            statement.close();
            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            clientes = null;
            System.out.println("AuthorizationModel-getClaveFideicomitentes:" + e.getMessage());
            if (connection != null) {
                conn.Desconecta(connection);
            }
        }
        return clientes;
    }

    public static Vector getCuentaOrigen() {
        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        Vector clientes = new Vector();
        String cliente = "";
        String MySql = "";
        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select cuenta_origen ";
            MySql += " from cuentas_banco ";
            MySql += " where status ='A' ";
            MySql += " order by cuenta_origen asc ";
//            System.out.println("getClaveFideicomitentes:" + MySql);
            clientes.add("Todo");
            ResultSet rstSQLServer = statement.executeQuery(MySql);
            while (rstSQLServer.next()) {
                cliente = rstSQLServer.getString(1);
                clientes.add(cliente);
            }
            rstSQLServer.close();
            statement.close();
            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            clientes = null;
            System.out.println("AuthorizationModel-getClaveFideicomitentes:" + e.getMessage());
            if (connection != null) {
                conn.Desconecta(connection);
            }
        }
        return clientes;
    }

    /**
     *
     * @param fideicomiso
     * @return datos fideicomiso (saldo) Este metodo regresa la información
     * basica del fideicomiso y el saldo actual en la BD
     */
    public static String getSaldo_y_Datos(String fideicomiso) {
        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String nombre_cliente = "";
        String correo = "";
        String tipo_honorario = "";
        String honorario_sin_iva = "";
        String saldo = "";
        String MySql = "";
        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select nombre_cliente,correo,tipo_honorario,honorario_sin_iva,saldo ";
            MySql += " from contratos ";
            MySql += " where clave_contrato ='" + fideicomiso + "' ";
            MySql += " and status ='A' ";
            ResultSet rstSQLServer = statement.executeQuery(MySql);
            while (rstSQLServer.next()) {
                nombre_cliente = rstSQLServer.getString("nombre_cliente");
                correo = rstSQLServer.getString("correo");
                tipo_honorario = rstSQLServer.getString("tipo_honorario");
                honorario_sin_iva = rstSQLServer.getString("honorario_sin_iva");
                saldo = rstSQLServer.getString("saldo");
            }
            rstSQLServer.close();
            statement.close();
            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            nombre_cliente = correo = tipo_honorario = honorario_sin_iva = saldo = "";
            System.out.println("AuthorizationModel-getSaldoFideicomitente:" + e.getMessage());
            if (connection != null) {
                conn.Desconecta(connection);
            }
        }
        DecimalFormat formato = new DecimalFormat("0.00");
        saldo = formato.format(Double.parseDouble(saldo));
        honorario_sin_iva = formato.format(Double.parseDouble(honorario_sin_iva));
        return nombre_cliente + "%" + correo + "%" + tipo_honorario + "%" + honorario_sin_iva + "%" + saldo;
    }

    public static Vector getEdoCtaFiles(String clave_contrato) {
        Vector nombresEdoCta = new Vector();
        Vector mesesCombo = new Vector();
        Vector resultado = null;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateF = new SimpleDateFormat("MMMM 'de' yyyy");

        try {
            File directorio = new File("C:\\inetpub\\ftproot\\EstadosDeCuenta\\" + clave_contrato + "\\");
            if (directorio.exists()) {
                if (directorio.isDirectory()) {
                    String[] listaArchivos = directorio.list();
                    for (int i = 0; i < listaArchivos.length; i++) {
                        String archivo = listaArchivos[i];
                        try {
                            String extencionFile = archivo.substring(archivo.length() - 4, archivo.length());
                            if (extencionFile.equals(".ZIP") || extencionFile.equals(".zip")) {
                                if (archivo.substring(7, 20).equals(clave_contrato)) {
                                    //Se obtiene a partir del nombre del archivo la fecha del periodo(mes) al que corresponde
                                    cal.set(Calendar.MONTH, Integer.parseInt(archivo.substring(26, 28)) - 1);
                                    cal.set(Calendar.YEAR, Integer.parseInt(archivo.substring(21, 25)));
                                    //Se obtiene formato de fecha para mostrar en el CheckBox de seleccion de periodo
                                    String campo = dateF.format(cal.getTime());
                                    //Se asigna al vector el nombre del archivo .zip a descargar
                                    nombresEdoCta.add(archivo);
                                    //Se agrega el titulo del CheckBox para descargar el archivo
                                    mesesCombo.add(campo);
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Exception_formatNumber:ModeloLayOut.getEdoCtaFiles()" + e.getMessage());
                        }

                    }
                }
                if (!nombresEdoCta.isEmpty() && !mesesCombo.isEmpty()) {
                    resultado = new Vector();
                    resultado.add(mesesCombo);
                    resultado.add(nombresEdoCta);
                }
            }
        } catch (Exception e) {
            resultado = null;
        }
        return resultado;
    }

    /**
     * Método que actualiza el status del lote según el conjunto de status que
     * se pasan como parámetro.
     *
     * @param clave_contrato : clave del contrato del cliente.
     * @param fecha_liquidacion : Fecha de la Liquidación.
     * @param nombre_archivo: Nombre del lote a actualizar.
     * @param usuario : Nombre del Usuario que genero los Lay_Out's
     *
     *
     * @param status_global: Status general que se desea establecer al lote.
     * @param status_autoriza: Status que se desea establecer de la
     * autorización.
     * @param status_proceso: Status actual del lote.
     * @return boolean valido: Regresa true si se actualiza correctamente el
     * status correspondiente,false en otro caso.
     */
    public synchronized boolean insertaAportacionSaldo(String clave_contrato, double saldo_actual, double importe_aportacion, double honorarios, double iva_honorarios, String usuario) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        boolean seGuardo = false;
        double nuevo_Saldo = 0.0;
        String MySql = "";
        DecimalFormat formato = new DecimalFormat("0.00");
        Calendar calendario = Calendar.getInstance();

//        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss.SSS");//FUNCIONA CORRECTAMENTE ANTERIORMENTE
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        try {
            if (!ModelUpdate.date_en_habil()) {
                calendario.set(Calendar.DAY_OF_MONTH, 1);
                calendario.set(Calendar.MONTH, calendario.get(Calendar.MONTH) + 1);
                calendario.set(Calendar.HOUR_OF_DAY, 0);
                calendario.set(Calendar.MINUTE, ModelUpdate.getNo_movimiento());
                calendario.set(Calendar.SECOND, 0);
                calendario.set(Calendar.MILLISECOND, 0);
            }

            connection = conn.ConectaSQLServer();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            nuevo_Saldo = importe_aportacion + saldo_actual;

            seGuardo = false;
            MySql = " insert into EC_" + clave_contrato + " ";
            MySql += " (fecha,concepto,abono,saldo,usuario_genera) ";
            MySql += " values ( ";
//            MySql += " '" + formatoFecha.format(calendario.getTime()) +"', "  ;
            MySql += " '" + formatoFecha.format(calendario.getTime()) + "', ";
//            MySql += " '"+ clave_contrato +"', ";
            MySql += " 'APORTACION A PATRIMONIO', ";
            MySql += " " + formato.format(importe_aportacion) + ", ";
            MySql += " " + formato.format(nuevo_Saldo) + ", ";
            MySql += " '" + usuario + "') ";
//            System.out.println("------------ ExecuteSQL-Aportacion: " + MySql);
            statement.executeUpdate(MySql);
            calendario.add(Calendar.MILLISECOND, 100);
            seGuardo = true;
//            Thread.sleep(5000);
//            this.wait(1000);

            if (seGuardo) {

                seGuardo = false;
                nuevo_Saldo -= honorarios;

                MySql = " insert into EC_" + clave_contrato + " ";
                MySql += " (fecha,concepto,cargo,saldo,usuario_genera) ";
                MySql += " values ( ";
                MySql += " '" + formatoFecha.format(calendario.getTime()) + "', ";
//            MySql += " GETDATE(), "  ;
//            MySql += " '"+ clave_contrato +"', ";
                MySql += " 'HONORARIOS FIDUCIARIOS', ";
                MySql += " " + formato.format(honorarios) + ", ";
                MySql += " " + formato.format(nuevo_Saldo) + ", ";
                MySql += " '" + usuario + "') ";
//            System.out.println("------------ ExecuteSQL-Aportacion: " + MySql);
                statement.executeUpdate(MySql);
                calendario.add(Calendar.MILLISECOND, 100);
                seGuardo = true;
//            this.wait(1000);
//             Thread.sleep(5000);
            }

            if (seGuardo) {
                seGuardo = false;
                nuevo_Saldo -= iva_honorarios;

                MySql = " insert into EC_" + clave_contrato + " ";
                MySql += " (fecha,concepto,cargo,saldo,usuario_genera) ";
                MySql += " values ( ";
//            MySql += " '" + fecha_operacion +" " + hora_operacion +":"+ min_operacion +":00.500', "  ;
                MySql += " '" + formatoFecha.format(calendario.getTime()) + "', ";
//            MySql += " GETDATE(), "  ;
//            MySql += " '"+ clave_contrato +"', ";
                MySql += " 'I.V.A. DE HONORARIOS FIDUCIARIOS', ";
                MySql += " " + formato.format(iva_honorarios) + ", ";
                MySql += " " + formato.format(nuevo_Saldo) + ", ";
                MySql += " '" + usuario + "') ";
//            System.out.println("------------ ExecuteSQL-Aportacion: " + MySql);
                statement.executeUpdate(MySql);
                seGuardo = true;
            }
            if (seGuardo) {
                seGuardo = false;
                MySql = " update contratos set ";
                MySql += " saldo=" + formato.format(nuevo_Saldo) + " ";
                MySql += " where clave_contrato ='" + clave_contrato + "' ";
//            System.out.println("------------ ExecuteSQL-Aportacion: " + MySql);
                statement.executeUpdate(MySql);
                seGuardo = true;
            }
            if (seGuardo) {
                connection.commit();
            } else {
                connection.rollback();
            }
//            System.out.println("Transaction commit...");
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
            System.out.println("------------ Termina correctamente aportacion.");
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("------------ Error de SQL:" + ex.getMessage());
            }
            e.printStackTrace();
            seGuardo = false;
            System.out.println("AuthorizationModel-insertaAportacionSaldo:" + e.toString());
            if (connection != null) {
                conn.Desconecta(connection);
            }
        }
        return seGuardo;
    }

    public static synchronized boolean insertaRestitucionSaldo(String observaciones, String clave_contrato, double saldo_actual, double importe_restitucion, String usuario) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        boolean seGuardo = false;
        double nuevo_Saldo = 0.0;
        String MySql = "";
        DecimalFormat formato = new DecimalFormat("0.00");
        Calendar calendario = Calendar.getInstance();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");//ESTO ES NUEVO, CUANDO SE MODIFIQUE DIA DE CORTE
//        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss.SSS");

        try {

            if (!ModelUpdate.date_en_habil()) {
                calendario.set(Calendar.DAY_OF_MONTH, 1);
                calendario.set(Calendar.MONTH, calendario.get(Calendar.MONTH) + 1);
                calendario.set(Calendar.HOUR_OF_DAY, 0);
                calendario.set(Calendar.MINUTE, ModelUpdate.getNo_movimiento());
                calendario.set(Calendar.SECOND, 0);
                calendario.set(Calendar.MILLISECOND, 0);
            }

            connection = conn.ConectaSQLServer();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            nuevo_Saldo = importe_restitucion + saldo_actual;

            seGuardo = false;
            MySql = " insert into EC_" + clave_contrato;
            MySql += " (fecha,concepto,observaciones,abono,saldo,usuario_genera) ";
            MySql += " values ( ";
//            MySql += " GETDATE(), "  ;
            MySql += " '" + formatoFecha.format(calendario.getTime()) + "', ";//ESTO ES NUEVO, CUANDO SE HAGA DIA DE CORTE
            MySql += " 'RESTITUCION DE PATRIMONIO', ";
            MySql += " '" + observaciones + "', ";
            MySql += " " + formato.format(importe_restitucion) + ", ";
            MySql += " " + formato.format(nuevo_Saldo) + ", ";
            MySql += " '" + usuario + "') ";

            statement.executeUpdate(MySql);
            seGuardo = true;

            if (seGuardo) {
                seGuardo = false;
                MySql = " update contratos set ";
                MySql += " saldo=" + formato.format(nuevo_Saldo) + " ";
                MySql += " where clave_contrato ='" + clave_contrato + "' ";

                statement.executeUpdate(MySql);
                seGuardo = true;
            }

            connection.commit();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("Error de SQL:" + ex.getMessage());
            }
            e.printStackTrace();
            seGuardo = false;
            System.out.println("AuthorizationModel-insertaRestitucionSaldo:" + e.toString());
            if (connection != null) {
                conn.Desconecta(connection);
            }
        }
        return seGuardo;
    }

    /**
     *
     * @param fideicomiso
     * @return (saldo) fideicomiso Este metodo regresa el saldo actual en la BD
     * del correspondiente fideicomiso
     */
    public static String getSaldo(String fideicomiso) {
        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String saldo = "";
        String MySql = "";
        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select saldo ";
            MySql += " from contratos ";
            MySql += " where clave_contrato ='" + fideicomiso + "' ";
            MySql += " and status ='A' ";
            ResultSet rstSQLServer = statement.executeQuery(MySql);
            while (rstSQLServer.next()) {
                saldo = rstSQLServer.getString("saldo");
                DecimalFormat formato = new DecimalFormat("0.00");
                saldo = formato.format(Double.parseDouble(saldo));
            }
            rstSQLServer.close();
            statement.close();
            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            saldo = "";
            System.out.println("AuthorizationModel-getSaldoFideicomitente:" + e.getMessage());
            if (connection != null) {
                conn.Desconecta(connection);
            }
        }
        return saldo;
    }

    public static String compruebaFechaOperacion(String fecha, String hora, String minuto) {

        java.util.Date fecha_hoy = new java.util.Date();
        java.util.Date fecha_operacion = null;
        int horaInt = 0;
        int minutoInt = 0;
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Fecha de hoy= " + formateador.format(fecha_hoy));
        try {
            fecha_operacion = formateador.parse(fecha);
        } catch (ParseException ex) {
            System.out.println("Excp compruebaFechaOperacion: Error en formato de fecha de operacion");
            return "Formato de fecha incorrecto";
        }
        if (fecha_operacion.after(fecha_hoy)) {
            System.out.println("La fecha es posterior a la actual");
            return "La fecha de operacion no puede ser posterior a la actual";
        }

        try {
            horaInt = Integer.parseInt(hora);
            if (horaInt < 0 || horaInt > 23) {
                return "La hora esta fuera de rango (00 - 23)hrs.";
            }
        } catch (Exception e) {
            return "Error en el formato de hora";
        }

        try {
            minutoInt = Integer.parseInt(minuto);
            if (minutoInt < 0 || minutoInt > 59) {
                return "Error en el rango de minutos (00 - 59)min.";
            }
        } catch (Exception e) {
            return "Error en el formato de hora";
        }
        return "";
    }

    public static double compruebaSaldoSuficiente(String saldo_actual, Double importe_liquidacion) {
        Double nuevo_saldo = null;
        Double saldo_a = 0.0;

        try {
            saldo_a = Double.parseDouble(saldo_actual);
            nuevo_saldo = saldo_a - importe_liquidacion;
        } catch (Exception e) {
            nuevo_saldo = null;
            System.out.println("Exception: compruebaSaldoSuficiente()" + e.getMessage());
        }
        return nuevo_saldo;
    }

    public static String compruebaSaldoSuficienteCancelados(String clave_contrato, String fecha_liquidacion, String nombre_archivo) {

        String saldo = "false";
        double importeTotal = 0.0;
        DecimalFormat formato = new DecimalFormat("$ #,##0.00");

        importeTotal += getImporteMovsMXPCancelados(clave_contrato, fecha_liquidacion, nombre_archivo);
        importeTotal += getImporteMovsT5Cancelados(clave_contrato, fecha_liquidacion, nombre_archivo);

        if (importeTotal <= 0) {
            saldo = "Error al obtener el importe de liquidación";
        } else {
            double saldoActual = Double.parseDouble(getSaldo(clave_contrato));
            System.out.println("Saldo actual en BD=" + saldoActual);
            System.out.println("Importe de la liquidación=" + importeTotal);
            if (saldoActual < importeTotal) {
                saldo = "No se puede completar la operación, Saldo= " + formato.format(saldoActual) + ", insuficiente para autorizar liquidación por " + formato.format(importeTotal);
            } else {
                saldo = "";
            }
        }
        return saldo;
    }

    public static Double getImporteMovsMXPCancelados(String clave_contrato, String fecha_liquidacion, String nombre_archivo) {
        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String MySql = "";
        Double importe = 0d;
        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " SELECT nombre_empleado,apellidoP_empleado,apellidoM_empleado,importe_liquidacion ";
            MySql += " FROM movimientos ";
            MySql += " where clave_contrato ='" + clave_contrato + "' ";
            MySql += " and nombre_archivo='" + nombre_archivo + "' ";
            MySql += " and fecha_liquidacion = '" + fecha_liquidacion + " 00:00:00.000' ";
            MySql += " and tipo_movimiento!=5 ";
            MySql += " EXCEPT ";
            MySql += " SELECT nombre_empleado,apellidoP_empleado,apellidoM_empleado,importe_liquidacion ";
            MySql += " FROM movimientos_cancelados ";
            MySql += " where clave_contrato ='" + clave_contrato + "' ";
            MySql += " and nombre_archivo='" + nombre_archivo + "' ";
            MySql += " and fecha_liquidacion = '" + fecha_liquidacion + " 00:00:00.000' ";
            MySql += " and tipo_movimiento!=5 ";

            ResultSet rstSQLServer = statement.executeQuery(MySql);
            while (rstSQLServer.next()) {
                importe += Double.parseDouble(rstSQLServer.getString("importe_liquidacion"));
            }

            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            importe = -1d;
            if (connection != null) {
                conn.Desconecta(connection);
            }
            System.out.println("AuthorizationModel-getTotalLiquidacionLote:" + e.getMessage());
        }
        return importe;
    }

    public static Double getImporteMovsT5Cancelados(String clave_contrato, String fecha_liquidacion, String nombre_archivo) {
        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String MySql = "";
        Double importe = 0d;
        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " SELECT nombre_empleado,apellidoP_empleado,apellidoM_empleado,importe_liquidacion_mxp ";
            MySql += " FROM movimientos ";
            MySql += " where clave_contrato ='" + clave_contrato + "' ";
            MySql += " and nombre_archivo='" + nombre_archivo + "' ";
            MySql += " and fecha_liquidacion = '" + fecha_liquidacion + " 00:00:00.000' ";
            MySql += " and tipo_movimiento=5 ";
            MySql += " EXCEPT ";
            MySql += " SELECT nombre_empleado,apellidoP_empleado,apellidoM_empleado,importe_liquidacion_mxp ";
            MySql += " FROM movimientos_cancelados ";
            MySql += " where clave_contrato ='" + clave_contrato + "' ";
            MySql += " and nombre_archivo='" + nombre_archivo + "' ";
            MySql += " and fecha_liquidacion = '" + fecha_liquidacion + " 00:00:00.000' ";
            MySql += " and tipo_movimiento=5 ";

            ResultSet rstSQLServer = statement.executeQuery(MySql);
            while (rstSQLServer.next()) {
                importe += Double.parseDouble(rstSQLServer.getString("importe_liquidacion_mxp"));
            }
            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            importe = -1d;
            if (connection != null) {
                conn.Desconecta(connection);
            }
            System.out.println("AuthorizationModel-getTotalLiquidacionLote:" + e.getMessage());
        }
        return importe;
    }

    /**
     * Método que proporciona el conjunto de fechas en que tendrán lugar las
     * liquidaciones de los lotes correspondientes al fideicomitente y status
     * que se le pasan como parámetro.
     *
     * @param clave_contrato: Clave de fideicomiso.
     * @param String status: Status.
     * @return Vector clientes: Conjunto de fechas de liquidación.
     */
    public static Vector getFechasLiquidacionPendientes(String clave_contrato, String status) {
        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        clsFecha c = new clsFecha();
        Vector files = new Vector();
        String MySql = "";
        String arch = "";
        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select  distinct(LEFT(CONVERT(VARCHAR, movimientos.fecha_liquidacion, 120), 10)) , contratos.nombre_cliente ";
            MySql += " from contratos contratos, movimientos_h movimientos  ";
            MySql += " where contratos.clave_contrato = movimientos.clave_contrato ";
            MySql += " and contratos.clave_contrato = '" + clave_contrato + "' ";
            MySql += " and contratos.status = 'A'  ";
            MySql += " and movimientos.status ='" + status + "'  ";
//            System.out.println(MySql);
            files.add("  -Seleccione-  ");
            ResultSet rstSQLServer = statement.executeQuery(MySql);
            c.setFormato("dd/MM/yyyy");
            while (rstSQLServer.next()) {
                arch = rstSQLServer.getString(1).trim();
                arch = c.CambiaFormatoFecha("yyyy-MM-dd", "dd/MM/yyyy", arch);
                nombre_cliente = rstSQLServer.getString(2);
                files.add(arch);
            }
            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            files = null;
            System.out.println("AuthorizationModel-getFechasLiquidacionPendientes:" + e.getMessage());
            if (connection != null) {
                conn.Desconecta(connection);
            }
        }
        return files;
    }

    /**
     * Método que regresa el nombre de las personas a las que se les hará envío
     * los cheques correspondientes al lote que se especifica como los datos que
     * se pasan como parámetro.
     *
     * @param n_cliente : Nombre del cliente.
     * @param clave_contrato : clave del contrato del cliente anterior.
     * @param mifecha : Fecha de Liquidación .
     * @param nombre_archivo : Nombre del Lay-Out al que corresponden los
     * movimientos.
     * @return Vector receptores: Conjunto de receptores a los que se les haran
     * llegar los cheques correspondientes al lote. Si surge algun error en la
     * ejecución se regresa null, si no se cuenta con receptores se regresa un
     * vector vacio y si hay receptores entonces se regresa un vector de
     * arreglos de string con receptor y cantidad total del cheque.
     */
    public static Vector getReceptorEnvioCheque(String n_cliente, String clave_contrato, String miFecha, String status, String nombre_archivo) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        Vector receptores = new Vector();
        String[] args = null;
        String MySql = "";
        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select l.envio_cheque, sum(cast(l.importe_liquidacion as float))    ";
            MySql += " from movimientos_h h , movimientos l , contratos contratos , cuentas_banco cuentas ";
            MySql += " where h.clave_contrato = l.clave_contrato ";
            MySql += " and l.clave_contrato = contratos.clave_contrato  ";
            MySql += " and h.fecha_liquidacion = l.fecha_liquidacion   ";
            MySql += " and h.nombre_archivo = l.nombre_archivo ";
            MySql += " and contratos.cuenta_origen = cuentas.cuenta_origen  ";
            MySql += " and l.fecha_liquidacion = '" + (new clsFecha().CambiaFormatoFecha("dd/MM/yyyy", "yyyy-MM-dd", miFecha)) + " 00:00:00.000" + "'";
            MySql += " and contratos.nombre_cliente = '" + n_cliente + "'";
            MySql += " and contratos.clave_contrato = '" + clave_contrato + "'";
            MySql += " and h.nombre_archivo = '" + nombre_archivo + "'";
            MySql += " and l.tipo_movimiento = 4  ";
            MySql += " and contratos.status = 'A'  ";
            MySql += " and cuentas.status = 'A'   ";
            MySql += " and h.status = '" + status + "'  ";
            MySql += " group by l.envio_cheque  ";

//            System.out.println("Emision de Cheques:" + MySql);
            ResultSet rstSQLServer = statement.executeQuery(MySql);

            while (rstSQLServer.next()) {
                args = new String[2];
                args[0] = rstSQLServer.getString(1).toString().trim(); //Persona Envio Cheque
                args[1] = rstSQLServer.getString(2).toString().trim(); //Suma Total
                receptores.add(args);
            }
            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            receptores = null;
            if (connection != null) {
                conn.Desconecta(connection);
            }
            System.out.println("AuthorizationModel-getReceptorEnvioCheque:" + e.getMessage());
        }
        return receptores;
    }

    /**
     * Método que gestiona la autorización del lote.
     *
     * @param movs_cancelados : Movimientos cancelados parcialmente.
     * @param cliente : Nombre del cliente asociado al lote.
     * @param clave_contrato : clave de contrato corresponiente al lote.
     * @param fecha_liquidacion : Fecha de Liquidación correspondiente al lote.
     * @param nombre_archivo : Nombre del Lay-Out al que corresponden los
     * movimientos.
     * @param usuario : Nombre del usuario que genera transacciones.
     * @param verifica : Número de lotes procesados satisfactoriamente.
     * @param correoOrigen : Correo electrónico emisor de reportes de
     * liquidación.
     * @param status_global : Status global del lote.
     * @param status_autoriza :Status de la autorización.
     * @param status_proceso : Status de la operación del proceso.
     * @return String: Regresa una cadena que contiene la descripción del estado
     * del proceso, si NO hay error regresa una cadena vacia.
     */
    public String autorizaLote(String cliente, String clave_contrato, String fecha_liquidacion,
            String nombre_archivo, String usuario, String urlArchivo, String persona_genera,
            int verifica, String correoOrigen, String status_global, String status_autoriza, String status_proceso, String realPath) {

        String clave_cliente = "";
        String correosDestino = "";
        String autoriza = "";
        Vector movs_cancelados = null;

        if (clave_contrato != null && !clave_contrato.equals("") && clave_contrato.length() > 9) {
            //Obtenemos la clave del fideicomiso en el sistema.
            clave_cliente = clave_contrato.substring(0, 9);
            //Obtenemos los correos de los usuarios dados de alta en el sistema para este fideicomiso.
            correosDestino = AuthorizationModel.getCorreoUsuariosFideicomiso(clave_cliente, clave_contrato);
            if (!correosDestino.equals("")) {
                if (!correosDestino.equals("Error obteniendo correos")) {
                    movs_cancelados = AuthorizationModel.getMovimientosCancelados(clave_contrato, fecha_liquidacion, nombre_archivo, status_proceso, "A");
                    if (movs_cancelados != null) {
                        //En caso de NO CONTAR con movimientos cancelados parcialmente. Se envian reportes .pdf y se actualiza estado aterminado de movimientos_h
                        if (movs_cancelados.isEmpty()) {
                            autoriza = this.autorizaMovimientos(cliente, clave_contrato, fecha_liquidacion, nombre_archivo, usuario, correosDestino, urlArchivo, status_global, status_autoriza, status_proceso, persona_genera, correoOrigen, verifica, realPath);
                            System.out.println("Autorizacion SIN cancelaciones: " + autoriza);
                        } //En caso de CONTAR con movimientos cancelados parcialmente
                        else {
//                            System.out.println("Comienza metodo de movimientos cancelados");
                            autoriza = this.autorizaMovimientosCancelados(movs_cancelados, cliente, clave_contrato, fecha_liquidacion, nombre_archivo, usuario, status_global, status_autoriza, status_proceso, urlArchivo, correoOrigen, correosDestino, persona_genera, verifica, realPath);
                            System.out.println("Autorizacion CON cancelaciones: " + autoriza);
                        }
                    } else {
                        autoriza = " Error obteniendo movimientos cancelados parcialmente ";
                    }
                } else {
                    autoriza = " Error obteniendo correos electrónicos ";
                }
            } else {
                autoriza = " Ningún usuarios dado de alta en el sistema cuenta con correo electrónico ";
            }
        } else {
            autoriza = "Error obteniendo la clave del fideicomitente en el sistema";
        }
        return autoriza;
    }

    /**
     * Método que actualiza el status del LayOut.
     *
     * @param usuario : Nombre del Usuario que genero los Lay_Out's
     * @param clave_cliente : clave del cliente.
     * @param clave_contrato : clave del contrato del cliente.
     * @param fecha_liquidacion : Fecha de la Liquidación.
     * @param status_global : Status general del lote.
     * @param status_autoriza : Status de autorización del lote.
     * @param proceso : Status actual del proceso.
     * @return boolean valido: Regresa true si se guardo satisfactoriamente la
     * información en la base de datos, false en otro caso.
     */
    public synchronized String eliminaLayOut(String clave_contrato, String fecha_liquidacion, String nombre_archivo,
            String usuario_autoriza, String status_global, String status_autoriza, String status_proceso,
            String correo_origen, String correo_destino, String asunto, String cuerpoCorreo,
            String urlArchivo, String resumen_liquidacion) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String MySql = "";

        boolean envia_correo = false;
        String seGuardo = "";
        System.out.println("Eliminando registros de base de datos .......");

        try {
            connection = conn.ConectaSQLServer();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            MySql = " delete ";
            MySql += " from movimientos ";
            MySql += " where clave_contrato = '" + clave_contrato + "' ";
            MySql += " and fecha_liquidacion = '" + (new clsFecha().CambiaFormatoFecha("dd/MM/yyyy", "yyyy-MM-dd", fecha_liquidacion)) + " 00:00:00.000" + "' ";
            MySql += " and nombre_archivo = '" + nombre_archivo + "' ";
//            System.out.println("eliminaLayOut:" + MySql);
            statement.executeUpdate(MySql);

            MySql = " delete ";
            MySql += " from movimientos_cancelados ";
            MySql += " where clave_contrato = '" + clave_contrato + "' ";
            MySql += " and fecha_liquidacion = '" + (new clsFecha().CambiaFormatoFecha("dd/MM/yyyy", "yyyy-MM-dd", fecha_liquidacion)) + " 00:00:00.000" + "' ";
            MySql += " and nombre_archivo = '" + nombre_archivo + "' ";
//            System.out.println("eliminaLayOut:" + MySql);
            statement.executeUpdate(MySql);

            MySql = " update movimientos_h ";
            MySql += " set status='" + status_global + "',";
            MySql += " status_autoriza='" + status_autoriza + "', ";
            MySql += " usuario_autoriza='" + usuario_autoriza + "', ";
            MySql += " fecha_usuario_autoriza = getDate() ";
            MySql += " where clave_contrato = '" + clave_contrato + "' ";
            MySql += " and fecha_liquidacion = '" + (new clsFecha().CambiaFormatoFecha("dd/MM/yyyy", "yyyy-MM-dd", fecha_liquidacion)) + " 00:00:00.000" + "' ";
            MySql += " and nombre_archivo = '" + nombre_archivo + "' ";
            MySql += " and status = '" + status_proceso + "' ";
//            System.out.println("eliminaLayOut:" + MySql);
            statement.executeUpdate(MySql);
//            System.out.println("Datos eliminados de BD ...");
//            System.out.println("Enviar correo");
            envia_correo = EnvioMail.enviaCorreo(correo_origen, correo_destino, asunto, cuerpoCorreo, urlArchivo, resumen_liquidacion);
//            envia_correo = EnvioMail.enviaCorreo(correo_origen, "luis-valerio@gp.org.mx", asunto, cuerpoCorreo, urlArchivo, resumen_liquidacion);
            envia_correo = true;
//            System.out.println("Correo enviado ...");
            if (envia_correo) {
                seGuardo = "Proceso realizado correctamente";
            } else {
                seGuardo = "Error enviando notificación de cancelación al fideicomitente";
            }
            if (seGuardo.equals("Proceso realizado correctamente")) {
                connection.commit();
            } else {
                connection.rollback();
            }
            statement.close();
            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            try {
                connection.rollback();
                statement.close();
                if (connection != null) {
                    conn.Desconecta(connection);
                }
                seGuardo = "Error cancelando movimientos";
                System.out.println("AuthorizationModel-eliminaLayOut:" + e.toString());
            } catch (Exception ex) {
                seGuardo = "Error cancelando movimientos";
                System.out.println("AuthorizationModel-eliminaLayOut:" + ex.toString());
            }
        }
        return seGuardo;
    }

    /**
     * Método que GENERA el reporte de Liquidación en formato pdf
     * correspondiente a la información que se pasa como parametro, el reporte
     * se almacena en el directorio correspondiente al ciente en cuestion.
     *
     * @param mov : Tipo de Moviniento.
     * @param archivo_jasper : Ruta del archivo .jrxml correspondiente al tipo
     * de movimiento anterior (./Common/LayOutMx.jrxml).
     * @param fecha_liquidacion : Fecha de Liquidación.
     * @param clave_contrato : clave del contrato del cliente.
     * @param cliente : Nombre del cliente.
     * @param lcnnConexion : Conexión a la Base de Datos.
     * @param nombre_archivo : Nombre del Lay-Out al que corresponden los
     * movimientos.
     * @return boolean valido: Regresa true si se genero satisfactoriamente,
     * else en otro caso
     */
    private synchronized boolean creaReporteLiquidacion(String archivo_jasper, String clave_contrato, String fecha_liquidacion,
            String nombre_archivo, String monto1, String monto2, String monto3, String monto4, String monto5, String montoTotalMXP,
            String movs_tipo1, String movs_tipo2, String movs_tipo3, String movs_tipo4, String movs_tipo5, String total_movs,
            String fecha_carga_lote, String fecha_hoy, String url, int idx, String saldo_actual, String nuevo_saldo, Connection lcnnConexion, String realPath) {

        String archivo = archivo_jasper;
        String clave_fidei = "";
        String reporte = "";
        String fecha = "";

        JasperReport report = null;
        OutputStream output = null;
        JasperPrint print = null;
        boolean genera = false;

        try {
            //Damos formato a la fecha de liquidación AAMMDD
            fecha = ModeloLiquidation.getFormatoFecha(fecha_liquidacion);
            //Verificamos si el nombre del archivo es valido
            if (archivo == null || archivo.equals("")) {
                System.out.println("creaReporteLiquidacion : Archivo *.jrxml invalido");
                return false;
            }
            try {
                //report = (JasperReport) JRLoader.loadObject(archivo);
                report = JasperCompileManager.compileReport(archivo);
                //Campilamos el Reporte de Liquidación.
                // report = JasperCompileManager.compileReport(archivo);
                //Creamos el archivo jasper para mandar pdf a pantalla
//                JasperCompileManager.compileReportToFile(archivo, "./Common/ReporteLiquidacion.jasper");
            } catch (JRException jre) {
                System.out.println("creaReporteLiquidacion:" + jre.toString());
                return false;
            }
            //Parámetros
            Map parametro = new HashMap();

            parametro.put("importe_M1", monto1);
            parametro.put("importe_M2", monto2);
            parametro.put("importe_M3", monto3);
            parametro.put("importe_M4", monto4);
            parametro.put("importe_M5", monto5);
            parametro.put("importe_total_MXP", montoTotalMXP);

            //Parametros que se agregan al reporte de liquidacion de saldos
            parametro.put("saldo_actual", saldo_actual);
//                parametro.put("txt_Nuevo_saldo", txt_Nuevo_saldo);
            parametro.put("nuevo_saldo", nuevo_saldo);

            parametro.put("total_movs_tipo1", movs_tipo1);
            parametro.put("total_movs_tipo2", movs_tipo2);
            parametro.put("total_movs_tipo3", movs_tipo3);
            parametro.put("total_movs_tipo4", movs_tipo4);
            parametro.put("total_movs_tipo5", movs_tipo5);
            parametro.put("total_movimientos", total_movs);

            parametro.put("logo", realPath + "\\images\\logo.jpg");

            parametro.put("clave_contrato", clave_contrato);
            parametro.put("fecha_liquidacion", fecha_liquidacion);
            parametro.put("nombre_archivo", nombre_archivo);

            parametro.put("fecha_carga", fecha_carga_lote);
            parametro.put("fecha_hoy", fecha_hoy);

            clave_fidei = clave_contrato.substring(6, 9);
            if (idx != -1) {
                if (idx > 0 && idx <= 9) {
                    //Creamos la salida del archivo generado
                    reporte = clave_fidei + "-0" + idx + "-" + "LQ" + "-01-" + fecha + ".pdf";
                    output = new FileOutputStream(new File(url + reporte));
                } else {
                    //Creamos la salida del archivo generado
                    reporte = clave_fidei + "-" + idx + "-" + "LQ" + "-01-" + fecha + ".pdf";
                    output = new FileOutputStream(new File(url + reporte));
                }
                //Generamos el reporte
                print = JasperFillManager.fillReport(report, parametro, lcnnConexion);
                //Exportamos a PDF
                JasperExportManager.exportReportToPdfStream(print, output);

                output.flush();
                output.close();
                genera = true;

            } else {
                genera = false;
            }
        } catch (Exception e) {
            genera = false;
            System.out.println("AuthorizationModel-creaReporteLiquidacion:" + e.getMessage());
            try {
                if (output != null) {
                    output.flush();
                    output.close();
                }
            } catch (IOException ioe) {
                System.out.println("AuthorizationModel-creaReporteLiquidacion:" + ioe.getMessage());
            }
        }
        return genera;
    }

    /**
     * Método que manda llamar al proceso correspondiente para GENERA el reporte
     * de Liquidación en formato pdf correspondiente a la información que se
     * pasa como parametro.
     *
     * @param clave_contrato : clave del contrato del cliente.
     * @param fecha_liquidacion : Fecha de Liquidación.
     * @param nombre_archivo : Nombre del Lay-Out al que corresponden los
     * movimientos.
     * @param resumen: Resumen de Movimientos obtenidos al validar el archivo.
     * @param url : Ruta del directorio donde se va a crear el reporte de
     * liquidación.
     * @param idx_archivo: Clave unica que identifica al reporte que se va a
     * generar.
     * @param lcnnConexion : Conexión a la Base de Datos.
     * @return boolean valido: Regresa true si se genero satisfactoriamente,
     * else en otro caso
     */
    public boolean generaReporteLiquidacion(String clave_contrato, String fecha_liquidacion, String nombre_archivo,
            ResumenMovimientos resumen, String url, int idx_archivo, String status, Connection connection, String realPath) {

        clsFecha fecha = new clsFecha();
        String mov1 = "", mov2 = "", mov3 = "", mov4 = "", mov5 = "", importeTotalMXP = "";
        String movs_tipo1 = "", movs_tipo2 = "", movs_tipo3 = "", movs_tipo4 = "", movs_tipo5 = "", total_movs = "";
        String saldo_actual = "", txt_NuevoSaldo = "", nuevo_saldo = "";
        DecimalFormat formato = new DecimalFormat("$ #,##0.00");

        String fecha_hoy = "", fecha_carga_lote = "";
        boolean seGuardo = false;

        try {
            mov1 = resumen.getPago_mov_tipo1() + "";
            mov2 = resumen.getPago_mov_tipo2() + "";
            mov3 = resumen.getPago_mov_tipo3() + "";
            mov4 = resumen.getPago_mov_tipo4() + "";
            mov5 = resumen.getPago_mov_tipo5() + "";
            importeTotalMXP = resumen.getImporteTotalMXP() + "";

            //Obtenemos los datos sobre saldos
            saldo_actual = formato.format(Double.parseDouble(resumen.getSaldo_actual()));
            txt_NuevoSaldo = resumen.getTxt_Nuevo_saldo();
            nuevo_saldo = formato.format(Double.parseDouble(resumen.getNuevo_saldo()));

            movs_tipo1 = resumen.total_movs_tipo1 + "";
            movs_tipo2 = resumen.total_movs_tipo2 + "";
            movs_tipo3 = resumen.total_movs_tipo3 + "";
            movs_tipo4 = resumen.total_movs_tipo4 + "";
            movs_tipo5 = resumen.total_movs_tipo5 + "";
            total_movs = resumen.total_movimientos + "";
            fecha_hoy = fecha.getFechaHoy();
            fecha_carga_lote = ModeloLiquidation.getFechaCaptura(clave_contrato, fecha_liquidacion, nombre_archivo, status);
            fecha_carga_lote = new clsFecha().CambiaFormatoFecha("yyyy-MM-dd HH:mm:ss.SSS", "dd/MM/yyyy HH:mm:ss", fecha_carga_lote);

            //version 3: con estado de cuenta saldos
            seGuardo = this.creaReporteLiquidacion(realPath + "\\WEB-INF\\classes\\Common\\RL_FINAL_SALDOS.jrxml", clave_contrato, fecha_liquidacion, nombre_archivo,
                    mov1, mov2, mov3, mov4, mov5, importeTotalMXP, movs_tipo1, movs_tipo2, movs_tipo3, movs_tipo4, movs_tipo5, total_movs, fecha_carga_lote, fecha_hoy, url, idx_archivo,
                    saldo_actual, nuevo_saldo, connection, realPath);

        } catch (Exception e) {
            seGuardo = false;
            System.out.println("AuthorizationModel-generaReporteLiquidacion:" + e.getMessage());
        }

        return seGuardo;
    }

    /**
     * Método que genera y envia por correo electrónico el reporte de
     * Liquidación con los datos que se le como parámetro.
     *
     * @param clave_contrato : Clave de Fideicomiso
     * @param fecha_liquidacion : Fecha de Liquidación.
     * @param fileName : Nombre del LayOut.
     * @param resumenMovimientos : Resumen de transacciones.
     * @param correoOrigen : Nombre del correo Emisor.
     * @param correoDestino : Correos destino.
     * @param asunto : Asunto del correo electrónico.
     * @param texto : Mensaje del Correo.
     * @param url : URL del archivo a Adjuntar.
     * @return String valido: Mensaje obtenido al realizar las transacciones, si
     * ocurrio algun error regresa una cadena vacia.
     */
    public String generaReporteLiquidacion_MXP(String clave_contrato, String fecha_liquidacion, String fileName,
            ResumenMovimientos resumenMovimientos, int idx_archivo, String status, String url, String realPath) {
        Connection connection = null;
        clsConexion conn = new clsConexion();
        String mensaje = "";
        boolean genera = false;
        try {
            //Realizamos una conexión a la BD.
            connection = conn.ConectaSQLServer();
            // Se crea el PDF del Reporte de Liquidación.
            genera = this.generaReporteLiquidacion(clave_contrato, fecha_liquidacion, fileName, resumenMovimientos, url, idx_archivo, status, connection, realPath);
            //Verificamos si se creo satisfactoriamente el Reporte de Liquidación.
            if (!genera) {
                mensaje = " Error generando reporte de liquidación ";
            }
            //Cerramos la conexión a la Base de Datos.
            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            mensaje = " Error al generar el reporte de liquidación ";
            System.out.println("AuthorizationModel-generaReporteLiquidacion_MXP:" + e.getMessage());
            //Cerramos la conexión a la Base de Datos.
            if (connection != null) {
                conn.Desconecta(connection);
            }
        }
        return mensaje;
    }
//ya se utilizo el formato de fecha BD y se paso por parametro 2

    public static Vector getTotalLiquidacion(String cliente, String clave_contrato, String fecha_liquidacion, String nombre_archivo, String status) {
        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        Vector receptores = new Vector();
        String[] args = null;
        String MySql = "";
        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select l.tipo_movimiento, count(h.clave_contrato) as numero_movimientos ,sum(cast(l.importe_liquidacion as float)) as importe_total ";
            MySql += " from movimientos_h h , movimientos l , contratos contratos   ";
            MySql += " where h.clave_contrato = l.clave_contrato   ";
            MySql += " and l.clave_contrato = contratos.clave_contrato ";
            MySql += " and h.fecha_liquidacion = l.fecha_liquidacion  ";
            MySql += " and h.nombre_archivo=l.nombre_archivo ";
            MySql += " and contratos.clave_contrato = '" + clave_contrato + "'";
            MySql += " and l.fecha_liquidacion = '" + fecha_liquidacion + " 00:00:00.000" + "'";
            MySql += " and contratos.nombre_cliente = '" + cliente + "'";
            MySql += " and h.nombre_archivo = '" + nombre_archivo + "'";
            MySql += " and contratos.status = 'A'  ";
            MySql += " and h.status = '" + status + "'  ";
            MySql += " group by l.tipo_movimiento  ";
//            System.out.println("getTotalLiquidacion:" + MySql);
            System.out.println("Fecha a utlizar en BD_getTotalLiquidacion:" + fecha_liquidacion + " 00:00:00.000");
            ResultSet rstSQLServer = statement.executeQuery(MySql);
            while (rstSQLServer.next()) {
                args = new String[3];
                args[0] = rstSQLServer.getString(1).toString().trim(); //Tipo de movimiento
                args[1] = rstSQLServer.getString(2).toString().trim(); //Número de movimientos
                args[2] = rstSQLServer.getString(3).toString().trim(); //Suma Total del importe
                if (args[0].equals("5")) {
                    Double importeT5 = getImporteMovsT5(clave_contrato, fecha_liquidacion, nombre_archivo);
                    args[2] = importeT5 + "";
                }
                receptores.add(args);
            }
            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            receptores = null;
            if (connection != null) {
                conn.Desconecta(connection);
            }
            System.out.println("AuthorizationModel-getTotalLiquidacion:" + e.getMessage());
        }
        return receptores;
    }

    /**
     * Obtiene un resumen de los movimientos incluidos en la orden de
     * liquidacion con tres elementos: Tipo;No_mvs;Importe
     *
     * @param cliente
     * @param clave_contrato
     * @param fecha_liquidacion
     * @param nombre_archivo
     * @param status
     * @return
     */
    public static Vector getResumenMovsPorTipo(String cliente, String clave_contrato, String fecha_liquidacion, String nombre_archivo, String status) {
        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        Vector receptores = new Vector();
        String[] args = null;
        String MySql = "";
        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select l.tipo_movimiento, count(h.clave_contrato) as numero_movimientos ,sum(cast(l.importe_liquidacion as float)) as importe_total ";
            MySql += " from movimientos_h h , movimientos l , contratos contratos   ";
            MySql += " where h.clave_contrato = l.clave_contrato   ";
            MySql += " and l.clave_contrato = contratos.clave_contrato ";
            MySql += " and h.fecha_liquidacion = l.fecha_liquidacion  ";
            MySql += " and h.nombre_archivo=l.nombre_archivo ";
            MySql += " and contratos.clave_contrato = '" + clave_contrato + "'";
            MySql += " and l.fecha_liquidacion = '" + fecha_liquidacion + " 00:00:00.000" + "'";
            MySql += " and contratos.nombre_cliente = '" + cliente + "'";
            MySql += " and h.nombre_archivo = '" + nombre_archivo + "'";
            MySql += " and contratos.status = 'A'  ";
            MySql += " and h.status = '" + status + "'  ";
            MySql += " group by l.tipo_movimiento  ";
//            System.out.println("getTotalLiquidacion:" + MySql);
            System.out.println("Fecha a utlizar en BD_getTotalLiquidacion:" + fecha_liquidacion + " 00:00:00.000");
            ResultSet rstSQLServer = statement.executeQuery(MySql);
            while (rstSQLServer.next()) {
                args = new String[3];
                args[0] = rstSQLServer.getString(1).toString().trim(); //Tipo de movimiento
                args[1] = rstSQLServer.getString(2).toString().trim(); //Número de movimientos
                args[2] = rstSQLServer.getString(3).toString().trim(); //Suma Total del importe
                receptores.add(args);
            }
            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            receptores = null;
            if (connection != null) {
                conn.Desconecta(connection);
            }
            System.out.println("AuthorizationModel-getTotalLiquidacion:" + e.getMessage());
        }
        return receptores;
    }

    public static Double getImporteTotal(String clave_contrato, String fecha_liquidacion, String nombre_archivo) {
        Double importeTotal = 0d;

        importeTotal = getImporteMovsMXP(clave_contrato, fecha_liquidacion, nombre_archivo) + getImporteMovsT5(clave_contrato, fecha_liquidacion, nombre_archivo);

        return importeTotal;
    }

    /**
     * Obtiene el importe total de la liquidacion por concepto de movimientos
     * 1,2,3 y 4
     *
     * @param clave_contrato
     * @param fecha_liquidacion
     * @param nombre_archivo
     * @return
     */
    public static Double getImporteMovsMXP(String clave_contrato, String fecha_liquidacion, String nombre_archivo) {
        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String MySql = "";
        Double importe = 0d;
        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select sum(cast(l.importe_liquidacion as float)) as importe_total ";
            MySql += " from movimientos_h h , movimientos l , contratos contratos   ";
            MySql += " where h.clave_contrato = l.clave_contrato   ";
            MySql += " and l.clave_contrato = contratos.clave_contrato ";
            MySql += " and h.fecha_liquidacion = l.fecha_liquidacion  ";
            MySql += " and h.nombre_archivo=l.nombre_archivo ";
            MySql += " and contratos.clave_contrato = '" + clave_contrato + "'";
            MySql += " and l.fecha_liquidacion = '" + fecha_liquidacion + " 00:00:00.000" + "'";
            MySql += " and h.nombre_archivo = '" + nombre_archivo + "'";
            MySql += " and l.tipo_movimiento!=5 ";
            MySql += " and contratos.status = 'A'  ";
            MySql += " and h.status = 'P'  ";
            ResultSet rstSQLServer = statement.executeQuery(MySql);
            if (rstSQLServer.next()) {
                importe = rstSQLServer.getDouble(1);
            }
            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            importe = -1d;
            if (connection != null) {
                conn.Desconecta(connection);
            }
            System.out.println("AuthorizationModel-getTotalLiquidacionLote:" + e.getMessage());
        }
        return importe;
    }

    /**
     * Obtiene el importe total de la liquidacion por concepto de movimientos 5
     *
     * @param clave_contrato
     * @param fecha_liquidacion
     * @param nombre_archivo
     * @return
     */
    public static Double getImporteMovsT5(String clave_contrato, String fecha_liquidacion, String nombre_archivo) {
        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String MySql = "";
        Double importe = 0d;
        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select sum(cast(l.importe_liquidacion_mxp as float)) as importe_total_mxp ";
            MySql += " from movimientos_h h , movimientos l , contratos contratos   ";
            MySql += " where h.clave_contrato = l.clave_contrato   ";
            MySql += " and l.clave_contrato = contratos.clave_contrato ";
            MySql += " and h.fecha_liquidacion = l.fecha_liquidacion  ";
            MySql += " and h.nombre_archivo=l.nombre_archivo ";
            MySql += " and contratos.clave_contrato = '" + clave_contrato + "'";
            MySql += " and l.fecha_liquidacion = '" + fecha_liquidacion + " 00:00:00.000" + "'";
            MySql += " and h.nombre_archivo = '" + nombre_archivo + "'";
            MySql += " and l.tipo_movimiento=5 ";
            MySql += " and contratos.status = 'A'  ";
            MySql += " and h.status = 'P' ";
            ResultSet rstSQLServer = statement.executeQuery(MySql);
            if (rstSQLServer.next()) {
                importe = rstSQLServer.getDouble(1);
            }
            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            importe = -1d;
            if (connection != null) {
                conn.Desconecta(connection);
            }
            System.out.println("AuthorizationModel-getTotalLiquidacionLote:" + e.getMessage());
        }
        return importe;
    }

    public static ResumenMovimientos getResumenMovimientos(Vector resumenMovs, String clave_contrato, String fecha_liquidacion) {

        //ya esta modificado
        BigDecimal hf = null;
        BigDecimal iva_hf = null;
        BigDecimal tmp = BigDecimal.ZERO;
        BigDecimal importe = BigDecimal.ZERO;
        BigDecimal importe_total = BigDecimal.ZERO;

        int tipo_movimiento = -1;
        int numero_movimientos = 0;
        int total_movs = 0;
        String importe_l = "";
        String importe_total_movimiento = "";
        ResumenMovimientos rl = new ResumenMovimientos();
        String[] args = new String[3];
        try {
            for (int i = 0; i < resumenMovs.size(); i++) {
                args = (String[]) resumenMovs.get(i);
                tipo_movimiento = Integer.parseInt(args[0]);
                numero_movimientos = Integer.parseInt(args[1]);
                importe_total_movimiento = args[2];
                importe = new BigDecimal(importe_total_movimiento);
                total_movs += numero_movimientos;
                switch (tipo_movimiento) {
                    case 1:
                        rl.setTotal_movs_tipo1(numero_movimientos);
                        rl.setPago_mov_tipo1(importe);
                        importe_total = importe_total.add(importe);
                        break;
                    case 2:
                        rl.setTotal_movs_tipo2(numero_movimientos);
                        rl.setPago_mov_tipo2(importe);
                        importe_total = importe_total.add(importe);
                        break;
                    case 3:
                        rl.setTotal_movs_tipo3(numero_movimientos);
                        rl.setPago_mov_tipo3(importe);
                        importe_total = importe_total.add(importe);
                        break;
                    case 4:
                        rl.setTotal_movs_tipo4(numero_movimientos);
                        rl.setPago_mov_tipo4(importe);
                        importe_total = importe_total.add(importe);
                        break;
                    case 5:
                        rl.setTotal_movs_tipo5(numero_movimientos);
                        rl.setPago_mov_tipo5(importe);
                        importe_total = importe_total.add(importe);
                        break;
                    default:
                        break;
                }
            }//End for
            //Especificamos el importe total en MXP.
            rl.setImporteTotalMXP(importe_total);
            rl.setTotal_movimientos(total_movs);

            importe_l = importe_total + "";
            //Especificamos la base y el porcentaje de honorarios sin IVA.
            String honSinIva = ModelUpdate.getTipoHonorario(clave_contrato);
            //Especificamos la Suficiencia Patronal.
            String sp_c = AuthorizationModel.getSuficienciaPatronal(importe_l, honSinIva, "16");
            rl.setSPR(new BigDecimal(sp_c));
            //Especificamos el monto de los honorarios fiduciarios.
            String hf_c = AuthorizationModel.getHonorariosFiduciarios(importe_l, sp_c, honSinIva);
            rl.setAFI(hf_c);
            //Especificamos el monto del IVA correspondiente a los honorarios fiduciarios.
            String xIva = ModelUpdate.getIVA(hf_c);
            rl.setIVA(xIva);

            hf = new BigDecimal(Double.parseDouble(hf_c));
            iva_hf = new BigDecimal(Double.parseDouble(xIva));

            hf = hf.add(iva_hf);
            hf_c = hf.setScale(2, BigDecimal.ROUND_HALF_UP) + "";
            //Especificamos el monto de los honorarios fiduciarioscon IVA.
            rl.setHonorarios_fidu(hf_c);

            //Se realiza la consulta del saldo actual
            String saldo = "";
            double nuevoSaldo = 0.0;
            saldo = AuthorizationModel.getSaldo(clave_contrato);
            if (saldo != "") {
                nuevoSaldo = Double.parseDouble(saldo) - Double.parseDouble(rl.getImporteTotalMXP().toString());
                DecimalFormat formato = new DecimalFormat("0.00");
                rl.setSaldo_actual(formato.format(Double.parseDouble(saldo)));
                rl.setTxt_Nuevo_saldo("Patrimonio disponible");
                rl.setNuevo_saldo(formato.format(nuevoSaldo));
            } else {
                System.out.println("Error obteniendo el saldo del fieicomiso en AuthorizationModel: ");
                rl = null;
            }

        } catch (Exception e) {
            System.out.println("AuthorizationModel-getResumenMovimientos:" + e.getMessage());
            rl = null;
        }
        return rl;
    }

    /**
     * Método para obtener la suficiencia Patrimonial Requerida con los datos
     * que se le pasan como parámetro.
     *
     * @param monto_MXP : Importe a sumar.
     * @param monto_HF : Importe a sumar.
     * @return String: Resultado de sumar monto_MXP y monto_HF; si ocurre un
     * error regresa una cadena vacia.
     */
    public static BigDecimal getSPR(BigDecimal montoTotalMXP, BigDecimal montoHF) {
        String trunc = "";
        int tmp = 0;

        try {
            //sumamos las dos cantidades.
            montoTotalMXP = montoTotalMXP.add(montoHF);
            //truncamos el numero a dos decimales
            montoHF = new BigDecimal("100");
            montoTotalMXP = montoTotalMXP.multiply(montoHF);
            trunc = montoTotalMXP + "";
            tmp = trunc.indexOf(".");

            if (tmp != -1) {
                montoTotalMXP = new BigDecimal(trunc.substring(0, tmp));
            }
            montoTotalMXP = montoTotalMXP.divide(montoHF);

        } catch (Exception e) {
            System.out.println("AuthorizationModel-sumaImporte:" + e.getMessage());
            return null;

        }
        return montoTotalMXP;

    }

    /**
     * Método que regresa el correo electrónico del usuario del sistema que se
     * le pasa como parámetro.
     *
     * @param clave_cliente : clave asociada al fideicomitente en el sistema.
     * @param usuario : Nombre de usuario.
     * @return String : correo electrónico.
     */
    public static String getCorreoUsuario(String clave_cliente, String clave_contrato, String fecha_liquidacion, String nombre_archivo) {
        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String correo = "";
        String MySql = "";
        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select contacto_usuario ";
            MySql += " from usuarios ";
            MySql += " where clave_contrato='" + clave_contrato.trim() + "' ";
            MySql += " and clave_cliente='" + clave_cliente.trim() + "' ";
            MySql += " and usuario in ( ";
            MySql += " select usuario_cliente ";
            MySql += " from movimientos_h ";
            MySql += " where clave_contrato = '" + clave_contrato.trim() + "' ";
            MySql += " and fecha_liquidacion = '" + fecha_liquidacion.trim() + " 00:00:00.000' ";
            MySql += " and nombre_archivo = '" + nombre_archivo.trim() + "' ) ";
            MySql += " and status='A' ";
//            System.out.println(MySql);
            ResultSet rstSQLServer = statement.executeQuery(MySql);
            if (rstSQLServer.next()) {
                correo = rstSQLServer.getString(1).toString().trim();
            }
            rstSQLServer.close();
            statement.close();
            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            correo = "Error obteniendo el e-mail del usuario que cargó el Lay-Out";
            System.out.println("AuthorizationModel-getCorreoUsuario:" + e.toString());
        }
        return correo;
    }

    /**
     * Método que los correos electrónicos de todos los usuarios de un
     * fideicomiso(externo).
     *
     * @param clave_cliente : clave asociada al fideicomitente en el sistema.
     * @param clave_contrato : Clave de fideicomiso.
     * @return String : Correos : usuario1,usuario2,usuario3...
     */
    public static String getCorreoUsuariosFideicomiso(String clave_cliente, String clave_contrato) {
        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String correo = "";
        String correos = "";
        String MySql = "";
        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select contacto_usuario ";
            MySql += " from usuarios ";
            MySql += " where clave_contrato='" + clave_contrato.trim() + "' ";
            MySql += " and clave_cliente='" + clave_cliente.trim() + "' ";
            MySql += " and status='A' ";
//            System.out.println(MySql);

            ResultSet rstSQLServer = statement.executeQuery(MySql);
            while (rstSQLServer.next()) {
                correo = rstSQLServer.getString(1).toString().trim();
                if (correos.equals("")) {
                    correos = correo;
                } else {
                    correos = correos + "," + correo;
                }
            }
            rstSQLServer.close();
            statement.close();
            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            correo = "Error obteniendo correos";
            System.out.println("AuthorizationModel-getCorreoUsuario:" + e.toString());
        }
        return correos;//Esta es la original, se busca el correo en la BD y se genera la accion

    }

    /**
     * Método que proporciona todos los movimientos de un lote.
     *
     * @param clave_contrato : Clave de fideicomiso.
     * @param fecha_liquidacion : Fecha en que tendrá lugar la liquidación.
     * @param nombre_archivo : Nombre del LayOut.
     * @param status: Status de lote.
     * @return Vector: Objetos de tipo Movimiento.
     */
    public static Vector getMovimientos(String clave_contrato, String fecha_liquidacion, String nombre_archivo, String status) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;

        Vector movimientos = new Vector();
        Movimiento movimiento = null;
        String nombre_empleado = "";
        String apellidoP_empleado = "";
        String apellidoM_empleado = "";
        String MySql = "";
        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select l.cuenta_deposito, l.apellidoP_empleado, apellidoM_empleado, l.nombre_empleado,";
            MySql += " l.tipo_movimiento, l.clave_banco, l.importe_liquidacion, l.tipo_moneda ";
            MySql += " from movimientos_h h, movimientos l  ";
            MySql += " where h.clave_contrato= l.clave_contrato ";
            MySql += " and h.fecha_liquidacion= l.fecha_liquidacion ";
            MySql += " and h.nombre_archivo= l.nombre_archivo ";
            MySql += " and h.clave_contrato = '" + clave_contrato + "' ";
            MySql += " and h.fecha_liquidacion = '" + fecha_liquidacion + " 00:00:00.000'";
            MySql += " and h.nombre_archivo = '" + nombre_archivo + "'";
            MySql += " and h.status = '" + status + "'";
            MySql += " order by apellidoP_empleado asc ";

//            System.out.println("getMovimientos:" + MySql);
            ResultSet rstSQLServer = statement.executeQuery(MySql);

            while (rstSQLServer.next()) {
                movimiento = new Movimiento();
                movimiento.setCuenta_deposito(rstSQLServer.getString(1));//Asignamos la cuenta depósito.
                apellidoP_empleado = rstSQLServer.getString(2);
                apellidoM_empleado = rstSQLServer.getString(3);
                nombre_empleado = rstSQLServer.getString(4);
                movimiento.setApellidoP_empleado(apellidoP_empleado);
                movimiento.setApellidoM_empleado(apellidoM_empleado);
                movimiento.setNombre_empleado(nombre_empleado);
                movimiento.setNombre_fideicomisario(apellidoP_empleado + " " + apellidoM_empleado + " " + nombre_empleado); //Asignamos el nombre del fideicomisario.
                movimiento.setTipo_movimiento(rstSQLServer.getString(5)); //Asignamos el tipo de movimiento.
                movimiento.setClave_banco(rstSQLServer.getString(6)); //Asignamos la clave de banco.
                movimiento.setImporte_liquidacion(rstSQLServer.getString(7)); //Asignamos el importe de liquidación.
                movimiento.setClave_moneda(rstSQLServer.getString(8)); //Asignamos la clave de moneda.
                movimientos.add(movimiento);
            }
            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            movimientos = null;
            if (connection != null) {
                conn.Desconecta(connection);
            }
            System.out.println("AuthorizationModel-getMovimientos:" + e.getMessage());
        }
        return movimientos;
    }

    /**
     * Método que elimina los movimientos que se especifican como parámetro.
     *
     * @param movimientos : Movimientos de un LayOut en particular.
     * @param clave_contrato : clave de fideicomiso del lote a modificar.
     * @param fecha_liquidacion : Fecha de liquidación del lote a modificar.
     * @param nombre_archivo : Nombre del LayOut cargado al que pertenecen los
     * movimientos e eliminar.
     * @param usuario_cancela : Usuario que realiza la cancelación parcial.
     * @param status : Status del proceso.
     * @return boolean valido: Regresa true si se eliminaron los registros
     * especificados de la base de datos, false en otro caso.
     */
    public synchronized boolean cancelaMvs(Vector movimientos, String clave_contrato, String fecha_liquidacion, String nombre_archivo, String usuario_cancela, String status) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;

        ArrayList ids_movs = null;
        String[] id_movs = null;
        Movimiento movimiento = null;
        boolean seGuardo = false;
        String MySql = "";
        int idx = 0;

        try {
            connection = conn.ConectaSQLServer();
            connection.setAutoCommit(false);
            statement = connection.createStatement();

            ids_movs = AuthorizationModel.getElimina_movs();

            for (int i = 0; i < ids_movs.size(); i++) {
                id_movs = (String[]) ids_movs.get(i);
                if (id_movs != null) {
                    for (int j = 0; j < id_movs.length; j++) {
                        idx = (i * 10) + Integer.parseInt(id_movs[j]);
                        movimiento = (Movimiento) movimientos.get(idx);
                        MySql = " delete ";
                        MySql += " from movimientos ";
                        MySql += " where clave_contrato = '" + clave_contrato + "' ";
                        MySql += " and fecha_liquidacion = '" + fecha_liquidacion + " 00:00:00.000" + "' ";
                        MySql += " and nombre_archivo = '" + nombre_archivo + "' ";
                        MySql += " and apellidoP_empleado = '" + movimiento.getApellidoP_empleado() + "' ";
                        MySql += " and apellidoM_empleado = '" + movimiento.getApellidoM_empleado() + "' ";
                        MySql += " and nombre_empleado = '" + movimiento.getNombre_empleado() + "' ";
                        MySql += " and cuenta_deposito = '" + movimiento.getCuenta_deposito() + "' ";
                        MySql += " and tipo_movimiento = '" + movimiento.getTipo_movimiento() + "' ";
                        MySql += " and clave_banco = '" + movimiento.getClave_banco() + "' ";
                        MySql += " and importe_liquidacion = '" + movimiento.getImporte_liquidacion() + "' ";
                        MySql += " and tipo_moneda = '" + movimiento.getClave_moneda() + "' ";
//                        System.out.println("cancelaMovs:" + MySql);
                        // Se ejecuta el encabezado
                        statement.executeUpdate(MySql);
                    }
                }
            }
            MySql = " update movimientos_h ";
            MySql += " set status_autoriza='" + status + "', ";
            MySql += " usuario_autoriza='" + usuario_cancela + "', ";
            MySql += " fecha_usuario_autoriza = getDate() ";
            MySql += " where clave_contrato = '" + clave_contrato + "' ";
            MySql += " and fecha_liquidacion = '" + fecha_liquidacion + " 00:00:00.000" + "' ";
            MySql += " and nombre_archivo = '" + nombre_archivo + "' ";

//            System.out.println("cancelaMovs:" + MySql);
            // Se ejecuta el encabezado
            statement.executeUpdate(MySql);

            connection.commit();
            seGuardo = true;
//            System.out.println("Transaction commit...");
            statement.close();
            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            e.printStackTrace();
            seGuardo = false;
            System.out.println("AuthorizationModel-cancelaMovs:" + e.toString());

            if (connection != null) {
                conn.Desconecta(connection);
            }
            try {
                statement.close();
            } catch (Exception ex) {
            }
        }
        return seGuardo;
    }

    /**
     * Método que elimina movimientos cancelados prcialmente, envia correo de
     * notificación al cliente, en caso no tener moviientos a bancos extranjeros
     * genera los respectivos reportes de liquidación y actualiza status global
     * del lote.
     *
     * @param movs_cancelados: Movimientos cancelados parcialmente.
     * @param cliente: Nombre del fideicomitente.
     * @param clave_contrato : clave de contrato corresponiente al lote.
     * @param fecha_liquidacion : Fecha de Liquidación correspondiente al lote.
     * @param nombre_archivo : Nombre del lote al que corresponden los
     * movimientos.
     * @param usuario : Nombre del usuario que genera transacciones.
     * @param status_global : Status general del lote.
     * @param status_autoriza : Status de la autorización correspondiente.
     * @param status_proceso :Status de la operación.
     * @param verifica : Número de lotes procesados satisfactoriamente.
     * @return String valido: Regresa un mensaje descriptivo de la operación
     * realizada, en caso de todo salir bien regresa una cadena vacia..
     */
    public synchronized String autorizaMovimientosCancelados(Vector movs_cancelados, String cliente, String clave_contrato,
            String fecha_liquidacion, String nombre_archivo, String usuario, String status_global, String status_autoriza,
            String status_proceso, String urlArchivo, String correoOrigen, String correosDestino, String persona_genera, int verifica, String realPath) {

        String autoriza = "";
        String correo_f = "";
        String asuntoCorreo = "";
        String cuerpoCorreo = "";
        String nombre_reportes = "";
        String resumen_liquidacion = "";
        Vector importeTotal = null;
        boolean actualiza = false;
        boolean eliminaMovs = false;
        boolean movs_in = false;
        ResumenMovimientos resumenMovs = null;
        ModeloLiquidation modelo = new ModeloLiquidation();

        //Verificamos si las transacciones ya fueron generadas por otro usuario.
        autoriza = ModeloLiquidation.verificaActualizacion(clave_contrato, fecha_liquidacion, nombre_archivo, status_proceso);

        if (autoriza.equals("")) {
            //Obtenemos el formato de fecha que se utiliza en la BD: yyyy-MM-dd
            String fecha_liquidacion_BD = new clsFecha().CambiaFormatoFecha("dd/MM/yyyy", "yyyy-MM-dd", fecha_liquidacion);
            autoriza = compruebaSaldoSuficienteCancelados(clave_contrato, fecha_liquidacion_BD, nombre_archivo);
            if (autoriza.equals("")) {
                //Eliminamos todos los movimientos cancelados parcialmente por el usuario. Elimina de movimientos y pone en status=C en cancelados.
                eliminaMovs = this.eliminaCancelacionParcial(movs_cancelados, clave_contrato, fecha_liquidacion_BD, nombre_archivo, usuario, "C");
                if (eliminaMovs) {
                    //Generamos el reporte de liquidación con la eliminación reflejada.
                    importeTotal = AuthorizationModel.getTotalLiquidacion(cliente, clave_contrato, fecha_liquidacion_BD, nombre_archivo, status_proceso);
                    if (importeTotal != null && importeTotal.size() > 0) {
                        resumenMovs = AuthorizationModel.getResumenMovimientos(importeTotal, clave_contrato, fecha_liquidacion);
                        if (resumenMovs != null) {

                            //Generamos el reporte de liquidación actualizado.
                            autoriza = this.generaReporteLiquidacion_MXP(clave_contrato, fecha_liquidacion, nombre_archivo, resumenMovs, verifica, status_proceso, urlArchivo, realPath);
                            if (autoriza.equals("")) {
                                //Obtenemos el nombre del resumen de liquidación actualizado.
                                resumen_liquidacion = ModeloLiquidation.getNombreResumenLiquidacion(clave_contrato, fecha_liquidacion, verifica);
                                if (!resumen_liquidacion.equals("")) {
                                    //Generamos los reportes de liquidación correspondientes a cada movimiento.
                                    autoriza = modelo.generaReportesLiquidacionMXP(cliente, clave_contrato, fecha_liquidacion, nombre_archivo, status_proceso, urlArchivo, persona_genera, verifica, realPath);
                                    //Verificamos si ocurrio algún error al generar los reportes.
                                    if (autoriza.equals("")) {
                                        //Verificamos si se cuenta con movimientos a bancos extranjeros.
                                        Vector clientes = ModeloLiquidation.getNombreFideicomitentes(clave_contrato, fecha_liquidacion_BD, "5", nombre_archivo, status_proceso);
                                        if (clientes != null) {
                                            if (importeTotal != null && importeTotal.size() > 0) {
                                                //Verificamos si se cuenta con movimientos a bancos extranjeros.
                                                if (clientes.size() > 0 && clientes.size() > 1) {
                                                    movs_in = true;
                                                }
                                            }
                                        }
                                        //Especificamos el conjunto de reportes de liquidación a enviar.
                                        nombre_reportes = AuthorizationModel.getReportesGenerados(clave_contrato, fecha_liquidacion_BD, nombre_archivo, "P", verifica, movs_in);
                                        if (!nombre_reportes.equals("")) {
                                            //Añadimos el nombre del resumen de liquidación actualizado.
                                            nombre_reportes = nombre_reportes + ";" + resumen_liquidacion;
                                            asuntoCorreo = "LIQUIDACIÓN " + clave_contrato + " " + fecha_liquidacion;
                                            cuerpoCorreo = AuthorizationModel.getEmailBody(movs_cancelados);
//                                        actualiza = EnvioMail.enviaCorreo(correoOrigen, "luis-valerio@gp.org.mx", "Cancelados "+asuntoCorreo, cuerpoCorreo, urlArchivo, nombre_reportes);
                                            actualiza = EnvioMail.enviaCorreo(correoOrigen, correosDestino, asuntoCorreo, cuerpoCorreo, urlArchivo, nombre_reportes);
                                            actualiza = true;
                                            if (actualiza) {
                                                correo_f = ModeloLiquidation.obtenCorreos("'OPERACION','SISTEMAS'");
//                                            actualiza = EnvioMail.enviaCorreo(correoOrigen, "luis-valerio@gp.org.mx","Cancelados facturacion"+ asuntoCorreo, cuerpoCorreo, urlArchivo, resumen_liquidacion);
                                                actualiza = EnvioMail.enviaCorreo(correoOrigen, correo_f, asuntoCorreo, cuerpoCorreo, urlArchivo, resumen_liquidacion);
                                                actualiza = true;
                                                if (actualiza) {
                                                    autoriza = ModeloLiquidation.generaExcelLote(clave_contrato, fecha_liquidacion, nombre_archivo, urlArchivo, status_proceso, verifica);
                                                    if (autoriza.equals("")) {
                                                        if (ModelUpdate.date_en_habil()) {// se verifica si el día esta dentro del rango de dias habiles de mes
                                                            actualiza = modelo.actualizaStatusAutoriza(clave_contrato, fecha_liquidacion_BD, nombre_archivo, usuario, status_global, status_autoriza, status_proceso, resumenMovs.getImporteTotalMXP().toString(), resumenMovs.getNuevo_saldo());
                                                        } else {// el dia y hora son posteriroes a la fecha de corte
                                                            //se prosigue a asignar la hora correspondiente del 1er día habil del mes siguiente
                                                            actualiza = modelo.actualizaStatusAutoriza_DiaCorte(clave_contrato, fecha_liquidacion_BD, nombre_archivo, usuario, status_global, status_autoriza, status_proceso, resumenMovs.getImporteTotalMXP().toString(), resumenMovs.getNuevo_saldo());
                                                        }

                                                        if (actualiza) {
                                                            autoriza = " Proceso realizado correctamente ";
                                                        } else {
                                                            autoriza = " Error actualizando status de la transacción ";
                                                        }
                                                    }
                                                } else {
                                                    autoriza = " Error enviando reporte de liquidación a facturación";
                                                }
                                            } else {
                                                autoriza = " Error enviando reportes de liquidación al fideicomitente";
                                            }
                                        } else {
                                            autoriza = "Error obteniendo el nombre de los reportes a enviar";
                                        }
                                    }
                                } else {
                                    autoriza = "Error obteniendo el nombre del resumen de liquidación final";
                                }
                            }

                        } else {
                            autoriza = " Error obteniendo porcentajes del resumen de liquidación actualizado ";
                        }
                    } else {
                        autoriza = " Error consultando importe total para cada movimiento ";
                    }
                } else {
                    autoriza = " Error eliminando movimientos cancelados ";
                }
            }
//            else {
//               DecimalFormat f = new DecimalFormat("$ #,##0.00");
//               autoriza = "No se puede autorizar el lote, saldo= " + f.format(Double.parseDouble(resumenMovs.getSaldo_actual())) + " insuficiente para autorizar la operación.";
//           }
        }
        return autoriza;

    }

    /**
     * Método que gestiona la cancelación total del lote.
     *
     * @param clave_cliente : Nombre del cliente asociado al lote.
     * @param cliente :Nombre del fideicomitente.
     * @param clave_contrato : Clave de fideicomiso.
     * @param fecha_liquidacion : Fecha en la que tendrá lugar la liquidación.
     * @param nombre_archivo : Nombre del Lay-Out al que corresponden los
     * movimientos.
     * @param usuario : Nombre del usuario que genera las transacciones.
     * @param status_global : Status global del LayOyt.
     * @param status_autoriza : Status de la cancelación total.
     * @param status_proceso : Status actual del lote.
     * @param asunto : Asunto del correo electrónico notificando la cancelación
     * al fideicomitente.
     * @param cuerpoCorreo : Cuerpo del correo electrónico ( html).
     * @param verifica : Identificador del lote.
     * @return String valido: Mensaje descriptivo de la operación realizada.
     */
    public synchronized String cancelacionTotalLote(String clave_cliente, String cliente, String clave_contrato,
            String fecha_liquidacion, String nombre_archivo, String usuario, String status_global,
            String status_autoriza, String status_proceso, String correo_origen, String correo_destino,
            String asunto, String cuerpoCorreo, String urlArchivo, String nombre_reporte) {
        String autoriza = "";
        //Verificamos si las transacciones ya fueron generadas por otro usuario.
        autoriza = ModeloLiquidation.verificaActualizacion(clave_contrato, fecha_liquidacion, nombre_archivo, status_proceso);
        if (autoriza.equals("")) {
            autoriza = this.eliminaLayOut(clave_contrato, fecha_liquidacion, nombre_archivo, usuario, status_global, status_autoriza, status_proceso, correo_origen, correo_destino, asunto, cuerpoCorreo, urlArchivo, nombre_reporte);
        } else {
            autoriza = " Otro usuario ha cancelado el lote correspondiente ";
        }
        return autoriza;
    }

    /**
     * Método que registra el movimiento cancelado que se especifica con los
     * datos que se pasan como parámetro.
     *
     * @param String movimineto: Información del movimineto a cancelar.
     * @param String usuario_cancela: Usuario que realiza la cancelación.
     * @param String status: Status de cancelación.
     * @return String: Mensaje descriptivo de la transacción, si todo va bien
     * regresa una cadena vacia.
     */
    public synchronized String almacenaMovimientoCancelado(Movimiento movimiento, String usuario_cancela, String status) {
        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        ResultSet rstSQLServer = null;
        String seGuardo = "";
        String MySql = "";
        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            MySql = " select l.clave_contrato  ";
            MySql += " from movimientos l, movimientos_cancelados c  ";
            MySql += " where l.clave_contrato = c.clave_contrato  ";
            MySql += " and l.fecha_liquidacion = c.fecha_liquidacion  ";
            MySql += " and l.nombre_archivo = c.nombre_archivo  ";
            MySql += " and l.tipo_movimiento = c.tipo_movimiento  ";
            MySql += " and l.clave_banco = c.clave_banco  ";
            MySql += " and l.tipo_moneda = c.tipo_moneda  ";
            MySql += " and l.nombre_empleado = c.nombre_empleado   ";
            MySql += " and l.apellidoP_empleado = c.apellidoP_empleado  ";
            MySql += " and l.apellidoM_empleado = c.apellidoM_empleado  ";
            MySql += " and l.clave_empleado = c.clave_empleado  ";
            MySql += " and l.importe_liquidacion = c.importe_liquidacion  ";
            MySql += " and l.cuenta_deposito = c.cuenta_deposito  ";
            MySql += " and l.curp = c.curp   ";
            MySql += " and l.fecha_ingreso = c.fecha_ingreso   ";
            MySql += " and l.puesto_empleado = c.puesto_empleado   ";
            MySql += " and l.depto_empleado = c.depto_empleado  ";
            MySql += " and l.envio_cheque = c.envio_cheque  ";
            MySql += " and l.destino_envio_cheque = c.destino_envio_cheque  ";
            MySql += " and l.tel_envio_cheque = c.tel_envio_cheque  ";
            MySql += " and l.correo_envio_cheque = c.correo_envio_cheque  ";
            MySql += " and l.banco_extranjero = c.banco_extranjero  ";
            MySql += " and l.dom_banco_extranjero = c.dom_banco_extranjero  ";
            MySql += " and l.pais_banco_extranjero = c.pais_banco_extranjero  ";
            MySql += " and l.ABA_BIC = c.ABA_BIC  ";
            MySql += " and l.nombre_fidei_banco_ext = c.nombre_fidei_banco_ext  ";
            MySql += " and l.direccion_fidei_ext = c.direccion_fidei_ext  ";
            MySql += " and l.pais_fidei_ext = c.pais_fidei_ext  ";
            MySql += " and l.tel_fidei_ext = c.tel_fidei_ext  ";
            MySql += " and l.clave_contrato = '" + movimiento.getClave_contrato() + "' ";
            MySql += " and l.fecha_liquidacion = '" + (new clsFecha().CambiaFormatoFecha("dd/MM/yyyy", "yyyy-MM-dd", movimiento.getFecha_liquidacion())) + " 00:00:00.000" + "' ";
            MySql += " and l.nombre_archivo = '" + movimiento.getNombre_archivo() + "' ";
            MySql += " and l.tipo_movimiento = '" + movimiento.getTipo_movimiento() + "' ";
            MySql += " and l.clave_banco = '" + movimiento.getClave_banco() + "' ";
            MySql += " and l.tipo_moneda = '" + movimiento.getClave_moneda() + "' ";
            MySql += " and l.nombre_empleado = '" + movimiento.getNombre_empleado() + "' ";
            MySql += " and l.apellidoP_empleado = '" + movimiento.getApellidoP_empleado() + "' ";
            MySql += " and l.apellidoM_empleado = '" + movimiento.getApellidoM_empleado() + "' ";
            MySql += " and l.clave_empleado = '" + movimiento.getNumero_empleado() + "' ";
            MySql += " and l.importe_liquidacion = '" + movimiento.getImporte_liquidacion() + "' ";
            MySql += " and l.cuenta_deposito = '" + movimiento.getCuenta_deposito() + "' ";
            MySql += " and l.curp = '" + movimiento.getCURP() + "' ";
            MySql += " and l.fecha_ingreso = '" + (new clsFecha().CambiaFormatoFecha("dd/MM/yyyy", "yyyy-MM-dd", movimiento.getFecha_ingreso())) + " 00:00:00.000" + "' ";
            MySql += " and l.puesto_empleado = '" + movimiento.getPuesto_empleado() + "' ";
            MySql += " and l.depto_empleado = '" + movimiento.getDepartamento_empleado() + "' ";
            MySql += " and l.envio_cheque = '" + movimiento.getNombre_receptor_cheque() + "' ";
            MySql += " and l.destino_envio_cheque = '" + movimiento.getDomicilio_destino_cheque() + "' ";
            MySql += " and l.tel_envio_cheque = '" + movimiento.getTel_destino_cheque() + "' ";
            MySql += " and l.correo_envio_cheque = '" + movimiento.getCorreo_destino_cheque() + "' ";
            MySql += " and l.banco_extranjero = '" + movimiento.getNombre_banco_extranjero() + "' ";
            MySql += " and l.dom_banco_extranjero = '" + movimiento.getDomicilio_banco_extranjero() + "' ";
            MySql += " and l.pais_banco_extranjero = '" + movimiento.getPais_banco_extranjero() + "' ";
            MySql += " and l.ABA_BIC = '" + movimiento.getABA_BIC() + "' ";
            MySql += " and l.nombre_fidei_banco_ext = '" + movimiento.getNombre_empleado_banco_extranjero() + "' ";
            MySql += " and l.direccion_fidei_ext = '" + movimiento.getDom_empleado_banco_extranjero() + "' ";
            MySql += " and l.pais_fidei_ext = '" + movimiento.getPais_empleado_banco_extranjero() + "' ";
            MySql += " and l.tel_fidei_ext = '" + movimiento.getTel_empleado_banco_extranjero() + "' ";
//            System.out.println(MySql);
            rstSQLServer = statement.executeQuery(MySql);
            if (!rstSQLServer.next()) {
                connection.setAutoCommit(false);
                MySql = " insert into movimientos_cancelados ";
                MySql += " ( clave_contrato , fecha_liquidacion , nombre_archivo , tipo_movimiento, clave_banco, tipo_moneda,"
                        + " nombre_empleado , apellidoP_empleado , apellidoM_empleado ,clave_empleado, importe_liquidacion ,importe_liquidacion_mxp,cuenta_deposito,"
                        + " curp, fecha_ingreso, puesto_empleado, depto_empleado, envio_cheque, destino_envio_cheque, tel_envio_cheque,"
                        + " correo_envio_cheque, banco_extranjero, dom_banco_extranjero, pais_banco_extranjero, ABA_BIC,"
                        + " nombre_fidei_banco_ext, direccion_fidei_ext, pais_fidei_ext, tel_fidei_ext,"
                        + " usuario_cancela, fecha_usuario_cancela, motivo, status )";
                MySql += " values  ";
                MySql += " ( ";
                MySql += " '" + movimiento.getClave_contrato() + "', ";
                MySql += " '" + (new clsFecha().CambiaFormatoFecha("dd/MM/yyyy", "yyyy-MM-dd", movimiento.getFecha_liquidacion())) + "', ";
                MySql += " '" + movimiento.getNombre_archivo() + "', ";
                MySql += " '" + movimiento.getTipo_movimiento() + "', ";
                MySql += " '" + movimiento.getClave_banco() + "', ";
                MySql += " '" + movimiento.getClave_moneda() + "', ";

                MySql += " '" + movimiento.getNombre_empleado() + "', ";
                MySql += " '" + movimiento.getApellidoP_empleado() + "', ";
                MySql += " '" + movimiento.getApellidoM_empleado() + "', ";
                MySql += " '" + movimiento.getNumero_empleado() + "', ";
                MySql += " '" + movimiento.getImporte_liquidacion() + "', ";
                MySql += " '" + movimiento.getImporte_liquidacion_mxp() + "', ";
                MySql += " '" + movimiento.getCuenta_deposito() + "', ";

                MySql += " '" + movimiento.getCURP() + "', ";
                MySql += " '" + (new clsFecha().CambiaFormatoFecha("dd/MM/yyyy", "yyyy-MM-dd", movimiento.getFecha_ingreso())) + "', ";
                MySql += " '" + movimiento.getPuesto_empleado() + "', ";
                MySql += " '" + movimiento.getDepartamento_empleado() + "', ";
                MySql += " '" + movimiento.getNombre_receptor_cheque() + "', ";
                MySql += " '" + movimiento.getDomicilio_destino_cheque() + "', ";
                MySql += " '" + movimiento.getTel_destino_cheque() + "', ";

                MySql += " '" + movimiento.getCorreo_destino_cheque() + "', ";
                MySql += " '" + movimiento.getNombre_banco_extranjero() + "', ";
                MySql += " '" + movimiento.getDomicilio_banco_extranjero() + "', ";
                MySql += " '" + movimiento.getPais_banco_extranjero() + "', ";
                MySql += " '" + movimiento.getABA_BIC() + "', ";

                MySql += " '" + movimiento.getNombre_empleado_banco_extranjero() + "', ";
                MySql += " '" + movimiento.getDom_empleado_banco_extranjero() + "', ";
                MySql += " '" + movimiento.getPais_empleado_banco_extranjero() + "', ";
                MySql += " '" + movimiento.getTel_empleado_banco_extranjero() + "', ";
                MySql += " '" + usuario_cancela + "', ";
                MySql += " getDate(), ";
                MySql += " '" + movimiento.getMotivo_cancelacion() + "', ";
                MySql += " '" + status + "' ";
                MySql += " ) ";
//                System.out.println("almacenaMovimientoCancelado:" + MySql);
                // Se ejecuta el encabezado
                statement.executeUpdate(MySql);
                // System.out.println("Transaction commit...");
                connection.commit();

                seGuardo = "Movimiento cancelado correctamente";
            } else {
                seGuardo = " El movimiento fue cancelado anteriormente ";
            }
            statement.close();
            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            System.out.println("AuthorizationModel-almacenaMovimientoCancelado:" + e.toString());
            seGuardo = " Error realizando la cancelación del movimiento ";
            e.printStackTrace();
            try {
                connection.rollback();
//                System.out.println("Connection rollback...");
                if (connection != null) {
                    conn.Desconecta(connection);
                }
            } catch (Exception e2) {
                System.out.println("AuthorizationModel-almacenaMovimientoCancelado:" + e2.toString());
            }
        }
        return seGuardo;
    }

    /**
     * Método que elimina el movimiento que se especifica de las cancelaciones
     * parciales
     *
     * @param String clave_contrato: Clave de Fideicomiso.
     * @param String fecha_liquidacion: Fecha en que tendrá lugar la
     * liquidación.
     * @param String nombre_archivo: Nombre del lote que contiene el movimiento
     * a cancelar.
     * @param String movimiento: Movimiento a cancelar.
     * @param String usuario_cancela: Usuario que realiza la cancelación.
     * @param String motivo_cancelacion:Motivo de cancelación.
     * @param String status: Status de cancelación.
     * @return boolean: true / false.
     */
    public synchronized boolean habilitaMovimientoCancelado(Movimiento movimiento, String status) {
        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        boolean seGuardo = false;
        String MySql = "";
        try {
            connection = conn.ConectaSQLServer();
            connection.setAutoCommit(false);
            statement = connection.createStatement();

            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " delete  ";
            MySql += " from movimientos_cancelados  ";
            MySql += " where clave_contrato = '" + movimiento.getClave_contrato() + "' ";
            MySql += " and fecha_liquidacion = '" + new clsFecha().CambiaFormatoFecha("dd/MM/yyyy", "yyyy-MM-dd", movimiento.getFecha_liquidacion()) + " 00:00:00.000" + "' ";
            MySql += " and nombre_archivo = '" + movimiento.getNombre_archivo() + "' ";
            MySql += " and cuenta_deposito = '" + movimiento.getCuenta_deposito() + "' ";
            MySql += " and nombre_empleado = '" + movimiento.getNombre_empleado() + "' ";
            MySql += " and apellidoP_empleado = '" + movimiento.getApellidoP_empleado() + "' ";
            MySql += " and apellidoM_empleado = '" + movimiento.getApellidoM_empleado() + "' ";
            MySql += " and clave_empleado = '" + movimiento.getNumero_empleado() + "' ";
            MySql += " and puesto_empleado = '" + movimiento.getPuesto_empleado() + "' ";
            MySql += " and depto_empleado = '" + movimiento.getDepartamento_empleado() + "' ";
            MySql += " and curp = '" + movimiento.getCURP() + "' ";
            MySql += " and fecha_ingreso = '" + new clsFecha().CambiaFormatoFecha("dd/MM/yyyy", "yyyy-MM-dd", movimiento.getFecha_ingreso()) + "' ";
            MySql += " and tipo_movimiento = '" + movimiento.getTipo_movimiento() + "' ";
            MySql += " and clave_banco = '" + movimiento.getClave_banco() + "' ";
            MySql += " and importe_liquidacion = '" + movimiento.getImporte_liquidacion() + "' ";
            MySql += " and tipo_moneda = '" + movimiento.getClave_moneda() + "' ";

            MySql += " and envio_cheque = '" + movimiento.getNombre_receptor_cheque() + "' ";
            MySql += " and destino_envio_cheque = '" + movimiento.getDomicilio_destino_cheque() + "' ";
            MySql += " and tel_envio_cheque = '" + movimiento.getTel_destino_cheque() + "' ";
            MySql += " and correo_envio_cheque = '" + movimiento.getCorreo_destino_cheque() + "' ";

            MySql += " and banco_extranjero = '" + movimiento.getNombre_banco_extranjero() + "' ";
            MySql += " and dom_banco_extranjero = '" + movimiento.getDomicilio_banco_extranjero() + "' ";
            MySql += " and pais_banco_extranjero = '" + movimiento.getPais_banco_extranjero() + "' ";
            MySql += " and ABA_BIC = '" + movimiento.getABA_BIC() + "' ";

            MySql += " and nombre_fidei_banco_ext = '" + movimiento.getNombre_empleado_banco_extranjero() + "' ";
            MySql += " and direccion_fidei_ext = '" + movimiento.getDom_empleado_banco_extranjero() + "' ";
            MySql += " and pais_fidei_ext = '" + movimiento.getPais_empleado_banco_extranjero() + "' ";
            MySql += " and tel_fidei_ext = '" + movimiento.getTel_empleado_banco_extranjero() + "' ";

            MySql += " and motivo = '" + movimiento.getMotivo_cancelacion() + "' ";
            MySql += " and status = '" + status + "' ";
//            System.out.println(MySql);
            // Se ejecuta el encabezado
            statement.executeUpdate(MySql);
            // System.out.println("Transaction commit...");
            connection.commit();
            seGuardo = true;
            statement.close();
            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            System.out.println("AuthorizationModel-habilitaMovimientoCancelado:" + e.getMessage());
            seGuardo = false;
            e.printStackTrace();
            try {
                connection.rollback();
//                System.out.println("Connection rollback...");
                if (connection != null) {
                    conn.Desconecta(connection);
                }
            } catch (Exception e2) {
                System.out.println("AuthorizationModel-habilitaMovimientoCancelado:" + e2.toString());
            }
        }
        return seGuardo;
    }

    /**
     * Método que modifica el motivo de cancelación de un movimiento cancelado
     * parcialmente.
     *
     * @param String movimiento: Movimiento a cancelar.
     * @param motivo_cancelacion: Nuevo motivo de cancelación.
     * @param String usuario_modifica: Usuario que realiza la modificación.
     * @param String status_modificacion: Status del movimiento a modificar.
     * @return boolean: true / false.
     */
//    public synchronized boolean modificarMovimientoCancelado(Movimiento movimiento, String motivo_cancelacion, String usuario_modifica, String status_movimiento) {
//        clsConexion conn = new clsConexion();
//        Connection connection = null;
//        Statement statement = null;
//        boolean seGuardo = false;
//        String MySql = "";
//        try {
//            connection = conn.ConectaSQLServer();
//            connection.setAutoCommit(false);
//            statement = connection.createStatement();
//
//            connection = conn.ConectaSQLServer();
//            statement = connection.createStatement();
//            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
//            MySql = " update movimientos_cancelados ";
//            MySql += " set motivo = '" + motivo_cancelacion + "' ";
//            MySql += " where clave_contrato = '" + movimiento.getClave_contrato() + "' ";
//            MySql += " and fecha_liquidacion = '" + movimiento.getFecha_liquidacion() + " 00:00:00.000" + "' ";
//            MySql += " and nombre_archivo = '" + movimiento.getNombre_archivo() + "' ";
//            MySql += " and nombre_empleado = '" + movimiento.getNombre_empleado() + "' ";
//            MySql += " and apellidoP_empleado = '" + movimiento.getApellidoP_empleado() + "' ";
//            MySql += " and apellidoM_empleado = '" + movimiento.getApellidoM_empleado() + "' ";
//            MySql += " and tipo_movimiento = '" + movimiento.getTipo_movimiento() + "' ";
//            MySql += " and cuenta_deposito = '" + movimiento.getCuenta_deposito() + "' ";
//            MySql += " and clave_banco = '" + movimiento.getClave_banco() + "' ";
//            MySql += " and importe_liquidacion = '" + movimiento.getImporte_liquidacion() + "' ";
//            MySql += " and tipo_moneda = '" + movimiento.getClave_moneda() + "' ";
//            MySql += " and status = '" + status_movimiento + "' ";
////            System.out.println(MySql);
//            // Se ejecuta el encabezado
//            statement.executeUpdate(MySql);
//            // System.out.println("Transaction commit...");
//            connection.commit();
//            seGuardo = true;
//            statement.close();
//            if (connection != null) {
//                conn.Desconecta(connection);
//            }
//        } catch (Exception e) {
//            System.out.println("AuthorizationModel-modificarMovimientoCancelado:" + e.toString());
//            seGuardo = false;
//            e.printStackTrace();
//            try {
//                connection.rollback();
////                System.out.println("Connection rollback...");
//                if (connection != null) {
//                    conn.Desconecta(connection);
//                }
//            } catch (Exception e2) {
//                System.out.println("AuthorizationModel-modificarMovimientoCancelado:" + e2.toString());
//            }
//        }
//        return seGuardo;
//    }
    /**
     * Método que proporciona todos los movimientos cancelados parcialmente de
     * un lote.
     *
     * @param clave_contrato : Clave de fideicomiso.
     * @param fecha_liquidacion : Fecha en que tendrá lugar la liquidación.
     * @param nombre_archivo : Nombre del LayOut.
     * @param status: Status de lote.
     * @return Vector: Objetos de tipo Movimiento, si ocurre un error regresa
     * null.
     */
    public static Vector getMovimientosCancelados(String clave_contrato, String fecha_liquidacion, String nombre_archivo, String status_lote, String status_cancelados) {
        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        Vector movimientos = new Vector();
        Movimiento movimiento = null;
        String nombre_empleado = "";
        String apellidoP_empleado = "";
        String apellidoM_empleado = "";
        String MySql = "";
        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select cancelados.cuenta_deposito, cancelados.apellidoP_empleado, cancelados.apellidoM_empleado, ";
            MySql += " cancelados.nombre_empleado, cancelados.tipo_movimiento, cancelados.clave_banco, ";
            MySql += " cancelados.importe_liquidacion, cancelados.tipo_moneda, cancelados.motivo ";
            MySql += " from movimientos_cancelados cancelados, movimientos_h movimientos";
            MySql += " where cancelados.clave_contrato = movimientos.clave_contrato";
            MySql += " and cancelados.fecha_liquidacion = movimientos.fecha_liquidacion";
            MySql += " and cancelados.nombre_archivo = movimientos.nombre_archivo";
            MySql += " and movimientos.clave_contrato = '" + clave_contrato + "' ";
            MySql += " and movimientos.fecha_liquidacion = '" + (new clsFecha().CambiaFormatoFecha("dd/MM/yyyy", "yyyy-MM-dd", fecha_liquidacion)) + " 00:00:00.000'";
            MySql += " and movimientos.nombre_archivo = '" + nombre_archivo + "'";
            MySql += " and movimientos.status = '" + status_lote + "'";
            MySql += " and cancelados.status = '" + status_cancelados + "'";
//            System.out.println("getMovimientosCancelados:" + MySql);
            ResultSet rstSQLServer = statement.executeQuery(MySql);
            while (rstSQLServer.next()) {
                movimiento = new Movimiento();
                //Cuenta depósito.
                movimiento.setCuenta_deposito(rstSQLServer.getString(1));
                //Apellido Paterno del fideicomisario.
                apellidoP_empleado = rstSQLServer.getString(2);
                //Apellido Materno del fideicomisario.
                apellidoM_empleado = rstSQLServer.getString(3);
                // Nombre(s) del fideicomisario.
                nombre_empleado = rstSQLServer.getString(4);
                movimiento.setApellidoP_empleado(apellidoP_empleado);
                movimiento.setApellidoM_empleado(apellidoM_empleado);
                movimiento.setNombre_empleado(nombre_empleado);
                //Asignamos el nombre completo del fideicomisario.
                movimiento.setNombre_fideicomisario(apellidoP_empleado + " " + apellidoM_empleado + " " + nombre_empleado);
                //Asignamos el tipo de movimiento.
                movimiento.setTipo_movimiento(rstSQLServer.getString(5));
                //Asignamos la clave de banco.
                movimiento.setClave_banco(rstSQLServer.getString(6));
                //Asignamos el importe de liquidación.
                movimiento.setImporte_liquidacion(rstSQLServer.getString(7));
                //Asignamos la clave de moneda.
                movimiento.setClave_moneda(rstSQLServer.getString(8));
                //Almacenamos el movimiento completo.
                movimiento.setMotivo_cancelacion(rstSQLServer.getString(9));
                //Almacenamos el movimiento completo.
                movimientos.add(movimiento);
            }
            rstSQLServer.close();
            statement.close();
            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            movimientos = null;
            if (connection != null) {
                conn.Desconecta(connection);
            }
            System.out.println("AuthorizationModel-getMovimientosCancelados:" + e.getMessage());
        }
        return movimientos;
    }

    /**
     * Método que elimina del lote los movimientos cancelados parciamente.
     *
     * @param movimientos : Movimientos de un LayOut en particular.
     * @param clave_contrato : clave de fideicomiso del lote a modificar.
     * @param fecha_liquidacion : Fecha de liquidación del lote a modificar.
     * @param nombre_archivo : Nombre del LayOut cargado al que pertenecen los
     * movimientos e eliminar.
     * @param usuario_cancela : Usuario que realiza la cancelación parcial.
     * @param status : Status del proceso.
     * @return boolean valido: Regresa true si se eliminaron los registros
     * especificados de la base de datos, false en otro caso.
     */
    public synchronized boolean eliminaCancelacionParcial(Vector movimientos, String clave_contrato, String fecha_liquidacion, String nombre_archivo, String usuario_cancela, String status_cancela) {
        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        Movimiento movimiento = null;
        boolean seGuardo = false;
        String MySql = "";
        try {
            connection = conn.ConectaSQLServer();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            for (int i = 0; i < movimientos.size(); i++) {
                System.out.println("eliminando movimiento= " + i);
                movimiento = (Movimiento) movimientos.get(i);
                MySql = " delete ";
                MySql += " from movimientos ";
                MySql += " where clave_contrato = '" + clave_contrato + "' ";
                MySql += " and fecha_liquidacion = '" + fecha_liquidacion + " 00:00:00.000" + "' ";
                MySql += " and nombre_archivo = '" + nombre_archivo + "' ";
                MySql += " and apellidoP_empleado = '" + movimiento.getApellidoP_empleado() + "' ";
                MySql += " and apellidoM_empleado = '" + movimiento.getApellidoM_empleado() + "' ";
                MySql += " and nombre_empleado = '" + movimiento.getNombre_empleado() + "' ";
                MySql += " and cuenta_deposito = '" + movimiento.getCuenta_deposito() + "' ";
                MySql += " and tipo_movimiento = '" + movimiento.getTipo_movimiento() + "' ";
                MySql += " and clave_banco = '" + movimiento.getClave_banco() + "' ";
                MySql += " and importe_liquidacion = '" + movimiento.getImporte_liquidacion() + "' ";
                MySql += " and tipo_moneda = '" + movimiento.getClave_moneda() + "' ";
//                System.out.println("eliminaCancelacionParcial:" + MySql);
                // Se ejecuta el encabezado
                System.out.println("MySQL_Elimina_movimientos:" + MySql);
                statement.executeUpdate(MySql);
            }
            MySql = " update movimientos_cancelados ";
            MySql += " set status='" + status_cancela + "', ";
            MySql += " usuario_cancela='" + usuario_cancela + "', ";
            MySql += " fecha_usuario_cancela = getDate() ";
            MySql += " where clave_contrato = '" + clave_contrato + "' ";
            MySql += " and fecha_liquidacion = '" + fecha_liquidacion + " 00:00:00.000" + "' ";
            MySql += " and nombre_archivo = '" + nombre_archivo + "' ";
            MySql += " and status = 'A' ";
            System.out.println("MySQL_update_movimientos_c:" + MySql);
            // Se ejecuta el encabezado
            statement.executeUpdate(MySql);
            connection.commit();
            seGuardo = true;
//            System.out.println("Transaction commit...");
            statement.close();
            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            e.printStackTrace();
            seGuardo = false;
            System.out.println("AuthorizationModel-eliminaCancelacionParcial:" + e.toString());
            if (connection != null) {
                conn.Desconecta(connection);
            }
            try {
                statement.close();
            } catch (Exception ex) {
            }
        }
        return seGuardo;
    }

    public static String getEmailBody(Vector movimientos) {

        String color = "#FFFFFF";
        String lstrBody = "";
        String lstrMensaje = "";
        Movimiento movimiento = null;
        try {
            lstrMensaje = "Por este medio, nos permitimos informarle que los siguientes movimientos han sido cancelados ";
            lstrMensaje += "por los motivos que se especifican y la liquidación adjunta refleja la cancelación realizada.";

            lstrBody += "<html> <style type='text/css'><!--"
                    + " .Estilo1 {font-family: Arial;} "
                    + " .tablaTitulo{font-family:Arial;font-size:14px;background-color:#00CCFF;color:#00CCFF;}"
                    + " .tablaMenu{background-color:#00CCFF;color:#00CCFF;}"
                    + " .letraTabla{font-size:15px;font-family:Arial;}"
                    + " --></style>";

            lstrBody += "<head> ";
//            lstrBody += "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\"> ";
            lstrBody += "<title>Documento sin t&iacute;tulo</title> ";
            lstrBody += "</head> ";

            lstrBody += "<body> ";
            lstrBody += "<table width=\"80%\"  border=\"0\"> ";
            lstrBody += "<font size=2 face=\"Arial\"> ";
            lstrBody += "<tr> ";
            lstrBody += "<td f>&nbsp;</td> ";
            lstrBody += "<td>&nbsp;</td> ";
            lstrBody += "</tr> ";
            lstrBody += "<tr> ";
            lstrBody += "<td colspan=\"2\" f><font size=2 face=\"Arial\">" + lstrMensaje + "</font></td> ";
            lstrBody += "</tr> ";
            if (!movimientos.isEmpty()) {
                lstrBody += "<tr> ";
                lstrBody += "<td colspan=\"2\"><p>&nbsp;</p> ";
                lstrBody += "<div align=\"center\"> ";
                lstrBody += "<table  table width=\"80%\" class='letraTabla'  align='center' border='1' bordercolordark='#B5D6FE' "
                        + " bordercolorlight='#528CEF'> ";
                lstrBody += " <tr style='color:#000000'> ";
                lstrBody += " <td bgcolor='#E3F2FE' nowrap ><center><strong>Fideicomisario</strong></center></td>";
                lstrBody += " <td bgcolor='#E3F2FE' nowrap ><center><strong>Movimiento</strong></center></td>";
                lstrBody += " <td bgcolor='#E3F2FE' nowrap ><center><strong>Cuenta Depósito</strong></center></td>";
                lstrBody += " <td bgcolor='#E3F2FE' nowrap ><center><strong>Banco</strong></center></td>";
                lstrBody += " <td bgcolor='#E3F2FE' nowrap ><center><strong>Importe Liquidación</strong></center></td>";
                lstrBody += " <td bgcolor='#E3F2FE' nowrap ><center><strong>Moneda</strong></center></td>";
                lstrBody += " <td bgcolor='#E3F2FE' nowrap ><center><strong>Motivo</strong></center></td>";
                lstrBody += " </tr> ";
                for (int i = 0; i < movimientos.size(); i++) {
                    movimiento = (Movimiento) movimientos.get(i);
                    lstrBody += " <tr style='color:#000000'> ";
                    if (i % 2 == 0) {
                        color = "#FFFFFF";
                    } else {
                        color = "#E3F2FE";
                    }
                    lstrBody += " <td bgcolor='" + color + "' nowrap ><center>" + movimiento.getNombre_fideicomisario() + "</center></td>";
                    lstrBody += " <td bgcolor='" + color + "' nowrap ><center>" + movimiento.getTipo_movimiento() + "</center></td>";
                    lstrBody += " <td bgcolor='" + color + "' nowrap ><center>" + movimiento.getCuenta_deposito() + "</center></td>";
                    lstrBody += " <td bgcolor='" + color + "' nowrap ><center>" + movimiento.getClave_banco() + "</center></td>";
                    lstrBody += " <td bgcolor='" + color + "' nowrap ><center>" + "$" + movimiento.getImporte_liquidacion() + "</center></td>";
                    lstrBody += " <td bgcolor='" + color + "' nowrap ><center>" + movimiento.getClave_moneda() + "</center></td>";
                    lstrBody += " <td bgcolor='" + color + "' nowrap ><left>" + movimiento.getMotivo_cancelacion() + "</center></td>";
                    lstrBody += " </tr> ";
                }
                lstrBody += "</table> ";
                lstrBody += "<p>&nbsp;</p> ";
                lstrBody += "</div> ";
                lstrBody += "</td> ";
                lstrBody += "</tr> ";
            }
            lstrMensaje = "Estos movimientos sólo podrán ser rehabilitados y operados por el fideicomiso a través de ";
            lstrMensaje += "un nuevo LAY-OUT generado para el efecto, o en su caso, a través de su alta en nuestra página: ";
            lstrMensaje += " <a href=\"http://www.fideicomisogds.mx\"> [www.fideicomisogds.mx] </a>";
            lstrBody += "<tr> ";
            lstrBody += "<td colspan=\"2\" f><font size=2 face=\"Arial\">" + lstrMensaje + "</font></td> ";
            lstrBody += "</tr> ";
            lstrBody += "<tr> ";
            lstrBody += "<td colspan=\"2\">&nbsp;</td> ";
            lstrBody += "</tr> ";
            lstrBody += "</table> ";
            lstrBody += " ";
            lstrBody += "</body> ";
            lstrBody += "</html> ";

//            System.out.println("cuerpoCorreo:" + lstrBody);
        } catch (Exception e) {
            lstrBody = "";
            System.out.println("AuthorizationModel-getEmailBody:" + e.getMessage());
        }
        return lstrBody;
    }

    /**
     * Método que proporciona el conjunto de fideicomitentes que cuentan con
     * lotes pendientes por operar con el status que se le pasa como parámetro.
     *
     * @param String status: Status
     * @return Vector clientes: Clientes con el status que se especifica.
     */
    public static Vector getFideicomisoAprobar(String status) {
        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        Vector clientes = new Vector();
        String cliente = "";
        String MySql = "";
        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select distinct(contratos.clave_contrato)";
            MySql += " from contratos contratos, movimientos_h movimientos ";
            MySql += " where contratos.clave_contrato= movimientos.clave_contrato ";
            MySql += " and contratos.status = 'A' ";
            MySql += " and movimientos.status ='" + status + "' ";
            MySql += " and movimientos.clave_contrato not in  ";
            MySql += " ( select distinct(h.clave_contrato) ";
            MySql += " from movimientos_h h, movimientos l ";
            MySql += " where h.clave_contrato = l.clave_contrato ";
            MySql += " and h.fecha_liquidacion = l.fecha_liquidacion ";
            MySql += " and h.nombre_archivo = l.nombre_archivo ";
            MySql += " and l.tipo_movimiento = 5 ";
            MySql += " and h.status='" + status + "' )";
            MySql += " order by contratos.clave_contrato asc ";
//            System.out.println("getFideicomisoAprobar:" + MySql);
            clientes.add("  -----Seleccione-----  ");
            ResultSet rstSQLServer = statement.executeQuery(MySql);
            while (rstSQLServer.next()) {
                cliente = rstSQLServer.getString(1);
                clientes.add(cliente);
            }
            rstSQLServer.close();
            statement.close();
            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            clientes = null;
            System.out.println("AuthorizationModel-getFideicomisoAprobar:" + e.getMessage());
            if (connection != null) {
                conn.Desconecta(connection);
            }
        }
        return clientes;
    }

    /**
     * Método que gestiona la autorización de un lote que no cuenta con
     * movimientos cancelados previamente.
     *
     * @param String cliente: Nombre del fideicomitente.
     * @param String clave_contrato: Clave de fideicomiso.
     * @param String fecha_liquidacion: Fecha en que tendrá lugar la
     * liquidación..
     * @param String nombre_archivo: Nombre del Lote a autorizar.
     * @param String usuario: Nombre del usuario que realiza la autorización.
     * @param String correoDestino: Destinatario de la notificación.
     * @param String urlArchivo: Ruta del reporte de liquidación en moneda
     * nacional a enviar por correo en caso de contar con movimientos a bancos
     * extranjeros.
     * @param String status_global: Status global que tendrá el lote después de
     * la autorización.
     * @param String status_autorizacion: Status de la autorización.
     * @param String status_proceso: Status actual del lote.
     * @param int verifica: Identificador de lote.
     * @return String: Mensaje descriptivo del resultado del proceso.
     */
    public synchronized String autorizaMovimientos(String cliente, String clave_contrato, String fecha_liquidacion, String nombre_archivo,
            String usuario, String correoDestino, String urlArchivo, String status_global, String status_autoriza, String status_proceso,
            String persona_genera, String correoOrigen, int verifica, String realPath) {
        String asuntoCorreo = "";
        String cuerpoCorreo = "";
        String autoriza = "";
        Double importe_total = 0d;
        String saldo_actual = "";
        String reportes = "";
        String reporteMXP = "";
        boolean movs_in = false;

        ModelUpdate modelo = new ModelUpdate();
        ModelUpdate.setNombre_pdf("");
        Vector clientes = null;
        boolean procesa = false;
        ResumenMovimientos resumenMovs = null;
        Vector importeTotal = null;
        //Verificamos si las transacciones ya fueron generadas por otro usuario.
        autoriza = ModeloLiquidation.verificaActualizacion(clave_contrato, fecha_liquidacion, nombre_archivo, status_proceso);
        if (autoriza.equals("")) {
            //Verifica si tiene suficiente saldo para realizar la autorización del lote.
            /**
             * //parametros necesarios para identificar saldo y lote de
             * movimientos actual:
             * clave_contrato;fecha_liquidacion;nombre_archivo;status_proceso
             */
            //Obtenemos el formato de fecha que se utiliza en la BD: yyyy-MM-dd
            String fecha_liquidacion_BD = new clsFecha().CambiaFormatoFecha("dd/MM/yyyy", "yyyy-MM-dd", fecha_liquidacion);
            //Generamos el reporte de liquidación con la eliminación reflejada.            
            importe_total = AuthorizationModel.getImporteTotal(clave_contrato, fecha_liquidacion_BD, nombre_archivo);
            if (importe_total >= 0) {
                //Se obtiene el saldo actual del fideicomiso en la BD
                saldo_actual = AuthorizationModel.getSaldo(clave_contrato);
                if (saldo_actual == "") {
                    return "Error al obtener el saldo del fideicomiso.";
                }
                Double nuevo_saldo = compruebaSaldoSuficiente(saldo_actual, importe_total);
                if (nuevo_saldo == null) {
                    return "Error al obtener el nuevo saldo. ";
                }
                if (nuevo_saldo < 0) {
                    DecimalFormat d = new DecimalFormat("$ #,##0.00");
                    return "No se puede autorizar el lote, saldo= " + d.format(Double.parseDouble(saldo_actual)) + " insuficiente para autorizar liquidación por " + d.format(importe_total);
                }
                //Generamos el reporte de liquidación en moneda nacional.
                importeTotal = AuthorizationModel.getTotalLiquidacion(cliente, clave_contrato, fecha_liquidacion_BD, nombre_archivo, status_proceso);
                //Verificamos si se cuenta con movimientos a bancos extranjeros.
                clientes = ModeloLiquidation.getNombreFideicomitentes(clave_contrato, fecha_liquidacion_BD, "5", nombre_archivo, status_proceso);
                if (clientes != null) {
                    if (importeTotal != null && importeTotal.size() > 0) {
                        //Verificamos si se cuenta con movimientos a bancos extranjeros.
                        if (clientes.size() > 0 && clientes.size() > 1) {
                            movs_in = true;
                        }

                        ///////////////////NUEVA AXTUALIZACION DE SALDOSSS                
                        resumenMovs = AuthorizationModel.getResumenMovimientos(importeTotal, clave_contrato, fecha_liquidacion);
                        if (resumenMovs != null) {
                            //Generamos el reporte de liquidación actualizado.
                            autoriza = this.generaReporteLiquidacion_MXP(clave_contrato, fecha_liquidacion, nombre_archivo, resumenMovs, verifica, status_proceso, urlArchivo, realPath);
                            if (autoriza.equals("")) {
                                reporteMXP = ModeloLiquidation.getNombreResumenLiquidacion(clave_contrato, fecha_liquidacion, verifica);
                                reportes = AuthorizationModel.getReportesGenerados(clave_contrato, fecha_liquidacion_BD, nombre_archivo, "P", verifica, movs_in);
                                if (reportes != null && !reporteMXP.equals("")) {
                                    reportes = reportes + ";" + reporteMXP;

                                    asuntoCorreo = "LIQUIDACIÓN " + clave_contrato + " " + fecha_liquidacion;
                                    cuerpoCorreo = "REPORTES CORRESPONDIENTES A LA LIQUIDACIÓN";
                                    //Se envian todos los reportes .pdf generados durante el proceso a los clientes en BD almacenados para el fideicomiso
                                    procesa = EnvioMail.enviaCorreo(correoOrigen, correoDestino, asuntoCorreo, cuerpoCorreo, urlArchivo, reportes);
//                    procesa = EnvioMail.enviaCorreo(correoOrigen, "luis-valerio@gp.org.mx","sin cancelaciones CLIENTE " + asuntoCorreo, cuerpoCorreo, urlArchivo, reportes);
                                    procesa = true;
                                    if (procesa) {
                                        correoDestino = ModeloLiquidation.obtenCorreos("'OPERACION','SISTEMAS'");
                                        if (movs_in) {
                                            //Se envían reportes pdf actualizados al departamento de operacion
                                            procesa = EnvioMail.enviaCorreo(correoOrigen, correoDestino, asuntoCorreo, cuerpoCorreo, urlArchivo, reporteMXP);
//                            procesa = EnvioMail.enviaCorreo(correoOrigen, "luis-valerio@gp.org.mx","sin cancelaciones OPERACION " + asuntoCorreo, cuerpoCorreo, urlArchivo, reporteMXP);
                                            procesa = true;
                                            if (!procesa) {
                                                autoriza = " Error enviando reporte de liquidación a facturación ";
                                            }
                                        }
                                        autoriza = ModeloLiquidation.generaExcelLote(clave_contrato, fecha_liquidacion, nombre_archivo, urlArchivo, status_proceso, verifica);
                                        if (autoriza.equals("")) {
                                            //Actualizamos el nuevo estado del lote en la BD y el registro de cargo de importe de liquidacion, así como el nuevo_Saldo
                                            String importeTotalFormateado = new DecimalFormat("0.00").format(importe_total);
                                            if (modelo.date_en_habil()) {// se verifica si el día esta dentro del rango de dias habiles de mes
                                                procesa = modelo.actualizaStatusLote(clave_contrato, fecha_liquidacion_BD, nombre_archivo, usuario, status_global, status_autoriza, status_proceso, importeTotalFormateado, nuevo_saldo);
                                            } else {// el dia y hora son posteriroes a la fecha de corte
                                                //se prosigue a asignar la hora correspondiente del 1er día habil del mes siguiente
                                                procesa = modelo.actualizaStatusLote_DiaCorte(clave_contrato, fecha_liquidacion_BD, nombre_archivo, usuario, status_global, status_autoriza, status_proceso, importeTotalFormateado, nuevo_saldo);
                                            }
                                            if (procesa) {
                                                autoriza = " Proceso realizado correctamente ";
                                            } else {
                                                autoriza = "Error actualizando status de la transacción";
                                            }
                                        }
                                    } else {
                                        autoriza = "Error enviando reportes de liquidación al fideicomitente";
                                    }
                                } else {
                                    autoriza = "Error obteniendo los reportes a enviar por correo electrónico";
                                }
                                ///////////////////NUEVA AXTUALIZACION DE SALDOSSS
                            } else {
                                autoriza = "Error generando el reporte de liquidación.";
                            }
                        } else {
                            autoriza = "Error obteniendo el resumen de liquidación para el fideicomiso";
                        }
                    } else {
                        autoriza = "Error consultanto importe total de cada movimiento";
                    }
                } else {
                    autoriza = "Error verificando movimientos a bancos extranjeros";
                }
            } else {
                autoriza = "Error obteniendo el importe de liquidación del lote de movimientos.";
            }
        }
        return autoriza;
    }

    /**
     * Método que regresa el nombre de los reportes de liquidación generados.
     *
     * @param clave_contrato : Clave de fideicomiso
     * @param fecha_liquidacion : Fecha en que tendrá lugar la liquidación
     * @param nombre_archivo : Nombre del lote.
     * @param status : Status del lote.
     * @param movs_in : Identificador de movimientos a bancos extranjeros.
     * @return String : correo electrónico. Si ocurre algún error regresa null.
     */
    //Aqui ya se utilizo la fecha de BD y pasada por argumentos
    public static String getReportesGenerados(String clave_contrato, String fecha_liquidacion, String nombre_archivo, String status, int verifica, boolean movs_in) {
        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        ResultSet rstSQLServer = null;
        String reportes_mxp = null;
        String reportes_in = "";
        String allReports = "";
        String MySql = "";
        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            if (movs_in) {
                MySql = " select reportes_liquidacion_mxp , reportes_liquidacion_in ";
                MySql += " from movimientos_h ";
                MySql += " where clave_contrato = '" + clave_contrato.trim() + "' ";
                MySql += " and fecha_liquidacion = '" + fecha_liquidacion + " 00:00:00.000' ";
                MySql += " and nombre_archivo = '" + nombre_archivo.trim() + "'  ";
                MySql += " and status = '" + status + "'";
//                System.out.println(MySql);
                rstSQLServer = statement.executeQuery(MySql);
                if (rstSQLServer.next()) {
                    reportes_mxp = rstSQLServer.getString(1).toString().trim();
                    reportes_in = rstSQLServer.getString(2).toString().trim();
                    if (!reportes_mxp.equals("")) {
                        allReports = reportes_mxp;
                    }
                    if (!reportes_in.equals("")) {
                        if (allReports.equals("")) {
                            allReports += reportes_in;
                        } else {
                            allReports += ";" + reportes_in;
                        }
                    }
                    if (allReports.equals("")) {
                        allReports = null;
                    }
                }
            } else {
                MySql = " select reportes_liquidacion_mxp ";
                MySql += " from movimientos_h ";
                MySql += " where clave_contrato = '" + clave_contrato.trim() + "' ";
                MySql += " and fecha_liquidacion = '" + fecha_liquidacion + " 00:00:00.000' ";
                MySql += " and nombre_archivo = '" + nombre_archivo.trim() + "'  ";
                MySql += " and status = '" + status + "'";
//                System.out.println(MySql);
                rstSQLServer = statement.executeQuery(MySql);
                if (rstSQLServer.next()) {
                    reportes_mxp = rstSQLServer.getString(1).toString().trim();
                    if (reportes_mxp.equals("")) {
                        allReports = null;
                    } else {
                        allReports = reportes_mxp;
                    }
                }
            }
            rstSQLServer.close();
            statement.close();
            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            allReports = null;
            System.out.println("AuthorizationModel-getCorreoUsuario:" + e.toString());
            if (connection != null) {
                conn.Desconecta(connection);
            }
            try {
                rstSQLServer.close();
                statement.close();
            } catch (Exception ex) {
            }
        }
        return allReports;

    }

    /**
     * Método que regresa el porcentaje de la administración fiduciaria según el
     * tipo de base. Si base = RIESGOS entonces HF = RL * XcHon Si base =
     * PATRIMONIO entonces HF = SP * XcHon
     *
     * @param importe_l : Importe de liquidación en MXP (RL)
     * @param sp_c : Suficiencia Patronal (SP)
     * @param honSinIva : Porcentaje honorario sin IVA
     * @return : honorarios fiduciarios sin IVA
     */
    public static String getHonorariosFiduciarios(String importe_l, String sp_c, String honSinIva) {
        int idx = -1;
        Double tmp = 0.0D;
        String base = "";
        String hf_c = "";
        BigDecimal rl = null;
        BigDecimal xHon = null;
        BigDecimal sp = null;
        BigDecimal hf = null;
        try {
            idx = honSinIva.indexOf(",");
            if (idx > 0) {
                base = honSinIva.substring(0, idx).toString().trim();
                honSinIva = honSinIva.substring(idx + 1, honSinIva.length()).toString().trim();
                xHon = new BigDecimal(Double.parseDouble(honSinIva) / 100);
                if (base.equals("RIESGOS")) {
                    rl = new BigDecimal(importe_l);
                    hf = rl.multiply(xHon);
                } else {
                    if (base.equals("PATRIMONIO")) {
                        sp = new BigDecimal(sp_c);
                        hf = sp.multiply(xHon);
                    } else {
                        hf = BigDecimal.ZERO;
                        hf_c = "0.00";
                    }
                }
                tmp = hf.doubleValue();
                if (tmp > 0.0) {
                    tmp = Math.rint(tmp * 100.0) / 100.0;
                    hf = new BigDecimal(tmp);
                }
                hf_c = hf.setScale(2, BigDecimal.ROUND_HALF_UP) + "";
            } else {
                hf_c = "0.00";
            }
        } catch (Exception e) {
            hf_c = "";
            System.out.println("Error calculando Honorarios Fiducuarios " + e.getMessage());
        }
        return hf_c;
    }

    /**
     * Método que calcula la suficiencia patronal dependiendo del tipo de base:
     * Si base=RIESGOS entonces SP = RL *( 1 + XcHon *(1+XcIVA)) Si base =
     * PATRIMONIO entonces SP = RL / ( 1- XcHon *(1+XcIVA))
     *
     * @param importe_liquidacion : Importe de Liquidacion en MXP (RL)
     * @param honSinIva : Porcentaje de honorario sin Iva.
     * @param IVA : Porcentaje del IVA
     * @return cadena que representa la suficiencia patronal.
     */
    public static String getSuficienciaPatronal(String importe_liquidacion, String honSinIva, String IVA) {
        BigDecimal cUno = null;
        BigDecimal rl = null;
        BigDecimal xHon = null;
        BigDecimal xIVA = null;
        BigDecimal sp = null;

        String base = "";
        String sp_c = "";
        int idx = -1;
        Double tmp = 0.0D;
        try {
            idx = honSinIva.indexOf(",");
            if (idx > 0) {
                base = honSinIva.substring(0, idx).toString().trim();
                honSinIva = honSinIva.substring(idx + 1, honSinIva.length()).toString().trim();

                rl = new BigDecimal(importe_liquidacion);
                xHon = new BigDecimal(Double.parseDouble(honSinIva) / 100);
                xIVA = new BigDecimal((Double.parseDouble(IVA) / 100) + 1.0);
                cUno = BigDecimal.ONE;

                if (base.equals("RIESGOS")) {
                    sp = rl.multiply(cUno.add(xHon.multiply(xIVA)));
                    tmp = sp.doubleValue();
                    if (tmp > 0.0) {
                        tmp = Math.rint(tmp * 100.0) / 100.0;
                        sp = new BigDecimal(tmp);
                    }
                } else {
                    if (base.equals("PATRIMONIO")) {
                        sp = rl.divide(cUno.subtract(xHon.multiply(xIVA)), 2, RoundingMode.HALF_UP);
                    } else {
                        sp = BigDecimal.ZERO;
                    }
                }
                sp_c = sp.setScale(2, BigDecimal.ROUND_HALF_UP) + "";
            } else {
                sp_c = "0.00";
            }
        } catch (Exception e) {
            sp_c = "";
            System.out.println("Error calculando Suficiencia Patronal " + e.getMessage());
        }
        return sp_c;
    }

    /**
     *
     * @param importe_aportacion
     * @param honSinIva
     * @param IVA
     * @return
     */
    public static double calculaDispercion(String importe_aportacion, String honSinIva, String IVA) {
        String base = "";
        int idx = -1;
        double totoal_dispercion = 0.0D;
        BigDecimal aportaBig = null;

        try {
            double aportacion = Double.parseDouble(importe_aportacion);
            double iva = Double.parseDouble(IVA);
            idx = honSinIva.indexOf(",");
            if (idx > 0) {
                base = honSinIva.substring(0, idx).toString().trim();
                honSinIva = honSinIva.substring(idx + 1, honSinIva.length()).toString().trim();
                double honorario = Double.parseDouble(honSinIva) / 100;

                if (base.equals("RIESGOS")) {
                    totoal_dispercion = (aportacion) / (1 + (honorario * (1 + iva)));
                } else {
                    if (base.equals("PATRIMONIO")) {
                        totoal_dispercion = aportacion * (1 - (honorario * (1 + iva)));
                    } else {
                        return 0.0D;
                    }
                }
                aportaBig = new BigDecimal(totoal_dispercion);
                aportaBig = aportaBig.setScale(4, RoundingMode.HALF_UP);
            } else {
                return 0.0;
            }
        } catch (Exception e) {
            aportaBig = new BigDecimal(BigInteger.ZERO);
            System.out.println("Error calculando Suficiencia Patronal " + e.getMessage());
        }
        return aportaBig.doubleValue();
    }

    public static double calculaHonorariosDispercion(String importe_aportacion, double importe_dispercion, String honSinIva, String IVA) {
        String base = "";
        int idx = -1;
        double honorarios = 0.0D;
        BigDecimal honoBig = null;

        try {
            double aportacion = Double.parseDouble(importe_aportacion);
            double iva = Double.parseDouble(IVA);
            idx = honSinIva.indexOf(",");
            if (idx > 0) {
                base = honSinIva.substring(0, idx).toString().trim();
                honSinIva = honSinIva.substring(idx + 1, honSinIva.length()).toString().trim();
                double honorario = Double.parseDouble(honSinIva) / 100;

                if (base.equals("RIESGOS")) {
                    honorarios = importe_dispercion * honorario;
                } else {
                    if (base.equals("PATRIMONIO")) {
                        honorarios = aportacion * honorario;
                    } else {
                        return 0.0D;
                    }
                }
                honoBig = new BigDecimal(honorarios);
                honoBig = honoBig.setScale(4, RoundingMode.HALF_UP);
            } else {
                return 0.0;
            }
        } catch (Exception e) {
            honoBig = new BigDecimal(BigInteger.ZERO);
            System.out.println("Error calculando Suficiencia Patronal " + e.getMessage());
        }
        return honoBig.doubleValue();
    }

}
