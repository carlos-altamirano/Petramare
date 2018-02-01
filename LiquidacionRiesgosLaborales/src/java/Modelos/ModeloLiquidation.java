package Modelos;

import java.io.*;
import java.sql.*;

import Beans.Usuario;
import Beans.Movimiento;

import Common.clsFecha;
import Common.clsConexion;

import java.util.Map;
import java.util.Vector;
import java.util.HashMap;
import java.util.StringTokenizer;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.jasperreports.engine.*;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ModeloLiquidation {

    //Variables para almacenar la suma total del importe de los movimientos.
    public static BigDecimal sumM1 = BigDecimal.ZERO;
    public static BigDecimal sumM2 = BigDecimal.ZERO;
    public static BigDecimal sumM3 = BigDecimal.ZERO;
    public static BigDecimal sumM4 = BigDecimal.ZERO;
    //Variable para identificar si se realizaron transacciones del movimiento tipo 1.
    public static boolean hay_mov1 = false;
    //Variable para identificar si se realizaron transacciones del movimiento tipo 2.
    public static boolean hay_mov2 = false;
    //Variable para identificar si se realizaron transacciones del movimiento tipo 3.
    public static boolean hay_mov3 = false;
    //Variable para almacenar a el destinatario del cheque del fideicomisario.
    public static Vector envio_cheque_mov4 = new Vector();
    //Variable para almacenar los fideicomisarios.
    public static Vector fidei_mov5 = new Vector();
    //Variable que almacena las fechas de liquidación pendientes de un dado cliente.
    public static Vector fechas_liquidacion = new Vector();
    //Variable que indica si se actualizo el status satisfactoriamente.
    public boolean actualizaStatus = false;
    //Variable que almacena los usuarios que intentan ingresar al sistema.
    public static String usersIngApp = "";
    //Variable que almacena el número de movimientos de un lote.
    public static String total_movimientos = "";
    //Variable que indica si un usuario intento entrar mas de 3 veces al sistema
    public static boolean bloqueaUser = false;
    //Alnmacena el nombre de los fideicomitentes con mov tipo 5.
    public static Vector namesFidei = new Vector();
    public static String reportesGenerados = "";

    public static String getReportes_generados() {
        return reportesGenerados;
    }

    public static void setReportes_generados(String reportes_generados) {
        ModeloLiquidation.reportesGenerados = reportes_generados;
    }

    public static Vector get_NamesFidei() {
        return namesFidei;
    }

    public static boolean isBloqueaUser() {
        return bloqueaUser;
    }

    public static String getUserIngApp() {
        return usersIngApp;
    }

    public boolean get_ActualizaStatus() {
        return actualizaStatus;
    }

    public static BigDecimal getSumM1() {
        return sumM1;
    }

    public static BigDecimal getSumM2() {
        return sumM2;
    }

    public static BigDecimal getSumM3() {
        return sumM3;
    }

    public static BigDecimal getSumM4() {
        return sumM4;
    }

    public static boolean get_Hay_mov1() {
        return hay_mov1;
    }

    public static boolean get_Hay_mov2() {
        return hay_mov2;
    }

    public static boolean get_Hay_mov3() {
        return hay_mov3;
    }

    public static Vector getEnvio_cheque_mov4() {
        return envio_cheque_mov4;
    }

    public static Vector getFidei_mov5() {
        return fidei_mov5;
    }

    public static void setFidei_mov5(Vector fidei_mov5) {
        ModeloLiquidation.fidei_mov5 = fidei_mov5;
    }

    /**
     * Método para autenficar al usuario que intenta ingresar al sistema.
     *
     * @param String usuario: Usuario
     * @param String password: Contraseña del usuario
     * @return boolean auth: true / false
     */
    public static Usuario validaUsuario(String usuario, String password) {

        usuario = usuario.toString().trim();
        password = password.toString().trim();
        Usuario userApp = null;
        String userIngApp = "";
        String users[] = null;
        String userTmp = "";
        bloqueaUser = false;
        String MySql = "";
        int k = 0;

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;

        try {
            //Hacemos la conexión a la BD.
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select clave_usuario , usuario , password , nombre_usuario ";
            MySql += " from usuarios_admin ";
            MySql += " where usuario ='" + usuario + "' ";
            MySql += " and password='" + password + "' ";
            MySql += " and status = 'A'";

            //System.out.println("validaUsuario"+MySql);
            ResultSet rstSQLServer = statement.executeQuery(MySql);

            if (rstSQLServer.next()) {
                userApp = new Usuario();

                String tmp = rstSQLServer.getString(1).toString().trim(); // Clave de usuario
                userApp.setClave_usuario(tmp);

                tmp = rstSQLServer.getString(2).toString().trim(); // Usuario de la SOFOM
                userApp.setUsuario(tmp);

                tmp = rstSQLServer.getString(3).toString().trim(); //Password del Usuario
                userApp.setPassword(tmp);

                tmp = rstSQLServer.getString(4).toString().trim(); // Nombre del Usuario
                userApp.setNombre_usuario(tmp);

                userApp.setAutentificado(true);

                if (!usersIngApp.equals("")) {
                    users = usersIngApp.split(";");
                    for (int i = 0; i < users.length; i++) {
                        userIngApp = users[i];
                        k = userIngApp.indexOf(":");
                        tmp = userIngApp.substring(0, k);
                        if (tmp.equals(usuario)) {
                            userTmp = userTmp + usuario + ":" + 1 + ";";
                        } else {
                            userTmp = userTmp + userIngApp + ";";
                        }
                    }//Fin del for
                    usersIngApp = userTmp;
                }

            } else { //Inicio del else

                userApp = null;
                String tmp = "";
                int val = 0;

                //Segunda consulta
                MySql = " select usuario";
                MySql += " from usuarios_admin ";
                MySql += " where usuario ='" + usuario + "' ";
                MySql += " and status = 'A'";
                rstSQLServer = statement.executeQuery(MySql);

                if (rstSQLServer.next()) {
                    if (usersIngApp.equals("")) {
                        usersIngApp = usuario + ":" + "1" + ";";
                    } else {
                        users = usersIngApp.split(";");
                        for (int i = 0; i < users.length; i++) {
                            userIngApp = users[i];
                            k = userIngApp.indexOf(":");
                            tmp = userIngApp.substring(0, k);
                            //Verificamos si el usuario que intenta ingresar se encuantra en la
                            //lista de los usuarios que han ingresado al sistema.
                            if (tmp.equals(usuario)) {
                                tmp = userIngApp.substring(k + 1, userIngApp.length());
                                val = Integer.parseInt(tmp);
                                //Si el numero de intentos es mayor a 0 y menor a 4 entonces
                                //incrementamos el número de intentos.
                                if (val > 0 && val < 3) {
                                    val++;
                                    userTmp = userTmp + usuario + ":" + val + ";";
                                    //en otro caso (intentos mayor a 3) mandamos una señal para que el
                                    //usuario sea bloqueado.
                                } else {
                                    bloqueaUser = true;
                                }
                            } else {
                                userTmp = userTmp + userIngApp + ";";
                            }
                        }//Fin del for
                        if (val > 0) {
                            usersIngApp = userTmp;
                        } else {
                            usersIngApp = usersIngApp + usuario + ":" + "1" + ";";
                        }
                    }//Fin del segundo else
                } //Fin de procesar el resultado de la consulta
            }//Fin del primer else
            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            bloqueaUser = false;
            if (connection != null) {
                conn.Desconecta(connection);
            }
            System.out.println("validaUsuario:" + e.getMessage());
        }
        return userApp;
    }

    /**
     * Método que proporciona el conjunto de fideicomitentes que corresponden a
     * los datos que se pasan como parámetro.
     *
     * @param clave_contrato: Clave de fideicomiso.
     * @param fecha_liquidacion: Fecha de liquidación.
     * @param tipo_movimiento: Tipo de movimiento.
     * @param nombre_archivo: Nombre del Lote.
     * @param status: Status del LayOut.
     * @return Vector: Nobre de fideicomitentes, en caso de ocurrir algún error
     * regresa null.
     */
    //YA se utilizo formato de fecha BD y se paso por parametro
    public static Vector getNombreFideicomitentes(String clave_contrato, String fecha_liquidacion, String tipo_movimiento, String nombre_archivo, String status) {

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

            MySql = " select distinct(contratos.nombre_cliente) ";
            MySql += " from contratos contratos, movimientos_h h, movimientos l ";
            MySql += " where h.clave_contrato = l.clave_contrato ";
            MySql += " and h.fecha_liquidacion = l.fecha_liquidacion ";
            MySql += " and h.nombre_archivo = l.nombre_archivo ";
            MySql += " and l.clave_contrato = contratos.clave_contrato ";
            MySql += " and contratos.status = 'A'  ";
            if (!clave_contrato.equals("")) {
                MySql += " and h.clave_contrato = '" + clave_contrato + "'  ";
            }
            if (!fecha_liquidacion.equals("")) {
                MySql += " and h.fecha_liquidacion = '" + fecha_liquidacion + " 00:00:00.000'  ";
            }
            if (!nombre_archivo.equals("")) {
                MySql += " and h.nombre_archivo = '" + nombre_archivo + "'  ";
            }
            if (!status.equals("")) {
                MySql += " and h.status = '" + status + "'  ";
            }
            if (!tipo_movimiento.equals("")) {
                MySql += " and l.tipo_movimiento in (" + tipo_movimiento + ") ";
            }
            MySql += " order by contratos.nombre_cliente asc ";
//            System.out.println("getNombreFideicomitentes:" + MySql);
            clientes.add("  ---------------Selecciona--------------  ");
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
            if (connection != null) {
                conn.Desconecta(connection);
            }
            System.out.println("getNombreFideicomitentes:" + e.getMessage());
        }
        return clientes;
    }

    /**
     * Método que regresa la suma total a liquidar correspondiente al tipo de
     * movimiento que se pasa como parámetro y los datos del fideicomitente que
     * se especifican.
     *
     * @param clave_contrato: Clave de fideicomiso.
     * @param fecha_liquidacion: Fecha de liquidación.
     * @param tipo_movimiento: Tipo de movimiento.
     * @param nombre_archivo: Nombre del Lote.
     * @param status: Status de movimientos.
     * @return String: Mensaje descriptivo de la transacción, si todo va bien
     * regresa una cadena con el importe total de los movimientos de este lote
     * que corresponden al tipo de movimiento especificado; si ocurre un error
     * regresa null y en cualquier otro caso regresa una cadena vacia.
     */
    public static String getTotalImporteLiquidacion(String clave_contrato, String fecha_liquidacion, String tipo_movimiento, String nombre_archivo, String status) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;

        String importe = "";
        String MySql = "";

        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select sum(cast(l.importe_liquidacion as float)) ";
            MySql += " from contratos contratos, movimientos_h h, movimientos l ";
            MySql += " where h.clave_contrato = l.clave_contrato ";
            MySql += " and l.clave_contrato = contratos.clave_contrato ";
            MySql += " and h.fecha_liquidacion = l.fecha_liquidacion ";
            MySql += " and h.nombre_archivo = l.nombre_archivo ";
            MySql += " and contratos.status = 'A'  ";

            if (!clave_contrato.equals("")) {
                MySql += " and h.clave_contrato = '" + clave_contrato + "'  ";
            }
            if (!fecha_liquidacion.equals("")) {
                MySql += " and h.fecha_liquidacion = '" + fecha_liquidacion + " 00:00:00.000'  ";
            }
            if (!nombre_archivo.equals("")) {
                MySql += " and h.nombre_archivo = '" + nombre_archivo + "'  ";
            }
            if (!status.equals("")) {
                MySql += " and h.status = '" + status + "'  ";
            }
            if (!tipo_movimiento.equals("")) {
                MySql += " and l.tipo_movimiento in (" + tipo_movimiento + ") ";

            }
//            System.out.println("getTotalImporteLiquidacion:" + MySql);
            ResultSet rstSQLServer = statement.executeQuery(MySql);

            if (rstSQLServer.next()) {
                importe = rstSQLServer.getString(1);
            }
            rstSQLServer.close();
            statement.close();
            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            importe = "";
            System.out.println("getTotalImporteLiquidacion:" + e.getMessage());
            if (connection != null) {
                conn.Desconecta(connection);
            }
        }
        return importe;
    }

    /**
     * Método que proporciona el nombre de los Lay-Out's cargados al sistema del
     * cliente y fecha de liquidación que se le pasa como parámetro.
     *
     * @param clave_contrato : Clave de contrato asociada a un cliente.
     * @param fecha_liquidacion: fecha de liquidación a verificar.
     * @param status: Status del Lay-Out.
     * @return Vector clientes: Nombre de Lay-Out's cargados.
     */
    public static Vector getFilesName(String clave_contrato, String fecha_liquidacion, String status) {
        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        Vector files = new Vector();
        String MySql = "";
        String arch = "";

        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select distinct(nombre_archivo) ";
            MySql += " from movimientos_h";
            MySql += " where clave_contrato = '" + clave_contrato + "' ";
            MySql += " and fecha_liquidacion = '" + (new clsFecha().CambiaFormatoFecha("dd/MM/yyyy", "yyyy-MM-dd", fecha_liquidacion)) + " 00:00:00.000' ";
            MySql += " and status = '" + status + "' ";
//            System.out.println(MySql);
            files.add("  -Seleccione-  ");
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
        } catch (Exception e) {
            files = null;
            if (connection != null) {
                conn.Desconecta(connection);
            }
            System.out.println("getFilesName:" + e.getMessage());
        }
        return files;
    }

    /**
     * Método que regresa la información general del cliente que se le pasa como
     * parámetro, asi como el conjunto de fechas de liquidación con movimientos
     * pendientes de dicho cliente.
     *
     * @param String clave_contrato : Clave de fideicomiso.
     * @return Vector datos: Conjunto de datos del cliente.
     */
    public static Vector getDatosCliente(String clave_contrato) {
        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;

        clsFecha fetch = new clsFecha();
        fechas_liquidacion = new Vector();
        Vector datos = new Vector();

        String fecha = "";
        String MySql = "";
        String dato = "";
        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            //Obtenemos la información general del cliente.
            MySql = " select c.nombre_cliente, c.domicilio_fiscal , c.RFC , c.grupo , m.clave_cliente ";
            MySql += " from contratos c, movimientos_h m ";
            MySql += " where c.clave_contrato= m.clave_contrato ";
            MySql += " and c.clave_contrato ='" + clave_contrato + "' ";
            MySql += " and c.status='A' ";
            MySql += " and m.status='A' ";
//            System.out.println("MySql:" + MySql);

            ResultSet rstSQLServer = statement.executeQuery(MySql);

            if (rstSQLServer.next()) {
                dato = rstSQLServer.getString(1); //Nombre del fideicomitente
                datos.add(dato);
                dato = rstSQLServer.getString(2); //Domicilio fiscal del fideicomitente
                datos.add(dato);
                dato = rstSQLServer.getString(3); //RFC del fideicomitent
                datos.add(dato);
                dato = rstSQLServer.getString(4); // Tipo de Contrato
                datos.add(dato);
                dato = rstSQLServer.getString(5); // Clave del cliente
                datos.add(dato);
            }
            if (datos.size() > 0) {
                //Obtenemos las fechas de liquidación con movimientos pendientes de este cliente
                MySql = " select  distinct(LEFT(CONVERT(VARCHAR, m.fecha_liquidacion, 120), 10)) ";
                MySql += " from contratos c, movimientos_h m  ";
                MySql += " where c.clave_contrato = m.clave_contrato ";
                MySql += " and c.clave_contrato = '" + clave_contrato + "' ";
                MySql += " and c.status = 'A'  ";
                MySql += " and m.status ='A'  ";

/////////////////////////////////////////
                rstSQLServer = statement.executeQuery(MySql);

                fechas_liquidacion.add("-Seleccione-");
                fetch.setFormato("dd/MM/yyyy");
                while (rstSQLServer.next()) {
                    //Damos formato a la fecha obtenida
                    fecha = rstSQLServer.getString(1);
                    fecha = fetch.CambiaFormatoFecha("yyyy-MM-dd", "dd/MM/yyyy", fecha);
                    fechas_liquidacion.add(fecha);
                }
////////////////////////////////////////

            }
            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            datos = null;
            fechas_liquidacion = null;
            if (connection != null) {
                conn.Desconecta(connection);
            }
            System.out.println("getDatosCliente:" + e.getMessage());
        }
        return datos;
    }

    /**
     * Método que regresa la fecha en la que el cliente realizo la captura del
     * LayOut.
     *
     * @param clave_contrato : Clave de contrato del cliente correspondiente a
     * la clave anterior.
     * @param fechaLiq : Fecha de Liquidación.
     * @return String Fecha: Fecha y Hora de Captura.
     */
    public static String getFechaCaptura(String clave_contrato, String fechaLiq, String nombre_archivo, String status) {
        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String hora = "";
        String MySql = "";
        total_movimientos = "";
        int idx = 0;
        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select fecha_captura ";
            MySql += " from movimientos_h  ";
            MySql += " where clave_contrato = '" + clave_contrato + "' ";
            MySql += " and fecha_liquidacion = '" + (new clsFecha().CambiaFormatoFecha("dd/MM/yyyy", "yyyy-MM-dd", fechaLiq)) + " 00:00:00.000'";
            MySql += " and nombre_archivo = '" + nombre_archivo + "'";
            MySql += " and status = '" + status + "'";
//            System.out.println("getFechaCaptura:" + MySql);
            ResultSet rstSQLServer = statement.executeQuery(MySql);
            if (rstSQLServer.next()) {
                hora = rstSQLServer.getString(1);
                idx = hora.indexOf(".");
                if (idx > 0) {
                    hora = hora.substring(0, idx);
                }
            }
            if (!hora.equals("")) {
                MySql = " select count(l.clave_contrato)";
                MySql += " from movimientos_h h, movimientos l ";
                MySql += " where h.clave_contrato = l.clave_contrato ";
                MySql += " and h.fecha_liquidacion = l.fecha_liquidacion ";
                MySql += " and h.nombre_archivo = l.nombre_archivo ";
                MySql += " and h.clave_contrato = '" + clave_contrato + "' ";
                MySql += " and h.fecha_liquidacion = '" + (new clsFecha().CambiaFormatoFecha("dd/MM/yyyy", "yyyy-MM-dd", fechaLiq)) + " 00:00:00.000'";
                MySql += " and h.nombre_archivo = '" + nombre_archivo + "'";
                MySql += " and h.status = '" + status + "'";
//                System.out.println("getFechaCaptura:" + MySql);
                rstSQLServer = statement.executeQuery(MySql);
                if (rstSQLServer.next()) {
                    idx = rstSQLServer.getInt(1);
                    //total_movimientos = rstSQLServer.getString(1).toString().trim();
                }
                MySql = " select count(clave_contrato)";
                MySql += " from movimientos_cancelados ";
                MySql += " where clave_contrato = '" + clave_contrato + "' ";
                MySql += " and fecha_liquidacion = '" + (new clsFecha().CambiaFormatoFecha("dd/MM/yyyy", "yyyy-MM-dd", fechaLiq)) + " 00:00:00.000'";
                MySql += " and nombre_archivo = '" + nombre_archivo + "'";
                MySql += " and status = 'A'";
//                System.out.println("Movimientos:" + MySql);
                rstSQLServer = statement.executeQuery(MySql);
                if (rstSQLServer.next()) {
                    idx = idx - rstSQLServer.getInt(1);
                }
                total_movimientos = idx + "";
            }
            rstSQLServer.close();
            statement.close();
            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            System.out.println("ModeloLiquidation-getFechaCaptura:" + e.getMessage());
            hora = null;
            total_movimientos = "";
            if (connection != null) {
                conn.Desconecta(connection);
            }
        }
        return hora;
    }

    /**
     * Método que regresa la fecha en la que el usuario del a SOFOM genero las
     * transacciones correspondientes a los datos que se pasan como parámetro.
     *
     * @param clave_contrato : Clave de contrato del cliente.
     * @param fechaLiq : Fecha de Liquidación.
     * @param nombre_archivo Nombre del Lay-Out
     * @return String Fecha: Fecha y Hora de transacciones.
     */
    public static String getFechaTransacciones(String clave_contrato, String fechaLiq, String nombre_archivo) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        namesFidei = new Vector();
        String nameFidei = "";
        String MySql = "";
        String hora = "";
        int idx = 0;

        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select fecha_usuario_opera ";
            MySql += " from movimientos_h  ";
            MySql += " where clave_contrato = '" + clave_contrato + "' ";
            MySql += " and fecha_liquidacion = '" + fechaLiq + " 00:00:00.000'";
            MySql += " and nombre_archivo = '" + nombre_archivo + "'";
            MySql += " and status = 'P'";

