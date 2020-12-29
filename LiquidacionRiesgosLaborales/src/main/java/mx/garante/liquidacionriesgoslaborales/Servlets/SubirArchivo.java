package mx.garante.liquidacionriesgoslaborales.Servlets;

import mx.garante.liquidacionriesgoslaborales.Beans.Message;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mx.garante.liquidacionriesgoslaborales.Modelos.ModelUpdate;
import mx.garante.liquidacionriesgoslaborales.Modelos.UnZip;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

public class SubirArchivo extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Almacena la lista de parametros cargados del formulario        
        System.out.println("Entra a SubirArchivo()");
        //Se asigna un valor vacio a la variable de sucesos que muestra los mensajes de error al subir archivo
        UnZip.sucesos = null;
        request.getSession().removeAttribute("sucesos");
        List fileItemsList = null;
        boolean isMultipart = false;
        String dirNameTmp = "./uploads/ESTADOS_CUENTA/";
        String accion="";
        String optionalFileName = "";
        Message messageBean = null;
        String fileName = null;
        int indice = 0;
        // Almacena la referencia al archivo cargado
        FileItem fileItem = null;
        HttpSession session = request.getSession();

        System.out.println("");
        // Manejador de archivos cargados
        ServletFileUpload servletFileUpload = null;
        // Se verifica si la petitición el multipart
        isMultipart = ServletFileUpload.isMultipartContent(request);

        if (isMultipart) {
            
            try {
                // Create a new file upload handler
                servletFileUpload = new ServletFileUpload(new DiskFileItemFactory());

                // Parse the request
                fileItemsList = servletFileUpload.parseRequest(request);

                Iterator it = fileItemsList.iterator();
                while (it.hasNext()) {
                    FileItem fileItemTemp = (FileItem) it.next();
                    // Si se trata de un campo
                    if (fileItemTemp.isFormField()) {
//                        System.out.println("Name-value Pair Info:");
//                        System.out.println("Field name:" + fileItemTemp.getFieldName());
//                        System.out.println("Field value:" + fileItemTemp.getString());

                        if (fileItemTemp.getFieldName().equals("accion")) {
                            accion = fileItemTemp.getString();
                            System.out.println("La accion:" +  accion);
                        }
                        // Vemos si una de esas variables del formulario es el nombre alternativo del archivo
                        if (fileItemTemp.getFieldName().equals("filename")) {
                            optionalFileName = fileItemTemp.getString();
                        }
                    } else // En caso contrario se trata del archivo y se guarda su referencia
                    {
                        fileItem = fileItemTemp;
                    }
                }
            } catch (FileUploadException ex) {
                System.out.println("Exception al subir archivo.");
                messageBean = new Message();
                messageBean.setDesc("Error al subir archivo");
                session.setAttribute("messageBean", messageBean);
            }            
            
            
            if (fileItem != null) {
                // Guardamos el archivo.
                if (fileItem.getSize() > 0) {
                    // Aquí valores del archivos cargado
                    System.out.println("loaded File Info:");
                    System.out.println("Content type:" + fileItem.getContentType());
                    System.out.println("Field name:" + fileItem.getFieldName());
                    System.out.println("File name:" + fileItem.getName());
                    System.out.println("File size:" + fileItem.getSize());
                    // Obtenemos el nombre del archivo
                    fileName = fileItem.getName();
                    //Obtenemos el punto de referencia de inicio de la extensión.
                    indice = fileName.indexOf(".");
                    //Verificamos si se trata de un archivo valido.
                    if (indice > 0) {
                        int tamArchivo = fileName.length();
                        String extArchivo = fileName.substring(indice, tamArchivo);
                        //Verificamos si se trata de un archivo de texto.
                        if (extArchivo.equals(".zip") || extArchivo.equals(".ZIP")) {
                            if (!fileName.equals("")) {
                                try {
                                    // Si no se ha declarado un nombre opcional, se pone el mismo nombre del archivo cargado
                                    if (optionalFileName.trim().equals("")) {
                                        fileName = FilenameUtils.getName(fileName);
                                    } else {
                                        fileName = optionalFileName;
                                    }
                                    boolean nomenclaturaCorrecta=false;
                                    // Se crea el archivo a donde se va guardar el contenido del archivo cargado
                                    if(accion!=null && accion.equals("subirConjuntoArchivos")){
                                    fileName = "EdosCta_temporal.zip";
                                    nomenclaturaCorrecta=true;
                                    }
                                    if(accion!=null && accion.equals("subirArchivo")){
                                        nomenclaturaCorrecta = ModelUpdate.getUrlEdoCtaFid(fileName);
                                        if(nomenclaturaCorrecta)
                                        dirNameTmp = ".\\inetpub\\ftproot\\EstadosDeCuenta\\" + fileName.substring(7,20) + "\\";
                                    }
                                    if(nomenclaturaCorrecta){
                                    File saveTo = new File(dirNameTmp + fileName);
                                    // Se escribe el contenido del archivo cargado al destino
                                    saveTo.exists();
                                    fileItem.write(saveTo);
                                    // Validación de archivo
                                            messageBean = new Message();
                                            if(accion!=null && accion.equals("subirConjuntoArchivos")){
                                            messageBean.setDesc("ARCHIVO: " + fileName + " subido correctamente.");
                                            messageBean.setDesc("Favor de seguir con el proceso de ordenamiento.");
                                            session.setAttribute("ordenamiento", "Ordenar conjunto de estados de cuenta");
                                            }
                                            if(accion!=null && accion.equals("subirArchivo"))
                                            messageBean.setDesc("ARCHIVO: " + fileName + " almacenado y ordenado correctamente.");
                                            session.setAttribute("messageBean", messageBean);                                      
                                    }else{
                                            messageBean = new Message();
                                            messageBean.setDesc("Nomenclatura incorrecta en el nombre del archivo.");
                                            session.setAttribute("messageBean", messageBean);                                        
                                    }
                                    
                                } catch (Exception ex) {
                                    messageBean = new Message();
                                    messageBean.setDesc("Error almacenando el archivo.");
                                    session.setAttribute("messageBean", messageBean);                                    
                                }

                            } else {
                                System.out.println("Nombre de archivo invalido");
                            }
                        } //Verifica que sea un archivo de texto.
                        else {
                            messageBean = new Message();
                            messageBean.setDesc("Se requiere un archivo .zip");
                            session.setAttribute("messageBean", messageBean);
                        }//Verifica que sera un archivo valido.
                    } else {
                            messageBean = new Message();
                            messageBean.setDesc("Archivo inválido, favor de verificar.");
                            session.setAttribute("messageBean", messageBean);
                    }
                } else {
                    messageBean = new Message();
                    messageBean.setDesc("Por favor especifica un archivo.");
                    session.setAttribute("messageBean", messageBean);
                }
            }

        }                
        
        response.sendRedirect("subirArchivo.htm");
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>


}
