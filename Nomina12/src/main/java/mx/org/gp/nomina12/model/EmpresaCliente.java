/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.org.gp.nomina12.model;

/**
 *
 * @author luis-valerio
 */
public class EmpresaCliente {
    
    public String rfc;
    public String entFed;
    public String claveContrato;

    public String getClaveContrato() {
        return claveContrato;
    }

    public void setClaveContrato(String claveContrato) {
        this.claveContrato = claveContrato;
    }    

    public String getEntFed() {
        return entFed;
    }

    public void setEntFed(String entFed) {
        this.entFed = entFed;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    @Override
    public String toString() {
        return "EmpresaCliente{" + "rfc=" + rfc + ", entFed=" + entFed + ", claveContrato=" + claveContrato + '}';
    }

}
