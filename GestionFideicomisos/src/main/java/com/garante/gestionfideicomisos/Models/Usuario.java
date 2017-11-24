package com.garante.gestionfideicomisos.Models;

import java.io.Serializable;
import java.util.Date;

public class Usuario implements Serializable {

    private String claveContrato;
    private String claveCliente;
    private String nombreUsuario;
    private String puesto;
    private String telefono;
    private String contactoUsuario;
    private String usuario;
    private String password;
    private Date fechaAlta;
    private Date fechaBloqueo;
    private String status;

    public String getClaveContrato() {
        return claveContrato;
    }

    public void setClaveContrato(String claveContrato) {
        this.claveContrato = claveContrato;
    }

    public String getClaveCliente() {
        return claveCliente;
    }

    public void setClaveCliente(String claveCliente) {
        this.claveCliente = claveCliente;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getContactoUsuario() {
        return contactoUsuario;
    }

    public void setContactoUsuario(String contactoUsuario) {
        this.contactoUsuario = contactoUsuario;
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

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Date getFechaBloqueo() {
        return fechaBloqueo;
    }

    public void setFechaBloqueo(Date fechaBloqueo) {
        this.fechaBloqueo = fechaBloqueo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Usuario{" + "claveContrato=" + claveContrato + ", claveCliente=" + claveCliente + ", nombreUsuario=" + nombreUsuario + ", puesto=" + puesto + ", telefono=" + telefono + ", contactoUsuario=" + contactoUsuario + ", usuario=" + usuario + ", password=" + password + ", fechaAlta=" + fechaAlta + ", fechaBloqueo=" + fechaBloqueo + ", status=" + status + '}';
    }

}
