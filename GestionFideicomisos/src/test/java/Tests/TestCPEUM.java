package Tests;

import com.garante.gestionfideicomisos.DAOs.CodigoPostalDAO;
import com.garante.gestionfideicomisos.Models.CodigoPostal;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.junit.Test;

public class TestCPEUM {

    //@Test
    public void insertaCPExcel() throws FileNotFoundException, IOException {
    
        FileInputStream file = new FileInputStream(new File("/Users/Erwin/Desktop/CPdescarga.xls"));

        HSSFWorkbook workbook = new HSSFWorkbook(file);
        for (int j = 1; j <= 32; j++) {
            HSSFSheet sheet = workbook.getSheetAt(j);
            
            int contador = 1;
            boolean omiteUno = false;
            for (Row row : sheet) {
                if (omiteUno) {
                    CodigoPostal cp = new CodigoPostal();
                    for (int i = 0; i<15; i++) {
                        Cell celda = row.getCell(i, Row.CREATE_NULL_AS_BLANK);
                        switch (contador) {
                            case 1:
                                cp.setdCodigo(celda.getStringCellValue());
                                break;
                            case 2:
                                cp.setdAsenta(celda.getStringCellValue());
                                break;
                            case 3:
                                cp.setdTipoAsenta(celda.getStringCellValue());
                                break;
                            case 4:
                                cp.setdMnpio(celda.getStringCellValue());
                                break;
                            case 5:
                                cp.setdEstado(celda.getStringCellValue());
                                break;
                            case 6:
                                cp.setdCiudad(celda.getStringCellValue());
                                break;
                            case 7:
                                cp.setdCP(celda.getStringCellValue());
                                break;
                            case 8:
                                cp.setcEstado(celda.getStringCellValue());
                                break;
                            case 9:
                                cp.setcOficina(celda.getStringCellValue());
                                break;
                            case 10:
                                cp.setcCP(celda.getStringCellValue());
                                break;
                            case 11:
                                cp.setcTipoAsenta(celda.getStringCellValue());
                                break;
                            case 12:
                                cp.setcMnpio(celda.getStringCellValue());
                                break;
                            case 13:
                                cp.setIdAsentaCpcons(celda.getStringCellValue());
                                break;
                            case 14:
                                cp.setdZona(celda.getStringCellValue());
                                break;
                            case 15:
                                cp.setcCveCiudad(celda.getStringCellValue());
                                break;
                        }
                        if (contador== 15) {
                            contador = 1;
                        } else {
                            contador++;
                        }
                    }
                    CodigoPostalDAO codigoPostalDAO = new CodigoPostalDAO();
                    codigoPostalDAO.save(cp);
                } else {
                    omiteUno = true;
                }
            }
            System.out.println("Estado insertado " + j);
        }
        workbook.close();
    }
}
