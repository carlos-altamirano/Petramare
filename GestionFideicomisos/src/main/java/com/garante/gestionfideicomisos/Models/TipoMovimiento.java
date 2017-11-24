package com.garante.gestionfideicomisos.Models;

import java.io.Serializable;

public class TipoMovimiento implements Serializable {

    private Integer tipoMovimiento;
    private String movimiento;

    public Integer getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(Integer tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public String getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(String movimiento) {
        this.movimiento = movimiento;
    }

}
