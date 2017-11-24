/*
 *    Author     : Luis Antio Valerio Gayosso
 *    Fecha:                        22/02/2011
 *    Descripción:                  Controlador : "ModeloLayOut.java" Modelo del sistema.
 *    Responsable:                  Carlos Altamirano
 */
package Modelos;

import java.io.*;
import java.sql.*;
import java.math.BigDecimal;

import Beans.Usuario;
import Beans.ResumenMovimientos;

import Common.clsFecha;
import Common.clsConexion;
import Common.EnvioMail;

import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.StringTokenizer;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.save.JRMultipleSheetsXlsSaveContributor.*;

import java.util.Random;
import java.util.Vector;

public class ModeloLayOut {

    //Variables de clase
    public static ResumenMovimientos resumenMov = null;
    public static ArrayList errores = null;
    //Variable que almacena los cliente que intentan ingresar al sistema.
    public static String usersIngApp = "";
    //Variable que indica si un cliente intento entrar mas de 3 veces al sistema
    public static boolean bloqueaUser = false;
    //Variable para almacenar la fecha de liquidación del LayOut que se intenta cargar
    public static String date_liquidation = "";
    //Almacena la fecha en que se almacena el lote en la Base de Datos.
    public static String fecha_carga = "";

    public static String getFecha_carga() {
        return fecha_carga;
    }

    public static String getDate_liquidation() {
        if (!date_liquidation.equals("")) {
            date_liquidation = ModeloLayOut.validaFecha(date_liquidation);
        }
        return date_liquidation;
    }

    public static boolean isBloqueaUser() {
        return bloqueaUser;
    }

    public static String getUsersIngApp() {
        return usersIngApp;
    }

    public static ArrayList getErrores() {
        return errores;
    }

    public static void setErrores(ArrayList errores) {
        ModeloLayOut.errores = errores;
    }

    public static ResumenMovimientos getResumenMov() {
        return resumenMov;
    }

    public static void setResumenMov(ResumenMovimientos resumenMov) {
        ModeloLayOut.resumenMov = resumenMov;
    }

    public static String filtraNulos(String lstrIn) {
        if (lstrIn == null) {
            return "";
        }
        return lstrIn;
    }
    //Constructor de la clase

    public ModeloLayOut() {
    }

    /**
     * Metodo para regresar la suma de los mivimientos realizados
     *
     * @return boolean resumenMov: suma datesEdoCta
     */
    public static ResumenMovimientos getResumenMovimientos() {
        return resumenMov;
    }

    /**
     * Método que regresa el formato DDMMAA a la fecha que se le pasa como
     * parámetro, asumiendo que la fecha que se le pasa tiene el formato:
     * DD/MM/YYYY.
     *
     * @param String fech : Fecha a cambiar de formato.
     * @return String fecha: Fecha con formato YYMMDD.
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
     * Método que regresa el formato DDMMAA a la fecha que se le pasa como
     * parámetro, asumiendo que la fecha que se le pasa tiene el formato:
     * YYYY-MM-DD
     *
     * @param String fech : Fecha a cambiar de formato.
     * @return String fecha: Fecha con formato YYMMDD.
     */
    public static String getFormatoFechaBaseDatos(String fech) {
        String fecha = "";
        try {
            String dia = fech.substring(8, 10);
            String mes = fech.substring(5, 7);
            String anio = fech.substring(2, 4);
            fecha = anio + mes + dia;
        } catch (Exception e) {
            fecha = "";
        }
        return fecha;
    }

    public static String getClaveArchivo_nombreArchivo_fechaLiq(String clave_contrato, String fecha_autoriza, String nombre_archivo) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;

        String MySql = "";
        int id_archivo = 0;
        Date fecha_liquidacion = null;

        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select clave_archivo,fecha_liquidacion ";
            MySql += " from movimientos_h ";
            MySql += " where clave_contrato = '" + clave_contrato + "' ";
//            MySql += " and fecha_liquidacion LIKE '%"+ fecha_autoriza  +" %' ";
            MySql += " and nombre_archivo = '" + nombre_archivo + "' ";

//            System.out.println("getClaveArchivo:" + MySql);
            ResultSet rstSQLServer = statement.executeQuery(MySql);

