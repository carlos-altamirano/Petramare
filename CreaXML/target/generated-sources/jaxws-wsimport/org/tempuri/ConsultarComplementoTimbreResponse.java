
package org.tempuri;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.datacontract.schemas._2004._07.tes_tfd_v33.RespuestaTFD33;


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
 *         &lt;element name="ConsultarComplementoTimbreResult" type="{http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios}RespuestaTFD33" minOccurs="0"/>
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
    "consultarComplementoTimbreResult"
})
@XmlRootElement(name = "ConsultarComplementoTimbreResponse")
public class ConsultarComplementoTimbreResponse {

    @XmlElementRef(name = "ConsultarComplementoTimbreResult", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<RespuestaTFD33> consultarComplementoTimbreResult;

    /**
     * Gets the value of the consultarComplementoTimbreResult property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link RespuestaTFD33 }{@code >}
     *     
     */
    public JAXBElement<RespuestaTFD33> getConsultarComplementoTimbreResult() {
        return consultarComplementoTimbreResult;
    }

    /**
     * Sets the value of the consultarComplementoTimbreResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link RespuestaTFD33 }{@code >}
     *     
     */
    public void setConsultarComplementoTimbreResult(JAXBElement<RespuestaTFD33> value) {
        this.consultarComplementoTimbreResult = value;
    }

}
