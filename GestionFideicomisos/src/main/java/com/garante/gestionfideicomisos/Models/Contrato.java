package com.garante.gestionfideicomisos.Models;

import java.io.Serializable;
import java.util.Date;

public class Contrato implements Serializable {

    private String claveContrato;
    private String nombreCliente;
    private String cuentaOrigen;
    private String grupo;
    private String domicilioFiscal;
    private String rfc;
    private String telefono;
    private String correo;
    private String tipoHonorario;
    private Double honorarioSinIva;
    private String oficinas;
    private Date fechaCaptura;
    private String status;
    private Double saldo;
    private String idCodes;
    private String entFed;
    private String codPos;
    private Boolean pld;
    private Integer tipoPersona;
    private String curp;

    public Contrato() {
    }
    
    public Contrato(String claveContrato, String nombreCliente, String cuentaOrigen, String grupo, String domicilioFiscal, String rfc, String telefono, String correo, String tipoHonorario, Double honorarioSinIva, String oficinas, Date fechaCaptura, String status, Double saldo, String idCodes, String entFed, String codPos, Boolean pld, Integer tipoPersona, String curp) {
        this.claveContrato = claveContrato;
        this.nombreCliente = nombreCliente;
        this.cuentaOrigen = cuentaOrigen;
        this.grupo = grupo;
        this.domicilioFiscal = domicilioFiscal;
        this.rfc = rfc;
        this.telefono = telefono;
        this.correo = correo;
        this.tipoHonorario = tipoHonorario;
        this.honorarioSinIva = honorarioSinIva;
        this.oficinas = oficinas;
        this.fechaCaptura = fechaCaptura;
        this.status = status;
        this.saldo = saldo;
        this.idCodes = idCodes;
        this.entFed = entFed;
        this.codPos = codPos;
        this.pld = pld;
        this.tipoPersona = tipoPersona;
        this.curp = curp;
    }

    public String getClaveContrato() {
        return this.claveContrato;
    }

    public void setClaveContrato(String claveContrato) {
        this.claveContrato = claveContrato;
    }

    public String getNombreCliente() {
        return this.nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getCuentaOrigen() {
        return cuentaOrigen;
    }

    public void setCuentaOrigen(String cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

    public String getGrupo() {
        return this.grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getDomicilioFiscal() {
        return this.domicilioFiscal;
    }

    public void setDomicilioFiscal(String domicilioFiscal) {
        this.domicilioFiscal = domicilioFiscal;
    }

    public String getRfc() {
        return this.rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return this.correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTipoHonorario() {
        return this.tipoHonorario;
    }

    public void setTipoHonorario(String tipoHonorario) {
        this.tipoHonorario = tipoHonorario;
    }

    public Double getHonorarioSinIva() {
        return this.honorarioSinIva;
    }

    public void setHonorarioSinIva(Double honorarioSinIva) {
        this.honorarioSinIva = honorarioSinIva;
    }

    public String getOficinas() {
        return this.oficinas;
    }

    public void setOficinas(String oficinas) {
        this.oficinas = oficinas;
    }

    public Date getFechaCaptura() {
        return this.fechaCaptura;
    }

    public void setFechaCaptura(Date fechaCaptura) {
        this.fechaCaptura = fechaCaptura;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getSaldo() {
        return this.saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public String getIdCodes() {
        return this.idCodes;
    }

    public void setIdCodes(String idCodes) {
        this.idCodes = idCodes;
    }

    public String getEntFed() {
        return entFed;
    }

    public void setEntFed(String entFed) {
        this.entFed = entFed;
    }

    public String getCodPos() {
        return codPos;
    }

    public void setCodPos(String codPos) {
        this.codPos = codPos;
    }

    public Boolean getPld() {
        return pld;
    }

    public void setPld(Boolean pld) {
        this.pld = pld;
    }

    public Integer getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(Integer tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    @Override
    public String toString() {
        return "Contrato{" + "claveContrato=" + claveContrato + ", nombreCliente=" + nombreCliente + ", cuentaOrigen=" + cuentaOrigen + ", grupo=" + grupo + ", domicilioFiscal=" + domicilioFiscal + ", rfc=" + rfc + ", telefono=" + telefono + ", correo=" + correo + ", tipoHonorario=" + tipoHonorario + ", honorarioSinIva=" + honorarioSinIva + ", oficinas=" + oficinas + ", fechaCaptura=" + fechaCaptura + ", status=" + status + ", saldo=" + saldo + ", idCodes=" + idCodes + ", entFed=" + entFed + ", codPos=" + codPos + ", pld=" + pld + ", tipoPersona=" + tipoPersona + ", curp=" + curp + '}';
    }
    
}