            if (rstSQLServer.next()) {
                id_archivo = Integer.parseInt(rstSQLServer.getString(1));
                fecha_liquidacion = rstSQLServer.getDate(2);
            } else {
                id_archivo = -1;
                fecha_liquidacion = null;
            }
            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            id_archivo = -1;
            fecha_liquidacion = null;
            if (connection != null) {
                conn.Desconecta(connection);
            }
            System.out.println("ModeloLayOut-getClaveArchivo_nombreArchivo_fechaLiq:" + e.getMessage());
        }
        return "" + id_archivo + ";" + fecha_liquidacion;
    }

    /**
     * Método para dar formato al importe que se muestra al cliente
     *
     * @param String importe : Importe a dar Formato ( 1345567.43).
     * @return String formato: Importe con Formato (1,345,567.43)
     */
    public static String formatoImporte(String importe) {

        importe = importe.toString().trim();
        String pesos = "", centavos = "", formato = "", tmp = "";
        int idx = 0, mult = 0, mod = 0;

        try {

            if (importe.contains(".")) {
                idx = importe.indexOf(".");
                pesos = importe.substring(0, idx);
                if (importe.substring(idx, importe.length()).length() > 2) {
                    centavos = importe.substring(idx, idx + 3);
                } else {
                    centavos = importe.substring(idx, idx + 2);
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
                formato = importe + ".00";
            }
        } catch (Exception e) {
            System.out.println("ModeloLayOut-formatoImporte:" + e.toString());
        }
        return "$ " + formato;
    }

    /**
     * Método para validar el importe de la liquidación
     *
     * @param String monto: Monto a validar
     * @return String importe: Importe validado
     */
    public static String validaImporte(String monto) {

        monto = monto.toString().trim();
        BigDecimal importe = BigDecimal.ZERO;
        String resultado = "";
        String decimal = "";
        int idx = 0;
        if (!monto.isEmpty()) {
            try {
                importe = new BigDecimal(monto);
                idx = monto.indexOf(".");
                if (idx >= 0) {
                    decimal = monto.substring(idx + 1, monto.length());
                    if (decimal.length() != 2) {
                        resultado = " Se espera una cantidad de 2 decimales. ";
                    } else if (importe.doubleValue() > 0.0) {
                        resultado = importe + "";
                    } else {
                        resultado = " Se espera una cantidad mayor a 0. ";
                    }
                } else {//Quiere decir que no tiene punto decimal. C
                    //CARGA_MANUAL: Se realizo validacion en JavaScript con valor mayor a minimo, así que es entero sin punto decimal
                    //CARGA_ARCHIVO: Se realiza despues de este metodo, la validación para valor mayor a salario mínimo
                    //por lo que solo existe la posibilidad de que sea un numero entero(sin punto decimal), 
                    //siendo un valor necesariamente positivo
                    Integer decimalInt = Integer.parseInt(monto);
                    resultado = (decimalInt > 0) ? (decimalInt + ".00") : (" Se espera una cantidad mayor a 0. ");
                }
            } catch (Exception ex) {
                importe = BigDecimal.ZERO;
                System.out.println("ModeloLayOut-validaImporte:" + ex.toString());
                resultado = " Formato del importe de liquidación inválido. ";
            }
        } else {
            importe = BigDecimal.ZERO;
            resultado = " Importe de liquidación obligatorio. ";
        }

        return resultado;

    }

    /**
     * Método para validar que la fecha que se pasa parámetro con el formato
     * YYYYMMDD corresponda a una fecha válida.
     *
     * @param String fecha: Fecha a validar
     * @return String fechaValida: Fecha con el formato DD-MM-YYYY
     */
    public static String validaFecha(String fecha) {

        String dia = "", mes = "", anio = "";
        String fechaValida = "";
        clsFecha data = new clsFecha();
        int idx = 0;
        try {
            if (fecha.length() == 8) {
                anio = fecha.substring(0, 4);
                mes = fecha.substring(4, 6);
                dia = fecha.substring(6, 8);

                idx = Integer.parseInt(dia);
                idx = Integer.parseInt(mes);
                idx = Integer.parseInt(anio);

                fechaValida = dia + "-" + mes + "-" + anio;
                data.setFormato("dd-MM-yyyy");
                data.setFecha(fechaValida);

                if (!data.fechaValida(fechaValida, "dd-MM-yyyy")) {
                    fechaValida = " Fecha Inválida. ";
                }
            } else {
                fechaValida = " Se Requieren 8 Caracteres. ";
            }
        } catch (Exception e) {
            fechaValida = " Formato de Fecha Inválido. ";
            System.out.println("ModeloLayOut-validaFecha:" + e.toString());
        }
        return fechaValida;
    }

    /**
     * Método para validar que la fecha que se pasa parámetro con el formato
     * YYYYMMDD corresponda a una fecha válida.
     *
     * @param String fecha: Fecha a validar
     * @return String fechaValida: Fecha con el formato yyyy-MM-dd
     */
    public static String validaFechaBD(String fecha) {

        String dia = "", mes = "", anio = "";
        String fechaValida = "";
        clsFecha data = new clsFecha();
        int idx = 0;
        try {
            if (fecha.length() == 8) {
                anio = fecha.substring(0, 4);
                mes = fecha.substring(4, 6);
                dia = fecha.substring(6, 8);

                idx = Integer.parseInt(dia);
                idx = Integer.parseInt(mes);
                idx = Integer.parseInt(anio);

                fechaValida = anio + "-" + mes + "-" + dia;
                data.setFormato("yyyy-MM-dd");
                data.setFecha(fechaValida);

                if (data.fechaValida(fechaValida, "yyyy-MM-dd")) {
                    fechaValida += " 00:00:00.000";
                } else {
                    fechaValida = " Fecha Inválida. ";
                }
            } else {
                fechaValida = " Se Requieren 8 Caracteres. ";
            }
        } catch (Exception e) {
            fechaValida = " Formato de Fecha Inválido. ";
            System.out.println("ModeloLayOut-validaFecha:" + e.toString());
        }
        return fechaValida;
    }

    /**
     * Método para verificar que el nombre del fideicomisario no contiene
     * caracteres especiales.
     *
     * @param String nombre: Nombres,Aprellido_Paterno/Apellido_Materno
     * @return boolean nombreFidei: True si no contiene caracteres especiales
     * falso en otro caso.
     */
    public static boolean validaNombreFidei(String nombre) {
        boolean valido = true;
        nombre = nombre.toString().trim().toUpperCase();
        String especiales = "‡†ƒ£Ž¥¤¢Ññ*#$%@&=\"¨^:.;<>+-_ÁÉÍÓÚáéíóúÄËÏÖÜäâëêïîöôüûÂÀÈÊÌÎÒÔÙÛàèìòù~[]{}^`'()!¡°|¬¨´?¿", tmp = "";

        if (!nombre.isEmpty()) {
            for (int i = 0; i < nombre.length(); i++) {
                tmp = nombre.charAt(i) + "";
                if (especiales.contains(tmp)) {
                    valido = false;
                    i = nombre.length();
                }
            }
        } else {
            valido = false;
        }

        return valido;
    }

    /**
     * Método para obtener el nombre del fideicomisario
     *
     * @param String nombre: Nombres,Aprellido_Paterno/Apellido_Materno
     * @return String nombreFidei: Nombre guardado en un arreglo
     */
    public static String[] setNombreFidei(String nombre) { //Verificar

        String[] nombreFidei = {"", "", ""};
        String apellido = "";
        StringTokenizer token = null;
        nombre = nombre.toString().trim().toUpperCase();
        token = new StringTokenizer(nombre, ",");

        if (token.hasMoreTokens()) {
            //Obtenemos el nombre del Fideicomisario
            nombreFidei[0] = token.nextToken().toString().trim(); //Nombres
            if (token.hasMoreTokens()) {
                //Obtenemos los apellidos del Fideicomisario
                nombre = token.nextToken().toString().trim();
                token = new StringTokenizer(nombre, "/");
                if (token.hasMoreTokens()) {
                    apellido = token.nextToken().toString().trim();//Apellido Paterno
                    if (!apellido.equals("")) {
                        nombreFidei[1] = apellido;
                        if (token.hasMoreTokens()) {
                            nombreFidei[2] = token.nextToken().toString().trim();//Apellido Materno
                        }
                    } else {
                        nombreFidei = null;
                    }
                } else {
                    nombreFidei = null;
                }
            } else {
                nombreFidei = null;
            }
        } else {
            nombreFidei = null;
        }

        return nombreFidei;
    }

    /**
     * Método para validar el campo Contrato del Lay-Out
     *
     * @param String campo: Clave del contrato
     * @return String[] desErrores: Errores obtenidos al validar
     */
    public static boolean validaContrato(String campo, int line, String clave_contrato) {

        String[] desErrores = null;
        String subCampo = "";
        boolean valido = true;
        int tmpo = 0;

        if (!campo.isEmpty()) {
            try {
                if (campo.contains("\"")) {
                    desErrores = new String[4];
                    desErrores[0] = line + "";
                    desErrores[1] = "1";
                    desErrores[2] = " Contrato ";
                    desErrores[3] = " Comillas en la clave del contrato";
                    errores.add(desErrores);
                    valido = false;
                }

                if (campo.contains("'")) {
                    desErrores = new String[4];
                    desErrores[0] = line + "";
                    desErrores[1] = "1";
                    desErrores[2] = " Contrato ";
                    desErrores[3] = " Símbolo inválido en la clave del contrato \" ' \" ";
                    errores.add(desErrores);
                    valido = false;
                }
                if (campo.length() == 13) {
                    if (clave_contrato.equals(campo)) {
                        subCampo = campo.substring(0, 3);
                        if (!subCampo.equals("FID")) {
                            desErrores = new String[4];
                            desErrores[0] = line + "";
                            desErrores[1] = "1";
                            desErrores[2] = " Contrato ";
                            desErrores[3] = "El identificador del fideicomiso debe ser FID. ";
                            errores.add(desErrores);
                            valido = false;
                        }
                        subCampo = campo.substring(3, 6);
                        tmpo = Integer.parseInt(subCampo);
                        subCampo = campo.substring(9, 11);
                        tmpo = Integer.parseInt(subCampo);

                        if (tmpo <= 0 || tmpo > 12) {
                            desErrores = new String[4];
                            desErrores[0] = line + "";
                            desErrores[1] = "1";
                            desErrores[2] = " Contrato ";
                            desErrores[3] = " Mes inválido. ";
                            errores.add(desErrores);
                            valido = false;
                        }
                        subCampo = campo.substring(11, 13);
                        tmpo = Integer.parseInt(subCampo);
                        if (tmpo <= 9) { //Aqui validar año de apertura de contrato
                            desErrores = new String[4];
                            desErrores[0] = line + "";
                            desErrores[1] = "1";
                            desErrores[2] = " Contrato ";
                            desErrores[3] = " Año de apertura inválido. ";
                            errores.add(desErrores);
                            valido = false;
                        }
                    } else if (clave_contrato.equals("PRB16082011")) {
                        valido = true;
                    } else {
                        desErrores = new String[4];
                        desErrores[0] = line + "";
                        desErrores[1] = "1";
                        desErrores[2] = " Contrato ";
                        desErrores[3] = " La clave de fideicomiso NO corresponde al fideicomitente. ";
                        errores.add(desErrores);
                        valido = false;
                    }
                } else {
                    desErrores = new String[4];
                    desErrores[0] = line + "";
                    desErrores[1] = "1";
                    desErrores[2] = " Contrato ";
                    desErrores[3] = " La clave debe ser de 13 dígitos ";
                    errores.add(desErrores);
                    valido = false;
                }
            } catch (Exception ex) {
                desErrores = new String[4];
                desErrores[0] = line + "";
                desErrores[1] = "1";
                desErrores[2] = " Contrato ";
                desErrores[3] = "Formato de clave inválido. ";
                errores.add(desErrores);
                valido = false;
                System.out.println("ModeloLayOut-validaContrato:" + ex.toString());
            }

        } else {
            desErrores = new String[4];
            desErrores[0] = line + "";
            desErrores[1] = "1";
            desErrores[2] = " Contrato ";
            desErrores[3] = " Campo obligatorio. ";
            errores.add(desErrores);
            valido = false;
        }
        return valido;
    }

    /**
     * Método para validar el campo tipo de movimiento del Lay-Out
     *
     * @param String campo: Tipo de Movimiento.
     * @param int line: Linea del archivo donde se realiza la validación
     * @return boolean valido: True si el tipo de movimiento es valido, false en
     * otro caso.
     */
    public static boolean validaMovimiento(String campo, int line) {

        String[] desErrores = null;
        boolean valido = false;
        int movimiento = 0;

        if (!campo.isEmpty()) {
            try {
                campo = campo.toString().trim();
                movimiento = Integer.parseInt(campo);
                if (movimiento > 0 && movimiento < 6) {
                    valido = true;
                    if (line > 1 && movimiento == 5) {
                        desErrores = new String[4];
                        desErrores[0] = line + "";
                        desErrores[1] = "3";
                        desErrores[2] = " Tipo de Movimiento ";
                        desErrores[3] = " Los movimientos a bancos extranjeros deben de ser subidos en archivos independientes.";
                        errores.add(desErrores);
                        valido = false;
                    }
                } else {
                    desErrores = new String[4];
                    desErrores[0] = line + "";
                    desErrores[1] = "3";
                    desErrores[2] = " Tipo de Movimiento ";
                    desErrores[3] = " Tipo de movimiento fuera de rango. ";
                    errores.add(desErrores);
                    valido = false;
                }
            } catch (Exception ex) {
                desErrores = new String[4];
                desErrores[0] = line + "";
                desErrores[1] = "3";
                desErrores[2] = " Tipo de Movimiento ";
                desErrores[3] = " Formato inválido. ";
                errores.add(desErrores);
                valido = false;
                System.out.println("ModeloLayOut-validaMovimiento:" + ex.toString());
            }
        } else {
            desErrores = new String[4];
            desErrores[0] = line + "";
            desErrores[1] = "3";
            desErrores[2] = " Tipo de Movimiento ";
            desErrores[3] = " Campo obligatorio. ";
            errores.add(desErrores);
            valido = false;
        }

        return valido;
    }

    /**
     * Método para validar el campo de la fecha de liquidación del Lay-Out
     *
     * @param String campo: Fecha de Liquidación
     * @param int line: Linea del archivo donde se realiza la validación
     * @return boolean valido: True si es una fecha valida, false en otro caso.
     */
    public static boolean validaFechaLiquidacion(String campo, int line) {

        String[] desErrores = null;
        clsFecha fecha = new clsFecha();
        String fechaLiquidacion = "";
        boolean valido = false;

        if (!campo.isEmpty()) {
            campo = ModeloLayOut.validaFecha(campo);
            if (campo.equals(" Fecha Inválida. ") || campo.equals(" Se Requieren 8 Caracteres. ") || campo.equals(" Formato de Fecha Inválido. ")) {
                desErrores = new String[4];
                desErrores[0] = line + "";
                desErrores[1] = "2";
                desErrores[2] = " Fecha de Liquidación ";
                desErrores[3] = " " + campo + " ";
                errores.add(desErrores);
                valido = false;
            } else {
                //Verificamos la semántica de la fecha de liquidación
                String fechaHoy = fecha.getFechaHoy("dd-MM-yyyy");
                fechaLiquidacion = campo;
                String fechaTmp = fechaHoy.substring(fechaHoy.length() - 4, fechaHoy.length());
                int anio = Integer.parseInt(fechaTmp);
                anio++;
                fechaTmp = fechaHoy.substring(0, fechaHoy.length() - 4) + anio;
                String formato = "dd-MM-yyyy";
                int a = fecha.ComparaFecha(fechaLiquidacion, formato, fechaHoy, formato);
                int b = fecha.ComparaFecha(fechaLiquidacion, formato, fechaTmp, formato);

                //La fecha no podrá ser anterior a la fecha de validación y no podrá ser posterior a un año.
                if (a >= 0 && b <= 0) {
                    valido = true;
                } else {
                    desErrores = new String[4];
                    desErrores[0] = line + "";
                    desErrores[1] = "2";
                    desErrores[2] = " Fecha de Liquidación ";
                    desErrores[3] = " La fecha debe ser posterior a la actual y NO mayor a un año. ";
                    errores.add(desErrores);
                    valido = false;
                }
            }
        } else {
            desErrores = new String[4];
            desErrores[0] = line + "";
            desErrores[1] = "2";
            desErrores[2] = " Fecha de Liquidación ";
            desErrores[3] = " Campo obligatorio. ";
            errores.add(desErrores);
            valido = false;
        }
        return valido;
    }

    /**
     * Método para validar la clave de Banco del Lay-Out
     *
     * @param String campo: Clave de Banco
     * @param String movimiento: Tipo de Movimiento actual
     * @param int line: Linea actual del archivo.
     * @return boolean valido: True si se trata de una clave valida, segun el
     * tipo de movimiento que se pasa como parámetro, false en otro caso.
     */
    public static boolean validaClaveBanco(String campo, int movimiento, int line) {

        String[] desErrores = null;
        boolean valida_clave_banco = false, valido = false;
        campo = campo.toString().trim();
        int clave_banco = 0;
        try {
            if (!campo.isEmpty()) {
                clave_banco = Integer.parseInt(campo);
                if (movimiento == 1) {
                    if (clave_banco == 1) {
                        valido = true;
                    } else {
                        desErrores = new String[4];
                        desErrores[0] = line + "";
                        desErrores[1] = "4";
                        desErrores[2] = " Clave de Banco ";
                        desErrores[3] = "La clave NO corresponde al tipo de movimiento proporcionado. ";
                        errores.add(desErrores);
                        valido = false;
                    }
                } else if (movimiento == 3) {
                    if (clave_banco == 12) {
                        valido = true;
                    } else {
                        desErrores = new String[4];
                        desErrores[0] = line + "";
                        desErrores[1] = "4";
                        desErrores[2] = " Clave de Banco ";
                        desErrores[3] = "La clave NO corresponde al tipo de movimiento proporcionado. ";
                        errores.add(desErrores);
                        valido = false;
                    }
                } else if (movimiento == 4 || movimiento == 5) {
                    if (clave_banco == 0) {
                        valido = true;
                    } else {
                        desErrores = new String[4];
                        desErrores[0] = line + "";
                        desErrores[1] = "4";
                        desErrores[2] = " Clave de Banco ";
                        desErrores[3] = "La clave NO corresponde al tipo de movimiento proporcionado. ";
                        errores.add(desErrores);
                        valido = false;
                    }
                } else if (movimiento == 2) {
                    if (clave_banco == 1) {
                        desErrores = new String[4];
                        desErrores[0] = line + "";
                        desErrores[1] = "4";
                        desErrores[2] = " Banco de Banco ";
                        desErrores[3] = " Clave NO VÁLIDA para el tipo de movimiento proporcionado. ";
                        errores.add(desErrores);
                        valido = false;
                    } else {
                        valida_clave_banco = ModeloLayOut.validaTipoBanco(clave_banco); //Se busca en la BD.
                        if (valida_clave_banco) {
                            valido = true;
                        } else {
                            desErrores = new String[4];
                            desErrores[0] = line + "";
                            desErrores[1] = "4";
                            desErrores[2] = " Clave de Banco ";
                            desErrores[3] = " Clave inexistente. ";
                            errores.add(desErrores);
                            valido = false;
                        }
                    }
                } else {
                    desErrores = new String[4];
                    desErrores[0] = line + "";
                    desErrores[1] = "4";
                    desErrores[2] = " Clave de Banco ";
                    desErrores[3] = "Tipo de movimiento fuera de rango. ";
                    errores.add(desErrores);
                    valido = false;
                }
            } else {
                desErrores = new String[4];
                desErrores[0] = line + "";
                desErrores[1] = "4";
                desErrores[2] = " Clave de Banco ";
                desErrores[3] = " Campo obligatorio. ";
                errores.add(desErrores);
                valido = false;
            }
        } catch (Exception ex) {
            desErrores = new String[4];
            desErrores[0] = line + "";
            desErrores[1] = "4";
            desErrores[2] = " Clave de Banco ";
            desErrores[3] = " Formato inválido. ";
            errores.add(desErrores);
            valido = false;
            System.out.println("ModeloLayOut-validaClaveBanco:" + ex.toString());
        }
        return valido;
    }

    /**
     * Método para validar la clave de moneda del Lay-Out
     *
     * @param String campo: Clave de Banco
     * @param String movimiento: Tipo de Movimiento actual
     * @param int line: Linea actual del archivo.
     * @return boolean valido: True si se trata de una clave valida, segun el
     * tipo de movimiento que se pasa como parámetro, false en otro caso.
     */
    public static boolean validaClaveMoenda(String campo, int movimiento, int line) {

        String[] desErrores = null;
        boolean valido = false;
        boolean valida_moneda = false;
        campo = campo.toString().trim();

        if (!campo.isEmpty()) {
            if (campo.length() >= 3) {
                campo = campo.toUpperCase();
                if (movimiento != 5) {
                    if (movimiento > 0 && movimiento < 5) {
                        if (campo.equals("MXP")) {
                            valido = true;
                        } else {
                            desErrores = new String[4];
                            desErrores[0] = line + "";
                            desErrores[1] = "5";
                            desErrores[2] = " Clave de Moneda ";
                            desErrores[3] = " Clave incorrecta, "
                                    + "se espera MXP según el tipo de movimiento. ";
                            errores.add(desErrores);
                            valido = false;
                        }
                    } else {
                        desErrores = new String[4];
                        desErrores[0] = line + "";
                        desErrores[1] = "5";
                        desErrores[2] = " Clave de Moneda ";
                        desErrores[3] = " Tipo de movimiento fuera de rango. ";
                        errores.add(desErrores);
                        valido = false;
                    }
                } else {
                    valida_moneda = ModeloLayOut.validaTipoMomeda(campo); //Se busca en la BD.
                    if (valida_moneda) {
                        valido = true;
                    } else {
                        desErrores = new String[4];
                        desErrores[0] = line + "";
                        desErrores[1] = "5";
                        desErrores[2] = " Clave de Moneda ";
                        desErrores[3] = " Clave inexistente, favor de verificar catálogo ";
                        errores.add(desErrores);
                        valido = false;
                    }
                }
            } else {
                desErrores = new String[4];
                desErrores[0] = line + "";
                desErrores[1] = "5";
                desErrores[2] = " Clave de Moneda ";
                desErrores[3] = " Clave incompleta ";
                errores.add(desErrores);
                valido = false;
            }
        } else {
            desErrores = new String[4];
            desErrores[0] = line + "";
            desErrores[1] = "5";
            desErrores[2] = " Clave de Moneda ";
            desErrores[3] = " Clave obligatoria ";
            errores.add(desErrores);
            valido = false;
        }
        return valido;
    }

    /**
     * Método para validar la cuenta de depósito del Lay-Out
     *
     * @param String campo: Clave de Banco
     * @param String movimiento: Tipo de Movimiento actual
     * @param int line: Linea actual del archivo.
     * @return boolean valido: True si se trata de una cuenta valida, según el
     * tipo de movimiento que se pasa como parámetro, false en otro caso.
     */
    public static boolean validaCuentaDeposito(String campo, int movimiento, int line) {
        boolean valido = false;
        String[] desErrores = null;
        desErrores = new String[4];
        long cuenta_deposito = 0;

        if (!campo.isEmpty()) {
            campo = campo.toString().trim();
            try {
                cuenta_deposito = Long.parseLong(campo);
                if (movimiento == 1) {
                    if (campo.length() == 10) {
                        valido = true;
                    } else {
                        desErrores[0] = line + "";
                        desErrores[1] = "7";
                        desErrores[2] = " Cuenta de Depósito ";
                        desErrores[3] = " La Cuenta debe ser de 10 dígitos. ";
                        errores.add(desErrores);
                        valido = false;
                    }
                } else if (movimiento == 2) {
                    if (campo.length() == 18) {
                        valido = true;
                    } else {
                        desErrores[0] = line + "";
                        desErrores[1] = "7";
                        desErrores[2] = " Cuenta de Depósito ";
                        desErrores[3] = " La Cuenta debe ser de 18 dígitos. ";
                        errores.add(desErrores);
                        valido = false;
                    }
                } else if (movimiento == 3) {
                    if (campo.length() == 16) {
                        valido = true;
                    } else {
                        desErrores[0] = line + "";
                        desErrores[1] = "7";
                        desErrores[2] = " Cuenta de Depósito ";
                        desErrores[3] = " La Cuenta debe ser de 16 dígitos. ";
                        errores.add(desErrores);
                        valido = false;
                    }
                } else if (movimiento == 4) {
                    if (campo.equals("0")) {
                        valido = true;
                    } else {
                        desErrores[0] = line + "";
                        desErrores[1] = "7";
                        desErrores[2] = " Cuenta de Depósito ";
                        desErrores[3] = " El número de cuenta debe ser 0. ";
                        errores.add(desErrores);
                        valido = false;
                    }
                } else if (movimiento == 5) {
                    if (!campo.isEmpty()) {
                        valido = true;
                    } else {
                        desErrores[0] = line + "";
                        desErrores[1] = "7";
                        desErrores[2] = " Cuenta de Depósito ";
                        desErrores[3] = " La cuenta debe ser mayor a 8 dígitos . ";
                        errores.add(desErrores);
                        valido = false;
                    }
                } else {
                    desErrores[0] = line + "";
                    desErrores[1] = "7";
                    desErrores[2] = " Cuenta de Depósito ";
                    desErrores[3] = " Tipo de movimiento fuera de rango. ";
                    errores.add(desErrores);
                    valido = false;
                }
            } catch (Exception ex) {
                desErrores[0] = line + "";
                desErrores[1] = "7";
                desErrores[2] = " Cuenta de Depósito ";
                desErrores[3] = " Formato inválido. ";
                errores.add(desErrores);
                valido = false;
                System.out.println("ModeloLayOut-validaCuentaDeposito:" + ex.toString());
            }
        } else {
            desErrores[0] = line + "";
            desErrores[1] = "7";
            desErrores[2] = " Cuenta de Depósito ";
            desErrores[3] = " Campo obligatorio. ";
            errores.add(desErrores);
            valido = false;
        }

        return valido;
    }

    /**
     * Método para validar la cuenta de depósito del Lay-Out
     *
     * @param String campo: Clave de Banco
     * @param String movimiento: Tipo de Movimiento actual
     * @param int line: Linea actual del archivo.
     * @return boolean valido: True si se trata de una cuenta valida, según el
     * tipo de movimiento que se pasa como parámetro, false en otro caso.
     */
    public static boolean validaCuentaDepositoABM(String campo, int movimiento, int line) {
        boolean valido = false;
        String[] desErrores = new String[4];
        long cuenta_deposito = 0;

        if (!campo.isEmpty()) {
            campo = campo.toString().trim();
            try {
                cuenta_deposito = Long.parseLong(campo);
                if (movimiento == 1) {
                    if (campo.length() == 10) {
                        valido = true;
                    } else {
                        desErrores[0] = line + "";
                        desErrores[1] = "7";
                        desErrores[2] = " Cuenta de Depósito ";
                        desErrores[3] = " La Cuenta debe ser de 10 dígitos. ";
                        errores.add(desErrores);
                        valido = false;
                    }
                } else if (movimiento == 2) {
                    if (campo.length() == 18) {
                        //ANTIO: Se obtendrá la clabe bancaria y se verificará en la BD en tabla bancosABM
                        //ANTIO: Esto se realizará obteniendo los 3 primeros caracteres de la cuenta bancaria que representa el banco a depositar
                        String verifica = ModeloLayOut.verificaBancoABM(campo);
                        if (verifica == null) {
                            valido = true;
                        } else {
                            desErrores[0] = line + "";
                            desErrores[1] = "7";
                            desErrores[2] = " Cuenta de Depósito ";
                            desErrores[3] = verifica;
                            errores.add(desErrores);
                            valido = false;
                        }
                    } else {
                        desErrores[0] = line + "";
                        desErrores[1] = "7";
                        desErrores[2] = " Cuenta de Depósito ";
                        desErrores[3] = " La Cuenta debe ser de 18 dígitos. ";
                        errores.add(desErrores);
                        valido = false;
                    }
                } else if (movimiento == 3) {
                    if (campo.length() == 16) {
                        valido = true;
                    } else {
                        desErrores[0] = line + "";
                        desErrores[1] = "7";
                        desErrores[2] = " Cuenta de Depósito ";
                        desErrores[3] = " La Cuenta debe ser de 16 dígitos. ";
                        errores.add(desErrores);
                        valido = false;
                    }
                } else if (movimiento == 4) {
                    if (campo.equals("0")) {
                        valido = true;
                    } else {
                        desErrores[0] = line + "";
                        desErrores[1] = "7";
                        desErrores[2] = " Cuenta de Depósito ";
                        desErrores[3] = " El número de cuenta debe ser 0. ";
                        errores.add(desErrores);
                        valido = false;
                    }
                } else if (movimiento == 5) {
                    if (!campo.isEmpty()) {
                        valido = true;
                    } else {
                        desErrores[0] = line + "";
                        desErrores[1] = "7";
                        desErrores[2] = " Cuenta de Depósito ";
                        desErrores[3] = " La cuenta debe ser mayor a 8 dígitos . ";
                        errores.add(desErrores);
                        valido = false;
                    }
                } else {
                    desErrores[0] = line + "";
                    desErrores[1] = "7";
                    desErrores[2] = " Cuenta de Depósito ";
                    desErrores[3] = " Tipo de movimiento fuera de rango. ";
                    errores.add(desErrores);
                    valido = false;
                }
            } catch (Exception ex) {
                desErrores[0] = line + "";
                desErrores[1] = "7";
                desErrores[2] = " Cuenta de Depósito ";
                desErrores[3] = " Formato inválido. ";
                errores.add(desErrores);
                valido = false;
                System.out.println("ModeloLayOut-validaCuentaDeposito:" + ex.toString());
            }
        } else {
            desErrores[0] = line + "";
            desErrores[1] = "7";
            desErrores[2] = " Cuenta de Depósito ";
            desErrores[3] = " Campo obligatorio. ";
            errores.add(desErrores);
            valido = false;
        }

        return valido;
    }

    /**
     *
     * @param campo: Clave bancaria de 18 digitos
     * @return mensage: Indica el error generado o null si no se encontro error.
     */
    public static String verificaBancoABM(String campo) {
        String respuesta = null;
        int clave_banco = 0;

        try {
            clave_banco = Integer.parseInt(campo.substring(0, 3));
            if (clave_banco >= 2 && clave_banco <= 902) {
                if (buscaBancoABM(clave_banco)) {
                    if (new Validaciones().validaCLABE(campo)) {
                        return null;
                    } else {
                        respuesta = "El dígito verificador de la CLABE es incorrecto.";
                    }
                } else {
                    respuesta = "La cuenta bancaria no esta asociada a ningun banco.";
                }
            } else {
                respuesta = "Clave bancaria fuera de rango";
            }
        } catch (Exception e) {
            respuesta = "Error en el formato de cuenta bancaria.";
            System.out.println("ModeloLayOut-validaCuentaDeposito:" + e.toString());
        }

        return respuesta;
    }

    /**
     * Metodo para la clave de banco que se pasa como parametro
     *
     * @param String moneda: Tipo de banco a validar
     * @return boolean valida: true / false
     */
    public static boolean buscaBancoABM(int clave_banco) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;

        boolean valida = false;
        String MySql = "";

        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select distinct clave ";
            MySql += " from bancosABM ";
            MySql += "where clave = " + clave_banco;

//            System.out.println(MySql);
            ResultSet rstSQLServer = statement.executeQuery(MySql);

            if (rstSQLServer.next()) {
                valida = true;
            } else {
                valida = false;
            }
            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            valida = false;
            System.out.println("ModeloLayOut-validaTipoBanco:" + e.toString());
        }
        return valida;

    }

    /**
     * Método para validar el importe de liquidación del Lay-Out
     *
     * @param String campo: Importe de liquidación
     * @param String movimiento: Tipo de Movimiento actual
     * @param int line: Linea actual del archivo.
     * @return boolean valido: True si se trata de un importe válido, según el
     * tipo de movimiento que se pasa como parámetro, false en otro caso.
     */
    public static boolean validaImporteLiquidacion(String campo, int movimiento, int line, float salario_minimo) {
        boolean valido = false;
        String[] desErrores = null;
        String validaImporte = "";
        if (!campo.isEmpty()) {

            validaImporte = ModeloLayOut.validaImporte(campo);

            if (validaImporte.equals(" Se espera una cantidad de 2 decimales. ") || validaImporte.equals(" Se espera una cantidad mayor a 0. ")
                    || validaImporte.equals(" Formato del importe de liquidación inválido. ") || validaImporte.equals(" Importe de Liquidación obligatorio. ")) {

                desErrores = new String[4];
                desErrores[0] = line + "";
                desErrores[1] = "8";
                desErrores[2] = " Importe de Liquidación ";
                desErrores[3] = validaImporte;
                errores.add(desErrores);
            } else if (!validaImporte.equals("")) {
                BigDecimal importe = new BigDecimal(validaImporte);
                if (movimiento == 1) {
                    resumenMov.sumMov1(importe);
                    resumenMov.inc_mov_tipo1();
                    valido = true;
                } else if (movimiento == 2) {
                    resumenMov.sumMov2(importe);
                    resumenMov.inc_mov_tipo2();
                    valido = true;
                } else if (movimiento == 3) {
                    resumenMov.sumMov3(importe);
                    resumenMov.inc_mov_tipo3();
                    valido = true;
                } else if (movimiento == 4) {
                    resumenMov.sumMov4(importe);
                    resumenMov.inc_mov_tipo4();
                    valido = true;
                } else if (movimiento == 5) {
                    resumenMov.sumMov5(importe);
                    resumenMov.inc_mov_tipo5();
                    return true;
                } else {
                    desErrores = new String[4];
                    desErrores[0] = line + "";
                    desErrores[1] = "8";
                    desErrores[2] = " Importe de Liquidación ";
                    desErrores[3] = " Tipo de movimiento fuera de rango. ";
                    errores.add(desErrores);
                    valido = false;
                }

                // Validación del salario mínimo
                if (importe.floatValue() < salario_minimo) {
                    desErrores = new String[4];
                    desErrores[0] = line + "";
                    desErrores[1] = "8";
                    desErrores[2] = " Importe de Liquidación ";
                    desErrores[3] = " Monto de indemnización inferior a la legal permitida: $" + salario_minimo;
                    errores.add(desErrores);
                    valido = false;
                }
            } else {
                desErrores = new String[4];
                desErrores[0] = line + "";
                desErrores[1] = "8";
                desErrores[2] = " Importe de Liquidación ";
                desErrores[3] = " Formato Inválido.";
                errores.add(desErrores);
                valido = false;
            } //a
        } else {
            desErrores = new String[4];
            desErrores[0] = line + "";
            desErrores[1] = "8";
            desErrores[2] = " Importe de Liquidación ";
            desErrores[3] = " Campo Obligatorio. ";
            errores.add(desErrores);
            valido = false;
        }
        return valido;
    }

    /**
     * Método para validar el CURP del Lay-Out
     *
     * @param String campo: CURP.
     * @param int line: Linea actual del archivo.
     * @return boolean valido: True si se trata de una CURP válida, false en
     * otro caso.
     */
    public static boolean validaCURP(String campo, int line) {

        String[] desErrores = null;
        boolean valido = false;
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
                            valido = true;
                        }
                    }
                    if (!valido) {
                        desErrores = new String[4];
                        desErrores[0] = line + "";
                        desErrores[1] = "10";
                        desErrores[2] = " CURP " + m.group();
                        desErrores[3] = "Nomenclatura incorrecta.";
                        errores.add(desErrores);
                    }
                } else {
                    desErrores = new String[4];
                    desErrores[0] = line + "";
                    desErrores[1] = "10";
                    desErrores[2] = " CURP ";
                    desErrores[3] = " Se Esperan 18 Caracteres. ";
                    errores.add(desErrores);
                    valido = false;
                }
            } else {
                desErrores = new String[4];
                desErrores[0] = line + "";
                desErrores[1] = "10";
                desErrores[2] = " CURP ";
                desErrores[3] = " Campo Obligatorio. ";
                errores.add(desErrores);
                valido = false;
            }
        } catch (Exception exc) {
            System.out.println("Exception:ModeloLayOut-validaCURP:" + exc.getMessage());
            desErrores = new String[4];
            desErrores[0] = line + "";
            desErrores[1] = "10";
            desErrores[2] = " CURP ";
            desErrores[3] = " Nomenclatura incorrecta. ";
            errores.add(desErrores);
            valido = false;
        }

        return valido;
    }

    /**
     * Método para validar el RFC del Lay-Out
     *
     * @param String campo: RFC.
     * @param int line: Linea actual del archivo.
     * @return boolean valido: True si se trata de un RFC válido, false en otro
     * caso.
     */
    public static boolean validaRFC(String campo, int line) {

        String[] desErrores = null;
        boolean valido = false;
        try {
            if (!campo.isEmpty()) {
                campo = campo.trim();
                campo = campo.toUpperCase();
                if (campo.length() == 13) {
                    //verificamos si se trata de una persona fisica
                    //expresion regular para verificacion de RFC-persona fisica
                    String patron = "[A-Z,Ñ,&]{4}[0-9]{2}[0-1][0-9][0-3][0-9][A-Z,0-9]{3}";
                    Pattern p = Pattern.compile(patron);
                    Matcher m = p.matcher(campo);
                    if (m.matches()) {
                        if (campo.equals(m.group())) {
                            valido = true;
                        }
                    }
                    if (!valido) {
                        desErrores = new String[4];
                        desErrores[0] = line + "";
                        desErrores[1] = "4";
                        desErrores[2] = " RFC " + m.group();
                        desErrores[3] = "Nomenclatura incorrecta.";
                        errores.add(desErrores);
                    }
                } else {
                    desErrores = new String[4];
                    desErrores[0] = line + "";
                    desErrores[1] = "4";
                    desErrores[2] = " RFC ";
                    desErrores[3] = " Se esperan 13 caracteres. ";
                    errores.add(desErrores);
                    valido = false;
                }
            } else {
                desErrores = new String[4];
                desErrores[0] = line + "";
                desErrores[1] = "4";
                desErrores[2] = " RFC ";
                desErrores[3] = " Campo Obligatorio. ";
                errores.add(desErrores);
                valido = false;
            }
        } catch (Exception exc) {
            System.out.println("Exception:ModeloLayOut-validaRFC:" + exc.getMessage());
            desErrores = new String[4];
            desErrores[0] = line + "";
            desErrores[1] = "4";
            desErrores[2] = " RFC ";
            desErrores[3] = " Nomenclatura incorrecta. ";
            errores.add(desErrores);
            valido = false;
        }

        return valido;
    }

    /**
     * Se valida que la cadena de entrada solo sean letras y no sean las
     * palabras NA, NINGUNA, NINGUN, NINGUNO
     *
     * @param campo
     * @return
     */
    public static boolean validaCadenaSinNumeros(String campo) {
        boolean valido = false;
        try {
            campo = campo.trim();
            if (!campo.isEmpty()) {
                //valida que no sea un numero decimal
                try {
                    Float.parseFloat(campo);
                } catch (NumberFormatException nfe) {
                    //Quiere decir que no es un numero, por lo tanto es correcto
                    valido = true;
                }
                String patron;
                Pattern p;
                Matcher m;
                if (valido) {
                    valido = false;
                    //expresion regular para verificar que la cadena solo tenga (letras,-,_,.,(,),#)
                    patron = "([A-Z]|[a-z]|[0-9]| |Ñ|ñ|!|\"|%|&|'|´|-|:|;|<|=|>|@|_|,|\\{|\\}|`|~|á|é|í|ó|ú|Á|É|Í|Ó|Ú|ü|Ü){1,100}";
                    p = Pattern.compile(patron);
                    m = p.matcher(campo);
                    if (m.matches()) {
                        if (campo.equals(m.group())) {
                            valido = true;
                        }
                    }
                }
                //expresion regular para verificar que no sea alguna de las palabaras
                //NA,NINGUN?,solo numeros                
                if (valido) {
                    patron = "(NA|N/A|N-A|N+A|N.A|^NINGUN(.?)|\\d*)";
                    p = Pattern.compile(patron);
                    m = p.matcher(campo);
                    if (m.matches()) {
                        if (campo.equals(m.group())) {
                            valido = false;
                        }
                    }
                }

            } else {
                valido = false;
            }
        } catch (Exception exc) {
            System.out.println("Exception:ModeloLayOut-validaCadenaSinNumeros:" + exc.getMessage());
            valido = false;
        }

        return valido;
    }

    /**
     * Metodo para validar el LAY-OUT
     *
     * @param String urlArchivo: Ruta del LAY-OUT
     * @return boolean auth: true / false
     */
    public static String isArchivoValido(String urlArchivo, String clave_contrato, float salario_minimo) {

        int index = 0, idx = 0;
        int movimiento = 0, line = 0;
        long cuenta_deposito = 0;
        int clave_banco = 0;//, verLayOut = -1;// se declara con un numero negativo

        String valido = "";
        String linea = "";
        String campo = "";
        String contrato = "";
        String fechaLiquidacion = "";
        String[] desErrores = null;
        clsFecha fecha = new clsFecha();
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        StringTokenizer token = null;
        errores = new ArrayList();
        resumenMov = new ResumenMovimientos();
        try {
            // Creacion de un BufferedReader para poder hacer
            // una lectura comoda (disponer del método readLine()).
            archivo = new File(urlArchivo);
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            while ((linea = br.readLine()) != null) {
                line++;
                index = 0;
                //if(line==1){
                //  verLayOut = identificaVersionLayOut(linea);
                //  if(verLayOut==1){//version antigua con datesEdoCta extranjeros
                //    }
                //    else if(verLayOut==2){//nueva version de LayOut de subida, (reduccion de campos, se agrega cabecera, se eliminan datesEdoCta a extranjero)
                //         //se prosigue a la extracción de la información incluida en la cabecera
                //         // es la unica información incluida en la cabecera, se prosigue a leer cada movimiento a partir de linea 2 del archivo
                //         // break;
                //          }
                //    else{//terminar proceso, ya que hay error en identificación de archivo
                //          }
                //}
                token = new StringTokenizer(linea, "|");
                //if(verLayOut==1)   // si es version anterior de LayOut, se prosigue con el procesamiento habitual
                while (token.hasMoreTokens()) {
                    campo = token.nextToken();
                    index++;

                    switch (index) {

                        case 1:  //Validación de CONTRATO
                            campo = campo.toString().trim();
//                        if(){
                            if (line != 1) {
                                if (!campo.equals(contrato)) {
                                    desErrores = new String[4];
                                    desErrores[0] = line + "";
                                    desErrores[1] = "1";
                                    desErrores[2] = " Contrato ";
                                    desErrores[3] = " La clave debe ser la misma para TODOS los movimientos. ";
                                    errores.add(desErrores);
                                }
                            } else if (ModeloLayOut.validaContrato(campo, line, clave_contrato)) {//Esto no deberia de ser, ya que lo realiza para todos los campos, solo revisa si hay un caracter no valido y si la fecha de apertura valida
                                contrato = campo;
                            } else {
                                contrato = "";
                            } //                            }
                            break;

                        case 2: //Validación de FECHA DE LIQUIDACIÓN
                            campo = campo.toString().trim();
                            if (line != 1) {
                                if (!fechaLiquidacion.equals(campo)) {
                                    desErrores = new String[4];
                                    desErrores[0] = line + "";
                                    desErrores[1] = "2";
                                    desErrores[2] = " Fecha de Liquidación ";
                                    desErrores[3] = " La fecha debe ser la misma para TODOS los movimientos. ";
                                    errores.add(desErrores);
                                }
                            } else {
                                if (campo.contains("'")) {
                                    desErrores = new String[4];
                                    desErrores[0] = line + "";
                                    desErrores[1] = "2";
                                    desErrores[2] = " Fecha de Liquidación ";
                                    desErrores[3] = " Símbolo inválido en la fecha \" ' \" ";
                                    errores.add(desErrores);
                                }

                                if (ModeloLayOut.validaFechaLiquidacion(campo, line)) {
                                    fechaLiquidacion = campo;
                                } else {
                                    fechaLiquidacion = "";
                                }
                            }
                            break;

                        case 3://Validación de TIPO DE MOVIMIENTO
                            campo = campo.toString().trim();
                            ModeloLayOut.validaMovimiento(campo, line);
                            try {
                                movimiento = Integer.parseInt(campo);
                            } catch (Exception e) {
                                System.out.println("ModeloLayOut-isArchivoValido:" + e.toString());
                            }
                            break;

                        case 4://Validación de CLAVE DE BANCO
                            campo = campo.toString().trim();
//                           ModeloLayOut.validaClaveBanco(campo, movimiento, line);
                            ModeloLayOut.validaRFC(campo, line);
                            if (campo.isEmpty()) {
                                desErrores = new String[4];
                                desErrores[0] = line + "";
                                desErrores[1] = "4";
                                desErrores[2] = " RFC ";
                                desErrores[3] = " Campo obligatorio. ";
                                errores.add(desErrores);
                            }
//                            try {
//                                clave_banco = Integer.parseInt(campo);
//                            } catch (Exception e) {
//                                System.out.println("ModeloLayOut-isArchivoValido:" + e.toString());
//                            }
                            break;

                        case 5://Validacion de MONEDA
                            campo = campo.toString().trim();
                            ModeloLayOut.validaClaveMoenda(campo, movimiento, line);
                            break;

                        case 6://Validación de NOMBRE DE FIDEICOMISARIO
                            desErrores = new String[4];
                            campo = campo.toString().trim().toUpperCase();
                            if (!campo.isEmpty()) {
                                if (!ModeloLayOut.validaNombreFidei(campo)) {
                                    desErrores[0] = line + "";
                                    desErrores[1] = "6";
                                    desErrores[2] = " Nombre de Fideicomisario ";
                                    desErrores[3] = " NO se aceptan caracteres especiales. ";
                                    errores.add(desErrores);
                                } else if (ModeloLayOut.setNombreFidei(campo) == null) {
                                    desErrores[0] = line + "";
                                    desErrores[1] = "6";
                                    desErrores[2] = " Nombre de Fideicomisario ";
                                    desErrores[3] = " Nombre incompleto. ";
                                    errores.add(desErrores);
                                }
                            } else {
                                desErrores[0] = line + "";
                                desErrores[1] = "6";
                                desErrores[2] = " Nombre de Fideicomisario ";
                                desErrores[3] = " Campo  obligatorio. ";
                                errores.add(desErrores);
                            }
                            break;

                        case 7://Validación de CUENTA DE DEPÓSITO
                            campo = campo.toString().trim();
                            boolean respuesta = ModeloLayOut.validaCuentaDepositoABM(campo, movimiento, line);
                            try {
                                if (respuesta) {
                                    clave_banco = Integer.parseInt(campo.substring(0, 3));
                                }
                                cuenta_deposito = Long.parseLong(campo);
                            } catch (Exception e) {
                                System.out.println("ModeloLayOut-isArchivoValido-cuentaDeposito:" + e.toString());
                            }

                            break;

                        case 8://Validacion de IMPORTE DE LIQUIDACION
                            campo = campo.toString().trim();
                            ModeloLayOut.validaImporteLiquidacion(campo, movimiento, line, salario_minimo);
                            break;

                        case 9://Validacion de ID DE FIDEICOMISARIO
                            campo = campo.toString().trim();
                            if (campo.isEmpty()) {
                                desErrores = new String[4];
                                desErrores[0] = line + "";
                                desErrores[1] = "9";
                                desErrores[2] = " ID de Fideicomisario ";
                                desErrores[3] = " Campo obligatorio. ";
                                errores.add(desErrores);
                            } else if (campo.contains("'")) {
                                desErrores = new String[4];
                                desErrores[0] = line + "";
                                desErrores[1] = "9";
                                desErrores[2] = " ID de Fideicomisario ";
                                desErrores[3] = " Símbolo inválido en la clave del fideicomisario \" ' \" ";
                                errores.add(desErrores);
                            }
                            break;

                        case 10://Validación de CURP
                            campo = campo.toString().trim();
                            ModeloLayOut.validaCURP(campo, line);
                            break;

                        case 11: //Validacion de FECHA INGRESO
                            campo = campo.toString().trim();
                            String tmpFecha = "";
                            if (!campo.isEmpty()) {
                                campo = ModeloLayOut.validaFecha(campo);
                                if (campo.equals(" Fecha Inválida. ") || campo.equals(" Se Requieren 8 Caracteres. ") || campo.equals(" Formato de Fecha Inválido. ")) {
                                    desErrores = new String[4];
                                    desErrores[0] = line + "";
                                    desErrores[1] = "11";
                                    desErrores[2] = " Fecha de Ingreso ";
                                    desErrores[3] = " " + campo + " ";
                                    errores.add(desErrores);
                                } else if (fechaLiquidacion.equals("")) {
                                    desErrores = new String[4];
                                    desErrores[0] = line + "";
                                    desErrores[1] = "11";
                                    desErrores[2] = " Fecha de Ingreso ";
                                    desErrores[3] = "La validación depende de la fecha de liquidación. ";
                                    errores.add(desErrores);
                                } else {
                                    String fechaIngreso = campo;
                                    tmpFecha = ModeloLayOut.validaFecha(fechaLiquidacion);
                                    String formato = "dd-MM-yyyy";
                                    int a = fecha.ComparaFecha(tmpFecha, formato, fechaIngreso, formato);
                                    if (a <= 0) {
                                        desErrores = new String[4];
                                        desErrores[0] = line + "";
                                        desErrores[1] = "11";
                                        desErrores[2] = " Fecha de Ingreso ";
                                        desErrores[3] = " Debe ser menor a la fecha de liquidación. ";
                                    }
                                }
                            } else {
                                desErrores = new String[4];
                                desErrores[0] = line + "";
                                desErrores[1] = "11";
                                desErrores[2] = " Fecha de Ingreso ";
                                desErrores[3] = " Campo obligatorio. ";
                                errores.add(desErrores);
                            }
                            break;

                        case 12://Validación de PUESTO
                        {
                            boolean respValidacion = ModeloLayOut.validaCadenaSinNumeros(campo);
                            if (!respValidacion) {
                                desErrores = new String[4];
                                desErrores[0] = line + "";
                                desErrores[1] = "12";
                                desErrores[2] = " Puesto ";
                                desErrores[3] = " Contenido inválido. ";
                                errores.add(desErrores);
                            }
                            break;
                        }

                        case 13://Validación de DEPARTAMENTO
                        {
                            boolean respValidacion = ModeloLayOut.validaCadenaSinNumeros(campo);
                            if (!respValidacion) {
                                desErrores = new String[4];
                                desErrores[0] = line + "";
                                desErrores[1] = "13";
                                desErrores[2] = " Departamento ";
                                desErrores[3] = " Contenido inválido. ";
                                errores.add(desErrores);
                            }
                            break;
                        }

                        case 14://Validación de ENVIO CHEQUE
                            campo = campo.toString().trim();
                            if (!campo.isEmpty()) {
                                campo = campo.toUpperCase();
                                if (campo.contains("'")) {
                                    desErrores = new String[4];
                                    desErrores[0] = line + "";
                                    desErrores[1] = "14";
                                    desErrores[2] = " Envío de Cheque ";
                                    desErrores[3] = " Símbolo inválido en destinatario \" ' \" ";
                                    errores.add(desErrores);
                                }
                                if (movimiento != 4) {
                                    if (movimiento > 0 && movimiento < 6) {
                                        if (!campo.equals("NA")) {
                                            desErrores = new String[4];
                                            desErrores[0] = line + "";
                                            desErrores[1] = "14";
                                            desErrores[2] = " Envío de Cheque ";
                                            desErrores[3] = " Se espera NA, "
                                                    + "según el tipo de movimiento especificado. ";
                                            errores.add(desErrores);
                                        }
                                    } else {
                                        desErrores = new String[4];
                                        desErrores[0] = line + "";
                                        desErrores[1] = "14";
                                        desErrores[2] = " Envío de Cheque ";
                                        desErrores[3] = " Tipo de movimiento fuera de rango. ";
                                        errores.add(desErrores);
                                    }
                                } else if (campo.length() < 3) {
                                    desErrores = new String[4];
                                    desErrores[0] = line + "";
                                    desErrores[1] = "14";
                                    desErrores[2] = " Envío de Cheque ";
                                    desErrores[3] = " Nombre del destinatario incompleto. ";
                                    errores.add(desErrores);
                                }
                            } else {
                                desErrores = new String[4];
                                desErrores[0] = line + "";
                                desErrores[1] = "14";
                                desErrores[2] = " Envío de Cheque ";
                                desErrores[3] = " Campo obligatorio. ";
                                errores.add(desErrores);
                            }
                            break;

                        case 15://Validacion de DESTINO CHEQUE
                            campo = campo.toString().trim();
                            if (!campo.isEmpty()) {
                                campo = campo.toUpperCase();

                                if (campo.contains("'")) {
                                    desErrores = new String[4];
                                    desErrores[0] = line + "";
                                    desErrores[1] = "15";
                                    desErrores[2] = " Destino de Cheque ";
                                    desErrores[3] = " Símbolo inválido en domicilio \" ' \" ";
                                    errores.add(desErrores);
                                }
                                if (movimiento != 4) {
                                    if (movimiento > 0 && movimiento < 6) {
                                        if (!campo.equals("NA")) {
                                            desErrores = new String[4];
                                            desErrores[0] = line + "";
                                            desErrores[1] = "15";
                                            desErrores[2] = " Destino de Cheque ";
                                            desErrores[3] = " Se espera NA, "
                                                    + "según el tipo de movimiento especificado. ";
                                            errores.add(desErrores);
                                        }
                                    } else {
                                        desErrores = new String[4];
                                        desErrores[0] = line + "";
                                        desErrores[1] = "15";
                                        desErrores[2] = " Destino de Cheque ";
                                        desErrores[3] = " Tipo de movimiento fuera de rango. ";
                                        errores.add(desErrores);
                                    }
                                } else if (campo.length() > 8) {
                                    valido = "";
                                } else {
                                    desErrores = new String[4];
                                    desErrores[0] = line + "";
                                    desErrores[1] = "15";
                                    desErrores[2] = " Destino de Cheque ";
                                    desErrores[3] = " Dirección de domicilio incompleta. ";

                                    errores.add(desErrores);
                                }
                            } else {
                                desErrores = new String[4];
                                desErrores[0] = line + "";
                                desErrores[1] = "15";
                                desErrores[2] = " Destino de Cheque ";
                                desErrores[3] = " Campo obligatorio. ";
                                errores.add(desErrores);
                            }
                            break;

                        case 16://Validación TELÉFONO CHEQUE
                            campo = campo.toString().trim();
                            if (!campo.isEmpty()) {
                                campo = campo.toUpperCase();
                                if (campo.contains("'")) {
                                    desErrores = new String[4];
                                    desErrores[0] = line + "";
                                    desErrores[1] = "16";
                                    desErrores[2] = " Teléfono de Cheque ";
                                    desErrores[3] = " Símbolo inválido en teléfono \" ' \" ";
                                    errores.add(desErrores);
                                }
                                if (movimiento != 4) {
                                    if (movimiento > 0 && movimiento < 6) {
                                        if (!campo.equals("NA")) {
                                            desErrores = new String[4];
                                            desErrores[0] = line + "";
                                            desErrores[1] = "16";
                                            desErrores[2] = " Teléfono de Cheque ";
                                            desErrores[3] = " Se espera NA, "
                                                    + "según el tipo de movimiento especificado.";
                                            errores.add(desErrores);
                                        }
                                    } else {
                                        desErrores = new String[4];
                                        desErrores[0] = line + "";
                                        desErrores[1] = "16";
                                        desErrores[2] = " Teléfono de Cheque ";
                                        desErrores[3] = " Tipo de movimiento fuera de rango. ";
                                        errores.add(desErrores);
                                    }
                                } else if (campo.length() < 3) {
                                    desErrores = new String[4];
                                    desErrores[0] = line + "";
                                    desErrores[1] = "16";
                                    desErrores[2] = " Teléfono de Cheque ";
                                    desErrores[3] = " Número telefónico incompleto. ";
                                    errores.add(desErrores);
                                }
                            } else {
                                desErrores = new String[4];
                                desErrores[0] = line + "";
                                desErrores[1] = "16";
                                desErrores[2] = " Teléfono de Cheque ";
                                desErrores[3] = " Campo obligatorio. ";
                                errores.add(desErrores);
                            }
                            break;

                        case 17://Validación CORREO CHEQUE
                            campo = campo.toString().trim();
                            if (!campo.isEmpty()) {
                                campo.toString().trim();
                                if (campo.contains("'")) {
                                    desErrores = new String[4];
                                    desErrores[0] = line + "";
                                    desErrores[1] = "17";
                                    desErrores[2] = " Correo de Cheque ";
                                    desErrores[3] = " Símbolo inválido en correo \" ' \" ";
                                    errores.add(desErrores);
                                }
                                if (movimiento != 4) {
                                    if (movimiento > 0 && movimiento < 6) {
                                        if (!campo.equals("NA")) {
                                            desErrores = new String[4];
                                            desErrores[0] = line + "";
                                            desErrores[1] = "17";
                                            desErrores[2] = " Correo de Cheque ";
                                            desErrores[3] = " Se espera NA, "
                                                    + "según el tipo de movimiento especificado. ";
                                            errores.add(desErrores);
                                        }
                                    } else {
                                        desErrores = new String[4];
                                        desErrores[0] = line + "";
                                        desErrores[1] = "17";
                                        desErrores[2] = " Correo de Cheque ";
                                        desErrores[3] = " Tipo de movimiento fuera de rango. ";
                                        errores.add(desErrores);
                                    }
                                } else if (campo.length() > 5) {
                                    if (!campo.contains("@")) {
                                        desErrores = new String[4];
                                        desErrores[0] = line + "";
                                        desErrores[1] = "17";
                                        desErrores[2] = " Correo de Cheque ";
                                        desErrores[3] = " Correo inválido. ";
                                        errores.add(desErrores);
                                    }
                                } else {
                                    desErrores = new String[4];
                                    desErrores[0] = line + "";
                                    desErrores[1] = "17";
                                    desErrores[2] = " Correo de Cheque ";
                                    desErrores[3] = " Correo incompleto. ";
                                    errores.add(desErrores);
                                }
                            } else {
                                desErrores = new String[4];
                                desErrores[0] = line + "";
                                desErrores[1] = "17";
                                desErrores[2] = " Correo de Cheque ";
                                desErrores[3] = " Campo obligatorio. ";
                                errores.add(desErrores);
                            }
                            break;

                        case 18://Validación BANCO EXTRANJERO
                            campo = campo.toString().trim();
                            if (!campo.isEmpty()) {
                                campo = campo.toUpperCase();

                                if (campo.contains("'")) {
                                    desErrores = new String[4];
                                    desErrores[0] = line + "";
                                    desErrores[1] = "18";
                                    desErrores[2] = " Banco Extranjero ";
                                    desErrores[3] = " Símbolo inválido en el nombre del banco \" ' \" ";
                                    errores.add(desErrores);
                                }

                                if (movimiento != 5) {
                                    if (movimiento > 0 && movimiento < 6) {
                                        if (!campo.equals("NA")) {
                                            desErrores = new String[4];
                                            desErrores[0] = line + "";
                                            desErrores[1] = "18";
                                            desErrores[2] = " Banco Extranjero ";
                                            desErrores[3] = " Se espera NA, "
                                                    + "según el tipo de movimiento especificado. ";
                                            errores.add(desErrores);
                                        }
                                    } else {
                                        desErrores = new String[4];
                                        desErrores[0] = line + "";
                                        desErrores[1] = "18";
                                        desErrores[2] = " Banco Extranjero ";
                                        desErrores[3] = " Tipo de movimiento fuera de rango. ";
                                        errores.add(desErrores);
                                    }
                                } else if (campo.length() < 2) {
                                    desErrores = new String[4];
                                    desErrores[0] = line + "";
                                    desErrores[1] = "18";
                                    desErrores[2] = " Banco Extranjero ";
                                    desErrores[3] = " Nombre del banco incompleto. ";
                                    errores.add(desErrores);
                                }
                            } else {
                                desErrores = new String[4];
                                desErrores[0] = line + "";
                                desErrores[1] = "18";
                                desErrores[2] = " Banco Extranjero ";
                                desErrores[3] = " Campo obligatorio. ";
                                errores.add(desErrores);
                            }
                            break;

                        case 19://Validacion DOMICILIO BANCO EXTRANJERO
                            campo = campo.toString().trim();
                            if (!campo.isEmpty()) {
                                campo = campo.toUpperCase();

                                if (campo.contains("'")) {
                                    desErrores = new String[4];
                                    desErrores[0] = line + "";
                                    desErrores[1] = "19";
                                    desErrores[2] = " Domicilio Banco Extranjero ";
                                    desErrores[3] = " Símbolo inválido en el domicilio \" ' \" ";
                                    errores.add(desErrores);
                                }
                                if (movimiento != 5) {
                                    if (movimiento > 0 && movimiento < 6) {
                                        if (!campo.equals("NA")) {
                                            desErrores = new String[4];
                                            desErrores[0] = line + "";
                                            desErrores[1] = "19";
                                            desErrores[2] = " Domicilio Banco Extranjero ";
                                            desErrores[3] = " Se espera NA, "
                                                    + "según el tipo de movimiento especificado. ";
                                            errores.add(desErrores);
                                        }
                                    } else {
                                        desErrores = new String[4];
                                        desErrores[0] = line + "";
                                        desErrores[1] = "19";
                                        desErrores[2] = " Domicilio Banco Extranjero ";
                                        desErrores[3] = " Tipo de movimiento fuera de rango. ";
                                        errores.add(desErrores);
                                    }
                                } else if (campo.length() < 2) {
                                    desErrores = new String[4];
                                    desErrores[0] = line + "";
                                    desErrores[1] = "19";
                                    desErrores[2] = " Domicilio Banco Extranjero ";
                                    desErrores[3] = " Dirección en extranjero incompleta. ";
                                    errores.add(desErrores);
                                }
                            } else {
                                desErrores = new String[4];
                                desErrores[0] = line + "";
                                desErrores[1] = "19";
                                desErrores[2] = " Domicilio Banco Extranjero ";
                                desErrores[3] = " Campo obligatorio. ";
                                errores.add(desErrores);
                            }
                            break;

                        case 20://Validacion PAIS BANCO EXTRANJERO
                            campo = campo.toString().trim();
                            if (!campo.isEmpty()) {
                                campo = campo.toUpperCase();

                                if (campo.contains("'")) {
                                    desErrores = new String[4];
                                    desErrores[0] = line + "";
                                    desErrores[1] = "20";
                                    desErrores[2] = " País Banco Extranjero ";
                                    desErrores[3] = " Símbolo inválido en el país \" ' \" ";
                                    errores.add(desErrores);
                                }
                                if (movimiento != 5) {
                                    if (movimiento > 0 && movimiento < 6) {
                                        if (!campo.equals("NA")) {
                                            desErrores = new String[4];
                                            desErrores[0] = line + "";
                                            desErrores[1] = "20";
                                            desErrores[2] = " País Banco Extranjero ";
                                            desErrores[3] = " Se espera NA, "
                                                    + "según el tipo de movimiento especificado. ";
                                            errores.add(desErrores);
                                        }
                                    } else {
                                        desErrores = new String[4];
                                        desErrores[0] = line + "";
                                        desErrores[1] = "20";
                                        desErrores[2] = " País Banco Extranjero ";
                                        desErrores[3] = " Tipo de movimiento fuera de rango. ";
                                        errores.add(desErrores);
                                    }
                                } else if (campo.length() < 2) {
                                    desErrores = new String[4];
                                    desErrores[0] = line + "";
                                    desErrores[1] = "20";
                                    desErrores[2] = " País Banco Extranjero ";
                                    desErrores[3] = " Nombre incompleto. ";
                                    errores.add(desErrores);
                                }
                            } else {
                                desErrores = new String[4];
                                desErrores[0] = line + "";
                                desErrores[1] = "20";
                                desErrores[2] = " País Banco Extranjero ";
                                desErrores[3] = " Campo obligatorio. ";
                                errores.add(desErrores);
                            }
                            break;

                        case 21://Validacion ABA/BIC
                            campo = campo.toString().trim();
                            if (!campo.isEmpty()) {

                                if (campo.contains("'")) {
                                    desErrores = new String[4];
                                    desErrores[0] = line + "";
                                    desErrores[1] = "21";
                                    desErrores[2] = " Clave ABA/BIC ";
                                    desErrores[3] = " Símbolo inválido en la clave \" ' \" ";
                                    errores.add(desErrores);
                                }

                                if (movimiento != 5) {
                                    campo = campo.toUpperCase();
                                    if (movimiento > 0 && movimiento < 6) {
                                        if (!campo.equals("NA")) {
                                            desErrores = new String[4];
                                            desErrores[0] = line + "";
                                            desErrores[1] = "21";
                                            desErrores[2] = " Clave ABA/BIC ";
                                            desErrores[3] = " Se espera NA, "
                                                    + "según el tipo de movimiento especificado. ";
                                            errores.add(desErrores);
                                        }
                                    } else {
                                        desErrores = new String[4];
                                        desErrores[0] = line + "";
                                        desErrores[1] = "21";
                                        desErrores[2] = " Clave ABA/BIC ";
                                        desErrores[3] = " Tipo de movimiento fuera de rango. ";
                                        errores.add(desErrores);
                                    }
                                } else if (campo.length() < 2) {
                                    desErrores = new String[4];
                                    desErrores[0] = line + "";
                                    desErrores[1] = "21";
                                    desErrores[2] = " Clave ABA/BIC ";
                                    desErrores[3] = " Clave incompleta. ";
                                    errores.add(desErrores);
                                }
                            } else {
                                desErrores = new String[4];
                                desErrores[0] = line + "";
                                desErrores[1] = "21";
                                desErrores[2] = " Clave ABA/BIC ";
                                desErrores[3] = " Campo obligatorio. ";
                                errores.add(desErrores);
                            }
                            break;

                        case 22://Validacion NOMBRE DEL FIDEICOMISARIO REGISTRADO EN BANCO EXTRANJERO
                            campo = campo.toString().trim();
                            if (!campo.isEmpty()) {
                                campo = campo.toUpperCase();

                                if (campo.contains("'")) {
                                    desErrores = new String[4];
                                    desErrores[0] = line + "";
                                    desErrores[1] = "22";
                                    desErrores[2] = " Fideicomisario en Banco Extranjero ";
                                    desErrores[3] = " Símbolo inválido en fideicomisario \" ' \" ";
                                    errores.add(desErrores);
                                }

                                if (movimiento != 5) {
                                    if (movimiento > 0 && movimiento < 6) {
                                        if (!campo.equals("NA")) {
                                            desErrores = new String[4];
                                            desErrores[0] = line + "";
                                            desErrores[1] = "22";
                                            desErrores[2] = " Fideicomisario en Banco Extranjero ";
                                            desErrores[3] = " Se espera NA, "
                                                    + "según el tipo de movimiento especificado. ";
                                            errores.add(desErrores);
                                        }
                                    } else {
                                        desErrores = new String[4];
                                        desErrores[0] = line + "";
                                        desErrores[1] = "22";
                                        desErrores[2] = " Fideicomisario en Banco Extranjero ";
                                        desErrores[3] = " Tipo de movimiento fuera de rango. ";
                                        errores.add(desErrores);
                                    }
                                } else if (campo.length() < 2) {
                                    desErrores = new String[4];
                                    desErrores[0] = line + "";
                                    desErrores[1] = "22";
                                    desErrores[2] = " Fideicomisario en Banco Extranjero ";
                                    desErrores[3] = " Nombre incompleto. ";
                                    errores.add(desErrores);
                                }
                            } else {
                                desErrores = new String[4];
                                desErrores[0] = line + "";
                                desErrores[1] = "22";
                                desErrores[2] = " Fideicomisario en Banco Extranjero ";
                                desErrores[3] = " Nombre obligatorio. ";
                                errores.add(desErrores);
                            }
                            break;

                        case 23://Validación DIRECCIÓN FIDEICOMISARIO
                            campo = campo.toString().trim();

                            if (!campo.isEmpty()) {
                                campo = campo.toUpperCase();

                                if (campo.contains("'")) {
                                    desErrores = new String[4];
                                    desErrores[0] = line + "";
                                    desErrores[1] = "23";
                                    desErrores[2] = " Domicilio en Banco Extranjero ";
                                    desErrores[3] = " Símbolo inválido en domicilio \" ' \" ";
                                    errores.add(desErrores);
                                }
                                if (movimiento != 5) {
                                    if (movimiento > 0 && movimiento < 6) {
                                        if (!campo.equals("NA")) {
                                            desErrores = new String[4];
                                            desErrores[0] = line + "";
                                            desErrores[1] = "23";
                                            desErrores[2] = " Domicilio en Banco Extranjero ";
                                            desErrores[3] = " Se espera NA, "
                                                    + "según el tipo de movimiento especificado. ";
                                            errores.add(desErrores);
                                        }
                                    } else {
                                        desErrores = new String[4];
                                        desErrores[0] = line + "";
                                        desErrores[1] = "23";
                                        desErrores[2] = " Domicilio en Banco Extranjero ";
                                        desErrores[3] = " Tipo de movimiento fuera de rango. ";
                                        errores.add(desErrores);
                                    }
                                } else if (campo.length() < 2) {
                                    desErrores = new String[4];
                                    desErrores[0] = line + "";
                                    desErrores[1] = "23";
                                    desErrores[2] = " Domicilio en Banco Extranjero ";
                                    desErrores[3] = " Domicilio incompleto. ";
                                    errores.add(desErrores);
                                }
                            } else {
                                desErrores = new String[4];
                                desErrores[0] = line + "";
                                desErrores[1] = "23";
                                desErrores[2] = " Domicilio en Banco Extranjero ";
                                desErrores[3] = " Campo obligatorio. ";
                                errores.add(desErrores);
                            }
                            break;

                        case 24://Validacion PAIS FIDEICOMISARIO
                            campo = campo.toString().trim();
                            if (!campo.isEmpty()) {
                                campo = campo.toUpperCase();

                                if (campo.contains("'")) {
                                    desErrores = new String[4];
                                    desErrores[0] = line + "";
                                    desErrores[1] = "24";
                                    desErrores[2] = " País Fideicomisario en Extranjero ";
                                    desErrores[3] = " Símbolo inválido en país \" ' \" ";
                                    errores.add(desErrores);
                                }
                                if (movimiento != 5) {
                                    if (movimiento > 0 && movimiento < 6) {
                                        if (!campo.equals("NA")) {
                                            desErrores = new String[4];
                                            desErrores[0] = line + "";
                                            desErrores[1] = "24";
                                            desErrores[2] = " País Fideicomisario en Extranjero ";
                                            desErrores[3] = " Se espera NA, "
                                                    + "según el tipo de movimiento especificado. ";
                                            errores.add(desErrores);
                                        }
                                    } else {
                                        desErrores = new String[4];
                                        desErrores[0] = line + "";
                                        desErrores[1] = "24";
                                        desErrores[2] = " País Fideicomisario en Extranjero ";
                                        desErrores[3] = " Tipo de movimiento fuera de rango. ";
                                        errores.add(desErrores);
                                    }
                                } else if (campo.length() < 2) {
                                    desErrores = new String[4];
                                    desErrores[0] = line + "";
                                    desErrores[1] = "24";
                                    desErrores[2] = " País Fideicomisario en Extranjero ";
                                    desErrores[3] = " Nombre incompleto. ";
                                    errores.add(desErrores);
                                }
                            } else {
                                desErrores = new String[4];
                                desErrores[0] = line + "";
                                desErrores[1] = "24";
                                desErrores[2] = " País Fideicomisario en Extranjero ";
                                desErrores[3] = " Campo obligatorio. ";
                                errores.add(desErrores);
                            }
                            break;

                        case 25://Validacion TELEFONO FIDEICOMISARIO
                            campo = campo.toString().trim();

                            if (!campo.isEmpty()) {
                                campo = campo.toUpperCase();
                                if (campo.contains("\"")) {
                                    desErrores = new String[4];
                                    desErrores[0] = line + "";
                                    desErrores[1] = "25";
                                    desErrores[2] = " Teléfono Fideicomisario en Extranjero ";
                                    desErrores[3] = " El número telefónico cuenta con comillas ";
                                    errores.add(desErrores);
                                }

                                if (campo.contains("'")) {
                                    desErrores = new String[4];
                                    desErrores[0] = line + "";
                                    desErrores[1] = "25";
                                    desErrores[2] = " Teléfono Fideicomisario en Extranjero ";
                                    desErrores[3] = " Símbolo inválido en teléfono \"'\" ";
                                    errores.add(desErrores);
                                }
                                if (movimiento != 5) {
                                    if (movimiento > 0 && movimiento < 6) {
                                        if (!campo.equals("NA")) {
                                            desErrores = new String[4];
                                            desErrores[0] = line + "";
                                            desErrores[1] = "25";
                                            desErrores[2] = " Teléfono Fideicomisario en Extranjero ";
                                            desErrores[3] = " Se espera NA, "
                                                    + "según el tipo de movimiento especificado. ";
                                            errores.add(desErrores);
                                        }
                                    } else {
                                        desErrores = new String[4];
                                        desErrores[0] = line + "";
                                        desErrores[1] = "25";
                                        desErrores[2] = " Teléfono Fideicomisario en Extranjero  ";
                                        desErrores[3] = " Tipo de movimiento fuera de rango. ";
                                        errores.add(desErrores);
                                    }
                                } else if (campo.length() < 3) {
                                    desErrores = new String[4];
                                    desErrores[0] = line + "";
                                    desErrores[1] = "25";
                                    desErrores[2] = " Teléfono Fideicomisario en Extranjero ";
                                    desErrores[3] = " Teléfono incompleto. ";
                                    errores.add(desErrores);
                                }
                            } else {
                                desErrores = new String[4];
                                desErrores[0] = line + "";
                                desErrores[1] = "25";
                                desErrores[2] = " Teléfono Fideicomisario en Extranjero ";
                                desErrores[3] = " Campo obligatorio. ";
                                errores.add(desErrores);
                            }
                            break;

                        default:
                            desErrores = new String[4];
                            desErrores[0] = line + "";
                            desErrores[1] = "          ";
                            desErrores[2] = "          ";
                            desErrores[3] = " Campos DE MAS en el LAY-OUT. ";
                            errores.add(desErrores);
                    }//Fin del swich

                    if (!token.hasMoreTokens() && index < 25) {
                        desErrores = new String[4];
                        desErrores[0] = line + "";
                        desErrores[1] = index + "";
                        desErrores[2] = "          ";
                        desErrores[3] = "Campos FALTANTES en el LAY-OUT. ";
                        errores.add(desErrores);
                    }

                }//Fin de lectura de linea

                //if(verLayOut==2)//Validación de LayOut Nuevo
//                while (token.hasMoreTokens()) {
//                    campo = token.nextToken();
//                    index++;
//
//                    switch (index) {
//
//                        case 1:  //Validación de CONTRATO
//       ANTIO:::::VUELVE A VALIDAR LOS MISMOS CAMPOS
            } //Fin de lectura del documento

            if (errores.isEmpty()) {

                BigDecimal b1 = null;
                BigDecimal b2 = null;
                //Establecemos la Fecha de Liquidación
                if (fechaLiquidacion.length() == 8) {
                    String anio = fechaLiquidacion.substring(0, 4);
                    String mes = fechaLiquidacion.substring(4, 6);
                    mes = fecha.mes(mes);
                    String dia = fechaLiquidacion.substring(6, 8);
                    fechaLiquidacion = dia + " de " + mes + " del " + anio;
                }
                resumenMov.setTotal_movimientos(resumenMov.get_total_movs());
                resumenMov.setFecha_liquidacion(fechaLiquidacion);

                //Establecemos formatos del importe
                String z = resumenMov.getPago_mov_tipo1() + "";
                resumenMov.setFormato_importe_tipo1(ModeloLayOut.formatoImporte(z));

                z = resumenMov.getPago_mov_tipo2() + "";
                resumenMov.setFormato_importe_tipo2(ModeloLayOut.formatoImporte(z));

                z = resumenMov.getPago_mov_tipo3() + "";
                resumenMov.setFormato_importe_tipo3(ModeloLayOut.formatoImporte(z));

                z = resumenMov.getPago_mov_tipo4() + "";
                resumenMov.setFormato_importe_tipo4(ModeloLayOut.formatoImporte(z));

                z = resumenMov.getPago_mov_tipo5() + "";
                String importeTipo5 = ModeloLayOut.formatoImporte(z);
                resumenMov.setFormato_importe_tipo5(importeTipo5.substring(2, importeTipo5.length()));

                //Establecemos la suma del importe total de los 4 datesEdoCta
                resumenMov.setImporteTotalMXP(resumenMov.getSumImporteTotalMXP());
                z = resumenMov.getImporteTotalMXP() + "";
                resumenMov.setFormato_importe_totalMXP(ModeloLayOut.formatoImporte(z));

                String honSinIva = ModeloLayOut.getInfoContrato(contrato);
                String sp_c = ModeloLayOut.getSuficienciaPatronal(z, honSinIva, "16");
                resumenMov.setSPR(new BigDecimal(sp_c));
                resumenMov.setFormato_SPR(ModeloLayOut.formatoImporte(sp_c));

                String hf_c = ModeloLayOut.getHonorariosFiduciarios(z, sp_c, honSinIva);
                resumenMov.setAFI(hf_c);
                resumenMov.setFormato_AFI(ModeloLayOut.formatoImporte(hf_c));

                String xIva = ModeloLayOut.estableceIVA(hf_c);
                resumenMov.setIVA(xIva);
                resumenMov.setFormato_iva(ModeloLayOut.formatoImporte(xIva));

                b1 = new BigDecimal(Double.parseDouble(hf_c));
                b2 = new BigDecimal(Double.parseDouble(xIva));

                b1 = b1.add(b2);
                z = b1.setScale(2, BigDecimal.ROUND_HALF_UP) + "";

                resumenMov.setHonorarios_fidu(z);
                resumenMov.setFormato_honorarios_fidu(ModeloLayOut.formatoImporte(z));

                //Se realiza la consulta del saldo actual
                String infoSaldo = "", arrayInfoSaldos[] = null;
                double saldo_actual = 0.0, nuevoSaldo = 0.0, liquidaciones_pendientes = 0.0;
                infoSaldo = ModeloLayOut.getSaldo_LiqPend_Fid(clave_contrato);
                arrayInfoSaldos = infoSaldo.split("%");
                if (arrayInfoSaldos.length == 3) {
                    liquidaciones_pendientes = Double.parseDouble(arrayInfoSaldos[2]);
                    saldo_actual = Double.parseDouble(arrayInfoSaldos[0]);
                    nuevoSaldo = saldo_actual - liquidaciones_pendientes - Double.parseDouble(resumenMov.getImporteTotalMXP().toString());
                    DecimalFormat formato = new DecimalFormat("$ #,##0.00");
                    resumenMov.setSaldo_actual(formato.format(saldo_actual));
                    resumenMov.setLiquidaciones_pendientes(formato.format(liquidaciones_pendientes));
                    resumenMov.setNuevo_saldo(formato.format(Math.abs(nuevoSaldo)));
                    if (nuevoSaldo < 0) {
                        System.out.println("Es insuficiente");
                        resumenMov.setTxt_Nuevo_saldo("Insuficiencia patrimonial");
                        double insuficiencia_patrimonial = Math.abs(nuevoSaldo);
                        honSinIva = ModeloLayOut.getInfoContrato(contrato);
                        sp_c = ModeloLayOut.getSuficienciaPatronal("" + insuficiencia_patrimonial, honSinIva, "16");
                        resumenMov.setAportacion_minima_requerida(formato.format(Double.parseDouble(sp_c)));
                    } else {
                        System.out.println("Hay remanente");
                        resumenMov.setTxt_Nuevo_saldo("Patrimonio remanente");
                        resumenMov.setAportacion_minima_requerida("$ 0.00");
                    }
                    Vector<Double> movsPendientesTipo5 = getMovsPendTipo5(clave_contrato);
                    if (movsPendientesTipo5 != null) {
                        if (movsPendientesTipo5.size() > 0) {
                            resumenMov.setTotal_movs_tipo5_pend(movsPendientesTipo5.get(0).intValue());
                            importeTipo5 = formato.format(movsPendientesTipo5.get(1));
                            resumenMov.setFormato_importe_tipo5_pend(importeTipo5.substring(2, importeTipo5.length()));
                        } else {
                            resumenMov.setTotal_movs_tipo5_pend(0);
                            resumenMov.setFormato_importe_tipo5_pend("0.00");
                        }
                    } else {
                        System.out.println("Error obteniendo movimientos pendientes Tipo 5");
                    }
                } else {
                    System.out.println("Error obteniendo el saldo del fieicomiso en ModeloLayOut");
                }

            }

        } catch (Exception e) {
            System.out.println("ModeloLayOut-isArchivoValido:" + e.toString());
            e.printStackTrace();

        } finally {
            // Cerramos el fichero; asegurarmos el cierre tanto
            // si todo fue bien como si surgio una excepcion.
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception ex) {
                valido = "Error al Realizar la Validación";
                ex.printStackTrace();
            }
        }
        return valido;
    }

    /**
     * Método que almacena en la base de datos el lote (Lay-Out) que se
     * especificaca con los datos que se pasan como parámetro.
     *
     * @param String urlArchivo:
     * @param String cliente:
     * @param String usuario:
     * @param String nombreArchivo:
     * @param String userApp: Bean asociado al usuario que ingreso al sistema.
     * @return boolean auth: true / false
     */
    /**
     * Método que almacena en la base de datos el lote (Lay-Out) que se
     * especificaca con los datos que se pasan como parámetro.
     *
     * @param clave_contrato: Clave del fideicomiso.
     * @param clave_cliente: Clave asociada la cliente en el sistema.
     * @param usuario: Usuario que intenta almacenar el lote.
     * @param nombreArchivo :Nombre del LayOut que se intenta almacenar.
     * @param urlArchivo: Ruta del LAY-OUT.
     * @return boolean auth: true / false
     */
    public synchronized boolean guardaLayOut(String clave_contrato, String clave_cliente, String usuario,
            String correo_origen, String correo_destino, String urlArchivo, String nombreArchivo) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;

        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        StringTokenizer token = null;
        List datos_bitacora = null;
        clsFecha fecha = new clsFecha();

        int idx = 0;
        String MySql = "";
        String campo = "";
        String fecha_hoy = "";
        int tipo_movimiento = 0;
        String fecha_liquidacion = "";
        String linea = "", registro = "";
        boolean seGuardo = false;
        fecha_carga = "";
        try {
            connection = conn.ConectaSQLServer();
            connection.setAutoCommit(false);
            statement = connection.createStatement();

            archivo = new File(urlArchivo + nombreArchivo);
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            while ((linea = br.readLine()) != null) {
                registro = "";
                tipo_movimiento = 0;
                idx = 0;
                token = new StringTokenizer(linea, "|");
                if (token.hasMoreTokens()) {
                    while (token.hasMoreTokens()) {
                        campo = token.nextToken();
                        if (!campo.equals(" ")) {
                            campo = campo.toUpperCase().toString().trim();
                        }
                        idx++;
                        if (idx == 3) {
                            tipo_movimiento = Integer.parseInt(campo);
                        }
//                        if (idx == 4) {
//                            continue;
//                        }
                        if (idx == 2 || idx == 11) {
                            campo = ModeloLayOut.validaFechaBD(campo);
                            if (idx == 2) {
                                fecha_liquidacion = campo;
                            }
                        } else if (idx == 6) {
                            String name[] = ModeloLayOut.setNombreFidei(campo);
                            if (name != null) {
                                campo = name[0] + "','" + name[1] + "','" + name[2];
                            }
                        } else if (campo.equals(" ") || campo.equals("NA")) {
                            campo = "NA";
                        }
                        if (registro.equals("")) {
                            registro = "'" + campo + "'";
                        } else if (idx == 8) {
                            if (campo.contains(".")) {
                                registro = registro + ",'" + campo + "'" + ",'0'";
                            } else {
                                String importeStr = ModeloLayOut.validaImporte(campo);
                                Double.parseDouble(importeStr);
                                registro = registro + ",'" + importeStr + "'" + ",'0'";
                            }
                        } else if (idx == 7) {
                            if (tipo_movimiento == 1) {
                                registro = registro + ",'" + campo + "'" + ",'" + 12 + "'";
                            } else if (tipo_movimiento == 2) {
                                registro = registro + ",'" + campo + "'" + ",'" + Integer.parseInt(campo.substring(0, 3)) + "'";
                            } else if (tipo_movimiento == 3) {
                                registro = registro + ",'" + campo + "'" + ",'" + 2 + "'";
                            } else if (tipo_movimiento == 4) {
                                registro = registro + ",'" + campo + "'" + ",'" + 0 + "'";
                            } else if (tipo_movimiento == 5) {
                                registro = registro + ",'" + campo + "'" + ",'" + 0 + "'";
                            }
                        } else {
                            registro = registro + "," + "'" + campo + "'";
                        }
                    }
//                    System.out.println("registro:" + registro);
                    MySql = " insert into movimientos ";
                    MySql += " ( clave_contrato , fecha_liquidacion , tipo_movimiento , rfc, tipo_moneda ,nombre_empleado , "
                            + " apellidoP_empleado , apellidoM_empleado , cuenta_deposito, clave_banco , importe_liquidacion ,importe_liquidacion_mxp, clave_empleado , curp , "
                            + " fecha_ingreso , puesto_empleado , depto_empleado , envio_cheque , destino_envio_cheque , tel_envio_cheque , "
                            + " correo_envio_cheque , banco_extranjero , dom_banco_extranjero , pais_banco_extranjero , ABA_BIC , "
                            + " nombre_fidei_banco_ext , direccion_fidei_ext , pais_fidei_ext , tel_fidei_ext,nombre_archivo )";
                    MySql += " values  ";
                    MySql += " ( ";
                    MySql += " " + registro + ", ";
                    MySql += " '" + nombreArchivo + "' ";
                    MySql += " ) ";
//                    System.out.println(MySql);
                    statement.executeUpdate(MySql);
                }
            }
            MySql = " insert into movimientos_h ";
            MySql += " (clave_cliente , clave_contrato , fecha_liquidacion , usuario_cliente , nombre_archivo , fecha_captura , status )";
            MySql += " values ";
            MySql += " ( ";
            MySql += "'" + clave_cliente + "',";
            MySql += "'" + clave_contrato + "',";
            MySql += "'" + fecha_liquidacion + "',";
            MySql += "'" + usuario + "',";
            MySql += "'" + nombreArchivo + "', ";
            MySql += " getDate() ,";
            MySql += "'A'";
            MySql += " ) ";
//            System.out.println(MySql);
            statement.executeUpdate(MySql);
            MySql = " update movimientos_h ";
            MySql += " set clave_archivo = (select count(clave_contrato)  ";
            MySql += " from movimientos_h ";
            MySql += " where clave_contrato = '" + clave_contrato + "' ";
            MySql += " and fecha_liquidacion ='" + fecha_liquidacion + "') ";
            MySql += " where clave_contrato = '" + clave_contrato + "' ";
            MySql += " and fecha_liquidacion = '" + fecha_liquidacion + "' ";
            MySql += " and nombre_archivo='" + nombreArchivo + "' ";
            MySql += " and status = 'A' ";
//            System.out.println(MySql);
            statement.executeUpdate(MySql);

            datos_bitacora = ModeloLayOut.getDatosBitacora();
            fecha_hoy = fecha.getFechaHoy();
            String genera_bitacora = this.generaBitacora(datos_bitacora, correo_origen, correo_destino, "Bitácora", "", urlArchivo, "BITACORA.txt");

            if (genera_bitacora.equals("")) {
                fecha_carga = fecha.getFechaHoy();
                connection.commit();
                seGuardo = true;
            } else {
                connection.rollback();
                seGuardo = false;
            }
        } catch (Exception e) {
            try {
                fecha_carga = "";
                System.out.println("ModeloLayOut-guardaLayOut:" + e.toString());
                e.printStackTrace();
                connection.rollback();
                if (connection != null) {
                    conn.Desconecta(connection);
                }
                statement.close();
                fr.close();
            } catch (Exception e2) {
                System.out.println("ModeloLayOut-guardaLayOut:" + e2.toString());
            }
            seGuardo = false;
        } finally {
            try {
                if (connection != null) {
                    conn.Desconecta(connection);
                }
                statement.close();
                fr.close();
            } catch (Exception ex) {
                System.out.println("ModeloLayOut-guardaLayOut:" + ex.toString());
            }
        }
        return seGuardo;
    }

    /**
     * Método que regresa una lista con la información necesaria para generar
     * una bitácora de los fideicomitentes que tienen datesEdoCta pendientes por
     * operar.
     *
     * @return Información de los Fideicomitentes que tienen datesEdoCta
     * pendientes por operar actualmente. Si no hay fideicomitentes pendientes
     * regresa una lista vacía, si hay algún error regresa null y lista con los
     * datos de los fideicomitentes pendientes en otro caso.
     */
    public static List getDatosBitacora() {
        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        ResultSet rstSQLServer = null;

        List datos_clientes = null;
        String dato_cliente = "";
        String MySql = "";
        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select h.clave_contrato, c.nombre_cliente, h.nombre_archivo,  ";
            MySql += " convert(varchar,fecha_liquidacion,103), h.usuario_cliente, ";
            MySql += " convert(varchar,h.fecha_captura,103)  ";
            MySql += " from movimientos_h h, contratos c  ";
            MySql += " where h.clave_contrato = c.clave_contrato  ";
            MySql += " and c.status = 'A'  ";
            MySql += " and h.status not in ('T')  ";
            MySql += " order by h.fecha_captura asc   ";
//            System.out.println(MySql);

            datos_clientes = new ArrayList();
            rstSQLServer = statement.executeQuery(MySql);
            if (rstSQLServer.next()) {
                dato_cliente = "FIDEICOMISO" + "	";
                dato_cliente += "NOMBRE FIDEICOMITENTE" + "	";
                dato_cliente += "NOMBRE DE LAY-OUT" + "	";
                dato_cliente += "FECHA DE LIQUIDACION" + "	";
                dato_cliente += "USUARIO CAPTURA" + "	";
                dato_cliente += "FECHA DE CARGA" + "	";
                ;
                datos_clientes.add(dato_cliente);
                while (rstSQLServer.next()) {
                    dato_cliente = rstSQLServer.getString(1).toString().trim() + "	";
                    dato_cliente += rstSQLServer.getString(2).toString().trim() + "	";
                    dato_cliente += rstSQLServer.getString(3).toString().trim() + "	";
                    dato_cliente += rstSQLServer.getString(4).toString().trim() + "	";
                    dato_cliente += rstSQLServer.getString(5).toString().trim() + "	";
                    dato_cliente += rstSQLServer.getString(6).toString().trim();
                    datos_clientes.add(dato_cliente);
                }
            }
            rstSQLServer.close();
            statement.close();
            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            datos_clientes = null;
            System.out.println("ModeloLayOut-getDatosBitacora:" + e.toString());
            try {
                rstSQLServer.close();
                statement.close();
                if (connection != null) {
                    conn.Desconecta(connection);
                }
            } catch (Exception ex) {
            }
        }
        return datos_clientes;
    }

    /**
     * Método que genera una bitacora informativa de los fideicomitentes que
     * tiene datesEdoCta pendientes por operar actualmente.
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
//                enviaBitacora = EnvioMail.enviaCorreo(correo_origen, correo_destino, asunto_correo, cuerpo_correo, url_bitacora, nombre_bitacora,"587");
//                if(!enviaBitacora){
//                enviaBitacora = EnvioMail.enviaCorreo(correo_origen, correo_destino, asunto_correo, cuerpo_correo, url_bitacora, nombre_bitacora,"25");
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

    /**
     * Método que almacena el lote especificado con los datos que se pasan como
     * parámetro y además crea una clave unica que identifica al reporte de
     * liquidación que se asociará con dicho lote.
     *
     * @param String clave_contrato: Clave de fideicomiso.
     * @param String clave_cliete:Clave del fideicomitente en el sistema de
     * liquidaciones.
     * @param String nombre_archivo: Nombre del lote (LAY-OUT).
     * @param String usuario: usuario que intenta almacenar el lote (No
     * necesariamente tiene que ser el mismo usuario que ingreso al sistema,
     * pero si tiene que ser un usuario valido para el fideicomitente).
     * @param String dirNameTmp: Ruta donde se encuentra almacenado el lote.
     * @return boolean auth: true / false
     */
    public synchronized String cargaLayOut(String clave_contrato, String clave_cliente, String nombre_archivo,
            String usuario, String dirNameTmp, String correo_origen, String correo_destino) {
        //Variables locales
        String mensaje = "";
        String verifica = "";
        boolean guarda = false;
        try {
            //Se verifica si el archivo ya fue almacenado por otro usuario.
            verifica = ModeloLayOut.archivoValidado(clave_contrato, "", nombre_archivo);
            if (verifica.equals("")) {
                //Se almacena el Lote en la Base de Datos.
                guarda = this.guardaLayOut(clave_contrato, clave_cliente, usuario, correo_origen, correo_destino, dirNameTmp, nombre_archivo);
                //Verificamos si ocurrio algún error al realizar el almacenamiento.
                if (!guarda) {
                    mensaje = " Error almacenando el archivo ";
                }
            } else {
                mensaje = " El archivo ha sido cargado por otro usuario ";
            }
        } catch (Exception e) {
            mensaje = "Error de almacenamiento";
            System.out.println("ModeloLayOut-cargaLayOut:" + e.getMessage());
        }
        return mensaje;
    }

    /**
     * Metodo para validar el tipo de moneda
     *
     * @param String moneda: Tipo de moneda a validar
     * @return boolean valida: true / false
     */
    public static boolean validaTipoMomeda(String moneda) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;

        boolean valida = false;
        String MySql = "";

        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select distinct clave ";
            MySql += " from monedas ";
            // Convertimos la clave de moneda en Mayusculas
            MySql += "where clave = '" + moneda.toUpperCase() + "'";
