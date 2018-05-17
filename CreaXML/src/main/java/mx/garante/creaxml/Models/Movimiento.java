package mx.garante.creaxml.Models;

import java.util.Date;

public class Movimiento {

    private String clave_contrato;
    private Date fecha_liquidacion;
    private String tipo_movimiento;
    private String clave_banco;
    private String tipo_moneda;
    private String nombre_empleado;
    private String apellidoP_empleado;
    private String apellidoM_empleado;
    private String clave_empleado;
    private String importe_liquidacion;
    private String importe_liquidacion_mxp;
    private String cuenta_deposito;
    private String curp;
    private Date fecha_ingreso;
    private String puesto_empleado;
    private String depto_empleado;
    private String envio_cheque;
    private String destino_envio_cheque;
    private String tel_envio_cheque;
    private String correo_envio_cheque;
    private String banco_extranjero;
    private String dom_banco_extranjero;
    private String pais_banco_extranjero;
    private String ABA_BIC;
    private String nombre_fidei_banco_ext;
    private String direccion_fidei_ext;
    private String pais_fidei_ext;
    private String tel_fidei_ext;
    private String nombre_archivo;
    private String rfc;

    public String getClave_contrato() {
        return clave_contrato;
    }

    public void setClave_contrato(String clave_contrato) {
        this.clave_contrato = clave_contrato;
    }

    public Date getFecha_liquidacion() {
        return fecha_liquidacion;
    }

    public void setFecha_liquidacion(Date fecha_liquidacion) {
        this.fecha_liquidacion = fecha_liquidacion;
    }

    public String getTipo_movimiento() {
        return tipo_movimiento;
    }

    public void setTipo_movimiento(String tipo_movimiento) {
        this.tipo_movimiento = tipo_movimiento;
    }

    public String getClave_banco() {
        return clave_banco;
    }

    public void setClave_banco(String clave_banco) {
        this.clave_banco = clave_banco;
    }

    public String getTipo_moneda() {
        return tipo_moneda;
    }

    public void setTipo_moneda(String tipo_moneda) {
        this.tipo_moneda = tipo_moneda;
    }

    public String getNombre_empleado() {
        return nombre_empleado;
    }

    public void setNombre_empleado(String nombre_empleado) {
        this.nombre_empleado = nombre_empleado;
    }

    public String getApellidoP_empleado() {
        return apellidoP_empleado;
    }

    public void setApellidoP_empleado(String apellidoP_empleado) {
        this.apellidoP_empleado = apellidoP_empleado;
    }

    public String getApellidoM_empleado() {
        return apellidoM_empleado;
    }

    public void setApellidoM_empleado(String apellidoM_empleado) {
        this.apellidoM_empleado = apellidoM_empleado;
    }

    public String getClave_empleado() {
        return clave_empleado;
    }

    public void setClave_empleado(String clave_empleado) {
        this.clave_empleado = clave_empleado;
    }

    public String getImporte_liquidacion() {
        return importe_liquidacion;
    }

    public void setImporte_liquidacion(String importe_liquidacion) {
        this.importe_liquidacion = importe_liquidacion;
    }

    public String getImporte_liquidacion_mxp() {
        return importe_liquidacion_mxp;
    }

    public void setImporte_liquidacion_mxp(String importe_liquidacion_mxp) {
        this.importe_liquidacion_mxp = importe_liquidacion_mxp;
    }

    public String getCuenta_deposito() {
        return cuenta_deposito;
    }

