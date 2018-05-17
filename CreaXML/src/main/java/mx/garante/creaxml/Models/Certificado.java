package mx.garante.creaxml.Models;

public class Certificado {

    private Integer idCertificado;
    private String nCertificado;
    private String certificado;
    private String password;

    public Certificado() {
    }
    
    public Certificado(Integer idCertificado, String nCertificado, String certificado, String password) {
        this.idCertificado = idCertificado;
        this.nCertificado = nCertificado;
        this.certificado = certificado;
        this.password = password;
    }

    public Integer getIdCertificado() {
        return idCertificado;
    }

    public void setIdCertificado(Integer idCertificado) {
        this.idCertificado = idCertificado;
    }

    public String getnCertificado() {
        return nCertificado;
    }

    public void setnCertificado(String nCertificado) {
        this.nCertificado = nCertificado;
    }

    public String getCertificado() {
        return certificado;
    }

    public void setCertificado(String certificado) {
        this.certificado = certificado;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
