//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.11 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2020.02.18 a las 09:06:28 AM ART 
//


package mx.garante.xmls;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Movimientos" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Movimiento" maxOccurs="unbounded"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;attribute name="Fecha" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                           &lt;attribute name="Concepto" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                           &lt;attribute name="Observaciones" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                           &lt;attribute name="Cargo" use="required" type="{https://www.garante.mx/cfd/addenda/edocta}t_Importe" /&gt;
 *                           &lt;attribute name="Abono" use="required" type="{https://www.garante.mx/cfd/addenda/edocta}t_Importe" /&gt;
 *                           &lt;attribute name="Saldo" use="required" type="{https://www.garante.mx/cfd/addenda/edocta}t_Importe" /&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="Honorarios" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Honorario" maxOccurs="unbounded"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;attribute name="Fecha" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                           &lt;attribute name="Honorario" use="required" type="{https://www.garante.mx/cfd/addenda/edocta}t_Importe" /&gt;
 *                           &lt;attribute name="Iva" use="required" type="{https://www.garante.mx/cfd/addenda/edocta}t_Importe" /&gt;
 *                           &lt;attribute name="Total" use="required" type="{https://www.garante.mx/cfd/addenda/edocta}t_Importe" /&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *                 &lt;attribute name="TotalHonorario" use="required" type="{https://www.garante.mx/cfd/addenda/edocta}t_Importe" /&gt;
 *                 &lt;attribute name="TotalIva" use="required" type="{https://www.garante.mx/cfd/addenda/edocta}t_Importe" /&gt;
 *                 &lt;attribute name="TotalTotal" use="required" type="{https://www.garante.mx/cfd/addenda/edocta}t_Importe" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="Version" use="required" type="{http://www.w3.org/2001/XMLSchema}string" fixed="1.0" /&gt;
 *       &lt;attribute name="FechaEdoCta" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="RfcFideicomitente" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="Contrato" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="Moneda" use="required" type="{http://www.w3.org/2001/XMLSchema}string" fixed="Pesos" /&gt;
 *       &lt;attribute name="TipoContrato" use="required" type="{http://www.w3.org/2001/XMLSchema}string" fixed="Fideicomiso de Garantia" /&gt;
 *       &lt;attribute name="SaldoAnteriorPatrimonio" use="required" type="{https://www.garante.mx/cfd/addenda/edocta}t_Importe" /&gt;
 *       &lt;attribute name="AportacionTotalFideicomiso" use="required" type="{https://www.garante.mx/cfd/addenda/edocta}t_Importe" /&gt;
 *       &lt;attribute name="AportacionPatrimonio" use="required" type="{https://www.garante.mx/cfd/addenda/edocta}t_Importe" /&gt;
 *       &lt;attribute name="RestitucionPatrimonio" use="required" type="{https://www.garante.mx/cfd/addenda/edocta}t_Importe" /&gt;
 *       &lt;attribute name="LiquidacionFideicomisarios" use="required" type="{https://www.garante.mx/cfd/addenda/edocta}t_Importe" /&gt;
 *       &lt;attribute name="HonorariosFiduciarios" use="required" type="{https://www.garante.mx/cfd/addenda/edocta}t_Importe" /&gt;
 *       &lt;attribute name="IvaHonorariosFiduciarios" use="required" type="{https://www.garante.mx/cfd/addenda/edocta}t_Importe" /&gt;
 *       &lt;attribute name="SaldoFinalPatrimonio" use="required" type="{https://www.garante.mx/cfd/addenda/edocta}t_Importe" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "movimientos",
    "honorarios"
})
@XmlRootElement(name = "EstadoDeCuenta", namespace = "https://www.garante.mx/cfd/addenda/edocta")
public class EstadoDeCuenta {

