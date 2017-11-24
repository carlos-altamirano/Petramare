package com.garante.gestionfideicomisos.Controllers;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DownloadController {
    
    @ResponseBody
    @RequestMapping(value = "/verifica/{nombreArchivo}/{contrato}", method = RequestMethod.POST)
    public String listaArchivos(@PathVariable String nombreArchivo, @PathVariable String contrato){
        List<String> lista = new ArrayList<>();
        String ruta = "/inetpub/ftproot/expedientes/" + contrato + "/" + nombreArchivo;
        File folder = new File(ruta);
        File[] listFiles = folder.listFiles();
        for (File archivo : listFiles) {
            String nombre = archivo.getName();
            lista.add(nombre);
        }
        return new Gson().toJson(lista);
    }
    
    @RequestMapping(value = "/download/{tipo}/{nombreArchivo}/{contrato}", method = RequestMethod.GET)
    public void descargaArchivo(@PathVariable String tipo, @PathVariable String nombreArchivo, @PathVariable String contrato, HttpServletResponse response) throws Exception {
        
        String ruta = "/inetpub/ftproot/expedientes/" + contrato + "/" + tipo + "/" + nombreArchivo;
        if (new File(ruta).exists()){
            this.descargar(ruta, response);
        }
        
    }
    
    private void descargar(String ruta, HttpServletResponse response) throws Exception {
        File file = new File(ruta);
        InputStream is = new FileInputStream(file);
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
        OutputStream os = response.getOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = is.read(buffer)) != -1) {
            os.write(buffer, 0, len);
        }
        os.flush();
        os.close();
        is.close();
    }
    
}
