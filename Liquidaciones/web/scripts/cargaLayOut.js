/*
 *    Author     : Luis Antio Valerio Gayosso
 *    Fecha:                         7/03/2011
 *    Descripción:                  Controlador : "cargaLayOut.js" Scripts necesarios para la carga del Lay-Out
 *    Responsable:                  Carlos Altamirano
 */


//Funcion para confirmar el importe total
function confirmarUsuario(forma,accion)
{
    alert( 'Favor de confirmar usuario ');
    forma.accion.value = accion
    forma.submit();

}

// Funcion para poder regresar a vistas anteriores
function atras(formulario,accion,listaObjetos,paginaDestino)
{
    formulario.accion.value = accion;
    formulario.nombresObjetos.value = listaObjetos;
    formulario.urlResponse.value = paginaDestino;
    formulario.submit();

    return false;
}

// Funcion para generar los saldos del cliente
function generaSaldo(formulario)
{
    formulario.submit();
    return false;
}

//Funcion para controlar el valor de la variable acción
function cargaValor(forma,accion){
    forma.accion.value = accion;
    forma.submit();

    return false;

}
function cleanSesion(forma,eliminaBeans,listaObjetos,paginaDestino)
{

    if (window.attachEvent) {
        window.attachEvent('onLoad', limpiaSesion(forma,eliminaBeans,listaObjetos,paginaDestino));
    }
    else if (window.addEventListener) {
        window.addEventListener('onLoad', limpiaSesion(forma,eliminaBeans,listaObjetos,paginaDestino), false);
    }
    else {
        document.addEventListener('onLoad', limpiaSesion(forma,eliminaBeans,listaObjetos,paginaDestino), false);
    } 

}


//Funcion para confirmar la impresion del importe total
function limpiaSesion(eliminaBeans,listaObjetos,paginaDestino)
{
    if (confirm("¿Desea imprimir el resumen del importe total?")){
        window.print();
        window.document.dataLoad.accion.value=eliminaBeans;
        window.document.dataLoad.nombresObjetos.value = listaObjetos;
        window.document.dataLoad.urlResponse.value=paginaDestino;
        window.document.dataLoad.submit();
    }
    else{
        window.document.dataLoad.accion.value=eliminaBeans;
        window.document.dataLoad.nombresObjetos.value = listaObjetos;
        window.document.dataLoad.urlResponse.value=paginaDestino;
        window.document.dataLoad.submit();
    }
    return false;
}

function imprimeBrowser(imprime)
{
    var ficha = document.getElementById(imprime);
    var ventimp = window.open(' ', 'popimpr');
    ventimp.document.write( ficha.innerHTML );
    ventimp.document.close();
    ventimp.print( );
    ventimp.close();
} 
