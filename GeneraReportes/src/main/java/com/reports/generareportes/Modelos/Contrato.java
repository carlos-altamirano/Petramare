package com.reports.generareportes.Modelos;

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

    @Override
    public String toString() {
        return "Contrato{" + "claveContrato=" + claveContrato + ", nombreCliente=" + nombreCliente + ", cuentaOrigen=" + cuentaOrigen + ", grupo=" + grupo + ", domicilioFiscal=" + domicilioFiscal + ", rfc=" + rfc + ", telefono=" + telefono + ", correo=" + correo + ", tipoHonorario=" + tipoHonorario + ", honorarioSinIva=" + honorarioSinIva + ", oficinas=" + oficinas + ", fechaCaptura=" + fechaCaptura + ", status=" + status + ", saldo=" + saldo + ", idCodes=" + idCodes + ", entFed=" + entFed + ", codPos=" + codPos + '}';
    }
    
}
