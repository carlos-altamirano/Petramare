package com.garante.gestionfideicomisos.Models;

import java.io.Serializable;

public class Moneda implements Serializable {

    private String clave;
    private String pais;
    private String divisa;

    public String getClave() {
        return this.clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getPais() {
        return this.pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getDivisa() {
        return this.divisa;
    }

    public void setDivisa(String divisa) {
        this.divisa = divisa;
    }
    
}
