
package org.datacontract.schemas._2004._07.tes_tfd_v33;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for DetallesPaqueteCreditos complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DetallesPaqueteCreditos">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="EnUso" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="FechaActivacion" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="FechaVencimiento" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="Paquete" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Timbres" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="TimbresRestantes" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="TimbresUsados" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Vigente" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DetallesPaqueteCreditos", propOrder = {
    "enUso",
    "fechaActivacion",
    "fechaVencimiento",
    "paquete",
    "timbres",
    "timbresRestantes",
    "timbresUsados",
    "vigente"
})
public class DetallesPaqueteCreditos {

    @XmlElement(name = "EnUso")
    protected Boolean enUso;
    @XmlElementRef(name = "FechaActivacion", namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", type = JAXBElement.class, required = false)
    protected JAXBElement<XMLGregorianCalendar> fechaActivacion;
    @XmlElementRef(name = "FechaVencimiento", namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", type = JAXBElement.class, required = false)
    protected JAXBElement<XMLGregorianCalendar> fechaVencimiento;
    @XmlElementRef(name = "Paquete", namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", type = JAXBElement.class, required = false)
    protected JAXBElement<String> paquete;
    @XmlElement(name = "Timbres")
    protected Integer timbres;
    @XmlElement(name = "TimbresRestantes")
    protected Integer timbresRestantes;
    @XmlElement(name = "TimbresUsados")
    protected Integer timbresUsados;
    @XmlElement(name = "Vigente")
    protected Boolean vigente;

    /**
     * Gets the value of the enUso property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEnUso() {
        return enUso;
    }

    /**
     * Sets the value of the enUso property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEnUso(Boolean value) {
        this.enUso = value;
    }

    /**
     * Gets the value of the fechaActivacion property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getFechaActivacion() {
        return fechaActivacion;
    }

    /**
     * Sets the value of the fechaActivacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setFechaActivacion(JAXBElement<XMLGregorianCalendar> value) {
        this.fechaActivacion = value;
    }

    /**
     * Gets the value of the fechaVencimiento property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getFechaVencimiento() {
        return fechaVencimiento;
    }

    /**
     * Sets the value of the fechaVencimiento property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setFechaVencimiento(JAXBElement<XMLGregorianCalendar> value) {
        this.fechaVencimiento = value;
    }

    /**
     * Gets the value of the paquete property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPaquete() {
        return paquete;
    }

    /**
     * Sets the value of the paquete property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPaquete(JAXBElement<String> value) {
        this.paquete = value;
    }

    /**
     * Gets the value of the timbres property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTimbres() {
        return timbres;
    }

    /**
     * Sets the value of the timbres property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTimbres(Integer value) {
        this.timbres = value;
    }

    /**
     * Gets the value of the timbresRestantes property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTimbresRestantes() {
        return timbresRestantes;
    }

    /**
     * Sets the value of the timbresRestantes property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTimbresRestantes(Integer value) {
        this.timbresRestantes = value;
    }

    /**
     * Gets the value of the timbresUsados property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTimbresUsados() {
        return timbresUsados;
    }

    /**
     * Sets the value of the timbresUsados property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTimbresUsados(Integer value) {
        this.timbresUsados = value;
    }

    /**
     * Gets the value of the vigente property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isVigente() {
        return vigente;
    }

    /**
     * Sets the value of the vigente property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setVigente(Boolean value) {
        this.vigente = value;
    }

}
