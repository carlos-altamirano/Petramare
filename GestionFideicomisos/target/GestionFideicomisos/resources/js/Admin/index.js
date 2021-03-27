$(function(){
    var main = new Main();
    
    if (main.getQueryString('contra') === 'ok') {
        main.creaModal('modal', 'success', '¡Nueva contraseña!', 'Se cambio exitosamente la contraseña');
    }
    if (main.getQueryString('contra') === 'error') {
        main.creaModal('modal', 'alert', '¡Nueva contraseña!', 'Error al re generar contraseña');
    }
    if (main.getQueryString('save') === 'ok') {
         main.creaModal('modal', 'success', '¡Atención!', 'Se guardarón los datos correctamente');
    }
    if (main.getQueryString('save') === 'error') {
        main.creaModal('modal', 'alert', '¡Atención!', 'Error al guardar los datos');
    }
    if (main.getQueryString('update') === 'ok') {
        main.creaModal('modal', 'success', '¡Atención!', 'Se actualizaron los datos correctamente');
    }
    if (main.getQueryString('update') === 'error') {
        main.creaModal('modal', 'alert', '¡Atención!', 'Error al actualizar los datos');
    }
    if (main.getQueryString('save') === 'duplicado') {
        main.creaModal('modal', 'warning', '¡Atención!', 'La información que intentas ingresar ya existe en el sistema');
    }
    
    $('#contrato').submit(function(){
        $('#claveContrato').removeAttr('disabled');
    });
    
    if (main.getQueryString('msg') === '1') {
        $('html,body').animate({
            scrollTop: $("#moverAbajo").offset().top
        }, 0);
        main.creaModal('modal', 'warning', '¡Atención!', 'El peso maximo del archivo es de 10MB');
    }
    if (main.getQueryString('msg') === '2') {
        $('html,body').animate({
            scrollTop: $("#moverAbajo").offset().top
        }, 0);
        main.creaModal('modal', 'success', '¡Correcto!', 'Se subió el archivo correctamente');
    }
    if (main.getQueryString('msg') === '3') {
        $('html,body').animate({
            scrollTop: $("#moverAbajo").offset().top
        }, 0);
        main.creaModal('modal', 'alert', '¡Atención!', 'Error al subir el archivo vuelve a intentarlo más tarde');
    }
    if (main.getQueryString('msg') === '4') {
        $('html,body').animate({
            scrollTop: $("#moverAbajo").offset().top
        }, 0);
        main.creaModal('modal', 'warning', '¡Atención!', 'Solo se permiten archivos PDF');
    }
    if (main.getQueryString('msg') === '5') {
        $('html,body').animate({
            scrollTop: $("#moverAbajo").offset().top
        }, 0);
        main.creaModal('modal', 'warning', '¡Atención!', 'Debes seleccionar un archivo para poder continuar');
    }
    if (main.getQueryString('msg') === '6') {
        $('html,body').animate({
            scrollTop: $("#moverAbajo").offset().top
        }, 0);
        main.creaModal('modal', 'alert', '¡Atención!', 'Error al subir el archivo');
    }
    
});