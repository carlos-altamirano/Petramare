/*
 *    Creado por:                   Luis Antio Valerio Gayosso
 *    Fecha:                        23/06/2011
 *    Descripción:                  Modelo : "login.js" Scripts para validar al usuario que ingresa al sistema
 *    Responsable:                  Carlos Altamirano
 */


// Funcion que permite simular la funcion trim() de java
String.prototype.trim = function()
{
    return this.replace(/^\s+|\s+$/g,"");
}

// Funcion que valida al usuario que intenta ingresar
function loginUsuario(forma,accion)
{
    var usuario = forma.usuario;
    var contrasenna = forma.contrasenna;

    if(usuario.value=='')
    {
        alert('Favor de Introducir Usuario');
    }
    else
    {
        if(contrasenna.value=='')
        {
            alert('Favor de Introducir Password');
        }
        else
        {
             forma.accion.value = accion;
            forma.submit();
        }
    }
    
    return false;
}


// Funcion para cambiar el Password del cliente
function cambiaPassword(formulario,accion){

    formulario.accion.value = accion;
    formulario.submit();
    return false;
}

function actualizaPassword(formulario,accion){

    var newPass1 = formulario.newPass1;
    var newPass2 = formulario.newPass2;

    if(newPass1.value==''){
        alert('Favor de Introducir el Nuevo Password');
        return false;
    }
    else{
        if(newPass2.value==''){
            alert('Favor de Confirmar Password');
            return false;
        }
        else{
            if(newPass1.value == newPass2.value){
                formulario.accion.value = accion;
                formulario.submit();
            }else{
                alert( ' No Coincide el Nuevo Password con la Confirmación ');
                return false;
            }
        }
    }
    return false;
}

function cambiaSalarioMinimo() {
    $.post('ControllerLiquidation', {accion:'cambiaSalarioMinimo:200'}, function(data) {
        $('#salario_minimo').val(data);
        $('#salaryModal').modal();
    });
}

function guardarSalarioMinimo() {
    $.post('ControllerLiquidation', {accion:'guardarSalarioMinimo:300', salario:$('#salario_minimo').val()}, function(data) {
        if (data == 0) return;
        $('#noticeModal').modal();
    });
}
