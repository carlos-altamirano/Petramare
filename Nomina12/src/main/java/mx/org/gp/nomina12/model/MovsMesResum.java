/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.org.gp.nomina12.model;

/**
 *
 * @author luis-valerio
 */
public class MovsMesResum {
    //rfc,importe,contrato

    private String rfc;
    private String contrato;
//    private Double importe;
//    private Double importe_mxp;

    public String getContrato() {
        return contrato;
    }

    public void setContrato(String contrato) {
        this.contrato = contrato;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

//    public Double getImporte() {
//        return importe;
//    }
//
//    public void setImporte(Double importe) {
//        this.importe = importe;
//    }
//
//    public Double getImporte_mxp() {
//        return importe_mxp;
//    }
//
//    public void setImporte_mxp(Double importe_mxp) {
//        this.importe_mxp = importe_mxp;
//    }


    @Override
    public String toString() {
//        return "MovsMesResum{" + "rfc=" + rfc + ", contrato=" + contrato + ", importe=" + importe + '}';
        return "MovsMesResum{" + "rfc=" + rfc + ", contrato=" + contrato + '}';
    }
    

}
