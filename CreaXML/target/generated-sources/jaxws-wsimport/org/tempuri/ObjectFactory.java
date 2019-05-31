
package org.tempuri;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import com.microsoft.schemas._2003._10.serialization.arrays.ArrayOfstring;
import org.datacontract.schemas._2004._07.tes_tfd_v33.RespuestaCancelacion;
import org.datacontract.schemas._2004._07.tes_tfd_v33.RespuestaCancelacionAsincrona;
import org.datacontract.schemas._2004._07.tes_tfd_v33.RespuestaCreditos;
import org.datacontract.schemas._2004._07.tes_tfd_v33.RespuestaEstatusCancelacionAsincrona;
import org.datacontract.schemas._2004._07.tes_tfd_v33.RespuestaReporte;
import org.datacontract.schemas._2004._07.tes_tfd_v33.RespuestaTFD33;
import org.datacontract.schemas._2004._07.tes_tfd_v33.RespuestaValidacionRFC;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.tempuri package. 
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

    private final static QName _EstatusCancelacionAsincronaResponseEstatusCancelacionAsincronaResult_QNAME = new QName("http://tempuri.org/", "EstatusCancelacionAsincronaResult");
    private final static QName _ObtenerAcuseCancelacionPassword_QNAME = new QName("http://tempuri.org/", "password");
    private final static QName _ObtenerAcuseCancelacionUsuario_QNAME = new QName("http://tempuri.org/", "usuario");
    private final static QName _ObtenerAcuseCancelacionUUID_QNAME = new QName("http://tempuri.org/", "uUID");
    private final static QName _ObtenerPDFResponseObtenerPDFResult_QNAME = new QName("http://tempuri.org/", "ObtenerPDFResult");
    private final static QName _TimbrarCFDIResponseTimbrarCFDIResult_QNAME = new QName("http://tempuri.org/", "TimbrarCFDIResult");
    private final static QName _ConsultarComplementoTimbreResponseConsultarComplementoTimbreResult_QNAME = new QName("http://tempuri.org/", "ConsultarComplementoTimbreResult");
    private final static QName _CancelacionAsincronaPasswordClavePrivada_QNAME = new QName("http://tempuri.org/", "passwordClavePrivada");
    private final static QName _CancelacionAsincronaClavePrivadaBase64_QNAME = new QName("http://tempuri.org/", "clavePrivada_Base64");
    private final static QName _CancelacionAsincronaListaCFDI_QNAME = new QName("http://tempuri.org/", "listaCFDI");
    private final static QName _CancelacionAsincronaRFCEmisor_QNAME = new QName("http://tempuri.org/", "rFCEmisor");
    private final static QName _ValidarRFCResponseValidarRFCResult_QNAME = new QName("http://tempuri.org/", "ValidarRFCResult");
    private final static QName _ObtenerAcuseEnvioResponseObtenerAcuseEnvioResult_QNAME = new QName("http://tempuri.org/", "ObtenerAcuseEnvioResult");
    private final static QName _CambiarPasswordResponseCambiarPasswordResult_QNAME = new QName("http://tempuri.org/", "CambiarPasswordResult");
    private final static QName _ObtenerAcuseCancelacionResponseObtenerAcuseCancelacionResult_QNAME = new QName("http://tempuri.org/", "ObtenerAcuseCancelacionResult");
    private final static QName _ConsultarTimbrePorReferenciaReferencia_QNAME = new QName("http://tempuri.org/", "referencia");
    private final static QName _ValidarRFCRfc_QNAME = new QName("http://tempuri.org/", "rfc");
    private final static QName _TimbrarCFDICadenaXML_QNAME = new QName("http://tempuri.org/", "cadenaXML");
    private final static QName _CancelarCFDIResponseCancelarCFDIResult_QNAME = new QName("http://tempuri.org/", "CancelarCFDIResult");
    private final static QName _ObtenerPDFLogoBase64_QNAME = new QName("http://tempuri.org/", "LogoBase64");
    private final static QName _ConsultarComprobantesResponseConsultarComprobantesResult_QNAME = new QName("http://tempuri.org/", "ConsultarComprobantesResult");
    private final static QName _ConsultarCreditosResponseConsultarCreditosResult_QNAME = new QName("http://tempuri.org/", "ConsultarCreditosResult");
    private final static QName _CancelacionAsincronaResponseCancelacionAsincronaResult_QNAME = new QName("http://tempuri.org/", "CancelacionAsincronaResult");
    private final static QName _ConsultarTimbrePorReferenciaResponseConsultarTimbrePorReferenciaResult_QNAME = new QName("http://tempuri.org/", "ConsultarTimbrePorReferenciaResult");
    private final static QName _CambiarPasswordPasswordNuevo_QNAME = new QName("http://tempuri.org/", "passwordNuevo");
    private final static QName _CambiarPasswordPasswordActual_QNAME = new QName("http://tempuri.org/", "passwordActual");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.tempuri
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ObtenerPDF }
     * 
     */
    public ObtenerPDF createObtenerPDF() {
        return new ObtenerPDF();
    }

    /**
     * Create an instance of {@link ObtenerAcuseCancelacion }
     * 
     */
    public ObtenerAcuseCancelacion createObtenerAcuseCancelacion() {
        return new ObtenerAcuseCancelacion();
    }

    /**
     * Create an instance of {@link ObtenerPDFResponse }
     * 
     */
    public ObtenerPDFResponse createObtenerPDFResponse() {
        return new ObtenerPDFResponse();
    }

    /**
     * Create an instance of {@link ConsultarComplementoTimbreResponse }
     * 
     */
    public ConsultarComplementoTimbreResponse createConsultarComplementoTimbreResponse() {
        return new ConsultarComplementoTimbreResponse();
    }

    /**
     * Create an instance of {@link ConsultarComplementoTimbre }
     * 
     */
    public ConsultarComplementoTimbre createConsultarComplementoTimbre() {
        return new ConsultarComplementoTimbre();
    }

    /**
     * Create an instance of {@link TimbrarCFDI }
     * 
     */
    public TimbrarCFDI createTimbrarCFDI() {
        return new TimbrarCFDI();
    }

    /**
     * Create an instance of {@link ConsultarTimbrePorReferencia }
     * 
     */
    public ConsultarTimbrePorReferencia createConsultarTimbrePorReferencia() {
        return new ConsultarTimbrePorReferencia();
    }

    /**
     * Create an instance of {@link ValidarRFC }
     * 
     */
    public ValidarRFC createValidarRFC() {
        return new ValidarRFC();
    }

    /**
     * Create an instance of {@link ValidarRFCResponse }
     * 
     */
    public ValidarRFCResponse createValidarRFCResponse() {
        return new ValidarRFCResponse();
    }

    /**
     * Create an instance of {@link ConsultarCreditos }
     * 
     */
    public ConsultarCreditos createConsultarCreditos() {
        return new ConsultarCreditos();
    }

    /**
     * Create an instance of {@link TimbrarCFDIResponse }
     * 
     */
    public TimbrarCFDIResponse createTimbrarCFDIResponse() {
        return new TimbrarCFDIResponse();
    }

    /**
     * Create an instance of {@link ConsultarTimbrePorReferenciaResponse }
     * 
     */
    public ConsultarTimbrePorReferenciaResponse createConsultarTimbrePorReferenciaResponse() {
        return new ConsultarTimbrePorReferenciaResponse();
    }

    /**
     * Create an instance of {@link EstatusCancelacionAsincrona }
     * 
     */
    public EstatusCancelacionAsincrona createEstatusCancelacionAsincrona() {
        return new EstatusCancelacionAsincrona();
    }

    /**
     * Create an instance of {@link CambiarPassword }
     * 
     */
    public CambiarPassword createCambiarPassword() {
        return new CambiarPassword();
    }

    /**
     * Create an instance of {@link ConsultarCreditosResponse }
     * 
     */
    public ConsultarCreditosResponse createConsultarCreditosResponse() {
        return new ConsultarCreditosResponse();
    }

    /**
     * Create an instance of {@link CancelacionAsincronaResponse }
     * 
     */
    public CancelacionAsincronaResponse createCancelacionAsincronaResponse() {
        return new CancelacionAsincronaResponse();
    }

    /**
     * Create an instance of {@link ObtenerAcuseCancelacionResponse }
     * 
     */
    public ObtenerAcuseCancelacionResponse createObtenerAcuseCancelacionResponse() {
        return new ObtenerAcuseCancelacionResponse();
    }

    /**
     * Create an instance of {@link ObtenerAcuseEnvio }
     * 
     */
    public ObtenerAcuseEnvio createObtenerAcuseEnvio() {
        return new ObtenerAcuseEnvio();
    }

    /**
     * Create an instance of {@link CancelacionAsincrona }
     * 
     */
    public CancelacionAsincrona createCancelacionAsincrona() {
        return new CancelacionAsincrona();
    }

    /**
     * Create an instance of {@link EstatusCancelacionAsincronaResponse }
     * 
     */
    public EstatusCancelacionAsincronaResponse createEstatusCancelacionAsincronaResponse() {
        return new EstatusCancelacionAsincronaResponse();
    }

    /**
     * Create an instance of {@link ObtenerAcuseEnvioResponse }
     * 
     */
    public ObtenerAcuseEnvioResponse createObtenerAcuseEnvioResponse() {
        return new ObtenerAcuseEnvioResponse();
    }

    /**
     * Create an instance of {@link ConsultarComprobantes }
     * 
     */
    public ConsultarComprobantes createConsultarComprobantes() {
        return new ConsultarComprobantes();
    }

    /**
     * Create an instance of {@link ConsultarComprobantesResponse }
     * 
     */
    public ConsultarComprobantesResponse createConsultarComprobantesResponse() {
        return new ConsultarComprobantesResponse();
    }

    /**
     * Create an instance of {@link CancelarCFDI }
     * 
     */
    public CancelarCFDI createCancelarCFDI() {
        return new CancelarCFDI();
    }

    /**
     * Create an instance of {@link CancelarCFDIResponse }
     * 
     */
    public CancelarCFDIResponse createCancelarCFDIResponse() {
        return new CancelarCFDIResponse();
    }

    /**
     * Create an instance of {@link CambiarPasswordResponse }
     * 
     */
    public CambiarPasswordResponse createCambiarPasswordResponse() {
        return new CambiarPasswordResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RespuestaEstatusCancelacionAsincrona }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "EstatusCancelacionAsincronaResult", scope = EstatusCancelacionAsincronaResponse.class)
    public JAXBElement<RespuestaEstatusCancelacionAsincrona> createEstatusCancelacionAsincronaResponseEstatusCancelacionAsincronaResult(RespuestaEstatusCancelacionAsincrona value) {
        return new JAXBElement<RespuestaEstatusCancelacionAsincrona>(_EstatusCancelacionAsincronaResponseEstatusCancelacionAsincronaResult_QNAME, RespuestaEstatusCancelacionAsincrona.class, EstatusCancelacionAsincronaResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "password", scope = ObtenerAcuseCancelacion.class)
    public JAXBElement<String> createObtenerAcuseCancelacionPassword(String value) {
        return new JAXBElement<String>(_ObtenerAcuseCancelacionPassword_QNAME, String.class, ObtenerAcuseCancelacion.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "usuario", scope = ObtenerAcuseCancelacion.class)
    public JAXBElement<String> createObtenerAcuseCancelacionUsuario(String value) {
        return new JAXBElement<String>(_ObtenerAcuseCancelacionUsuario_QNAME, String.class, ObtenerAcuseCancelacion.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "uUID", scope = ObtenerAcuseCancelacion.class)
    public JAXBElement<String> createObtenerAcuseCancelacionUUID(String value) {
        return new JAXBElement<String>(_ObtenerAcuseCancelacionUUID_QNAME, String.class, ObtenerAcuseCancelacion.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RespuestaTFD33 }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "ObtenerPDFResult", scope = ObtenerPDFResponse.class)
    public JAXBElement<RespuestaTFD33> createObtenerPDFResponseObtenerPDFResult(RespuestaTFD33 value) {
        return new JAXBElement<RespuestaTFD33>(_ObtenerPDFResponseObtenerPDFResult_QNAME, RespuestaTFD33 .class, ObtenerPDFResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RespuestaTFD33 }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "TimbrarCFDIResult", scope = TimbrarCFDIResponse.class)
    public JAXBElement<RespuestaTFD33> createTimbrarCFDIResponseTimbrarCFDIResult(RespuestaTFD33 value) {
        return new JAXBElement<RespuestaTFD33>(_TimbrarCFDIResponseTimbrarCFDIResult_QNAME, RespuestaTFD33 .class, TimbrarCFDIResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RespuestaTFD33 }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "ConsultarComplementoTimbreResult", scope = ConsultarComplementoTimbreResponse.class)
    public JAXBElement<RespuestaTFD33> createConsultarComplementoTimbreResponseConsultarComplementoTimbreResult(RespuestaTFD33 value) {
        return new JAXBElement<RespuestaTFD33>(_ConsultarComplementoTimbreResponseConsultarComplementoTimbreResult_QNAME, RespuestaTFD33 .class, ConsultarComplementoTimbreResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "passwordClavePrivada", scope = CancelacionAsincrona.class)
    public JAXBElement<String> createCancelacionAsincronaPasswordClavePrivada(String value) {
        return new JAXBElement<String>(_CancelacionAsincronaPasswordClavePrivada_QNAME, String.class, CancelacionAsincrona.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "password", scope = CancelacionAsincrona.class)
    public JAXBElement<String> createCancelacionAsincronaPassword(String value) {
        return new JAXBElement<String>(_ObtenerAcuseCancelacionPassword_QNAME, String.class, CancelacionAsincrona.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "clavePrivada_Base64", scope = CancelacionAsincrona.class)
    public JAXBElement<String> createCancelacionAsincronaClavePrivadaBase64(String value) {
        return new JAXBElement<String>(_CancelacionAsincronaClavePrivadaBase64_QNAME, String.class, CancelacionAsincrona.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfstring }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "listaCFDI", scope = CancelacionAsincrona.class)
    public JAXBElement<ArrayOfstring> createCancelacionAsincronaListaCFDI(ArrayOfstring value) {
        return new JAXBElement<ArrayOfstring>(_CancelacionAsincronaListaCFDI_QNAME, ArrayOfstring.class, CancelacionAsincrona.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "usuario", scope = CancelacionAsincrona.class)
    public JAXBElement<String> createCancelacionAsincronaUsuario(String value) {
        return new JAXBElement<String>(_ObtenerAcuseCancelacionUsuario_QNAME, String.class, CancelacionAsincrona.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "rFCEmisor", scope = CancelacionAsincrona.class)
    public JAXBElement<String> createCancelacionAsincronaRFCEmisor(String value) {
        return new JAXBElement<String>(_CancelacionAsincronaRFCEmisor_QNAME, String.class, CancelacionAsincrona.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RespuestaValidacionRFC }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "ValidarRFCResult", scope = ValidarRFCResponse.class)
    public JAXBElement<RespuestaValidacionRFC> createValidarRFCResponseValidarRFCResult(RespuestaValidacionRFC value) {
        return new JAXBElement<RespuestaValidacionRFC>(_ValidarRFCResponseValidarRFCResult_QNAME, RespuestaValidacionRFC.class, ValidarRFCResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RespuestaTFD33 }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "ObtenerAcuseEnvioResult", scope = ObtenerAcuseEnvioResponse.class)
    public JAXBElement<RespuestaTFD33> createObtenerAcuseEnvioResponseObtenerAcuseEnvioResult(RespuestaTFD33 value) {
        return new JAXBElement<RespuestaTFD33>(_ObtenerAcuseEnvioResponseObtenerAcuseEnvioResult_QNAME, RespuestaTFD33 .class, ObtenerAcuseEnvioResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RespuestaTFD33 }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "CambiarPasswordResult", scope = CambiarPasswordResponse.class)
    public JAXBElement<RespuestaTFD33> createCambiarPasswordResponseCambiarPasswordResult(RespuestaTFD33 value) {
        return new JAXBElement<RespuestaTFD33>(_CambiarPasswordResponseCambiarPasswordResult_QNAME, RespuestaTFD33 .class, CambiarPasswordResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "password", scope = ConsultarCreditos.class)
    public JAXBElement<String> createConsultarCreditosPassword(String value) {
        return new JAXBElement<String>(_ObtenerAcuseCancelacionPassword_QNAME, String.class, ConsultarCreditos.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "usuario", scope = ConsultarCreditos.class)
    public JAXBElement<String> createConsultarCreditosUsuario(String value) {
        return new JAXBElement<String>(_ObtenerAcuseCancelacionUsuario_QNAME, String.class, ConsultarCreditos.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RespuestaTFD33 }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "ObtenerAcuseCancelacionResult", scope = ObtenerAcuseCancelacionResponse.class)
    public JAXBElement<RespuestaTFD33> createObtenerAcuseCancelacionResponseObtenerAcuseCancelacionResult(RespuestaTFD33 value) {
        return new JAXBElement<RespuestaTFD33>(_ObtenerAcuseCancelacionResponseObtenerAcuseCancelacionResult_QNAME, RespuestaTFD33 .class, ObtenerAcuseCancelacionResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "password", scope = ConsultarTimbrePorReferencia.class)
    public JAXBElement<String> createConsultarTimbrePorReferenciaPassword(String value) {
        return new JAXBElement<String>(_ObtenerAcuseCancelacionPassword_QNAME, String.class, ConsultarTimbrePorReferencia.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "usuario", scope = ConsultarTimbrePorReferencia.class)
    public JAXBElement<String> createConsultarTimbrePorReferenciaUsuario(String value) {
        return new JAXBElement<String>(_ObtenerAcuseCancelacionUsuario_QNAME, String.class, ConsultarTimbrePorReferencia.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "referencia", scope = ConsultarTimbrePorReferencia.class)
    public JAXBElement<String> createConsultarTimbrePorReferenciaReferencia(String value) {
        return new JAXBElement<String>(_ConsultarTimbrePorReferenciaReferencia_QNAME, String.class, ConsultarTimbrePorReferencia.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "password", scope = ValidarRFC.class)
    public JAXBElement<String> createValidarRFCPassword(String value) {
        return new JAXBElement<String>(_ObtenerAcuseCancelacionPassword_QNAME, String.class, ValidarRFC.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "usuario", scope = ValidarRFC.class)
    public JAXBElement<String> createValidarRFCUsuario(String value) {
        return new JAXBElement<String>(_ObtenerAcuseCancelacionUsuario_QNAME, String.class, ValidarRFC.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "rfc", scope = ValidarRFC.class)
    public JAXBElement<String> createValidarRFCRfc(String value) {
        return new JAXBElement<String>(_ValidarRFCRfc_QNAME, String.class, ValidarRFC.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "password", scope = TimbrarCFDI.class)
    public JAXBElement<String> createTimbrarCFDIPassword(String value) {
        return new JAXBElement<String>(_ObtenerAcuseCancelacionPassword_QNAME, String.class, TimbrarCFDI.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "usuario", scope = TimbrarCFDI.class)
    public JAXBElement<String> createTimbrarCFDIUsuario(String value) {
        return new JAXBElement<String>(_ObtenerAcuseCancelacionUsuario_QNAME, String.class, TimbrarCFDI.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "cadenaXML", scope = TimbrarCFDI.class)
    public JAXBElement<String> createTimbrarCFDICadenaXML(String value) {
        return new JAXBElement<String>(_TimbrarCFDICadenaXML_QNAME, String.class, TimbrarCFDI.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "referencia", scope = TimbrarCFDI.class)
    public JAXBElement<String> createTimbrarCFDIReferencia(String value) {
        return new JAXBElement<String>(_ConsultarTimbrePorReferenciaReferencia_QNAME, String.class, TimbrarCFDI.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "password", scope = ConsultarComprobantes.class)
    public JAXBElement<String> createConsultarComprobantesPassword(String value) {
        return new JAXBElement<String>(_ObtenerAcuseCancelacionPassword_QNAME, String.class, ConsultarComprobantes.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "usuario", scope = ConsultarComprobantes.class)
    public JAXBElement<String> createConsultarComprobantesUsuario(String value) {
        return new JAXBElement<String>(_ObtenerAcuseCancelacionUsuario_QNAME, String.class, ConsultarComprobantes.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "password", scope = ConsultarComplementoTimbre.class)
    public JAXBElement<String> createConsultarComplementoTimbrePassword(String value) {
        return new JAXBElement<String>(_ObtenerAcuseCancelacionPassword_QNAME, String.class, ConsultarComplementoTimbre.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "usuario", scope = ConsultarComplementoTimbre.class)
    public JAXBElement<String> createConsultarComplementoTimbreUsuario(String value) {
        return new JAXBElement<String>(_ObtenerAcuseCancelacionUsuario_QNAME, String.class, ConsultarComplementoTimbre.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "uUID", scope = ConsultarComplementoTimbre.class)
    public JAXBElement<String> createConsultarComplementoTimbreUUID(String value) {
        return new JAXBElement<String>(_ObtenerAcuseCancelacionUUID_QNAME, String.class, ConsultarComplementoTimbre.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RespuestaCancelacion }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "CancelarCFDIResult", scope = CancelarCFDIResponse.class)
    public JAXBElement<RespuestaCancelacion> createCancelarCFDIResponseCancelarCFDIResult(RespuestaCancelacion value) {
        return new JAXBElement<RespuestaCancelacion>(_CancelarCFDIResponseCancelarCFDIResult_QNAME, RespuestaCancelacion.class, CancelarCFDIResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "password", scope = ObtenerPDF.class)
    public JAXBElement<String> createObtenerPDFPassword(String value) {
        return new JAXBElement<String>(_ObtenerAcuseCancelacionPassword_QNAME, String.class, ObtenerPDF.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "usuario", scope = ObtenerPDF.class)
    public JAXBElement<String> createObtenerPDFUsuario(String value) {
        return new JAXBElement<String>(_ObtenerAcuseCancelacionUsuario_QNAME, String.class, ObtenerPDF.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "LogoBase64", scope = ObtenerPDF.class)
    public JAXBElement<String> createObtenerPDFLogoBase64(String value) {
        return new JAXBElement<String>(_ObtenerPDFLogoBase64_QNAME, String.class, ObtenerPDF.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "uUID", scope = ObtenerPDF.class)
    public JAXBElement<String> createObtenerPDFUUID(String value) {
        return new JAXBElement<String>(_ObtenerAcuseCancelacionUUID_QNAME, String.class, ObtenerPDF.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RespuestaReporte }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "ConsultarComprobantesResult", scope = ConsultarComprobantesResponse.class)
    public JAXBElement<RespuestaReporte> createConsultarComprobantesResponseConsultarComprobantesResult(RespuestaReporte value) {
        return new JAXBElement<RespuestaReporte>(_ConsultarComprobantesResponseConsultarComprobantesResult_QNAME, RespuestaReporte.class, ConsultarComprobantesResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RespuestaCreditos }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "ConsultarCreditosResult", scope = ConsultarCreditosResponse.class)
    public JAXBElement<RespuestaCreditos> createConsultarCreditosResponseConsultarCreditosResult(RespuestaCreditos value) {
        return new JAXBElement<RespuestaCreditos>(_ConsultarCreditosResponseConsultarCreditosResult_QNAME, RespuestaCreditos.class, ConsultarCreditosResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RespuestaCancelacionAsincrona }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "CancelacionAsincronaResult", scope = CancelacionAsincronaResponse.class)
    public JAXBElement<RespuestaCancelacionAsincrona> createCancelacionAsincronaResponseCancelacionAsincronaResult(RespuestaCancelacionAsincrona value) {
        return new JAXBElement<RespuestaCancelacionAsincrona>(_CancelacionAsincronaResponseCancelacionAsincronaResult_QNAME, RespuestaCancelacionAsincrona.class, CancelacionAsincronaResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RespuestaTFD33 }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "ConsultarTimbrePorReferenciaResult", scope = ConsultarTimbrePorReferenciaResponse.class)
    public JAXBElement<RespuestaTFD33> createConsultarTimbrePorReferenciaResponseConsultarTimbrePorReferenciaResult(RespuestaTFD33 value) {
        return new JAXBElement<RespuestaTFD33>(_ConsultarTimbrePorReferenciaResponseConsultarTimbrePorReferenciaResult_QNAME, RespuestaTFD33 .class, ConsultarTimbrePorReferenciaResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "passwordNuevo", scope = CambiarPassword.class)
    public JAXBElement<String> createCambiarPasswordPasswordNuevo(String value) {
        return new JAXBElement<String>(_CambiarPasswordPasswordNuevo_QNAME, String.class, CambiarPassword.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "usuario", scope = CambiarPassword.class)
    public JAXBElement<String> createCambiarPasswordUsuario(String value) {
        return new JAXBElement<String>(_ObtenerAcuseCancelacionUsuario_QNAME, String.class, CambiarPassword.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "passwordActual", scope = CambiarPassword.class)
    public JAXBElement<String> createCambiarPasswordPasswordActual(String value) {
        return new JAXBElement<String>(_CambiarPasswordPasswordActual_QNAME, String.class, CambiarPassword.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "password", scope = ObtenerAcuseEnvio.class)
    public JAXBElement<String> createObtenerAcuseEnvioPassword(String value) {
        return new JAXBElement<String>(_ObtenerAcuseCancelacionPassword_QNAME, String.class, ObtenerAcuseEnvio.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "usuario", scope = ObtenerAcuseEnvio.class)
    public JAXBElement<String> createObtenerAcuseEnvioUsuario(String value) {
        return new JAXBElement<String>(_ObtenerAcuseCancelacionUsuario_QNAME, String.class, ObtenerAcuseEnvio.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "uUID", scope = ObtenerAcuseEnvio.class)
    public JAXBElement<String> createObtenerAcuseEnvioUUID(String value) {
        return new JAXBElement<String>(_ObtenerAcuseCancelacionUUID_QNAME, String.class, ObtenerAcuseEnvio.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "password", scope = EstatusCancelacionAsincrona.class)
    public JAXBElement<String> createEstatusCancelacionAsincronaPassword(String value) {
        return new JAXBElement<String>(_ObtenerAcuseCancelacionPassword_QNAME, String.class, EstatusCancelacionAsincrona.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "usuario", scope = EstatusCancelacionAsincrona.class)
    public JAXBElement<String> createEstatusCancelacionAsincronaUsuario(String value) {
        return new JAXBElement<String>(_ObtenerAcuseCancelacionUsuario_QNAME, String.class, EstatusCancelacionAsincrona.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "referencia", scope = EstatusCancelacionAsincrona.class)
    public JAXBElement<String> createEstatusCancelacionAsincronaReferencia(String value) {
        return new JAXBElement<String>(_ConsultarTimbrePorReferenciaReferencia_QNAME, String.class, EstatusCancelacionAsincrona.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "passwordClavePrivada", scope = CancelarCFDI.class)
    public JAXBElement<String> createCancelarCFDIPasswordClavePrivada(String value) {
        return new JAXBElement<String>(_CancelacionAsincronaPasswordClavePrivada_QNAME, String.class, CancelarCFDI.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "password", scope = CancelarCFDI.class)
    public JAXBElement<String> createCancelarCFDIPassword(String value) {
        return new JAXBElement<String>(_ObtenerAcuseCancelacionPassword_QNAME, String.class, CancelarCFDI.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "clavePrivada_Base64", scope = CancelarCFDI.class)
    public JAXBElement<String> createCancelarCFDIClavePrivadaBase64(String value) {
        return new JAXBElement<String>(_CancelacionAsincronaClavePrivadaBase64_QNAME, String.class, CancelarCFDI.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfstring }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "listaCFDI", scope = CancelarCFDI.class)
    public JAXBElement<ArrayOfstring> createCancelarCFDIListaCFDI(ArrayOfstring value) {
        return new JAXBElement<ArrayOfstring>(_CancelacionAsincronaListaCFDI_QNAME, ArrayOfstring.class, CancelarCFDI.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "usuario", scope = CancelarCFDI.class)
    public JAXBElement<String> createCancelarCFDIUsuario(String value) {
        return new JAXBElement<String>(_ObtenerAcuseCancelacionUsuario_QNAME, String.class, CancelarCFDI.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "rFCEmisor", scope = CancelarCFDI.class)
    public JAXBElement<String> createCancelarCFDIRFCEmisor(String value) {
        return new JAXBElement<String>(_CancelacionAsincronaRFCEmisor_QNAME, String.class, CancelarCFDI.class, value);
    }

}
