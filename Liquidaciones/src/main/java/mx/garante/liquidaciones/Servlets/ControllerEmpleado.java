package mx.garante.liquidaciones.Servlets;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.datacontract.schemas._2004._07.tes_tfd_v33.RespuestaValidacionRFC;
import mx.garante.liquidaciones.Beans.Empleado;
import mx.garante.liquidaciones.Common.EnvioMail;
import mx.garante.liquidaciones.Modelos.ModeloCapture;
import mx.garante.liquidaciones.Modelos.ModeloEmpleado;
import org.tempuri.IWSCFDI33;
import org.tempuri.WSCFDI33;

public class ControllerEmpleado extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String accion = request.getParameter("accion");
        if (accion != null) {
            
            ModeloEmpleado empleadoDAO = new ModeloEmpleado();
            System.out.println("ControllerEmpleado " + accion);
            switch (Integer.parseInt(accion.split(":")[1])) {
                case 1: {
                    String rfc = request.getParameter("rfc");
//                    Empleado empleado = empleadoDAO.buscaRFC(rfc);
                    Empleado empleado = ModeloCapture.getInfoCFDI(rfc);
                    PrintWriter out = response.getWriter();
                    out.println(empleado);
                    out.close();
                    break;
                }
                case 2: {
                    String rfc = request.getParameter("buequeda");
                    String curp = request.getParameter("curp");
                    String email = request.getParameter("email");

                    final Empleado emp = ModeloCapture.getInfoCFDI(rfc);

                    if (emp!= null) {
                        if (emp.getCurp().equals(curp)) {
                            emp.setEmail(email);
                            String contrasena = generaContra(6);
                            emp.setContra(contrasena);
                            boolean res = empleadoDAO.activa(emp);
                            if (res) {
                                Thread hilo = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        enviaCorreoActivacion(emp);
                                    }
                                });
                                hilo.start();
                                //todo correcto
                                response.sendRedirect("EmpleadoLogin.htm?activa=4");
                            } else {
                                //error al activar
                                response.sendRedirect("NuevaCuentaEmpleado.htm?activa=3");
                            }
                        } else {
                            //Error coincidencia
                            response.sendRedirect("NuevaCuentaEmpleado.htm?activa=2");
                        }
                    } else {
                        //empleado no encontrado
                        response.sendRedirect("NuevaCuentaEmpleado.htm?activa=1");
                    }
                    break;
                }
                case 3: {
                    String user = request.getParameter("user");
                    String pass = request.getParameter("password");
                    Empleado emp2 = empleadoDAO.buscaEmpleado(user, pass);
                    if (emp2 != null) {
                        HttpSession sesion = request.getSession();
                        sesion.setAttribute("sesionidUser", emp2.getId());
                        sesion.setAttribute("sesionUser", emp2.getRfc());
                        sesion.setAttribute("sesionName", emp2.getNombre() + " " + emp2.getAppat() + " " + emp2.getApmat());
                        response.sendRedirect("ArchivosEmpleado.htm");
                    } else {
                        response.sendRedirect("EmpleadoLogin.htm?login=error");
                    }
                    break;
                }
                case 4: {
                    HttpSession sesion = request.getSession();
                    sesion.removeAttribute("sesionidUser");
                    sesion.removeAttribute("sesionUser");
                    sesion.removeAttribute("sesionName");
                    response.sendRedirect("EmpleadoLogin.htm?login=ok");
                    break;
                }
                case 5: {//cambiar contraseña
                    String userName = (String) request.getSession().getAttribute("sesionUser");
                    if (userName != null) {
                        String passwo = request.getParameter("actual");
                        String newPass = request.getParameter("nueva");
                        String newPass2 = request.getParameter("nueva2");
                        Integer idUser = (Integer) request.getSession().getAttribute("sesionidUser");
                        final Empleado empCambiaUser = empleadoDAO.busca(idUser);
                        if (empCambiaUser != null) {
                            if (empCambiaUser.getContra().equals(passwo)) {
                                if (newPass.equals(newPass2)) {
                                    empCambiaUser.setContra(newPass2);
                                    if (empleadoDAO.cambiaPassword(empCambiaUser)) {
                                        Thread hilo = new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                enviaCorreoActivacion(empCambiaUser);
                                            }
                                        });
                                        hilo.start();
                                        response.sendRedirect("CambiaContra.htm?cambio=ok");
                                    } else {
                                        response.sendRedirect("CambiaContra.htm?cambio=error");
                                    }
                                } else {
                                    response.sendRedirect("CambiaContra.htm?newPass=error");
                                }
                            } else {
                                response.sendRedirect("CambiaContra.htm?contraActual=error");
                            }
                        }
                        
                    } else {
                        response.sendRedirect("EmpleadoLogin.htm?login=no");
                    }
                    break;
                }
                case 6: {//Descarga Archivos
                    Empleado emp3 = empleadoDAO.busca((Integer) request.getSession().getAttribute("sesionidUser"));
                    String archivo = request.getParameter("archivo");
                    String anio0 = request.getParameter("anio");
                    String ruta = ".\\inetpub\\ftproot\\CFDI\\"+emp3.getRfc()+"\\"+ anio0 + "\\" +archivo;
                    try {
                        descargar(ruta, response);
                    } catch (Exception ex) {
                        System.out.println("Error descarga archivo " + ex);
                    }
                    break;
                }
                case 7: {
                    Empleado emp4 = empleadoDAO.busca((Integer) request.getSession().getAttribute("sesionidUser"));
                    String anio = request.getParameter("anio");
                    List<String> lista = new ArrayList<>();
                    String rutaLista = ".\\inetpub\\ftproot\\CFDI\\"+ emp4.getRfc()+"\\"+anio;
                    File folder = new File(rutaLista);
                    File[] listFiles = folder.listFiles();
                    for (File file : listFiles) {
                        String nombre = file.getName();
                        lista.add(nombre);
                    }
                    PrintWriter salidaJSON = response.getWriter();
                    salidaJSON.println(new Gson().toJson(lista));
                    salidaJSON.close();
                    break;
                }
                case 8: {
                    String rfcAValidar = request.getParameter("rfc");
                    PrintWriter salida = response.getWriter();
                    salida.println(ModeloCapture.validaRFC(rfcAValidar));
                    salida.close();
                    break;
                }
                case 9: {
                    
                    String rfc = request.getParameter("rfc");
                    response.setContentType("text/html;charset=UTF-8");
                    PrintWriter out = response.getWriter();
                    if (rfc.length() == 13) {
                        String expresion = "[A-Z&Ñ]{4}[0-9]{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])[A-Z0-9]{2}[0-9A]";
                        if (rfc.matches(expresion)) {
                            try {
                                IWSCFDI33 servicio = new WSCFDI33().getSoapHttpEndpoint();
                                RespuestaValidacionRFC respuesta = servicio.validarRFC("GDS160406V45", "HzrG4KXEe%", rfc);
                                if (respuesta.isCancelado()) {
                                    out.println("Este RFC esta Cancelado por el SAT");
                                } else {
                                    if (!respuesta.isRFCLocalizado()) {
                                        out.println("RFC no encontrado en el SAT");
                                    }
                                }
                            } catch(Exception e) {
                                out.println("Error para verificar RFC favor de intentarlo mas tarde");
                            }
                        } else {
                            out.println("No es un RFC con estructura valida");
                        }
                        out.close();
                    }
                    break;
                }
                case 10: {
                    Empleado emp4 = empleadoDAO.busca((Integer) request.getSession().getAttribute("sesionidUser"));
                    List<String> lista = new ArrayList<>();
                    String rutaLista = ".\\inetpub\\ftproot\\CFDI\\"+ emp4.getRfc();
                    File folder = new File(rutaLista);
                    if (folder.exists()) {
                        String[] listFiles = folder.list();
                        for (String nombre : listFiles) {
                            lista.add(nombre);
                        }
                        PrintWriter salidaJSON = response.getWriter();
                        salidaJSON.println(new Gson().toJson(lista));
                        salidaJSON.close();
                    }
                }
            }
        } else {
            response.sendRedirect("EmpleadoLogin.htm?login=no");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
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
     * Handles the HTTP <code>POST</code> method.
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

    private void descargar(String ruta, HttpServletResponse response) throws Exception {
        File file = new File(ruta);
        InputStream is = new FileInputStream(file);
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
        OutputStream os = response.getOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = is.read(buffer)) != -1) {
            os.write(buffer, 0, len);
        }
        os.flush();
        os.close();
        is.close();
    }
    
    public void enviaCorreoActivacion(Empleado empleado) {
        try {
            String html = "<h4>Hola " + empleado.getNombre() + " " + empleado.getAppat() + " " + empleado.getApmat() + " <br><br>Esta es la informaci&oacute;n con la cual podras ingresar al Kiosco recuperaci&oacute;n CFDI.</h4>\n" +
                "    <table border=\"1\">\n" +
                "        <thead>\n" +
                "            <tr>\n" +
                "                <th>RFC</th>\n" +
                "                <th>Contrase&ntilde;a</th>\n" +
                "            </tr>\n" +
                "        </thead>\n" +
                "        <tdoby>\n";
                    html += "<tr>\n";
                    html += "<td>"+empleado.getRfc()+"</td>\n";
                    html += "<td>"+empleado.getContra()+"</td>\n";
                    html += "</tr>";
                html += "</tdoby>\n" +
                "    </table>";
            if (!EnvioMail.enviaCorreo2("liquidaciones@fideicomisopsc.mx", empleado.getEmail(), "Kiosco recuperación CFDI", html)) {
                Thread.sleep(180000);
                EnvioMail.enviaCorreo2("liquidaciones@fideicomisopsc.mx", empleado.getEmail(), "Kiosco recuperación CFDI", html);
            }
        } catch (InterruptedException ex) {
            System.out.println("Error al enviar correo " + ex);
        }
    }
    
    public String generaContra(int longitud) {
        String cadenaAleatoria = "";
        long milis = new java.util.GregorianCalendar().getTimeInMillis();
        Random r = new Random(milis);
        int i = 0;
        while (i < longitud) {
            char c = (char) r.nextInt(255);
            if ((c >= '0' && c <= '9') || (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) {
                cadenaAleatoria += c;
                i++;
            }
        }
        return cadenaAleatoria;
    }
    
}
