/*
 *    Creado por:                   Luis Antio Valerio Gayosso
 *    Fecha:                        27/06/2011
 *    Descripción:                  Modelo : "liquidation.js" Scripts para generar las liquidaciones pendiente
 *    Responsable:                  Carlos Altamirano
 */
//se ingresa el valor del salario minimo para validar los movimientos tipo 5
var salario_minimo = 73.04;

// Funcion para poder regresar a vistas anteriores
function atras(formulario, accion, listaObjetos, paginaDestino)
{
    formulario.accion.value = accion;
    formulario.nombresObjetos.value = listaObjetos;
    formulario.urlResponse.value = paginaDestino;
    formulario.submit();
    return false;

}

function setAction(formulario, accion) {

    formulario.accion.value = accion;
    formulario.submit();
    return false;
}

function setActionComboCli(formulario, accion) {
    var indice = formulario.selComboData.selectedIndex;
    var valor = formulario.selComboData.options[indice].text;
    formulario.cliente.value = valor;
    formulario.accion.value = accion;
    formulario.submit();
    return false;
}

function setImporteRes(formulario, accion) {
    var importeLiquidacion = formulario.importe_restitucion.value.trim();
    if (importeLiquidacion > 0) {
        formulario.accion.value = accion;
        formulario.submit();
        return true;
    }
    alert('Importe de restitución inválido $' + importeLiquidacion);
    return false;
}

function almacenaOperacion(formulario, accion, operacion) {
    if (operacion == 'restitucion') {
        formulario.operacion.value = 'restitucion';
        formulario.accion.value = accion;
        if (confirm("¿Estas seguro de realizar la operación de restitución?")) {
            formulario.submit();
            return true;
        }
    } else if (operacion == 'aportacion') {
        formulario.operacion.value = 'aportacion';
        formulario.accion.value = accion;
        if (confirm("¿Estas seguro de realizar la operación de aportación?")) {
            formulario.submit();
            return true;
        }
    }

    return false;
}

function setImporteApo(formulario, accion) {
    var importeLiquidacion = formulario.importe_aportacion.value.trim();
    if (importeLiquidacion > 0) {
        formulario.accion.value = accion;
        formulario.submit();
        return true;
    }
    alert('Importe de aportación inválido $' + importeLiquidacion);
    return false;
}

function descargaArchivo(formulario, vector_descarga) {
    if (confirm("Apunto de descargar:" + vector_descarga)) {
        formulario.accion.value = vector_descarga;
        formulario.submit();
        return true;
    }
    return false;
}

