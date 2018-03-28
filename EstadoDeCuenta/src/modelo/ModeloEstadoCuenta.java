/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

/**
 *
 * @author Luis-Valerio
 */
public class ModeloEstadoCuenta {

    public static Vector<Double> getDatos_EC(String clave_contrato, String fecha_ini, String fecha_fin, double saldo_inicio_mes) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        Vector<Double> datos = new Vector<Double>();
        Double aportacion_mes = -1.0D;
        Double restitucion_mes = -1.0D;
        Double liquidacion_mes = -1.0D;
        Double honorario_mes = -1.0D;
        Double iva_mes = -1.0D;
        Double saldo_final_mes = -1.0D;
        Integer no_movimientos = 0;

        String MySql = "";

        try {

            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
///////Comienza el calculo de aportacion_mes
            MySql += " select sum(cast(abono as float)) as aportacion_mes ";
            MySql += " from EC_" + clave_contrato + " ";
            MySql += " where concepto='APORTACION A PATRIMONIO' ";
            MySql += " and fecha>='" + fecha_ini + " 00:00:00.000' ";
            MySql += " and fecha<='" + fecha_fin + " 23:59:59.000' ";
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
            MySql += " from EC_" + clave_contrato + " ";
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
            MySql += " from EC_" + clave_contrato + " ";
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
            MySql += " from EC_" + clave_contrato + " ";
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
            MySql = " select sum(cast(cargo as float))*.16 as iva_mes ";
            MySql += " from EC_" + clave_contrato + " ";
            MySql += " where concepto='HONORARIOS FIDUCIARIOS' ";
            MySql += " and fecha>='" + fecha_ini + " 00:00:00.000' ";
            MySql += " and fecha<='" + fecha_fin + " 23:59:59.000' ";
            rstSQLServer = statement.executeQuery(MySql);
            delimita = 0;
            while (rstSQLServer.next()) {
                delimita++;
                iva_mes = rstSQLServer.getDouble(1);
//                System.out.println("iva_mes=" + iva_mes);
            }
///////Comienza el calculo de liquidacion_mes
            MySql = " select concepto ";
            MySql += " from EC_" + clave_contrato + " ";
            MySql += " where fecha>='" + fecha_ini + " 00:00:00.000' ";
            MySql += " and fecha<='" + fecha_fin + " 23:59:59.000' ";
            rstSQLServer = statement.executeQuery(MySql);
            delimita = 0;
            while (rstSQLServer.next()) {
                no_movimientos++;
            }

            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }

            if (aportacion_mes != null && aportacion_mes >= 0) {
                datos.add(new BigDecimal(aportacion_mes).setScale(2, RoundingMode.HALF_UP).doubleValue());
            } else {
                datos.add(0D);
            }

            if (restitucion_mes != null) {
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

            saldo_final_mes = saldo_inicio_mes + datos.get(0) + datos.get(1) - datos.get(2) - datos.get(3) - datos.get(4);
            datos.add(new BigDecimal(saldo_final_mes).setScale(2, RoundingMode.HALF_UP).doubleValue());
            datos.add(no_movimientos.doubleValue());

        } catch (Exception e) {
            datos = null;
            System.out.println("ModeloEstadoCuenta:getDatos_EC" + e.toString());
        }
        return datos;
    }

    public static Double getSaldo_inicio_mes(String clave_contrato, String fecha_inicio) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        Double saldo_inicio_mes = -1.0D;
        boolean encontrado = false;

        String MySql = "";

        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
///////Comienza busqueda se saldo_inicio_mes
            MySql += " select saldo ";
//            MySql += " from saldo_corte_mes ";
//	    MySql += " where fideicomiso='" + clave_contrato +"' ";
            MySql += " from saldo_inicio_mes ";
            MySql += " where clave_contrato='" + clave_contrato + "' ";
//	    MySql += " where clave_contrato='APORTACION A PATRIMONIO' ";
            MySql += " and fecha='" + fecha_inicio + " 00:00:00.000' ";