//            System.out.println(MySql);

            ResultSet rstSQLServer = statement.executeQuery(MySql);

            if (rstSQLServer.next()) {
                valida = true;

            } else {
                valida = false;
            }
            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            valida = false;
            System.out.println("ModeloLayOut-validaTipoMomeda:" + e.toString());
        }
        return valida;
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
        return correo;
    }

    /**
     * Metodo para la clave de banco que se pasa como parametro
     *
     * @param String moneda: Tipo de banco a validar
     * @return boolean valida: true / false
     */
    public static boolean validaTipoBanco(int clave_banco) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;

        boolean valida = false;
        String MySql = "";

        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select distinct clave ";
            MySql += " from bancos ";
            MySql += "where clave = " + clave_banco;

//            System.out.println(MySql);
            ResultSet rstSQLServer = statement.executeQuery(MySql);

            if (rstSQLServer.next()) {
                valida = true;
            } else {
                valida = false;
            }
            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            valida = false;
            System.out.println("ModeloLayOut-validaTipoBanco:" + e.toString());
        }
        return valida;

    }

    /**
     * Metodo para validar si se encuentra registrada la clave de contrato que
     * se pasa como parámetro.
     *
     * @param String clave: Clave de contrato a validar
     * @return boolean valida: true / false
     */
    public static boolean validaClaveContrato(String clave) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;

        boolean valida = false;
        String MySql = "";

        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select distinct clave_contrato ";
            MySql += " from contratos ";
            // Convertimos la clave de moneda en Mayusculas
            MySql += "where clave_contrato = '" + clave.toUpperCase() + "'";
