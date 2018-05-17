package mx.garante.liquidaciones.Beans;

import java.math.BigDecimal;

public class ResumenMovimientos {

    public String clave_contrato = "";
    //Cantidad de dispersiones de Bancomer a Bancomer
    public int total_movs_tipo1 = 0;
    //Cantidad de dispersiones de Bancomer a Otros Bancos
    public int total_movs_tipo2 = 0;
    //Cantidad de dispersiones de Bancomer a Tarjetas de Débito Banamex
    public int total_movs_tipo3 = 0;
    //Cantidad de dispersiones de Emisión de Cheques (Bancomer)
    public int total_movs_tipo4 = 0;
    //Cantidad de dispersiones de Bancomer a Bancos Extranjeros
    public int total_movs_tipo5 = 0;
    //Cantidad de dispersiones de Bancomer a Bancos Extranjeros Pendientes
    public int total_movs_tipo5_pend = 0;
    //Cantidad total de transacciones a efectuar.
    public int total_movimientos = 0;
    // Almacena el importe del movimiento de tipo1
    public BigDecimal pago_mov_tipo1 = BigDecimal.ZERO;
    // Almacena el importe del movimiento de tipo2
    public BigDecimal pago_mov_tipo2 = BigDecimal.ZERO;
    // Almacena el importe del movimiento de tipo3
    public BigDecimal pago_mov_tipo3 = BigDecimal.ZERO;
    // Almacena el importe del movimiento de tipo4
    public BigDecimal pago_mov_tipo4 = BigDecimal.ZERO;
    // Almacena el importe del movimiento de tipo5
    public BigDecimal pago_mov_tipo5 = BigDecimal.ZERO;
    // Almacena el saldo actual del fideicomiso
    public String saldo_actual = "";
    // Almacena el texto que imprimirá en pantalla dependiendo del nuevo saldo(positivo o negativo
    public String txt_Nuevo_saldo = "";
    // Almacena el nuevo saldo 
    public String nuevo_saldo = "";    
    // Almacena el texto que imprimirá en pantalla dependiendo del nuevo saldo(positivo o negativo
    public String liquidaciones_pendientes = "";
    // Almacena el nuevo saldo 
    public String aportacion_minima_requerida = "";     
    //Importe Total en MXP
    public BigDecimal importeTotalMXP = BigDecimal.ZERO;
    //Administración Fiduciaria Integral
    public String AFI = "";
    //Impuesto al Valor Agragado
    public String IVA = "";
    //Almacena el importe de los honorarios fiduciarios
    public String honorarios_fidu = "";
    //Honorarios Fiduciarios
    public BigDecimal HF = BigDecimal.ZERO;
    //Suficiencia Patrimonial Requerida
    public BigDecimal SPR = BigDecimal.ZERO;
    //Descripción del movimiento tipo 1
    public String desc_pago_mov_tipo1 = " Pago a Terceros de Bancomer a Bancomer ";
    //Descripción del movimiento tipo 2
    public String desc_pago_mov_tipo2 = " Traspasos de Bancomer a Otros ";
    //Descripción del movimiento tipo 3
    public String desc_pago_mov_tipo3 = " Traspasos de Bancomer a TDD Banamex ";
    //Descripción del movimiento tipo 4
    public String desc_pago_mov_tipo4 = " Emisión de Cheques ";
    //Descripción del movimiento tipo 5
    public String desc_pago_mov_tipo5 = " Traspasos de Bancomer a Bancos Extranjeros ";
    //Descripción la suma del importe total en pesos
    public String des_importe_total = "Importe Total en MXP";
    //Almacena el importe con formato de tipo de movimiento 1
    public String formato_importe_tipo1 = "";
    //Almacena el importe con formato de tipo de movimiento 2
    public String formato_importe_tipo2 = "";
    //Almacena el importe con formato de tipo de movimiento 3
    public String formato_importe_tipo3 = "";
    //Almacena el importe con formato de tipo de movimiento 4
    public String formato_importe_tipo4 = "";
    //Almacena el importe con formato de tipo de movimiento 5
    public String formato_importe_tipo5 = "";
    //Almacena el importe con formato de tipo de movimiento 5 pendientes
    public String formato_importe_tipo5_pend = "";
    //Almacena el importe con formato del IVA
    public String formato_iva = "";
    //Almacena el importe con formato del AFI
    public String formato_AFI = "";
    //Almacena el importe total en MXP con formato
    public String formato_importe_totalMXP = "";
    //Almacena el formato del importe de los honorarios fiduciarios
    public String formato_honorarios_fidu = "";
    //Almacena el formato del importe de la Suficiencia Patrimonial Requerida
    public String formato_SPR = "";
    public String fecha_liquidacion = "";

    public void setClaveContrato(String clave_contrato) {
        this.clave_contrato = clave_contrato;
    }

    public String getClaveContrato() {
        return clave_contrato;
    }

    public void setTotal_movimientos(int total_movimientos) {
        this.total_movimientos = total_movimientos;
    }

    public int getTotal_movimientos() {
        return total_movimientos;
    }

