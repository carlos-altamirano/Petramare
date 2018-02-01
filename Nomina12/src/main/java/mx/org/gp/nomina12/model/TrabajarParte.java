/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.org.gp.nomina12.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import mx.sat.cfdi.CEstado;
import mx.sat.cfdi.CTipoNomina;
import mx.sat.cfdi.Comprobante;
import mx.sat.cfdi.Nomina;

/**
 *
 * @author luis-valerio
 */
public class TrabajarParte implements Runnable {

    List<MovsMesResum> movimientosMensuales;
    List<String> movimientosMensuales2;
    final Integer MOVS_MAX_PROCESA;
    Integer hilos = 0;
    final Integer SIZE_MOVS;
    Date fechaAnaliza;
    List<String> errores = new ArrayList<>();

    public TrabajarParte(List<MovsMesResum> movimientosMensuales, List<String> rfc_errores, Integer MOVS_MAX_PROCESA, Date fechaAnaliza) {
        this.movimientosMensuales = movimientosMensuales;
        this.movimientosMensuales2 = rfc_errores;
        SIZE_MOVS = movimientosMensuales.size();
        Double t = Math.ceil((Double) SIZE_MOVS.doubleValue() / MOVS_MAX_PROCESA.doubleValue());
        if (MOVS_MAX_PROCESA >= SIZE_MOVS) {
            t = 1d;
            this.MOVS_MAX_PROCESA = SIZE_MOVS;
        } else {
            this.MOVS_MAX_PROCESA = MOVS_MAX_PROCESA;
        }
        hilos = t.intValue();
        this.fechaAnaliza = fechaAnaliza;
    }

    @Override
    public void run() {
        try {
            ObtenInfoBD obtenInforBD = new ObtenInfoBD();
            Calendar c = Calendar.getInstance();
            Date ultimoDiaMes;
            Date primerDiaMes;
            Integer diasDelMes;
            //creamos instancia de gregorianClendar para inicializar un xmlCalendar
            GregorianCalendar gcUltimoDiaMes = new GregorianCalendar();
            c.setTime(fechaAnaliza);
            //seteamos a la instancia de Calendar la fecha del ultimo dia de mes
            c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
            ultimoDiaMes = c.getTime();
            //seteamos al Calendar el primer dia del mes
            c.set(Calendar.DAY_OF_MONTH, 1);
            primerDiaMes = c.getTime();
            diasDelMes = c.getActualMaximum(Calendar.DAY_OF_MONTH);
            gcUltimoDiaMes.setTime(ultimoDiaMes);

            XMLGregorianCalendar xmlCalUltimoDiaMes = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcUltimoDiaMes);

            Integer id_hilo = new Integer(Thread.currentThread().getName());
            System.out.println("#HILO=" + id_hilo);
                //GARANTE
            String rfcEmisor = "GDS160406V45";
            String nomRazonSocialEmisor = "Garante Desarrollo y Salud, S.A. de C.V. SOFOM E.N.R.";
            String cpEmisor = "06500";
            String regimenFiscal = "601";

            //COMPROBANTE
            Comprobante comprobante = new Comprobante();
            comprobante.setFecha(xmlCalUltimoDiaMes);
            comprobante.setLugarExpedicion(cpEmisor);
            comprobante.setTipoDeComprobante("N");
            comprobante.setMetodoDePago("NA");
            comprobante.setMoneda("MXN");
            comprobante.setFormaDePago("99");

            //EMISOR
            Comprobante.Emisor emisorComp = new Comprobante.Emisor();
            emisorComp.setRfc(rfcEmisor);
            emisorComp.setNombre(nomRazonSocialEmisor);
            Comprobante.Emisor.RegimenFiscal regimen = new Comprobante.Emisor.RegimenFiscal();
            regimen.setRegimen(regimenFiscal);
            emisorComp.getRegimenFiscal().add(regimen);

            //COMPLEMENTO NOMINA
            Nomina n = new Nomina();
            n.setTipoNomina(CTipoNomina.E);
            n.setFechaPago(xmlCalUltimoDiaMes);
            n.setFechaFinalPago(xmlCalUltimoDiaMes);
            gcUltimoDiaMes.setTime(primerDiaMes);
            xmlCalUltimoDiaMes = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcUltimoDiaMes);
            n.setFechaInicialPago(xmlCalUltimoDiaMes);
            n.setNumDiasPagados(new BigDecimal(diasDelMes));
            //n.setPercepciones(null);
            //n.setDeducciones(null);