    public void setCuenta_deposito(String cuenta_deposito) {
        this.cuenta_deposito = cuenta_deposito;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public Date getFecha_ingreso() {
        return fecha_ingreso;
    }

    public void setFecha_ingreso(Date fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    public String getPuesto_empleado() {
        return puesto_empleado;
    }

    public void setPuesto_empleado(String puesto_empleado) {
        this.puesto_empleado = puesto_empleado;
    }

    public String getDepto_empleado() {
        return depto_empleado;
    }

    public void setDepto_empleado(String depto_empleado) {
        this.depto_empleado = depto_empleado;
    }

    public String getEnvio_cheque() {
        return envio_cheque;
    }

    public void setEnvio_cheque(String envio_cheque) {
        this.envio_cheque = envio_cheque;
    }

    public String getDestino_envio_cheque() {
        return destino_envio_cheque;
    }

    public void setDestino_envio_cheque(String destino_envio_cheque) {
        this.destino_envio_cheque = destino_envio_cheque;
    }

    public String getTel_envio_cheque() {
        return tel_envio_cheque;
    }

    public void setTel_envio_cheque(String tel_envio_cheque) {
        this.tel_envio_cheque = tel_envio_cheque;
    }

    public String getCorreo_envio_cheque() {
        return correo_envio_cheque;
    }

    public void setCorreo_envio_cheque(String correo_envio_cheque) {
        this.correo_envio_cheque = correo_envio_cheque;
    }

    public String getBanco_extranjero() {
        return banco_extranjero;
    }

    public void setBanco_extranjero(String banco_extranjero) {
        this.banco_extranjero = banco_extranjero;
    }

    public String getDom_banco_extranjero() {
        return dom_banco_extranjero;
    }

    public void setDom_banco_extranjero(String dom_banco_extranjero) {
        this.dom_banco_extranjero = dom_banco_extranjero;
    }

    public String getPais_banco_extranjero() {
        return pais_banco_extranjero;
    }

    public void setPais_banco_extranjero(String pais_banco_extranjero) {
        this.pais_banco_extranjero = pais_banco_extranjero;
    }

    public String getABA_BIC() {
        return ABA_BIC;
    }

    public void setABA_BIC(String ABA_BIC) {
        this.ABA_BIC = ABA_BIC;
    }

    public String getNombre_fidei_banco_ext() {
        return nombre_fidei_banco_ext;
    }

    public void setNombre_fidei_banco_ext(String nombre_fidei_banco_ext) {
        this.nombre_fidei_banco_ext = nombre_fidei_banco_ext;
    }

    public String getDireccion_fidei_ext() {
        return direccion_fidei_ext;
    }

    public void setDireccion_fidei_ext(String direccion_fidei_ext) {
        this.direccion_fidei_ext = direccion_fidei_ext;
    }

    public String getPais_fidei_ext() {
        return pais_fidei_ext;
    }

    public void setPais_fidei_ext(String pais_fidei_ext) {
        this.pais_fidei_ext = pais_fidei_ext;
    }

    public String getTel_fidei_ext() {
        return tel_fidei_ext;
    }

    public void setTel_fidei_ext(String tel_fidei_ext) {
        this.tel_fidei_ext = tel_fidei_ext;
    }

    public String getNombre_archivo() {
        return nombre_archivo;
    }

    public void setNombre_archivo(String nombre_archivo) {
        this.nombre_archivo = nombre_archivo;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    @Override
    public String toString() {
        return "Movimiento{" + "clave_contrato=" + clave_contrato + ", fecha_liquidacion=" + fecha_liquidacion + ", tipo_movimiento=" + tipo_movimiento + ", clave_banco=" + clave_banco + ", tipo_moneda=" + tipo_moneda + ", nombre_empleado=" + nombre_empleado + ", apellidoP_empleado=" + apellidoP_empleado + ", apellidoM_empleado=" + apellidoM_empleado + ", clave_empleado=" + clave_empleado + ", importe_liquidacion=" + importe_liquidacion + ", importe_liquidacion_mxp=" + importe_liquidacion_mxp + ", cuenta_deposito=" + cuenta_deposito + ", curp=" + curp + ", fecha_ingreso=" + fecha_ingreso + ", puesto_empleado=" + puesto_empleado + ", depto_empleado=" + depto_empleado + ", envio_cheque=" + envio_cheque + ", destino_envio_cheque=" + destino_envio_cheque + ", tel_envio_cheque=" + tel_envio_cheque + ", correo_envio_cheque=" + correo_envio_cheque + ", banco_extranjero=" + banco_extranjero + ", dom_banco_extranjero=" + dom_banco_extranjero + ", pais_banco_extranjero=" + pais_banco_extranjero + ", ABA_BIC=" + ABA_BIC + ", nombre_fidei_banco_ext=" + nombre_fidei_banco_ext + ", direccion_fidei_ext=" + direccion_fidei_ext + ", pais_fidei_ext=" + pais_fidei_ext + ", tel_fidei_ext=" + tel_fidei_ext + ", nombre_archivo=" + nombre_archivo + ", rfc=" + rfc + '}';
    }
    
}
