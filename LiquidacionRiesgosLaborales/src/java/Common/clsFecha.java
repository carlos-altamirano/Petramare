package Common;

import java.util.*;
import java.text.*;

public class clsFecha {

    /** Contiene el Formato de la fecha a usar
     **/
    private String FormatoFecha;
    /** Contiene la fecha creada
     **/
    private Date Fecha;

    /** Constructor que inicializa el formato de la fecha cuando se es instanciada,
     * por default ser&aacute; dd/MM/yyyy kk:mm:ss
     **/
    public clsFecha() {
        this.FormatoFecha = "dd/MM/yyyy kk:mm:ss";
    }

    /** Coloca el nuevo formato de fecha a usar
     *@param Formato de fecha
     **/
    public void setFormato(String Formato) {
        this.FormatoFecha = Formato;
    }

    /** Retorna el Formato que se a estado usando
     *@return Formato
     **/
    public String getFormato() {
        return this.FormatoFecha;
    }

    /** Establece una Fecha
     * @param Date Fecha
     **/
    public void setFecha(Date Fec) {
        this.Fecha = Fec;
    }

    public Date getFechaDate() {
        return this.Fecha;
    }

    /** Inserta la fecha pero deber&aacute; de respetar el formato de la fecha para que no marque
     * una Exception
     * @param Date Fecha
     **/
    public void setFecha(String lFecha) {

        SimpleDateFormat lsdfFecha = new SimpleDateFormat(this.FormatoFecha);
        this.Fecha = lsdfFecha.parse(lFecha, new ParsePosition(0));
        //System.out.println("setFecha(): " + this.Fecha + "\t" + "lFecha: " + lFecha);

    }

    /** Obtiene la fecha en el formato solicitado
     * @param Formato
     * @return String Fecha
     **/
    public String getFecha(String Formato) {
        SimpleDateFormat NewFecha = new SimpleDateFormat(Formato);
        return (NewFecha.format(this.Fecha));
    }

    /** Obtiene la fecha en el formato que a sido configurado previamente
     * @param Formato
     * @return String Fecha
     **/
    public String getFecha() {

        SimpleDateFormat NewFecha = new SimpleDateFormat(this.FormatoFecha);
        //System.out.println("getFecha(): " + this.Fecha);
        return (NewFecha.format(this.Fecha));
    }

    /** Realiza el cambio de formato de una fecha establecida
     * @param String lstrOriginal Contiene el formato original la fecha a transformar
     * @param String lstrNuevo Contiene el formato al que se desea cambiar
     * @param String lstrFecha Fecha que se va a transformar
     * @return String Fecha Transformada
     **/
    public String CambiaFormatoFecha(String lstrOriginal, String lstrNuevo, String lstrFecha) {
        //"yyMMdd"      //"dd/MM/yyyy"
        try{
        SimpleDateFormat lsdfFecha = new SimpleDateFormat(lstrOriginal);
        java.util.Date lfecOriginal = lsdfFecha.parse(lstrFecha, new ParsePosition(0));
        SimpleDateFormat lsdfNuevo = new SimpleDateFormat(lstrNuevo);
        return (lsdfNuevo.format(lfecOriginal));
        }
        catch(Exception ex){
            System.out.println("Exc:" + ex.getMessage());
        }
        return lstrFecha;
    }

    /** Regresa la fecha del Sistema
     * @return fecha del sistema
     **/
    public String getFechaHoy() {
        String fetch = "";
        Date date = new Date();
        try {
            SimpleDateFormat NewFecha = new SimpleDateFormat(this.FormatoFecha);
            fetch = (NewFecha.format(date));
        } catch (Exception e) {
//            System.out.println(e.toString());
        }
        return fetch;
    }

    /** Regresa la fecha del Sistema
     * @return fecha del sistema
     **/
    public String getFechaHoy(String formato) {
        String fetch = "";
        Date date = new Date();
        try {
            SimpleDateFormat NewFecha = new SimpleDateFormat(formato);
            fetch = (NewFecha.format(date));
        } catch (Exception e) {
//            System.out.println(e.toString());
        }
        return fetch;
    }