//            MySql += " and fecha_inicio='"+ formatoFecha.format(calendario.getTime()) +" 00:00:00.000' ";
            ResultSet rstSQLServer = statement.executeQuery(MySql);
            while (rstSQLServer.next()) {
                encontrado = true;
                saldo_inicio_mes = rstSQLServer.getDouble(1);
                System.out.println("saldo_inicio_mes=" + saldo_inicio_mes);
            }
            if (!encontrado) {
//            System.out.println("Sin resultados");
                saldo_inicio_mes = 0D;
            }

        } catch (Exception e) {
//            datos = null;
            System.out.println("ModeloEstadoCuenta-getSaldo_inicio_mes:" + e.toString());
        }
        return saldo_inicio_mes;
    }

    public static String getEmailBody(Vector movimientos) {

        System.out.println("Obteniendo el cuerpo del correo para enviar archivo LayOut ...");
        String color = "#FFFFFF";
        String lstrBody = "";
        String lstrMensaje = "";
        Vector movimiento = null;
//        SimpleDateFormat formateador = new SimpleDateFormat(
//        "MMMM 'de' yyyy", new Locale("es_ES"));
        SimpleDateFormat periodoEdoCta = new SimpleDateFormat("MMMM 'de' yyyy", new Locale("es", "MX"));
        try {
            lstrMensaje = "Envio de archivo LayOut para generación de estados de cuenta mensual, ";
            if (lanzaproceso.LanzaProceso.corteLast) {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.MONTH, -1);
                lstrMensaje += "correspondiente al mes de " + periodoEdoCta.format(cal.getTime()) + ". <br><br><br>";
            }
            else{
            lstrMensaje += "correspondiente al mes de " + periodoEdoCta.format(new Date()) + ". <br><br><br>";            
            }
            movimiento = (Vector) movimientos.get(movimientos.size() - 1);
            lstrMensaje += "Resumen de generación de archivo LayOut: <br>";
            lstrMensaje += " Movimientos con errores: " + movimiento.get(0) + "<br>";
            lstrMensaje += " Movimientos correctos: " + movimiento.get(1) + "<br>";
            lstrMensaje += " Total de movimientos: " + movimiento.get(2) + "<br><br>";
            lstrMensaje += " Favor de verificar la información. <br>";

            lstrBody += "<html> <style type='text/css'><!--"
                    + " .Estilo1 {font-family: Arial;} "
                    + " .tablaTitulo{font-family:Arial;font-size:14px;background-color:#00CCFF;color:#00CCFF;}"
                    + " .tablaMenu{background-color:#00CCFF;color:#00CCFF;}"
                    + " .letraTabla{font-size:15px;font-family:Arial;}"
                    + " --></style>";

            lstrBody += "<head> ";
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
            if (movimientos.size() > 1) {
                lstrBody += "<tr> ";
                lstrBody += "<td colspan=\"2\"><p>&nbsp;</p> ";
                lstrBody += "<div align=\"center\"> ";
                lstrBody += "<table  table width=\"80%\" class='letraTabla'  align='center' border='1' bordercolordark='#B5D6FE' "
                        + " bordercolorlight='#528CEF'> ";
                lstrBody += " <tr style='color:#000000'> ";
                lstrBody += " <td bgcolor='#E3F2FE' nowrap ><center><strong>Fideicomiso</strong></center></td>";
                lstrBody += " <td bgcolor='#E3F2FE' nowrap ><center><strong>Descripción del error</strong></center></td>";
                lstrBody += " </tr> ";
                for (int i = 0; i < movimientos.size() - 1; i++) {
                    movimiento = (Vector) movimientos.get(i);
                    lstrBody += " <tr style='color:#000000'> ";
                    if (i % 2 == 0) {
                        color = "#FFFFFF";
                    } else {
                        color = "#E3F2FE";
                    }
                    lstrBody += " <td bgcolor='" + color + "' nowrap ><center>" + movimiento.get(0) + "</center></td>";
                    lstrBody += " <td bgcolor='" + color + "' nowrap ><center>" + movimiento.get(1) + "</center></td>";
                    lstrBody += " </tr> ";
                }
                lstrBody += "</table> ";
                lstrBody += "<p>&nbsp;</p> ";
                lstrBody += "</div> ";
                lstrBody += "</td> ";
                lstrBody += "</tr> ";
            }
            lstrMensaje = "Dudas y aclaraciones, favor de comunicarse a travez del correo: soporte@fideicomisogds.mx .";
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

    public static boolean setSaldo_inicio_mes(String clave_contrato, double saldo) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        boolean result = false;
        Calendar cal = Calendar.getInstance();
//        SimpleDateFormat formatoFecha = new SimpleDateFormat("YYYY-dd-MM ");
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd ");

        String MySql = "";

        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            //Si es ejecutado el mes siguiente, solo se pone al primer día de mes
            if (lanzaproceso.LanzaProceso.corteLast) {
                cal.set(Calendar.DAY_OF_MONTH, 1);
                //Si es ejecutado el mes corriente, se pone al primer día del mes siguiente
            } else {
                cal.set(Calendar.DAY_OF_MONTH, 1);
                cal.add(Calendar.MONTH, 1);
            }

            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " insert into saldo_inicio_mes(fecha,clave_contrato,saldo) ";
            MySql += " values ('" + formatoFecha.format(cal.getTime()).toString() + "00:00:00.000', ";
            MySql += "'" + clave_contrato + "', ";
            MySql += "" + saldo + ") ";
            statement.executeUpdate(MySql);
            result = true;

        } catch (Exception e) {
            result = false;
            System.out.println("ModeloEstadoCuenta-setSaldo_inicio_mes:" + e.toString());
        }
        return result;
    }

    public static boolean compruebaNuevoSaldoMes(String clave_contrato) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        int i = -1;
        boolean result = false;
        Calendar cal = Calendar.getInstance();
