package mx.garante.creaxml;

import com.sun.xml.internal.bind.marshaller.NamespacePrefixMapper;
import mx.garante.xmls.v2.CEstado;
import mx.garante.xmls.v2.CMetodoPago;
import mx.garante.xmls.v2.CMoneda;
import mx.garante.xmls.v2.CTipoDeComprobante;
import mx.garante.xmls.v2.CTipoFactor;
import mx.garante.xmls.v2.CTipoNomina;
import mx.garante.xmls.v2.CUsoCFDI;
import mx.garante.xmls.v2.Comprobante;
import mx.garante.xmls.v2.EstadoDeCuenta;
import mx.garante.xmls.v2.Nomina;
import mx.garante.xmls.v2.TimbreFiscalDigital;
import mx.garante.creaxml.DAOs.CertificadosDAO;
import mx.garante.creaxml.DAOs.CompEdoCtaDAO;
import mx.garante.creaxml.DAOs.CompNominaDAO;
import mx.garante.creaxml.DAOs.ContratosDAO;
import mx.garante.creaxml.DAOs.GenericEdoCtaDAO;
import mx.garante.creaxml.DAOs.MovimientosDAO;
import mx.garante.creaxml.Helpers.Empaquetar;
import mx.garante.creaxml.Helpers.Encriptar;
import mx.garante.creaxml.Helpers.Fecha;
import mx.garante.creaxml.Models.Certificado;
import mx.garante.creaxml.Models.CompEdoCta;
import mx.garante.creaxml.Models.CompNomina;
import mx.garante.creaxml.Models.Contrato;
import mx.garante.creaxml.Models.EdoCta;
import mx.garante.creaxml.Models.Movimiento;
import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;
import java.io.StringReader;
import java.io.Reader;
import java.math.BigInteger;
import java.util.GregorianCalendar;
import javax.xml.XMLConstants;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import mx.garante.creaxml.Helpers.ErroresXML;
import mx.garante.creaxml.Helpers.ReportesExcel;
import org.datacontract.schemas._2004._07.tes_tfd_v33.RespuestaTFD33;
import org.datacontract.schemas._2004._07.tes_tfd_v33.RespuestaValidacionRFC;
import org.datacontract.schemas._2004._07.tes_tfd_v33.Timbre33;
import org.tempuri.IWSCFDI33;
import org.tempuri.WSCFDI33;

public class CreaXML {

    private static final double iva = 0.16;

    private static final String rfc_prov_cert = "FLI081010EK2";
    private static final String csds_path = "Certificados";

