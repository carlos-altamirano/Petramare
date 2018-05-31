package mx.garante.liquidacionriesgoslaborales.Modelos;

import net.sf.jasperreports.engine.*;
import java.math.BigDecimal;
import mx.garante.liquidacionriesgoslaborales.Common.clsConexion;
import mx.garante.liquidacionriesgoslaborales.Common.clsFecha;
import java.util.HashMap;
import java.util.Vector;
import java.util.Map;
import java.sql.*;
import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ModelUpdate {

    //Variable que almacena la fecha de liquidación de un cliente con mov. tipo 5.
    public static String nombre_fideicomitente = null;
    public static int total_movs_tipo1 = 0;
    public static int total_movs_tipo2 = 0;
    public static int total_movs_tipo3 = 0;
    public static int total_movs_tipo4 = 0;
    public static int total_movs_tipo5 = 0;
    public static String nombre_pdf = "";

    public static String getNombre_pdf() {
        return nombre_pdf;
    }

    public static void setNombre_pdf(String nombre_pdf) {
        ModelUpdate.nombre_pdf = nombre_pdf;
    }

    public static String getNombre_fideicomitente() {
        return nombre_fideicomitente;
    }

    public static void setNombre_fideicomitente(String nombre_fideicomitente) {
        ModelUpdate.nombre_fideicomitente = nombre_fideicomitente;
    }

    public static String getImporteMonedaExtranjera(String cliente, String clave_contrato, String fecha_liquidacion, String nombre_archivo, String nombre_fidei, String status_lote) {
        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String importe = "";
        String importe_mxp = "";
        String MySql = "";
        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select l.importe_liquidacion , l.tipo_moneda, l.importe_liquidacion_mxp";
            MySql += " from movimientos_h h , movimientos l , contratos contratos ";
            MySql += " where h.clave_contrato = l.clave_contrato ";
            MySql += " and l.clave_contrato = contratos.clave_contrato ";
            MySql += " and h.fecha_liquidacion = l.fecha_liquidacion ";
            MySql += " and h.nombre_archivo=l.nombre_archivo ";
            MySql += " and contratos.clave_contrato = '" + clave_contrato + "'";
            MySql += " and l.fecha_liquidacion = '" + (new clsFecha().CambiaFormatoFecha("dd/MM/yyyy", "yyyy-MM-dd", fecha_liquidacion)) + " 00:00:00.000' ";
            MySql += " and contratos.nombre_cliente = '" + cliente + "'";
            MySql += " and h.nombre_archivo = '" + nombre_archivo + "'";
            MySql += " and l.nombre_fidei_banco_ext = '" + nombre_fidei + "'";
            MySql += " and l.tipo_movimiento = 5 ";
            MySql += " and contratos.status = 'A' ";
            MySql += " and h.status = '" + status_lote + "'  ";
//            System.out.println("getImporteMonedaExtranjera:" + MySql);
            ResultSet rstSQLServer = statement.executeQuery(MySql);
            if (rstSQLServer.next()) {
                importe = rstSQLServer.getString(1).toString().trim(); // Importe de Liquidación
                importe = new DecimalFormat("#,##0.00").format(Double.parseDouble(importe)); //Formato Importe de Liquidación
                importe_mxp = rstSQLServer.getString(3).toString().trim(); // Importe de Liquidación MXP
                importe_mxp = new DecimalFormat("#,##0.00").format(Double.parseDouble(importe_mxp)); //Formato Importe MXP
                importe = importe + "%" + rstSQLServer.getString(2).toString().trim(); //Tipo de moneda
                importe = importe + "%" + importe_mxp;//Concatenando importe MXP
            }
            rstSQLServer.close();
            statement.close();
            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            importe = "Error verificando importe de liquidación";
            if (connection != null) {
                conn.Desconecta(connection);
            }
            System.out.println("ModelUpdate-getImporteMonedaExtranjera:" + e.toString());
        }
        return importe;
    }

    public static String getAFI(String infoContrato, BigDecimal importe) {

        String honorario = "", iva = "";
        BigDecimal resultado = BigDecimal.ZERO;
        infoContrato = infoContrato.toString().trim();
        BigDecimal honorario_sin_iva = BigDecimal.ZERO;
        int idx = 0;
        double sin_iva = 0.0, val = 0.0;
        String valorAFI = "";
        try {
            if (infoContrato.contains(",")) {
                idx = infoContrato.indexOf(",");

                honorario = infoContrato.substring(0, idx).toString().trim();
                iva = infoContrato.substring(idx + 1, infoContrato.length()).toString().trim();

                val = importe.doubleValue(); //Cambiamos el formato del importe total en MXP
                sin_iva = Double.parseDouble(iva) / 100; //Sacamos el porcentaje del campo sin_iva obtenido
                honorario_sin_iva = new BigDecimal(sin_iva);

                if (honorario.equals("RIESGOS")) {
                    resultado = new BigDecimal(val);
                    resultado = resultado.multiply(honorario_sin_iva);   // (importe total en MXP) * (%honorario_sin_iva)
                } else {
                    val = val / 0.9; //importe total en MXP / 90%
                    resultado = new BigDecimal(val);
                    resultado = resultado.multiply(honorario_sin_iva);  //(importe total en MXP / 90%)* (%honorario_sin_iva)
                }

                val = resultado.doubleValue();
                val = Math.rint(val * 100.0) / 100.0;

                resultado = new BigDecimal(val);
                valorAFI = resultado.setScale(2, BigDecimal.ROUND_HALF_UP) + "";

            }
        } catch (Exception e) {
            System.out.println("ModelUpdate-getAFI:" + e.toString());
        }

        return valorAFI;
    }

    public static String getIVA(String val) {
        double iva = 0.16D, resultado = 0.0D;
        BigDecimal big = null;
        String tmp = "";

        try {
            resultado = Double.parseDouble(val);

            //Obtenemos el IVA correspondiente
            resultado = resultado * iva;
            //truncamos el numero a dos decimales
            resultado = Math.rint(resultado * 100.0) / 100.0;

            big = new BigDecimal(resultado);
            tmp = big.setScale(2, BigDecimal.ROUND_HALF_UP) + "";

        } catch (Exception e) {
            System.out.println("ModelUpdate-getIVA:" + e.toString());
        }

        return tmp;

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
    public static String sumaImporte(String monto_MXP, String monto_HF) {

        BigDecimal montoTotalMXP = BigDecimal.ZERO;
        BigDecimal montoHF = BigDecimal.ZERO;
        String trunc = "";
        int tmp = 0;
        try {
            montoTotalMXP = new BigDecimal(monto_MXP);
            montoHF = new BigDecimal(monto_HF);

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
            System.out.println("ModelUpdate-sumaImporte:" + e.getMessage());
            return trunc = "";
        }
        return montoTotalMXP + "";

    }

    /**
     * Método para obtener el tipo de honorario y % de honorarios sin iva del
     * contrato que se pasa como parámetro.
     *
     * @param contrato : clave de contrato
     * @return String info: tipo_honorario,%honorario_sin_iva.
     */
    public static String getTipoHonorario(String contrato) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;

        contrato.toString().trim();
        String info = "";
        String MySql = "";
        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select tipo_honorario,honorario_sin_iva ";
            MySql += " from contratos ";
            MySql += "where clave_contrato = '" + contrato + "'";

//            System.out.println(MySql);
            ResultSet rstSQLServer = statement.executeQuery(MySql);

            if (rstSQLServer.next()) {
                info = rstSQLServer.getString(1).toString().trim();
                info = info + "," + rstSQLServer.getString(2).toString().trim();
            }
            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            info = "";
            if (connection != null) {
                conn.Desconecta(connection);
            }
            System.out.println("ModelUpdate-getInfoContrato:" + e.toString());
        }
        return info;

    }

    /**
     * Método regresa el Importe Total en MXP.
     *
     * @param montoM1 : Monto total de movimientos del tipo 1.
     * @param montoM2 : Monto total de movimientos del tipo 2.
     * @param montoM3 : Monto total de movimientos del tipo 3.
     * @param montoM4 : Monto total de movimientos del tipo 4.
     * @param montoM5 : Monto total de movimientos del tipo 5.
     * @return BigDecimal: Suma total de los diferentes tipos de movimiento, en
     * caso de error regresa una cadena vacia.
     */
    public static BigDecimal getMontoTotalMXP(String montoM1, String montoM2, String montoM3, String montoM4, String montoM5) {

        BigDecimal tmprl = BigDecimal.ZERO;
        BigDecimal importe = BigDecimal.ZERO;
        try {
            tmprl = new BigDecimal(montoM1);
            importe = importe.add(tmprl);

            tmprl = new BigDecimal(montoM2);
            importe = importe.add(tmprl);

            tmprl = new BigDecimal(montoM3);
            importe = importe.add(tmprl);

            tmprl = new BigDecimal(montoM4);
            importe = importe.add(tmprl);

            tmprl = new BigDecimal(montoM5);
            importe = importe.add(tmprl);

        } catch (Exception e) {
            importe = BigDecimal.ZERO;
            System.out.println("ModelUpdate-getMontoTotalMXP:" + e.getMessage());
        }
        return importe;
    }

    /**
     * Método que regresa el monto total de los movimientos de Bancomer a
     * Bancomer (Movimiento tipo 1) tomando como base los datos que se pasan
     * como parámetro.
     *
     * @param cliente : Nombre del Cliente
     * @param clave_contrato : Clave de contrato asociada al cliente.
     * @param miFecha
     * @param nombre_archivo : Nombre del Lay-Out cargado.
     * @param status_lote
     * @return String montoFormato: Importe total de Bancomer a Bancomer; si
     * ocurre un error regresa una cadena vacia.
     */
    public static String montoBancomerToBancomer(String cliente, String clave_contrato, String miFecha, String nombre_archivo, String status_lote) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;

        BigDecimal importe = BigDecimal.ZERO;
        BigDecimal montoTotal = BigDecimal.ZERO;
        total_movs_tipo1 = 0;
        String MySql = "";
        String total = "";
        try {

            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select l.importe_liquidacion ";
            MySql += " from movimientos_h h , movimientos l , contratos contratos ";
            MySql += " where h.clave_contrato = l.clave_contrato ";
            MySql += " and l.clave_contrato = contratos.clave_contrato ";
            MySql += " and h.fecha_liquidacion = l.fecha_liquidacion ";
            MySql += " and h.nombre_archivo=l.nombre_archivo ";
            MySql += " and contratos.clave_contrato = '" + clave_contrato + "'";
            MySql += " and l.fecha_liquidacion = '" + miFecha + " 00:00:00.000" + "'";
            MySql += " and contratos.nombre_cliente = '" + cliente + "'";
            MySql += " and h.nombre_archivo = '" + nombre_archivo + "'";
            MySql += " and l.tipo_movimiento = 1 ";
            MySql += " and contratos.status = 'A' ";
            MySql += " and h.status = '" + status_lote + "'  ";
//            System.out.println("BancomerToBancomer:" + MySql);

            ResultSet rstSQLServer = statement.executeQuery(MySql);

            while (rstSQLServer.next()) {
                importe = new BigDecimal(rstSQLServer.getString(1).toString().trim()); // Importe de Liquidación
                montoTotal = montoTotal.add(importe);
                total_movs_tipo1++;
            }
            total = montoTotal + "";

            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            total = "";
            total_movs_tipo1 = 0;
            if (connection != null) {
                conn.Desconecta(connection);
            }
            System.out.println("ModelUpdate-montoBancomerToBancomer:" + e.toString());
        }
        return total;
    }

    /**
     * Método que regresa el monto total de los movimientos de Bancomer a Otros
     * (Movimiento tipo 2) Bancos tomando como base los parámetros que se pasan.
     *
     * @param cliente : Nombre del Cliente
     * @param clave_contrato : Clave de contrato asociada al cliente.
     * @param nombre_archivo : Nombre del Lay-Out cargado.
     * @return String montoFormato: Importe total de Bancomer a Otros Bancos; si
     * ocurre un error regresa una cadena vacia.
     */
    public static String montoOtrosBancos(String cliente, String clave_contrato, String miFecha, String nombre_archivo, String status_lote) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;

        BigDecimal importe = BigDecimal.ZERO;
        BigDecimal montoTotal = BigDecimal.ZERO;
        total_movs_tipo2 = 0;
        String MySql = "";
        String total = "";

        try {

            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select l.importe_liquidacion ";
            MySql += " from movimientos_h h , movimientos l , contratos contratos ";
            MySql += " where h.clave_contrato = l.clave_contrato ";
            MySql += " and l.clave_contrato = contratos.clave_contrato ";
            MySql += " and h.fecha_liquidacion = l.fecha_liquidacion ";
            MySql += " and h.nombre_archivo=l.nombre_archivo ";
            MySql += " and contratos.clave_contrato = '" + clave_contrato + "'";
            MySql += " and l.fecha_liquidacion = '" + miFecha + " 00:00:00.000" + "'";
            MySql += " and contratos.nombre_cliente = '" + cliente + "'";
            MySql += " and h.nombre_archivo='" + nombre_archivo + "'";
            MySql += " and l.tipo_movimiento = 2 ";
            MySql += " and contratos.status = 'A' ";
            MySql += " and h.status = '" + status_lote + "'  ";

//            System.out.println("montoOtrosBancos:" + MySql);
            ResultSet rstSQLServer = statement.executeQuery(MySql);
            while (rstSQLServer.next()) {
                importe = new BigDecimal(rstSQLServer.getString(1).toString().trim()); // Importe de Liquidación
                montoTotal = montoTotal.add(importe);
                total_movs_tipo2++;
            }
            total = montoTotal + "";

            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            total = "";
            total_movs_tipo2 = 0;
            if (connection != null) {
                conn.Desconecta(connection);
            }
            System.out.println("ModelUpdate-montoBancomerToOtrosBancos:" + e.toString());
        }
        return total;
    }

    /**
     * Método que regresa el monto total de los movimientos TDD Banamex
     * (Movimiento tipo 3) tomando como base los parámetros que se pasan.
     *
     * @param cliente : Nombre del Cliente
     * @param clave_contrato : Clave de contrato asociada al cliente.
     * @param nombre_archivo : Nombre del Lay-Out cargado.
     * @return String montoFormato: Importe total de TDD Banamex; si ocurre un
     * error regresa una cadena vacia.
     */
    public static String montoTDDBanamex(String cliente, String clave_contrato, String miFecha, String nombre_archivo, String status_lote) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;

        BigDecimal importe = BigDecimal.ZERO;
        BigDecimal montoTotal = BigDecimal.ZERO;
        total_movs_tipo3 = 0;
        String MySql = "";
        String total = "";

        try {

            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select l.importe_liquidacion ";
            MySql += " from movimientos_h h , movimientos l , contratos contratos ";
            MySql += " where h.clave_contrato = l.clave_contrato ";
            MySql += " and l.clave_contrato = contratos.clave_contrato ";
            MySql += " and h.fecha_liquidacion = l.fecha_liquidacion ";
            MySql += "  and h.nombre_archivo=l.nombre_archivo ";
            MySql += " and contratos.clave_contrato = '" + clave_contrato + "'";
            MySql += " and l.fecha_liquidacion = '" + miFecha + " 00:00:00.000" + "'";
            MySql += " and contratos.nombre_cliente = '" + cliente + "'";
            MySql += " and h.nombre_archivo= '" + nombre_archivo + "'";
            MySql += " and l.tipo_movimiento = 3 ";
            MySql += " and contratos.status = 'A' ";
            MySql += " and h.status = '" + status_lote + "'  ";

//            System.out.println("getTDDBanamex:" + MySql);
            ResultSet rstSQLServer = statement.executeQuery(MySql);
            while (rstSQLServer.next()) {
                importe = new BigDecimal(rstSQLServer.getString(1).toString().trim()); // Importe de Liquidación
                montoTotal = montoTotal.add(importe);
                total_movs_tipo3++;
            }
            total = montoTotal + "";

            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            total = "";
            total_movs_tipo3 = 0;
            if (connection != null) {
                conn.Desconecta(connection);
            }
            System.out.println("ModelUpdate-getTDDBanamex:" + e.toString());
        }
        return total;
    }

    /**
     * Método que regresa el monto total de los movimientos que son emisión
     * (Movimiento tipo 4) de Cheques tomando como base los parámetros que se
     * pasan.
     *
     * @param cliente : Nombre del Cliente
     * @param clave_contrato : Clave de contrato asociada al cliente.
     * @param nombre_archivo : Nombre del Lay-Out cargado.
     * @return String montoFormato: Monto total de Emisión de Cheques; si ocurre
     * un error regresa una cadena vacia.
     */
    public static String montoEmisionCheques(String cliente, String clave_contrato, String miFecha, String nombre_archivo, String status_lote) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;

        BigDecimal importe = BigDecimal.ZERO;
        BigDecimal montoTotal = BigDecimal.ZERO;
        total_movs_tipo4 = 0;
        String MySql = "";
        String total = "";
        try {

            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select l.importe_liquidacion  ";
            MySql += " from movimientos_h h , movimientos l , contratos contratos ";
            MySql += " where h.clave_contrato = l.clave_contrato ";
            MySql += " and l.clave_contrato = contratos.clave_contrato ";
            MySql += " and h.fecha_liquidacion = l.fecha_liquidacion ";
            MySql += " and h.nombre_archivo = l.nombre_archivo ";
            MySql += " and contratos.clave_contrato = '" + clave_contrato + "'";
            MySql += " and l.fecha_liquidacion = '" + miFecha + " 00:00:00.000" + "'";
            MySql += " and contratos.nombre_cliente = '" + cliente + "'";
            MySql += " and h.nombre_archivo = '" + nombre_archivo + "'";
            MySql += " and l.tipo_movimiento = 4 ";
            MySql += " and contratos.status = 'A' ";
            MySql += " and h.status = '" + status_lote + "'  ";

//            System.out.println("montoEmisionCheques:" + MySql);
            ResultSet rstSQLServer = statement.executeQuery(MySql);
            while (rstSQLServer.next()) {
                importe = new BigDecimal(rstSQLServer.getString(1).toString().trim()); // Importe de Liquidación
                montoTotal = montoTotal.add(importe);
                total_movs_tipo4++;
            }
            total = montoTotal + "";

            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            total = "";
            total_movs_tipo4 = 0;
            if (connection != null) {
                conn.Desconecta(connection);
            }
            System.out.println("ModelUpdate-montoEmisionCheques:" + e.toString());
        }
        return total;
    }

    /**
     * Método que regresa el monto total de los movimientos a Bancos (Movimiento
     * tipo 5) Extranjeros tomando como base los parámetros que se pasan.
     *
     * @param cliente : Nombre del Cliente
     * @param clave_contrato : Clave de contrato asociada al cliente.
     * @param nombre_archivo : Nombre del Lay-Out cargado.
     * @return String montoFormato: Monto total de movimientos del tipo; si
     * ocurre un error regresa una cadena vacia.
     */
    public static String montoBancosExtranjeros(String cliente, String clave_contrato, String miFecha, String nombre_archivo, String status_lote) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;

        BigDecimal importe = BigDecimal.ZERO;
        BigDecimal montoTotal = BigDecimal.ZERO;
        total_movs_tipo5 = 0;
        String MySql = "";
        String total = "";
        int idx = 0;

        try {

            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select l.importe_liquidacion_mxp  ";
            MySql += " from movimientos_h h , movimientos l , contratos contratos ";
            MySql += " where h.clave_contrato = l.clave_contrato ";
            MySql += " and l.clave_contrato = contratos.clave_contrato ";
            MySql += " and h.fecha_liquidacion = l.fecha_liquidacion ";
            MySql += " and h.nombre_archivo = l.nombre_archivo ";
            MySql += " and contratos.clave_contrato = '" + clave_contrato + "'";
            MySql += " and l.fecha_liquidacion = '" + miFecha + " 00:00:00.000" + "'";
            MySql += " and contratos.nombre_cliente = '" + cliente + "'";
            MySql += " and h.nombre_archivo = '" + nombre_archivo + "'";
            MySql += " and l.tipo_movimiento = 5 ";
            MySql += " and contratos.status = 'A' ";
            MySql += " and h.status = '" + status_lote + "'  ";

//            System.out.println("montoBancosExtranjeros:" + MySql);
            ResultSet rstSQLServer = statement.executeQuery(MySql);
            while (rstSQLServer.next()) {
                importe = new BigDecimal(rstSQLServer.getString(1).toString().trim()); // Importe de Liquidación
                montoTotal = montoTotal.add(importe);
                total_movs_tipo5++;
            }
            total = montoTotal + "";

            idx = total.indexOf(".");

            if (idx == -1) {
                total = total + ".00";
            }

            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            total = "";
            total_movs_tipo5 = 0;
            if (connection != null) {
                conn.Desconecta(connection);
            }
            System.out.println("ModelUpdate-montoBancosExtranjeros:" + e.toString());
        }
        return total;
    }

    //Regresa el total de transacciones Involucradas en este lote
    public static String getTotalTransacciones() {
        String total = "";
        total = total_movs_tipo1 + total_movs_tipo2 + total_movs_tipo3 + total_movs_tipo4 + total_movs_tipo5 + "";
        return total;
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
    private synchronized boolean generaRL_PDF(String archivo_jasper, String clave_contrato, String fecha_liquidacion,
            String nombre_archivo, String monto1, String monto2, String monto3, String monto4, String monto5, String montoTotalMXP,
            String montoAFI, String montoIVA, String montoHF, String montoSPR_MXP, String movs_tipo1, String movs_tipo2, String movs_tipo3,
            String movs_tipo4, String movs_tipo5, String total_movs, String urlArchivo, int idx, Connection lcnnConexion) {

        String archivo = archivo_jasper;
        JasperReport report = null;
        OutputStream output = null;
        JasperPrint print = null;
        String fecha = "";
        String clave_fidei = "";
        String nombreReporte = "";
        boolean genera = false;

        try {
            //Damos formato a la fecha de liquidación
            fecha = ModeloLiquidation.getFormatoFecha(fecha_liquidacion);
            if (!fecha.equals("")) {
                //Verificamos si el nombre del archivo es valido
                if (archivo == null || archivo.equals("")) {
                    System.out.println("ModelUpdate-generaRL_PDF : Archivo *.jrxml invalido");
                    return false;
                }
                try {
                    //Cargamos el archivo binario
                    //report = (JasperReport) JRLoader.loadObject(archivo);
                    //Compilamos el reporte para obtener el binario.
                    report = JasperCompileManager.compileReport(archivo);

                } catch (JRException jre) {
                    System.out.println("ModelUpdate-generaRL_PDF-Compilación:" + jre.toString());
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
                parametro.put("importe_AFI", montoAFI);
                parametro.put("importe_IVA", montoIVA);
                parametro.put("importe_HF", montoHF);
                parametro.put("SPR_MXP", montoSPR_MXP);
                parametro.put("nombre_archivo", nombre_archivo);
                parametro.put("imagen_SME", "./Logos/SME.jpg");
                parametro.put("imagen_Fidei", "./Logos/FideiFuturo.jpg");
                parametro.put("clave_contrato", clave_contrato);
                parametro.put("fecha_liquidacion", fecha_liquidacion);
                parametro.put("total_movs_tipo1", movs_tipo1);
                parametro.put("total_movs_tipo2", movs_tipo2);
                parametro.put("total_movs_tipo3", movs_tipo3);
                parametro.put("total_movs_tipo4", movs_tipo4);
                parametro.put("total_movs_tipo5", movs_tipo5);
                parametro.put("total_movimientos", total_movs);

                clave_fidei = clave_contrato.substring(6, 9);

                if (idx != -1) {
                    if (idx > 0 && idx <= 9) {
                        nombreReporte = clave_fidei + "-0" + idx + "-" + "LQ" + "-01-" + fecha + ".pdf";
                        //Creamos la salida del archivo generado
                        output = new FileOutputStream(new File(urlArchivo + nombreReporte));
                    } else {
                        //Creamos la salida del archivo generado
                        nombreReporte = clave_fidei + "-" + idx + "-" + "LQ" + "-01-" + fecha + ".pdf";
                        output = new FileOutputStream(new File(urlArchivo + nombreReporte));
                    }
                    //Generamos el reporte
                    print = JasperFillManager.fillReport(report, parametro, lcnnConexion);
                    //Exportamos a PDF
                    JasperExportManager.exportReportToPdfStream(print, output);
                    ModelUpdate.setNombre_pdf(nombreReporte);
                    output.flush();
                    output.close();
                    genera = true;

                } else {
                    genera = false;
                }
            } else {
                genera = false;
            }
        } catch (Exception e) {
            System.out.println("ModelUpdate-generaRL_PDF:" + e.getMessage());
            try {
                output.flush();
                output.close();
            } catch (IOException ex) {
                System.out.println("ModelUpdate-generaRL_PDF:" + ex.getMessage());
            }
            genera = false;

        }

        return genera;
    }

    /**
     * Método que genera pdf que corresponde a el Reporte de Liquidación con la
     * Actualización de los movimientos a Bancos Extranjeros.
     *
     * @param cliente : Nombre del Cliente
     * @param clave_contrato : Clave de contrato asociada al cliente.
     * @param fecha_liquidacion: Fecha en que tendrá lugar la liquidación.
     * @param nombre_archivo : Nombre del Lay-Out cargado.
     * @param status_lote: Status actual del lote.
     * @param correoOrigen:Correo que envía el correo de notificación.
     * @param correoDestino:Correo de destinatarios.
     * @param asunto: Asunto del correo de notificación.
     * @param cuerpoCorreo
     * @param urlArchivo: ruta del archivo a adjuntar al correo.
     * @param idx_archivo: Identificador del lote procesado.
     * @return boolean: true si se creo y envío correctamente el reporte de
     * liquidación con importe a bancos extranjeros reflejado, else en otro
     * caso.
     */
    public boolean generaRL_MXP(String cliente, String clave_contrato, String fecha_liquidacion,
            String nombre_archivo, String status_lote, String correoOrigen,
            String correoDestino, String asunto, String cuerpoCorreo, String urlArchivo, int idx_archivo) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        boolean seGuardo = false;
//        boolean existe = false;

//        ModeloLiquidation modelo = new ModeloLiquidation();
        String mov1 = "";
        String mov2 = "";
        String mov3 = "";
        String mov4 = "";
        String mov5 = "";

        //Variables para especificar el total de movimientos de cada tipo
        String movs_tipo1 = "";
        String movs_tipo2 = "";
        String movs_tipo3 = "";
        String movs_tipo4 = "";
        String movs_tipo5 = "";
        String total_movs = "";
        String tipo_honorario = "";

        try {
            //Obtenemos los movimientos de tipo 1.
            mov1 = ModelUpdate.montoBancomerToBancomer(cliente, clave_contrato, fecha_liquidacion, nombre_archivo, status_lote);
            movs_tipo1 = ModelUpdate.total_movs_tipo1 + "";
            //Verificamos si surgio algun error al obtener los movimientos de tipo 1.
            if (!mov1.equals("")) {
                //Obtenemos los movimientos de tipo 2.
                mov2 = ModelUpdate.montoOtrosBancos(cliente, clave_contrato, fecha_liquidacion, nombre_archivo, status_lote);
                movs_tipo2 = ModelUpdate.total_movs_tipo2 + "";
                //Verificamos si surgio algun error al obtener los movimientos de tipo 2.
                if (!mov2.equals("")) {
                    //Obtenemos los movimientos de tipo 3.
                    mov3 = ModelUpdate.montoTDDBanamex(cliente, clave_contrato, fecha_liquidacion, nombre_archivo, status_lote);
                    movs_tipo3 = ModelUpdate.total_movs_tipo3 + "";
                    //Verificamos si surgio algun error al obtener los movimientos de tipo 3.
                    if (!mov3.equals("")) {
                        //Obtenemos los movimientos de tipo 4.
                        mov4 = ModelUpdate.montoEmisionCheques(cliente, clave_contrato, fecha_liquidacion, nombre_archivo, status_lote);
                        movs_tipo4 = ModelUpdate.total_movs_tipo4 + "";
                        //Verificamos si surgio algun error al obtener los movimientos de tipo 4.
                        if (!mov4.equals("")) {
                            //Obtenemos los movimientos de tipo 5.
                            mov5 = ModelUpdate.montoBancosExtranjeros(cliente, clave_contrato, fecha_liquidacion, nombre_archivo, status_lote);
                            movs_tipo5 = ModelUpdate.total_movs_tipo5 + "";
                            //Verificamos si ocurrio algun error al generar los movimientos de tipo 5.
                            if (!mov5.equals("")) {
                                //Obtenemos el Importe Total en MXP.
                                BigDecimal montoTotalMXP = ModelUpdate.getMontoTotalMXP(mov1, mov2, mov3, mov4, mov5);
                                //Obtenemos los datos del tipo de honorario
                                tipo_honorario = ModelUpdate.getTipoHonorario(clave_contrato);
                                //Obtenemos la administración Fiducuaria Integral.
                                String montoAFI = ModelUpdate.getAFI(tipo_honorario, montoTotalMXP);
                                //Obtenemos el IVA (16%) del AFI.
                                String montoIVA = ModelUpdate.getIVA(montoAFI);
                                //Obtenemos los Honorarios Fiduciarios.
                                String montoHF = ModelUpdate.sumaImporte(montoAFI, montoIVA);
                                //Obtenemos la Suficiencia Patrimonial Requerida en pesos Mexicanos.
                                String montoSPR_MXP = ModelUpdate.sumaImporte(montoHF, montoTotalMXP + "");

                                total_movs = ModelUpdate.getTotalTransacciones();

                                //Creamos la conexión a la Base de Datos.
                                connection = conn.ConectaSQLServer();

                                seGuardo = this.generaRL_PDF("./Common/RL_FINAL.jrxml", clave_contrato, fecha_liquidacion, nombre_archivo,
                                        mov1, mov2, mov3, mov4, mov5, montoTotalMXP + "", montoAFI, montoIVA, montoHF, montoSPR_MXP,
                                        movs_tipo1, movs_tipo2, movs_tipo3, movs_tipo4, movs_tipo5, total_movs, urlArchivo, idx_archivo, connection);

                                if (connection != null) {
                                    conn.Desconecta(connection);
                                }

                            } else { //Ocurrio un error al obtener movimientos de tipo 5
                                seGuardo = false;
                            }
                        } else { //Ocurrio un error al obtener movimientos de tipo 4
                            seGuardo = false;
                        }
                    } else { //Ocurrio un error al obtener movimientos de tipo 3
                        seGuardo = false;
                    }
                } else { //Ocurrio un error al obtener movimientos de tipo 2
                    seGuardo = false;
                }
            } else { //Ocurrio un error al obtener movimientos de tipo 1
                seGuardo = false;
            }
        } catch (Exception e) {
            seGuardo = false;
            if (connection != null) {
                conn.Desconecta(connection);
            }
            System.out.println("ModelUpdate-generaRL_MXP:" + e.getMessage());
        }
        return seGuardo;

    }

    public synchronized boolean generaM5_PDF(String cliente, String clave_contrato, String fecha_liquidacion,
            String nombre_archivo, String nombre_fidei, String importe_mxp, String status, String urlArchivo, int verifica, String realPath) {

        clsConexion conn = new clsConexion();
        String archivo = realPath + "\\WEB-INF\\classes\\Common\\LayOutM5_MXP.jrxml";
        Connection connection = null;
        JasperReport report = null;
        OutputStream output = null;
        JasperPrint print = null;
        boolean genera = false;
        String reporte = "";
        String fecha = "";
        int idx = 0;
        try {
            //Damos formato a la fecha de liquidación
            fecha = ModeloLiquidation.getFormatoFecha(fecha_liquidacion);
            if (!fecha.equals("")) {
                //Damos formato a la fecha de liquidación DDOCTAA
                //fecha = ModeloLiquidation.getFormatoMesFecha(fecha_liquidacion);

                //Verificamos si el nombre del archivo es valido
                if (archivo == null || archivo.equals("")) {
                    System.out.println("ModelUpdate-generaM5_PDF : Archivo *.jrxml invalido");
                    return false;
                }
                try {
                    System.out.println("Compilando..." + archivo);
                    report = JasperCompileManager.compileReport(archivo);
                } catch (JRException jre) {
                    System.out.println("ModelUpdate-generaM5_PDF-Compilación:" + jre.toString());
                    return false;
                }
                //Inicializamos los Parámetros.
                Map parametro = new HashMap();
                parametro.put("clave_contrato", clave_contrato);
                parametro.put("fecha_liquidacion", (new clsFecha().CambiaFormatoFecha("dd/MM/yyyy", "yyyy-MM-dd", fecha_liquidacion)) + " 00:00:00.000 ");
                parametro.put("nombre_cliente", cliente);
                parametro.put("nombre_fidei_banco_ext", nombre_fidei);
                parametro.put("logo", realPath + "images\\logo.jpg");
                parametro.put("nombre_archivo", nombre_archivo);
                parametro.put("importe_liquidacion_mxp", importe_mxp);
                parametro.put("status", status);

                String clave_fidei = clave_contrato.substring(6, 9);

                idx = ModelUpdate.archivosGenerados(clave_contrato, fecha_liquidacion, nombre_archivo, status);

                if (verifica >= 0 && idx >= 0) {
                    if (verifica > 0 && verifica <= 9) {
                        if (idx >= 0 && idx <= 9) {
                            //Creamos la salida del archivo generado
                            reporte = clave_fidei + "-0" + verifica + "-IN" + "-0" + idx + "-" + fecha + ".pdf";
                            output = new FileOutputStream(new File(urlArchivo + reporte));
                        } else {
                            //Creamos la salida del archivo generado
                            reporte = clave_fidei + "-0" + verifica + "-IN" + "-" + idx + "-" + fecha + ".pdf";
                            output = new FileOutputStream(new File(urlArchivo + reporte));
                        }
                    } else if (idx >= 0 && idx <= 9) {
                        //Creamos la salida del archivo generado
                        reporte = clave_fidei + "-" + verifica + "-IN" + "-0" + idx + "-" + fecha + ".pdf";
                        output = new FileOutputStream(new File(urlArchivo + reporte));
                    } else {
                        //Creamos la salida del archivo generado
                        reporte = clave_fidei + "-" + verifica + "-IN" + "-" + idx + "-" + fecha + ".pdf";
                        output = new FileOutputStream(new File(urlArchivo + reporte));
                    }
                    //Creamos la conexión a la Base de Datos.
                    connection = conn.ConectaSQLServer();
                    //Generamos el reporte
                    print = JasperFillManager.fillReport(report, parametro, connection);
                    //Exportamos a PDF
                    JasperExportManager.exportReportToPdfStream(print, output);
                    //Cerramos el flujo.
                    output.flush();
                    output.close();
                    genera = this.actualizaReportesMXP(clave_contrato, fecha_liquidacion, nombre_archivo, reporte, status, connection);

                } else {
                    genera = false;
                }
            } else { // Error al dar formato a fecha_hoy:DDOCTYY
                genera = false;
            }
        } catch (Exception e) {
            try {
                genera = false;
                System.out.println("ModelUpdate-generaM5_PDF:" + e.getMessage());
                System.out.println("ModelUpdate-generaM5_PDF:" + e.getLocalizedMessage());
                System.out.println("ModelUpdate-generaM5_PDF:" + e.toString());
                System.out.println("ModelUpdate-generaM5_PDF:" + e.getStackTrace());
                if (connection != null) {
                    conn.Desconecta(connection);
                }
                if (output != null) {
                    output.flush();
                    output.close();
                }
            } catch (IOException ex) {
            }
        }
        return genera;
    }

    /**
     * Método que actualiza el importe de liquidación en MXP de los movimientos
     * a Bancos Extranjeros.
     *
     * @param importe_mxp : Importe de Liquidación en MXP.
     * @param clave_contrato : clave del contrato del cliente.
     * @param fecha_liquidacion : Fecha de la Liquidación.
     * @param nombre_archivo : Nombre del Lay_Out correspondiente a dichos
     * movimientos.
     * @param nombre_fidei : Nombre del Fideicomisario en el Extranjero.
     * @return String verifica: Regresa cadena vacia si todo salio
     * correctamente, en otro caso una cadena con un mensaje descriptivo de la
     * operación.
     */
    public synchronized String actualizaImporteMov5(String importe_mxp, String clave_contrato, String fecha_liquidacion, String nombre_archivo, String nombre_fidei, String status_lote) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String verifica = "";
        String MySql = "";

        //ya no se valida si el importe de liquidacion en MXP fue actualizado por otro usuario, ya que ahora se puede actualizar cuantas veces sea necesario
//        verifica = ModelUpdate.verificaImporteMXP(clave_contrato, fecha_liquidacion, nombre_archivo, nombre_fidei, status_lote);
//        if (verifica.equals("0")) {
        try {
            connection = conn.ConectaSQLServer();
            connection.setAutoCommit(false);
            statement = connection.createStatement();

            MySql = " update movimientos";
            MySql += " set importe_liquidacion_mxp = '" + importe_mxp + "' ";
            MySql += " where tipo_movimiento = 5 ";
            MySql += " and clave_contrato = '" + clave_contrato + "' ";
            MySql += " and fecha_liquidacion = '" + (new clsFecha().CambiaFormatoFecha("dd/MM/yyyy", "yyyy-MM-dd", fecha_liquidacion)) + " 00:00:00.000' ";
            MySql += " and nombre_fidei_banco_ext = '" + nombre_fidei + "' ";
            MySql += " and nombre_archivo = '" + nombre_archivo + "' ";

            statement.executeUpdate(MySql);
            verifica = "";

            connection.commit();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            verifica = " Error al realizar la actualización en MXP ";
            System.out.println("ModelUpdate-actualizaImporteMov5:" + e.toString());
            e.printStackTrace();
            if (connection != null) {
                conn.Desconecta(connection);
            }
        }
//        } else if (!verifica.equals("Error al Verificar Importe en MXP")) {
//            verifica = "El importe ha sido actualizado por otro usuario";
//        }
        return verifica;
    }

    public synchronized String verificaCanceladoM5(String clave_contrato, String fecha_liquidacion, String nombre_archivo, String nombre_fidei) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String verifica = "";
        String MySql = "";

        try {
            connection = conn.ConectaSQLServer();
            connection.setAutoCommit(false);
            statement = connection.createStatement();

            MySql = " select * from movimientos_cancelados ";
            MySql += " where clave_contrato = '" + clave_contrato + "' ";
            MySql += " and fecha_liquidacion = '" + (new clsFecha().CambiaFormatoFecha("dd/MM/yyyy", "yyyy-MM-dd", fecha_liquidacion)) + " 00:00:00.000' ";
            MySql += " and nombre_fidei_banco_ext = '" + nombre_fidei + "' ";
            MySql += " and nombre_archivo = '" + nombre_archivo + "' ";
            MySql += " and tipo_movimiento = 5 ";

            ResultSet rstSQLServer = statement.executeQuery(MySql);
            if (rstSQLServer.next()) {
                verifica = "ENCONTRADO";
            }

            connection.commit();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            verifica = "ERROR";
            System.out.println("ModelUpdate-actualizaImporteMov5:" + e.toString());
            e.printStackTrace();
            if (connection != null) {
                conn.Desconecta(connection);
            }
        }
        return verifica;
    }

    /**
     * Método que verifica si ya fue almacenado el importe de liquidación en MXP
     * que se especifica con los valores que se pasan como parámetro.
     *
     * @param importe_mxp : Importe de Liquidación en MXP.
     * @param clave_contrato : clave del contrato del cliente.
     * @param fecha_liquidacion : Fecha de la Liquidación.
     * @param nombre_archivo : Nombre del Lay_Out correspondiente a dichos
     * movimientos.
     * @param nombre_fidei : Nombre del Fideicomisario en el Extranjero.
     * @return boolean valido: Regresa true si se actualizo la información
     * satisfactoriamente en la base de datos, false en otro caso.
     */
    public static String verificaImporteMXP(String clave_contrato, String fecha_liquidacion, String nombre_archivo, String nombre_fidei, String status_lote) {
        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String verifica = "";
        String MySql = "";
        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select importe_liquidacion_mxp ";
            MySql += " from movimientos_h h, movimientos l  ";
            MySql += " where h.clave_contrato = l.clave_contrato  ";
            MySql += " and h.fecha_liquidacion = l.fecha_liquidacion  ";
            MySql += " and h.nombre_archivo = l.nombre_archivo  ";
            MySql += " and l.tipo_movimiento = 5 ";
            MySql += " and h.clave_contrato = '" + clave_contrato + "' ";
            MySql += " and h.fecha_liquidacion = '" + (new clsFecha().CambiaFormatoFecha("dd/MM/yyyy", "yyyy-MM-dd", fecha_liquidacion)) + " 00:00:00.000" + "' ";
            MySql += " and h.nombre_archivo = '" + nombre_archivo + "'";
            MySql += " and l.nombre_fidei_banco_ext = '" + nombre_fidei + "'";
            MySql += " and h.status = '" + status_lote + "'";
//            System.out.println(MySql);
            ResultSet rstSQLServer = statement.executeQuery(MySql);
            if (rstSQLServer.next()) {
                verifica = rstSQLServer.getString(1).toString().trim();
            }
            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            verifica = "Error al verificar importe en MXP";
            System.out.println("ModelUpdate-verificaImporteMXP:" + e.toString());
            if (connection != null) {
                conn.Desconecta(connection);
            }
        }
        return verifica;
    }

    /**
     * Método que regresa la fecha y el usuario que genera las transacciones
     * correspondientes a los datos que se pasan como parámetro.
     *
     * @param clave_contrato : Clave de contrato del cliente.
     * @param fechaLiq : Fecha de Liquidación.
     * @param nombre_archivo Nombre del Lay-Out
     * @return String Fecha: Fecha y usuario de transacciones.
     */
    public static String getFechaTransacciones(String clave_contrato, String fechaLiq, String nombre_archivo, String status_lote) {
        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String MySql = "";
        String hora = "";
        int idx = 0;

        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select h.fecha_usuario_opera , users.nombre_usuario ";
            MySql += " from movimientos_h h ,usuarios_admin  users";
            MySql += " where h.usuario_opera = users.usuario ";
            MySql += " and h.clave_contrato = '" + clave_contrato + "' ";
            MySql += " and h.fecha_liquidacion = '" + (new clsFecha().CambiaFormatoFecha("dd/MM/yyyy", "yyyy-MM-dd", fechaLiq)) + " 00:00:00.000' ";
            MySql += " and h.nombre_archivo = '" + nombre_archivo + "'";
            MySql += " and h.status = '" + status_lote + "'";
//            System.out.println("getFechaTransacciones:" + MySql);
            ResultSet rstSQLServer = statement.executeQuery(MySql);
            if (rstSQLServer.next()) {
                hora = rstSQLServer.getString(1);
                idx = hora.indexOf(".");
                if (idx > 0) {
                    hora = hora.substring(0, idx);
                }
                hora = hora + "," + rstSQLServer.getString(2);
            }

            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            hora = "";
            if (connection != null) {
                conn.Desconecta(connection);
            }
            System.out.println("ModelUpdate-getFechaTransacciones:" + e.toString());
        }
        return hora;
    }

    /**
     * Método que regresa el conjunto de fideicomisarios en el extranjero
     * pendientes por operar.
     *
     * @param clave_contrato : Clave de contrato del cliente.
     * @param fechaLiq : Fecha de Liquidación.
     * @param nombre_archivo Nombre del Lay-Out
     * @return Vector namesFidei: Conjunto de Fideicomisarios en Banco
     * Extranjero, si ocurrio un error regresa Null.
     */
    public static String getFideiBancoExtranjero(String clave_contrato, String fechaLiq, String nombre_archivo, String status_lote, boolean incluir_actualizados) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;

        String nombreFideicomisarioExt = "";
        String MySql = "";

        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            MySql = " select nombre_fidei_banco_ext ";
            MySql += " from movimientos_h h, movimientos l  ";
            MySql += " where h.clave_contrato = l.clave_contrato  ";
            MySql += " and h.fecha_liquidacion = l.fecha_liquidacion  ";
            MySql += " and h.nombre_archivo = l.nombre_archivo  ";
            MySql += " and l.tipo_movimiento = 5 ";
            MySql += " and h.clave_contrato = '" + clave_contrato + "' ";
            MySql += " and h.fecha_liquidacion = '" + (new clsFecha().CambiaFormatoFecha("dd/MM/yyyy", "yyyy-MM-dd", fechaLiq)) + " 00:00:00.000' ";
            MySql += " and h.nombre_archivo = '" + nombre_archivo + "'";
            MySql += " and h.status = '" + status_lote + "'";
            if (incluir_actualizados) {
                MySql += " and l.importe_liquidacion_mxp != '0'";
            } else {
                MySql += " and l.importe_liquidacion_mxp = '0'";
            }
            MySql += " order by nombre_fidei_banco_ext asc ";