//        SimpleDateFormat formatoFecha = new SimpleDateFormat("YYYY-dd-MM ");
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd ");

        String MySql = "";

        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            //Si es ejecutado el mes siguiente, solo se pone al primer día de mes
            if (lanzaproceso.LanzaProceso.corteLast) {
                cal.set(Calendar.DAY_OF_MONTH, 1);
                //Si es ejecutado el mes corriente, se pone al primer día del mes siguiente
            } else {
                cal.set(Calendar.DAY_OF_MONTH, 1);
                cal.add(Calendar.MONTH, 1);
            }

            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select fecha from saldo_inicio_mes ";
            MySql += " where fecha='" + formatoFecha.format(cal.getTime()).toString() + "00:00:00.000' ";
            MySql += " and clave_contrato='" + clave_contrato + "' ";
            statement.executeQuery(MySql);
            ResultSet rstSQLServer = statement.executeQuery(MySql);
            i = 0;
            while (rstSQLServer.next()) {
                i++;
            }
            System.out.println("Se encontraron:" + i + " registros con fecha de inicio de mes.");
            if (i > 0) {
                connection = conn.ConectaSQLServer();
                statement = connection.createStatement();
                statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

                MySql = " delete from saldo_inicio_mes";
                MySql += " where fecha='" + formatoFecha.format(cal.getTime()).toString() + "00:00:00.000' ";
                MySql += " and clave_contrato='" + clave_contrato + "' ";
                statement.executeUpdate(MySql);
                System.out.println("Se eliminaron:" + i + " registros con fecha de inicio de mes.");
            }
            result = true;

        } catch (Exception e) {
            result = false;
            System.out.println("ModeloEstadoCuenta-setSaldo_inicio_mes:" + e.toString());
        }
        return result;
    }

    public static String getCorreoUsuariosFideicomiso(String clave_contrato) {
        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String correoTmp = "";
        String correo1 = "";
        String correo2 = "";
        String correos = "";
        int contador = 0;
        String MySql = "";
        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select distinct(contacto_usuario),usuario ";
            MySql += " from usuarios ";
            MySql += " where clave_contrato='" + clave_contrato.trim() + "' ";
            MySql += " and status='A' ";
            MySql += " order by usuario";

            ResultSet rstSQLServer = statement.executeQuery(MySql);
            while (rstSQLServer.next()) {
                contador++;
                correoTmp = rstSQLServer.getString(1).toString().trim();
                if (!correoTmp.equals("")) {
                    if (contador <= 3) {
                        if (contador == 1) {
                            correos = correoTmp;
                            correo1 = correoTmp;
                        }
                        if (contador == 2 && !correos.equals(correoTmp)) {
                            correos += "," + correoTmp;
                            correo2 = correoTmp;
                        }
                        if (contador == 3 && !correo1.equals(correoTmp) && !correo2.equals(correoTmp)) {
                            correos += "," + correoTmp;
                        }
                    } else {
                        break;
                    }
                }
            }
            rstSQLServer.close();
            statement.close();
            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            correos = "Error obteniendo correos";
            System.out.println("AuthorizationModel-getCorreoUsuario:" + e.toString());
        }
        return correos;//Esta es la original, se busca el correo en la BD y se genera la accion
    }

    public static double getSaldo(String fideicomiso) {
        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        double saldo = 0;
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
                saldo = rstSQLServer.getDouble("saldo");
//                DecimalFormat formato = new DecimalFormat("0.00");
//                saldo = formato.format(Double.parseDouble(saldo));
            }
            rstSQLServer.close();
            statement.close();
            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
