package servlets;

import Beans.Message;
import Beans.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Saldo extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        Vector movimientos = new Vector();
        Vector nombresEdoCta = null;
        PrintWriter out = response.getWriter();
        Message mensaje = null;
        String obsRemov = "";
        String[] nombresObjetos = null;

        try {

            HttpSession session = request.getSession(false);
            if (session != null && !session.isNew()) {
                Usuario usuarioApp = null;
                usuarioApp = (Usuario) session.getAttribute("userApp");
                obsRemov = "nombresEdoCta;fecha_ini;fecha_fin;reporte;reporteSize;liquidaciones;liquidacionesSize";
                //Especificamos los objetos a remover de la sesion
                nombresObjetos = obsRemov.split(";");
                //Se remueven los objetos especificados de la sesion
                synchronized (session) {
                    this.remueveAtributos(nombresObjetos, session);
                }
                if (usuarioApp != null) {
                    try {
                        String clave_contrato = usuarioApp.getClave_contrato();
                        String fecha_ini = request.getParameter("fecha_ini");
                        if (fecha_ini != null && !fecha_ini.equals("")) {
                            String tmpo = Modelos.ModeloLayOut.compruebaFechaOperacion(fecha_ini);
                            if (!tmpo.equals("")) {
                                mensaje = new Message();
                                mensaje.setDesc(tmpo);
                                session.setAttribute("messageBean", mensaje);
                                fecha_ini = Modelos.ModeloLayOut.obtenFormatoFecha(fecha_ini, 0);
                            }
                        } else {
                            fecha_ini = Modelos.ModeloLayOut.obtenFormatoFecha(fecha_ini, 0);
                        }
                        String fecha_fin = request.getParameter("fecha_fin");
                        if (fecha_fin != null && !fecha_fin.equals("")) {
                            String tmpo = Modelos.ModeloLayOut.compruebaFechaOperacion(fecha_fin);
                            if (!tmpo.equals("")) {
                                mensaje = new Message();
                                mensaje.setDesc(tmpo);
                                session.setAttribute("messageBean", mensaje);
                                fecha_fin = Modelos.ModeloLayOut.obtenFormatoFecha(fecha_fin, 1);
                            }
                        } else {
                            fecha_fin = Modelos.ModeloLayOut.obtenFormatoFecha(fecha_fin, 1);
                        }
                        String tipo_operacion = request.getParameter("tipo_operacion");
                        if (tipo_operacion != null && !tipo_operacion.equals("")) {
                            if (tipo_operacion.equals("RESTITUCION")) {
                                request.setAttribute("selR", "selected");
                            }
                            if (tipo_operacion.equals("APORTACION")) {
                                request.setAttribute("selA", "selected");
                            }
                            if (tipo_operacion.equals("LIQUIDACION")) {
                                request.setAttribute("selL", "selected");
                            }
                        }
                        if (clave_contrato.equals("PRB16082011")) {
                            clave_contrato = "FID000PRB0514";
                        }
                        movimientos = Modelos.ModeloLayOut.getMovimientos(clave_contrato, fecha_ini, fecha_fin, tipo_operacion);
//                nombresEdoCta = Modelos.ModeloLayOut.get10DatesEdoCta(clave_contrato);
                        nombresEdoCta = Modelos.ModeloLayOut.getEdoCtaFiles(clave_contrato);
                        if (nombresEdoCta != null && !nombresEdoCta.isEmpty()) {
                            session.setAttribute("nombresEdoCta", nombresEdoCta);
                        }
                        session.setAttribute("fecha_ini", fecha_ini);
                        session.setAttribute("fecha_fin", fecha_fin);
                        if (movimientos.isEmpty()) {
                            session.setAttribute("reporte", null);
                            session.setAttribute("reporteSize", 0);
                        } else {
                            session.setAttribute("reporte", movimientos);
                            session.setAttribute("reporteSize", movimientos.size() - 1);
                        }
//              movimientos = Modelos.ModeloLayOut.getMovimientos("", "", "");
                        System.out.println("Obtenemos liquidaciones pendientes");
                        double saldo_actual = Double.parseDouble(session.getAttribute("saldo_actual").toString());
                        movimientos = Modelos.ModeloLayOut.getLiduiacionesPendietes(clave_contrato, saldo_actual);
                        Vector saldos = null;
//                String saldo_por_pagar = "", suficiencia_total = "";
                        if (movimientos.isEmpty()) {
                            session.setAttribute("saldos", new Vector());
//                session.setAttribute("liquidaciones", null);
//                session.setAttribute("suficiencia_total","$ 0.00");
//                session.setAttribute("saldo_por_pagar","$ 0.00");      
                        } else {
                            if (movimientos.size() > 0) {
                                saldos = (Vector) movimientos.get(movimientos.size() - 1);
                                if (saldos != null && saldos.size() > 0) {
                                    session.setAttribute("saldos", saldos);
//                             session.setAttribute("saldosSize", saldos.size()-1);
//                            suficiencia_total = saldos.get(0).toString();
//                            saldo_por_pagar = saldos.get(1).toString();
                                    movimientos.remove(movimientos.size() - 1);
                                }
                                session.setAttribute("liquidaciones", movimientos);
                                if (movimientos.size() > 0) {
                                    session.setAttribute("liquidacionesSize", movimientos.size() - 1);
                                } else {
                                    session.setAttribute("liquidaciones", null);
                                    session.setAttribute("liquidacionesSize", 0);
                                }
                            }
                        }
//                session.setAttribute("liquidaciones", movimientos);
//                session.setAttribute("suficiencia_total",suficiencia_total);
//                session.setAttribute("saldo_por_pagar",saldo_por_pagar);
                        request.getRequestDispatcher("Saldo.htm").forward(request, response);
                    } catch (Exception e) {
                        System.out.println("ExceptionInterno Saldo.do:" + e.getMessage());
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
            System.out.println("Exception Saldo.do:" + e.getMessage());
            response.sendRedirect("UserLogin.htm");
        } finally {
            out.close();
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

    public void remueveAtributos(String nombresAtributos[], HttpSession session) {
        for (int i = 0; i < nombresAtributos.length; i++) {
            session.removeAttribute(nombresAtributos[i]);
        }
    }
}
