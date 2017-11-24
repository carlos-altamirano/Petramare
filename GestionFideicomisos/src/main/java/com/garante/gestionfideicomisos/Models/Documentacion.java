package com.garante.gestionfideicomisos.Models;

public class Documentacion {

    private Integer idDocumentacion;
    private Boolean cedulaidFiscal;
    private Boolean constFirmaElect;
    private Boolean compDomicilio;
    private Boolean identifiOficial;
    private Boolean curp;
    private Boolean primaReg;
    private Boolean escrituraContCliente;
    private Boolean poderesDominio;
    private Boolean registroCNBV;
    private String claveContrato;
    private Boolean contrato;

    public Integer getIdDocumentacion() {
        return idDocumentacion;
    }

    public void setIdDocumentacion(Integer idDocumentacion) {
        this.idDocumentacion = idDocumentacion;
    }

    public Boolean getCedulaidFiscal() {
        return cedulaidFiscal;
    }

    public void setCedulaidFiscal(Boolean cedulaidFiscal) {
        this.cedulaidFiscal = cedulaidFiscal;
    }

    public Boolean getConstFirmaElect() {
        return constFirmaElect;
    }

    public void setConstFirmaElect(Boolean constFirmaElect) {
        this.constFirmaElect = constFirmaElect;
    }

    public Boolean getCompDomicilio() {
        return compDomicilio;
    }

    public void setCompDomicilio(Boolean compDomicilio) {
        this.compDomicilio = compDomicilio;
    }

    public Boolean getIdentifiOficial() {
        return identifiOficial;
    }

    public void setIdentifiOficial(Boolean identifiOficial) {
        this.identifiOficial = identifiOficial;
    }

    public Boolean getCurp() {
        return curp;
    }

    public void setCurp(Boolean curp) {
        this.curp = curp;
    }

    public Boolean getPrimaReg() {
        return primaReg;
    }

    public void setPrimaReg(Boolean primaReg) {
        this.primaReg = primaReg;
    }

    public Boolean getEscrituraContCliente() {
        return escrituraContCliente;
    }

    public void setEscrituraContCliente(Boolean escrituraContCliente) {
        this.escrituraContCliente = escrituraContCliente;
    }

    public Boolean getPoderesDominio() {
        return poderesDominio;
    }

    public void setPoderesDominio(Boolean poderesDominio) {
        this.poderesDominio = poderesDominio;
    }

    public Boolean getRegistroCNBV() {
        return registroCNBV;
    }

    public void setRegistroCNBV(Boolean registroCNBV) {
        this.registroCNBV = registroCNBV;
    }

    public String getClaveContrato() {
        return claveContrato;
    }

    public void setClaveContrato(String claveContrato) {
        this.claveContrato = claveContrato;
    }
    
    public Boolean getContrato() {
        return contrato;
    }

    public void setContrato(Boolean contrato) {
        this.contrato = contrato;
    }
    
}
