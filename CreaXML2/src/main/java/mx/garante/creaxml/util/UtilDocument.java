package mx.garante.creaxml.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Properties;
//import org.apache.log4j.Logger;

public class UtilDocument {
    //private static final Logger logger = Logger.getLogger(UtilDocument.class);

    public static Properties loadConfiguration() throws Exception {
        Properties props = new Properties();
        String realPath = "";
        String osName = System.getProperty("os.name");
        String OS_SYSTEM = "linux";
        if (osName.contains("indows")) {
            OS_SYSTEM = "windows";
        }

        String pathRunning = UtilDocument.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        //logger.info("pathRunning :: " + pathRunning);

        try {
            String decoded = URLDecoder.decode(pathRunning, "UTF-8");
            realPath = decoded.trim();
        } catch (UnsupportedEncodingException arg9) {
            //logger.error(arg9);
        }

        if (OS_SYSTEM.equalsIgnoreCase("windows") && realPath.startsWith("/")) {
            realPath = realPath.substring(1);
        }

        File fileTmp = new File(realPath);
        if (fileTmp.isFile()) {
            realPath = fileTmp.getParent();
        }

        String pathTo;
        if (!realPath.endsWith("/")) {
            pathTo = realPath + File.separator;
        } else {
            pathTo = realPath;
        }

        realPath = pathTo + "config.properties";
        File fileConfig = new File(realPath);
        if (!fileConfig.isFile()) {
            throw new Exception(" Config file don\'t exists => " + realPath);
        } else {
            FileInputStream fileIs = new FileInputStream(new File(realPath));
            props.load(fileIs);
            return props;
        }
    }    
}
