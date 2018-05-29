
package org.datacontract.schemas._2004._07.tes_tfd_v33;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RespuestaCancelacion complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RespuestaCancelacion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DetallesCancelacion" type="{http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios}ArrayOfDetalleCancelacion" minOccurs="0"/>
 *         &lt;element name="MensajeError" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MensajeErrorDetallado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OperacionExitosa" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="XMLAcuse" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RespuestaCancelacion", propOrder = {
    "detallesCancelacion",
    "mensajeError",
    "mensajeErrorDetallado",
    "operacionExitosa",
    "xmlAcuse"
})
public class RespuestaCancelacion {

    @XmlElementRef(name = "DetallesCancelacion", namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", type = JAXBElement.class, required = false)
    protected JAXBElement<ArrayOfDetalleCancelacion> detallesCancelacion;
    @XmlElementRef(name = "MensajeError", namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", type = JAXBElement.class, required = false)
    protected JAXBElement<String> mensajeError;
    @XmlElementRef(name = "MensajeErrorDetallado", namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", type = JAXBElement.class, required = false)
    protected JAXBElement<String> mensajeErrorDetallado;
    @XmlElement(name = "OperacionExitosa")
    protected Boolean operacionExitosa;
    @XmlElementRef(name = "XMLAcuse", namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", type = JAXBElement.class, required = false)
    protected JAXBElement<String> xmlAcuse;

    /**
     * Gets the value of the detallesCancelacion property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfDetalleCancelacion }{@code >}
     *     
     */
    public JAXBElement<ArrayOfDetalleCancelacion> getDetallesCancelacion() {
        return detallesCancelacion;
    }

    /**
     * Sets the value of the detallesCancelacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfDetalleCancelacion }{@code >}
     *     
     */
    public void setDetallesCancelacion(JAXBElement<ArrayOfDetalleCancelacion> value) {
        this.detallesCancelacion = value;
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
     * Gets the value of the xmlAcuse property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getXMLAcuse() {
        return xmlAcuse;
    }

    /**
     * Sets the value of the xmlAcuse property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setXMLAcuse(JAXBElement<String> value) {
        this.xmlAcuse = value;
    }

}
