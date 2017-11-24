$(function(){
    $('#archivo').click(function(){
        $('#nombreArchivo').html('');
        $('#nombreArchivo').removeClass('warning');
        $('#nombreArchivo').removeClass('success');
        $('#nombreArchivo').removeClass('label');
        $('#nombreArchivo').fadeIn('slow');
        $('#archivo').closest('form').get(0).reset();
    });
    $('#archivo').change(function(){
        if ($(this).val() === '') {
            $('#nombreArchivo').fadeOut('slow');
        } else {
            var nombreArchivo = $(this).val().replace('C:\\fakepath\\','');
            var datos = {'nombre':nombreArchivo};
            var res = peticionesAjax('/cte/verificaNombreArchivo', 'post', datos);
                $('#nombreArchivo').addClass('label');
            if (res === 'false') {
                $('#nombreArchivo').addClass('success');
                $('#nombreArchivo').html( '<i class="fi-check"></i> ' + nombreArchivo);
            } else {
                $('#archivo').closest('form').get(0).reset();
                $('#nombreArchivo').addClass('warning');
                $('#nombreArchivo').html( '<i class="fi-alert"></i> El nombre del archivo ya existe');
            }
            $('#nombreArchivo').fadeIn('slow');
        }
    });
    
    var peticionesAjax = function(direccion, tipo, datos) {
        var res;
        $.ajax({
            url : direccion,
            type: tipo,
            data : datos,
            async: false
        }).done(function(data){
            res = data;
        }).error(function(){
            res = "error";
        });
        return res;
    };
    
});