    public int get_total_movs() {
        return total_movs_tipo1 + total_movs_tipo2 + total_movs_tipo3 + total_movs_tipo4 + total_movs_tipo5;
    }

    public int getTotal_movs_tipo1() {
        return total_movs_tipo1;
    }

    public int getTotal_movs_tipo2() {
        return total_movs_tipo2;
    }

    public int getTotal_movs_tipo3() {
        return total_movs_tipo3;
    }

    public int getTotal_movs_tipo4() {
        return total_movs_tipo4;
    }

    public int getTotal_movs_tipo5() {
        return total_movs_tipo5;
    }

    public int inc_mov_tipo1() {
        return total_movs_tipo1++;
    }

    public int inc_mov_tipo2() {
        return total_movs_tipo2++;
    }

    public int inc_mov_tipo3() {
        return total_movs_tipo3++;
    }

    public int inc_mov_tipo4() {
        return total_movs_tipo4++;
    }

    public int inc_mov_tipo5() {
        return total_movs_tipo5++;
    }

    public String getFecha_liquidacion() {
        return fecha_liquidacion;
    }

    public void setFecha_liquidacion(String fecha_liquidacion) {
        this.fecha_liquidacion = fecha_liquidacion;
    }

    public String getFormato_SPR() {
        return formato_SPR;
    }

    public void setFormato_SPR(String formato_SPR) {
        this.formato_SPR = formato_SPR;
    }

    public String getFormato_honorarios_fidu() {
        return formato_honorarios_fidu;
    }

    public void setFormato_honorarios_fidu(String formato_honorarios_fidu) {
        this.formato_honorarios_fidu = formato_honorarios_fidu;
    }

    public String getHonorarios_fidu() {
        return honorarios_fidu;
    }

    public void setHonorarios_fidu(String honorarios_fidu) {
        this.honorarios_fidu = honorarios_fidu;
    }

    public String getFormato_importe_totalMXP() {
        return formato_importe_totalMXP;
    }

    public void setFormato_importe_totalMXP(String formato_importe_totalMXP) {
        this.formato_importe_totalMXP = formato_importe_totalMXP;
    }

    public String getFormato_AFI() {
        return formato_AFI;
    }

    public void setFormato_AFI(String formato_AFI) {
        this.formato_AFI = formato_AFI;
    }

    public String getFormato_iva() {
        return formato_iva;
    }

    public void setFormato_iva(String formato_iva) {
        this.formato_iva = formato_iva;
    }

    public String getAFI() {
        return AFI;
    }

    public void setAFI(String AFI) {
        this.AFI = AFI;
    }

    public BigDecimal getHF() {
        return HF;
    }

    public void setHF(BigDecimal HF) {
        this.HF = HF;
    }

    public String getIVA() {
        return IVA;
    }

    public void setIVA(String IVA) {
        this.IVA = IVA;
    }

    public BigDecimal getSPR() {
        return SPR;
    }

    public void setSPR(BigDecimal SPR) {
        this.SPR = SPR;
    }

    public String getFormato_importe_tipo1() {
        return formato_importe_tipo1;
    }

    public String getFormato_importe_tipo2() {
        return formato_importe_tipo2;
    }

    public String getFormato_importe_tipo3() {
        return formato_importe_tipo3;
    }

    public String getFormato_importe_tipo4() {
        return formato_importe_tipo4;
    }

    public String getFormato_importe_tipo5() {
        return formato_importe_tipo5;
    }

    public void setFormato_importe_tipo1(String importe_tipo1) {
        this.formato_importe_tipo1 = importe_tipo1;
    }

    public void setFormato_importe_tipo2(String importe_tipo2) {
        this.formato_importe_tipo2 = importe_tipo2;
    }

    public void setFormato_importe_tipo3(String importe_tipo3) {
        this.formato_importe_tipo3 = importe_tipo3;
    }

    public void setFormato_importe_tipo4(String importe_tipo4) {
        this.formato_importe_tipo4 = importe_tipo4;
    }

    public void setFormato_importe_tipo5(String importe_tipo5) {
        this.formato_importe_tipo5 = importe_tipo5;
    }

    public String getDes_importe_total() {
        return des_importe_total;
    }

    public void setDes_importe_total(String des_importe_total) {
        this.des_importe_total = des_importe_total;
    }

    public String getDesc_pago_mov_tipo1() {
        return desc_pago_mov_tipo1;
    }

    public void setDesc_pago_mov_tipo1(String desc_pago_mov_tipo1) {
        this.desc_pago_mov_tipo1 = desc_pago_mov_tipo1;
    }

    public String getDesc_pago_mov_tipo2() {
        return desc_pago_mov_tipo2;
    }

    public void setDesc_pago_mov_tipo2(String desc_pago_mov_tipo2) {
        this.desc_pago_mov_tipo2 = desc_pago_mov_tipo2;
    }

    public String getDesc_pago_mov_tipo3() {
        return desc_pago_mov_tipo3;
    }

    public void setDesc_pago_mov_tipo3(String desc_pago_mov_tipo3) {
        this.desc_pago_mov_tipo3 = desc_pago_mov_tipo3;
    }

