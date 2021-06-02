/*
 *    Author     : Luis Antio Valerio Gayosso
 *    Fecha:                        22/02/2011
 *    Descripción:                  Controlador : "login.js" Scripts necesarios la autentificación del usuario.
 *    Responsable:                  Carlos Altamirano
 */
// Funcion que permite simular la funcion trim() de java
String.prototype.trim = function()
{
    return this.replace(/^\s+|\s+$/g,"");
}



// Función que valida al usuario que intenta Acceder.
function loginUsuario(forma,accion)
{
    var custNum = forma.custNum.value.trim();
    var usuario = forma.usuario.value.trim();
    var contrasenna = forma.contrasenna.value.trim();

    if ( custNum == '' )
    {
        alert('Favor de introducir clave de cliente');
    }
    else
    {
        if( usuario == '')
        {
            alert('Favor de introducir usuario');
        }
        else
        {
            if(contrasenna == '')
            {
                alert('Favor de introducir Contraseña');
            }
            else
            {
                forma.accion.value = accion;
                forma.submit();
            }
        }
    }
    return false;
}

// Funcion que vuelve a validar al usuario para guardar en la BD
function loginUsuario2(forma,accion)
{

    var usuario = forma.usuario;
    var contrasenna = forma.contrasenna;

    alert( 'FAVOR DE PULSAR UNA SOLA VEZ ');
    if(usuario.value=='')
    {
        alert('Favor de introducir nombre de usuario');
        return false;
    }
    else
    {
        if(contrasenna.value=='')
        {
            alert('Favor de introducir el password correspondiente');
            return false;
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
        alert('Favor de introducir la nueva contraseña');
        return false;
    }
    else{
        if(newPass2.value==''){
            alert('Favor de confirmar contraseña');
            return false;
        }
        else{
            if(newPass1.value == newPass2.value){
                formulario.accion.value = accion;
                formulario.submit();
            }else{
                alert( ' La nueva contraseña NO coincide con la confirmación ');
                return false;
            }
        }
    }
    return false;
}

function setAction(formulario,accion){

    formulario.accion.value = accion;
    formulario.submit();
    return false;
}
