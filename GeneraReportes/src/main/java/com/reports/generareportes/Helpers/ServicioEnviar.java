package com.reports.generareportes.Helpers;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class ServicioEnviar {
    
    private final Properties propiedades;
    private final Session session;
    
    public ServicioEnviar() {
        propiedades = new Properties();
        propiedades.setProperty("mail.smtp.host", "mail.gp.org.mx");
        propiedades.setProperty("mail.smtp.starttls.enable", "false"); //requiere tls?
        propiedades.setProperty("mail.smtp.port", "587");
        propiedades.setProperty("mail.smtp.user", "no-reply@gp.org.mx");
        propiedades.setProperty("mail.password", "Noreply17*");
        propiedades.setProperty("mail.smtp.auth", "true"); //le decimos que requiere contraseña
        
        session = Session.getDefaultInstance(propiedades);
        session.setDebug(true);
    }
    
    /**
     * Se crear el cuerpo del mensaje para ser enviado
     * @param asunto not null
     * @param mensaje not null
     * @param para not null, Permite enviar direcciones de correo electronico separados por ; 
     * @param archivos Lista de archivos a enviar puede ser null
     * @return Boolean true - se envio correctamente, false- error al enviar
     */
    public Boolean enviar(String asunto, String mensaje, String para, List<File> archivos) {
        
        boolean res = false;
        
        try {
            MimeMessage correo = new MimeMessage(session);
            correo.setFrom(new InternetAddress(propiedades.getProperty("mail.smtp.user")));
            String[] direcciones = para.split(";");
            boolean paraTo = false;
            for (String direccion : direcciones) {
                if (paraTo) {
                    correo.addRecipients(Message.RecipientType.CC, direccion);
                } else {
                    correo.addRecipients(Message.RecipientType.TO, direccion);
                    paraTo = true;
                }
            }
            correo.setSubject(asunto, "UTF-8");
            
            MimeBodyPart contenido = new MimeBodyPart();
            contenido.setContent(mensaje, "text/html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(contenido);
            
            if (archivos != null) {
                for (File archivo : archivos) {
                    MimeBodyPart messageBodyPart = new MimeBodyPart();
                    messageBodyPart.setText(mensaje, "text/html");
                    DataSource source = new FileDataSource(archivo);
                    messageBodyPart.setDataHandler(new DataHandler(source));
                    messageBodyPart.setFileName(archivo.getName());
                    multipart.addBodyPart(messageBodyPart);
                }
                correo.setContent(multipart);
            } else {
                correo.setContent(multipart);
            }
            
            Transport t = session.getTransport("smtp");
            t.connect((String) propiedades.get("mail.smtp.user"), (String) propiedades.get("mail.password"));  
            t.sendMessage(correo, correo.getAllRecipients());
            t.close();
            res = true;
        } catch (AddressException ex) {
            System.out.println("Error direccion -> " + ex);
        } catch (MessagingException ex) {
            System.out.println("Error mensaje -> " + ex);
        }
        
        return res;
        
    }
    
    public String obtenCuerpo(String url) {
        String contenido = "";
        try {
            URL urlObj = new URL(url);
            InputStreamReader isr = new InputStreamReader(urlObj.openStream());
            BufferedReader br = new BufferedReader(isr);
            String linea;
            while ((linea= br.readLine())!= null) {
                contenido+=linea;
            }
            br.close();
        } catch (MalformedURLException ex) {
            System.out.println("Error url" + ex);
        } catch (IOException ex) {
            System.out.println("Error al leer html " + ex);
        }
        return contenido;
    } 
    
}
