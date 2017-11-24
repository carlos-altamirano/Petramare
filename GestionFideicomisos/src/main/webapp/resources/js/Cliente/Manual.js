$(function () {
    
    $('#dp1').fdatepicker({
        format: 'mm-dd-yyyy',
        disableDblClickSelection: true
    });
    $('#dp2').fdatepicker({
        format: 'mm-dd-yyyy',
        disableDblClickSelection: true
    });
    
    var fechaHoy = function(){
        var fecha = new Date();
        var dia = '' + fecha.getDate();
        var mes = '' + (fecha.getMonth()+1);
        var anio = fecha.getFullYear();
        if (dia.length === 1) { dia = '0' + dia; }
        if (mes.length === 1) { mes = '0' + mes; }
        return mes+'-'+dia+'-'+anio;
    };
    $('#fechaActual').click(function(){
        $('#dp1').val(fechaHoy());
        $('#errordp1').fadeOut();
    });
    $('#fechaActual2').click(function(){
        $('#dp2').val(fechaHoy());
        $('#errordp2').fadeOut();
    });
    
    function validaFecha(fechaInicial,fechaFinal){
        valuesStart=fechaInicial.split("/");
        valuesEnd=fechaFinal.split("/");
        var dateStart=new Date(valuesStart[2],(valuesStart[1]-1),valuesStart[0]);
        var dateEnd=new Date(valuesEnd[2],(valuesEnd[1]-1),valuesEnd[0]);
        if(dateStart>=dateEnd) {
            return false;
        }
        return true;
    }
    
    $('#dp1').change(function(){
        var fechaSelec = $(this).val();
        if (fechaSelec !== '') {
            //fecha actual
            var fecha = new Date();
            var dia = '' + fecha.getDate();
            var mes = '' + (fecha.getMonth()+1);
            var anio = fecha.getFullYear();
            if (dia.length === 1) { dia = '0' + dia; }
            if (mes.length === 1) { mes = '0' + mes; }
            //fecha seleccionada
            var anioSel = fechaSelec.substring(6,10);
            var mesSel = fechaSelec.substring(0,2);
            var diaSel = fechaSelec.substring(3,5);
            var dateSel = diaSel+"/"+mesSel+"/"+anioSel;
            var dateCompare = dia+"/"+mes+"/"+anio;
            if (validaFecha(dateSel, dateCompare)) {
                $('#errordp1').fadeIn();
            } else {
                $('#errordp1').fadeOut();
            }
        }
    });
    
    $('#dp2').change(function(){
        var fechaSelec = $(this).val();
        if (fechaSelec !== '') {
            //fecha actual
            var fecha = new Date();
            var dia = '' + fecha.getDate();
            var mes = '' + (fecha.getMonth()+1);
            var anio = fecha.getFullYear();
            if (dia.length === 1) { dia = '0' + dia; }
            if (mes.length === 1) { mes = '0' + mes; }
            //fecha seleccionada
            var anioSel = fechaSelec.substring(6,10);
            var mesSel = fechaSelec.substring(0,2);
            var diaSel = fechaSelec.substring(3,5);
            var dateSel = diaSel+"/"+mesSel+"/"+anioSel;
            var dateCompare = dia+"/"+mes+"/"+anio;
            if (validaFecha(dateSel, dateCompare)) {
                $('#errordp2').fadeIn();
            } else {
                $('#errordp2').fadeOut();
            }
        }
    });
    
    $('#movimiento').submit(function(){
        var dp1 = $('#dp1').val();
        var anioSel = dp1.substring(6,10);
        var mesSel = dp1.substring(0,2);
        var diaSel = dp1.substring(3,5);
        var dp2 = $('#dp2').val();
        var anioSel2 = dp1.substring(6,10);
        var mesSel2 = dp1.substring(0,2);
        var diaSel2 = dp1.substring(3,5);
        $('#dp1').val(anioSel+'-'+mesSel+'-'+diaSel);
        $('#dp2').val(anioSel2+'-'+mesSel2+'-'+diaSel2);
    });
    
    var cheque = function(valor) {
        $('#envioCheque').val(valor);
        $('#destinoEnvioCheque').val(valor);
        $('#telEnvioCheque').val(valor);
        $('#correoEnvioCheque').val(valor);
    };
    
    var extranjero = function(valor){
        $('#bancoExtranjero').val(valor);
        $('#domBancoExtranjero').val(valor);
        $('#paisBancoExtranjero').val(valor);
        $('#abaBic').val(valor);
        $('#nombreFideiBancoExt').val(valor);
        $('#direccionFideiExt').val(valor);
        $('#paisFideiExt').val(valor);
        $('#telFideiExt').val(valor);
    };
    
    $('#tipoMovimiento').change(function(){
        var tipoMovimiento =$(this).val();
        $('#cuentaDeposito').val('');
        $('#cuentaDeposito').removeAttr('pattern');
        $('#cuentaDeposito').removeAttr('title');
        
        $('#telEnvioCheque').removeAttr('maxlength');
        $('#telEnvioCheque').removeAttr('minlength');
        $('#telEnvioCheque').removeAttr('pattern');
        $('#telEnvioCheque').removeAttr('title');
        
        $('#telFideiExt').removeAttr('maxlength');
        $('#telFideiExt').removeAttr('minlength');
        $('#telFideiExt').removeAttr('pattern');
        $('#telFideiExt').removeAttr('title');
        
        $('#correoEnvioCheque').removeAttr('type');
        $('#correoEnvioCheque').attr('type', 'text');
        switch (tipoMovimiento) {
            case '1':
                $('#cuentaDeposito').attr('pattern','^[0-9]{10}$');
                $('#cuentaDeposito').attr('title','Solo se permiten 10 numeros');
                cheque('NA');
                extranjero('NA');
                break;
            case '2':
                $('#cuentaDeposito').attr('pattern','^[0-9]{18}$');
                $('#cuentaDeposito').attr('title','Solo se permiten 18 numeros');
                cheque('NA');
                extranjero('NA');
                break;
            case '3':
                $('#cuentaDeposito').attr('pattern','^[0-9]{16}$');
                $('#cuentaDeposito').attr('title','Solo se permiten 16 numeros');
                cheque('NA');
                extranjero('NA');
                break;
            case '4':
                $('#cuentaDeposito').attr('pattern', '^[0]{1}');
                $('#cuentaDeposito').attr('title','Solo se permite el 0');
                $('#cuentaDeposito').val('0');
                $('#telEnvioCheque').attr('maxlength', '10');
                $('#telEnvioCheque').attr('minlength', '8');
                $('#telEnvioCheque').attr('pattern', '^[0-9]+');
                $('#telEnvioCheque').attr('title', 'Solo se permiten numeros');
                $('#correoEnvioCheque').attr('type', 'email');
                cheque('');
                extranjero('NA');
                break;
            case '5':
                $('#cuentaDeposito').attr('pattern','^[0-9]{10}$');
                $('#cuentaDeposito').attr('title','Solo se permiten 10 numeros');
                $('#telFideiExt').attr('maxlength', '10');
                $('#telFideiExt').attr('minlength', '8');
                $('#telFideiExt').attr('pattern', '^[0-9]+');
                $('#telFideiExt').attr('title', 'Solo se permiten numeros');
                cheque('NA');
                extranjero('');
                break;
        }
    });
    
    var tipoCuatro = function(){
        var tipoMovimiento = $('#tipoMovimiento').val();
        if (tipoMovimiento==='4') {
            var nombre = $('#nombreEmpleado').val().trim();
            var apppat = $('#apellidoPEmpleado').val().trim();
            var apmat = $('#apellidoMEmpleado').val().trim();
            $('#envioCheque').val(nombre + ' ' + apppat + ' ' + apmat);
        }
    };
    $('#nombreEmpleado').on('keyup', function(){
        tipoCuatro();
    });
    $('#apellidoPEmpleado').on('keyup', function(){
        tipoCuatro();
    });
    $('#apellidoMEmpleado').on('keyup', function(){
        tipoCuatro();
    });
    
});