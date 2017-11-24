/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * Author     : Luis Antio Valerio Gayosso
 */


function regresar(formulario,accion,listaObjetos,paginaDestino)
{
    formulario.accion.value = accion;
    formulario.nombresObjetos.value = listaObjetos;
    formulario.urlResponse.value = paginaDestino;
    formulario.submit();
    return false;
}

function buscar_movimientos(formulario,accion)
{
    formulario.accion.value = accion;
    
    formulario.submit();

    return false;
}

function enviar(formulario)
{
    formulario.accion.value = accion;
//    formulario.action.value = 'muestraPDF.do';
    document.forms.saldo.action= 'PasswordChange.htm';
    formulario.submit();

    return false;
}