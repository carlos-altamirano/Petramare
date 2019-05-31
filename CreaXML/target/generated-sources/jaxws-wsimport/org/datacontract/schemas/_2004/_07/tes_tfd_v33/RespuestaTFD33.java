
package org.datacontract.schemas._2004._07.tes_tfd_v33;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RespuestaTFD33 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RespuestaTFD33">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CodigoConfirmacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CodigoRespuesta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CreditosRestantes" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="MensajeError" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MensajeErrorDetallado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OperacionExitosa" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="PDFResultado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Timbre" type="{http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios}Timbre33" minOccurs="0"/>
 *         &lt;element name="XMLResultado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RespuestaTFD33", propOrder = {
    "codigoConfirmacion",
    "codigoRespuesta",
    "creditosRestantes",
    "mensajeError",
    "mensajeErrorDetallado",
    "operacionExitosa",
    "pdfResultado",
    "timbre",
    "xmlResultado"
})
public class RespuestaTFD33 {

    @XmlElementRef(name = "CodigoConfirmacion", namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", type = JAXBElement.class, required = false)
    protected JAXBElement<String> codigoConfirmacion;
    @XmlElementRef(name = "CodigoRespuesta", namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", type = JAXBElement.class, required = false)
    protected JAXBElement<String> codigoRespuesta;
    @XmlElement(name = "CreditosRestantes")
    protected Integer creditosRestantes;
    @XmlElementRef(name = "MensajeError", namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", type = JAXBElement.class, required = false)
    protected JAXBElement<String> mensajeError;
    @XmlElementRef(name = "MensajeErrorDetallado", namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", type = JAXBElement.class, required = false)
    protected JAXBElement<String> mensajeErrorDetallado;
    @XmlElement(name = "OperacionExitosa")
    protected Boolean operacionExitosa;
    @XmlElementRef(name = "PDFResultado", namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", type = JAXBElement.class, required = false)
    protected JAXBElement<String> pdfResultado;
    @XmlElementRef(name = "Timbre", namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", type = JAXBElement.class, required = false)
    protected JAXBElement<Timbre33> timbre;
    @XmlElementRef(name = "XMLResultado", namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", type = JAXBElement.class, required = false)
    protected JAXBElement<String> xmlResultado;

    /**
     * Gets the value of the codigoConfirmacion property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCodigoConfirmacion() {
        return codigoConfirmacion;
    }

    /**
     * Sets the value of the codigoConfirmacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCodigoConfirmacion(JAXBElement<String> value) {
        this.codigoConfirmacion = value;
    }

    /**
     * Gets the value of the codigoRespuesta property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCodigoRespuesta() {
        return codigoRespuesta;
    }

    /**
     * Sets the value of the codigoRespuesta property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCodigoRespuesta(JAXBElement<String> value) {
        this.codigoRespuesta = value;
    }

    /**
     * Gets the value of the creditosRestantes property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCreditosRestantes() {
        return creditosRestantes;
    }

    /**
     * Sets the value of the creditosRestantes property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCreditosRestantes(Integer value) {
        this.creditosRestantes = value;
    }

    /**
     * Gets the value of the mensajeError property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getMensajeError() {
        return mensajeError;
    }

    /**
     * Sets the value of the mensajeError property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setMensajeError(JAXBElement<String> value) {
        this.mensajeError = value;
    }

    /**
     * Gets the value of the mensajeErrorDetallado property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getMensajeErrorDetallado() {
        return mensajeErrorDetallado;
    }

    /**
     * Sets the value of the mensajeErrorDetallado property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setMensajeErrorDetallado(JAXBElement<String> value) {
        this.mensajeErrorDetallado = value;
    }

    /**
     * Gets the value of the operacionExitosa property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isOperacionExitosa() {
        return operacionExitosa;
    }

    /**
     * Sets the value of the operacionExitosa property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setOperacionExitosa(Boolean value) {
        this.operacionExitosa = value;
    }

    /**
     * Gets the value of the pdfResultado property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPDFResultado() {
        return pdfResultado;
    }

    /**
     * Sets the value of the pdfResultado property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPDFResultado(JAXBElement<String> value) {
        this.pdfResultado = value;
    }

    /**
     * Gets the value of the timbre property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Timbre33 }{@code >}
     *     
     */
    public JAXBElement<Timbre33> getTimbre() {
        return timbre;
    }

    /**
     * Sets the value of the timbre property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Timbre33 }{@code >}
     *     
     */
    public void setTimbre(JAXBElement<Timbre33> value) {
        this.timbre = value;
    }

    /**
     * Gets the value of the xmlResultado property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getXMLResultado() {
        return xmlResultado;
    }

    /**
     * Sets the value of the xmlResultado property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setXMLResultado(JAXBElement<String> value) {
        this.xmlResultado = value;
    }

}
