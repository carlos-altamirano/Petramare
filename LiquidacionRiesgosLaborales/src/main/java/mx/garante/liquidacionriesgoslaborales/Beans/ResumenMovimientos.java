package mx.garante.liquidacionriesgoslaborales.Beans;

import java.math.BigDecimal;

public class ResumenMovimientos {

    //Descripción de las dispersiones de Bancomer a Bancomer.
    public String desc_pago_mov_tipo1 = " Pago a Terceros de Bancomer a Bancomer ";
    //Cantidad total de dispersiones de Bancomer a Bancomer.
    public int total_movs_tipo1 = 0;
    // Almacena el importe total en MXP de Bancomer a Bancomer.
    public BigDecimal pago_mov_tipo1 = BigDecimal.ZERO;
    //Almacena el importe con formato de Bancomer a Bancomer.
    public String formato_importe_tipo1 = "";

    //Descripción de las dispersiones de Bancomer a Otros Bancos.
    public String desc_pago_mov_tipo2 = " Traspasos de Bancomer a Otros ";
    //Cantidad total de dispersiones de Bancomer a Otros Bancos.
    public int total_movs_tipo2 = 0;
    // Almacena el importe total en MXP de Bancomer a Otros Bancos.
    public BigDecimal pago_mov_tipo2 = BigDecimal.ZERO;
    //Almacena el importe con formato de tipo de movimiento 2
    public String formato_importe_tipo2 = "";

    //Descripción de las dispersiones que son traspasos de Bancomer a TDD Banamex.
    public String desc_pago_mov_tipo3 = " Traspasos de Bancomer a TDD Banamex ";
    //Cantidad total de dispersiones que son traspasos de Bancomer a TDD Banamex.
    public int total_movs_tipo3 = 0;
    // Almacena el importe total en MXP de los traspasos de Bancomer a TDD Banamex.
    public BigDecimal pago_mov_tipo3 = BigDecimal.ZERO;
    //Almacena el importe con formato de tipo de movimiento 3
    public String formato_importe_tipo3 = "";

    //Descripción de las dispersiones que son emisión de cheques.
    public String desc_pago_mov_tipo4 = " Emisión de Cheques ";
    //Cantidad total de dispersiones que son emisión de cheques (Bancomer).
    public int total_movs_tipo4 = 0;
    // Almacena el importe total en MXP de la emisión de cheques.
    public BigDecimal pago_mov_tipo4 = BigDecimal.ZERO;
    //Almacena el importe con formato de tipo de movimiento 4
    public String formato_importe_tipo4 = "";

    //Descripción de las dispersiones que son traspasos a bancos extranjeros.
    public String desc_pago_mov_tipo5 = " Traspasos de Bancomer a Bancos Extranjeros ";
    //Cantidad total de dispersiones de los traspasos a bancos extranjeros.
    public int total_movs_tipo5 = 0;
    // Almacena el importe total en MXP de los traspasos a bancos extranjeros.
    public BigDecimal pago_mov_tipo5 = BigDecimal.ZERO;
    //Almacena el importe con formato de tipo de movimiento 5
    public String formato_importe_tipo5 = "";

    //Descripción la suma total del importe de liquidación en MXP.
    public String des_importe_total = "Importe Total en MXP";
    //Importe Total en MXP.
    //Cantidad total de dispersiones en MXP.
    public int total_movimientos = 0;
    public BigDecimal importeTotalMXP = BigDecimal.ZERO;
    //Almacena el importe total en MXP con formato
    public String formato_importe_totalMXP = "";

    //Descripción de la AFI.
    public String des_AFI = "Administración Fiduciaria Integral";
    //Administración Fiduciaria Integral
    public String AFI = "";
    //Almacena el importe con formato del AFI
    public String formato_AFI = "";
    //Descripción del IVA.
    public String des_IVA = "Impuesto al Valor Agregado 16%";
    //Impuesto al Valor Agragado
    public String IVA = "";
    //Almacena el importe con formato del IVA
    public String formato_iva = "";
    //Honorarios Fiduciarios
    //Descripción de los HF.
    public String des_HF = "Honorarios Fiduciarios";
    public BigDecimal HF = BigDecimal.ZERO;
    //Almacena el importe de los honorarios fiduciarios
    public String honorarios_fidu = "";
    //Almacena el formato del importe de los honorarios fiduciarios
    public String formato_honorarios_fidu = "";
    //Descripción la suma total del importe de liquidación en MXP.

    public String des_SPR = "SUFICIENCIA PATRIMONIAL REQUERIDA PARA MOVIMIENTOS EN PESOS MEXICANOS";
    //Suficiencia Patrimonial Requerida
    public BigDecimal SPR = BigDecimal.ZERO;
    //Almacena el formato del importe de la Suficiencia Patrimonial Requerida
    public String formato_SPR = "";
    //Descripción la suma total del importe de liquidación en MXP.

    public String des_SPR_BE = "SUFICIENCIA PATRIMONIAL REQUERIDA PARA MOVIMIENTOS EN MONEDA EXTRANJERA";

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

    public String getNuevo_saldo() {
        return nuevo_saldo;
    }

    public void setNuevo_saldo(String nuevo_saldo) {
        this.nuevo_saldo = nuevo_saldo;
    }

    public String fecha_liquidacion = "";

    // Almacena el saldo actual del fideicomiso
    public String saldo_actual = "";
    // Almacena el texto que imprimirá en pantalla dependiendo del nuevo saldo(positivo o negativo
    public String txt_Nuevo_saldo = "";
    // Almacena el nuevo saldo 
    public String nuevo_saldo = "";     
    

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

    public void setTotal_movs_tipo1(int total_movs_tipo1) {
        this.total_movs_tipo1 = total_movs_tipo1;
    }

    public void setTotal_movs_tipo2(int total_movs_tipo2) {
        this.total_movs_tipo2 = total_movs_tipo2;
    }

    public void setTotal_movs_tipo3(int total_movs_tipo3) {
        this.total_movs_tipo3 = total_movs_tipo3;
    }

    public void setTotal_movs_tipo4(int total_movs_tipo4) {
        this.total_movs_tipo4 = total_movs_tipo4;
    }

    public void setTotal_movs_tipo5(int total_movs_tipo5) {
        this.total_movs_tipo5 = total_movs_tipo5;
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

    public void setDes_AFI(String des_AFI) {
        this.des_AFI = des_AFI;
    }

    public void setDes_HF(String des_HF) {
        this.des_HF = des_HF;
    }

    public void setDes_IVA(String des_IVA) {
        this.des_IVA = des_IVA;
    }

    public void setDes_SPR(String des_SPR) {
        this.des_SPR = des_SPR;
    }

    public void setDes_SPR_BE(String des_SPR_BE) {
        this.des_SPR_BE = des_SPR_BE;
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
}
