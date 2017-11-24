package com.garante.gestionfideicomisos.Models;

import java.io.Serializable;

public class CuentaBanco implements Serializable {

    private String cuentaOrigen;
    private String numCuenta;
    private String claveCuenta;
    private String status;
    
    public String getCuentaOrigen() {
        return this.cuentaOrigen;
    }

    public void setCuentaOrigen(String cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

    public String getNumCuenta() {
        return this.numCuenta;
    }

    public void setNumCuenta(String numCuenta) {
        this.numCuenta = numCuenta;
    }

    public String getClaveCuenta() {
        return this.claveCuenta;
    }

    public void setClaveCuenta(String claveCuenta) {
        this.claveCuenta = claveCuenta;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CuentaBanco{" + "cuentaOrigen=" + cuentaOrigen + ", numCuenta=" + numCuenta + ", claveCuenta=" + claveCuenta + ", status=" + status + '}';
    }
    
}
