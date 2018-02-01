package mx.org.gp.nomina12.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.sat.cfdi.Comprobante;
import mx.sat.cfdi.Nomina;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author luis-valerio
 */
public class ModeloLayOut {

    /**
     * Escribe en el archivo el comprobante pasado como parametro, el nombre del
     * archivo sera: "COMP_NOMINA_YYYYMM.txt"
     *
     * @param comprobante
     * @param fechaUltimoDia
     * @return
     */
    public boolean escribeComprobante(Comprobante comprobante, Date fechaUltimoDia) {
        boolean valido = false;
        DecimalFormat formatoImporte = new DecimalFormat("0.00");
        DecimalFormat formatoEntero = new DecimalFormat("0");
        SimpleDateFormat formatoFecha10 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatoFechaComp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String linea = "";
        Writer writer = null;
        SimpleDateFormat formatNameFile = new SimpleDateFormat("yyyyMM");
        try {
            writer = new OutputStreamWriter(new FileOutputStream("C:\\gds\\source\\COMP_NOMINA_" + formatNameFile.format(fechaUltimoDia) + ".txt", true), "UTF-8");
            //se prosigue con el llenado de la linea 00 "COMPROBANTE"
            linea = "00|";
            linea += comprobante.getEmisor().getRfc() + "|";
            linea += comprobante.getEmisor().getNombre() + "|";
            linea += comprobante.getEmisor().getRegimenFiscal().get(0).getRegimen() + "|";
            linea += comprobante.getReceptor().getRfc() + "|";
            linea += comprobante.getReceptor().getNombre() + "|";
            linea += comprobante.getSerie() + "|";
            linea += "|";//folio
            linea += formatoFechaComp.format(comprobante.getFecha().toGregorianCalendar().getTime()) + "|";//fecha
            linea += formatoImporte.format(comprobante.getSubTotal()) + "|";
            linea += "|";//Descuento
            linea += formatoImporte.format(comprobante.getTotal()) + "|";
            linea += comprobante.getLugarExpedicion() + "|";
            linea += comprobante.getTipoDeComprobante() + "|";
            linea += comprobante.getMetodoDePago() + "|";
            linea += comprobante.getMoneda() + "|";
            linea += comprobante.getFormaDePago();
            writer.write(linea + "\r\n");

            //comenzamos con la escritura de la linea 01 "CONCEPTOS"
            linea = "01|";
            linea += formatoEntero.format(comprobante.getConceptos().getConcepto().get(0).getCantidad()) + "|";
            linea += comprobante.getConceptos().getConcepto().get(0).getDescripcion() + "|";
            linea += comprobante.getConceptos().getConcepto().get(0).getUnidad() + "|";
            linea += formatoImporte.format(comprobante.getConceptos().getConcepto().get(0).getValorUnitario()) + "|";
            linea += formatoImporte.format(comprobante.getConceptos().getConcepto().get(0).getImporte());
            writer.write(linea + "\r\n");

            //comenzamos con la escritura de la linea 02 "COMPLEMENTO DE NOMINA"
            Nomina nomina;
            if (comprobante.getComplemento().getAny().get(0) instanceof Nomina) {
                nomina = (Nomina) comprobante.getComplemento().getAny().get(0);
                linea = "02|";
                linea += nomina.getTipoNomina() + "|";
                linea += formatoFecha10.format(nomina.getFechaPago().toGregorianCalendar().getTime()) + "|";
                linea += formatoFecha10.format(nomina.getFechaInicialPago().toGregorianCalendar().getTime()) + "|";
                linea += formatoFecha10.format(nomina.getFechaFinalPago().toGregorianCalendar().getTime()) + "|";
                linea += formatoEntero.format(nomina.getNumDiasPagados()) + "|";
                linea += "|";//Total de percepciones
                linea += "|";//Total de deducciones
                linea += formatoImporte.format(nomina.getTotalOtrosPagos());//Total Otros Pagos
                writer.write(linea + "\r\n");

                //comenzamos con la escritura de la linea 03 "EMISOR"
                linea = "03|";
                linea += "|";//CURP
                linea += "|";//Registro patronal
                linea += nomina.getEmisor().getRfcPatronOrigen();
                writer.write(linea + "\r\n");

                //comenzamos con la escritura de la linea 05 "RECEPTOR"
                linea = "05|";
                linea += nomina.getReceptor().getCurp() + "|";
                linea += "|";//Número de Seguridad Social
                linea += "|";//Fecha de Inicio Relación Laboral
                linea += "|";//Antigüedad
                linea += nomina.getReceptor().getTipoContrato() + "|";
                linea += "|";//Sindicalizado
                linea += "|";//Tipo de Jornada
                linea += nomina.getReceptor().getTipoRegimen() + "|";
                if (nomina.getReceptor().getNumEmpleado().length() <= 15) {
                    linea += nomina.getReceptor().getNumEmpleado() + "|";
                } else {
                    linea += nomina.getReceptor().getNumEmpleado().substring(0, 14) + "|";
                }
                if (nomina.getReceptor().getDepartamento().length() <= 100) {
                    linea += nomina.getReceptor().getDepartamento() + "|";
                } else {
                    linea += nomina.getReceptor().getDepartamento().substring(0, 99) + "|";
                }
                if (nomina.getReceptor().getPuesto().length() <= 100) {
                    linea += nomina.getReceptor().getPuesto() + "|";
                } else {
                    linea += nomina.getReceptor().getPuesto().substring(0, 99) + "|";
                }
                linea += "|";//Riesgo de Puesto
                linea += nomina.getReceptor().getPeriodicidadPago() + "|";
                linea += ObtenInfoBD.obtenBanco(nomina.getReceptor().getBanco()) + "|";
                linea += ObtenInfoBD.obtenCuenta(nomina.getReceptor().getBanco()) + "|";
                linea += "|";//Salario Base Cot. Apor.
                linea += "|";//Salario Diario Integrado
                linea += nomina.getReceptor().getClaveEntFed();
                writer.write(linea + "\r\n");

                //comenzamos con la escritura de la linea 15 "OTROS PAGOS"
                for (Nomina.OtrosPagos.OtroPago otP : nomina.getOtrosPagos().getOtroPago()) {
                    linea = "15|";
                    linea += otP.getTipoOtroPago() + "|";
                    linea += otP.getClave() + "|";
                    linea += otP.getConcepto() + "|";
                    linea += formatoImporte.format(otP.getImporte());
                    writer.write(linea + "\r\n");
                }
            }
            valido = true;
        } catch (Exception e) {

        } finally {
            try {
                writer.close();
//                writer_inicio.close();
            } catch (Exception e) {
                System.out.println("GneraEdoCta:" + e.getMessage());
            }
        }
        return valido;
    }

