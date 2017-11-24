package com.garante.gestionfideicomisos.Helpers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {

    public boolean write(String accion, String usuario, String texto) {
        boolean res = false;
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
        Date fecha = new Date();
        FileWriter fw = null;
        PrintWriter pw = null;
        File ruta = new File("/inetpub/Logs/"+ yearFormat.format(fecha));
        File archivo = new File("/inetpub/Logs/"+ yearFormat.format(fecha) +"/"+monthFormat.format(fecha)+".csv");
        
        if (!ruta.exists()) {
            ruta.mkdirs();
        }
        
        try {
            fw = new FileWriter(archivo, true);
            pw = new PrintWriter(fw);
            pw.println(dateFormat.format(fecha) + ";" + accion + ";" + usuario + ";" + texto);
        } catch (IOException ex) {
            System.out.println("Error al escribir " + ex);
        } finally {
            try {
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException ex) {
                System.out.println("Error al cerrar archivo " +ex);
            }
        }
        
        return res;
    }
    
}
