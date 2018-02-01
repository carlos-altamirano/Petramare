package com.reports.generareportes;

import com.reports.generareportes.DAO.Consultas;
import com.reports.generareportes.Helpers.Fechas;
import com.reports.generareportes.Helpers.GenExcel;
import com.reports.generareportes.Helpers.ServicioEnviar;
import com.reports.generareportes.Modelos.Contrato;
import com.reports.generareportes.Modelos.Movimiento;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GeneraReportes {
    
    /**
     * 
     * @param args formato yyyy-MM, basedeDatos obligatorios ejemplo 2018-03, sofom
     * @throws java.text.ParseException
     */
    public static void main(String[] args) throws ParseException {
        
        Consultas consultas = new Consultas();
        String dataBase = args[1];
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
            "Importe Liquidacion" , "Importe Liquidacion MXN"
        };
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat formatSalida = new SimpleDateFormat("yyyy-MM-dd");
        
        Date date = dateFormat.parse(args[0]);
        
        List<Movimiento> movimientos = consultas.consultaMovs(dataBase, formatSalida.format(Fechas.getPrimerDiaDeMes(date)), formatSalida.format(Fechas.getUltimoDiaDeMes(date)));
        boolean r2 = GenExcel.generaExcel("Movimientos", titulosMovs, movimientos, null);
        
        if (r1 && r2) {
            ServicioEnviar mail = new ServicioEnviar();
            List<File> archivos = new ArrayList<>();
            archivos.add(movFile);
            archivos.add(contraFile);
            Boolean resEnvio = mail.enviar("Reportes mensuales", "Reporte de " + dataBase, "carlos-altamirano@gp.org.mx", archivos);
            if (resEnvio) {
                System.out.println("Correo enviado correctamente");
            } else {
                System.out.println("Error al enviar correo ");
            }
        }
        
    }
    
}
