package mx.garante.creaxml.Models;

import java.util.Date;

public class CompNomina {

    private Integer idComprobante;
    private Date fecha;
    private String fechaNomina;
    private String claveContrato;
    private String rfc;
    private Double total;
    private Date fechaTimbre;
    private String rfcProv;
    private String uuid;
    private String selloCFD;
    private String nCertificado;
    private String selloSAT;
    private Certificado certificado;

    public CompNomina() {
    }

    public CompNomina(Integer idComprobante, Date fecha, String fechaNomina, String claveContrato, String rfc, Double total, Date fechaTimbre, String rfcProv, String uuid, String selloCFD, String nCertificado, String selloSAT, Certificado certificado) {
        this.idComprobante = idComprobante;
        this.fecha = fecha;
        this.fechaNomina = fechaNomina;
        this.claveContrato = claveContrato;
        this.rfc = rfc;
        this.total = total;
        this.fechaTimbre = fechaTimbre;
        this.rfcProv = rfcProv;
        this.uuid = uuid;
        this.selloCFD = selloCFD;
        this.nCertificado = nCertificado;
        this.selloSAT = selloSAT;
        this.certificado = certificado;
    }

    public Integer getIdComprobante() {
        return idComprobante;
    }

    public void setIdComprobante(Integer idComprobante) {
        this.idComprobante = idComprobante;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getFechaNomina() {
        return fechaNomina;
    }

    public void setFechaNomina(String fechaNomina) {
        this.fechaNomina = fechaNomina;
    }

    public String getClaveContrato() {
        return claveContrato;
    }
    
    public void setClaveContrato(String claveContrato) {
        this.claveContrato = claveContrato;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Date getFechaTimbre() {
        return fechaTimbre;
    }

    public void setFechaTimbre(Date fechaTimbre) {
        this.fechaTimbre = fechaTimbre;
    }

    public String getRfcProv() {
        return rfcProv;
    }

    public void setRfcProv(String rfcProv) {
        this.rfcProv = rfcProv;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSelloCFD() {
        return selloCFD;
    }

    public void setSelloCFD(String selloCFD) {
        this.selloCFD = selloCFD;
    }

    public String getnCertificado() {
        return nCertificado;
    }

    public void setnCertificado(String nCertificado) {
        this.nCertificado = nCertificado;
    }

    public String getSelloSAT() {
        return selloSAT;
    }

    public void setSelloSAT(String selloSAT) {
        this.selloSAT = selloSAT;
    }

    public Certificado getCertificado() {
        return certificado;
    }

    public void setCertificado(Certificado certificado) {
        this.certificado = certificado;
    }
    
}
