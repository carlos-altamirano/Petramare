package mx.garante.liquidaciones.Modelos;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Vector;
import java.util.List;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import mx.garante.liquidaciones.Common.clsConexion;

import mx.garante.liquidaciones.Common.clsFecha;
import mx.garante.liquidaciones.Beans.Usuario;
import mx.garante.liquidaciones.Beans.Movimiento;
import mx.garante.liquidaciones.Beans.ResumenMovimientos;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModeloCapture {

    //Almacena los moviminetos validados correctamente, listos para almacenar en la BD.
    public static Vector movimientosValidos = new Vector();
    //Variable que almacena la fecha en la que tendrá lugar la liquidacción.
    public static String nombre_archivo = "";
    public static String fechaLiquidacion = "";
    public static int clave_archivo = -1;

    public static void setNombre_archivo(String nombre_archivo) {
        ModeloCapture.nombre_archivo = nombre_archivo;
    }

    public static String getNombre_archivo() {
        return nombre_archivo;
    }

    public static String getFechaLiquidacion() {
        return fechaLiquidacion;
    }

    public static void setFechaLiquidacion(String fechaLiquidacion) {
        ModeloCapture.fechaLiquidacion = fechaLiquidacion;
    }

    public static int getClave_archivo() {
        return clave_archivo;
    }

    public static void setClave_archivo(int clave_archivo) {
        ModeloCapture.clave_archivo = clave_archivo;
    }

    /**
     * Método para los distintos tipos de movimiento registrados en la base de
     * datos.
     *
     * @return Vector: 1 .- A cuentas Bancomer. Si ocurre algún error regresa
     * null.
     */
    public static Vector getTiposMovimiento() {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;

        Vector movimientos = new Vector();
        String movimiento = "";
        String MySql = "";

        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select tipo_movimiento, movimiento ";
            MySql += " from tipos_movimiento ";
//            MySql += " where tipo_movimiento not in ('5')";

//            System.out.println(MySql);
            ResultSet rstSQLServer = statement.executeQuery(MySql);
            movimientos.add("    Seleccione    ");
            while (rstSQLServer.next()) {
                movimiento = rstSQLServer.getString(1).toString().trim();
                movimiento = movimiento + ".-" + rstSQLServer.getString(2).toString().trim();
                movimientos.add(movimiento);
            }
            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            movimientos = null;
            System.out.println("ModeloCapture-getTiposMovimiento:" + e.toString());
        }
        return movimientos;
    }

    /**
     * Método que regresa el conjunto de claves de banco actuales.
     *
     * @return Vector: 1 .- Bancomer. Si ocurre un error regresa null.
     */
    public static Vector getClavesBanco() {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;

        Vector claves = new Vector();
        String clave = "";
        String MySql = "";

        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = "select clave, banco ";
            MySql += " from bancos  ";
            MySql += " order by clave asc  ";

//            System.out.println(MySql);
            ResultSet rstSQLServer = statement.executeQuery(MySql);
            claves.add("    Seleccione    ");

            while (rstSQLServer.next()) {
                clave = rstSQLServer.getString(1).toString().trim();
                clave = clave + ".-" + rstSQLServer.getString(2).toString().trim();
                claves.add(clave);
            }
            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            clave = null;
            System.out.println("ModeloCapture-getClavesBanco:" + e.toString());
        }
        return claves;
    }

    /**
     * Método que regresa el conjunto de claves de banco actuales.
     *
     * @return Vector: 1 .- Bancomer. Si ocurre un error regresa null.
     */
    public static Vector getClavesBacoABM() {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;

        Vector claves = new Vector();
        String clave = "";
        String MySql = "";

        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = "select clave,abreviacion ";
            MySql += " from bancosABM  ";
            MySql += " order by clave asc  ";

//            System.out.println(MySql);
            ResultSet rstSQLServer = statement.executeQuery(MySql);
            claves.add("    Seleccione    ");

            while (rstSQLServer.next()) {
                clave = rstSQLServer.getString(1).toString().trim();
                clave = clave + ".-" + rstSQLServer.getString(2).toString().trim();
                claves.add(clave);
            }
            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            clave = null;
            System.out.println("ModeloCapture-getClavesBanco:" + e.toString());
        }
        return claves;
    }

    /**
     * Método que regresa el conjunto de claves de banco actuales.
     *
     * @return Vector: 1 .- Bancomer. Si ocurre un error regresa null.
     */
    public static Vector getAbreviacionesBancosABM() {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;

        Vector claves = new Vector();
        String clave = "";
        String MySql = "";

        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = "select abreviacion ";
            MySql += " from bancosABM  ";
            MySql += " order by clave asc  ";

//            System.out.println(MySql);
            ResultSet rstSQLServer = statement.executeQuery(MySql);
            claves.add("    Seleccione    ");

            while (rstSQLServer.next()) {
                // clave = rstSQLServer.getString(1).toString().trim();
                claves.add(rstSQLServer.getString(1).toString().trim());
            }
            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            clave = null;
            System.out.println("ModeloCapture-getAbreviacionesBancosABM:" + e.toString());
        }
        return claves;
    }

    /**
     * Método que regresa el conjunto de claves de moneda actuales.
     *
     * @return Vector: MXP .- MEXICO. Si ocurre un error regresa null.
     */
    public static Vector getClavesMoneda() {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;

        Vector claves = new Vector();
        String clave = "";
        String MySql = "";

        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select clave, pais ";
            MySql += " from monedas ";
            MySql += " order by pais asc  ";

//            System.out.println(MySql);
            ResultSet rstSQLServer = statement.executeQuery(MySql);
            claves.add("  Seleccione  ");

            while (rstSQLServer.next()) {
                clave = rstSQLServer.getString(1).toString().trim();
                clave = clave + ".-" + rstSQLServer.getString(2).toString().trim();
                claves.add(clave);
            }
            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            clave = null;
            System.out.println("ModeloCapture-getClavesMoneda:" + e.toString());
        }
        return claves;
    }

    /**
     * Método que regresa los datos del banco referentes a la clave de banco que
     * se le pasa como parámetro.
     *
     * @return Vector: 12.-BANAMEX.
     */
    public static String getClaveanco(String clave_banco) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;

        String clave = "";
        String MySql = "";

        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select clave, banco ";
            MySql += " from bancos ";
            MySql += " where clave=" + clave_banco + " ";

//            System.out.println(MySql);
            ResultSet rstSQLServer = statement.executeQuery(MySql);

            if (rstSQLServer.next()) {
                clave = rstSQLServer.getString(1).toString().trim() + ".-" + rstSQLServer.getString(2).toString().trim();
            }
            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            clave = "";
            System.out.println("ModeloCapture-getClaveBanco:" + e.toString());
        }
        return clave;
    }

    /**
     * Método que regresa los datos del banco referentes a la clave de banco que
     * se le pasa como parámetro.
     *
     * @return Vector: 12.-BANAMEX.
     */
    public static String getAbreviacionBancoABM(String clave_banco) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;

        String clave = "";
        String MySql = "";

        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select abreviacion ";
            MySql += " from bancosABM ";
            MySql += " where clave=" + clave_banco + " ";