    static public List<List> leerErroresCODES() throws FileNotFoundException, IOException {
        List<List> listaRFC = new ArrayList();
        List<String> listaRFC1 = new ArrayList();
        List<String> listaRFC2 = new ArrayList();
        FileInputStream file = new FileInputStream(new File("C:\\gds\\erroresCODES.xls"));
        HSSFWorkbook workbook = new HSSFWorkbook(file);
        HSSFSheet sheet = workbook.getSheetAt(0);
        System.out.println("sheet.getPhysicalNumberOfRows():" + sheet.getPhysicalNumberOfRows());
        for (int i = 2; i < sheet.getPhysicalNumberOfRows(); i++) {
            Cell celda = sheet.getRow(i).getCell(5, Row.CREATE_NULL_AS_BLANK);
            Cell celda2 = sheet.getRow(i).getCell(6, Row.CREATE_NULL_AS_BLANK);
            String valorCelda = celda.getStringCellValue();
            String valorCelda2 = celda2.getStringCellValue();
            //System.out.println("RFC " + valorCelda);
            listaRFC1.add(valorCelda);
            listaRFC2.add(valorCelda2);
            listaRFC.add(listaRFC1);
            listaRFC.add(listaRFC2);
//            Cell celda = row.getCell(i, Row.CREATE_NULL_AS_BLANK);
//            String algo = celda.getStringCellValue();
        }

        workbook.close();
        return listaRFC;
    }

