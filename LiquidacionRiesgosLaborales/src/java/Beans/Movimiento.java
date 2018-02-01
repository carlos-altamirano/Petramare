package Beans;

public class Movimiento {

    //Variable que almacena la clave de fideicomiso a la que pertenece este movimiento.
    String clave_contrato = "";
    //Variable que almacena la fecha en la que tendrá lugar la liquidación de este movimiento.
    String fecha_liquidacion = "";
    //Variable que almacena el nombre del lote al que corresponde este movimiento.
    String nombre_archivo = "";
    //Variable que almacena el tipo de movimiento al que pertenece este movimiento.
    String tipo_movimiento = "";
    //Variable que almacena la clave de banco de la cuenta del fideicomisario de este movimiento.
    String clave_banco = "";
    //Variable que almacena la clave de moneda del importe de liquidación de este movimiento.
    String clave_moneda = "";
    //Variable que almacena los nombres del fideicomisario de este movimiento.
    String nombre_empleado = "";
    //Variable que almacena el apellido paterno del fideicomisario de este movimiento.
    String apellidoP_empleado = "";
    //Variable que almacena  el apellido materno del fideicomisario de este movimiento.
    String apellidoM_empleado = "";
    //Variable que almacena el número de cuenta del fideicomisario de este movimiento.
    String cuenta_deposito = "";
    //Variable que almacena el importe de liquidación de este movimiento.
    String importe_liquidacion = "";
    //Variable que almacena el importe de liquidación de este movimiento en moneda Nacional
    String importe_liquidacion_mxp = "";
    //Variable que almacena la clave o número de control de nómina del fideicomisario de este movimiento.
    String numero_empleado = "";
    //Variable que almacena la CURP del fideicomisario de este movimiento.
    String CURP = "";
    //Variable que almacena la fecha de ingreso del fideicomisario al empleo de este movimiento.
    String fecha_ingreso = "";
    //Variable que almacena el puesto del fideicomisario de este movimiento.
    String puesto_empleado = "";
    //Variable que almacena el departamento del fideicomisario de este movimiento.
    String departamento_empleado = "";
    //Variable que almacena el nombre de la persona a quién se enviará el cheque de este movimiento.
    String nombre_receptor_cheque = "";
    //Variable que almacena el domicilio de la persona a quién se enviará el cheque de este movimiento.
    String domicilio_destino_cheque = "";
    //Variable que almacena el teléfono de la persona a quién se enviará el cheque de este movimiento.
    String tel_destino_cheque = "";
    //Variable que almacena el correo de la persona a quién se enviará el cheque de este movimiento.
    String correo_destino_cheque = "";
    //Variable que almacena el nombre del banco que posee la cuenta del fideicomisario de este movimiento.
    String nombre_banco_extranjero = "";
    //Variable que almacena el domicilio del banco que posee la cuenta del fideicomisario de este movimiento.
    String domicilio_banco_extranjero = "";
    //Variable que almacena el país del banco que posee la cuenta del fideicomisario de este movimiento.
    String pais_banco_extranjero = "";
    //Variable que almacena la clave ABA_BIC del banco extranjero de este movimiento.
    String ABA_BIC = "";
    //Variable que almacena el nombre del fideicomisario registrado en banco extranjero de este movimiento.
    String nombre_empleado_banco_extranjero = "";
    //Variable que almacena el domicilio del fideicomisario registrado en banco extranjero de este movimiento.
    String dom_empleado_banco_extranjero = "";
    //Variable que almacena el pais en el que el fideicomisario tiene su domicilio en el extranjero de este movimiento.
    String pais_empleado_banco_extranjero = "";
    //Variable que almacena el teléfono del fideicomisario que se encuentra en el extranjero de este movimiento.
    String tel_empleado_banco_extranjero = "";
    //Variable que almacena el motivo de cancelación de este movimiento.
    String motivo_cancelacion = "";
    //Variable que almacena el nombre completo del fideicomisario de este movimiento.
    String nombre_fideicomisario = "";
//Variable que verifica si es un movimiento vacio.
    boolean isEmpty = true;