    @XmlElement(name = "Movimientos", namespace = "https://www.garante.mx/cfd/addenda/edocta")
    protected EstadoDeCuenta.Movimientos movimientos;
    @XmlElement(name = "Honorarios", namespace = "https://www.garante.mx/cfd/addenda/edocta")
    protected EstadoDeCuenta.Honorarios honorarios;
    @XmlAttribute(name = "Version", required = true)
    protected String version;
    @XmlAttribute(name = "FechaEdoCta", required = true)
    protected String fechaEdoCta;
    @XmlAttribute(name = "RfcFideicomitente", required = true)
    protected String rfcFideicomitente;
    @XmlAttribute(name = "Contrato", required = true)
    protected String contrato;
    @XmlAttribute(name = "Moneda", required = true)
    protected String moneda;
    @XmlAttribute(name = "TipoContrato", required = true)
    protected String tipoContrato;
    @XmlAttribute(name = "SaldoAnteriorPatrimonio", required = true)
    protected BigDecimal saldoAnteriorPatrimonio;
    @XmlAttribute(name = "AportacionTotalFideicomiso", required = true)
    protected BigDecimal aportacionTotalFideicomiso;
    @XmlAttribute(name = "AportacionPatrimonio", required = true)
    protected BigDecimal aportacionPatrimonio;
    @XmlAttribute(name = "RestitucionPatrimonio", required = true)
    protected BigDecimal restitucionPatrimonio;
    @XmlAttribute(name = "LiquidacionFideicomisarios", required = true)
    protected BigDecimal liquidacionFideicomisarios;
    @XmlAttribute(name = "HonorariosFiduciarios", required = true)
    protected BigDecimal honorariosFiduciarios;
    @XmlAttribute(name = "IvaHonorariosFiduciarios", required = true)
    protected BigDecimal ivaHonorariosFiduciarios;
    @XmlAttribute(name = "SaldoFinalPatrimonio", required = true)
    protected BigDecimal saldoFinalPatrimonio;

    /**
     * Obtiene el valor de la propiedad movimientos.
     * 
     * @return
     *     possible object is
     *     {@link EstadoDeCuenta.Movimientos }
     *     
     */
    public EstadoDeCuenta.Movimientos getMovimientos() {
        return movimientos;
    }

    /**
     * Define el valor de la propiedad movimientos.
     * 
     * @param value
     *     allowed object is
     *     {@link EstadoDeCuenta.Movimientos }
     *     
     */
    public void setMovimientos(EstadoDeCuenta.Movimientos value) {
        this.movimientos = value;
    }

    /**
     * Obtiene el valor de la propiedad honorarios.
     * 
     * @return
     *     possible object is
     *     {@link EstadoDeCuenta.Honorarios }
     *     
     */
    public EstadoDeCuenta.Honorarios getHonorarios() {
        return honorarios;
    }

    /**
     * Define el valor de la propiedad honorarios.
     * 
     * @param value
     *     allowed object is
     *     {@link EstadoDeCuenta.Honorarios }
     *     
     */
    public void setHonorarios(EstadoDeCuenta.Honorarios value) {
        this.honorarios = value;
    }

