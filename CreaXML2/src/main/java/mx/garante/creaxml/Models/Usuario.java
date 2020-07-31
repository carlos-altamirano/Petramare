package mx.garante.creaxml.Models;

public class Usuario {

    private String clave_contrato;
    private String clave_cliente;
    private String nombre_usuario;
    private String puesto_usuario;
    private String telefono_usuario;
    private String contacto_usuario;
    private String usuario;
    private String password;
    private String fecha_alta;
    private String fecha_bloqueo;
    private String status;

    public String getClave_contrato() {
        return clave_contrato;
    }

    public void setClave_contrato(String clave_contrato) {
        this.clave_contrato = clave_contrato;
    }

    public String getClave_cliente() {
        return clave_cliente;
    }

    public void setClave_cliente(String clave_cliente) {
        this.clave_cliente = clave_cliente;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getPuesto_usuario() {
        return puesto_usuario;
    }

    public void setPuesto_usuario(String puesto_usuario) {
        this.puesto_usuario = puesto_usuario;
    }

    public String getTelefono_usuario() {
        return telefono_usuario;
    }

    public void setTelefono_usuario(String telefono_usuario) {
        this.telefono_usuario = telefono_usuario;
    }

    public String getContacto_usuario() {
        return contacto_usuario;
    }

    public void setContacto_usuario(String contacto_usuario) {
        this.contacto_usuario = contacto_usuario;
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

    public String getFecha_alta() {
        return fecha_alta;
    }

    public void setFecha_alta(String fecha_alta) {
        this.fecha_alta = fecha_alta;
    }

    public String getFecha_bloqueo() {
        return fecha_bloqueo;
    }

    public void setFecha_bloqueo(String fecha_bloqueo) {
        this.fecha_bloqueo = fecha_bloqueo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