    /**
     * Establece la clave de fideicomiso actual del movimiento.
     * @param clave_contrato : Nueva clave de fideicomiso.
     */
    public void setClave_contrato(String clave_contrato) {
        this.clave_contrato = clave_contrato;
    }

    /**
     * Regresa la clave de fideicomiso al que pertenece el movimiento.
     * @return clave de fideicomiso.
     */
    public String getClave_contrato() {
        return clave_contrato;
    }

    /**
     * Establece la fecha en la que tendrá lugar la liquidación del movimiento.
     * @param fecha_liquidacion: Fecha de liquidación.
     */
    public void setFecha_liquidacion(String fecha_liquidacion) {
        this.fecha_liquidacion = fecha_liquidacion;
    }

    /**
     * Regresa la fecha en la que tendrá lugar la liquidación del movimiento.
     * @return fecha de liquidación.
     */
    public String getFecha_liquidacion() {
        return fecha_liquidacion;
    }

    /**
     * Establece el nombre del lote al que pertenece el movimiento.
     * @param nombre_archivo: Nombre del lote.
     */
    public void setNombre_archivo(String nombre_archivo) {
        this.nombre_archivo = nombre_archivo;
    }

    /**
     * Regresa el nombre del lote al que pertenece el movimiento.
     * @return nombre del lote.
     */
    public String getNombre_archivo() {
        return nombre_archivo;
    }

    /**
     *  Establece el tipo de movimiento al que pertenece el movimiento.
     * @param tipo_movimiento : Tipo de movimiento ( 1,2,3,4 y 5)
     */
    public void setTipo_movimiento(String tipo_movimiento) {
        this.tipo_movimiento = tipo_movimiento;
    }

    /**
     * Regresa el tipo de movimiento al que pertenece el movimiento.
     * @return tipo de movimiento.
     */
    public String getTipo_movimiento() {
        return tipo_movimiento;
    }

    /**
     * Establece la clave de banco de la cuenta del fideicomisario de este movimiento.
     * @param clave_banco: Clave de banco
     */
    public void setClave_banco(String clave_banco) {
        this.clave_banco = clave_banco;
    }

    /**
     * Regresa la clave de banco de la cuenta del fideicomisario de este movimiento.
     * @return clave de banco.
     */
    public String getClave_banco() {
        return clave_banco;
    }

    /**
     * Establece el tipo de moneda del importe de liquidación de este movimiento.
     * @param clave_moneda: Clave de moneda.
     */
    public void setClave_moneda(String clave_moneda) {
        this.clave_moneda = clave_moneda;
    }

    /**
     * Regresa el tipo de moneda del importe de liquidación de este movimiento.
     * @return tipo de moneda
     */
    public String getClave_moneda() {
        return clave_moneda;
    }

    /**
     *Establece los nombres del fideicomisario de este movimiento.
     * @param nombre_empleado: Nombre(s) del fideicomisario.
     */
    public void setNombre_empleado(String nombre_empleado) {
        this.nombre_empleado = nombre_empleado;
    }

    /**
     * Regresa los nombres del fideicomisario de este movimiento.
     * @return nombre(s) del fideicomisario.
     */
    public String getNombre_empleado() {
        return nombre_empleado;
    }

    /**
     *Establece el apellido paterno del fideicomisario para este movimiento.
     * @param apellidoP_empleado : Apellido Paterno
     */
    public void setApellidoP_empleado(String apellidoP_empleado) {
        this.apellidoP_empleado = apellidoP_empleado;
    }

    /**
     *Regresar el apellido paterno del fideicomisario para este movimiento.
     * @return Apellido Paterno
     */
    public String getApellidoP_empleado() {
        return apellidoP_empleado;
    }

    /**
     *Establece el apellido materno del fideicomisario para este movimiento.
     * @param apellidoM_empleado : Apellido Materno.
     */
    public void setApellidoM_empleado(String apellidoM_empleado) {
        this.apellidoM_empleado = apellidoM_empleado;
    }