    public String getFecha(Date Fecha) {
        SimpleDateFormat NewFecha = new SimpleDateFormat(this.FormatoFecha);
        return (NewFecha.format(Fecha));

    }

    public String getFechaAyerAlf(Date Fecha) {
        String lstrFormato = this.FormatoFecha;
        this.FormatoFecha = "yyyy D";
        int liDia = (new Integer(this.getFecha(Fecha).substring(4).trim())).intValue() - 1;
        String fechaHoy = this.getFecha(Fecha).substring(0, 4) + " " + liDia;
        this.setFecha(fechaHoy);
        this.setFormato(lstrFormato);
        return getFecha();
    }

    /**Compara Fecha
     *@param String Fecha1 Base
     *@param String Formato de la Fecha1
     *@param String Fecha2 a Comparar
     *@param String Formato de la Fecha2
     *@return int 0 si son iguales, n&uacute;mero negativo si es menor, n&uacute;mero positivo si es mayor
     **/
    public int ComparaFecha(String lstrFec1, String lstrFormato1, String lstrFec2, String lstrFormato2) {
        clsFecha lobjFec1 = new clsFecha();
        clsFecha lobjFec2 = new clsFecha();

        lobjFec1.setFormato(lstrFormato1);
        lobjFec1.setFecha(lstrFec1);

        lobjFec2.setFormato(lstrFormato2);
        lobjFec2.setFecha(lstrFec2);
        return (lobjFec1.getFechaDate().compareTo(lobjFec2.getFechaDate()));
    }

    /**Compara Fecha
     *@param Date Fecha a comparar con este Objeto
     *@return int 0 si son iguales, n&uacute;mero negativo si es menor, n&uacute;mero positivo si es mayor
     **/
    public int ComparaFecha(Date lobjFecha) {
        return (this.getFechaDate().compareTo(lobjFecha));
    }

    /** Obtiene el número de dias entre dos fechas
     *
     *@param Date  Fecha Inicial
     *@param Date  Fecha Final
     *@return long Número de días entre las 2 fechas
     **/
    public long getNumDays(Date fecIni, Date fecFin) {
        int oneMinute = 60 * 1000;
        int oneHour = oneMinute * 60;
        int oneDay = oneHour * 24;
        long diff = fecFin.getTime() - fecIni.getTime();
        if (diff >= oneDay) {
            diff = diff / oneDay;
            return diff + 1;
        } else {
            return 0;
        }
    }

    /** Obtiene el dia de la semana para una fecha
     *
     *@param Date  Fecha
     *@return int  Número de día de la semana 0-Domingo, 6-Sábado
     **/
    public int getDayOfWeek(Date FecD) {
        Calendar Fech = null;
        Fech.setTime(FecD);
        return Fech.get(Fech.DAY_OF_WEEK);
    }

    /** Obtiene el número de dias entre dos fechas sin contar los domingos
     *
     *@param String  Fecha Inicial
     *@param String  Fecha Final
     *@return long   Número de días sin contar los domingos
     **/
    public long getNumDaysNoSun(String lstrFecI, String lstrFecF) {
        SimpleDateFormat lsdfFecha = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date fecIni = lsdfFecha.parse(lstrFecI, new ParsePosition(0));
        java.util.Date fecFin = lsdfFecha.parse(lstrFecF, new ParsePosition(0));
        long numDays = getNumDays(fecIni, fecFin);
        long numSun = (numDays / 7);
        long numOth = numDays % 7;
        int nDayWk = getDayOfWeek(fecIni);
        int numDysForSD = 7 - nDayWk;
        if (numOth > numDysForSD) {
            numSun++;
        }
        return numDays - numSun;
    }

