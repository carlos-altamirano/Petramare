/*
 *    Creado por:                   Luis Antio Valerio Gayosso
 *    Fecha:                        21/06/2011
 *    Descripción:                  Bean : "Usuario.java" Almacena los datos del usuario
 *    Responsable:                  Carlos Altamirano
 */
package Beans;

public class Usuario {

    String clave_usuario = "";
    String nombre_usuario = "";
    String usuario = "";
    String password = "";
    boolean autentificado = false;

    public String getClave_usuario() {
        return clave_usuario;
    }

    public void setClave_usuario(String clave_usuario) {
        this.clave_usuario = clave_usuario;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
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

    public boolean isAutentificado() {
        return autentificado;
    }

    public void setAutentificado(boolean autentificado) {
        this.autentificado = autentificado;
    }
}
