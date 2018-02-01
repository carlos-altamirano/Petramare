package com.reports.generareportes.Helpers;

import com.reports.generareportes.Modelos.Contrato;
import com.reports.generareportes.Modelos.Movimiento;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
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

public class GenExcel {
    
    public static boolean generaExcel(String nombre, String[] titulos, List<Movimiento> movs, List<Contrato> contratos) {
        System.out.println("Generando reporte " + nombre);
        boolean res = false;
        try {
            
            String path = System.getProperty("user.dir");
            
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
            
            if (movs != null) {
                XSSFCellStyle style = workbook.createCellStyle();
                style.setBorderBottom(BorderStyle.THIN);
                style.setBorderTop(BorderStyle.THIN);
                style.setBorderLeft(BorderStyle.THIN);
                style.setBorderRight(BorderStyle.THIN);
                
                for (int x = 0; x < movs.size(); x++) {
                    Movimiento mov = movs.get(x);
                    XSSFRow row = sheet.createRow(x+1);
                    for (int j = 0; j < titulos.length; j++) {
                        XSSFCell cell = row.createCell(j);
                        cell.setCellStyle(style);
                        switch(j) {
                            case 0:
                                cell.setCellValue(mov.getClaveContrato());
                                break;
                            case 1:
                                cell.setCellValue(mov.getFechaUsuarioAutoriza());
                                break;
                            case 2:
                                cell.setCellValue(mov.getFechaLiquidacion());
                                break;
                            case 3:
                                cell.setCellValue(mov.getClaveEmpleado());
                                break;
                            case 4:
                                cell.setCellValue(mov.getNombreEmpleado());
                                break;
                            case 5:
                                cell.setCellValue(mov.getApellidoPEmpleado());
                                break;
                            case 6:
                                cell.setCellValue(mov.getApellidoMEmpleado());
                                break;
                            case 7:
                                cell.setCellValue(mov.getCurp());
                                break;
                            case 8:
                                cell.setCellValue(mov.getRfc());
                                break;
                            case 9:
                                cell.setCellValue(mov.getCuentaDeposito());
                                break;
                            case 10:
                                cell.setCellValue(mov.getFechaIngreso());
                                break;
                            case 11:
                                cell.setCellValue(mov.getDeptoEmpleado());
                                break;
                            case 12:
                                cell.setCellValue(mov.getPuestoEmpleado());
                                break;
                            case 13:
                                cell.setCellValue(mov.getTipo());
                                break;
                            case 14:
                                cell.setCellValue(mov.getImporteLiquidacion());
                                break;
                            case 15:
                                cell.setCellValue(mov.getImporteLiquidacionMxn());
                                break;
                        }
                    }
                }
            } else {
                XSSFCellStyle style = workbook.createCellStyle();
                style.setBorderBottom(BorderStyle.THIN);
                style.setBorderTop(BorderStyle.THIN);
                style.setBorderLeft(BorderStyle.THIN);
                style.setBorderRight(BorderStyle.THIN);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                for (int x = 0; x < contratos.size(); x++) {
                    Contrato contrato = contratos.get(x);
                    XSSFRow row = sheet.createRow(x+1);
                    for (int i = 0; i < titulos.length; i++) {
                        XSSFCell cell = row.createCell(i);
                        cell.setCellStyle(style);
                        switch(i) {
                            case 0:
                                cell.setCellValue(contrato.getClaveContrato());
                                break;
                            case 1:
                                cell.setCellValue(contrato.getNombreCliente());
                                break;
                            case 2:
                                cell.setCellValue(contrato.getCuentaOrigen());
                                break;
                            case 3:
                                cell.setCellValue(contrato.getGrupo());
                                break;
                            case 4:
                                cell.setCellValue(contrato.getDomicilioFiscal());
                                break;
                            case 5:
                                cell.setCellValue(contrato.getRfc());
                                break;
                            case 6:
                                cell.setCellValue(contrato.getTelefono());
                                break;
                            case 7:
                                cell.setCellValue(contrato.getCorreo());
                                break;
                            case 8:
                                cell.setCellValue(contrato.getTipoHonorario());
                                break;
                            case 9:
                                cell.setCellValue(contrato.getHonorarioSinIva());
                                break;
                            case 10:
                                cell.setCellValue(contrato.getOficinas());
                                break;
                            case 11:
                                cell.setCellValue(dateFormat.format(contrato.getFechaCaptura()));
                                break;
                            case 12:
                                cell.setCellValue(contrato.getStatus());
                                break;
                            case 13:
                                cell.setCellValue(contrato.getSaldo());
                                break;
                            case 14:
                                cell.setCellValue(contrato.getIdCodes());
                                break;
                            case 15:
                                cell.setCellValue(contrato.getEntFed());
                                break;
                            case 16:
                                cell.setCellValue(contrato.getCodPos());
                                break;
                        }
                    }
                }
            }
            
            for (int i = 0; i < titulos.length; i++) {
                sheet.autoSizeColumn(i);
            }
            
            OutputStream out = new FileOutputStream(path + "\\" + nombre + ".xlsx");
            workbook.write(out);
            res = true;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GenExcel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GenExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    
}
