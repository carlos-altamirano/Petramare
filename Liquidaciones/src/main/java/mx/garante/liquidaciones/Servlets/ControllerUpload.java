package mx.garante.liquidaciones.Servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.util.List;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Vector;
import mx.garante.liquidaciones.Beans.Usuario;
import mx.garante.liquidaciones.Beans.Message;
import mx.garante.liquidaciones.Modelos.ModeloCapture;
import mx.garante.liquidaciones.Beans.ResumenMovimientos;
import mx.garante.liquidaciones.Common.clsConexion;
import mx.garante.liquidaciones.Modelos.ModeloLayOut;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
//Para Cargar Archivo
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

//Para imprimir
//import Common.*;
//import java.awt.Font;
public class ControllerUpload extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws org.apache.commons.fileupload.FileUploadException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, FileUploadException {

        //Variables para almacenar la accion a realizar
        String action;
        int numberAction = 0;

        // Alamacen la session del cliente
        HttpSession session;

        // Almacena la url de la pagina jsp o Servlet que fungira como vista
        String urlResponse = "";

        // Almacena las variables de session que seran eliminadas
        String nombresObjetos[] = null;

        // Bean para los mensajes de respuesta del cliente
        Message messageBean = null;

        //Bean para limpiar sesion.
        Message messageClean = null;
        // Bean para el manejo de session de usuario

        Usuario userApp = null;

        //Almacena el nombre del cliente
        String cliente = "";

        //Almacena la clave que identifica al cliente
        String custNum = "";

        // Almacen el identificador del usuario
        String usuario = null;

        // Almacena la contrasenna del usuario
        String contrasenna = null;

        // Almacena los errores obtenidos al validar
        ArrayList errores = null;

        // Variable para volver a confirmar usuario
        String confirmaUsuario = null;

        //Variable para cambiar el Password del usuario
        String cambiaPassword = null;

        //Variable para ver en pantalla solo el resumen de movimientos
        String imprimeBrowser = null;

        //Almacena el resumen de los movimientos validados
        ResumenMovimientos resumenMovimientos = null;

        //Variable para imprimir movimientos
        ResumenMovimientos imprimeResumenMovimientos = null;
        ModeloLayOut modelo_l = null;
        ////////////////////////////////////////////////////////////////
        // Declaración de variables para el manejo de archivos        //
        // Almacena el nombre opcional del archivo                    //
        ////////////////////////////////////////////////////////////////
        boolean isMultipart = false;

        String optionalFileName = "";

        // Manejador de archivos cargados
        ServletFileUpload servletFileUpload = null;

        // Almacena la lista de parametros cargados del formulario
        List fileItemsList = null;

        // Almacena la referencia al archivo cargado
        FileItem fileItem = null;

        // Almacena el nombre del archivo cargado
        String fileName = null;

        // Almacena el directorio temporal de destino para el archivo cargado
        String dirNameTmp = "./uploads/";

        // Almacena el directorio de publicacion de archivos cargados
        // String dirNamePublishFiles = "./uploads/";

        // Crear o obtiene la session actual
        session = request.getSession();

        //Si se cargo el archivo satisfactoriamente
        String fileLoad = null;

        //Se obtiene la accion a realizar para llamar el modelo asociado
        //y generar la vista correspondiente
        String aux = request.getParameter("accion");

        ArrayList listFiles = new ArrayList();
        // Verificamos si viene de un enc-type=form-data
        if (aux == null) {
            System.out.println("AUX ES =NULL");
            // Se verifica si la petitición el multipart
            isMultipart = ServletFileUpload.isMultipartContent(request);

            // Check that we have a file upload request
            if (isMultipart) {
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
                            System.out.println("EQUALS= ACCION");
                            aux = fileItemTemp.getString();
                            System.out.println("AUX: " + aux);
                        }
                        // Vemos si una de esas variables del formulario es el nombre alternativo del archivo
                        if (fileItemTemp.getFieldName().equals("filename")) {
                            System.out.println("EQUALS= FILENAME");
                            optionalFileName = fileItemTemp.getString();
                        }
                    } else // En caso contrario se trata del archivo y se guarda su referencia
                    {
                        fileItem = fileItemTemp;
                    }
                }
            }
        }
        System.out.println("**ControllerUpload** Accion seleccionada: " + aux);

        if (aux != null && aux.compareTo("") != 0) {
            //Se obtiene el nombre y el numero de la accion
            String[] arrayAction = aux.split(":");

            if (arrayAction.length == 2) {
                action = arrayAction[0];
                try {
                    numberAction = Integer.parseInt(arrayAction[1]);
                } catch (NumberFormatException ex) {
                    numberAction = 0;
                    System.out.println("\tError");
                    System.out.println("\t  Clase:  ControllerUpload.java");
                    System.out.println("\t  Metodo: processRequest");
                    System.out.println("\t  Descripcion: El numero de la accion a realizar es invalido. Verifique que se pueda convertir a un valor numerico");
                    ex.printStackTrace();
                }
            }
        }