    /**
     *Regresa el apellido materno del fideicomisario para este movimiento.
     * @return Apellido Materno
     */
    public String getApellidoM_empleado() {
        return apellidoM_empleado;
    }

    /**
     * Establece el número de cuenta del fideicomisario de este movimiento.
     * @param cuenta_deposito : Número de cuenta de depósito.
     */
    public void setCuenta_deposito(String cuenta_deposito) {
        this.cuenta_deposito = cuenta_deposito;
    }

    /**
     * Regresa el número de cuenta del fideicomisario de este movimiento.
     * @return número de cuenta de depósito.
     */
    public String getCuenta_deposito() {
        return cuenta_deposito;
    }

    /**
     * Establece el importe de liquidación de este movimiento.
     * @param importe_liquidacion : Importe de liquidación.
     */
    public void setImporte_liquidacion(String importe_liquidacion) {
        this.importe_liquidacion = importe_liquidacion;
    }

    /**
     * Regresa el importe de liquidación de este movimiento.
     * @return importe de liquidación.
     */
    public String getImporte_liquidacion() {
        return importe_liquidacion;
    }

    public String getImporte_liquidacion_mxp() {
        return importe_liquidacion_mxp;
    }

    public void setImporte_liquidacion_mxp(String importe_liquidacion_mxp) {
        this.importe_liquidacion_mxp = importe_liquidacion_mxp;
    }    
    
    /**
     * Establece la clave o número de control de nómina del fideicomisario de este movimiento.
     * @param numero_empleado : Clave ó número de control de nómina
     */
    public void setNumero_empleado(String numero_empleado) {
        this.numero_empleado = numero_empleado;
    }

    /**
     * Regresa la clave o número de control de nómina del fideicomisario de este movimiento.
     * @return clave ó número de control de nómina.
     */
    public String getNumero_empleado() {
        return numero_empleado;
    }

    /**
     * Establece la CURP del fideicomisario de este movimiento.
     * @param CURP: CURP del fideicomisario.
     */
    public void setCURP(String CURP) {
        this.CURP = CURP;
    }

    /**
     * Regresa la CURP del fideicomisario de este movimiento.
     * @return CURP del fideicomisario.
     */
    public String getCURP() {
        return CURP;
    }