    // Configuration PSC
//    private static final String user = "PSC181023RJ7";
//    private static final String password = "HzrG4KX%";
//
//    private static final String rfc_emisor = "PSC181023RJ7";
//    private static final String razon_social_emisor = "Petramare Servicios Consolidados, S.A. de C.V. SOFOM E.N.R.";
//    private static final String cp_emisor = "53500";
//    private static final String regimen_emisor = "601";
//
//    private static final String cer_number = "00001000000502158019";
//    private static final String cer_filename = "CSD_PETRAMARE_SERVICIOS.cer";
//    private static final String key_filename = "CSD_PETRAMARE_SERVICIOS.key";
//    private static final String cer_password = "petramare6605";
    // Configuration GDS
    private static final String user = "GDS160406V45";
    private static final String password = "HzrG4KXEe%";
    private static final String rfc_emisor = "GDS160406V45";
    private static final String razon_social_emisor = "Garante Desarrollo y Salud, S.A. de C.V. SOFOM E.N.R.";
    private static final String cp_emisor = "06500";
    private static final String regimen_emisor = "601";
    private static final String cer_number = "00001000000503921958";
    private static final String cer_filename = "CSD_MATRIZ_GDS160406V45_20200504_135051s.cer";
    private static final String key_filename = "CSD_MATRIZ_GDS160406V45_20200504_135051.key";
    private static final String cer_password = "GDS160406";
    private static final String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo",
        "Junio", "Julio", "Agosto", "Septiembre",
        "Octubre", "Noviembre", "Diciembre"};

    private static final String directorioLocal = System.getProperty("user.dir");
    private static final StreamSource formatoCadenaOriginal = new StreamSource(new File(directorioLocal + "/src/main/resources/configuration/cadenaoriginal_3_3.xslt"));
    private static final StreamSource formatoCadenaOriginalTimbre = new StreamSource(new File(directorioLocal + "/src/main/resources/configuration/cadenaoriginal_TFD_1_1.xslt"));

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd'T'HH:mm:ss");
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat format2 = new SimpleDateFormat("yyyyMMdd");
    private static final SimpleDateFormat format3 = new SimpleDateFormat("yyyyMM");
    private static final SimpleDateFormat format4 = new SimpleDateFormat("yyyy-MM");

    private static final NameSpaceMapper SPACE_MAPPER = new NameSpaceMapper();
    private static final String LOCATION = "http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv33.xsd https://www.garante.mx/cfd/addenda/edocta https://www.garante.mx/EdoctaV1.xsd";
    private static final String LOCATIONFinal = "http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv33.xsd http://www.sat.gob.mx/TimbreFiscalDigital http://www.sat.gob.mx/sitio_internet/cfd/TimbreFiscalDigital/TimbreFiscalDigitalv11.xsd https://www.garante.mx/cfd/addenda/edocta https://www.garante.mx/EdoctaV1.xsd";
    private static final String LOCATION2 = "http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv33.xsd http://www.sat.gob.mx/nomina12 http://www.sat.gob.mx/sitio_internet/cfd/nomina/nomina12.xsd";
    private static final String LOCATION2Final = "http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv33.xsd http://www.sat.gob.mx/TimbreFiscalDigital http://www.sat.gob.mx/sitio_internet/cfd/TimbreFiscalDigital/TimbreFiscalDigitalv11.xsd http://www.sat.gob.mx/nomina12 http://www.sat.gob.mx/sitio_internet/cfd/nomina/nomina12.xsd";

    public static void main(String[] args) {
        if (isCSDFiles()) {
            System.out.println("== CSD Encontrados ==");
        } else {
            System.out.println("== CSD NO Encontrados ==");
        }
        menu();
    }

    private static boolean isCSDFiles() {
        try {
            File cer = new File(directorioLocal + "/" + csds_path + "/" + cer_filename);
            File key = new File(directorioLocal + "/" + csds_path + "/" + key_filename);
            return cer.exists() && key.exists();
        } catch (Exception e) {
            return false;
        }
    }

    private static void menu() {
        boolean res = true;
        while (res) {
            System.out.println("*************** MENU ***************");
            System.out.println("1 .- Proceso fin de mes");
            System.out.println("2 .- Generar Estados de cuenta");
            System.out.println("3 .- Generar Nomina");
            System.out.println("4 .- Generar certificado");
            System.out.println("5 .- Consultar por referencia");
            System.out.println("6 .- Consulta RFC");
            System.out.println("7 .- Consulta RFC por mes");
            System.out.println("8 .- Salir");
            System.out.println("10 .- Generar XML y PDF");
            System.out.print("Escribe el numero de tu seleccion -> ");

            Scanner entradaEscaner = new Scanner(System.in);
            String entradaTeclado = entradaEscaner.next();

            entradaEscaner = new Scanner(System.in);
            switch (entradaTeclado) {
                case "1": {
                    System.out.print("Ingresa la fecha en este formato yyyy-MM en caso contrario dejar vacio -> ");
                    String fechaConsola = entradaEscaner.nextLine();

                    Date fechaHoy = null;
                    if (fechaConsola.equals("")) {
                        fechaHoy = new Date();
                        System.out.println("Fecha (vacia)" + fechaHoy);
                    } else {
                        fechaHoy = Fecha.creaDate(fechaConsola);
                        System.out.println("Fecha (consola): " + fechaHoy);
                    }

                    generaEdoCuenta(fechaHoy);
                    System.out.println("=============================================================");
                    System.out.println("=============================================================");
                    System.out.println("====NÓMINA==============NÓMINA==============NÓMINA===========");
                    System.out.println("=============================================================");
                    System.out.println("=============================================================");
                    generaNomina(fechaHoy);
                    break;
                }
                case "2": {
                    System.out.print("Ingresa la fecha en este formato yyyy-MM en caso contrario dejar vacio -> ");
                    String fechaConsola = entradaEscaner.nextLine();

                    Date fechaHoy = null;
                    if (fechaConsola.equals("")) {
                        fechaHoy = new Date();
                        System.out.println("Fecha (vacia)" + fechaHoy);
                    } else {
                        fechaHoy = Fecha.creaDate(fechaConsola);
                        System.out.println("Fecha (consola): " + fechaHoy);
                    }

                    generaEdoCuenta(fechaHoy);
                    break;
                }
                case "3": {
                    System.out.print("Ingresa la fecha en este formato yyyy-MM en caso contrario dejar vacio -> ");
                    String fechaConsola = entradaEscaner.nextLine();

                    Date fechaHoy = null;
                    if (fechaConsola.equals("")) {
                        fechaHoy = new Date();
                    } else {
                        fechaHoy = Fecha.creaDate(fechaConsola);
                    }

                    generaNomina(fechaHoy);
                    break;
                }
                case "4":
                    generaCertificado();
                    break;
                case "5":
                    consultaPorReferencia();
                    break;
                case "6":
                    consultaRFC();
                    break;
                case "7":
                    System.out.print("Ingresa la fecha en este formato yyyy-MM en caso contrario dejar vacio -> ");
                    String fechaConsola = entradaEscaner.nextLine();
                    Date fechaHoy = null;
                    if (fechaConsola.equals("")) {
                        fechaHoy = new Date();
                    } else {
                        fechaHoy = Fecha.creaDate(fechaConsola);
                    }
                    consultaRFCMES(fechaHoy);
                    break;
                case "8":
                    res = false;
                    System.out.println("Fin del proceso");
                    break;
                case "10":
                    System.out.print("Ingresa la fecha en este formato yyyy-MM en caso contrario dejar vacio -> ");
                    fechaConsola = entradaEscaner.nextLine();

                    fechaHoy = null;
                    if (fechaConsola.equals("")) {
                        fechaHoy = new Date();
                    } else {
                        fechaHoy = Fecha.creaDate(fechaConsola);
                    }
                    Regenerarfiles(fechaHoy);
                    break;
                default:
                    System.out.println("Opcion no valida");
                    break;
            }

        }
    }

    private static void consultaPorReferencia() {
        System.out.print("Ingresa la referencia formato (rfc+yyyyMM) o (contrato+yyyyMM) -> ");
        Scanner entradaEscaner = new Scanner(System.in);
        String referencia = entradaEscaner.nextLine();
        IWSCFDI33 servicio = new WSCFDI33().getSoapHttpEndpointHttps();
        RespuestaTFD33 rtfd = servicio.consultarTimbrePorReferencia(user, password, referencia.trim());
        if (rtfd.isOperacionExitosa()) {
            System.out.println(rtfd.getXMLResultado().getValue());
        } else {
            System.out.println("CodigoRespuesta: " + rtfd.getCodigoRespuesta().getValue());
            System.out.println("MensajeError: " + rtfd.getMensajeError().getValue());
        }
    }

    private static void consultaRFC() {
        System.out.print("Ingresa RFC a buscar -> ");
        Scanner entradaEscaner = new Scanner(System.in);
        String rfc = entradaEscaner.nextLine();
        IWSCFDI33 servicio = new WSCFDI33().getSoapHttpEndpointHttps();
        RespuestaValidacionRFC respuestaValidacionRFC = servicio.validarRFC(user, password, rfc.trim());

        System.out.println("Localizado: " + respuestaValidacionRFC.isRFCLocalizado());
        System.out.println("Cancelado: " + respuestaValidacionRFC.isCancelado());
        System.out.println("Subcontratacion: " + respuestaValidacionRFC.isSubcontratacion());
        System.out.println("Unidad SNCF: " + respuestaValidacionRFC.isUnidadSNCF());
    }

    private static void generaCertificado() {
        try {
            System.out.print("Ingresa el nombre del certificado, debe ser el mismo que el numero de certificado -> ");
            Scanner entradaEscaner = new Scanner(System.in);
            String nCertificado = entradaEscaner.nextLine();
            Certificado certificado = new Certificado();
            certificado.setCertificado(Encriptar.base64File(directorioLocal + "/Certificados/" + nCertificado + ".cer"));
            certificado.setnCertificado(nCertificado);
            Console console = System.console();
            char[] password = console.readPassword("Escribe la contraseña -> ");
            certificado.setPassword(new String(password));
            CertificadosDAO certificadosDAO = new CertificadosDAO();
            if (certificadosDAO.insert(certificado)) {
                System.out.println("Se creo nuevo certificado");
            } else {
                System.out.println("Error al crear certificado");
            }
        } catch (Exception e) {
            System.out.println("Error al encontrar certificado");
        }
    }

    public static void consultaRFCMES(Date fechaHoy) {
        Calendar c1 = Fecha.getPrimerDiaDeMes(fechaHoy);
        Calendar c2 = Fecha.getUltimoDiaDeMes(fechaHoy);
        MovimientosDAO movimientosDAO = new MovimientosDAO();
        List<String> rfcs = movimientosDAO.getRFCMes(format.format(c1.getTime()), format.format(c2.getTime()));
        for (String rfc : rfcs) {
            IWSCFDI33 servicio = new WSCFDI33().getSoapHttpEndpointHttps();
            RespuestaValidacionRFC respuestaValidacionRFC = servicio.validarRFC(user, password, rfc);
            if (!respuestaValidacionRFC.isRFCLocalizado()) {
                System.out.println(rfc + " No localizado");
            } else {
                if (respuestaValidacionRFC.isCancelado()) {
                    System.out.println(rfc + " Cancelado por el sat");
                } else {
                    System.out.println(rfc + " Correcto");
                }
            }
        }
    }

    private static void generaEdoCuenta(Date fechaHoy) {
        int errorestimbrado = 0, errorespdf = 0, erroresxml = 0, totaledoscta = 0;
        String rfcActual = "";
        List<String> rfcErrores = new ArrayList<>();
        List<ErroresXML> lErrores = new ArrayList<>();
        ContratosDAO contratosDAO = new ContratosDAO();
        GenericEdoCtaDAO edoCtaDAO = new GenericEdoCtaDAO();
        CompEdoCtaDAO compEdoCtaDAO = new CompEdoCtaDAO();

        List<Contrato> contratos = contratosDAO.getAll();
        Integer totalTimbrados = compEdoCtaDAO.cuentaMes(format4.format(fechaHoy));

        Calendar c1 = Fecha.getPrimerDiaDeMes(fechaHoy);
        Calendar c2 = Fecha.getUltimoDiaDeMes(fechaHoy);

        totaledoscta = contratos.size();

        int cont = 1;

        for (Contrato contrato : contratos) {

            System.out.println(contrato.getClave_contrato() + " " + cont + " de " + contratos.size());
            cont++;

            rfcActual = contrato.getRFC();

            try {
                CompEdoCtaDAO ctaDAO = new CompEdoCtaDAO();
                CompEdoCta compEdoCta = ctaDAO.getBy(contrato.getClave_contrato(), format.format(c1.getTime()));

                if (compEdoCta == null) {
                    compEdoCta = new CompEdoCta();
                    compEdoCta.setFecha(new Date());
                    compEdoCta.setRfcProv(rfc_prov_cert);
                    Certificado certificado = new Certificado();
                    certificado.setIdCertificado(1);
                    certificado.setPassword(cer_password);
                    certificado.setnCertificado(cer_number);
                    certificado.setCertificado(Encriptar.base64File(directorioLocal + "/" + csds_path + "/" + cer_filename));
                    /*certificado.setnCertificado("fel_mx_2019");
                     certificado.setCertificado(Encriptar.base64File(directorioLocal + "/Certificados/fel_mx_2019.cer"));¨*/
                    compEdoCta.setCertificado(certificado);
                }

                List<EdoCta> edoCtas = edoCtaDAO.getEdoCta(contrato.getClave_contrato(), format.format(c1.getTime()), format.format(c2.getTime()));

                System.out.println("Estados de Cta: " + edoCtas.size());

                Comprobante comprobante = new Comprobante();
                comprobante.setVersion("3.3");
                //comprobante.setFecha(dateFormat.format(compEdoCta.getFecha()));

                GregorianCalendar cal = new GregorianCalendar();
                cal.setTime(compEdoCta.getFecha());

                try {
                    javax.xml.datatype.XMLGregorianCalendar fecha;
                    fecha = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal.get(Calendar.YEAR),
                            cal.get(Calendar.MONTH) + 1,
                            cal.get(Calendar.DAY_OF_MONTH),
                            cal.get(Calendar.HOUR_OF_DAY),
                            cal.get(Calendar.MINUTE),
                            cal.get(Calendar.SECOND),
                            DatatypeConstants.FIELD_UNDEFINED,
                            DatatypeConstants.FIELD_UNDEFINED);

                    comprobante.setFecha(fecha);

                } catch (DatatypeConfigurationException ex) {
                    Logger.getLogger(CreaXML.class.getName()).log(Level.SEVERE, null, ex);
                }

                System.out.println("No Certificado: " + cer_number);
                comprobante.setNoCertificado(cer_number);
                //comprobante.setNoCertificado(compEdoCta.getCertificado().getnCertificado());
                comprobante.setCertificado(compEdoCta.getCertificado().getCertificado().replace("?", ""));
                comprobante.setFormaPago("03");
                comprobante.setMetodoPago(CMetodoPago.PUE);

                Double totalIVA = 0.00;
                Double totalHonorario = 0.00;
                Double totalAbono = 0.00;
                Double totalOrdenLiq = 0.00;
                Double totalRestitucion = 0.00;
                Double saldoAnterior = 0.00;
                Double saldoFinal = 0.00;

                EstadoDeCuenta.Movimientos movs = new EstadoDeCuenta.Movimientos();
                List<EstadoDeCuenta.Movimientos.Movimiento> movss = new ArrayList<>();

                boolean primero = true;
                for (EdoCta edoCta : edoCtas) {
                    EstadoDeCuenta.Movimientos.Movimiento mov = new EstadoDeCuenta.Movimientos.Movimiento();
                    if (primero) {
                        if (edoCta.getConcepto().equals("APORTACION A PATRIMONIO")) {
                            saldoAnterior = edoCta.getSaldo() - edoCta.getAbono();
                        } else {
                            saldoAnterior = edoCta.getSaldo() + edoCta.getCargo();
                        }
                        primero = false;
                    }
                    saldoFinal = edoCta.getSaldo();
                    if (edoCta.getConcepto().equals("APORTACION A PATRIMONIO")) {
                        totalAbono += edoCta.getAbono();
                    }
                    if (edoCta.getConcepto().equals("HONORARIOS FIDUCIARIOS")) {
                        totalHonorario += edoCta.getCargo();
                    }
                    System.out.println("EDOCTA ABONO" + edoCta.getAbono());
                    if (edoCta.getConcepto().equals("RESTITUCION DE PATRIMONIO")) {
                        totalRestitucion += edoCta.getAbono();
                    }
                    if (edoCta.getConcepto().equals("ORDEN DE LIQUIDACION")) {
                        totalOrdenLiq += edoCta.getCargo();
                    }
                    mov.setCargo(new BigDecimal(edoCta.getCargo()).setScale(2, RoundingMode.HALF_UP));
                    mov.setAbono(new BigDecimal(edoCta.getAbono()).setScale(2, RoundingMode.HALF_UP));
                    mov.setSaldo(new BigDecimal(edoCta.getSaldo()).setScale(2, RoundingMode.HALF_UP));
                    mov.setConcepto(edoCta.getConcepto());
                    mov.setFecha(format.format(edoCta.getFecha()));
                    movss.add(mov);
                }
//        movs.setMovimiento(movss);
                movs.getMovimiento().clear();
                movs.getMovimiento().addAll(movss);

                totalIVA = totalHonorario * iva;

                if (totalAbono > 0) {
                    comprobante.setTotal(new BigDecimal(totalAbono).setScale(2, RoundingMode.HALF_UP));
                    Double subtotal = totalAbono - totalIVA;
                    comprobante.setSubTotal(new BigDecimal(subtotal.toString()).setScale(2, RoundingMode.HALF_UP));
                } else {
                    comprobante.setTotal(new BigDecimal(0.00));
                    comprobante.setSubTotal(new BigDecimal(0.00));
                }
                comprobante.setMoneda(CMoneda.MXN);
                comprobante.setTipoDeComprobante(CTipoDeComprobante.I);
                comprobante.setLugarExpedicion(cp_emisor);

                //Emisor
                Comprobante.Emisor emisor = new Comprobante.Emisor();
                emisor.setRfc(rfc_emisor);
                emisor.setNombre(razon_social_emisor);
                emisor.setRegimenFiscal(regimen_emisor);
                comprobante.setEmisor(emisor);

                //Receptor
                Comprobante.Receptor receptor = new Comprobante.Receptor();
                receptor.setRfc(contrato.getRFC());
                receptor.setNombre(contrato.getNombre_cliente());
                receptor.setUsoCFDI(CUsoCFDI.G_03);

                comprobante.setReceptor(receptor);

                //Conceptos
                List<Comprobante.Conceptos.Concepto> listaConceptos = new ArrayList<>();
                Comprobante.Conceptos conceptos = new Comprobante.Conceptos();

                Comprobante.Conceptos.Concepto concepto1 = new Comprobante.Conceptos.Concepto();
                concepto1.setClaveProdServ("84101503");
                concepto1.setCantidad(new BigDecimal(1));
                concepto1.setClaveUnidad("E48");
                concepto1.setUnidad("SERVICIO");
                concepto1.setDescripcion("GARANTIA FIDUCIARIA");
                concepto1.setValorUnitario(new BigDecimal(totalAbono - totalHonorario - totalIVA).setScale(2, RoundingMode.HALF_UP));
                concepto1.setImporte(new BigDecimal(totalAbono - totalHonorario - totalIVA).setScale(2, RoundingMode.HALF_UP));

                Comprobante.Conceptos.Concepto concepto2 = new Comprobante.Conceptos.Concepto();
                concepto2.setClaveProdServ("84101500");
                concepto2.setCantidad(new BigDecimal(1));
                concepto2.setClaveUnidad("E48");
                concepto2.setUnidad("SERVICIO");
                concepto2.setDescripcion("HONORARIOS POR SERVICIOS FIDUCIARIOS");
                concepto2.setValorUnitario(new BigDecimal(totalHonorario).setScale(2, RoundingMode.HALF_UP));
                concepto2.setImporte(new BigDecimal(totalHonorario).setScale(2, RoundingMode.HALF_UP));
                Comprobante.Conceptos.Concepto.Impuestos impuestos = new Comprobante.Conceptos.Concepto.Impuestos();
                Comprobante.Conceptos.Concepto.Impuestos.Traslados traslados = new Comprobante.Conceptos.Concepto.Impuestos.Traslados();
                List<Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado> trasladoss = new ArrayList<>();
                Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado traslado = new Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado();
                traslado.setBase(new BigDecimal(totalHonorario).setScale(2, RoundingMode.HALF_UP));
                traslado.setImpuesto("002");
                traslado.setTipoFactor(CTipoFactor.TASA);
                traslado.setTasaOCuota(new BigDecimal(iva).setScale(6, RoundingMode.HALF_UP));
                traslado.setImporte(new BigDecimal(totalIVA).setScale(2, RoundingMode.HALF_UP));
                trasladoss.add(traslado);

//        traslados.setTraslado(trasladoss);
                traslados.getTraslado().clear();
                traslados.getTraslado().addAll(trasladoss);
                impuestos.setTraslados(traslados);
                concepto2.setImpuestos(impuestos);

                listaConceptos.add(concepto2);
                listaConceptos.add(concepto1);
//        conceptos.setConcepto(listaConceptos);

                conceptos = new Comprobante.Conceptos();
                conceptos.getConcepto().addAll(listaConceptos);
                comprobante.setConceptos(conceptos);

                Comprobante.Impuestos impuestos2 = new Comprobante.Impuestos();
                impuestos2.setTotalImpuestosTrasladados(new BigDecimal(totalIVA).setScale(2, RoundingMode.HALF_UP));
                Comprobante.Impuestos.Traslados traslados2 = new Comprobante.Impuestos.Traslados();
                List<Comprobante.Impuestos.Traslados.Traslado> trasladoss2 = new ArrayList<>();
                Comprobante.Impuestos.Traslados.Traslado traslado2 = new Comprobante.Impuestos.Traslados.Traslado();
                traslado2.setImpuesto("002");
                traslado2.setTipoFactor(CTipoFactor.TASA);
                traslado2.setTasaOCuota(new BigDecimal(iva).setScale(6, RoundingMode.HALF_UP));
                traslado2.setImporte(new BigDecimal(totalIVA).setScale(2, RoundingMode.HALF_UP));
                trasladoss2.add(traslado2);
                //traslados2.setTraslado(trasladoss2);
                traslados2.getTraslado().clear();
                traslados2.getTraslado().addAll(trasladoss2);
                impuestos2.setTraslados(traslados2);
                comprobante.setImpuestos(impuestos2);

                JAXBContext jAXBContext = JAXBContext.newInstance(Comprobante.class);
                Marshaller marshaller = jAXBContext.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

                // marshaller.setProperty("com.sun.xml.internal.bind.namespacePrefixMapper", new MyNamespaceMapper());
                marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, LOCATION);

                StringWriter sw = new StringWriter();
                marshaller.marshal(comprobante, sw);
                String xmlEnvio = sw.toString();
                //System.exit(100);
                Reader reader = new StringReader(xmlEnvio);
                StreamSource sourceXML = new StreamSource(reader);

                TransformerFactory tFactory = TransformerFactory.newInstance();
                Transformer transformer = tFactory.newTransformer(formatoCadenaOriginal);

                StringWriter writer = new StringWriter();
                StreamResult result = new StreamResult(writer);
                transformer.transform(sourceXML, result);

                if (compEdoCta.getUuid() == null) {

                    // Bloque 503 - 517
                    System.out.println(writer.toString());
                    Encriptar.setKeyFilename(key_filename);
                    comprobante.setSello(Encriptar.rsaTexto(writer.toString(), compEdoCta.getCertificado().getPassword()));

                    StringWriter sw3 = new StringWriter();
                    marshaller.marshal(comprobante, sw3);
                    //marshaller.marshal(comprobante, System.out); // Display XML
                    String xmlEnvio3 = sw3.toString();

                    xmlEnvio3 = xmlEnvio3.replaceAll(" standalone=\"yes\"", "");
                    xmlEnvio3 = xmlEnvio3.replaceAll(" https://www.garante.mx/cfd/addenda/edocta https://www.garante.mx/EdoctaV1.xsd", "");
                    System.out.println(xmlEnvio3);

                    String referencia = contrato.getClave_contrato() + format3.format(fechaHoy);
                    System.out.println("referencia: " + referencia);

                    IWSCFDI33 servicio = new WSCFDI33().getSoapHttpEndpointHttps();
                    RespuestaTFD33 rtfd = servicio.timbrarCFDI(user, password, xmlEnvio3, referencia);

                    if (rtfd.isOperacionExitosa()) {
                        Timbre33 timbre33 = rtfd.getTimbre().getValue();
                        String uuid = timbre33.getUUID().getValue();
                        System.out.println(rtfd.getXMLResultado());
                        System.out.println("Timbrado - UUID: " + uuid);
                        compEdoCta.setFechaTimbre(Fecha.getXMLGregorianCalendar(timbre33.getFechaTimbrado()));
                        compEdoCta.setUuid(uuid);
                        compEdoCta.setSelloCFD(timbre33.getSelloCFD().getValue());
                        compEdoCta.setnCertificado(timbre33.getNumeroCertificadoSAT().getValue());
                        compEdoCta.setSelloSAT(timbre33.getSelloSAT().getValue());

                        // insertar en BD
                        compEdoCta.setFechaEdoCta(format.format(fechaHoy));
                        compEdoCta.setClaveContrato(contrato.getClave_contrato());
                        compEdoCta.setTotal(comprobante.getTotal().doubleValue());
                        ctaDAO.insert(compEdoCta);
                    } else {
                        System.out.println("Error edoCta -> " + contrato.getClave_contrato() + " ");
                        System.out.println("CodigoRespuesta: " + rtfd.getCodigoRespuesta().getValue());
                        System.out.println("MensajeError: " + rtfd.getMensajeError().getValue());
                        guardaError(lErrores, new ErroresXML(rfcActual, format.format(new Date()), rtfd.getMensajeError().getValue()));
                    }
                } else {
                    comprobante.setSello(compEdoCta.getSelloCFD());
                }
                //Proceso de generacion XML y PDF
                //if (totalTimbrados == contratos.size()) {
                System.out.println("Timbrados: " + totalTimbrados + " Contratos: " + contratos.size() + " - se genera estado de cuenta");
                List<Comprobante.Complemento> complementos = new ArrayList<>();
                Comprobante.Complemento complemento = new Comprobante.Complemento();

                List<Object> tfds = new ArrayList<>();
                TimbreFiscalDigital tdf = new TimbreFiscalDigital();
                tdf.setFechaTimbrado(Fecha.crearXMLGregorianCalendar(compEdoCta.getFechaTimbre()));
                tdf.setVersion("1.1");
                tdf.setRfcProvCertif(compEdoCta.getRfcProv());
                tdf.setUUID(compEdoCta.getUuid());
                tdf.setSelloCFD(compEdoCta.getSelloCFD());
                tdf.setNoCertificadoSAT(compEdoCta.getnCertificado());
                tdf.setSelloSAT(compEdoCta.getSelloSAT());
                tfds.add(tdf);

                StringWriter sw2 = new StringWriter();
                marshaller.marshal(tdf, sw2);
                String xmlEnvio2 = sw2.toString();

                Reader reader2 = new StringReader(xmlEnvio2);
                StreamSource sourceXML2 = new StreamSource(reader2);

                TransformerFactory tFactory2 = TransformerFactory.newInstance();
                Transformer transformer2 = tFactory2.newTransformer(formatoCadenaOriginalTimbre);

                StringWriter writer2 = new StringWriter();
                StreamResult result2 = new StreamResult(writer2);
                transformer2.transform(sourceXML2, result2);

                String cadorigen = "||1.1|"
                        + tdf.getUUID() + "|"
                        + tdf.getFechaTimbrado() + "|"
                        + tdf.getRfcProvCertif() + "|"
                        + "ValorDelAtributoLeyenda|"
                        + tdf.getSelloCFD() + "|"
                        + tdf.getNoCertificadoSAT() + "||";
                System.out.println("Cadena Origen: " + cadorigen);

                //complemento.setAny(tfds);
                complemento.getAny().clear();
                complemento.getAny().addAll(tfds);
                complementos.add(complemento);
                //comprobante.setComplemento(complementos);
                comprobante.getComplemento().clear();
                comprobante.getComplemento().addAll(complementos);

                Comprobante.Addenda addenda = new Comprobante.Addenda();
                List<Object> edoCuentas = new ArrayList<>();

                EstadoDeCuenta edoDeCuenta = new EstadoDeCuenta();
                edoDeCuenta.setAportacionTotalFideicomiso(new BigDecimal(totalAbono).setScale(2, RoundingMode.HALF_UP));
                edoDeCuenta.setContrato(contrato.getClave_contrato());
                String fechaEdoCta = format.format(c1.getTime()).substring(8, 10) + " al " + format.format(c2.getTime()).substring(8, 10) + " de " + meses[c1.get(Calendar.MONTH)] + " de " + c1.get(Calendar.YEAR);
                edoDeCuenta.setFechaEdoCta(fechaEdoCta);
                edoDeCuenta.setHonorariosFiduciarios(new BigDecimal(totalHonorario).setScale(2, RoundingMode.HALF_UP));
                edoDeCuenta.setIvaHonorariosFiduciarios(new BigDecimal(totalIVA).setScale(2, RoundingMode.HALF_UP));
                edoDeCuenta.setLiquidacionFideicomisarios(new BigDecimal(totalOrdenLiq).setScale(2, RoundingMode.HALF_UP));
                edoDeCuenta.setMoneda(CMoneda.MXN.toString());
                edoDeCuenta.setRestitucionPatrimonio(new BigDecimal(totalRestitucion).setScale(2, RoundingMode.HALF_UP));
                edoDeCuenta.setRfcFideicomitente(contrato.getRFC());
                edoDeCuenta.setSaldoAnteriorPatrimonio(new BigDecimal(saldoAnterior).setScale(2, RoundingMode.HALF_UP));
                edoDeCuenta.setSaldoFinalPatrimonio(new BigDecimal(saldoFinal).setScale(2, RoundingMode.HALF_UP));
                edoDeCuenta.setTipoContrato("Fideicomiso de Garantia");
                edoDeCuenta.setVersion("1.0");

                edoDeCuenta.setMovimientos(movs);

                edoCuentas.add(edoDeCuenta);
                //addenda.setAny(edoCuentas);
                addenda.getAny().clear();
                addenda.getAny().addAll(edoCuentas);
                comprobante.setAddenda(addenda);

                contrato.setEdoCtas(edoCtas);

                String salida = File.separator + "inetpub" + File.separator + "ftproot" + File.separator + "EstadosDeCuenta" + File.separator + contrato.getClave_contrato() + File.separator + c2.get(Calendar.YEAR) + File.separator;
                // Local Test Output Path
                //salida = "./" + "EstadosDeCuenta" + File.separator + contrato.getClave_contrato() + File.separator + c2.get(Calendar.YEAR) + File.separator;
                File carpetas = new File(salida);
                carpetas.mkdirs();
                marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, LOCATIONFinal);
                marshaller.marshal(comprobante, new File(salida + meses[c2.get(Calendar.MONTH)] + ".xml"));

                CreaPDF.edoCuenta(contrato, saldoAnterior, saldoFinal, totalRestitucion, totalOrdenLiq, totalHonorario, totalIVA, totalAbono, fechaEdoCta, tdf, salida + meses[c2.get(Calendar.MONTH)], cadorigen, emisor.getRfc());

                /////////////////////////////////////////////////////////////////////////////////////////////////// CREACION DE ZIP
                File files[] = new File[2];
                files[0] = new File(salida + meses[c2.get(Calendar.MONTH)] + ".xml");
                files[1] = new File(salida + meses[c2.get(Calendar.MONTH)] + ".pdf");
                File zip = new File("c:" + File.separator + "inetpub" + File.separator + "ftproot" + File.separator + "EstadosDeCuenta" + File.separator + contrato.getClave_contrato() + File.separator + "EDOCTA_" + contrato.getClave_contrato() + "_" + c2.get(Calendar.YEAR) + "_" + Empaquetar.numMes(c2.get(Calendar.MONTH)) + "" + ".zip");
                try {
                    Empaquetar.addFilesToExistingZip2(zip, files);
                    if (!Empaquetar.eliminaR(carpetas)) {
                        System.out.println("Error al eliminar carpeta " + carpetas.getAbsolutePath());
                    }
                } catch (IOException e) {
                    System.out.println("Error: " + e.getMessage());
                }