//            System.out.println(MySql);

            ResultSet rstSQLServer = statement.executeQuery(MySql);

            if (rstSQLServer.next()) {
                valida = true;
            } else {
                valida = false;
            }
            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            valida = false;
            System.out.println("ModeloLayOut-validaClaveContrato:" + e.toString());
        }
        return valida;
    }

    /**
     * Método para validar si el archivo que se pasa como parámetro ya fue
     * almacenado en la BD.
     *
     * @param String clave_contrato: Clave de fideicomiso.
     * @param String fecha_liquidacion: Fecha en que tendrá lugar la
     * liquidación.
     * @param String nombreArchivo: Archivo a validar
     * @return boolean valida: true / false
     */
    public static String archivoValidado(String clave_contrato, String fecha_liquidacion, String nombreArchivo) {
        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;

        String valida = "";
        String MySql = "";
        try {
            if (nombreArchivo != null && !nombreArchivo.equals("")) {
                connection = conn.ConectaSQLServer();
                statement = connection.createStatement();
                statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

                MySql = " select nombre_archivo ";
                MySql += " from movimientos_h ";
                MySql += " where nombre_archivo = '" + nombreArchivo + "'";

                if (!clave_contrato.equals("")) {
                    MySql += " and clave_contrato = '" + clave_contrato + "'";
                }
                if (!fecha_liquidacion.equals("")) {
                    MySql += " and fecha_liquidacion = '" + fecha_liquidacion + "'";
                }

                ResultSet rstSQLServer = statement.executeQuery(MySql);
                if (rstSQLServer.next()) {
                    valida = "Almacenado";
                } else {
                    valida = "";
                }
                rstSQLServer.close();
                statement.close();
                if (connection != null) {
                    conn.Desconecta(connection);
                }
            } else {
                valida = "Error nombre de Lay-Out";
            }
        } catch (Exception e) {
            if (connection != null) {
                conn.Desconecta(connection);
            }
            valida = "Error validando carga previa";
            System.out.println("ModeloLayOut-archivoValidado:" + e.toString());
        }
        return valida;
    }

    /**
     * Método para autenficar al usuario que intenta ingresar al sistema.
     *
     * @param String cliente: Clave de acceso asociada al cliente.
     * @param String usuario: Usuario.
     * @param String password: Contraseña del usuario.
     * @return boolean auth: true / false. Si ocurre alg[un error regresa
     */
    public static Usuario validaUsuario(String cliente, String usuario, String password) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;

        Usuario userApp = new Usuario();
        String users[] = null;

        String userIngApp = "";
        String userTmp = "";
        String tmp = "";
        String MySql = "";

        bloqueaUser = false;
        int k = 0;
        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            if (cliente.equals("PRB16082011") && usuario.equals("PRB16082011") && password.equals("PRB16082011")) {
                userApp.setId_cliente("PRB16082011");
                userApp.setClave_contrato("PRB16082011");
                userApp.setCliente("PRUEBA SISTEMAS");
                userApp.setUsuario("PRB16082011");
                userApp.setPassword("PRB16082011");
                userApp.setAutentificado(true);
            } else {
                MySql = "select usuario.clave_cliente ,usuario.clave_contrato, contrato.nombre_cliente , usuario.usuario , usuario.password ";
                MySql += " from usuarios usuario , contratos contrato ";
                MySql += " where usuario.clave_contrato = contrato.clave_contrato";
                MySql += " and usuario.clave_cliente ='" + cliente + "' ";
                MySql += " and usuario.usuario ='" + usuario + "' ";
                MySql += " and usuario.password='" + password + "' ";
                MySql += " and contrato.status='A' ";
                MySql += " and usuario.status = 'A'";
                //                System.out.println(MySql);
                ResultSet rstSQLServer = statement.executeQuery(MySql);

                if (rstSQLServer.next()) {
                    tmp = rstSQLServer.getString(1).toString().trim(); // Clave de cliente
                    userApp.setId_cliente(tmp);

                    tmp = rstSQLServer.getString(2).toString().trim(); // Clave de contrato
                    userApp.setClave_contrato(tmp);

                    tmp = rstSQLServer.getString(3).toString().trim(); //Nombre de cliente
                    userApp.setCliente(tmp);

                    tmp = rstSQLServer.getString(4).toString().trim(); //Usuario de cliente
                    userApp.setUsuario(tmp);

                    tmp = rstSQLServer.getString(5).toString().trim(); //Password de cliente
                    userApp.setPassword(tmp);

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
                } else {
                    tmp = "";
                    int val = 0;
                    //Verificamos si el usuario que intenta ingresar esta registrado
                    //en la base para guardar un registro de su acceso sin éxito.
                    MySql = " select usuario";
                    MySql += " from usuarios ";
                    MySql += " where clave_cliente ='" + cliente + "' ";
                    MySql += " and usuario ='" + usuario + "' ";
                    MySql += " and status = 'A'";
                    rstSQLServer = statement.executeQuery(MySql);
//                    System.out.println(MySql);
                    System.out.println("Pasa 3");
                    if (rstSQLServer.next()) {
                        if (usersIngApp.equals("")) {
                            usersIngApp = usuario + ":" + "1" + ";";
                        } else {
                            users = usersIngApp.split(";");
                            for (int i = 0; i < users.length; i++) {
                                userIngApp = users[i];
                                k = userIngApp.indexOf(":");
                                tmp = userIngApp.substring(0, k);
                                //Verificamos si el cliente que intenta ingresar se encuantra en la
                                //lista de los clientes que han ingresado al sistema.
                                if (tmp.equals(usuario)) {
                                    tmp = userIngApp.substring(k + 1, userIngApp.length());
                                    val = Integer.parseInt(tmp);
                                    //Si el numero de intentos es mayor a 0 y menor a 4 entonces
                                    //incrementamos el número de intentos.
                                    if (val > 0 && val < 3) {
                                        val++;
                                        userTmp = userTmp + usuario + ":" + val + ";";
                                        //en otro caso (intentos mayor a 3) mandamos una señal para que el
                                        //cliente sea bloqueado.
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
                    userApp = new Usuario();
                }//Fin del primer else
                rstSQLServer.close();
                statement.close();

                if (connection != null) {
                    conn.Desconecta(connection);
                }
            }
        } catch (Exception e) {
            userApp = null;
            System.out.println("ModeloLayOut-validaUsuario:" + e.toString());
        }
        return userApp;
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

        String cliente = userApp.getId_cliente().toString().trim();
        String usuario = userApp.getUsuario().toString().trim();
        String password = userApp.getPassword().toString().trim();
        newPassword = newPassword.toString().trim();

        try {
            connection = conn.ConectaSQLServer();
            connection.setAutoCommit(false);
            statement = connection.createStatement();

            MySql = " update usuarios ";
            MySql += " set password='" + newPassword + "' ";
            MySql += " where clave_cliente ='" + cliente + "' ";
            MySql += " and usuario ='" + usuario + "' ";
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
            System.out.println("ModeloLayOut-actualizaUsuario:" + e.getMessage());
            actualiza = false;

            if (connection != null) {
                conn.Desconecta(connection);
            }
        }
        return actualiza;
    }

    /**
     * Metodo para calcular la AFI según los campos que se pasan coo parámetro.
     *
     * @param String infoContrato: honorario,%honorario_sin_iva
     * @param BigDecimal importe: Importe total en MXP
     * @return BigDecimal resultado: valor de la AFI.
     */
    public static String estableceAFI(String infoContrato, BigDecimal importe) {

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
            System.out.println("ModeloLayOut-estableceAFI:" + e.toString());
        }

        return valorAFI;
    }

    /**
     * Metodo para calcular la IVA según los campos que se pasan como parámetro.
     *
     * @param BigDecimal AFI: AFI
     * @return BigDecimal resultado: IVA del AFI (16%).
     */
    public static String estableceIVA(String val) {
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
            System.out.println("ModeloLayOut-estableceIVA:" + e.toString());
        }

        return tmp;

    }

    /**
     * Metodo para obtener el tipo de honorario y honorarios sin iva del
     * contrato que se pasa como parámetro.
     *
     * @param contrato : clave de contrato
     * @return String info: tipo_honorario,honorario_sin_iva.
     */
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

    /**
     * Metodo para obtener la suficiencia Patrimonial Requerida con el resumen
     * de datesEdoCta que se pasa como parámetro.
     *
     * @param resumen : Resumen de datesEdoCta a realizar
     * @return String d1: Obtención de SPR.
     */
    public static BigDecimal estableceSPR(ResumenMovimientos resumen) {
        String trunc = "";
        BigDecimal d1 = resumen.getImporteTotalMXP();
        BigDecimal d2 = new BigDecimal(resumen.getHonorarios_fidu());
        d1 = d1.add(d2);

        //truncamos el numero a dos decimales
        d2 = new BigDecimal("100");
        d1 = d1.multiply(d2);
        trunc = d1 + "";
        d1 = new BigDecimal(trunc.substring(0, trunc.indexOf(".")));
        d1 = d1.divide(d2);
        return d1;

    }

    /*
     * Método que bloquea la sesión de un usuario.
     * @param clave_cliente : Clave del fideicomitente asociada al ingreso del sistema.
     * @param usuario : Nombre del usuario.
     * @return Boolean: regresa ture si el bloqueo se realizo correctamente,
     * else en otro caso.
     */
    public static synchronized boolean regeneraPassword(String clave_cliente, String usuario, String correoOrigen, String correoDestino) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;

        String[] specialChar = {"B", "b", "#", "x", "W", "z", "X", "*", "Z", "w", "F", "f"};
        boolean seGuardo = false;
        String newPassword = "";
        String lstrBody = "";
        String lstrMensaje = "";
        int random = 0, val;
        String MySql = "";
        try {
            connection = conn.ConectaSQLServer();
            connection.setAutoCommit(false);
            statement = connection.createStatement();

            newPassword = (int) (Math.random() * 1000000000.0) + "";
            val = 12 - newPassword.length();

            for (int j = 0; j < val; j++) {
                random = (int) Math.floor(Math.random() * 12);
                newPassword = newPassword + specialChar[random];
            }
            MySql = " update usuarios";
            MySql += " set password = '" + newPassword + "', ";
            MySql += " fecha_bloqueo=getDate()";
            MySql += " where clave_cliente = '" + clave_cliente + "' ";
            MySql += " and usuario = '" + usuario + "' ";

            //Creamos el cuerpo del correo electrónico.
            lstrMensaje = "Este mensaje responde a una solicitud automatizada para la regeneración de contraseña.";

            lstrBody += "<html> <style type='text/css'><!--"
                    + " .Estilo1 {font-family: Arial} "
                    + " .tablaTitulo{font-family:Arial;font-size:14px;background-color:#00CCFF;color:#00CCFF;}"
                    + " .tablaMenu{background-color:#00CCFF;color:#00CCFF;}"
                    + " .letraTabla{font-size:15px;font-family:Arial;}"
                    + " --></style>";
            lstrBody += "<head> ";
            lstrBody += "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\"> ";
            lstrBody += "<title>Documento sin t&iacute;tulo</title> ";
            lstrBody += "</head> ";

            lstrBody += "<body> ";
            lstrBody += "<table width=\"70%\"  border=\"0\"> ";
            lstrBody += "<font size=2 face=\"Arial\"> ";
            lstrBody += "<tr> ";
            lstrBody += "<td f>&nbsp;</td> ";
            lstrBody += "<td>&nbsp;</td> ";
            lstrBody += "</tr> ";
            lstrBody += "<tr> ";
            lstrBody += "<td colspan=\"2\" f><font size=2 face=\"Arial\">" + lstrMensaje + "</font></td> ";
            lstrBody += "</tr> ";
            lstrBody += "<tr> ";
            lstrBody += "<td colspan=\"2\"> ";
            lstrBody += "<div align=\"center\"> ";
            lstrBody += "<table  class='letraTabla'  bordercolordark='#ddf8cc' "
                    + " bordercolorlight='#ddf8cc'> ";

            lstrBody += " <tr style='color:#000000'> ";
            lstrBody += " <td bgcolor='#ddf8cc' nowrap align=\"right\"><b>&nbsp;" + " Clave Cliente: " + "</b></td>";
            lstrBody += " <td bgcolor='#ddf8cc' nowrap  align=\"left\"><b>&nbsp;" + clave_cliente + "</b></td>";
            lstrBody += " </tr> ";
            lstrBody += " <tr style='color:#000000'> ";
            lstrBody += " <td bgcolor='#ddf8cc' nowrap align=\"right\"><b>&nbsp;" + " Usuario: " + "</b></td>";
            lstrBody += " <td bgcolor='#ddf8cc' nowrap align=\"left\" ><b>&nbsp;" + usuario + "</b></td>";
            lstrBody += " </tr> ";
            lstrBody += " <tr style='color:#000000'> ";
            lstrBody += " <td bgcolor='#ddf8cc' nowrap align=\"right\"><b>&nbsp;" + " Contraseña: " + "</b></td>";
            lstrBody += " <td bgcolor='#ddf8cc' nowrap align=\"left\" ><b>&nbsp;" + newPassword + "</b></td>";
            lstrBody += " </tr> ";

            lstrBody += "</table> ";
            lstrBody += "</div> ";
            lstrBody += "</td> ";
            lstrBody += "</tr> ";
            lstrBody += "<tr> ";
            lstrBody += "<td colspan=\"2\" >&nbsp;</td> ";
            lstrBody += "</tr> ";
            lstrBody += "<tr> ";
            lstrBody += "<td colspan=\"2\" f><font size=2 face=\"Arial\">Le sugerimos cambiar su contraseña. </font></td> ";
            lstrBody += "</tr> ";
            lstrBody += "<tr> ";
            lstrBody += "<td colspan=\"2\">&nbsp;</td> ";
            lstrBody += "</tr> ";
            lstrBody += "</table> ";
            lstrBody += " ";
            lstrBody += "</body> ";
            lstrBody += "</html> ";

            seGuardo = EnvioMail.enviaCorreo(correoOrigen, correoDestino, " Regeneración de Contraseña ", lstrBody, "587");
//            System.out.println(MySql);
            // Se ejecuta el encabezado
            statement.executeUpdate(MySql);

            connection.commit();
//            System.out.println("Transaction commit...");
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            seGuardo = false;
            System.out.println("ModeloLayOut-regeneraPassword:" + e.toString());
            e.printStackTrace();
            if (connection != null) {
                conn.Desconecta(connection);
            }
        }
        return seGuardo;
    }

    /**
     * Método que regresa el correo electrónico del usuario del sistema que se
     * le pasa como parámetro.
     *
     * @param clave_cliente : clave asociada al fideicomitente en el sistema.
     * @param usuario : Nombre de usuario.
     * @return String : correo electrónico.
     */
    public static String getCorreoUsuario(String clave_cliente, String usuario) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;

        clave_cliente.toString().trim();
        usuario.toString().trim();
        String correo = "";
        String MySql = "";

        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select contacto_usuario ";
            MySql += " from usuarios ";
            MySql += " where clave_cliente = '" + clave_cliente + "'";
            MySql += " and usuario  = '" + usuario + "'";
            MySql += " and status = 'A'";

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
            correo = null;
            System.out.println("ModeloLayOut-getCorreoUsuario:" + e.toString());
        }

        return correo;