//            System.out.println("Fideicomitentes:" + MySql);
//            namesFidei.add(" ---------- Seleccione ---------- ");

            ResultSet rstSQLServer = statement.executeQuery(MySql);

            while (rstSQLServer.next()) {
                nombreFideicomisarioExt = rstSQLServer.getString(1);
            }
            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            nombreFideicomisarioExt = null;
            if (connection != null) {
                conn.Desconecta(connection);
            }
            System.out.println("ModelUpdate-getFideiBancoExtranjero:" + e.toString());
        }
        return nombreFideicomisarioExt;
    }

    /**
     * Método que asigna el status "M" (Modificación) al lote.
     *
     * @param clave_contrato : clave del contrato del cliente.
     * @param fecha_liquidacion : Fecha de la Liquidación.
     * @param nombre_archivo : Nombre del Lay_Out correspondiente a dichos
     * movimientos.
     * @return boolean valido: Regresa true si se actualizo la información
     * satisfactoriamente en la base de datos, false en otro caso.
     */
    public synchronized boolean actualizaStatusMod(String clave_contrato, String fecha_liquidacion, String nombre_archivo, String usuario_modifica) {
        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        boolean seGuardo = false;
        String MySql = "";

        try {
            //Realizamos la conexión a la Base de Datos.
            connection = conn.ConectaSQLServer();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            MySql = " update movimientos_h";
            MySql += " set status = 'P', ";
            MySql += " usuario_actualiza = '" + usuario_modifica + "', ";
            MySql += " fecha_usuario_actualiza = getDate()";
            MySql += " where clave_contrato = '" + clave_contrato + "' ";
            MySql += " and fecha_liquidacion = '" + (new clsFecha().CambiaFormatoFecha("dd/MM/yyyy", "yyyy-MM-dd", fecha_liquidacion)) + " 00:00:00.000' ";
            MySql += " and nombre_archivo = '" + nombre_archivo + "' ";
//            System.out.println(MySql);
            // Se ejecuta el encabezado de la consulta
            statement.executeUpdate(MySql);
            seGuardo = true;

            connection.commit();
//            System.out.println("Transaction commit...");
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            seGuardo = false;
            System.out.println("ModelUpdate-actualizaImporteMov5:" + e.toString());
            e.printStackTrace();
            if (connection != null) {
                conn.Desconecta(connection);
            }
        }
        return seGuardo;
    }

    /**
     * Método que proporciona el conjunto de fideicomiso que tienen movimientos
     * pendientes a bancos extranjeros según el status que se para como
     * parámetro.
     *
     * @param status: Status de movimientos.
     * @return Vector clientes: Fideicomisos. Si ocurre un error regresa null.
     */
    public static Vector getFideicomisosMT5(String status, boolean incluir_actualizados) {
        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;

        Vector clientes = new Vector();
        String MySql = "";
        String cliente = "";
        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select distinct(h.clave_contrato) ";
            MySql += " from contratos contratos, movimientos_h h, movimientos l ";
            MySql += " where h.clave_contrato = l.clave_contrato ";
            MySql += " and l.clave_contrato = contratos.clave_contrato ";
            MySql += " and h.fecha_liquidacion = l.fecha_liquidacion ";
            MySql += " and h.nombre_archivo = l.nombre_archivo ";
            MySql += " and contratos.status = 'A'  ";
            MySql += " and h.status = '" + status + "'  ";
            MySql += " and l.tipo_movimiento in (5) ";
            if (incluir_actualizados) {
                MySql += " and l.importe_liquidacion_mxp != '0'";
            } else {
                MySql += " and l.importe_liquidacion_mxp = '0'";
            }
            MySql += " order by h.clave_contrato asc ";

//            System.out.println("getFideicomisosMT5:" + MySql);
            clientes.add("  -----Seleccione-----  ");

            ResultSet rstSQLServer = statement.executeQuery(MySql);
            while (rstSQLServer.next()) {
                cliente = rstSQLServer.getString(1).toString().trim();
                clientes.add(cliente);
            }
            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            clientes = null;
            if (connection != null) {
                conn.Desconecta(connection);
            }
            System.out.println("ModelUpdate-getClientesMT5:" + e.getMessage());
        }
        return clientes;
    }

    /**
     * Método que verifica si los movimientos a bancos extranjeros del lote que
     * se le pasa como parámetro ya fue actualizados.
     *
     * @param clave_contrato: Clave de fideicomiso.
     * @param fecha-liquidacion: Fecha en que tendrá lugar la liquidación
     * @param nombre_archivo :Nombre del Lay-Out.
     * @param status_proceso: Status actual del lote.
     * @return boolean: True si ya fue realizada la actualización del importe de
     * liquidación en moneda nacional, false en otro caso.
     */
    public static boolean verificaActualizacionLote(String clave_contrato, String fecha_liquidacion, String nombre_archivo, String status_proceso) {
        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        boolean verifica = false;
        String MySql = "";
        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select l.clave_contrato, l.fecha_liquidacion, l.nombre_archivo, l.cuenta_deposito, ";
            MySql += " l.nombre_empleado, l.apellidoP_empleado, l.apellidoM_empleado, l.tipo_movimiento,  ";
            MySql += " l.clave_banco, l.importe_liquidacion, l.tipo_moneda  ";
            MySql += " from contratos contratos, movimientos_h h, movimientos l";
            MySql += " where h.clave_contrato = l.clave_contrato ";
            MySql += " and l.clave_contrato = contratos.clave_contrato ";
            MySql += " and h.fecha_liquidacion = l.fecha_liquidacion ";
            MySql += " and h.nombre_archivo = l.nombre_archivo ";
            MySql += " and contratos.status = 'A'  ";
            MySql += " and h.clave_contrato = '" + clave_contrato + "'  ";
            MySql += " and h.fecha_liquidacion = '" + new clsFecha().CambiaFormatoFecha("dd/MM/yyyy", "yyyy-MM-dd", fecha_liquidacion) + "'  ";
            MySql += " and h.nombre_archivo = '" + nombre_archivo + "'  ";
            MySql += " and h.status = '" + status_proceso + "'  ";
            MySql += " and l.importe_liquidacion_mxp = '0'";
            MySql += " and l.tipo_movimiento in (5) ";
            MySql += " EXCEPT ";
            MySql += " select clave_contrato, fecha_liquidacion, nombre_archivo, cuenta_deposito, ";
            MySql += " nombre_empleado, apellidoP_empleado, apellidoM_empleado, tipo_movimiento,  ";
            MySql += " clave_banco, importe_liquidacion, tipo_moneda  ";
            MySql += " from movimientos_cancelados  ";
            MySql += " where clave_contrato = '" + clave_contrato + "'  ";
            MySql += " and fecha_liquidacion = '" + new clsFecha().CambiaFormatoFecha("dd/MM/yyyy", "yyyy-MM-dd", fecha_liquidacion) + "'  ";
            MySql += " and nombre_archivo = '" + nombre_archivo + "'  ";
            MySql += " and status='A'  ";

//            System.out.println("verificaActualizacionLote:" + MySql);
            ResultSet rstSQLServer = statement.executeQuery(MySql);
            if (rstSQLServer.next()) {
                verifica = false;
            } else {
                verifica = true;
            }
            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            verifica = false;
            if (connection != null) {
                conn.Desconecta(connection);
            }
            System.out.println("ModelUpdate-verificaActualizacionLote:" + e.getMessage());
        }
        return verifica;
    }

    /**
     * Método que regresa las fechas de liquidación de movimientos a Bancos
     * Extranjeros pendientes por operar del cliente que se le pasa como
     * parámetro.
     *
     * @param String clave_contrato :Clave de fideicomiso.
     * @return Vector datos: Conjunto de fechas en que tendrá lugar la
     * liquidación. Si ocurrio un error en la ejecución regresa Null.
     */
    public static Vector getDateLiqMov5(String clave_contrato, String status_lote, boolean incluir_actualizados) {
        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;

        clsFecha fetch = new clsFecha();
        Vector fechas = new Vector();
        nombre_fideicomitente = "";
        String MySql = "";
        String fecha = "";
        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            //Obtenemos las fechas de liquidación con movimientos pendientes de este cliente
            MySql = " select  distinct(LEFT(CONVERT(VARCHAR, h.fecha_liquidacion, 120), 10)),contratos.nombre_cliente ";
            MySql += " from contratos contratos, movimientos_h h, movimientos l ";
            MySql += " where h.clave_contrato = l.clave_contrato ";
            MySql += " and l.clave_contrato = contratos.clave_contrato ";
            MySql += " and h.fecha_liquidacion = l.fecha_liquidacion ";
            MySql += " and h.nombre_archivo = l.nombre_archivo    ";
            MySql += " and contratos.clave_contrato = '" + clave_contrato + "' ";
            MySql += " and contratos.status = 'A'   ";
            MySql += " and h.status ='" + status_lote + "'  ";
            MySql += " and l.tipo_movimiento in (5) ";
            if (incluir_actualizados) {
                MySql += " and l.importe_liquidacion_mxp != '0'";
            } else {
                MySql += " and l.importe_liquidacion_mxp = '0'";
            }

//            System.out.println("getDateLiqMov5:" + MySql);
            fechas.add(" -Seleccione- ");

            ResultSet rstSQLServer = statement.executeQuery(MySql);
            fetch.setFormato("dd/MM/yyyy");
            while (rstSQLServer.next()) {
                //Damos formato a la fecha obtenida
                fecha = rstSQLServer.getString(1);
                fecha = fetch.CambiaFormatoFecha("yyyy-MM-dd", "dd/MM/yyyy", fecha);
                nombre_fideicomitente = rstSQLServer.getString(2);
                fechas.add(fecha);
            }
            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            fechas = null;
            nombre_fideicomitente = "";
            if (connection != null) {
                conn.Desconecta(connection);
            }
            System.out.println("ModelUpdate-getDateLiqMov5:" + e.getMessage());
        }
        return fechas;
    }

    /**
     * Método que proporciona el nombre de los Lay-Out's con movimientos
     * pendientes a Bancos Extranjeros del cliente y fecha que se le pasa como
     * parámetro.
     *
     * @param clave_contrato : Clave de contrato asociada a un cliente.
     * @param fecha_liquidacion: fecha de liquidación a verificar.
     * @param status: Status del Lay-Out.
     * @return Vector clientes: Nombre de Lay-Out's cargados.
     */
    public static Vector getFilesName(String clave_contrato, String fecha_liquidacion, String status, boolean incluir_actualizados) {
        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;

        Vector files = new Vector();
        String MySql = "";
        String arch = "";
        try {
            files.add("  -Seleccione-  ");
            if (!fecha_liquidacion.equals("-Seleccione-")) {

                connection = conn.ConectaSQLServer();
                statement = connection.createStatement();
                statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

                MySql += " select distinct(h.nombre_archivo) ";
                MySql += " from contratos c, movimientos_h h, movimientos l ";
                MySql += " where h.clave_contrato = l.clave_contrato ";
                MySql += " and h.fecha_liquidacion = l.fecha_liquidacion ";
                MySql += " and h.nombre_archivo = l.nombre_archivo ";
                MySql += " and c.clave_contrato = h.clave_contrato ";
                MySql += " and h.clave_contrato = '" + clave_contrato + "' ";
                MySql += " and h.fecha_liquidacion = '" + (new clsFecha().CambiaFormatoFecha("dd/MM/yyyy", "yyyy-MM-dd", fecha_liquidacion)) + " 00:00:00.000' ";
                MySql += " and c.status = 'A'  ";
                MySql += " and h.status = '" + status + "' ";
                MySql += " and l.tipo_movimiento in (5)  ";
                if (incluir_actualizados) {
                    MySql += " and l.importe_liquidacion_mxp != '0'";
                } else {
                    MySql += " and l.importe_liquidacion_mxp = '0'";
                }
                ResultSet rstSQLServer = statement.executeQuery(MySql);

                while (rstSQLServer.next()) {
                    arch = rstSQLServer.getString(1);
                    files.add(arch);
                }
                rstSQLServer.close();
                statement.close();

                if (connection != null) {
                    conn.Desconecta(connection);
                }
            }
        } catch (Exception e) {
            files = null;
            if (connection != null) {
                conn.Desconecta(connection);
            }
            System.out.println("ModelUpdate-getFilesName:" + e.getMessage());
        }
        return files;
    }

    /**
     * Método que regresa el número de lotes que se han procesado
     * satisfactoriamente con los datos que se pasan como parámetro.
     *
     * @param clave_contrato : clave de Fideicomiso.
     * @param fecha_liquidacion : fecha de Liquidación.
     * @return int : numero de archivos generados. Si ocurre algun error regresa
     * -1.
     */
    public static int archivosGenerados(String clave_contrato, String fecha_liquidacion, String nombre_archivo, String status_lote) {
        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String MySql = "";
        int idx = 0;
        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select count(importe_liquidacion_mxp) ";
            MySql += " from movimientos_h h, movimientos l ";
            MySql += " where h.clave_contrato = l.clave_contrato ";
            MySql += " and h.fecha_liquidacion = l.fecha_liquidacion ";
            MySql += " and h.nombre_archivo = l.nombre_archivo ";
            MySql += " and h.clave_contrato = '" + clave_contrato + "' ";
            MySql += " and h.fecha_liquidacion = '" + (new clsFecha().CambiaFormatoFecha("dd/MM/yyyy", "yyyy-MM-dd", fecha_liquidacion)) + " 00:00:00.000' ";
            MySql += " and h.nombre_archivo = '" + nombre_archivo + "' ";
            MySql += " and l.importe_liquidacion_mxp not in ('0') ";
            MySql += " and h.status = '" + status_lote + "'  ";
            MySql += " and l.tipo_movimiento = 5 ";

//            System.out.println("archivosGenerados:" + MySql);
            ResultSet rstSQLServer = statement.executeQuery(MySql);

            if (rstSQLServer.next()) {
                idx = rstSQLServer.getInt(1);
            }
            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            idx = -1;
            if (connection != null) {
                conn.Desconecta(connection);
            }
            System.out.println("ModelUpdate-archivosGenerados:" + e.getMessage());
        }
        return idx;
    }

    public static int reportesLiquidacionGenerados(String clave_contrato, String fecha_liquidacion) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;

        String MySql = "";
        int idx = 0;

        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select count(distinct(h.nombre_archivo))  ";
            MySql += " from movimientos_h h, movimientos l  ";
            MySql += " where h.clave_contrato = l.clave_contrato  ";
            MySql += " and h.fecha_liquidacion = l.fecha_liquidacion  ";
            MySql += " and h.nombre_archivo = l.nombre_archivo ";
            MySql += " and h.clave_contrato= '" + clave_contrato + "' ";
            MySql += " and h.fecha_liquidacion = '" + fecha_liquidacion + " 00:00:00.00' ";
            MySql += " and l.tipo_movimiento = 5  ";
            MySql += " and h.status = 'M'   ";

