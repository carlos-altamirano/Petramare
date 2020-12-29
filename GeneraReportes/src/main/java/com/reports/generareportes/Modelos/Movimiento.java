package com.reports.generareportes.Modelos;

import java.io.Serializable;

public class Movimiento implements Serializable {
    
    private String claveContrato;
    private String fechaUsuarioAutoriza;
    private String fechaLiquidacion;
    private String claveEmpleado;
    private String nombreEmpleado;
    private String apellidoPEmpleado;
    private String apellidoMEmpleado;
    private String curp;
    private String rfc;
    private String cuentaDeposito;
    private String fechaIngreso;
    private String deptoEmpleado;
    private String puestoEmpleado;
    private String tipo;
    private String importeLiquidacion;
    private String importeLiquidacionMxn;
    private String uuid;

    public String getClaveContrato() {
        return claveContrato;
    }

    public void setClaveContrato(String claveContrato) {
        this.claveContrato = claveContrato;
    }

    public String getFechaUsuarioAutoriza() {
        return fechaUsuarioAutoriza;
    }

    public void setFechaUsuarioAutoriza(String fechaUsuarioAutoriza) {
        this.fechaUsuarioAutoriza = fechaUsuarioAutoriza;
    }

    public String getFechaLiquidacion() {
        return fechaLiquidacion;
    }

    public void setFechaLiquidacion(String fechaLiquidacion) {
        this.fechaLiquidacion = fechaLiquidacion;
    }

    public String getClaveEmpleado() {
        return claveEmpleado;
    }

    public void setClaveEmpleado(String claveEmpleado) {
        this.claveEmpleado = claveEmpleado;
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

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getCuentaDeposito() {
        return cuentaDeposito;
    }

    public void setCuentaDeposito(String cuentaDeposito) {
        this.cuentaDeposito = cuentaDeposito;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getDeptoEmpleado() {
        return deptoEmpleado;
    }

    public void setDeptoEmpleado(String deptoEmpleado) {
        this.deptoEmpleado = deptoEmpleado;
    }

    public String getPuestoEmpleado() {
        return puestoEmpleado;
    }

    public void setPuestoEmpleado(String puestoEmpleado) {
        this.puestoEmpleado = puestoEmpleado;
    }

    public String getImporteLiquidacion() {
        return importeLiquidacion;
    }

    public void setImporteLiquidacion(String importeLiquidacion) {
        this.importeLiquidacion = importeLiquidacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getImporteLiquidacionMxn() {
        return importeLiquidacionMxn;
    }

    public void setImporteLiquidacionMxn(String importeLiquidacionMxn) {
        this.importeLiquidacionMxn = importeLiquidacionMxn;
    }

    @Override
    public String toString() {
        return "Movimiento{" + "claveContrato=" + claveContrato + ", fechaUsuarioAutoriza=" + fechaUsuarioAutoriza + ", fechaLiquidacion=" + fechaLiquidacion + ", claveEmpleado=" + claveEmpleado + ", nombreEmpleado=" + nombreEmpleado + ", apellidoPEmpleado=" + apellidoPEmpleado + ", apellidoMEmpleado=" + apellidoMEmpleado + ", curp=" + curp + ", rfc=" + rfc + ", cuentaDeposito=" + cuentaDeposito + ", fechaIngreso=" + fechaIngreso + ", deptoEmpleado=" + deptoEmpleado + ", puestoEmpleado=" + puestoEmpleado + ", importeLiquidacion=" + importeLiquidacion + '}';
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    
}