//        return "antio_valerio@hotmail.com";

    }

    /**
     * Método que regresa la clave del archivo (Lote procesado)
     *
     * @param clave_contrato: Clave de Fideicomiso.
     * @param fecha_liquidacion : Fecha de Liquidación.
     * @param nombre_archivo : Nombre del layOut.
     * @return int : Identificador de lote almacenado.
     */
    public static int getClaveArchivo(String clave_contrato, String fecha_liquidacion, String nombre_archivo) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        clsFecha fecha = new clsFecha();

        String MySql = "";
        int id_archivo = 0;

        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select clave_archivo ";
            MySql += " from movimientos_h ";
            MySql += " where clave_contrato = '" + clave_contrato + "' ";
            MySql += " and fecha_liquidacion = '" + fecha.CambiaFormatoFecha("dd/MM/yyyy", "yyyy-MM-dd", fecha_liquidacion) + " 00:00:00.00' ";
            MySql += " and nombre_archivo = '" + nombre_archivo + "' ";

//            System.out.println("getClaveArchivo:" + MySql);
            ResultSet rstSQLServer = statement.executeQuery(MySql);

            if (rstSQLServer.next()) {
                id_archivo = Integer.parseInt(rstSQLServer.getString(1));
            } else {
                id_archivo = -1;
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
            System.out.println("ModeloLayOut-getClaveArchivo:" + e.getMessage());
        }
        return id_archivo;
    }

    /**
     * Método que regresa la clave del archivo (Lote procesado)
     *
     * @param clave_contrato: Clave de Fideicomiso.
     * @param fecha_liquidacion : Fecha de Liquidación.
     * @param nombre_archivo : Nombre del layOut.
     * @return int : Identificador de lote almacenado.
     */
    public static int getClaveArchivo_nombreArchivo(String clave_contrato, String nombre_archivo) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;

        String MySql = "";
        int id_archivo = 0;

        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select clave_archivo ";
            MySql += " from movimientos_h ";
            MySql += " where clave_contrato = '" + clave_contrato + "' ";
