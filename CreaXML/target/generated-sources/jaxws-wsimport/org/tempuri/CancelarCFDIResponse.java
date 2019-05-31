
package org.tempuri;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.datacontract.schemas._2004._07.tes_tfd_v33.RespuestaCancelacion;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CancelarCFDIResult" type="{http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios}RespuestaCancelacion" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "cancelarCFDIResult"
})
@XmlRootElement(name = "CancelarCFDIResponse")
public class CancelarCFDIResponse {

    @XmlElementRef(name = "CancelarCFDIResult", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<RespuestaCancelacion> cancelarCFDIResult;

    /**
     * Gets the value of the cancelarCFDIResult property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link RespuestaCancelacion }{@code >}
     *     
     */
    public JAXBElement<RespuestaCancelacion> getCancelarCFDIResult() {
        return cancelarCFDIResult;
    }

    /**
     * Sets the value of the cancelarCFDIResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link RespuestaCancelacion }{@code >}
     *     
     */
    public void setCancelarCFDIResult(JAXBElement<RespuestaCancelacion> value) {
        this.cancelarCFDIResult = value;
    }

}
