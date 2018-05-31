package mx.garante.liquidacionriesgoslaborales.Servlets;

import mx.garante.liquidacionriesgoslaborales.Beans.Message;
import mx.garante.liquidacionriesgoslaborales.Beans.Usuario;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mx.garante.liquidacionriesgoslaborales.Modelos.AuthorizationModel;

public class DescargaArchivo extends HttpServlet {

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
//            System.out.println("Entra a DescargaArchivo");
        Message messageBean = null;
        Usuario userApp = null;
        String pdfFileName = "consulta.csv";
        boolean correcto = false;
        HttpSession session = request.getSession();

        //Se obtienen los clientes que tienen movimientos pendientes por operar.             
        try {
            userApp = (Usuario) session.getAttribute("userApp");
            if (userApp != null) {
                String vector_info = request.getParameter("vector_info");
                if (vector_info != null && !vector_info.equals("")) {
                    System.out.println("La variable a descargar es:" + vector_info);
                    if (vector_info.equals("vector_consulta")) {
                        Vector vector_consulta = (Vector) session.getAttribute("vector_consulta");
                        Vector vector_consulta_total = (Vector) session.getAttribute("vector_consulta_total");
                        if (vector_consulta != null && vector_consulta_total != null) {
                            vector_consulta.add(vector_consulta_total);
                            correcto = AuthorizationModel.generaArchivoDeConsulta(vector_consulta, "consulta_ejecutiva");
                            pdfFileName = "consulta_ejecutiva.csv";
                        }
                    }
                    if (vector_info.equals("movimientos_detalle")) {
                        Vector vector_consulta = (Vector) session.getAttribute("movimientos_detalle");
                        if (vector_consulta != null) {
                            correcto = AuthorizationModel.generaArchivoDeConsulta(vector_consulta, "movimientos_detalle");
                            pdfFileName = "movimientos_detalle.csv";
                        }
                    }
                    if (vector_info.equals("descarga_saldos")) {
                            correcto = AuthorizationModel.generaConsultaSaldos(request.getParameter("fechaFin"));
                            pdfFileName = "saldos.csv";
                    }
                    if (vector_info.equals("movimientosPeriodo")) {
                        Vector vector_consulta = (Vector) session.getAttribute("movimientosPeriodo");
                        if (vector_consulta != null) {
                            correcto = AuthorizationModel.generaArchivoDeConsulta(vector_consulta, "consulta_periodo");
                            pdfFileName = "consulta_periodo.csv";
                        }
                    }
                } else {            
                    // Se actualiza el archivo que contiene el resultado de la consulta
                    correcto = AuthorizationModel.actualizaArchivo();                    
                }
                if (correcto) {
                    System.out.println("Se ha generado correctamente file:" + pdfFileName);
                    String urlReporte = "D:\\apache-tomcat-8.5.23\\bin\\temp\\";
                    File pdfFile = new File(urlReporte + pdfFileName);
                    response.setContentType("text/csv;");
                    response.addHeader("Content-Disposition", "attachment; filename=" + pdfFileName);
                    response.setContentLength((int) pdfFile.length());

                    FileInputStream fileInputStream = new FileInputStream(pdfFile);
                    OutputStream responseOutputStream = response.getOutputStream();
                    int bytes;
                    while ((bytes = fileInputStream.read()) != -1) {
                        responseOutputStream.write(bytes);
                    }
                } else {
                    messageBean = new Message();
                    messageBean.setDesc(" Error actualizando el archivo. ");
                    session.setAttribute("messageBean", messageBean);
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
