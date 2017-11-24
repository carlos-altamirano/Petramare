package com.garante.gestionfideicomisos.Helpers.Mail;

import java.io.*;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class HTMLMail {

    /**
     * Algunas constantes
     */
    static public int SIMPLE = 0;
    static public int MULTIPART = 1;
    /**
     * Algunos mensajes de error
     */
    public static String ERROR_01_LOADFILE = "Error al cargar el fichero";
    public static String ERROR_02_SENDMAIL = "Error al enviar el mail";
    /**
     * Variables
     */
    private Properties props = new Properties();
    private String host, protocol, user, password;
    private String from, content, to;
    private String subject = "";
    /**
     * MultiPart para crear mensajes compuestos
     */
    MimeMultipart multipart = new MimeMultipart("related");
// -----

    /**
     * Constructor
     *
     * @param host nombre del servidor de correo
     * @param user usuario de correo
     * @param password password del usuario
     */
    public HTMLMail(String host, String user, String password) {
        props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", host);
        props.setProperty("mail.user", user);
        props.setProperty("mail.password", password);
        props.setProperty("mail.smtp.starttls.enable", "false");
        props.setProperty("mail.smtp.port", "587");
        props.setProperty("mail.smtp.auth", "true");
    }
//-----

    /**
     * Muestra un mensaje de trazas
     *     
* @param metodo nombre del metodo
     * @param mensaje mensaje a mostrar
     */
    static public void trazas(String metodo, String mensaje) {
// TODO: reemplazar para usar Log4J
        System.out.println("[" + HTMLMail.class.getName() + "][" + metodo
                + "]:[" + mensaje + "]");
    }
// -----

    /**
     * Carga el contenido de un fichero de texto HTML en un String
     *     
* @param pathname ruta del fichero
     * @return un String con el contenido del fichero
     * @throws Exception Excepcion levantada en caso de error
     */
    static public String loadHTMLFile(String pathname) throws Exception {
        String content = "";
        File f = null;
        BufferedReader in = null;
        try {
            f = new File(pathname);
            if (f.exists()) {
                long len_bytes = f.length();
                trazas("loadHTMLFile", "pathname:" + pathname + ", len:" + len_bytes);
            }
            in = new BufferedReader(new FileReader(f));
            String str;
            while ((str = in.readLine()) != null) {
// process(str);
                str = str.trim();
                content = content + str;
            }
            in.close();
            return content;
        } catch (Exception e) {
            String MENSAJE_ERROR = ERROR_01_LOADFILE + ": ['" + pathname + "'] : " + e.toString();
            throw new Exception(MENSAJE_ERROR);
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }
// ------

    /**
     * Añade el contenido base al multipart
     *
     * @throws Exception Excepcion levantada en caso de error
     */
    public void addContentToMultipart() throws Exception {
// first part (the html)
        BodyPart messageBodyPart = new MimeBodyPart();
        String htmlText = this.getContent();
        messageBodyPart.setContent(htmlText, "text/html");
// add it
        this.multipart.addBodyPart(messageBodyPart);
    }
// -----

    /**
     * Añade el contenido base al multipart
     *
     * @param htmlText contenido html que se muestra en el mensaje de correo
     * @throws Exception Excepcion levantada en caso de error
     */
    public void addContent(String htmlText) throws Exception {
// first part (the html)
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(htmlText, "text/html");
// add it
        this.multipart.addBodyPart(messageBodyPart);
    }
// -----

    /**
     * Añade al mensaje un cid:name utilizado para guardar las imagenes
     * referenciadas en el HTML de la forma <img src=cid:name />
     *
     * @param cidname identificador que se le da a la imagen. suele ser un
     * string generado aleatoriamente.
     * @param pathname ruta del fichero que almacena la imagen
     * @throws Exception excepcion levantada en caso de error
     */
    public void addCID(String cidname, String pathname) throws Exception {
        DataSource fds = new FileDataSource(pathname);
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setDataHandler(new DataHandler(fds));
        messageBodyPart.setHeader("Content-ID", "<" + cidname + ">");
        this.multipart.addBodyPart(messageBodyPart);
    }
// ----

    /**
     * Añade un attachement al mensaje de email
     *
     * @param pathname ruta del fichero
     * @throws Exception excepcion levantada en caso de error
     */
    public void addAttach(String pathname) throws Exception {
        File file = new File(pathname);
        BodyPart messageBodyPart = new MimeBodyPart();
        DataSource ds = new FileDataSource(file);
        messageBodyPart.setDataHandler(new DataHandler(ds));
        messageBodyPart.setFileName(file.getName());
        messageBodyPart.setDisposition(Part.ATTACHMENT);
        this.multipart.addBodyPart(messageBodyPart);
    }
// ----

    /**
     * Envia un correo multipart
     *
     * @throws Exception Excepcion levantada en caso de error
     */
    public void sendMultipart() throws Exception {
        Session mailSession = Session.getInstance(this.props, null);
        mailSession.setDebug(true);
        Transport transport = mailSession.getTransport();
        MimeMessage message = new MimeMessage(mailSession);
        message.setSubject(this.getSubject(), "UTF-8");
        message.setFrom(new InternetAddress(this.getFrom()));
        //Analizamos cadena y obtenemos los correos destino
        String[] correos = this.getTo().split(",");
        for (String correo : correos) {
            System.out.println("CorreoTo::::" + correo);
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(correo));
        }

// put everything together
        message.setContent(multipart);
        transport.connect(this.props.getProperty("mail.user"), "L1qu1da*");
//        transport.connect();
        transport.sendMessage(message,
                message.getRecipients(Message.RecipientType.TO));
        transport.close();
    }
// -----

    /**
     * Envia un correo simple
     *
     * @throws Exception Excepcion levantada en caso de error
     */
    public void send() throws Exception {
        try {
            Session mailSession = Session.getDefaultInstance(this.props, null);
            mailSession.setDebug(true);
            Transport transport = mailSession.getTransport();
            MimeMessage message = new MimeMessage(mailSession);
            message.setSubject(this.getSubject());
            message.setFrom(new InternetAddress(this.getFrom()));
            message.setContent(this.getContent(), "text/html");
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(this.getTo()));
            transport.connect(this.props.getProperty("mail.user"), "L1qu1da*");
            transport.sendMessage(message,
                    message.getRecipients(Message.RecipientType.TO));
            transport.close();
        } catch (Exception e) {
            String MENSAJE_ERROR = ERROR_02_SENDMAIL + " : " + e.toString();
            throw new Exception(MENSAJE_ERROR);
        }
    }
//-----

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}