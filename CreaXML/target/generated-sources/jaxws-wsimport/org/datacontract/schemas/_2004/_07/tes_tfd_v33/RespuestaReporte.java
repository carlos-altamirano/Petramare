
package org.datacontract.schemas._2004._07.tes_tfd_v33;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RespuestaReporte complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RespuestaReporte">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ListaComprobantes" type="{http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios}ArrayOfRegistroTimbre" minOccurs="0"/>
 *         &lt;element name="MensajeError" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OperacionExitosa" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="TotalComprobantesPeriodo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RespuestaReporte", propOrder = {
    "listaComprobantes",
    "mensajeError",
    "operacionExitosa",
    "totalComprobantesPeriodo"
})
public class RespuestaReporte {

    @XmlElementRef(name = "ListaComprobantes", namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", type = JAXBElement.class, required = false)
    protected JAXBElement<ArrayOfRegistroTimbre> listaComprobantes;
    @XmlElementRef(name = "MensajeError", namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", type = JAXBElement.class, required = false)
    protected JAXBElement<String> mensajeError;
    @XmlElement(name = "OperacionExitosa")
    protected Boolean operacionExitosa;
    @XmlElement(name = "TotalComprobantesPeriodo")
    protected Integer totalComprobantesPeriodo;

    /**
     * Gets the value of the listaComprobantes property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfRegistroTimbre }{@code >}
     *     
     */
    public JAXBElement<ArrayOfRegistroTimbre> getListaComprobantes() {
        return listaComprobantes;
    }

    /**
     * Sets the value of the listaComprobantes property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfRegistroTimbre }{@code >}
     *     
     */
    public void setListaComprobantes(JAXBElement<ArrayOfRegistroTimbre> value) {
        this.listaComprobantes = value;
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
     * Gets the value of the totalComprobantesPeriodo property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTotalComprobantesPeriodo() {
        return totalComprobantesPeriodo;
    }

    /**
     * Sets the value of the totalComprobantesPeriodo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTotalComprobantesPeriodo(Integer value) {
        this.totalComprobantesPeriodo = value;
    }

}
