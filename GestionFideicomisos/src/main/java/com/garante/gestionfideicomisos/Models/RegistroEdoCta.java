package com.garante.gestionfideicomisos.Models;

import java.io.Serializable;
import java.util.Date;

public class RegistroEdoCta implements Serializable {

    private Integer idRegistro;
    private Date fechaIni;
    private Date fechaFin;
    private Integer movimientos;
    private String estado;

    public Integer getIdRegistro() {
        return this.idRegistro;
    }

    public void setIdRegistro(Integer idRegistro) {
        this.idRegistro = idRegistro;
    }

    public Date getFechaIni() {
        return this.fechaIni;
    }

    public void setFechaIni(Date fechaIni) {
        this.fechaIni = fechaIni;
    }

    public Date getFechaFin() {
        return this.fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getMovimientos() {
        return this.movimientos;
    }

    public void setMovimientos(Integer movimientos) {
        this.movimientos = movimientos;
    }

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }


}
