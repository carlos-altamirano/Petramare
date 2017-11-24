package Common;

import java.io.FileOutputStream;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class clsExcel {

    /**
     * Crea una hoja Excel y la guarda.
     */
    public static void main(String[] args) {
//        boolean creaExcel = false;
        // Se crea el libro
        HSSFWorkbook libro = new HSSFWorkbook();
        // Se crea una hoja dentro del libro
        HSSFSheet hoja = libro.createSheet();
        // Se crea una fila dentro de la hoja
        HSSFRow fila = hoja.createRow(0);

        // Se crea una celda dentro de la fila
        HSSFCell celda0 = fila.createCell((short) 0);
        // Se crea el contenido de la celda y se mete en ella.
        HSSFRichTextString texto0 = new HSSFRichTextString("CONTRATO");
        celda0.setCellValue(texto0);

        // Se crea una celda dentro de la fila
        HSSFCell celda1 = fila.createCell((short) 1);
        // Se crea el contenido de la celda y se mete en ella.
        HSSFRichTextString texto1 = new HSSFRichTextString("FECHA DE LIQUIDACIÓN");
        celda1.setCellValue(texto1);

        // Se crea una celda dentro de la fila
        HSSFCell celda2 = fila.createCell((short) 2);
        // Se crea el contenido de la celda y se mete en ella.
        HSSFRichTextString texto2 = new HSSFRichTextString("CUENTA DE ORIGEN");
        celda2.setCellValue(texto2);

        // Se crea una celda dentro de la fila
        HSSFCell celda3 = fila.createCell((short) 3);
        // Se crea el contenido de la celda y se mete en ella.
        HSSFRichTextString texto3 = new HSSFRichTextString("BANCO");
        celda3.setCellValue(texto3);

        // Se crea una celda dentro de la fila
        HSSFCell celda4 = fila.createCell((short) 4);
        // Se crea el contenido de la celda y se mete en ella.
        HSSFRichTextString texto4 = new HSSFRichTextString("CUENTA DE DEPÓSITO");
        celda4.setCellValue(texto4);

        // Se crea una celda dentro de la fila
        HSSFCell celda5 = fila.createCell((short) 5);
        // Se crea el contenido de la celda y se mete en ella.
        HSSFRichTextString texto5 = new HSSFRichTextString("IMPORTE DE LA LIQUIDACIÓN");
        celda5.setCellValue(texto5);

        // Se crea una celda dentro de la fila
        HSSFCell celda6 = fila.createCell((short) 6);
        // Se crea el contenido de la celda y se mete en ella.
        HSSFRichTextString texto6 = new HSSFRichTextString("TIPO DE MOVIMIENTO");
        celda6.setCellValue(texto6);

        // Se crea una celda dentro de la fila
        HSSFCell celda7 = fila.createCell((short) 7);
        // Se crea el contenido de la celda y se mete en ella.
        HSSFRichTextString texto7 = new HSSFRichTextString("NOMBRE(S) DEL FIDEICOMISARIO");
        celda7.setCellValue(texto7);

        // Se crea una celda dentro de la fila
        HSSFCell celda8 = fila.createCell((short) 8);
        // Se crea el contenido de la celda y se mete en ella.
        HSSFRichTextString texto8 = new HSSFRichTextString("APELLIDO PATERNO FIDEICOMISARIO");
        celda8.setCellValue(texto8);

        // Se crea una celda dentro de la fila
        HSSFCell celda9 = fila.createCell((short) 9);
        // Se crea el contenido de la celda y se mete en ella.
        HSSFRichTextString texto9 = new HSSFRichTextString("APELLIDO MATERNO FIDEICOMISARIO");
        celda9.setCellValue(texto9);

        // Se crea una celda dentro de la fila
        HSSFCell celda10 = fila.createCell((short) 10);
        // Se crea el contenido de la celda y se mete en ella.
        HSSFRichTextString texto10 = new HSSFRichTextString("C.U.R.P.");
        celda10.setCellValue(texto10);

        try {
            FileOutputStream elFichero = new FileOutputStream("holamundo.xls");
            libro.write(elFichero);
            elFichero.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        return creaExcel;
    }
}