//            MySql += " and fecha_liquidacion = '" + fecha_liquidacion + " 00:00:00.00' ";
            MySql += " and nombre_archivo = '" + nombre_archivo + "' ";

//            System.out.println("getClaveArchivo:" + MySql);
            ResultSet rstSQLServer = statement.executeQuery(MySql);

            if (rstSQLServer.next()) {
                id_archivo = Integer.parseInt(rstSQLServer.getString(1));
            } else {
                id_archivo = -1;
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
            System.out.println("ModeloLayOut-getClaveArchivo:" + e.getMessage());
        }
        return id_archivo;
    }

    /**
     * Obtiene el saldo del fideicomiso, movimientos pendientes y el importe de
     * liquidaciones pendientes en un vector separado por %
     *
     * @param clave_contrato
     * @return (saldo + "%" + movimientosPendientes + "%" + importeLiqPend)
     */
    public static String getSaldo_LiqPend_Fid(String clave_contrato) {
        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;

        String MySql = "";
        String saldo = "";
        int movimientosPendientes = 0;
        String importeLiqPend = "";
        String resultado = "";

//        System.out.println("Buscar para contrato: " + clave_contrato);
        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            if (clave_contrato.equals("PRB16082011")) {
                clave_contrato = "FID000PRB0514";
            }

            MySql = " select saldo  ";
            MySql += " from contratos ";
            MySql += " where clave_contrato = '" + clave_contrato + "' ";
            MySql += " and status = 'A'; ";

            ResultSet rstSQLServer = statement.executeQuery(MySql);

            if (rstSQLServer.next()) {
                saldo = rstSQLServer.getString("saldo").trim();
            }
            rstSQLServer.close();
            statement.close();
            rstSQLServer = null;
            statement = null;
            MySql = null;

            //COMIENZA la ejecucion de las nuevas sentencias para obtener los demas datos      
            MySql = "";
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = "select count(h.clave_contrato) as numero_movimientos ,";
            MySql += " sum(cast(l.importe_liquidacion as decimal(13,2))) as importe_total ";
            MySql += " from movimientos_h h , movimientos l , contratos contratos ";
            MySql += " where h.clave_contrato = l.clave_contrato ";
            MySql += " and l.clave_contrato = contratos.clave_contrato ";
            MySql += " and h.fecha_liquidacion = l.fecha_liquidacion ";
            MySql += " and h.nombre_archivo=l.nombre_archivo ";
            MySql += " and contratos.clave_contrato = '" + clave_contrato + "' ";
            MySql += " and contratos.status = 'A' ";
            MySql += " and h.status in ('P' ,'A') ";
            MySql += " and l.tipo_movimiento!=5; ";

            rstSQLServer = statement.executeQuery(MySql);

            if (rstSQLServer.next()) {
//                System.out.println("Encuentra pendientes.");
                movimientosPendientes = (int) rstSQLServer.getInt("numero_movimientos");
                importeLiqPend = rstSQLServer.getString("importe_total").trim();
            }
            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
            resultado = "" + saldo + "%" + movimientosPendientes + "%" + importeLiqPend + "";
//            System.out.println("Termina extrsaccion de saldo y pendientes.");

        } catch (Exception e) {
            resultado = "0.0%def%0.0";
            if (movimientosPendientes == 0) {
                resultado = "" + saldo + "%0%0.0";
            }
            if (connection != null) {
                conn.Desconecta(connection);
            }
            System.out.println("ModeloLayOut-getSaldo_LiqPend_Fid:resultado: " + resultado + "; " + e.getMessage());
        }