//            System.out.println("Fecha Captura:" + MySql);
            ResultSet rstSQLServer = statement.executeQuery(MySql);
            if (rstSQLServer.next()) {
                hora = rstSQLServer.getString(1);
                idx = hora.indexOf(".");
                if (idx > 0) {
                    hora = hora.substring(0, idx);
                }
            }

            MySql = " select nombre_fidei_banco_ext ";
            MySql += " from movimientos_h h, movimientos l  ";
            MySql += " where h.clave_contrato = l.clave_contrato  ";
            MySql += " and h.fecha_liquidacion = l.fecha_liquidacion  ";
            MySql += " and h.nombre_archivo = l.nombre_archivo  ";
            MySql += " and l.tipo_movimiento = 5 ";
            MySql += " and h.clave_contrato = '" + clave_contrato + "' ";
            MySql += " and h.fecha_liquidacion = '" + fechaLiq + " 00:00:00.000'";
            MySql += " and h.nombre_archivo = '" + nombre_archivo + "'";
            MySql += " and h.status = 'P'";

            //System.out.println("Fideicomitentes:" + MySql);
            namesFidei.add(" ---------- Selecciona ---------- ");

            rstSQLServer = statement.executeQuery(MySql);

            while (rstSQLServer.next()) {
                nameFidei = rstSQLServer.getString(1);
                namesFidei.add(nameFidei);
            }

            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            hora = "";
            namesFidei = null;
            if (connection != null) {
                conn.Desconecta(connection);
            }
            System.out.println("getFechaCaptura:" + e.toString());
        }
        return hora;
    }

    /**
     * Método que regresa el formato DDMMAA a la fecha que se le pasa como
     * parámetro, asumiendo que la fecha que se le pasa tiene el formato:
     * dd/MM/yyyy
     *
     * @param String fech : Fecha a cambiar de formato.
     * @return String fecha: Fecha con formato DDMMAA.
     */
    public static String getFormatoFecha(String fech) {
        String fecha = "";
        try {
            String dia = fech.substring(0, 2);
            String mes = fech.substring(3, 5);
            String anio = fech.substring(8, fech.length());
            fecha = anio + mes + dia;
        } catch (Exception e) {
            fecha = "";
        }
        return fecha;
    }

    /**
     * Método que regresa el formato DDAGOAA a la fecha que se le pasa como
     * parámetro, asumiendo que la fecha que se le pasa tiene el formato:
     * dd/MM/yyyy
     *
     * @param String fech : Fecha a cambiar de formato.
     * @return String fecha: Fecha con formato DDMMAA.
     */
    public static String getFormatoMesFecha(String fech) {
        String fecha = "";
        clsFecha date = new clsFecha();
        try {
            String dia = fech.substring(0, 2);
            String mes = fech.substring(3, 5);
            String anio = fech.substring(fech.length() - 2, fech.length());
            mes = date.mes(mes);
            fecha = anio + mes + dia;
        } catch (Exception e) {
            fecha = "";
            System.out.println("getFormatoMesFecha:" + e.getMessage());
        }
        return fecha;
    }

    /**
     * Metodo para dar formato al importe que se muestra al cliente.
     *
     * @param String importe: Cantidad a dar formato.
     * @return String formato: importe con formato.
     */
    public static String formatoImporte(String importe) {

        importe = importe.toString().trim();
        String pesos = "", centavos = "", formato = "", tmp = "";
        int idx = 0, mult = 0, mod = 0;

        try {
            if (importe.contains(".")) {
                idx = importe.indexOf(".");
                pesos = importe.substring(0, idx);
                centavos = importe.substring(idx, importe.length());
                if (centavos.length() > 3) {
                    centavos = centavos.substring(0, 3);
                } else if (centavos.length() == 2) {
                    centavos = centavos + "0";
                }
                if (pesos.length() > 3) {
                    mult = pesos.length() / 3;
                    mod = pesos.length() % 3;

                    tmp = pesos.substring(0, mod);
                    pesos = pesos.substring(mod, pesos.length());
                    if (mod > 0) {
                        while (mult > 0) {
                            formato = "," + pesos.substring(pesos.length() - 3, pesos.length()) + formato;
                            pesos = pesos.substring(0, pesos.length() - 3);
                            mult--;
                        }
                        formato = tmp + formato + centavos;
                    } else {
                        while (mult > 0) {
                            if (mult == 1) {
                                formato = pesos.substring(pesos.length() - 3, pesos.length()) + formato;
                                pesos = pesos.substring(0, pesos.length() - 3);
                            } else {
                                formato = "," + pesos.substring(pesos.length() - 3, pesos.length()) + formato;
                                pesos = pesos.substring(0, pesos.length() - 3);
                            }
                            mult--;
                        }
                        formato = tmp + formato + centavos;
                    }
                } else {
                    formato = pesos + centavos;
                }
            } else if (importe.length() > 3) {

                mult = importe.length() / 3;
                mod = importe.length() % 3;

                tmp = importe.substring(0, mod);
                pesos = importe.substring(mod, importe.length());

                if (mod > 0) {
                    while (mult > 0) {
                        formato = "," + pesos.substring(pesos.length() - 3, pesos.length()) + formato;
                        pesos = pesos.substring(0, pesos.length() - 3);
                        mult--;
                    }
                    formato = tmp + formato;
                } else {
                    while (mult > 0) {
                        if (mult == 1) {
                            formato = pesos.substring(pesos.length() - 3, pesos.length()) + formato;
                            pesos = pesos.substring(0, pesos.length() - 3);
                        } else {
                            formato = "," + pesos.substring(pesos.length() - 3, pesos.length()) + formato;
                            pesos = pesos.substring(0, pesos.length() - 3);
                        }
                        mult--;
                    }
                    formato = tmp + formato;
                }
            } else {
                formato = importe;
            }
        } catch (Exception e) {
            System.out.println("formatoImporte:" + e.getMessage());
        }
        return formato;
    }

    /**
     * Método que verifica si el lote que se especifica a pasado de un estado a
     * otro.
     *
     * @param clave_contrato : Clave de contrato del cliente correspondiente al
     * lote.
     * @param fechaLiq : Fecha de Liquidación de lote.
     * @param nombre_archivo : Nombre del Lay-Out cargado.
     * @param status : Status de la transacción.
     * @return String valida: Cadena vacia si no han sido creados, cadena con un
     * mensaje si ya fueron creados los reportes o si ocurrio algún error.
     */
    public static String verificaActualizacion(String clave_contrato, String fechaLiq, String nombre_archivo, String status) {
        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String valida = "";
        String MySql = "", estado = "";
        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            MySql = " select status ";
            MySql += " from movimientos_h  ";
            MySql += " where clave_contrato = '" + clave_contrato + "' ";
            MySql += " and fecha_liquidacion = '" + (new clsFecha().CambiaFormatoFecha("dd/MM/yyyy", "yyyy-MM-dd", fechaLiq)) + " 00:00:00.000' ";
            MySql += " and nombre_archivo = '" + nombre_archivo + "' ";
//            System.out.println("verificaActualizacion:" + MySql);
            ResultSet rstSQLServer = statement.executeQuery(MySql);
            if (rstSQLServer.next()) {
                estado = rstSQLServer.getString(1).toString().trim();
                if (!estado.equals(status)) {
                    valida = " Los reportes han sido generados por otro usuario. ";
                }
            } else {
                valida = "Lote no registrado en la base de datos";
            }
            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            valida = " Error verificando el status del lote ";
            if (connection != null) {
                conn.Desconecta(connection);
            }
            System.out.println("verificaActualizacion:" + e.toString());
        }
        return valida;
    }

    /**
     * Método que regresa todos los movimientos del Lay-Out por procesar.
     * Movimientos del cliente , contrato y fecha de liquidación que se le pasan
     * como parámetro. Cada movimiento arrojado cuenta con el formato
     * correspondiente.
     *
     * @param cliente : Nombre del cliente.
     * @param clave_contrato : clave del contrato del cliente anterior.
     * @param mifecha : Fecha de Liquidación .
     * @param nombre_archivo : Nombre del Lay-Out al que corresponden los
     * movimientos.
     * @return Vector movimientos: Conjunto de movimientos con formato de
     * NetCash. Si surge algún error en la ejecución se regresa null, si no se
     * cuenta con movimientos de este tipo entonces se regresa un vector vacio y
     * si hay movimientos de este tipo entonces se regresa un vector de Strings
     * con un movimiento por cada nodo.
     */
    public static Vector getMovsSvcBBVA(String cliente, String clave_contrato, String miFecha, String nombre_archivo) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        Vector reporte = new Vector();
        Vector reporteFinal = new Vector();
        int limita_NoMovimientos = 1;

        // Campos de Lay-Out
        String tipo_movimiento = "";
        String cuenta_destino = "";
        String clave_moneda = "";
        String importe = "";
        String nombreFidei = "";
        String clave_fidei = "";
        String fecha_liquidacion = "";
        String clave_banco = "";
        String linea = "";
        String LO_tipo_cuenta = "";

        // Consultas
        String MySql = "";

        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select l.tipo_movimiento, l.cuenta_deposito, l.tipo_moneda, l.importe_liquidacion, l.nombre_empleado, l.apellidoP_empleado, l.apellidoM_empleado,";
            MySql += " l.clave_contrato, l.clave_banco, l.fecha_liquidacion";
            MySql += " from movimientos_h h , movimientos l , contratos contratos , cuentas_banco cuentas ";
            MySql += " where h.clave_contrato = l.clave_contrato ";
            MySql += " and l.clave_contrato = contratos.clave_contrato ";
            MySql += " and h.fecha_liquidacion = l.fecha_liquidacion ";
            MySql += " and h.nombre_archivo=l.nombre_archivo ";
            MySql += " and contratos.cuenta_origen = cuentas.cuenta_origen ";
            MySql += " and contratos.clave_contrato = '" + clave_contrato + "'";
            MySql += " and l.fecha_liquidacion = '" + (new clsFecha().CambiaFormatoFecha("dd/MM/yyyy", "yyyy-MM-dd", miFecha)) + " 00:00:00.000" + "'";
            MySql += " and contratos.nombre_cliente = '" + cliente + "'";
            MySql += " and h.nombre_archivo = '" + nombre_archivo + "'";
            MySql += " and contratos.status = 'A'";
            MySql += " and cuentas.status = 'A'";
            MySql += " and h.status = 'A'";
            MySql += " and l.tipo_movimiento = '2' ";
            MySql += " order by l.nombre_empleado asc ";

            ResultSet rstSQLServer = statement.executeQuery(MySql);

            while (rstSQLServer.next() && limita_NoMovimientos <= 100) { //Inicio de Transacciones

                // Se obtienen campos de Lay-Out de Netcash
                tipo_movimiento = rstSQLServer.getString("tipo_movimiento").trim(); // Tipo de movimiento
                cuenta_destino = rstSQLServer.getString("cuenta_deposito").trim(); // Cuenta destino
                clave_moneda = rstSQLServer.getString("tipo_moneda").trim(); // Moneda
                importe = rstSQLServer.getString("importe_liquidacion").trim(); // Importe de liquidaciÃ³n
                nombreFidei = rstSQLServer.getString("nombre_empleado").trim() + " "; //Nombre del Empleado
                nombreFidei += rstSQLServer.getString("apellidoP_empleado").trim() + " "; //Apellido Paterno del Empleado
                nombreFidei += rstSQLServer.getString("apellidoM_empleado").trim(); //Apellido Materno del Empleado
                clave_fidei = rstSQLServer.getString("clave_contrato").trim(); // Clave de contrato
                clave_banco = rstSQLServer.getString("clave_banco").trim(); // Clave de banco
                fecha_liquidacion = rstSQLServer.getString("fecha_liquidacion").trim(); // Fecha LiquidaciÃ³n

//                /* TIPO DE MONEDA */
//                String LO_tipo_moneda = clave_moneda;
//                // Validamos tipo de moneda
//                if (clave_moneda.length() != 3) {
//                    LO_tipo_moneda = "MXP";
//                } else if (!clave_moneda.equals("MXP")) {
//                    LO_tipo_moneda = "MXP";
//                }
                // ElaboraciÃ³n de formato de campos requeridos para Lay-Out
                String LO_contador = "";
                LO_contador = new DecimalFormat("000").format(limita_NoMovimientos);

                /* CUENTA DEPÃ“SITO */
                String LO_cuenta_deposito = cuenta_destino;
                if (cuenta_destino.length() != 18) {
                    LO_cuenta_deposito = "";
                }

                /* IMPORTE DE LIQUIDACIÃ“N */
                String LO_importe_liquidacion = importe;
                String imp_ceros = "";
                String decimales = "";
                String importe_entero = "";
                int imp_num_ceros = 0;
                int idx = 0;

                idx = importe.indexOf(".");
                //El importe debe de contener punto decimal
                if (idx > 0 && (idx + 3) == importe.length()) {
                    decimales = importe.substring(idx + 1, idx + 3);
                    importe_entero = importe.substring(0, idx);
                    // Obtenemos longitud de importe de liquidaciÃ³n
                    if (importe_entero.length() < 10) {
                        imp_num_ceros = 10 - importe_entero.length();
                        // Abarcamos con 0's las posiciones faltantes
                        for (int i = 0; i < imp_num_ceros; i++) {
                            imp_ceros += "0";
                        }
                        LO_importe_liquidacion = imp_ceros + importe_entero + decimales;
                    }
                } else {
                    LO_importe_liquidacion = "";
                }

                /* NOMBRE DEL BENEFICIARIO */
                String LO_nombre_beneficiario = nombreFidei;
                String nombre_espacios = "";
                int nombre_num_espacios = 0;
                // Obtenemos longitud del nombre de liquidaciÃ³n
                if (nombreFidei.length() < 40) {
                    nombre_num_espacios = 40 - nombreFidei.length();
                    for (int i = 0; i < nombre_num_espacios; i++) {
                        nombre_espacios += " ";
                    }
                    LO_nombre_beneficiario = nombreFidei + nombre_espacios;
                } else if (nombreFidei.length() > 40) {
                    LO_nombre_beneficiario = nombreFidei.substring(0, 40);
                } else {
                    LO_nombre_beneficiario = nombreFidei;
                }

                //Referencia 
                String LO_referencia = clave_contrato + "                           ";
                //Se requiere comprobante fiscal 
                String LO_req_comprobante = "N";
                //Si se requiere comprobante, se ingresa el IVA
                String LO_iva = "000000000000";
                //Si se requiere comprobante, se ingresa el RFC.
                String LO_rfc = "             ";

                // Validamos datos y creamos lÃ­neas de Lay-Out
                if (!LO_contador.isEmpty() && !LO_cuenta_deposito.isEmpty() && !LO_importe_liquidacion.isEmpty()) {

                    // Formamos lÃ­nea de movimiento
                    linea = LO_contador + LO_cuenta_deposito + LO_importe_liquidacion + LO_nombre_beneficiario
                            + LO_referencia + LO_req_comprobante + LO_iva + LO_rfc;
                    reporte.add(linea);
                } else {
                    reporte = null;
                    reporteFinal = null;
                    break;
                }
                if (limita_NoMovimientos < 100) {
                    limita_NoMovimientos++;
                } else {
                    limita_NoMovimientos = 1;
                    reporteFinal.add(reporte);
                    reporte = new Vector();
                }

            }//Fin de Transacciones

            //Verificamos si se realizo alguna transacciÃ³n
            if (reporte.size() > 0) {
                reporteFinal.add(reporte);
                System.out.println("Movs BBVA generados correctamente");
            }
            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }

        } catch (Exception e) {
            reporteFinal = null;
            if (connection != null) {
                conn.Desconecta(connection);
            }
            System.out.println("getAllMoves" + e.getMessage());
//            System.out.println("getAllMoves" + e);
        }
        return reporteFinal;
    }

    /**
     * Método que regresa todos los movimientos del Lay-Out por procesar.
     * Movimientos del cliente , contrato y fecha de liquidación que se le pasan
     * como parámetro. Cada movimiento arrojado cuenta con el formato
     * correspondiente.
     *
     * @param cliente : Nombre del cliente.
     * @param clave_contrato : clave del contrato del cliente anterior.
     * @param mifecha : Fecha de Liquidación .
     * @param nombre_archivo : Nombre del Lay-Out al que corresponden los
     * movimientos.
     * @return Vector movimientos: Conjunto de movimientos con formato de
     * NetCash. Si surge algún error en la ejecución se regresa null, si no se
     * cuenta con movimientos de este tipo entonces se regresa un vector vacio y
     * si hay movimientos de este tipo entonces se regresa un vector de Strings
     * con un movimiento por cada nodo.
     */
    public static Vector getAllMoves(String cliente, String clave_contrato, String miFecha, String nombre_archivo) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        Vector reporte = new Vector();
        Vector reporteFinal = new Vector();
        int limita_NoMovimientos = 1;

        // Campos de Lay-Out
        String tipo_movimiento = "";
        String cuenta_origen = "";
        String cuenta_destino = "";
        String clave_moneda = "";
        String importe = "";
        String nombreFidei = "";
        String clave_fidei = "";
        String fecha_liquidacion = "";
        String clave_banco = "";
        String linea = "";
        String LO_tipo_cuenta = "";

        // Consultas
        String MySql = "";

        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select l.tipo_movimiento, l.cuenta_deposito, cuentas.num_cuenta , l.tipo_moneda, l.importe_liquidacion, l.nombre_empleado, l.apellidoP_empleado, l.apellidoM_empleado,";
            MySql += " l.clave_contrato, l.clave_banco, l.fecha_liquidacion";
            MySql += " from movimientos_h h , movimientos l , contratos contratos , cuentas_banco cuentas ";
            MySql += " where h.clave_contrato = l.clave_contrato ";
            MySql += " and l.clave_contrato = contratos.clave_contrato ";
            MySql += " and h.fecha_liquidacion = l.fecha_liquidacion ";
            MySql += " and h.nombre_archivo=l.nombre_archivo ";
            MySql += " and contratos.cuenta_origen = cuentas.cuenta_origen ";
            MySql += " and contratos.clave_contrato = '" + clave_contrato + "'";
            MySql += " and l.fecha_liquidacion = '" + (new clsFecha().CambiaFormatoFecha("dd/MM/yyyy", "yyyy-MM-dd", miFecha)) + " 00:00:00.000" + "'";
            MySql += " and contratos.nombre_cliente = '" + cliente + "'";
            MySql += " and h.nombre_archivo = '" + nombre_archivo + "'";
            MySql += " and contratos.status = 'A'";
            MySql += " and cuentas.status = 'A'";
            MySql += " and h.status = 'A'";
            MySql += " and l.tipo_movimiento NOT IN ('4','5')";
            MySql += " order by l.nombre_empleado asc ";

            ResultSet rstSQLServer = statement.executeQuery(MySql);

            while (rstSQLServer.next() && limita_NoMovimientos <= 200) { //Inicio de Transacciones

                // Se obtienen campos de Lay-Out de Netcash
                tipo_movimiento = rstSQLServer.getString("tipo_movimiento").trim(); // Tipo de movimiento
                cuenta_destino = rstSQLServer.getString("cuenta_deposito").toString().trim(); // Cuenta destino
                cuenta_origen = rstSQLServer.getString("num_cuenta").trim(); // Cuenta orÃ­gen
                clave_moneda = rstSQLServer.getString("tipo_moneda").trim(); // Moneda
                importe = rstSQLServer.getString("importe_liquidacion").trim(); // Importe de liquidaciÃ³n
                nombreFidei = rstSQLServer.getString("nombre_empleado").trim() + " "; //Nombre del Empleado
                nombreFidei += rstSQLServer.getString("apellidoP_empleado").trim() + " "; //Apellido Paterno del Empleado
                nombreFidei += rstSQLServer.getString("apellidoM_empleado").trim(); //Apellido Materno del Empleado
                clave_fidei = rstSQLServer.getString("clave_contrato").trim(); // Clave de contrato
                clave_banco = rstSQLServer.getString("clave_banco").trim(); // Clave de banco
                fecha_liquidacion = rstSQLServer.getString("fecha_liquidacion").trim(); // Fecha LiquidaciÃ³n

                // ElaboraciÃ³n de formato de campos requeridos para Lay-Out

                /* TIPO DE MOVIMIENTO */
                String LO_tipo_movimiento = tipo_movimiento;
                // Validamos longitud de valor del movimiento
                if (tipo_movimiento.length() == 1) {

                    //Se identifica el tipo de movimiento: PTC, cuentas bancomer, mov==1
                    if (tipo_movimiento.equals("1")) {
                        // Asignamos formato solicitado en NetCash
                        LO_tipo_movimiento = "PTC";

                        /* CUENTA DEPÃ“SITO */
                        String LO_cuenta_deposito = cuenta_destino;
                        String cd_ceros = "";
                        int cd_num_ceros = 0;
                        // Obtenemos longitud de cuenta para depositar
                        if (cuenta_destino.length() < 18) {
                            cd_num_ceros = 18 - cuenta_destino.length();
                            // Abarcamos con 0's las posiciones faltantes
                            for (int i = 0; i < cd_num_ceros; i++) {
                                cd_ceros += "0";
                            }
                            LO_cuenta_deposito = cd_ceros + cuenta_destino;
                        }

                        /* CUENTA ORÃ�GEN */
                        String LO_cuenta_origen = cuenta_origen;
                        String co_ceros = "";
                        int co_num_ceros = 0;
                        // Obtenemos longitud de cuenta orÃ­gen
                        if (cuenta_origen.length() < 18) {
                            co_num_ceros = 18 - cuenta_origen.length();
                            // Abarcamos con 0's las posiciones faltantes
                            for (int i = 0; i < co_num_ceros; i++) {
                                co_ceros += "0";
                            }
                            LO_cuenta_origen = co_ceros + cuenta_origen;
                        }

                        /* TIPO DE MONEDA */
                        String LO_tipo_moneda = clave_moneda;
                        // Validamos tipo de moneda
                        if (clave_moneda.length() != 3) {
                            LO_tipo_moneda = "MXP";
                        } else if (!clave_moneda.equals("MXP")) {
                            LO_tipo_moneda = "MXP";
                        }

                        /* IMPORTE DE LIQUIDACIÃ“N */
                        String LO_importe_liquidacion = importe;
                        String imp_ceros = "";
                        String decimales = "";
                        String temporal = "";
                        int imp_num_ceros = 0;
                        int idx = 0;

                        idx = importe.indexOf(".");
                        temporal = importe.substring(idx + 1, idx + 3);

                        if (!temporal.equals("00")) {
                            decimales = "." + temporal;
                        } else {
                            decimales = ".00";
                        }

                        // Obtenemos longitud de importe de liquidaciÃ³n
                        if (importe.length() < 16) {
                            imp_num_ceros = 16 - importe.length();
                            // Abarcamos con 0's las posiciones faltantes
                            for (int i = 0; i < imp_num_ceros; i++) {
                                imp_ceros += "0";
                            }
                            LO_importe_liquidacion = imp_ceros + importe.substring(0, idx) + decimales;
                        }

                        /* MOTIVO DE PAGO */
                        String LO_clave_contrato = clave_fidei;
                        String cc_espacios = "";
                        int cc_num_espacios = 0;
                        // Obtenemos longitud de clave de contrato
                        if (clave_fidei.length() < 30) {
                            cc_num_espacios = 30 - clave_fidei.length();
                            // Abarcamos con espacios las posiciones faltantes
                            for (int i = 0; i < cc_num_espacios; i++) {
                                cc_espacios += " ";
                            }
                            LO_clave_contrato = clave_fidei + cc_espacios;
//                    LO_clave_contrato = clave_fidei;
                        }
                        // Validamos datos y creamos lÃ­neas de Lay-Out
                        if (!LO_tipo_movimiento.equals("") && !LO_cuenta_deposito.equals("") && !LO_cuenta_origen.equals("")
                                && !LO_tipo_moneda.equals("") && !LO_importe_liquidacion.equals("") && !LO_clave_contrato.equals("")) {
                            // Formamos lÃ­nea de movimiento
                            linea = LO_tipo_movimiento + LO_cuenta_deposito + LO_cuenta_origen + LO_tipo_moneda + LO_importe_liquidacion + LO_clave_contrato;
                            // Agregamos lÃ­nea al reporte

                            reporte.add(linea);
                        } else {
                            reporte = null;
                            reporteFinal = null;
                            break;
                        }
                    } //Se identifica el tipo de movimiento: PSC, otras cuentas y tarjeta debito banamex, mov==2 y mov==3 respectivamente
                    else if (tipo_movimiento.equals("2") || tipo_movimiento.equals("3")) {
                        LO_tipo_movimiento = "PSC";


                        /* CUENTA DEPÃ“SITO */
                        String LO_cuenta_deposito = cuenta_destino;
                        String cd_ceros = "";
                        int cd_num_ceros = 0;
                        // Obtenemos longitud de cuenta para depositar
                        if (cuenta_destino.length() < 18) {
                            cd_num_ceros = 18 - cuenta_destino.length();
                            // Abarcamos con 0's las posiciones faltantes
                            for (int i = 0; i < cd_num_ceros; i++) {
                                cd_ceros += "0";
                            }
                            LO_cuenta_deposito = cd_ceros + cuenta_destino;
                        }

                        /* CUENTA ORÃ�GEN */
                        String LO_cuenta_origen = cuenta_origen;
                        String co_ceros = "";
                        int co_num_ceros = 0;
                        // Obtenemos longitud de cuenta orÃ­gen
                        if (cuenta_origen.length() < 18) {
                            co_num_ceros = 18 - cuenta_origen.length();
                            // Abarcamos con 0's las posiciones faltantes
                            for (int i = 0; i < co_num_ceros; i++) {
                                co_ceros += "0";
                            }
                            LO_cuenta_origen = co_ceros + cuenta_origen;
                        }

                        /* TIPO DE MONEDA */
                        String LO_tipo_moneda = clave_moneda;
                        // Validamos tipo de moneda
                        if (clave_moneda.length() != 3) {
                            LO_tipo_moneda = "MXP";
                        } else if (!clave_moneda.equals("MXP")) {
                            LO_tipo_moneda = "MXP";
                        }

                        /* IMPORTE DE LIQUIDACIÃ“N */
                        String LO_importe_liquidacion = importe;
                        String imp_ceros = "";
                        String decimales = "";
                        String temporal = "";
                        int imp_num_ceros = 0;
                        int idx = 0;

                        idx = importe.indexOf(".");
                        temporal = importe.substring(idx + 1, idx + 3);

                        if (!temporal.equals("00")) {
                            decimales = "." + temporal;
                        } else {
                            decimales = ".00";
                        }

                        // Obtenemos longitud de importe de liquidaciÃ³n
                        if (importe.length() < 16) {
                            imp_num_ceros = 16 - importe.length();
                            // Abarcamos con 0's las posiciones faltantes
                            for (int i = 0; i < imp_num_ceros; i++) {
                                imp_ceros += "0";
                            }
                            LO_importe_liquidacion = imp_ceros + importe.substring(0, idx) + decimales;
                        }

                        /* NOMBRE DEL BENEFICIARIO */
                        String LO_nombre_beneficiario = nombreFidei;
                        String nombre_espacios = "";
                        int nombre_num_espacios = 0;
                        // Obtenemos longitud del nombre de liquidaciÃ³n
                        if (nombreFidei.length() < 30) {
                            nombre_num_espacios = 30 - nombreFidei.length();
                            for (int i = 0; i < nombre_num_espacios; i++) {
                                nombre_espacios += " ";
                            }
                            LO_nombre_beneficiario = nombreFidei + nombre_espacios;
                        } else if (nombreFidei.length() > 30) {
                            LO_nombre_beneficiario = nombreFidei.substring(0, 30);
                        } else {
                            LO_nombre_beneficiario = nombreFidei;
                        }

//                        /* BANCOS OBTENIDOS DE BD */
//                        String LO_numero_banco = "0";
//                        // Asignamos nÃºmero de banco de acuerdo al catÃ¡logo de Banxico
//                        if (!clave_banco.equals("")) {
////                            LO_numero_banco = ModeloLiquidation.getClaveBanxico(clave_banco);
//                            LO_numero_banco = clave_banco;
//                            if (LO_numero_banco.length() < 3) {
//                            imp_ceros = "";
//                            imp_num_ceros = 3 - LO_numero_banco.length();
//                            // Abarcamos con 0's las posiciones faltantes
//                            for (int i = 0; i < imp_num_ceros; i++) {
//                                imp_ceros += "0";
//                            }
//                            LO_numero_banco = imp_ceros + LO_numero_banco;
//                        }
//                        }
                        /* BANCOS OBTENIDOS DE SubString(cuenta_destino) */
                        String LO_numero_banco = "0";


                        /*SE IDENTIFICA EL TIPO DE CUENTA*/
                        if (tipo_movimiento.equals("3")) {
                            LO_tipo_cuenta = "03";
                            LO_numero_banco = "002";
                        }
                        if (tipo_movimiento.equals("2")) {
                            LO_tipo_cuenta = "40";
                            LO_numero_banco = cuenta_destino.substring(0, 3);
                        }

                        /* MOTIVO DE PAGO */
                        String LO_clave_contrato = clave_fidei;
                        String cc_espacios = "";
                        int cc_num_espacios = 0;
                        // Obtenemos longitud de clave de contrato
                        if (clave_fidei.length() < 30) {
                            cc_num_espacios = 30 - clave_fidei.length();
                            // Abarcamos con espacios las posiciones faltantes
                            for (int i = 0; i < cc_num_espacios; i++) {
                                cc_espacios += " ";
                            }
                            LO_clave_contrato = clave_fidei + cc_espacios;
//                    LO_clave_contrato = clave_fidei;
                        }

                        /* REFERENCIA NUMÃ‰RICA */
                        String LO_fecha_liquidacion = miFecha;
                        if (!miFecha.equals("")) {
                            String formato_fecha = "";
                            formato_fecha = ModeloLiquidation.getFormatoFecha(miFecha);
                            if (formato_fecha.length() == 6) {
                                LO_fecha_liquidacion = "0" + formato_fecha;
                            }
                        }

                        /* DISPONIBILIDAD */
                        // Asignamos disponibilidad de acuerdo al Tipo de movimiento
                        String LO_disponibilidad = "H";

                        // Validamos datos y creamos lÃ­neas de Lay-Out
                        if (!LO_tipo_movimiento.equals("") && !LO_cuenta_deposito.equals("") && !LO_cuenta_origen.equals("")
                                && !LO_tipo_moneda.equals("") && !LO_importe_liquidacion.equals("") && !LO_nombre_beneficiario.equals("")
                                && !LO_tipo_cuenta.equals("") && !LO_clave_contrato.equals("") && !LO_fecha_liquidacion.equals("")
                                && !LO_disponibilidad.equals("") && !LO_numero_banco.equals("")) {

                            // Formamos lÃ­nea de movimiento
                            linea = LO_tipo_movimiento + LO_cuenta_deposito + LO_cuenta_origen + LO_tipo_moneda + LO_importe_liquidacion
                                    + LO_nombre_beneficiario + LO_tipo_cuenta + LO_numero_banco + LO_clave_contrato + LO_fecha_liquidacion
                                    + LO_disponibilidad;
                            // Agregamos lÃ­nea al reporte
                            //reporte =new Vector();
                            reporte.add(linea);
                        } else {
                            reporte = null;
                            reporteFinal = null;
                            break;
                        }
                    } //para tipos de movimiento 4,5     
                    else {
                        reporte = null;
                        reporteFinal = null;
                        break;
                    }//si no coincide ningun movimiento
                    if (limita_NoMovimientos < 200) {
                        limita_NoMovimientos++;
                    } else {
                        limita_NoMovimientos = 1;
                        reporteFinal.add(reporte);
                        reporte = new Vector();
                    }
                }//si la longitud del movimiento no es 1

            }//Fin de Transacciones

            //Verificamos si se realizo alguna transacciÃ³n
            if (reporte.size() > 0) {
                reporteFinal.add(reporte);
                System.out.println("MOVIMIENTOS DE NETCASH GENERADOS CORRECTAMENTE");
            }
            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }

        } catch (Exception e) {
            reporteFinal = null;
            if (connection != null) {
                conn.Desconecta(connection);
            }
            System.out.println("getAllMoves" + e.getMessage());
//            System.out.println("getAllMoves" + e);
        }
        return reporteFinal;
    }

    /**
     * Método que regresa todos los movimientos del Lay-Out del tipo 2 y 3.
     * Movimientos del cliente , contrato y fecha de liquidación que se le pasan
     * como parámetro. Cada movimiento arrojado cuenta con el formato
     * correspondiente.
     *
     * @param cliente : Nombre del cliente.
     * @param clave_contrato : clave del contrato del cliente anterior.
     * @param mifecha : Fecha de Liquidación .
     * @param nombre_archivo : Nombre del Lay-Out al que corresponden los
     * movimientos.
     * @return Vector movimientos: Conjunto de movimientos con formato de Cuenta
     * Interbancaria. Si surge algún error en la ejecución se regresa null, si
     * no se cuenta con movimientos de este tipo entonces se regresa un vector
     * vacio y si hay movimientos de este tipo entonces se regresa un vector de
     * Strings con un movimiento por cada nodo.
     */
    public static Vector getMovesTwoThreeLAB(String cliente, String clave_contrato, String miFecha, String nombre_archivo) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        Vector reporte = new Vector();
        Vector reporteFinal = new Vector();
        int limita_NoMovimientos = 1;

        // Campos de Lay-Out
        String tipo_movimiento = "";
        String LO_tipo_cuenta = "";
        String cuenta_destino = "";
        String clave_moneda = "";
        String importe = "";
        String nombreFidei = "";
        String clave_fidei = "";
        String clave_banco = "";
        String cuenta_origen = "";
        String fecha_liquidacion = "";
        String linea = "";

        // Consultas
        String MySql = "";

        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select l.tipo_movimiento, l.cuenta_deposito, cuentas.num_cuenta , l.tipo_moneda, l.importe_liquidacion, l.nombre_empleado, l.apellidoP_empleado, l.apellidoM_empleado,";
            MySql += " l.clave_contrato, l.clave_banco, l.fecha_liquidacion";
            MySql += " from movimientos_h h , movimientos l , contratos contratos , cuentas_banco cuentas ";
            MySql += " where h.clave_contrato = l.clave_contrato ";
            MySql += " and l.clave_contrato = contratos.clave_contrato ";
            MySql += " and h.fecha_liquidacion = l.fecha_liquidacion ";
            MySql += " and h.nombre_archivo=l.nombre_archivo ";
            MySql += " and contratos.cuenta_origen = cuentas.cuenta_origen ";
            MySql += " and contratos.clave_contrato = '" + clave_contrato + "'";
            MySql += " and l.fecha_liquidacion = '" + (new clsFecha().CambiaFormatoFecha("dd/MM/yyyy", "yyyy-MM-dd", miFecha)) + " 00:00:00.000" + "'";
            MySql += " and contratos.nombre_cliente = '" + cliente + "'";
            MySql += " and h.nombre_archivo = '" + nombre_archivo + "'";
            MySql += " and contratos.status = 'A'";
            MySql += " and cuentas.status = 'A'";
            MySql += " and h.status = 'A'";
            MySql += " and l.tipo_movimiento NOT IN ('1','4','5')";
            MySql += " order by l.nombre_empleado asc ";

            ResultSet rstSQLServer = statement.executeQuery(MySql);

            while (rstSQLServer.next() && limita_NoMovimientos <= 200) { //Inicio de Transacciones

                // Se obtienen campos de Lay-Out de Netcash
                tipo_movimiento = rstSQLServer.getString("tipo_movimiento").trim(); // Tipo de movimiento
                cuenta_destino = rstSQLServer.getString("cuenta_deposito").toString().trim(); // Cuenta destino
                cuenta_origen = rstSQLServer.getString("num_cuenta").trim(); // Cuenta orígen
                clave_moneda = rstSQLServer.getString("tipo_moneda").trim(); // Moneda
                importe = rstSQLServer.getString("importe_liquidacion").trim(); // Importe de liquidación
                nombreFidei = rstSQLServer.getString("nombre_empleado").trim() + " "; //Nombre del Empleado
                nombreFidei += rstSQLServer.getString("apellidoP_empleado").trim() + " "; //Apellido Paterno del Empleado
                nombreFidei += rstSQLServer.getString("apellidoM_empleado").trim(); //Apellido Materno del Empleado
                clave_fidei = rstSQLServer.getString("clave_contrato").trim(); // Clave de contrato
                clave_banco = rstSQLServer.getString("clave_banco").trim(); // Clave de banco
                fecha_liquidacion = rstSQLServer.getString("fecha_liquidacion").trim(); // Fecha Liquidación

                // Elaboración de formato de campos requeridos para Lay-Out PSC
//                        /* BANCOS banxico ABM */
//                        String LO_numero_banco = "0";
//                        // Asignamos nÃºmero de banco de acuerdo al catÃ¡logo de Banxico
//                        if (!clave_banco.equals("")) {
////                            LO_numero_banco = ModeloLiquidation.getClaveBanxico(clave_banco);
//                            LO_numero_banco = clave_banco;
//                            if (LO_numero_banco.length() < 3) {
//                            String imp_ceros = "";
//                            int imp_num_ceros=0;
//                            imp_num_ceros = 3 - LO_numero_banco.length();
//                            // Abarcamos con 0's las posiciones faltantes
//                            for (int i = 0; i < imp_num_ceros; i++) {
//                                imp_ceros += "0";
//                            }
//                            LO_numero_banco = imp_ceros + LO_numero_banco;
//                        }
//                        }                

                /* BANCOS OBTENIDOS DE SubString(cuenta_destino) */
                String LO_numero_banco = "0";

                /* TIPO DE MOVIMIENTO */
                String LO_tipo_movimiento = tipo_movimiento;
                // Validamos longitud de valor del movimiento
                if (tipo_movimiento.length() == 1) {
                    if (tipo_movimiento.equals("2") || tipo_movimiento.equals("3")) {
                        if (tipo_movimiento.equals("3")) {
                            LO_tipo_cuenta = "03";
                            LO_numero_banco = "002";
                        }
                        if (tipo_movimiento.equals("2")) {
                            LO_tipo_cuenta = "40";
                            LO_numero_banco = cuenta_destino.substring(0, 3);
                        }
                    }
                }

                /* CUENTA DEPÓSITO */
                String LO_cuenta_deposito = cuenta_destino;
                String cd_ceros = "";
                int cd_num_ceros = 0;
                // Obtenemos longitud de cuenta para depositar
                if (cuenta_destino.length() < 18) {
                    cd_num_ceros = 18 - cuenta_destino.length();
                    // Abarcamos con 0's las posiciones faltantes
                    for (int i = 0; i < cd_num_ceros; i++) {
                        cd_ceros += "0";
                    }
                    LO_cuenta_deposito = cd_ceros + cuenta_destino;
                }

                /* TIPO DE MONEDA */
                String LO_tipo_moneda = clave_moneda;
                // Validamos tipo de moneda
                if (clave_moneda.length() != 3) {
                    LO_tipo_moneda = "MXP";
                } else if (!clave_moneda.equals("MXP")) {
                    LO_tipo_moneda = "MXP";
                }

                /* IMPORTE DE LIQUIDACIÓN */
                String LO_importe_liquidacion = "9999999999999.99";
//                String LO_importe_liquidacion = importe;
//                
//                DecimalFormat format = new DecimalFormat("0000000000000.00");
//                double tempoImporte = Double.parseDouble(importe);
//                tempoImporte++;
//
//                LO_importe_liquidacion = format.format(tempoImporte);                
//                
//                
//                String imp_ceros = "";
//                String decimales = "";
//                String temporal = "";
//                int imp_num_ceros = 0;
//                int idx = 0;
//
//                idx = importe.indexOf(".");
//                temporal = importe.substring(idx+1, idx+3);
//
//                if (!temporal.equals("00")){
//                    decimales = "." + temporal;
//                }
//                else{
//                    decimales = ".00";
//                }
//                
//                // Obtenemos longitud de importe de liquidación
//                if (importe.length() < 16){
//                    imp_num_ceros = 16 - importe.length();
//                    // Abarcamos con 0's las posiciones faltantes
//                    for (int i=0; i<imp_num_ceros; i++){
//                        imp_ceros += "0";
//                    }
//                    LO_importe_liquidacion = imp_ceros + importe.substring(0, idx) + decimales;
//                }

                /* NOMBRE DEL BENEFICIARIO */
                String LO_nombre_beneficiario = nombreFidei;
                String nombre_espacios = "";
                int nombre_num_espacios = 0;
                // Obtenemos longitud del nombre de liquidación
                if (nombreFidei.length() < 30) {
                    nombre_num_espacios = 30 - nombreFidei.length();
                    for (int i = 0; i < nombre_num_espacios; i++) {
                        nombre_espacios += " ";
                    }
                    LO_nombre_beneficiario = nombreFidei + nombre_espacios;
                } else if (nombreFidei.length() > 30) {
                    LO_nombre_beneficiario = nombreFidei.substring(0, 30);
                } else {
                    LO_nombre_beneficiario = nombreFidei;
                }

                /* MOTIVO DE PAGO */
                String LO_clave_contrato = clave_fidei;
                String cc_espacios = "";
                int cc_num_espacios = 0;
                // Obtenemos longitud de clave de contrato
                if (clave_fidei.length() < 30) {
                    cc_num_espacios = 30 - clave_fidei.length();
                    // Abarcamos con espacios las posiciones faltantes
                    for (int i = 0; i < cc_num_espacios; i++) {
                        cc_espacios += " ";
                    }
                    LO_clave_contrato = clave_fidei + cc_espacios;
//                    LO_clave_contrato = clave_fidei;
                }

                // Validamos datos y creamos líneas de Lay-Out
                if (!LO_tipo_movimiento.equals("") && !LO_cuenta_deposito.equals("")
                        && !LO_tipo_moneda.equals("") && !LO_importe_liquidacion.equals("") && !LO_nombre_beneficiario.equals("")
                        && !LO_tipo_cuenta.equals("") && !LO_clave_contrato.equals("")
                        && !LO_numero_banco.equals("")) {

                    // Formamos línea de movimiento
                    linea = LO_numero_banco + LO_cuenta_deposito + LO_tipo_moneda + LO_importe_liquidacion
                            + LO_nombre_beneficiario + LO_clave_contrato + LO_tipo_cuenta;
                    // Agregamos línea al reporte
                    //reporte =new Vector();
                    reporte.add(linea);
                } else {
                    reporte = null;
                    reporteFinal = null;
                    break;
                }
                if (limita_NoMovimientos < 200) {
                    limita_NoMovimientos++;
                } else {
                    limita_NoMovimientos = 1;
                    reporteFinal.add(reporte);
                    reporte = new Vector();
                }
            }//Fin de Transacciones

            //Verificamos si se realizo alguna transacción
            if (reporte.size() > 0) {
                reporteFinal.add(reporte);
                System.out.println("MOVIMIENTOS DE NETCASH GENERADOS CORRECTAMENTE");
            }
            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }

        } catch (Exception e) {
            reporteFinal = null;
            if (connection != null) {
                conn.Desconecta(connection);
            }
            System.out.println("getMovesTwoThree" + e.getMessage());
        }
        return reporteFinal;
    }

    /**
     * Método que regresa todos los movimientos del Lay-Out del tipo 2 y 3.
     * Movimientos del cliente , contrato y fecha de liquidación que se le pasan
     * como parámetro. Cada movimiento arrojado cuenta con el formato
     * correspondiente.
     *
     * @param cliente : Nombre del cliente.
     * @param clave_contrato : clave del contrato del cliente anterior.
     * @param mifecha : Fecha de Liquidación .
     * @param nombre_archivo : Nombre del Lay-Out al que corresponden los
     * movimientos.
     * @return Vector movimientos: Conjunto de movimientos con formato de Cuenta
     * Interbancaria. Si surge algún error en la ejecución se regresa null, si
     * no se cuenta con movimientos de este tipo entonces se regresa un vector
     * vacio y si hay movimientos de este tipo entonces se regresa un vector de
     * Strings con un movimiento por cada nodo.
     */
    public static Vector getMovesTwoThree(String cliente, String clave_contrato, String miFecha, String nombre_archivo) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        Vector reporte = new Vector();
        String tmp = "";

        // Campos de Lay-Out
        String tipo_movimiento = "";
        String LO_tipo_cuenta = "";
        String cuenta_destino = "";
        String clave_moneda = "";
        String importe = "";
        String nombreFidei = "";
        String clave_fidei = "";
        String clave_banco = "";
        String cuenta_origen = "";
        String fecha_liquidacion = "";
        String linea = "";

        // Consultas
        String MySql = "";

        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select l.tipo_movimiento, l.cuenta_deposito, cuentas.num_cuenta , l.tipo_moneda, l.importe_liquidacion, l.nombre_empleado, l.apellidoP_empleado, l.apellidoM_empleado,";
            MySql += " l.clave_contrato, l.clave_banco, l.fecha_liquidacion";
            MySql += " from movimientos_h h , movimientos l , contratos contratos , cuentas_banco cuentas ";
            MySql += " where h.clave_contrato = l.clave_contrato ";
            MySql += " and l.clave_contrato = contratos.clave_contrato ";
            MySql += " and h.fecha_liquidacion = l.fecha_liquidacion ";
            MySql += " and h.nombre_archivo=l.nombre_archivo ";
            MySql += " and contratos.cuenta_origen = cuentas.cuenta_origen ";
            MySql += " and contratos.clave_contrato = '" + clave_contrato + "'";
            MySql += " and l.fecha_liquidacion = '" + (new clsFecha().CambiaFormatoFecha("dd/MM/yyyy", "yyyy-MM-dd", miFecha)) + " 00:00:00.000" + "'";
            MySql += " and contratos.nombre_cliente = '" + cliente + "'";
            MySql += " and h.nombre_archivo = '" + nombre_archivo + "'";
            MySql += " and contratos.status = 'A'";
            MySql += " and cuentas.status = 'A'";
            MySql += " and h.status = 'A'";
            MySql += " and l.tipo_movimiento NOT IN ('1','4','5')";
            MySql += " order by l.nombre_empleado asc ";

            ResultSet rstSQLServer = statement.executeQuery(MySql);

            while (rstSQLServer.next()) { //Inicio de Transacciones

                tmp = "";
                // Se obtienen campos de Lay-Out de Netcash
                tipo_movimiento = rstSQLServer.getString("tipo_movimiento").trim(); // Tipo de movimiento
                cuenta_destino = rstSQLServer.getString("cuenta_deposito").toString().trim(); // Cuenta destino
                cuenta_origen = rstSQLServer.getString("num_cuenta").trim(); // Cuenta orígen
                clave_moneda = rstSQLServer.getString("tipo_moneda").trim(); // Moneda
                importe = rstSQLServer.getString("importe_liquidacion").trim(); // Importe de liquidación
                nombreFidei = rstSQLServer.getString("nombre_empleado").trim() + " "; //Nombre del Empleado
                nombreFidei += rstSQLServer.getString("apellidoP_empleado").trim() + " "; //Apellido Paterno del Empleado
                nombreFidei += rstSQLServer.getString("apellidoM_empleado").trim(); //Apellido Materno del Empleado
                clave_fidei = rstSQLServer.getString("clave_contrato").trim(); // Clave de contrato
                clave_banco = rstSQLServer.getString("clave_banco").trim(); // Clave de banco
                fecha_liquidacion = rstSQLServer.getString("fecha_liquidacion").trim(); // Fecha Liquidación

                // Elaboración de formato de campos requeridos para Lay-Out PSC

                /* CUENTA DEPÓSITO */
                String LO_cuenta_deposito = cuenta_destino;
                String cd_ceros = "";
                int cd_num_ceros = 0;
                // Obtenemos longitud de cuenta para depositar
                if (cuenta_destino.length() < 18) {
                    cd_num_ceros = 18 - cuenta_destino.length();
                    // Abarcamos con 0's las posiciones faltantes
                    for (int i = 0; i < cd_num_ceros; i++) {
                        cd_ceros += "0";
                    }
                    LO_cuenta_deposito = cd_ceros + cuenta_destino;
                }

                /* CUENTA ORÍGEN */
                String LO_cuenta_origen = cuenta_origen;
                String co_ceros = "";
                int co_num_ceros = 0;
                // Obtenemos longitud de cuenta orígen
                if (cuenta_origen.length() < 18) {
                    co_num_ceros = 18 - cuenta_origen.length();
                    // Abarcamos con 0's las posiciones faltantes
                    for (int i = 0; i < co_num_ceros; i++) {
                        co_ceros += "0";
                    }
                    LO_cuenta_origen = co_ceros + cuenta_origen;
                }

                /* TIPO DE MONEDA */
                String LO_tipo_moneda = clave_moneda;
                // Validamos tipo de moneda
                if (clave_moneda.length() != 3) {
                    LO_tipo_moneda = "MXP";
                } else if (!clave_moneda.equals("MXP")) {
                    LO_tipo_moneda = "MXP";
                }

                /* IMPORTE DE LIQUIDACIÓN */
                String LO_importe_liquidacion = importe;
                String imp_ceros = "";
                String decimales = "";
                String temporal = "";
                int imp_num_ceros = 0;
                int idx = 0;

                idx = importe.indexOf(".");
                temporal = importe.substring(idx + 1, idx + 3);

                if (!temporal.equals("00")) {
                    decimales = "." + temporal;
                } else {
                    decimales = ".00";
                }

                // Obtenemos longitud de importe de liquidación
                if (importe.length() < 16) {
                    imp_num_ceros = 16 - importe.length();
                    imp_ceros = "";
                    // Abarcamos con 0's las posiciones faltantes
                    for (int i = 0; i < imp_num_ceros; i++) {
                        imp_ceros += "0";
                    }
                    LO_importe_liquidacion = imp_ceros + importe.substring(0, idx) + decimales;
                }

                /* NOMBRE DEL BENEFICIARIO */
                String LO_nombre_beneficiario = nombreFidei;
                String nombre_espacios = "";
                int nombre_num_espacios = 0;
                // Obtenemos longitud del nombre de liquidación
                if (nombreFidei.length() < 30) {
                    nombre_num_espacios = 30 - nombreFidei.length();
                    for (int i = 0; i < nombre_num_espacios; i++) {
                        nombre_espacios += " ";
                    }
                    LO_nombre_beneficiario = nombreFidei + nombre_espacios;
                } else if (nombreFidei.length() > 30) {
                    LO_nombre_beneficiario = nombreFidei.substring(0, 30);
                } else {
                    LO_nombre_beneficiario = nombreFidei;
                }


                /* BANCOS banxico ABM */
//                        String LO_numero_banco = "0";
//                        // Asignamos nÃºmero de banco de acuerdo al catÃ¡logo de Banxico
//                        if (!clave_banco.equals("")) {
////                            LO_numero_banco = ModeloLiquidation.getClaveBanxico(clave_banco);
//                            LO_numero_banco = clave_banco;
//                            if (LO_numero_banco.length() < 3) {
//                            imp_ceros = "";
//                            imp_num_ceros = 3 - LO_numero_banco.length();
//                            // Abarcamos con 0's las posiciones faltantes
//                            for (int i = 0; i < imp_num_ceros; i++) {
//                                imp_ceros += "0";
//                            }
//                            LO_numero_banco = imp_ceros + LO_numero_banco;
//                        }
//                        }                 
                /* BANCOS OBTENIDOS DE SubString(cuenta_destino) */
                String LO_numero_banco = "0";

                /* TIPO DE MOVIMIENTO */
                String LO_tipo_movimiento = tipo_movimiento;
                // Validamos longitud de valor del movimiento
                if (tipo_movimiento.length() == 1) {
                    if (tipo_movimiento.equals("1")) {
                        // Asignamos formato solicitado en NetCash
                        LO_tipo_movimiento = "PTC";
                    } else if (tipo_movimiento.equals("2") || tipo_movimiento.equals("3")) {
                        LO_tipo_movimiento = "PSC";
                        if (tipo_movimiento.equals("3")) {
                            LO_tipo_cuenta = "03";
                            LO_numero_banco = "002";
                        }
                        if (tipo_movimiento.equals("2")) {
                            LO_tipo_cuenta = "40";
                            LO_numero_banco = cuenta_destino.substring(0, 3);
                        }
                    }
                }

                /* MOTIVO DE PAGO */
                String LO_clave_contrato = clave_fidei;
                String cc_espacios = "";
                int cc_num_espacios = 0;
                // Obtenemos longitud de clave de contrato
                if (clave_fidei.length() < 30) {
                    cc_num_espacios = 30 - clave_fidei.length();
                    // Abarcamos con espacios las posiciones faltantes
                    for (int i = 0; i < cc_num_espacios; i++) {
                        cc_espacios += " ";
                    }
                    LO_clave_contrato = clave_fidei + cc_espacios;
//                    LO_clave_contrato = clave_fidei;
                }

                /* REFERENCIA NUMÉRICA */
                String LO_fecha_liquidacion = miFecha;
                if (!miFecha.equals("")) {
                    String formato_fecha = "";
                    formato_fecha = ModeloLiquidation.getFormatoFecha(miFecha);
                    if (formato_fecha.length() == 6) {
                        LO_fecha_liquidacion = "0" + formato_fecha;
                    }
                }

                /* DISPONIBILIDAD */
                // Asignamos disponibilidad de acuerdo al Tipo de movimiento
                String LO_disponibilidad = "H";
                // Validamos longitud de valor del movimiento
                if (tipo_movimiento.length() == 1) {
                    if (tipo_movimiento.equals("1")) {
                        // Asignamos formato solicitado en NetCash
                        LO_disponibilidad = "H";
                    }
                }

                // Validamos datos y creamos líneas de Lay-Out
                if (!LO_tipo_movimiento.equals("") && !LO_cuenta_deposito.equals("") && !LO_cuenta_origen.equals("")
                        && !LO_tipo_moneda.equals("") && !LO_importe_liquidacion.equals("") && !LO_nombre_beneficiario.equals("")
                        && !LO_tipo_cuenta.equals("") && !LO_clave_contrato.equals("") && !LO_fecha_liquidacion.equals("")
                        && !LO_disponibilidad.equals("") && !LO_numero_banco.equals("")) {

                    // Formamos línea de movimiento
                    linea = LO_cuenta_deposito + LO_cuenta_origen + LO_tipo_moneda + LO_importe_liquidacion
                            + LO_nombre_beneficiario + LO_tipo_cuenta + LO_numero_banco + LO_clave_contrato + LO_fecha_liquidacion
                            + LO_disponibilidad;
                    // Agregamos línea al reporte
                    //reporte =new Vector();
                    reporte.add(linea);
                } else {
                    reporte = null;
                    break;
                }
            }//Fin de Transacciones

            //Verificamos si se realizo alguna transacción
            if (reporte.size() > 0) {
                System.out.println("MOVIMIENTOS DE CUENTAS INTERBANCARIAS GENERADOS CORRECTAMENTE");
            }
            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }

        } catch (Exception e) {
            reporte = null;
            if (connection != null) {
                conn.Desconecta(connection);
            }
            System.out.println("getMovesTwoThree" + e.getMessage());
        }
        return reporte;
    }

    /**
     * Método que regresa todos los movimientos del Lay-Out del tipo 1.
     * Movimientos del cliente , contrato y fecha de liquidación que se le pasan
     * como parámetro. Cada movimiento arrojado cuenta con el formato
     * correspondiente.
     *
     * @param cliente : Nombre del cliente.
     * @param clave_contrato : clave del contrato del cliente anterior.
     * @param mifecha : Fecha de Liquidación .
     * @param nombre_archivo : Nombre del Lay-Out al que corresponden los
     * movimientos.
     * @return Vector movimientos: Conjunto de movimientos con formato de
     * Cuentas Bancomer. Si surge algún error en la ejecución se regresa null,
     * si no se cuenta con movimientos de este tipo entonces se regresa un
     * vector vacio y si hay movimientos de este tipo entonces se regresa un
     * vector de Strings con un movimiento por cada nodo.
     */
    public static Vector getMovesBancomerLAB(String cliente, String clave_contrato, String miFecha, String nombre_archivo) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        Vector reporte = new Vector();
        Vector reporteFinal = new Vector();
        int limita_NoMovimientos = 1;
        String tmp = "";

        // Campos de Lay-Out
        String cuenta_destino = "";
        String clave_moneda = "";
        String importe = "";
        String nombreFidei = "";
        String clave_fidei = "";
        String linea = "";
        String tipo_movimiento = "";
        String cuenta_origen = "";
        String clave_banco = "";
        String fecha_liquidacion = "";

        // Consultas
        String MySql = "";

        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select l.tipo_movimiento, l.cuenta_deposito, cuentas.num_cuenta , l.tipo_moneda, l.importe_liquidacion, l.nombre_empleado, l.apellidoP_empleado, l.apellidoM_empleado,";
            MySql += " l.clave_contrato, l.clave_banco, l.fecha_liquidacion";
            MySql += " from movimientos_h h , movimientos l , contratos contratos , cuentas_banco cuentas ";
            MySql += " where h.clave_contrato = l.clave_contrato ";
            MySql += " and l.clave_contrato = contratos.clave_contrato ";
            MySql += " and h.fecha_liquidacion = l.fecha_liquidacion ";
            MySql += " and h.nombre_archivo=l.nombre_archivo ";
            MySql += " and contratos.cuenta_origen = cuentas.cuenta_origen ";
            MySql += " and contratos.clave_contrato = '" + clave_contrato + "'";
            MySql += " and l.fecha_liquidacion = '" + (new clsFecha().CambiaFormatoFecha("dd/MM/yyyy", "yyyy-MM-dd", miFecha)) + " 00:00:00.000" + "'";
            MySql += " and contratos.nombre_cliente = '" + cliente + "'";
            MySql += " and h.nombre_archivo = '" + nombre_archivo + "'";
            MySql += " and contratos.status = 'A'";
            MySql += " and cuentas.status = 'A'";
            MySql += " and h.status = 'A'";
            MySql += " and l.tipo_movimiento NOT IN ('2','3','4','5')";
            MySql += " order by l.nombre_empleado asc ";

            ResultSet rstSQLServer = statement.executeQuery(MySql);

            while (rstSQLServer.next() && limita_NoMovimientos <= 200) { //Inicio de Transacciones

                tmp = "";
                // Se obtienen campos de Lay-Out de Netcash
                tipo_movimiento = rstSQLServer.getString("tipo_movimiento").trim(); // Tipo de movimiento
                cuenta_destino = rstSQLServer.getString("cuenta_deposito").toString().trim(); // Cuenta destino
                cuenta_origen = rstSQLServer.getString("num_cuenta").trim(); // Cuenta orígen
                clave_moneda = rstSQLServer.getString("tipo_moneda").trim(); // Moneda
                importe = rstSQLServer.getString("importe_liquidacion").trim(); // Importe de liquidación
                nombreFidei = rstSQLServer.getString("nombre_empleado").trim() + " "; //Nombre del Empleado
                nombreFidei += rstSQLServer.getString("apellidoP_empleado").trim() + " "; //Apellido Paterno del Empleado
                nombreFidei += rstSQLServer.getString("apellidoM_empleado").trim(); //Apellido Materno del Empleado
                clave_fidei = rstSQLServer.getString("clave_contrato").trim(); // Clave de contrato
                clave_banco = rstSQLServer.getString("clave_banco").trim(); // Clave de banco
                fecha_liquidacion = rstSQLServer.getString("fecha_liquidacion").trim(); // Fecha Liquidación

                // Elaboración de formato de campos requeridos para Lay-Out

                /* TIPO DE MOVIMIENTO 
                 String LO_tipo_movimiento = tipo_movimiento;
                 // Validamos longitud de valor del movimiento
                 if (tipo_movimiento.length() == 1){
                 if (tipo_movimiento.equals("1")){
                 // Asignamos formato solicitado en NetCash
                 LO_tipo_movimiento = "PTC";
                 }
                 else if (tipo_movimiento.equals("2") || tipo_movimiento.equals("3")){
                 LO_tipo_movimiento = "PSC";
                 }
                 }*/

 /* CUENTA DEPÓSITO */
                String LO_cuenta_deposito = cuenta_destino;
                String cd_ceros = "";
                int cd_num_ceros = 0;
                // Obtenemos longitud de cuenta para depositar
                if (cuenta_destino.length() < 18) {
                    cd_num_ceros = 18 - cuenta_destino.length();
                    // Abarcamos con 0's las posiciones faltantes
                    for (int i = 0; i < cd_num_ceros; i++) {
                        cd_ceros += "0";
                    }
                    LO_cuenta_deposito = cd_ceros + cuenta_destino;
                }

                /* CUENTA ORÍGEN 
                 String LO_cuenta_origen = cuenta_origen;
                 String co_ceros = "";
                 int co_num_ceros = 0;
                 // Obtenemos longitud de cuenta orígen
                 if (cuenta_origen.length() < 18){
                 co_num_ceros = 18 - cuenta_origen.length();
                 // Abarcamos con 0's las posiciones faltantes
                 for (int i=0; i<co_num_ceros; i++){
                 co_ceros += "0";
                 }
                 LO_cuenta_origen = co_ceros + cuenta_origen;
                 }*/

 /* TIPO DE MONEDA */
                String LO_tipo_moneda = clave_moneda;
                // Validamos tipo de moneda
                if (clave_moneda.length() != 3) {
                    LO_tipo_moneda = "MXP";
                } else if (!clave_moneda.equals("MXP")) {
                    LO_tipo_moneda = "MXP";
                }

                /* IMPORTE DE LIQUIDACIÓN */
                String LO_importe_liquidacion = "9999999999999.99";
//                String LO_importe_liquidacion = importe;                
//                DecimalFormat format = new DecimalFormat("0000000000000.00");
//                double tempoImporte = Double.parseDouble(importe);
//                tempoImporte++;
//
//                LO_importe_liquidacion = format.format(tempoImporte);

//                String LO_importe_liquidacion = importe;
//                String imp_ceros = "";
//                String decimales = "";
//                String temporal = "";
//                int imp_num_ceros = 0;
//                int idx = 0;
//
//                idx = importe.indexOf(".");
//                temporal = importe.substring(idx+1, idx+3);
//
//                if (!temporal.equals("00")){
//                    decimales = "." + temporal;
//                }
//                else{
//                    decimales = ".00";
//                }
//                
//                // Obtenemos longitud de importe de liquidación
//                if (importe.length() < 16){
//                    imp_num_ceros = 16 - importe.length();
//                    // Abarcamos con 0's las posiciones faltantes
//                    for (int i=0; i<imp_num_ceros; i++){
//                        imp_ceros += "0";
//                    }
//                    LO_importe_liquidacion = imp_ceros + importe.substring(0, idx) + decimales;
//                }
                /* NOMBRE DEL BENEFICIARIO */
                String LO_nombre_beneficiario = nombreFidei;
                String nombre_espacios = "";
                int nombre_num_espacios = 0;
                // Obtenemos longitud del nombre de liquidación
                if (nombreFidei.length() < 30) {
                    nombre_num_espacios = 30 - nombreFidei.length();
                    for (int i = 0; i < nombre_num_espacios; i++) {
                        nombre_espacios += " ";
                    }
                    LO_nombre_beneficiario = nombreFidei + nombre_espacios;
                } else if (nombreFidei.length() > 30) {
                    LO_nombre_beneficiario = nombreFidei.substring(0, 30);
                } else {
                    LO_nombre_beneficiario = nombreFidei;
                }

                /* MOTIVO DE PAGO */
                String LO_clave_contrato = clave_fidei;
                String cc_espacios = "";
                int cc_num_espacios = 0;
                // Obtenemos longitud de clave de contrato
                if (clave_fidei.length() < 30) {
                    cc_num_espacios = 30 - clave_fidei.length();
                    // Abarcamos con espacios las posiciones faltantes
                    for (int i = 0; i < cc_num_espacios; i++) {
                        cc_espacios += " ";
                    }
                    LO_clave_contrato = clave_fidei + cc_espacios;
//                    LO_clave_contrato = clave_fidei;
                }
                // Validamos datos y creamos líneas de Lay-Out
                if (!LO_cuenta_deposito.equals("") && !LO_tipo_moneda.equals("") && !LO_importe_liquidacion.equals("") && !LO_clave_contrato.equals("")) {
                    // Formamos línea de movimiento
                    linea = LO_cuenta_deposito + LO_tipo_moneda + LO_importe_liquidacion + LO_nombre_beneficiario + LO_clave_contrato;
                    // Agregamos línea al reporte

                    reporte.add(linea);
                } else {
                    reporte = null;
                    reporteFinal = null;
                    break;
                }

                if (limita_NoMovimientos < 200) {
                    limita_NoMovimientos++;
                } else {
                    limita_NoMovimientos = 1;
                    reporteFinal.add(reporte);
                    reporte = new Vector();
                }

            }//Fin de Transacciones

            //Verificamos si se realizo alguna transacción
            if (reporte.size() > 0) {
                reporteFinal.add(reporte);
                System.out.println("MOVIMIENTOS DE NETCASH GENERADOS CORRECTAMENTE");
            }
            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }

        } catch (Exception e) {
            reporteFinal = null;
            if (connection != null) {
                conn.Desconecta(connection);
            }
            System.out.println("getMovesBancomer" + e.getMessage());
        }
        return reporteFinal;
    }

    /**
     * Método que regresa todos los movimientos del Lay-Out del tipo 1.
     * Movimientos del cliente , contrato y fecha de liquidación que se le pasan
     * como parámetro. Cada movimiento arrojado cuenta con el formato
     * correspondiente.
     *
     * @param cliente : Nombre del cliente.
     * @param clave_contrato : clave del contrato del cliente anterior.
     * @param mifecha : Fecha de Liquidación .
     * @param nombre_archivo : Nombre del Lay-Out al que corresponden los
     * movimientos.
     * @return Vector movimientos: Conjunto de movimientos con formato de
     * Cuentas Bancomer. Si surge algún error en la ejecución se regresa null,
     * si no se cuenta con movimientos de este tipo entonces se regresa un
     * vector vacio y si hay movimientos de este tipo entonces se regresa un
     * vector de Strings con un movimiento por cada nodo.
     */
    public static Vector getMovesBancomer(String cliente, String clave_contrato, String miFecha, String nombre_archivo) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        Vector reporte = new Vector();
        String tmp = "";

        // Campos de Lay-Out
        String cuenta_destino = "";
        String clave_moneda = "";
        String importe = "";
        String nombreFidei = "";
        String clave_fidei = "";
        String linea = "";
        String tipo_movimiento = "";
        String cuenta_origen = "";
        String clave_banco = "";
        String fecha_liquidacion = "";

        // Consultas
        String MySql = "";

        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select l.tipo_movimiento, l.cuenta_deposito, cuentas.num_cuenta , l.tipo_moneda, l.importe_liquidacion, l.nombre_empleado, l.apellidoP_empleado, l.apellidoM_empleado,";
            MySql += " l.clave_contrato, l.clave_banco, l.fecha_liquidacion";
            MySql += " from movimientos_h h , movimientos l , contratos contratos , cuentas_banco cuentas ";
            MySql += " where h.clave_contrato = l.clave_contrato ";
            MySql += " and l.clave_contrato = contratos.clave_contrato ";
            MySql += " and h.fecha_liquidacion = l.fecha_liquidacion ";
            MySql += " and h.nombre_archivo=l.nombre_archivo ";
            MySql += " and contratos.cuenta_origen = cuentas.cuenta_origen ";
            MySql += " and contratos.clave_contrato = '" + clave_contrato + "'";
            MySql += " and l.fecha_liquidacion = '" + (new clsFecha().CambiaFormatoFecha("dd/MM/yyyy", "yyyy-MM-dd", miFecha)) + " 00:00:00.000" + "'";
            MySql += " and contratos.nombre_cliente = '" + cliente + "'";
            MySql += " and h.nombre_archivo = '" + nombre_archivo + "'";
            MySql += " and contratos.status = 'A'";
            MySql += " and cuentas.status = 'A'";
            MySql += " and h.status = 'A'";
            MySql += " and l.tipo_movimiento NOT IN ('2','3','4','5')";
            MySql += " order by l.nombre_empleado asc ";

            ResultSet rstSQLServer = statement.executeQuery(MySql);

            while (rstSQLServer.next()) { //Inicio de Transacciones

                tmp = "";
                // Se obtienen campos de Lay-Out de Netcash
                tipo_movimiento = rstSQLServer.getString("tipo_movimiento").trim(); // Tipo de movimiento
                cuenta_destino = rstSQLServer.getString("cuenta_deposito").toString().trim(); // Cuenta destino
                cuenta_origen = rstSQLServer.getString("num_cuenta").trim(); // Cuenta orígen
                clave_moneda = rstSQLServer.getString("tipo_moneda").trim(); // Moneda
                importe = rstSQLServer.getString("importe_liquidacion").trim(); // Importe de liquidación
                nombreFidei = rstSQLServer.getString("nombre_empleado").trim() + " "; //Nombre del Empleado
                nombreFidei += rstSQLServer.getString("apellidoP_empleado").trim() + " "; //Apellido Paterno del Empleado
                nombreFidei += rstSQLServer.getString("apellidoM_empleado").trim(); //Apellido Materno del Empleado
                clave_fidei = rstSQLServer.getString("clave_contrato").trim(); // Clave de contrato
                clave_banco = rstSQLServer.getString("clave_banco").trim(); // Clave de banco
                fecha_liquidacion = rstSQLServer.getString("fecha_liquidacion").trim(); // Fecha Liquidación

                // Elaboración de formato de campos requeridos para Lay-Out

                /* TIPO DE MOVIMIENTO */
                String LO_tipo_movimiento = tipo_movimiento;
                // Validamos longitud de valor del movimiento
                if (tipo_movimiento.length() == 1) {
                    if (tipo_movimiento.equals("1")) {
                        // Asignamos formato solicitado en NetCash
                        LO_tipo_movimiento = "PTC";
                    } else if (tipo_movimiento.equals("2") || tipo_movimiento.equals("3")) {
                        LO_tipo_movimiento = "PSC";
                    }
                }

                /* CUENTA DEPÓSITO */
                String LO_cuenta_deposito = cuenta_destino;
                String cd_ceros = "";
                int cd_num_ceros = 0;
                // Obtenemos longitud de cuenta para depositar
                if (cuenta_destino.length() < 18) {
                    cd_num_ceros = 18 - cuenta_destino.length();
                    // Abarcamos con 0's las posiciones faltantes
                    for (int i = 0; i < cd_num_ceros; i++) {
                        cd_ceros += "0";
                    }
                    LO_cuenta_deposito = cd_ceros + cuenta_destino;
                }

                /* CUENTA ORÍGEN */
                String LO_cuenta_origen = cuenta_origen;
                String co_ceros = "";
                int co_num_ceros = 0;
                // Obtenemos longitud de cuenta orígen
                if (cuenta_origen.length() < 18) {
                    co_num_ceros = 18 - cuenta_origen.length();
                    // Abarcamos con 0's las posiciones faltantes
                    for (int i = 0; i < co_num_ceros; i++) {
                        co_ceros += "0";
                    }
                    LO_cuenta_origen = co_ceros + cuenta_origen;
                }

                /* TIPO DE MONEDA */
                String LO_tipo_moneda = clave_moneda;
                // Validamos tipo de moneda
                if (clave_moneda.length() != 3) {
                    LO_tipo_moneda = "MXP";
                } else if (!clave_moneda.equals("MXP")) {
                    LO_tipo_moneda = "MXP";
                }

                /* IMPORTE DE LIQUIDACIÓN */
                String LO_importe_liquidacion = importe;
                String imp_ceros = "";
                String decimales = "";
                String temporal = "";
                int imp_num_ceros = 0;
                int idx = 0;

                idx = importe.indexOf(".");
                temporal = importe.substring(idx + 1, idx + 3);

                if (!temporal.equals("00")) {
                    decimales = "." + temporal;
                } else {
                    decimales = ".00";
                }

                // Obtenemos longitud de importe de liquidación
                if (importe.length() < 16) {
                    imp_num_ceros = 16 - importe.length();
                    // Abarcamos con 0's las posiciones faltantes
                    for (int i = 0; i < imp_num_ceros; i++) {
                        imp_ceros += "0";
                    }
                    LO_importe_liquidacion = imp_ceros + importe.substring(0, idx) + decimales;
                }

                /* MOTIVO DE PAGO */
                String LO_clave_contrato = clave_fidei;
                String cc_espacios = "";
                int cc_num_espacios = 0;
                // Obtenemos longitud de clave de contrato
                if (clave_fidei.length() < 30) {
                    cc_num_espacios = 30 - clave_fidei.length();
                    // Abarcamos con espacios las posiciones faltantes
                    for (int i = 0; i < cc_num_espacios; i++) {
                        cc_espacios += " ";
                    }
                    LO_clave_contrato = clave_fidei + cc_espacios;
//                    LO_clave_contrato = clave_fidei;
                }
                // Validamos datos y creamos líneas de Lay-Out
                if (!LO_tipo_movimiento.equals("") && !LO_cuenta_deposito.equals("") && !LO_cuenta_origen.equals("")
                        && !LO_tipo_moneda.equals("") && !LO_importe_liquidacion.equals("") && !LO_clave_contrato.equals("")) {
                    // Formamos línea de movimiento
                    linea = LO_cuenta_deposito + LO_cuenta_origen + LO_tipo_moneda + LO_importe_liquidacion + LO_clave_contrato;
                    // Agregamos línea al reporte

                    reporte.add(linea);
                } else {
                    reporte = null;
                    break;
                }
            }//Fin de Transacciones

            //Verificamos si se realizo alguna transacción
            if (reporte.size() > 0) {
                System.out.println("MOVIMIENTOS DE CUENTAS BANCOMER GENERADOS CORRECTAMENTE");
            }
            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }

        } catch (Exception e) {
            reporte = null;
            if (connection != null) {
                conn.Desconecta(connection);
            }
            System.out.println("getMovesBancomer" + e.getMessage());
        }
        return reporte;
    }

    /**
     * Método que regresa la clave de banco de acuerdo al nuevo catálogo de
     * Banxico.
     *
     * @param banco : Banco.
     * @return Clave de Banco: Clave de banco de acuerdo a catálogo de Banxico.
     */
    public static String getClaveBanxico(String banco) {
        String ClaveBanxico = "0";
        int claveBanco = 0;

        // Convertimos cadena a entero para seleccionar clave de Banxico
        claveBanco = Integer.parseInt(banco);

        switch (claveBanco) {
            case 0:
                ClaveBanxico = "000";
                break;
            case 1:
                ClaveBanxico = "012";
                break;
            case 2:
                ClaveBanxico = "614";
                break;
            case 3:
                ClaveBanxico = "621";
                break;
            case 4:
                ClaveBanxico = "622";
                break;
            case 5:
                ClaveBanxico = "062";
                break;
            case 6:
                ClaveBanxico = "638";
                break;
            case 7:
                ClaveBanxico = "103";
                break;
            case 8:
                ClaveBanxico = "128";
                break;
            case 9:
                ClaveBanxico = "127";
                break;
            case 10:
                ClaveBanxico = "610";
                break;
            case 11:
                ClaveBanxico = "030";
                break;
            case 12:
                ClaveBanxico = "002";
                break;
            case 13:
                ClaveBanxico = "086";
                break;
            case 14:
                ClaveBanxico = "006";
                break;
            case 15:
                ClaveBanxico = "137";
                break;
            case 16:
                ClaveBanxico = "138";
                break;
            case 17:
                ClaveBanxico = "130";
                break;
            case 18:
                ClaveBanxico = "142";
                break;
            case 19:
                ClaveBanxico = "140";
                break;
            case 20:
                ClaveBanxico = "600";
                break;
            case 21:
                ClaveBanxico = "136";
                break;
            case 22:
                ClaveBanxico = "134";
                break;
            case 23:
                ClaveBanxico = "019";
                break;
            case 24:
                ClaveBanxico = "106";
                break;
            case 25:
                ClaveBanxico = "009";
                break;
            case 26:
                ClaveBanxico = "072";
                break;
            case 27:
                ClaveBanxico = "058";
                break;
            case 28:
                ClaveBanxico = "166";
                break;
            case 29:
                ClaveBanxico = "060";
                break;
            case 30:
                ClaveBanxico = "001";
                break;
            case 31:
                ClaveBanxico = "129";
                break;
            case 32:
                ClaveBanxico = "000";
                break;
            case 33:
                ClaveBanxico = "000";
                break;
            case 34:
                ClaveBanxico = "107";
                break;
            case 35:
                ClaveBanxico = "600";
                break;
            case 36:
                ClaveBanxico = "644";
                break;
            case 37:
                ClaveBanxico = "143";
                break;
            case 38:
                ClaveBanxico = "126";
                break;
            case 39:
                ClaveBanxico = "124";
                break;
            case 40:
                ClaveBanxico = "626";
                break;
            case 41:
                ClaveBanxico = "000";
                break;
            case 42:
                ClaveBanxico = "131";
                break;
            case 43:
                ClaveBanxico = "616";
                break;
            case 44:
                ClaveBanxico = "000";
                break;
            case 45:
                ClaveBanxico = "601";
                break;
            case 46:
                ClaveBanxico = "000";
                break;
            case 47:
                ClaveBanxico = "168";
                break;
            case 48:
                ClaveBanxico = "629";
                break;
            case 49:
                ClaveBanxico = "021";
                break;
            case 50:
                ClaveBanxico = "036";
                break;
            case 51:
                ClaveBanxico = "902";
                break;
            case 52:
                ClaveBanxico = "116";
                break;
            case 53:
                ClaveBanxico = "037";
                break;
            case 54:
                ClaveBanxico = "611";
                break;
            case 55:
                ClaveBanxico = "059";
                break;
            case 56:
                ClaveBanxico = "032";
                break;
            case 57:
                ClaveBanxico = "110";
                break;
            case 58:
                ClaveBanxico = "640";
                break;
            case 59:
                ClaveBanxico = "619";
                break;
            case 60:
                ClaveBanxico = "602";
                break;
            case 61:
                ClaveBanxico = "615";
                break;
            case 62:
                ClaveBanxico = "042";
                break;
            case 63:
                ClaveBanxico = "132";
                break;
            case 64:
                ClaveBanxico = "613";
                break;
            case 65:
                ClaveBanxico = "135";
                break;
            case 66:
                ClaveBanxico = "637";
                break;
            case 67:
                ClaveBanxico = "000";
                break;
            case 68:
                ClaveBanxico = "620";
                break;
            case 69:
                ClaveBanxico = "133";
                break;
            case 70:
                ClaveBanxico = "014";
                break;
            case 71:
                ClaveBanxico = "044";
                break;
            case 72:
                ClaveBanxico = "646";
                break;
            case 73:
                ClaveBanxico = "623";
                break;
            case 74:
                ClaveBanxico = "633";
                break;
            case 75:
                ClaveBanxico = "102";
                break;
            case 76:
                ClaveBanxico = "607";
                break;
            case 77:
                ClaveBanxico = "108";
                break;
            case 78:
                ClaveBanxico = "139";
                break;
            case 79:
                ClaveBanxico = "618";
                break;
            case 80:
                ClaveBanxico = "617";
                break;
            case 81:
                ClaveBanxico = "605";
                break;
            case 82:
                ClaveBanxico = "631";
                break;
            case 83:
                ClaveBanxico = "113";
                break;
            case 84:
                ClaveBanxico = "608";
                break;
            case 85:
                ClaveBanxico = "141";
                break;
            case 86:
                ClaveBanxico = "627";
                break;
            case 87:
                ClaveBanxico = "628";
                break;
            case 88:
                ClaveBanxico = "000";
                break;
        }

        return ClaveBanxico;
    }

    /**
     * Método que regresa todos los movimientos de Bancomer a Bancomer
     * (Movimientos de tipo 1) del cliente , contrato y fecha de liquidación que
     * se le pasan como parámetro. Cada movimiento arrojado cuenta con el
     * formato correspondiente.
     *
     * @param cliente : Nombre del cliente.
     * @param clave_contrato : clave del contrato del cliente anterior.
     * @param mifecha : Fecha de Liquidación .
     * @param nombre_archivo : Nombre del Lay-Out al que corresponden los
     * movimientos.
     * @return Vector movimientos: Conjunto de movimientos de Bancomer a
     * Bancomer con formato. Si surge algun error en la ejecución se regresa
     * null, si no se cuenta con movimientos de este tipo entonces se regresa un
     * vector vacio y si hay movimientos de este tipo entonces se regresa un
     * vector de Strings con un movimiento por cada nodo.
     */
    public static Vector getBancomerToBancomer(String cliente, String clave_contrato, String miFecha, String nombre_archivo) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;

        BigDecimal val = BigDecimal.ZERO;
        Vector reporte = new Vector();
        String cuenta_destino = "";
        String cuenta_origen = "";
        String clave_moneda = "";
        String nombreFidei = "";
        sumM1 = BigDecimal.ZERO;
        String importe = "";
        String linea = "";
        String clave_fid_temp = "";
        String MySql = "";
        hay_mov1 = false;
        String tmp = "";
        int idx = 0;

        clave_fid_temp = clave_contrato;
        if (clave_fid_temp.length() != 13) {//Nunca sucedera ya que por regla es de longitud 13
            int longitud = clave_fid_temp.length();
            if (longitud < 13) {
                String temp = "";
                for (int y = 1; y <= (13 - longitud); y++) {
                    temp = temp + " ";
                }
                clave_fid_temp = temp + clave_fid_temp;
            } else {
                clave_fid_temp = clave_fid_temp.substring(0, 13);
            }
        }
        final String REF_CLAVE_FID = clave_fid_temp;
        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select cuentas.num_cuenta , l.nombre_empleado , l.apellidoP_empleado , l.apellidoM_empleado ,   ";
            MySql += " l.cuenta_deposito , l.importe_liquidacion , l.tipo_moneda  ";
            MySql += " from movimientos_h h , movimientos l , contratos contratos , cuentas_banco cuentas ";
            MySql += " where h.clave_contrato = l.clave_contrato ";
            MySql += " and l.clave_contrato = contratos.clave_contrato ";
            MySql += " and h.fecha_liquidacion = l.fecha_liquidacion ";
            MySql += " and h.nombre_archivo=l.nombre_archivo ";
            MySql += " and contratos.cuenta_origen = cuentas.cuenta_origen ";
            MySql += " and contratos.clave_contrato = '" + clave_contrato + "'";
            MySql += " and l.fecha_liquidacion = '" + miFecha + " 00:00:00.000" + "'";
            MySql += " and contratos.nombre_cliente = '" + cliente + "'";
            MySql += " and h.nombre_archivo = '" + nombre_archivo + "'";
            MySql += " and l.tipo_movimiento = 1 ";
            MySql += " and contratos.status = 'A' ";
            MySql += " and cuentas.status = 'A' ";
            MySql += " and h.status = 'A'  ";
            MySql += " order by l.nombre_empleado asc ";
