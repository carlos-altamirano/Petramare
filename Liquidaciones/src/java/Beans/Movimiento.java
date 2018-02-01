package Beans;

public class Movimiento {

    //Variable que almacena la clave de fideicomiso.
    String clave_contrato = "";
    //Variable que almacena la fecha de liquidación.
    String fecha_liquidacion = "";
    //Variable que almacena el tipo de movimiento.
    String tipo_movimiento = "";
    //Variable que almacena la clave de banco de la cuenta del fideicomisario.
    String clave_banco = "";
    //Variable que almacena la clave de moneda para la liquidación.
    String clave_moneda = "";
    //Variable que almacena los nombres del fideicomisario.
    String nombre_empleado = "";
    //Variable que almacena el apellido paterno del fideicomisario.
    String apellidoP_empleado = "";
    //Variable que almacena  el apellido materno del fideicomisario.
    String apellidoM_empleado = "";
    String nombre_fideicomisario = apellidoP_empleado + " " + apellidoM_empleado + " " + nombre_empleado;
    //Variable que almacena el número de cuenta del fideicomisario.
    String cuenta_deposito = "";
    //Variable que almacena el importe de liquidación.
    String importe_liquidacion = "";
    //Variable que almacena la clave o número de control de nómina del fideicomisario.
    String numero_empleado = "";
    //Variable que almacena la CURP del fideicomisario.
    String CURP = "";
    //Variable que almacena el RFC del fideicomisario.
    String RFC = "";
    //Variable que almacena la fecha de ingreso del fideicomisario al empleo.
    String fecha_ingreso = "";
    //Variable que almacena el puesto del fideicomisario.
    String puesto_empleado = "";
    //Variable que almacena el departamento del fideicomisario.
    String departamento_empleado = "";
    //Variable que almacena el nombre de la persona a quién se enviará el cheque.
    String nombre_receptor_cheque = "";
    //Variable que almacena el domicilio de la persona a quién se enviará el cheque.
    String domicilio_destino_cheque = "";
    //Variable que almacena el teléfono de la persona a quién se enviará el cheque.
    String tel_destino_cheque = "";
    //Variable que almacena el correo de la persona a quién se enviará el cheque.
    String correo_destino_cheque = "";
    //Variable que almacena el nombre del banco que posee la cuenta del fideicomisario.
    String nombre_banco_extranjero = "";
    //Variable que almacena el domicilio del banco que posee la cuenta del fideicomisario.
    String domicilio_banco_extranjero = "";
    //Variable que almacena el país del banco que posee la cuenta del fideicomisario.
    String pais_banco_extranjero = "";
    //Variable que almacena la clave ABA_BIC del banco extranjero.
    String ABA_BIC = "";
    //Variable que almacena el nombre del fideicomisario registrado en banco extranjero.
    String nombre_empleado_banco_extranjero = "";
    //Variable que almacena el domicilio del fideicomisario registrado en banco extranjero.
    String dom_empleado_banco_extranjero = "";
    //Variable que almacena el pais en el que el fideicomisario tiene su domicilio en el extranjero.
    String pais_empleado_banco_extranjero = "";
    //Variable que almacena el teléfono del fideicomisario que se encuentra en el extranjero.
    String tel_empleado_banco_extranjero = "";

    public String getNombre_fideicomisario() {
        return nombre_fideicomisario;
    }

    public void setNombre_fideicomisario(String nombre_fideicomisario) {
        this.nombre_fideicomisario = nombre_fideicomisario;
    }

    public String getABA_BIC() {
        return ABA_BIC;
    }

    public void setABA_BIC(String ABA_BIC) {
        this.ABA_BIC = ABA_BIC;
    }

    public String getCURP() {
        return CURP;
    }

    public void setCURP(String CURP) {
        this.CURP = CURP;
    }

    public String getRFC() {
        return RFC;
    }

    public void setRFC(String RFC) {
        this.RFC = RFC;
    }

    public String getApellidoM_empleado() {
        return apellidoM_empleado;
    }

    public void setApellidoM_empleado(String apellidoM_empleado) {
        this.apellidoM_empleado = apellidoM_empleado;
    }

    public String getApellidoP_empleado() {
        return apellidoP_empleado;
    }

    public void setApellidoP_empleado(String apellidoP_empleado) {
        this.apellidoP_empleado = apellidoP_empleado;
    }

    public String getClave_banco() {
        return clave_banco;
    }

    public void setClave_banco(String clave_banco) {
        this.clave_banco = clave_banco;
    }

    public String getClave_contrato() {
        return clave_contrato;
    }

    public void setClave_contrato(String clave_contrato) {
        this.clave_contrato = clave_contrato;
    }

