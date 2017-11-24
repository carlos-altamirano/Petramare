package com.garante.gestionfideicomisos.Models;

import java.io.Serializable;

public class Banco implements Serializable {

    private Integer clave;
    private String banco;
    
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

    @Override
    public String toString() {
        return "Banco{" + "clave=" + clave + ", banco=" + banco + '}';
    }
    
}