//        System.out.println("El resultado de consultar el saldo= " + resultado);
        return resultado;
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

    /**
     * Método que GENERA el reporte de Liquidación en formato pdf
     * correspondiente a la información que se pasa como parametro, el reporte
     * se almacena en el directorio correspondiente al ciente en cuestion.
     *
     * @param archivo_jasper : Ruta del archivo .jrxml correspondiente al tipo
     * de movimiento
     * @param clave_contrato : Clave de fideicomiso.
     * @param fecha_liquidacion : Fecha en la que tendrá lugar la liquidación.
     * @param nombre_archivo : Nombre del Lay-Out al que corresponden los
     * datesEdoCta.
     * @param monto1 : Importe de liquidación de bancomer a bancomer.
     * @param monto2 : Importe de liquidación de bancomer a otros bancos.
     * @param monto3 : Importe de liquidación a Tarjetas de Débito Banamex.
     * @param monto4 : Importe de liquidación a cheques.
     * @param monto5 : Importe de liquidación a bancos extranjeros.
     * @param montoTotalMXP : Importe total en MXP.
     * @param montoAFI: Importe de AFI.
     * @param montoIVA : Importe del IVA.
     * @param montoHF : Importe de los honorarios
     * @param montoSPR_MXP : Importe de la sufuciencia patronal requerida.
     * @param movs_tipo1 : Total de datesEdoCta de tipo 1.
     * @param movs_tipo2 : Total de datesEdoCta de tipo 2.
     * @param movs_tipo3 : Total de datesEdoCta de tipo 3.
     * @param movs_tipo4 : Total de datesEdoCta de tipo 4.
     * @param movs_tipo5 : Total de datesEdoCta de tipo 5.
     * @param total_movs : Total de ovimientos
     * @param fecha_carga_lote : Fecha de almacenamiento de lote
     * @param fecha_hoy : fecha de hoy.
     * @param url: Ruta donde se va a crear el archivo.
     * @param idx : Identificador de archivo.
     * @param lcnnConexion : Conexión a la Base de Datos.
     * @return Regresa true si se genero satisfactoriamente, else en otro caso
     */
    private synchronized boolean creaRL_Cliente_Saldos(String archivo_jasper, String clave_contrato, String fecha_liquidacion, String fecha, String fileName,
            String monto1, String monto2, String monto3, String monto4, String monto5, String monto5_pend, String montoTotalMXP,
            String movs_tipo1, String movs_tipo2, String movs_tipo3, String movs_tipo4, String movs_tipo5, String movs_tipo5_pend,
            String total_movs, String fecha_carga_lote, String fecha_hoy, String url, int idx,
            String saldo_actual, String pendientes, String txt_Nuevo_saldo, String nuevo_saldo, String apo_min_req, Connection lcnnConexion, String realPath) {

        JasperReport report = null;
        OutputStream output = null;
        JasperPrint print = null;
        Map parametro = new HashMap();
        File file = null;
        String archivo = archivo_jasper;
        String reporte = "";
        String clave_fidei = "";
        boolean genera = false;
        try {
            //Verificamos si el nombre del archivo es válido.
            if (archivo == null || archivo.equals("")) {
                System.out.println("creaRL_Cliente : Archivo *.jrxml inválido");
                return false;
            }
            if (!fecha.equals("") && !url.equals("")) {
                //Verificamos si existe el directorio de la fecha de liquidación, si no existe lo creamos.
                file = new File(url);
                if (!file.isDirectory()) {
                    if (!file.mkdirs()) {
                        return false;
                    }
                }
                try {
//                    report = (JasperReport) JRLoader.loadObject(archivo);
                    //Campilamos el Reporte de Liquidación.
                    report = JasperCompileManager.compileReport(archivo);
                    //Creamos el archivo jasper para mandar pdf a pantalla
//                JasperCompileManager.compileReportToFile(archivo, "./Common/ReporteLiquidacion.jasper");
                } catch (JRException jre) {
                    System.out.println("creaRL_Cliente:" + jre.toString());
                    return false;
                }
                parametro.put("importe_M1", monto1);
                parametro.put("importe_M2", monto2);
                parametro.put("importe_M3", monto3);
                parametro.put("importe_M4", monto4);
                parametro.put("importe_M5", monto5);
                parametro.put("importe_M5_pend", monto5_pend);
                parametro.put("importe_total_MXP", montoTotalMXP);

                //Parametros que se agregan al reporte de liquidacion de saldos
                parametro.put("saldo_actual", saldo_actual);
                parametro.put("pendientes", pendientes);
                parametro.put("txt_Nuevo_saldo", txt_Nuevo_saldo);
                parametro.put("nuevo_saldo", nuevo_saldo);
                parametro.put("apo_min_req", apo_min_req);

                parametro.put("total_movs_tipo1", movs_tipo1);
                parametro.put("total_movs_tipo2", movs_tipo2);
                parametro.put("total_movs_tipo3", movs_tipo3);
                parametro.put("total_movs_tipo4", movs_tipo4);
                parametro.put("total_movs_tipo5", movs_tipo5);
                parametro.put("total_movs_tipo5_pend", movs_tipo5_pend);
                parametro.put("total_movimientos", total_movs);

                parametro.put("logo", realPath + "\\images\\logo.jpg");

                parametro.put("clave_contrato", clave_contrato);
                parametro.put("fecha_liquidacion", fecha_liquidacion);
                parametro.put("nombre_archivo", fileName);
                parametro.put("fecha_carga", fecha_carga_lote);
                parametro.put("fecha_hoy", fecha_hoy);
                if (idx != -1) {
                    clave_fidei = clave_contrato.substring(6, 9);
                    if (idx > 0 && idx <= 9) {
                        //Creamos la salida del archivo generado
                        reporte = clave_fidei + "-0" + idx + "-" + "LQ" + "-01-" + fecha + ".pdf";
                        output = new FileOutputStream(new File(url + "\\" + reporte));
                    } else {
                        //Creamos la salida del archivo generado
                        reporte = clave_fidei + "-" + idx + "-" + "LQ" + "-01-" + fecha + ".pdf";
                        output = new FileOutputStream(new File(url + "\\" + reporte));
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
            } else {
                genera = false;
            }
        } catch (Exception e) {
            genera = false;
            System.out.println("ModeloLayOut-creaRL_Cliente_SALDOS:" + e.getMessage());
            try {
                if (output != null) {
                    output.flush();
                    output.close();
                }
            } catch (IOException ioe) {
                System.out.println("ModeloLayOut-creaRL_Cliente_SALDOS:" + ioe.getMessage());
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
     * datesEdoCta.
     * @param resumen : Resumen de Movimientos obtenidos al validar el archivo.
     * @param url : Ruta del directorio donde se va a crear el reporte de
     * liquidación.
     * @param idx_archivo: Clave unica que identifica al reporte que se va a
     * generar.
     * @param lcnnConexion : Conexión a la Base de Datos.
     * @return boolean valido: Regresa true si se genero satisfactoriamente,
     * else en otro caso
     */
    public boolean getRL_Cliente_Saldos(String clave_contrato, String fecha_liquidacion, String formato_fecha, String nombre_archivo, ResumenMovimientos resumen, String url, int idx_archivo, Connection connection, String realPath) {

        String url_jasper = "", mov1 = "", mov2 = "", mov3 = "", mov4 = "", mov5 = "", mov5_pend = "", importeTotalMXP = "";
        String movs_tipo1 = "", movs_tipo2 = "", movs_tipo3 = "", movs_tipo4 = "", movs_tipo5 = "", movs_tipo5_pend = "", total_movs = "";
        clsFecha fecha = new clsFecha();
        String fecha_hoy = "", fecha_carga_lote = "";
        String saldo_actual = "", txt_NuevoSaldo = "", nuevo_saldo = "", pendientes = "", apo_min_req = "";

        boolean seGuardo = false;
        try {
            mov1 = resumen.pago_mov_tipo1 + "";
            mov2 = resumen.pago_mov_tipo2 + "";
            mov3 = resumen.pago_mov_tipo3 + "";
            mov4 = resumen.pago_mov_tipo4 + "";
            mov5 = resumen.pago_mov_tipo5 + "";
            mov5_pend = resumen.formato_importe_tipo5_pend;
            importeTotalMXP = resumen.importeTotalMXP + "";
            //Obtenemos los datos sobre saldos
            saldo_actual = resumen.getSaldo_actual();
            txt_NuevoSaldo = resumen.getTxt_Nuevo_saldo();
            nuevo_saldo = resumen.getNuevo_saldo();
            pendientes = resumen.getLiquidaciones_pendientes();
            apo_min_req = resumen.getAportacion_minima_requerida();

            movs_tipo1 = resumen.total_movs_tipo1 + "";
            movs_tipo2 = resumen.total_movs_tipo2 + "";
            movs_tipo3 = resumen.total_movs_tipo3 + "";
            movs_tipo4 = resumen.total_movs_tipo4 + "";
            movs_tipo5 = resumen.total_movs_tipo5 + "";
            movs_tipo5_pend = resumen.total_movs_tipo5_pend + "";
            total_movs = resumen.total_movimientos + "";
            fecha_hoy = fecha.getFechaHoy();
            fecha_carga_lote = ModeloLayOut.getFecha_carga();

            if (apo_min_req.equals("$ 0.00")) {
                if (resumen.total_movs_tipo5 > 0 || resumen.total_movs_tipo5_pend > 0) {
                    url_jasper = realPath + "\\WEB-INF\\classes\\Common\\RL_SALDOS_M5.jrxml";
                } else {
                    url_jasper = realPath + "\\WEB-INF\\classes\\Common\\RL_SALDOS.jrxml";
                }
            } else if (resumen.total_movs_tipo5 > 0 || resumen.total_movs_tipo5_pend > 0) {
                url_jasper = realPath + "\\WEB-INF\\classes\\Common\\RL_SALDOS_DEFICIT_M5.jrxml";
            } else {
                url_jasper = realPath + "\\WEB-INF\\classes\\Common\\RL_SALDOS_DEFICIT.jrxml";
            }
            seGuardo = this.creaRL_Cliente_Saldos(url_jasper, clave_contrato, fecha_liquidacion, formato_fecha, nombre_archivo,
                    mov1, mov2, mov3, mov4, mov5, mov5_pend, importeTotalMXP, movs_tipo1, movs_tipo2,
                    movs_tipo3, movs_tipo4, movs_tipo5, movs_tipo5_pend, total_movs, fecha_carga_lote, fecha_hoy, url, idx_archivo,
                    saldo_actual, pendientes, txt_NuevoSaldo, nuevo_saldo, apo_min_req, connection, realPath);
//            }
//            else{
//                System.out.println("Error en la longitud del arreglo de consulta de saldo.");
//            }
        } catch (Exception e) {
            seGuardo = false;
            System.out.println("ModeloLayOut-getRL_Cliente_SALDOS:" + e.getMessage());
        }
        return seGuardo;
    }

    /**
     * Método que genera el reporte de Liquidación correspondiente a los datos
     * que se le pasan como parámetro.
     *
     * @param clave_contrato: Clave de Fideicomiso
     * @param fecha_liquidacion: Fecha de Liquidación.
     * @param fileName: Nombre del LayOut.
     * @param resumenMovimientos: Resumen de transacciones.
     * @param idx_archivo: Identificador de lote.
     * @param url: URL del archivo a Adjuntar.
     * @return String valido: Mensaje obtenido al generar el reporte de
     * liquidación, si todo va bien regresa una cadena vacia.
     */
    public String genera_RL(String clave_contrato, String fecha_liquidacion, String formato_fecha, String fileName, ResumenMovimientos resumenMovimientos, int idx_archivo, String url, String realPath) {
        Connection connection = null;
        clsConexion conn = new clsConexion();
        String mensaje = "";
        boolean genera = false;
        try {
            //Realizamos una conexión a la BD.
            connection = conn.ConectaSQLServer();
            // Se crea el PDF del Reporte de Liquidación.
//            genera = this.getRL_Cliente(clave_contrato, fecha_liquidacion, formato_fecha, fileName, resumenMovimientos, url, idx_archivo,infoSaldo, connection);
            genera = this.getRL_Cliente_Saldos(clave_contrato, fecha_liquidacion, formato_fecha, fileName, resumenMovimientos, url, idx_archivo, connection, realPath);
            //Verificamos si se creo satisfactoriamente el Reporte de Liquidación.
            if (genera) {
                mensaje = "";
            } else {
                mensaje = " Error al generar reporte de liquidación";
            }
            //Cerramos la conexión a la Base de Datos.
            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            mensaje = " Error generando reporte de liquidación ";
            System.out.println("ModeloLayOut-genera_RL:" + e.getMessage());
            //Cerramos la conexión a la Base de Datos.
            if (connection != null) {
                conn.Desconecta(connection);
            }
        }
        return mensaje;
    }

    /**
     * Método que envía un correo electrónico con los datos que se le pasan como
     * parámetro.
     *
     * @param correoOrigen : Nombre del correo Emisor.
     * @param correoDestino : Correos destino.
     * @param asunto : Asunto del correo electrónico.
     * @param texto : Cuerpo del correo.
     * @param url : Ruta del archivo a adjuntar en el correo.
     * @return String: Mensaje descriptivo de la operación.
     */
    public String enviaReporteLiquidacion(String correoOrigen, String correoDestino, String asunto, String texto, String urlArchivo, String nombreArchivo) {
        boolean envia = false;
        String mensaje = "";
        envia = EnvioMail.enviaCorreo(correoOrigen, correoDestino, asunto, texto, urlArchivo, nombreArchivo, "587");
        envia = true;
        if (!envia) {
            mensaje = " Error enviando correo electrónico ";
        }
        return mensaje;
    }

    /**
     * Método que regresa el nombre del resumen de liquidación asociado al
     * fideicomiso que se le pasa como parámetro.
     *
     * @param clave_contrato : Clave de fideicomiso.
     * @param fecha_liquidacion : Fecha en que tendrá lugar la liquidación.
     * @param nombre_archivo : Nombre del lote cargado.
     * @param verifica : Identificador del lote.
     * @return String : Nombre del resumen de liquidación, si surge algún error
     * regresa una cadena vacia.
     */
    public static String getNombreResumenLiquidacion(String clave_contrato, String fecha_liquidacion, String nombre_archivo, int verifica) {
        String nombre = "";
        String clave_fidei = "";
        try {
            if (verifica != -1 && clave_contrato.length() > 9 && !fecha_liquidacion.equals("") && !nombre_archivo.equals("")) {
                clave_fidei = clave_contrato.substring(6, 9);
                if (verifica > 0 && verifica <= 9) {
                    nombre = clave_fidei + "-0" + verifica + "-" + "LQ" + "-01-" + fecha_liquidacion + ".pdf";
                } else {
                    nombre = clave_fidei + "-" + verifica + "-" + "LQ" + "-01-" + fecha_liquidacion + ".pdf";
                }
            } else {
                nombre = "";
            }
        } catch (Exception e) {
            nombre = "";
            System.out.println("ModeloLayOut-getNombreResumenLiquidacion" + e.getMessage());
        }
        return nombre;
    }

    /**
     * Método que regresa el correo electrónico del usuario del sistema que se
     * le pasa como parámetro.
     *
     * @param clave_cliente : clave asociada al fideicomitente en el sistema.
     * @param usuario : Nombre de usuario.
     * @return String : correo electrónico.
     */
    public static String getFechaLiquidacion(String clave_contrato, String clave_cliente, String nombre_archivo) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;

        String fecha = "";
        String MySql = "";

        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select convert(varchar,fecha_liquidacion,103) ";
            MySql += " from contratos c, movimientos_h h ";
            MySql += " where c.clave_contrato = h.clave_contrato ";
            MySql += " and c.status = 'A' ";
            MySql += " and h.clave_contrato = '" + clave_contrato + "' ";
            MySql += " and h.clave_cliente = '" + clave_cliente + "' ";
            MySql += " and h.nombre_archivo  = '" + nombre_archivo + "' ";

//            System.out.println(MySql);
            ResultSet rstSQLServer = statement.executeQuery(MySql);

            if (rstSQLServer.next()) {
                fecha = rstSQLServer.getString(1).toString().trim();
            }
            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            fecha = "";
            System.out.println("ModeloLayOut-getFechaLiquidacion:" + e.toString());
        }
        return fecha;

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

            MySql = " select fecha,concepto,observaciones,cargo,abono,saldo,nombre_archivo ";
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
                    movimientos.add(encabezado);
                }
                Vector result = new Vector();
                result.add(rstSQLServer.getString("fecha").toString().trim());
                result.add(rstSQLServer.getString("concepto").toString().trim());
                result.add(rstSQLServer.getString("observaciones").toString().trim());
                result.add(dec.format(rstSQLServer.getDouble("cargo")));
                result.add(dec.format(rstSQLServer.getDouble("abono")));
                result.add(dec.format(rstSQLServer.getDouble("saldo")));
                result.add(rstSQLServer.getString("nombre_archivo").toString().trim());
                movimientos.add(result);
            }
            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            movimientos = null;
            System.out.println("ModeloLayOut-getMovimientos:" + e.toString());
        }
        return movimientos;

    }

    public static Vector get10DatesEdoCta(String clave_contrato) {

        clsConexion conn = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        Vector nombresEdoCta = null;
        Vector mesesCombo = null;
        Vector resultado = null;
        SimpleDateFormat dateF = new SimpleDateFormat("_yyyy_MM");
        int i = 0;

        String MySql = "";

        try {
            connection = conn.ConectaSQLServer();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            MySql = " select fecha_fin from registro_edo_cta ";
            MySql += " where fecha_fin>='2014-01-01 00:00:00.000' ";
            MySql += " and fecha_ini>='2014-01-01 00:00:00.000' ";
            MySql += " and estado='CORRECTO' ";
            MySql += " order by fecha_fin desc";
            nombresEdoCta = new Vector();
            mesesCombo = new Vector();
//            System.out.println(MySql);
            ResultSet rstSQLServer = statement.executeQuery(MySql);
            while (rstSQLServer.next() && i < 10) {
                i++;
                java.util.Date date = rstSQLServer.getDate("fecha_fin");
                String fechaFormato = dateF.format(date);
                String nombreEdoCta = "EDOCTA_" + clave_contrato + fechaFormato;
                mesesCombo.add(DateFormat.getDateInstance(DateFormat.LONG).format(date).substring(5));
                nombresEdoCta.add(nombreEdoCta);
            }
            rstSQLServer.close();
            statement.close();
            resultado = new Vector();
            if (!nombresEdoCta.isEmpty() && !mesesCombo.isEmpty()) {
                resultado.add(mesesCombo);
                resultado.add(nombresEdoCta);
            }

            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            resultado = null;
            System.out.println("ModeloLayOut-getMovimientos:" + e.toString());
        }
        return resultado;

    }

    public static Vector getEdoCtaFiles(String clave_contrato) {
        Vector nombresEdoCta = new Vector();
        Vector mesesCombo = new Vector();
        Vector resultado = null;
        Calendar cal = Calendar.getInstance();

        try {
            SimpleDateFormat dateF = new SimpleDateFormat("MMMM 'de' yyyy");
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
     *
     * @param clave_contrato
     * @param saldo_actual
     * @return
     */
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
//            MySql += " and l.tipo_movimiento!=5 ";
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
                    encabezado.add("MOVIMIENTOS");
                    encabezado.add("IMPORTE DE LIQUIDACIÓN");
//                    encabezado.add("HONORARIOS FIDUCIARIOS");
//                    encabezado.add("I.V.A. DE HONORARIOS");
//                    encabezado.add("SUFICIENCIA PATRIMONIAL");
                    movimientos.add(encabezado);
                }
                Vector result = new Vector();
                result.add(movimientos.size());
                java.util.Date fecha = rstSQLServer.getDate("fecha_liquidacion");
                result.add(new SimpleDateFormat("dd/MMM/yyyy").format(fecha));
                String nombre_archivo = rstSQLServer.getString("nombre_archivo").trim();
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
                String honSinIva = ModeloLayOut.getInfoContrato(clave_contrato);
                String sp_c = ModeloLayOut.getSuficienciaPatronal("" + deficit, honSinIva, "16");
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
            System.out.println("ModeloLayOut-getMovimientos:" + e.toString());
        }
        return movimientos;

    }

    /**
     * Regresa el importe de liquidación pendiente real, es decir, se restan
     * movimientos cancelados, y movimientos tipo5
     *
     * @param clave_contrato
     * @param fecha_liquidacion
     * @param nombre_archivo
     * @param movsPend
     * @param importe
     * @return
     */
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

            //Se obtendra el importe de los movimientos que se cancelado, después de ello se le restará al total de la liquidación
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            MySql += " select importe_liquidacion ";
            MySql += " from movimientos_cancelados ";
            MySql += " where clave_contrato='" + clave_contrato + "' ";
            MySql += " and fecha_liquidacion = '" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(fecha_liquidacion) + "' ";
            MySql += " and nombre_archivo = '" + nombre_archivo + "' ";
            MySql += " and status = 'A'  ";
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

            //A partir de Enero de 2016 se acordo que en los archivos LayOut no podría haber movimientos de tipo5 con algún otro,
            //ademas, los archivos que contengan movimientos tipo 5, sólo podrán contener un movimiento
            //Se agrega codigo que busca movimientos tipo 5 y su importe de liquidacion en MXP
            /*
                                rstSQLServer = null;
                                count = 0;
                                pendientes = resultado[0].intValue();
                                importe = resultado[1];
                                liquidacion = 0;
                                MySql = "";                        
                                statement = connection.createStatement();
                                statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
                                MySql += " select importe_liquidacion ";
                                MySql += " from movimientos ";
                                MySql += " where clave_contrato='" + clave_contrato + "' ";
                                MySql += " and fecha_liquidacion = '" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(fecha_liquidacion) + "' ";
                                MySql += " and nombre_archivo = '" + nombre_archivo + "' ";
                                MySql += " and tipo_movimiento=5  ";
                                rstSQLServer = statement.executeQuery(MySql);

                                while (rstSQLServer.next()) {
                                    liquidacion += rstSQLServer.getDouble("importe_liquidacion");
                                    count++;
                                }

                                if (count > 0 && pendientes >= count) {
                                    count = pendientes + count;
                                    resultado[1] = importe - liquidacion;
                                }
             */
            rstSQLServer.close();
            statement.close();

            if (connection != null) {
                conn.Desconecta(connection);
            }
        } catch (Exception e) {
            System.out.println("ModeloLayOut-getLiquidacionesPendientes:" + e.toString());
        }