    /** Obtiene el número de dias entre dos fechas sin contar los domingos
     *
     *@param String  Fecha Inicial
     *@param String  Fecha Final
     *@return long   Número de días sin contar los domingos
     **/
    public long getNumDaysNoSunDom(Date fecIni, Date fecFin) {
        //SimpleDateFormat lsdfFecha = new SimpleDateFormat("dd/MM/yyyy");
        //java.util.Date fecIni=lsdfFecha.parse(lstrFecI,new ParsePosition(0));
        //java.util.Date fecFin=lsdfFecha.parse(lstrFecF,new ParsePosition(0));
        long numDays = getNumDays(fecIni, fecFin);
        long numSun = (numDays / 7);
        numSun = numSun * 2;
        long numOth = numDays % 7;
        int nDayWk = fecIni.getDay();
        int numDysForSD = 7 - nDayWk;
        if (numOth > numDysForSD) {
            numSun += 2;
        }
        return numDays - numSun;
    }

    /**Calcula la diferencia de Dias entre una fecha
     * NOTA: el tiempo esta en milisegundos con getTime(): long, representando los
     * milisegundos transcurridos desde el 1/1/1970.
     * Con lo que para restar dos fechas basta con restar los long y la
     * diferencia son los milisegundos de diferencia,
     * que en horas seria dividir por 3600000 y en dias en
     * 3600000 * 24 horas
     * @param clsFecha lobjFec Fecha a Comparar
     * @return int Numero de Dias de Diferencia
     **/
    public int DiferenciaDias(clsFecha lobjFec) {
        long lDiferencia = this.Fecha.getTime() - (lobjFec.getFechaDate().getTime());
        return ((int) lDiferencia / 86400000);
    }

    /**Calcula la diferencia de Dias entre una fecha
     *NOTA: el tiempo esta en milisegundos con getTime(): long, representando los
     * milisegundos transcurridos desde el 1/1/1970.
     * Con lo que para restar dos fechas basta con restar los long y la
     * diferencia son los milisegundos de diferencia,
     * que en horas seria dividir por 3600000 y en dias en
     * 3600000 * 24 horas
     * @param Date lobjFec Fecha a Comparar
     * @return int Numero de Dias de Diferencia
     **/
    public int DiferenciaDias(Date lobjFec) {
        long lDiferencia = this.Fecha.getTime() - lobjFec.getTime();
        return ((int) lDiferencia / 86400000);
    }

