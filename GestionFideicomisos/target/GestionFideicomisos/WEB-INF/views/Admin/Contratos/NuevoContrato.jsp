<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Garante</title>
    <link rel="icon" type="image/png" href="/gestionfideicomisos/resources/img/icono.png" />
    <link rel="stylesheet" href="/gestionfideicomisos/resources/css/Foundation/foundation.min.css">
    <link rel="stylesheet" href="/gestionfideicomisos/resources/css/Foundation/foundation-icons/foundation-icons.css">
    <link rel="stylesheet" href="/gestionfideicomisos/resources/css/Admin/index.css">
</head>
<body>
    
    <jsp:include page="/WEB-INF/views/Layouts/menu.jsp"></jsp:include>
    
    <div class="large-8 large-centered centered columns" id="pld" style="display: none;">
        <form>
            <fieldset class="fieldset">
                <legend><h4>PLD</h4></legend>
                <div id="encontradoPLD"></div>
            </fieldset>
        </form>
    </div>
    
    <div class="large-8 large-centered centered columns" id="validacion" style="display: none;">
        <fieldset class="fieldset">
            <legend><h5>Esta operación requiere autorización del oficial de cumplimiento</h5></legend>
            <form id="verificaPersona">
                <input type="hidden" name="contrato" id="contratoEnviar"/>
                <input type="hidden" name="nombreEmp" id="nombreEnviar"/>
                <div class="row">
                    <div class="large-6 columns">
                        <label>Usuario</label>
                        <input type="text" name="userName" placeholder="Usuario" required="required"/>
                    </div>
                    <div class="large-6 columns">
                        <label>Contraseña</label>
                        <input type="password" name="password" id="password" placeholder="Contraseña" required="required"/>
                    </div>
                    <div class="large-12 columns">
                        <label>Observaciones</label>
                        <textarea name="obs" id="obs" placeholder="Observaciones" maxlength="250" required="required"></textarea>
                    </div>
                </div>
            </form>
            <div class="row">
                <div class="large-6 medium-6 small-6 columns" style="text-align: right;">
                    <button class="button botonSofom" id="validaUsuario">Continuar</button>
                </div>
                <div class="large-6 medium-6 small-6 columns">
                    <a id="cancelapld" class="button" style="background-color: red;">Cancelar</a>
                </div>
            </div>
        </fieldset>
    </div>
    
    <div class="large-8 large-centered centered columns" id="divFormValido">
    <form:form commandName="contrato" method="post" action="${action}">
        <form:hidden path="pld" id="pldCampo"/>
        <fieldset class="fieldset">
            <legend><h4>Contrato</h4></legend>
            <div class="row">
                <div class="large-4 columns">
                    <label>Clave contrato</label>
                    <input type="text" name="claveContrato" pattern="^([FID]{3}[0-9]{3}[A-Z]{3}[0-1]{1}[0-9]{1}[0-9]{2})" maxlength="13" title="Solo el formato FID###CCCMMAA" id="claveContrato" value="${contrato.claveContrato}" placeholder="Clave contrato" required="required" <c:if test="${editable != null}">disabled="true"</c:if> />
                </div>
                <div class="large-4 columns">
                    <label>Nombre cliente</label>
                    <form:input path="nombreCliente" placeholder="Nombre cliente" 
                                pattern="^([a-zA-Z ñáéíóúÑÁÉÍÓÚ.,]{2,50})$" title="Solo se aceptan letras"
                                required="required"/>
                </div>
                <div class="large-4 columns">
                    <label>Tipo de persona</label>
                    <form:select path="tipoPersona">
                        <form:option value="" label="Seleccione"/>
                        <form:option value="1" label="Moral"/>
                        <form:option value="2" label="Fisica"/>
                    </form:select>
                </div>
            </div>
            <div class="row">
                <div class="large-4 columns">
                    <label>Grupo</label>
                    <form:input path="grupo" placeholder="Grupo" required="required"/>
                </div>
                <div class="large-4 columns">
                    <label>Domicilio fiscal</label>
                    <form:textarea path="domicilioFiscal" placeholder="Domicilio fiscal" 
                                   style="width='100%'; max-width: 100%; min-width: 100%; heigth=''"
                                   rows="2" required="required"></form:textarea>
                </div>
                <div class="large-4 columns">
                    <label>RFC</label>
                    <form:input path="rfc" placeholder="RFC"
                                title="Agrega un RFC valido con Homo-clave"
                                required="required"/>
                </div>
            </div>
            <div class="row">
                <div class="large-4 columns">
                    <label>Teléfono</label>
                    <form:input path="telefono" placeholder="Teléfono" pattern="^[0-9]+$"
                                title="Debes introducir un numero de telefono correcto ejemplo 5545362738" 
                                required="required"/>
                </div>
                <div class="large-4 columns">
                    <label>E-mail asesor</label>
                    <form:input type="email" path="correo" placeholder="E-mail" required="required"/>
                </div>
                <div class="large-4 columns">
                    <label>Tipo de honorario</label>
                    <form:select path="tipoHonorario" required="required">
                        <form:option value="" label="Selecciona un tipo"/>
                        <form:option value="RIESGOS" label="RIESGOS"/>
                        <form:option value="PATRIMONIO" label="PATRIMONIO"/>
                    </form:select>
                </div>
            </div>
            <div class="row">
                <div class="large-4 columns">
                    <label>Porcentaje de honorarios</label>
                    <form:input pattern="^([1-9]+|-)?([1-9]+|,)*\.?[0-9]+" title="Solo números con digitos mayores a 0.00" path="honorarioSinIva" value="8.62" placeholder="Honorario sin iva" required="required"/>
                </div>
                <div class="large-4 columns">
                    <label>Oficinas</label>
                    <form:input path="oficinas" placeholder="Oficinas" 
                                pattern="^([a-zA-Z ñáéíóúÑÁÉÍÓÚ.,]{2,30})$" title="Solo se aceptan letras"
                                required="required"/>
                </div>
                <div class="large-4 columns">
                    <label>ID Codes</label>
                    <form:input path="idCodes" placeholder="ID Codes" pattern="[1-9]+" 
                                required="required" title="Solo valores mayores a 0"/>
                </div>
                <form:hidden path="saldo" placeholder="Saldo" />
                <form:hidden path="fechaCaptura" value="${fechaCaptura}"/>
                <form:hidden path="status"/>
            </div>
            <div class="row">
                <div class="large-4 columns">
                    <label>Código postal</label>
                    <form:input path="codPos" placeholder="Código postal" pattern="[0-9]{5}" maxlength="5" title="Agrega un codigo postal valido" required="required"/>
                </div>
                <div class="large-4 columns">
                    <label>Entidad federativa</label>
                    <form:select path="entFed" disabled="true">
                        <form:option value="" label="Seleccione"/>
                        <form:option value="AGU" label="Aguascalientes"/>
                        <form:option value="BCN" label="Baja California Norte"/>
                        <form:option value="BCS" label="Baja California Sur"/>
                        <form:option value="CAM" label="Campeche"/>
                        <form:option value="CHP" label="Chiapas"/>
                        <form:option value="CHH" label="Chihuahua"/>
                        <form:option value="COA" label="Coahuila"/>
                        <form:option value="COL" label="Colima"/>
                        <form:option value="DIF" label="Ciudad de México"/>
                        <form:option value="DUR" label="Durango"/>
                        <form:option value="GUA" label="Guanajuato"/>
                        <form:option value="GRO" label="Guerrero"/>
                        <form:option value="HID" label="Hidalgo"/>
                        <form:option value="JAL" label="Jalisco"/>
                        <form:option value="MEX" label="Estado de Mexico"/>
                        <form:option value="MIC" label="Michoacán"/>
                        <form:option value="MOR" label="Morelos"/>
                        <form:option value="NAY" label="Nayarit"/>
                        <form:option value="NLE" label="Nuevo León"/>
                        <form:option value="OAX" label="Oaxaca"/>
                        <form:option value="PUE" label="Puebla"/>
                        <form:option value="QUE" label="Querétaro"/>
                        <form:option value="ROO" label="Quintana Roo"/>
                        <form:option value="SLP" label="San Luis Potosí"/>
                        <form:option value="SIN" label="Sinaloa"/>
                        <form:option value="SON" label="Sonora"/>
                        <form:option value="TAB" label="Tabasco"/>
                        <form:option value="TAM" label="Tamaulipas"/>
                        <form:option value="TLA" label="Tlaxcala"/>
                        <form:option value="VER" label="Veracruz"/>
                        <form:option value="YUC" label="Yucatán"/>
                        <form:option value="ZAC" label="Zacatecas"/>
                        <form:option value="AL" label="Alabama"/>
                        <form:option value="AK" label="Alaska"/>
                        <form:option value="AZ" label="Arizona"/>
                        <form:option value="AR" label="Arkansas"/>
                        <form:option value="CA" label="California"/>
                        <form:option value="NC" label="Carolina del Norte"/>
                        <form:option value="SC" label="Carolina del Sur"/>
                        <form:option value="CO" label="Colorado"/>
                        <form:option value="CT" label="Connecticut"/>
                        <form:option value="ND" label="Dakota del Norte"/>
                        <form:option value="SD" label="Dakota del Sur"/>
                        <form:option value="DE" label="Delaware"/>
                        <form:option value="FL" label="Florida"/>
                        <form:option value="GA" label="Georgia"/>
                        <form:option value="HI" label="Hawái"/>
                        <form:option value="ID" label="Idaho"/>
                        <form:option value="IL" label="Illinois"/>
                        <form:option value="IN" label="Indiana"/>
                        <form:option value="IA" label="Iowa"/>
                        <form:option value="KS" label="Kansas"/>
                        <form:option value="KY" label="Kentucky"/>
                        <form:option value="LA" label="Luisiana"/>
                        <form:option value="ME" label="Maine"/>
                        <form:option value="MD" label="Maryland"/>
                        <form:option value="MA" label="Massachusetts"/>
                        <form:option value="MI" label="Míchigan"/>
                        <form:option value="MN" label="Minnesota"/>
                        <form:option value="MS" label="Misisipi"/>
                        <form:option value="MO" label="Misuri"/>
                        <form:option value="MT" label="Montana"/>
                        <form:option value="NE" label="Nebraska"/>
                        <form:option value="NV" label="Nevada"/>
                        <form:option value="NJ" label="Nueva Jersey"/>
                        <form:option value="NY" label="Nueva York"/>
                        <form:option value="NH" label="Nuevo Hampshire"/>
                        <form:option value="NM" label="Nuevo México"/>
                        <form:option value="OH" label="Ohio"/>
                        <form:option value="OK" label="Oklahoma"/>
                        <form:option value="OR" label="Oregón"/>
                        <form:option value="PA" label="Pensilvania"/>
                        <form:option value="RI" label="Rhode Island"/>
                        <form:option value="TN" label="Tennessee"/>
                        <form:option value="TX" label="Texas"/>
                        <form:option value="UT" label="Utah"/>
                        <form:option value="VT" label="Vermont"/>
                        <form:option value="VA" label="Virginia"/>
                        <form:option value="WV" label="Virginia Occidental"/>
                        <form:option value="WA" label="Washington"/>
                        <form:option value="WI" label="Wisconsin"/>
                        <form:option value="WY" label="Wyoming"/>
                        <form:option value="ON" label="Ontario"/>
                        <form:option value="QC" label="Quebec"/>
                        <form:option value="NS" label="Nueva Escocia"/>
                        <form:option value="NB" label="Nuevo Brunswick"/>
                        <form:option value="MB" label="Manitoba"/>
                        <form:option value="BC" label="Columbia Británica"/>
                        <form:option value="PE" label="Isla del Príncipe Eduardo"/>
                        <form:option value="SK" label="Saskatchewan"/>
                        <form:option value="AB" label="Alberta"/>
                        <form:option value="NL" label="Terranova y Labrador"/>
                        <form:option value="NT" label="Territorios del Noroeste"/>
                        <form:option value="YT" label="Yukón"/>
                        <form:option value="UN" label="Nunavut"/>
                    </form:select>
                </div>
                <div class="large-4 columns">
                    <label>Cuenta origen</label>
                    <select name="cuentaOrigen" required>
                        <option value="">Selecciona una cuenta</option>
                        <c:forEach items="${cuentas}" var="i">
                            <option value="${i.cuentaOrigen}" <c:if test="${contrato.cuentaOrigen ne null}"> <c:if test="${contrato.cuentaOrigen eq i.cuentaOrigen}">selected</c:if> </c:if> >${i.cuentaOrigen}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="row">
                <div class="large-4 columns" id="ocultaCurp">
                    <label>CURP</label>
                    <form:input path="curp" placeholder="CURP" 
                                pattern="[A-Z][AEIOUX][A-Z]{2}[0-9]{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])[MH]([ABCMTZ]S|[BCJMOT]C|[CNPST]L|[GNQ]T|[GQS]R|C[MH]|[MY]N|[DH]G|NE|VZ|DF|SP)[BCDFGHJ-NP-TV-Z]{3}[0-9A-Z][0-9]" title="Solo se aceptan curp valida"
                                required="required"/>
                </div>
            </div>
            <div class="row centrar">
                <input type="submit" class="button botonSofom" value="Guardar">
            </div>
        </fieldset>
        </form:form>
    </div>
    
    <c:if test="${contrato.claveContrato ne null}">
        <div class="large-8 large-centered centered columns" id="socios">
            <fieldset class="fieldset">
                <legend><h4>Socios y Representantes legales</h4></legend>
                <form id="addSocio" method="post" action="/gestionfideicomisos/adm/socio/add">
                    <input type="hidden" name="claveContrato" value="${contrato.claveContrato}"/>
                    <div class="row">
                        <div class="large-4 columns">
                            <label>Tipo</label>
                            <select name="tipo" id="tipoSocio" required="required">
                                <option value="">Seleccione</option>
                                <option value="1">Representante legal</option>
                                <option value="2">Socio</option>
                            </select>
                        </div>
                        <div class="large-4 columns">
                            <label>Nombre</label>
                            <input type="text" name="nombre" id="nombreSocio" maxlength="50" placeholder="Nombre completo" required="required"/>
                        </div>
                        <div class="large-4 columns">
                            <label>RFC</label>
                            <input type="text" name="rfc" id="rfcSocio" maxlength="13" placeholder="RFC" pattern="[A-Z&Ñ]{4}[0-9]{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])[A-Z0-9]{2}[0-9A]" title="Agrega un RFC valido" required="required"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="large-4 columns">
                            <label>CURP</label>
                            <input type="text" name="curp" id="curpSocio" maxlength="18" placeholder="CURP" pattern="[A-Z][AEIOUX][A-Z]{2}[0-9]{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])[MH]([ABCMTZ]S|[BCJMOT]C|[CNPST]L|[GNQ]T|[GQS]R|C[MH]|[MY]N|[DH]G|NE|VZ|DF|SP)[BCDFGHJ-NP-TV-Z]{3}[0-9A-Z][0-9]" title="Agrega una CURP valida" required="required"/>
                        </div>
                        <div class="large-4 columns">
                            <label>Nacionalidad</label>
                            <select name="nacionalidad" id="nacionalidadSocio" required="required">
                                <option value="">Seleccione</option>
                                <option value="1">Mexicana</option>
                                <option value="2">Extranjera</option>
                            </select>
                        </div>
                        <div class="large-4 columns" id="ocultaporcentajeSocio">
                            <label>Porcentaje</label>
                            <input type="number" name="porcentaje" max="100.00" min="0.00" step="any" id="porcentajeSocio" placeholder="Porcentaje" required="required"/>
                        </div>
                    </div>
                    <div class="row centrar">
                        <input type="submit" class="button botonSofom" value="Guardar">
                    </div>
                </form>
                <br/>
                <div class="row">
                    <table>
                        <thead>
                            <tr>
                                <th>Tipo</th>
                                <th>Nombre</th>
                                <th>RFC</th>
                                <th>CURP</th>
                                <th>Nacionalidad</th>
                                <th>Porcentaje</th>
                                <th>Borrar</th>
                            </tr>
                        </thead>
                        <tbody id="sociostabla">
                            <c:forEach items="${socios}" var="i">
                                <tr>
                                    <c:if test="${i.tipo eq 1}">
                                        <td>Representante</td>
                                    </c:if>
                                    <c:if test="${i.tipo eq 2}">
                                        <td>Socio</td>
                                    </c:if>
                                    <td>${i.nombre}</td>
                                    <td>${i.rfc}</td>
                                    <td>${i.curp}</td>
                                    <c:if test="${i.nacionalidad eq 1}">
                                        <td>Mexicana</td>
                                    </c:if>
                                    <c:if test="${i.nacionalidad eq 2}">
                                        <td>Extranjera</td>
                                    </c:if>
                                    <c:if test="${i.porcentaje eq 0.0}">
                                        <td>-</td>
                                    </c:if>
                                    <c:if test="${i.porcentaje ne 0.0}">
                                        <td>${i.porcentaje} %</td>
                                    </c:if>
                                    <td style="text-align: center;"><a href="/gestionfideicomisos/adm/socio/${i.idRepre}/${contrato.claveContrato}/delete" style="color: red;"><span style='font-size: 2em;'>-</span></a></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </fieldset>
        </div>
    </c:if>
    
    <c:if test="${documentacion ne null}">
    <div class="large-8 large-centered centered columns">
        <form:form commandName="documentacion" method="post" action="/gestionfideicomisos/upload" enctype="multipart/form-data">
        <fieldset class="fieldset">
            <legend><h4>Documentación</h4></legend>
            <form:hidden path="idDocumentacion"/>
            <input type="hidden" name="seleccion" id="seleccion">
            <input type="file" name="archivo" accept=".pdf" id="archivo" style="display: none;">
            <div class="row">
                <div class="large-4 columns" style="text-align: center;">
                    <label>Cédula de Identificaciín Fiscal</label>
                    <a class="button clickSubir" val="1">Subir</a>
                    <c:if test="${documentacion.cedulaidFiscal}">
                        <button data-toggle="RFC-dropdown" id="RFCid-dropdown" url="/gestionfideicomisos/verifica/RFC/${documentacion.claveContrato}" class="button buscaArchivos">Descargar</button>
                        <div style="width: 143px;" class="dropdown-pane" id="RFC-dropdown" data-dropdown data-hover="true" data-hover-pane="true"></div>
                    </c:if>
                </div>
                <div class="large-4 columns" style="text-align: center;">
                    <label>Firma Electrónica Avanzada</label>
                    <a class="button clickSubir" val="2">Subir</a>
                    <c:if test="${documentacion.constFirmaElect}">
                        <button data-toggle="efirma-dropdown" id="efirmaid-dropdown" url="/gestionfideicomisos/verifica/efirma/${documentacion.claveContrato}" class="button buscaArchivos">Descargar</button>
                        <div style="width: 143px;" class="dropdown-pane" id="efirma-dropdown" data-dropdown data-hover="true" data-hover-pane="true"></div>
                    </c:if>
                </div>
                <div class="large-4 columns" style="text-align: center;">
                    <label>Comprobante de Domicilio</label>
                    <a class="button clickSubir" val="3">Subir</a>
                    <c:if test="${documentacion.compDomicilio}">
                        <button data-toggle="compDomicilio-dropdown" id="compDomicilioid-dropdown" url="/gestionfideicomisos/verifica/compDomicilio/${documentacion.claveContrato}" class="button buscaArchivos">Descargar</button>
                        <div style="width: 143px;" class="dropdown-pane" id="compDomicilio-dropdown" data-dropdown data-hover="true" data-hover-pane="true"></div>
                    </c:if>
                </div>
            </div>
            <div class="row">
                <div class="large-4 columns" style="text-align: center;">
                    <label>Identificación Oficial</label>
                    <a class="button clickSubir" val="4">Subir</a>
                    <c:if test="${documentacion.identifiOficial}">
                        <button data-toggle="ID-dropdown" id="IDid-dropdown" url="/gestionfideicomisos/verifica/ID/${documentacion.claveContrato}" class="button buscaArchivos">Descargar</button>
                        <div style="width: 143px;" class="dropdown-pane" id="ID-dropdown" data-dropdown data-hover="true" data-hover-pane="true"></div>
                    </c:if>
                </div>
                <div class="large-4 columns" style="text-align: center;">
                    <label>Clave Única del Registro de Población</label>
                    <a class="button clickSubir" val="5">Subir</a>
                    <c:if test="${documentacion.curp}">
                        <button data-toggle="CURP-dropdown" id="CURPid-dropdown" url="/gestionfideicomisos/verifica/CURP/${documentacion.claveContrato}" class="button buscaArchivos">Descargar</button>
                        <div style="width: 143px;" class="dropdown-pane" id="CURP-dropdown" data-dropdown data-hover="true" data-hover-pane="true"></div>
                    </c:if>
                </div>
                <div class="large-4 columns" style="text-align: center;">
                    <label>Prima de Riesgos de Trabajo (IMSS)</label>
                    <a class="button clickSubir" val="6">Subir</a>
                    <c:if test="${documentacion.primaReg}">
                        <button data-toggle="primaReg-dropdown" id="primaRegid-dropdown" url="/gestionfideicomisos/verifica/primaReg/${documentacion.claveContrato}" class="button buscaArchivos">Descargar</button>
                        <div style="width: 143px;" class="dropdown-pane" id="primaReg-dropdown" data-dropdown data-hover="true" data-hover-pane="true"></div>
                    </c:if>
                </div>
            </div>
            <div class="row">
                <div class="large-4 columns" style="text-align: center;">
                    <label>Escritura Constitutiva del Cliente</label>
                    <a class="button clickSubir" val="7">Subir</a>
                    <c:if test="${documentacion.escrituraContCliente}">
                        <button data-toggle="escrituraConstitutivaCliente-dropdown" id="escrituraConstitutivaClienteid-dropdown" url="/gestionfideicomisos/verifica/escrituraConstitutivaCliente/${documentacion.claveContrato}" class="button buscaArchivos">Descargar</button>
                        <div style="width: 143px;" class="dropdown-pane" id="escrituraConstitutivaCliente-dropdown" data-dropdown data-hover="true" data-hover-pane="true"></div>
                    </c:if>
                </div>
                <div class="large-4 columns" style="text-align: center;">
                    <label>Escritura Poderes de Dominio</label>
                    <a class="button clickSubir" val="8">Subir</a>
                    <c:if test="${documentacion.poderesDominio}">
                        <button data-toggle="poderesDominio-dropdown" id="poderesDominioid-dropdown" url="/gestionfideicomisos/verifica/poderesDominio/${documentacion.claveContrato}" class="button buscaArchivos">Descargar</button>
                        <div style="width: 143px;" class="dropdown-pane" id="poderesDominio-dropdown" data-dropdown data-hover="true" data-hover-pane="true"></div>
                    </c:if>
                </div>
                <div class="large-4 columns" style="text-align: center;">
                    <label>Registro Comisión Nacional Bancaria y de Valores y/o la Comisión Nacional</label>
                    <a class="button clickSubir" val="9">Subir</a>
                    <c:if test="${documentacion.registroCNBV}">
                        <button data-toggle="CNBV-dropdown" id="CNBVid-dropdown" url="/gestionfideicomisos/verifica/CNBV/${documentacion.claveContrato}" class="button buscaArchivos">Descargar</button>
                        <div style="width: 143px;" class="dropdown-pane" id="CNBV-dropdown" data-dropdown data-hover="true" data-hover-pane="true"></div>
                    </c:if>
                </div>
            </div>
            <div class="row">
                <div class="large-4 columns" style="text-align: center;">
                    <label>Contrato de fideicomiso</label>
                    <a class="button clickSubir" val="10">Subir</a>
                    <c:if test="${documentacion.contrato}">
                        <button data-toggle="contrato-dropdown" id="contratoid-dropdown" url="/gestionfideicomisos/verifica/Contrato/${documentacion.claveContrato}" class="button buscaArchivos">Descargar</button>
                        <div style="width: 143px;" class="dropdown-pane" id="contrato-dropdown" data-dropdown data-hover="true" data-hover-pane="true"></div>
                    </c:if>
                </div>
            </div>
        </fieldset>
        </form:form>
    </div>
    </c:if>
    
    <!-- Modal -->
    <div class="reveal" id="modal" data-reveal style="text-align: center;">
        <h1 id="modalTitulo">Awesome!</h1>
        <p class="lead" id="modalContenido">I have another modal inside of me!</p>
        <a class="button " id="modalBoton" data-close>Aceptar</a>
        <button class="close-button" data-close aria-label="Close reveal" type="button">
          <span aria-hidden="true">&times;</span>
        </button>
    </div>
    
    <div id="moverAbajo"></div>
    <script src="/gestionfideicomisos/resources/js/jquery.js"></script>
    <script src="/gestionfideicomisos/resources/js/Foundation/foundation.js"></script>
    <script src="/gestionfideicomisos/resources/js/Foundation/app.js"></script>
    <script src="/gestionfideicomisos/resources/js/Main.js"></script>
    <script src="/gestionfideicomisos/resources/js/Admin/index.js"></script>
    <script>
        $(function(){
            var m = new Main();
            $('#claveContrato').keyup(function(){
                var clave = $(this).val();
                $('form input[type=submit]').fadeIn();
                if (clave.length === 13) {
                    $.post('/gestionfideicomisos/adm/buscaClave', {clave:clave}, function(data){
                        if (data === 'si') {
                             m.creaModal('modal', 'warning', '¡Atención!', 'Ya existe el contrato que intentas ingresar, favor de cambiarlo para poder continuar');
                             $('form input[type=submit]').fadeOut();
                        }
                    });
                }
            });
            
            if ($('#contrato').attr('action') === '/gestionfideicomisos/adm/contrato/save') {
                $('#tipoHonorario').val('PATRIMONIO');
            }
            
            $('#cancelapld').click(function(){
                window.location = "/gestionfideicomisos/adm/cancela/cliente/pld?contrato="+$('#claveContrato').val()+"&nombreEmp="+$('#nombreCliente').val();
            });
            
            var ajaxPLD = function(nombreCliente, tipo){
                $('#verificaPersona').attr('var', tipo);
                $.get('https://api.trade.gov/consolidated_screening_list/search?api_key=0b4lqNGmnWmC1MgPYrkCHltx&q='+nombreCliente, {}, function(data){
                    if (data.results.length >= 1) {
                        $('#pld').fadeIn('slow');
                        $('#validacion').fadeIn('slow');
                        $('#socios').fadeOut('slow');
                        $('#status').val('A');
                        $('#divFormValido').fadeOut('slow');
                        if (tipo === 'socio') {
                            $('#nombreEnviar').val($('#nombreSocio').val());
                        } else if (tipo === 'cliente') {
                            $('#nombreEnviar').val($('#nombreCliente').val());
                        }
                        $('#contratoEnviar').val($('#claveContrato').val());
                        $('#pldCampo').val('true');
                        var contenido = "";
                        for (var i = 0; i < data.results.length; i++) {
                            var encontrado = data.results[i];
                            contenido += "<ul class='vertical menu'>";
                                contenido += "<li><a style='color: red; padding-bottom:1%;' class='muestrainfo' var='"+i+"'>* "+encontrado.name+"</a></li>";
                                
                                contenido += "<li class='info"+i+"' style='display:none;'>";
                                    contenido += "<a style='padding-left:6%; color: #3c97f5;' class='muestrainfo'>Nombres alternos: "+encontrado.alt_names.length+" encontrados</a>";
                                    for (var j = 0; j < encontrado.alt_names.length; j++) {
                                        var altEncuentra = encontrado.alt_names[j];
                                        contenido += "<ul class='vertical menu'><li><a style='color:black; padding-left:8%'>"+altEncuentra+"</a></li></ul>";
                                    }
                                contenido += "</li>";
                                
                                contenido += "<li class='info"+i+"' style='display:none;'>";
                                    contenido += "<a style='padding-left:6%; color: #3c97f5;' class='muestrainfo'>Programas a los que pertenece: "+encontrado.programs.length+" encontrados</a>";
                                    for (var j = 0; j < encontrado.programs.length; j++) {
                                        var programsEncuentra = encontrado.programs[j];
                                        contenido += "<ul class='vertical menu'><li><a style='color:black; padding-left:8%'>"+programsEncuentra+"</a></li></ul>";
                                    }
                                contenido += "</li>";
                                
                                contenido += "<li class='info"+i+"' style='display:none;'>";
                                    contenido += "<a style='padding-left:6%; padding-bottom:2%; color: black;' class='muestrainfo'><span style='color: #3c97f5;'>Fuente: </span>"+encontrado.source+"</a>";
                                contenido += "</li>";
                                
                            contenido += "</ul>";
                        }
                        $("#encontradoPLD").html(contenido);
                        
                        $('.muestrainfo').click(function(){
                            var valor = $(this).attr('var');
                            $('.info'+valor).toggle();
                        });
                        
                    } else {
                        $('#pld').fadeOut('slow');
                        $('#validacion').fadeOut('slow');
                        $('#socios').fadeIn('slow');
                        $('#divFormValido').fadeIn('slow');
                    }
                });
            };
            
            $('#nombreSocio').change(function (){
                var nombreCliente = $(this).val();
                ajaxPLD(nombreCliente, "socio");
            });
            
            $('#nombreCliente').change(function (){
                var nombreCliente = $(this).val();
                ajaxPLD(nombreCliente, "cliente");
            });
            
            $('#validaUsuario').click(function (){
                var url = "";
                if ($('#verificaPersona').attr('var') === 'socio') {
                    url = "/gestionfideicomisos/adm/valida/usuario/socios";
                } else if ($('#verificaPersona').attr('var') === 'cliente') {
                    url = "/gestionfideicomisos/adm/valida/usuario";
                }
                $.post(url, $('#verificaPersona').serialize(), function (data){
                    if (data === 'ok'){
                        $('#pld').fadeOut('slow');
                        $('#validacion').fadeOut('slow');
                        $('#socios').fadeIn('slow');
                        $('#divFormValido').fadeIn('slow');
                        $('#verificaPersona')[0].reset();
                    } else {
                        m.creaModal('modal', 'warning', '¡Atención!', 'Datos incorrectos');
                    }
                });
            });
            
            $('#tipoSocio').change(function(){
                if ($(this).val() === '1') {
                    $('#porcentajeSocio').removeAttr('required');
                    $('#porcentajeSocio').val('');
                    $('#ocultaporcentajeSocio').fadeOut('slow');
                } else {
                     $('#porcentajeSocio').attr('required', 'required');
                     $('#ocultaporcentajeSocio').fadeIn('slow');
                }
            });
            
            $('#addSocio').submit(function (e){
                e.preventDefault();
                $.post('/gestionfideicomisos/adm/socio/add', $('#addSocio').serialize(), function(data) {
                    if (data !== 'error') {
                        window.location = "/gestionfideicomisos/adm/contrato/"+$('#claveContrato').val()+"/edit?update=ok";
                    } else {
                        m.creaModal('modal', 'warning', '¡Atención!', 'Error al guardar información');
                    }
                });
            });
            
            <c:if test="${contrato.claveContrato ne null}">
                function representantesMethod(){
                    if ($('#sociostabla tr').length === 0) {
                        m.creaModal('modal', 'warning', '¡Atención!', 'Se deben agregar representante legal y socios');
                    } else {
                        var totalPorcentaje = 0.0;
                        var encuentraRepre = false;
                        var encuentraSocio = false;
                        for (var i = 1; i <= $('#sociostabla tr').length; i++) {
                            if ($('#sociostabla tr:nth-child('+i+') td:nth-child(1)').text() === 'Representante') {
                                encuentraRepre = true;
                            }
                            if ($('#sociostabla tr:nth-child('+i+') td:nth-child(1)').text() === 'Socio') {
                                encuentraSocio = true;
                                totalPorcentaje += parseFloat($('#sociostabla tr:nth-child('+i+') td:nth-child(6)').text().replace(' %',''));
                            }
                        }
                        totalPorcentaje = parseFloat(totalPorcentaje.toFixed(2));
                        if (encuentraSocio) {
                            if (totalPorcentaje >= 99.9 && totalPorcentaje <= 100.00) {
                            } else {
                                m.creaModal('modal', 'warning', '¡Atención!', 'El porcentaje de los socios debe coincidir con el 99.9 % o el 100.0%');
                            }
                        } else {
                            m.creaModal('modal', 'warning', '¡Atención!', 'Se deben agregar los socios');
                        }
                        if (!encuentraRepre) {
                            m.creaModal('modal', 'warning', '¡Atención!', 'Se deben agregar el representante legal');
                        }
                    }
                }
                setTimeout(function(){representantesMethod();}, 3000);
            </c:if>
            
            var enviaForm = function() {
                $('#contrato').submit(function(e){
                    $('#contrato input[name=claveContrato]').removeAttr('disabled');
                    $('#entFed').removeAttr('disabled');
                    if ($('#entFed').val() === '') {
                        e.preventDefault();
                        m.creaModal('modal', 'warning', '¡Atención!', 'Debes escribir el código postal para que se seleccione una entidad federativa');
                        $('#entFed').attr('disabled', 'disabled');
                        if ($('#contrato').attr('action') === '/gestionfideicomisos/adm/contrato/update') {
                            $('#contrato input[name=claveContrato]').attr('disabled', 'disabled');
                        }
                    } else {
                        if ($('#contrato').attr('action') === '/gestionfideicomisos/adm/contrato/save') {
                            e.preventDefault();
                            $.post('/gestionfideicomisos/adm/buscaClave', {clave:$('#claveContrato').val()}, function(data){
                                if (data === 'si') {
                                    m.creaModal('modal', 'warning', '¡Atención!', 'Ya existe el contrato que intentas ingresar, favor de cambiarlo para poder continuar');
                                    $('#entFed').attr('disabled', 'disabled');
                                } else {
                                    $('#contrato').unbind().submit();
                                }
                            });
                        }
                    }
                });
            };
            
            enviaForm();
            
            if ($('#tipoPersona').val()==='1') {
                $('#curp').removeAttr('required');
                $('#ocultaCurp').fadeOut('fast');
                $('#rfc').removeAttr('rfc');
                $('#rfc').attr('pattern', '[A-Z&Ñ]{3}[0-9]{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])[A-Z0-9]{2}[0-9A]');
            }
            
            $('#tipoPersona').change(function(){
                if ($(this).val() === '1') {
                    $('#curp').removeAttr('required');
                    $('#ocultaCurp').fadeOut('fast');
                    $('#rfc').removeAttr('rfc');
                    $('#rfc').attr('pattern', '[A-Z&Ñ]{3}[0-9]{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])[A-Z0-9]{2}[0-9A]');
                } else {
                    $('#curp').attr('required', 'required');
                    $('#ocultaCurp').fadeIn('slow');
                    $('#rfc').removeAttr('rfc');
                    $('#rfc').attr('pattern', '[A-Z&Ñ]{4}[0-9]{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])[A-Z0-9]{2}[0-9A]');
                }
            });
            
            if ($('#pld').val() === '') {
                $('#pld').val('false');
            }
            
            $('.clickSubir').click(function(){
                $('#seleccion').val($(this).attr('val'));
                $('#archivo').trigger('click');
            });
            $('#archivo').change(function(){
                if ($('#archivo').val() !== '') {
                    $('#documentacion').trigger('submit');
                }
            });
            $('#codPos').keyup(function(){
                var codPost = $(this).val();
                if (codPost.length === 5) {
                    $.post('/gestionfideicomisos/adm/codigo/postal', {cp:codPost}, function(data){
                        $('#entFed').val('');
                        if (data !== 'error') {
                            $('#entFed').attr('disabled','disabled');
                            switch(data) {
                                case 'Aguascalientes':
                                    $('#entFed').val('AGU');
                                    break;
                                case 'Baja California':
                                    $('#entFed').val('BCN');
                                    break;
                                case 'Baja California Sur':
                                    $('#entFed').val('BCS');
                                    break;
                                case 'Campeche':
                                    $('#entFed').val('CAM');
                                    break;
                                case 'Chiapas':
                                    $('#entFed').val('CHP');
                                    break;
                                case 'Chihuahua':
                                    $('#entFed').val('CHH');
                                    break;
                                case 'Coahuila de Zaragoza':
                                    $('#entFed').val('COA');
                                    break;
                                case 'Colima':
                                    $('#entFed').val('COL');
                                    break;
                                case 'Ciudad de México':
                                    $('#entFed').val('DIF');
                                    break;
                                case 'Durango':
                                    $('#entFed').val('DUR');
                                    break;
                                case 'Guanajuato':
                                    $('#entFed').val('GUA');
                                    break;
                                case 'Guerrero':
                                    $('#entFed').val('GRO');
                                    break;
                                case 'Hidalgo':
                                    $('#entFed').val('HID');
                                    break;
                                case 'Jalisco':
                                    $('#entFed').val('JAL');
                                    break;
                                case 'México':
                                    $('#entFed').val('MEX');
                                    break;
                                case 'Michoacán de Ocampo':
                                    $('#entFed').val('MIC');
                                    break;
                                case 'Morelos':
                                    $('#entFed').val('MOR');
                                    break;
                                case 'Nayarit':
                                    $('#entFed').val('NAY');
                                    break;
                                case 'Nuevo León':
                                    $('#entFed').val('NLE');
                                    break;
                                case 'Oaxaca':
                                    $('#entFed').val('OAX');
                                    break;
                                case 'Puebla':
                                    $('#entFed').val('PUE');
                                    break;
                                case 'Querétaro':
                                    $('#entFed').val('QUE');
                                    break;
                                case 'Quintana Roo':
                                    $('#entFed').val('ROO');
                                    break;
                                case 'San Luis Potosí':
                                    $('#entFed').val('SLP');
                                    break;
                                case 'Sinaloa':
                                    $('#entFed').val('SIN');
                                    break;
                                case 'Sonora':
                                    $('#entFed').val('SON');
                                    break;
                                case 'Tabasco':
                                    $('#entFed').val('TAB');
                                    break;
                                case 'Tamaulipas':
                                    $('#entFed').val('TAM');
                                    break;
                                case 'Tlaxcala':
                                    $('#entFed').val('TLA');
                                    break;
                                case 'Veracruz de Ignacio de la Llave':
                                    $('#entFed').val('VER');
                                    break;
                                case 'Yucatán':
                                    $('#entFed').val('YUC');
                                    break;
                                case 'Zacatecas':
                                    $('#entFed').val('ZAC');
                                    break;
                            }
                        }
                    });
                } else if (codPost.length === 0) {
                    $('#entFed').attr('disabled','disabled');
                }
            });
            
            var buscaArchivos = function(url, id){
                $.post(url, {}, function(data){
                    var datos = JSON.parse(data);
                    var html = "";
                    for (i=0;i<datos.length;i++) {
                        var fecha = datos[i].slice(datos[i].replace('.pdf','').length-8,datos[i].replace('.pdf','').length);
                        var dia = fecha.slice(6,8);
                        var mes = fecha.slice(4,6);
                        var anio = fecha.slice(0,4);
                        html += "<a style='color: #000000;' href='/gestionfideicomisos/download/"+id+"/"+datos[i]+"/"+$('#claveContrato').val()+"'>"+dia + " - " + mes + " - " + anio +"</a>";
                        html += "<br>";
                    }
                    $('#'+id+'-dropdown').html(html);
                });
            };
            
            $('.buscaArchivos').click(function(e){
                e.preventDefault();
            });
            $('.buscaArchivos').hover(function(){
                var url = $(this).attr('url');
                var id = $(this).attr('id').replace('id-dropdown','');
                buscaArchivos(url, id);
            });
            
            <c:if test="${depa eq 'Cumplimiento'}">
                $('#contrato input').attr('disabled','disabled');
                $('#addSocio input').attr('disabled','disabled');
                $('textarea').attr('disabled','disabled');
                $('select').attr('disabled','disabled');
                $('#nuevaContra').fadeOut('fast');
                $('#contrato input[type=submit]').fadeOut('fast');
                $('#addSocio input[type=submit]').fadeOut('fast');
            </c:if>
            
        });
    </script>
</body>
</html>