package mx.org.gp.nomina12;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import mx.org.gp.nomina12.model.ModeloLayOut;
import mx.org.gp.nomina12.model.MovsMesResum;
import mx.org.gp.nomina12.model.ObtenInfoBD;
import mx.org.gp.nomina12.model.TrabajarParte;

public class Main {

    public static void main(String[] args) {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatoFechaComp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        List<MovsMesResum> movimientosMensuales;
        ObtenInfoBD obtenInforBD = new ObtenInfoBD();
        Calendar c = Calendar.getInstance();
        Date ultimoDiaMes;
        Date primerDiaMes;
        Date fechaHilo = new Date();
        Integer diasDelMes;
        String errores = "";
        //creamos instancia de gregorianClendar para inicializar un xmlCalendar
        GregorianCalendar gcUltimoDiaMes = new GregorianCalendar();
        c.setTime(new Date());
        if (args != null) {
            if (args.length >= 1) {
                try {
                    Date date0 = formatoFecha.parse(args[0]);
                    c.setTime(date0);
                    fechaHilo = date0;
                    if (args.length > 1) {
                        //Se ingresa el rfc
                        errores = args[1];
                    }
                } catch (Exception e) {
                    System.out.println("Error en la fecha de entrada ...");
                }
            }
        }
        //seteamos a la instancia de Calendar la fecha del ultimo dia de mes
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        ultimoDiaMes = c.getTime();
        //seteamos al Calendar el primer dia del mes
        c.set(Calendar.DAY_OF_MONTH, 1);
        primerDiaMes = c.getTime();
        diasDelMes = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        gcUltimoDiaMes.setTime(ultimoDiaMes);
        //CONCEPTOS
        if (errores.isEmpty()) {
            int count = 0;
            long time_start, time_end;
            final Integer MOVS_MAX_PROCESA = 2500;
            time_start = System.currentTimeMillis();
            movimientosMensuales = obtenInforBD.obtenGrupoMovsPorRFC(primerDiaMes, ultimoDiaMes);
            System.out.println("Tiempo =" + (System.currentTimeMillis() - time_start) + " milisegundos ");
            Double t = Double.parseDouble("" + movimientosMensuales.size());
            t = Math.ceil(t / MOVS_MAX_PROCESA.doubleValue());
            Integer hilos = t.intValue();
            TrabajarParte tp = new TrabajarParte(movimientosMensuales,null, MOVS_MAX_PROCESA, fechaHilo);
            System.out.println("Hora de inicio del proceso" + new Date());
            for (int i = 1; i <= hilos; i++) {
                new Thread(tp, "" + i).start();
            }
        } else {
            try {
                List<List> rfc_errores = ModeloLayOut.leerErroresCODES();
                List<String> rfc_errores1 = rfc_errores.get(0);
                List<String> rfc_errores2= rfc_errores.get(1);
//                Set<String> rfc_errores = ModeloLayOut.leerErroresCODES_CURP();
                //Se obtiene la columna de errores de CURP y se obtiene la CURP de este campo de error
//                ModeloLayOut.obtieneCURPErroresCODES();
                movimientosMensuales = new ArrayList<>();
                int cont = 0;
                for (String rfcE : rfc_errores1) {
                    MovsMesResum mov = obtenInforBD.obtenGrupoMovsPorRFC(primerDiaMes, ultimoDiaMes, rfcE);
                    if (mov != null) {
//                        mov.setRfc(rfc_errores2.get(cont));
                        movimientosMensuales.add(mov);
                    }
                }
                final Integer MOVS_MAX_PROCESA = 2500;
                Double t = Double.parseDouble("" + movimientosMensuales.size());
                t = Math.ceil(t / MOVS_MAX_PROCESA.doubleValue());
                Integer hilos = t.intValue();
                TrabajarParte tp = new TrabajarParte(movimientosMensuales,rfc_errores2, MOVS_MAX_PROCESA, fechaHilo);
                System.out.println("Hora de inicio del proceso" + new Date());
                for (int i = 1; i <= hilos; i++) {
                    new Thread(tp, "" + i).start();
                }
            } catch (IOException ex) {
                System.out.println("Exception leerErroresCodes:" + ex.getMessage());
            }
        }
    }

}