//            System.out.println(MySql);
            ResultSet rstSQLServer = statement.executeQuery(MySql);

            if (rstSQLServer.next()) {
                clave = rstSQLServer.getString(1).toString().trim();
            }
            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            clave = "";
            System.out.println("ModeloCapture-getAbreviacionBancoABM:" + e.toString());
        }
        return clave;
    }

    /**
     * Método que regresa los datos del banco referentes a la clave de banco que
     * se le pasa como parámetro.
     *
     * @return Vector: 12.-BANAMEX.
     */
    public static String getClaveBancoABM(String abreviacion) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;

        String clave = "";
        String MySql = "";

        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select clave ";
            MySql += " from bancosABM ";
            MySql += " where abreviacion='" + abreviacion + "' ";

//            System.out.println(MySql);
            ResultSet rstSQLServer = statement.executeQuery(MySql);

            if (rstSQLServer.next()) {
                clave = rstSQLServer.getString(1).toString().trim();
            }
            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            clave = "";
            System.out.println("ModeloCapture-getClaveBancoABM:" + e.toString());
        }
        return clave;
    }

    /**
     * Método que regresa los datos de la moneda referentes a la clave de moneda
     * que se le pasa como parámetro.
     *
     * @return Vector: MXP.-MÉXICO.
     */
    public static String getClaveMoneda(String clave_moneda) {
        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String clave = "";
        String MySql = "";
        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select clave, pais ";
            MySql += " from monedas ";
            MySql += " where clave='" + clave_moneda + "' ";

//            System.out.println(MySql);
            ResultSet rstSQLServer = statement.executeQuery(MySql);

            if (rstSQLServer.next()) {
                clave = rstSQLServer.getString(1).toString().trim() + ".-" + rstSQLServer.getString(2).toString().trim();
            }
            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            clave = "";
            System.out.println("ModeloCapture-getClaveMoneda:" + e.toString());
        }
        return clave;
    }

    /**
     * Método que valida la cuenta de banco según el tipo de movimientos que se
     * pasa como parámetro.
     *
     * @param tipo_movimiento: Tipo de movimiento.
     * @param cuenta_banco : cuenta de banco del fideicomisario.
     * @return String: Mensaje informativo referente al resultado de la
     * validación.
     */
    public static String validaTipoCuenta(String tipo_movimiento, String clave_banco, String cuenta_banco) {

        String mensaje = "";

        if (tipo_movimiento.equals("1") && cuenta_banco.length() != 10) {
            mensaje = "El número de cuenta del fideicomisario debe ser a 10 posiciones, según el tipo de movimiento especificado";
        }
        if (tipo_movimiento.equals("2")) {
            if (cuenta_banco.length() != 18) {
                mensaje = "La CLABE debe ser a 18 posiciones, según el tipo de movimiento especificado";
            } else {
                try {
                    int cuentaBanco = Integer.parseInt(cuenta_banco.substring(0, 3));
                    int claveBanco = Integer.parseInt(clave_banco);
                    if (claveBanco == cuentaBanco) {
                        if (!new Validaciones().validaCLABE(cuenta_banco)) {
                            mensaje = "El dígito verificador de la CLABE es incorrecto.";
                        }
                    } else {
                        mensaje = "La cuenta bancaria no corresponde al nombre de banco especificado.";
                    }
                } catch (Exception e) {
                    mensaje = "Error en el formato de cuenta bancaria.";
                }
            }
        }
        if (tipo_movimiento.equals("3") && cuenta_banco.length() != 16) {
            mensaje = "El número de tarjeta de débito debe ser a 16 posiciones, según el tipo de movimiento especificado";
        }
        if (tipo_movimiento.equals("4") && cuenta_banco != null) {
            mensaje = "El número de cuenta no corresponde al tipo de movimiento especificado";
        }
        return mensaje;
    }

    public static String truncarImporteLiquidacion(String importe_liquidacion) {
        String importe = "";
        int val = importe_liquidacion.indexOf(".");
        if (val > 0) {
            if ((importe_liquidacion.length() - val) >= 3) {
                importe = importe_liquidacion.substring(0, val) + importe_liquidacion.substring(val, val + 3);
            } else {
                importe = importe_liquidacion.substring(0, val) + importe_liquidacion.substring(val, val + 2) + "0";
            }
        } else {
            importe = importe + ".00";
        }
        return importe;
    }

    /**
     * Método para validar que la fecha en que tendrá lugar la liquidación sea
     * una fecha válida: Formato válido, fecha posterior o igual a la fecha
     * actual y que la fecha sea menor a un año a partir de la fecha actual.
     *
     * @param fecha_liquidacion: Fecha a validar.
     * @return String: Mensaje que se obtiene al realizar la validación
     * correspondiente, si todo va bien regresa una cadena vacio, en otro caso
     * regresa un mensaje descriptivo del error obtenido.
     */
    public static String validaFechaLiquidacion(String fecha_liquidacion) {
        clsFecha fecha = new clsFecha();
        String formato = "dd/MM/yyyy";
        String mensaje = "";
        try {
            if (fecha.fechaValida(fecha_liquidacion, "dd/MM/yyyy")) {
                //Verificamos la semántica de la fecha de liquidación
                String fechaHoy = fecha.getFechaHoy("dd/MM/yyyy");
                String fechaTmp = fechaHoy.substring(fechaHoy.length() - 4, fechaHoy.length());
                int anio = Integer.parseInt(fechaTmp);
                anio++;
                fechaTmp = fechaHoy.substring(0, fechaHoy.length() - 4) + anio;
                // 0 si son iguales, negativo si es menor y positivo si es mayor
                int a = fecha.ComparaFecha(fecha_liquidacion, formato, fechaHoy, formato);
                int b = fecha.ComparaFecha(fecha_liquidacion, formato, fechaTmp, formato);

                //La fecha no podrá ser anterior a la fecha de validación y no podrá ser posterior a un año.
                if (a >= 0 && b <= 0) {
                    mensaje = "";
                } else if (a < 0) {
                    mensaje = " La fecha en que tendrá lugar la liquidación debe ser posterior o igual a la fecha actual. ";
                } else {
                    mensaje = " La fecha en que tendrá lugar la liquidación no debe ser mayor a un año a partir de la fecha actual. ";
                }
            } else {
                mensaje = " El formato de la fecha de liquidación es inválido (Formato:DD/MM/YYYY ). ";
            }
        } catch (Exception e) {
            mensaje = " El formato de la fecha de liquidación es inválido (Formato:DD/MM/YYYY ). ";
            System.out.println("ModeloCapture-validaFechaLiquidacion" + e.getMessage());
        }
        return mensaje;
    }

    /**
     * Método para validar que la fecha de ingreso sea una fecha válida.
     *
     * @param fecha_ingreso: Fecha a validar.
     * @return String: Mensaje que se obtiene al realizar la validación
     * correspondiente, si todo va bien regresa una cadena vacio, en otro caso
     * regresa un mensaje descriptivo del error obtenido.
     */
    public static String validaFechaIngreso(String fecha_ingreso) {
        clsFecha fecha = new clsFecha();
        String formato = "dd/MM/yyyy";
        String mensaje = "";
        try {
            if (fecha.fechaValida(fecha_ingreso, "dd/MM/yyyy")) {
                //Verificamos la semántica de la fecha de liquidación
                String fechaHoy = fecha.getFechaHoy("dd/MM/yyyy");
                String fechaTmp = fechaHoy.substring(fechaHoy.length() - 4, fechaHoy.length());
                int anio = Integer.parseInt(fechaTmp);
                anio++;
                fechaTmp = fechaHoy.substring(0, fechaHoy.length() - 4) + anio;
                // 0 si son iguales, negativo si es menor y positivo si es mayor
                int a = fecha.ComparaFecha(fecha_ingreso, formato, fechaHoy, formato);

                //La fecha no podrá ser anterior a la fecha de validación y no podrá ser posterior a un año.
                if (a < 0) {
                    mensaje = "";
                } else {
                    mensaje = " La fecha de ingreso tiene que ser anterior a la fecha actual. ";
                }
            } else {
                mensaje = " El formato de la fecha de ingreso es inválido (Formato:DD/MM/YYYY ). ";
            }
        } catch (Exception e) {
            mensaje = " El formato de la fecha de ingreso es inválido (Formato:DD/MM/YYYY ). ";
            System.out.println("ModeloCapture-validaFechaIngreso" + e.getMessage());
        }
        return mensaje;
    }

    /**
     * Método para verificar que el nombre del fideicomisario no contiene
     * caracteres especiales.
     *
     * @param String nombre: Nombres,Aprellido_Paterno/Apellido_Materno
     * @return String nombreFidei: True si no contiene caracteres especiales
     * falso en otro caso.
     */
    public static String validaNombreFidei(String nombre) {
        String valido = "";
        nombre = nombre.toString().trim().toUpperCase();
        String especiales = "‡†ƒ£Ž¥¤¢Ññ*#$%@&=\"¨^:.;<>+-_ÁÉÍÓÚáéíóúÄËÏÖÜäâëêïîöôüûÂÀÈÊÌÎÒÔÙÛàèìòù~[]{}^`'()!¡°|¬¨´?¿", tmp = "";

        if (!nombre.isEmpty()) {
            for (int i = 0; i < nombre.length(); i++) {
                tmp = nombre.charAt(i) + "";
                if (especiales.contains(tmp)) {
                    valido = "No se aceptan caracteres especiales en el nombre del fideicomisario";
                    i = nombre.length();
                }
            }
        } else {
            valido = "El nombre del fideicomisario es obligatorio.";
        }

        return valido;
    }

    /**
     * Método validar la clave de banco y el tipo de moneda según el tipo de
     * movimiento especificado.
     *
     * @param tipo_movimiento: Tipo de movimiento.
     * @param clave_banco: Clave de banco.
     * @param clave_moneda: Tipo de moneda.
     * @return String: Mensaje con descripción de la transacción, si todo sale
     * bien regresa una cadena vacia.
     */
    public static String validaBancoMoneda(String tipo_movimiento, String clave_banco, String clave_moneda) {

        String valido = "";
        if (tipo_movimiento.equals("1")) {
            if (clave_banco.equals("12")) {
                if (clave_moneda.equals("MXP")) {
                    valido = "";
                } else {
                    valido = "El tipo de moneda NO corresponde al tipo de movimiento especificado";
                }
            } else {
                valido = "La clave de banco NO corresponde al tipo de movimiento especificado";
            }
        } else if (tipo_movimiento.equals("2")) {
            if (!clave_banco.equals("Seleccione")) {
                if (clave_moneda.equals("MXP")) {
                    valido = "";
                } else {
                    valido = "La clave de banco NO correspondiente al tipo de movimiento especificado";
                }
            } else {
                valido = "Clave de banco inválida según el tipo de movimiento especificado";
            }

        } else if (tipo_movimiento.equals("3")) {
            if (clave_banco.equals("2")) {
                if (clave_moneda.equals("MXP")) {
                    valido = "";
                } else {
                    valido = "El tipo de moneda NO corresponde al tipo de movimiento especificado";
                }
            } else {
                valido = "La clave de banco NO corresponde al tipo de movimiento especificado";
            }
        } else if (tipo_movimiento.equals("4")) {
            if (clave_banco.equals("0")) {
                if (clave_moneda.equals("MXP")) {
                    valido = "";
                } else {
                    valido = "El tipo de moneda NO corresponde al tipo de movimiento especificado";
                }
            } else {
                valido = "La clave de banco NO corresponde al tipo de movimiento especificado";
            }
        } else if (tipo_movimiento.equals("5")) {
            if (clave_banco.equals("0")) {
                if (!clave_moneda.equals("Seleccione")) {
                    valido = "";
                } else {
                    valido = "Seleccione el tipo de moneda correspondiente según el tipo de movimiento";
                }
            } else {
                valido = "La clave de banco NO corresponde al tipo de movimiento especificado";
            }
        } else {
            valido = "Tipo de movimiento no especificado.";
        }
        return valido;
    }

    /**
     * Método que valida el movimiento que se le pasa como parámetro.
     *
     * @param movimiento: Bean que define las caracteristicas de un movimiento.
     * @return String: Mensaje descriptivo del resultado de la validación.
     */
    public static String validaMovimiento(Movimiento movimiento) {
        int tmp = 0;
        String mensaje = "";
        String fecha_liquidacion = "", tipo_movimiento = "", clave_banco = "";
        String clave_moneda = "", nombre_fideicomisario = "", apellidoP_empleado = "", apellidoM_empleado = "";
        String fecha_ingreso = "", puesto_empleado = "", departamento_empleado = "", clave_empleado = "";
        String CURP = "", RFC = "", cuenta_deposito = "", importe_liquidacion = "", envio_cheque = "", destino_cheque = "";
        String telefono_envio_cheque = "", correo_envio_cheque = "", banco_extranjero = "", domicilio_banco_extranjero = "";
        String pais_banco_extranjero = "", ABA_BIC = "", nombre_en_banco_extranjero = "", direccion_en_banco_extranjero = "";
        String pais_en_banco_extranjero = "", telefono_en_banco_extranjero = "";

        try {
            fecha_liquidacion = movimiento.getFecha_liquidacion();
            if (!fecha_liquidacion.equals("")) {
                //La fecha de liquidación debe ser la misma para todos los movimientos capturados para un mismo lote.
                //----------ESTO NO FUNCIONA CUANDO VARIOS USUARIOS ESTAN ACTIVOS
//                if (!ModeloCapture.getFechaLiquidacion().equals("")) {
//                    if (!ModeloCapture.getFechaLiquidacion().equals(fecha_liquidacion)) {
//                        return mensaje = "La fecha en la que tendrá lugar la liquidación debe ser la misma para todos los movimientos de un mismo lote.";
//                    }
//                }
                //Validamos la fecha en la que tendrá lugar la liquidación
                //mensaje = ModeloCapture.validaFechaLiquidacion(fecha_liquidacion);
                if (mensaje.equals("")) {
                    tipo_movimiento = movimiento.getTipo_movimiento();
                    clave_banco = movimiento.getClave_banco();
                    clave_moneda = movimiento.getClave_moneda();
                    if (!tipo_movimiento.equals("Seleccione") && !clave_banco.equals("Seleccione") && !clave_moneda.equals("Seleccione")) {
                        //Obtenemos el tipo de movimiento
                        tmp = tipo_movimiento.indexOf(".");
                        tipo_movimiento = tipo_movimiento.substring(0, tmp);
                        //Obtenemos la clave del banco
                        //tmp = clave_banco.indexOf(".");
                        //clave_banco = clave_banco.substring(0, tmp);
                        clave_banco = getClaveBancoABM(clave_banco);
                        //Obtenemos la clave de moneda
                        tmp = clave_moneda.indexOf(".");
                        clave_moneda = clave_moneda.substring(0, tmp);
                        //Validamos el tipo de movimiento, clave de banco y clave de moneda.
                        mensaje = ModeloCapture.validaBancoMoneda(tipo_movimiento, clave_banco, clave_moneda);
                        if (mensaje.equals("")) {
                            movimiento.setTipo_movimiento(tipo_movimiento);
                            movimiento.setClave_banco(clave_banco);
                            movimiento.setClave_moneda(clave_moneda);
                            nombre_fideicomisario = movimiento.getNombre_empleado();
                            if (!nombre_fideicomisario.equals("")) {
                                //Validamos el nombre(s) del fideicomisario.
                                mensaje = ModeloCapture.validaNombreFidei(nombre_fideicomisario);
                                if (mensaje.equals("")) {
                                    apellidoP_empleado = movimiento.getApellidoP_empleado();
                                    if (!apellidoP_empleado.equals("")) {
                                        //Validamos el apellido paterno del fideicomisario.
                                        mensaje = ModeloCapture.validaNombreFidei(apellidoP_empleado);
                                        if (mensaje.equals("")) {
                                            apellidoM_empleado = movimiento.getApellidoM_empleado();
                                            if (!apellidoM_empleado.equals("")) {
                                                //Validamos el apellido materno del fideicomisario.
                                                mensaje = ModeloCapture.validaNombreFidei(apellidoM_empleado);
                                                if (mensaje.equals("")) {
                                                    fecha_ingreso = movimiento.getFecha_ingreso();
                                                    if (!fecha_ingreso.equals("")) {
                                                        //Validamos la fecha de ingreso del fideicomisario.
                                                        mensaje = ModeloCapture.validaFechaIngreso(fecha_ingreso);
                                                        if (mensaje.equals("")) {
                                                            puesto_empleado = movimiento.getPuesto_empleado();
                                                            departamento_empleado = movimiento.getDepartamento_empleado();
                                                            clave_empleado = movimiento.getNumero_empleado();
                                                            CURP = movimiento.getCURP();
                                                            RFC = movimiento.getRFC();
                                                            if (ModeloLayOut.validaCadenaSinNumeros(puesto_empleado)) {
                                                                if (ModeloLayOut.validaCadenaSinNumeros(departamento_empleado)) {
                                                                    if (!clave_empleado.equals("")) {
                                                                        String msgCURP = validaCURP(CURP);
                                                                        if (msgCURP.equals("CORRECTO")) {
                                                                            String msgRFC = validaRFC(RFC);
                                                                            if (msgRFC.equals("CORRECTO")) {
                                                                                cuenta_deposito = movimiento.getCuenta_deposito();
//                                                                            if (!cuenta_deposito.equals("")) {
                                                                                //mensaje = ModeloCapture.validaTipoCuenta(tipo_movimiento, cuenta_deposito);
                                                                                mensaje = ModeloCapture.validaTipoCuenta(tipo_movimiento, clave_banco, cuenta_deposito);
                                                                                if (mensaje.equals("")) {
                                                                                    importe_liquidacion = movimiento.getImporte_liquidacion();
                                                                                    if (!importe_liquidacion.equals("")) {
                                                                                        mensaje = ModeloLayOut.validaImporte(importe_liquidacion);
                                                                                        if (!mensaje.equals(" Se espera una cantidad de 2 decimales. ") && !mensaje.equals(" Se espera una cantidad mayor a 0. ")
                                                                                                && !mensaje.equals(" Formato del importe de liquidación inválido. ") && !mensaje.equals(" Importe de Liquidación obligatorio. ")) {
                                                                                            try {
                                                                                                Double.parseDouble(mensaje);
                                                                                            } catch (Exception ex) {
                                                                                                return "Error en el valor de importe de liquidación.";
                                                                                            }
                                                                                            movimiento.setImporte_liquidacion(mensaje);
                                                                                            mensaje = "";
                                                                                            if (tipo_movimiento.equals("1") || tipo_movimiento.equals("2") || tipo_movimiento.equals("3")) {
                                                                                                movimiento.setNombre_receptor_cheque("NA");
                                                                                                movimiento.setDomicilio_destino_cheque("NA");
                                                                                                movimiento.setTel_destino_cheque("NA");
                                                                                                movimiento.setCorreo_destino_cheque("NA");

                                                                                                movimiento.setNombre_banco_extranjero("NA");
                                                                                                movimiento.setDomicilio_banco_extranjero("NA");
                                                                                                movimiento.setPais_banco_extranjero("NA");
                                                                                                movimiento.setABA_BIC("NA");
                                                                                                movimiento.setNombre_empleado_banco_extranjero("NA");
                                                                                                movimiento.setDom_empleado_banco_extranjero("NA");
                                                                                                movimiento.setPais_empleado_banco_extranjero("NA");
                                                                                                movimiento.setTel_empleado_banco_extranjero("NA");
                                                                                            } else if (tipo_movimiento.equals("4")) {
                                                                                                envio_cheque = movimiento.getNombre_receptor_cheque();
                                                                                                destino_cheque = movimiento.getDomicilio_destino_cheque();
                                                                                                telefono_envio_cheque = movimiento.getTel_destino_cheque();
                                                                                                correo_envio_cheque = movimiento.getCorreo_destino_cheque();

                                                                                                if (!envio_cheque.equals("") && !destino_cheque.equals("") && !telefono_envio_cheque.equals("") && !correo_envio_cheque.equals("")) {

                                                                                                    if (!envio_cheque.equals("NA") && !destino_cheque.equals("NA") && !telefono_envio_cheque.equals("NA") && !correo_envio_cheque.equals("NA")) {
                                                                                                        movimiento.setNombre_banco_extranjero("NA");
                                                                                                        movimiento.setDomicilio_banco_extranjero("NA");
                                                                                                        movimiento.setPais_banco_extranjero("NA");
                                                                                                        movimiento.setABA_BIC("NA");
                                                                                                        movimiento.setNombre_empleado_banco_extranjero("NA");
                                                                                                        movimiento.setDom_empleado_banco_extranjero("NA");
                                                                                                        movimiento.setPais_empleado_banco_extranjero("NA");
                                                                                                        movimiento.setTel_empleado_banco_extranjero("NA");
                                                                                                    } else {
                                                                                                        mensaje = "Según el tipo de movimiento seleccionado se espera información diferente en los campos que definen el envío de un cheque.";
                                                                                                    }
                                                                                                } else {
                                                                                                    mensaje = "Capture la información correspondiente al envio de cheque";
                                                                                                }
                                                                                            } else if (tipo_movimiento.equals("5")) {
                                                                                                banco_extranjero = movimiento.getNombre_banco_extranjero();
                                                                                                domicilio_banco_extranjero = movimiento.getDomicilio_banco_extranjero();
                                                                                                pais_banco_extranjero = movimiento.getPais_banco_extranjero();
                                                                                                ABA_BIC = movimiento.getABA_BIC();
                                                                                                nombre_en_banco_extranjero = movimiento.getNombre_empleado_banco_extranjero();
                                                                                                direccion_en_banco_extranjero = movimiento.getDom_empleado_banco_extranjero();
                                                                                                pais_en_banco_extranjero = movimiento.getPais_empleado_banco_extranjero();
                                                                                                telefono_en_banco_extranjero = movimiento.getTel_empleado_banco_extranjero();

                                                                                                if (!banco_extranjero.equals("") && !domicilio_banco_extranjero.equals("") && !pais_banco_extranjero.equals("")
                                                                                                        && !ABA_BIC.equals("") && !nombre_en_banco_extranjero.equals("") && !direccion_en_banco_extranjero.equals("")
                                                                                                        && !pais_en_banco_extranjero.equals("") && !telefono_en_banco_extranjero.equals("")) {

                                                                                                    if (!banco_extranjero.equals("NA") && !domicilio_banco_extranjero.equals("NA") && !pais_banco_extranjero.equals("NA")
                                                                                                            && !ABA_BIC.equals("NA") && !nombre_en_banco_extranjero.equals("NA") && !direccion_en_banco_extranjero.equals("NA")
                                                                                                            && !pais_en_banco_extranjero.equals("NA") && !telefono_en_banco_extranjero.equals("NA")) {
                                                                                                        movimiento.setNombre_receptor_cheque("NA");
                                                                                                        movimiento.setDomicilio_destino_cheque("NA");
                                                                                                        movimiento.setTel_destino_cheque("NA");
                                                                                                        movimiento.setCorreo_destino_cheque("NA");
                                                                                                    } else {
                                                                                                        mensaje = "Según el tipo de movimiento seleccionado se espera información diferente en los campos que definen un movimiento a bancos extranjeros.";
                                                                                                    }
                                                                                                } else {
                                                                                                    mensaje = "Capture la información correspondiente a Bancos Extranjeros";
                                                                                                }
                                                                                            } else {
                                                                                                mensaje = "Tipo de movimiento indefinido, consulte a su administrador.";
                                                                                            }
                                                                                        } else {
                                                                                            return mensaje;
                                                                                        }
                                                                                    } else {
                                                                                        mensaje = " Capture el importe de liquidación sin formato y hasta dos decimales ";
                                                                                    }
                                                                                } else {
                                                                                    return mensaje;
                                                                                }
//                                                                            } else {
//                                                                                mensaje = " Capture el número de cuenta del fideicomisario, de acuerdo al tipo de movimiento especificado ";
//                                                                            }
                                                                            } else {
                                                                                mensaje = msgRFC;
                                                                            }
                                                                        } else {
                                                                            mensaje = msgCURP;
                                                                        }
                                                                    } else {
                                                                        mensaje = " Capture la clave o número de control de nómina del fideicomisario ";
                                                                    }
                                                                } else {
                                                                    mensaje = " El valor para el campo de departamento del empleado es inválido. ";
                                                                }
                                                            } else {
                                                                mensaje = " El valor para el campo de puesto del empleado es inválido.";
                                                            }
                                                        } else {
                                                            return mensaje;
                                                        }
                                                    } else {
                                                        mensaje = " Seleccione la fecha de ingreso del fideicomisario al empleo ";
                                                    }
                                                } else {
                                                    return mensaje;
                                                }
                                            }
//                                             else {
//                                                mensaje = " Capture el apellido materno del fideicomisario ";
//                                            }
                                        } else {
                                            return mensaje;
                                        }
                                    } else {
                                        mensaje = " Capture el apellido paterno del fideicomisario ";
                                    }
                                } else {
                                    return mensaje;
                                }
                            } else {
                                mensaje = " Capture el nombre(s) del fideicomisario ";
                            }
                        } else {
                            return mensaje;
                        }
                    } else {
                        mensaje = "Seleccione el tipo de movimiento y/o clave de banco y/o clave de moneda";
                    }
                } else {
                    return mensaje;
                }
            } else {
                mensaje = " Seleccione la fecha en la que tendrá lugar la liquidación ";
            }

        } catch (Exception e) {
            mensaje = "Error al realizar la validación del movimiento capturado " + e;
        }
        return mensaje;
    }

    public static ResumenMovimientos getResumenMovimientos(Vector movimientos) {
        ResumenMovimientos resumenMovs = new ResumenMovimientos();
        Movimiento movimiento = null;
        BigDecimal b1 = null;
        BigDecimal b2 = null;
        int tipo_movimiento = -1;
        String clave_contrato = "";
        String fecha_liquidacion = "";
        String tmp = "";
        try {
            if (!movimientos.isEmpty()) {
                for (int i = 0; i < movimientos.size(); i++) {
                    movimiento = (Movimiento) movimientos.get(i);
                    //Obtenemos el tipo de movimiento de la transacción.
                    tipo_movimiento = Integer.parseInt(movimiento.getTipo_movimiento());
                    //Obtenemos el importe total de la transacción.
                    tmp = movimiento.getImporte_liquidacion();

                    if (!tmp.equals("")) {
                        BigDecimal importe = new BigDecimal(tmp);
                        if (tipo_movimiento == 1) {
                            resumenMovs.sumMov1(importe);
                            resumenMovs.inc_mov_tipo1();
                        } else if (tipo_movimiento == 2) {
                            resumenMovs.sumMov2(importe);
                            resumenMovs.inc_mov_tipo2();
                        } else if (tipo_movimiento == 3) {
                            resumenMovs.sumMov3(importe);
                            resumenMovs.inc_mov_tipo3();
                        } else if (tipo_movimiento == 4) {
                            resumenMovs.sumMov4(importe);
                            resumenMovs.inc_mov_tipo4();
                        } else if (tipo_movimiento == 5) {
                            resumenMovs.sumMov5(importe);
                            resumenMovs.inc_mov_tipo5();
                        }
                    }
                }
                //Obtenemos la clave de fideicomiso.
                clave_contrato = movimiento.getClave_contrato();
                //Obtenemos la fecha de liquidación.
                fecha_liquidacion = movimiento.getFecha_liquidacion();
                //Establecemos la fecha de liquidación.
                resumenMovs.setFecha_liquidacion(fecha_liquidacion);
                //Establecemos el total de transacciones a realizar para este lote.
                resumenMovs.setTotal_movimientos(resumenMovs.get_total_movs());
                //Establecemos la suma del importe total en MXP.
                resumenMovs.setImporteTotalMXP(resumenMovs.getSumImporteTotalMXP());
                String importe_l = resumenMovs.getImporteTotalMXP() + "";
                resumenMovs.setFormato_importe_totalMXP(ModeloLayOut.formatoImporte(importe_l));
                //Establecemos el formatos de cada unos de los movimientos.
                tmp = resumenMovs.getPago_mov_tipo1() + "";
                resumenMovs.setFormato_importe_tipo1(ModeloLayOut.formatoImporte(tmp));

                tmp = resumenMovs.getPago_mov_tipo2() + "";
                resumenMovs.setFormato_importe_tipo2(ModeloLayOut.formatoImporte(tmp));

                tmp = resumenMovs.getPago_mov_tipo3() + "";
                resumenMovs.setFormato_importe_tipo3(ModeloLayOut.formatoImporte(tmp));

                tmp = resumenMovs.getPago_mov_tipo4() + "";
                resumenMovs.setFormato_importe_tipo4(ModeloLayOut.formatoImporte(tmp));

                tmp = resumenMovs.getPago_mov_tipo5() + "";
                resumenMovs.setFormato_importe_tipo5(ModeloLayOut.formatoImporte(tmp));
                String importeTipo5 = ModeloLayOut.formatoImporte(tmp);
                resumenMovs.setFormato_importe_tipo5(importeTipo5.substring(2, importeTipo5.length()));

                //Establecemos la Sufucuencia Patronal Requerida.
                String honSinIva = ModeloLayOut.getInfoContrato(clave_contrato);
                String sp_c = ModeloLayOut.getSuficienciaPatronal(importe_l, honSinIva, "16");
                resumenMovs.setSPR(new BigDecimal(sp_c));
                resumenMovs.setFormato_SPR(sp_c);

                //Establecemos los Honorarios Fiduciarios
                String hf_c = ModeloLayOut.getHonorariosFiduciarios(importe_l, sp_c, honSinIva);
                resumenMovs.setAFI(hf_c);
                resumenMovs.setFormato_AFI(ModeloLayOut.formatoImporte(hf_c));

                //Establecemos el IVA de los honorios fiducuarios.
                String xIva = ModeloLayOut.estableceIVA(hf_c);
                resumenMovs.setIVA(xIva);
                resumenMovs.setFormato_iva(ModeloLayOut.formatoImporte(xIva));

                //Obtenemos el importe del Honorario Fiduciario ( AFI+IVA).
                b1 = new BigDecimal(Double.parseDouble(hf_c));
                b2 = new BigDecimal(Double.parseDouble(xIva));
                b1 = b1.add(b2);
                //Redondeamos la cantidad obtenida.
                tmp = b1.setScale(2, BigDecimal.ROUND_HALF_UP) + "";
                //Establecemos finalmente el importe de los honorarios fiduciarios.
                resumenMovs.setHonorarios_fidu(tmp);
                resumenMovs.setFormato_honorarios_fidu(ModeloLayOut.formatoImporte(tmp));

                //Se realiza la consulta del saldo actual
                String infoSaldo = "", arrayInfoSaldos[] = null;
                double saldo_actual = 0.0, nuevoSaldo = 0.0, liquidaciones_pendientes = 0.0;
                infoSaldo = ModeloLayOut.getSaldo_LiqPend_Fid(clave_contrato);
                arrayInfoSaldos = infoSaldo.split("%");
                if (arrayInfoSaldos.length == 3) {
                    liquidaciones_pendientes = Double.parseDouble(arrayInfoSaldos[2]);
                    saldo_actual = Double.parseDouble(arrayInfoSaldos[0]);
                    nuevoSaldo = saldo_actual - liquidaciones_pendientes - Double.parseDouble(resumenMovs.getImporteTotalMXP().toString());
                    DecimalFormat formato = new DecimalFormat("$ #,##0.00");
                    resumenMovs.setSaldo_actual(formato.format(saldo_actual));
                    resumenMovs.setLiquidaciones_pendientes(formato.format(liquidaciones_pendientes));
                    resumenMovs.setNuevo_saldo(formato.format(Math.abs(nuevoSaldo)));
                    if (nuevoSaldo < 0) {
//                        System.out.println("Es insuficiente");
                        resumenMovs.setTxt_Nuevo_saldo("Insuficiencia patrimonial");
                        double insuficiencia_patrimonial = Math.abs(nuevoSaldo);
                        if (clave_contrato.equals("PRB16082011")) {
                            clave_contrato = "FID000PRB0514";
                        }
                        honSinIva = ModeloLayOut.getInfoContrato(clave_contrato);
                        sp_c = ModeloLayOut.getSuficienciaPatronal("" + insuficiencia_patrimonial, honSinIva, "16");
                        resumenMovs.setAportacion_minima_requerida(formato.format(Double.parseDouble(sp_c)));
                    } else {
//                        System.out.println("Hay remanente");
                        resumenMovs.setTxt_Nuevo_saldo("Patrimonio remanente");
                        resumenMovs.setAportacion_minima_requerida("$ 0.00");
                    }
                    Vector<Double> movsPendientesTipo5 = getMovsPendTipo5(clave_contrato);
                    if (movsPendientesTipo5 != null) {
                        if (movsPendientesTipo5.size() > 0) {
                            resumenMovs.setTotal_movs_tipo5_pend(movsPendientesTipo5.get(0).intValue());
                            importeTipo5 = formato.format(movsPendientesTipo5.get(1));
                            resumenMovs.setFormato_importe_tipo5_pend(importeTipo5.substring(2, importeTipo5.length()));
                        } else {
                            resumenMovs.setTotal_movs_tipo5_pend(0);
                            resumenMovs.setFormato_importe_tipo5_pend("0.00");
                        }
                    } else {
                        System.out.println("Error obteniendo movimientos pendientes Tipo 5");
                    }
                } else {
                    System.out.println("Error obteniendo el saldo del fieicomiso en ModeloLayOut");
                }

            }
        } catch (Exception e) {
            resumenMovs = null;
            System.out.println("ModeloCapture-getResumenMovimientos:" + e.getMessage());
        }
        return resumenMovs;
    }

    public synchronized boolean almacenaCargaManual(String clave_contrato, String fecha_liquidacion,
            Vector movimientos, Usuario userApp, String correo_origen, String correo_destino, String urlArchivo) {

        String usuario = "", cliente = "", registro = "", MySql = "", fecha = "", nombreArchivo = "";
        clsFecha fechaForm = new clsFecha();
        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        Movimiento movimiento = null;
        List datos_bitacora = null;
        ModeloCapture.clave_archivo = -1;
        ModeloCapture.nombre_archivo = "";
        boolean almacena = false;
        int idx_archivo = -1;
        try {
            idx_archivo = ModeloCapture.getIdArchivo(clave_contrato, fecha_liquidacion);
            if (idx_archivo > 0) {
                fecha = ModeloLayOut.getFormatoFecha(fecha_liquidacion);
                if (!fecha.equals("")) {
                    if (idx_archivo > 0 && idx_archivo <= 9) {
                        //Creamos la salida del archivo generado
                        nombreArchivo = clave_contrato.substring(6, 9) + "0" + idx_archivo + fecha;
                    } else {
                        nombreArchivo = clave_contrato.substring(6, 9) + idx_archivo + fecha;
                    }
                    ModeloCapture.setNombre_archivo(nombreArchivo);
                    ModeloCapture.setClave_archivo(idx_archivo);
                    connection = conn.ConectaSQLServer();
                    connection.setAutoCommit(false);
                    statement = connection.createStatement();

                    for (int i = 0; i < movimientos.size(); i++) {
                        movimiento = (Movimiento) movimientos.get(i);
                        registro = "'" + movimiento.getClave_contrato() + "' , '" + fechaForm.CambiaFormatoFecha("dd/MM/yyyy", "yyyy-MM-dd", movimiento.getFecha_liquidacion()) + " 00:00:00.000' , '" + movimiento.getTipo_movimiento() + "' , '";
                        registro += movimiento.getClave_banco() + "' , '" + movimiento.getClave_moneda() + "' , '" + movimiento.getNombre_empleado() + "' , '";
                        registro += movimiento.getApellidoP_empleado() + "' , '" + movimiento.getApellidoM_empleado() + "' , '" + movimiento.getCuenta_deposito() + "' , '";
                        registro += movimiento.getImporte_liquidacion() + "' , '" + "0" + "' , '" + movimiento.getNumero_empleado() + "' , '" + movimiento.getCURP() + "' , '" + movimiento.getRFC() + "' , '";
                        registro += fechaForm.CambiaFormatoFecha("dd/MM/yyyy", "yyyy-MM-dd", movimiento.getFecha_ingreso()) + " 00:00:00.000' , '" + movimiento.getPuesto_empleado() + "' , '" + movimiento.getDepartamento_empleado() + "' , '";
                        registro += movimiento.getNombre_receptor_cheque() + "' , '" + movimiento.getDomicilio_destino_cheque() + "' , '";
                        registro += movimiento.getTel_destino_cheque() + "' , '" + movimiento.getCorreo_destino_cheque() + "' , '";
                        registro += movimiento.getNombre_banco_extranjero() + "' , '" + movimiento.getDomicilio_banco_extranjero() + "' , '";
                        registro += movimiento.getPais_banco_extranjero() + "' , '" + movimiento.getABA_BIC() + "' , '";
                        registro += movimiento.getNombre_empleado_banco_extranjero() + "' , '" + movimiento.getDom_empleado_banco_extranjero() + "' , '";
                        registro += movimiento.getPais_empleado_banco_extranjero() + "' , '" + movimiento.getTel_empleado_banco_extranjero() + "' , ";
                        registro += "'" + nombreArchivo + "' ";
                        // System.out.println("Registro Manual:" + registro);
                        MySql = " insert into movimientos ";
                        MySql += " ( clave_contrato , fecha_liquidacion , tipo_movimiento , clave_banco , tipo_moneda ,nombre_empleado , "
                                + " apellidoP_empleado , apellidoM_empleado , cuenta_deposito , importe_liquidacion ,importe_liquidacion_mxp, clave_empleado , curp , rfc , "
                                + " fecha_ingreso , puesto_empleado , depto_empleado , envio_cheque , destino_envio_cheque , tel_envio_cheque , "
                                + " correo_envio_cheque , banco_extranjero , dom_banco_extranjero , pais_banco_extranjero , ABA_BIC , "
                                + " nombre_fidei_banco_ext , direccion_fidei_ext , pais_fidei_ext , tel_fidei_ext,nombre_archivo )";
                        MySql += " values  ";
                        MySql += " ( ";
                        MySql += " " + registro + " ";
                        MySql += " ) ";
//                     System.out.println("MySql:" + MySql);
                        // Se ejecuta el encabezado
                        statement.executeUpdate(MySql);
                    }
                    cliente = userApp.getId_cliente();
                    clave_contrato = movimiento.getClave_contrato();
                    fecha_liquidacion = fechaForm.CambiaFormatoFecha("dd/MM/yyyy", "yyyy-MM-dd", movimiento.getFecha_liquidacion());

                    usuario = userApp.getUsuario();
                    MySql = " insert into movimientos_h ";
                    MySql += " (clave_cliente , clave_contrato , fecha_liquidacion , usuario_cliente , nombre_archivo , fecha_captura , status, clave_archivo )";
                    MySql += " values ";
                    MySql += " ( ";
                    MySql += "'" + cliente + "',";
                    MySql += "'" + clave_contrato + "',";
                    MySql += "'" + fecha_liquidacion + " 00:00:00.000',";
                    MySql += "'" + usuario + "',";
                    MySql += "'" + nombreArchivo + "', ";
                    MySql += " getDate() ,";
                    MySql += "'A',";
                    MySql += "'" + idx_archivo + "' ";
                    MySql += " ) ";
//                     System.out.println("MySql2:"+ MySql);
                    // Se ejecuta el encabezado
                    statement.executeUpdate(MySql);
                    // System.out.println("Transaction commit...");
                    datos_bitacora = ModeloLayOut.getDatosBitacora();
                    String genera_bitacora = this.generaBitacora(datos_bitacora, correo_origen, correo_destino, "Bitácora", "", urlArchivo, "BITACORA.txt");
                    if (genera_bitacora.equals("")) {
                        connection.commit();
                        almacena = true;
                    } else {
                        connection.rollback();
                        almacena = false;
                    }
                } else {
                    almacena = false;
                }
            } else {
                almacena = false;
            }
        } catch (Exception e) {
            try {
                if (connection != null) {
                    conn.Desconecta(connection);
                }
                statement.close();
                System.out.println("ModeloCapture-almacenaCargaManual:" + e.toString());
            } catch (Exception ex) {
                System.out.println("ModeloCapture-almacenaCargaManual:" + ex.toString());
            }
            almacena = false;
        } finally {
            try {
                if (connection != null) {
                    conn.Desconecta(connection);
                }
                statement.close();
            } catch (Exception ex) {
                System.out.println("ModeloLayOut-guardaLayOut:" + ex.toString());
            }
        }
        return almacena;
    }

    /**
     * Método que genera una bitacora informativa de los fideicomitentes que
     * tiene movimientos pendientes por operar actualmente.
     *
     * @param datos_bitacora : Datos que deberá contener la bitácora.
     * @param correo_origen : Correo que envia la notificación.
     * @param correo_destino : Correo que recibe la notificación.
     * @param asunto_correo : Asunto de la notificación.
     * @param cuerpo_correo : Cuerpo del correo de la notificación.
     * @param url_bitacora : Ruta donde se encuentra la bitácora.
     * @param nombre_bitacora : Nombre de la bitácora.
     * @return Mensaje informativo de la transacción, si todo va bien regresa
     * una cadena vacia.
     */
    public synchronized String generaBitacora(List datos_bitacora, String correo_origen, String correo_destino,
            String asunto_correo, String cuerpo_correo, String url_bitacora, String nombre_bitacora) {
        File bitacora = null;
        PrintWriter pw = null;
        String reporta = "";
        boolean enviaBitacora = false;
        try {
            if (datos_bitacora != null && datos_bitacora.size() > 0) {
                bitacora = new File(url_bitacora + nombre_bitacora);
                bitacora.delete();
                pw = new PrintWriter(new FileOutputStream(bitacora));
                for (int i = 0; i < datos_bitacora.size(); i++) {
                    pw.println((String) datos_bitacora.get(i));
                }
                pw.close();
//                enviaBitacora = EnvioMail.enviaCorreo(correo_origen, correo_destino, asunto_correo, cuerpo_correo, url_bitacora, nombre_bitacora, "587");
//                if(!enviaBitacora){
//                enviaBitacora = EnvioMail.enviaCorreo(correo_origen, correo_destino, asunto_correo, cuerpo_correo, url_bitacora, nombre_bitacora,"587");
//                }
                enviaBitacora = true;
                if (!enviaBitacora) {
                    reporta = "Error enviando bitácora ";
                }
            } else {
                reporta = "Error obteniendo información de bitácora";
            }
        } catch (Exception e) {
            reporta = "Error generando bitácora";
            System.out.println(e.toString());
        }
        return reporta;
    }

    public static int getIdArchivo(String clave_contrato, String fecha_liquidacion) {
        String MySql = "";
        int id_archivo = 1;
        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        clsFecha fecha = new clsFecha();

        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql += " select count(clave_contrato)  ";
            MySql += " from movimientos_h ";
            MySql += " where clave_contrato = '" + clave_contrato + "' ";
            MySql += " and fecha_liquidacion ='" + fecha.CambiaFormatoFecha("dd/MM/yyyy", "yyyy-MM-dd", fecha_liquidacion) + " 00:00:00.00' ";

//            System.out.println(MySql);
            ResultSet rstSQLServer = statement.executeQuery(MySql);

            if (rstSQLServer.next()) {
                id_archivo = Integer.parseInt(rstSQLServer.getString(1));
                id_archivo++;
            }
            rstSQLServer.close();
            statement.close();
            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            id_archivo = -1;
            System.out.println("ModeloCapture-getIdArchivo:" + e.toString());
        }
        return id_archivo;
    }

    /**
     * Se obtendrán los movimientos pendientes para transacciones a bancos
     * extranjeros y el importe total de estos
     *
     * @param clave_contrato
     * @return Vector con 2 valores Double
     */
    public static Vector<Double> getMovsPendTipo5(String clave_contrato) {
        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;

        String MySql = "";
        Double count = 0d;
        Double importeLiquidacion = 0d;
        Vector<Double> resultado = null;

        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            if (clave_contrato.equals("PRB16082011")) {
                clave_contrato = "FID000PRB0514";
            }

            MySql = " select m.importe_liquidacion from movimientos m,movimientos_h mh ";
            MySql += " where m.clave_contrato = '" + clave_contrato + "' ";
            MySql += " and m.clave_contrato=mh.clave_contrato ";
            MySql += " and m.fecha_liquidacion=mh.fecha_liquidacion ";
            MySql += " and m.nombre_archivo = mh.nombre_archivo ";
            MySql += " and m.tipo_movimiento=5 and mh.status in ('A','P') ";

            ResultSet rstSQLServer = statement.executeQuery(MySql);

            while (rstSQLServer.next()) {
                importeLiquidacion += Double.parseDouble(rstSQLServer.getString("importe_liquidacion"));
                count++;
            }
            resultado = new Vector();
            if (count > 0) {
                resultado.add(count);
                resultado.add(importeLiquidacion);
            }

        } catch (Exception e) {
            resultado = null;
            if (count == 0) {
                resultado = null;
            }
            if (connection != null) {
                conn.Desconecta(connection);
            }
            System.out.println("ModeloLayOut-getMovsPendTipo5: " + resultado + "; " + e.getMessage());
        }
        return resultado;
    }

    public static String validaCURP(String campo) {

        String[] desErrores = null;
        String valido = "";

        try {
            if (!campo.isEmpty()) {
                campo = campo.toString().trim();
                campo = campo.toUpperCase();
                if (campo.length() == 18) {
                    //expresion regular para verificacion de CURP
//                    String patron = "[A-Z][A,E,I,O,U,X][A-Z]{2}[0-9]{2}[0-1][0-9][0-3][0-9]";
//                    patron += "[M,H][A-Z]{2}[B,C,D,F,G,H,J,K,L,M,N,Ñ,P,Q,R,S,T,V,W,X,Y,Z]{3}[0-9,A-Z][0-9]";
                    String patron = "[A-Z][AEIOUX][A-Z]{2}[0-9]{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])[MH]"
                            + "([ABCMTZ]S|[BCJMOT]C|[CNPST]L|[GNQ]T|[GQS]R|C[MH]|[MY]N|[DH]G|NE|VZ|DF|SP)"
                            + "[BCDFGHJ-NP-TV-Z]{3}[0-9A-Z][0-9]";
                    Pattern p = Pattern.compile(patron);
                    Matcher m = p.matcher(campo);

                    if (m.matches()) {
                        if (campo.equals(m.group())) {
                            valido = "CORRECTO";
                        }
                    }
                } else {
                    valido = "La longitud de la CURP debe de ser de 18 dígitos.";
                }
            } else {
                valido = "La CURP es un dato requerido, favor de ingresarla.";
            }
        } catch (Exception exc) {
            valido = "La nomenclatura de CURP es incorrecta.";
        }

        return valido;
    }

    public static String validaRFC(String campo) {

        String[] desErrores = null;
        String valido = "";

        try {
            if (!campo.isEmpty()) {
                campo = campo.toString().trim();
                campo = campo.toUpperCase();
                if (campo.length() == 13) {
                    //expresion regular para verificacion de RFC
                    String patron = "[A-Z,Ñ,&]{4}[0-9]{2}[0-1][0-9][0-3][0-9][A-Z,0-9]{3}";
                    Pattern p = Pattern.compile(patron);
                    Matcher m = p.matcher(campo);

                    if (m.matches()) {
                        if (campo.equals(m.group())) {
                            valido = "CORRECTO";
                        }
                    }
                } else {
                    valido = "La longitud del RFC debe de ser de 13 dígitos.";
                }
            } else {
                valido = "El RFC es un dato requerido, favor de ingresarlo.";
            }
        } catch (Exception exc) {
            valido = "La nomenclatura de RFC es incorrecta.";
        }

        return valido;
    }

}
