package mx.garante.liquidaciones.Beans;


public class Usuario {

    String cliente = "";
    String id_cliente = "";
    String usuario = "";
    String password = "";
    String clave_contrato = "";
    boolean autentificado = false;

    public Usuario() {
        cliente = "";
        id_cliente = "";
        usuario = "";
        password = "";
        clave_contrato = "";
        autentificado = false;
    }

    public String getClave_contrato() {
        return clave_contrato;
    }

    public void setClave_contrato(String clave_contrato) {
        this.clave_contrato = clave_contrato;
    }

    public boolean isAutentificado() {
        return autentificado;
    }

    public void setAutentificado(boolean autentificado) {
        this.autentificado = autentificado;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(String id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
