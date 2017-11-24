package com.garante.gestionfideicomisos.Models;

import java.io.Serializable;

public class LogPLD implements Serializable {
    
    private Integer idLogPLD;
    
    private String usuario;
    
    private String accion;
    
    private String observaciones;

    public LogPLD() {
    }
    
    public LogPLD(Integer idLogPLD, String usuario, String accion, String observaciones) {
        this.idLogPLD = idLogPLD;
        this.usuario = usuario;
        this.accion = accion;
        this.observaciones = observaciones;
    }

    public Integer getIdLogPLD() {
        return idLogPLD;
    }

    public void setIdLogPLD(Integer idLogPLD) {
        this.idLogPLD = idLogPLD;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public String toString() {
        return "LogPLD{" + "idLogPLD=" + idLogPLD + ", usuario=" + usuario + ", accion=" + accion + ", observaciones=" + observaciones + '}';
    }
    
}
