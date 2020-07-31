package mx.garante.creaxml.Models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Contrato {

    private String clave_contrato;
    private String nombre_cliente;
    private String cuenta_origen;
    private String grupo;
    private String domicilio_fiscal;
    private String RFC;
    private String telefono;
    private String correo;
    private String tipo_honorario;
    private Float honorario_sin_iva;
    private String oficinas;
    private Date fecha_captura;
    private String status;
    private Float saldo;
    private String id_codes;
    private String ent_fed;
    private String cod_pos;
    private List<EdoCta> edoCtas = new ArrayList<>();

    public String getClave_contrato() {
        return clave_contrato;
    }

    public void setClave_contrato(String clave_contrato) {
        this.clave_contrato = clave_contrato;
    }

    public String getNombre_cliente() {
        return nombre_cliente;
    }

    public void setNombre_cliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
    }

    public String getCuenta_origen() {
        return cuenta_origen;
    }

    public void setCuenta_origen(String cuenta_origen) {
        this.cuenta_origen = cuenta_origen;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getDomicilio_fiscal() {
        return domicilio_fiscal;
    }

    public void setDomicilio_fiscal(String domicilio_fiscal) {
        this.domicilio_fiscal = domicilio_fiscal;
    }

    public String getRFC() {
        return RFC;
    }

    public void setRFC(String RFC) {
        this.RFC = RFC;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTipo_honorario() {
        return tipo_honorario;
    }

    public void setTipo_honorario(String tipo_honorario) {
        this.tipo_honorario = tipo_honorario;
    }

    public Float getHonorario_sin_iva() {
        return honorario_sin_iva;
    }

    public void setHonorario_sin_iva(Float honorario_sin_iva) {
        this.honorario_sin_iva = honorario_sin_iva;
    }

    public String getOficinas() {
        return oficinas;
    }

    public void setOficinas(String oficinas) {
        this.oficinas = oficinas;
    }

    public Date getFecha_captura() {
        return fecha_captura;
    }

    public void setFecha_captura(Date fecha_captura) {
        this.fecha_captura = fecha_captura;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Float getSaldo() {
        return saldo;
    }

    public void setSaldo(Float saldo) {
        this.saldo = saldo;
    }

    public String getId_codes() {
        return id_codes;
    }

    public void setId_codes(String id_codes) {
        this.id_codes = id_codes;
    }

    public String getEnt_fed() {
        return ent_fed;
    }

    public void setEnt_fed(String ent_fed) {
        this.ent_fed = ent_fed;
    }

    public String getCod_pos() {
        return cod_pos;
    }

    public void setCod_pos(String cod_pos) {
        this.cod_pos = cod_pos;
    }

    public List<EdoCta> getEdoCtas() {
        return edoCtas;
    }

    public void setEdoCtas(List<EdoCta> edoCtas) {
        this.edoCtas = edoCtas;
    }

    @Override
    public String toString() {
        return "Contrato{" + "clave_contrato=" + clave_contrato + ", nombre_cliente=" + nombre_cliente + ", cuenta_origen=" + cuenta_origen + ", grupo=" + grupo + ", domicilio_fiscal=" + domicilio_fiscal + ", RFC=" + RFC + ", telefono=" + telefono + ", correo=" + correo + ", tipo_honorario=" + tipo_honorario + ", honorario_sin_iva=" + honorario_sin_iva + ", oficinas=" + oficinas + ", fecha_captura=" + fecha_captura + ", status=" + status + ", saldo=" + saldo + ", id_codes=" + id_codes + ", ent_fed=" + ent_fed + ", cod_pos=" + cod_pos + '}';
    }
    
}