//                } else {
//                    System.out.println("Timbrados: " + totalTimbrados + " Contratos: " + contratos.size() + " - No se genera estado de cuenta");
//                }

            } catch (JAXBException | TransformerException ex) {
                Logger.getLogger(CreaXML.class.getName()).log(Level.SEVERE, null, ex);
                erroresxml++;
                if (!rfcErrores.contains(rfcActual)) {
                    rfcErrores.add(rfcActual);
                }
                guardaError(lErrores, new ErroresXML(rfcActual, format.format(new Date()), ex.getMessage()));
            }

        }
        ReportesExcel reportes = new ReportesExcel();
        //ReportesExcel.setXLXSPath("./"); // Para pruebs locales se puede pasar el path
        reportes.generaExcel("EDOCTA_" + format4.format(fechaHoy), lErrores);
    }

    private static void Regenerarfiles(Date fechaHoy) {
        int errorestimbrado = 0, errorespdf = 0, erroresxml = 0, totaledoscta = 0;
        String rfcActual = "";
        List<String> rfcErrores = new ArrayList<>();
        List<ErroresXML> lErrores = new ArrayList<>();
        ContratosDAO contratosDAO = new ContratosDAO();
        GenericEdoCtaDAO edoCtaDAO = new GenericEdoCtaDAO();
        CompEdoCtaDAO compEdoCtaDAO = new CompEdoCtaDAO();

        List<Contrato> contratos = contratosDAO.getAll();
//        System.out.println("fechahoy="+format4.format(fechaHoy));
        Integer totalTimbrados = compEdoCtaDAO.cuentaMes(format4.format(fechaHoy));

        Calendar c1 = Fecha.getPrimerDiaDeMes(fechaHoy);
        Calendar c2 = Fecha.getUltimoDiaDeMes(fechaHoy);

        totaledoscta = contratos.size();

        System.out.println("Generando estados de cuenta");

        int cont = 1;

        for (Contrato contrato : contratos) {

            System.out.println(contrato.getClave_contrato() + " " + cont + " de " + contratos.size());
            cont++;

            rfcActual = contrato.getRFC();

            try {
                CompEdoCtaDAO ctaDAO = new CompEdoCtaDAO();
                CompEdoCta compEdoCta = ctaDAO.getBy(contrato.getClave_contrato(), format.format(c1.getTime()));

                if (compEdoCta != null) {
                    List<EdoCta> edoCtas = edoCtaDAO.getEdoCta(contrato.getClave_contrato(), format.format(c1.getTime()), format.format(c2.getTime()));

                    Comprobante comprobante = new Comprobante();
                    comprobante.setVersion("3.3");
//        comprobante.setFecha(dateFormat.format(compEdoCta.getFecha()));

                    GregorianCalendar cal = new GregorianCalendar();
                    cal.setTime(compEdoCta.getFecha());
                    try {
                        comprobante.setFecha(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
                    } catch (DatatypeConfigurationException ex) {
                        Logger.getLogger(CreaXML.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    comprobante.setNoCertificado(compEdoCta.getCertificado().getnCertificado());
                    comprobante.setCertificado(compEdoCta.getCertificado().getCertificado());
                    comprobante.setFormaPago("03");
                    comprobante.setMetodoPago(CMetodoPago.PUE);

                    Double totalIVA = 0.00;
                    Double totalHonorario = 0.00;
                    Double totalAbono = 0.00;
                    Double totalOrdenLiq = 0.00;
                    Double totalRestitucion = 0.00;
                    Double saldoAnterior = 0.00;
                    Double saldoFinal = 0.00;

                    EstadoDeCuenta.Movimientos movs = new EstadoDeCuenta.Movimientos();
                    List<EstadoDeCuenta.Movimientos.Movimiento> movss = new ArrayList<>();

                    boolean primero = true;
                    for (EdoCta edoCta : edoCtas) {
                        EstadoDeCuenta.Movimientos.Movimiento mov = new EstadoDeCuenta.Movimientos.Movimiento();
                        if (primero) {
                            if (edoCta.getConcepto().equals("APORTACION A PATRIMONIO")) {
                                saldoAnterior = edoCta.getSaldo() - edoCta.getAbono();
                            } else {
                                saldoAnterior = edoCta.getSaldo() + edoCta.getCargo();
                            }
                            primero = false;
                        }
                        saldoFinal = edoCta.getSaldo();
                        if (edoCta.getConcepto().equals("APORTACION A PATRIMONIO")) {
                            totalAbono += edoCta.getAbono();
                        }
                        if (edoCta.getConcepto().equals("HONORARIOS FIDUCIARIOS")) {
                            totalHonorario += edoCta.getCargo();
                        }
                        if (edoCta.getConcepto().equals("RESTITUCION DE PATRIMONIO")) {
                            totalRestitucion += edoCta.getAbono();
                        }
                        if (edoCta.getConcepto().equals("ORDEN DE LIQUIDACION")) {
                            totalOrdenLiq += edoCta.getCargo();
                        }
                        mov.setCargo(new BigDecimal(edoCta.getCargo()).setScale(2, RoundingMode.HALF_UP));
                        mov.setAbono(new BigDecimal(edoCta.getAbono()).setScale(2, RoundingMode.HALF_UP));
                        mov.setSaldo(new BigDecimal(edoCta.getSaldo()).setScale(2, RoundingMode.HALF_UP));
                        mov.setConcepto(edoCta.getConcepto());
                        mov.setFecha(format.format(edoCta.getFecha()));
                        movss.add(mov);
                    }
//        movs.setMovimiento(movss);
                    movs.getMovimiento().clear();
                    movs.getMovimiento().addAll(movss);

                    totalIVA = totalHonorario * iva;

                    if (totalAbono > 0) {
                        comprobante.setTotal(new BigDecimal(totalAbono).setScale(2, RoundingMode.HALF_UP));
                        Double subtotal = totalAbono - totalIVA;
                        comprobante.setSubTotal(new BigDecimal(subtotal.toString()).setScale(2, RoundingMode.HALF_UP));
                    } else {
                        comprobante.setTotal(new BigDecimal(0.00));
                        comprobante.setSubTotal(new BigDecimal(0.00));
                    }
                    comprobante.setMoneda(CMoneda.MXN);
                    comprobante.setTipoDeComprobante(CTipoDeComprobante.I);
                    comprobante.setLugarExpedicion(cp_emisor);

                    //Emisor
                    Comprobante.Emisor emisor = new Comprobante.Emisor();
                    emisor.setRfc(rfc_emisor);
                    emisor.setNombre(razon_social_emisor);
                    emisor.setRegimenFiscal(regimen_emisor);
                    comprobante.setEmisor(emisor);

                    //Receptor
                    Comprobante.Receptor receptor = new Comprobante.Receptor();
                    receptor.setRfc(contrato.getRFC());
                    receptor.setNombre(contrato.getNombre_cliente());
                    receptor.setUsoCFDI(CUsoCFDI.G_03);
                    comprobante.setReceptor(receptor);

                    //Conceptos
                    List<Comprobante.Conceptos.Concepto> listaConceptos = new ArrayList<>();
                    Comprobante.Conceptos conceptos = new Comprobante.Conceptos();

                    Comprobante.Conceptos.Concepto concepto1 = new Comprobante.Conceptos.Concepto();
                    concepto1.setClaveProdServ("84101503");
                    concepto1.setCantidad(new BigDecimal(1));
                    concepto1.setClaveUnidad("E48");
                    concepto1.setUnidad("SERVICIO");
                    concepto1.setDescripcion("GARANTIA FIDUCIARIA");
                    concepto1.setValorUnitario(new BigDecimal(totalAbono - totalHonorario - totalIVA).setScale(2, RoundingMode.HALF_UP));
                    concepto1.setImporte(new BigDecimal(totalAbono - totalHonorario - totalIVA).setScale(2, RoundingMode.HALF_UP));

                    Comprobante.Conceptos.Concepto concepto2 = new Comprobante.Conceptos.Concepto();
                    concepto2.setClaveProdServ("84101500");
                    concepto2.setCantidad(new BigDecimal(1));
                    concepto2.setClaveUnidad("E48");
                    concepto2.setUnidad("SERVICIO");
                    concepto2.setDescripcion("HONORARIOS POR SERVICIOS FIDUCIARIOS");
                    concepto2.setValorUnitario(new BigDecimal(totalHonorario).setScale(2, RoundingMode.HALF_UP));
                    concepto2.setImporte(new BigDecimal(totalHonorario).setScale(2, RoundingMode.HALF_UP));
                    Comprobante.Conceptos.Concepto.Impuestos impuestos = new Comprobante.Conceptos.Concepto.Impuestos();
                    Comprobante.Conceptos.Concepto.Impuestos.Traslados traslados = new Comprobante.Conceptos.Concepto.Impuestos.Traslados();
                    List<Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado> trasladoss = new ArrayList<>();
                    Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado traslado = new Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado();
                    traslado.setBase(new BigDecimal(totalHonorario).setScale(2, RoundingMode.HALF_UP));
                    traslado.setImpuesto("002");
                    traslado.setTipoFactor(CTipoFactor.TASA);
                    traslado.setTasaOCuota(new BigDecimal(iva).setScale(6, RoundingMode.HALF_UP));
                    traslado.setImporte(new BigDecimal(totalIVA).setScale(2, RoundingMode.HALF_UP));
                    trasladoss.add(traslado);

//        traslados.setTraslado(trasladoss);
                    traslados.getTraslado().clear();
                    traslados.getTraslado().addAll(trasladoss);
                    impuestos.setTraslados(traslados);
                    concepto2.setImpuestos(impuestos);

                    listaConceptos.add(concepto2);
                    listaConceptos.add(concepto1);
//        conceptos.setConcepto(listaConceptos);
                    conceptos.getConcepto().clear();
                    conceptos.getConcepto().addAll(listaConceptos);
                    comprobante.setConceptos(conceptos);

                    Comprobante.Impuestos impuestos2 = new Comprobante.Impuestos();
                    impuestos2.setTotalImpuestosTrasladados(new BigDecimal(totalIVA).setScale(2, RoundingMode.HALF_UP));
                    Comprobante.Impuestos.Traslados traslados2 = new Comprobante.Impuestos.Traslados();
                    List<Comprobante.Impuestos.Traslados.Traslado> trasladoss2 = new ArrayList<>();
                    Comprobante.Impuestos.Traslados.Traslado traslado2 = new Comprobante.Impuestos.Traslados.Traslado();
                    traslado2.setImpuesto("002");
                    traslado2.setTipoFactor(CTipoFactor.TASA);
                    traslado2.setTasaOCuota(new BigDecimal(iva).setScale(6, RoundingMode.HALF_UP));
                    traslado2.setImporte(new BigDecimal(totalIVA).setScale(2, RoundingMode.HALF_UP));
                    trasladoss2.add(traslado2);
//        traslados2.setTraslado(trasladoss2);
                    traslados2.getTraslado().clear();
                    traslados2.getTraslado().addAll(trasladoss2);
                    impuestos2.setTraslados(traslados2);
                    comprobante.setImpuestos(impuestos2);
//import com.sun.xml.internal.bind.marshaller.NamespacePrefixMapper;
                    JAXBContext jAXBContext = JAXBContext.newInstance(Comprobante.class);
                    Marshaller marshaller = jAXBContext.createMarshaller();
                    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

                    marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", SPACE_MAPPER);
//                    marshaller.setProperty("com.sun.xml.internal.bind.marshaller.NamespacePrefixMapper", SPACE_MAPPER);
                    marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, LOCATION);

                    comprobante.setSello(compEdoCta.getSelloCFD());
                    //Proceso de generacion XML y PDF
                    System.out.println("totalTimbrados =" + totalTimbrados + "; contratos.size()=" + contratos.size());
                    if (totalTimbrados == contratos.size()) {
//                    if (totalTimbrados != contratos.size()) {
                        List<Comprobante.Complemento> complementos = new ArrayList<>();
                        Comprobante.Complemento complemento = new Comprobante.Complemento();
                        List<Object> tfds = new ArrayList<>();
                        TimbreFiscalDigital tdf = new TimbreFiscalDigital();
                        String rtto;
                        try {
                            tdf.setFechaTimbrado(Fecha.crearXMLGregorianCalendar(compEdoCta.getFechaTimbre()));
                            tdf.setVersion("1.1");
                            tdf.setRfcProvCertif(compEdoCta.getRfcProv());
                            tdf.setUUID(compEdoCta.getUuid());
                            tdf.setSelloCFD(compEdoCta.getSelloCFD());
                            tdf.setNoCertificadoSAT(compEdoCta.getnCertificado());
                            tdf.setSelloSAT(compEdoCta.getSelloSAT());
                            tfds.add(tdf);
                        } catch (Exception e) {
                            rtto = e.getMessage();
                            System.out.println(e.getMessage());
                        }

                        StringWriter sw2 = new StringWriter();
                        marshaller.marshal(tdf, sw2);
                        String xmlEnvio2 = sw2.toString();

                        Reader reader2 = new StringReader(xmlEnvio2);
                        StreamSource sourceXML2 = new StreamSource(reader2);

                        TransformerFactory tFactory2 = TransformerFactory.newInstance();
                        Transformer transformer2 = tFactory2.newTransformer(formatoCadenaOriginalTimbre);

                        StringWriter writer2 = new StringWriter();
                        StreamResult result2 = new StreamResult(writer2);
                        transformer2.transform(sourceXML2, result2);

                        String cadorigen = "||1.1|"
                                + tdf.getUUID() + "|"
                                + tdf.getFechaTimbrado() + "|"
                                + tdf.getRfcProvCertif() + "|"
                                + "ValorDelAtributoLeyenda|"
                                + tdf.getSelloCFD() + "|"
                                + tdf.getNoCertificadoSAT() + "||";

//          complemento.setAny(tfds);
                        complemento.getAny().clear();
                        complemento.getAny().addAll(tfds);
                        complementos.add(complemento);
//          comprobante.setComplemento(complementos);
                        comprobante.getComplemento().clear();
                        comprobante.getComplemento().addAll(complementos);

                        Comprobante.Addenda addenda = new Comprobante.Addenda();
                        List<Object> edoCuentas = new ArrayList<>();

                        EstadoDeCuenta edoDeCuenta = new EstadoDeCuenta();
                        edoDeCuenta.setAportacionTotalFideicomiso(new BigDecimal(totalAbono).setScale(2, RoundingMode.HALF_UP));
                        edoDeCuenta.setContrato(contrato.getClave_contrato());
                        String fechaEdoCta = format.format(c1.getTime()).substring(8, 10) + " al " + format.format(c2.getTime()).substring(8, 10) + " de " + meses[c1.get(Calendar.MONTH)] + " de " + c1.get(Calendar.YEAR);
                        edoDeCuenta.setFechaEdoCta(fechaEdoCta);
                        edoDeCuenta.setHonorariosFiduciarios(new BigDecimal(totalHonorario).setScale(2, RoundingMode.HALF_UP));
                        edoDeCuenta.setIvaHonorariosFiduciarios(new BigDecimal(totalIVA).setScale(2, RoundingMode.HALF_UP));
                        edoDeCuenta.setLiquidacionFideicomisarios(new BigDecimal(totalOrdenLiq).setScale(2, RoundingMode.HALF_UP));
                        edoDeCuenta.setMoneda(CMoneda.MXN.toString());
                        edoDeCuenta.setRestitucionPatrimonio(new BigDecimal(totalRestitucion).setScale(2, RoundingMode.HALF_UP));
                        edoDeCuenta.setRfcFideicomitente(contrato.getRFC());
                        edoDeCuenta.setSaldoAnteriorPatrimonio(new BigDecimal(saldoAnterior).setScale(2, RoundingMode.HALF_UP));
                        edoDeCuenta.setSaldoFinalPatrimonio(new BigDecimal(saldoFinal).setScale(2, RoundingMode.HALF_UP));
                        edoDeCuenta.setTipoContrato("Fideicomiso de Garantia");
                        edoDeCuenta.setVersion("1.0");

                        edoDeCuenta.setMovimientos(movs);

                        edoCuentas.add(edoDeCuenta);
//          addenda.setAny(edoCuentas);
                        addenda.getAny().clear();
                        addenda.getAny().addAll(edoCuentas);
                        comprobante.setAddenda(addenda);

                        contrato.setEdoCtas(edoCtas);

                        String salida = File.separator + "inetpub" + File.separator + "ftproot" + File.separator + "EstadosDeCuenta" + File.separator + contrato.getClave_contrato() + File.separator + c2.get(Calendar.YEAR) + File.separator;
                        File carpetas = new File(salida);
//                        System.out.println("SALIDA ARCHIVO="+salida);
                        carpetas.mkdirs();
                        marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, LOCATIONFinal);
                        marshaller.marshal(comprobante, new File(salida + meses[c2.get(Calendar.MONTH)] + ".xml"));

                        CreaPDF.edoCuenta(contrato, saldoAnterior, saldoFinal, totalRestitucion, totalOrdenLiq, totalHonorario, totalIVA, totalAbono, fechaEdoCta, tdf, salida + meses[c2.get(Calendar.MONTH)], cadorigen, emisor.getRfc());

                        /////////////////////////////////////////////////////////////////////////////////////////////////// CREACION DE ZIP
                        File files[] = new File[2];
                        files[0] = new File(salida + meses[c2.get(Calendar.MONTH)] + ".xml");
                        files[1] = new File(salida + meses[c2.get(Calendar.MONTH)] + ".pdf");
                        File zip = new File("c:" + File.separator + "inetpub" + File.separator + "ftproot" + File.separator + "EstadosDeCuenta" + File.separator + contrato.getClave_contrato() + File.separator + "EDOCTA_" + contrato.getClave_contrato() + "_" + c2.get(Calendar.YEAR) + "_" + Empaquetar.numMes(c2.get(Calendar.MONTH)) + "" + ".zip");
                        try {
                            Empaquetar.addFilesToExistingZip2(zip, files);
                            if (!Empaquetar.eliminaR(carpetas)) {
                                System.out.println("Error al eliminar carpeta " + carpetas.getAbsolutePath());
                            }
                        } catch (IOException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    }
                }

            } catch (JAXBException | TransformerException ex) {
                Logger.getLogger(CreaXML.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
                erroresxml++;
                if (!rfcErrores.contains(rfcActual)) {
                    rfcErrores.add(rfcActual);
                }
                guardaError(lErrores, new ErroresXML(rfcActual, format.format(new Date()), ex.getMessage()));
            }

        }

    }

    private static void generaNomina(Date fechaHoy) {
        System.out.println("Generando Nomina");
        SimpleDateFormat formatNomina = new SimpleDateFormat("yyyy-MM-dd");
        int errorestimbrado = 0, errorespdf = 0, erroresxml = 0, totalcompsnomina = 0;
        String rfcActual = "";
        List<String> rfcErrores = new ArrayList<>();
        List<ErroresXML> lErrores = new ArrayList<>();
        ContratosDAO contratosDAO = new ContratosDAO();
        MovimientosDAO movimientosDAO = new MovimientosDAO();
        CompNominaDAO compNominaDAO = new CompNominaDAO();

        Calendar c1 = Fecha.getPrimerDiaDeMes(fechaHoy);
        c1.set(Calendar.YEAR, 2020); // El año de procesamiento esta fijo
        Calendar c2 = Fecha.getUltimoDiaDeMes(fechaHoy);
        c2.set(Calendar.YEAR, 2020); // El año de procesamiento esta fijo

        List<String> rfcs = movimientosDAO.getRFCMes(format.format(c1.getTime()), format.format(c2.getTime()));
        Integer totalTimbrados = compNominaDAO.cuentaMes(format4.format(fechaHoy));

        int cont = 1;
        for (String rfc : rfcs) {

            System.out.println(rfc + " " + cont + " de " + rfcs.size());
            cont++;

            rfcActual = rfc;

            try {

                List<Movimiento> movimientos = movimientosDAO.getAll(rfc, format.format(c1.getTime()), format.format(c2.getTime()));

                CompNomina compNomina = compNominaDAO.getBy(rfc, format4.format(c1.getTime()));

                if (compNomina == null) {
                    compNomina = new CompNomina();
                    compNomina.setFecha(new Date());
                    compNomina.setRfcProv(rfc_prov_cert);
                    Certificado certificado = new Certificado();
                    certificado.setIdCertificado(1);
                    certificado.setPassword(cer_password);
                    certificado.setnCertificado(cer_number);
                    certificado.setCertificado(Encriptar.base64File(directorioLocal + "/" + csds_path + "/" + cer_filename));
                    compNomina.setCertificado(certificado);
                }

                Comprobante comprobante = new Comprobante();
                comprobante.setVersion("3.3");
                //comprobante.setFecha(dateFormat.format(compNomina.getFecha()));

                GregorianCalendar cal = new GregorianCalendar();

                cal.setTime(compNomina.getFecha());
                try {
                    comprobante.setFecha(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
                    System.out.println("Fecha 1: " + comprobante.getFecha().toString());
                    javax.xml.datatype.XMLGregorianCalendar fecha;
                    fecha = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal.get(Calendar.YEAR),
                            cal.get(Calendar.MONTH) + 1,
                            cal.get(Calendar.DAY_OF_MONTH),
                            cal.get(Calendar.HOUR_OF_DAY),
                            cal.get(Calendar.MINUTE),
                            cal.get(Calendar.SECOND),
                            DatatypeConstants.FIELD_UNDEFINED,
                            DatatypeConstants.FIELD_UNDEFINED);
                    comprobante.setFecha(fecha);
                    System.out.println("Fecha 2: " + comprobante.getFecha().toString());
                } catch (DatatypeConfigurationException ex) {
                    Logger.getLogger(CreaXML.class.getName()).log(Level.SEVERE, null, ex);
                }

                comprobante.setNoCertificado(compNomina.getCertificado().getnCertificado());
                comprobante.setCertificado(compNomina.getCertificado().getCertificado());

                Double totalYSubTotal = 0.0;

                for (Movimiento movimiento : movimientos) {
                    totalYSubTotal += Double.parseDouble(movimiento.getImporte_liquidacion());
                }

                comprobante.setTotal(new BigDecimal(totalYSubTotal).setScale(2, RoundingMode.HALF_UP));
                comprobante.setSubTotal(new BigDecimal(totalYSubTotal).setScale(2, RoundingMode.HALF_UP));

                comprobante.setTipoDeComprobante(CTipoDeComprobante.N);
                comprobante.setMoneda(CMoneda.MXN);
                comprobante.setLugarExpedicion(cp_emisor);

                comprobante.setMetodoPago(CMetodoPago.PUE);
                comprobante.setFormaPago("99");
                comprobante.setSerie(movimientos.get(0).getClave_contrato());

                //Emisor
                Comprobante.Emisor emisor = new Comprobante.Emisor();
                emisor.setRfc(rfc_emisor);
                emisor.setNombre(razon_social_emisor);
                emisor.setRegimenFiscal(regimen_emisor);
                comprobante.setEmisor(emisor);

                //Receptor
                Comprobante.Receptor receptor = new Comprobante.Receptor();
                receptor.setRfc(rfc);
                receptor.setNombre(movimientos.get(0).getNombre_empleado() + " " + movimientos.get(0).getApellidoP_empleado() + " " + movimientos.get(0).getApellidoM_empleado());
                receptor.setUsoCFDI(CUsoCFDI.P_01);
                comprobante.setReceptor(receptor);

                List<Comprobante.Conceptos.Concepto> listaConceptos = new ArrayList<>();
                Comprobante.Conceptos conceptos = new Comprobante.Conceptos();

                Comprobante.Conceptos.Concepto concepto1 = new Comprobante.Conceptos.Concepto();
                concepto1.setClaveProdServ("84111505");
                concepto1.setCantidad(new BigDecimal(1));
                concepto1.setClaveUnidad("ACT");
                concepto1.setDescripcion("Pago de nómina");
                concepto1.setValorUnitario(new BigDecimal(totalYSubTotal).setScale(2, RoundingMode.HALF_UP));
                concepto1.setImporte(new BigDecimal(totalYSubTotal).setScale(2, RoundingMode.HALF_UP));

                listaConceptos.add(concepto1);
                //conceptos.setConcepto(listaConceptos);
                conceptos.getConcepto().clear();
                conceptos.getConcepto().addAll(listaConceptos);
                comprobante.setConceptos(conceptos);

                List<Comprobante.Complemento> complementos = new ArrayList<>();
                Comprobante.Complemento complemento = new Comprobante.Complemento();

                List<Object> listaComplementos = new ArrayList<>();
                Nomina nomina = new Nomina();
                nomina.setVersion("1.2");
                nomina.setTipoNomina(CTipoNomina.E);

                //nomina.setFechaPago(formatNomina.format(c2.getTime()));
                GregorianCalendar cal1 = new GregorianCalendar();
                cal1.setTime(c2.getTime());
                try {
                    javax.xml.datatype.XMLGregorianCalendar nominaFechaPago;
                    nominaFechaPago = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal1.get(Calendar.YEAR),
                            cal1.get(Calendar.MONTH) + 1,
                            cal1.get(Calendar.DAY_OF_MONTH),
                            DatatypeConstants.FIELD_UNDEFINED,
                            DatatypeConstants.FIELD_UNDEFINED,
                            DatatypeConstants.FIELD_UNDEFINED,
                            DatatypeConstants.FIELD_UNDEFINED,
                            DatatypeConstants.FIELD_UNDEFINED);
                    nomina.setFechaPago(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal1));
                    System.out.println("FechaPago 1:" + nomina.getFechaPago().toString());
                    nomina.setFechaPago(nominaFechaPago);
                    System.out.println("FechaPago 2:" + nomina.getFechaPago().toString());
                } catch (DatatypeConfigurationException ex) {
                    Logger.getLogger(CreaXML.class.getName()).log(Level.SEVERE, null, ex);
                }

                //nomina.setFechaInicialPago(formatNomina.format(c1.getTime()));
                GregorianCalendar cal2 = new GregorianCalendar();
                cal2.setTime(c1.getTime());
                try {
                    javax.xml.datatype.XMLGregorianCalendar nominaFechaInicialPago;
                    nominaFechaInicialPago = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal2.get(Calendar.YEAR),
                            cal2.get(Calendar.MONTH) + 1,
                            cal2.get(Calendar.DAY_OF_MONTH),
                            DatatypeConstants.FIELD_UNDEFINED,
                            DatatypeConstants.FIELD_UNDEFINED,
                            DatatypeConstants.FIELD_UNDEFINED,
                            DatatypeConstants.FIELD_UNDEFINED,
                            DatatypeConstants.FIELD_UNDEFINED);
                    nomina.setFechaInicialPago(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal2));
                    System.out.println("FechaPagoInicial 1:" + nomina.getFechaInicialPago().toString());
                    nomina.setFechaInicialPago(nominaFechaInicialPago);
                    System.out.println("FechaPagoInicial 2:" + nomina.getFechaInicialPago().toString());
                } catch (DatatypeConfigurationException ex) {
                    Logger.getLogger(CreaXML.class.getName()).log(Level.SEVERE, null, ex);
                }

                //nomina.setFechaFinalPago(formatNomina.format(c2.getTime()));
                GregorianCalendar cal3 = new GregorianCalendar();
                cal3.setTime(c2.getTime());
                try {
                    javax.xml.datatype.XMLGregorianCalendar nominaFechaFinalPago;
                    nominaFechaFinalPago = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal3.get(Calendar.YEAR),
                            cal3.get(Calendar.MONTH) + 1,
                            cal3.get(Calendar.DAY_OF_MONTH),
                            DatatypeConstants.FIELD_UNDEFINED,
                            DatatypeConstants.FIELD_UNDEFINED,
                            DatatypeConstants.FIELD_UNDEFINED,
                            DatatypeConstants.FIELD_UNDEFINED,
                            DatatypeConstants.FIELD_UNDEFINED);
                    nomina.setFechaFinalPago(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal3));
                    System.out.println("FechaPagoFinal 1:" + nomina.getFechaFinalPago().toString());
                    nomina.setFechaFinalPago(nominaFechaFinalPago);
                    System.out.println("FechaPagoFinal 2:" + nomina.getFechaFinalPago().toString());
                } catch (DatatypeConfigurationException ex) {
                    Logger.getLogger(CreaXML.class.getName()).log(Level.SEVERE, null, ex);
                }

                nomina.setNumDiasPagados(new BigDecimal(formatNomina.format(c2.getTime()).substring(8, 10)));
                nomina.setTotalOtrosPagos(new BigDecimal(totalYSubTotal).setScale(2, RoundingMode.HALF_UP));

                //Nomina Emisor
                Nomina.Emisor emisorNom = new Nomina.Emisor();
                String _contrato = movimientos.get(0).getClave_contrato();
                Contrato contrato = contratosDAO.get(_contrato);
                // IF FOUND Contrato
                if (contrato != null) {
                    System.out.println("Contrato " + _contrato + " Activo");
                    emisorNom.setRfcPatronOrigen(contrato.getRFC());
                } else {
                    System.out.println("Contrato " + _contrato + " Inactivo - Skip");
                    continue;
                }

                nomina.setEmisor(emisorNom);

                //Nomina Receptor
                Nomina.Receptor receptorNom = new Nomina.Receptor();
                receptorNom.setCurp(movimientos.get(0).getCurp());
                receptorNom.setTipoContrato("99");
                receptorNom.setTipoRegimen("99");
                receptorNom.setNumEmpleado(movimientos.get(0).getClave_empleado());
                receptorNom.setPeriodicidadPago("99");
                receptorNom.setPuesto(movimientos.get(0).getPuesto_empleado());
                receptorNom.setDepartamento(movimientos.get(0).getDepto_empleado());

                // Si se registra una cuenta de tarjeta de débito a 16 posiciones 
                // o una cuenta bancaria a 11 posiciones 
                // o un número de teléfono celular a 10 posiciones, debe existir el banco.
                System.out.println("Cuenta Deposito: " + movimientos.get(0).getCuenta_deposito());
                if (movimientos.get(0).getCuenta_deposito().length() == 10) {
                    //receptorNom.setCuentaBancaria(movimientos.get(0).getCuenta_deposito());
                    receptorNom.setCuentaBancaria(new BigInteger(movimientos.get(0).getCuenta_deposito()));
                    receptorNom.setBanco(String.format("%03d", Integer.parseInt(movimientos.get(0).getClave_banco())));
                }

                if (contrato != null) {
                    receptorNom.setClaveEntFed(CEstado.fromValue(contrato.getEnt_fed()));
                }
                //if (movimientos.get(0).getClave_banco().length() == 10) {
                //  receptorNom.setBanco(String.format("%03d", Integer.parseInt(movimientos.get(0).getClave_banco())));
                //}

                Nomina.OtrosPagos otrosPagos = new Nomina.OtrosPagos();
                List<Nomina.OtrosPagos.OtroPago> listOtroPagos = new ArrayList<>();

                for (Movimiento m : movimientos) {
                    Nomina.OtrosPagos.OtroPago otroPago = new Nomina.OtrosPagos.OtroPago();
                    otroPago.setTipoOtroPago("999");
                    otroPago.setClave("RL" + format2.format(m.getFecha_liquidacion()) + "TF");
                    otroPago.setConcepto("Indemnización por Enfermedades y-o Riesgos Laborales");
                    otroPago.setImporte(new BigDecimal(m.getImporte_liquidacion()).setScale(2, RoundingMode.HALF_UP));
                    listOtroPagos.add(otroPago);
                }

                //otrosPagos.setOtroPago(listOtroPagos);
                otrosPagos.getOtroPago().clear();
                otrosPagos.getOtroPago().addAll(listOtroPagos);

                nomina.setReceptor(receptorNom);
                nomina.setOtrosPagos(otrosPagos);
                listaComplementos.add(nomina);

                //complemento.setAny(listaComplementos);
                complemento.getAny().clear();
                complemento.getAny().addAll(listaComplementos);

                complementos.add(complemento);
                //comprobante.setComplemento(complementos);
                comprobante.getComplemento().clear();
                comprobante.getComplemento().addAll(complementos);

                JAXBContext jAXBContext = JAXBContext.newInstance(Comprobante.class);
                Marshaller marshaller = jAXBContext.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                //marshaller.setProperty("com.sun.xml.internal.bind.namespacePrefixMapper", SPACE_MAPPER);
                marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, LOCATION2);

                StringWriter sw = new StringWriter();
                marshaller.marshal(comprobante, sw);
                String xmlEnvio = sw.toString();

                Reader reader = new StringReader(xmlEnvio);
                StreamSource sourceXML = new StreamSource(reader);

                TransformerFactory tFactory = TransformerFactory.newInstance();
                Transformer transformer = tFactory.newTransformer(formatoCadenaOriginal);

                StringWriter writer = new StringWriter();
                StreamResult result = new StreamResult(writer);
                transformer.transform(sourceXML, result);

                if (compNomina.getUuid() == null) {
                    //if (compNomina.getUuid() != null) { // Dejar pasar timbrados para pruebas
                    System.out.println("UUID NOT FOUND");
                    // Aquí estaba el bloue 1258 - 1270
                    Encriptar.setKeyFilename(key_filename);
                    comprobante.setSello(Encriptar.rsaTexto(writer.toString(), compNomina.getCertificado().getPassword()));

                    StringWriter sw3 = new StringWriter();
                    marshaller.marshal(comprobante, sw3);
                    marshaller.marshal(comprobante, System.out); // Display XML
                    String xmlEnvio3 = sw3.toString();

                    xmlEnvio3 = xmlEnvio3.replaceAll(" standalone=\"yes\"", "");
                    //xmlEnvio3 = xmlEnvio3.replaceAll(" xmlns:edocta=\"https://www.garante.mx/cfd/addenda/edocta\"", "");
                    //xmlEnvio3 = xmlEnvio3.replaceAll(" xmlns:tfd=\"http://www.sat.gob.mx/TimbreFiscalDigital\"", "");
                    //xmlEnvio3 = xmlEnvio3.replaceAll(" xmlns:nomina12=\"http://www.sat.gob.mx/nomina12\"", "");
                    //xmlEnvio3 = xmlEnvio3.replaceAll(" http://www.sat.gob.mx/sitio_internet/cfd/nomina/nomina12.xsd", "");

                    //xmlEnvio3 = xmlEnvio3.replaceAll("<nomina12:Nomina", "<nomina12:Nomina xmlns:nomina12=\"http://www.sat.gob.mx/nomina12\"");
                    //System.out.println(xmlEnvio3);
                    String referencia = rfc + format3.format(fechaHoy);
                    System.out.println("Referencia: " + referencia);
                    //System.out.println(xmlEnvio3);
                    //System.exit(0);
                    IWSCFDI33 servicio = new WSCFDI33().getSoapHttpEndpointHttps();
                    RespuestaTFD33 rtfd = null;
                    rtfd = servicio.timbrarCFDI(user, password, xmlEnvio3, referencia);

                    if (rtfd.isOperacionExitosa()) {
                        Timbre33 timbre33 = rtfd.getTimbre().getValue();
                        System.out.println(rtfd.getXMLResultado());
                        System.out.println("Timbrado - UUID: " + rtfd.getTimbre().getValue().getUUID());
                        compNomina.setFechaTimbre(Fecha.getXMLGregorianCalendar(timbre33.getFechaTimbrado()));
                        compNomina.setUuid(timbre33.getUUID().getValue());
                        compNomina.setSelloCFD(timbre33.getSelloCFD().getValue());
                        compNomina.setnCertificado(timbre33.getNumeroCertificadoSAT().getValue());
                        compNomina.setSelloSAT(timbre33.getSelloSAT().getValue());

                        // insertar en BD
                        compNomina.setFechaNomina(format.format(fechaHoy));
                        compNomina.setClaveContrato(contrato.getClave_contrato());
                        compNomina.setRfc(rfc);
                        compNomina.setTotal(comprobante.getTotal().doubleValue());
                        compNominaDAO.insert(compNomina);
                    } else {
                        System.out.println("Error web service RFC -> " + rfc);
                        try {
                            System.out.println("Error edoCta -> " + contrato.getClave_contrato() + " ");
                        } catch (NullPointerException e) {
                            System.out.println("Error edoCta -> NULL");
                        }
                        System.out.println("CodigoRespuesta: " + rtfd.getCodigoRespuesta().getValue());
                        System.out.println("MensajeError: " + rtfd.getMensajeError().getValue());
                        guardaError(lErrores, new ErroresXML(rfc, format.format(new Date()), rtfd.getMensajeError().getValue()));
                    }

                } else {
                    System.out.println("UUID FOUND");
                    comprobante.setSello(compNomina.getSelloCFD());
                }

                //if (totalTimbrados == rfcs.size()) {
                TimbreFiscalDigital timbre = new TimbreFiscalDigital();
                timbre.setVersion("1.1");
                timbre.setRfcProvCertif(compNomina.getRfcProv());
                timbre.setSelloSAT(compNomina.getSelloSAT());
                timbre.setNoCertificadoSAT(compNomina.getnCertificado());
                timbre.setSelloCFD(compNomina.getSelloCFD());
                timbre.setFechaTimbrado(Fecha.crearXMLGregorianCalendar(compNomina.getFechaTimbre()));
                timbre.setUUID(compNomina.getUuid());

                StringWriter sw2 = new StringWriter();
                marshaller.marshal(timbre, sw2);
                String xmlEnvio2 = sw2.toString();

                Reader reader2 = new StringReader(xmlEnvio2);
                StreamSource sourceXML2 = new StreamSource(reader2);

                TransformerFactory tFactory2 = TransformerFactory.newInstance();
                Transformer transformer2 = tFactory2.newTransformer(formatoCadenaOriginalTimbre);

                StringWriter writer2 = new StringWriter();
                StreamResult result2 = new StreamResult(writer2);
                transformer2.transform(sourceXML2, result2);

                String cadorigen = "||1.1|"
                        + compNomina.getUuid() + "|"
                        + Fecha.crearXMLGregorianCalendar(compNomina.getFechaTimbre()) + "|"
                        + compNomina.getRfcProv() + "|"
                        + "ValorDelAtributoLeyenda|"
                        + compNomina.getSelloCFD() + "|"
                        + compNomina.getnCertificado() + "||";
                System.out.println("Cadena Original: " + cadorigen);

                listaComplementos.add(timbre);
                //complemento.setAny(listaComplementos);
                complemento.getAny().clear();
                complemento.getAny().addAll(listaComplementos);

                String fecha = format2.format(fechaHoy);
                String salida = File.separator + "inetpub" + File.separator + "ftproot" + File.separator + "CFDI" + File.separator + rfc + File.separator + c2.get(Calendar.YEAR) + File.separator;
                File carpetas = new File(salida);
                carpetas.mkdirs();

                marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, LOCATION2Final);
                marshaller.marshal(comprobante, new File(salida + meses[c2.get(Calendar.MONTH)] + ".xml"));

                CreaPDF.nomina(comprobante, timbre, receptor, emisor, emisorNom, receptorNom, nomina, movimientos, concepto1, listOtroPagos, salida + meses[c2.get(Calendar.MONTH)], cadorigen, contrato.getNombre_cliente());

                /////////////////////////////////////////////////////////////////////////////////////////////////// CREACION DE ZIP
                File files[] = new File[2];
                files[0] = new File(salida + meses[c2.get(Calendar.MONTH)] + ".xml");
                files[1] = new File(salida + meses[c2.get(Calendar.MONTH)] + ".pdf");

                File zip = new File(File.separator + "inetpub" + File.separator + "ftproot" + File.separator + "CFDI" + File.separator + receptor.getRfc() + File.separator + fecha.substring(0, 4) + File.separator + Fecha.nombreMes(fecha.substring(4, 6)) + ".zip");
                try {
                    Empaquetar.addFilesToExistingZip2(zip, files);
                    if (!Empaquetar.eliminaR(files[0])) {
                        System.out.println("Error al eliminar " + carpetas.getAbsolutePath());
                    }
                    if (!Empaquetar.eliminaR(files[1])) {
                        System.out.println("Error al eliminar " + carpetas.getAbsolutePath());
                    }
                } catch (IOException e) {
                    System.out.println("Error: " + e.getMessage());
                }
                //}

            } catch (JAXBException | TransformerException ex) {
                Logger.getLogger(CreaXML.class.getName()).log(Level.SEVERE, null, ex);
                erroresxml++;
                if (!rfcErrores.contains(rfcActual)) {
                    rfcErrores.add(rfcActual);
                }
                guardaError(lErrores, new ErroresXML(rfc, format.format(new Date()), ex.getMessage()));
            }

        }
        ReportesExcel reportes = new ReportesExcel();
        reportes.generaExcel("NOMINA_" + format4.format(fechaHoy), lErrores);
    }

    private static void guardaError(List<ErroresXML> lErrores, ErroresXML error) {
        boolean registrado = false;
        for (ErroresXML e : lErrores) {
            if (error.equals(e)) {
                registrado = true;
                break;
            }
        }
        if (!registrado) {
            lErrores.add(error);
        }
    }
}