    public boolean fechaValida(String lstrIn) {
        String fdt = "dd/MM/yyyy";
        StringTokenizer lobjST = new StringTokenizer(lstrIn, "/");
        String[] lstrTmp = new String[3];
        int i = 0;
        try {
            while (lobjST.hasMoreTokens()) {
                lstrTmp[i] = lobjST.nextToken();
                i++;
            }
            if (lstrTmp[0].length() < 1 || lstrTmp[1].length() < 1 || lstrTmp[2].length() != 4) {
                return false;
            }
            i = Integer.parseInt(lstrTmp[0]);
            i = Integer.parseInt(lstrTmp[1]);
            i = Integer.parseInt(lstrTmp[2]);
        } catch (Exception e) {
            return false;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(fdt);
            sdf.setLenient(false);
            java.util.Date dt2 = sdf.parse(lstrIn);
            return true;
        } catch (ParseException e) {
            return false;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public boolean fechaValida(String lstrIn, String fdt) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(fdt);
            sdf.setLenient(false);
            java.util.Date dt2 = sdf.parse(lstrIn);
            return true;
        } catch (ParseException e) {
            return false;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public long ComparaFechas(String lstrFecha1, String lstrForamto1, String lstrFecha2, String lstrForamto2) {
        SimpleDateFormat lsdfFecha = new SimpleDateFormat(lstrForamto1);
        Date ldatFecha1 = lsdfFecha.parse(lstrFecha1, new ParsePosition(0));
        lsdfFecha = new SimpleDateFormat(lstrForamto2);
        Date ldatFecha2 = lsdfFecha.parse(lstrFecha2, new ParsePosition(0));
        return ldatFecha2.getTime() - ldatFecha1.getTime();
    }

    public long DiferenciaDias(String lstrFecha1, String lstrForamto1, String lstrFecha2, String lstrForamto2) {
        SimpleDateFormat lsdfFecha = new SimpleDateFormat(lstrForamto1);
        Date ldatFecha1 = lsdfFecha.parse(lstrFecha1, new ParsePosition(0));
        lsdfFecha = new SimpleDateFormat(lstrForamto2);
        Date ldatFecha2 = lsdfFecha.parse(lstrFecha2, new ParsePosition(0));
        return (ldatFecha2.getTime() - ldatFecha1.getTime()) / 86400000;
    }

    public boolean isDentroRango(String lstrFecha1, String lstrForamto1, String lstrFecha2, String lstrForamto2,
            String lstrFecha3, String lstrForamto3) {
        SimpleDateFormat lsdfFecha = new SimpleDateFormat(lstrForamto1);
        Date ldatFecha1 = lsdfFecha.parse(lstrFecha1, new ParsePosition(0));
        lsdfFecha = new SimpleDateFormat(lstrForamto2);
        Date ldatFecha2 = lsdfFecha.parse(lstrFecha2, new ParsePosition(0));
        lsdfFecha = new SimpleDateFormat(lstrForamto3);
        Date ldatFecha3 = lsdfFecha.parse(lstrFecha3, new ParsePosition(0));

        if (ldatFecha3.getTime() < ldatFecha1.getTime()) {
            return false;
        }
        if (ldatFecha3.getTime() > ldatFecha2.getTime()) {
            return false;
        }
        return true;
    }

    /*
     *Regresa el numero del dia de la semana de una fecha
     * */
    public int diaSemana(String fecha) {
        java.util.Date formato = new java.util.Date();
        java.text.SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaActual = null;

        try {

            fechaActual = df.parse(fecha);

        } catch (ParseException e) {

//            System.err.println("No se ha podido parsear la fecha.");
            e.printStackTrace();
        }

        GregorianCalendar fechaCalendario = new GregorianCalendar();
        fechaCalendario.setTime(fechaActual);
        int diaSemana = fechaCalendario.get(Calendar.DAY_OF_WEEK) - 1;
        //System.out.println("numero de dia de la semana: " + diaSemana);
        return diaSemana;
    }//termina el metodo DiaSemana

    public String restaMes(int m, int a, int t) {
        String MA = "";
        int m2 = 0, a2 = 0;

        m2 = m;
        a2 = a;
        for (int x = 1; x <= t; x++) {
            m2 = m2 - 1;

            if (m2 == 0) {
                m2 = 12;
                a2 = a2 - 1;
            }//termina el if
        }//termina el for
        MA = m2 + "/" + a2;

        return MA;
    }

    public String mes(String m) {
        String mes = "";
        if (m.equals("01") || m.equals("1")) {
            mes = "ENE";
        }
        if (m.equals("02") || m.equals("2")) {
            mes = "FEB";
        }
        if (m.equals("03") || m.equals("3")) {
            mes = "MAR";
        }
        if (m.equals("04") || m.equals("4")) {
            mes = "ABR";
        }
        if (m.equals("05") || m.equals("5")) {
            mes = "MAY";
        }
        if (m.equals("06") || m.equals("6")) {
            mes = "JUN";
        }
        if (m.equals("07") || m.equals("7")) {
            mes = "JUL";
        }
        if (m.equals("08") || m.equals("8")) {
            mes = "AGO";
        }
        if (m.equals("09") || m.equals("9")) {
            mes = "SEP";
        }
        if (m.equals("10")) {
            mes = "OCT";
        }
        if (m.equals("11")) {
            mes = "NOV";
        }
        if (m.equals("12")) {
            mes = "DIC";
        }
        return mes;

    }
}
