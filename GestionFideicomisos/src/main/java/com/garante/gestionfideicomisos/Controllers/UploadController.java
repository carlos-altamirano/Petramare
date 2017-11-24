package com.garante.gestionfideicomisos.Controllers;

import com.garante.gestionfideicomisos.DAOs.DocumentacionDAO;
import com.garante.gestionfideicomisos.Models.Documentacion;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UploadController {
    
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String uploadFiles(MultipartFile archivo, Integer idDocumentacion, Integer seleccion) {
        String url;
        DocumentacionDAO documentacionDAO = new DocumentacionDAO();
        Documentacion documentacion = documentacionDAO.get(idDocumentacion);
        Documentacion documentacionRespaldo = documentacion;
        try {
            if (!archivo.isEmpty()) {
                byte[] archivoBytes = archivo.getBytes();
                if (archivoBytes.length > 2000000) {
                    url = "redirect:/adm/contrato/"+documentacion.getClaveContrato()+"/edit?msg=1";
                } else {
                    String ext = this.extValidas(archivo.getContentType());
                    String nombreArchivo = "";
                    if (!ext.equals("")) {
                        String fecha = new SimpleDateFormat("yyyyMMdd").format(new Date());
                        switch(seleccion) {
                            case 1:
                                documentacion.setCedulaidFiscal(true);
                                nombreArchivo = "RFC";
                                break;
                            case 2:
                                documentacion.setConstFirmaElect(true);
                                nombreArchivo = "efirma";
                                break;
                            case 3:
                                documentacion.setCompDomicilio(true);
                                nombreArchivo = "compDomicilio";
                                break;
                            case 4:
                                documentacion.setIdentifiOficial(true);
                                nombreArchivo = "ID";
                                break;
                            case 5:
                                documentacion.setCurp(true);
                                nombreArchivo = "CURP";
                                break;
                            case 6:
                                documentacion.setPrimaReg(true);
                                nombreArchivo = "primaReg";
                                break;
                            case 7:
                                documentacion.setEscrituraContCliente(true);
                                nombreArchivo = "escrituraConstitutivaCliente";
                                break;
                            case 8:
                                documentacion.setPoderesDominio(true);
                                nombreArchivo = "poderesDominio";
                                break;
                            case 9:
                                documentacion.setRegistroCNBV(true);
                                nombreArchivo = "CNBV";
                                break;
                            case 10:
                                documentacion.setContrato(true);
                                nombreArchivo = "contrato";
                                break;
                        }
                        
                        File ruta = new File("\\inetpub\\ftproot\\expedientes\\"+documentacion.getClaveContrato()+"\\"+nombreArchivo);
                        ruta.mkdirs();
                        String nombreArchivo2 = nombreArchivo+fecha;
                        File file = new File("/inetpub/ftproot/expedientes/"+documentacion.getClaveContrato()+"/"+nombreArchivo+"/"+nombreArchivo2+"."+ext);
                        
                        BufferedOutputStream stream;
                        stream = new BufferedOutputStream(new FileOutputStream(file));
                        stream.write(archivoBytes);
                        stream.close();
                        
                        if (documentacionDAO.update(documentacion)) {
                            url = "redirect:/adm/contrato/"+documentacion.getClaveContrato()+"/edit?msg=2";
                        } else {
                            documentacionDAO.update(documentacionRespaldo);
                            url = "redirect:/adm/contrato/"+documentacion.getClaveContrato()+"/edit?msg=3";
                        }
                        
                    } else {
                        url = "redirect:/adm/contrato/"+documentacion.getClaveContrato()+"/edit?msg=4";
                    }
                }
            } else {
                url = "redirect:/adm/contrato/"+documentacion.getClaveContrato()+"/edit?msg=5";
            }
            
        } catch (Exception e) {
            url = "redirect:/adm/contrato/"+documentacion.getClaveContrato()+"/edit?msg=6";
        }
        return url;
    }
    
    private String extValidas(String tipo) {
        String res = "";
        if (tipo.equals("application/pdf")) {
            res = tipo.replace("application/", "");
        }
        return res;
    }
    
}
