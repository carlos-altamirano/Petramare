package com.garante.gestionfideicomisos.Models;

import java.io.Serializable;
import java.util.Date;

public class SaldoInicioMes implements Serializable {

    private Date fecha;
    private String claveContrato;
    private Double saldo;

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getClaveContrato() {
        return claveContrato;
    }

    public void setClaveContrato(String claveContrato) {
        this.claveContrato = claveContrato;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

}
