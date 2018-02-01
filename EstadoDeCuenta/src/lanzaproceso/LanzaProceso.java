package lanzaproceso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import modelo.GeneraEdoCta;

public class LanzaProceso {

    static SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm.ss.SSS");
    private static boolean dia_corte = false;
    private static boolean pendiente_dia_corte = false;
    private static int noMesManual = 0;
    public static boolean corteLast = false;

    public static void main(String[] args) {
        if (args != null && args.length == 1) {
            //Aplicacion ejecutada con un solo argumento(todavía es día de corte o antes de que lo sea)
            if (args[0].equals("now")) {
                System.out.println("Ejecutando el proceso ..........");
                dia_corte = true;
                pendiente_dia_corte = true;
            } else {
                if (args[0].equals("last")) {                    
                    System.out.println("Ejecutando el proceso para estados de cuenta del mes anterior");
                    corteLast = true;
                    dia_corte = true;
                    pendiente_dia_corte = true;
                } else {
                    try {
                        int temporal = Integer.parseInt(args[0]);
                        if (temporal > 0 && temporal <= 12) {
                            noMesManual = temporal;
                            System.out.println("Ejecutando el proceso para el mes:" + noMesManual);
                            dia_corte = true;
                            pendiente_dia_corte = true;
                        } else {
                            System.out.println("Número de mes:" + temporal + ", fuera de rango");
                        }
                    } catch (Exception ex) {
                        System.out.println("El argumento no cumple las especificaciones");
                    }
                }
            }
        }
        while (true) {

            try {
                long mili_dormir = dormirNumHrs();
                System.out.println("A punto de dormir: " + mili_dormir);
                Thread.sleep(mili_dormir);
            } catch (InterruptedException e) {
            }
        }
    }

    static long dormirNumHrs() {
//        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-dd-MM");
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();

        Date ultimoDiaHabilMes = getUltimoDiaHabilDeMes();
        if (pendiente_dia_corte) {
            System.out.println(" ------- Ejecutar proceso de generación de estado de cuenta  ---------");
            boolean edo_cta_correcto = false;
            edo_cta_correcto = GeneraEdoCta.init();

            if (edo_cta_correcto) {
                System.out.println("{{{{{{{{{{{{{{{{{{El proceso ha terminado correctamente}}}}}}}}}}}}}}}}}}");
                cal = Calendar.getInstance();
                System.out.println("Hora: " + cal.getTime().toString());
                dia_corte = false;
                corteLast = false;
                pendiente_dia_corte = false;
                return mili_sig_dia(cal);
            } else {
                System.out.println("{{{{{{{{{{{{{{{{{{El proceso ha terminado CON_ERRORES}}}}}}}}}}}}}}}}}}");
                System.out.println("{{{{{{{{{{{{{{{{{{El proceso se re-programa a 5 minutos}}}}}}}}}}}}}}}}}}");
                return 1000 * 60 * 5;//Vuelve a intentarlo en 5 minutos
            }

        }
        if (!dia_corte) {
            if (formatoFecha.format(cal.getTime()).equals(formatoFecha.format(ultimoDiaHabilMes))) {
//            if(cal.getTime().before(ultimoDiaHabilMes)){
                dia_corte = true;
                pendiente_dia_corte = true;
                return mili_ejecucion();
//            }
//            else{
//                System.out.println("La hora programada para la generación de estado de cuenta, ya sucedió.");
//            }
            }
        }
        return mili_sig_dia(cal);
    }

    public static Date getUltimoDiaHabilDeMes() {
        Calendar calMax = Calendar.getInstance();
        calMax.set(calMax.get(Calendar.YEAR),
                calMax.get(Calendar.MONTH),
                calMax.getActualMaximum(Calendar.DAY_OF_MONTH),
                16,
                0,
                0);
//        calMax.getMaximum(Calendar.HOUR_OF_DAY),
//        calMax.getMaximum(Calendar.MINUTE),
//        calMax.getMaximum(Calendar.SECOND));

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

//         calMax.set(2014, 6, 14, 16,14);
        System.out.println("La fecha de corte al mes= " + formatoFecha.format(calMax.getTime()));

        return calMax.getTime();
    }

    static long mili_sig_dia(Calendar cal) {
        long mili_ahora = cal.getTimeInMillis();
        System.out.println("Hora actual: " + cal.getTime().toString());
        cal.add(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 2);
        System.out.println("Hora de la proxima ejecucion: " + cal.getTime().toString());
        long mili_inicio_daemon = cal.getTimeInMillis();
        long mili_restantes = mili_inicio_daemon - mili_ahora;
//        System.out.println("Milisegundos restantes para comenzar: " + mili_restantes);
//        System.out.println("Segundos restantes: " + mili_restantes/1000.0);
//        System.out.println("Minutos restantes: " + mili_restantes/(1000.0*60.0));
        System.out.println("Horas restantes: " + mili_restantes / (1000.0 * 60.0 * 60.0));

        return mili_restantes;

    }

    static long mili_ejecucion() {
        Calendar cal = Calendar.getInstance();
        long mili_ahora = cal.getTimeInMillis();
        System.out.println("Hora actual: " + cal.getTime().toString());
        //Hora exacta para la ejecución del proceso para generar el LsyOut de EDO.CTA.
        cal.set(Calendar.HOUR_OF_DAY, 16);
        cal.set(Calendar.MINUTE, 2);
        System.out.println("Hora programada para generación de estado de cuenta: " + cal.getTime().toString());
        long mili_inicio_daemon = cal.getTimeInMillis();
        long mili_restantes = mili_inicio_daemon - mili_ahora;
//        System.out.println("Milisegundos restantes para comenzar: " + mili_restantes);
//        System.out.println("Segundos restantes: " + mili_restantes/1000.0);
        System.out.println("Minutos restantes: " + mili_restantes / (1000.0 * 60.0));
        System.out.println("Horas restantes: " + mili_restantes / (1000.0 * 60.0 * 60.0));

        return mili_restantes;
    }
}
