package mx.garante.liquidacionriesgoslaborales.Modelos;

import mx.garante.liquidacionriesgoslaborales.Beans.Message;
import java.io.*;
import java.util.zip.*;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.CopyOption;
import java.nio.file.StandardCopyOption;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class UnZip {

    final static int BUFFER = 2048;
    static public Vector<Message> sucesos = new Vector<Message>();

    public static String Inicio() {
        String resultado = "";
        String fileZip_origen = "";
        //1.- Subir archivo .zip que contiene todos los estados de cuenta
        //2.- Almacenar en el equipo el archivo subido por el usuario y poner nombre descriptivo del día subido.
        //3.- Descomprimir archivo .zip subido por el cliente y almacenar todos los archivos de estados
        //    de cuenta en una carpeta temporal.
        //4.- Extraer cada uno de los archivos contenidos en la carpeta temporal, y copiarlos en 
        //    su carpeta correspondiente de cada fideicomiso modificando el nombre con la nomenclatura correcta.
        try {
//            copyFile(".\\prueba\\contratos.txt", ".\\prueba\\prueba\\contratos_copy.txt");
            fileZip_origen = copy_ESTADOS_CUENTA();
        } catch (IOException ex) {
            System.out.println("Exception al copiar archivo");
            return "Error al copiar el archivo";
        }

        if (!fileZip_origen.equals("")) {
            boolean correcto = unZip(fileZip_origen);
            //Se tiene descomprimido el .zip y cada uno de los estados de cuenta en .\\EDOS_CTA_UPLOADS\\temporal
            //Se prosigue a copiar cada uno de los estados de cuenta en su correspondiente carpeta para el fideicomiso
            if (correcto) {
                String respuesta = ordenaEdosCta();
                if (respuesta.equals("Archivos ordenados correctamente")) {
                    System.out.println(respuesta);
                    resultado = respuesta;
                } else {
                    System.out.println(respuesta);
                    resultado = respuesta;
                }

            } else {
                System.out.println("Error al descomprimir el archivo.");
                return "Error al descomprimir el archivo.";
            }
        } else {
            System.out.println("No se puede localizar el archivo");
            return "No se puede localizar el archivo";
        }

        eliminaArchivosTemp(".\\EDOS_CTA_UPLOADS\\temporal\\");

        return resultado;
    }

    private synchronized static void copyFile(String origen, String destino) throws IOException {
        Path FROM = Paths.get(origen);
        Path TO = Paths.get(destino);
//sobreescribir el fichero de destino, si existe, y copiar
// los atributos, incluyendo los permisos rwx
        CopyOption[] options = new CopyOption[]{
            StandardCopyOption.REPLACE_EXISTING,
//            StandardCopyOption.ATOMIC_MOVE,
            StandardCopyOption.COPY_ATTRIBUTES
        };
        Files.copy(FROM, TO, options);
    }

    private synchronized static void eliminaArchivosTemp(String directorio) {
        File directorioTemp = new File(directorio);
        String[] listaArchivos = directorioTemp.list();
        if (directorioTemp.isDirectory()) {
            System.out.println("Se eliminaran:" + listaArchivos.length + " archivos de la carpeta temporal");
            for (int i = 0; i < listaArchivos.length; i++) {
                Path FROM = Paths.get(directorio + listaArchivos[i]);
                try {
                    Files.deleteIfExists(FROM);
                } catch (IOException ex) {
                    System.out.println("No se puede eliminar:" + directorio + listaArchivos);
                }
            }
        }
        System.out.println("Archivos eliminados correctamente.");
    }

    private synchronized static String copy_ESTADOS_CUENTA() throws IOException {
        String destino = ".\\EDOS_CTA_UPLOADS\\" + generaNombre_ESTADOS_CUENTA();
        //URL en Servidor Virtual
        String origen = ".\\tomcat\\bin\\uploads\\ESTADOS_CUENTA\\EdosCta_temporal.zip";

        Path FROM = Paths.get(origen);
        Path TO = Paths.get(destino);
//sobreescribir el fichero de destino, si existe, y copiar
// los atributos, incluyendo los permisos rwx
        CopyOption[] options = new CopyOption[]{
            StandardCopyOption.REPLACE_EXISTING,
            StandardCopyOption.COPY_ATTRIBUTES
        };
        System.out.println("Copiando .zip a la carpeta con nombre para registro .\\EDOS_CTA_UPLOADS\\");
        Files.copy(FROM, TO, options);
        System.out.println("Archivo copiado: " + destino);
        return destino;
    }

    private static String generaNombre_ESTADOS_CUENTA() {
        //El formato para el nombre del .zip que contiene todos los estados de cuenta contenidos en el es:
        // EdosCta_ddMMMYYYY.zip
        SimpleDateFormat dateF = new SimpleDateFormat("ddMMMyyyy-HHmm");
        return ("EdosCta_" + dateF.format(new Date()) + ".zip");
    }

    private synchronized static boolean unZip(String zipOrigen) {
        boolean unZipCorrecto = false;

        try {
            File dirDestino = new File(".\\EDOS_CTA_UPLOADS\\temporal");
            BufferedOutputStream dest = null;
//            FileInputStream fis = new FileInputStream(fileZip_origen);
            FileInputStream fis = new FileInputStream(zipOrigen);
            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
            FileOutputStream fos = null;
            ZipEntry entry;
            int count;
            int index = 0;
            byte data[] = new byte[BUFFER];
            String rutaarchivo = null;
            while ((entry = zis.getNextEntry()) != null) {
                System.out.println("Extracting: " + entry);
                rutaarchivo = entry.getName();
                // tenemos que quitar el primer directorio
                index = rutaarchivo.indexOf("/");
                rutaarchivo = rutaarchivo.substring(index + 1);
                if (rutaarchivo.trim().length() > 0) {
                    // write the files to the disk
                    try {
                        dest = null;
                        File fileDest = new File(dirDestino.getAbsolutePath() + "/" + rutaarchivo);
                        if (entry.isDirectory()) {
                            fileDest.mkdirs();
                        } else {
                            if (fileDest.getParentFile().exists() == false) {
                                fileDest.getParentFile().mkdirs();
                            }

                            fos = new FileOutputStream(fileDest);
                            dest = new BufferedOutputStream(fos, BUFFER);
                            while ((count = zis.read(data, 0, BUFFER)) != -1) {
                                dest.write(data, 0, count);
                            }
                            dest.flush();
                        }
                    } catch (Exception ex) {
                        System.out.println("Error al descomprimir el archivo");
                        return false;
                    } finally {
                        try {
                            if (dest != null) {
                                dest.close();
                            }
                        } catch (Exception e) {;
                        }
                    }
                }
            }
            zis.close();
            unZipCorrecto = true;
        } catch (Exception e) {
            e.printStackTrace();
            unZipCorrecto = false;
        }

        return unZipCorrecto;
    }

    private static String ordenaEdosCta() {
        Vector<Message> messages = new Vector<Message>();
        String respuesta = "";
        int correcto = 0;
        File directorio = new File(".\\EDOS_CTA_UPLOADS\\temporal\\");
        String[] listaArchivos = directorio.list();
        if (directorio.isDirectory()) {
            System.out.println("Se han encontrado [" + listaArchivos.length + "] estados de cuenta");
            for (int i = 0; i < listaArchivos.length; i++) {
                if (listaArchivos[i].length() == 36) {
                    //Extraer de la carpeta temporal cada uno de los estados de cuenta
                    //Actualmente cada uno de los estados de cuenta tiene el nombre con nomenclatura: NNN_EDOCTA_ClaveContrato_YYYY_MM.zip
                    String nombreCorrecto = listaArchivos[i].substring(4);
                    String clave_contrato = nombreCorrecto.substring(7, 20);
                    //Verificamos si el contrato existe
                    if (ModeloLiquidation.existeContrato(clave_contrato)) {
                        //Verificamos si la ruta para almacenar el estado de cuenta existe en el sistema
                        File directorioFideicomiso = new File(".\\inetpub\\ftproot\\EstadosDeCuenta\\" + clave_contrato + "\\");
                        if (!directorioFideicomiso.exists()) {
                            if (!directorioFideicomiso.mkdir()) {
                                messages.add(new Message(0, "Carpeta de Fideicomiso", listaArchivos[i], "No se pudo crear la carpeta para el fideicomiso correspondiente al nombre del archivo."));
                            }
                        }
                        if (directorioFideicomiso.exists() && directorioFideicomiso.isDirectory()) {
                            String ext = listaArchivos[i].substring(33, listaArchivos[i].length());
                            if (ext.equals("zip") || ext.equals("ZIP")) {
                                try {
                                    String destino = ".\\inetpub\\ftproot\\EstadosDeCuenta\\" + clave_contrato + "\\" + nombreCorrecto;
                                    String origen = ".\\EDOS_CTA_UPLOADS\\temporal\\" + listaArchivos[i];
                                    copyFile(origen, destino);
                                    correcto++;
                                } catch (IOException ex) {
                                    System.out.println("Error al copiar:" + ex.getMessage());
                                    messages.add(new Message(0, "Copiar archivo", listaArchivos[i], "Error al copiar el archivo"));
                                }
                            } else {
                                messages.add(new Message(0, "Extención de archivo", listaArchivos[i], "El tipo de archivo no es un ZIP"));
                            }
                        } else {
                            messages.add(new Message(0, "URL", listaArchivos[i], "La ruta de ubicación no existe"));
                        }
                    } else {
                        messages.add(new Message(0, "Contrato", listaArchivos[i], "La clave de contrato:" + clave_contrato + " no existe."));
                    }
                } else {
                    messages.add(new Message(0, "Longitud", listaArchivos[i], "La longitud del nombre de estado de cuenta es diferente de 36"));
                }
                //Extraer a que fideicomiso corresponde
                //Modificar el nombre del archivo a una nomenclatura correcta: EDOCTA_ClaveContrato_YYYY_MM.zip
            }
            if (messages.isEmpty()) {
                return "Archivos ordenados correctamente";
            } else {
                respuesta = "Archivos ordenados correctamente: " + correcto + " archivos.";
                sucesos = messages;
            }
        }

        return respuesta;
    }
}