    public String getClave_moneda() {
        return clave_moneda;
    }

    public void setClave_moneda(String clave_moneda) {
        this.clave_moneda = clave_moneda;
    }

    public String getCorreo_destino_cheque() {
        return correo_destino_cheque;
    }

    public void setCorreo_destino_cheque(String correo_destino_cheque) {
        this.correo_destino_cheque = correo_destino_cheque;
    }

    public String getCuenta_deposito() {
        return cuenta_deposito;
    }

    public void setCuenta_deposito(String cuenta_deposito) {
        this.cuenta_deposito = cuenta_deposito;
    }

    public String getDepartamento_empleado() {
        return departamento_empleado;
    }

    public void setDepartamento_empleado(String departamento_empleado) {
        this.departamento_empleado = departamento_empleado;
    }

    public String getDom_empleado_banco_extranjero() {
        return dom_empleado_banco_extranjero;
    }

    public void setDom_empleado_banco_extranjero(String dom_empleado_banco_extranjero) {
        this.dom_empleado_banco_extranjero = dom_empleado_banco_extranjero;
    }

    public String getDomicilio_banco_extranjero() {
        return domicilio_banco_extranjero;
    }

    public void setDomicilio_banco_extranjero(String domicilio_banco_extranjero) {
        this.domicilio_banco_extranjero = domicilio_banco_extranjero;
    }

    public String getDomicilio_destino_cheque() {
        return domicilio_destino_cheque;
    }

    public void setDomicilio_destino_cheque(String domicilio_destino_cheque) {
        this.domicilio_destino_cheque = domicilio_destino_cheque;
    }

    public String getFecha_ingreso() {
        return fecha_ingreso;
    }

    public void setFecha_ingreso(String fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    public String getFecha_liquidacion() {
        return fecha_liquidacion;
    }

    public void setFecha_liquidacion(String fecha_liquidacion) {
        this.fecha_liquidacion = fecha_liquidacion;
    }

    public String getImporte_liquidacion() {
        return importe_liquidacion;
    }

    public void setImporte_liquidacion(String importe_liquidacion) {
        this.importe_liquidacion = importe_liquidacion;
    }

    public String getNombre_banco_extranjero() {
        return nombre_banco_extranjero;
    }

    public void setNombre_banco_extranjero(String nombre_banco_extranjero) {
        this.nombre_banco_extranjero = nombre_banco_extranjero;
    }

    public String getNombre_empleado() {
        return nombre_empleado;
    }

    public void setNombre_empleado(String nombre_empleado) {
        this.nombre_empleado = nombre_empleado;
    }

    public String getNombre_empleado_banco_extranjero() {
        return nombre_empleado_banco_extranjero;
    }

    public void setNombre_empleado_banco_extranjero(String nombre_empleado_banco_extranjero) {
        this.nombre_empleado_banco_extranjero = nombre_empleado_banco_extranjero;
    }

    public String getNombre_receptor_cheque() {
        return nombre_receptor_cheque;
    }

    public void setNombre_receptor_cheque(String nombre_receptor_cheque) {
        this.nombre_receptor_cheque = nombre_receptor_cheque;
    }

    public String getNumero_empleado() {
        return numero_empleado;
    }

    public void setNumero_empleado(String numero_empleado) {
        this.numero_empleado = numero_empleado;
    }

    public String getPais_banco_extranjero() {
        return pais_banco_extranjero;
    }

    public void setPais_banco_extranjero(String pais_banco_extranjero) {
        this.pais_banco_extranjero = pais_banco_extranjero;
    }

    public String getPais_empleado_banco_extranjero() {
        return pais_empleado_banco_extranjero;
    }

    public void setPais_empleado_banco_extranjero(String pais_empleado_banco_extranjero) {
        this.pais_empleado_banco_extranjero = pais_empleado_banco_extranjero;
    }

    public String getPuesto_empleado() {
        return puesto_empleado;
    }

    public void setPuesto_empleado(String puesto_empleado) {
        this.puesto_empleado = puesto_empleado;
    }

    public String getTel_destino_cheque() {
        return tel_destino_cheque;
    }

    public void setTel_destino_cheque(String tel_destino_cheque) {
        this.tel_destino_cheque = tel_destino_cheque;
    }

    public String getTel_empleado_banco_extranjero() {
        return tel_empleado_banco_extranjero;
    }

    public void setTel_empleado_banco_extranjero(String tel_empleado_banco_extranjero) {
        this.tel_empleado_banco_extranjero = tel_empleado_banco_extranjero;
    }

    public String getTipo_movimiento() {
        return tipo_movimiento;
    }

    public void setTipo_movimiento(String tipo_movimiento) {
        this.tipo_movimiento = tipo_movimiento;
    }
}