//            System.out.println("reportesLiquidacionGenerados:" + MySql);
            ResultSet rstSQLServer = statement.executeQuery(MySql);

            if (rstSQLServer.next()) {
                idx = Integer.parseInt(rstSQLServer.getString(1));
            }
            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            idx = -1;
            if (connection != null) {
                conn.Desconecta(connection);
            }
            System.out.println("ModelUpdate-reportesLiquidacionGenerados:" + e.getMessage());
        }
        return idx;
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
    public synchronized boolean actualizaStatusLote(String clave_contrato, String fecha_liquidacion, String nombre_archivo, String usuario, String status_global, String status_autoriza, String status_proceso, String importe_total, double nuevo_saldo) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        boolean seGuardo = false;
        String MySql = "";
        DecimalFormat formato = new DecimalFormat("0.00");
        try {
            connection = conn.ConectaSQLServer();
            connection.setAutoCommit(false);
            statement = connection.createStatement();

            MySql = " update movimientos_h";
            MySql += " set status = '" + status_global + "', ";
            MySql += " status_autoriza = '" + status_autoriza + "', ";
            MySql += " usuario_autoriza='" + usuario + "', ";
            MySql += " fecha_usuario_autoriza = getDate() ";
            MySql += " where clave_contrato = '" + clave_contrato + "' ";
            MySql += " and fecha_liquidacion = '" + fecha_liquidacion + " 00:00:00.000" + "' ";
            MySql += " and nombre_archivo = '" + nombre_archivo + "' ";
            MySql += " and status = '" + status_proceso + "' ";

            // Se ejecuta el encabezado
            statement.executeUpdate(MySql);
            seGuardo = true;

            if (seGuardo) {
                seGuardo = false;
                MySql = " insert into EC_" + clave_contrato + " ";
                MySql += " (fecha,concepto,cargo,saldo,usuario_genera,nombre_archivo) ";
                MySql += " values ( ";
                MySql += " getDate(), ";
                MySql += " 'ORDEN DE LIQUIDACION', ";
                MySql += " " + importe_total + ", ";
                MySql += " " + formato.format(nuevo_saldo) + ", ";
                MySql += " '" + usuario + "', ";
                MySql += " '" + nombre_archivo + "') ";

                statement.executeUpdate(MySql);
                seGuardo = true;
            }

            if (seGuardo) {
                seGuardo = false;
                MySql = " update contratos set ";
                MySql += " saldo=" + formato.format(nuevo_saldo) + " ";
                MySql += " where clave_contrato ='" + clave_contrato + "' ";

                statement.executeUpdate(MySql);
                seGuardo = true;
            }

            connection.commit();
//            System.out.println("Transaction commit...");
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
            System.out.println("ModelUpdate-actualizaStatusAutoriza:" + e.toString());
            if (connection != null) {
                conn.Desconecta(connection);
            }
        }
        return seGuardo;
    }

    public synchronized boolean actualizaStatusLote_DiaCorte(String clave_contrato, String fecha_liquidacion, String nombre_archivo, String usuario, String status_global, String status_autoriza, String status_proceso, String importe_total, double nuevo_saldo) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        boolean seGuardo = false;
        String MySql = "";
        DecimalFormat formato = new DecimalFormat("0.00");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, getNo_movimiento());
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss.SSS");

        try {
            connection = conn.ConectaSQLServer();
            connection.setAutoCommit(false);
            statement = connection.createStatement();

            MySql = " update movimientos_h";
            MySql += " set status = '" + status_global + "', ";
            MySql += " status_autoriza = '" + status_autoriza + "', ";
            MySql += " usuario_autoriza='" + usuario + "', ";
            MySql += " fecha_usuario_autoriza = '" + formatoFecha.format(cal.getTime()) + "' ";
            MySql += " where clave_contrato = '" + clave_contrato + "' ";
            MySql += " and fecha_liquidacion = '" + fecha_liquidacion + " 00:00:00.000" + "' ";
            MySql += " and nombre_archivo = '" + nombre_archivo + "' ";
            MySql += " and status = '" + status_proceso + "' ";

            // Se ejecuta el encabezado
            statement.executeUpdate(MySql);
            seGuardo = true;

            if (seGuardo) {
                seGuardo = false;
                MySql = " insert into EC_" + clave_contrato + " ";
                MySql += " (fecha,concepto,cargo,saldo,usuario_genera,nombre_archivo) ";
                MySql += " values ( ";
                MySql += " '" + formatoFecha.format(cal.getTime()) + "', ";
//            MySql += " '"+ clave_contrato +"', ";
                MySql += " 'ORDEN DE LIQUIDACION', ";
                MySql += " " + importe_total + ", ";
                MySql += " " + formato.format(nuevo_saldo) + ", ";
                MySql += " '" + usuario + "', ";
                MySql += " '" + nombre_archivo + "') ";

                statement.executeUpdate(MySql);
                seGuardo = true;
            }

            if (seGuardo) {
                seGuardo = false;
                MySql = " update contratos set ";
                MySql += " saldo=" + formato.format(nuevo_saldo) + " ";
                MySql += " where clave_contrato ='" + clave_contrato + "' ";

                statement.executeUpdate(MySql);
                seGuardo = true;
            }

            connection.commit();
//            System.out.println("Transaction commit...");
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
            System.out.println("ModelUpdate-actualizaStatusLote_DiaCorte:" + e.toString());
            if (connection != null) {
                conn.Desconecta(connection);
            }
        }
        return seGuardo;
    }

    public static int getNo_movimiento() {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String MySql = "";
        int numero = 0;

        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select movimientos ";
            MySql += " from registro_edo_cta ";
            MySql += " where estado = 'CONTADOR_MOVS_MES' ";
            MySql += " and fecha_ini='1999-01-01 00:00:00.000' ";
            MySql += " and fecha_fin='1999-01-01 00:00:00.000' ";

            ResultSet rstSQLServer = statement.executeQuery(MySql);

            if (rstSQLServer.next()) {
                numero = rstSQLServer.getInt("movimientos");
                System.out.println("numero=" + numero);
            } else {
                System.out.println("no encontrado");
            }
            conn = new clsConexion();
            connection = null;
            statement = null;
            connection = conn.ConectaSQLServer();
            connection.setAutoCommit(false);
            statement = connection.createStatement();

            MySql = " update registro_edo_cta ";
            MySql += " set movimientos=" + (numero + 1);
            MySql += " where estado ='CONTADOR_MOVS_MES' ";
            MySql += " and fecha_ini='1999-01-01 00:00:00.000' ";
            MySql += " and fecha_fin='1999-01-01 00:00:00.000' ";
            statement.executeUpdate(MySql);
            connection.commit();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }

        } catch (Exception e) {
            if (connection != null) {
                conn.Desconecta(connection);
            }
            System.out.println("ModelUpdate-getInfoContrato:" + e.toString());
        }

        return numero;
    }

    /**
     * Este metodo indica si el día actual esta dentro del rango de dias habiles
     * del mes
     *
     * @return
     */
    public static synchronized boolean date_en_habil() {
        java.util.Date fecha_actual = new java.util.Date();
        java.util.Date fecha_ultimo_dia = getUltimoDiaHabilDeMes();

        return fecha_actual.before(fecha_ultimo_dia);
    }

    public static java.util.Date getUltimoDiaHabilDeMes() {
        Calendar calMax = Calendar.getInstance();
        calMax.set(calMax.get(Calendar.YEAR),
                calMax.get(Calendar.MONTH),
                calMax.getActualMaximum(Calendar.DAY_OF_MONTH),
                16, 0, 0);

//        System.out.println("El ultimo dia de mes: " + calMax.getTime().toString());
        int dia_semana = calMax.get(Calendar.DAY_OF_WEEK);
//        System.out.println("Dia de semana: " + dia_semana);   
        if (dia_semana == 1) {
//             System.out.println("Cae en domingo");
            calMax.set(Calendar.DAY_OF_MONTH, calMax.get(Calendar.DAY_OF_MONTH) - 2);
        }
        if (dia_semana == 7) {
//           System.out.println("Cae en sabado");
            calMax.set(Calendar.DAY_OF_MONTH, calMax.get(Calendar.DAY_OF_MONTH) - 1);
        }

//         calMax.set(2014, 7, 5, 16,14);
        System.out.println("La fecha de corte al mes= " + calMax.getTime().toString());

        return calMax.getTime();
    }

    public static boolean getUrlEdoCtaFid(String nombre_archivo) {
        //la cadena de entrada es el nombre del estado de cuenta
        // que de be de tener el siguiente formato
        //EDOCTA_ClaveContrato_YYYY_MM.zip
        System.out.println("Comprobando nomenclatura de nombre para:" + nombre_archivo);
        if (nombre_archivo.length() == "EDOCTA_ClaveContrato_YYYY_MM.zip".length()) {
            //la ruta que debe  de existir, tiene el siguiente formato:
            //"C:\\inetpub\\ftproot\\EstadosDeCuenta\\" + clave_contrato +  "\\";
            File directorio = new File("C:\\inetpub\\ftproot\\EstadosDeCuenta\\" + nombre_archivo.substring(7, 20) + "\\");
            if (directorio.exists()) {
                if (directorio.isDirectory()) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public synchronized boolean actualizaReportesMXP(String clave_contrato, String fecha_liquidacion, String nombre_archivo, String reporte, String status, Connection connection) {

        clsConexion conn = new clsConexion();
        Statement statement = null;
        ResultSet rstSQLServer = null;
        boolean seGuardo = false;
        String reportes = "";
        String MySql = "";

        try {
            connection = conn.ConectaSQLServer();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select reportes_liquidacion_in  ";
            MySql += " from movimientos_h  ";
            MySql += " where clave_contrato= '" + clave_contrato + "' ";
            MySql += " and fecha_liquidacion = '" + (new clsFecha().CambiaFormatoFecha("dd/MM/yyyy", "yyyy-MM-dd", fecha_liquidacion)) + " 00:00:00.000' ";
            MySql += " and nombre_archivo = '" + nombre_archivo + "' ";
            MySql += " and status = '" + status + "' ";

            rstSQLServer = statement.executeQuery(MySql);

            if (rstSQLServer.next()) {
                reportes = rstSQLServer.getString(1);
                if (reportes == null || reportes.equals("")) {
                    reportes = reporte;
                } else {
                    reportes = reportes.trim() + ";" + reporte;
                }
                seGuardo = true;
            } else {
                seGuardo = false;
            }
            rstSQLServer.close();
            if (seGuardo) {
                MySql = " update movimientos_h ";
                MySql += " set reportes_liquidacion_in = '" + reportes + "' ";
                MySql += " where clave_contrato= '" + clave_contrato + "' ";
                MySql += " and fecha_liquidacion = '" + (new clsFecha().CambiaFormatoFecha("dd/MM/yyyy", "yyyy-MM-dd", fecha_liquidacion)) + " 00:00:00.000' ";
                MySql += " and nombre_archivo = '" + nombre_archivo + "' ";
                MySql += " and status = '" + status + "' ";
//                System.out.println("actualizaReportesMXP:" + MySql);
                // Se ejecuta el encabezado
                statement.executeUpdate(MySql);
                connection.commit();
//            System.out.println("Transaction commit...");
            }
            statement.close();
            if (connection != null) {
                conn.Desconecta(connection);
            }
            seGuardo = true;
        } catch (Exception e) {
            try {
                e.printStackTrace();
                seGuardo = false;
                System.out.println("ModelUpdate-actualizaReportesMXP:" + e.toString());
                if (connection != null) {
                    conn.Desconecta(connection);
                }
                rstSQLServer.close();
            } catch (Exception ex) {
                System.out.println("ModelUpdate-actualizaReportesMXP:" + ex.toString());
                try {
                    statement.close();
                } catch (Exception e1) {
                    System.out.println("ModelUpdate-actualizaReportesMXP:" + e1.toString());
                }
            }
        }
        return seGuardo;
    }
}
