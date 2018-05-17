package mx.garante.creaxml.Helpers;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.InternetAddress;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EnvioMail {

    public static boolean enviaCorreo(String correosDestino, String asunto, String cuerpo) {
        Log LOG = LogFactory.getLog(EnvioMail.class);
        boolean res = false;
        Properties props = null;
        try {
            props = new Properties();
            props.setProperty("mail.smtp.host", "mail.abugaber.com");
            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.user", "soporte@fideicomisogds.mx");
            props.setProperty("mail.password", "Soport3*");
            props.setProperty("mail.smtp.starttls.enable", "false"); //requiere tls?
            props.setProperty("mail.smtp.auth", "true");
            Session session = Session.getInstance(props);
            
            String direcciones[];
            if(correosDestino.contains(":")){
                direcciones = correosDestino.split(":");
            }else{
                direcciones = new String[1]; direcciones[0] = correosDestino;
            }
            
            MimeMessage correo = new MimeMessage(session);
            correo.setFrom(new InternetAddress(props.getProperty("mail.smtp.user")));
            
            for (String direccione : direcciones) {
                correo.addRecipients(Message.RecipientType.TO, direccione);
            }
            
            correo.setSubject(asunto, "UTF-8");

            MimeBodyPart contenido = new MimeBodyPart();
            contenido.setContent(cuerpo, "text/html; charset=utf-8");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(contenido);
            correo.setContent(multipart);
            Transport t = session.getTransport("smtp");
            t.connect((String) props.get("mail.smtp.user"), (String) props.get("mail.password"));
            
            t.sendMessage(correo, correo.getAllRecipients());
            t.close();
            res = true;
        } catch (MessagingException e) {
            LOG.error("Method: enviar = Error al enviar email : "+e.getMessage());
        }

        return res;
    }
}
