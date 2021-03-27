$(function(){
    var main = new Main();
    
    if (main.getQueryString('contra') === 'ok') {
        main.creaModal('modal', 'success', '�Nueva contrase�a!', 'Se cambio exitosamente la contrase�a');
    }
    if (main.getQueryString('contra') === 'error') {
        main.creaModal('modal', 'alert', '�Nueva contrase�a!', 'Error al re generar contrase�a');
    }
    if (main.getQueryString('save') === 'ok') {
         main.creaModal('modal', 'success', '�Atenci�n!', 'Se guardar�n los datos correctamente');
    }
    if (main.getQueryString('save') === 'error') {
        main.creaModal('modal', 'alert', '�Atenci�n!', 'Error al guardar los datos');
    }
    if (main.getQueryString('update') === 'ok') {
        main.creaModal('modal', 'success', '�Atenci�n!', 'Se actualizaron los datos correctamente');
    }
    if (main.getQueryString('update') === 'error') {
        main.creaModal('modal', 'alert', '�Atenci�n!', 'Error al actualizar los datos');
    }
    if (main.getQueryString('save') === 'duplicado') {
        main.creaModal('modal', 'warning', '�Atenci�n!', 'La informaci�n que intentas ingresar ya existe en el sistema');
    }
    
    $('#contrato').submit(function(){
        $('#claveContrato').removeAttr('disabled');
    });
    
    if (main.getQueryString('msg') === '1') {
        $('html,body').animate({
            scrollTop: $("#moverAbajo").offset().top
        }, 0);
        main.creaModal('modal', 'warning', '�Atenci�n!', 'El peso maximo del archivo es de 10MB');
    }
    if (main.getQueryString('msg') === '2') {
        $('html,body').animate({
            scrollTop: $("#moverAbajo").offset().top
        }, 0);
        main.creaModal('modal', 'success', '�Correcto!', 'Se subi� el archivo correctamente');
    }
    if (main.getQueryString('msg') === '3') {
        $('html,body').animate({
            scrollTop: $("#moverAbajo").offset().top
        }, 0);
        main.creaModal('modal', 'alert', '�Atenci�n!', 'Error al subir el archivo vuelve a intentarlo m�s tarde');
    }
    if (main.getQueryString('msg') === '4') {
        $('html,body').animate({
            scrollTop: $("#moverAbajo").offset().top
        }, 0);
        main.creaModal('modal', 'warning', '�Atenci�n!', 'Solo se permiten archivos PDF');
    }
    if (main.getQueryString('msg') === '5') {
        $('html,body').animate({
            scrollTop: $("#moverAbajo").offset().top
        }, 0);
        main.creaModal('modal', 'warning', '�Atenci�n!', 'Debes seleccionar un archivo para poder continuar');
    }
    if (main.getQueryString('msg') === '6') {
        $('html,body').animate({
            scrollTop: $("#moverAbajo").offset().top
        }, 0);
        main.creaModal('modal', 'alert', '�Atenci�n!', 'Error al subir el archivo');
    }
    
});