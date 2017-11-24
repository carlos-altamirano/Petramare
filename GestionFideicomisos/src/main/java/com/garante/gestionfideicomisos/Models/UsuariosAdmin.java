package com.garante.gestionfideicomisos.Models;

import java.io.Serializable;

public class UsuariosAdmin implements Serializable {

    private String claveUsuario;
    private String nombreUsuario;
    private String departamento;
    private String correo;
    private String usuario;
    private String password;
    private String status;
    private String tipoCuenta;

    public String getClaveUsuario() {
        return claveUsuario;
    }

    public void setClaveUsuario(String claveUsuario) {
        this.claveUsuario = claveUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    @Override
    public String toString() {
        return "UsuariosSofom{" + "claveUsuario=" + claveUsuario + ", nombreUsuario=" + nombreUsuario + ", departamento=" + departamento + ", correo=" + correo + ", usuario=" + usuario + ", password=" + password + ", status=" + status + ", tipoCuenta=" + tipoCuenta + '}';
    }

}
