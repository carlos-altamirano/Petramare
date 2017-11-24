/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Beans.Message;
import Beans.Usuario;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Luis-Valerio
 */
public class DescargaEdoCta extends HttpServlet {

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

        Message messageBean = null;
        Usuario userApp = null;
        HttpSession session = request.getSession();

        try {
            userApp = (Usuario) session.getAttribute("userApp");
            if (userApp != null) {

                String nombreArchivo = request.getParameter("nombreRL");
                String accion = request.getParameter("accion");
                if ("descargaEdoCta".equals(accion) && nombreArchivo != null && !nombreArchivo.equals("")) {
//                            String zipName = nombreArchivo + ".zip";
                    String zipName = request.getParameter("selComboPeriodo");
//                            System.out.println("ziName" + zipName);
//                            String zipName = nombreArchivo;
//                            zipName = "EDOCTA_FID158AZO0514_2014_07.zip";
                    String datos[] = null;
                    String urlZip = "C:\\inetpub\\ftproot\\EstadosDeCuenta\\" + session.getAttribute("clave_contratoC").toString() + "\\";
//                    urlZip = "C:\\inetpub\\ftproot\\EstadosDeCuenta\\FID158AZO0514\\";
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

            } else {
                messageBean = new Message();
                messageBean.setDesc(" Favor de iniciar sesion. ");
                session.setAttribute("messageBean", messageBean);
                response.sendRedirect("login.htm");
            }
        } catch (Exception ex) {
            System.out.println("Exception MuetraMovimientosFiduciarios:" + ex.getMessage());
            response.setHeader("Content-Disposition", "");
            response.setContentType("text/plain");
            messageBean = new Message();
            messageBean.setDesc(" Error actualizando el archivo. ");
            session.setAttribute("messageBean", messageBean);
        }


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
