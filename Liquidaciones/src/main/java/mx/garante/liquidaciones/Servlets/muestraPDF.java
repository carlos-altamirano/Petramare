package mx.garante.liquidaciones.Servlets;

import mx.garante.liquidaciones.Beans.Message;
import mx.garante.liquidaciones.Beans.Usuario;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mx.garante.liquidaciones.Modelos.ModeloLayOut;

public class muestraPDF extends HttpServlet {

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

        Message mensaje = null;

        try {
            HttpSession session = request.getSession(false);
            if (session != null && !session.isNew()) {
                Usuario usuarioApp = null;
                usuarioApp = (Usuario) session.getAttribute("userApp");
                if (usuarioApp != null) {
                    try {

                        String fecha_autorizacion = request.getParameter("fecha_liquidacion");
                        String nombreArchivo = request.getParameter("nombreRL");
                        String accion = request.getParameter("accion");
                        if (accion != null && !"descargaEdoCta".equals(accion) && fecha_autorizacion != null && !fecha_autorizacion.equals("") && nombreArchivo != null && !nombreArchivo.equals("")) {
                            String pdfFileName = nombreArchivo;
//                            System.out.println("Formato fecha=" + fecha_autorizacion);
                            String fechaBD = fecha_autorizacion.substring(0, 10);
//                            System.out.println("Clave de fideicomiso: " + usuarioApp.getClave_contrato());
//                            System.out.println("fechBD= " + fechaBD);
//                            System.out.println("Nombre archivo= " + nombreArchivo);
                            String idArch_fechLiq = ModeloLayOut.getClaveArchivo_nombreArchivo_fechaLiq(usuarioApp.getClave_contrato(), fechaBD, nombreArchivo);
                            String datos[] = null;
                            datos = idArch_fechLiq.split(";");
//                            for (String string : datos) {
//                                System.out.println("Dto:" + string);
//                            }                            
                            String fecha_corta = ModeloLayOut.getFormatoFechaBaseDatos(datos[1]);
                            String nombre_archivo = ModeloLayOut.getNombreResumenLiquidacion(usuarioApp.getClave_contrato(), fecha_corta, nombreArchivo, Integer.parseInt(datos[0]));
//                    pdfFileName = "hola.pdf";
                            pdfFileName = nombre_archivo;
                            String urlReporte = ".\\inetpub\\ftproot\\Reportes Liquidacion\\" + usuarioApp.getClave_contrato() + "\\" + fecha_corta + "\\";
                            File pdfFile = new File(urlReporte + pdfFileName);
                            response.setContentType("aplication/pdf;");
                            response.addHeader("Content-Disposition", "attachment; filename=" + pdfFileName);
                            response.setContentLength((int) pdfFile.length());

                            FileInputStream fileInputStream = new FileInputStream(pdfFile);
                            OutputStream responseOutputStream = response.getOutputStream();
                            int bytes;
                            while ((bytes = fileInputStream.read()) != -1) {
                                responseOutputStream.write(bytes);
                            }
//                    response.sendRedirect("Saldo.do");
                        } else if ("descargaEdoCta".equals(accion) && nombreArchivo != null && !nombreArchivo.equals("")) {
                            String zipName = request.getParameter("selComboPeriodo");
                            String datos[] = null;
                            String urlZip = ".\\inetpub\\ftproot\\EstadosDeCuenta\\" + usuarioApp.getClave_contrato() + "\\";
                            File zipFile = new File(urlZip + zipName);
                            response.setContentType("application/zip;");
                            response.addHeader("Content-Disposition", "attachment; filename=" + zipName);
                            response.setContentLength((int) zipFile.length());

                            FileInputStream fileInputStream = new FileInputStream(zipFile);
                            OutputStream responseOutputStream = response.getOutputStream();
                            int bytes;
                            while ((bytes = fileInputStream.read()) != -1) {
                                responseOutputStream.write(bytes);
                            }
                        }

                    } catch (Exception e) {
                        System.out.println("ExceptionInterno muestraPDF.do:" + e.getMessage());
                        response.setHeader("Content-Disposition", "");
                        Message messageBean = new Message();
                        messageBean.setDesc("Archivo no disponible.");
                        session.setAttribute("messageBean", messageBean);
                        response.setContentType("text/plain");
                        response.sendRedirect("Saldo.do");
                    }
                } else {
                    mensaje = new Message();
                    mensaje.setDesc("Tiempo se session agotado.");
                    session.setAttribute("messageBean", mensaje);
                    response.sendRedirect("index.htm");
                }

            } else {
                mensaje = new Message();
                mensaje.setDesc("Error de Conexión, favor de verificar conexión a internet");
                session.setAttribute("messageBean", mensaje);
                response.sendRedirect("index.htm");
            }

        } catch (Exception e) {
            System.out.println("Exception muestraPDF.do:" + e.getMessage());
            response.sendRedirect("UserLogin.htm");
        } finally {
//            out.close();
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
}
