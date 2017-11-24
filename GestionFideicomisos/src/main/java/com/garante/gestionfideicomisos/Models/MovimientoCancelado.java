package com.garante.gestionfideicomisos.Models;

import java.io.Serializable;
import java.util.Date;

public class MovimientoCancelado implements Serializable {

    private String claveContrato;
    private Date fechaLiquidacion;
    private String nombreArchivo;
    private Integer tipoMovimiento;
    private Integer claveBanco;
    private String tipoMoneda;
    private String nombreEmpleado;
    private String apellidoPEmpleado;
    private String apellidoMEmpleado;
    private String claveEmpleado;
    private String importeLiquidacion;
    private String cuentaDeposito;
    private String curp;
    private Date fechaIngreso;
    private String puestoEmpleado;
    private String deptoEmpleado;
    private String envioCheque;
    private String destinoEnvioCheque;
    private String telEnvioCheque;
    private String correoEnvioCheque;
    private String bancoExtranjero;
    private String domBancoExtranjero;
    private String paisBancoExtranjero;
    private String abaBic;
    private String nombreFideiBancoExt;
    private String direccionFideiExt;
    private String paisFideiExt;
    private String telFideiExt;
    private String usuarioCancela;
    private Date fechaUsuarioCancela;
    private String motivo;
    private String status;

    public String getClaveContrato() {
        return claveContrato;
    }

    public void setClaveContrato(String claveContrato) {
        this.claveContrato = claveContrato;
    }

    public Date getFechaLiquidacion() {
        return fechaLiquidacion;
    }

    public void setFechaLiquidacion(Date fechaLiquidacion) {
        this.fechaLiquidacion = fechaLiquidacion;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public Integer getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(Integer tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public Integer getClaveBanco() {
        return claveBanco;
    }

    public void setClaveBanco(Integer claveBanco) {
        this.claveBanco = claveBanco;
    }

    public String getTipoMoneda() {
        return tipoMoneda;
    }

    public void setTipoMoneda(String tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getApellidoPEmpleado() {
        return apellidoPEmpleado;
    }

    public void setApellidoPEmpleado(String apellidoPEmpleado) {
        this.apellidoPEmpleado = apellidoPEmpleado;
    }

    public String getApellidoMEmpleado() {
        return apellidoMEmpleado;
    }

    public void setApellidoMEmpleado(String apellidoMEmpleado) {
        this.apellidoMEmpleado = apellidoMEmpleado;
    }

    public String getClaveEmpleado() {
        return claveEmpleado;
    }

    public void setClaveEmpleado(String claveEmpleado) {
        this.claveEmpleado = claveEmpleado;
    }

    public String getImporteLiquidacion() {
        return importeLiquidacion;
    }

    public void setImporteLiquidacion(String importeLiquidacion) {
        this.importeLiquidacion = importeLiquidacion;
    }

    public String getCuentaDeposito() {
        return cuentaDeposito;
    }

    public void setCuentaDeposito(String cuentaDeposito) {
        this.cuentaDeposito = cuentaDeposito;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getPuestoEmpleado() {
        return puestoEmpleado;
    }

    public void setPuestoEmpleado(String puestoEmpleado) {
        this.puestoEmpleado = puestoEmpleado;
    }

    public String getDeptoEmpleado() {
        return deptoEmpleado;
    }

    public void setDeptoEmpleado(String deptoEmpleado) {
        this.deptoEmpleado = deptoEmpleado;
    }

    public String getEnvioCheque() {
        return envioCheque;
    }

    public void setEnvioCheque(String envioCheque) {
        this.envioCheque = envioCheque;
    }

    public String getDestinoEnvioCheque() {
        return destinoEnvioCheque;
    }

    public void setDestinoEnvioCheque(String destinoEnvioCheque) {
        this.destinoEnvioCheque = destinoEnvioCheque;
    }

    public String getTelEnvioCheque() {
        return telEnvioCheque;
    }

    public void setTelEnvioCheque(String telEnvioCheque) {
        this.telEnvioCheque = telEnvioCheque;
    }

    public String getCorreoEnvioCheque() {
        return correoEnvioCheque;
    }

    public void setCorreoEnvioCheque(String correoEnvioCheque) {
        this.correoEnvioCheque = correoEnvioCheque;
    }

    public String getBancoExtranjero() {
        return bancoExtranjero;
    }

    public void setBancoExtranjero(String bancoExtranjero) {
        this.bancoExtranjero = bancoExtranjero;
    }

    public String getDomBancoExtranjero() {
        return domBancoExtranjero;
    }

    public void setDomBancoExtranjero(String domBancoExtranjero) {
        this.domBancoExtranjero = domBancoExtranjero;
    }

    public String getPaisBancoExtranjero() {
        return paisBancoExtranjero;
    }

    public void setPaisBancoExtranjero(String paisBancoExtranjero) {
        this.paisBancoExtranjero = paisBancoExtranjero;
    }

    public String getAbaBic() {
        return abaBic;
    }

    public void setAbaBic(String abaBic) {
        this.abaBic = abaBic;
    }

    public String getNombreFideiBancoExt() {
        return nombreFideiBancoExt;
    }

    public void setNombreFideiBancoExt(String nombreFideiBancoExt) {
        this.nombreFideiBancoExt = nombreFideiBancoExt;
    }

    public String getDireccionFideiExt() {
        return direccionFideiExt;
    }

    public void setDireccionFideiExt(String direccionFideiExt) {
        this.direccionFideiExt = direccionFideiExt;
    }

    public String getPaisFideiExt() {
        return paisFideiExt;
    }

    public void setPaisFideiExt(String paisFideiExt) {
        this.paisFideiExt = paisFideiExt;
    }

    public String getTelFideiExt() {
        return telFideiExt;
    }

    public void setTelFideiExt(String telFideiExt) {
        this.telFideiExt = telFideiExt;
    }

    public String getUsuarioCancela() {
        return usuarioCancela;
    }

    public void setUsuarioCancela(String usuarioCancela) {
        this.usuarioCancela = usuarioCancela;
    }

    public Date getFechaUsuarioCancela() {
        return fechaUsuarioCancela;
    }

    public void setFechaUsuarioCancela(Date fechaUsuarioCancela) {
        this.fechaUsuarioCancela = fechaUsuarioCancela;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
