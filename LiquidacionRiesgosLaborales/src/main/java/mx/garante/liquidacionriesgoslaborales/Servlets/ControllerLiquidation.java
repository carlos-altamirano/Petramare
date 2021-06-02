package mx.garante.liquidacionriesgoslaborales.Servlets;

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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.util.ArrayList;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mx.garante.liquidacionriesgoslaborales.Beans.Usuario;
import mx.garante.liquidacionriesgoslaborales.Beans.Message;
import mx.garante.liquidacionriesgoslaborales.Beans.Movimiento;
import mx.garante.liquidacionriesgoslaborales.Modelos.ModelUpdate;
import mx.garante.liquidacionriesgoslaborales.Modelos.ModeloLiquidation;
import mx.garante.liquidacionriesgoslaborales.Modelos.AuthorizationModel;
import mx.garante.liquidacionriesgoslaborales.Modelos.UnZip;

public class ControllerLiquidation extends HttpServlet {

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

        //Variables para almacenar la accion a realizar
        int numberAction = 0;
        String action;
        // Almacen a el usuario
        String usuario = null;
        // Almacena la contrasenna del usuario
        String contrasenna = null;
        // Alamacen la session del usuario
        HttpSession session;
        // Almacena la url de la pagina jsp o Servlet que fungira como vista
        String urlResponse = "";
        // Almacena las variables de session que seran eliminadas
        String nombresObjetos[] = null;
        // Bean para los mensajes de respuesta del usuario
        Message messageBean = null;
        // Bean para el manejo de session de usuario
        Usuario userApp = null;
        //Variable para especificar que se desea generar las liquidaciones pendientes.
        String movPending = null;
        //Variable que almacena al cliente seleccionado por el usuario
        String nombre_cliente = null;
        //Varible que almacena los datos del cliente seleccionado.
        Vector dataClient = null;
        //Almacena la clave del Contrato del cliente seleccionado
        String clave_contrato = null;
        //Almacena la fecha de liquidación del cliente seleccionado
        String fecha_liquidacion = null;
        //Almacena la fecha de captura del lay_out
        String fecha_captura = null;
        //Variable para cambiar el Password del usuario
        String cambiaPassword = null;
        // Almacena el nombre del archivo cargado
        String fileName = null;
        //Vector que almacena los nombres de Lay-Out's cargados de un cliente y fecha dados.
        Vector filesName = null;
        //Variable para manejar el modulo Actualiza Movimientos Tipo 5 (Bancos Extranjeros)
        String modActualiza = null;
        //Variable que almacena el conjunto de clientes con movimientos a Bancos Extranjeros.
        Vector cliMov5Pend = null;
        //variable para almacenas las fechas  de Liquidación de los clientes con mov 5.
        Vector dateLiqMov5 = null;
        //Almacena la fecha en la que el usuario genero movimientos de un Lay-Out dado.
        String fecha_usuario_opera = null;
        //Usuario de la SOFOM que opera lote
        String usuario_opera = null;
        //ALmacena el nombre de los Fideicomisarios
        String namesFideiMov5 = null;
        //Variable que almacena el nombre del Fideicomisario seleccionado
        String name_fidei = null;
//        String userIngApp = "";
        //Variable para manejar el módulo de autorización de transacciones
        String modAutoriza = null;
        //Variable para la confirmación de todas las transacciones de un lote.
        String confirmacionLote = null;
        //Variable para la confirmación de todas las transacciones de un lote.
        String aportaciones_restituciones = null;
        //Variable para el manejo de la cancelación toral de un lote.
        String cancelacionTotal = null;
        //Variable para el manejo de la cancelación parcial de un lote.
        String cancelacionParcial = null;
        Vector cliMovConfirma = null;
        Vector allMovimientos = null;
        //Status global del lote
        String clave_cliente = null;
        //Variable que almacena el motivo de la cancelación del movimiento.
        String motivo_cancelacion = null;
        Vector opciones_c = null;
        boolean cancelaM = false; //status de transacción
        // Movimiento movimientoC = null; //Datos del movimiento a cancelar.
        Movimiento mov_cancelado = null;
        Movimiento movimiento = null;
        String cancela_movimiento = null;
        String persona_genera = null;
        boolean enviado = false;

        String correo_origen = null;
        String correo_destino = null;
        String asunto = null;
        String cuerpoCorreo = null;
        ModeloLiquidation modeloLiquidacion = new ModeloLiquidation();
        ModelUpdate modeloActualiza = new ModelUpdate();
        AuthorizationModel modeloAutoriza = new AuthorizationModel();
        String urlArchivo = null;
        String formato_fecha = "";
        Vector motivosCancelacion = null;
        //Variable que identifica un archivo;
        int id_archivo = -1;
        //Variable que sirve de indice.
        int idx = 0;
//        int idx_mov = 0;