//            System.out.println(MySql);
            ResultSet rstSQLServer = statement.executeQuery(MySql);

            while (rstSQLServer.next()) { //Inicio de Transacciones
                tmp = "";
                cuenta_origen = rstSQLServer.getString(1).toString().trim(); // Cuenta de Origen
                nombreFidei = rstSQLServer.getString(2).toString().trim() + " "; //Nombre del Empleado
                nombreFidei += rstSQLServer.getString(3).toString().trim() + " ";//Apellido Paterno del Empleado
                nombreFidei += rstSQLServer.getString(4).toString().trim(); //Apellido Materno del Empleado

                cuenta_destino = rstSQLServer.getString(5).toString().trim(); //Cuenta a Depositar

                val = new BigDecimal(rstSQLServer.getString(6).toString().trim()); // Importe de Liquidación
                sumM1 = sumM1.add(val);
                importe = val + "";
                idx = importe.indexOf(".");
                if ((importe.length() - (idx + 1)) > 1) {
                    importe = importe.substring(0, idx + 3);
                } else {
                    importe = importe.substring(0, idx + 1) + "00";
                }
                clave_moneda = rstSQLServer.getString(7);  //Tipo de Moneda

                if (cuenta_origen.length() != 13) {
                    cuenta_origen = ""; //Este caso en teoría no pude pasar, se cacha en el sistema gestionCuentas
                }
                if (nombreFidei.length() <= 30) {
                    idx = 30 - nombreFidei.length();
                    for (int i = 0; i < idx; i++) {
                        nombreFidei += " ";
                    }
                } else {
                    nombreFidei = nombreFidei.substring(0, 30);
                }
                if (cuenta_destino.length() != 10) {
                    cuenta_destino = ""; // Este caso en teoría no pude pasar porque se valido anteriormente al cargar el LayOut.
                } else {
                    cuenta_destino = "910" + cuenta_destino;
                }
                if (importe.length() <= 11) {
                    idx = 11 - importe.length();
                    for (int i = 0; i < idx; i++) {
                        tmp += " ";
                    }
                    importe = tmp + importe;
                } else {
                    importe = ""; //La posiblilidad de este caso es nula, un cliente no tiene un importe muy alto pata este mov.
                }
                if (clave_moneda.length() != 3) {
                    clave_moneda = ""; //No se pude dar este caso ya que todas las claves existentes son de 3 dígitos.
                }

                if (!cuenta_origen.equals("") && !nombreFidei.equals("") && !importe.equals("") && !clave_moneda.equals("")
                        && !cuenta_destino.equals("") && !clave_fid_temp.equals("")) {
                    linea = cuenta_origen + nombreFidei + cuenta_destino + importe + clave_moneda + REF_CLAVE_FID;
                    reporte.add(linea);
                } else {
                    reporte = null;
                    break;
                }
            }//Fin de Transacciones
            //Verificamos si se realizo alguna transacción
            if (reporte.size() > 0) {
                hay_mov1 = true;
            }
            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            reporte = null;
            hay_mov1 = false;
            if (connection != null) {
                conn.Desconecta(connection);
            }
            System.out.println("getBancomerToBancomer" + e.getMessage());
        }
        return reporte;
    }

    /**
     * Método que regresa todos los movimientos de Bancomer a Otros Bancos
     * (Movimiento tipo 2) del cliente , contrato y fecha de liquidación que se
     * le pasan como parámetro. Cada elemento del vector tiene el formato
     * estipulado.
     *
     * @param cliente : Nombre del cliente.
     * @param clave_contrato : Clave del contrato del cliente anterior.
     * @param mifecha : Fecha de Liquidación .
     * @param nombre_archivo : Nombre del Lay-Out al que corresponden los
     * movimientos.
     * @return Vector movimientos: Conjunto de movimientos de Bancomer a otros
     * Bancos. Si surge algun error en la ejecución se regresa null, si no se
     * cuenta con movimientos de este tipo entonces se regresa un vector vacio y
     * si hay movimientos de este tipo entonces se regresa un vector de Strings
     * con un movimiento por cada nodo.
     */
    public static Vector getOtrosBancos(String cliente, String clave_contrato, String miFecha, String nombre_archivo) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;

        Vector movimientos = new Vector();
        BigDecimal val = BigDecimal.ZERO;
        sumM2 = BigDecimal.ZERO;
        String cuenta_destino = "";
        String cuenta_origen = "";
        String clave_moneda = "";
        String nombreFidei = "";
        String importe = "";
        String MySql = "";
        String linea = "";
        String tmp = "";
        hay_mov2 = false;
        int idx = 0;
        String clave_fid_temp = "";

        clave_fid_temp = clave_contrato;
        if (clave_fid_temp.length() != 13) {//Nunca sucedera ya que por regla es de longitud 13
            int longitud = clave_fid_temp.length();
            if (longitud < 13) {
                String temp = "";
                for (int y = 1; y <= (13 - longitud); y++) {
                    temp = temp + " ";
                }
                clave_fid_temp = temp + clave_fid_temp;
            } else {
                clave_fid_temp = clave_fid_temp.substring(0, 13);
            }
        }
        final String REF_CLAVE_FID = clave_fid_temp;

        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select l.cuenta_deposito , cuentas.num_cuenta , l.nombre_empleado , l.apellidoP_empleado , l.apellidoM_empleado , ";
            MySql += " l.importe_liquidacion , l.tipo_moneda ";
            MySql += " from movimientos_h h , movimientos l , contratos contratos , cuentas_banco cuentas ";
            MySql += " where h.clave_contrato = l.clave_contrato ";
            MySql += " and l.clave_contrato = contratos.clave_contrato ";
            MySql += " and h.fecha_liquidacion = l.fecha_liquidacion ";
            MySql += " and h.nombre_archivo=l.nombre_archivo ";
            MySql += " and contratos.cuenta_origen = cuentas.cuenta_origen ";
            MySql += " and contratos.clave_contrato = '" + clave_contrato + "'";
            MySql += " and l.fecha_liquidacion = '" + miFecha + " 00:00:00.000" + "'";
            MySql += " and contratos.nombre_cliente = '" + cliente + "'";
            MySql += " and h.nombre_archivo='" + nombre_archivo + "'";
            MySql += " and l.tipo_movimiento = 2 ";
            MySql += " and contratos.status = 'A' ";
            MySql += " and cuentas.status = 'A' ";
            MySql += " and h.status = 'A'  ";
            MySql += " order by l.nombre_empleado asc ";

