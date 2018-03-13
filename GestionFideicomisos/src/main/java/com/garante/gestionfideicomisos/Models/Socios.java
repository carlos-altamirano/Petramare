package com.garante.gestionfideicomisos.Models;

import java.io.Serializable;

public class Socios implements Serializable {
    
    private Integer idRepre;
    private Integer tipo;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String rfc;
    private String curp;
    private Integer nacionalidad;
    private Double porcentaje;
    private String claveContrato;

    public Socios() {
    }

    public Socios(Integer idRepre, Integer tipo, String nombre, String apellido1, String apellido2, String rfc, String curp, Integer nacionalidad, Double porcentaje, String claveContrato) {
        this.idRepre = idRepre;
        this.tipo = tipo;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.rfc = rfc;
        this.curp = curp;
        this.nacionalidad = nacionalidad;
        this.porcentaje = porcentaje;
        this.claveContrato = claveContrato;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }
    
    public Integer getIdRepre() {
        return idRepre;
    }

    public void setIdRepre(Integer idRepre) {
        this.idRepre = idRepre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public Integer getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(Integer nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public Double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public String getClaveContrato() {
        return claveContrato;
    }

    public void setClaveContrato(String claveContrato) {
        this.claveContrato = claveContrato;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Socios{" + "idRepre=" + idRepre + ", tipo=" + tipo + ", nombre=" + nombre + ", apellido1=" + apellido1 + ", apellido2=" + apellido2 + ", rfc=" + rfc + ", curp=" + curp + ", nacionalidad=" + nacionalidad + ", porcentaje=" + porcentaje + ", claveContrato=" + claveContrato + '}';
    }
    
}
