/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.garante.creaxml.Helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Desarrollo
 */
public class ReportesExcel {
    
    private String path;
    private static final String BASE_PATH = "c:" + File.separator +"inetpub" + File.separator + "ftproot" + File.separator;

    public ReportesExcel(){
        path = System.getProperty("user.dir");
    }
    
    public boolean generaExcel(String tipoMovimiento, List<ErroresXML> lErrores){
        return generaCSV(tipoMovimiento, lErrores);
    }
    
    public boolean generaExcelFull(String nombre, String[] titulos, List<String> elementos, List<String> fechasHora) {
        //File reporte = new File(File.separator+ "inetpub" + File.separator + "ftproot" + File.separator + nombre + ".xlsx");
    	File reporte = new File(nombre + ".xlsx");
        if (reporte.exists()) reporte.delete();
        System.out.println("Generando reporte " + nombre + ".xlsx");
        boolean res = false;
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet(nombre);
            XSSFRow headers = sheet.createRow(0);
            for (int i = 0; i < titulos.length; i++) {
                XSSFCell campo = headers.createCell(i);
                // estilo fondo negro letra blanca
                XSSFCellStyle style = workbook.createCellStyle();
                style.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                Font font = workbook.createFont();
                font.setColor(IndexedColors.WHITE.getIndex());
                style.setFont(font);   
                campo.setCellValue(titulos[i]);
                campo.setCellStyle(style);
            }
            if (elementos != null) {
                XSSFCellStyle style = workbook.createCellStyle();
                style.setBorderBottom(BorderStyle.THIN);
                style.setBorderTop(BorderStyle.THIN);
                style.setBorderLeft(BorderStyle.THIN);
                style.setBorderRight(BorderStyle.THIN);   
                for (int x = 0; x < elementos.size(); x++) {
                    String mov = elementos.get(x), fechaHora = fechasHora.get(x);
                    XSSFRow row = sheet.createRow(x+1);
                    for (int j = 0; j < titulos.length; j++) {
                        XSSFCell cell = row.createCell(j);
                        cell.setCellStyle(style);
                        switch(j) {
                            case 0:
                                cell.setCellValue(mov);
                                break;
                            case 1:
                                cell.setCellValue(fechaHora);
                                break;
                        }
                    }
                }
            }
            for (int i = 0; i < titulos.length; i++) {
                sheet.autoSizeColumn(i);
            }   
            OutputStream out = new FileOutputStream(BASE_PATH + File.separator + nombre + ".xlsx");
            workbook.write(out);
            res = true;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReportesExcel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReportesExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    
    public static boolean generaCSV(String nombre, List<ErroresXML> lErrores){
        boolean res = false;
        FileWriter fileWriter = null;
        FileWriter fileWriter2 = null;
        PrintWriter printWriter = null;
        PrintWriter printWriter2 = null;
        try {
        	File archivo = new File( BASE_PATH + File.separator + nombre + ".csv");
            if(archivo.exists()) {
                fileWriter = new FileWriter(archivo, true);
            } else {
                fileWriter = new FileWriter(archivo);
            }
            printWriter = new PrintWriter(fileWriter);
            for(int i = 0; i < lErrores.size(); i++){
                printWriter.println(lErrores.get(i));
            }
            res = true;
        } catch (IOException ex) {
            Logger.getLogger(ReportesExcel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (null != fileWriter) {
                    fileWriter.close();
                }
                if (null != fileWriter2) {
                    fileWriter2.close();
                }
            } catch (IOException e) {
            }
        }
        return res;
    }
    
}
