/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.org.gp.nomina12.model;

import java.util.Date;

/**
 *
 * @author luis-valerio
 */
public class Movimiento{
    
    public String fecha_liquidacion;
    private Double importe_liquidacion;
    private Double importe_liquidacion_mxp;
    private String tipo_movimiento;

    public Double getImporte_liquidacion() {
        return importe_liquidacion;
    }

    public String getFecha_liquidacion() {
        return fecha_liquidacion;
    }

    public void setFecha_liquidacion(String fecha_liquidacion) {
        this.fecha_liquidacion = fecha_liquidacion;
    }

    public void setImporte_liquidacion(Double importe_liquidacion) {
        this.importe_liquidacion = importe_liquidacion;
    }

    public Double getImporte_liquidacion_mxp() {
        return importe_liquidacion_mxp;
    }

    public void setImporte_liquidacion_mxp(Double importe_liquidacion_mxp) {
        this.importe_liquidacion_mxp = importe_liquidacion_mxp;
    }

    public String getTipo_movimiento() {
        return tipo_movimiento;
    }

    public void setTipo_movimiento(String tipo_movimiento) {
        this.tipo_movimiento = tipo_movimiento;
    }

    
}