//        System.out.println("********numberAction: " + numberAction + " ");

        //Se verifica el tipo de operacion a realizar y se solicita una operacion al modelo correspondiente
        switch (numberAction) {

            /**Inicio de autentificación de usuario**/
            case 1:
                String[] vars = {"userApp", "messageBean", "fileLoad", "fileName", "errores", "resumenMovimientos", "imprimeResumenMovimientos", "messageClean", "confirmaUsuario"};
                String correoDestino = "";
                boolean bloqueaUser = false;
                try {
                    // Se obtiene la clave del cliente que intenta entrar al sistema.
                    if (request.getParameter("custNum") != null && !request.getParameter("custNum").equals("")) {
                        custNum = request.getParameter("custNum").toString().trim().toUpperCase();
                    }
                    // Se obtiene el usuario asociado al cliente que intenta entrar al sistema.
                    if (request.getParameter("usuario") != null && !request.getParameter("usuario").equals("")) {
                        usuario = request.getParameter("usuario").toString().trim();
                    }
                    // Se obtiene la contrasenna del usuario.
                    if (request.getParameter("contrasenna") != null && !request.getParameter("contrasenna").equals("")) {
                        contrasenna = request.getParameter("contrasenna").toString().trim();
                    }
                    
                    // Muestra en consola datos recibidos
//                    System.out.println("CLAVE: " + custNum);
//                    System.out.println("USUARIO: " + usuario);
//                    System.out.println("CONTRASENA: " + contrasenna);

                    // Llamamos al modelo para que autentifique al usuario.
                    userApp = ModeloLayOut.validaUsuario(custNum, usuario, contrasenna);
                    // Muestra en consola validación de usuario
//                    System.out.println("VALIDO: " + userApp);
//                    System.out.println("AUTENTICADO: " + userApp.isAutentificado());

                    if (userApp != null && userApp.isAutentificado()) {
                        urlResponse = "menuLoadFile.htm";
                        ///variables que se agregan a session en este apartado:    saldo_por_pagar;saldo_actual;resumen_saldo
                        String infoSaldo = "",arrayInfoSaldos[] = null;
                        infoSaldo = ModeloLayOut.getSaldo_LiqPend_Fid(userApp.getClave_contrato()); 
                        arrayInfoSaldos = infoSaldo.split("%");
                        if(arrayInfoSaldos.length==3){
//                            if(saldo_actual<0){
//                                session.setAttribute("saldo_por_pagar", Math.abs(saldo_actual));}
//                            else{
//                                session.setAttribute("saldo_por_pagar", 0);}
                            
                            session.setAttribute("saldo_actual",Double.parseDouble(arrayInfoSaldos[0]));
                            session.setAttribute("resumen_saldo", "resumen de saldo");
                        }
                    } else {
                        messageBean = new Message();
                        if (userApp == null) {
                            messageBean.setDesc("Error de Conexión, favor de verificar conexión a internet");
                        } else {
                            boolean bloquea = ModeloLayOut.isBloqueaUser();
                            if (bloquea) {
                                correoDestino = ModeloLayOut.getCorreoUsuario(custNum, usuario);
                                if (correoDestino != null) {
                                    if (!correoDestino.equals("")) {
                                        bloqueaUser = ModeloLayOut.regeneraPassword(custNum, usuario, "liquidaciones@fideicomisopsc.mx", correoDestino);
                                        if (bloqueaUser) {
                                            messageBean.setDesc(" Su contraseña ha cambiado por seguridad. Consulte su correo electrónico.");
                                        } else {
                                            messageBean.setDesc("Error regenerando contraseña por seguridad ");
                                        }
                                    } else {
                                        messageBean.setDesc("No se tiene registro de correo electrónico asociado a esta cuenta, consulte a su administrador");
                                    }
                                } else {
                                    messageBean.setDesc("Error de Conexión, favor de verificar conexión a internet");
                                }
                            } else {
                                messageBean.setDesc("Error : Usuario y/o Contraseña inválidos");
                            }
                        }
                        urlResponse = "UserLogin.htm";
                    }
                    // Se sincronizan los beans con la session del usuario
                    synchronized (session) {
                        //Se remueven los objetos especificados de la sesion
                        this.remueveAtributos(vars, session);
                        session.setAttribute("messageBean", messageBean);
                        session.setAttribute("userApp", userApp);
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            /**Fin de autenticación de usuario**/
            /**Inicio de Validación y Carga de Archivo**/
            case 2:
                // Indice de verificación
                int indice = 0;
                float salario_minimo;
                boolean confirmar=false;
                ArrayList tmpErrores = null;
                // Se crea el mesaje de respuesta de la operación
                messageBean = new Message();
                try {
                    // Se obtienen las variables de session
                    userApp = (Usuario) session.getAttribute("userApp");
                    //Se verifica si la sesión aún esta activa.
                    if (userApp != null) {
                        // Se verifica si es un formulario con archivos
                        if (isMultipart) {
                            // Si hay una archivo cargado desde formulario
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
                                        if (extArchivo.equals(".txt") || extArchivo.equals(".TXT")) {
                                            fileName = ModeloLayOut.quitaEspaciosBlancos(fileName);
                                            if (!fileName.equals("")) {
                                                //Verificamos si el archivo fue almacenado anteriormente
                                                if (ModeloLayOut.archivoValidado("", "", FilenameUtils.getName(fileName)).equals("")) {
                                                    // Si no se ha declarado un nombre opcional, se pone el mismo nombre del archivo cargado
                                                    if (optionalFileName.trim().equals("")) {
                                                        fileName = FilenameUtils.getName(fileName);
                                                    } else {
                                                        fileName = optionalFileName;
                                                    }
                                                    // Se crea el archivo a donde se va guardar el contenido del archivo cargado
                                                    File saveTo = new File(dirNameTmp + fileName);
                                                    // Se escribe el contenido del archivo cargado al destino
                                                    fileItem.write(saveTo);
                                                    // Hasta aqui el archivo ya esta en el servidor y se puede leer, escribir, mover, etc.
                                                    String clave_contrato = userApp.getClave_contrato().toString().trim();
                                                    salario_minimo = this.cargaSalarioMinimo();
                                                    //Se realiza la validación del Archivo
                                                    String messageVal = ModeloLayOut.isArchivoValido(dirNameTmp + fileName, clave_contrato, salario_minimo);
                                                    
                                                    // Validación de archivo
                                                    System.out.println("ARCHIVO: " + messageVal);
                                                    
                                                    //Se Verifica si ocurrio algún error al realizar la validación.
                                                    if (messageVal.equals("")) {
                                                        //Se verifica si se detectaron errores al realizar la validación.
                                                        tmpErrores = ModeloLayOut.getErrores();
                                                        if (tmpErrores.isEmpty()) {
                                                            resumenMovimientos = ModeloLayOut.getResumenMov();
                                                            if (resumenMovimientos != null) {
                                                                confirmar = true;
                                                                messageBean.setDesc("Favor de confirmar información");
                                                                session.removeAttribute("resumen_saldo");
                                                            } else {
                                                                //Error al realizar el resumen de los movimientos a guardar
                                                                messageBean.setDesc("Error obteniendo el resumen de liquidación");
                                                            }
                                                        } else {
                                                            errores = new ArrayList();
                                                            if (tmpErrores.size() <= 10) {
                                                                for (int i = 0; i < tmpErrores.size(); i++) {
                                                                    errores.add((String[]) tmpErrores.get(i));
                                                                }
                                                            } else {
                                                                for (int i = 0; i < 10; i++) {
                                                                    errores.add((String[]) tmpErrores.get(i));
                                                                }
                                                            }
                                                            //Errores obtenidos al realizar la validación
                                                            messageBean.setDesc(" Se detectó " + tmpErrores.size() + " error(es) ");
                                                            fileLoad = "Error realizando validación de archivo";
                                                        }
                                                    } else {
                                                        fileLoad = "Error validando archivo";
                                                        messageBean.setDesc(messageVal);
                                                    }
                                                }//Verifica si el archivo ha sido cargado anteriormente.
                                                else {
                                                    messageBean.setDesc(" Archivo cargado anteriormente, favor de verificar. ");
                                                }
                                            } else {
                                                messageBean.setDesc(" Nombre de archivo inválido ");
                                            }
                                        } //Verifica que sea un archivo de texto.
                                        else {
                                            messageBean.setDesc(" Se requiere un archivo de texto. ");
                                        }//Verifica que sera un archivo valido.
                                    } else {
                                        messageBean.setDesc(" Archivo inválido favor de verificar. ");
                                    }
                                } else {
                                    messageBean.setDesc(" Favor de especificar archivo ");
                                }
                                if(!confirmar){session.setAttribute("resumen_saldo", "resumen de saldo");}
                            }
                        }
                        urlResponse = "menuLoadFile.htm";
                    }//Fin de validación de sesión
                    else {
                        messageBean.setDesc(" Tiempo de sesión agotado ");
                        urlResponse = "UserLogin.htm";
                    }
                    // Se sincronizan los beans con la session del usuario
                    synchronized (session) {
                        session.setAttribute("messageBean", messageBean);
                        session.setAttribute("userApp", userApp);
                        session.setAttribute("fileLoad", fileLoad);
                        session.setAttribute("fileName", fileName);
                        session.setAttribute("errores", errores);
                        session.setAttribute("resumenMovimientos", resumenMovimientos);

                        session.removeAttribute("confirmaUsuario");
                        session.removeAttribute("imprimeResumenMovimientos");
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    urlResponse = "menuLoadFile.htm";
                }

                break;
            /**Fin de autenticacion de Validación y Carga de Archivo**/
            /**Inicio de Almacenamiento del Archivo en la BD**/
            case 3:
                String genera = "";
                String guarda = "";
                String correo_o = "";
                String correo_d = "";
                String asunto = "";
                String body = "";
                String urlReporte = "";
                String fecha_l = "";
                String fecha_liquidacion = "";

                boolean carga_l = false;
                messageBean = new Message();
                Connection connection = null;
                clsConexion conn = new clsConexion();
                int idx_archivo = 0;

                try {
                    // Se obtienen las variables de session globales.
                    userApp = (Usuario) session.getAttribute("userApp"); //Información del usuario que entra al sistema
                    fileName = (String) session.getAttribute("fileName"); //Nombre del LayOut que se desea cargar.
                    resumenMovimientos = (ResumenMovimientos) session.getAttribute("resumenMovimientos"); //Resumen de movimientos proporcionados
                    imprimeResumenMovimientos = (ResumenMovimientos) session.getAttribute("resumenMovimientos");

                    if (userApp != null && fileName != null && resumenMovimientos != null && imprimeResumenMovimientos != null) {
                        //Se obtiene la clave del cliente en el sistema.
                        cliente = userApp.getId_cliente();
                        // Se obtiene el usuario que intenta ingresar al sistemas
                        if (request.getParameter("usuario") != null && !request.getParameter("usuario").equals("")) {
                            usuario = request.getParameter("usuario");
                        }
                        // Se Obtiene la Contrasenna del Usuario
                        if (request.getParameter("contrasenna") != null && !request.getParameter("contrasenna").equals("")) {
                            contrasenna = request.getParameter("contrasenna");
                        }
                        if (usuario != null && contrasenna != null) {

                            if (cliente.equals("PRB16082011") && usuario.equals("PRB16082011") && contrasenna.equals("PRB16082011")) {
                                messageClean = new Message();
                                carga_l = true;
                                synchronized (session) {
                                    session.removeAttribute("fileLoad");
                                    session.removeAttribute("fileName");
                                    session.removeAttribute("errores");
                                    session.removeAttribute("confirmaUsuario");
                                    session.removeAttribute("resumenMovimientos");
                                    session.removeAttribute("imprimeResumenMovimientos");

                                    session.setAttribute("messageBean", messageBean);
                                    session.setAttribute("userApp", userApp);
                                    if (carga_l) {
                                        session.setAttribute("imprimeResumenMovimientos", imprimeResumenMovimientos);
                                        session.setAttribute("messageClean", messageClean);
                                    }
                                }
                            } else {
                                // Llamando al modelo para autentificar al usuario.
                                Usuario userApp1 = ModeloLayOut.validaUsuario(cliente, usuario, contrasenna);
                                //Verificamos la Autentificación del Usuario.
                                if (userApp1 != null && userApp1.isAutentificado()) {
                                    correo_o = "liquidaciones@fideicomisopsc.mx";
                                    //Clave asociada al contrato del cliente.
                                    String clave_contrato = (String) userApp.getClave_contrato();
                                    modelo_l = new ModeloLayOut();
                                    //Obtener saldo y liquidacion total de movimientos pendientes por procesar=>A y por operar=>P
                                    //Se realiza la consulta, antes de actualizar la BD con los
//                                    String infoSaldo = "";
//                                    infoSaldo = ModeloLayOut.getSaldo_LiqPend_Fid(clave_contrato);                                    
                                    //Se almacena el Lote en la Base de Datos. movimientos y movimientos_h
                                    System.out.println("Almacenando movimientos en base de datos ......");
                                    guarda = modelo_l.cargaLayOut(clave_contrato, cliente, fileName, userApp.getUsuario(), dirNameTmp, correo_o, correo_o);
                                    if (guarda.equals("")) {
                                        System.out.println("Movimientos almacenados correctamente en la base de datos .....");
                                        fecha_liquidacion = ModeloLayOut.getFechaLiquidacion(clave_contrato, cliente, fileName); //Fecha de Liquidación.
                                        fecha_l = ModeloLayOut.getFormatoFecha(fecha_liquidacion); // DDMMYYYY --> YYMMDD.
                                        if (!fecha_l.equals("")) {
                                            
                                            // Url local
                                            //urlReporte = ".\\Reportes Liquidacion\\" + clave_contrato + "\\" + fecha_l;
                                            // Url servidor
                                            urlReporte = ".\\inetpub\\ftproot\\Reportes Liquidacion\\" + clave_contrato + "\\" + fecha_l;
                                            idx_archivo = ModeloLayOut.getClaveArchivo(clave_contrato, fecha_liquidacion, fileName);
//                                            if(!infoSaldo.equals("") && !infoSaldo.equals("0.0%def%0.0")){
                                            //Generamos el reporte de liquidación.
                                            String realPath = getServletContext().getRealPath(File.separator);
                                            genera = modelo_l.genera_RL(clave_contrato, fecha_liquidacion, fecha_l, fileName, resumenMovimientos, idx_archivo, urlReporte, realPath);
                                            if (genera.equals("")) {
                                                asunto = "LIQUIDACIÓN " + clave_contrato + " " + fecha_liquidacion;
                                                correo_d = ModeloLayOut.obtenCorreos("'SISTEMAS','OPERACION'");
                                                if (correo_d.equals("")) {
                                                    body = "Correo NO enviado al personal de la SOFOM debido a lista de correos vacia, favor de verificar";
                                                    correo_d = "soporte@fideicomisopsc.mx";
                                                }
                                                String nombre_r = ModeloLayOut.getNombreResumenLiquidacion(clave_contrato, fecha_l, fileName, idx_archivo);
                                                if (!nombre_r.equals("")) {
                                                    urlReporte = urlReporte + "\\";
                                                    //Enviamos el Reporte de Liquidación por correo electrónico.
                                                    guarda = modelo_l.enviaReporteLiquidacion(correo_o, correo_d, asunto, body, urlReporte, nombre_r);                                                    
                                                    if (guarda.equals("")) {
                                                        if(guarda.equals("")){
//                                                        session.setAttribute("saldo_por_pagar", Double.parseDouble(resumenMovimientos.getNuevo_saldo()));
                                                        session.setAttribute("resumen_saldo", "mostrar resumen");
                                                        messageClean = new Message();
                                                        carga_l = true;}
//                                                        else{
//                                                            messageBean.setDesc(guarda);
//                                                        }
                                                    } else {
                                                        messageBean.setDesc(guarda);
                                                    }
                                                } else {
                                                    messageBean.setDesc("Error obteniendo el nombre del reporte de liquidación");
                                                }
                                            } else {
                                                //Error al generar reporte de liquidación.
                                                messageBean.setDesc(genera);
                                                }
//                                            }
//                                            else {
//                                                //Error al generar reporte de liquidación.
//                                                messageBean.setDesc("Error al consultar y generar el saldo del fideicomiso.");
//                                            }
                                        } else {
                                            messageBean.setDesc("Error obteniendo el formato de la fecha de liquidación");
                                        }
                                    } else {
                                        //Error almacenando movimientos.
                                        messageBean.setDesc(guarda);
                                    }
                                    // Se sincronizan los beans con la sessión del usuario
                                    synchronized (session) {
                                        session.removeAttribute("fileLoad");
                                        session.removeAttribute("fileName");
                                        session.removeAttribute("errores");
                                        session.removeAttribute("confirmaUsuario");
                                        session.removeAttribute("resumenMovimientos");
                                        session.removeAttribute("imprimeResumenMovimientos");

                                        session.setAttribute("messageBean", messageBean);
                                        session.setAttribute("userApp", userApp);
                                        if (carga_l) {
                                            session.setAttribute("imprimeResumenMovimientos", imprimeResumenMovimientos);
                                            session.setAttribute("messageClean", messageClean);
                                        }
                                    }
                                } else {//Fin de Valida Usuario.
                                    if (userApp == null) {
                                        messageBean.setDesc(" Error validando usuario ");
                                    } else {
                                        messageBean.setDesc(" Usuario y/o Contraseña inválidos ");
                                    }
                                    synchronized (session) {
                                        session.setAttribute("messageBean", messageBean);
                                    }
                                }
                            }
                        } else {
                            messageBean.setDesc("Error obteniendo datos capturados");
                            synchronized (session) {
                                session.setAttribute("messageBean", messageBean);
                            }
                        }
                        urlResponse = "menuLoadFile.htm";
                    } else {
                        messageBean.setDesc(" Tiempo de sesión agotado ");
                        synchronized (session) {
                            session.setAttribute("messageBean", messageBean);
                        }
                        urlResponse = "UserLogin.htm";
                    }
                } catch (Exception e) {
                    //Cerramos la conexión a la Base de Datos.
                    if (connection != null) {
                        conn.Desconecta(connection);
                    }
                    System.out.println(e.getMessage());
                    urlResponse = "menuLoadFile.htm";
                }

                break;
            /**Fin de Almacenamiento del Archivo en la BD**/
            /**Inicio de Impresión del resumen del importe total capturado.**/
            case 4:
                break;

            case 5:
                confirmaUsuario = "confirmaUsuario";
                userApp = (Usuario) session.getAttribute("userApp");
                try {
                    if (userApp != null) {
                        synchronized (session) {
                            session.setAttribute("confirmaUsuario", confirmaUsuario);
                        }
                        urlResponse = "menuLoadFile.htm";
                    } else {
                        messageBean = new Message();
                        messageBean.setDesc(" Tiempo de sesión agotado ");
                        synchronized (session) {
                            session.setAttribute("messageBean", messageBean);
                        }
                        urlResponse = "UserLogin.htm";
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    urlResponse = "menuLoadFile.htm";
                }

                break;

            case 6:
                try {
                    userApp = (Usuario) session.getAttribute("userApp");
                    if (userApp != null) {
                        synchronized (session) {
                            session.removeAttribute("errores");
                            session.removeAttribute("confirmaUsuario");
                            session.removeAttribute("resumenMovimientos");
                            session.removeAttribute("imprimeResumenMovimientos");
                        }
                        urlResponse = "PasswordChange.htm";
                    } else {
                        messageBean = new Message();
                        messageBean.setDesc(" Tiempo de sesión agotado ");
                        synchronized (session) {
                            session.setAttribute("messageBean", messageBean);
                        }
                        urlResponse = "UserLogin.htm";
                    }

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    urlResponse = "PasswordChange.htm";
                }

                break;

            case 7:
                try {
                    //Obtenemos los datos del usuario autentificado al inicio.
                    userApp = (Usuario) session.getAttribute("userApp");
                    messageBean = new Message();
                    boolean nuevo_password = false;

                    if (userApp != null) {
                        String custNum1 = userApp.getId_cliente().toUpperCase();
                        String usuario1 = userApp.getUsuario();
                        String contrasenna1 = userApp.getPassword();
                        //Obtenemos los datos del usuario que desea cambiar el Password
                        if (request.getParameter("custNum") != null && !request.getParameter("custNum").equals("")) {
                            custNum = request.getParameter("custNum").toUpperCase().toString().trim();
                        }
                        // Se obtiene el usuario que intenta entrar al sistemas
                        if (request.getParameter("usuario") != null && !request.getParameter("usuario").equals("")) {
                            usuario = request.getParameter("usuario").toString().trim();
                        }
                        // Se obtiene la contrasenna del usuario
                        if (request.getParameter("contrasenna") != null && !request.getParameter("contrasenna").equals("")) {
                            contrasenna = request.getParameter("contrasenna").toString().trim();
                        }
                        if (custNum != null && usuario != null && contrasenna != null) {
                            if (custNum1.equals(custNum) && usuario1.equals(usuario) && contrasenna1.equals(contrasenna)) {
                                cambiaPassword = "cambiaPassword";
                                nuevo_password = true;
                                messageBean.setDesc(" Introduzca nueva contraseña ");
                            } else {
                                messageBean.setDesc(" Error: Usuario y/o Contraseña inválidos ");
                            }
                        } else {
                            messageBean.setDesc("Error obteniendo datos capturados");
                        }
                        urlResponse = "PasswordChange.htm";
                    } else {
                        messageBean.setDesc(" Tiempo se sesión agotado ");
                        urlResponse = "UserLogin.htm";
                    }
                    synchronized (session) {
                        session.setAttribute("messageBean", messageBean);
                        if (nuevo_password) {
                            session.setAttribute("cambiaPassword", cambiaPassword);
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    urlResponse = "PasswordChange.htm";
                }
                break;

            case 8:
                //Obtenemos los datos del usuario autentificado al inicio.
                userApp = (Usuario) session.getAttribute("userApp");
                messageBean = new Message();
                String newPass1 = null;
                String newPass2 = null;
                boolean tmp_cambia = false;
                try {
                    if (userApp != null) {
                        usuario = userApp.getUsuario();
                        //Obtenemos el nuevo Password
                        if (request.getParameter("newPass1") != null && !request.getParameter("newPass1").equals("")) {
                            newPass1 = request.getParameter("newPass1").toString().trim();
                        }
                        // Confirmamos el nuevo Password
                        if (request.getParameter("newPass2") != null && !request.getParameter("newPass2").equals("")) {
                            newPass2 = request.getParameter("newPass2").toString().trim();
                        }
                        if (newPass1 != null && newPass2 != null) {
                            if (!newPass1.equals("") && !newPass2.equals("")) {
                                if (newPass1.length() >= 8 && !newPass1.equals(usuario)) {
                                    if (newPass1.equals(newPass2)) {
                                        boolean actualiza = ModeloLayOut.actualizaUsuario(userApp, newPass1);
                                        if (actualiza) {
                                            userApp.setPassword(newPass1);
                                            messageBean.setDesc(" Contraseña actualizada correctamente. ");
                                            tmp_cambia = true;
                                            urlResponse = "menuLoadFile.htm";
                                        } else {
                                            messageBean.setDesc(" Error actualizando contraseña ");
                                            tmp_cambia = true;
                                            urlResponse = "menuLoadFile.htm";
                                        }
                                    } else {
                                        messageBean.setDesc(" La nueva contraseña no coincide con la confirmación. ");
                                        urlResponse = "PasswordChange.htm";
                                    }
                                } else {
                                    messageBean.setDesc("La contraseña debe tener al menos 8 caracteres alfanuméricos y debe ser diferente al nombre de usuario");
                                    urlResponse = "PasswordChange.htm";
                                }
                            } else {
                                messageBean.setDesc("Favor de capturar la nueva contraseña");
                                urlResponse = "PasswordChange.htm";
                            }
                        } else {
                            messageBean.setDesc(" Error obteniendo datos capturados ");
                            tmp_cambia = true;
                            urlResponse = "menuLoadFile.htm";
                        }
                    } else {
                        messageBean.setDesc(" Tiempo se sesión agotado ");
                        urlResponse = "UserLogin.htm";
                    }
                    synchronized (session) {
                        session.setAttribute("messageBean", messageBean);
                        session.setAttribute("userApp", userApp);
                        if (tmp_cambia) {
                            session.removeAttribute("cambiaPassword");
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;

            case 9: //Regres una sesion anterior en cambiaPassword
                try {
                    userApp = (Usuario) session.getAttribute("userApp");
                    if (userApp != null) {
                        synchronized (session) {
                            session.removeAttribute("cambiaPassword");
                        }
                        urlResponse = "menuLoadFile.htm";
                    } else {
                        messageBean = new Message();
                        messageBean.setDesc(" Tiempo de sesión agotado ");
                        synchronized (session) {
                            session.setAttribute("messageBean", messageBean);
                        }
                        urlResponse = "UserLogin.htm";
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    urlResponse = "menuLoadFile.htm";
                }

                break;

            /**Inicialización del formulario para capturar movimientos de manera manual.**/
            case 10:
                userApp = (Usuario) session.getAttribute("userApp");
                try {
                    if (userApp != null) {
                        String obsRemov = "imprimeResumenMovimientos;confirmaUsuario;resumenMovimientos;errores;fecha_liquidacion;"
                                + "tipo_movimiento;clave_banco;clave_moneda;nombreFideicomisario;apellidoPaterno;"
                                + "apellidoMaterno;fechaIngreso;puesto;departamento;claveFideicomisario;CURP;RFC;cuentaDeposito;"
                                + "importe_liquidacion;envioCheque;destinoCheque;telefonoCheque;correoCheque;bancoExtranjero;"
                                + "dirBancoExtranjero;paisBancoExtranjero;ABA_BIC;nombreFideiBancoExtranjero;"
                                + "dirFideiBancoExtranjero;paisFideiBancoExtranjero;telFideiBancoExtranjero;"
                                + "resumenMovs;confirmaUsuario;imprimeResumen;messageClean;movimientosIngresados";
                        //Especificamos los objetos a remover de la sesion
                        nombresObjetos = obsRemov.split(";");
                        //Se remueven los objetos especificados de la sesion
                        synchronized (session) {
                            this.remueveAtributos(nombresObjetos, session);
                        }
                                    //----ESTO NO PERMITE COMPARTIR RECURSOS CORRECTAMENTE
                                    //----ModeloCapture.setMovimientosCargados(new Vector());
                                    //----ModeloCapture.setFechaLiquidacion("");
                        //Obtenemos del modelo los diferentes tipos de movimiento existentes.
                        Vector tiposMovimiento = ModeloCapture.getTiposMovimiento();
                        //Vector clavesBanco = ModeloCapture.getClavesBancoABM();
                        Vector clavesBanco = ModeloCapture.getAbreviacionesBancosABM();
                        Vector clavesMoneda = ModeloCapture.getClavesMoneda();
                        if (tiposMovimiento != null && clavesBanco != null && clavesMoneda != null) {
                            //Se sincroniza la session del usuario.
                            synchronized (session) {
                                session.setAttribute("tiposMovimiento", tiposMovimiento);
                                session.setAttribute("clavesBanco", clavesBanco);
                                session.setAttribute("clavesMoneda", clavesMoneda);
                                urlResponse = "ManualCaptureMovements.htm";
                            }
                        } else {
                            messageBean = new Message();
                            messageBean.setDesc(" Error obteniendo información de captura manual ");
                            synchronized (session) {
                                session.setAttribute("messageBean", messageBean);
                            }
                            urlResponse = "menuLoadFile.htm";
                        }
                    } else {
                        messageBean = new Message();
                        messageBean.setDesc(" Tiempo de sessión agotado ");
                        synchronized (session) {
                            session.setAttribute("messageBean", messageBean);
                        }
                        urlResponse = "UserLogin.htm";
                    }

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    urlResponse = "menuLoadFile.htm";
                }
                break;
            /**Fin de la inicialización del formulario para capturar movimientos de manera manual.**/
            /**Inicio de eliminacion de objetos de la sesion actual**/
            case 100:
                //Se genera el arreglo con los nombres de los objetos a remover request.getParameter("accion")

                if (!request.getParameter("nombresObjetos").equals("")) {
                    nombresObjetos = request.getParameter("nombresObjetos").split(";");
                }

                //Se obtiene el nombre de la página a donde se redigira
                if (!request.getParameter("urlResponse").equals("")) {
                    urlResponse = request.getParameter("urlResponse");
                }

                //Se remueven los objetos especificados de la sesion
                this.remueveAtributos(nombresObjetos, session);
                session.setAttribute("resumen_saldo", "Mostrar informacion de saldo");
                break;
            /**Fin de eliminacion de objetos de la sesion actual**/
        }//Fin del switch

//        System.out.println("Redirigiendo: " + urlResponse);
        // Se redirecciona a la pagina correspondiente
        response.sendRedirect(urlResponse);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (FileUploadException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (FileUploadException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    /**Metodo que permite remover los atributos especificados de la sesion pasada como argumento
     * @param nombresAtributos
     * @param session
     */
    public void remueveAtributos(String nombresAtributos[], HttpSession session) {
        for (int i = 0; i < nombresAtributos.length; i++) {
            session.removeAttribute(nombresAtributos[i]);
        }
    }

    public boolean despliega_PDF(HttpServletResponse response, HashMap parametros, String nombre_jasper, Connection lcnnConexion) throws ServletException, IOException {
        boolean genera = false;
        ServletOutputStream servletOutputStream = response.getOutputStream();
        File reportFile = new File("./Common/ReporteLiquidacion.jasper");
        byte[] bytes = null;

        try {
            bytes = JasperRunManager.runReportToPdf(reportFile.getPath(), parametros, lcnnConexion);
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);

            servletOutputStream.write(bytes, 0, bytes.length);
            servletOutputStream.flush();
            servletOutputStream.close();
            genera = true;
        } catch (JRException e) {
            genera = false;
            // display stack trace in the browser
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            e.printStackTrace(printWriter);
            response.setContentType("text/plain");
            response.getOutputStream().print(stringWriter.toString());
        }
        return genera;
    }

    private float cargaSalarioMinimo() {
        String fName = "./salario_minimo.txt";
        File fp = new File(fName);
        float salarioMinimo = 88.36F;

        try  {
            if (fp.exists()) {
                String line = null;
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fp)));
                if ((line = reader.readLine()) != null) {
                    salarioMinimo = Float.valueOf(line.trim());
                }
            } else {
                fp.createNewFile();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            return salarioMinimo;
        }
    }

    private boolean guardarSalarioMinimo(String salarioMinimo) {
        String fName = "./salario_minimo.txt";
        File fp = new File(fName);

        if (fp.exists()) {
            String line = null;
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fp)))) {
                writer.write(salarioMinimo);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                return false;
            }
        }

        return true;
    }
}
