package mx.garante.creaxml.Models;

import java.util.Date;

public class EdoCta {

    private Date fecha;
    private String concepto;
    private String observaciones;
    private Double cargo;
    private Double abono;
    private Double saldo;
    private String usuario_genera;
    private String nombre_archivo;
    private Contrato contrato;

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Double getCargo() {
        return cargo;
    }

    public void setCargo(Double cargo) {
        this.cargo = cargo;
    }

    public Double getAbono() {
        return abono;
    }

    public void setAbono(Double abono) {
        this.abono = abono;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public String getUsuario_genera() {
        return usuario_genera;
    }

    public void setUsuario_genera(String usuario_genera) {
        this.usuario_genera = usuario_genera;
    }

    public String getNombre_archivo() {
        return nombre_archivo;
    }

    public void setNombre_archivo(String nombre_archivo) {
        this.nombre_archivo = nombre_archivo;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    @Override
    public String toString() {
        return "EdoCta{" + "fecha=" + fecha + ", concepto=" + concepto + ", observaciones=" + observaciones + ", cargo=" + cargo + ", abono=" + abono + ", saldo=" + saldo + ", usuario_genera=" + usuario_genera + ", nombre_archivo=" + nombre_archivo + '}';
    }
    
}
