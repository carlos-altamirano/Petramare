package com.garante.gestionfideicomisos.Models;

import java.io.Serializable;
import java.util.Date;

public class MovimientoH implements Serializable {

    private String claveCliente;
    private String claveContrato;
    private Date fechaLiquidacion;
    private String usuarioCliente;
    private String nombreArchivo;
    private Date fechaCaptura;
    private String status;
    private String usuarioOpera;
    private Date fechaUsuarioOpera;
    private String statusAutoriza;
    private String usaurioAutoriza;
    private Date fechaUsuarioAutoriza;
    private String claveArchivo;
    private String reportesLiquidacionMxp;
    private String reportesLiquidacionIn;

    public String getClaveCliente() {
        return claveCliente;
    }

    public void setClaveCliente(String claveCliente) {
        this.claveCliente = claveCliente;
    }

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

    public String getUsuarioCliente() {
        return usuarioCliente;
    }

    public void setUsuarioCliente(String usuarioCliente) {
        this.usuarioCliente = usuarioCliente;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public Date getFechaCaptura() {
        return fechaCaptura;
    }

    public void setFechaCaptura(Date fechaCaptura) {
        this.fechaCaptura = fechaCaptura;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsuarioOpera() {
        return usuarioOpera;
    }

    public void setUsuarioOpera(String usuarioOpera) {
        this.usuarioOpera = usuarioOpera;
    }

    public Date getFechaUsuarioOpera() {
        return fechaUsuarioOpera;
    }

    public void setFechaUsuarioOpera(Date fechaUsuarioOpera) {
        this.fechaUsuarioOpera = fechaUsuarioOpera;
    }

    public String getStatusAutoriza() {
        return statusAutoriza;
    }

    public void setStatusAutoriza(String statusAutoriza) {
        this.statusAutoriza = statusAutoriza;
    }

    public String getUsaurioAutoriza() {
        return usaurioAutoriza;
    }

    public void setUsaurioAutoriza(String usaurioAutoriza) {
        this.usaurioAutoriza = usaurioAutoriza;
    }

    public Date getFechaUsuarioAutoriza() {
        return fechaUsuarioAutoriza;
    }

    public void setFechaUsuarioAutoriza(Date fechaUsuarioAutoriza) {
        this.fechaUsuarioAutoriza = fechaUsuarioAutoriza;
    }

    public String getClaveArchivo() {
        return claveArchivo;
    }

    public void setClaveArchivo(String claveArchivo) {
        this.claveArchivo = claveArchivo;
    }

    public String getReportesLiquidacionMxp() {
        return reportesLiquidacionMxp;
    }

    public void setReportesLiquidacionMxp(String reportesLiquidacionMxp) {
        this.reportesLiquidacionMxp = reportesLiquidacionMxp;
    }

    public String getReportesLiquidacionIn() {
        return reportesLiquidacionIn;
    }

    public void setReportesLiquidacionIn(String reportesLiquidacionIn) {
        this.reportesLiquidacionIn = reportesLiquidacionIn;
    }

}
