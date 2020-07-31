/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.garante.creaxml.Helpers;

import java.util.Objects;

/**
 *
 * @author Desarrollo
 */
public class ErroresXML {
    String rfc;
    String fechaHora;
    String error;

    public ErroresXML(String rfc, String fechaHora, String error) {
        this.rfc = rfc;
        this.fechaHora = fechaHora;
        this.error = error;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return rfc + "," + fechaHora + "," + error;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ErroresXML other = (ErroresXML) obj;
        if (!Objects.equals(this.rfc, other.rfc)) {
            return false;
        }
        return true;
    }   
}