    /**
     * Obtiene el valor de la propiedad version.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        if (version == null) {
            return "1.0";
        } else {
            return version;
        }
    }

    /**
     * Define el valor de la propiedad version.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaEdoCta.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaEdoCta() {
        return fechaEdoCta;
    }

    /**
     * Define el valor de la propiedad fechaEdoCta.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaEdoCta(String value) {
        this.fechaEdoCta = value;
    }

    /**
     * Obtiene el valor de la propiedad rfcFideicomitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRfcFideicomitente() {
        return rfcFideicomitente;
    }

    /**
     * Define el valor de la propiedad rfcFideicomitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRfcFideicomitente(String value) {
        this.rfcFideicomitente = value;
    }

    /**
     * Obtiene el valor de la propiedad contrato.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContrato() {
        return contrato;
    }

    /**
     * Define el valor de la propiedad contrato.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContrato(String value) {
        this.contrato = value;
    }

    /**
     * Obtiene el valor de la propiedad moneda.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMoneda() {
        if (moneda == null) {
            return "Pesos";
        } else {
            return moneda;
        }
    }

    /**
     * Define el valor de la propiedad moneda.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMoneda(String value) {
        this.moneda = value;
    }

    /**
     * Obtiene el valor de la propiedad tipoContrato.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoContrato() {
        if (tipoContrato == null) {
            return "Fideicomiso de Garantia";
        } else {
            return tipoContrato;
        }
    }

    /**
     * Define el valor de la propiedad tipoContrato.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoContrato(String value) {
        this.tipoContrato = value;
    }

    /**
     * Obtiene el valor de la propiedad saldoAnteriorPatrimonio.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSaldoAnteriorPatrimonio() {
        return saldoAnteriorPatrimonio;
    }

    /**
     * Define el valor de la propiedad saldoAnteriorPatrimonio.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSaldoAnteriorPatrimonio(BigDecimal value) {
        this.saldoAnteriorPatrimonio = value;
    }

    /**
     * Obtiene el valor de la propiedad aportacionTotalFideicomiso.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAportacionTotalFideicomiso() {
        return aportacionTotalFideicomiso;
    }

    /**
     * Define el valor de la propiedad aportacionTotalFideicomiso.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAportacionTotalFideicomiso(BigDecimal value) {
        this.aportacionTotalFideicomiso = value;
    }

    /**
     * Obtiene el valor de la propiedad aportacionPatrimonio.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAportacionPatrimonio() {
        return aportacionPatrimonio;
    }

    /**
     * Define el valor de la propiedad aportacionPatrimonio.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAportacionPatrimonio(BigDecimal value) {
        this.aportacionPatrimonio = value;
    }

    /**
     * Obtiene el valor de la propiedad restitucionPatrimonio.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getRestitucionPatrimonio() {
        return restitucionPatrimonio;
    }

    /**
     * Define el valor de la propiedad restitucionPatrimonio.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setRestitucionPatrimonio(BigDecimal value) {
        this.restitucionPatrimonio = value;
    }

    /**
     * Obtiene el valor de la propiedad liquidacionFideicomisarios.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getLiquidacionFideicomisarios() {
        return liquidacionFideicomisarios;
    }

    /**
     * Define el valor de la propiedad liquidacionFideicomisarios.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setLiquidacionFideicomisarios(BigDecimal value) {
        this.liquidacionFideicomisarios = value;
    }

    /**
     * Obtiene el valor de la propiedad honorariosFiduciarios.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getHonorariosFiduciarios() {
        return honorariosFiduciarios;
    }

    /**
     * Define el valor de la propiedad honorariosFiduciarios.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setHonorariosFiduciarios(BigDecimal value) {
        this.honorariosFiduciarios = value;
    }

    /**
     * Obtiene el valor de la propiedad ivaHonorariosFiduciarios.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIvaHonorariosFiduciarios() {
        return ivaHonorariosFiduciarios;
    }

    /**
     * Define el valor de la propiedad ivaHonorariosFiduciarios.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIvaHonorariosFiduciarios(BigDecimal value) {
        this.ivaHonorariosFiduciarios = value;
    }

    /**
     * Obtiene el valor de la propiedad saldoFinalPatrimonio.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSaldoFinalPatrimonio() {
        return saldoFinalPatrimonio;
    }

    /**
     * Define el valor de la propiedad saldoFinalPatrimonio.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSaldoFinalPatrimonio(BigDecimal value) {
        this.saldoFinalPatrimonio = value;
    }


    /**
     * <p>Clase Java para anonymous complex type.
     * 
     * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="Honorario" maxOccurs="unbounded"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;attribute name="Fecha" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                 &lt;attribute name="Honorario" use="required" type="{https://www.garante.mx/cfd/addenda/edocta}t_Importe" /&gt;
     *                 &lt;attribute name="Iva" use="required" type="{https://www.garante.mx/cfd/addenda/edocta}t_Importe" /&gt;
     *                 &lt;attribute name="Total" use="required" type="{https://www.garante.mx/cfd/addenda/edocta}t_Importe" /&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *       &lt;/sequence&gt;
     *       &lt;attribute name="TotalHonorario" use="required" type="{https://www.garante.mx/cfd/addenda/edocta}t_Importe" /&gt;
     *       &lt;attribute name="TotalIva" use="required" type="{https://www.garante.mx/cfd/addenda/edocta}t_Importe" /&gt;
     *       &lt;attribute name="TotalTotal" use="required" type="{https://www.garante.mx/cfd/addenda/edocta}t_Importe" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "honorario"
    })
    public static class Honorarios {

        @XmlElement(name = "Honorario", namespace = "https://www.garante.mx/cfd/addenda/edocta", required = true)
        protected List<EstadoDeCuenta.Honorarios.Honorario> honorario;
        @XmlAttribute(name = "TotalHonorario", required = true)
        protected BigDecimal totalHonorario;
        @XmlAttribute(name = "TotalIva", required = true)
        protected BigDecimal totalIva;
        @XmlAttribute(name = "TotalTotal", required = true)
        protected BigDecimal totalTotal;

        /**
         * Gets the value of the honorario property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the honorario property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getHonorario().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link EstadoDeCuenta.Honorarios.Honorario }
         * 
         * 
         */
        public List<EstadoDeCuenta.Honorarios.Honorario> getHonorario() {
            if (honorario == null) {
                honorario = new ArrayList<EstadoDeCuenta.Honorarios.Honorario>();
            }
            return this.honorario;
        }

