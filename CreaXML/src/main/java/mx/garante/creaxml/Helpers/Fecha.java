package mx.garante.creaxml.Helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Fecha {
    
    public static Calendar getPrimerDiaDeMes(Date fecha) {
        Calendar calMin = Calendar.getInstance();
        calMin.setTime(fecha);
        calMin.set(Calendar.DAY_OF_MONTH, 1);
        calMin.set(Calendar.HOUR_OF_DAY, 0);
        calMin.set(Calendar.MINUTE, 0);
        calMin.set(Calendar.SECOND, 0);
        return calMin;
    }
    
    public static Calendar getUltimoDiaDeMes(Date fecha) {
        Calendar calMax = Calendar.getInstance();
        calMax.setTime(fecha);
        calMax.set(Calendar.DAY_OF_MONTH, calMax.getActualMaximum(Calendar.DAY_OF_MONTH));
        calMax.set(Calendar.HOUR_OF_DAY, 16);
        calMax.set(Calendar.MINUTE, 00);
        calMax.set(Calendar.SECOND, 00);
        return calMax;
    }
    
    public static Date creaDate(String fecha) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM");
        Calendar c1 = Calendar.getInstance();
        try {
            //c1.setTimeInMillis(0);
            c1.setTime(formato.parse(fecha));
            c1.set(Calendar.DAY_OF_MONTH, Calendar.DAY_OF_MONTH);
            c1.set(Calendar.HOUR_OF_DAY, Calendar.HOUR_OF_DAY);
            c1.set(Calendar.MINUTE, Calendar.MINUTE);
            c1.set(Calendar.SECOND, Calendar.SECOND);
        } catch (ParseException ex) {
            System.out.println("Error formato de fecha");
        }
        return c1.getTime();
    }
        
    public static String nombreMes(String mes){
        String nomMes = "";
        switch(mes){
            case "01": nomMes = "Enero"; break;
            case "02": nomMes = "Febrero"; break;
            case "03": nomMes = "Marzo"; break;
            case "04": nomMes = "Abril"; break;
            case "05": nomMes = "Mayo"; break;
            case "06": nomMes = "Junio"; break;
            case "07": nomMes = "Julio"; break;
            case "08": nomMes = "Agosto"; break;
            case "09": nomMes = "Septiembre"; break;
            case "10": nomMes = "Octubre"; break;
            case "11": nomMes = "Noviembre"; break;
            case "12": nomMes = "Diciembre"; break;
        }
        return nomMes;
    }
}