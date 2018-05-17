package com.sofom.creaxml;

import mx.garante.creaxml.DAOs.ContratosDAO;
import mx.garante.creaxml.DAOs.MovimientosDAO;
import mx.garante.creaxml.Helpers.Fecha;
import mx.garante.creaxml.Models.Contrato;
import mx.garante.creaxml.Models.Movimiento;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.junit.Test;

public class Tests {
    
    @Test
    public void consulta() {
        /*ContratosDAO contratosDAO = new ContratosDAO();
        MovimientosDAO movimientosDAO = new MovimientosDAO();
        
        List<Contrato> contratos = contratosDAO.getAll();
        
        Date date = new Date();*/
        //date = Fecha.getPrimerDiaDeMes(date);
        //Calendar c1 = Fecha.creaCalendar(date, 0, 0, 0);
        //date = Fecha.getUltimoDiaDeMes(c1.getTime());
        /*SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        System.out.println(format.format(c1.getTime()));
        System.out.println(format.format(date));
        
        for (Contrato contrato : contratos) {
            System.out.println("****************************************************************");
            List<Movimiento> movimientos = movimientosDAO.getAll(contrato.getClave_contrato(), format.format(c1.getTime()), format.format(date));
            movimientos.forEach(System.out::println);
        }*/
    }
    
}