        // Crear o obtiene la session actual
        session = request.getSession();

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
//                    System.out.println("\tError");
//                    System.out.println("\t  Clase:  ControllerUpload.java");
//                    System.out.println("\t  Metodo: processRequest");
//                    System.out.println("\t  Descripcion: El numero de la accion a realizar es invalido. Verifique que se pueda convertir a un valor numerico");
                    ex.printStackTrace();
                }
            }
        }

        System.out.println("********numberAction: " + numberAction + " ");

        //Se verifica el tipo de operacion a realizar y se solicita una operacion al modelo correspondiente
        switch (numberAction) {

            /**
             * Inicio de autentificación de usuario*
             */
            case 1:

                //Especificamos los objetos a remover de la sesion
                nombresObjetos = "correo_c;tipo_honorario_c;honorario_sin_iva_c;saldo_actual;cancela_movs;movPending;modAutoriza;modActualiza;confirmacionLote;aportaciones_restituciones".split(";");
                //Se remueven los objetos especificados de la sesion
                synchronized (session) {
                    this.remueveAtributos(nombresObjetos, session);
                }

                // Se obtiene el usuario que intenta entrar al sistemas
                if (request.getParameter("usuario") != null && !request.getParameter("usuario").equals("")) {
                    usuario = request.getParameter("usuario");
                }
                // Se obtiene la contrasenna del usuario
                if (request.getParameter("contrasenna") != null && !request.getParameter("contrasenna").equals("")) {
                    contrasenna = request.getParameter("contrasenna");
                }

                // Llamando al modelo para que autentifique al usuario
                userApp = ModeloLiquidation.validaUsuario(usuario, contrasenna);

                if (userApp != null && userApp.isAutentificado()) {
                    urlResponse = "menuSettlements.htm";
                } else {
                    messageBean = new Message();
                    if (userApp == null) {
                        boolean bloquea = ModeloLiquidation.isBloqueaUser();
                        if (bloquea) {
                            boolean bloqueaUser = modeloLiquidacion.bloqueaUsuario(usuario);
                            if (bloqueaUser) {
                                messageBean.setDesc("Aviso: CUENTA BLOQUEADA, consulte a su administrador");
                            } else {
                                messageBean.setDesc("Error: Consulte a su Administrador");
                            }
                        } else {
                            messageBean.setDesc("Error: Usuario y/o Contraseña inválidos");
                        }
                    } else {
                        messageBean.setDesc("Error; Consulte a su Administrador");
                    }
                    urlResponse = "login.htm";
                }

                // Se sincronizan los beans con la session del usuario
                synchronized (session) {
                    session.setAttribute("messageBean", messageBean);
                    session.setAttribute("userApp", userApp);
                }
                break;
            /**
             * Fin de autentificación de usuario*
             */
            /**
             * Inicio:Obtenemos los Clientes con Movimientos Pendientes Por
             * Operar*
             */
            case 2:
                Vector cliMovPend = null;
                movPending = "movPending";
                //Se especifican las variables de sesion a remover.
                String temp = "clave_contratoC;saldos;nombre_cliente;domicilio_cliente;saldo_cliente;fecha_ini;fecha_fin;reporte;reporteSize;liquidaciones;liquidacionesSize;";
                temp += "correo_c;tipo_honorario_c;honorario_sin_iva_c;saldo_actual;movPending;modAutoriza;modActualiza;confirmacionLote;aportaciones_restituciones;";
                temp += "cliMovPend;dataClient;dateLiquidation;nameClient;fecha_liquidacion;clave_contrato;filesName;nombre_archivo;date_Capt";
                temp += "allMovimientos;consultar_movimientos";
                //variables correspondientes al modulo de consulta Ejecutiva
                temp += "fecha_fin;fecha_fin_2;fecha_ini;fecha_ini_2;allMovimientos;estados_de_cuenta;cuentasOrigen;consulta_ejecutiva;"
                        + "fecha_ini_;fecha_fin_;fecha_ini;fecha_fin;movimientosPeriodo;movimientosPeriodoSize;"
                        + "clave_contratoC;cuenta_origen;fecha_fin_2;fecha_ini_2;vector_consulta_total;vector_consulta;vector_consultaSize";
                nombresObjetos = temp.split(";");
                //Se remueven los variables de la sesion.
                synchronized (session) {
                    this.remueveAtributos(nombresObjetos, session);
                }
                //Se obtienen los fideicomitentes (clave de fideicomiso) que tienen movimientos pendientes por operar
                cliMovPend = AuthorizationModel.getClaveFideicomitentes("A");
                //Se verifica si ocurrio algún error.
                if (cliMovPend != null) {
                    if (cliMovPend.size() <= 1) {
                        messageBean = new Message();
                        messageBean.setDesc("No se cuenta con movimientos pendientes por el momento");
                    }
                } else {
                    messageBean = new Message();
                    messageBean.setDesc("Error obteniendo fideicomitentes con movimientos pendientes por operar");
                }
                // Se sincronizan los clientes con la session del usuario
                synchronized (session) {
                    session.setAttribute("messageBean", messageBean);
                    session.setAttribute("movPending", movPending);
                    session.setAttribute("cliMovPend", cliMovPend);
                }
                urlResponse = "menuSettlements.htm";

                break;
            /**
             * Fin:Obtenemos los Clientes con Movimientos Pendientes Por Operar*
             */
            /**
             * Inicio:Obtenemos los datos generales del cliente seleccionado*
             */
            case 3:
                //Se especifican las variables de sesion a remover.
                nombresObjetos = "dataClient;dateLiquidation;nameClient;fecha_liquidacion;clave_contrato;filesName;nombre_archivo;date_Capt".split(";");
                cancelaM = false;
                //Se remueven los variables de la sesion.
                synchronized (session) {
                    this.remueveAtributos(nombresObjetos, session);
                }
                // Se obtiene el nombre del cliente a procesar
                if (request.getParameter("cliente") != null && !request.getParameter("cliente").equals("")) {
                    clave_contrato = request.getParameter("cliente");
                }
                // Llamamos al modelo para obtener los datos generales del cliente que se le pasa
                dataClient = ModeloLiquidation.getDatosCliente(clave_contrato);
                if (dataClient != null) {
                    if (dataClient.size() > 0) {
                        if (ModeloLiquidation.fechas_liquidacion.size() > 0) {
                            nombre_cliente = (String) dataClient.get(0);
                            cancelaM = true;
                        } else {
                            messageBean = new Message();
                            messageBean.setDesc(" Error obteniendo las fechas en las que tendrá lugar la liquidación ");
                        }
                    } else {
                        messageBean = new Message();
                        messageBean.setDesc(" Seleccione Fideicomitente ");
                    }
                } else {
                    messageBean = new Message();
                    messageBean.setDesc(" Error obteniendo datos generales del fideicomitente ");
                }
                synchronized (session) {
                    if (cancelaM) {
                        //Nombre del Fideicomitente
                        session.setAttribute("nameClient", nombre_cliente);
                        //Datos generales del Fideicomitente
                        session.setAttribute("dataClient", dataClient);
                        //Fechas de Liquidación pendientes
                        session.setAttribute("dateLiquidation", ModeloLiquidation.fechas_liquidacion);
                    }
                    session.setAttribute("clave_contrato", clave_contrato);
                    session.setAttribute("messageBean", messageBean);

                }
                urlResponse = "menuSettlements.htm";
                break;
            /**
             * Fin:Obtenemos los datos generales del cliente seleccionado*
             */
            /**
             * Inicio:Obtenemos la fecha de captura del Lay-Out por parte del
             * cliente*
             */
            case 4:
                //Almacenamos las variables de sesion a remover.
                nombresObjetos = "nombre_archivo;date_Capt".split(";");
                cancelaM = false;
                //Se remueven los objetos especificados de la sesion
                synchronized (session) {
                    this.remueveAtributos(nombresObjetos, session);
                }
                // Se obtienen las variables de session
                clave_contrato = (String) session.getAttribute("clave_contrato");
                fecha_liquidacion = (String) session.getAttribute("fecha_liquidacion");
                // Se obtiene el nombre del Lay-Out a procesar.
                if (request.getParameter("nombre_archivo") != null && !request.getParameter("nombre_archivo").equals("")) {
                    fileName = request.getParameter("nombre_archivo");
                }
                if (fileName != null) {
                    //Llamamos al modelo para obtener la fecha de captura del Lay-Out por parte del cliente
                    fecha_captura = ModeloLiquidation.getFechaCaptura(clave_contrato, fecha_liquidacion, fileName, "A");
                } else {
                    messageBean = new Message();
                    messageBean.setDesc(" Error obteniendo nombre de Lay-Out seleccionado ");
                }
                //Sincronizamos para almacenas variables a la sesion
                synchronized (session) {
                    session.setAttribute("date_Capt", fecha_captura);
                    session.setAttribute("messageBean", messageBean);
                    session.setAttribute("nombre_archivo", fileName);
                }
                urlResponse = "menuSettlements.htm";

                break;
            /**
             * Fin:Obtenemos la fecha de captura del Lay-Out por parte del
             * cliente*
             */
            // Caso que gestiona la generacion Lay-out's asi como los correspondientes
            // reportes de liquidación a bancos extranjeros.
            case 5:
                // Se obtienen las variables de session necesarias.
                userApp = (Usuario) session.getAttribute("userApp"); // Bean para almacenar todo lo relacionado con el usuario que ingreso al sistema.
                nombre_cliente = (String) session.getAttribute("nameClient"); // Nombre del fideicomitente seleccionado.
                clave_contrato = (String) session.getAttribute("clave_contrato"); //Clave del Fideicomitente
                fecha_liquidacion = (String) session.getAttribute("fecha_liquidacion"); //Fecha de Liquidación seleccionada
                fileName = (String) session.getAttribute("nombre_archivo"); // Nombre del lote a procesar.

                messageBean = new Message();
                String verifica = "";

//                try {
                if (userApp != null && nombre_cliente != null && clave_contrato != null && fecha_liquidacion != null && fileName != null) {
                    formato_fecha = ModeloLiquidation.getFormatoFecha(fecha_liquidacion);

                    //Verificamos si se cuenta con los datos indispensables.
                    if (!formato_fecha.equals("")) {

                        // Url local
                        //urlArchivo = ".\\Reportes Liquidacion\\" + clave_contrato + "\\" + formato_fecha + "\\";
//                            System.out.println("URL ARCHIVO: " + urlArchivo);
                        // Url Servidor
                        urlArchivo = ".\\inetpub\\ftproot\\Reportes Liquidacion\\" + clave_contrato + "\\" + formato_fecha + "\\";

                        usuario = userApp.getUsuario();
                        persona_genera = userApp.getNombre_usuario();
                        if (!usuario.equals("") && !persona_genera.equals("")) {

                            //Obtenemos el identificador del lote a procesar.
                            id_archivo = ModeloLiquidation.getClaveArchivo(clave_contrato, fecha_liquidacion, fileName);

                            //Generamos los layOut's correspondientes y el reporte de liquidación a bancos extranjeros.
                            verifica = modeloLiquidacion.generaTransacciones(nombre_cliente, clave_contrato, fecha_liquidacion, fileName, usuario, urlArchivo, persona_genera, id_archivo, getServletContext().getRealPath(File.separator));

                            messageBean.setDesc(verifica);
                        } else {
                            messageBean.setDesc(" Error obteniendo información del usuario ");
                        }
                    } else {
                        messageBean.setDesc(" Error obteniendo el formato de la fecha de liquidación ");
                    }
                } else {
                    messageBean.setDesc(" Error obteniendo datos de la sesion ");
                }
//                } catch (Exception e) {
//                    messageBean.setDesc(" Error generando transacciones ");
//                }

                //Obtenemos del modelo los clientes actualizados
                cliMovPend = AuthorizationModel.getClaveFideicomitentes("A");
                // Especificamos los objetos a remover de la sesion.
                nombresObjetos = "cliMovPend;dataClient;dateLiquidation;nameClient;fecha_liquidacion;clave_contrato;filesName;nombre_archivo;date_Capt".split(";");

                synchronized (session) {
                    this.remueveAtributos(nombresObjetos, session);
                    session.setAttribute("messageBean", messageBean);
                    session.setAttribute("cliMovPend", cliMovPend);
                }
                urlResponse = "menuSettlements.htm";
                break;

            case 6: //Mandamos llamar al jsp encargado de cambiar el Password
                urlResponse = "changePassword.htm";
                break;

            //Verificamos que el usuario que intenta cambiar el password tenga permisos.
            case 7:
                //Obtenemos los datos del usuario autentificado al inicio.
                userApp = (Usuario) session.getAttribute("userApp");

                String usuario1 = userApp.getUsuario();
                String contrasenna1 = userApp.getPassword();

                //Obtenemos los datos del usuario que desea cambiar el Password
                if (request.getParameter("usuario") != null && !request.getParameter("usuario").equals("")) {
                    usuario = request.getParameter("usuario").toString().trim();
                }
                // Se obtiene la contrasenna del usuario
                if (request.getParameter("contrasenna") != null && !request.getParameter("contrasenna").equals("")) {
                    contrasenna = request.getParameter("contrasenna").toString().trim();
                }

                if (usuario1.equals(usuario) && contrasenna1.equals(contrasenna)) {
                    cambiaPassword = "cambiaPassword";
                    synchronized (session) {
                        session.setAttribute("cambiaPassword", cambiaPassword);
                    }
                } else {
                    messageBean = new Message();
                    messageBean.setDesc("Error: Usuario y/o Contraseña incorrectos");
                    synchronized (session) {
                        session.setAttribute("messageBean", messageBean);
                    }
                }
                urlResponse = "changePassword.htm";
                break;

            case 8:
                //Obtenemos los datos del usuario autentificado al inicio.
                userApp = (Usuario) session.getAttribute("userApp");
                String newPass1 = null,
                 newPass2 = null;

                //Obtenemos el nuevo Password
                if (request.getParameter("newPass1") != null && !request.getParameter("newPass1").equals("")) {
                    newPass1 = request.getParameter("newPass1").toString().trim();
                }
                // Confirmamos el nuevo Password
                if (request.getParameter("newPass2") != null && !request.getParameter("newPass2").equals("")) {
                    newPass2 = request.getParameter("newPass2").toString().trim();
                }
                if (newPass1.equals(newPass2)) {
                    boolean actualiza = ModeloLiquidation.actualizaUsuario(userApp, newPass1);
                    if (actualiza) {
                        userApp.setPassword(newPass1);
                        messageBean = new Message();
                        messageBean.setDesc(" Contraseña actualizada correctamente ");
                        synchronized (session) {
                            session.setAttribute("messageBean", messageBean);
                            session.setAttribute("userApp", userApp);
                            session.removeAttribute("cambiaPassword");
                        }
                        urlResponse = "menuSettlements.htm";
                    } else {
                        messageBean = new Message();
                        messageBean.setDesc(" Error actualizando p0assword ");
                        synchronized (session) {
                            session.setAttribute("messageBean", messageBean);
                            session.removeAttribute("cambiaPassword");
                        }
                        urlResponse = "menuSettlements.htm";
                    }
                } else {
                    messageBean = new Message();
                    messageBean.setDesc(" No coincide la nueva contraseña con la confirmación ");
                    synchronized (session) {
                        session.setAttribute("messageBean", messageBean);
                    }
                    urlResponse = "changePassword.htm";
                }
                break;

            case 9: //Regresa una sesion anterior en cambiaPassword
                synchronized (session) {
                    session.removeAttribute("cambiaPassword");
                }
                urlResponse = "menuSettlements.htm";
                break;

            //Nombres de Lay-Outs Cargados
            case 10:

                //Almacenamos las variables de sesion a remover.
                nombresObjetos = "fecha_liquidacion;filesName;nombre_archivo;date_Capt".split(";");
                //Se remueven los objetos especificados de la sesion
                synchronized (session) {
                    this.remueveAtributos(nombresObjetos, session);
                }
                clave_contrato = (String) session.getAttribute("clave_contrato");
                // Se obtiene la fecha de liquidacion seleccionada
                if (request.getParameter("fecha_liquidacion") != null && !request.getParameter("fecha_liquidacion").equals("")) {
                    fecha_liquidacion = request.getParameter("fecha_liquidacion");
                    System.out.println("fecha_liquidacion en request:" + fecha_liquidacion);
                }
                if (clave_contrato != null && fecha_liquidacion != null) {
                    //Llamamos al modelo para obtener la fecha de captura del Lay-Out por parte del cliente
                    filesName = ModeloLiquidation.getFilesName(clave_contrato, fecha_liquidacion, "A");
                    //Verificamos si ocurrio algún error
                    if (filesName != null) {
                        if (filesName.size() <= 1) {
                            messageBean = new Message();
                            messageBean.setDesc(" No se cuenta con algún Lay-Out ");
                        }
                    } else {
                        //Si ocurrio algún error creamos un mensaje descriptivo.
                        messageBean = new Message();
                        messageBean.setDesc(" Error obteniendo Lay-Outs cargados en el sistema  ");
                    }
                } else {
                    messageBean = new Message();
                    messageBean.setDesc(" Error obteniendo datos de la sesion ");
                }
                //Sincronizamos para almacenas variables a la sesion
                synchronized (session) {
                    session.setAttribute("messageBean", messageBean);
                    session.setAttribute("clave_contrato", clave_contrato);
                    session.setAttribute("fecha_liquidacion", fecha_liquidacion);
                    session.setAttribute("filesName", filesName);
                }

                urlResponse = "menuSettlements.htm";

                break;

            //Obtenemos los Clientes con Movimientos a Bancos Extranjeros.
            case 11: {
                //Almacenamos las variables de sesion a remover.
                temp = "correo_c;saldos;tipo_honorario_c;honorario_sin_iva_c;saldo_actual;confirmacionLote;aportaciones_restituciones;movPending;modAutoriza;modActualiza;cliMov5Pend;";
                temp += "clave_contratoC;nombre_cliente;domicilio_cliente;saldo_cliente;fecha_ini;fecha_fin;reporte;reporteSize;liquidaciones;liquidacionesSize; clave_contratoC;nombre_cliente;domicilio_cliente;saldo_cliente;fecha_ini;fecha_fin;reporte;reporteSize;liquidaciones;liquidacionesSize;allMovimientos;consultar_movimientos;";
                temp += "c_contrato;dateLiqMov5;filesNameMov5;selDateLiqMov5;claveContratoM5;fechaUsuarioOpera;namesFideiMov5;selFileNameMov5;usuario_opera_lote;selNameFideiMov5;";
                temp += "importeMonedaExtranjera;nombre_fideicomitente;";
                //variables correspondientes al modulo de consulta Ejecutiva
                temp += "fecha_fin;fecha_fin_2;fecha_ini;fecha_ini_2;allMovimientos;estados_de_cuenta;cuentasOrigen;consulta_ejecutiva;"
                        + "fecha_ini_;fecha_fin_;fecha_ini;fecha_fin;movimientosPeriodo;movimientosPeriodoSize;"
                        + "clave_contratoC;cuenta_origen;fecha_fin_2;fecha_ini_2;vector_consulta_total;vector_consulta;vector_consultaSize";
                nombresObjetos = temp.split(";");
                //Se remueven los objetos especificados de la sesion
                synchronized (session) {
                    this.remueveAtributos(nombresObjetos, session);
                }
                modActualiza = "modActualiza";
                String path = "";
                boolean incluir_actualizados = false;
                if (aux.equals("ActualizarMovsExtranjeros:11")) {
                    path = "?accionImporteM5=" + "actualizaImporteM5";
                }
                if (aux.equals("ModificarMovsExtranjeros:11")) {
                    path = "?accionImporteM5=" + "modificaImporteM5";
                    incluir_actualizados = true;
                }
                //Obtenemos del modelo los clientes que tienen movimientos pendientes por operar
                cliMov5Pend = ModelUpdate.getFideicomisosMT5("P", incluir_actualizados);

                if (cliMov5Pend != null) {
                    if (cliMov5Pend.size() <= 1) {
                        messageBean = new Message();
                        messageBean.setDesc(" No se cuenta con movimientos a Bancos Extranjeros ");
                    }
                } else {
                    messageBean = new Message();
                    messageBean.setDesc(" Error obteniendo fideicomisos ");
                }
                // Se sincronizan los clientes con la session del usuario
                synchronized (session) {
                    session.setAttribute("messageBean", messageBean);
                    session.setAttribute("modActualiza", modActualiza);
                    session.setAttribute("cliMov5Pend", cliMov5Pend);
                }
                urlResponse = "menuSettlements.htm" + path;
                break;
            }

            //Obtenemos las fechas de Liquidación con Movimientos a Bancos Extranjeros.
            case 12: {
                //Almacenamos las variables de sesion a remover.
                nombresObjetos = "c_contrato;dateLiqMov5;selDateLiqMov5;claveContratoM5;filesNameMov5;selFileNameMov5;fechaUsuarioOpera;usuario_opera_lote;namesFideiMov5;selNameFideiMov5;importeMonedaExtranjera;nombre_fideicomitente".split(";");
                //Se remueven los objetos especificados de la sesion
                synchronized (session) {
                    this.remueveAtributos(nombresObjetos, session);
                }
                //Se obtiene la accion seleccionada por el usuario
                String accionImporteM5 = "";
                boolean incluir_actualizados = false;
                if (request.getParameter("accionImporteM5") != null && !request.getParameter("accionImporteM5").equals("")) {
                    accionImporteM5 = request.getParameter("accionImporteM5");
                }
                if (accionImporteM5.equals("modificaImporteM5")) {
                    incluir_actualizados = true;
                }

                // Obtenemos el nombre del cliente seleccionado por el usuario.
                if (request.getParameter("cliente") != null && !request.getParameter("cliente").equals("")) {
                    clave_contrato = request.getParameter("cliente");
                }
                //Verificamos si capturamos correctamente al cliente seleccionado.
                if (clave_contrato != null) {
                    // Llamamos al modelo para obtener las fechas de liquidación con movimientos a Bancos Extranjeros.
                    dateLiqMov5 = ModelUpdate.getDateLiqMov5(clave_contrato, "P", incluir_actualizados);
                    if (dateLiqMov5 != null) {
                        if (dateLiqMov5.size() <= 1) {
                            dateLiqMov5 = null;
                        } else {
//                            messageBean = new Message();
//                            messageBean.setDesc(" Seleccione Fideicomitente ");
                            nombre_cliente = ModelUpdate.getNombre_fideicomitente();
                        }
                    } else {
                        messageBean = new Message();
                        messageBean.setDesc(" Error obteniendo fechas en las que tendrá lugar la liquidación. ");
                    }
                } else {
                    messageBean = new Message();
                    messageBean.setDesc(" Error obteniendo el fideicomitente seleccionado ");
                }
                //Sincronizamos para almacenar las variables de sesion.
                synchronized (session) {
                    session.setAttribute("messageBean", messageBean);
                    session.setAttribute("c_contrato", clave_contrato);
                    session.setAttribute("nombre_fideicomitente", nombre_cliente);
                    session.setAttribute("dateLiqMov5", dateLiqMov5);
                }
                urlResponse = "menuSettlements.htm?accionImporteM5=" + accionImporteM5;
                break;
            }

            //Obtenemos los LayOut's con movimientos pendientes del cliente y fecha seleccionada
            case 13: {
                //Almacenamos las variables de sesion a remover.
                nombresObjetos = "selDateLiqMov5;filesNameMov5;selFileNameMov5;fechaUsuarioOpera;usuario_opera_lote;namesFideiMov5;selNameFideiMov5;importeMonedaExtranjera".split(";");
                //Se remueven los objetos especificados de la sesion.
                synchronized (session) {
                    this.remueveAtributos(nombresObjetos, session);
                }
                //Obtenemos las Variables de Sesión.
                clave_contrato = (String) session.getAttribute("c_contrato");
                //Se obtiene la accion seleccionada por el usuario
                String accionImporteM5 = "";
                boolean incluir_actualizados = false;
                if (request.getParameter("accionImporteM5") != null && !request.getParameter("accionImporteM5").equals("")) {
                    accionImporteM5 = request.getParameter("accionImporteM5");
                }
                if (accionImporteM5.equals("modificaImporteM5")) {
                    incluir_actualizados = true;
                }
                // Se obtiene la fecha de liquidación seleccionada por el usuario.
                if (request.getParameter("fecha_liquidacion") != null && !request.getParameter("fecha_liquidacion").equals("")) {
                    fecha_liquidacion = request.getParameter("fecha_liquidacion");
                }
                //Verificamos si capturamos satisfactoriamente la información.
                if (fecha_liquidacion != null && clave_contrato != null) {
                    //Llamamos al modelo para obtener los LayOut's cargados para el cliente y fecha seleccionados.
                    filesName = ModelUpdate.getFilesName(clave_contrato, fecha_liquidacion, "P", incluir_actualizados);
                    //Verificamos si ocurrio algún error.
                    if (filesName != null) {
//                        if (filesName.size() <= 1) {
//                            messageBean = new Message();
//                            messageBean.setDesc(" Seleccione fecha de liquidación ");
//                        }
                    } else {
                        //Si ocurrio algún error creamos un mensaje descriptivo.
                        messageBean = new Message();
                        messageBean.setDesc(" Error obteniendo Lay-Outs almacenados ");
                    }
                } else {
                    //Si ocurrio algún error creamos un mensaje descriptivo.
                    messageBean = new Message();
                    messageBean.setDesc(" Error obteniendo datos de la sesión ");
                }
                //Sincronizamos para almacenas variables a la sesion.
                synchronized (session) {
                    session.setAttribute("messageBean", messageBean);
                    session.setAttribute("selDateLiqMov5", fecha_liquidacion);
                    session.setAttribute("filesNameMov5", filesName);
                }
                urlResponse = "menuSettlements.htm?accionImporteM5=" + accionImporteM5;
                break;
            }
            //Obtenemos el conjunto de Fideicomisarios en el Extranjero.
            case 14: {
                idx = 0;
                String monto = "";
                String t_moneda = "";
                String monto_mxp = "0";
                boolean valida = false;
                //Almacenamos las variables de sesion a remover.
                nombresObjetos = "fechaUsuarioOpera;namesFideiMov5;selNameFideiMov5;usuario_lote_opera;importeMonedaExtranjera".split(";");
                //Se remueven los objetos especificados de la sesion
                synchronized (session) {
                    this.remueveAtributos(nombresObjetos, session);
                }
                //Obtenemos las Variables de Sesión
                nombre_cliente = (String) session.getAttribute("nombre_fideicomitente");
                clave_contrato = (String) session.getAttribute("c_contrato");
                fecha_liquidacion = (String) session.getAttribute("selDateLiqMov5");
                fileName = (String) session.getAttribute("selFileNameMov5");
                //Se obtiene la accion seleccionada por el usuario
                String accionImporteM5 = "";
                boolean incluir_actualizados = false;
                if (request.getParameter("accionImporteM5") != null && !request.getParameter("accionImporteM5").equals("")) {
                    accionImporteM5 = request.getParameter("accionImporteM5");
                }
                if (accionImporteM5.equals("modificaImporteM5")) {
                    incluir_actualizados = true;
                }
                // Se obtiene el nombre del cliente a procesar.
                if (request.getParameter("nombre_archivo") != null && !request.getParameter("nombre_archivo").equals("")) {
                    fileName = request.getParameter("nombre_archivo");
                }
                try {
                    if (fileName != null && !fileName.equals("-Seleccione-")) {
                        //Llamamos al modelo para obtener la fecha en que se generaron las transacciones correspondientes.
                        fecha_usuario_opera = ModelUpdate.getFechaTransacciones(clave_contrato, fecha_liquidacion, fileName, "P");
                        //Llamamos al modelo para obtener los fideicomisarios en el extranjero.
                        namesFideiMov5 = ModelUpdate.getFideiBancoExtranjero(clave_contrato, fecha_liquidacion, fileName, "P", incluir_actualizados);
                        //Verificamos si ocurrio algun error.
                        if (namesFideiMov5 != null) {

                            name_fidei = namesFideiMov5;
                            //Obtenemos el importe en moneda extranjera para este empleado.
                            monto = ModelUpdate.getImporteMonedaExtranjera(nombre_cliente, clave_contrato, fecha_liquidacion, fileName, name_fidei, "P");
                            if (monto.equals("") || monto.equals("Error verificando importe de liquidación")) {
                                messageBean = new Message();
                                messageBean.setDesc(" Error obteniendo importe de liquidación ");
                                valida = false;
                            } else {
                                String[] infoMonedas = monto.split("%");
                                if (infoMonedas.length == 3) {
                                    monto = infoMonedas[0];
                                    t_moneda = infoMonedas[1];
                                    monto_mxp = infoMonedas[2];
                                } else {
                                    monto = "0";
                                    monto_mxp = "0";
                                    t_moneda = "NULL";
                                }
                                valida = true;
                            }

                            if (!fecha_usuario_opera.equals("")) {
                                idx = fecha_usuario_opera.indexOf(",");
                                if (idx > 0) {
                                    usuario_opera = fecha_usuario_opera.substring(idx + 1, fecha_usuario_opera.length());
                                    fecha_usuario_opera = fecha_usuario_opera.substring(0, idx);
                                } else {
                                    messageBean = new Message();
                                    messageBean.setDesc(" Error obteniendo el usuario de operación ");
                                }
                            }
                        } else {
                            messageBean = new Message();
                            messageBean.setDesc(" Error obteniendo fideicomisarios pendientes ");
                        }
                    }
                    //Sincronizamos para almacenas variables a la sesion.
                    synchronized (session) {
                        if (valida) {
                            session.setAttribute("fechaUsuarioOpera", fecha_usuario_opera);
                            session.setAttribute("usuario_opera_lote", usuario_opera);
                            session.setAttribute("namesFideiMov5", namesFideiMov5);
                            session.setAttribute("selFileNameMov5", fileName);
                            session.setAttribute("selNameFideiMov5", name_fidei);
                            session.setAttribute("importeMonedaExtranjera", monto);
                            session.setAttribute("tipo_moneda", t_moneda);
                            request.setAttribute("monto_mxp", monto_mxp);
                        }
                        session.setAttribute("messageBean", messageBean);
                    }
                } catch (Exception e) {
                }
                urlResponse = "menuSettlements.htm?accionImporteM5=" + accionImporteM5;
                request.getRequestDispatcher(urlResponse).forward(request, response);
                enviado = true;
                break;
            }

            //Obtenemos el Importe en Moneda Extranjera del Fideicomisario
            case 15: {
                String monto = "";
                String t_moneda = "";
                boolean valida = false;

                //Almacenamos las variables de sesion a remover.
                nombresObjetos = "selNameFideiMov5;importeMonedaExtranjera".split(";");
                //Se remueven los objetos especificados de la sesion
                synchronized (session) {
                    this.remueveAtributos(nombresObjetos, session);
                }
                //Obtenemos las Variables de Sesión
                nombre_cliente = (String) session.getAttribute("nombre_fideicomitente");
                clave_contrato = (String) session.getAttribute("c_contrato");
                fecha_liquidacion = (String) session.getAttribute("selDateLiqMov5");
                fileName = (String) session.getAttribute("selFileNameMov5");

                //Se obtiene la accion seleccionada por el usuario
                String accionImporteM5 = "";
                if (request.getParameter("accionImporteM5") != null && !request.getParameter("accionImporteM5").equals("")) {
                    accionImporteM5 = request.getParameter("accionImporteM5");
                }

                // Se obtiene el nombre del Fideicomisario (Empleado).
                if (request.getParameter("nombre_fideicomisario") != null && !request.getParameter("nombre_fideicomisario").equals("")) {
                    name_fidei = request.getParameter("nombre_fideicomisario");
                }
                if (!name_fidei.equals("---------- Seleccione ----------")) {
                    //Obtenemos el importe en moneda extranjera para este empleado.
                    monto = ModelUpdate.getImporteMonedaExtranjera(nombre_cliente, clave_contrato, fecha_liquidacion, fileName, name_fidei, "P");
                    if (monto.equals("") || monto.equals("Error verificando importe de liquidación")) {
                        messageBean = new Message();
                        messageBean.setDesc(" Error obteniendo importe de liquidación ");
                        valida = false;
                    } else {
                        idx = monto.indexOf("|");
                        if (idx > 0) {
                            t_moneda = monto.substring(idx + 1, monto.length());
                            monto = monto.substring(0, idx);
                        } else {
                            monto = "";
                        }
                        valida = true;
                    }
                }
                //Sincronizamos para almacenas variables a la sesion.
                synchronized (session) {
                    if (valida) {
                        session.setAttribute("selNameFideiMov5", name_fidei);
                        session.setAttribute("importeMonedaExtranjera", monto);
                        session.setAttribute("tipo_moneda", t_moneda);
                    }
                    session.setAttribute("messageBean", messageBean);
                }

                urlResponse = "menuSettlements.htm?accionImporteM5=" + accionImporteM5;
                break;
            }

            case 16: {
                boolean actualiza = false;
                String almacena = "";
                String imp_mxp_str = null;
                double importe_mxp = 0;

                //Obtenemos las Variables de Sesión
                userApp = (Usuario) session.getAttribute("userApp");
                nombre_cliente = (String) session.getAttribute("nombre_fideicomitente"); //Nombre del Fideicomitente Seleccionado.
                clave_contrato = (String) session.getAttribute("c_contrato"); //Clave del contrato asociado al fideicomitente.
                fecha_liquidacion = (String) session.getAttribute("selDateLiqMov5"); // Fecha de liquidación seleccionada.
                fileName = (String) session.getAttribute("selFileNameMov5"); //Archivo seleccionado.
                name_fidei = (String) session.getAttribute("selNameFideiMov5"); //Nombre del Fideicomisario a Actualizar importe.
                formato_fecha = ModeloLiquidation.getFormatoFecha(fecha_liquidacion);
                //Se obtiene la accion seleccionada por el usuario
                String accionImporteM5 = "";
                boolean incluir_actualizados = false;
                if (request.getParameter("accionImporteM5") != null && !request.getParameter("accionImporteM5").equals("")) {
                    accionImporteM5 = request.getParameter("accionImporteM5");
                }
                if (accionImporteM5.equals("modificaImporteM5")) {
                    incluir_actualizados = true;
                }
                if (!formato_fecha.equals("")) {
                    urlArchivo = ".\\inetpub\\ftproot\\Reportes Liquidacion\\" + clave_contrato + "\\" + formato_fecha + "\\";
                    // Se obtiene el importe en MXP.
                    if (request.getParameter("importe_MXP") != null && !request.getParameter("importe_MXP").equals("")) {
                        imp_mxp_str = request.getParameter("importe_MXP");
                        try {
                            importe_mxp = Double.parseDouble(imp_mxp_str);
                            //Se da formato a la cadena que se obtuvo del request con el importe mxp
                            imp_mxp_str = new DecimalFormat("0.00").format(importe_mxp);
                        } catch (Exception e) {
                            imp_mxp_str = null;
                        }
                    }
                    //Verificamos si surgio algún error
                    almacena = modeloActualiza.verificaCanceladoM5(clave_contrato, fecha_liquidacion, fileName, name_fidei);
                    if (almacena.equals("")) {
                        if (imp_mxp_str != null) {
                            messageBean = new Message();
//                            //Actualizamos la información en la Base de datos.
//                            almacena = modeloActualiza.actualizaImporteMov5(imp_mxp_str, clave_contrato, fecha_liquidacion, fileName, name_fidei, "P");
//                            //Verificamos si se genero correctamente el reporte del fideicomisario.
//                            if (almacena.equals("")) {
                            id_archivo = ModeloLiquidation.getClaveArchivo(clave_contrato, fecha_liquidacion, fileName);
                            //Generamos el PDF correspondiente con la información actualizada.
                            actualiza = modeloActualiza.generaM5_PDF(nombre_cliente, clave_contrato, fecha_liquidacion, fileName, name_fidei, imp_mxp_str, "P", urlArchivo, id_archivo, getServletContext().getRealPath(File.separator));
                            //Verificamos si se almaceno correctamente en la BD el importe en MXP.
                            if (actualiza) {
                                //Actualizamos la información en la Base de datos.
                                almacena = modeloActualiza.actualizaImporteMov5(imp_mxp_str, clave_contrato, fecha_liquidacion, fileName, name_fidei, "P");
                                //Verificamos si se genero correctamente el reporte del fideicomisario.
                                if (almacena.equals("")) {
                                    synchronized (session) {
                                        session.removeAttribute("namesFideiMov5");
                                        session.removeAttribute("selNameFideiMov5");
                                        session.removeAttribute("importeMonedaExtranjera");
                                        session.setAttribute("namesFideiMov5", namesFideiMov5);
                                    }
                                    //Actualizamos el status de la transacción.
                                    usuario_opera = userApp.getUsuario();
                                    actualiza = modeloActualiza.actualizaStatusMod(clave_contrato, fecha_liquidacion, fileName, usuario_opera);
                                    //Verificamos si se actualizo correctamente el status del lote
                                    if (actualiza) {
                                        messageBean.setDesc(" Actualización de lote realizada correctamente ");
                                        nombresObjetos = "c_contrato;dateLiqMov5;selDateLiqMov5;filesNameMov5;selFileNameMov5;claveContratoM5;fechaUsuarioOpera;namesFideiMov5;selNameFideiMov5;usuario_opera_lote;importeMonedaExtranjera;nombre_fideicomitente".split(";");
                                        //Actualizamos la lista de clientes con movimientos a Bancos Extranjeros
                                        cliMov5Pend = ModelUpdate.getFideicomisosMT5("P", incluir_actualizados);
                                        //Se remueven los objetos especificados de la sesion
                                        synchronized (session) {
                                            session.removeAttribute("cliMov5Pend");
                                            this.remueveAtributos(nombresObjetos, session);
                                            session.setAttribute("cliMov5Pend", cliMov5Pend);
                                        }
                                    } else {
                                        messageBean.setDesc(" Error actualizando status de la transacción ");
                                    }
                                } else {
                                    messageBean.setDesc(almacena);
                                }
                            } else {
                                messageBean.setDesc(" Error generando reporte de liquidación ");
                            }
//                            } else {
//                                messageBean.setDesc(almacena);
//                            }
                        } else {
                            messageBean = new Message();
                            messageBean.setDesc(" Error al capturar importe en MXP ");
                        }
                    } else {
                        if (almacena.equals("ENCONTRADO")) {
                            messageBean = new Message();
                            messageBean.setDesc(" El movimiento ha sido cancelado parcialmente,favor de habilitarlo antes de actualizar el importe de liquidación.  ");
                        }
                        if (almacena.equals("ERROR")) {
                            messageBean = new Message();
                            messageBean.setDesc(" Error al verificar cancelación parcial de movimiento. ");
                        }
                    }
                } else {
                    messageBean = new Message();
                    messageBean.setDesc(" Error obteniendo formato de la fecha ");
                }
                synchronized (session) {
                    session.setAttribute("messageBean", messageBean);
                }
                urlResponse = "menuSettlements.htm?accionImporteM5=" + accionImporteM5;
                break;
            }

            /**
             * Inicio:Inicialización del Módulo de autorización de
             * Transacciones*
             */
            case 17:

                modAutoriza = "Autoriza movimientos";
                //Almacenamos las variables de sesion a remover.
                temp = "clave_contratoC;saldos;nombre_cliente;domicilio_cliente;saldo_cliente;fecha_ini;fecha_fin;reporte;reporteSize;liquidaciones;liquidacionesSize;allMovimientos;consultar_movimientos;";
                temp += "correo_c;tipo_honorario_c;honorario_sin_iva_c;saldo_actual;confirmacionLote;aportaciones_restituciones;movPending;modAutoriza;modActualiza;confirmacionLote;";
                temp += "cancelacionParcial;cancelacionTotal;clave_contratoC;cliente_c;fechas_liquidacionC;fecha_c;lotes_c;lote_c;total_movimientos;fecha_capturaC";
                //variables correspondientes al modulo de consulta Ejecutiva
                temp += "fecha_fin;fecha_fin_2;fecha_ini;fecha_ini_2;allMovimientos;estados_de_cuenta;cuentasOrigen;consulta_ejecutiva;"
                        + "fecha_ini_;fecha_fin_;fecha_ini;fecha_fin;movimientosPeriodo;movimientosPeriodoSize;"
                        + "clave_contratoC;cuenta_origen;fecha_fin_2;fecha_ini_2;vector_consulta_total;vector_consulta;vector_consultaSize";
                nombresObjetos = temp.split(";");

                //Se remueven los objetos especificados de la sesion
                synchronized (session) {
                    this.remueveAtributos(nombresObjetos, session);
                    session.setAttribute("modAutoriza", modAutoriza);
                }
                urlResponse = "menuSettlements.htm";
                break;
            /**
             * Fin:Inicialización del Módulo de autorización de Movimientos*
             */
            /**
             * Inicio:CONFIRMACIÓN de Movimientos*
             */
            case 18:
                /**
                 * Inicio:Se obtienen las claves de fideicomiso con
                 * transacciones pendientes por autorizar*
                 */
                temp = "clave_contratoC;saldos;nombre_cliente;domicilio_cliente;saldo_cliente;fecha_ini;fecha_fin;reporte;reporteSize;liquidaciones;liquidacionesSize; clave_contratoC;nombre_cliente;domicilio_cliente;saldo_cliente;fecha_ini;fecha_fin;reporte;reporteSize;liquidaciones;liquidacionesSize;allMovimientos;consultar_movimientos;";
                temp += "correo_c;tipo_honorario_c;honorario_sin_iva_c;saldo_actual;movPending;modActualiza;modAutoriza;confirmacionLote;aportaciones_restituciones;cancelacionParcial;cancelacionTotal;cliMovConfirma;allMovimientos;clave_contratoC;cliente_c;fechas_liquidacionC;fecha_c;lotes_c;lote_c;total_movimientos;fecha_capturaC;movs_cancelados";
                //variables correspondientes al modulo de consulta Ejecutiva
                temp += "fecha_fin;fecha_fin_2;fecha_ini;fecha_ini_2;allMovimientos;estados_de_cuenta;cuentasOrigen;consulta_ejecutiva;"
                        + "fecha_ini_;fecha_fin_;fecha_ini;fecha_fin;movimientosPeriodo;movimientosPeriodoSize;"
                        + "clave_contratoC;cuenta_origen;fecha_fin_2;fecha_ini_2;vector_consulta_total;vector_consulta;vector_consultaSize";
                nombresObjetos = temp.split(";");
                //Se remueven los variables de la sesion.
                synchronized (session) {
                    this.remueveAtributos(nombresObjetos, session);
                }
                confirmacionLote = "confirmacionLote";
                //Se obtienen los clientes que tienen movimientos pendientes por operar.
                cliMovConfirma = AuthorizationModel.getClaveFideicomitentes("P");
                //Se verifica si ocurrio algún error.
                if (cliMovConfirma != null) {
                    if (cliMovConfirma.size() >= 0 && cliMovConfirma.size() <= 1) {
                        messageBean = new Message();
                        messageBean.setDesc("Por el momento NO se cuenta con movimientos pendientes por autorizar");
                    }
                } else {
                    messageBean = new Message();
                    messageBean.setDesc("Error obteniendo claves de fideicomiso");
                }
                synchronized (session) {
                    session.setAttribute("cliMovConfirma", cliMovConfirma);
                    session.setAttribute("confirmacionLote", confirmacionLote);
                    session.setAttribute("messageBean", messageBean);
                }
                urlResponse = "menuSettlements.htm";
                break;
            /**
             * Inicio:CONFIRMACIÓN de Movimientos*
             */
            /**
             * Inicio:Se obtiene el nombre del fideicomitente y las fechas de
             * liquidación pendientes por autorizar*
             */
            case 19:
                //Se especifican las variables de sesion a remover.
                nombresObjetos = "clave_contratoC;cliente_c;fechas_liquidacionC;fecha_c;lotes_c;lote_c;total_movimientos;fecha_capturaC;movs_cancelados;nombre_empleado;apellidoP_empleado;apellidoM_empleado;cuenta_deposito;tipo_movimiento;clave_banco;importe_liquidacion;tipo_moneda;opcion_c;opciones_c;opcionC;opcionH".split(";");
                //Se remueven los variables de la sesion.
                synchronized (session) {
                    this.remueveAtributos(nombresObjetos, session);
                }
                // Se obtiene el nombre del cliente a procesar
                if (request.getParameter("cliente") != null && !request.getParameter("cliente").equals("")) {
                    clave_contrato = request.getParameter("cliente");
                }
                if (clave_contrato != null) {
                    if (!clave_contrato.equals("-----Seleccione-----")) {
                        Vector fechas_liquidacionC = AuthorizationModel.getFechasLiquidacionPendientes(clave_contrato, "P");
                        nombre_cliente = AuthorizationModel.getNombre_cliente();
                        if (fechas_liquidacionC != null && !nombre_cliente.equals("")) {
                            synchronized (session) {
                                //Clave de contrato
                                session.setAttribute("clave_contratoC", clave_contrato);
                                //Nombre del Fideicomitente
                                session.setAttribute("cliente_c", nombre_cliente);
                                //Fechas de Liquidación pendientes
                                session.setAttribute("fechas_liquidacionC", fechas_liquidacionC);
                            }
                        } else {
                            messageBean = new Message();
                            messageBean.setDesc(" Error obteniendo fechas de liquidación ");
                        }
                    }
                } else {
                    messageBean = new Message();
                    messageBean.setDesc(" Error obteniendo clave de fideicomiso ");
                }
                synchronized (session) {
                    session.setAttribute("messageBean", messageBean);
                }
                urlResponse = "menuSettlements2.htm";
                break;
            /**
             * Fin:Se obtiene el nombre del fideicomitente y las fechas de
             * liquidación pendientes por autorizar*
             */
            /**
             * Inicio:Se obtienen los lotes almacenados pendientes por
             * autorizar*
             */
            case 20:
                Vector filesNameC = null;
                //Se especifican las variables de sesion a remover.
                nombresObjetos = "fecha_c;lotes_c;lote_c;total_movimientos;fecha_capturaC;movs_cancelados;nombre_empleado;apellidoP_empleado;apellidoM_empleado;cuenta_deposito;tipo_movimiento;clave_banco;importe_liquidacion;tipo_moneda;opcion_c;opciones_c;opcionC;opcionH".split(";");
                //Se remueven los variables de la sesion.
                synchronized (session) {
                    this.remueveAtributos(nombresObjetos, session);
                }
                //Clave del contrato asociado al fideicomitente.
                clave_contrato = (String) session.getAttribute("clave_contratoC");
                // Se obtiene el nombre del cliente a procesar
                if (request.getParameter("fecha_liquidacion") != null && !request.getParameter("fecha_liquidacion").equals("")) {
                    fecha_liquidacion = request.getParameter("fecha_liquidacion");
                }
                if (clave_contrato != null && fecha_liquidacion != null) {
                    if (!fecha_liquidacion.equals("-Seleccione-")) {
                        filesNameC = ModeloLiquidation.getFilesName(clave_contrato, fecha_liquidacion, "P");
                        if (filesNameC == null) {
                            messageBean = new Message();
                            messageBean.setDesc(" Error obteniendo LayOuts pendientes");
                        }
                    }
                } else {
                    messageBean = new Message();
                    messageBean.setDesc(" Error obteniendo datos de la sesion ");
                }
                synchronized (session) {
                    session.setAttribute("messageBean", messageBean);
                    //Datos generales del Fideicomitente
                    session.setAttribute("fecha_c", fecha_liquidacion);
                    //Fechas de Liquidación pendientes
                    session.setAttribute("lotes_c", filesNameC);
                }
                urlResponse = "menuSettlements2.htm";
                break;
            /**
             * Fin:Se obtienen los lotes almacenados pendientes por autorizar*
             */
            /**
             * Inicio:Se obtiene la fecha de captura y número de movimientos de
             * un lote en particular *
             */
            case 21:
                //Se especifican las variables de sesion a remover.
                nombresObjetos = "lote_c;total_movimientos;fecha_capturaC".split(";");
                //Se remueven los variables de la sesion.
                synchronized (session) {
                    this.remueveAtributos(nombresObjetos, session);
                }
                //Clave de fideicomiso seleccionado.
                clave_contrato = (String) session.getAttribute("clave_contratoC");
                // Fecha de liquidación seleccionada.
                fecha_liquidacion = (String) session.getAttribute("fecha_c");
                // Se obtiene del LayOut seleccionado.
                if (request.getParameter("nombre_archivo") != null && !request.getParameter("nombre_archivo").equals("")) {
                    fileName = request.getParameter("nombre_archivo");
                }
                if (fileName != null && clave_contrato != null && fecha_liquidacion != null) {
                    if (!fileName.equals("-Seleccione-")) {
                        //Llamamos al modelo para obtener la fecha de captura del Lay-Out por parte del cliente
                        fecha_captura = ModeloLiquidation.getFechaCaptura(clave_contrato, fecha_liquidacion, fileName, "P");
                        if (fecha_captura == null) {
                            messageBean = new Message();
                            messageBean.setDesc("Error obteniendo la fecha de captura del lote");
                            fecha_captura = "";
                        }
                    }
                } else {
                    messageBean = new Message();
                    messageBean.setDesc(" Error obteniendo datos de la sesion ");
                }
                synchronized (session) {
                    session.setAttribute("messageBean", messageBean);
                    session.setAttribute("lote_c", fileName);
                    session.setAttribute("total_movimientos", ModeloLiquidation.total_movimientos);
                    session.setAttribute("fecha_capturaC", fecha_captura);
                }
                urlResponse = "menuSettlements.htm";
                break;
            /**
             * Fin:Se obtiene la fecha de captura y número de movimientos de un
             * lote en particular *
             */
            //se valida la confirmacion de movimientos, y se actualiza el nuevo Reporte de liquidación si es necesario
            case 22:
                messageBean = new Message();
                // Usuario que ingreso al sistema.
                userApp = (Usuario) session.getAttribute("userApp");
                //Clave de fideicomiso seleccionada.
                clave_contrato = (String) session.getAttribute("clave_contratoC");
                //Nombre del Fideicomitente seleccionado.
                nombre_cliente = (String) session.getAttribute("cliente_c");
                //Fecha de liquidación seleccionada.
                fecha_liquidacion = (String) session.getAttribute("fecha_c");
                //Nombre del LayOut seleccionado.
                fileName = (String) session.getAttribute("lote_c");
                //Variable para autorización.
                String autorizaL = "";
                persona_genera = "";

                if (userApp != null && clave_contrato != null && nombre_cliente != null && fecha_liquidacion != null && fileName != null) {

                    formato_fecha = ModeloLiquidation.getFormatoFecha(fecha_liquidacion);//Fecha en formato DDMMAA
                    if (!formato_fecha.equals("")) {
                        //Ruta donde se gereraran los reportes
                        urlArchivo = ".\\inetpub\\ftproot\\Reportes Liquidacion\\" + clave_contrato + "\\" + formato_fecha + "\\";
                        //Usuario que autoriza el lote.C
                        usuario = userApp.getUsuario();
                        //Nombre completo del usuario que autoriza.
                        persona_genera = userApp.getNombre_usuario();
                        //Identificador único del lote.
                        id_archivo = ModeloLiquidation.getClaveArchivo(clave_contrato, fecha_liquidacion, fileName);
                        if (id_archivo >= 0) {
                            autorizaL = modeloAutoriza.autorizaLote(nombre_cliente, clave_contrato, fecha_liquidacion, fileName, usuario, urlArchivo, persona_genera, id_archivo, "liquidaciones@fideicomisopsc.mx", "T", "A", "P", getServletContext().getRealPath(File.separator));
                            messageBean.setDesc(autorizaL);
                        } else {
                            messageBean.setDesc(" Error consultando identificador de lote ");
                        }
                    } else {
                        messageBean.setDesc(" Error obteniendo el formato de la fecha ");
                    }
                } else {
                    messageBean.setDesc(" Error obteniendo datos de la sesion ");
                }

                nombresObjetos = "clave_contratoC;cliente_c;fechas_liquidacionC;fecha_c;lotes_c;lote_c;total_movimientos;fecha_capturaC".split(";");
                synchronized (session) {
                    this.remueveAtributos(nombresObjetos, session);
                    session.setAttribute("messageBean", messageBean);
                }
                urlResponse = "menuSettlements.htm";
                break;

            case 23:
                /**
                 * Inicio:Cancelación total lote*
                 */
                nombresObjetos = "movPending;modActualiza;confirmacionLote;aportaciones_restituciones;cancelacionParcial;cancelacionTotal;cliMovConfirma;allMovimientos;clave_contratoC;cliente_c;fechas_liquidacionC;fecha_c;lotes_c;lote_c;total_movimientos;fecha_capturaC".split(";");
                //Se remueven los variables de la sesion.
                synchronized (session) {
                    this.remueveAtributos(nombresObjetos, session);
                }
                cancelacionTotal = "cancelacionTotal";
                //Se obtienen los clientes que tienen movimientos pendientes por operar.
                cliMovConfirma = AuthorizationModel.getClaveFideicomitentes("P");
                //Se verifica si ocurrio algún error.
                if (cliMovConfirma != null) {
                    if (cliMovConfirma.size() <= 1) {
                        messageBean = new Message();
                        messageBean.setDesc("No se cuenta con movimientos pendientes por el momento");
                    }
                } else {
                    messageBean = new Message();
                    messageBean.setDesc("Error obteniendo claves de fideicomiso");
                }
                // Se sincronizan los clientes con la session del usuario
                synchronized (session) {
                    session.setAttribute("messageBean", messageBean);
                    session.setAttribute("cancelacionTotal", cancelacionTotal);
                    session.setAttribute("cliMovConfirma", cliMovConfirma);
                }
                urlResponse = "menuSettlements.htm";
                break;

            case 24:
                messageBean = new Message();
                //Nombre del Fideicomitente seleccionado.
                nombre_cliente = (String) session.getAttribute("cliente_c");
                //Clave del contrato asociado al fideicomitente.
                clave_contrato = (String) session.getAttribute("clave_contratoC");
                //Fecha de liquidación seleccionada.
                fecha_liquidacion = (String) session.getAttribute("fecha_c");
                //Nombre del LayOut seleccionado.
                fileName = (String) session.getAttribute("lote_c");
                // Usuario que ingreso al sistema.
                userApp = (Usuario) session.getAttribute("userApp");
                String nombre_reporte = null;
                String cancelaT = "";
                opciones_c = new Vector();

                try {
                    if (userApp != null) {
                        if (clave_contrato != null && nombre_cliente != null && fecha_liquidacion != null && fileName != null) {
                            usuario = userApp.getUsuario();
                            clave_cliente = clave_contrato.substring(0, 9);
                            formato_fecha = ModeloLiquidation.getFormatoFecha(fecha_liquidacion);//Fecha en formato DDMMAA
                            if (!formato_fecha.equals("")) {
                                //Ruta donde se gereraran los reportes
                                urlArchivo = ".\\inetpub\\ftproot\\Reportes Liquidacion\\" + clave_contrato + "\\" + formato_fecha + "\\";
                                //Verificamos cuantos archivos diferentes se han almacenado para este cliente y fecha
                                id_archivo = ModeloLiquidation.getClaveArchivo(clave_contrato, fecha_liquidacion, fileName);
                                if (id_archivo > 0) {
                                    //Nombre del reporte de liquidación.
                                    nombre_reporte = ModeloLiquidation.getNombreResumenLiquidacion(clave_contrato, fecha_liquidacion, id_archivo);
                                    if (!nombre_reporte.equals("")) {
                                        correo_origen = "liquidaciones@fideicomisopsc.mx";
                                        correo_destino = AuthorizationModel.getCorreoUsuariosFideicomiso(clave_cliente, clave_contrato);
                                        if (!correo_destino.equals("")) {
                                            asunto = "CANCELACIÓN TOTAL " + clave_contrato + " " + fecha_liquidacion;
                                            cuerpoCorreo = AuthorizationModel.getEmailBody(opciones_c);
                                            //Cancelamos y notificamos de la cancelación.
                                            cancelaT = modeloAutoriza.cancelacionTotalLote(clave_cliente, nombre_cliente, clave_contrato, fecha_liquidacion, fileName, usuario, "C", "T", "P", correo_origen, correo_destino, asunto, cuerpoCorreo, urlArchivo, nombre_reporte);
                                        } else {
                                            cancelaT = " No se tiene registro del correo electrónico del usuario que cargo el lote.";
                                        }
                                    } else {
                                        cancelaT = " Error obteniendo el nombre del resumen de liquidación ";
                                    }
                                } else {
                                    cancelaT = " Error obteniendo identificador de lote ";
                                }
                            } else {
                                cancelaT = " Error obteniendo la ruta del reporte a cancelar ";
                            }
                        } else {
                            cancelaT = "Error obteniendo los datos seleccionados";
                        }
                    } else {
                        cancelaT = " Tiempo de sesión agotado";
                    }
                    messageBean.setDesc(cancelaT);
                    nombresObjetos = "cancelacionTotal;cliMovConfirma;allMovimientos;clave_contratoC;cliente_c;fechas_liquidacionC;fecha_c;lotes_c;lote_c;total_movimientos;fecha_capturaC".split(";");
                    synchronized (session) {
                        session.setAttribute("messageBean", messageBean);
                        this.remueveAtributos(nombresObjetos, session);
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                urlResponse = "menuSettlements.htm";
                break;

            case 25:
                /**
                 * Inicio:Inicialización del módulo cancelación parcial de
                 * movimientos.*
                 */
                nombresObjetos = "movPending;modActualiza;confirmacionLote;aportaciones_restituciones;cancelacionParcial;cancelacionTotal;cliMovConfirma;allMovimientos;clave_contratoC;cliente_c;fechas_liquidacionC;fecha_c;lotes_c;lote_c;total_movimientos;fecha_capturaC;nombre_empleado;apellidoP_empleado;apellidoM_empleado;cuenta_deposito;tipo_movimiento;clave_banco;importe_liquidacion;tipo_moneda;opcion_c;opciones_c;opcionC;opcionH;opcionM;motivo_c".split(";");
                //Se remueven los variables de la sesion.
                synchronized (session) {
                    this.remueveAtributos(nombresObjetos, session);
                }
                cancelacionParcial = "cancelacionParcial";

                AuthorizationModel.setElimina_movs(new ArrayList());
                //Se obtienen los clientes que tienen movimientos pendientes por operar.
                cliMovConfirma = AuthorizationModel.getClaveFideicomitentes("P");
                //Se verifica si ocurrio algún error.
                if (cliMovConfirma != null) {
                    if (cliMovConfirma.size() <= 1) {
                        messageBean = new Message();
                        messageBean.setDesc("No se cuenta con movimientos pendientes por el momento");
                    }
                } else {
                    messageBean = new Message();
                    messageBean.setDesc("Error al obtener claves de fideicomiso");
                }
                // Se sincronizan los clientes con la session del usuario
                synchronized (session) {
                    session.setAttribute("messageBean", messageBean);
                    session.setAttribute("cancelacionParcial", cancelacionParcial);
                    session.setAttribute("cliMovConfirma", cliMovConfirma);
                }
                urlResponse = "menuSettlements2.htm";
                break;
            /**
             * Fin:Inicialización del módulo cancelación parcial de
             * movimientos.*
             */
            case 26:
                nombresObjetos = "nombre_empleado;apellidoP_empleado;apellidoM_empleado;cuenta_deposito;tipo_movimiento;clave_banco;importe_liquidacion;tipo_moneda;opcion_c;opciones_c;opcionC;opcionH".split(";");
                //Se remueven los variables de la sesion.
                synchronized (session) {
                    this.remueveAtributos(nombresObjetos, session);
                }
                //Clave del contrato asociado al fideicomitente.
                clave_contrato = (String) session.getAttribute("clave_contratoC");
                //Fecha de liquidación seleccionada.
                fecha_liquidacion = (String) session.getAttribute("fecha_c");
                // Se obtiene el nombre del cliente a procesar
                if (request.getParameter("nombre_archivo") != null && !request.getParameter("nombre_archivo").equals("")) {
                    fileName = request.getParameter("nombre_archivo");
                }
                if (clave_contrato != null && fecha_liquidacion != null && fileName != null && fileName != null) {
                    //Obtener la fecha de almacenamiento de lote y el número de movimientos.
                    fecha_captura = ModeloLiquidation.getFechaCaptura(clave_contrato, fecha_liquidacion, fileName, "P");
                    opciones_c = new Vector();
                    opciones_c.add("-----Seleccione-----");
                    opciones_c.add("Cancelar Movimiento");
                    opciones_c.add("Habilitar Movimiento");
                } else {
                    messageBean = new Message();
                    messageBean.setDesc(" Error obteniendo datos de la sesion ");
                }
                //Actualizamos las variables se sesion.
                synchronized (session) {
                    session.setAttribute("messageBean", messageBean);
                    session.setAttribute("fecha_capturaC", fecha_captura);
                    session.setAttribute("opciones_c", opciones_c);
                    session.setAttribute("lote_c", fileName);
                    session.setAttribute("total_movimientos", ModeloLiquidation.total_movimientos);
                }
                urlResponse = "menuSettlements2.htm";
                break;
            case 27:
                String opcionC = null;
                String opcionH = null;
                String opcionM = null;
                String opcion_c = null;
                /**
                 * Inicio:Inicialización del módulo cancelación parcial de
                 * movimientos.*
                 */
                nombresObjetos = "mov_cancelado;opcionC;opcionH".split(";");
                //Se remueven los variables de la sesion.
                synchronized (session) {
                    this.remueveAtributos(nombresObjetos, session);
                }
                if (request.getParameter("opcionC") != null && !request.getParameter("opcionC").equals("")) {
                    opcion_c = request.getParameter("opcionC");
                }
                if (opcion_c != null) {
                    if (opcion_c.equals("Habilitar Movimiento")) {
                        opcionH = opcion_c;
                        opcionC = null;
                        opcionM = null;
                    } else if (opcion_c.equals("Cancelar Movimiento")) {
                        opcionC = opcion_c;
                        opcionH = null;
                        opcionM = null;
                    }
                } else {
                    messageBean = new Message();
                    messageBean.setDesc(" Error al obtener información capturada ");
                }
                motivosCancelacion = new Vector();
                motivosCancelacion.add("--------------- Seleccione ---------------");
                motivosCancelacion.add("SPEI DEVUELTO");
                motivosCancelacion.add("CUENTA INVÁLIDA");
                motivosCancelacion.add("LA CUENTA NO EXISTE");
                motivosCancelacion.add("EL BENEFICIARIO NO COINCIDE CON EL NÚMERO DE CUENTA");
                motivosCancelacion.add("FONDOS INSUFICIENTES EN EL PATRIMONIO");
                motivosCancelacion.add("NO ESTAN CUBIERTOS LOS HONORARIOS CORRESPONDIENTES");

                //Actualizamos las variables se sesion.
                synchronized (session) {
                    session.setAttribute("messageBean", messageBean);
                    session.setAttribute("opcion_c", opcion_c);
                    session.setAttribute("opcionC", opcionC);
                    session.setAttribute("opcionH", opcionH);
                    session.setAttribute("opcionM", opcionM);
                    session.setAttribute("motivosCancelacion", motivosCancelacion);
                    session.setAttribute("cancela_movs", "Canela_movimiento");
                }
                urlResponse = "menuSettlements2.htm";
                break;

            //Cancelación de Movimiento
            case 28:
                //Mensaje en respuesta al usuario.
                messageBean = new Message();
                movimiento = new Movimiento();
                //Clave de fideicomiso seleccionado.
                clave_contrato = (String) session.getAttribute("clave_contratoC");
                //Fecha de liquidación seleccionada.
                fecha_liquidacion = (String) session.getAttribute("fecha_c");
                //Nombre del LayOut seleccionado.
                fileName = (String) session.getAttribute("lote_c");
                //Mensaje informativo del resultado de loa transacción.
                nombresObjetos = "mov_cancelado".split(";");
                //Se remueven los variables de la sesion.
                synchronized (session) {
                    this.remueveAtributos(nombresObjetos, session);
                }
                try {
                    movimiento.setClave_contrato(clave_contrato);
                    movimiento.setFecha_liquidacion(fecha_liquidacion);
                    movimiento.setNombre_archivo(fileName);
                    // Se obtiene la cuenta de depósito especificada por el usuario.
                    if (request.getParameter("cuenta_deposito") != null) {
                        movimiento.setCuenta_deposito(request.getParameter("cuenta_deposito").toString().trim());
                    }
                    // Se obtiene el nombre del fideicomisario.
                    if (request.getParameter("nombre_empleado") != null) {
                        movimiento.setNombre_empleado(request.getParameter("nombre_empleado").toString().trim());
                    }
                    // Se obtiene el apellido paterno del fideicomisario.
                    if (request.getParameter("apellidoP_empleado") != null) {
                        movimiento.setApellidoP_empleado(request.getParameter("apellidoP_empleado").toString().trim());
                    }
                    // Se obtiene el apellido materno del fideicomisario.
                    if (request.getParameter("apellidoM_empleado") != null) {
                        movimiento.setApellidoM_empleado(request.getParameter("apellidoM_empleado").toString().trim());
                    }
                    // Se obtiene el identificador del fideicomisario.
                    if (request.getParameter("id_empleado") != null) {
                        movimiento.setNumero_empleado(request.getParameter("id_empleado").toString().trim());
                    }
                    // Se obtiene el puesto del fideicomisario.
                    if (request.getParameter("puesto_empleado") != null) {
                        movimiento.setPuesto_empleado(request.getParameter("puesto_empleado").toString().trim());
                    }
                    // Se obtiene el departamento al que pertenece el fideicomisario.
                    if (request.getParameter("depto_empleado") != null) {
                        movimiento.setDepartamento_empleado(request.getParameter("depto_empleado").toString().trim());
                    }
                    // Se obtiene la fecha de ingreso del fideicomisario.
                    if (request.getParameter("fecha_ingreso") != null) {
                        movimiento.setFecha_ingreso(request.getParameter("fecha_ingreso").toString().trim());
                    }
                    // Se obtiene el tipo de movimiento.
                    if (request.getParameter("tipo_movimiento") != null) {
                        movimiento.setTipo_movimiento(request.getParameter("tipo_movimiento").toString().trim());
                    }
                    //Se obtiene la clave del banco.
                    if (request.getParameter("clave_banco") != null) {
                        movimiento.setClave_banco(request.getParameter("clave_banco").toString().trim());
                    }
                    //Se obtiene el importe de liquidación.
                    if (request.getParameter("importe_liquidacion") != null) {
                        movimiento.setImporte_liquidacion(request.getParameter("importe_liquidacion").toString().trim());
                    }
                    //se obtiene el tipo de moneda del importe de liquidación.
                    if (request.getParameter("tipo_moneda") != null) {
                        movimiento.setClave_moneda(request.getParameter("tipo_moneda").toString().trim());
                    }
                    // Se obtiene la persona a la que se le hará envio del cheque.
                    if (request.getParameter("envio_cheque") != null) {
                        movimiento.setNombre_receptor_cheque(request.getParameter("envio_cheque").toString().trim());
                    }
                    // Se obtiene el domicilio de la persona a la que se le hará el envio de cheques.
                    if (request.getParameter("destino_cheque") != null) {
                        movimiento.setDomicilio_destino_cheque(request.getParameter("destino_cheque").toString().trim());
                    }
                    //Se obtiene el teléfono del envio del cheque.
                    if (request.getParameter("telefono_cheque") != null) {
                        movimiento.setTel_destino_cheque(request.getParameter("telefono_cheque").toString().trim());
                    }
                    //Se obtiene el correo del envio del cheque.
                    if (request.getParameter("correo_cheque") != null) {
                        movimiento.setCorreo_destino_cheque(request.getParameter("correo_cheque").toString().trim());
                    }
                    //Se obtiene el nombre del banco extranjero.
                    if (request.getParameter("banco_extranjero") != null) {
                        movimiento.setNombre_banco_extranjero(request.getParameter("banco_extranjero").toString().trim());
                    }
                    //Se obtiene el domicilio del banco extranjero.
                    if (request.getParameter("domicilio_extranjero") != null) {
                        movimiento.setDomicilio_banco_extranjero(request.getParameter("domicilio_extranjero").toString().trim());
                    }
                    //Se obtiene el país del banco extranjero.
                    if (request.getParameter("pais_extranjero") != null) {
                        movimiento.setPais_banco_extranjero(request.getParameter("pais_extranjero").toString().trim());
                    }
                    //Se obtiene el aba_bic del movimiento.
                    if (request.getParameter("aba_bic") != null) {
                        movimiento.setABA_BIC(request.getParameter("aba_bic").toString().trim());
                    }
                    //Se obtiene el nombre del fideicomisario en el extranjero.
                    if (request.getParameter("nombre_fidei_extranjero") != null) {
                        movimiento.setNombre_empleado_banco_extranjero(request.getParameter("nombre_fidei_extranjero").toString().trim());
                    }
                    //Se obtiene ela dirección del fideicomisario en el extranjero.
                    if (request.getParameter("direccion_fidei_extranjero") != null) {
                        movimiento.setDom_empleado_banco_extranjero(request.getParameter("direccion_fidei_extranjero").toString().trim());
                    }

                    //Se obtiene el país del fideicomisario en el extranjero.
                    if (request.getParameter("pais_fidei_extranjero") != null) {
                        movimiento.setPais_empleado_banco_extranjero(request.getParameter("pais_fidei_extranjero").toString().trim());
                    }
                    //Se obtiene el teléfono del fideicomisario en el extranjero.
                    if (request.getParameter("tel_fidei_extranjero") != null) {
                        movimiento.setTel_empleado_banco_extranjero(request.getParameter("tel_fidei_extranjero").toString().trim());
                    }
                    movimiento.setIsEmpty(false);

                    //Varificamos que se encuentre registrado un movimiento con los datos especificados.
                    mov_cancelado = ModeloLiquidation.getDatosMovimiento(movimiento, "P");
                    //Verificamos si surgió algún error al realizar la verificación anterior.
                    if (mov_cancelado != null) {
                        motivo_cancelacion = mov_cancelado.getMotivo_cancelacion();
                        messageBean.setDesc(motivo_cancelacion);
                    } else {
                        messageBean.setDesc("Error obteniendo información del movimiento a cancelar");
                        mov_cancelado = movimiento;
                    }
                } catch (Exception e) {
                    messageBean.setDesc(" Error al realizar la transacción ");
                }
                //Actualizamos las variables se sesion.
                synchronized (session) {
                    session.setAttribute("messageBean", messageBean);
                    session.setAttribute("mov_cancelado", mov_cancelado);
                }
                urlResponse = "menuSettlements.htm";
                break;

            case 29:
                //Mensaje en respuesta al usuario.
                messageBean = new Message();
                //Información del movimiento cancelado.
                movimiento = (Movimiento) session.getAttribute("mov_cancelado");
                userApp = (Usuario) session.getAttribute("userApp");
                //Nombre del usuario de sesión.
                usuario = userApp.getUsuario();

                if (movimiento != null) {
                    if (movimiento.getIsEmpty()) {
                        messageBean.setDesc("Especifique correctamente el movimiento a cancelar");
                    } else {
                        // Se obtiene el motivo de cancelación.
                        if (request.getParameter("motivo_cancelacion") != null && !request.getParameter("motivo_cancelacion").equals("")) {
                            motivo_cancelacion = request.getParameter("motivo_cancelacion").toUpperCase();
                        }
                        if (motivo_cancelacion != null) {
                            if (!motivo_cancelacion.equals("--------------- Seleccione ---------------")) {
                                movimiento.setMotivo_cancelacion(motivo_cancelacion);
                                cancela_movimiento = modeloAutoriza.almacenaMovimientoCancelado(movimiento, usuario, "A");
                                messageBean.setDesc(cancela_movimiento);
                                nombresObjetos = "movimiento;mov_cancelado;opcion_c;opcionC;opcionH;opinionM;motivo_c".split(";");
                                if (cancela_movimiento.equals("Movimiento cancelado correctamente")) {
                                    fecha_captura = ModeloLiquidation.getFechaCaptura(movimiento.getClave_contrato(), movimiento.getFecha_liquidacion(), movimiento.getNombre_archivo(), "P");
                                }
                            } else {
                                messageBean.setDesc(" Seleccione el motivo de la cancelación ");
                            }
                        } else {
                            messageBean.setDesc(" Error obteniendo el motivo de la cancelación ");
                        }
                    }
                } else {
                    messageBean.setDesc(" Error oteniendo datos de la sesión ");
                }
                synchronized (session) {
                    session.setAttribute("messageBean", messageBean);
                    session.setAttribute("total_movimientos", ModeloLiquidation.total_movimientos);
                    if (nombresObjetos != null) {
                        this.remueveAtributos(nombresObjetos, session);
                    }
                }
                urlResponse = "menuSettlements2.htm";
                break;

            //Habilita Movimiento cancelado
            case 30:
                //Clave de fideicomiso seleccionado.
                clave_contrato = (String) session.getAttribute("clave_contratoC");
                //Fecha de liquidación seleccionada.
                fecha_liquidacion = (String) session.getAttribute("fecha_c");
                //Nombre del LayOut seleccionado.
                fileName = (String) session.getAttribute("lote_c");
                //Mensaje final de usuario.
                messageBean = new Message();
                //Movimiento que será habilitado.
                movimiento = new Movimiento();

                try {
                    movimiento.setClave_contrato(clave_contrato);
                    movimiento.setFecha_liquidacion(fecha_liquidacion);
                    movimiento.setNombre_archivo(fileName);
                    // Se obtiene la cuenta de depósito especificada por el usuario.
                    if (request.getParameter("cuenta_deposito") != null) {
                        movimiento.setCuenta_deposito(request.getParameter("cuenta_deposito").toString().trim());
                    }
                    // Se obtiene el nombre del fideicomisario.
                    if (request.getParameter("nombre_empleado") != null) {
                        movimiento.setNombre_empleado(request.getParameter("nombre_empleado").toString().trim());
                    }
                    // Se obtiene el apellido paterno del fideicomisario.
                    if (request.getParameter("apellidoP_empleado") != null) {
                        movimiento.setApellidoP_empleado(request.getParameter("apellidoP_empleado").toString().trim());
                    }
                    // Se obtiene el apellido materno del fideicomisario.
                    if (request.getParameter("apellidoM_empleado") != null) {
                        movimiento.setApellidoM_empleado(request.getParameter("apellidoM_empleado").toString().trim());
                    }
                    // Se obtiene el identificador del fideicomisario.
                    if (request.getParameter("numero_empleado") != null) {
                        movimiento.setNumero_empleado(request.getParameter("numero_empleado").toString().trim());
                    }
                    // Se obtiene el puesto del fideicomisario.
                    if (request.getParameter("puesto_empleado") != null) {
                        movimiento.setPuesto_empleado(request.getParameter("puesto_empleado").toString().trim());
                    }
                    // Se obtiene el departamento al que pertenece el fideicomisario.
                    if (request.getParameter("departamento_empleado") != null) {
                        movimiento.setDepartamento_empleado(request.getParameter("departamento_empleado").toString().trim());
                    }
                    // Se obtiene la fecha de ingreso del fideicomisario.
                    if (request.getParameter("fecha_ingreso") != null) {
                        movimiento.setFecha_ingreso(request.getParameter("fecha_ingreso").toString().trim());
                    }
                    // Se obtiene el tipo de movimiento.
                    if (request.getParameter("tipo_movimiento") != null) {
                        movimiento.setTipo_movimiento(request.getParameter("tipo_movimiento").toString().trim());
                    }
                    //Se obtiene la clave del banco.
                    if (request.getParameter("clave_banco") != null) {
                        movimiento.setClave_banco(request.getParameter("clave_banco").toString().trim());
                    }
                    //Se obtiene el importe de liquidación.
                    if (request.getParameter("importe_liquidacion") != null) {
                        movimiento.setImporte_liquidacion(request.getParameter("importe_liquidacion").toString().trim());
                    }
                    //se obtiene el tipo de moneda del importe de liquidación.
                    if (request.getParameter("clave_moneda") != null) {
                        movimiento.setClave_moneda(request.getParameter("clave_moneda").toString().trim());
                    }
                    // Se obtiene la persona a la que se le hará envio del cheque.
                    if (request.getParameter("nombre_receptor_cheque") != null) {
                        movimiento.setNombre_receptor_cheque(request.getParameter("nombre_receptor_cheque").toString().trim());

                    }
                    // Se obtiene el domicilio de la persona a la que se le hará el envio de cheques.
                    if (request.getParameter("domicilio_destino_cheque") != null) {
                        movimiento.setDomicilio_destino_cheque(request.getParameter("domicilio_destino_cheque").toString().trim());
                    }
                    //Se obtiene el teléfono del envio del cheque.
                    if (request.getParameter("tel_destino_cheque") != null) {
                        movimiento.setTel_destino_cheque(request.getParameter("tel_destino_cheque").toString().trim());
                    }
                    //Se obtiene el correo del envio del cheque.
                    if (request.getParameter("correo_destino_cheque") != null) {
                        movimiento.setCorreo_destino_cheque(request.getParameter("correo_destino_cheque").toString().trim());
                    }
                    //Se obtiene el nombre del banco extranjero.
                    if (request.getParameter("nombre_banco_extranjero") != null) {
                        movimiento.setNombre_banco_extranjero(request.getParameter("nombre_banco_extranjero").toString().trim());
                    }
                    //Se obtiene el domicilio del banco extranjero.
                    if (request.getParameter("domicilio_banco_extranjero") != null) {
                        movimiento.setDomicilio_banco_extranjero(request.getParameter("domicilio_banco_extranjero").toString().trim());
                    }
                    //Se obtiene el país del banco extranjero.
                    if (request.getParameter("pais_banco_extranjero") != null) {
                        movimiento.setPais_banco_extranjero(request.getParameter("pais_banco_extranjero").toString().trim());
                    }
                    //Se obtiene el aba_bic del movimiento.
                    if (request.getParameter("ABA_BIC") != null) {
                        movimiento.setABA_BIC(request.getParameter("ABA_BIC").toString().trim());
                    }
                    //Se obtiene el nombre del fideicomisario en el extranjero.
                    if (request.getParameter("nombre_empleado_banco_extranjero") != null) {
                        movimiento.setNombre_empleado_banco_extranjero(request.getParameter("nombre_empleado_banco_extranjero").toString().trim());
                    }
                    //Se obtiene ela dirección del fideicomisario en el extranjero.
                    if (request.getParameter("dom_empleado_banco_extranjero") != null) {
                        movimiento.setDom_empleado_banco_extranjero(request.getParameter("dom_empleado_banco_extranjero").toString().trim());
                    }

                    //Se obtiene el país del fideicomisario en el extranjero.
                    if (request.getParameter("pais_empleado_banco_extranjero") != null) {
                        movimiento.setPais_empleado_banco_extranjero(request.getParameter("pais_empleado_banco_extranjero").toString().trim());
                    }
                    //Se obtiene el teléfono del fideicomisario en el extranjero.
                    if (request.getParameter("tel_empleado_banco_extranjero") != null) {
                        movimiento.setTel_empleado_banco_extranjero(request.getParameter("tel_empleado_banco_extranjero").toString().trim());
                    }
                    //Se obtiene el teléfono del fideicomisario en el extranjero.
                    if (request.getParameter("m_cancelacion") != null) {
                        movimiento.setMotivo_cancelacion(request.getParameter("m_cancelacion").toString().trim());
                    }
                    movimiento.setIsEmpty(false);

                    mov_cancelado = ModeloLiquidation.getCancelacionParcial(movimiento, "A");
                    if (mov_cancelado != null) {
                        motivo_cancelacion = mov_cancelado.getNombre_fideicomisario();
                        messageBean.setDesc(motivo_cancelacion);
                    } else {
                        messageBean.setDesc("Error obteniendo información del movimiento que será habilitado");
                        mov_cancelado = movimiento;
                    }
                } catch (Exception e) {
                    messageBean.setDesc(" Error al realizar la transacción ");
                }
                //Actualizamos las variables se sesion.
                synchronized (session) {
                    session.setAttribute("messageBean", messageBean);

                    session.setAttribute("mov_cancelado", mov_cancelado);
                }
                urlResponse = "menuSettlements.htm";
                break;

            case 31:
                //Mensaje en respuesta al usuario.
                messageBean = new Message();
                //Información del movimiento cancelado
                movimiento = (Movimiento) session.getAttribute("mov_cancelado");
                // datos del usuario que ingreso al sistema.
                userApp = (Usuario) session.getAttribute("userApp");
                //Nombre del usuario que realiza la cancelación.
                usuario = userApp.getUsuario();

                if (movimiento != null) {
                    if (movimiento.getIsEmpty()) {
                        messageBean.setDesc("Especifique correctamente el movimiento que desea habilitar");
                    } else {
                        cancelaM = modeloAutoriza.habilitaMovimientoCancelado(movimiento, "A");
                        if (cancelaM) {
                            fecha_captura = ModeloLiquidation.getFechaCaptura(movimiento.getClave_contrato(), movimiento.getFecha_liquidacion(), movimiento.getNombre_archivo(), "P");
                            messageBean.setDesc(" Movimiento habilitado correctamente ");
                            //Variables a remover de la sesion.
                            nombresObjetos = "movimiento;mov_cancelado;opcion_c;opcionC;opcionH;opinionM;motivo_c".split(";");
                        } else {
                            messageBean.setDesc(" Error habilitando movimiento cancelado ");
                        }
                    }
                } else {
                    messageBean.setDesc(" Error obteniendo información del movimiento cancelado ");
                }
                synchronized (session) {
                    session.setAttribute("messageBean", messageBean);
                    if (nombresObjetos != null) {
                        this.remueveAtributos(nombresObjetos, session);
                        session.setAttribute("total_movimientos", ModeloLiquidation.total_movimientos);
                    }
                }
                urlResponse = "menuSettlements2.htm";
                break;

            case 32:
                nombresObjetos = "clave_contratoC;cliente_c;fechas_liquidacionC;fecha_c;lotes_c;lote_c;total_movimientos;fecha_capturaC;opcion_c;opciones_c;opcionC;opcionH;opcionM;motivo_c;cliMovConfirma;allMovimientos;modAutoriza;cancela_movs;mov_cancelado".split(";");
                //Se remueven los variables de la sesion.
                synchronized (session) {
                    this.remueveAtributos(nombresObjetos, session);
                }
                urlResponse = "menuSettlements.htm";
                break;

            case 33:

                nombresObjetos = "lote_c;total_movimientos;fecha_capturaC".split(";");
                clave_contrato = (String) session.getAttribute("clave_contratoC");
                fecha_liquidacion = (String) session.getAttribute("fecha_c");
                //Se remueven los variables de la sesion.
                synchronized (session) {
                    this.remueveAtributos(nombresObjetos, session);
                }
                nombresObjetos = null;

                if (request.getParameter("nombre_archivo") != null && !request.getParameter("nombre_archivo").equals("")) {
                    fileName = request.getParameter("nombre_archivo");
                }

                if (clave_contrato != null && fecha_liquidacion != null && fileName != null) {
                    if (!fileName.equals("-Seleccione-")) {
                        if (ModelUpdate.verificaActualizacionLote(clave_contrato, fecha_liquidacion, fileName, "P")) {
                            fecha_captura = ModeloLiquidation.getFechaCaptura(clave_contrato, fecha_liquidacion, fileName, "P");
                        } else {
                            messageBean = new Message();
                            messageBean.setDesc("Favor de actualizar los movimientos a bancos extranjeros de este lote");
                            nombresObjetos = "clave_contratoC;cliente_c;fechas_liquidacionC;fecha_c;lotes_c;lote_c".split(";");
                        }
                    }
                } else {
                    messageBean = new Message();
                    messageBean.setDesc("Error obteniendo datos de la sesion");
                }
                synchronized (session) {
                    session.setAttribute("messageBean", messageBean);
                    session.setAttribute("lote_c", fileName);
                    if (fecha_captura != null) {
                        session.setAttribute("fecha_capturaC", fecha_captura);
                        session.setAttribute("total_movimientos", ModeloLiquidation.total_movimientos);
                    }
                    if (nombresObjetos != null) {
                        this.remueveAtributos(nombresObjetos, session);
                    }
                }
                urlResponse = "menuSettlements.htm";
                break;

            //Inicio de modulo para insertar Aportaciones y deducciones
            case 34:

                /**
                 * Inicio:Se obtienen las claves de fideicomiso con
                 * transacciones pendientes por autorizar*
                 */
                temp = "clave_contratoC;saldos;nombre_cliente;domicilio_cliente;saldo_cliente;fecha_ini;fecha_fin;reporte;reporteSize;liquidaciones;liquidacionesSize; clave_contratoC;nombre_cliente;domicilio_cliente;saldo_cliente;fecha_ini;fecha_fin;reporte;reporteSize;liquidaciones;liquidacionesSize;allMovimientos;consultar_movimientos;";
                temp += "aportacion;selApo;restitucion;selRes;fecha_operacion;hora_operacion;min_operacion;correo_c;tipo_honorario_c;honorario_sin_iva_c;saldo_actual;aportacion;restitucion;cliMovConfirma;allMovimientos;confirmacionLote;messageBean;Pending;modActualiza;modAutoriza;movPending;confirmacionLote;";
                temp += "aportaciones_restituciones;cancelacionParcial;cancelacionTotal;cliMovConfirma;allMovimientos;clave_contratoC;fechas_liquidacionC;fecha_c;lotes_c;lote_c;total_movimientos;fecha_capturaC;movs_cancelados";
                //variables correspondientes al modulo de consulta Ejecutiva
                temp += "fecha_fin;fecha_fin_2;fecha_ini;fecha_ini_2;allMovimientos;estados_de_cuenta;cuentasOrigen;consulta_ejecutiva;"
                        + "fecha_ini_;fecha_fin_;fecha_ini;fecha_fin;movimientosPeriodo;movimientosPeriodoSize;"
                        + "clave_contratoC;cuenta_origen;fecha_fin_2;fecha_ini_2;vector_consulta_total;vector_consulta;vector_consultaSize";

                nombresObjetos = temp.split(";");
                //Se remueven los variables de la sesion.
                synchronized (session) {
                    this.remueveAtributos(nombresObjetos, session);
                }
                aportaciones_restituciones = "aportaciones_restituciones";
                //Se obtienen los clientes que tienen movimientos pendientes por operar.
                allMovimientos = AuthorizationModel.getAllClaveFideicomitentes();
                //Se verifica si ocurrio algún error.
                if (allMovimientos == null) {
                    messageBean = new Message();
                    messageBean.setDesc("Error obteniendo claves de fideicomiso");
                }
                synchronized (session) {
                    session.setAttribute("allMovimientos", allMovimientos);
                    session.setAttribute("aportaciones_restituciones", aportaciones_restituciones);
                    session.setAttribute("messageBean", messageBean);
                }
                urlResponse = "menuSettlements.htm";
                break;
            /**
             * Inicio:Aportaciones y restituciones*
             */
            /**
             * Inicio:Se obtiene el nombre del fideicomitente *
             */
            case 35:
                //Se especifican las variables de sesion a remover.
                nombresObjetos = "aportacion;selApo;restitucion;selRes;fecha_operacion;hora_operacion;min_operacion;cliMovConfirma;confirmacionLote;messageBean;Pending;modActualiza;modAutoriza;lote_c;total_movimientos;fecha_capturaC;clave_contratoC;fechas_liquidacionC;fecha_c;lotes_c;lote_c;total_movimientos;fecha_capturaC;movs_cancelados;nombre_empleado;apellidoP_empleado;apellidoM_empleado;cuenta_deposito;tipo_movimiento;clave_banco;importe_liquidacion;tipo_moneda;opcion_c;opciones_c;opcionC;opcionH".split(";");
                //Se remueven los variables de la sesion.
                synchronized (session) {
                    this.remueveAtributos(nombresObjetos, session);
                }
                // Se obtiene el nombre del cliente a procesar
                if (request.getParameter("cliente") != null && !request.getParameter("cliente").equals("")) {
                    clave_contrato = request.getParameter("cliente");
                }
                if (clave_contrato != null) {
                    if (!clave_contrato.equals("-----Seleccione-----")) {
                        String info_cliente = AuthorizationModel.getSaldo_y_Datos(clave_contrato);
                        String arrayInfo[] = null;
                        arrayInfo = info_cliente.split("%");
                        if (arrayInfo.length == 5) {
                            synchronized (session) {
                                //cadena eliminar de session = correo_c;tipo_honorario_c;honorario_sin_iva_c;saldo_actual;
                                //Clave de contrato
                                session.setAttribute("clave_contratoC", clave_contrato);
                                //Nombre del Contrato
                                session.setAttribute("cliente_c", arrayInfo[0]);
                                //Correo del Contrato
                                session.setAttribute("correo_c", arrayInfo[1]);
                                //Correo del Contrato
                                session.setAttribute("tipo_honorario_c", arrayInfo[2]);
                                //Correo del Contrato
                                session.setAttribute("honorario_sin_iva_c", arrayInfo[3]);
                                //Saldo actual registrado en contrato
                                double saldo = Double.parseDouble(arrayInfo[4]);
                                session.setAttribute("saldo_actual", saldo);
                            }
                        } else {
                            messageBean = new Message();
                            messageBean.setDesc(" Error obteniendo el saldo actual del fideicomiso ");
                        }
                    }
                } else {
                    messageBean = new Message();
                    messageBean.setDesc(" Error obteniendo clave de fideicomiso ");
                }
                synchronized (session) {
                    session.setAttribute("messageBean", messageBean);
                }
                urlResponse = "menuSettlements.htm";
                break;
            /**
             * Fin:Se obtiene el nombre del fideicomitente y las fechas de
             * liquidación pendientes por autorizar*
             */
            /**
             * Inicio:Se valida la fecha y hora de operacion *
             */
            case 36:
                String tipo_operacion = "";
//                String fecha_operacion = "";
//                String hora_operacion = "";
//                String min_operacion = "";
//                String error = "false";

                //Se especifican las variables de sesion a remover.
                nombresObjetos = "importe_restitucion;nuevo_saldo_restitucion;importe_aportacion;honorarios_fiduciarios;iva_honorarios;aportacion_neta;nuevo_saldo_aportacion;messageBean;selApo;selRes;fecha_operacion;hora_operacion;min_operacion;aportacion;restitucion;cliMovConfirma;confirmacionLote;messageBean;Pending;modActualiza;modAutoriza;lote_c;total_movimientos;fecha_capturaC;fechas_liquidacionC;fecha_c;lotes_c;lote_c;total_movimientos;fecha_capturaC;movs_cancelados;nombre_empleado;apellidoP_empleado;apellidoM_empleado;cuenta_deposito;tipo_movimiento;clave_banco;importe_liquidacion;tipo_moneda;opcion_c;opciones_c;opcionC;opcionH".split(";");
                //Se remueven los variables de la sesion.
                synchronized (session) {
                    this.remueveAtributos(nombresObjetos, session);
                }
                if (request.getParameter("operacion") != null && !request.getParameter("operacion").equals("")) {
                    tipo_operacion = request.getParameter("operacion");
                }
                /*
                 * Esto es temporal, ya que la fecha asignada será la actual y no será modificable
                 
                 if (request.getParameter("fecha_operacion") != null && !request.getParameter("fecha_operacion").equals("")) {
                 fecha_operacion = request.getParameter("fecha_operacion");
                 }
                 if (request.getParameter("hora_operacion") != null && !request.getParameter("hora_operacion").equals("")) {
                 hora_operacion = request.getParameter("hora_operacion");
                 }
                 if (request.getParameter("min_operacion") != null && !request.getParameter("min_operacion").equals("")) {
                 min_operacion = request.getParameter("min_operacion");
                 }
                 // Se obtiene el nombre del cliente a procesar

                 error = AuthorizationModel.compruebaFechaOperacion(fecha_operacion, hora_operacion, min_operacion);
                
                 */

                //                if(error.isEmpty()){
                if (tipo_operacion != null) {
                    if (!tipo_operacion.equals("seleccione")) {
                        if (tipo_operacion.equals("aportacion")) {
                            session.setAttribute("aportacion", "aportación");
                            session.setAttribute("selApo", "selected");
                        }//Atributos de session incluidos en este case:    aportacion;selApo;restitucion;selRes;fecha_operacion;hora_operacion;min_operacion;
                        else if (tipo_operacion.equals("restitucion")) {
                            session.setAttribute("restitucion", "restitución");
                            session.setAttribute("selRes", "selected");
                        }
                        //                        session.setAttribute("fecha_operacion",fecha_operacion);
                        //                        session.setAttribute("hora_operacion",hora_operacion);
                        //                        session.setAttribute("min_operacion",min_operacion);
                    } else {
                        messageBean = new Message();
                        messageBean.setDesc("Favor de especificar el tipo de operación.");
                        synchronized (session) {
                            session.setAttribute("messageBean", messageBean);
                        }
                    }
                }
                //                } else {
                //                    messageBean = new Message();
                //                    messageBean.setDesc(error);
                //                    synchronized (session) {
                //                    session.setAttribute("messageBean", messageBean);
                //                }
                //                }
                urlResponse = "menuSettlements.htm";
                break;
            /**
             * Fin:Se obtiene el nombre del fideicomitente y las fechas de
             * liquidación pendientes por autorizar*
             */
            /**
             * Case para calcular la aportacion ingresada por el usuario
             */
            case 37:

                Double importe = 0.0;
                String importe_format = "";
                double saldo_actual = 0.0;
                //Se especifican las variables de sesion a remover.
                nombresObjetos = "importe_aportacion;honorarios_fiduciarios;iva_honorarios;aportacion_neta;nuevo_saldo_aportacion;messageBean;opcion_c;opciones_c;opcionC;opcionH;messageBean".split(";");
                //Se remueven los variables de la sesion.
                synchronized (session) {
                    this.remueveAtributos(nombresObjetos, session);
                }
                try {
                    if (request.getParameter("importe_aportacion") != null && !request.getParameter("importe_aportacion").equals("")) {
                        importe = Double.parseDouble(request.getParameter("importe_aportacion"));
                    }
                    clave_contrato = (String) session.getAttribute("clave_contratoC");
                    saldo_actual = Double.parseDouble(session.getAttribute("saldo_actual").toString());
                    saldo_actual = new BigDecimal(saldo_actual).setScale(2, RoundingMode.HALF_UP).doubleValue();
                    DecimalFormat formato = new DecimalFormat("0.00");
                    importe_format = formato.format(importe);
                    String honSinIva = ModelUpdate.getTipoHonorario(clave_contrato);
                    double dispersion = AuthorizationModel.calculaDispercion(importe_format, honSinIva, "0.16");
                    dispersion = new BigDecimal(dispersion).setScale(2, RoundingMode.HALF_UP).doubleValue();
                    double honorarios = AuthorizationModel.calculaHonorariosDispercion(importe_format, dispersion, honSinIva, "0.16");
                    honorarios = new BigDecimal(honorarios).setScale(2, RoundingMode.HALF_UP).doubleValue();
                    BigDecimal ivaBig = new BigDecimal(honorarios * 0.16D);
                    ivaBig = ivaBig.setScale(2, RoundingMode.HALF_UP);
                    double iva_honorarios = ivaBig.doubleValue();
                    double dispersionSumatoria = Double.parseDouble(importe_format) - honorarios - iva_honorarios;
                    if (dispersion != dispersionSumatoria) {
                        double diferencia = dispersionSumatoria - dispersion;
//                            System.out.println("Dispersion= " + dispersion);
//                            System.out.println("DispersionSumatoria= " + dispersionSumatoria);
                        if (Math.abs(diferencia) < 0.09D) {
//                            System.out.println("Honorarios= " + honorarios);
//                            System.out.println("IVA= " + iva_honorarios);
                            if (diferencia < 0) {//Se decrementa al honorario, y se aumenta aportacion neta
                                honorarios += new BigDecimal(diferencia).setScale(2, RoundingMode.HALF_UP).doubleValue();
                            }
                            if (diferencia > 0) {//Se incrementa el iva y se decrementa aportacion neta
                                iva_honorarios += new BigDecimal(diferencia).setScale(2, RoundingMode.HALF_UP).doubleValue();;
                            }
                            dispersionSumatoria = Double.parseDouble(importe_format) - honorarios - iva_honorarios;
                            dispersionSumatoria = new BigDecimal(dispersionSumatoria).setScale(2, RoundingMode.HALF_UP).doubleValue();
//                            System.out.println("-----------------");
//                            System.out.println("HonorariosNuevo= " + honorarios);
//                            System.out.println("IVANuevo= " + iva_honorarios);   
//                            System.out.println("DispersionNuevo= " + dispersion);
//                            System.out.println("DispersionSumatoriaNuevo= " + dispersionSumatoria);                            
                        } else {
                            messageBean = new Message();
                            messageBean.setDesc("Error al calcular la aportación.");
                            synchronized (session) {
                                session.setAttribute("messageBean", messageBean);
                                urlResponse = "menuSettlements.htm";
                                break;
                            }
                        }
                    }
                    if (dispersion != dispersionSumatoria) {
                        messageBean = new Message();
                        messageBean.setDesc("Error al calcular la aportación.");
                        synchronized (session) {
                            session.setAttribute("messageBean", messageBean);
                            urlResponse = "menuSettlements.htm";
                            break;
                        }
                    }
//                        //Especificamos la Suficiencia Patronal.
//                        String sp_c = AuthorizationModel.getSuficienciaPatronal(importe_format, honSinIva, "16");
//                        //Especificamos el monto de los honorarios fiduciarios.
//                        String hf_c = AuthorizationModel.getHonorariosFiduciarios(importe_format, sp_c, honSinIva);
//                        //Especificamos el monto del IVA correspondiente a los honorarios fiduciarios.
//                        String xIva = ModelUpdate.getIVA(hf_c);        
                    //variables a remover de sesion en este case:   importe_aportacion;honorarios_fiduciarios;iva_honorarios;aportacion_neta;nuevo_saldo_aportacion;messageBean;
                    session.setAttribute("importe_aportacion", Double.parseDouble(importe_format));
                    session.setAttribute("honorarios_fiduciarios", honorarios);
                    session.setAttribute("iva_honorarios", iva_honorarios);
                    session.setAttribute("aportacion_neta", dispersion);
                    session.setAttribute("nuevo_saldo_aportacion", new BigDecimal(saldo_actual + dispersion).setScale(2, RoundingMode.HALF_UP).doubleValue());

                } catch (Exception e) {
                    messageBean = new Message();
                    messageBean.setDesc("Formato de importe de restitución incorrecto, favor de verificar");
                    synchronized (session) {
                        session.setAttribute("messageBean", messageBean);
                    }
                    urlResponse = "menuSettlements.htm";
                    break;
                }

                urlResponse = "menuSettlements.htm";
                break;

            /**
             * Case para calcular la restitucion ingresada por el usuario
             */
            case 38:

                importe = 0.0;
                importe_format = "";
//                String tmp="";
                saldo_actual = 0.0;
                //Se especifican las variables de sesion a remover.
                nombresObjetos = "importe_restitucion;nuevo_saldo_restitucion;opcion_c;opciones_c;opcionC;opcionH;messageBean".split(";");
                //Se remueven los variables de la sesion.
                synchronized (session) {
                    this.remueveAtributos(nombresObjetos, session);
                }
                try {
                    if (request.getParameter("importe_restitucion") != null && !request.getParameter("importe_restitucion").equals("")) {
                        importe = Double.parseDouble(request.getParameter("importe_restitucion"));
                    }
                    saldo_actual = Double.parseDouble(session.getAttribute("saldo_actual").toString());
                    session.setAttribute("importe_restitucion", importe);
                    session.setAttribute("nuevo_saldo_restitucion", (importe + saldo_actual));
                    //variables de session a remover en case:     importe_restitucion;nuevo_saldo_restitucion;messageBean
                } catch (Exception e) {
                    System.out.println("Exception calculaRestitucion: " + e.getMessage());
                    messageBean = new Message();
                    messageBean.setDesc("Formato de importe de restitución incorrecto, favor de verificar");
                    synchronized (session) {
                        session.setAttribute("messageBean", messageBean);
                    }
                    urlResponse = "menuSettlements.htm";
                    break;
                }

                urlResponse = "menuSettlements.htm";
                break;

            /**
             * Case que almacena en la BD los datos ingresados por el usuario
             */
            case 39: {
                tipo_operacion = null;
                double saldoActual = -1D;
                //                        fecha_operacion = "";
                //                        hora_operacion = "";
                //                        min_operacion = "";
                messageBean = new Message();
                tipo_operacion = request.getParameter("operacion");
                clave_contrato = (String) session.getAttribute("clave_contratoC");
                saldo_actual = Double.parseDouble(session.getAttribute("saldo_actual").toString());
                userApp = (Usuario) session.getAttribute("userApp");
                //                        DecimalFormat formatoSTD = new DecimalFormat("0.00");
                /*
                 * Esto es un proceso temporal, ya que posteriormente no se podramodificar la hora de ingreso de estas operaciones
                 */
                //                                if (session.getAttribute("fecha_operacion") != null && !session.getAttribute("fecha_operacion").equals("")) {
                //                                    fecha_operacion = (String)session.getAttribute("fecha_operacion");
                //                                }
                //                                if (session.getAttribute("hora_operacion") != null && !session.getAttribute("hora_operacion").equals("")) {
                //                                    hora_operacion = (String)session.getAttribute("hora_operacion");
                //                                }
                //                                if (session.getAttribute("min_operacion") != null && !session.getAttribute("min_operacion").equals("")) {
                //                                    min_operacion = (String)session.getAttribute("min_operacion");
                //                                }

                // ------   APORTACIÓN ----------
                //Se insertan los datos:    
                // APORTACION-> fecha="Datehoy"; fideicomiso=clave_contratoC; concepto="APORTACIÓN A PATRIMONIO";
                //              abono=importe_aportacion; saldo=(saldo_actual+importe_aportacion); usuario_genera=userApp.getUsuario()
                // HONORARIOS-> fecha="Datehoy+(10 milisegundo)"; fideicomiso=clave_contratoC; concepto="HONORARIOS FIDUCIARIOS";
                //              cargo=honorarios_fiduciarios; saldo=(saldo_actual-honorarios_fiduciarios); usuario_genera=userApp.getUsuario()
                // IVA HONOR.-> fecha="Datehoy+(20 milisegundo)"; fideicomiso=clave_contratoC; concepto=" I.V.A. HONORARIOS FIDUCIARIOS";
                //              cargo=iva_honorarios; saldo=(saldo_actual-iva_honorarios); usuario_genera=userApp.getUsuario()
                if (userApp != null) {
                    if (tipo_operacion.equals("aportacion")) {
//                            DateFormat dateF2 = DateFormat.getDateInstance(DateFormat.SHORT);
//                            SimpleDateFormat formatoCorto = new SimpleDateFormat("yyyy-MM-dd");
//                            try {
//                            d = dateF2.parse(fecha_operacion);
//                            }
//                            catch(ParseException e) {
//                            System.out.println("No es posible parsear: " + fecha_operacion);
//                            }
//                            fecha_operacion = formatoCorto.format(d); 
//                            fecha_operacion += " " + hora_operacion +":"+ min_operacion +":00.000";
                        if (session.getAttribute("importe_aportacion") != null && session.getAttribute("honorarios_fiduciarios") != null && session.getAttribute("iva_honorarios") != null) {
                            double importe_aportacion = Double.parseDouble(session.getAttribute("importe_aportacion").toString());
                            double honorarios_fiduciarios = Double.parseDouble(session.getAttribute("honorarios_fiduciarios").toString());
                            double iva_honorarios = Double.parseDouble(session.getAttribute("iva_honorarios").toString());
////////
                            String info_cliente = AuthorizationModel.getSaldo_y_Datos(clave_contrato);
                            String arrayInfo[] = null;
                            arrayInfo = info_cliente.split("%");
                            if (arrayInfo.length == 5) {
                                saldoActual = Double.parseDouble(arrayInfo[4]);
                            } else {
                                messageBean = new Message();
                                messageBean.setDesc(" Error obteniendo el saldo actual del fideicomiso ");
                            }
                            //Se inserta la operacion a la BD  
                            if (saldoActual >= 0 && saldo_actual == saldoActual) {
                                boolean guardado = modeloAutoriza.insertaAportacionSaldo(clave_contrato, saldo_actual, importe_aportacion, honorarios_fiduciarios, iva_honorarios, userApp.getUsuario());
                                if (guardado) {
//                        java.util.Date fecha_actual = new  java.util.Date();
                                    //se especifica el formato de la fecha y hora para ser almacenado en la BD
//                        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//                        Calendar calendario= Calendar.getInstance();                        
//                        calendario.setTime(fecha_actual);
                                    //Se inserta el registro de aportacion 
//                        saldo_actual = saldo_actual + importe_aportacion;
//                        String regApo= formateador.format(calendario.getTime()) + clave_contrato + "APORTACIÓN A PATRIMONIO"
//                                + formatoSTD.format(importe_aportacion) + formatoSTD.format(saldo_actual) + userApp.getUsuario()  ;
//                        System.out.println("regApo" + regApo);
//                        calendario.add(Calendar.MILLISECOND, 20);
                                    //Se inserta el registro de honorarios 
//                        saldo_actual = saldo_actual - honorarios_fiduciarios;
//                        String regHon= formateador.format(calendario.getTime()) + clave_contrato + "HONORARIOS FIDUCIARIOS"
//                                + formatoSTD.format(honorarios_fiduciarios) + formatoSTD.format(saldo_actual) + userApp.getUsuario()  ;
//                        System.out.println("regHon" + regHon);
//                        calendario.add(Calendar.MILLISECOND, 20);                        
                                    //Se inserta el registro de IVA honorarios 
//                        saldo_actual = saldo_actual  - iva_honorarios;
//                        String regIVAHon= formateador.format(calendario.getTime()) + clave_contrato + "I.V.A. HONORARIOS FIDUCIARIOS"
//                                + formatoSTD.format(iva_honorarios) + formatoSTD.format(saldo_actual) + userApp.getUsuario()  ;
//                        System.out.println("regIVAHon" + regIVAHon);
//                        calendario.add(Calendar.MILLISECOND, 20); 
                                    messageBean.setDesc("Operación de aportación realizada");

                                    //Se especifican las variables de sesion a remover.
                                    nombresObjetos = "tipo_operacion;importe_restitucion;nuevo_saldo_restitucion;importe_aportacion;honorarios_fiduciarios;iva_honorarios;aportacion_neta;nuevo_saldo_aportacion;aportacion;selApo;restitucion;selRes;fecha_operacion;hora_operacion;min_operacion;correo_c;tipo_honorario_c;honorario_sin_iva_c;saldo_actual;clave_contratoC".split(";");
                                    //Se remueven los variables de la sesion.
                                    synchronized (session) {
                                        this.remueveAtributos(nombresObjetos, session);
                                    }
                                } else {
                                    messageBean.setDesc("Error al guardar en la Base de Datos.");
                                }
                            } else {
                                messageBean = new Message();
                                messageBean.setDesc(" Error: el saldo ha sido modificado por otra operación. ");
                            }
                        } else {
                            messageBean.setDesc("Error al concretar la operación.");
                        }
                    } else if (tipo_operacion.equals("restitucion")) {
                        // ------   RESTITUCIÓN ----------
                        // RESTITUCION-> fecha="Datehoy"; fideicomiso=clave_contratoC; concepto="RESTITUCIÓN DE PATRIMONIO";
                        //              abono=importe_restitucion; saldo=(saldo_actual+importe_restitucion=nuevo_saldo_restitucion); usuario_genera=userApp.getUsuario()                            
                        if (session.getAttribute("importe_restitucion") != null) {
                            double importe_restitucion = Double.parseDouble(session.getAttribute("importe_restitucion").toString());
                            String observaciones = request.getParameter("observaciones");
/////////////
                            String info_cliente = AuthorizationModel.getSaldo_y_Datos(clave_contrato);
                            String arrayInfo[] = null;
                            arrayInfo = info_cliente.split("%");
                            if (arrayInfo.length == 5) {
                                saldoActual = Double.parseDouble(arrayInfo[4]);
                            } else {
                                messageBean = new Message();
                                messageBean.setDesc(" Error obteniendo el saldo actual del fideicomiso ");
                            }
                            //Se inserta la operacion a la BD  
                            if (saldoActual >= 0 && saldo_actual == saldoActual) {
                                boolean guardado = AuthorizationModel.insertaRestitucionSaldo(observaciones, clave_contrato, saldo_actual, importe_restitucion, userApp.getUsuario());
                                if (guardado) {

//                        java.util.Date fecha_actual = new  java.util.Date();
                                    //se especifica el formato de la fecha y hora para ser almacenado en la BD
//                        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//                        Calendar calendario= Calendar.getInstance();                        
//                        calendario.setTime(fecha_actual);
                                    //Se inserta el registro de restitucion 
//                        saldo_actual = saldo_actual + importe_restitucion;
//                        String regRes= formateador.format(calendario.getTime()) + clave_contrato + "RESTITUCIÓN DE PATRIMONIO"
//                                + formatoSTD.format(importe_restitucion) + formatoSTD.format(saldo_actual) + userApp.getUsuario()  ;
//                        System.out.println("regRes" + regRes);                        
                                    //Se especifican las variables de sesion a remover.
                                    nombresObjetos = "tipo_operacion;importe_restitucion;nuevo_saldo_restitucion;importe_aportacion;honorarios_fiduciarios;iva_honorarios;aportacion_neta;nuevo_saldo_aportacion;aportacion;selApo;restitucion;selRes;fecha_operacion;hora_operacion;min_operacion;correo_c;tipo_honorario_c;honorario_sin_iva_c;saldo_actual;clave_contratoC".split(";");
                                    //Se remueven los variables de la sesion.
                                    synchronized (session) {
                                        this.remueveAtributos(nombresObjetos, session);
                                    }
                                    messageBean.setDesc("Operación de restitución realizada");
                                    System.out.println("Se realizo restitucion.");
                                } else {
                                    messageBean.setDesc("Error al guardar en la Base de Datos.");
                                }
                            } else {
                                messageBean = new Message();
                                messageBean.setDesc(" Error: el saldo ha sido modificado por otra operación. ");
                            }
                        } else {
                            messageBean.setDesc("Error al concretar la operación.");
                        }
                    }
                } else {
                    messageBean.setDesc("Tiempo de sesion agotado.");
                    urlResponse = "login.htm";
                    break;
                }
                synchronized (session) {
                    session.setAttribute("messageBean", messageBean);
                }
                urlResponse = "menuSettlements.htm";
                break;
            }

            /**
             * Case que cancela la operacion de restitucion o paortacion y
             * regresa a pantalla principal de generacion de aportaciones y
             * restituciones
             */
            case 40:
                //Se especifican las variables de sesion a remover.
                nombresObjetos = "importe_restitucion;nuevo_saldo_restitucion;importe_aportacion;honorarios_fiduciarios;iva_honorarios;aportacion_neta;nuevo_saldo_aportacion;aportacion;selApo;restitucion;selRes;fecha_operacion;hora_operacion;min_operacion;correo_c;tipo_honorario_c;honorario_sin_iva_c;saldo_actual;clave_contratoC".split(";");
                //Se remueven los variables de la sesion.
                synchronized (session) {
                    this.remueveAtributos(nombresObjetos, session);
                }

                urlResponse = "menuSettlements.htm";
                break;

            case 41: {

                temp = "clave_contratoC;saldos;nombre_cliente;domicilio_cliente;saldo_cliente;fecha_ini;fecha_fin;reporte;reporteSize;liquidaciones;liquidacionesSize;";
                temp += "consultar_movimientos;clave_contratoC;nombre_cliente;domicilio_cliente;saldo_cliente;fecha_ini;fecha_fin;reporte;reporteSize;liquidaciones;liquidacionesSize;";
                temp += "aportacion;selR;selA;selL;restitucion;fecha_operacion;hora_operacion;min_operacion;correo_c;tipo_honorario_c;honorario_sin_iva_c;saldo_actual;aportacion;restitucion;";
                temp += "cliMovConfirma;allMovimientos;confirmacionLote;messageBean;Pending;modActualiza;modAutoriza;movPending;confirmacionLote;aportaciones_restituciones;cancelacionParcial;cancelacionTotal;cliMovConfirma;allMovimientos;clave_contratoC;fechas_liquidacionC;fecha_c;lotes_c;lote_c;total_movimientos;fecha_capturaC;movs_cancelados;";
                //variables correspondientes al modulo de consulta Ejecutiva
                temp += "fecha_fin;fecha_fin_2;fecha_ini;fecha_ini_2;allMovimientos;estados_de_cuenta;cuentasOrigen;consulta_ejecutiva;"
                        + "fecha_ini_;fecha_fin_;fecha_ini;fecha_fin;movimientosPeriodo;movimientosPeriodoSize;"
                        + "clave_contratoC;cuenta_origen;fecha_fin_2;fecha_ini_2;vector_consulta_total;vector_consulta;vector_consultaSize";
                /**
                 * Inicio:Se obtienen las claves de fideicomiso con
                 * transacciones pendientes por autorizar*
                 */
                nombresObjetos = temp.split(";");
                //Se remueven los variables de la sesion.
                synchronized (session) {
                    this.remueveAtributos(nombresObjetos, session);
                }
                //Se obtienen los clientes que tienen movimientos pendientes por operar.
                allMovimientos = AuthorizationModel.getAllClaveFideicomitentes();
                //Se verifica si ocurrio algún error.
                if (allMovimientos == null) {
                    messageBean = new Message();
                    messageBean.setDesc("Error obteniendo claves de fideicomiso");
                }
                synchronized (session) {
                    //variables a remover en este case: clave_contratoC;nombre_cliente;domicilio_cliente;saldo_cliente;fecha_ini;fecha_fin;reporte;reporteSize;liquidaciones;liquidacionesSize;allMovimientos;consultar_movimientos;
                    session.setAttribute("allMovimientos", allMovimientos);
                    session.setAttribute("consultar_movimientos", "consultar_movimientos");
                    session.setAttribute("messageBean", messageBean);
                }
                urlResponse = "menuSettlements.htm";
                break;
            }

            case 42: {
                //Se especifican las variables de sesion a remover.
                //Variables agregadas a session en este case: 
                nombresObjetos = "fecha_ini;fecha_fin;reporte;selR;selA;selL;saldos;reporteSize;liquidaciones;liquidacionesSize;".split(";");
                Vector movimientos = new Vector();
                String datos[] = null;
                //Se remueven los variables de la sesion.
                synchronized (session) {
                    this.remueveAtributos(nombresObjetos, session);
                }
                // Se obtiene el nombre del cliente a procesar
                if (request.getParameter("cliente") != null && !request.getParameter("cliente").equals("")) {
                    clave_contrato = request.getParameter("cliente");
                    session.setAttribute("clave_contratoC", clave_contrato);
                }
                if (session.getAttribute("clave_contratoC") != null && !session.getAttribute("clave_contratoC").toString().equals("")) {
                    clave_contrato = session.getAttribute("clave_contratoC").toString();
                }
                if (clave_contrato != null) {
                    if (!clave_contrato.equals("-----Seleccione-----")) {
                        datos = AuthorizationModel.getDatos_cliente(clave_contrato);
                        if (datos != null && datos.length == 3) {
                            synchronized (session) {
                                session.setAttribute("clave_contratoC", clave_contrato);
                                session.setAttribute("nombre_cliente", datos[0]);
                                session.setAttribute("domicilio_cliente", datos[1]);
//                                session.setAttribute("saldo_cliente", datos[2]);
                                session.setAttribute("saldo_cliente", new DecimalFormat("$ #,##0.00").format(Double.parseDouble(datos[2])));
                            }

//////////Comienza implementacion de nuevo modulo    "onsulta de movimientos fiduciarios"                          
                            String fecha_ini = request.getParameter("fecha_ini_");
                            if (fecha_ini != null && !fecha_ini.equals("")) {
                                String tmpo = ModeloLiquidation.compruebaFechaOperacion(fecha_ini);
                                if (!tmpo.equals("")) {
                                    messageBean = new Message();
                                    messageBean.setDesc(tmpo);
                                    session.setAttribute("messageBean", messageBean);
                                    fecha_ini = ModeloLiquidation.obtenFormatoFecha(fecha_ini, 0);
                                }
                            } else {
                                fecha_ini = ModeloLiquidation.obtenFormatoFecha(fecha_ini, 0);
                            }
                            String fecha_fin = request.getParameter("fecha_fin_");
                            if (fecha_fin != null && !fecha_fin.equals("")) {
                                String tmpo = ModeloLiquidation.compruebaFechaOperacion(fecha_fin);
                                if (!tmpo.equals("")) {
                                    messageBean = new Message();
                                    messageBean.setDesc(tmpo);
                                    session.setAttribute("messageBean", messageBean);
                                    fecha_fin = ModeloLiquidation.obtenFormatoFecha(fecha_fin, 1);
                                }
                            } else {
                                fecha_fin = ModeloLiquidation.obtenFormatoFecha(fecha_fin, 1);
                            }
                            String filtro_tipo = request.getParameter("tipo_operacion");
                            if (filtro_tipo != null && !filtro_tipo.equals("")) {
                                if (filtro_tipo.equals("RESTITUCION")) {
                                    session.setAttribute("selR", "selected");
                                }
                                if (filtro_tipo.equals("APORTACION")) {
                                    session.setAttribute("selA", "selected");
                                }
                                if (filtro_tipo.equals("LIQUIDACION")) {
                                    session.setAttribute("selL", "selected");
                                }
                            }
                            movimientos = ModeloLiquidation.getMovimientos(clave_contrato, fecha_ini, fecha_fin, filtro_tipo);
                            session.setAttribute("fecha_ini", fecha_ini);
                            session.setAttribute("fecha_fin", fecha_fin);
                            if (movimientos.isEmpty()) {
                                session.setAttribute("reporte", null);
                                session.setAttribute("reporteSize", 0);
                            } else {
                                session.setAttribute("reporte", movimientos);
                                session.setAttribute("reporteSize", movimientos.size() - 1);
                            }

////////Inicia el modulo de consulta de liquidaciones
                            movimientos = ModeloLiquidation.getLiduiacionesPendietes(clave_contrato, Double.parseDouble(datos[2]));
//                            System.out.println("Movimientos pendientes:" + movimientos);
                            Vector saldos = null;
//                String saldo_por_pagar = "", suficiencia_total = "";
                            if (movimientos.isEmpty()) {
                                session.setAttribute("saldos", new Vector());
                            } else if (movimientos.size() > 0) {
                                saldos = (Vector) movimientos.get(movimientos.size() - 1);
                                if (saldos != null && saldos.size() > 0) {
                                    session.setAttribute("saldos", saldos);
                                    movimientos.remove(movimientos.size() - 1);
                                }
                                session.setAttribute("liquidaciones", movimientos);
//                    System.out.println("Liquidacionesss:" + movimientos);
                                if (movimientos.size() > 0) {
                                    session.setAttribute("liquidacionesSize", movimientos.size() - 1);
                                } else {
                                    session.setAttribute("liquidaciones", null);
                                    session.setAttribute("liquidacionesSize", 0);
                                }
                            }
//////////Termina implementacion de nuevo modulo                        

                        } else {
                            messageBean = new Message();
                            messageBean.setDesc(" Error obteniendo datos del fideicomiso. ");
                        }
                    }
                } else {
                    messageBean = new Message();
                    messageBean.setDesc(" Error obteniendo clave de fideicomiso ");
                }
                synchronized (session) {
                    session.setAttribute("messageBean", messageBean);
                }
                urlResponse = "menuSettlements.htm";
                break;

            }

            case 43: {

                temp = "consulta_ejecutiva;clave_contratoC;saldos;nombre_cliente;domicilio_cliente;saldo_cliente;fecha_ini;fecha_fin;reporte;reporteSize;liquidaciones;liquidacionesSize;";
                temp += "consultar_movimientos;clave_contratoC;nombre_cliente;domicilio_cliente;saldo_cliente;fecha_ini;fecha_fin;reporte;reporteSize;liquidaciones;liquidacionesSize;";
                temp += "aportacion;selR;selA;selL;restitucion;fecha_operacion;allMovimientos;estados_de_cuenta;hora_operacion;min_operacion;correo_c;tipo_honorario_c;honorario_sin_iva_c;saldo_actual;aportacion;restitucion;";
                temp += "cliMovConfirma;allMovimientos;confirmacionLote;messageBean;Pending;modActualiza;modAutoriza;movPending;confirmacionLote;aportaciones_restituciones;cancelacionParcial;cancelacionTotal;cliMovConfirma;allMovimientos;clave_contratoC;fechas_liquidacionC;fecha_c;lotes_c;lote_c;total_movimientos;fecha_capturaC;movs_cancelados;";
                /**
                 * Inicio:Se obtienen las claves de fideicomiso con
                 * transacciones pendientes por autorizar*
                 */
                nombresObjetos = temp.split(";");
                //Se remueven los variables de la sesion.
                synchronized (session) {
                    this.remueveAtributos(nombresObjetos, session);
                }
                //Se obtienen los clientes que tienen movimientos pendientes por operar.
                allMovimientos = AuthorizationModel.getAllClaveFideicomitentes();
                Vector cuentasOrigen = AuthorizationModel.getCuentaOrigen();
                allMovimientos.set(0, "Todo");
                //Se verifica si ocurrio algún error.
                if (allMovimientos == null) {
                    messageBean = new Message();
                    messageBean.setDesc("Error obteniendo claves de fideicomiso");
                }
                synchronized (session) {
                    //variables a remover en este case: fecha_fin;fecha_fin_2;fecha_ini;fecha_ini_2;allMovimientos;cuentasOrigen;consulta_ejecutiva;
                    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar cal = Calendar.getInstance();
                    session.setAttribute("fecha_fin", formato.format(cal.getTime()));
                    session.setAttribute("fecha_fin_2", formato.format(cal.getTime()));
                    cal.set(Calendar.DAY_OF_MONTH, 1);
                    session.setAttribute("fecha_ini", formato.format(cal.getTime()));
                    session.setAttribute("fecha_ini_2", formato.format(cal.getTime()));
                    session.setAttribute("allMovimientos", allMovimientos);
                    session.setAttribute("cuentasOrigen", cuentasOrigen);
                    session.setAttribute("consulta_ejecutiva", "consulta_ejecutiva");
                    session.setAttribute("messageBean", messageBean);
                }
                urlResponse = "menuSettlements.htm";
                break;
            }

            case 45: {
                //Se especifican las variables de sesion a remover.
                nombresObjetos = "".split(";");
                //Se remueven los variables de la sesion.
                synchronized (session) {
                    this.remueveAtributos(nombresObjetos, session);
                }
                String fecha_ini = request.getParameter("fecha_ini_2");
                String fecha_fin = request.getParameter("fecha_fin_2");
                String cuenta_origen = "";
                clave_contrato = "";
                // Se obtiene el nombre del cliente a procesar
                if (request.getParameter("cliente") != null && !request.getParameter("cliente").equals("")) {
                    clave_contrato = request.getParameter("cliente");
                    System.out.println("clave_contrato:" + clave_contrato);
                }
                if (request.getParameter("operacion") != null && !request.getParameter("operacion").equals("")) {
                    cuenta_origen = request.getParameter("operacion");
                }

                if (clave_contrato != null && cuenta_origen != null) {
//                    if (clave_contrato.equals("Todo")) {
                    Vector movimientos = AuthorizationModel.getConsultaEjecutiva(clave_contrato, cuenta_origen, fecha_ini, fecha_fin);
                    Vector movimientos_detalle = AuthorizationModel.getConsultaEjecutivaDetalle(clave_contrato, cuenta_origen, fecha_ini, fecha_fin);
                    if (movimientos != null && movimientos_detalle != null) {
                        synchronized (session) {
                            //variables asignadas a session en este case:
                            //clave_contratoC;cuenta_origen;fecha_fin_2;fecha_ini_2;vector_consulta_total;vector_consulta;vector_consultaSize
                            //Clave de contrato
                            session.setAttribute("clave_contratoC", clave_contrato);
                            //Nombre del Fideicomitente
                            session.setAttribute("cuenta_origen", cuenta_origen);
                            //Fechas de Liquidación pendientes
                            session.setAttribute("fecha_fin_2", fecha_fin);
                            session.setAttribute("fecha_ini_2", fecha_ini);
                            //vector que contiene el total de las cantidades de losregistros encotnrados
                            session.setAttribute("vector_consulta_total", movimientos.get(movimientos.size() - 1));
//                                System.out.println("Movimientos total= " + movimientos.get(movimientos.size()-1));
                            //vector que contiene los registros encontrados
                            movimientos.remove(movimientos.size() - 1);
                            session.setAttribute("vector_consulta", movimientos);
//                                System.out.println("Movimientos Detalle:" + movimientos_detalle);
                            session.setAttribute("movimientos_detalle", movimientos_detalle);
                            int size = movimientos.size();
                            if (movimientos.size() < 1) {
                                size = 0;
                            }
                            session.setAttribute("vector_consultaSize", size);
                        }
                    } else {
                        messageBean = new Message();
                        messageBean.setDesc(" Error obteniendo fechas de liquidación ");
                    }
//                    }
                } else {
                    messageBean = new Message();
                    messageBean.setDesc(" Error obteniendo clave de fideicomiso ");
                }
                synchronized (session) {
                    session.setAttribute("messageBean", messageBean);
                }
                urlResponse = "menuSettlements.htm";
                break;
            }

            case 46: {
                System.out.println("Ejecutar proceso de unZip() y ordenamiento de EdosCta");
                String respuesta = UnZip.Inicio();
                messageBean = new Message();
                synchronized (session) {
                    messageBean.setDesc(respuesta);
                    session.setAttribute("ordenamiento", respuesta);
                    session.setAttribute("sucesos", UnZip.sucesos);
                    session.setAttribute("messageBean", messageBean);
                }
                urlResponse = "subirArchivo.htm";
                break;
            }

            case 47: {
                //Case para consultar y opción de descarga de estado de cuenta de cliente seleccionado
                temp = "clave_contratoC;saldos;nombre_cliente;domicilio_cliente;saldo_cliente;fecha_ini;fecha_fin;reporte;reporteSize;liquidaciones;liquidacionesSize; clave_contratoC;nombre_cliente;domicilio_cliente;saldo_cliente;fecha_ini;fecha_fin;reporte;reporteSize;liquidaciones;liquidacionesSize;allMovimientos;consultar_movimientos;";
                temp += "aportacion;selApo;restitucion;selRes;fecha_operacion;hora_operacion;min_operacion;correo_c;tipo_honorario_c;honorario_sin_iva_c;saldo_actual;aportacion;restitucion;cliMovConfirma;allMovimientos;confirmacionLote;messageBean;Pending;modActualiza;modAutoriza;movPending;confirmacionLote;";
                temp += "aportaciones_restituciones;cancelacionParcial;cancelacionTotal;cliMovConfirma;allMovimientos;clave_contratoC;fechas_liquidacionC;fecha_c;lotes_c;lote_c;total_movimientos;fecha_capturaC;movs_cancelados";
                //variables correspondientes al modulo de consulta Ejecutiva
                temp += "fecha_fin;fecha_fin_2;fecha_ini;fecha_ini_2;allMovimientos;estados_de_cuenta;cuentasOrigen;consulta_ejecutiva;"
                        + "fecha_ini_;fecha_fin_;fecha_ini;fecha_fin;movimientosPeriodo;movimientosPeriodoSize;"
                        + "clave_contratoC;nombresEdoCta;cliente_c;cuenta_origen;fecha_fin_2;fecha_ini_2;vector_consulta_total;vector_consulta;vector_consultaSize";

                nombresObjetos = temp.split(";");
                //Se remueven los variables de la sesion.
                synchronized (session) {
                    this.remueveAtributos(nombresObjetos, session);
                }
                String estados_de_cuenta = "estados_de_cuenta";
                //Se obtienen las claves de todos los clientes
                allMovimientos = AuthorizationModel.getAllClaveFideicomitentes();
                //Se verifica si ocurrio algún error.
                if (allMovimientos == null) {
                    messageBean = new Message();
                    messageBean.setDesc("Error obteniendo claves de fideicomiso");
                }
                synchronized (session) {
                    session.setAttribute("allMovimientos", allMovimientos);
                    session.setAttribute("habilitado", "");
                    session.setAttribute("estados_de_cuenta", estados_de_cuenta);
                    session.setAttribute("messageBean", messageBean);
                }
                urlResponse = "menuSettlements.htm";
                break;
            }

            case 48: {
                //Se obtiene la información básica del fideicomiso y los estados de cuenta disponibles para este.
                if (request.getParameter("cliente") != null && !request.getParameter("cliente").equals("")) {
                    clave_contrato = request.getParameter("cliente");
                }
                if (clave_contrato != null) {
                    if (!clave_contrato.equals("-----Seleccione-----")) {
                        String info_cliente = AuthorizationModel.getSaldo_y_Datos(clave_contrato);
                        String arrayInfo[] = null;
                        arrayInfo = info_cliente.split("%");
                        Vector nombresEdoCta = AuthorizationModel.getEdoCtaFiles(clave_contrato);
                        if (arrayInfo.length == 5) {
                            synchronized (session) {
                                //cadena eliminar de session = correo_c;tipo_honorario_c;honorario_sin_iva_c;saldo_actual;
                                //Clave de contrato
                                session.setAttribute("clave_contratoC", clave_contrato);
                                //Nombre del Contrato
                                session.setAttribute("cliente_c", arrayInfo[0]);
                                //Se insertan los estados de cuenta disponibles para el fideicomiso seleccionado
                                session.setAttribute("nombresEdoCta", nombresEdoCta);
                                //Se desabilita el check de seleccion de fideicomiso para asegurar la descarga correcta
                                session.setAttribute("habilitado", "disabled");
                            }
                        } else {
                            messageBean = new Message();
                            messageBean.setDesc(" Error obteniendo la información del fideicomiso. ");
                        }
                    }
                } else {
                    messageBean = new Message();
                    messageBean.setDesc(" Error obteniendo clave de fideicomiso ");
                }
                synchronized (session) {
                    session.setAttribute("messageBean", messageBean);
                }
                urlResponse = "menuSettlements.htm";
                break;
            }

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

                break;
            /**
             * Fin de eliminacion de objetos de la sesion actual*
             */

            case 200:
                float salarioMinimo = this.cargaSalarioMinimo();
                PrintWriter salida = response.getWriter();
                salida.println(salarioMinimo);
                salida.close();
                return;

            case 300:
                int res = 0;
                String minSalary = "88.36";

                if (!request.getParameter("salario").equals("")) {
                    minSalary = request.getParameter("salario");
                }
                if (this.guardarSalarioMinimo(minSalary)) {
                    res = 1;
                }
                PrintWriter ajaxResult = response.getWriter();
                ajaxResult.println(res);
                ajaxResult.close();
                return;
        }//Fin del switch

//        System.out.println("Redirigiendo: " + urlResponse);
        // Se redirecciona a la pagina correspondiente
        if (!enviado) {
            response.sendRedirect(urlResponse);
        }

    }

    /**
     * Metodo que permite remover los atributos especificados de la sesion
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
