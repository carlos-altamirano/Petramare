package mx.garante.liquidaciones.Servlets;

import java.util.Vector;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.garante.liquidaciones.Modelos.ModeloCapture;
import mx.garante.liquidaciones.Beans.Movimiento;
import mx.garante.liquidaciones.Beans.Usuario;
import mx.garante.liquidaciones.Beans.Message;
import mx.garante.liquidaciones.Beans.ResumenMovimientos;
import mx.garante.liquidaciones.Modelos.ModeloLayOut;
import java.io.File;
import java.io.PrintWriter;
import org.datacontract.schemas._2004._07.tes_tfd_v33.RespuestaValidacionRFC;
import org.tempuri.IWSCFDI33;
import org.tempuri.WSCFDI33;

public class ControllerCapture extends HttpServlet {

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
        try {
            processRequest(request, response);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
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
        try {
            processRequest(request, response);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

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

        String dirNameTmp = "./uploads/";
        //Variables para almacenar la acción a realizar.
        int numberAction = 0;
        // Almacena el número de acción a realizar.
        String action;
        //Almacena la clave del cliente asociada al sistema.
        String cliente = null;
        // Almacen a el usuario.
        String usuario = null;
        //Almacena la contraseña del ususario.
        String contrasenna = null;
        // Alamacen la session del usuario.
        HttpSession session = request.getSession();
        // Almacena la URL de la página jsp o Servlet que fungirá como vista.
        String urlResponse = "";
        // Almacena las variables de session que serán eliminadas.
        String nombresObjetos[] = null;
        // Almacena las variables de session que serán eliminadas separadas por punto y coma.
        String obsRemov = null;
        // Bean para los mensajes de respuesta del usuario.
        Message messageBean = null;
        //Bean que almacena los datos del usuario que ingreso al sistema.
        Usuario userApp = null;
        // Almacena la clave de contrato del cliente actual.
        String clave_contrato = null;
        //Almacena la fecha de liquidación actual.
        String fecha_liquidacion = null;
        //Almacena el nombre del lote.
        String nombre_archivo = null;
        //Almacena el tipo de movimiento actual.
        String tipo_movimiento = null;
        //Almacena la clave de banco actual.
        String clave_banco = "";
        //Almacena la clave de moneda actual.
        String clave_moneda = null;
        //Almacena los nombres del fideicomisario.
        String nombreFideicomisario = null;
        //Almacena el apellido paterno del fideicomisario.
        String apellidoPaterno = null;
        //almacena el apellido materno del fideicomisario.
        String apellidoMaterno = null;
        //Almacena la fecha de ingreso del fideicomisario al empleo.
        String fechaIngreso = null;
        //Almacena el puesto del fideicomisario.
        String puesto = null;
        //Almacena el departamento del fideicomisario.
        String departamento = null;
        //Almacena la clave o número de control de nómina del fideicomisario.
        String claveFideicomisario = null;
        //almacena la CURP del fideicomisario.
        String CURP = null;
        //almacena el RFC del fideicomisario.
        String RFC = null;
        //Almacena el número de cuenta del fideicomisario.
        String cuentaDeposito = null;
        //Almacena el importe de liquidación.
        String importe_liquidacion = null;
        //Almacena el nombre de la persona a quién se enviará el cheque.
        String envioCheque = null;
        //Almacena el domicilio de la persona a quién se enviará el cheque.
        String destinoCheque = null;
        //Almacena el teléfono de la persona a quién se enviará el cheque.
        String telefonoCheque = null;
        //Almacena el correo de la persona a quién se enviará el cheque.
        String correoCheque = null;
        //Almacena el nombre del banco que posee la cuenta del fideicomisario.
        String bancoExtranjero = null;
        //Almacena el domicilio del banco que posee la cuenta del fideicomisario.
        String dirBancoExtranjero = null;
        //Almacena el país del banco que posee la cuenta del fideicomisario.
        String paisBancoExtranjero = null;
        //Almacena la clave ABA_BIC del banco extranjero.
        String ABA_BIC = null;
        //Almacena el nombre del fideicomisario registrado en banco extranjero.
        String nombreFideiBancoExtranjero = null;
        //Almacena el domicilio del fideicomisario registrado en banco extranjero.
        String dirFideiBancoExtranjero = null;
        //Almacena el pais en el que el fideicomisario tiene su domicilio en el extranjero.
        String paisFideiBancoExtranjero = null;
        //Almacena el teléfono del fideicomisario que se encuentra en el extranjero.
        String telFideiBancoExtranjero = null;
        //Almancena los movimientos cargados por el ususario.
        Vector movimientos = new Vector();
        //Almacena el resumen de movimientos capurados temporalmente
        ResumenMovimientos resumenMovs = null;
        ModeloLayOut modelo_l = null;
        ModeloCapture modelo_c = null;
        String urlArchivo = "";
        String imprimeResumen = null;

        //Se obtiene la accion a realizar para llamar el modelo asociado
        //y generar la vista correspondiente
        String aux = request.getParameter("accion");

        //Verificamos si la acción es válida
        if (aux != null && aux.compareTo("") != 0) {
            //Se obtiene el nombre y el numero de la accion
            String[] arrayAction = aux.split(":");

            if (arrayAction.length == 2) {
                action = arrayAction[0];
                try {
                    numberAction = Integer.parseInt(arrayAction[1]);
                } catch (NumberFormatException ex) {
                    numberAction = 0;
                    ex.printStackTrace();
                }
            }
        }

        System.out.println("**ControllerCapture** numberAction: " + numberAction + " ");

        //Se verifica el tipo de operacion a realizar y se solicita una operacion al modelo correspondiente
        switch (numberAction) {
            /**
             * Inicio de especificación del tipo de moneda y clave de banco
             * según el tipo de movimiento seleccionado.*
             */
            case 1:
                try {
                    userApp = (Usuario) session.getAttribute("userApp");
                    if (userApp != null) {
                        obsRemov = "tipo_movimiento;clave_banco;clave_moneda;nombreFideicomisario;apellidoPaterno;"
                                + "apellidoMaterno;cuentaDeposito;importeLiquidacion;claveFideicomisario;CURP;RFC;fechaIngreso;"
                                + "puesto;departamento;envioCheque;destinoCheque;telefonoCheque;"
                                + "correoCheque;bancoExtranjero;dirBancoExtranjero;paisBancoExtranjero;ABA_BIC;"
                                + "nombreFideiBancoExtranjero;dirFideiBancoExtranjero;paisFideiBancoExtranjero;telFideiBancoExtranjero;"
                                + "resumenMovs;confirmaUsuario";
                        //Especificamos los objetos a remover de la sesion
                        nombresObjetos = obsRemov.split(";");
                        //Se remueven los objetos especificados de la sesion
                        synchronized (session) {
                            this.remueveAtributos(nombresObjetos, session);
                        }
                        // Se obtiene el tipo de movimiento seleccionado.
                        if (request.getParameter("tipo_movimiento") != null && !request.getParameter("tipo_movimiento").equals("")) {
                            tipo_movimiento = request.getParameter("tipo_movimiento");
                        }
                        if (tipo_movimiento != null) {
                            if (!tipo_movimiento.equals("Seleccione")) {
                                String[] movimiento = tipo_movimiento.split(".-");
                                if (movimiento[0].equals("1") || movimiento[0].equals("2") || movimiento[0].equals("3")) {

                                    //Especificamos la clave de banco para estos movimientos.
                                    if (movimiento[0].equals("1")) {
                                        clave_banco = ModeloCapture.getAbreviacionBancoABM("12");
                                    }
                                    if (movimiento[0].equals("3")) {
                                        clave_banco = ModeloCapture.getAbreviacionBancoABM("2");
                                    }
                                    //Especificamos la clave de moneda para estos movimientos.
                                    clave_moneda = ModeloCapture.getClaveMoneda("MXP");

                                    synchronized (session) {
                                        session.setAttribute("tipo_movimiento", tipo_movimiento);
                                        session.setAttribute("clave_banco", clave_banco);
                                        session.setAttribute("clave_moneda", clave_moneda);

                                        session.setAttribute("envioCheque", "NA");
                                        session.setAttribute("destinoCheque", "NA");
                                        session.setAttribute("telefonoCheque", "NA");
                                        session.setAttribute("correoCheque", "NA");

                                        session.setAttribute("bancoExtranjero", "NA");
                                        session.setAttribute("dirBancoExtranjero", "NA");
                                        session.setAttribute("paisBancoExtranjero", "NA");
                                        session.setAttribute("ABA_BIC", "NA");
                                        session.setAttribute("nombreFideiBancoExtranjero", "NA");
                                        session.setAttribute("dirFideiBancoExtranjero", "NA");
                                        session.setAttribute("paisFideiBancoExtranjero", "NA");
                                        session.setAttribute("telFideiBancoExtranjero", "NA");
                                    }
                                } else if (movimiento[0].equals("4")) {
                                    //Especificamos la clave de banco para este movimiento.
                                    clave_banco = ModeloCapture.getAbreviacionBancoABM("0");
                                    //Especificamos de moneda para este movimiento.
                                    clave_moneda = ModeloCapture.getClaveMoneda("MXP");

                                    synchronized (session) {
                                        session.setAttribute("tipo_movimiento", tipo_movimiento);
                                        session.setAttribute("clave_banco", clave_banco);
                                        session.setAttribute("clave_moneda", clave_moneda);

                                        session.setAttribute("bancoExtranjero", "NA");
                                        session.setAttribute("dirBancoExtranjero", "NA");
                                        session.setAttribute("paisBancoExtranjero", "NA");
                                        session.setAttribute("ABA_BIC", "NA");
                                        session.setAttribute("nombreFideiBancoExtranjero", "NA");
                                        session.setAttribute("dirFideiBancoExtranjero", "NA");
                                        session.setAttribute("paisFideiBancoExtranjero", "NA");
                                        session.setAttribute("telFideiBancoExtranjero", "NA");
                                    }
                                } else if (movimiento[0].equals("5")) {
                                    //Especificamos la clave de banco para este movimiento.
                                    clave_banco = ModeloCapture.getAbreviacionBancoABM("0");
                                    synchronized (session) {
                                        session.setAttribute("tipo_movimiento", tipo_movimiento);
                                        session.setAttribute("clave_banco", clave_banco);

                                        session.setAttribute("envioCheque", "NA");
                                        session.setAttribute("destinoCheque", "NA");
                                        session.setAttribute("telefonoCheque", "NA");
                                        session.setAttribute("correoCheque", "NA");
                                    }
                                } else {
                                    messageBean = new Message();
                                    messageBean.setDesc("Tipo de movimiento indefinido, consulte a su administrador");
                                    synchronized (session) {
                                        session.setAttribute("messageBean", messageBean);
                                    }
                                }
                            }
                        } else {
                            messageBean = new Message();
                            messageBean.setDesc(" Error obteniendo el tipo de movimiento seleccionado ");
                            synchronized (session) {
                                session.setAttribute("messageBean", messageBean);
                            }
                        }
                        urlResponse = "ManualCaptureMovements.htm";
                    } else {
                        messageBean = new Message();
                        messageBean.setDesc(" Tipo de sesión agotado ");
                        synchronized (session) {
                            session.setAttribute("messageBean", messageBean);
                        }
                        urlResponse = "UserLogin.htm";
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    urlResponse = "ManualCaptureMovements.htm";
                }
                break;
            /**
             * Fin de especificación del tipo de moneda y clave de banco según
             * el tipo de movimiento seleccionado.*
             */
            /**
             * Inicio del almacenamiento temporal (Vector) del movimiento
             * capturado por el usuario *
             */
            case 2:
                //Se obtienen los datos del usuario que ingreso al sistema.
                userApp = (Usuario) session.getAttribute("userApp");
                tipo_movimiento = (String) session.getAttribute("tipo_movimiento");
                String fecha_l = (String) session.getAttribute("fecha_liquidacion");
                //Mensaje informativo para el usuario.
                boolean errorFecha = false;
                messageBean = new Message();
                Movimiento movimiento = null;
                String valida = "";
                obsRemov = "";
                int idx = 0;

                try {
                    if (userApp != null) {
                        //Se obtiene la clave del contrato del cliente asociado al usuario.
                        clave_contrato = userApp.getClave_contrato();
                        //Se obtiene la fecha de liquidación seleccionada.
                        if (request.getParameter("fechaLiquidacion") != null && !request.getParameter("fechaLiquidacion").equals("")) {
                            fecha_liquidacion = request.getParameter("fechaLiquidacion").trim();
                        }
                        //Se obtiene la clave de banco seleccionada.
                        if (request.getParameter("clave_banco") != null && !request.getParameter("clave_banco").equals("")) {
                            clave_banco = request.getParameter("clave_banco").trim();
                        }
                        // Se obtiene la clave de moneda seleccionada.
                        if (request.getParameter("clave_moneda") != null && !request.getParameter("clave_moneda").equals("")) {
                            clave_moneda = request.getParameter("clave_moneda").trim();
                        }
                        // Se obtiene el tipo de movimiento seleccionado.
                        if (request.getParameter("nombreFideicomisario") != null && !request.getParameter("nombreFideicomisario").equals("")) {
                            nombreFideicomisario = request.getParameter("nombreFideicomisario").trim().toUpperCase();
                        }
                        // Se obtiene el tipo de movimiento seleccionado.
                        if (request.getParameter("apellidoPaterno") != null && !request.getParameter("apellidoPaterno").equals("")) {
                            apellidoPaterno = request.getParameter("apellidoPaterno").trim().toUpperCase();
                        }
                        // Se obtiene el tipo de movimiento seleccionado.
                        if (request.getParameter("apellidoMaterno") != null) {
                            apellidoMaterno = request.getParameter("apellidoMaterno").trim().toUpperCase();
                        }
                        // Se obtiene el tipo de movimiento seleccionado.
                        if (request.getParameter("fechaIngreso") != null && !request.getParameter("fechaIngreso").equals("")) {
                            fechaIngreso = request.getParameter("fechaIngreso").trim();
                        }
                        // Se obtiene el tipo de movimiento seleccionado.
                        if (request.getParameter("puesto") != null && !request.getParameter("puesto").equals("")) {
                            puesto = request.getParameter("puesto").trim().toUpperCase();
                        }
                        // Se obtiene el tipo de movimiento seleccionado.
                        if (request.getParameter("departamento") != null && !request.getParameter("departamento").equals("")) {
                            departamento = request.getParameter("departamento").trim().toUpperCase();
                        }
                        // Se obtiene el tipo de movimiento seleccionado.
                        if (request.getParameter("claveFideicomisario") != null && !request.getParameter("claveFideicomisario").equals("")) {
                            claveFideicomisario = request.getParameter("claveFideicomisario").trim();
                        }
                        // Se obtiene el tipo de movimiento seleccionado.
                        if (request.getParameter("CURP") != null && !request.getParameter("CURP").equals("")) {
                            CURP = request.getParameter("CURP").trim().toUpperCase();
                        }
                        if (request.getParameter("RFC") != null && !request.getParameter("RFC").equals("")) {
                            RFC = request.getParameter("RFC").trim().toUpperCase();
                        }
                        // Se obtiene el tipo de movimiento seleccionado.
                        if (request.getParameter("cuentaDeposito") != null && !request.getParameter("cuentaDeposito").equals("")) {
                            cuentaDeposito = request.getParameter("cuentaDeposito").trim();
                        }
                        // Se obtiene el tipo de movimiento seleccionado.
                        if (request.getParameter("importeLiquidacion") != null && !request.getParameter("importeLiquidacion").equals("")) {
                            importe_liquidacion = request.getParameter("importeLiquidacion").trim();
                        }
                        // Se obtiene el tipo de movimiento seleccionado.
                        if (request.getParameter("envioCheque") != null && !request.getParameter("envioCheque").equals("")) {
                            envioCheque = request.getParameter("envioCheque").trim().toUpperCase();
                        }
                        // Se obtiene el tipo de movimiento seleccionado.
                        if (request.getParameter("destinoCheque") != null && !request.getParameter("destinoCheque").equals("")) {
                            destinoCheque = request.getParameter("destinoCheque").trim().toUpperCase();
                        }
                        // Se obtiene el tipo de movimiento seleccionado.
                        if (request.getParameter("telefonoCheque") != null && !request.getParameter("telefonoCheque").equals("")) {
                            telefonoCheque = request.getParameter("telefonoCheque").trim().toUpperCase();
                        }
                        // Se obtiene el tipo de movimiento seleccionado.
                        if (request.getParameter("correoCheque") != null && !request.getParameter("correoCheque").equals("")) {
                            correoCheque = request.getParameter("correoCheque").trim();
                        }
                        // Se obtiene el tipo de movimiento seleccionado.
                        if (request.getParameter("bancoExtranjero") != null && !request.getParameter("bancoExtranjero").equals("")) {
                            bancoExtranjero = request.getParameter("bancoExtranjero").trim().toUpperCase();
                        }
                        // Se obtiene el tipo de movimiento seleccionado.
                        if (request.getParameter("dirBancoExtranjero") != null && !request.getParameter("dirBancoExtranjero").equals("")) {
                            dirBancoExtranjero = request.getParameter("dirBancoExtranjero").trim().toUpperCase();
                        }
                        // Se obtiene el tipo de movimiento seleccionado.
                        if (request.getParameter("paisBancoExtranjero") != null && !request.getParameter("paisBancoExtranjero").equals("")) {
                            paisBancoExtranjero = request.getParameter("paisBancoExtranjero").trim().toUpperCase();
                        }
                        // Se obtiene el tipo de movimiento seleccionado.
                        if (request.getParameter("ABA_BIC") != null && !request.getParameter("ABA_BIC").equals("")) {
                            ABA_BIC = request.getParameter("ABA_BIC").trim().toUpperCase();
                        }
                        // Se obtiene el tipo de movimiento seleccionado.
                        if (request.getParameter("nombreFideiBancoExtranjero") != null && !request.getParameter("nombreFideiBancoExtranjero").equals("")) {
                            nombreFideiBancoExtranjero = request.getParameter("nombreFideiBancoExtranjero").trim().toUpperCase();
                        }
                        // Se obtiene el tipo de movimiento seleccionado.
                        if (request.getParameter("dirFideiBancoExtranjero") != null && !request.getParameter("dirFideiBancoExtranjero").equals("")) {
                            dirFideiBancoExtranjero = request.getParameter("dirFideiBancoExtranjero").trim().toUpperCase();
                        }
                        // Se obtiene el tipo de movimiento seleccionado.
                        if (request.getParameter("paisFideiBancoExtranjero") != null && !request.getParameter("paisFideiBancoExtranjero").equals("")) {
                            paisFideiBancoExtranjero = request.getParameter("paisFideiBancoExtranjero").trim().toUpperCase();
                        }
                        // Se obtiene el tipo de movimiento seleccionado.
                        if (request.getParameter("telFideiBancoExtranjero") != null && !request.getParameter("telFideiBancoExtranjero").equals("")) {
                            telFideiBancoExtranjero = request.getParameter("telFideiBancoExtranjero").trim().toUpperCase();
                        }
                        if (clave_contrato != null && fecha_liquidacion != null && tipo_movimiento != null
                                && clave_banco != null && clave_moneda != null && nombreFideicomisario != null
                                && apellidoPaterno != null && apellidoMaterno != null && fechaIngreso != null
                                && puesto != null && departamento != null && claveFideicomisario != null
                                && CURP != null && RFC != null && ((!tipo_movimiento.equals("4.-Emisión de Cheques") && cuentaDeposito != null) || (tipo_movimiento.equals("4.-Emisión de Cheques") && cuentaDeposito == null)) && importe_liquidacion != null
                                && envioCheque != null && destinoCheque != null && telefonoCheque != null
                                && correoCheque != null && bancoExtranjero != null && dirBancoExtranjero != null
                                && paisBancoExtranjero != null && ABA_BIC != null && nombreFideiBancoExtranjero != null
                                && dirFideiBancoExtranjero != null && paisFideiBancoExtranjero != null && telFideiBancoExtranjero != null) {

                            movimiento = new Movimiento();

                            movimiento.setClave_contrato(clave_contrato);
                            movimiento.setFecha_liquidacion(fecha_liquidacion);
                            movimiento.setTipo_movimiento(tipo_movimiento);
                            movimiento.setClave_banco(clave_banco);
                            movimiento.setClave_moneda(clave_moneda);
                            movimiento.setNombre_empleado(nombreFideicomisario);
                            movimiento.setApellidoP_empleado(apellidoPaterno);
                            movimiento.setApellidoM_empleado(apellidoMaterno);
                            movimiento.setFecha_ingreso(fechaIngreso);
                            movimiento.setPuesto_empleado(puesto);
                            movimiento.setDepartamento_empleado(departamento);
                            movimiento.setNumero_empleado(claveFideicomisario);
                            movimiento.setCURP(CURP);
                            movimiento.setRFC(RFC);
                            movimiento.setCuenta_deposito(cuentaDeposito);
                            movimiento.setImporte_liquidacion(importe_liquidacion);

                            movimiento.setNombre_receptor_cheque(envioCheque);
                            movimiento.setDomicilio_destino_cheque(destinoCheque);
                            movimiento.setTel_destino_cheque(telefonoCheque);
                            movimiento.setCorreo_destino_cheque(correoCheque);

                            movimiento.setNombre_banco_extranjero(bancoExtranjero);
                            movimiento.setDomicilio_banco_extranjero(dirBancoExtranjero);
                            movimiento.setPais_banco_extranjero(paisBancoExtranjero);
                            movimiento.setABA_BIC(ABA_BIC);
                            movimiento.setNombre_empleado_banco_extranjero(nombreFideiBancoExtranjero);
                            movimiento.setDom_empleado_banco_extranjero(dirFideiBancoExtranjero);
                            movimiento.setPais_empleado_banco_extranjero(paisFideiBancoExtranjero);
                            movimiento.setTel_empleado_banco_extranjero(telFideiBancoExtranjero);

                            valida = ModeloCapture.validaMovimiento(movimiento);

                            if (valida.equals("")) {
                                Vector movimientosIngresados = (Vector) session.getAttribute("movimientosIngresados");
                                if (movimientosIngresados == null) {
                                    movimientosIngresados = new Vector();
                                }

                                //--SE modifica para que pueda funionar con varios usuarios a la vez
                                if (movimientosIngresados.size() != 0) {
                                    if (fecha_l == null || !fecha_l.equals(fecha_liquidacion)) {
                                        errorFecha = true;
                                        if (fecha_l == null || fecha_l == "") {
                                            messageBean.setDesc("Error obteniendo la fecha de liquidación del lote.");
                                        } else {
                                            messageBean.setDesc("La fecha en la que tendrá lugar la liquidación debe ser la misma para todos los movimientos de un mismo lote.");
                                        }
                                    }
                                } else {
                                    String mensaje = ModeloCapture.validaFechaLiquidacion(fecha_liquidacion);
                                    if (!mensaje.equals("")) {
                                        errorFecha = true;
                                        messageBean.setDesc(mensaje);
                                    }
                                }
                                if (!errorFecha) {
                                    messageBean.setDesc("Capture un nuevo movimiento o de clic en FINALIZAR para terminar la operación.");
                                    //Agregamos el nuevo movimiento validado correctamente a la lista de movimientos capturados.
                                    movimientosIngresados.add(movimiento);
                                    session.setAttribute("movimientosIngresados", movimientosIngresados);
                                    session.setAttribute("fecha_liquidacion", fecha_liquidacion);
                                    //----ESTO NO PERMITE COMPARTIR RECURSOS CORRECTAMENTE
                                    //----ModeloCapture.movimientosValidos.add(movimiento);
                                    //----Especificamos la fecha de liquidación de movimiento almacenado
                                    //----ModeloCapture.setFechaLiquidacion(fecha_liquidacion);
                                    //Especificamos los objetos a remover de la sesion
                                    obsRemov = "tipo_movimiento;clave_banco;clave_moneda;"
                                            + "nombreFideicomisario;apellidoPaterno;apellidoMaterno;fechaIngreso;puesto;"
                                            + "departamento;claveFideicomisario;CURP;RFC;cuentaDeposito;importe_liquidacion;"
                                            + "envioCheque;destinoCheque;telefonoCheque;correoCheque;bancoExtranjero;"
                                            + "dirBancoExtranjero;paisBancoExtranjero;ABA_BIC;nombreFideiBancoExtranjero;"
                                            + "dirFideiBancoExtranjero;paisFideiBancoExtranjero;telFideiBancoExtranjero";
                                    nombresObjetos = obsRemov.split(";");
                                }
                            } else {
                                if (fecha_l != null && fecha_l.equals("")) {
                                    session.setAttribute("fecha_liquidacion", fecha_l);
                                } else {
                                    session.setAttribute("fecha_liquidacion", fecha_liquidacion);
                                }
                                messageBean.setDesc(valida);
                                idx = tipo_movimiento.indexOf(".");
                                tipo_movimiento = tipo_movimiento.substring(0, idx);
                            }
                        } else {
                            messageBean.setDesc(" Error obteniendo información capturada, consulte a su administrador ");
                        }
                        synchronized (session) {
                            if (obsRemov != null && !obsRemov.equals("")) {
                                //Se remueven los objetos especificados de la sesion
                                this.remueveAtributos(nombresObjetos, session);
                            }
                            session.setAttribute("messageBean", messageBean);
                            if (!valida.equals("") || errorFecha) {
                                session.setAttribute("nombreFideicomisario", nombreFideicomisario);
                                session.setAttribute("apellidoPaterno", apellidoPaterno);
                                session.setAttribute("apellidoMaterno", apellidoMaterno);
                                session.setAttribute("fechaIngreso", fechaIngreso);
                                session.setAttribute("puesto", puesto);
                                session.setAttribute("departamento", departamento);
                                session.setAttribute("claveFideicomisario", claveFideicomisario);
                                session.setAttribute("CURP", CURP);
                                session.setAttribute("RFC", RFC);
                                session.setAttribute("cuentaDeposito", cuentaDeposito);
                                session.setAttribute("importe_liquidacion", importe_liquidacion);

                                if (tipo_movimiento.equals("4")) {
                                    session.setAttribute("bancoExtranjero", bancoExtranjero);
                                    session.setAttribute("dirBancoExtranjero", dirBancoExtranjero);
                                    session.setAttribute("paisBancoExtranjero", paisBancoExtranjero);
                                    session.setAttribute("ABA_BIC", ABA_BIC);
                                    session.setAttribute("nombreFideiBancoExtranjero", nombreFideiBancoExtranjero);
                                    session.setAttribute("dirFideiBancoExtranjero", dirFideiBancoExtranjero);
                                    session.setAttribute("paisFideiBancoExtranjero", paisFideiBancoExtranjero);
                                    session.setAttribute("telFideiBancoExtranjero", telFideiBancoExtranjero);
                                }
                                if (tipo_movimiento.equals("5")) {
                                    session.setAttribute("envioCheque", envioCheque);
                                    session.setAttribute("destinoCheque", destinoCheque);
                                    session.setAttribute("telefonoCheque", telefonoCheque);
                                    session.setAttribute("correoCheque", correoCheque);
                                }
                            }
                        }
                        urlResponse = "ManualCaptureMovements.htm";
                    } else {
                        messageBean.setDesc(" Tiempo de sesión agotado ");
                        synchronized (session) {
                            session.setAttribute("messageBean", messageBean);
                        }
                        urlResponse = "UserLogin.htm";
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    urlResponse = "ManualCaptureMovements.htm";
                }

                break;
            /**
             * Fin del almacenamiento temporal (Vector) del movimiento capturado
             * por el usuario *
             */
            //Se genera el resumen de liquidación correspondiente a los movimientos capturados.
            case 3:
                userApp = (Usuario) session.getAttribute("userApp");
                //Mensaje informativous
                clave_contrato = userApp.getClave_contrato();
                messageBean = new Message();
                try {
                    if (userApp != null) {
                        Vector movimientosIngresados = (Vector) session.getAttribute("movimientosIngresados");
                        //----ESTO NO PERMITE COMPARTIR RECURSOS CORRECTAMENTE
                        //----movimientos = ModeloCapture.getMovimientosCargados();                        
                        //----if (movimientos.isEmpty()) {
                        if (movimientosIngresados.isEmpty()) {
                            messageBean.setDesc(" No se cuenta con algún movimiento capturado por el momento ");
                            urlResponse = "ManualCaptureMovements.htm";
                        } else {
                            //----resumenMovs = ModeloCapture.getResumenMovimientos(movimientos);
                            resumenMovs = ModeloCapture.getResumenMovimientos(movimientosIngresados);
                            messageBean.setDesc(" Favor de confirmar información ");
                            urlResponse = "LiquidationSummary.htm";
                        }
                        //Se sincroniza la sesion para meter las variables correspondientes.
                        synchronized (session) {
                            session.setAttribute("messageBean", messageBean);
                            session.setAttribute("resumenMovs", resumenMovs);
                            session.removeAttribute("confirmaUsuario");
                        }
                    } else {
                        messageBean.setDesc(" Tiempo de sesión agotado ");
                        synchronized (session) {
                            session.setAttribute("messageBean", messageBean);
                        }
                        urlResponse = "UserLogin.htm";
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    urlResponse = "ManualCaptureMovements.htm";
                }
                break;

            //Se especifica la variable para confirmar resumen de liquidación
            case 4:
                userApp = (Usuario) session.getAttribute("userApp");
                try {
                    if (userApp != null) {
                        String confirmaUsuario = "Favor de confirmar usuario";
                        //Se sincroniza la sesion para meter las variables correspondientes.
                        synchronized (session) {
                            session.setAttribute("confirmaUsuario", confirmaUsuario);
                        }
                        urlResponse = "LiquidationSummary.htm";
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
                    urlResponse = "LiquidationSummary.htm";
                }
                break;

            //Cancela almacenamiento de información cargada temporalmente.
            case 5:
                userApp = (Usuario) session.getAttribute("userApp");
                try {
                    if (userApp != null) {
                        //Se eliminan los movimientos almacenados temporalmente.                           
                        //----ESTO NO PERMITE COMPARTIR RECURSOS CORRECTAMENTE
                        //----ModeloCapture.setMovimientosCargados(new Vector());
                        //Se inicializa la fecha de liquidación.
                        //-----ModeloCapture.setFechaLiquidacion("");
                        //Se genera el arreglo con los nombres de los objetos a remover request.getParameter("accion")
                        if (!request.getParameter("nombresObjetos").equals("")) {
                            nombresObjetos = request.getParameter("nombresObjetos").split(";");
                        }
                        //Se obtiene el nombre de la página a donde se redigira
                        if (!request.getParameter("urlResponse").equals("")) {
                            urlResponse = request.getParameter("urlResponse");
                        }
                        synchronized (session) {
                            //Se remueven los objetos especificados de la sesion
                            this.remueveAtributos(nombresObjetos, session);
                            session.removeAttribute("movimientosIngresados");
                            session.removeAttribute("fecha_liquidacion");
                        }
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
//                    urlResponse = "ManualCaptureMovements.htm";
                    urlResponse = "menuLoadFile.htm";
                }
                break;

            //Se almacena en la base de datos los movimientos cargados temporalmente.
            case 6:
                int idx_archivo = 0;
                String genera_l = "";
                String nombre_cliente = "";
                String correos = "";
                String correo_o = "";
                String correo_d = "";
                String asunto = "";
                String messageClean = null;
                String formato_fecha = "";
                boolean almacena = false;
                messageBean = new Message();
                ModeloCapture.clave_archivo = -1;
                ModeloCapture.nombre_archivo = "";
                try {
                    userApp = (Usuario) session.getAttribute("userApp");
                    if (userApp != null) {
                        Vector movimientosIngresados = (Vector) session.getAttribute("movimientosIngresados");
                        //----ESTO NO PERMITE COMPARTIR RECURSOS CORRECTAMENTE
                        //Obtenemos los movimientos cargados por el usuario de forma manual.
                        //----movimientos = ModeloCapture.getMovimientosCargados();
                        //Obtenemos el resumen de movimientos
                        resumenMovs = (ResumenMovimientos) session.getAttribute("resumenMovs");
                        fecha_liquidacion = (String) session.getAttribute("fecha_liquidacion");
                        //Obtenemos la fecha en que tendrá lugar la liquidación.
                        //----fecha_liquidacion = ModeloCapture.getFechaLiquidacion();
                        movimientos = movimientosIngresados;
                        if (!movimientos.isEmpty() && movimientos != null) {
                            // Se obtienen las variables de session globales.
                            userApp = (Usuario) session.getAttribute("userApp"); //Información del usuario que inicio sesión
                            //Obtenemos la clave del cliente asociada al sistema de liquidaciones.
                            cliente = userApp.getId_cliente();
                            // Se obtiene el usuario que intenta almacenar movimientos.
                            if (request.getParameter("usuario") != null && !request.getParameter("usuario").equals("")) {
                                usuario = request.getParameter("usuario").toString().trim();
                            }
                            // Se Obtiene la Contrasenna del Usuario
                            if (request.getParameter("contrasenna") != null && !request.getParameter("contrasenna").equals("")) {
                                contrasenna = request.getParameter("contrasenna").toString().trim();
                            }
                            if (cliente.equals("PRB16082011") && usuario.equals("PRB16082011") && contrasenna.equals("PRB16082011")) {
                                //----ESTO NO PERMITE COMPARTIR RECURSOS CORRECTAMENTE
                                //----ModeloCapture.setMovimientosCargados(new Vector());
                                session.removeAttribute("movimientosIngresados");
                                session.removeAttribute("fecha_liquidacion");
                                //-----ModeloCapture.setFechaLiquidacion("");
                                imprimeResumen = "imprimeResumen";
                                messageClean = "messageClean";
                                urlResponse = "ManualCaptureMovements.htm";
                            } else {
                                // Llamando al modelo para que autentifique al usuario.
                                Usuario userApp1 = ModeloLayOut.validaUsuario(cliente, usuario, contrasenna);
                                //Verificamos la Autentificación del Usuario.
                                if (userApp1 != null && userApp1.isAutentificado()) {
                                    //Obtenemos la clave del contrato.
                                    clave_contrato = userApp.getClave_contrato();
                                    correo_o = "liquidaciones@fideicomisogds.mx";
                                    correo_d = "liquidaciones@fideicomisogds.mx";
                                    modelo_c = new ModeloCapture();
                                    //Se realiza la consulta del saldo actual
//                                        String infoSaldo = "";
//                                        infoSaldo = ModeloLayOut.getSaldo_LiqPend_Fid(clave_contrato);                                    
                                    //Almacenamos en la base de datos los movimientos capturados por el usuario.
                                    System.out.println("Inicia almacenamiento de movimientos en BD de forma MANUAL .......");
                                    almacena = modelo_c.almacenaCargaManual(clave_contrato, fecha_liquidacion, movimientos, userApp1, correo_o, correo_d, dirNameTmp);
                                    if (almacena) {
                                        System.out.println("Terminan movimientos en BD de forma MANUAL ............");
                                        nombre_archivo = ModeloCapture.getNombre_archivo();
                                        idx_archivo = ModeloLayOut.getClaveArchivo(clave_contrato, fecha_liquidacion, nombre_archivo);
//                                        if(!infoSaldo.equals("") && !infoSaldo.equals("0.0%def%0.0")){
                                        formato_fecha = ModeloLayOut.getFormatoFecha(fecha_liquidacion); // DDMMYYYY --> YYMMDD.
                                        if (!formato_fecha.equals("")) {
                                            modelo_l = new ModeloLayOut();
                                            urlArchivo = ".\\inetpub\\ftproot\\Reportes Liquidacion\\" + clave_contrato + "\\" + formato_fecha;
                                            //Generamos el reporte de liquidación.
                                            genera_l = modelo_l.genera_RL(clave_contrato, fecha_liquidacion, formato_fecha, nombre_archivo, resumenMovs, idx_archivo, urlArchivo, getServletContext().getRealPath(File.separator));
                                            if (genera_l.equals("")) {
                                                nombre_cliente = userApp.getCliente();
                                                String body = "El cliente " + nombre_cliente + " realizó la captura de movimientos de forma manual, favor de verificar información.";
                                                correos = ModeloLayOut.obtenCorreos("'SISTEMAS','OPERACION'");
                                                if (correos.equals("")) {
                                                    body = "Correo NO enviado al personal de la SOFOM debido a lista de correos vacia, favor de verificar";
                                                    correos = "soporte@fideicomisogds.mx";
                                                }
                                                asunto = "LIQUIDACIÓN MANUAL " + clave_contrato + " " + fecha_liquidacion;
                                                String nombre_r = ModeloLayOut.getNombreResumenLiquidacion(clave_contrato, formato_fecha, nombre_archivo, idx_archivo);
                                                if (!nombre_r.equals("")) {
                                                    urlArchivo = urlArchivo + "\\";
                                                    //Enviamos correo electrónico.
                                                    genera_l = modelo_l.enviaReporteLiquidacion(correo_o, correos, asunto, body, urlArchivo, nombre_r);
                                                    if (genera_l.equals("")) {
                                                        //----ESTO NO PERMITE COMPARTIR RECURSOS CORRECTAMENTE
                                                        //----ModeloCapture.setMovimientosCargados(new Vector());
                                                        session.removeAttribute("movimientosIngresados");
                                                        session.removeAttribute("fecha_liquidacion");
//                                                        session.setAttribute("saldo_por_pagar", Double.parseDouble(resumenMovs.getNuevo_saldo()));
                                                        //----ModeloCapture.setFechaLiquidacion("");
                                                        imprimeResumen = "imprimeResumen";
                                                        messageClean = "messageClean";
                                                        urlResponse = "ManualCaptureMovements.htm";
                                                    } else {
                                                        messageBean.setDesc(genera_l);
                                                        //----ESTO NO PERMITE COMPARTIR RECURSOS CORRECTAMENTE
                                                        //----ModeloCapture.setMovimientosCargados(new Vector());
                                                        session.removeAttribute("movimientosIngresados");
                                                        session.removeAttribute("fecha_liquidacion");
                                                        //-----ModeloCapture.setFechaLiquidacion("");
                                                        urlResponse = "ManualCaptureMovements.htm";
                                                    }
                                                } else {
                                                    messageBean.setDesc("Error obteniendo el nombre del reporte de liquidación");
                                                    //----ESTO NO PERMITE COMPARTIR RECURSOS CORRECTAMENTE
                                                    //----ModeloCapture.setMovimientosCargados(new Vector());
                                                    session.removeAttribute("movimientosIngresados");
                                                    session.removeAttribute("fecha_liquidacion");
                                                    //------ModeloCapture.setFechaLiquidacion("");
                                                    urlResponse = "ManualCaptureMovements.htm";
                                                }
                                            } else {
                                                messageBean.setDesc(genera_l);
                                                //----ESTO NO PERMITE COMPARTIR RECURSOS CORRECTAMENTE
                                                //----ModeloCapture.setMovimientosCargados(new Vector());
                                                session.removeAttribute("movimientosIngresados");
                                                session.removeAttribute("fecha_liquidacion");
                                                //------ModeloCapture.setFechaLiquidacion("");
                                                urlResponse = "ManualCaptureMovements.htm";
                                            }
                                        } else {
                                            messageBean.setDesc("Error obteniendo el formato de la fecha de liquidación");
                                            //----ESTO NO PERMITE COMPARTIR RECURSOS CORRECTAMENTE
                                            //----ModeloCapture.setMovimientosCargados(new Vector());
                                            session.removeAttribute("movimientosIngresados");
                                            session.removeAttribute("fecha_liquidacion");
                                            //---------ModeloCapture.setFechaLiquidacion("");
                                            urlResponse = "ManualCaptureMovements.htm";
                                        }
//                                            } else {
//                                            messageBean.setDesc("Error obteniendo el saldo actual del  fideicomiso");
//                                                    //----ESTO NO PERMITE COMPARTIR RECURSOS CORRECTAMENTE
//                                                    //----ModeloCapture.setMovimientosCargados(new Vector());
//                                            session.removeAttribute("movimientosIngresados");
//                                            session.removeAttribute("fecha_liquidacion");
//                                                    //---------ModeloCapture.setFechaLiquidacion("");
//                                            urlResponse = "ManualCaptureMovements.htm";
//                                            }                                        
                                    } else {
                                        messageBean.setDesc(" Error almacenando movimientos ");
                                        //----ESTO NO PERMITE COMPARTIR RECURSOS CORRECTAMENTE
                                        //----ModeloCapture.setMovimientosCargados(new Vector());
                                        session.removeAttribute("movimientosIngresados");
                                        session.removeAttribute("fecha_liquidacion");
                                        //-------ModeloCapture.setFechaLiquidacion("");
                                        urlResponse = "ManualCaptureMovements.htm";
                                    }
                                } else {
                                    messageBean.setDesc(" Usuario y/o Contraseña inválidos");
                                    urlResponse = "LiquidationSummary.htm";
                                }
                            }
                        } else {
                            messageBean.setDesc(" No se cuenta con algún movimiento capturado por el momento ");
                            urlResponse = "ManualCaptureMovements.htm";
                        }
                    } else {
                        messageBean.setDesc(" Tiempo de sesión agotado ");
                        urlResponse = "UserLogin.htm";
                    }
                    //Se remueven los objetos especificados de la sesion
                    synchronized (session) {
                        session.setAttribute("messageBean", messageBean);
                        session.setAttribute("imprimeResumen", imprimeResumen);
                        session.setAttribute("messageClean", messageClean);
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    urlResponse = "ManualCaptureMovements.htm";
                }
                break;

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
                session.setAttribute("resumen_saldo", "Mostra informacion de saldo");
                break;
            /**
             * Fin de eliminacion de objetos de la sesion actual*
             */
        }//Fin del switch

//        System.out.println("Redirigiendo: " + urlResponse);
        // Se redirecciona a la pagina correspondiente
        response.sendRedirect(urlResponse);

    }

    /**
     * Método que permite remover los atributos especificados de la sesion
     * pasada como argumento
     *
     * @param nombresAtributos
     * @param session
     */
    public void remueveAtributos(String nombresAtributos[], HttpSession session) {
        for (int i = 0; i < nombresAtributos.length; i++) {
            session.removeAttribute(nombresAtributos[i]);
        }
    }

    /**
     * Método que permite remover los atributos especificados de la sesion
     * pasada como argumento
     *
     * @param nombresAtributos
     * @param session
     */
    public void setAtributos(String nombresAtributos[], HttpSession session) {
        for (int i = 0; i < nombresAtributos.length; i++) {
            session.setAttribute(nombresAtributos[i], nombresAtributos[i]);
        }
    }
}
