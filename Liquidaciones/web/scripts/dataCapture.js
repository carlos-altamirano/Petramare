/*
 *    Author     : Luis Antio Valerio Gayosso
 *    Fecha:                        24/11/2011 11:40 A.M.
 *    Descripción:                  Controlador : "dataCapture.js" Scripts necesarios para la carga Manual.
 *    Responsable:                  Carlos Altamirano
 */

// Funcion que permite simular la funcion trim() de java
String.prototype.trim = function ()
{
    return this.replace(/^\s+|\s+$/g, "");
}

function setActionComboMovs(formulario, accion) {
    var indice = formulario.selComboMovs.selectedIndex;
    var valor = formulario.selComboMovs.options[indice].text;
    formulario.tipo_movimiento.value = valor;
    formulario.accion.value = accion;
    formulario.submit();
    return false;
}

function validaInputNum(e) {
    var tecla = (document.all) ? e.keyCode : e.which;
    if (tecla == 8)
        return true;
    patron = /[0-9.]/;
    te = String.fromCharCode(tecla);
    return patron.test(te);
}

function validaCURP() {
    var curpTest = document.getElementById("curp").value;
    curpTest = curpTest.toUpperCase();
    document.captureData.CURP.value = curpTest;
//    var patron = "[A-Z][A,E,I,O,U,X][A-Z]{2}[0-9]{2}[0-1][0-9][0-3][0-9]";
//    patron += "[M,H][A-Z]{2}[B,C,D,F,G,H,J,K,L,M,N,Ñ,P,Q,R,S,T,V,W,X,Y,Z]{3}[0-9,A-Z][0-9]";
    var patron = "[A-Z][AEIOUX][A-Z]{2}[0-9]{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])[MH]"
            + "([ABCMTZ]S|[BCJMOT]C|[CNPST]L|[GNQ]T|[GQS]R|C[MH]|[MY]N|[DH]G|NE|VZ|DF|SP)"
            + "[BCDFGHJ-NP-TV-Z]{3}[0-9A-Z][0-9]";
    var expreg = new RegExp(patron);

    if (expreg.test(curpTest)) {
        return true;
    } else {
        alert("Clave Única de Registro de Población incorrecta, favor de verificar.");
        return false;
    }
}

function validaRFC() {
    var rfcTest = document.getElementById("rfc").value;
    rfcTest = rfcTest.toUpperCase();
    document.captureData.RFC.value = rfcTest;
    var patron = "[A-Z,Ñ,&]{4}[0-9]{2}[0-1][0-9][0-3][0-9][A-Z,0-9]{3}";
    var expreg = new RegExp(patron);

    if (expreg.test(rfcTest)) {
        return true;
    } else {
        alert("RFC incorrecto, favor de verificar.");
        return false;
    }
}

function validaInputLetter(e) {
    var tecla = (document.all) ? e.keyCode : e.which;
    if (tecla == 8)
        return true;
    patron = /[A-Za-z\s]/;

    te = String.fromCharCode(tecla);
    return patron.test(te);
}

function setAction(formulario, accion) {
    formulario.accion.value = accion;
    formulario.submit();
    return false;
}

// Funcion para poder regresar a vistas anteriores
function atras(formulario, accion, listaObjetos, paginaDestino)
{
    formulario.accion.value = accion;
    formulario.nombresObjetos.value = listaObjetos;
    formulario.urlResponse.value = paginaDestino;
    formulario.submit();

    return false;
}

//Funcion para confirmar la impresion del importe total
function limpiaSesion(eliminaBeans, listaObjetos, paginaDestino)
{
    if (confirm("¿Desea imprimir el resumen del importe total?")) {
        window.print();
        window.document.captureData.accion.value = eliminaBeans;
        window.document.captureData.nombresObjetos.value = listaObjetos;
        window.document.captureData.urlResponse.value = paginaDestino;
        window.document.captureData.submit();
    } else {
        window.document.captureData.accion.value = eliminaBeans;
        window.document.captureData.nombresObjetos.value = listaObjetos;
        window.document.captureData.urlResponse.value = paginaDestino;
        window.document.captureData.submit();
    }
    return false;
}