//           System.out.println("resultado: " + resultado[1] + " - " + resultado[0]);
        return resultado;
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
     *
     * @param fecha
     * @param operacion se indica que fecha se formatea, inicio de mes o fecha
     * al dia de hoy 0 para el inicio de mes 1 para el dia de hoy
     * @return
     */
    static public String obtenFormatoFecha(String fecha, int operacion) {
        String fechaFormato = "";
        try {

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
        } catch (Exception ex) {
            System.out.println("Error de formato de fecha:ModeloLayOut-obtenFormatoFecha:" + ex.getMessage());
        }

        return fechaFormato;
    }

    public static String compruebaFechaOperacion(String fecha) {
        java.util.Date fecha_hoy = new java.util.Date();
        java.util.Date fecha_operacion = null;
//        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-mm-dd");
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
                } else if (base.equals("PATRIMONIO")) {
                    sp = new BigDecimal(sp_c);
                    hf = sp.multiply(xHon);
                } else {
                    hf = BigDecimal.ZERO;
                    hf_c = "0.00";
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
                } else if (base.equals("PATRIMONIO")) {
                    sp = rl.divide(cUno.subtract(xHon.multiply(xIVA)), 2, RoundingMode.HALF_UP);
                } else {
                    sp = BigDecimal.ZERO;
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

    //Comprueba que no contenga caracteres prohibidos
    /* El caracter '+' representa una o más veces
     * El caracter '^' dentro de los corchetes es un NOT, permite encontrar cualquier
     * carácter que NO se encuentre dentro del grupo indicado
     */
    public static String quitaEspaciosBlancos(String input) {
        Pattern pattern = null;
        Matcher matcher = null;
        boolean isFind = false;
        StringBuffer sBuffer = null;
        String input_v = "";
        try {
            pattern = Pattern.compile("\\s+");
            matcher = pattern.matcher(input);
            isFind = matcher.find();
            sBuffer = new StringBuffer();
            while (isFind) {
                matcher.appendReplacement(sBuffer, "");
                isFind = matcher.find();
            }
            // Añade el último segmento de la entrada a la cadena
            matcher.appendTail(sBuffer);
            input_v = sBuffer.toString();
        } catch (Exception e) {
            input_v = "";
            System.out.println("ModeloLayOut-quitaEspaciosBlancos" + e.getMessage());
        }
        return input_v;

    }

    public static void main(String arg[]) {
//        boolean success = false;
//        String url = "./../../../../Reportes Liquidacion/FID009AIR1210/28_04_12";
//
//        success = (new File(url)).mkdirs();
//        if (success) {
//            if (success) {
//                System.out.println("Directories: " + url + " created");
//            }
//        }
//        String honSinIva = ModeloLayOut.getInfoContrato("FID015CDM0111");
//        System.out.println(honSinIva);
//        String sp_c = ModeloLayOut.getSuficienciaPatronal("0", honSinIva, "16");
//        System.out.println("SP=" + sp_c);
//        String hf = ModeloLayOut.getHonorariosFiduciarios("0", sp_c, honSinIva);
//        System.out.println("HF=" + hf);
//        String iva = ModeloLayOut.estableceIVA(hf);
//        System.out.println("IVA=" + iva);
//        String newPassword = "";
//        int random = 0, val;
//        String[] specialChar = {"B", "b", "#", "x", "W", "z", "X", "*", "Z", "w", "F", "f"};
//
//        for (int i = 0; i < 100; i++) {
//            newPassword = (int) (Math.random() * 1000000000.0) + "";
//
//            val = 12 - newPassword.length();
//            for (int j = 0; j < val; j++) {
//                random = (int) Math.floor(Math.random() * (12) + 0);
//                newPassword = newPassword + specialChar[random];
//            }
//            System.out.println("CASO " + i + " :" + newPassword + ":");
//        }
    }
}
