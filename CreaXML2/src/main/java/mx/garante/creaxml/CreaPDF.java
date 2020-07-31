package mx.garante.creaxml;

import mx.garante.xmls.CMoneda;
import mx.garante.xmls.Comprobante;
import mx.garante.xmls.Nomina;
import mx.garante.xmls.TimbreFiscalDigital;
import mx.garante.creaxml.Models.Contrato;
import mx.garante.creaxml.Models.EdoCta;
import mx.garante.creaxml.Models.Movimiento;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.garante.creaxml.Helpers.Fecha;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class CreaPDF {
    
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private static SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");

    public static boolean edoCuenta(Contrato contrato, Double saldoAnterior, Double saldoFinal, Double restitucion, Double liquidacion, Double honorarios, Double ivaHonorarios, Double totalAbono, String fechaEdoCta, TimbreFiscalDigital tfd, String salida, String cadenaOriginal, String rfcEmisor) {
        String reportes = System.getProperty("user.dir") + "/src/main/resources/Reportes/";
        String imagen = System.getProperty("user.dir") + "/src/main/resources/img/";
        File file = new File(reportes + "EdoCuenta.jrxml");
        boolean res = false;

        try {
            JasperDesign jasperDesign = JRXmlLoader.load(file);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("foliofiscal", tfd.getUUID());
            parameters.put("noserie", tfd.getNoCertificadoSAT());
            parameters.put("horacertificado", tfd.getFechaTimbrado().toString());
            parameters.put("metodopago", "Transferencia Electrónica de Fondos");
            parameters.put("selloCFDI", tfd.getSelloCFD());
            parameters.put("selloSAT", tfd.getSelloSAT());
            parameters.put("cadenaOriginal", cadenaOriginal);
            parameters.put("periodo", "PERIODO del " + fechaEdoCta);
            parameters.put("fideicomitente", contrato.getNombre_cliente());
            parameters.put("direccion", contrato.getDomicilio_fiscal());
            parameters.put("rfc", contrato.getRFC());
            parameters.put("moneda", CMoneda.MXN.toString());
            parameters.put("contrato", contrato.getClave_contrato());
            parameters.put("saldoAnterior", saldoAnterior);
            parameters.put("aportacionTotal", totalAbono);
            parameters.put("restitucion", restitucion);
            parameters.put("liquidacion", liquidacion);
            parameters.put("honorarios", honorarios);
            parameters.put("iva", ivaHonorarios);
            parameters.put("saldoFinal", saldoFinal);

            parameters.put("imagen1", imagen + "/imagen1.jpg");
            parameters.put("imagen2", imagen + "/imagen2.jpg");

            parameters.put("qr", generaQr(tfd.getUUID(), rfcEmisor, contrato.getRFC(), totalAbono, tfd.getSelloCFD()));

            List<EdoCta> detalle = contrato.getEdoCtas();

            JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(detalle);
            parameters.put("detalle", itemsJRBean);

            byte[] bytes = JasperRunManager.runReportToPdf(jasperReport, parameters, new JREmptyDataSource());
            Path path = Paths.get(salida + ".pdf");
            Files.write(path, bytes);
            res = true;
        } catch (JRException | IOException ex) {
            Logger.getLogger(CreaXML.class.getName()).log(Level.SEVERE, null, ex);
        }

        return res;
    }
    
    public static boolean nomina(Comprobante comprobante, TimbreFiscalDigital timbre, Comprobante.Receptor receptor, Comprobante.Emisor emisor, Nomina.Emisor emisorNom, Nomina.Receptor receptorNom, Nomina nomina, List<Movimiento> movimientos, Comprobante.Conceptos.Concepto concepto1, List<Nomina.OtrosPagos.OtroPago> detalle1, String salida, String cadenaOriginal, String nombreCliente) {

        List<Nomina.OtrosPagos.OtroPago> detalle = new ArrayList<>();

        for (Nomina.OtrosPagos.OtroPago op : detalle1) {
            detalle.add(op);
        }

        String reportes = System.getProperty("user.dir") + "/src/main/resources/Reportes/";
        String imagen = System.getProperty("user.dir") + "/src/main/resources/img/";
        File file = new File(reportes + "ReporteNomina.jrxml");
        boolean res = false;
        try {
            JasperDesign jasperDesign = JRXmlLoader.load(file);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("ClaveFid", movimientos.get(0).getClave_contrato());
            parameters.put("RegPatronal", " ");
            parameters.put("NumFolio", " ");
            parameters.put("NombrePatOrg", nombreCliente);
            parameters.put("RFCEmisor", emisor.getRfc());
            parameters.put("Fecha", comprobante.getFecha().toString());
            parameters.put("RFCPatOrg", emisorNom.getRfcPatronOrigen());
            parameters.put("NombreRec", receptor.getNombre());
            parameters.put("NSSRec", " ");
            parameters.put("RFCRec", receptor.getRfc());
            parameters.put("NSSRec", " ");
            parameters.put("CURPRec", movimientos.get(0).getCurp());
            parameters.put("NumEmpRec", movimientos.get(0).getClave_empleado());
            parameters.put("RegEmp", receptorNom.getTipoRegimen());
            parameters.put("PerPagEmp", receptorNom.getPeriodicidadPago());
            parameters.put("EntFedRec", receptorNom.getClaveEntFed().toString());
            parameters.put("JorRec", " ");
            parameters.put("IniRelLab", " ");
            parameters.put("AntRec", " ");
            parameters.put("TContRec", receptorNom.getTipoContrato());
            parameters.put("DPagRec", nomina.getNumDiasPagados().toString());
            parameters.put("FecPag", nomina.getFechaPago().toString());
            parameters.put("FecIniPag", nomina.getFechaInicialPago().toString());
            parameters.put("FecFinPag", nomina.getFechaFinalPago().toString());

            parameters.put("STotRec", comprobante.getSubTotal());
            parameters.put("DescRec", new BigDecimal(0));
            parameters.put("TotRec", comprobante.getTotal());

            parameters.put("ConcCant", concepto1.getCantidad().toString());
            parameters.put("ConcDesc", concepto1.getDescripcion());
            parameters.put("ConcUni", concepto1.getClaveUnidad());
            parameters.put("ConcVUni", concepto1.getValorUnitario());
            parameters.put("ConcImp", concepto1.getImporte());
            parameters.put("NominaT", nomina.getTipoNomina().toString());
            parameters.put("NominaLFE", comprobante.getLugarExpedicion());
            parameters.put("NominaFP", comprobante.getFormaPago());
            parameters.put("NominaMP", comprobante.getMetodoPago().toString());
            parameters.put("TotalPerc", new BigDecimal(0));
            parameters.put("TotalDed", new BigDecimal(0));
            parameters.put("TotalOP", nomina.getTotalOtrosPagos());
            parameters.put("Moneda", comprobante.getMoneda().toString());

            parameters.put("FolioFisc", timbre.getUUID());
            parameters.put("FHCert", dateFormat.format(Fecha.getXMLGregorianCalendar(timbre.getFechaTimbrado())) );
            parameters.put("NSCSAT", timbre.getNoCertificadoSAT());
            parameters.put("NSCCSD", comprobante.getNoCertificado());
            parameters.put("RegFisc", "General de Ley Personas Morales");
            parameters.put("SelloDigCFDI", timbre.getSelloCFD());
            parameters.put("SelloDigSAT", timbre.getSelloSAT());
            parameters.put("CadCertSAT", cadenaOriginal);
            
            parameters.put("imagen", imagen + "/imagen1.jpg");

            parameters.put("qr", generaQr(timbre.getUUID(), emisor.getRfc(), receptor.getRfc(), comprobante.getTotal().doubleValue(), timbre.getSelloCFD()));

            JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(detalle);
            parameters.put("detalle", itemsJRBean);

            byte[] bytes = JasperRunManager.runReportToPdf(jasperReport, parameters, new JREmptyDataSource());
            Path path = Paths.get(salida + ".pdf");
            Files.write(path, bytes);
            res = true;
        } catch (JRException | IOException ex) {
            Logger.getLogger(CreaXML.class.getName()).log(Level.SEVERE, null, ex);
        }

        return res;
    }

    public static String generaQr(String uuid, String rfcEmisor, String rfcReceptor, Double total, String caracteres) {

        String qr = "https://verificacfdi.facturaelectronica.sat.gob.mx/default.aspx";

        StringTokenizer tokenizer = new StringTokenizer(new BigDecimal(total).setScale(2, RoundingMode.HALF_UP).toString(), ".");
        int entero = 0;
        int decimal = 0;
        boolean enteros = true;
        while (tokenizer.hasMoreTokens()) {
            if (enteros) {
                entero = Integer.parseInt(tokenizer.nextToken());
                enteros = false;
            } else {
                decimal = Integer.parseInt(tokenizer.nextToken());
            }
        }

        String decimalCeros = decimal + "";
        for (int i = decimalCeros.length(); i < 6; i++) {
            decimalCeros += "0";
        }

        qr += "?id=" + uuid;
        qr += "&re=" + rfcEmisor;
        qr += "&rr=" + rfcReceptor;
        qr += "&tt=" + String.format("%018d", entero) + "." + decimalCeros;
        qr += "&fe=" + caracteres.substring(caracteres.trim().length() - 8, caracteres.trim().length());

        return qr;
    }
    
}