//---
function setActionCancel(formulario, accion) {

    var indice = formulario.selComboMotivoCancelacion.selectedIndex;
    var valor = formulario.selComboMotivoCancelacion.options[indice].text;

    if (formulario.cuenta_deposito.value == '') {
        alert('Indique el número de cuenta de depósito');
    } else {
        if (formulario.nombre_empleado.value == '') {
            alert('Indique el nombre(s) del fideicomisario');
        } else {
            if (formulario.apellidoP_empleado.value == '') {
                alert('Indique el apellido paterno del fideicomisario');
            } else {
                if (formulario.apellidoM_empleado.value == '') {
                    alert('Indique el apellido materno del fideicomisario');
                } else {
                    if (formulario.id_empleado.value == '') {
                        alert('Indique el ideintificador del fideicomisario');
                    } else {
                        if (formulario.puesto_empleado.value == '') {
                            alert('Indique el puesto del fideicomisario');
                        } else {
                            if (formulario.depto_empleado.value == '') {
                                alert('Indique el departamento del fideicomisario');
                            } else {
                                if (formulario.fecha_ingreso.value == '') {
                                    alert('Indique la fecha de ingreso del fideicomisario');
                                } else {
                                    if (formulario.tipo_movimiento.value == '') {
                                        alert('Indique el tipo de movimiento');
                                    } else {
                                        if (formulario.clave_banco.value == '') {
                                            alert('Indique la clave del banco');
                                        } else {
                                            if (formulario.importe_liquidacion.value == '') {
                                                alert('Indique el importe de liquidación');
                                            } else {
                                                if (formulario.tipo_moneda.value == '') {
                                                    alert('Indique el tipo de moneda');
                                                } else {
                                                    if (formulario.envio_cheque.value == '') {
                                                        alert('Indique el receptor del cheque');
                                                    } else {
                                                        if (formulario.destino_cheque.value == '') {
                                                            alert('Indique el destino del cheque');
                                                        } else {
                                                            if (formulario.telefono_cheque.value == '') {
                                                                alert('Indique el teléfono del cheque');
                                                            } else {
                                                                if (formulario.correo_cheque.value == '') {
                                                                    alert('Indique el correo del cheque');
                                                                } else {
                                                                    if (formulario.banco_extranjero.value == '') {
                                                                        alert('Indique el banco extranjero');
                                                                    } else {
                                                                        if (formulario.domicilio_extranjero.value == '') {
                                                                            alert('Indique el domicilio del banco extranjero');
                                                                        } else {
                                                                            if (formulario.pais_extranjero.value == '') {
                                                                                alert('Indique el país del banco extranjero');
                                                                            } else {
                                                                                if (formulario.aba_bic.value == '') {
                                                                                    alert('Indique el ABA_BIC');
                                                                                } else {
                                                                                    if (formulario.nombre_fidei_extranjero.value == '') {
                                                                                        alert('Indique el nombre del fideicomisario en el extranjero');
                                                                                    } else {
                                                                                        if (formulario.direccion_fidei_extranjero.value == '') {
                                                                                            alert('Indique el domicilio del fideicomisario en el extranjero');
                                                                                        } else {
                                                                                            if (formulario.pais_fidei_extranjero.value == '') {
                                                                                                alert('Indique el país del fideicomisario en el extranjero');
                                                                                            } else {
                                                                                                if (formulario.tel_fidei_extranjero.value == '') {
                                                                                                    alert('Indique el teléfono del fideicomisario en el extranjero');
                                                                                                } else {
                                                                                                    if (valor == '--------------- Seleccione ---------------') {
                                                                                                        alert('Seleccione el motivo de la cancelación');
                                                                                                    } else {
                                                                                                        formulario.motivo_cancelacion.value = valor;
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

    return false;
}

function setActionComboDate(formulario, accion) {
    var indice = formulario.selComboDate.selectedIndex;
    var valor = formulario.selComboDate.options[indice].text;
    formulario.fecha_liquidacion.value = valor;
    formulario.accion.value = accion;
    formulario.submit();
    return false;
}


function setActionComboFileName(formulario, accion) {
    var indice = formulario.selComboFileName.selectedIndex;
    var valor = formulario.selComboFileName.options[indice].text;
    formulario.nombre_archivo.value = valor;
    formulario.accion.value = accion;
    formulario.submit();
    return false;
}

function setActionComboClaveFiso(formulario, accion) {
    var indice = formulario.selComboClaveFiso.selectedIndex;
    var valor = formulario.selComboClaveFiso.options[indice].text;
    formulario.cliente.value = valor;
    formulario.accion.value = accion;
    formulario.submit();
    return false;
}

function setActionComboOperacion(formulario, accion) {
    var indice = formulario.tipo_operacion.selectedIndex;
    var valor = formulario.tipo_operacion.options[indice].value;
    formulario.operacion.value = valor;
    formulario.accion.value = accion;
    formulario.submit();
    return false;
}
function setActionComboCtaOrigen(formulario, accion) {
    var indice = formulario.selComboCtaOrigen.selectedIndex;
    var valor = formulario.selComboCtaOrigen.options[indice].text;
    formulario.operacion.value = valor;
    formulario.accion.value = accion;
    formulario.submit();
    return false;
}

function setActionComboDateC(formulario, accion) {
    var indice = formulario.selComboDateC.selectedIndex;
    var valor = formulario.selComboDateC.options[indice].text;
    formulario.fecha_liquidacion.value = valor;
    formulario.accion.value = accion;
    formulario.submit();
    return false;
}

function setActionComboFileNameC(formulario, accion) {
    var indice = formulario.selComboFileNameC.selectedIndex;
    var valor = formulario.selComboFileNameC.options[indice].text;
    formulario.nombre_archivo.value = valor;
    formulario.accion.value = accion;
    formulario.submit();
    return false;
}

function setActionComboCliMov5(formulario, accion) {
    var indice = formulario.selComboCliMov5.selectedIndex;
    var valor = formulario.selComboCliMov5.options[indice].text;
    formulario.cliente.value = valor;
    formulario.accion.value = accion;
    formulario.submit();
    return false;
}

function setActionComboDateMov5(formulario, accion) {
    var indice = formulario.selComboDateMov5.selectedIndex;
    var valor = formulario.selComboDateMov5.options[indice].text;
    formulario.fecha_liquidacion.value = valor;
    formulario.accion.value = accion;
    formulario.submit();
    return false;
}


function setActionComboFileNameMov5(formulario, accion) {
    var indice = formulario.selComboFileNameMov5.selectedIndex;
    var valor = formulario.selComboFileNameMov5.options[indice].text;
    formulario.nombre_archivo.value = valor;
    formulario.accion.value = accion;
    formulario.submit();
    return false;
}

function setActionComboNameFideiMov5(formulario, accion) {

    var indice = formulario.selComboNameFideiMov5.selectedIndex;
    var valor = formulario.selComboNameFideiMov5.options[indice].text;
    formulario.nombre_fideicomisario.value = valor;
    formulario.accion.value = accion;
    formulario.submit();
    return false;
}

function setActionComboOption(formulario, accion) {
    var indice = formulario.selComboOpcion.selectedIndex;
    var valor = formulario.selComboOpcion.options[indice].text;
    formulario.opcionC.value = valor;
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

function setActualiza(formulario, accion) {
    var importe = document.getElementById("monto_mxp").value;
    if (importe == '') {
        alert('Favor de realizar la conversion del importe a MXN');
        return false;
    } else {
        if (importe < salario_minimo) {
            alert('El importe de liquidación es menor al legalmente permitido ( $ ' + salario_minimo + ")");
            return false;
        } else {
            formulario.importe_MXP.value = quitaComas(importe);
            formulario.accion.value = accion;
            formulario.submit();
        }
        return false;
    }
}

function calcularValorMXP() {
    var valorDivisa = document.getElementById("valorDivisa").value;
    var montoME = document.getElementById("Monto_ME").value;
    valorDivisa = quitaComas(valorDivisa);
    if (eventoComas("valorDivisa") === true) {
        montoME = quitaComas(montoME);
        document.getElementById("monto_mxp").value = ponComas((valorDivisa * montoME).toFixed(2));
    }
    return false;
}

function eventoComas(val) {
    var inDecimal = document.getElementById(val).value;
    inDecimal = quitaComas(inDecimal);
    var pattern = /[^(\d\.)]/;
    if (pattern.test(inDecimal)) {
        alert('Formato de entrada incorrecto, favor de verificar');
        return false;
    }
    var indexPunto = inDecimal.toString().indexOf(".");
    //se revisa si tiene punto decimal
    if (indexPunto !== -1) {
        if (indexPunto + 1 < inDecimal.length) {
            var decimales = inDecimal.toString().substr(indexPunto + 1, inDecimal.length);
            pattern = /(\D)/;
            if (pattern.test(decimales)) {
                alert("Los numeros decimales indicados en" + "(" + inDecimal + ")" + " son incorrectos.");
                return false;
            }
            if (decimales.length > 3) {
                alert("La cantidad indicada " + "(" + inDecimal + ")" + " no puede contener más de 3 digitos decimales.");
                document.getElementById(val).value = inDecimal.toString().substr(0, indexPunto + 4);
                return false;
            }
        }
    }
    inDecimal = ponComas(inDecimal);
    document.getElementById(val).value = inDecimal;
    return true;
}

function ponComas(val) {
    val = val.toString();
    var pattern = /(-?\d+)(\d{3})/;
    while (pattern.test(val)) {
        val = val.replace(pattern, "$1,$2");
    }
    return val;
}

function quitaComas(valor) {
    var coma = ',';
    while (valor.toString().indexOf(coma) != -1) {
        valor = valor.toString().replace(coma, '');
    }
    return valor;
}

function pulsar_enter(e, formulario, accion) {
    tecla = (document.all) ? e.keyCode : e.which;
    if (tecla == 13)
    {
        formulario.accion.value = accion;
        formulario.submit();
        return false;
    }
    return false;
}

