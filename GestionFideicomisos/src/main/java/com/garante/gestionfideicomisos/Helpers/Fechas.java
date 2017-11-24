package com.garante.gestionfideicomisos.Helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Fechas {
    
    /**
    * Compara que fecha es mayor, menor o si son iguales
    * @param fecha1 Date a comparar
    * @param fechaActual Date dia de hoy
    * @return 1 si es menor a fecha actual, 2 si es mayor a fecha actual, 3 si las fechas son exactamente iguales
    */
    public static String compararFechasConDate(Date fecha1, Date fechaActual) {
        String resultado;
        if (fecha1.before(fechaActual)) {
            //La Fecha 1 es menor
            resultado = "1";
        } else if (fecha1.after(fechaActual)) {
            //La Fecha 1 es Mayor
            resultado = "2";
        } else {
            //Las Fechas Son iguales
            resultado = "3";
        }
        return resultado;
    }
    
    public static boolean esHoy(Date fecha) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
        String fechaInt = dateFormat.format(fecha);
        String fechaHoyInt = dateFormat.format(new Date());
        boolean res = false;
        if (fechaInt.equals(fechaHoyInt)) {
            res = true;
        }
        return res;
    }
    
    public static Date creaFecha(int anio, int mes, int dia) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(anio, mes, dia);
        return cal.getTime();
    }
    
    public static Date creaFecha(int anio, int mes, int dia, int hora, int minutos, int segundos) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(anio, mes, dia, hora, minutos, segundos);
        return cal.getTime();
    }
    
    public static Date getPrimerDiaDeMes(Date fecha) {
        Calendar calMin = Calendar.getInstance();
        calMin.setTime(fecha);
        calMin.set(calMin.get(Calendar.YEAR),
                calMin.get(Calendar.MONTH),
                1,//mes
                0,//hora
                0,//minuto
                0);//segundo
        return calMin.getTime();
    }
    
    public static Date getUltimoDiaDeMes(Date fecha) {
        Calendar calMax = Calendar.getInstance();
        calMax.setTime(fecha);
        calMax.set(calMax.get(Calendar.YEAR),
                calMax.get(Calendar.MONTH),
                calMax.getActualMaximum(Calendar.DAY_OF_MONTH),
                calMax.getMaximum(Calendar.HOUR_OF_DAY),
                calMax.getMaximum(Calendar.MINUTE),
                calMax.getMaximum(Calendar.SECOND));
        return calMax.getTime();
    }
    
    /**
    * Parsea una fecha a formato String
    * @param fecha Date not null
    * @return Fecha en formato YYYY-MM-dd HH:mm:ss
    */
    public static String creaFechaString(Date fecha) {
        SimpleDateFormat sDF = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        return sDF.format(fecha);
    }
    
    /**
    * Parsea una fecha String a formato Date
    * @param fecha Date not null
    * @return Fecha en formato YYYY-MM-dd HH:mm:ss
    */
    public static Date creaFechaDate(String fecha) throws ParseException {
        SimpleDateFormat sDF = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        return sDF.parse(fecha);
    }
    
}