//            System.out.println(MySql);
            ResultSet rstSQLServer = statement.executeQuery(MySql);

            while (rstSQLServer.next()) {//Inicio de Transacciones
                tmp = "";
                cuenta_destino = rstSQLServer.getString(1).toString().trim();
                cuenta_origen = rstSQLServer.getString(2).toString().trim();
                nombreFidei = rstSQLServer.getString(3).toString().trim() + " ";
                nombreFidei += rstSQLServer.getString(4).toString().trim() + " ";
                nombreFidei += rstSQLServer.getString(5).toString().trim();
                val = new BigDecimal(rstSQLServer.getString(6).toString().trim());
                sumM2 = sumM2.add(val);

                importe = val + "";
                idx = importe.indexOf(".");
                if ((importe.length() - (idx + 1)) > 1) {
                    importe = importe.substring(0, idx + 3);
                } else {
                    importe = importe.substring(0, idx + 1) + "00";
                }
                clave_moneda = rstSQLServer.getString(7).toString().trim();

                if (cuenta_destino.length() != 18) {
                    cuenta_destino = ""; //No se puede dar este caso ya que se valido previamente en el LayOut
                }

                if (cuenta_origen.length() != 13) {
                    cuenta_origen = ""; //No se puede dar este caso, se valida en el sistema gestion de Cuentas
                }

                if (nombreFidei.length() <= 30) {
                    idx = 30 - nombreFidei.length();
                    for (int i = 0; i < idx; i++) {
                        nombreFidei += " ";
                    }
                } else {
                    nombreFidei = nombreFidei.substring(0, 30);
                }

                if (importe.length() <= 16) {
                    idx = 16 - importe.length();
                    for (int i = 0; i < idx; i++) {
                        tmp += " ";
                    }
                    importe = tmp + importe;
                } else {
                    importe = ""; //No se valida a petición de Carlos Altamirano, validación no necesaria.
                }
                if (clave_moneda.length() != 3) {
                    clave_moneda = ""; //Todas las claves son de 3 digitos por lo que este caso no es posible.
                }

                if (!cuenta_origen.equals("") && !nombreFidei.equals("") && !importe.equals("") && !clave_moneda.equals("")
                        && !cuenta_destino.equals("") && !clave_fid_temp.equals("")) {
                    linea = cuenta_destino + cuenta_origen + nombreFidei + importe + clave_moneda + REF_CLAVE_FID;
                    movimientos.add(linea);
                } else {
                    movimientos = null;
                    break;
                }
            }//Fin de Transacciones
            //Verificamos si hay algun movimiento generado.
            if (movimientos.size() > 0) {
                hay_mov2 = true;
            }
            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }

        } catch (Exception e) {
            movimientos = null;
            hay_mov2 = false;
            if (connection != null) {
                conn.Desconecta(connection);
            }
            System.out.println("getOtrosBancos:" + e.getMessage());
        }
        return movimientos;
    }

    /**
     * Método que regresa todos los movimientos de Bancomer a TDD Bancomer
     * (Movimiento tipo 3) del cliente , contrato y fecha de liquidación que se
     * le pasan como parámetro. Cada movimiento tiene el formato estipulado.
     *
     * @param cliente : Nombre del cliente.
     * @param clave_contrato : clave del contrato del cliente anterior.
     * @param mifecha : Fecha de Liquidación .
     * @param nombre_archivo : Nombre del Lay-Out al que corresponden los
     * movimientos.
     * @return Vector movimientos: Conjunto de movimientos de Bancomer a TDD
     * Bancomer. Si surge algun error en la ejecución se regresa null, si no se
     * cuenta con movimientos de este tipo entonces se regresa un vector vacio y
     * si hay movimientos de este tipo entonces se regresa un vector de Strings
     * con un movimiento por cada nodo.
     */
    public static Vector getTDDBanamex(String cliente, String clave_contrato, String miFecha, String nombre_archivo) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;

        Vector movimientos = new Vector();
        BigDecimal val = BigDecimal.ZERO;
        String cuenta_destino = "";
        String cuenta_origen = "";
        String clave_moneda = "";
        String nombreFidei = "";
        sumM3 = BigDecimal.ZERO;
        String importe = "";
        String MySql = "";
        String linea = "";
        String tmp = "";
        hay_mov3 = false;
        int idx = 0;
        String clave_fid_temp = "";

        clave_fid_temp = clave_contrato;
        if (clave_fid_temp.length() != 13) {//Nunca sucedera ya que por regla es de longitud 13
            int longitud = clave_fid_temp.length();
            if (longitud < 13) {
                String temp = "";
                for (int y = 1; y <= (13 - longitud); y++) {
                    temp = temp + " ";
                }
                clave_fid_temp = temp + clave_fid_temp;
            } else {
                clave_fid_temp = clave_fid_temp.substring(0, 13);
            }
        }
        final String REF_CLAVE_FID = clave_fid_temp;

        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select l.cuenta_deposito  , cuentas.num_cuenta , l.tipo_moneda , l.importe_liquidacion  ,   ";
            MySql += "  l.nombre_empleado , l.apellidoP_empleado , l.apellidoM_empleado ";
            MySql += " from movimientos_h h , movimientos l , contratos contratos , cuentas_banco cuentas ";
            MySql += " where h.clave_contrato = l.clave_contrato ";
            MySql += " and l.clave_contrato = contratos.clave_contrato ";
            MySql += " and h.fecha_liquidacion = l.fecha_liquidacion ";
            MySql += "  and h.nombre_archivo=l.nombre_archivo ";
            MySql += " and contratos.cuenta_origen = cuentas.cuenta_origen ";
            MySql += " and contratos.clave_contrato = '" + clave_contrato + "'";
            MySql += " and l.fecha_liquidacion = '" + miFecha + " 00:00:00.000" + "'";
            MySql += " and contratos.nombre_cliente = '" + cliente + "'";
            MySql += " and h.nombre_archivo= '" + nombre_archivo + "'";
            MySql += " and l.tipo_movimiento = 3 ";
            MySql += " and contratos.status = 'A' ";
            MySql += " and cuentas.status = 'A' ";
            MySql += " and h.status = 'A'  ";
            MySql += " order by l.nombre_empleado asc ";

//            System.out.println(MySql);
            ResultSet rstSQLServer = statement.executeQuery(MySql);
            movimientos = new Vector();

            while (rstSQLServer.next()) { //Inicio de Transacciones
                tmp = "";
                cuenta_destino = "00" + rstSQLServer.getString(1).toString().trim();
                cuenta_origen = rstSQLServer.getString(2).toString().trim();
                clave_moneda = rstSQLServer.getString(3).toString().trim();

                val = new BigDecimal(rstSQLServer.getString(4).toString().trim());
                sumM3 = sumM3.add(val);

                importe = val + "";
                idx = importe.indexOf(".");
                if ((importe.length() - (idx + 1)) > 1) {
                    importe = importe.substring(0, idx + 3);
                } else {
                    importe = importe.substring(0, idx + 1) + "00";
                }

                nombreFidei = rstSQLServer.getString(5).toString().trim() + " ";
                nombreFidei += rstSQLServer.getString(6).toString().trim() + " ";
                nombreFidei += rstSQLServer.getString(7).toString().trim();

                if (cuenta_destino.length() != 18) {
                    cuenta_destino = ""; // No puede pasar por validación de LayOut y el prefijo 00
                }

                if (cuenta_origen.length() != 13) {
                    cuenta_origen = ""; //No puede pasar, se verifica en el sistema gestionCuentas
                }

                if (clave_moneda.length() != 3) {
                    clave_moneda = ""; //Todas las claves son de 3 digitos por lo que no se da este caso.
                }

                if (importe.length() <= 16) {
                    idx = 16 - importe.length();
                    for (int i = 0; i < idx; i++) {
                        tmp += " ";
                    }
                    importe = tmp + importe;
                } else {
                    importe = ""; //No se valida a petición de Carlos altamirano.
                }

                if (nombreFidei.length() <= 30) {
                    idx = 30 - nombreFidei.length();
                    for (int i = 0; i < idx; i++) {
                        nombreFidei += " ";
                    }
                } else {
                    nombreFidei = nombreFidei.substring(0, 30);
                }

                if (!cuenta_origen.equals("") && !nombreFidei.equals("") && !importe.equals("") && !clave_moneda.equals("")
                        && !cuenta_destino.equals("") && !clave_fid_temp.equals("")) {
                    linea = cuenta_destino + cuenta_origen + clave_moneda + importe + nombreFidei + "03002" + REF_CLAVE_FID;
                    movimientos.add(linea);
                } else {
                    movimientos = null;
                    break;
                }
            }//Fin de Transacciones
            if (movimientos.size() > 0) {
                hay_mov3 = true;
            }
            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            movimientos = null;
            hay_mov3 = false;
            if (connection != null) {
                conn.Desconecta(connection);
            }
            System.out.println("getTDDBanamex:" + e.getMessage());
        }
        return movimientos;
    }

    /**
     * Método que regresa todos los movimientos de las Emisiones de Cheques
     * (Movimiento tipo 4) del cliente , contrato y fecha de liquidación que se
     * le pasan como parámetro. Cada movimiento tiene el formato estipulado.
     *
     * @param cliente : Nombre del cliente.
     * @param clave_contrato : clave del contrato del cliente anterior.
     * @param mifecha : Fecha de Liquidación .
     * @param nombre_archivo : Nombre del Lay-Out al que corresponden los
     * movimientos.
     * @return Vector movimientos: Conjunto de movimientos de las Emisiones de
     * Cheques Si surge algun error en la ejecución se regresa null, si no se
     * cuenta con movimientos de este tipo entonces se regresa un vector vacio y
     * si hay movimientos de este tipo entonces se regresa un vector de Strings
     * con un movimiento por cada nodo.
     */
    public static Vector getEmisionCheches(String cliente, String clave_contrato, String miFecha, String nombre_archivo) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;

        BigDecimal val = BigDecimal.ZERO;
        Vector movimientos = null;
        String nombreFidei = "";
        String curp = "";
        String importe = "";
        String linea = "";
        String tmp = "";
        String MySql = "";
        String fecha = "";
        int idx = 0;

        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            //Obtenemos la info necesaria para generar el correspondiente Lay-Out.
            MySql = "  select l.nombre_empleado , l.apellidoP_empleado , l.apellidoM_empleado , ";
            MySql += " l.importe_liquidacion , l.curp   ";
            MySql += " from movimientos_h h , movimientos l , contratos contratos , cuentas_banco cuentas ";
            MySql += " where h.clave_contrato = l.clave_contrato ";
            MySql += " and l.clave_contrato = contratos.clave_contrato ";
            MySql += " and h.fecha_liquidacion = l.fecha_liquidacion ";
            MySql += " and h.nombre_archivo = l.nombre_archivo ";
            MySql += " and contratos.cuenta_origen = cuentas.cuenta_origen ";
            MySql += " and contratos.clave_contrato = '" + clave_contrato + "'";
            MySql += " and l.fecha_liquidacion = '" + (new clsFecha().CambiaFormatoFecha("dd/MM/yyyy", "yyyy-MM-dd", miFecha)) + " 00:00:00.000" + "'";
            MySql += " and contratos.nombre_cliente = '" + cliente + "'";
            MySql += " and h.nombre_archivo = '" + nombre_archivo + "'";
            MySql += " and l.tipo_movimiento = 4 ";
            MySql += " and contratos.status = 'A' ";
            MySql += " and cuentas.status = 'A' ";
            MySql += " and h.status = 'A'  ";
            MySql += " order by l.nombre_empleado asc ";
