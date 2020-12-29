package com.reports.generareportes;

import com.reports.generareportes.DAO.Consultas;
import com.reports.generareportes.Helpers.Fecha;
import com.reports.generareportes.Helpers.Fechas;
import com.reports.generareportes.Helpers.GenExcel;
import com.reports.generareportes.Helpers.ServicioEnviar;
import com.reports.generareportes.Modelos.Contrato;
import com.reports.generareportes.Modelos.Movimiento;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class GeneraReportes {
    
    /**
     * 
     * @param args formato yyyy-MM, basedeDatos obligatorios ejemplo 2018-03, sofom
     * @throws java.text.ParseException
     */
    public static void main(String[] args) throws ParseException {
        
        Consultas consultas = new Consultas();
        
        System.out.print("Ingresa la fecha en este formato yyyy-MM en caso contrario dejar vacio -> ");
        Scanner entradaEscaner = new Scanner(System.in);
        String fechaConsola = entradaEscaner.nextLine();
        Date fechaHoy = null;
        if (fechaConsola.equals("")) {
            fechaHoy = new Date();
        } else {
            fechaHoy = GeneraReportes.creaDate(fechaConsola);
        }
        
        String dataBase = "garante";
        String path = System.getProperty("user.dir");
        
        File contraFile = new File(path + "\\Contratos.xlsx");
        File movFile = new File(path + "\\Movimientos.xlsx");
        
        if (contraFile.exists()) {
            contraFile.delete();
        }
        if (movFile.exists()) {
            movFile.delete();
        }
        
        //reporte 1
        String[] titulosContra = {
            "Clave Contrato", "Nombre Cliente", "Cuenta Origen", "Grupo", "Domicilio Fiscal",
            "RFC", "Telefono", "Email", "Tipo Honorario", "Honorario sin IVA", "Oficinas",
            "Fecha Captura", "Status", "Saldo", "ID Codes", "Entidad Federativa", "Codigo Postal"
        };
        List<Contrato> contratos = consultas.getContratos(dataBase);
        boolean r1 = GenExcel.generaExcel("Contratos", titulosContra, null, contratos);
        
        //reporte 2
        String[] titulosMovs = {
            "Clave Contrato", "Fecha Usuario Autoriza", "Fecha Liquidacion",
            "Clave Empleado", "Nombre Empleado", "Apellido Paterno", "Apellido Materno",
            "CURP", "RFC", "Cuenta Deposito", "Fecha Ingreso", "Departamento", "Puesto", "Tipo", 
            "Importe Liquidacion" , "Importe Liquidacion MXN","UUID"
        };
        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat formatSalida = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c1 = Fecha.getPrimerDiaDeMes(fechaHoy);
        Calendar c2 = Fecha.getUltimoDiaDeMes(fechaHoy);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Movimiento> movimientos = consultas.getAll(dataBase, format.format(c1.getTime()), format.format(c2.getTime()));
        //List<Movimiento> movimientos = consultas.consultaMovs(dataBase, formatSalida.format(Fechas.getPrimerDiaDeMes(fechaHoy)), formatSalida.format(Fechas.getUltimoDiaDeMes(fechaHoy)));
        boolean r2 = GenExcel.generaExcel("Movimientos", titulosMovs, movimientos, null);
        
        if (r1 && r2) {
            ServicioEnviar mail = new ServicioEnviar();
            List<File> archivos = new ArrayList<>();
            archivos.add(movFile);
            archivos.add(contraFile);
            Boolean resEnvio = mail.enviar("Reportes mensuales", "Reporte de " + dataBase, "contacto@garante.mx", archivos);
            if (resEnvio) {
                System.out.println("Correo enviado correctamente");
            } else {
                System.out.println("Error al enviar correo ");
            }
        }
        
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
    
}
