
package org.datacontract.schemas._2004._07.tes_tfd_v33;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.datacontract.schemas._2004._07.tes_tfd_v33 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _RespuestaTFD33_QNAME = new QName("http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", "RespuestaTFD33");
    private final static QName _Timbre33_QNAME = new QName("http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", "Timbre33");
    private final static QName _RespuestaCancelacion_QNAME = new QName("http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", "RespuestaCancelacion");
    private final static QName _ArrayOfDetalleCancelacion_QNAME = new QName("http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", "ArrayOfDetalleCancelacion");
    private final static QName _DetalleCancelacion_QNAME = new QName("http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", "DetalleCancelacion");
    private final static QName _RespuestaCancelacionAsincrona_QNAME = new QName("http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", "RespuestaCancelacionAsincrona");
    private final static QName _RespuestaEstatusCancelacionAsincrona_QNAME = new QName("http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", "RespuestaEstatusCancelacionAsincrona");
    private final static QName _RespuestaCreditos_QNAME = new QName("http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", "RespuestaCreditos");
    private final static QName _ArrayOfDetallesPaqueteCreditos_QNAME = new QName("http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", "ArrayOfDetallesPaqueteCreditos");
    private final static QName _DetallesPaqueteCreditos_QNAME = new QName("http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", "DetallesPaqueteCreditos");
    private final static QName _RespuestaReporte_QNAME = new QName("http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", "RespuestaReporte");
    private final static QName _ArrayOfRegistroTimbre_QNAME = new QName("http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", "ArrayOfRegistroTimbre");
    private final static QName _RegistroTimbre_QNAME = new QName("http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", "RegistroTimbre");
    private final static QName _RespuestaValidacionRFC_QNAME = new QName("http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", "RespuestaValidacionRFC");
    private final static QName _RegistroTimbreEstado_QNAME = new QName("http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", "Estado");
    private final static QName _RegistroTimbreRFCEmisor_QNAME = new QName("http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", "RFCEmisor");
    private final static QName _RegistroTimbreRFCReceptor_QNAME = new QName("http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", "RFCReceptor");
    private final static QName _RegistroTimbreUUID_QNAME = new QName("http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", "UUID");
    private final static QName _DetallesPaqueteCreditosFechaActivacion_QNAME = new QName("http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", "FechaActivacion");
    private final static QName _DetallesPaqueteCreditosFechaVencimiento_QNAME = new QName("http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", "FechaVencimiento");
    private final static QName _DetallesPaqueteCreditosPaquete_QNAME = new QName("http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", "Paquete");
    private final static QName _DetalleCancelacionCodigoResultado_QNAME = new QName("http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", "CodigoResultado");
    private final static QName _DetalleCancelacionMensajeResultado_QNAME = new QName("http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", "MensajeResultado");
    private final static QName _Timbre33NumeroCertificadoSAT_QNAME = new QName("http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", "NumeroCertificadoSAT");
    private final static QName _Timbre33SelloCFD_QNAME = new QName("http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", "SelloCFD");
    private final static QName _Timbre33SelloSAT_QNAME = new QName("http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", "SelloSAT");
    private final static QName _RespuestaValidacionRFCMensajeError_QNAME = new QName("http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", "MensajeError");
    private final static QName _RespuestaValidacionRFCRFC_QNAME = new QName("http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", "RFC");
    private final static QName _RespuestaReporteListaComprobantes_QNAME = new QName("http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", "ListaComprobantes");
    private final static QName _RespuestaCreditosPaquetes_QNAME = new QName("http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", "Paquetes");
    private final static QName _RespuestaEstatusCancelacionAsincronaEstatus_QNAME = new QName("http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", "Estatus");
    private final static QName _RespuestaEstatusCancelacionAsincronaReferencia_QNAME = new QName("http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", "Referencia");
    private final static QName _RespuestaEstatusCancelacionAsincronaXMLAcuse_QNAME = new QName("http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", "XMLAcuse");
    private final static QName _RespuestaCancelacionAsincronaDetallesCancelacion_QNAME = new QName("http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", "DetallesCancelacion");
    private final static QName _RespuestaCancelacionMensajeErrorDetallado_QNAME = new QName("http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", "MensajeErrorDetallado");
    private final static QName _RespuestaTFD33CodigoConfirmacion_QNAME = new QName("http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", "CodigoConfirmacion");
    private final static QName _RespuestaTFD33CodigoRespuesta_QNAME = new QName("http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", "CodigoRespuesta");
    private final static QName _RespuestaTFD33PDFResultado_QNAME = new QName("http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", "PDFResultado");
    private final static QName _RespuestaTFD33Timbre_QNAME = new QName("http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", "Timbre");
    private final static QName _RespuestaTFD33XMLResultado_QNAME = new QName("http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", "XMLResultado");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.datacontract.schemas._2004._07.tes_tfd_v33
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RespuestaTFD33 }
     * 
     */
    public RespuestaTFD33 createRespuestaTFD33() {
        return new RespuestaTFD33();
    }

    /**
     * Create an instance of {@link RespuestaCancelacion }
     * 
     */
    public RespuestaCancelacion createRespuestaCancelacion() {
        return new RespuestaCancelacion();
    }

    /**
     * Create an instance of {@link RespuestaCancelacionAsincrona }
     * 
     */
    public RespuestaCancelacionAsincrona createRespuestaCancelacionAsincrona() {
        return new RespuestaCancelacionAsincrona();
    }

    /**
     * Create an instance of {@link RespuestaEstatusCancelacionAsincrona }
     * 
     */
    public RespuestaEstatusCancelacionAsincrona createRespuestaEstatusCancelacionAsincrona() {
        return new RespuestaEstatusCancelacionAsincrona();
    }

    /**
     * Create an instance of {@link RespuestaCreditos }
     * 
     */
    public RespuestaCreditos createRespuestaCreditos() {
        return new RespuestaCreditos();
    }

    /**
     * Create an instance of {@link RespuestaReporte }
     * 
     */
    public RespuestaReporte createRespuestaReporte() {
        return new RespuestaReporte();
    }

    /**
     * Create an instance of {@link RespuestaValidacionRFC }
     * 
     */
    public RespuestaValidacionRFC createRespuestaValidacionRFC() {
        return new RespuestaValidacionRFC();
    }

    /**
     * Create an instance of {@link Timbre33 }
     * 
     */
    public Timbre33 createTimbre33() {
        return new Timbre33();
    }

    /**
     * Create an instance of {@link ArrayOfDetalleCancelacion }
     * 
     */
    public ArrayOfDetalleCancelacion createArrayOfDetalleCancelacion() {
        return new ArrayOfDetalleCancelacion();
    }

    /**
     * Create an instance of {@link DetalleCancelacion }
     * 
     */
    public DetalleCancelacion createDetalleCancelacion() {
        return new DetalleCancelacion();
    }

    /**
     * Create an instance of {@link ArrayOfDetallesPaqueteCreditos }
     * 
     */
    public ArrayOfDetallesPaqueteCreditos createArrayOfDetallesPaqueteCreditos() {
        return new ArrayOfDetallesPaqueteCreditos();
    }

    /**
     * Create an instance of {@link DetallesPaqueteCreditos }
     * 
     */
    public DetallesPaqueteCreditos createDetallesPaqueteCreditos() {
        return new DetallesPaqueteCreditos();
    }

    /**
     * Create an instance of {@link ArrayOfRegistroTimbre }
     * 
     */
    public ArrayOfRegistroTimbre createArrayOfRegistroTimbre() {
        return new ArrayOfRegistroTimbre();
    }

    /**
     * Create an instance of {@link RegistroTimbre }
     * 
     */
    public RegistroTimbre createRegistroTimbre() {
        return new RegistroTimbre();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RespuestaTFD33 }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link RespuestaTFD33 }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", name = "RespuestaTFD33")
    public JAXBElement<RespuestaTFD33> createRespuestaTFD33(RespuestaTFD33 value) {
        return new JAXBElement<RespuestaTFD33>(_RespuestaTFD33_QNAME, RespuestaTFD33 .class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Timbre33 }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Timbre33 }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", name = "Timbre33")
    public JAXBElement<Timbre33> createTimbre33(Timbre33 value) {
        return new JAXBElement<Timbre33>(_Timbre33_QNAME, Timbre33 .class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RespuestaCancelacion }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link RespuestaCancelacion }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", name = "RespuestaCancelacion")
    public JAXBElement<RespuestaCancelacion> createRespuestaCancelacion(RespuestaCancelacion value) {
        return new JAXBElement<RespuestaCancelacion>(_RespuestaCancelacion_QNAME, RespuestaCancelacion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfDetalleCancelacion }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfDetalleCancelacion }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", name = "ArrayOfDetalleCancelacion")
    public JAXBElement<ArrayOfDetalleCancelacion> createArrayOfDetalleCancelacion(ArrayOfDetalleCancelacion value) {
        return new JAXBElement<ArrayOfDetalleCancelacion>(_ArrayOfDetalleCancelacion_QNAME, ArrayOfDetalleCancelacion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DetalleCancelacion }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DetalleCancelacion }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", name = "DetalleCancelacion")
    public JAXBElement<DetalleCancelacion> createDetalleCancelacion(DetalleCancelacion value) {
        return new JAXBElement<DetalleCancelacion>(_DetalleCancelacion_QNAME, DetalleCancelacion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RespuestaCancelacionAsincrona }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link RespuestaCancelacionAsincrona }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", name = "RespuestaCancelacionAsincrona")
    public JAXBElement<RespuestaCancelacionAsincrona> createRespuestaCancelacionAsincrona(RespuestaCancelacionAsincrona value) {
        return new JAXBElement<RespuestaCancelacionAsincrona>(_RespuestaCancelacionAsincrona_QNAME, RespuestaCancelacionAsincrona.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RespuestaEstatusCancelacionAsincrona }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link RespuestaEstatusCancelacionAsincrona }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", name = "RespuestaEstatusCancelacionAsincrona")
    public JAXBElement<RespuestaEstatusCancelacionAsincrona> createRespuestaEstatusCancelacionAsincrona(RespuestaEstatusCancelacionAsincrona value) {
        return new JAXBElement<RespuestaEstatusCancelacionAsincrona>(_RespuestaEstatusCancelacionAsincrona_QNAME, RespuestaEstatusCancelacionAsincrona.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RespuestaCreditos }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link RespuestaCreditos }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", name = "RespuestaCreditos")
    public JAXBElement<RespuestaCreditos> createRespuestaCreditos(RespuestaCreditos value) {
        return new JAXBElement<RespuestaCreditos>(_RespuestaCreditos_QNAME, RespuestaCreditos.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfDetallesPaqueteCreditos }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfDetallesPaqueteCreditos }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", name = "ArrayOfDetallesPaqueteCreditos")
    public JAXBElement<ArrayOfDetallesPaqueteCreditos> createArrayOfDetallesPaqueteCreditos(ArrayOfDetallesPaqueteCreditos value) {
        return new JAXBElement<ArrayOfDetallesPaqueteCreditos>(_ArrayOfDetallesPaqueteCreditos_QNAME, ArrayOfDetallesPaqueteCreditos.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DetallesPaqueteCreditos }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DetallesPaqueteCreditos }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", name = "DetallesPaqueteCreditos")
    public JAXBElement<DetallesPaqueteCreditos> createDetallesPaqueteCreditos(DetallesPaqueteCreditos value) {
        return new JAXBElement<DetallesPaqueteCreditos>(_DetallesPaqueteCreditos_QNAME, DetallesPaqueteCreditos.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RespuestaReporte }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link RespuestaReporte }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", name = "RespuestaReporte")
    public JAXBElement<RespuestaReporte> createRespuestaReporte(RespuestaReporte value) {
        return new JAXBElement<RespuestaReporte>(_RespuestaReporte_QNAME, RespuestaReporte.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfRegistroTimbre }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfRegistroTimbre }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", name = "ArrayOfRegistroTimbre")
    public JAXBElement<ArrayOfRegistroTimbre> createArrayOfRegistroTimbre(ArrayOfRegistroTimbre value) {
        return new JAXBElement<ArrayOfRegistroTimbre>(_ArrayOfRegistroTimbre_QNAME, ArrayOfRegistroTimbre.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegistroTimbre }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link RegistroTimbre }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", name = "RegistroTimbre")
    public JAXBElement<RegistroTimbre> createRegistroTimbre(RegistroTimbre value) {
        return new JAXBElement<RegistroTimbre>(_RegistroTimbre_QNAME, RegistroTimbre.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RespuestaValidacionRFC }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link RespuestaValidacionRFC }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", name = "RespuestaValidacionRFC")
    public JAXBElement<RespuestaValidacionRFC> createRespuestaValidacionRFC(RespuestaValidacionRFC value) {
        return new JAXBElement<RespuestaValidacionRFC>(_RespuestaValidacionRFC_QNAME, RespuestaValidacionRFC.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", name = "Estado", scope = RegistroTimbre.class)
    public JAXBElement<String> createRegistroTimbreEstado(String value) {
        return new JAXBElement<String>(_RegistroTimbreEstado_QNAME, String.class, RegistroTimbre.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", name = "RFCEmisor", scope = RegistroTimbre.class)
    public JAXBElement<String> createRegistroTimbreRFCEmisor(String value) {
        return new JAXBElement<String>(_RegistroTimbreRFCEmisor_QNAME, String.class, RegistroTimbre.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", name = "RFCReceptor", scope = RegistroTimbre.class)
    public JAXBElement<String> createRegistroTimbreRFCReceptor(String value) {
        return new JAXBElement<String>(_RegistroTimbreRFCReceptor_QNAME, String.class, RegistroTimbre.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", name = "UUID", scope = RegistroTimbre.class)
    public JAXBElement<String> createRegistroTimbreUUID(String value) {
        return new JAXBElement<String>(_RegistroTimbreUUID_QNAME, String.class, RegistroTimbre.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", name = "FechaActivacion", scope = DetallesPaqueteCreditos.class)
    public JAXBElement<XMLGregorianCalendar> createDetallesPaqueteCreditosFechaActivacion(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_DetallesPaqueteCreditosFechaActivacion_QNAME, XMLGregorianCalendar.class, DetallesPaqueteCreditos.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", name = "FechaVencimiento", scope = DetallesPaqueteCreditos.class)
    public JAXBElement<XMLGregorianCalendar> createDetallesPaqueteCreditosFechaVencimiento(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_DetallesPaqueteCreditosFechaVencimiento_QNAME, XMLGregorianCalendar.class, DetallesPaqueteCreditos.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", name = "Paquete", scope = DetallesPaqueteCreditos.class)
    public JAXBElement<String> createDetallesPaqueteCreditosPaquete(String value) {
        return new JAXBElement<String>(_DetallesPaqueteCreditosPaquete_QNAME, String.class, DetallesPaqueteCreditos.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", name = "CodigoResultado", scope = DetalleCancelacion.class)
    public JAXBElement<String> createDetalleCancelacionCodigoResultado(String value) {
        return new JAXBElement<String>(_DetalleCancelacionCodigoResultado_QNAME, String.class, DetalleCancelacion.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", name = "MensajeResultado", scope = DetalleCancelacion.class)
    public JAXBElement<String> createDetalleCancelacionMensajeResultado(String value) {
        return new JAXBElement<String>(_DetalleCancelacionMensajeResultado_QNAME, String.class, DetalleCancelacion.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", name = "UUID", scope = DetalleCancelacion.class)
    public JAXBElement<String> createDetalleCancelacionUUID(String value) {
        return new JAXBElement<String>(_RegistroTimbreUUID_QNAME, String.class, DetalleCancelacion.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", name = "Estado", scope = Timbre33 .class)
    public JAXBElement<String> createTimbre33Estado(String value) {
        return new JAXBElement<String>(_RegistroTimbreEstado_QNAME, String.class, Timbre33 .class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", name = "NumeroCertificadoSAT", scope = Timbre33 .class)
    public JAXBElement<String> createTimbre33NumeroCertificadoSAT(String value) {
        return new JAXBElement<String>(_Timbre33NumeroCertificadoSAT_QNAME, String.class, Timbre33 .class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", name = "SelloCFD", scope = Timbre33 .class)
    public JAXBElement<String> createTimbre33SelloCFD(String value) {
        return new JAXBElement<String>(_Timbre33SelloCFD_QNAME, String.class, Timbre33 .class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", name = "SelloSAT", scope = Timbre33 .class)
    public JAXBElement<String> createTimbre33SelloSAT(String value) {
        return new JAXBElement<String>(_Timbre33SelloSAT_QNAME, String.class, Timbre33 .class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", name = "UUID", scope = Timbre33 .class)
    public JAXBElement<String> createTimbre33UUID(String value) {
        return new JAXBElement<String>(_RegistroTimbreUUID_QNAME, String.class, Timbre33 .class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", name = "MensajeError", scope = RespuestaValidacionRFC.class)
    public JAXBElement<String> createRespuestaValidacionRFCMensajeError(String value) {
        return new JAXBElement<String>(_RespuestaValidacionRFCMensajeError_QNAME, String.class, RespuestaValidacionRFC.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", name = "RFC", scope = RespuestaValidacionRFC.class)
    public JAXBElement<String> createRespuestaValidacionRFCRFC(String value) {
        return new JAXBElement<String>(_RespuestaValidacionRFCRFC_QNAME, String.class, RespuestaValidacionRFC.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfRegistroTimbre }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfRegistroTimbre }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", name = "ListaComprobantes", scope = RespuestaReporte.class)
    public JAXBElement<ArrayOfRegistroTimbre> createRespuestaReporteListaComprobantes(ArrayOfRegistroTimbre value) {
        return new JAXBElement<ArrayOfRegistroTimbre>(_RespuestaReporteListaComprobantes_QNAME, ArrayOfRegistroTimbre.class, RespuestaReporte.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", name = "MensajeError", scope = RespuestaReporte.class)
    public JAXBElement<String> createRespuestaReporteMensajeError(String value) {
        return new JAXBElement<String>(_RespuestaValidacionRFCMensajeError_QNAME, String.class, RespuestaReporte.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", name = "MensajeError", scope = RespuestaCreditos.class)
    public JAXBElement<String> createRespuestaCreditosMensajeError(String value) {
        return new JAXBElement<String>(_RespuestaValidacionRFCMensajeError_QNAME, String.class, RespuestaCreditos.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfDetallesPaqueteCreditos }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfDetallesPaqueteCreditos }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", name = "Paquetes", scope = RespuestaCreditos.class)
    public JAXBElement<ArrayOfDetallesPaqueteCreditos> createRespuestaCreditosPaquetes(ArrayOfDetallesPaqueteCreditos value) {
        return new JAXBElement<ArrayOfDetallesPaqueteCreditos>(_RespuestaCreditosPaquetes_QNAME, ArrayOfDetallesPaqueteCreditos.class, RespuestaCreditos.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", name = "Estatus", scope = RespuestaEstatusCancelacionAsincrona.class)
    public JAXBElement<String> createRespuestaEstatusCancelacionAsincronaEstatus(String value) {
        return new JAXBElement<String>(_RespuestaEstatusCancelacionAsincronaEstatus_QNAME, String.class, RespuestaEstatusCancelacionAsincrona.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", name = "MensajeError", scope = RespuestaEstatusCancelacionAsincrona.class)
    public JAXBElement<String> createRespuestaEstatusCancelacionAsincronaMensajeError(String value) {
        return new JAXBElement<String>(_RespuestaValidacionRFCMensajeError_QNAME, String.class, RespuestaEstatusCancelacionAsincrona.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", name = "Referencia", scope = RespuestaEstatusCancelacionAsincrona.class)
    public JAXBElement<String> createRespuestaEstatusCancelacionAsincronaReferencia(String value) {
        return new JAXBElement<String>(_RespuestaEstatusCancelacionAsincronaReferencia_QNAME, String.class, RespuestaEstatusCancelacionAsincrona.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", name = "XMLAcuse", scope = RespuestaEstatusCancelacionAsincrona.class)
    public JAXBElement<String> createRespuestaEstatusCancelacionAsincronaXMLAcuse(String value) {
        return new JAXBElement<String>(_RespuestaEstatusCancelacionAsincronaXMLAcuse_QNAME, String.class, RespuestaEstatusCancelacionAsincrona.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfDetalleCancelacion }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfDetalleCancelacion }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", name = "DetallesCancelacion", scope = RespuestaCancelacionAsincrona.class)
    public JAXBElement<ArrayOfDetalleCancelacion> createRespuestaCancelacionAsincronaDetallesCancelacion(ArrayOfDetalleCancelacion value) {
        return new JAXBElement<ArrayOfDetalleCancelacion>(_RespuestaCancelacionAsincronaDetallesCancelacion_QNAME, ArrayOfDetalleCancelacion.class, RespuestaCancelacionAsincrona.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", name = "MensajeError", scope = RespuestaCancelacionAsincrona.class)
    public JAXBElement<String> createRespuestaCancelacionAsincronaMensajeError(String value) {
        return new JAXBElement<String>(_RespuestaValidacionRFCMensajeError_QNAME, String.class, RespuestaCancelacionAsincrona.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", name = "Referencia", scope = RespuestaCancelacionAsincrona.class)
    public JAXBElement<String> createRespuestaCancelacionAsincronaReferencia(String value) {
        return new JAXBElement<String>(_RespuestaEstatusCancelacionAsincronaReferencia_QNAME, String.class, RespuestaCancelacionAsincrona.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfDetalleCancelacion }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfDetalleCancelacion }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", name = "DetallesCancelacion", scope = RespuestaCancelacion.class)
    public JAXBElement<ArrayOfDetalleCancelacion> createRespuestaCancelacionDetallesCancelacion(ArrayOfDetalleCancelacion value) {
        return new JAXBElement<ArrayOfDetalleCancelacion>(_RespuestaCancelacionAsincronaDetallesCancelacion_QNAME, ArrayOfDetalleCancelacion.class, RespuestaCancelacion.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", name = "MensajeError", scope = RespuestaCancelacion.class)
    public JAXBElement<String> createRespuestaCancelacionMensajeError(String value) {
        return new JAXBElement<String>(_RespuestaValidacionRFCMensajeError_QNAME, String.class, RespuestaCancelacion.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", name = "MensajeErrorDetallado", scope = RespuestaCancelacion.class)
    public JAXBElement<String> createRespuestaCancelacionMensajeErrorDetallado(String value) {
        return new JAXBElement<String>(_RespuestaCancelacionMensajeErrorDetallado_QNAME, String.class, RespuestaCancelacion.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", name = "XMLAcuse", scope = RespuestaCancelacion.class)
    public JAXBElement<String> createRespuestaCancelacionXMLAcuse(String value) {
        return new JAXBElement<String>(_RespuestaEstatusCancelacionAsincronaXMLAcuse_QNAME, String.class, RespuestaCancelacion.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", name = "CodigoConfirmacion", scope = RespuestaTFD33 .class)
    public JAXBElement<String> createRespuestaTFD33CodigoConfirmacion(String value) {
        return new JAXBElement<String>(_RespuestaTFD33CodigoConfirmacion_QNAME, String.class, RespuestaTFD33 .class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", name = "CodigoRespuesta", scope = RespuestaTFD33 .class)
    public JAXBElement<String> createRespuestaTFD33CodigoRespuesta(String value) {
        return new JAXBElement<String>(_RespuestaTFD33CodigoRespuesta_QNAME, String.class, RespuestaTFD33 .class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", name = "MensajeError", scope = RespuestaTFD33 .class)
    public JAXBElement<String> createRespuestaTFD33MensajeError(String value) {
        return new JAXBElement<String>(_RespuestaValidacionRFCMensajeError_QNAME, String.class, RespuestaTFD33 .class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", name = "MensajeErrorDetallado", scope = RespuestaTFD33 .class)
    public JAXBElement<String> createRespuestaTFD33MensajeErrorDetallado(String value) {
        return new JAXBElement<String>(_RespuestaCancelacionMensajeErrorDetallado_QNAME, String.class, RespuestaTFD33 .class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", name = "PDFResultado", scope = RespuestaTFD33 .class)
    public JAXBElement<String> createRespuestaTFD33PDFResultado(String value) {
        return new JAXBElement<String>(_RespuestaTFD33PDFResultado_QNAME, String.class, RespuestaTFD33 .class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Timbre33 }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Timbre33 }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", name = "Timbre", scope = RespuestaTFD33 .class)
    public JAXBElement<Timbre33> createRespuestaTFD33Timbre(Timbre33 value) {
        return new JAXBElement<Timbre33>(_RespuestaTFD33Timbre_QNAME, Timbre33 .class, RespuestaTFD33 .class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", name = "XMLResultado", scope = RespuestaTFD33 .class)
    public JAXBElement<String> createRespuestaTFD33XMLResultado(String value) {
        return new JAXBElement<String>(_RespuestaTFD33XMLResultado_QNAME, String.class, RespuestaTFD33 .class, value);
    }

}