//            System.out.println(MySql);
            ResultSet rstSQLServer = statement.executeQuery(MySql);

            fecha = ModeloLiquidation.getFormatoFecha(miFecha);
            movimientos = new Vector();
            while (rstSQLServer.next()) { //Inicio de Transacciones
                tmp = "";
                nombreFidei = rstSQLServer.getString(1).toString().trim() + " ";
                nombreFidei += rstSQLServer.getString(2).toString().trim() + " ";
                nombreFidei += rstSQLServer.getString(3).toString().trim();

                val = new BigDecimal(rstSQLServer.getString(4).toString().trim());
                importe = val + "";
                idx = importe.indexOf(".");
                if ((importe.length() - (idx + 1)) > 1) {
                    importe = importe.substring(0, idx + 3);
                } else {
                    importe = importe.substring(0, idx + 1) + "00";
                }

                curp = rstSQLServer.getString(5).toString().trim();

                if (nombreFidei.length() <= 60) {
                    idx = 60 - nombreFidei.length();
                    for (int i = 0; i < idx; i++) {
                        nombreFidei += " ";
                    }
                } else {
                    nombreFidei = nombreFidei.substring(0, 60);
                }

                if (importe.length() <= 28) {
                    idx = 28 - importe.length();
                    for (int i = 0; i < idx; i++) {
                        tmp += " ";
                    }
                    importe = tmp + importe;
                } else {
                    importe = ""; //No se valida a petición del usuario.
                }

                if (!nombreFidei.equals("") && !fecha.equals("") && !importe.equals("")
                        && !curp.equals("")) {
                    linea = "     " + nombreFidei + fecha + importe + curp;
                    movimientos.add(linea);
                } else {
                    movimientos = null;
                    break;
                }
            }//Fin de Transacciones

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
            System.out.println("getEmisionCheches:" + e.getMessage());
        }
        return movimientos;
    }

    /**
     * Método que regresa el nombre de todos los fideicomisarios que tiene
     * movimientos de tipo 5 en base a los datos que se pasan como parámetro.
     * (Movimientos de tipo 5)
     *
     * @param n_cliente : Nombre del cliente.
     * @param clave_contrato : clave del contrato del cliente anterior.
     * @param mifecha : Fecha de Liquidación .
     * @param nombre_archivo : Nombre del Lay-Out al que corresponden los
     * movimientos.
     * @return Vector movimientos: Fideicomisarios.
     */
    public static Vector getBancosExtranjeros(String n_cliente, String clave_contrato, String miFecha, String nombre_archivo) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        Vector mov5 = new Vector();
        String MySql = "";

        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select l.nombre_fidei_banco_ext  ";
            MySql += " from movimientos_h h , movimientos l , contratos contratos , cuentas_banco cuentas   ";
            MySql += " where h.clave_contrato = l.clave_contrato  ";
            MySql += " and l.clave_contrato = contratos.clave_contrato  ";
            MySql += " and h.fecha_liquidacion = l.fecha_liquidacion  ";
            MySql += " and h.nombre_archivo = l.nombre_archivo  ";
            MySql += " and contratos.cuenta_origen = cuentas.cuenta_origen  ";
            MySql += " and contratos.clave_contrato = '" + clave_contrato + "'";
            MySql += " and l.fecha_liquidacion = '" + (new clsFecha().CambiaFormatoFecha("dd/MM/yyyy", "yyyy-MM-dd", miFecha)) + " 00:00:00.000" + "'";
            MySql += " and contratos.nombre_cliente = '" + n_cliente + "'";
            MySql += " and h.nombre_archivo = '" + nombre_archivo + "'";
            MySql += " and l.tipo_movimiento = 5   ";
            MySql += " and contratos.status = 'A'  ";
            MySql += " and cuentas.status = 'A'  ";
            MySql += " and h.status = 'A'  ";
            MySql += " order by l.nombre_fidei_banco_ext asc ";

//            System.out.println(MySql);
            ResultSet rstSQLServer = statement.executeQuery(MySql);

            while (rstSQLServer.next()) {
                //Nombre del Fideicomisario
                mov5.add(rstSQLServer.getString(1).toString().trim()); //Nombre del Fideicomisario
            }
            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            mov5 = null;
            System.out.println("getBancosExtranjeros:" + e.getMessage());
        }
        return mov5;
    }

    /**
     * Método que se encarga de generar el LayOut NetCash correspondiente a los
     * datos que se le pasan como parámetro.
     *
     * @param Vector enume : Movimientos generados con formato.
     * @param clave_contrato : clave del contrato del cliente correspondiente a
     * los movimientos anteriores.
     * @param fecha_liq : Fecha de Liquidación .
     */
    public synchronized boolean escribirNetcash(Vector enume, String clave_contrato, String fecha_liq, String ext, String urlArchivo, int verifica, String type_netcash, String countArchivoOut) throws Exception {

        PrintWriter pw = null;
        File reporte = null;
        boolean seGuardo = false;
        String clave_fideicomitente = "";
        String fecha = "";
        int idx = 0;

        try {
            idx = enume.size();
            if (idx > 0) {
                //Damos formato a la fecha de liquidación
                fecha = ModeloLiquidation.getFormatoFecha(fecha_liq);
                if (!fecha.equals("")) {
                    if (verifica >= 0 && clave_contrato.length() >= 9) {
                        clave_fideicomitente = clave_contrato.substring(6, 9);

                        // Creamos reporte 
                        reporte = new File(urlArchivo + clave_fideicomitente + "-0" + verifica + "-" + type_netcash + countArchivoOut + fecha + ext);

                        reporte.delete();

                        pw = new PrintWriter(new FileOutputStream(reporte));
                        for (int i = 0; i < idx; i++) {
                            String linea = (String) enume.get(i);
                            pw.println(linea);
                        }

                        seGuardo = true;

                    } else {
                        seGuardo = false;
                    }
                } else {
                    seGuardo = false;
                }
            } else {
                seGuardo = false;
            }

        } catch (Exception e) {
            System.out.println("escribir:" + e.getMessage());
            seGuardo = false;
        } finally {
            if (idx > 0) {
                pw.close();
            }
        }
        return seGuardo;
    }

    /**
     * Método que se encarga de generar el LayOut correspondiente a los datos
     * que se le pasan como parámetro.
     *
     * @param Vector enume : Movimientos generados con formato.
     * @param clave_contrato : clave del contrato del cliente correspondiente a
     * los movimientos anteriores.
     * @param fecha_liq : Fecha de Liquidación .
     * @param String mov: Tipo de movimiento correspondiente a los movimientos
     * anteriores.
     * @return boolean:true si se genero el archivo correspondiente con los
     * movimientos, else en otro caso.
     */
    public synchronized boolean escribir1Archivo(Vector enume, String clave_contrato, String fecha_liq, String mov, String ext, String urlArchivo, int verifica) throws Exception {

        PrintWriter pw = null;
        File reporte = null;
        boolean seGuardo = false;
        String clave_fideicomitente = "";
        String fecha = "";
        int idx = 0;

        try {
            idx = enume.size();
            if (idx > 0) {
                //Damos formato a la fecha de liquidación
                fecha = ModeloLiquidation.getFormatoFecha(fecha_liq);
                if (!fecha.equals("")) {
                    if (verifica >= 0 && clave_contrato.length() >= 9) {
                        clave_fideicomitente = clave_contrato.substring(6, 9);
                        if (verifica > 0 && verifica <= 9) {
                            //Si es movimiento tipo 4 damos nombre especifico al archivo de salida: CIDDMMYY.N01
                            if (mov.equals("CH")) {
                                reporte = new File(urlArchivo + "CI" + "0" + verifica + fecha + ext);
                            } else {
                                reporte = new File(urlArchivo + clave_fideicomitente + "-0" + verifica + "-" + mov + "-01-" + fecha + ext);
                            }
                        } else //Si es movimiento tipo 4 damos nombre especifico al archivo de salida: CIDDMMYY.N01
                        {
                            if (mov.equals("CH")) {
                                reporte = new File(urlArchivo + "CI" + verifica + fecha + ext);
                            } else {
                                reporte = new File(urlArchivo + clave_fideicomitente + "-" + verifica + "-" + mov + "-01-" + fecha + ext);
                            }
                        }
                        reporte.delete();
                        pw = new PrintWriter(new FileOutputStream(reporte));
                        for (int i = 0; i < idx; i++) {
                            String linea = (String) enume.get(i);
                            pw.println(linea);
                        }
                        seGuardo = true;
                    } else {
                        seGuardo = false;
                    }
                } else {
                    seGuardo = false;
                }
            } else {
                seGuardo = false;
            }
        } catch (Exception e) {
            System.out.println("escribir:" + e.getMessage());
            seGuardo = false;
        } finally {
            if (idx > 0) {
                pw.close();
            }
        }
        return seGuardo;
    }

    /**
     * Método que se encarga de generar el LayOut correspondiente a los datos
     * que se le pasan como parámetro. Estos generados en varios lotes de N
     * movimientos cada uno
     *
     * @param Vector enume : Movimientos generados con formato.
     * @param clave_contrato : clave del contrato del cliente correspondiente a
     * los movimientos anteriores.
     * @param fecha_liq : Fecha de Liquidación .
     * @param String mov: Tipo de movimiento correspondiente a los movimientos
     * anteriores.
     * @return boolean:true si se genero el archivo correspondiente con los
     * movimientos, else en otro caso.
     */
    public synchronized boolean escribirEnPartes(Vector enume, String clave_contrato, String fecha_liq, String mov, String ext, String urlArchivo, int verifica) throws Exception {

        PrintWriter pw = null;
        File reporte = null;
        boolean seGuardo = false;
        String clave_fideicomitente = "";
        int countArchivos = 1;
        int countMovs = 0;
        String noArchivoFideicomiso = "", noArchivo = "";
        int countMovimientosIn = 1;
        int countMovimientosEx = 1;
        int numArchivosTotal = 0;
        final float sizeFile = 200;
        int movsTotal = 0;
        String fecha = "";

        try {
            movsTotal = enume.size();
            if (movsTotal > 0) {
                //Damos formato a la fecha de liquidación
                fecha = ModeloLiquidation.getFormatoFecha(fecha_liq);
                if (!fecha.equals("")) {
                    if (verifica >= 0 && clave_contrato.length() >= 9) {
                        //Si es movimiento de CHEQUES, no se crearán varios archivos, solo se crea un .NO1
                        if (mov.equals("CH")) {
                            noArchivoFideicomiso = (verifica > 0 && verifica <= 9) ? ("0" + verifica + "-") : ("-" + verifica + "-");
                            reporte = new File(urlArchivo + "CI" + noArchivoFideicomiso + fecha + ext);
                            reporte.delete();
                            pw = new PrintWriter(new FileOutputStream(reporte));
                            for (int i = 0; i < movsTotal; i++) {
                                String linea = (String) enume.get(i);
                                pw.println(linea);
                            }
                            seGuardo = true;
                        }//SI NO ENTONCES ES UN TIPO DE MOVIMIENTO 1,2, ó 3
                        else {
                            clave_fideicomitente = clave_contrato.substring(6, 9);
                            numArchivosTotal = (int) (Math.ceil(movsTotal / sizeFile));
                            noArchivoFideicomiso = (verifica > 0 && verifica <= 9) ? ("-0" + verifica + "-") : ("-" + verifica + "-");
                            //Se escriben las lineas en los archivos necesarios
                            for (int idxArchivoActual = 1; idxArchivoActual <= numArchivosTotal; idxArchivoActual++) {
                                noArchivo = (idxArchivoActual <= 9) ? ("-0" + idxArchivoActual + "-") : ("-" + idxArchivoActual + "-");
                                reporte = new File(urlArchivo + clave_fideicomitente + noArchivoFideicomiso + mov + noArchivo + fecha + ext);
                                reporte.delete();
                                pw = new PrintWriter(new FileOutputStream(reporte));
                                System.out.println("Comenzando escritura de archivo " + idxArchivoActual + " para el movimiento " + mov);
                                while (countMovimientosEx <= movsTotal && countMovimientosIn <= sizeFile) {
                                    String linea = (String) enume.get(countMovimientosEx - 1);
                                    pw.println(linea);
                                    countMovimientosIn++;
                                    countMovimientosEx++;
                                }
                                //SE inicializa el contador de lineas permitidas y se cierra el buffer de escritura al archivo actual
                                countMovimientosIn = 1;
                                pw.close();
                            }
                            System.out.println("Se han generado " + numArchivosTotal + " archivos para el movimiento " + mov);
                            seGuardo = true;
                        }
                    } else {
                        seGuardo = false;
                    }
                } else {
                    seGuardo = false;
                }
            } else {
                seGuardo = false;
            }
        } catch (Exception e) {
            System.out.println("escribir:" + e.getMessage());
            seGuardo = false;
        } finally {
            if (movsTotal > 0) {
                pw.close();
            }
        }
        return seGuardo;
    }

    /**
     * Método que GENERA LayOut de Cuentas Bancomer.
     *
     * @param cliente : Nombre del cliente.
     * @param clave_cliente : clave del cliente.
     * @param clave_contrato : clave del contrato del cliente.
     * @param mifecha : Fecha de la Liquidación.
     * @param verifica : Lotes procesados almacenados para este cliente y fecha.
     * @return boolean valido: Regresa true si se creó correctamente LayOut
     * Cuentas Bancomer.
     */
    public String creaLayOutNetcashCuentasBancomer(String cliente, String clave_contrato, String mifecha, String nombre_archivo, String urlArchivo, int verifica) {

        Vector moves = null;
        String seGuardo = "";
        String countFileOut = "";

        try {
            //Obtenemos los movimientos de tipo 1.
            moves = this.getMovesBancomerLAB(cliente, clave_contrato, mifecha, nombre_archivo);
            //Verificamos si surgió algún error al obtener los movimientos de Cuentas Bancomer
            if (moves.size() > 0) {
                // Creamos Lay-Out mixto de Netcash
                for (int numFile = 1; numFile <= moves.size(); numFile++) {
                    if (numFile <= 9) {
                        countFileOut = "-0" + numFile + "-";
                    } else {
                        countFileOut = "-" + numFile + "-";
                    }
                    Vector movsArchivoActual = new Vector();
                    movsArchivoActual = (Vector) moves.get((numFile - 1));
                    if (!escribirNetcash(movsArchivoActual, clave_contrato, mifecha, ".txt", urlArchivo, verifica, "NC-LAB-CB", countFileOut)) {
                        return seGuardo = "Error al generar el LayOut Alta Beneficiarias de Cuentas Bancomer en Archivo No=" + numFile;
                    }
                }
                moves.clear();
                moves = this.getMovesBancomer(cliente, clave_contrato, mifecha, nombre_archivo);
            } else {
                // No se generaron movimientos de Netcash
                System.out.println("NO HAY MOVIMIENTOS DE NETCASH Alta de Beneficiarias Cuentas Bancomer");
                return seGuardo = "Sin movimientos de Cuentas Bancomer";
            }

            if (moves.size() > 0) {
                // Creamos Lay-Out de Cuentas Bancomer
                if (!this.escribirNetcash(moves, clave_contrato, mifecha, ".txt", urlArchivo, verifica, "NC-LIG-PTC-CB", "-01-")) {
                    return seGuardo = "Error al generar el LayOut Importacion de Grupos de Cuentas Bancomer";
                }
            } else {
                // No se generaron movimientos de Cuentas Bancomer
                System.out.println("NO HAY MOVIMIENTOS DE CUENTAS BANCOMER");
                return seGuardo = "Sin movimientos de Cuentas Bancomer";
            }

        } catch (Exception e) {
            seGuardo = "Error al generar LayOut de Cuentas Bancomer";
            System.out.println("creaLayOutNetcashCuentasBancomer:" + e.getMessage());
        }
        return seGuardo;
    }

    /**
     * Método que GENERA LayOut de Cuentas Interbancarias(PSC).
     *
     * @param cliente : Nombre del cliente.
     * @param clave_cliente : clave del cliente.
     * @param clave_contrato : clave del contrato del cliente.
     * @param mifecha : Fecha de la Liquidación.
     * @param verifica : Lotes procesados almacenados para este cliente y fecha.
     * @return boolean valido: Regresa true si se creó correctamente LayOut de
     * Cuentas Interbancarias.
     */
    public String creaLayOutNetcashCuentasInterbancarias(String cliente, String clave_contrato, String mifecha, String nombre_archivo, String urlArchivo, int verifica) {

        Vector moves = null;
        String seGuardo = "";
        String countFileOut = "";

        try {
            //Obtenemos los movimientos de tipo 2 y 3

            moves = this.getMovesTwoThreeLAB(cliente, clave_contrato, mifecha, nombre_archivo);
            //Verificamos si surgió algún error al obtener los movimientos de Cuentas Interbancarias
            if (moves.size() > 0) {
                // Creamos Lay-Out mixto de Netcash
                for (int numFile = 1; numFile <= moves.size(); numFile++) {
                    if (numFile <= 9) {
                        countFileOut = "-0" + numFile + "-";
                    } else {
                        countFileOut = "-" + numFile + "-";
                    }
                    Vector movsArchivoActual = new Vector();
                    movsArchivoActual = (Vector) moves.get((numFile - 1));
                    if (!escribirNetcash(movsArchivoActual, clave_contrato, mifecha, ".txt", urlArchivo, verifica, "NC-LAB-CI", countFileOut)) {
                        return seGuardo = "Error al generar el layOut de Netcash Alta deBeneficiarias en Archivo No=" + numFile;
                    }
                }
                moves.clear();
                moves = this.getMovesTwoThree(cliente, clave_contrato, mifecha, nombre_archivo);
            } else {
                // No se generaron movimientos de Netcash
                System.out.println("NO HAY MOVIMIENTOS DE NETCASH CUENTAS INTERBANCARIAS");
                return seGuardo = "Sin movimientos de Cuentas Interbancarias";
            }

            if (moves.size() > 0) {
                // Creamos Lay-Out de Cuentas Interbancarias
                if (!this.escribirNetcash(moves, clave_contrato, mifecha, ".txt", urlArchivo, verifica, "NC-LIG-PSC-CI", "-01-")) {
                    return seGuardo = "Error al generar el LayOut de Cuentas Interbancarias";
                }
            } else {
                // No se generaron movimientos de Netcash
                System.out.println("NO HAY MOVIMIENTOS DE CUENTAS INTERBANCARIAS");
                return seGuardo = "Sin movimientos de Cuentas Interbancarias";
            }

        } catch (Exception e) {
            seGuardo = "Error al generar LayOut de Cuentas Interbancarias";
            System.out.println("creaLayOutNetcashCuentasInterbancarias:" + e.getMessage());
        }
        return seGuardo;
    }

    /**
     * Método que GENERA LayOut mixto de Netcash.
     *
     * @param cliente : Nombre del cliente.
     * @param clave_cliente : clave del cliente.
     * @param clave_contrato : clave del contrato del cliente.
     * @param mifecha : Fecha de la Liquidación.
     * @param verifica : Lotes procesados almacenados para este cliente y fecha.
     * @return boolean valido: Regresa true si se creó correctamente LayOut
     * mixto de Netcash.
     */
    public String creaLayOutNetcash(String cliente, String clave_contrato, String mifecha, String nombre_archivo, String urlArchivo, int verifica) {

        Vector moves = null;
        String seGuardo = "";
        String countFileOut = "";

        try {
            //Obtenemos los movimientos.
            moves = this.getAllMoves(cliente, clave_contrato, mifecha, nombre_archivo);

            //Verificamos si surgió algún error al obtener los movimientos de NetCash
            // for(int i=0;i<moves.size();i++){
            //   System.out.println("Vector:[" + i +"]="+moves.get(i));}
            if (moves.size() > 0) {
                // Creamos Lay-Out mixto de Netcash
                for (int numFile = 1; numFile <= moves.size(); numFile++) {
                    if (numFile <= 9) {
                        countFileOut = "-0" + numFile + "-";
                    } else {
                        countFileOut = "-" + numFile + "-";
                    }
                    Vector movsArchivoActual = new Vector();
                    movsArchivoActual = (Vector) moves.get((numFile - 1));
                    if (!escribirNetcash(movsArchivoActual, clave_contrato, mifecha, ".txt", urlArchivo, verifica, "NC-LIG-MIXTO", countFileOut)) {
                        return seGuardo = "Error al generar el layOut de Netcash en Archivo No=" + numFile;
                    }
                }
            } else {
                // No se generaron movimientos de Netcash
                System.out.println("NO HAY MOVIMIENTOS DE NETCASH");
                return seGuardo = "Sin movimientos de NetCash";
            }

        } catch (Exception e) {
            seGuardo = "Error al generar los LayOut de Netcash";
            System.out.println("creaLayOutNetcash:" + e.getMessage());
        }
        return seGuardo;
    }

    /**
     * Método que GENERA LayOut para servicios de BBVA.
     *
     * @param cliente : Nombre del cliente.
     * @param clave_cliente : clave del cliente.
     * @param clave_contrato : clave del contrato del cliente.
     * @param mifecha : Fecha de la Liquidación.
     * @param verifica : Lotes procesados almacenados para este cliente y fecha.
     * @return boolean valido: Regresa true si se creó correctamente LayOut
     * mixto de Netcash.
     */
    public String creaLayOutBBVA(String cliente, String clave_contrato, String mifecha, String nombre_archivo, String urlArchivo, int verifica) {

        Vector moves = null;
        String seGuardo = "";
        String countFileOut = "";

        try {
            //Obtenemos los movimientos.
            moves = this.getMovsSvcBBVA(cliente, clave_contrato, mifecha, nombre_archivo);

            //Verificamos si surgió algún error al obtener los movimientos de NetCash
            // for(int i=0;i<moves.size();i++){
            //   System.out.println("Vector:[" + i +"]="+moves.get(i));}
            if (moves.size() > 0) {
                // Creamos Lay-Out mixto de Netcash
                for (int numFile = 1; numFile <= moves.size(); numFile++) {
                    if (numFile <= 9) {
                        countFileOut = "-0" + numFile + "-";
                    } else {
                        countFileOut = "-" + numFile + "-";
                    }
                    Vector movsArchivoActual = new Vector();
                    movsArchivoActual = (Vector) moves.get((numFile - 1));
                    if (!escribirNetcash(movsArchivoActual, clave_contrato, mifecha, ".txt", urlArchivo, verifica, "BBVA", countFileOut)) {
                        return seGuardo = "Error al generar el layOut de BBVA en Archivo No=" + numFile;
                    }
                }
            } else {
                // No se generaron movimientos de Netcash
                System.out.println("NO HAY MOVIMIENTOS BBVA");
                return seGuardo = "Sin movimientos BBVA";
            }

        } catch (Exception e) {
            seGuardo = "Error al generar los LayOut de BBVA";
            System.out.println("creaLayOutNetcash:" + e.getMessage());
        }
        return seGuardo;
    }

    /**
     * Método que GENERA LOS 4 LayOut's.
     *
     * @param cliente : Nombre del cliente.
     * @param clave_cliente : clave del cliente.
     * @param clave_contrato : clave del contrato del cliente.
     * @param mifecha : Fecha de la Liquidación.
     * @param verifica : Lotes procesados almacenados para este cliente y fecha.
     * @return boolean valido: Regresa true si se crearon correctamente los 4
     * LayOut's.
     */
    public String creaLayOuts(String cliente, String clave_contrato, String mifecha, String nombre_archivo, String urlArchivo, int verifica) {
//        Vector mov1 = null;
//        Vector mov2 = null;
//        Vector mov3 = null;
        Vector mov4 = null;
        Vector mov5 = null;
        String seGuardo = "";
        try {
            //Verificamos si el directorio del fideicomitente existe si no lo creamos.
//            if (this.verifica_dir_cliente(clave_contrato, urlArchivo)) {
            //Obtenemos los movimientos de tipo 1.
//            mov1 = ModeloLiquidation.getBancomerToBancomer(cliente, clave_contrato, mifecha, nombre_archivo);
//            //Verificamos si surgio algun error al obtener los movimientos de tipo 1.
//            if (mov1 != null) {
//                //Obtenemos los movimientos de tipo 2.
//                mov2 = ModeloLiquidation.getOtrosBancos(cliente, clave_contrato, mifecha, nombre_archivo);
//                //Verificamos si surgio algun error al obtener los movimientos de tipo 2.
//                if (mov2 != null) {
//                    //Obtenemos los movimientos de tipo 3.
//                    mov3 = ModeloLiquidation.getTDDBanamex(cliente, clave_contrato, mifecha, nombre_archivo);
//                    //Verificamos si surgio algun error al obtener los movimientos de tipo 3.
//                    if (mov3 != null) {
            //Obtenemos los movimientos de tipo 4.
            mov4 = ModeloLiquidation.getEmisionCheches(cliente, clave_contrato, mifecha, nombre_archivo);
            //Verificamos si surgio algun error al obtener los movimientos de tipo 4.
            if (mov4 != null) {
                //Obtenemos los movimientos de tipo 5.
//                            mov5 = ModeloLiquidation.getBancosExtranjeros(cliente, clave_contrato, mifecha, nombre_archivo);
//                            //Verificamos si ocurrio algun error al generar los movimientos de tipo 5.
//                            if (mov5 != null) {
                //Verificamos si hay movimientos de tipo 1.
//                                if (ModeloLiquidation.get_Hay_mov1()) {
//                                    //Generamos el correspondiente LayOut.
//                                    if (!this.escribirEnPartes(mov1, clave_contrato, mifecha, "BB", ".txt", urlArchivo, verifica)) {
//                                        return seGuardo = "Error al generar el layOut correspondiente a cuentas Bancomer";
//                                    }
//                                }
//                                //Verificamos si hay movimientos de tipo 2.
//                                if (ModeloLiquidation.get_Hay_mov2()) {
//                                    //Generamos el correspondiente LayOut.
//                                    if (!this.escribirEnPartes(mov2, clave_contrato, mifecha, "OT", ".txt", urlArchivo, verifica)) {
//                                        return seGuardo = "Error al generar el layOut correspondiente a otros bancos";
//                                    }
//                                }
//                                //Verificamos si hay movimientos de tipo 3.
//                                if (ModeloLiquidation.get_Hay_mov3()) {
//                                    //Generamos el correspondiente LayOut.
//                                    if (!this.escribirEnPartes(mov3, clave_contrato, mifecha, "TD", ".txt", urlArchivo, verifica)) {
//                                        return seGuardo = "Error al generar el layOut correspondiente a tarjetas de débito Banamex";
//                                    }
//                                }
                //Verificamos si hay movimientos de tipo 4.
                if (!mov4.isEmpty()) {
                    if (!this.escribirEnPartes(mov4, clave_contrato, mifecha, "CH", ".N01", urlArchivo, verifica)) {
                        return seGuardo = "Error al generar el layOut correspondiente a emisión de cheques";
                    }
                }
//                            } else { //Ocurrio un error al obtener movimientos de tipo 5
//                                seGuardo = "Error al obtener los movimientos a Bancos Extranjeros";
//                            }
            } else { //Ocurrio un error al obtener movimientos de tipo 4
                seGuardo = "Error al obtener los movimientos de emisión de cheques";
            }
//                    } else { //Ocurrio un error al obtener movimientos de tipo 3
//                        seGuardo = "Error al obtener los movimientos de Bancomer a tarjetas de débito Banamex";
//                    }
//                } else { //Ocurrio un error al obtener movimientos de tipo 2
//                    seGuardo = "Error al obtener los movimientos de Bancomer a otros Bancos";
//                }
//            } else { //Ocurrio un error al obtener movimientos de tipo 1
//                seGuardo = "Error al obtener los movimientos de Bancomer a Bancomer";
//            }
//            } else {
//                seGuardo = "Error al crear el directorio del fideicomitente";
//            }
        } catch (Exception e) {
            seGuardo = "Error al generar los layOuts correspondientes";
            System.out.println("creaLayOuts:" + e.getMessage());
        }
        return seGuardo;
    }

    public static String compruebaFechaOperacion(String fecha) {

        java.util.Date fecha_hoy = new java.util.Date();
        java.util.Date fecha_operacion = null;
//        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
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

        return "";
    }

    static public String obtenFormatoFecha(String fecha, int operacion) {
        String fechaFormato = "";
        //Formato de fecha para local
//            SimpleDateFormat formatoDate = new SimpleDateFormat("dd/MM/yyyy");
        //Formato de fecha para principal
        SimpleDateFormat formatoDate = new SimpleDateFormat("yyyy-MM-dd");

        if (operacion == 0) {
            Calendar calendario = Calendar.getInstance();
            calendario.set(Calendar.DAY_OF_MONTH, 1);
            return formatoDate.format(calendario.getTime());
        }
        if (operacion == 1) {
            return formatoDate.format(new java.util.Date());
        }

        return fechaFormato;
    }

    public static Vector getMovimientos(String clave_contrato, String fecha_ini, String fecha_fin, String concepto_in) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        Vector movimientos = null;
        DecimalFormat dec = new DecimalFormat("$ #,##0.00");

        String MySql = "";

        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select fecha,concepto,observaciones,cargo,abono,saldo,usuario_genera ";
            MySql += " from EC_" + clave_contrato + " ";
            if (fecha_ini != null && !fecha_ini.equals("")) {
                MySql += " where fecha >='" + fecha_ini + " 00:00:00.000' ";
            }
            if (fecha_fin != null && !fecha_fin.equals("")) {
                MySql += " and fecha <='" + fecha_fin + " 23:59:59.999' ";
            }
            if (concepto_in != null && !concepto_in.equals("")) {
                if (concepto_in.equals("APORTACION")) {
                    MySql += " and concepto IN('APORTACION A PATRIMONIO','HONORARIOS FIDUCIARIOS','I.V.A. DE HONORARIOS FIDUCIARIOS') ";
                } else {
                    MySql += " and concepto LIKE '%" + concepto_in + "%' ";
                }
            }
            MySql += " order by fecha asc";
            movimientos = new Vector();