        /**
         * Obtiene el valor de la propiedad totalHonorario.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getTotalHonorario() {
            return totalHonorario;
        }

        /**
         * Define el valor de la propiedad totalHonorario.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setTotalHonorario(BigDecimal value) {
            this.totalHonorario = value;
        }

        /**
         * Obtiene el valor de la propiedad totalIva.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getTotalIva() {
            return totalIva;
        }

        /**
         * Define el valor de la propiedad totalIva.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setTotalIva(BigDecimal value) {
            this.totalIva = value;
        }

        /**
         * Obtiene el valor de la propiedad totalTotal.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getTotalTotal() {
            return totalTotal;
        }

        /**
         * Define el valor de la propiedad totalTotal.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setTotalTotal(BigDecimal value) {
            this.totalTotal = value;
        }


        /**
         * <p>Clase Java para anonymous complex type.
         * 
         * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;attribute name="Fecha" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *       &lt;attribute name="Honorario" use="required" type="{https://www.garante.mx/cfd/addenda/edocta}t_Importe" /&gt;
         *       &lt;attribute name="Iva" use="required" type="{https://www.garante.mx/cfd/addenda/edocta}t_Importe" /&gt;
         *       &lt;attribute name="Total" use="required" type="{https://www.garante.mx/cfd/addenda/edocta}t_Importe" /&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class Honorario {

            @XmlAttribute(name = "Fecha", required = true)
            protected String fecha;
            @XmlAttribute(name = "Honorario", required = true)
            protected BigDecimal honorario;
            @XmlAttribute(name = "Iva", required = true)
            protected BigDecimal iva;
            @XmlAttribute(name = "Total", required = true)
            protected BigDecimal total;

            /**
             * Obtiene el valor de la propiedad fecha.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getFecha() {
                return fecha;
            }

            /**
             * Define el valor de la propiedad fecha.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setFecha(String value) {
                this.fecha = value;
            }

            /**
             * Obtiene el valor de la propiedad honorario.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getHonorario() {
                return honorario;
            }

            /**
             * Define el valor de la propiedad honorario.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setHonorario(BigDecimal value) {
                this.honorario = value;
            }

            /**
             * Obtiene el valor de la propiedad iva.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getIva() {
                return iva;
            }

            /**
             * Define el valor de la propiedad iva.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setIva(BigDecimal value) {
                this.iva = value;
            }

            /**
             * Obtiene el valor de la propiedad total.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getTotal() {
                return total;
            }

            /**
             * Define el valor de la propiedad total.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setTotal(BigDecimal value) {
                this.total = value;
            }

        }

    }


    /**
     * <p>Clase Java para anonymous complex type.
     * 
     * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="Movimiento" maxOccurs="unbounded"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;attribute name="Fecha" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                 &lt;attribute name="Concepto" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                 &lt;attribute name="Observaciones" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                 &lt;attribute name="Cargo" use="required" type="{https://www.garante.mx/cfd/addenda/edocta}t_Importe" /&gt;
     *                 &lt;attribute name="Abono" use="required" type="{https://www.garante.mx/cfd/addenda/edocta}t_Importe" /&gt;
     *                 &lt;attribute name="Saldo" use="required" type="{https://www.garante.mx/cfd/addenda/edocta}t_Importe" /&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "movimiento"
    })
    public static class Movimientos {

        @XmlElement(name = "Movimiento", namespace = "https://www.garante.mx/cfd/addenda/edocta", required = true)
        protected List<EstadoDeCuenta.Movimientos.Movimiento> movimiento;

        public void setMovimiento(List<EstadoDeCuenta.Movimientos.Movimiento> movimiento) {
			this.movimiento = movimiento;
		}


		/**
         * Gets the value of the movimiento property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the movimiento property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getMovimiento().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link EstadoDeCuenta.Movimientos.Movimiento }
         * 
         * 
         */
        public List<EstadoDeCuenta.Movimientos.Movimiento> getMovimiento() {
            if (movimiento == null) {
                movimiento = new ArrayList<EstadoDeCuenta.Movimientos.Movimiento>();
            }
            return this.movimiento;
        }


