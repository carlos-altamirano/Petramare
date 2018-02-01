/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.org.gp.nomina12.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luis-valerio
 */
public class InfoEmpleadoMes {

    public String claveContrato;
    public String rfc;
    public Double ImporteMes;
    public String curp;
    public String claveEmpleado;
    public String departamento;
    public String puesto;
    public String cuentaCLABE;
    public String nombre;
    public String apellidoPaterno;
    public String apellidoMaterno;
    public String tipoMovimiento;
    public EmpresaCliente empresa;
    public List<Movimiento> movimientos;

    public InfoEmpleadoMes() {
        movimientos = new ArrayList<>();
    }

    public List<Movimiento> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<Movimiento> movimientos) {
        this.movimientos = movimientos;
    }

    public EmpresaCliente getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaCliente empresa) {
        this.empresa = empresa;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCuentaCLABE() {
        return cuentaCLABE;
    }

    public void setCuentaCLABE(String cuentaCLABE) {
        this.cuentaCLABE = cuentaCLABE;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getClaveEmpleado() {
        return claveEmpleado;
    }

    public void setClaveEmpleado(String claveEmpleado) {
        this.claveEmpleado = claveEmpleado;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public Double getImporteMes() {
        return ImporteMes;
    }

    public void setImporteMes(Double ImporteMes) {
        this.ImporteMes = ImporteMes;
    }

    public String getClaveContrato() {
        return claveContrato;
    }

    public void setClaveContrato(String claveContrato) {
        this.claveContrato = claveContrato;
    }

    @Override
    public String toString() {
        return "InfoEmpleadoMes{" + "claveContrato=" + claveContrato + ", rfc=" + rfc + ", ImporteMes=" + ImporteMes + ", curp=" + curp + ", claveEmpleado=" + claveEmpleado + ", departamento=" + departamento + ", puesto=" + puesto + ", cuentaCLABE=" + cuentaCLABE + ", nombre=" + nombre + ", apellidoPaterno=" + apellidoPaterno + ", apellidoMaterno=" + apellidoMaterno + ", empresa=" + empresa + ", movimientos=" + movimientos + '}';
    }

}