    /**
     * Establece la fecha de ingreso del fideicomisario al empleo de este movimiento.
     * @param fecha_ingreso: Fecha ingreso.
     */
    public void setFecha_ingreso(String fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    /**
     * Regresa la fecha de ingreso del fideicomisario al empleo de este movimiento.
     * @return fecha de ingreso.
     */
    public String getFecha_ingreso() {
        return fecha_ingreso;
    }

    /**
     * Establece el puesto del fideicomisario de este movimiento.
     * @param puesto_empleado: Puesto del fideicomisario.
     */
    public void setPuesto_empleado(String puesto_empleado) {
        this.puesto_empleado = puesto_empleado;
    }

    /**
     * Regresa el puesto del fideicomisario de este movimiento.
     * @return puesto del fideicomisario.
     */
    public String getPuesto_empleado() {
        return puesto_empleado;
    }

    /**
     * Establece el departamento al que pertenece el fideicomisario de este movimiento.
     * @param departamento_empleado : Departamento del fideicomisario.
     */
    public void setDepartamento_empleado(String departamento_empleado) {
        this.departamento_empleado = departamento_empleado;
    }

    /**
     * Regresa el departamento al que pertenece el fideicomisario de este movimiento.
     * @return departamento del fideicomisario.
     */
    public String getDepartamento_empleado() {
        return departamento_empleado;
    }

    /**
     * Establece el nombre de la persona a quién se enviará el cheque de este movimiento.
     * @param nombre_receptor_cheque : Persona a quién se le enviará el cheque.
     */
    public void setNombre_receptor_cheque(String nombre_receptor_cheque) {
        this.nombre_receptor_cheque = nombre_receptor_cheque;
    }

    /**
     * Regresa el nombre de la persona a quién se enviará el cheque de este movimiento.
     * @return Persona a quién se le enviará el cheque.
     */
    public String getNombre_receptor_cheque() {
        return nombre_receptor_cheque;
    }

    /**
     * Establece el domicilio de la persona a quién se enviará el cheque de este movimiento.
     * @param domicilio_destino_cheque : Domicilio de la persona a quién se enviará el cheque.
     */
    public void setDomicilio_destino_cheque(String domicilio_destino_cheque) {
        this.domicilio_destino_cheque = domicilio_destino_cheque;
    }

    /**
     * Regresa el domicilio de la persona a quién se enviará el cheque de este movimiento.
     * @return Domicilio de la persona a quién se enviará el cheque.
     */
    public String getDomicilio_destino_cheque() {
        return domicilio_destino_cheque;
    }

    /**
     * Establece el teléfono de la persona a quién se enviará el cheque de este movimiento.
     * @param tel_destino_cheque : Teléfono de la persona a la que se le enviará el cheque.
     */
    public void setTel_destino_cheque(String tel_destino_cheque) {
        this.tel_destino_cheque = tel_destino_cheque;
    }

    /**
     * Regresa el teléfono de la persona a quién se enviará el cheque de este movimiento.
     * @return Teléfono de la persona a la que se le enviará el cheque.
     */
    public String getTel_destino_cheque() {
        return tel_destino_cheque;
    }

    /**
     * Establece el correo de la persona a quién se enviará el cheque de este movimiento.
     * @param correo_destino_cheque : Correo de la persona a la que se le enviará el cheque.
     */
    public void setCorreo_destino_cheque(String correo_destino_cheque) {
        this.correo_destino_cheque = correo_destino_cheque;
    }

    /**
     * Regresa el correo de la persona a quién se enviará el cheque de este movimiento.
     * @return Correo de la persona a la que se le enviará el cheque.
     */
    public String getCorreo_destino_cheque() {
        return correo_destino_cheque;
    }

    /**
     * Establece el nombre del banco que posee la cuenta del fideicomisario de este movimiento.
     * @param nombre_banco_extranjero : Nombre del banco extranjero.
     */
    public void setNombre_banco_extranjero(String nombre_banco_extranjero) {
        this.nombre_banco_extranjero = nombre_banco_extranjero;
    }

    /**
     * Regresa el nombre del banco que posee la cuenta del fideicomisario de este movimiento.
     * @return nombre del banco extranjero.
     */
    public String getNombre_banco_extranjero() {
        return nombre_banco_extranjero;
    }

    /**
     * Establece el domicilio del "banco extranjero" de este movimiento.
     * @param domicilio_banco_extranjero : Domicilio del banco extranjero.
     */
    public void setDomicilio_banco_extranjero(String domicilio_banco_extranjero) {
        this.domicilio_banco_extranjero = domicilio_banco_extranjero;
    }

    /**
     * Regresa el domicilio del "banco extranjero" de este movimiento.
     * @return Domicilio del banco extranjero.
     */
    public String getDomicilio_banco_extranjero() {
        return domicilio_banco_extranjero;
    }

    /**
     * Establece el nombre del país en donde el "Banco extranjero" realizará el pago.
     * @param pais_banco_extranjero : Nombre del país.
     */
    public void setPais_banco_extranjero(String pais_banco_extranjero) {
        this.pais_banco_extranjero = pais_banco_extranjero;
    }

    /**
     * Regresa el nombre del país en donde el "Banco extranjero" realzará el pago.
     * @return Nombre del país.
     */
    public String getPais_banco_extranjero() {
        return pais_banco_extranjero;
    }

    /**
     * Establece la clave ABA_BIC del banco extranjero de este movimiento.
     * @param ABA_BIC :ABA_BIC
     */
    public void setABA_BIC(String ABA_BIC) {
        this.ABA_BIC = ABA_BIC;
    }

    /**
     * Regresa la clave ABA_BIC del banco extranjero de este movimiento.
     * @return ABA_BIC
     */
    public String getABA_BIC() {
        return ABA_BIC;
    }

    /**
     * Establece el nombre del fideicomisario (registrado en los terminos de aperturade la cuenta)
     * registrado en banco extranjero de este movimiento.
     * @param nombre_empleado_banco_extranjero :Nombre del fideicomisario en extranjero.
     */
    public void setNombre_empleado_banco_extranjero(String nombre_empleado_banco_extranjero) {
        this.nombre_empleado_banco_extranjero = nombre_empleado_banco_extranjero;
    }

    /**
     * Regresa el nombre del fideicomisario (registrado en los terminos de aperturade la cuenta)
     * registrado en banco extranjero de este movimiento.
     * @return Nombre del fideicomisario en extranjero.
     */
    public String getNombre_empleado_banco_extranjero() {
        return nombre_empleado_banco_extranjero;
    }

    /**
     * Establece el domicilio del fideicomisario en el extranjero para este movimiento.
     * @param dom_empleado_banco_extranjero : Domicilio de fidei en extranjero.
     */
    public void setDom_empleado_banco_extranjero(String dom_empleado_banco_extranjero) {
        this.dom_empleado_banco_extranjero = dom_empleado_banco_extranjero;
    }

    /**
     * Regresa el domicilio del fideicomisario en el extranjero para este movimiento.
     * @return Domicilio de fidei en extranjero.
     */
    public String getDom_empleado_banco_extranjero() {
        return dom_empleado_banco_extranjero;
    }

    /**
     * Establece el pais en el que el fideicomisario tiene su domicilio en el extranjero de este movimiento.
     * @param pais_empleado_banco_extranjero : Domicilio de fideicomisario en extranjero.
     */
    public void setPais_empleado_banco_extranjero(String pais_empleado_banco_extranjero) {
        this.pais_empleado_banco_extranjero = pais_empleado_banco_extranjero;
    }

    /**
     * Regresa el pais en el que el fideicomisario tiene su domicilio en el extranjero de este movimiento.
     * @return Domicilio de fideicomisario en extranjero.
     */
    public String getPais_empleado_banco_extranjero() {
        return pais_empleado_banco_extranjero;
    }

    /**
     * Establece el teléfono del fideicomisario que se encuentra en el extranjero de este movimiento.
     * @param tel_empleado_banco_extranjero :Teléfono de fideicomisario en extranjero.
     */
    public void setTel_empleado_banco_extranjero(String tel_empleado_banco_extranjero) {
        this.tel_empleado_banco_extranjero = tel_empleado_banco_extranjero;
    }

    /**
     * Regresa el teléfono del fideicomisario que se encuentra en el extranjero de este movimiento.
     * @return Teléfono de fideicomisario en extranjero.
     */
    public String getTel_empleado_banco_extranjero() {
        return tel_empleado_banco_extranjero;
    }

    /**
     * Establece el motivo de cancelación de este movimiento.
     * @param motivo_cancelacion : Motivo de cancelación
     */
    public void setMotivo_cancelacion(String motivo_cancelacion) {
        this.motivo_cancelacion = motivo_cancelacion;
    }

    /**
     * Regresa el motivo de cancelación de este movimiento.
     * @return  motivo de cancelación.
     */
    public String getMotivo_cancelacion() {
        return motivo_cancelacion;
    }

    /**
     * Establece el nombre completo del fideicomisario  de este movimiento.
     * @param nombre_fideicomisario : Nombre completo de fideicomisario.
     */
    public void setNombre_fideicomisario(String nombre_fideicomisario) {
        this.nombre_fideicomisario = nombre_fideicomisario;
    }

    /**
     * Regresa nombre completo del fideicomisario  de este movimiento.
     * @return Nombre completo de fideicomisario.
     */
    public String getNombre_fideicomisario() {
        return nombre_fideicomisario;
    }

    /**
     * Establece si el movimiento es vacio.
     * @param isEmpty: Estado del movimiento.
     */
    public void setIsEmpty(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }

    /**
     * Regresa si el movimiento es vacio.
     * @return estado del movimiento.
     */
    public boolean getIsEmpty() {
        return isEmpty;
    }
}
