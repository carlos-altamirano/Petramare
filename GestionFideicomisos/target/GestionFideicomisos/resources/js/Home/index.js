$(function () {
    var getQueryString = function (name) {
        name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
        var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
                results = regex.exec(location.search);
        return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
    };

    if (getQueryString('sesion') === 'false') {
        $('#error-auth').text('Usuario o contraseña invalida');
        $('#error-auth').css({'color': 'red', 'font-size': '1.3em'});
        $('#error-auth').show();
    }
    if (getQueryString('sesion') === 'true') {
        $('#error-auth').text('Sesión cerrada correctamente');
        $('#error-auth').css({'color': 'green', 'font-size': '1.3em'});
        $('#error-auth').show();
    }
    if (getQueryString('sesion') === 'error') {
        $('#error-auth').text('Sesión expirada');
        $('#error-auth').css({'color': 'orange', 'font-size': '1.3em'});
        $('#error-auth').show();
    }

    $('#mostrarContra').click(function () {
        if ($(this).attr('value') === 'true') {
            $(this).attr('value', 'false');
            $(this).attr('title', 'Ocultar contraseña');
            $('#contra').attr('type', 'text');
            $('#cambiaIcono').removeClass('fi-lock');
            $('#cambiaIcono').addClass('fi-unlock');
        } else {
            $(this).attr('value', 'true');
            $(this).attr('title', 'Mostrar contraseña');
            $('#contra').attr('type', 'password');
            $('#cambiaIcono').removeClass('fi-unlock');
            $('#cambiaIcono').addClass('fi-lock');
        }
    });

});