//            System.out.println(MySql);
            ResultSet rstSQLServer = statement.executeQuery(MySql);

            while (rstSQLServer.next()) {
                if (movimientos.size() == 0) {
                    Vector encabezado = new Vector();
                    encabezado.add("FECHA");
                    encabezado.add("CONCEPTO");
                    encabezado.add("OBSERVACIONES");
                    encabezado.add("CARGO");
                    encabezado.add("ABONO");
                    encabezado.add("SALDO");
                    encabezado.add("USUARIO");
                    movimientos.add(encabezado);
                }
                Vector result = new Vector();
                result.add(rstSQLServer.getString("fecha").toString().trim());
                result.add(rstSQLServer.getString("concepto").toString().trim());
                result.add(rstSQLServer.getString("observaciones").toString().trim());
                result.add(dec.format(rstSQLServer.getDouble("cargo")));
                result.add(dec.format(rstSQLServer.getDouble("abono")));
                result.add(dec.format(rstSQLServer.getDouble("saldo")));
                result.add(rstSQLServer.getString("usuario_genera").toString().trim());
                movimientos.add(result);
            }
            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            movimientos = null;
            System.out.println("ModeloLiquidation-getMovimientos:" + e.toString());
        }
        return movimientos;

    }

    public static Vector getLiduiacionesPendietes(String clave_contrato, double saldo_actual) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        Vector movimientos = null;
        DecimalFormat dec = new DecimalFormat("$ #,##0.00");
        double importeTotal = 0;

        String MySql = "";

        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            MySql += " select h.fecha_liquidacion,l.nombre_archivo,count(h.clave_contrato) as numero_movimientos , ";
            MySql += " sum(cast(l.importe_liquidacion as float)) as importe_total ";
            MySql += " from movimientos_h h , movimientos l , contratos contratos ";
            MySql += " where h.clave_contrato = l.clave_contrato ";
            MySql += " and l.clave_contrato = contratos.clave_contrato ";
            MySql += " and h.fecha_liquidacion = l.fecha_liquidacion  ";
            MySql += " and h.nombre_archivo=l.nombre_archivo ";
            MySql += " and contratos.clave_contrato = '" + clave_contrato + "' ";
            MySql += " and contratos.status = 'A'  ";
            MySql += " and h.status in ('P' ,'A') ";
            MySql += " group by l.nombre_archivo,h.fecha_liquidacion ";
            MySql += " order by h.fecha_liquidacion asc";
            movimientos = new Vector();
//            System.out.println(MySql);
            ResultSet rstSQLServer = statement.executeQuery(MySql);

            while (rstSQLServer.next()) {
                if (movimientos.size() == 0) {
                    Vector encabezado = new Vector();
                    encabezado.add("NO.");
                    encabezado.add("FECHA DE LIQUIDACIÓN");
                    encabezado.add("NOMBRE ARCHIVO");
                    encabezado.add("MOVIMIENTOS");
                    encabezado.add("IMPORTE DE LIQUIDACIÓN");
                    movimientos.add(encabezado);
                }
                Vector result = new Vector();
                result.add(movimientos.size());
                java.util.Date fecha = rstSQLServer.getDate("fecha_liquidacion");
                result.add(new SimpleDateFormat("dd/MMM/yyyy").format(fecha));
                String nombre_archivo = rstSQLServer.getString("nombre_archivo").trim();
                result.add(nombre_archivo);
                Vector infoTipo5 = getImporteLiqMovsTipo5(clave_contrato, fecha, nombre_archivo);
                //si infoTipo5 NO es vacio quiere decir que el lote es de tipo 5 (solo un movimiento) y se obtuvo el importe a mostrar
                if (!infoTipo5.isEmpty()) {
                    result.add(1);
                    result.add(infoTipo5.get(0));
                    movimientos.add(result);
                    importeTotal += (Double) infoTipo5.get(1);
                } else {
                    String numero_movimientos = rstSQLServer.getString("numero_movimientos").trim();
                    double importeLiquidacion = rstSQLServer.getDouble("importe_total");
                    Double[] temp = cuentaPendientesLote(clave_contrato, fecha, nombre_archivo, numero_movimientos, importeLiquidacion);
                    result.add(temp[0].intValue());
                    result.add(dec.format(temp[1]));
                    movimientos.add(result);
                    importeTotal += temp[1];
                }
            }
            Vector saldos = new Vector();
            saldos.add("Patrimonio disponible");
            saldos.add(dec.format(saldo_actual));
            saldos.add("Liquidaciones pendientes");
            saldos.add(dec.format(importeTotal));
            double saldo_por_pagar = saldo_actual - importeTotal;
            if (saldo_por_pagar < 0) {
                saldos.add("Insuficiencia Patrimonial");
                saldos.add(dec.format(Math.abs(saldo_por_pagar)));
                double deficit = Math.abs(saldo_por_pagar);
                /**
                 * Comienza el calculo de los honorarios, I.V.A. y comisiones
                 * para la liquidacion actual
                 */
                String honSinIva = getInfoContrato(clave_contrato);
                String sp_c = AuthorizationModel.getSuficienciaPatronal("" + deficit, honSinIva, "16");
                saldos.add("Aportación mínima requerida");
                saldos.add(dec.format(Double.parseDouble(sp_c)));

            } else {
                saldos.add("Patrimonio remanente");
                saldos.add(dec.format(saldo_por_pagar));
            }
            movimientos.add(saldos);
            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            movimientos = null;
            System.out.println("ModeloLayOut-getLiquidacionesPendientes:" + e.toString());
        }
        return movimientos;

    }

    public static Double[] cuentaPendientesLote(String clave_contrato, java.util.Date fecha_liquidacion, String nombre_archivo, String movsPend, double importe) {
        Double resultado[] = new Double[2];
        resultado[0] = Double.parseDouble(movsPend);
        resultado[1] = importe;
        int count = 0;
        double liquidacion = 0;
        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;

        String MySql = "";

        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            MySql += " select importe_liquidacion ";
            MySql += " from movimientos_cancelados ";
            MySql += " where clave_contrato='" + clave_contrato + "' ";
//            MySql += " and fecha_liquidacion = '" + new SimpleDateFormat("yyyy-dd-MM HH:mm:ss.SSS").format(fecha_liquidacion) + "' ";//FUNCIONABA CORRECTAMENTE ANTERIORMENTE, CONSULTA EJECUTIVA
            MySql += " and fecha_liquidacion = '" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(fecha_liquidacion) + "' ";
            MySql += " and nombre_archivo = '" + nombre_archivo + "' ";
            MySql += " and status = 'A'  ";
//            System.out.println(MySql);
            ResultSet rstSQLServer = statement.executeQuery(MySql);

            while (rstSQLServer.next()) {
                liquidacion += rstSQLServer.getDouble("importe_liquidacion");
                count++;
            }
            int pendientes = Integer.parseInt(movsPend);
            if (count > 0 && pendientes > count) {
                count = pendientes - count;
                resultado[0] = (double) count;
                resultado[1] = importe - liquidacion;
            }
            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            System.out.println("ModeloLayOut-getLiquidacionesPendientes:" + e.getMessage());
        }