        /**
         * <p>Clase Java para anonymous complex type.
         * 
         * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;attribute name="Fecha" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *       &lt;attribute name="Concepto" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *       &lt;attribute name="Observaciones" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *       &lt;attribute name="Cargo" use="required" type="{https://www.garante.mx/cfd/addenda/edocta}t_Importe" /&gt;
         *       &lt;attribute name="Abono" use="required" type="{https://www.garante.mx/cfd/addenda/edocta}t_Importe" /&gt;
         *       &lt;attribute name="Saldo" use="required" type="{https://www.garante.mx/cfd/addenda/edocta}t_Importe" /&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class Movimiento {

            @XmlAttribute(name = "Fecha", required = true)
            protected String fecha;
            @XmlAttribute(name = "Concepto", required = true)
            protected String concepto;
            @XmlAttribute(name = "Observaciones")
            protected String observaciones;
            @XmlAttribute(name = "Cargo", required = true)
            protected BigDecimal cargo;
            @XmlAttribute(name = "Abono", required = true)
            protected BigDecimal abono;
            @XmlAttribute(name = "Saldo", required = true)
            protected BigDecimal saldo;

            /**
             * Obtiene el valor de la propiedad fecha.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getFecha() {
                return fecha;
            }

            /**
             * Define el valor de la propiedad fecha.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setFecha(String value) {
                this.fecha = value;
            }

            /**
             * Obtiene el valor de la propiedad concepto.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getConcepto() {
                return concepto;
            }

            /**
             * Define el valor de la propiedad concepto.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setConcepto(String value) {
                this.concepto = value;
            }

            /**
             * Obtiene el valor de la propiedad observaciones.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getObservaciones() {
                return observaciones;
            }

            /**
             * Define el valor de la propiedad observaciones.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setObservaciones(String value) {
                this.observaciones = value;
            }

            /**
             * Obtiene el valor de la propiedad cargo.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getCargo() {
                return cargo;
            }

            /**
             * Define el valor de la propiedad cargo.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setCargo(BigDecimal value) {
                this.cargo = value;
            }

            /**
             * Obtiene el valor de la propiedad abono.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getAbono() {
                return abono;
            }

            /**
             * Define el valor de la propiedad abono.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setAbono(BigDecimal value) {
                this.abono = value;
            }

            /**
             * Obtiene el valor de la propiedad saldo.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getSaldo() {
                return saldo;
            }

            /**
             * Define el valor de la propiedad saldo.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setSaldo(BigDecimal value) {
                this.saldo = value;
            }

        }

    }

}
