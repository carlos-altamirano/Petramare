package com.garante.gestionfideicomisos.Models;

import java.io.Serializable;

public class CodigoPostal implements Serializable {

    private Integer id;
    private String dCodigo;
    private String dAsenta;
    private String dTipoAsenta;
    private String dMnpio;
    private String dEstado;
    private String dCiudad;
    private String dCP;
    private String cEstado;
    private String cOficina;
    private String cCP;
    private String cTipoAsenta;
    private String cMnpio;
    private String idAsentaCpcons;
    private String dZona;
    private String cCveCiudad;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getdCodigo() {
        return dCodigo;
    }

    public void setdCodigo(String dCodigo) {
        this.dCodigo = dCodigo;
    }

    public String getdAsenta() {
        return dAsenta;
    }

    public void setdAsenta(String dAsenta) {
        this.dAsenta = dAsenta;
    }

    public String getdTipoAsenta() {
        return dTipoAsenta;
    }

    public void setdTipoAsenta(String dTipoAsenta) {
        this.dTipoAsenta = dTipoAsenta;
    }

    public String getdMnpio() {
        return dMnpio;
    }

    public void setdMnpio(String dMnpio) {
        this.dMnpio = dMnpio;
    }

    public String getdEstado() {
        return dEstado;
    }

    public void setdEstado(String dEstado) {
        this.dEstado = dEstado;
    }

    public String getdCiudad() {
        return dCiudad;
    }

    public void setdCiudad(String dCiudad) {
        this.dCiudad = dCiudad;
    }

    public String getdCP() {
        return dCP;
    }

    public void setdCP(String dCP) {
        this.dCP = dCP;
    }

    public String getcEstado() {
        return cEstado;
    }

    public void setcEstado(String cEstado) {
        this.cEstado = cEstado;
    }

    public String getcOficina() {
        return cOficina;
    }

    public void setcOficina(String cOficina) {
        this.cOficina = cOficina;
    }

    public String getcCP() {
        return cCP;
    }

    public void setcCP(String cCP) {
        this.cCP = cCP;
    }

    public String getcTipoAsenta() {
        return cTipoAsenta;
    }

    public void setcTipoAsenta(String cTipoAsenta) {
        this.cTipoAsenta = cTipoAsenta;
    }

    public String getcMnpio() {
        return cMnpio;
    }

    public void setcMnpio(String cMnpio) {
        this.cMnpio = cMnpio;
    }

    public String getIdAsentaCpcons() {
        return idAsentaCpcons;
    }

    public void setIdAsentaCpcons(String idAsentaCpcons) {
        this.idAsentaCpcons = idAsentaCpcons;
    }

    public String getdZona() {
        return dZona;
    }

    public void setdZona(String dZona) {
        this.dZona = dZona;
    }

    public String getcCveCiudad() {
        return cCveCiudad;
    }

    public void setcCveCiudad(String cCveCiudad) {
        this.cCveCiudad = cCveCiudad;
    }

    @Override
    public String toString() {
        return "CodigoPostale{" + "id=" + id + ", dCodigo=" + dCodigo + ", dAsenta=" + dAsenta + ", dTipoAsenta=" + dTipoAsenta + ", dMnpio=" + dMnpio + ", dEstado=" + dEstado + ", dCiudad=" + dCiudad + ", dCP=" + dCP + ", cEstado=" + cEstado + ", cOficina=" + cOficina + ", cCP=" + cCP + ", cTipoAsenta=" + cTipoAsenta + ", cMnpio=" + cMnpio + ", idAsentaCpcons=" + idAsentaCpcons + ", dZona=" + dZona + ", cCveCiudad=" + cCveCiudad + '}';
    }
    
}
