package com.garante.gestionfideicomisos.Helpers.Mail;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GestionSendMail {

    private final String origen = "liquidaciones@fideicomisogds.mx";
    private final String passwd = "L1qu1da*";
    private final String host = "mail.abugaber.com";
    private String destinatarios;
    private Tipos_cuerpo_HTML tipoCuerpo;
    private HTMLMail mailConfig;
    private String cuerpoCorreo;
    private List<String> archivosAdjuntos;
    private boolean solicitudArchivosAdjuntos = false;
    
    public enum Tipos_cuerpo_HTML {
        GENERA_CONTRASENA, BIENVENIDO, INTENTOCREARUSUARIOPLD, OPERACIONESPLD
    };
    
    public GestionSendMail() {}

    public GestionSendMail(Tipos_cuerpo_HTML tip, String destinatarios) {
        tipoCuerpo = tip;
        this.destinatarios = destinatarios;
        iniciaParametrosMail();
    }

    public String getDestinatarios() {
        return destinatarios;
    }
    
    public void setDestinatarios(String destinatarios) {
        this.destinatarios = destinatarios;
    }
    
    private void iniciaParametrosMail() {
        try {
            this.cuerpoCorreo = obtenCuerpoCorreo();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void setCuerpoOperacionBloqueadaPLD(String contrato) {
        this.cuerpoCorreo = obtenCuerpobloqueoPLD(contrato);
    }
    
    public String obtenCuerpobloqueoPLD(String contrato) {
         String contenidoHTML = "Te informamos que han sido bloqueadas las operaciones en el contrato " + contrato 
                 + " porque se han obtenido registros de PLD en el cliente";
         return contenidoHTML;
    }
    
    public void setCuerpoIntentoCrearClientePLD(String nombre, String contrato, String username) {
        this.cuerpoCorreo = obtenCuerpoIntentoCrearClientePLD(nombre, contrato, username);
    }
    
    private String obtenCuerpoIntentoCrearClientePLD(String nombre, String contrato, String username) {
        String contenidoHTML = "Por este medio le informamos que en el sistema Gestion Fideicomisos el usuario "+username+" intento dar de alta una persona que se encuentra en PLD, corroborar esta información<br/><br/>";
        contenidoHTML += ""
                + "<div class=\"centrar\">\n"
                + "            <table id=\"tabla\">\n"
                + "                <thead>\n"
                + "                    <tr class=\"info\">\n"
                + "                        <th>Contrato</th>\n"
                + "                        <th>Cliente</th>\n"
                + "                    </tr>\n"
                + "                </thead>\n"
                + "                <tbody>\n"
                + "                    <tr class=\"active\">\n"
                + "                        <td>" + contrato + "</td>\n"
                + "                        <td>" + nombre + "</td>\n"
                + "                    </tr>\n"
                + "                </tbody>\n"
                + "            </table>\n"
                + "        </div>\n"
                + "</br>";
        return contenidoHTML;
    }
    
    public void setCuerpoNuevaContrasena(String nombre, String usuario, String contrasena) {
        this.cuerpoCorreo = obtenCuerpoNuevaContrasena(nombre, usuario, contrasena);
    }
    
    private String obtenCuerpoNuevaContrasena(String nombre, String usuario, String contrasena) {
        String contenidoHTML = "";
        contenidoHTML = ""
                + "<HEAD>"
                + "</HEAD>"
                + "<BODY>"
                + "Hola " + nombre + ","
                + "\n <br><br>"
                + "Te mostramos la información necesaria para acceder al sistema gestion de fideicomisos:<br><br>"
                + "<div class=\"centrar\">\n"
                + "            <table id=\"tabla\">\n"
                + "                <thead>\n"
                + "                    <tr class=\"info\">\n"
                + "                        <th>Usuario</th>\n"
                + "                        <th>Contraseña</th>\n"
                + "                    </tr>\n"
                + "                </thead>\n"
                + "                <tbody>\n"
                + "                    <tr class=\"active\">\n"
                + "                        <td>" + usuario + "</td>\n"
                + "                        <td>" + contrasena + "</td>\n"
                + "                    </tr>\n"
                + "                </tbody>\n"
                + "            </table>\n"
                + "        </div>\n"
                + "</br>"
                + "</BODY>"
                + "</HTML>"
                + "";
        return contenidoHTML;
    }
    
    public void setCuerpoNuevoCliente(String clave_contrato, String clave_cliente, String nombre_fideicomitente,
            String nombre_usuario, String telefono_usuario, String usuario, String contrasenna, String correo_usuario) {
        this.cuerpoCorreo = getCuerpoNuevoCliente(clave_contrato, clave_cliente, nombre_fideicomitente, nombre_usuario, telefono_usuario, usuario, contrasenna, correo_usuario);
    }
    
    private String getCuerpoNuevoCliente(String clave_contrato, String clave_cliente, String nombre_fideicomitente,
            String nombre_usuario, String telefono_usuario, String usuario, String contrasenna, String correo_usuario) {
        String lstrBody = "";
        String lstrMensaje = "";

        lstrBody += "<html>";
        lstrBody += "<head> ";
        lstrBody += "<title>Documento sin t&iacute;tulo</title> ";
        lstrBody += "</head> ";

        lstrBody += "<body> ";
        lstrBody += "<table border=\"0\" width=\"80%\" style=\"font-size:12px;font-family:Arial;\"   > ";

        lstrMensaje = "Estimad@ Cliente: ";
        lstrBody += "<tr> ";
        lstrBody += "<td colspan=\"5\">" + lstrMensaje + "</td> ";
        lstrBody += "</tr> ";

        lstrBody += "<tr> ";
        lstrBody += "<td >&nbsp;</td> ";
        lstrBody += "</tr> ";

        lstrMensaje = "Agradecemos su inscripción como usuario del Sistema de Liquidaciones de Garante. ";
        lstrBody += "<tr> ";
        lstrBody += "<td colspan=\"5\">" + lstrMensaje + "</td> ";
        lstrBody += "</tr> ";

        lstrBody += "<tr> ";
        lstrBody += "<td >&nbsp;</td> ";
        lstrBody += "</tr> ";

        lstrMensaje = "La información registrada es la siguiente: ";
        lstrBody += "<tr> ";
        lstrBody += "<td colspan=\"5\">" + lstrMensaje + "</td> ";
        lstrBody += "</tr> ";

        lstrBody += "<tr> ";
        lstrBody += "<td colspan=\"5\">  ";
        lstrBody += "<ul> <li type=\"disc\"> Clave de Fideicomiso: " + clave_contrato + " </li> </ul> ";
        lstrBody += "</td> ";
        lstrBody += "<tr> ";

        lstrBody += "<tr> ";
        lstrBody += "<td colspan=\"5\">  ";
        lstrBody += "<ul> <li type=\"disc\"> Nombre del Fideicomitente: " + nombre_fideicomitente + " </li> </ul> ";
        lstrBody += " </td> ";
        lstrBody += "<tr> ";

        lstrBody += "<tr> ";
        lstrBody += "<td colspan=\"5\">  ";
        lstrBody += "<ul> <li type=\"disc\"> Nombre del Usuario: " + nombre_usuario + " </li> </ul> ";
        lstrBody += " </td> ";
        lstrBody += "<tr> ";

        lstrBody += "<tr> ";
        lstrBody += "<td colspan=\"5\">  ";
        lstrBody += "<ul> <li type=\"disc\"> Correo Electrónico: " + correo_usuario + " </li> </ul> ";
        lstrBody += " </td> ";
        lstrBody += "<tr> ";

        lstrBody += "<tr> ";
        lstrBody += "<td colspan=\"5\">  ";
        lstrBody += "<ul> <li type=\"disc\"> Teléfono Contacto: " + telefono_usuario + " </li> </ul> ";
        lstrBody += " </td> ";
        lstrBody += "<tr> ";

        lstrMensaje = "Su Clave de Cliente, Usuario y Contraseña para acceder a nuestro sitio www.fideicomisogds.mx son las que se muestran a continuación: ";
        lstrBody += "<tr> ";
        lstrBody += "<td colspan=\"5\">" + lstrMensaje + "</td> ";
        lstrBody += "</tr> ";

        lstrBody += "<tr> ";
        lstrBody += "<td >&nbsp;</td> ";
        lstrBody += "</tr> ";

        lstrBody += "<tr> ";
        lstrBody += "<td colspan=\"5\"> ";
        lstrBody += "<table align=\"center\" style=\"border:#006699 1px solid;\"> ";
        lstrBody += "<tr> ";
        lstrBody += "<td align=\"right\"> <b>Clave de Cliente: </b></td> ";
        lstrBody += "<td align=\"left\"> <b>" + clave_cliente + "</b></td> ";
        lstrBody += "</tr> ";
        lstrBody += "<tr> ";
        lstrBody += "<td align=\"right\"> <b> Usuario: </b></td> ";
        lstrBody += "<td align=\"left\"> <b>" + usuario + " </b></td> ";
        lstrBody += "</tr> ";
        lstrBody += "<tr> ";
        lstrBody += "<td align=\"right\"> <b> Contraseña: </b></td> ";
        lstrBody += "<td align=\"left\"> <b> " + contrasenna + "</b></td> ";
        lstrBody += "</tr> ";
        lstrBody += "</table> ";
        lstrBody += "</td> ";
        lstrBody += "</tr> ";

        lstrBody += "<tr> ";
        lstrBody += "<td >&nbsp;</td> ";
        lstrBody += "</tr> ";

        lstrMensaje = "Como usuario del sistema de liquidaciones, usted tendrá acceso a servicios exclusivos en nuestro sitio, "
                + "por lo que esperamos poder atenderles con un servicio de calidad. ";
        lstrBody += "<tr> ";
        lstrBody += "<td colspan=\"5\">" + lstrMensaje + "</td> ";
        lstrBody += "</tr> ";

        lstrBody += "<tr> ";
        lstrBody += "<td >&nbsp;</td> ";
        lstrBody += "</tr> ";

        lstrMensaje = "Para cualquier duda o aclaración, por favor envíenos un mensaje de correo a"
                + " soporte@fideicomisogds.mx donde con gusto lo atenderemos.";
        lstrBody += "<tr> ";
        lstrBody += "<td colspan=\"5\">" + lstrMensaje + "</td> ";
        lstrBody += "</tr> ";

        lstrBody += "<tr> ";
        lstrBody += "<td >&nbsp;</td> ";
        lstrBody += "</tr> ";

        lstrBody += "<tr> ";
        lstrBody += "<td style=\"font-size:10px;font-family:Arial;\"> Nota: Este mensaje responde a una solicitud automatizada"
                + " de la correcta activación de cuenta en el sistema de liquidaciones y es enviado desde una cuenta que es"
                + " exclusiva para enviar mensajes de salida, por lo que no es posible responder correos electrónicos si"
                + " usted usa la opción de responder.</td> ";
        lstrBody += "</tr> ";

        lstrBody += "<tr> ";
        lstrBody += "<td >&nbsp;</td> ";
        lstrBody += "</tr> ";

        lstrMensaje = "Atentamente. ";
        lstrBody += "<tr> ";
        lstrBody += "<td colspan=\"5\">" + lstrMensaje + "</td> ";
        lstrBody += "</tr> ";

        lstrBody += "<tr> ";
        lstrBody += "<td >&nbsp;</td> ";
        lstrBody += "</tr> ";

        lstrBody += "</table> ";
        lstrBody += "</body> ";
        lstrBody += "</html> ";

        return lstrBody;
    }
    
    private String obtenCuerpoCorreo() {
        String bodyHTML = "";
        switch (tipoCuerpo) {
            case GENERA_CONTRASENA:
                 bodyHTML = ""
                        + "<HEAD>"
                        + "</HEAD>"
                        + "<BODY>"
                        + "Se ha creado una nueva contraseña "
                        + "<P> Favor de revisar la solicitud en:</P>"
                        + "<br/>"
                        + "</BODY>"
                        + "</HTML>"
                        + "";
                break;
        }
        return bodyHTML;
    }
    
    private String obtenAsuntoCorreo() {
        String asunto = "";
        switch (tipoCuerpo) {
            case GENERA_CONTRASENA:
                asunto = "Se ha generado una nueva contraseña para el sistema de Garante";
                break;
            case BIENVENIDO:
                asunto = "Bienvenido al Sistema de Liquidaciones de Garante";
                break;
            case INTENTOCREARUSUARIOPLD:
                asunto = "Se intento crear un usuario que se encuentra en PLD";
                break;
            case OPERACIONESPLD:
                asunto = "Operaciones bloqueadas de concontrato en PLD";
                break;
        }
        return asunto;
    }
    
    public boolean enviar() {
        boolean correcto = false;
        try {
            // propiedades de conexion al servidor de correo
            mailConfig = new HTMLMail(host, origen, passwd);
//            System.out.println("origen:" + origen);
            mailConfig.setFrom(origen);
            String subject = obtenAsuntoCorreo();
//            System.out.println("Subject:" + subject);
            mailConfig.setSubject(subject);
//            System.out.println("destino:" + destinatarios);
            mailConfig.setTo(destinatarios);
//            System.out.println("CuerpoCorreo:" + cuerpoCorreo);
            mailConfig.addContent(cuerpoCorreo);
            if (solicitudArchivosAdjuntos) {
                if (archivosAdjuntos != null && archivosAdjuntos.size() > 0) {
                    adjuntarArchivos(mailConfig);
                } else {
                    System.out.println("Se encontraron problemas en alguno de los archivos adjuntos.");
                    return false;
                }
            }
            // CID de una imagen
            //mailConfig.addCID("firma", "C:\\crediFuturo\\imagenes\\SME.jpg");
//             enviar el correo MULTIPART
            mailConfig.sendMultipart();
            System.out.println("[ Mail enviado ]");
            correcto = true;
        } catch (Exception ex) {
            System.out.println("Exc:enviar():" + ex.getMessage());
        }
        return correcto;
    }
    
    private boolean adjuntarArchivos(HTMLMail htmlMail) {
        for (String ruta : archivosAdjuntos) {
            try {
                htmlMail.addAttach(ruta);
            } catch (Exception ex) {
                System.out.println("No se pudo adjuntar al email el archivo: " + ruta);
                System.out.println("Excpetion:" + ex.getMessage());
                return false;
            }
        }
        return true;
    }
    
    public void setArchivosAdjuntos(List<String> archivosAdjuntos) {
        this.solicitudArchivosAdjuntos = true;
        this.archivosAdjuntos = obtenArchivosAdjuntos(archivosAdjuntos);
    }
    
    /**
     * Obtiene a atravez de un vector los archivos a insertar en el correo
     *
     * @param archivosAdjuntos
     * @return
     */
    private List<String> obtenArchivosAdjuntos(List<String> archivosAdjuntos) {
        List<String> procesoCorrecto = new ArrayList<>();

        try {
            for (String ruta : archivosAdjuntos) {
                File file = new File(ruta);
                if (file != null) {
                    if (!file.canRead() || !file.exists()) {
                        System.out.println("El archivo no se puede leer o no existe");
                        return new ArrayList<>();
                    }
                    procesoCorrecto.add(ruta);
                } else {
                    System.out.println("Archivo es nulo");
                    return new ArrayList<>();
                }
            }
        } catch (Exception ex) {
            procesoCorrecto = null;
            System.out.println("Exception analizando vector de archivos adjuntos");
            System.out.println("Exception:" + ex.getMessage());
        }
        return procesoCorrecto;
    }
    
}
