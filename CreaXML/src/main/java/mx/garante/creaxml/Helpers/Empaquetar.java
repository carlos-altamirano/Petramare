package mx.garante.creaxml.Helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Empaquetar {

    public static void addFilesToExistingZip2(File zipFile, File[] files) throws IOException {
        FileOutputStream fos = new FileOutputStream(zipFile);
        ZipOutputStream out = new ZipOutputStream(fos);
        byte[] buf = new byte[1024];
        for (File f : files) {
            FileInputStream in = new FileInputStream(f);

            out.putNextEntry(new ZipEntry(f.getName()));
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.closeEntry();
            in.close();
        }
        out.close();
    }

    public static String numMes(String mes) {
        String num = "";
        switch(mes){
            case "Enero": num = "01"; break; 
            case "Febrero": num = "02"; break; 
            case "Marzo": num = "03"; break; 
            case "Abril": num = "04"; break; 
            case "Mayo": num = "05"; break; 
            case "Junio": num = "06"; break; 
            case "Julio": num = "07"; break; 
            case "Agosto": num = "08"; break; 
            case "Septiembre": num = "09"; break; 
            case "Octubre": num = "10"; break; 
            case "Noviembre": num = "11"; break; 
            case "Diciembre": num = "12"; break; 
        }
        return num;
    }
    
    public static String numMes(int mes) {
        String num = "";
        switch(mes){
            case 0: num = "01"; break; 
            case 1: num = "02"; break; 
            case 2: num = "03"; break; 
            case 3: num = "04"; break; 
            case 4: num = "05"; break; 
            case 5: num = "06"; break; 
            case 6: num = "07"; break; 
            case 7: num = "08"; break; 
            case 8: num = "09"; break; 
            case 9: num = "10"; break; 
            case 10: num = "11"; break; 
            case 11: num = "12"; break; 
        }
        return num;
    }

    public static void copiarMass(String origen, String destino, String name) {
        File carpetaInicial = new File(origen);
        File carpetaFinal = new File(destino);
        if (!carpetaFinal.exists()) {
            carpetaFinal.mkdir();
        }
        Stream.of(carpetaInicial.listFiles(
                f -> f.getName().contains("name"))
        ).forEach(f -> {
            try {
                Files.copy(Paths.get(f.getAbsolutePath()), Paths.get(new File(carpetaFinal, f.getName()).getAbsolutePath()));
            } catch (IOException e) {
                e.printStackTrace(System.out);
            }
        });
    }

    public static void copiar(String origen, String destino) {
        try {
            File inFile = new File(origen);
            File outFile = new File(destino);
            File rootDest = outFile.getParentFile();
            if (!rootDest.exists()) {
                rootDest.mkdirs();
            }
            FileInputStream in = new FileInputStream(inFile);
            FileOutputStream out = new FileOutputStream(outFile);
            int c;
            while ((c = in.read()) != -1) {
                out.write(c);
            }
            in.close();
            out.close();
        } catch (IOException e) {
            System.err.println("ERROR ENTRADA / SALIDA : " + e.getMessage());
        }
    }
    
    public static boolean eliminaR(File carpeta){
        if(carpeta.isDirectory()){
            boolean vacio = true;
            File contenido[] = carpeta.listFiles();
            for(File f : contenido){
                if(f.isDirectory()){
                    if(!eliminaR(f)){
                        vacio = false; break;
                    }
                }else{
                    if(!f.delete()){
                        vacio = false; break;
                    }
                }
            }
            vacio = vacio?carpeta.delete():vacio;
            return vacio;
        }else
            return carpeta.delete();
    }
}
