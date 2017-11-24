package com.garante.gestionfideicomisos.Models;

import java.io.Serializable;

public class BancoABM implements Serializable {

    private Integer clave;
    private String banco;
    private String abreviacion;
    
    public Integer getClave() {
        return this.clave;
    }

    public void setClave(Integer clave) {
        this.clave = clave;
    }

    public String getBanco() {
        return this.banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getAbreviacion() {
        return this.abreviacion;
    }

    public void setAbreviacion(String abreviacion) {
        this.abreviacion = abreviacion;
    }

    @Override
    public String toString() {
        return "BancoABM{" + "clave=" + clave + ", banco=" + banco + ", abreviacion=" + abreviacion + '}';
    }

}