    public String getDesc_pago_mov_tipo4() {
        return desc_pago_mov_tipo4;
    }

    public void setDesc_pago_mov_tipo4(String desc_pago_mov_tipo4) {
        this.desc_pago_mov_tipo4 = desc_pago_mov_tipo4;
    }

    public String getSaldo_actual() {
        return saldo_actual;
    }

    public void setSaldo_actual(String saldo_actual) {
        this.saldo_actual = saldo_actual;
    }

    public String getTxt_Nuevo_saldo() {
        return txt_Nuevo_saldo;
    }

    public void setTxt_Nuevo_saldo(String txt_Nuevo_saldo) {
        this.txt_Nuevo_saldo = txt_Nuevo_saldo;
    }

    public String getLiquidaciones_pendientes() {
        return liquidaciones_pendientes;
    }

    public void setLiquidaciones_pendientes(String liquidaciones_pendientes) {
        this.liquidaciones_pendientes = liquidaciones_pendientes;
    }

    public String getAportacion_minima_requerida() {
        return aportacion_minima_requerida;
    }

    public void setAportacion_minima_requerida(String aportacion_minima_requerida) {
        this.aportacion_minima_requerida = aportacion_minima_requerida;
    }
    
    public String getNuevo_saldo() {
        return nuevo_saldo;
    }

    public void setNuevo_saldo(String nuevo_saldo) {
        this.nuevo_saldo = nuevo_saldo;
    }

    public String getDesc_pago_mov_tipo5() {
        return desc_pago_mov_tipo5;
    }

    public void setDesc_pago_mov_tipo5(String desc_pago_mov_tipo5) {
        this.desc_pago_mov_tipo5 = desc_pago_mov_tipo5;
    }

    public BigDecimal getPago_mov_tipo1() {
        return pago_mov_tipo1;
    }

    public void setPago_mov_tipo1(BigDecimal pago_mov_tipo1) {

        this.pago_mov_tipo1 = pago_mov_tipo1;

    }

    public BigDecimal getPago_mov_tipo2() {
        return pago_mov_tipo2;
    }

    public void setPago_mov_tipo2(BigDecimal pago_mov_tipo2) {
        this.pago_mov_tipo2 = pago_mov_tipo2;
    }

    public BigDecimal getPago_mov_tipo3() {
        return pago_mov_tipo3;
    }

    public void setPago_mov_tipo3(BigDecimal pago_mov_tipo3) {
        this.pago_mov_tipo3 = pago_mov_tipo3;
    }

    public BigDecimal getPago_mov_tipo4() {
        return pago_mov_tipo4;
    }

    public void setPago_mov_tipo4(BigDecimal pago_mov_tipo4) {
        this.pago_mov_tipo4 = pago_mov_tipo4;
    }

    public BigDecimal getPago_mov_tipo5() {
        return pago_mov_tipo5;
    }

    public void setPago_mov_tipo5(BigDecimal pago_mov_tipo5) {
        this.pago_mov_tipo5 = pago_mov_tipo5;
    }

    public void sumMov1(BigDecimal nuevoMov1) {
        this.pago_mov_tipo1 = this.pago_mov_tipo1.add(nuevoMov1);
    }

    public void sumMov2(BigDecimal nuevoMov2) {
        this.pago_mov_tipo2 = this.pago_mov_tipo2.add(nuevoMov2);
    }

    public void sumMov3(BigDecimal nuevoMov3) {
        this.pago_mov_tipo3 = this.pago_mov_tipo3.add(nuevoMov3);
    }

    public void sumMov4(BigDecimal nuevoMov4) {
        this.pago_mov_tipo4 = this.pago_mov_tipo4.add(nuevoMov4);
    }

    public void sumMov5(BigDecimal nuevoMov5) {
        this.pago_mov_tipo5 = this.pago_mov_tipo5.add(nuevoMov5);
    }

    public BigDecimal getSumImporteTotalMXP() {
        BigDecimal sumaTotal = BigDecimal.ZERO;
        sumaTotal = sumaTotal.add(this.pago_mov_tipo1);
        sumaTotal = sumaTotal.add(this.pago_mov_tipo2);
        sumaTotal = sumaTotal.add(this.pago_mov_tipo3);
        sumaTotal = sumaTotal.add(this.pago_mov_tipo4);
        return sumaTotal;
    }

    public BigDecimal getImporteTotalMXP() {
        return this.importeTotalMXP;
    }

    public void setImporteTotalMXP(BigDecimal importe) {
        this.importeTotalMXP = importe;
    }

    public int getTotal_movs_tipo5_pend() {
        return total_movs_tipo5_pend;
    }

    public void setTotal_movs_tipo5_pend(int total_movs_tipo5_pend) {
        this.total_movs_tipo5_pend = total_movs_tipo5_pend;
    }

    public String getFormato_importe_tipo5_pend() {
        return formato_importe_tipo5_pend;
    }

    public void setFormato_importe_tipo5_pend(String formato_importe_tipo5_pend) {
        this.formato_importe_tipo5_pend = formato_importe_tipo5_pend;
    }        
    
}
