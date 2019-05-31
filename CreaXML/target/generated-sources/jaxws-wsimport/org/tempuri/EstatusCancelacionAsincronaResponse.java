
package org.tempuri;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.datacontract.schemas._2004._07.tes_tfd_v33.RespuestaEstatusCancelacionAsincrona;


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
 *         &lt;element name="EstatusCancelacionAsincronaResult" type="{http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios}RespuestaEstatusCancelacionAsincrona" minOccurs="0"/>
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
    "estatusCancelacionAsincronaResult"
})
@XmlRootElement(name = "EstatusCancelacionAsincronaResponse")
public class EstatusCancelacionAsincronaResponse {

    @XmlElementRef(name = "EstatusCancelacionAsincronaResult", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<RespuestaEstatusCancelacionAsincrona> estatusCancelacionAsincronaResult;

    /**
     * Gets the value of the estatusCancelacionAsincronaResult property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link RespuestaEstatusCancelacionAsincrona }{@code >}
     *     
     */
    public JAXBElement<RespuestaEstatusCancelacionAsincrona> getEstatusCancelacionAsincronaResult() {
        return estatusCancelacionAsincronaResult;
    }

    /**
     * Sets the value of the estatusCancelacionAsincronaResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link RespuestaEstatusCancelacionAsincrona }{@code >}
     *     
     */
    public void setEstatusCancelacionAsincronaResult(JAXBElement<RespuestaEstatusCancelacionAsincrona> value) {
        this.estatusCancelacionAsincronaResult = value;
    }

}
