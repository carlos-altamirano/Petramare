package mx.garante.creaxml.Models;

import java.util.Date;

public class CompEdoCta {

    private Integer idComprobante;
    private Date fecha;
    private String fechaEdoCta;
    private String claveContrato;
    private Double total;
    private Date fechaTimbre;
    private String rfcProv;
    private String uuid;
    private String selloCFD;
    private String nCertificado;
    private String selloSAT;
    private Certificado certificado;

    public CompEdoCta() {
    }

    public CompEdoCta(Integer idComprobante, Date fecha, String fechaEdoCta, String claveContrato, Double total, Date fechaTimbre, String rfcProv, String uuid, String selloCFD, String nCertificado, String selloSAT, Certificado certificado) {
        this.idComprobante = idComprobante;
        this.fecha = fecha;
        this.fechaEdoCta = fechaEdoCta;
        this.claveContrato = claveContrato;
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

    public String getFechaEdoCta() {
        return fechaEdoCta;
    }

    public void setFechaEdoCta(String fechaEdoCta) {
        this.fechaEdoCta = fechaEdoCta;
    }

    public String getClaveContrato() {
        return claveContrato;
    }

    public void setClaveContrato(String claveContrato) {
        this.claveContrato = claveContrato;
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
