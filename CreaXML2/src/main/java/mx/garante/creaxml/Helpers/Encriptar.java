package mx.garante.creaxml.Helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.Signature;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.ssl.PKCS8Key;

public class Encriptar {

    private static String key_filename = "";

    public static void setKeyFilename(String name) {
        key_filename = name;
    }

    public static String base64File(String archivo) {
        InputStream inputStream = null;
        String cadena = "";
        try {
            File certificado = new File(archivo);
            byte[] bytes = new byte[(int) certificado.length()];
            inputStream = new FileInputStream(certificado);
            inputStream.read(bytes);

            cadena = Base64.getEncoder().encodeToString(bytes);

        } catch (IOException ex) {
            Logger.getLogger(Encriptar.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(Encriptar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return cadena;
    }

    public static String base64Texto(String texto) {
        String cadena = Base64.getEncoder().encodeToString(texto.getBytes());
        return cadena;
    }

    public static String sha256Texto(String texto) {
        String cadena = DigestUtils.sha256Hex(texto);
        return cadena;
    }

    public static String rsaTexto(String texto, String password) {
        String cadena = "";
        InputStream inputStream = null;
        try {
            File file = new File(System.getProperty("user.dir") + File.separator + "Certificados" + File.separator + key_filename);
            byte[] bytes = new byte[(int) file.length()];
            inputStream = new FileInputStream(file);
            inputStream.read(bytes);
            PKCS8Key pkcsk = new PKCS8Key(bytes, password.toCharArray());
            PrivateKey privateKey = pkcsk.getPrivateKey();

            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey);
            signature.update(texto.getBytes("UTF-8"));

            byte[] cadenaFirmada = signature.sign();

            cadena = Base64.getEncoder().encodeToString(cadenaFirmada);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Encriptar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | GeneralSecurityException ex) {
            Logger.getLogger(Encriptar.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(Encriptar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return cadena;
    }

}