    static public Set<String> leerErroresCODES_CURP() throws FileNotFoundException, IOException {
        Set<String> listaRFC = new TreeSet<>();
        ObtenInfoBD oi = new ObtenInfoBD();
        FileInputStream file = new FileInputStream(new File("C:\\gds\\curp_ok.xls"));
        HSSFWorkbook workbook = new HSSFWorkbook(file);
        HSSFSheet sheet = workbook.getSheetAt(0);
        for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
            Cell celda = sheet.getRow(i).getCell(0, Row.CREATE_NULL_AS_BLANK);
            String curp = celda.getStringCellValue();
            String rfc = oi.obtenRFC_fromCURP(curp);
            if (!rfc.isEmpty()) {
                listaRFC.add(rfc);
            }else{
                System.out.println("El RFC obtenido por la CURP:" + curp + " es vacio.");
            }
        }

        workbook.close();
        return listaRFC;
    }

    synchronized static public void obtieneCURPErroresCODES() throws IOException {
        FileInputStream file = null;
        try {
            file = new FileInputStream(new File("C:\\gds\\curps.xls"));
            HSSFWorkbook workbook = new HSSFWorkbook(file);
            HSSFSheet sheet = workbook.getSheetAt(0);
            Writer writer = null;
            writer = new OutputStreamWriter(new FileOutputStream("C:\\gds\\curps_out.txt"), "UTF-8");
            for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
                System.out.println("Movimiento=" + i);
                Cell celda = sheet.getRow(i).getCell(0, Row.CREATE_NULL_AS_BLANK);
                String valorCelda = celda.getStringCellValue();
                if (valorCelda.length() > 18) {
                    int index = valorCelda.indexOf("'");
                    String sub = valorCelda.substring(index + 1, index + 19);
                    sub += "\r\n";
                    writer.write(sub);
                } else {
                    System.out.println("No se puede obtener la CURP de la cadena:" + valorCelda);
                }
            }
            writer.close();
            workbook.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ModeloLayOut.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                file.close();
            } catch (IOException ex) {
                Logger.getLogger(ModeloLayOut.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public boolean escribeComprobanteHilo(Comprobante comprobante, Date fechaUltimoDia, Integer idHilo) {
        boolean valido = false;
        DecimalFormat formatoImporte = new DecimalFormat("0.00");
        DecimalFormat formatoEntero = new DecimalFormat("0");
        SimpleDateFormat formatoFecha10 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatoFechaComp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String linea = "";
        Writer writer = null;
        SimpleDateFormat formatNameFile = new SimpleDateFormat("yyyyMM");
        try {
            writer = new OutputStreamWriter(new FileOutputStream("C:\\gds\\source\\COMP_NOMINA_" + formatNameFile.format(fechaUltimoDia) + "(H" + idHilo + ").txt", true), "UTF-8");
            //se prosigue con el llenado de la linea 00 "COMPROBANTE"
            linea = "00|";
            linea += comprobante.getEmisor().getRfc() + "|";
            linea += comprobante.getEmisor().getNombre() + "|";
            linea += comprobante.getEmisor().getRegimenFiscal().get(0).getRegimen() + "|";
            linea += comprobante.getReceptor().getRfc() + "|";
            linea += comprobante.getReceptor().getNombre() + "|";
            linea += comprobante.getSerie() + "|";
            linea += "|";//folio
            linea += formatoFechaComp.format(comprobante.getFecha().toGregorianCalendar().getTime()) + "|";//fecha
            linea += formatoImporte.format(comprobante.getSubTotal()) + "|";
            linea += "|";//Descuento
            linea += formatoImporte.format(comprobante.getTotal()) + "|";
            linea += comprobante.getLugarExpedicion() + "|";
            linea += comprobante.getTipoDeComprobante() + "|";
            linea += comprobante.getMetodoDePago() + "|";
            linea += comprobante.getMoneda() + "|";
            linea += comprobante.getFormaDePago();
            writer.write(linea + "\r\n");

            //comenzamos con la escritura de la linea 01 "CONCEPTOS"
            linea = "01|";
            linea += formatoEntero.format(comprobante.getConceptos().getConcepto().get(0).getCantidad()) + "|";
            linea += comprobante.getConceptos().getConcepto().get(0).getDescripcion() + "|";
            linea += comprobante.getConceptos().getConcepto().get(0).getUnidad() + "|";
            linea += formatoImporte.format(comprobante.getConceptos().getConcepto().get(0).getValorUnitario()) + "|";
            linea += formatoImporte.format(comprobante.getConceptos().getConcepto().get(0).getImporte());
            writer.write(linea + "\r\n");

            //comenzamos con la escritura de la linea 02 "COMPLEMENTO DE NOMINA"
            Nomina nomina;
            if (comprobante.getComplemento().getAny().get(0) instanceof Nomina) {
                nomina = (Nomina) comprobante.getComplemento().getAny().get(0);
                linea = "02|";
                linea += nomina.getTipoNomina() + "|";
                linea += formatoFecha10.format(nomina.getFechaPago().toGregorianCalendar().getTime()) + "|";
                linea += formatoFecha10.format(nomina.getFechaInicialPago().toGregorianCalendar().getTime()) + "|";
                linea += formatoFecha10.format(nomina.getFechaFinalPago().toGregorianCalendar().getTime()) + "|";
                linea += formatoEntero.format(nomina.getNumDiasPagados()) + "|";
                linea += "|";//Total de percepciones
                linea += "|";//Total de deducciones
                linea += formatoImporte.format(nomina.getTotalOtrosPagos());//Total Otros Pagos
                writer.write(linea + "\r\n");

                //comenzamos con la escritura de la linea 03 "EMISOR"
                linea = "03|";
                linea += "|";//CURP
                linea += "|";//Registro patronal
                linea += nomina.getEmisor().getRfcPatronOrigen();
                writer.write(linea + "\r\n");

                //comenzamos con la escritura de la linea 05 "RECEPTOR"
                linea = "05|";
                linea += nomina.getReceptor().getCurp() + "|";
                linea += "|";//Número de Seguridad Social
                linea += "|";//Fecha de Inicio Relación Laboral
                linea += "|";//Antigüedad
                linea += nomina.getReceptor().getTipoContrato() + "|";
                linea += "|";//Sindicalizado
                linea += "|";//Tipo de Jornada
                linea += nomina.getReceptor().getTipoRegimen() + "|";
                if (nomina.getReceptor().getNumEmpleado().length() <= 15) {
                    linea += nomina.getReceptor().getNumEmpleado() + "|";
                } else {
                    linea += nomina.getReceptor().getNumEmpleado().substring(0, 14) + "|";
                }
                if (nomina.getReceptor().getDepartamento().length() <= 100) {
                    linea += nomina.getReceptor().getDepartamento() + "|";
                } else {
                    linea += nomina.getReceptor().getDepartamento().substring(0, 99) + "|";
                }
                if (nomina.getReceptor().getPuesto().length() <= 100) {
                    linea += nomina.getReceptor().getPuesto() + "|";
                } else {
                    linea += nomina.getReceptor().getPuesto().substring(0, 99) + "|";
                }
                linea += "|";//Riesgo de Puesto
                linea += nomina.getReceptor().getPeriodicidadPago() + "|";
                linea += ObtenInfoBD.obtenBanco(nomina.getReceptor().getBanco()) + "|";
                linea += ObtenInfoBD.obtenCuenta(nomina.getReceptor().getBanco()) + "|";
                linea += "|";//Salario Base Cot. Apor.
                linea += "|";//Salario Diario Integrado
                linea += nomina.getReceptor().getClaveEntFed();
                writer.write(linea + "\r\n");

                //comenzamos con la escritura de la linea 15 "OTROS PAGOS"
                for (Nomina.OtrosPagos.OtroPago otP : nomina.getOtrosPagos().getOtroPago()) {
                    linea = "15|";
                    linea += otP.getTipoOtroPago() + "|";
                    linea += otP.getClave() + "|";
                    linea += otP.getConcepto() + "|";
                    linea += formatoImporte.format(otP.getImporte());
                    writer.write(linea + "\r\n");
                }
            }
            valido = true;
        } catch (Exception e) {

        } finally {
            try {
                writer.close();
//                writer_inicio.close();
            } catch (Exception e) {
                System.out.println("GneraEdoCta:" + e.getMessage());
            }
        }
        return valido;
    }

}