            //NOMINA RECEPTOR
            Nomina.Receptor nomina_receptor = new Nomina.Receptor();
            nomina_receptor.setTipoContrato("99");
            nomina_receptor.setTipoRegimen("05");
            nomina_receptor.setPeriodicidadPago("99");
            //nomina_receptor.setSindicalizado(null);
            //nomina_receptor.setTipoJornada(null);    

            for (int i = 0; i < MOVS_MAX_PROCESA; i++) {
                Integer idx = ((id_hilo - 1) * MOVS_MAX_PROCESA) + i;
                if (idx >= SIZE_MOVS) {//Si el indice está fuera de rango entonces se deja de iterar
                    break;
                }

                MovsMesResum mov = movimientosMensuales.get(idx);
                //SE OBTIENE LA INFORMACION REQUERIDA DEL EMPLEADO Y EMPRESA
//                 * totalOtrosPagos = new BigDecimal(mov.getImporte());
                InfoEmpleadoMes infoEmpleado = obtenInforBD.obtenInfoEmpYconceptos(mov.getRfc(), mov.getContrato(), primerDiaMes, ultimoDiaMes);
//                EmpresaCliente empresa = infoEmpleado.getEmpresa();
//                rfcCliente = infoEmpleado.getEmpresa().getRfc();
//                entFedCliente = infoEmpleado.getEmpresa().getEntFed();
//                claveContrato = infoEmpleado.getEmpresa().getClaveContrato();
                String nombreTrabajador = infoEmpleado.getNombre() + " " + infoEmpleado.getApellidoPaterno() + " " + infoEmpleado.getApellidoMaterno();
//                curpTrabajador = infoEmpleado.getCurp();
//                rfcTrabajador = mov.getRfc();
//                numEmp = infoEmpleado.getClaveEmpleado();
//                departamentoEmp = infoEmpleado.getDepartamento();
//                puestoEmp = infoEmpleado.getPuesto();
//                cuentaCLABE = infoEmpleado.getCuentaCLABE();

                //CONCEPTOS
                Comprobante.Conceptos conceptos = new Comprobante.Conceptos();
                Comprobante.Conceptos.Concepto concepto = new Comprobante.Conceptos.Concepto();
                concepto.setCantidad(new BigDecimal(1));
                concepto.setDescripcion("Pago de nómina");
                concepto.setUnidad("ACT");
//                *concepto.setValorUnitario(totalOtrosPagos);
//                *concepto.setImporte(totalOtrosPagos);
//                conceptos.getConcepto().add(concepto);

                //COMPROBANTE
                comprobante.setSerie(infoEmpleado.getEmpresa().getClaveContrato());
//                *comprobante.setSubTotal(totalOtrosPagos);
//                *comprobante.setTotal(totalOtrosPagos);
                //RECEPTOR
                Comprobante.Receptor receptorComp = new Comprobante.Receptor();
                if (movimientosMensuales2!= null) {
                    receptorComp.setRfc(movimientosMensuales2.get(i));
                } else {
                    receptorComp.setRfc(mov.getRfc());
                }
                receptorComp.setNombre(nombreTrabajador);
                //NOMINA
//                *n.setTotalOtrosPagos(totalOtrosPagos);
                //NOMINA EMISOR
                Nomina.Emisor nomina_emisor = new Nomina.Emisor();
//            nomina_emisor.setCurp(curpCliente);
                //nomina_emisor.setRegistroPatronal("");
                nomina_emisor.setRfcPatronOrigen(infoEmpleado.getEmpresa().getRfc());
                //nomina_emisor.setEntidadSNCF(null);
                //nomina_emisor.getEntidadSNCF().setOrigenRecurso(null);
                //nomina_emisor.getEntidadSNCF().setMontoRecursoPropio(BigDecimal.ZERO);
                //NOMINA RECEPTOR
                nomina_receptor.setCurp(infoEmpleado.getCurp());
                //nomina_receptor.setNumSeguridadSocial(null);
                //nomina_receptor.setFechaInicioRelLaboral(null);
                //nomina_receptor.setAntigüedad(null);
                nomina_receptor.setNumEmpleado(infoEmpleado.getClaveEmpleado());
                nomina_receptor.setDepartamento(infoEmpleado.getDepartamento());
                nomina_receptor.setPuesto(infoEmpleado.getPuesto());
                //nomina_receptor.setRiesgoPuesto(null);
                nomina_receptor.setBanco(infoEmpleado.getCuentaCLABE());
//                nomina_receptor.setCuentaBancaria(new BigInteger(obtenInforBD.obtenCuenta(cuentaCLABE)));
                //nomina_receptor.setSalarioBaseCotApor(null);
                nomina_receptor.setClaveEntFed(CEstado.valueOf(infoEmpleado.getEmpresa().getEntFed()));
//                nomina_receptor.setClaveEntFed(CEstado.MEX);/**/
                //OTROS PAGOS
                Nomina.OtrosPagos otrosPagos = new Nomina.OtrosPagos();
//                List<Map> movsPagos = obtenInforBD.obtenConceptos(mov.getRfc(), mov.getContrato(), primerDiaMes, ultimoDiaMes);
                List<Movimiento> movsPagos = infoEmpleado.getMovimientos();
                BigDecimal totalOtrosPagos = new BigDecimal(0);
                for (int j = (movsPagos.size() - 1); j >= 0; j--) {
                    Movimiento m = movsPagos.get(j);
                    //OTROS PAGOS                                
                    Nomina.OtrosPagos.OtroPago otP = new Nomina.OtrosPagos.OtroPago();
                    otP.setTipoOtroPago("999");
                    otP.setClave(obtenInforBD.obtenClaveOtroPago(m.getFecha_liquidacion(), m.getTipo_movimiento()));
                    otP.setConcepto("Indemnización por Enfermedades y-o Riesgos Laborales");
                    if (m.getImporte_liquidacion_mxp() == 0) {
                        otP.setImporte(new BigDecimal(m.getImporte_liquidacion()));
                        totalOtrosPagos = totalOtrosPagos.add(new BigDecimal(m.getImporte_liquidacion()));
                    } else {
                        otP.setImporte(new BigDecimal(m.getImporte_liquidacion_mxp()));
                        totalOtrosPagos = totalOtrosPagos.add(new BigDecimal(m.getImporte_liquidacion_mxp()));
                    }
                    //otP.setSubsidioAlEmpleo(null);
                    //otP.setCompensacionSaldosAFavor(null);
                    otrosPagos.getOtroPago().add(otP);
                }
                concepto.setValorUnitario(totalOtrosPagos);
                concepto.setImporte(totalOtrosPagos);
                conceptos.getConcepto().add(concepto);
                comprobante.setSubTotal(totalOtrosPagos);
                comprobante.setTotal(totalOtrosPagos);
                n.setTotalOtrosPagos(totalOtrosPagos);

                //AGREGAR DEPENDENCIAS
                comprobante.setEmisor(emisorComp);
                comprobante.setConceptos(conceptos);
                comprobante.setReceptor(receptorComp);
                n.setEmisor(nomina_emisor);
                n.setReceptor(nomina_receptor);
                n.setOtrosPagos(otrosPagos);
                Comprobante.Complemento comp = new Comprobante.Complemento();
                comp.getAny().add(n);
                comprobante.setComplemento(comp);

                //Para cada trabajador en un fideicomiso se escribira en el archivo LayOut y cerrara el archivo
                //este archivo tendra por nombre el mes al que hace referencia y fecha en formato:"COMP_NOMINA_YYYYMM"
                ModeloLayOut modelo = new ModeloLayOut();

                if (!modelo.escribeComprobanteHilo(comprobante, ultimoDiaMes, id_hilo)) {
                    System.out.println("Error generando archivo LayOut Nomina");
                }
//                System.out.println("H" + id_hilo + " " + i);
//                time_end = System.currentTimeMillis();
//                System.out.println("(Hilo:" + id_hilo + "," + idx + ") de " + movimientosMensuales.size() + " tiempo (" + (time_end - time_start) + " milisegundos) RFC:" + mov.getRfc());

            }
            System.out.println("Termina Hilo=" + id_hilo);
        } catch (DatatypeConfigurationException | NumberFormatException e) {
            synchronized (this) {
                errores.add("Error encontrado:");
            }
            System.out.println("Error:" + e.getMessage());
        }
    }

}