//           System.out.println("resultado: " + resultado[1] + " - " + resultado[0]);
        return resultado;
    }

    public static String getInfoContrato(String contrato) {

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

            MySql = " select distinct tipo_honorario,honorario_sin_iva ";
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
            System.out.println("ModeloLayOut-getInfoContrato:" + e.toString());
        }
        return info;
    }

    public static boolean existeContrato(String clave_contrato) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;

        clave_contrato.toString().trim();
        boolean existe = false;
        String MySql = "";

        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select distinct clave_contrato ";
            MySql += " from contratos ";
            MySql += "where clave_contrato = '" + clave_contrato + "'";

            ResultSet rstSQLServer = statement.executeQuery(MySql);

            if (rstSQLServer.next()) {
                existe = true;
            }
            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            existe = false;
            System.out.println("ModeloLayOut-getInfoContrato:" + e.toString());
        }
        return existe;
    }

    /**
     * Método que GENERA el reporte de Liquidación en formato pdf
     * correspondiente a la información que se pasa como parametro, el reporte
     * se almacena en el directorio correspondiente al ciente en cuestion.
     *
     * @param mov : Tipo de Moviniento.
     * @param archivo_jasper : Ruta del archivo .jrxml correspondiente al tipo
     * de movimiento anterior (./Common/LayOutMx.jrxml).
     * @param fecha_liquidacion : Fecha de Liquidación DD/MM/YYYY.
     * @param clave_contrato : clave del contrato del cliente.
     * @param cliente : Nombre del cliente.
     * @param lcnnConexion : Conexión a la Base de Datos.
     * @param nombre_archivo : Nombre del Lay-Out al que corresponden los
     * movimientos.
     * @param suma_importe : Suma total del importe de liquidación
     * @param verifica: Lotes procesados satisfactoriamente.
     * @return boolean valido: Regresa true si se genero satisfactoriamente,
     * else en otro caso
     */
    public synchronized boolean creaReporteLiquidacion(String mov, String archivo_jasper, String clave_contrato, String fecha_liquidacion,
            String n_cliente, Connection lcnnConexion, String nombre_archivo, String status, String urlArchivo,
            String persona_elabora, int verifica, String realPath) {
        String archivo = archivo_jasper;
        String[] args = null;
        JasperReport report = null;
        OutputStream output = null;
        JasperPrint print = null;
        boolean genera = false, error = false;
        String fecha = "";
        String nombreReporte = "";
        String reportes = "";
        int idx = 0;
        try {
            if (verifica >= 0) {
                //Damos formato a la fecha de liquidación
                fecha = ModeloLiquidation.getFormatoFecha(fecha_liquidacion);
                String clave_fideicomitente = clave_contrato.substring(6, 9);
                //Verificamos si el nombre del archivo es valido
                if (archivo == null || archivo.equals("")) {
                    genera = false;
                }
                try {
                    System.out.println("Compilando ..." + archivo);
                    report = JasperCompileManager.compileReport(archivo);
                } catch (Exception e) {
                    genera = false;
                    error = true;
                    System.out.println("cargando y compilando jasper:" + e.getMessage());
                    //System.exit(3);
                }
                if (!error) {
                    if (mov.equals("CH")) {
                        Vector receptores = AuthorizationModel.getReceptorEnvioCheque(n_cliente, clave_contrato, fecha_liquidacion, status, nombre_archivo);
                        if (receptores != null && receptores.size() >= 0) {
                            if (receptores.isEmpty()) {
                                genera = true;
                            } else {
                                //Parametros
                                Map parametro = new HashMap();
                                parametro.put(JRParameter.REPORT_LOCALE, new Locale("es", "MX"));
                                parametro.put("clave_contrato", clave_contrato);
                                parametro.put("fecha_liquidacion", (new clsFecha().CambiaFormatoFecha("dd/MM/yyyy", "yyyy-MM-dd", fecha_liquidacion)));
                                parametro.put("nombre_cliente", n_cliente);
                                parametro.put("nombre_archivo", nombre_archivo);
                                parametro.put("logo", realPath + "\\images\\logo.jpg");
                                parametro.put("status", status);
                                parametro.put("persona_elabora", persona_elabora);

                                //Obtenemos nombre de la persona a la que se le enviara el cheque
                                for (int i = 0; i < receptores.size(); i++) {
                                    idx++;
                                    args = (String[]) receptores.get(i);
                                    parametro.put("envio_cheque", args[0]);

                                    if (verifica > 0 && verifica <= 9) {
                                        if (idx >= 0 && idx <= 9) {
                                            //Creamos la salida del archivo generado
                                            nombreReporte = clave_fideicomitente + "-0" + verifica + "-" + mov + "-0" + idx + "-" + fecha + ".pdf";
                                            output = new FileOutputStream(new File(urlArchivo + nombreReporte));
                                        } else {
                                            //Creamos la salida del archivo generado
                                            nombreReporte = clave_fideicomitente + "-0" + verifica + "-" + mov + "-" + idx + "-" + fecha + ".pdf";
                                            output = new FileOutputStream(new File(urlArchivo + nombreReporte));
                                        }
                                    } else if (idx >= 0 && idx <= 9) {
                                        //Creamos la salida del archivo generado
                                        nombreReporte = clave_fideicomitente + "-" + verifica + "-" + mov + "-0" + idx + "-" + fecha + ".pdf";
                                        output = new FileOutputStream(new File(urlArchivo + nombreReporte));
                                    } else {
                                        //Creamos la salida del archivo generado
                                        nombreReporte = clave_fideicomitente + "-" + verifica + "-" + mov + "-" + idx + "-" + fecha + ".pdf";
                                        output = new FileOutputStream(new File(urlArchivo + nombreReporte));
                                    }
                                    //Generamos el reporte
                                    print = JasperFillManager.fillReport(report, parametro, lcnnConexion);
                                    //Exportamos a PDF
                                    JasperExportManager.exportReportToPdfStream(print, output);
                                    output.flush();
                                    output.close();

                                    reportes = ModeloLiquidation.getReportes_generados();
                                    if (reportes.equals("")) {
                                        ModeloLiquidation.setReportes_generados(nombreReporte);
                                    } else {
                                        reportes = reportes + ";" + nombreReporte;
                                        ModeloLiquidation.setReportes_generados(reportes);
                                    }
                                    parametro.remove("envio_cheque");
                                }
                                genera = true;
                            }
                        } else {
                            genera = false;
                        }
                    } else if (mov.equals("IN")) {
                        Vector empleadosExtranjero = ModeloLiquidation.getBancosExtranjeros(n_cliente, clave_contrato, fecha_liquidacion, nombre_archivo);
                        if (empleadosExtranjero != null && empleadosExtranjero.size() >= 0) {
                            if (empleadosExtranjero.isEmpty()) {
                                genera = true;
                            } else {
                                //Parámetros
                                Map parametro = new HashMap();
                                parametro.put(JRParameter.REPORT_LOCALE, new Locale("es", "MX"));
                                parametro.put("clave_contrato", clave_contrato);
                                parametro.put("fecha_liquidacion", (new clsFecha().CambiaFormatoFecha("dd/MM/yyyy", "yyyy-MM-dd", fecha_liquidacion)));
                                parametro.put("nombre_cliente", n_cliente);
                                parametro.put("nombre_archivo", nombre_archivo);
                                parametro.put("logo", realPath + "\\images\\logo.jpg");
                                //Realizamos la transacción para cada fideicomisario en el extranjero.
                                for (int i = 0; i < empleadosExtranjero.size(); i++) {
                                    idx++;
                                    parametro.put("nombre_fidei_banco_ext", empleadosExtranjero.get(i));

                                    if (verifica > 0 && verifica <= 9) {
                                        if (idx >= 0 && idx <= 9) {
                                            //Creamos la salida del archivo generado
                                            nombreReporte = clave_fideicomitente + "-0" + verifica + "-" + mov + "-0" + idx + "-" + fecha + ".pdf";
                                            output = new FileOutputStream(new File(urlArchivo + nombreReporte));
                                        } else {
                                            //Creamos la salida del archivo generado
                                            nombreReporte = clave_fideicomitente + "-0" + verifica + "-" + mov + "-" + idx + "-" + fecha + ".pdf";
                                            output = new FileOutputStream(new File(urlArchivo + nombreReporte));
                                        }
                                    } else if (idx >= 0 && idx <= 9) {
                                        //Creamos la salida del archivo generado
                                        nombreReporte = clave_fideicomitente + "-" + verifica + "-" + mov + "-0" + idx + "-" + fecha + ".pdf";
                                        output = new FileOutputStream(new File(urlArchivo + nombreReporte));
                                    } else {
                                        //Creamos la salida del archivo generado
                                        nombreReporte = clave_fideicomitente + "-" + verifica + "-" + mov + "-" + idx + "-" + fecha + ".pdf";
                                        output = new FileOutputStream(new File(urlArchivo + nombreReporte));
                                    }
//                                    //Generamos el reporte
                                    print = JasperFillManager.fillReport(report, parametro, lcnnConexion);
//                                    //Exportamos a PDF
                                    JasperExportManager.exportReportToPdfStream(print, output);
//
                                    output.flush();
                                    output.close();
                                    reportes = ModeloLiquidation.getReportes_generados();
                                    if (reportes.equals("")) {
                                        ModeloLiquidation.setReportes_generados("MovimientoTipo5");
                                    }
                                    parametro.remove("nombre_fidei_banco_ext");
                                }
                                genera = true;
                            }
                        } else {
                            genera = false;
                        }
                    } else {
                        //Parámetros
                        Map parametro = new HashMap();
                        parametro.put(JRParameter.REPORT_LOCALE, new Locale("es", "MX"));
                        parametro.put("clave_contrato", clave_contrato);
                        parametro.put("fecha_liquidacion", (new clsFecha().CambiaFormatoFecha("dd/MM/yyyy", "yyyy-MM-dd", fecha_liquidacion)));
                        parametro.put("nombre_cliente", n_cliente);
                        parametro.put("nombre_archivo", nombre_archivo);
                        parametro.put("logo", realPath + "\\images\\logo.jpg");
                        parametro.put("status", status);
                        parametro.put("persona_elabora", persona_elabora);

                        if (verifica > 0 && verifica <= 9) {
                            //Creamos la salida del archivo generado
                            nombreReporte = clave_fideicomitente + "-0" + verifica + "-" + mov + "-01-" + fecha + ".pdf";
                            output = new FileOutputStream(new File(urlArchivo + nombreReporte));
                        } else {
                            //Creamos la salida del archivo generado
                            nombreReporte = clave_fideicomitente + "-" + verifica + "-" + mov + "-01-" + fecha + ".pdf";
                            output = new FileOutputStream(new File(urlArchivo + nombreReporte));
                        }
                        //Generamos el reporte
                        //System.out.println("Antes de JasperFillManager");
//                        print = JasperFillManager.fillReport(archivo, parametro, lcnnConexion);
                        print = JasperFillManager.fillReport(report, parametro, lcnnConexion);

                        //Exportamos a PDF
                        //System.out.println("Antes de JasperExportManager");
                        JasperExportManager.exportReportToPdfStream(print, output);
                        output.flush();
                        output.close();

                        reportes = ModeloLiquidation.getReportes_generados();
                        if (reportes.equals("")) {
                            ModeloLiquidation.setReportes_generados(nombreReporte);
                        } else {
                            reportes = reportes + ";" + nombreReporte;
                            ModeloLiquidation.setReportes_generados(reportes);
                        }
                        genera = true;
                    }
                }
            } //Fin validación de la variable 'verifica'>=0.
            else {
                genera = false;
            }
        } catch (Exception e) {
            ModeloLiquidation.setReportes_generados("");
            genera = false;
            try {
                if (output != null) {
                    output.flush();
                    output.close();
                }
            } catch (IOException ex) {
            }
            System.out.println("creaReporteLiquidacion:" + e.getMessage());
            System.out.println("Exception: " + e.getLocalizedMessage());
            System.out.println("Exception: " + e.toString());
            System.out.println("Exception: " + e.getCause().getMessage());
            System.out.println("Exception: " + e.getCause().toString());
        }

        return genera;
    }

    /**
     * Método que GENERA todas las transacciones para un lote especifico (
     * Lay-Out's y Reportes de Liquidación correspondientes)
     *
     * @param nombre_cliente :Nombre del Fideicomitente del Lote a Procesar.
     * @param clave_contrato : Clave de Contrato del Fideicomitente.
     * @param fecha_liquidacion : Fecha en la que tendrá lugar la liquidación.
     * @param fileName : Nombre del lote.
     * @param usuario : usuatio que procesa el Lay-Out.
     * @param urlArchivo : Ruta donde se generaran los archivos.
     * @param persona_genera : Nombre completo de la persona asociada al usuario
     * que genera la transacción.
     * @param verifica : Identificador del lote.
     * @return Mensaje descriptivo de la transacción
     */
    public String generaTransacciones(String nombre_cliente, String clave_contrato, String fecha_liquidacion, String fileName,
            String usuario, String urlArchivo, String persona_genera, int verifica, String realPath) {

        String messageBean = "";
        boolean actualiza = false;
        try {
            //Verificamos el identificador del archivo.
            if (verifica >= 0) {
                //Verificamos si las transacciones ya fueron generadas por otro usuario.
                messageBean = ModeloLiquidation.verificaActualizacion(clave_contrato, fecha_liquidacion, fileName, "A");
                //System.out.println("Este es el contenido autorizacion:" +messageBean+ "Termina aqui-----");
                if (messageBean.equals("")) {//si no hay mensage, no se genero ningun error

                    //Se crean los archivos para servicio temporal de BBVA
                    messageBean = this.creaLayOutBBVA(nombre_cliente, clave_contrato, fecha_liquidacion, fileName, urlArchivo, verifica);
                    if (messageBean.equals("") || messageBean.equals("Sin movimientos BBVA")) {
                        // Se crea Lay-Out de Netcash//ya lo escribio //Genera NC MIXTO
                        messageBean = this.creaLayOutNetcash(nombre_cliente, clave_contrato, fecha_liquidacion, fileName, urlArchivo, verifica);
                        if (messageBean.equals("") || messageBean.equals("Sin movimientos de NetCash")) {
                            //"Sin movimientos de NetCash"(movs 1,2 y 3)
                            // Se crea Lay-Out de de Netcash Cuentas interbancarias (PSC)
                            messageBean = this.creaLayOutNetcashCuentasInterbancarias(nombre_cliente, clave_contrato, fecha_liquidacion, fileName, urlArchivo, verifica);
                            //System.out.println("Este es el contenido creaLayOutNetcashCuentasInterbancarias:" +messageBean+ "Termina aqui-----");
                            if (messageBean.equals("") || messageBean.equals("Sin movimientos de Cuentas Interbancarias")) {

                                // Se crea Lay-Out de de Netcash Cuentas Bancomer (PTC)
                                messageBean = this.creaLayOutNetcashCuentasBancomer(nombre_cliente, clave_contrato, fecha_liquidacion, fileName, urlArchivo, verifica);
                                //System.out.println("Este es el contenido creaLayOutNetcashCuentasBancomer:" +messageBean+ "Termina aqui-----");
                                if (messageBean.equals("") || messageBean.equals("Sin movimientos de Cuentas Bancomer")) {

                                    //Se crean los Lay-Out's correspondientes
                                    messageBean = this.creaLayOuts(nombre_cliente, clave_contrato, fecha_liquidacion, fileName, urlArchivo, verifica);
                                    //System.out.println("Este es el contenido creaLayOuts:" +messageBean+ "Termina aqui-----");
                                    //Verificamos si se crearon satisfactoriamente los 4 LayOut's con los correspondientes movimientos
                                    if (messageBean.equals("")) {
                                        //Generamos los reportes de liquidación en formato pdf.

                                        messageBean = this.generaReportesLiquidacion(nombre_cliente, clave_contrato, fecha_liquidacion, fileName, "A", urlArchivo, persona_genera, verifica, realPath);
                                        //Verificamos si ocurrio algún error al generar los reportes.

                                        if (messageBean.equals("")) {
                                            messageBean = ModeloLiquidation.getReportes_generados();
                                            if (!messageBean.equals("")) {
                                                actualiza = this.actualizaStatusOpera(usuario, clave_contrato, fecha_liquidacion, "P", fileName, messageBean);
                                                //Verificamos si se actualizo el estatus correctamente
                                                if (actualiza) {
                                                    messageBean = " Proceso realizado correctamente ";
                                                    System.out.println("Generacion de archivos correcta.");
                                                } else {
                                                    messageBean = " Error actualizando status global del lote ";
                                                }
                                            } else {
                                                messageBean = " Error obteniendo nombre de reportes de liquidación generados ";
                                            }
                                        } else {
                                            messageBean = "Error al generar los reportes de liquidacion PDF";
                                        }
                                    } else {
                                        messageBean = "Error al generar los Lay-Out Cash-Windows";
                                    }
                                } else {
                                    messageBean = "Error al generar Lay-Out de Cuentas Bancomer";
                                }
                            } // Cuentas Interbancarias
                            else {
                                messageBean = "Error al generar Lay-Out de Cuentas Interbancarias";
                            }
                        } else {
                            messageBean = "Error al generar Lay-Out";
                        }
                    } else {
                        messageBean = "Error al generar Lay-Out BBVA";
                    }
                } // NetCash
                else {
                    messageBean = "Error al generar Lay-Out de Netcash";
                }

            } else {
                messageBean = "Error consultando el identificador del lote almacenado ";
            }
        } catch (Exception ex) {
            messageBean = " Error generando las transacciones correspondientes ";
            System.out.println("generaTransacciones:" + ex.getMessage());
        }
        return messageBean;
    }

    /**
     * Método que actualiza el status al generar los LayOut's correspondientes y
     * el reporte de liquidacion a bancons extranjeros del lote que se
     * especifica con los datos que se pasan como parámetro.
     *
     * @param usuario : Nombre del Usuario que genero los Lay_Out's
     * @param clave_cliente : clave del cliente.
     * @param clave_contrato : clave del contrato del cliente.
     * @param fecha_liquidacion : Fecha de la Liquidación.
     * @param status : Status a establecer.
     * @return boolean valido: Regresa true si se actualiza correctamente el
     * status correspondiente al generar los layOut's correspondientes, false en
     * otro caso.
     */
    public synchronized boolean actualizaStatusOpera(String usuario, String clave_contrato, String fecha_liquidacion, String status, String nombre_archivo, String reportes_liquidacion) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        boolean seGuardo = false;
        String MySql = "";

        //Verificamos si es un lote con movimientos solo de tipo 5
        if (reportes_liquidacion.equals("MovimientoTipo5")) {
            reportes_liquidacion = "";
        }
        try {
            connection = conn.ConectaSQLServer();
            connection.setAutoCommit(false);
            statement = connection.createStatement();

            MySql = " update movimientos_h";
            MySql += " set status = '" + status + "', ";
            MySql += " usuario_opera='" + usuario + "', ";
            MySql += " fecha_usuario_opera = getDate() , ";
            MySql += " reportes_liquidacion_mxp= '" + reportes_liquidacion + "' ";
            MySql += " where clave_contrato = '" + clave_contrato + "' ";
            MySql += " and fecha_liquidacion = '" + (new clsFecha().CambiaFormatoFecha("dd/MM/yyyy", "yyyy-MM-dd", fecha_liquidacion)) + " 00:00:00.000" + "' ";
            MySql += " and nombre_archivo = '" + nombre_archivo + "' ";
            MySql += " and status = 'A' ";
//            System.out.println("actualizaStatus:" + MySql);
            // Se ejecuta el encabezado
            statement.executeUpdate(MySql);
            seGuardo = true;
            connection.commit();
//            System.out.println("Transaction commit...");
            statement.close();
            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            e.printStackTrace();
            seGuardo = false;
            System.out.println("actualizaStatusOpera:" + e.toString());
            if (connection != null) {
                conn.Desconecta(connection);
            }
        }
        return seGuardo;
    }

    /**
     * Método que actualiza el status al realizar la autorización
     * correspondiente del lote que se especifica con los datos que se pasan
     * como parámetro.
     *
     * @param usuario : Nombre del Usuario que genero los Lay_Out's
     * @param clave_cliente : clave del cliente.
     * @param clave_contrato : clave del contrato del cliente.
     * @param fecha_liquidacion : Fecha de la Liquidación.
     * @param status : Status a establecer.
     * @return boolean valido: Regresa true si se actualiza correctamente el
     * status correspondiente al generar los layOut's correspondientes, false en
     * otro caso.
     */
    public synchronized boolean actualizaStatusAutoriza(String clave_contrato, String fecha_liquidacion, String nombre_archivo, String usuario, String status_global, String status_autoriza, String status_proceso, String importe_total, String nuevo_saldo) {

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

            // se formatean las cantidades a ser almacenadas en la BD
            importe_total = formato.format(Double.parseDouble(importe_total));
            nuevo_saldo = formato.format(Double.parseDouble(nuevo_saldo));

            MySql = " update movimientos_h";
            MySql += " set status = '" + status_global + "', ";
            MySql += " status_autoriza = '" + status_autoriza + "', ";
            MySql += " usuario_autoriza='" + usuario + "', ";
            MySql += " fecha_usuario_autoriza = getDate() ";
            MySql += " where clave_contrato = '" + clave_contrato + "' ";
            MySql += " and fecha_liquidacion = '" + fecha_liquidacion + " 00:00:00.000" + "' ";
            MySql += " and nombre_archivo = '" + nombre_archivo + "' ";
            MySql += " and status = '" + status_proceso + "' ";

            System.out.println("actualizaStatusAutoriza:" + MySql);
            // Se ejecuta el encabezado
            statement.executeUpdate(MySql);
            seGuardo = true;

            if (seGuardo) {
                seGuardo = false;
                MySql = " insert EC_" + clave_contrato + " ";
                MySql += " (fecha,concepto,cargo,saldo,usuario_genera,nombre_archivo) ";
                MySql += " values ( ";
                MySql += " getDate(), ";
                MySql += " 'ORDEN DE LIQUIDACION', ";
                MySql += " " + importe_total + ", ";
                MySql += " " + nuevo_saldo + ", ";
                MySql += " '" + usuario + "', ";
                MySql += " '" + nombre_archivo + "') ";

                statement.executeUpdate(MySql);
                seGuardo = true;
            }

            if (seGuardo) {
                seGuardo = false;
                MySql = " update contratos set ";
                MySql += " saldo='" + nuevo_saldo + "' ";
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
                Logger.getLogger(ModeloLiquidation.class.getName()).log(Level.SEVERE, null, ex);
            }
            e.printStackTrace();
            seGuardo = false;
            System.out.println("actualizaStatusAutoriza:" + e.toString());
            if (connection != null) {
                conn.Desconecta(connection);
            }
        }
        return seGuardo;
    }

    public synchronized boolean actualizaStatusAutoriza_DiaCorte(String clave_contrato, String fecha_liquidacion, String nombre_archivo, String usuario, String status_global, String status_autoriza, String status_proceso, String importe_total, String nuevo_saldo) {

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
        cal.set(Calendar.MINUTE, ModelUpdate.getNo_movimiento());
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss.SSS");
        try {
            connection = conn.ConectaSQLServer();
            connection.setAutoCommit(false);
            statement = connection.createStatement();

            // se formatean las cantidades a ser almacenadas en la BD
            importe_total = formato.format(Double.parseDouble(importe_total));
            nuevo_saldo = formato.format(Double.parseDouble(nuevo_saldo));

            MySql = " update movimientos_h";
            MySql += " set status = '" + status_global + "', ";
            MySql += " status_autoriza = '" + status_autoriza + "', ";
            MySql += " usuario_autoriza='" + usuario + "', ";
            MySql += " fecha_usuario_autoriza = '" + formatoFecha.format(cal.getTime()) + "' ";
            MySql += " where clave_contrato = '" + clave_contrato + "' ";
            MySql += " and fecha_liquidacion = '" + fecha_liquidacion + " 00:00:00.000" + "' ";
            MySql += " and nombre_archivo = '" + nombre_archivo + "' ";
            MySql += " and status = '" + status_proceso + "' ";

            System.out.println("MySQL_actualizaStatusAutoriza_DiaCorte:" + MySql);
            // Se ejecuta el encabezado
            statement.executeUpdate(MySql);
            seGuardo = true;

            if (seGuardo) {
                seGuardo = false;
                MySql = " insert EC_" + clave_contrato + " ";
                MySql += " (fecha,concepto,cargo,saldo,usuario_genera,nombre_archivo) ";
                MySql += " values ( ";
                MySql += " '" + formatoFecha.format(cal.getTime()) + "', ";
//            MySql += " getDate(), ";
//            MySql += " '"+ clave_contrato +"', ";
                MySql += " 'ORDEN DE LIQUIDACION', ";
                MySql += " " + importe_total + ", ";
                MySql += " " + nuevo_saldo + ", ";
                MySql += " '" + usuario + "', ";
                MySql += " '" + nombre_archivo + "') ";

                System.out.println("MySQL_2_EC_actualizaStatusAutoriza_DiaCorte:" + MySql);

                statement.executeUpdate(MySql);
                seGuardo = true;
            }

            if (seGuardo) {
                seGuardo = false;
                MySql = " update contratos set ";
                MySql += " saldo='" + nuevo_saldo + "' ";
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
                Logger.getLogger(ModeloLiquidation.class.getName()).log(Level.SEVERE, null, ex);
            }
            e.printStackTrace();
            seGuardo = false;
            System.out.println("actualizaStatusAutoriza:" + e.toString());
            if (connection != null) {
                conn.Desconecta(connection);
            }
        }
        return seGuardo;
    }

    public synchronized boolean bloqueaUsuario(String usuario) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        boolean seGuardo = false;
        String MySql = "";
        try {
            connection = conn.ConectaSQLServer();
            connection.setAutoCommit(false);
            statement = connection.createStatement();

            MySql = " update usuarios_admin";
            MySql += " set status = 'B',";
            MySql += " fecha_bloqueo=getDate()";
            MySql += " where usuario = '" + usuario + "' ";

//            System.out.println(MySql);
            // Se ejecuta el encabezado
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
            System.out.println("bloqueaUsuario:" + e.toString());
            e.printStackTrace();
            if (connection != null) {
                conn.Desconecta(connection);
            }
        }
        return seGuardo;
    }

    /**
     * Método que actualiza el password
     *
     * @param String userApp: Datos de usuario
     * @param String cliente: Clave del Cliente
     * @param String usuario: Usuario
     * @param String password: Contraseña del usuario
     * @return boolean auth: true / false
     */
    public static boolean actualizaUsuario(Usuario userApp, String newPassword) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        boolean actualiza = false;
        String MySql = "";

        String usuario = userApp.getUsuario().toString().trim();
        String password = userApp.getPassword().toString().trim();
        newPassword = newPassword.toString().trim();

        try {
            connection = conn.ConectaSQLServer();
            connection.setAutoCommit(false);
            statement = connection.createStatement();

            MySql = " update usuarios_admin ";
            MySql += " set password='" + newPassword + "' ";
            MySql += " where usuario ='" + usuario + "' ";
            MySql += " and password='" + password + "' ";
            MySql += " and status = 'A'";

            statement.executeUpdate(MySql);

            connection.commit();
            actualiza = true;
            //         System.out.println("Transaction commit...");
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }

        } catch (Exception e) {
            System.out.println("actualizaUsuario:" + e.getMessage());
            actualiza = false;

            if (connection != null) {
                conn.Desconecta(connection);
            }
        }
        return actualiza;
    }

    public synchronized boolean verifica_dir_cliente(String contrato, String urlArchivo) throws Exception {

        //Variable que almacena el archivo a trabajar.
        File archivo = null;
        //Permite abrir un archivo para leer caracteres.
        FileReader fr = null;
        //Permite manejar lineas completas a diferencia de FileReader.
        BufferedReader br = null;
        FileWriter fw = null;
        PrintWriter pw = null;
        //Varible que identifica si ocurrio algun error al realizar la transaccion.
        boolean existe = false;
        //Almacena una linea del archivo a leer.
        String linea = "";
        //Comando ms-dos a ejecutar.
        String comando = "cmd /c md \"" + urlArchivo + "\"";
//        System.out.println("comando:" + comando);
        try {
            /* Apertura del fichero y creacion de BufferedReader para poder
             * hacer una lectura comoda (disponer del metodo readLine()).
             */
            archivo = new File("./Common/Contratos.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            while ((linea = br.readLine()) != null) {
                if (linea.trim().equals(contrato.trim())) {
                    existe = true;
                    break;
                }
            }
            if (!existe) {
                try {
                    Runtime runtime = Runtime.getRuntime();
                    runtime.exec(comando);
//                    System.out.println(comando);
                    fw = new FileWriter("./Common/Contratos.txt", true);
                    pw = new PrintWriter(fw);
                    pw.println(contrato);
                    existe = true;
                } catch (IOException ioe) {
                    existe = false;
                    System.out.println("verifica_dir_cliente:" + ioe.getMessage());
                }
            }
        } catch (Exception e) {
            existe = false;
            e.printStackTrace();
            System.out.println("verifica_dir_cliente:" + e.getMessage());
        } finally {
            // Cerramos el fichero, nos asegurarnos que se cierra,
            // tanto si todo va bien como si surgio una excepción.
            try {
                if (null != fr) {
                    fr.close();
                }
                if (null != fw) {
                    fw.close();
                }
            } catch (Exception e2) {
                existe = false;
                e2.printStackTrace();
                try {
                    if (null != fw) {
                        fw.close();
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                    existe = false;
                }
            }
        }
        return existe;
    }

    /**
     * Método que verifica si existe el directorio correspondiente a la fecha de
     * liquidación(fecha_liquidacion) dentro del directorio del fideicomitente
     * (clave_contrato).
     *
     * @param clave_contrato : clave de Fideicomiso.
     * @param fecha_liquidacion : fecha de Liquidación.
     * @return boolean : true, false si ocurrio algún error.
     */
    public synchronized boolean verifica_dir_fecha(String clave_contrato, String fecha_liquidacion, String urlArchivo) throws Exception {

        //Variable que almacena el archivo a trabajar.
        File archivo = null;
        //Permite abrir un archivo para leer caracteres.
        FileReader fr = null;
        //Permite manejar lineas completas a diferencia de FileReader.
        BufferedReader br = null;
        FileWriter fw = null;
        PrintWriter pw = null;
        StringTokenizer token = null;
        //Varible que identifica si ocurrio algun error al realizar la transaccion.
        boolean existe = false;
        //Almacena una linea del archivo a leer.
        String linea = "";
        String contrato = "";
        String fechas = "";
        String fecha = "";
        //Comando ms-dos a ejecutar:
        String comando = "cmd /c md \"" + urlArchivo + fecha_liquidacion + "\"";
//        System.out.println("comando:" + comando);
        try {
            /* Apertura del fichero y creacion de BufferedReader para poder
             * hacer una lectura comoda (disponer del metodo readLine()).
             */
            archivo = new File("./Common/" + clave_contrato + ".txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            while ((linea = br.readLine()) != null) {
                token = new StringTokenizer(linea, "|");
                if (token.hasMoreTokens()) {
                    contrato = token.nextToken();
                    if (token.hasMoreTokens()) {
                        fechas = token.nextToken();
                        token = new StringTokenizer(fechas, ":");

                        while (!fecha.equals(fecha_liquidacion)) {
                            fecha = token.nextToken().trim();
                        }
                    } else {
                        existe = false;
                    }
                }

                if (linea.trim().equals(fecha_liquidacion.trim())) {
                    existe = true;
                    break;
                }
            }
            if (!existe) {
                try {
                    Runtime runtime = Runtime.getRuntime();
                    runtime.exec(comando);
//                    System.out.println(comando);
                    fw = new FileWriter("./Common/Contratos.txt", true);
                    pw = new PrintWriter(fw);
                    pw.println(clave_contrato);

                } catch (IOException ioe) {
                    existe = false;
                    System.out.println("existe_directorio:" + ioe.getMessage());
                }
            }
        } catch (Exception e) {
            existe = false;
            e.printStackTrace();
            System.out.println("existe_directorio:" + e.getMessage());
        } finally {
            // Cerramos el fichero, nos asegurarnos que se cierra,
            // tanto si todo va bien como si surgio una excepcion.
            try {
                if (null != fr) {
                    fr.close();
                }
                if (null != fw) {
                    fw.close();
                }
                existe = true;
            } catch (Exception e2) {
                existe = false;
                e2.printStackTrace();
                try {
                    if (null != fw) {
                        fw.close();
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
        }
        return existe;
    }

    /**
     * Método que regresa el número de reportes de liquidación generados con los
     * datos que se pasan como parametro.
     *
     * @param clave_contrato : clave de Fideicomiso.
     * @param fecha_liquidacion : fecha de Liquidación.
     * @return int : numero de archivos generados. Si ocurre algun error regresa
     * -1.
     */
    public static int archivosGenerados(String clave_contrato, String fecha_liquidacion) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;

        String MySql = "";
        int idx = 0;

        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select count(clave_contrato) ";
            MySql += " from movimientos_h ";
            MySql += " where clave_contrato = '" + clave_contrato + "' ";
            MySql += " and fecha_liquidacion = '" + fecha_liquidacion + " 00:00:00.00' ";
            MySql += " and status in ('P','M','F')  ";

//            System.out.println("archivosGenerados:" + MySql);
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
            System.out.println("archivosGenerados:" + e.getMessage());
        }
        return idx;
    }

    /**
     * Método que regresa la clave del LayOut que corresponde a los datos que se
     * le pasan como parámetro.
     *
     * @param clave_contrato : clave de Fideicomiso.
     * @param fecha_liquidacion : fecha de Liquidación.
     * @param nombre_archivo : Nombre del Archivo.
     * @return int : Identificador asociado al lote cargando. Si ocurre algún
     * error regresa -1.
     */
    public static int getClaveArchivo(String clave_contrato, String fecha_liquidacion, String nombre_archivo) {
        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String MySql = "";
        int id_archivo = -1;
        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select clave_archivo ";
            MySql += " from movimientos_h ";
            MySql += " where clave_contrato = '" + clave_contrato + "' ";
            MySql += " and fecha_liquidacion = '" + (new clsFecha().CambiaFormatoFecha("dd/MM/yyyy", "yyyy-MM-dd", fecha_liquidacion)) + " 00:00:00.00' ";
            MySql += " and nombre_archivo = '" + nombre_archivo + "' ";
//            System.out.println("getClaveArchivo:" + MySql);
            ResultSet rstSQLServer = statement.executeQuery(MySql);
            if (rstSQLServer.next()) {
                id_archivo = Integer.parseInt(rstSQLServer.getString(1));
            }
            rstSQLServer.close();
            statement.close();
            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            id_archivo = -1;
            if (connection != null) {
                conn.Desconecta(connection);
            }
            System.out.println("getClaveArchivo:" + e.getMessage());
        }
        return id_archivo;
    }

    /**
     * Método que regresa los datos generales para la eliminación parcial de un
     * movimiento.
     *
     * @param cancelado : Bean que especifica los datos del movimiento a
     * cancelar.
     * @return Movimiento:Bean del movimiento registrado en la base de datos
     * según los datos que se especifican en el Bean que se pasa como parámetro,
     * si ocurre algún error regresa null. Regresa como mensaje de cancelación
     * el resultado de realizar la transacción, si todo va bien regresa una
     * cadena vacia.
     */
    public static Movimiento getDatosMovimiento(Movimiento cancelado, String status) {

        clsConexion conn = null;
        Connection connection = null;
        Statement statement = null;
        Movimiento movimiento = null;
        String clave_contrato = null;
        String fecha_liquidacion = null;
        String fecha_ingreso = null;
        String nombre_archivo = null;
        clsFecha fecha = new clsFecha();
        String mensaje = null;
        String MySql = "";
        int idx = 1, id = 0;

        try {
            fecha_ingreso = cancelado.getFecha_ingreso();
            if (fecha_ingreso != null && !fecha_ingreso.equals("")) {
                if (fecha.fechaValida(fecha_ingreso, "dd/MM/yyyy")) {
                } else {
                    mensaje = "Formato de fecha inválido en la fecha de ingreso";
                }
            }
            if (mensaje == null) {
                conn = new clsConexion();
                connection = conn.ConectaSQLServer();
                statement = connection.createStatement();
                statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

                clave_contrato = cancelado.getClave_contrato();
                fecha_liquidacion = cancelado.getFecha_liquidacion();
                nombre_archivo = cancelado.getNombre_archivo();
                MySql = " select l.cuenta_deposito, l.nombre_empleado, l.apellidoP_empleado, l.apellidoM_empleado, ";
                MySql += " l.clave_empleado, l.puesto_empleado, l.depto_empleado, l.curp, ";
                MySql += " l.tipo_movimiento, l.clave_banco, l.importe_liquidacion,l.importe_liquidacion_mxp, l.tipo_moneda,";
                MySql += " l.envio_cheque, l.destino_envio_cheque, l.tel_envio_cheque, l.correo_envio_cheque, ";
                MySql += " l.banco_extranjero, l.dom_banco_extranjero, l.pais_banco_extranjero, l.ABA_BIC, ";
                MySql += " l.nombre_fidei_banco_ext, l.direccion_fidei_ext, l.pais_fidei_ext, l.tel_fidei_ext, l.fecha_ingreso";
                MySql += " from movimientos_h h, movimientos l";
                MySql += " where h.clave_contrato = l.clave_contrato ";
                MySql += " and h.fecha_liquidacion = l.fecha_liquidacion";
                MySql += " and h.nombre_archivo = l.nombre_archivo";
                MySql += " and h.clave_contrato = '" + clave_contrato + "' ";
                MySql += " and h.fecha_liquidacion = '" + (new clsFecha().CambiaFormatoFecha("dd/MM/yyyy", "yyyy-MM-dd", fecha_liquidacion)) + " 00:00:00.00' ";
                MySql += " and h.nombre_archivo = '" + nombre_archivo + "' ";
                MySql += " and h.status = '" + status + "' ";

                if (!cancelado.getCuenta_deposito().equals("")) {
                    MySql += " and l.cuenta_deposito = '" + cancelado.getCuenta_deposito() + "' ";
                }
                if (!cancelado.getNombre_empleado().equals("")) {
                    MySql += " and l.nombre_empleado = '" + cancelado.getNombre_empleado() + "' ";
                }
                if (!cancelado.getApellidoP_empleado().equals("")) {
                    MySql += " and l.apellidoP_empleado = '" + cancelado.getApellidoP_empleado() + "' ";
                }
                if (!cancelado.getApellidoM_empleado().equals("")) {
                    MySql += " and l.apellidoM_empleado = '" + cancelado.getApellidoM_empleado() + "' ";
                }
                if (!cancelado.getNumero_empleado().equals("")) {
                    MySql += " and l.clave_empleado = '" + cancelado.getNumero_empleado() + "' ";
                }
                if (!cancelado.getPuesto_empleado().equals("")) {
                    MySql += " and l.puesto_empleado = '" + cancelado.getPuesto_empleado() + "' ";
                }
                if (!cancelado.getDepartamento_empleado().equals("")) {
                    MySql += " and l.depto_empleado = '" + cancelado.getDepartamento_empleado() + "' ";
                }
                if (!cancelado.getCURP().equals("")) {
                    MySql += " and l.curp = '" + cancelado.getCURP() + "' ";
                }
                if (!cancelado.getFecha_ingreso().equals("")) {
                    MySql += " and l.fecha_ingreso = '" + cancelado.getFecha_ingreso() + " 00:00:00.000' ";
                }
                if (!cancelado.getTipo_movimiento().equals("")) {
                    MySql += " and l.tipo_movimiento = '" + cancelado.getTipo_movimiento() + "' ";
                }
                if (!cancelado.getClave_banco().equals("")) {
                    MySql += " and l.clave_banco = '" + cancelado.getClave_banco() + "' ";
                }
                if (!cancelado.getImporte_liquidacion().equals("")) {
                    MySql += " and l.importe_liquidacion = '" + cancelado.getImporte_liquidacion() + "' ";
                }
                if (!cancelado.getClave_moneda().equals("")) {
                    MySql += " and l.tipo_moneda = '" + cancelado.getClave_moneda() + "' ";
                }
                if (!cancelado.getNombre_receptor_cheque().equals("")) {
                    MySql += " and l.envio_cheque = '" + cancelado.getNombre_receptor_cheque() + "' ";
                }
                if (!cancelado.getDomicilio_destino_cheque().equals("")) {
                    MySql += " and l.destino_envio_cheque = '" + cancelado.getDomicilio_destino_cheque() + "' ";
                }
                if (!cancelado.getTel_destino_cheque().equals("")) {
                    MySql += " and l.tel_envio_cheque = '" + cancelado.getTel_destino_cheque() + "' ";
                }
                if (!cancelado.getCorreo_destino_cheque().equals("")) {
                    MySql += " and l.correo_envio_cheque = '" + cancelado.getCorreo_destino_cheque() + "' ";
                }
                if (!cancelado.getNombre_banco_extranjero().equals("")) {
                    MySql += " and l.banco_extranjero = '" + cancelado.getNombre_banco_extranjero() + "' ";
                }
                if (!cancelado.getDomicilio_banco_extranjero().equals("")) {
                    MySql += " and l.dom_banco_extranjero = '" + cancelado.getDomicilio_banco_extranjero() + "' ";
                }
                if (!cancelado.getPais_banco_extranjero().equals("")) {
                    MySql += " and l.pais_banco_extranjero = '" + cancelado.getPais_banco_extranjero() + "' ";
                }
                if (!cancelado.getABA_BIC().equals("")) {
                    MySql += " and l.ABA_BIC = '" + cancelado.getABA_BIC() + "' ";
                }
                if (!cancelado.getNombre_empleado_banco_extranjero().equals("")) {
                    MySql += " and l.nombre_fidei_banco_ext = '" + cancelado.getNombre_empleado_banco_extranjero() + "' ";
                }
                if (!cancelado.getDom_empleado_banco_extranjero().equals("")) {
                    MySql += " and l.direccion_fidei_ext = '" + cancelado.getDom_empleado_banco_extranjero() + "' ";
                }
                if (!cancelado.getPais_empleado_banco_extranjero().equals("")) {
                    MySql += " and l.pais_fidei_ext = '" + cancelado.getPais_empleado_banco_extranjero() + "' ";
                }
                if (!cancelado.getTel_empleado_banco_extranjero().equals("")) {
                    MySql += " and l.tel_fidei_ext = '" + cancelado.getTel_empleado_banco_extranjero() + "' ";
                }
//                System.out.println(MySql);
                ResultSet rstSQLServer = statement.executeQuery(MySql);
                movimiento = new Movimiento();
                while (rstSQLServer.next() && idx >= 0) {
                    movimiento.setClave_contrato(clave_contrato);
                    movimiento.setFecha_liquidacion(fecha_liquidacion);
                    movimiento.setNombre_archivo(nombre_archivo);
                    movimiento.setCuenta_deposito(rstSQLServer.getString(1)); //Cuenta Depósito
                    movimiento.setNombre_empleado(rstSQLServer.getString(2)); //Nombre(s) fideicomisario
                    movimiento.setApellidoP_empleado(rstSQLServer.getString(3)); //Apellido Paterno fideicomisario
                    movimiento.setApellidoM_empleado(rstSQLServer.getString(4)); //Apellido Materno fideicomisario
                    movimiento.setNumero_empleado(rstSQLServer.getString(5)); //Identificador de fideicomisario
                    movimiento.setPuesto_empleado(rstSQLServer.getString(6)); //Puesto del fideicomisario
                    movimiento.setDepartamento_empleado(rstSQLServer.getString(7)); //Departamento del fideicomisario
                    movimiento.setCURP(rstSQLServer.getString(8)); //CURP del fideicomisario
                    movimiento.setTipo_movimiento(rstSQLServer.getString(9)); //Tipo de movimiento
                    movimiento.setClave_banco(rstSQLServer.getString(10)); //Clave de Banco
                    movimiento.setImporte_liquidacion(rstSQLServer.getString(11)); //Importe de liquidación
                    movimiento.setImporte_liquidacion_mxp(rstSQLServer.getString(12)); //Importe de liquidación MXP
                    movimiento.setClave_moneda(rstSQLServer.getString(13)); //Tipo de moneda
                    movimiento.setNombre_receptor_cheque(rstSQLServer.getString(14)); //Receptor de cheques
                    movimiento.setDomicilio_destino_cheque(rstSQLServer.getString(15)); //Domicilio receptor de cheques
                    movimiento.setTel_destino_cheque(rstSQLServer.getString(16));//Teléfono receptor de cheques
                    movimiento.setCorreo_destino_cheque(rstSQLServer.getString(17)); //Correo receptor de cheques
                    movimiento.setNombre_banco_extranjero(rstSQLServer.getString(18));//Banco extranjero
                    movimiento.setDomicilio_banco_extranjero(rstSQLServer.getString(19)); //Domicilio Banco extranjero
                    movimiento.setPais_banco_extranjero(rstSQLServer.getString(20));//País Banco extranjero
                    movimiento.setABA_BIC(rstSQLServer.getString(21));//ABA_BIC
                    movimiento.setNombre_empleado_banco_extranjero(rstSQLServer.getString(22));//Nombre fideicomisario en extranjero
                    movimiento.setDom_empleado_banco_extranjero(rstSQLServer.getString(23));//Domicilio fideicomisario en extranjero
                    movimiento.setPais_empleado_banco_extranjero(rstSQLServer.getString(24)); //País fideicomisario en extranjero
                    movimiento.setTel_empleado_banco_extranjero(rstSQLServer.getString(25));//Teléfono fideicomisario en extranjero
                    movimiento.setIsEmpty(false);
                    fecha_ingreso = rstSQLServer.getString(26);
                    id = fecha_ingreso.indexOf(" ");
                    if (id > 0) {
                        fecha_ingreso = fecha_ingreso.substring(0, id).toString().trim();
                        fecha_ingreso = fecha.CambiaFormatoFecha("yyyy-MM-dd", "dd/MM/yyyy", fecha_ingreso);
                    } else {
                        movimiento.setMotivo_cancelacion("Error obteniendo el formato de la fecha de ingreso");
                        movimiento.setIsEmpty(true);
                    }
                    movimiento.setFecha_ingreso(fecha_ingreso); //Fecha de ingreso al empleo
                    idx--;
                }
                if (idx == 1) {
                    movimiento.setMotivo_cancelacion("No se cuenta con algún movimiento registrado según la información proporcionada");
                    movimiento.setIsEmpty(true);
                } else if (idx == 0) {
                    movimiento.setMotivo_cancelacion("Seleccione el motivo de la cancelación");
                } else if (idx < 0) {
                    movimiento = cancelado;
                    movimiento.setMotivo_cancelacion("Favor de especificar más información del movimiento");
                    movimiento.setIsEmpty(true);
                }
                rstSQLServer.close();
                statement.close();

                if (connection != null) {
                    conn.Desconecta(connection);
                }
            } else {
                movimiento = cancelado;
                movimiento.setMotivo_cancelacion(mensaje);
            }
        } catch (Exception e) {
            movimiento = null;
            if (connection != null) {
                conn.Desconecta(connection);
            }
            System.out.println("getDatosMovimiento:" + e.getMessage());
        }
        return movimiento;
    }

    /**
     * Método que proporciona los datos generales del movimiento qie se desea
     * habilitar.
     *
     * @param cancelado: Bean que contiene la información de un movimiento
     * cancelado.
     * @return Movimiento:Información del Bean registrado en la base con la
     * información que se pasa como parámetro,si ocurre algún error regresa
     * null.
     */
    public static Movimiento getCancelacionParcial(Movimiento cancelado, String status) {
        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        Movimiento movimiento = null;
        String MySql = "";
        String fecha_ingreso = null;
        String fecha_cancelacion = null;
        String mensaje = null;
        clsFecha fecha = new clsFecha();
        int idx = 1, id = 0;
        try {
            fecha_ingreso = cancelado.getFecha_ingreso();
            if (fecha_ingreso != null && !fecha_ingreso.equals("")) {
                if (fecha.fechaValida(fecha_ingreso, "dd/MM/yyyy")) {
                } else {
                    mensaje = "Formato de fecha inválido en la fecha de ingreso";
                }
            }
            if (mensaje == null) {
                connection = conn.ConectaSQLServer();
                statement = connection.createStatement();
                statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

                MySql = " select cuenta_deposito, nombre_empleado, apellidoP_empleado, apellidoM_empleado, ";
                MySql += " clave_empleado, puesto_empleado, depto_empleado, curp, ";
                MySql += " tipo_movimiento, clave_banco, importe_liquidacion, tipo_moneda, ";
                MySql += " envio_cheque, destino_envio_cheque, tel_envio_cheque, correo_envio_cheque, ";
                MySql += " banco_extranjero, dom_banco_extranjero, pais_banco_extranjero, ABA_BIC, ";
                MySql += " nombre_fidei_banco_ext, direccion_fidei_ext, pais_fidei_ext, tel_fidei_ext, ";
                MySql += " motivo, fecha_ingreso, fecha_usuario_cancela ";
                MySql += " from movimientos_cancelados ";
                MySql += " where clave_contrato = '" + cancelado.getClave_contrato() + "' ";
                MySql += " and fecha_liquidacion = '" + new clsFecha().CambiaFormatoFecha("dd/MM/yyyy", "yyyy-MM-dd", cancelado.getFecha_liquidacion()) + " 00:00:00.00' ";
                MySql += " and nombre_archivo = '" + cancelado.getNombre_archivo() + "' ";
                MySql += " and status = '" + status + "' ";

                if (!cancelado.getCuenta_deposito().equals("")) {
                    MySql += " and cuenta_deposito = '" + cancelado.getCuenta_deposito() + "' ";
                }
                if (!cancelado.getNombre_empleado().equals("")) {
                    MySql += " and nombre_empleado = '" + cancelado.getNombre_empleado() + "' ";
                }
                if (!cancelado.getApellidoP_empleado().equals("")) {
                    MySql += " and apellidoP_empleado = '" + cancelado.getApellidoP_empleado() + "' ";
                }
                if (!cancelado.getApellidoM_empleado().equals("")) {
                    MySql += " and apellidoM_empleado = '" + cancelado.getApellidoM_empleado() + "' ";
                }
                if (!cancelado.getNumero_empleado().equals("")) {
                    MySql += " and clave_empleado = '" + cancelado.getNumero_empleado() + "' ";
                }
                if (!cancelado.getPuesto_empleado().equals("")) {
                    MySql += " and puesto_empleado = '" + cancelado.getPuesto_empleado() + "' ";
                }
                if (!cancelado.getDepartamento_empleado().equals("")) {
                    MySql += " and depto_empleado = '" + cancelado.getDepartamento_empleado() + "' ";
                }
                if (!cancelado.getCURP().equals("")) {
                    MySql += " and curp = '" + cancelado.getCURP() + "' ";
                }
                if (!cancelado.getFecha_ingreso().equals("")) {
                    MySql += " and fecha_ingreso = '" + cancelado.getFecha_ingreso() + " 00:00:00.000' ";
                }
                if (!cancelado.getTipo_movimiento().equals("")) {
                    MySql += " and tipo_movimiento = '" + cancelado.getTipo_movimiento() + "' ";
                }
                if (!cancelado.getClave_banco().equals("")) {
                    MySql += " and clave_banco = '" + cancelado.getClave_banco() + "' ";
                }
                if (!cancelado.getImporte_liquidacion().equals("")) {
                    MySql += " and importe_liquidacion = '" + cancelado.getImporte_liquidacion() + "' ";
                }
                if (!cancelado.getClave_moneda().equals("")) {
                    MySql += " and tipo_moneda = '" + cancelado.getClave_moneda() + "' ";
                }
                if (!cancelado.getNombre_receptor_cheque().equals("")) {
                    MySql += " and envio_cheque = '" + cancelado.getNombre_receptor_cheque() + "' ";
                }
                if (!cancelado.getDomicilio_destino_cheque().equals("")) {
                    MySql += " and destino_envio_cheque = '" + cancelado.getDomicilio_destino_cheque() + "' ";
                }
                if (!cancelado.getTel_destino_cheque().equals("")) {
                    MySql += " and tel_envio_cheque = '" + cancelado.getTel_destino_cheque() + "' ";
                }
                if (!cancelado.getCorreo_destino_cheque().equals("")) {
                    MySql += " and correo_envio_cheque = '" + cancelado.getCorreo_destino_cheque() + "' ";
                }
                if (!cancelado.getNombre_banco_extranjero().equals("")) {
                    MySql += " and banco_extranjero = '" + cancelado.getNombre_banco_extranjero() + "' ";
                }
                if (!cancelado.getDomicilio_banco_extranjero().equals("")) {
                    MySql += " and dom_banco_extranjero = '" + cancelado.getDomicilio_banco_extranjero() + "' ";
                }
                if (!cancelado.getPais_banco_extranjero().equals("")) {
                    MySql += " and pais_banco_extranjero = '" + cancelado.getPais_banco_extranjero() + "' ";
                }
                if (!cancelado.getABA_BIC().equals("")) {
                    MySql += " and ABA_BIC = '" + cancelado.getABA_BIC() + "' ";
                }
                if (!cancelado.getNombre_empleado_banco_extranjero().equals("")) {
                    MySql += " and nombre_fidei_banco_ext = '" + cancelado.getNombre_empleado_banco_extranjero() + "' ";
                }
                if (!cancelado.getDom_empleado_banco_extranjero().equals("")) {
                    MySql += " and direccion_fidei_ext = '" + cancelado.getDom_empleado_banco_extranjero() + "' ";
                }
                if (!cancelado.getPais_empleado_banco_extranjero().equals("")) {
                    MySql += " and pais_fidei_ext = '" + cancelado.getPais_empleado_banco_extranjero() + "' ";
                }
                if (!cancelado.getTel_empleado_banco_extranjero().equals("")) {
                    MySql += " and tel_fidei_ext = '" + cancelado.getTel_empleado_banco_extranjero() + "' ";
                }
                if (!cancelado.getMotivo_cancelacion().equals("")) {
                    MySql += " and motivo = '" + cancelado.getMotivo_cancelacion() + "' ";
                }

//                System.out.println(MySql);
                ResultSet rstSQLServer = statement.executeQuery(MySql);
                movimiento = new Movimiento();

                while (rstSQLServer.next() && idx >= 0) {
                    if (idx != 0) {
                        movimiento.setClave_contrato(cancelado.getClave_contrato());
                        movimiento.setFecha_liquidacion(cancelado.getFecha_liquidacion());
                        movimiento.setNombre_archivo(cancelado.getNombre_archivo());
                        movimiento.setCuenta_deposito(rstSQLServer.getString(1)); //Cuenta Depósito
                        movimiento.setNombre_empleado(rstSQLServer.getString(2)); //Nombre(s) fideicomisario
                        movimiento.setApellidoP_empleado(rstSQLServer.getString(3)); //Apellido Paterno fideicomisario
                        movimiento.setApellidoM_empleado(rstSQLServer.getString(4)); //Apellido Materno fideicomisario

                        movimiento.setNumero_empleado(rstSQLServer.getString(5)); //Identificador de fideicomisario
                        movimiento.setPuesto_empleado(rstSQLServer.getString(6)); //Puesto del fideicomisario
                        movimiento.setDepartamento_empleado(rstSQLServer.getString(7)); //Departamento del fideicomisario
                        movimiento.setCURP(rstSQLServer.getString(8)); //CURP del fideicomisario

                        movimiento.setTipo_movimiento(rstSQLServer.getString(9)); //Tipo de movimiento
                        movimiento.setClave_banco(rstSQLServer.getString(10)); //Clave de Banco
                        movimiento.setImporte_liquidacion(rstSQLServer.getString(11)); //Importe de liquidación
                        movimiento.setClave_moneda(rstSQLServer.getString(12)); //Tipo de moneda

                        movimiento.setNombre_receptor_cheque(rstSQLServer.getString(13)); //Receptor de cheques
                        movimiento.setDomicilio_destino_cheque(rstSQLServer.getString(14)); //Domicilio receptor de cheques
                        movimiento.setTel_destino_cheque(rstSQLServer.getString(15));//Teléfono receptor de cheques
                        movimiento.setCorreo_destino_cheque(rstSQLServer.getString(16)); //Correo receptor de cheques

                        movimiento.setNombre_banco_extranjero(rstSQLServer.getString(17));//Banco extranjero
                        movimiento.setDomicilio_banco_extranjero(rstSQLServer.getString(18)); //Domicilio Banco extranjero
                        movimiento.setPais_banco_extranjero(rstSQLServer.getString(19));//País Banco extranjero
                        movimiento.setABA_BIC(rstSQLServer.getString(20));//ABA_BIC

                        movimiento.setNombre_empleado_banco_extranjero(rstSQLServer.getString(21));//Nombre fideicomisario en extranjero
                        movimiento.setDom_empleado_banco_extranjero(rstSQLServer.getString(22));//Domicilio fideicomisario en extranjero
                        movimiento.setPais_empleado_banco_extranjero(rstSQLServer.getString(23)); //País fideicomisario en extranjero
                        movimiento.setTel_empleado_banco_extranjero(rstSQLServer.getString(24));//Teléfono fideicomisario en extranjero

                        movimiento.setMotivo_cancelacion(rstSQLServer.getString(25)); //Motivo de cancelación.
                        fecha_ingreso = rstSQLServer.getString(26);
                        fecha_cancelacion = rstSQLServer.getString(27);//Fecha de cancelación
                        movimiento.setIsEmpty(false);
                        //Damos formato a la fecha de ingreso.
                        id = fecha_ingreso.indexOf(" ");
                        if (id > 0) {
                            fecha_ingreso = fecha_ingreso.substring(0, id).toString().trim();
                            fecha_ingreso = fecha.CambiaFormatoFecha("yyyy-MM-dd", "dd/MM/yyyy", fecha_ingreso);
                            movimiento.setFecha_ingreso(fecha_ingreso);
                        } else {
                            movimiento.setNombre_fideicomisario("Error obteniendo el formato de la fecha de ingreso");
                            movimiento.setIsEmpty(true);
                        }
                        //Damos formato a la fecha de cancelación.
                        id = fecha_cancelacion.indexOf(" ");
                        if (id > 0) {
                            fecha_cancelacion = fecha_cancelacion.substring(0, id).toString().trim();
                            fecha_cancelacion = fecha.CambiaFormatoFecha("yyyy-MM-dd", "dd/MM/yyyy", fecha_cancelacion);
                        } else {
                            movimiento.setNombre_fideicomisario("Error obteniendo el formato de la fecha de cancelación");
                            movimiento.setIsEmpty(true);
                        }
                        idx--;
                    } else {
                        idx--;
                    }
                }
                if (idx == 1) {
                    movimiento.setNombre_fideicomisario("No se cuenta con un movimiento cancelado previamente según la información proporcionada");
                    movimiento.setIsEmpty(true);
                } else if (idx == 0) {
                    movimiento.setNombre_fideicomisario("Fecha de cancelación: " + fecha_cancelacion);
                } else if (idx < 0) {
                    movimiento = cancelado;
                    movimiento.setNombre_fideicomisario("Favor de especificar más información del movimiento");
                    movimiento.setIsEmpty(true);
                }
                rstSQLServer.close();
                statement.close();
                if (connection != null) {
                    conn.Desconecta(connection);
                }
            } else {
                movimiento = cancelado;
                movimiento.setNombre_fideicomisario(mensaje);
                movimiento.setIsEmpty(true);
            }
        } catch (Exception e) {
            movimiento = null;
            if (connection != null) {
                conn.Desconecta(connection);
            }
            System.out.println("getCancelacionParcial:" + e.getMessage());
        }
        return movimiento;
    }

    /**
     * Método que regresa el conjunto de correos asociados a las áreas que se
     * especifican como parámetro.
     *
     * @param String departamento: Nombre del departamento :
     * 'TESORERIA','OPERACION'
     * @return boolean String correos:
     * denis-hernandez@gp.org.mx,fernando-cardenas@gp.orgt.mx
     */
    public static String obtenCorreos(String departamento) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;

        String correo = "";
        String MySql = "";

        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select correo ";
            MySql += " from usuarios_admin ";
            MySql += " where departamento in ( " + departamento + ")";
            MySql += " and status = 'A' ";

//            System.out.println(MySql);
            ResultSet rstSQLServer = statement.executeQuery(MySql);

            while (rstSQLServer.next()) {
                if (correo.equals("")) {
                    correo = rstSQLServer.getString(1).toString().trim();
                } else {
                    correo = correo + "," + rstSQLServer.getString(1).toString().trim();
                }
            }
            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
//            System.out.println("correos:" + correo);
        } catch (Exception e) {
            correo = "";
            System.out.println("ModeloLayOut-validaTipoMomeda:" + e.toString());
        }
        return correo;//Este es el original se obtiene el correo de sofom
    }

    /**
     * Método que proporciona el conjunto de fideicomitentes que corresponden a
     * los datos que se pasan como parámetro.
     *
     * @param clave_contrato: Clave de fideicomiso.
     * @param fecha_liquidacion: Fecha de liquidación.
     * @param tipo_movimiento: Tipo de movimiento.
     * @param nombre_archivo: Nombre del Lote.
     * @param status: Status del LayOut.
     * @return Vector: Nobre de fideicomitentes, en caso de ocurrir algún error
     * regresa null.
     */
    public static String generaExcelLote(String clave_contrato, String fecha_liquidacion,
            String nombre_archivo, String urlArchivo, String status_lote, int idx_archivo) {
        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        ResultSet rstSQLServer = null;

        HSSFRow fila = null;
        HSSFCell celda = null;
        HSSFRichTextString texto = null;
        FileOutputStream elFichero = null;

        String genera = "";
        String MySql = "";
        String dato = "";
        String fecha = "";
        String file_name = "";
        String clave_fidei = "";
        int j = 0, k = 0;
        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            MySql = " select h.clave_contrato, h.fecha_liquidacion, cuentas.num_cuenta, l.clave_banco, ";
            MySql += " l.cuenta_deposito, l.importe_liquidacion_mxp, l.importe_liquidacion, l.tipo_movimiento,  ";
            MySql += " l.nombre_empleado, l.apellidoP_empleado, l.apellidoM_empleado, l.curp ";
            MySql += " from movimientos_h h, movimientos l, cuentas_banco cuentas, contratos contratos ";
            MySql += " where h.clave_contrato = l.clave_contrato ";
            MySql += " and l.clave_contrato = contratos.clave_contrato ";
            MySql += " and h.fecha_liquidacion = l.fecha_liquidacion ";
            MySql += " and h.nombre_archivo = l.nombre_archivo ";
            MySql += " and cuentas.cuenta_origen = contratos.cuenta_origen ";
            MySql += " and h.clave_contrato = '" + clave_contrato + "' ";
            MySql += " and h.fecha_liquidacion = '" + (new clsFecha().CambiaFormatoFecha("dd/MM/yyyy", "yyyy-MM-dd", fecha_liquidacion)) + " 00:00:00.00' ";
            MySql += " and h.nombre_archivo = '" + nombre_archivo + "' ";
            MySql += " and contratos.status = 'A'  ";
            MySql += " and cuentas.status = 'A'  ";
            MySql += " and h.status = '" + status_lote + "'  ";
            MySql += " order by l.nombre_empleado asc ";
//            System.out.println("generaExcelLote:" + MySql);
            rstSQLServer = statement.executeQuery(MySql);
            if (rstSQLServer.next()) {
                // Se crea el libro.
                HSSFWorkbook libro = new HSSFWorkbook();
                // Se crea una hoja dentro del libro.
                HSSFSheet hoja = libro.createSheet();
                // Se crea una fila dentro de la hoja.
                fila = hoja.createRow(j);
                //Se crea el encabezado de cada columna.
                HSSFCell celda0 = fila.createCell((short) 0); //(celda dentro de la fila)
                // Se crea el contenido de la celda y se mete en ella.
                HSSFRichTextString texto0 = new HSSFRichTextString("CONTRATO");
                celda0.setCellValue(texto0);

                HSSFCell celda1 = fila.createCell((short) 1);
                HSSFRichTextString texto1 = new HSSFRichTextString("FECHA DE LIQUIDACIÓN");
                celda1.setCellValue(texto1);

                HSSFCell celda2 = fila.createCell((short) 2);
                HSSFRichTextString texto2 = new HSSFRichTextString("CUENTA DE ORIGEN");
                celda2.setCellValue(texto2);

                HSSFCell celda3 = fila.createCell((short) 3);
                HSSFRichTextString texto3 = new HSSFRichTextString("BANCO");
                celda3.setCellValue(texto3);

                HSSFCell celda4 = fila.createCell((short) 4);
                HSSFRichTextString texto4 = new HSSFRichTextString("CUENTA DE DEPÓSITO");
                celda4.setCellValue(texto4);

                HSSFCell celda5 = fila.createCell((short) 5);
                HSSFRichTextString texto5 = new HSSFRichTextString("IMPORTE DE LA LIQUIDACIÓN");
                celda5.setCellValue(texto5);

                HSSFCell celda6 = fila.createCell((short) 6);
                HSSFRichTextString texto6 = new HSSFRichTextString("TIPO DE MOVIMIENTO");
                celda6.setCellValue(texto6);

                HSSFCell celda7 = fila.createCell((short) 7);
                HSSFRichTextString texto7 = new HSSFRichTextString("NOMBRE(S) DEL FIDEICOMISARIO");
                celda7.setCellValue(texto7);

                HSSFCell celda8 = fila.createCell((short) 8);
                HSSFRichTextString texto8 = new HSSFRichTextString("APELLIDO PATERNO FIDEICOMISARIO");
                celda8.setCellValue(texto8);

                HSSFCell celda9 = fila.createCell((short) 9);
                HSSFRichTextString texto9 = new HSSFRichTextString("APELLIDO MATERNO FIDEICOMISARIO");
                celda9.setCellValue(texto9);

                HSSFCell celda10 = fila.createCell((short) 10);
                HSSFRichTextString texto10 = new HSSFRichTextString("C.U.R.P.");
                celda10.setCellValue(texto10);
                do {
                    j++;
                    // Se crea una fila dentro de la hoja
                    fila = hoja.createRow(j);
                    for (int i = 0; i < 11; i++) {
                        if (i < 5) {
                            dato = rstSQLServer.getString(i + 1).toString().trim();
                            if (i == 1) {
                                k = dato.indexOf(" ");
                                if (k > 0) {
                                    dato = dato.substring(0, k).toString().trim();
                                }
                            }
                            texto = new HSSFRichTextString(dato);
                            celda = fila.createCell((short) i);
                            celda.setCellValue(texto);
                        } else if (i == 5) {
                            dato = rstSQLServer.getString(i + 1).toString().trim();
                            if (dato.equals("0")) {
                                dato = rstSQLServer.getString(i + 2).toString().trim();
                                texto = new HSSFRichTextString(dato);
                                celda = fila.createCell((short) i);
                                celda.setCellValue(texto);
                            } else {
                                texto = new HSSFRichTextString(dato);
                                celda = fila.createCell((short) i);
                                celda.setCellValue(texto);
                            }
                        } else {
                            dato = rstSQLServer.getString(i + 2).toString().trim();
                            texto = new HSSFRichTextString(dato);
                            celda = fila.createCell((short) i);
                            celda.setCellValue(texto);
                        }
                    }
                } while (rstSQLServer.next());
                fecha = ModeloLiquidation.getFormatoFecha(fecha_liquidacion);
                if (!fecha.equals("")) {
                    clave_fidei = clave_contrato.substring(6, 9);
                    if (idx_archivo > 0) {
                        if (idx_archivo > 0 && idx_archivo <= 9) {
                            file_name = clave_fidei + "-0" + idx_archivo + "-" + "LQ" + "-01-" + fecha + ".xls";
                        } else {
                            file_name = clave_fidei + "-" + idx_archivo + "-" + "LQ" + "-01-" + fecha + ".xls";
                        }
                        elFichero = new FileOutputStream(urlArchivo + file_name);
                        libro.write(elFichero);
                        if (elFichero != null) {
                            elFichero.close();
                        }
                    } else {
                        genera = "Identificador de lote inválido";
                    }
                } else {
                    genera = " Excel: Error formato de fecha ";
                }
            } else {
                genera = " Excel: No se arrojó algún movimiento ";
            }
            rstSQLServer.close();
            statement.close();
            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            genera = " Excel: Error generando reporte ";
            System.out.println("generaExcelLote:" + e.getMessage());
            try {
                if (rstSQLServer != null) {
                    rstSQLServer.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    conn.Desconecta(connection);
                }
                if (elFichero != null) {
                    elFichero.close();
                }
            } catch (Exception ex) {
            }
        }
        return genera;
    }

    /**
     * Método que genera los reportes de liquidación de tipo 1,2, 3 y 4.
     *
     * @param n_cliente : Nombre del cliente.
     * @param clave_contrato : clave del contrato del cliente.
     * @param fecha_liquidacion : Fecha de Liquidación.
     * @param nombre_archivo : Nombre del Lay-Out al que corresponden los
     * movimientos.
     * @param status : Estatus actual del lote.
     * @param urlArchivo : Ruta donde se guardará el reporte.
     * @param persona_genera : Persona que genera el reporte de liquidación.
     * @param verifica : Número de lotes procesados satisfactoriamente.
     * @return String valido: Regresa una cadena que contiene un mnsaje
     * descriptivo de la transacción realizada, si ocurre algún error regresa un
     * mensaje descriptivo del error. Si todo sale bien regresa una cadena
     * vacia.
     */
    public String generaReportesLiquidacionMXP(String n_cliente, String clave_contrato, String fecha_liquidacion,
            String nombre_archivo, String status, String urlArchivo, String persona_genera, int verifica, String realPath) {

        boolean reporte = false;
        String genera = "";
        int movimiento = -1;
        reportesGenerados = "";
        Vector tipos_mov = null;
        clsConexion conn = new clsConexion();
        Connection connection = null;

        try {
            System.out.println("Comienza generacion de reportes de liquidacion");
            tipos_mov = ModeloLiquidation.getTiposMovimiento(clave_contrato, fecha_liquidacion, nombre_archivo, status);
            if (tipos_mov != null) {
                //Creamos la conexión a la Base de Datos.
                connection = conn.ConectaSQLServer();
                for (int i = 0; i < tipos_mov.size(); i++) {
                    movimiento = (Integer) tipos_mov.get(i);

                    switch (movimiento) {
                        case 1:
                            //Generamos el Reporte de Liquidación Correspondiente al Movimiento de Tipo 1.
                            reporte = this.creaReporteLiquidacion("BB", realPath + "\\WEB-INF\\classes\\Common\\LayOutM1.jrxml", clave_contrato, fecha_liquidacion, n_cliente, connection, nombre_archivo, status, urlArchivo, persona_genera, verifica, realPath);
                            //Verificamos si surgio algun error al generar el reporte de liquidación.
                            if (reporte == false) {
                                if (connection != null) {
                                    conn.Desconecta(connection);
                                }
                                return genera = " Error al generar el reporte de liquidación:Bancomer a Bancomer ";
                            }
                            break;
                        case 2:
                            reporte = this.creaReporteLiquidacion("OT", realPath + "\\WEB-INF\\classes\\Common\\LayOutM2.jrxml", clave_contrato, fecha_liquidacion, n_cliente, connection, nombre_archivo, status, urlArchivo, persona_genera, verifica, realPath);
                            //Verificamos si surgio algun error al generar el reporte de liquidación.
                            if (reporte == false) {
                                if (connection != null) {
                                    conn.Desconecta(connection);
                                }
                                return genera = " Error al Generar el reportes de liquidación:Bancomer a Otros Bancos ";
                            }
                            break;
                        case 3:
                            reporte = this.creaReporteLiquidacion("TD", realPath + "\\WEB-INF\\classes\\Common\\LayOutM3.jrxml", clave_contrato, fecha_liquidacion, n_cliente, connection, nombre_archivo, status, urlArchivo, persona_genera, verifica, realPath);
                            //Verificamos si surgio algun error al generar el reporte de liquidación.
                            if (reporte == false) {
                                if (connection != null) {
                                    conn.Desconecta(connection);
                                }
                                return genera = " Error al generar el reporte de liquidación:Bancomer a TDD Bancomer ";
                            }
                            break;
                        case 4:
                            //Generamos el Reporte de Liquidación Correspondiente al Movimiento Tipo 4.
                            reporte = this.creaReporteLiquidacion("CH", realPath + "\\WEB-INF\\classes\\Common\\LayOutM4.jrxml", clave_contrato, fecha_liquidacion, n_cliente, connection, nombre_archivo, status, urlArchivo, persona_genera, verifica, realPath);
                            //Verificamos si surgio algun error al generar el reporte de liquidación.
                            if (reporte == false) {
                                if (connection != null) {
                                    conn.Desconecta(connection);
                                }
                                return genera = " Error al generar el reporte de liquidación:Emisión de Cheques ";
                            }
                            break;

                        case 5:
                            break;

                        default:
                            genera = " Error generando reporte de liquidación, tipo de movimiento inválido ";
                            i = tipos_mov.size();
                    }//End switch
                }//End for
            } else {
                genera = " Error obteniendo tipo de movimientos ";
            }
            if (connection != null) {
                conn.Desconecta(connection);
            }

        } catch (Exception e) {
            reportesGenerados = "";
            genera = " Error al generar los reportes de liquidación correspondientes ";
            if (connection != null) {
                conn.Desconecta(connection);
            }
        }
        return genera;
    }

    public String generaReportesLiquidacionIN(String n_cliente, String clave_contrato, String fecha_liquidacion,
            String nombre_archivo, String status, String urlArchivo, String persona_genera, int verifica, String realPath) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        boolean creaRM5 = false;
        String genera = "";
        try {
            //Creamos la conexión a la Base de Datos.
            connection = conn.ConectaSQLServer();
            creaRM5 = this.creaReporteLiquidacion("IN", realPath + "\\WEB-INF\\classes\\Common\\LayOutM5.jrxml", clave_contrato, fecha_liquidacion, n_cliente, connection, nombre_archivo, status, urlArchivo, persona_genera, verifica, realPath);
            if (creaRM5 == false) {
                genera = " Error generando reportes de movimientos a bancos extranjeros ";
            }
            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            genera = " Error generando reportes de bancos extranjeros ";
            if (connection != null) {
                conn.Desconecta(connection);
            }
        }
        return genera;
    }

    public synchronized String generaReportesLiquidacion(String n_cliente, String clave_contrato, String fecha_liquidacion,
            String nombre_archivo, String status, String urlArchivo, String persona_genera, int verifica, String realPath) {

        String genera = "";
        //Verificamos si las transacciones ya fueron generadas por otro usuario.
        genera = ModeloLiquidation.verificaActualizacion(clave_contrato, fecha_liquidacion, nombre_archivo, status);
        //Si otro usuario no a creado los reportes los creamos.
        if (genera.equals("")) {
            genera = this.generaReportesLiquidacionMXP(n_cliente, clave_contrato, fecha_liquidacion, nombre_archivo, status, urlArchivo, persona_genera, verifica, realPath);
            if (genera.equals("")) {
                genera = this.generaReportesLiquidacionIN(n_cliente, clave_contrato, fecha_liquidacion, nombre_archivo, status, urlArchivo, persona_genera, verifica, realPath);
            }
        }
        return genera;
    }

    /**
     * Se regresa el valor del importe de liquidación en moneda nacional de los
     * movimientos extranjeros encontrados en el lote(recordar que sólo es un
     * movimiento por lote). Si el valor importe_liquidacion_mxp = 0, quiere
     * decir que ahún no se ha indicado el importe de liq en MXP. Si se
     * encuentra algún error, entonces se regresa vector vacio y en la vista se
     * mostrará lo que se encontró en la busqueda anterior
     *
     * @param clave_contrato
     * @param fecha_liquidacion
     * @param nombre_archivo
     * @return
     */
    private static Vector getImporteLiqMovsTipo5(String clave_contrato, java.util.Date fecha_liquidacion, String nombre_archivo) {
        String stringRetorno = "";
        Vector vecRetorno = new Vector();
        DecimalFormat dec = new DecimalFormat("#,##0.00");
        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;

        String MySql = "";

        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            MySql += " select importe_liquidacion,importe_liquidacion_mxp, tipo_moneda ";
            MySql += " from movimientos ";
            MySql += " where clave_contrato='" + clave_contrato + "' ";
            MySql += " and fecha_liquidacion = '" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(fecha_liquidacion) + "' ";
            MySql += " and nombre_archivo = '" + nombre_archivo + "' ";
            MySql += " and tipo_movimiento=5  ";
            ResultSet rstSQLServer = statement.executeQuery(MySql);

            if (rstSQLServer.next()) {
                double importe_liquidacion_mxp = rstSQLServer.getDouble("importe_liquidacion_mxp");
                double importe_liquidacion = rstSQLServer.getDouble("importe_liquidacion");
                double importe = 0;
                if (importe_liquidacion_mxp > 0) {
                    stringRetorno = "$ " + dec.format(importe_liquidacion_mxp);
                    importe = importe_liquidacion_mxp;
                } else {
                    stringRetorno = rstSQLServer.getString("tipo_moneda") + " " + dec.format(importe_liquidacion);
                }
                vecRetorno.add(stringRetorno);
                vecRetorno.add(importe);
            }

            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            System.out.println("ModeloLayOut-contieneMovsTipo5:" + e.toString());
            vecRetorno = new Vector();
        }

        return vecRetorno;
    }

    /**
     * Método que regresa la clave del LayOut que corresponde a los datos que se
     * le pasan como parámetro.
     *
     * @param clave_contrato : clave de Fideicomiso.
     * @param fecha_liquidacion : fecha de Liquidación.
     * @param nombre_archivo : Nombre del Archivo.
     * @return int : Identificador asociado al lote cargando. Si ocurre algún
     * error regresa -1.
     */
    public static Vector getTiposMovimiento(String clave_contrato, String fecha_liquidacion, String nombre_archivo, String status) {
        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String MySql = "";
        Vector tipos = null;
        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select distinct(tipo_movimiento) ";
            MySql += " from contratos c, movimientos_h h, movimientos l ";
            MySql += " where h.clave_contrato = l.clave_contrato ";
            MySql += " and h.clave_contrato = c.clave_contrato ";
            MySql += " and h.fecha_liquidacion = l.fecha_liquidacion ";
            MySql += " and h.nombre_archivo = l.nombre_archivo ";
            MySql += " and h.clave_contrato = '" + clave_contrato + "' ";
            MySql += " and h.fecha_liquidacion = '" + (new clsFecha().CambiaFormatoFecha("dd/MM/yyyy", "yyyy-MM-dd", fecha_liquidacion)) + " 00:00:00.000'  ";
            MySql += " and h.nombre_archivo = '" + nombre_archivo + "'  ";
            MySql += " and c.status = 'A' ";
            MySql += " and h.status = '" + status + "'";
            MySql += " order by tipo_movimiento asc ";

            System.out.println("MySql_getTiposMovimientos:" + MySql);
            ResultSet rstSQLServer = statement.executeQuery(MySql);
            tipos = new Vector();
            while (rstSQLServer.next()) {
                tipos.add(rstSQLServer.getInt(1));
            }
            rstSQLServer.close();
            statement.close();
            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            tipos = null;
            if (connection != null) {
                conn.Desconecta(connection);
            }
            System.out.println("ModeloLiquidation-getTiposMovimiento:" + e.getMessage());
        }
        return tipos;
    }

    /**
     * Método que regresa el nombre del resumen de liquidación asociado al
     * fideicomiso que se le pasa como parámetro.
     *
     * @param clave_contrato : Clave de fideicomiso.
     * @param fecha_liquidacion : Fecha en que tendrá lugar la liquidación.
     * @param verifica : Identificador del lote.
     * @return String : Nombre del resumen de liquidación, si surge algún error
     * regresa una cadena vacia.
     */
    public static String getNombreResumenLiquidacion(String clave_contrato, String fecha_liquidacion, int verifica) {
        String nombre = "";
        String fecha = "";
        String clave_fidei = "";
        try {
            fecha = ModeloLiquidation.getFormatoFecha(fecha_liquidacion);
            if (!fecha.equals("") && verifica != -1) {
                clave_fidei = clave_contrato.substring(6, 9);
                if (verifica > 0 && verifica <= 9) {
                    nombre = clave_fidei + "-0" + verifica + "-" + "LQ" + "-01-" + fecha + ".pdf";
                } else {
                    nombre = clave_fidei + "-" + verifica + "-" + "LQ" + "-01-" + fecha + ".pdf";
                }
            } else {
                nombre = "";
            }
        } catch (Exception e) {
            nombre = "";
            System.out.println("ModeloLiquidation-getNombreResumenLiquidacion" + e.getMessage());
        }
        return nombre;
    }

}
