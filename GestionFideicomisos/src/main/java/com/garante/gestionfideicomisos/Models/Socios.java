package com.garante.gestionfideicomisos.Models;

import java.io.Serializable;

public class Socios implements Serializable {
    
    private Integer idRepre;
    private Integer tipo;
    private String nombre;
    private String rfc;
    private String curp;
    private Integer nacionalidad;
    private Double porcentaje;
    private String claveContrato;

    public Socios() {
    }

    public Socios(Integer idRepre, Integer tipo, String nombre, String rfc, String curp, Integer nacionalidad, Double porcentaje, String claveContrato) {
        this.idRepre = idRepre;
        this.tipo = tipo;
        this.nombre = nombre;
        this.rfc = rfc;
        this.curp = curp;
        this.nacionalidad = nacionalidad;
        this.porcentaje = porcentaje;
        this.claveContrato = claveContrato;
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
        return "Socios{" + "idRepre=" + idRepre + ", tipo=" + tipo + ", nombre=" + nombre + ", rfc=" + rfc + ", curp=" + curp + ", nacionalidad=" + nacionalidad + ", porcentaje=" + porcentaje + ", claveContrato=" + claveContrato + '}';
    }
    
}