function guardarMovimiento(formulario, accion) {

    var fechaLiquidacion = formulario.fechaLiquidacion.value.trim();

    var tipoMovimiento = formulario.tipo_movimiento.value.trim();

    var indice1 = formulario.selComboKeyBank.selectedIndex;
    var claveBanco = formulario.selComboKeyBank.options[indice1].text;
    formulario.clave_banco.value = claveBanco;

    var indice2 = formulario.selComboKeyCoin.selectedIndex;
    var claveMoneda = formulario.selComboKeyCoin.options[indice2].text;
    formulario.clave_moneda.value = claveMoneda;

    var movimiento = tipoMovimiento.substring(0, 1).trim();
    var nombreFideicomisario = formulario.nombreFideicomisario.value.trim();
    var apellidoPaterno = formulario.apellidoPaterno.value.trim();
    var apellidoMaterno = formulario.apellidoMaterno.value.trim();

    var fechaIngreso = formulario.fechaIngreso.value.trim();
    var puesto = formulario.puesto.value.trim();
    var departamento = formulario.departamento.value.trim();

    var claveFideicomisario = formulario.claveFideicomisario.value.trim();
    var CURP = formulario.CURP.value.trim();
    var RFC = formulario.RFC.value.trim();

    var cuentaDeposito = formulario.cuentaDeposito.value.trim();
    var importeLiquidacion = formulario.importeLiquidacion.value.trim();

    var envioCheque = formulario.envioCheque.value.trim();
    var destinoCheque = formulario.destinoCheque.value.trim();
    var telefonoCheque = formulario.telefonoCheque.value.trim();
    var correoCheque = formulario.correoCheque.value.trim();

    var bancoExtranjero = formulario.bancoExtranjero.value.trim();
    var dirBancoExtranjero = formulario.dirBancoExtranjero.value.trim();
    var paisBancoExtranjero = formulario.paisBancoExtranjero.value.trim();
    var ABA_BIC = formulario.ABA_BIC.value.trim();
    var nombreFideiBancoExtranjero = formulario.nombreFideiBancoExtranjero.value.trim();
    var dirFideiBancoExtranjero = formulario.dirFideiBancoExtranjero.value.trim();
    var paisFideiBancoExtranjero = formulario.paisFideiBancoExtranjero.value.trim();
    var telFideiBancoExtranjero = formulario.telFideiBancoExtranjero.value.trim();

    if (fechaLiquidacion == '') {
        alert('Seleccione la fecha en la que tendrá lugar la liquidación.');
    } else {
        if (tipoMovimiento == 'Seleccione' || tipoMovimiento == '') {
            alert(' Seleccione el tipo de movimiento. ');
        } else {
            if (claveBanco == 'Seleccione' || claveBanco == '') {
                alert(' Seleccione la clave de banco de la cuenta del fideicomisario. ');
            } else {
                if (claveMoneda == 'Seleccione' || claveMoneda == '') {
                    alert(' Seleccione la clave de moneda correspondiente a la liquidación. ');
                } else {
                    if (nombreFideicomisario == '') {
                        alert(' Capture el nombre(s) del fideicomisario. ');
                    } else {
                        if (apellidoPaterno == '') {
                            alert(' Capture el apellido paterno del fideicomisario. ');
                        } else {
                            if (fechaIngreso == '') {
                                alert(' Seleccione la fecha de ingreso del fideicomisario al empleo. ');
                            } else {
                                if (puesto == '') {
                                    alert(' Capture el puesto que ocupa el fideicomisario al momento de la liquidación. ');
                                } else {
                                    if (departamento == '') {
                                        alert(' Capture el departamento en el que labora el fideicomisario al momento de la liquidación. ');
                                    } else {
                                        if (claveFideicomisario == '') {
                                            alert(' Capture la clave o número de control de nómina del fideicomisario. ');
                                        } else {
                                            if (CURP == '') {
                                                alert(' Capture la Clave Única de Registro de Población del fideicomisario. ');
                                            } else {
                                                if (!validaCURP()) {
//                                                    alert(' La nomenclatura de la Clave Única de Registro de Población es incorrecta.  ');
                                                } else {
                                                    if (RFC == '') {
                                                        alert(' Capture correctamente el RFC del fideicomisario. ');
                                                    } else {
                                                        if (!validaRFC()) {
                                                        } else {
                                                            if (cuentaDeposito == '' && movimiento != '4') {
                                                                alert(' Capture el número de cuenta del fideicomisario, de acuerdo al tipo de movimiento especificado. ');
                                                            } else {
                                                                if (movimiento == '1' && cuentaDeposito.length != '10') {
                                                                    alert('El número de cuenta del fideicomisario debe ser a 10 posiciones, según el tipo de movimiento especificado.');
                                                                } else {
                                                                    if (movimiento == '2' && cuentaDeposito.length != '18') {
                                                                        alert('La CLABE debe ser a 18 posiciones, según el tipo de movimiento especificado.');
                                                                    } else {
                                                                        if (movimiento == '3' && cuentaDeposito.length != '16') {
                                                                            alert('El número de tarjeta de débito debe ser a 16 posiciones, según el tipo de movimiento especificado.');
                                                                        } else {
                                                                            if (movimiento == '4' && cuentaDeposito != '') {
                                                                                alert('El número de cuenta no corresponde al tipo de movimiento especificado.');
                                                                            } else {
                                                                                if (importeLiquidacion == '') {
                                                                                    alert(' Capture el importe de liquidación sin formato y hasta dos decimales. ');
                                                                                } else {
                                                                                    // Validación del salario mínimo
                                                                                    if (movimiento != '5' && importeLiquidacion < 80.04) {
                                                                                        alert(' Monto de indemnización inferior al legal permitida: $' + importeLiquidacion);
                                                                                    } else {
                                                                                        if (envioCheque == '') {
                                                                                            alert(' Capture el nombre de la persona a la que se le enviará el cheque correspondiente. ');
                                                                                        } else {
                                                                                            if (destinoCheque == '') {
                                                                                                alert(' Capture el domicilio de la persona a quién se le hará llegar el cheque. ');
                                                                                            } else {
                                                                                                if (telefonoCheque == '') {
                                                                                                    alert(' Capture el teléfono con clave de larga distancia de la persona a quién se le enviará el cheque. ');
                                                                                                } else {
                                                                                                    if (correoCheque == '') {
                                                                                                        alert(' Capture la cuenta de correo electrónico de la persona a quién se le hará llegar el cheque. ');
                                                                                                    } else {
                                                                                                        if (bancoExtranjero == '') {
                                                                                                            alert(' Capture el nombre del banco que posee la cuenta del fideicomisario en el Extranjero. ');
                                                                                                        } else {
                                                                                                            if (dirBancoExtranjero == '') {
                                                                                                                alert(' Capture la dirección del banco que posee la cuenta del fideicomisario en el Extranjero. ');
                                                                                                            } else {
                                                                                                                if (paisBancoExtranjero == '') {
                                                                                                                    alert(' Capture la dirección del banco que posee la cuenta el fideicomisario en el Extranjero.  ');
                                                                                                                } else {
                                                                                                                    if (ABA_BIC == '') {
                                                                                                                        alert(' Capture la clave ABA_BIC del Banco Extranjero. ');
                                                                                                                    } else {
                                                                                                                        if (nombreFideiBancoExtranjero == '') {
                                                                                                                            alert(' Capture el nombre del fideicomisario registrado en los términos de apertura de la cuenta en el extranjero. ');
                                                                                                                        } else {
                                                                                                                            if (dirFideiBancoExtranjero == '') {
                                                                                                                                alert(' Capture el domicilio del fideicomisario en el extranjero. ');
                                                                                                                            } else {
                                                                                                                                if (paisFideiBancoExtranjero == '') {
                                                                                                                                    alert(' Capture el nombre del país en el que el fideicomisario tiene su domicilio en el extranjero. ');
                                                                                                                                } else {
                                                                                                                                    if (telFideiBancoExtranjero == '') {
                                                                                                                                        alert(' Capture el número telefónico con clave de larga distancia del fideicomisario que se encuentra en el extranjero. ');
                                                                                                                                    } else {
                                                                                                                                        formulario.accion.value = accion;
                                                                                                                                        formulario.submit();
                                                                                                                                    }
                                                                                                                                }
                                                                                                                            }
                                                                                                                        }
                                                                                                                    }
                                                                                                                }
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


}

