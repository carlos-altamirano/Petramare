/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.garante.creaxml.Helpers;

import mx.garante.xmls.Comprobante;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Desarrollo
 */
public class CadenaOrigen {
    
    public static String cadenaOriginal(Comprobante comp){
        String cad ="||";
        
    //  Información del nodo Comprobante
        cad = cad + comp.getVersion();
        //cad = cad +"|"+ comp.getSerie() +"|"+ comp.getFolio();
        cad = cad +"|"+ comp.getFecha() +"|"+ comp.getFormaPago() +"|"+ comp.getNoCertificado();
        //cad = cad +"|"+ comp.getCondicionesDePago();
        cad = cad +"|"+ comp.getSubTotal().toString() +"|"+ new BigDecimal(0.00).toString() +"|"+ comp.getMoneda().toString();
        //cad = cad +"|"+ comp.getTipoCambio();
        cad = cad +"|"+ comp.getTotal().toString() +"|"+ comp.getTipoDeComprobante().toString() +"|"+ comp.getMetodoPago().toString() +"|"+ comp.getLugarExpedicion().toString();
        //cad = cad +"|"+ comp.getConfirmacion();

        // Informacion del nodo CFDIRelacionados
        // NADA DE INFORMACION AL PARECER
        
        //Información del nodo Emisor
        Comprobante.Emisor emisor = comp.getEmisor();
        cad = cad +"|"+ emisor.getRfc() +"|"+ emisor.getNombre() +"|"+ emisor.getRegimenFiscal().toString();
        
        // Información del nodo Receptor
        Comprobante.Receptor receptor = comp.getReceptor();//ok
        cad = cad +"|"+ receptor.getRfc() +"|"+ receptor.getNombre();//ok
        //cad = cad +"|"+ receptor.getResidenciaFiscal().toString() +"|"+ receptor.getNumRegIdTrib();
        cad = cad +"|"+ receptor.getUsoCFDI().toString();
                
        String cad2 = "";
        List<Comprobante.Conceptos.Concepto> lConceptos = comp.getConceptos().getConcepto();
        for (int i = 0; i< lConceptos.size(); i++){
            Comprobante.Conceptos.Concepto conc = lConceptos.get(i);
            cad2 = cad2 +"|"+ conc.getClaveProdServ().toString();//ok
            //cad2 = cad2 +"|"+ conc.getNoIdentificacion().toString();
            cad2 = cad2 +"|"+ conc.getCantidad().toString() +"|"+ conc.getClaveUnidad().toString();
            //cad2 = cad2 +"|"+ conc.getUnidad().toString();
            cad2 = cad2 +"|"+ conc.getDescripcion().toString() +"|"+ conc.getValorUnitario().toString() +"|"+ conc.getImporte().toString();
            //cad2 = cad2 +"|"+ conc.getDescuento().toString();
            
            Comprobante.Conceptos.Concepto.Impuestos impuestos = conc.getImpuestos();
            if(impuestos != null){
                Comprobante.Conceptos.Concepto.Impuestos.Traslados trasados = impuestos.getTraslados();
                if(trasados != null){
                    List<Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado> lTras = trasados.getTraslado();
                    if(lTras != null){
                        for(int j = 0; j < lTras.size(); j++){
                            Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado tras = lTras.get(j);
                            cad2 = cad2 +"|"+ tras.getBase().toString();
                            cad2 = cad2 +"|"+ tras.getImporte().toString();
                            cad2 = cad2 +"|"+ tras.getTipoFactor().toString();
                            cad2 = cad2 +"|"+ tras.getTasaOCuota().toString();
                        }
                    }
                }
                
                cad2 = cad2 +"|"+ conc.getImporte().toString();
                
                Comprobante.Conceptos.Concepto.Impuestos.Retenciones retenciones = impuestos.getRetenciones();
                if(retenciones != null){
                    List<Comprobante.Conceptos.Concepto.Impuestos.Retenciones.Retencion> lRet = retenciones.getRetencion();
                    if(lRet != null){
                        for(int j = 0; j <= lRet.size(); j++){
                            Comprobante.Conceptos.Concepto.Impuestos.Retenciones.Retencion ret = lRet.get(j);
                            cad2 = cad2 +"|"+ ret.getBase().toString();
                            cad2 = cad2 +"|"+ ret.getImporte().toString();
                            cad2 = cad2 +"|"+ ret.getTipoFactor().toString();
                            cad2 = cad2 +"|"+ ret.getTasaOCuota().toString();
                            cad2 = cad2 +"|"+ ret.getImporte().toString();
                        }
                    }
                }
            }
        }cad = cad + cad2;
        
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////
        
        Comprobante.Impuestos compImpuestos = comp.getImpuestos();
            if(compImpuestos != null){
                Comprobante.Impuestos.Retenciones compRet = compImpuestos.getRetenciones();
                if(compRet != null){
                    List<Comprobante.Impuestos.Retenciones.Retencion> lCompRet = compRet.getRetencion();
                    if(lCompRet != null){
                        for(int k = 0; k < lCompRet.size(); k++){
                            Comprobante.Impuestos.Retenciones.Retencion lTras = lCompRet.get(k);
                            cad = cad +"|"+ lTras.getImpuesto().toString();
                            cad = cad +"|"+ lTras.getImporte().toString();
                        }
                    }
                    cad = cad + "|" + compImpuestos.getTotalImpuestosRetenidos().toString();
                }
                Comprobante.Impuestos.Traslados compTras = compImpuestos.getTraslados();
                if(compTras != null){
                    List<Comprobante.Impuestos.Traslados.Traslado> lCompTras = compTras.getTraslado();
                    if(lCompTras != null){
                        for(int k = 0; k < lCompTras.size(); k++){
                            Comprobante.Impuestos.Traslados.Traslado lTras = lCompTras.get(k);
                            cad = cad +"|"+ lTras.getImpuesto().toString();
                            cad = cad +"|"+ lTras.getTipoFactor().toString();
                            cad = cad +"|"+ lTras.getTasaOCuota().toString();
                            cad = cad +"|"+ lTras.getImporte().toString();
                        }
                    }
                    cad = cad + "|" + compImpuestos.getTotalImpuestosTrasladados().toString();
                }                
            }
            
        List<Comprobante.Complemento> complemento = comp.getComplemento();
        if(complemento != null){
            for(int x = 0; x < complemento.size(); x++){
                Comprobante.Complemento elemComplemento = complemento.get(x);
                List<Object> elemComList = elemComplemento.getAny();
                for(Object obj : elemComList){
                    cad = cad +"|"+ obj.toString();
                }
            }
        }
        
        return cad+"||";
    }
    
}