//            saldo=0;
            System.out.println("AuthorizationModel-getSaldoFideicomitente:" + e.getMessage());
            if (connection != null) {
                conn.Desconecta(connection);
            }
        }
        return saldo;
    }

    public static boolean setSaldoInicioMes(String clave_contrato, String fecha_inicio, double saldo) {
        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String MySql = "";
        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

///////Comienza busqueda se saldo_inicio_mes
//            MySql += " insert into saldo_corte_mes(fecha_corte,fideicomiso,saldo) ";
            MySql += " insert into saldo_inicio_mes(fecha,clave_contrato,saldo) ";
            MySql += " values('" + fecha_inicio + " 00:00:00.000', ";
            MySql += " '" + clave_contrato + "', ";
            MySql += " " + saldo + ") ";
//            MySql += " from saldo_inicio_mes ";
            statement.executeUpdate(MySql);

        } catch (Exception e) {

            System.out.println("ModeloEstadoCuenta-getSaldoInicioMes:" + e.getMessage());
            if (connection != null) {
                conn.Desconecta(connection);
            }
            return false;
        }
        return true;
    }

    public static boolean setFechaCreacionEdoCta() {
        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String MySql = "";
        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

///////Se inserta fecha de generación de edo_cta y se pone en estado=EN_PROCESO
//            tabla: fecha_ini,fecha_fin,movs,estado
            MySql += " insert into registro_edo_cta(fecha_ini,fecha_fin,movimientos,estado) ";
            MySql += " values(GETDATE(), ";
            MySql += " '2001-01-01 00:00:00.000', ";
            MySql += " 0, ";
            MySql += " 'EN_PROCESO' ) ";
//            MySql += " from saldo_inicio_mes ";
            statement.executeUpdate(MySql);

        } catch (Exception e) {

            System.out.println("ModeloEstadoCuenta-setFechaCreacionEdoCta:" + e.getMessage());
            if (connection != null) {
                conn.Desconecta(connection);
            }
            return false;
        }
        return true;
    }

    public static boolean setFechaFinalizacionEdoCta(String estado) {
        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String MySql = "";
        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

///////Se inserta fecha de generación de edo_cta y se pone en estado=EN_PROCESO
//            tabla: fecha_ini,fecha_fin,movs,estado
//            MySql += " update registro_edo_cta(fecha_ini,fecha_fin,movs,estado) ";
            MySql += " update registro_edo_cta ";
            MySql += " set fecha_fin=GETDATE(), ";
            MySql += " estado='" + estado + "' ";
            MySql += " where fecha_fin='2001-01-01 00:00:00.000' ";
            MySql += " and estado='EN_PROCESO' ";
            statement.executeUpdate(MySql);

        } catch (Exception e) {

            System.out.println("ModeloEstadoCuenta-setFechaCreacionEdoCta:" + e.getMessage());
            if (connection != null) {
                conn.Desconecta(connection);
            }
            return false;
        }
        return true;
    }

    public static boolean setFechaActualizacionEdoCta(String estado) {
        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String MySql = "";
        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

///////Se inserta fecha de generación de edo_cta y se pone en estado=EN_PROCESO
//            tabla: fecha_ini,fecha_fin,movs,estado
            MySql += " update registro_edo_cta set ";
            MySql += " fecha_fin=GETDATE(), ";
            MySql += " estado='" + estado + "' ";
            MySql += " where  fecha_fin='2014-01-01 00:00:00.000' ";
            MySql += " and estado='EN_PROCESO' ";
            statement.executeUpdate(MySql);

        } catch (Exception e) {

            System.out.println("ModeloEstadoCuenta-setFechaCreacionEdoCta:" + e.getMessage());
            if (connection != null) {
                conn.Desconecta(connection);
            }
            return false;
        }
        return true;
    }

    public static Date getUltimoDiaDeMes() {
        Calendar calMax = Calendar.getInstance();
        calMax.set(calMax.get(Calendar.YEAR),
                calMax.get(Calendar.MONTH),
                calMax.getActualMaximum(Calendar.DAY_OF_MONTH),
                calMax.getMaximum(Calendar.HOUR_OF_DAY),
                calMax.getMaximum(Calendar.MINUTE),
                calMax.getMaximum(Calendar.SECOND));

        return calMax.getTime();
    }

    public static Date getUltimoDiaHabilDeMesAnterior() {
        Calendar calMax = Calendar.getInstance();
        calMax.add(Calendar.MONTH, -1);
        calMax.set(calMax.get(Calendar.YEAR),
                calMax.get(Calendar.MONTH),
                calMax.getActualMaximum(Calendar.DAY_OF_MONTH),
                calMax.getMaximum(Calendar.HOUR_OF_DAY),
                calMax.getMaximum(Calendar.MINUTE),
                calMax.getMaximum(Calendar.SECOND));

//        int dia_semana = calMax.get(Calendar.DAY_OF_WEEK);
//        if (dia_semana == 1) {
//            calMax.set(Calendar.DAY_OF_MONTH, calMax.get(Calendar.DAY_OF_MONTH) - 2);
//        }
//        if (dia_semana == 7) {
//            calMax.set(Calendar.DAY_OF_MONTH, calMax.get(Calendar.DAY_OF_MONTH) - 1);
//        }        

        return calMax.getTime();
    }
}
