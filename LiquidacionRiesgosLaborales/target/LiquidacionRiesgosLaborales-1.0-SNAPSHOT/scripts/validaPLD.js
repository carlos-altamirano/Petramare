$(function(){
    
    var enviaForm = function (claveContrato){
        $('#cliente').val(claveContrato);
        $('#accion').val('AportacionRestitucionObtenDatos:35');
        $('#formLiquidation').submit();
    };
    
    $('#cambiaClaveFideicomiso').change(function(){
        var claveContra = $('#cambiaClaveFideicomiso :selected').text();
        $.post('/gestionfideicomisos/verifica/contrato', {claveContrato:claveContra}, function (contrato){
            contrato = JSON.parse(contrato);
            if (contrato.pld === 1) {
                enviaForm(claveContra);
            } else {
                $.get('https://api.trade.gov/consolidated_screening_list/search?api_key=0b4lqNGmnWmC1MgPYrkCHltx&q='+contrato.nombre, {}, function(pld){
                    if (pld.results.length >= 1) {
                        $.post('/gestionfideicomisos/bloquea/contrato', {claveContrato:claveContra}, function(respuesta){
                            alert('Este Fideicomiso tiene problemas de PLD y por lo tanto han sido bloqueado');
                        });
                    } else {
                        if (contrato.socios.length === 0) {
                            enviaForm(claveContra);
                        } else {
                            var encontroSocio = false;
                            for (var i = 0; i < contrato.socios.length; i++) {
                                $.ajax({
                                    url: 'https://api.trade.gov/consolidated_screening_list/search?api_key=0b4lqNGmnWmC1MgPYrkCHltx&q='+contrato.socios[i].nombre,
                                    async: false,
                                    method: 'get'
                                }).done(function(pld){
                                    if (pld.results.length >= 1) {
                                        encontroSocio = true;
                                    }
                                });
                            }
                            if (encontroSocio) {
                                $.post('/gestionfideicomisos/bloquea/contrato', {claveContrato:claveContra}, function(respuesta){
                                    alert('Este Fideicomiso tiene problemas de PLD y por lo tanto han sido bloqueado');
                                });
                            } else {
                                enviaForm(claveContra);
                            }
                        }
                    }
                });
            }
            
        });
        
    });
    
});