<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Garante</title>
    <link rel="icon" type="image/png" href="/resources/img/icono.png" />
    <link rel="stylesheet" href="/resources/css/Foundation/foundation.min.css">
    <link rel="stylesheet" href="/resources/css/Foundation/foundation-icons/foundation-icons.css">
    <link rel="stylesheet" href="/resources/css/Foundation/datePicker/foundation-datepicker.min.css">
    <link rel="stylesheet" href="/resources/css/Cliente/Manual.css">
</head>
<body>
    <jsp:include page="/WEB-INF/views/Layouts/menu.jsp"></jsp:include>
    
    <div class="large-8 large-centered centered columns">
        <form:form commandName="movimiento" action="/cte/liquidacion/guarda" method="post">
            <fieldset class="fieldset">
                <legend><h4>Liquidaci�n</h4></legend>
                <form:hidden path="claveContrato"/>
                <div class="row">
                    <div class="large-4 columns">
                        <label>Tipo de Movimiento</label>
                        <form:select path="tipoMovimiento" id="tipoMovimiento" required="required">
                            <form:option value="" label="Seleccione"/>
                            <form:option value="1" label="A cuentas Bancomer"/>
                            <form:option value="2" label="A otros Bancos"/>
                            <form:option value="3" label="A tarjetas de d�bito Banamex"/>
                            <form:option value="4" label="Emisi�n de Cheques"/>
                            <form:option value="5" label="A bancos Extranjeros"/>
                        </form:select>
                        <div class="error">Valor invalido</div>
                    </div>
                    <div class="large-4 columns">
                       <label>Fecha de Liquidaci�n</label>
                       <div class="input-group">
                           <form:input path="fechaLiquidacion" cssClass="input-group-field" id="dp1" required="required"/>
                            <span class="input-group-label" id="fechaActual" data-tooltip aria-haspopup="true" class="has-tip" title="Poner fecha actual"><i class="fi-calendar"></i></span>
                       </div>
                       <div class="error" id="errordp1">Fecha invalida</div>
                    </div>
                    <div class="large-4 columns">
                        <label>Clave de Banco</label>
                        <select name="claveBanco" required>
                            <option value="" >Seleccione</option>
                            <c:forEach items="${bancos}" var="i">
                                <option  value="${i.clave}">${i.banco}</option>
                            </c:forEach>
                        </select>
                        <div class="error">Valor invalido</div>
                    </div>
                </div>
                
                <div class="row">
                    <div class="large-4 columns">
                        <label>Clave de Moneda</label>
                        <select name="tipoMoneda" required>
                            <option value="">Seleccione</option>
                            <c:forEach items="${monedas}" var="i">
                                <option  value="${i.clave}">${i.clave} - ${i.pais}</option>
                            </c:forEach>
                        </select>
                        <div class="error">Valor invalido</div>
                    </div>
                    <div class="large-4 columns">
                        <label>Nombres de Fideicomisario</label>
                        <form:input path="nombreEmpleado" placeholder="Nombres"
                                    pattern="^([a-zA-Z ]{2,30})$" title="No se aceptan caracteres especiales (�, �, *, #, $, %, =, +, �, �, �, �, �, etc.)"
                                    required="required"/>
                        <div class="error">Valor invalido</div>
                    </div>
                    <div class="large-4 columns">
                        <label>Apellido Paterno</label>
                        <form:input path="apellidoPEmpleado"
                                    pattern="^([a-zA-Z ]{2,30})$" title="No se aceptan caracteres especiales (�, �, *, #, $, %, =, +, �, �, �, �, �, etc.)"
                                    placeholder="Apellido paterno" required="required"/>
                        <div class="error">Valor invalido</div>
                    </div>
                </div>
                
                <div class="row">
                    <div class="large-4 columns">
                        <label>Apellido Materno</label>
                        <form:input path="apellidoMEmpleado"
                                    pattern="^([a-zA-Z ]{2,30})$" title="No se aceptan caracteres especiales (�, �, *, #, $, %, =, +, �, �, �, �, �, etc.)"
                                    placeholder="Apellido materno" required="required"/>
                        <div class="error">Valor invalido</div>
                    </div>
                    <div class="large-4 columns">
                        <label>Fecha de Ingreso</label>
                        <div class="input-group">
                            <form:input path="fechaIngreso" cssClass="input-group-field"  id="dp2" required="required"/>
                            <span class="input-group-label" id="fechaActual2" data-tooltip aria-haspopup="true" class="has-tip" title="Poner fecha actual"><i class="fi-calendar"></i></span>
                       </div>
                       <div class="error" id="errordp2">Fecha invalida</div>
                    </div>
                    <div class="large-4 columns">
                        <label>Puesto</label>
                        <form:input path="puestoEmpleado" placeholder="Puesto" required="required"/>
                        <div class="error">Valor invalido</div>
                    </div>
                </div>
                
                <div class="row">
                    <div class="large-4 columns">
                        <label>Departamento</label>
                        <form:input path="deptoEmpleado" placeholder="Departamento" required="required"/>
                        <div class="error">Valor invalido</div>
                    </div>
                    <div class="large-4 columns">
                        <label>ID del Fideicomisario</label>
                        <form:input path="claveEmpleado" placeholder="Clave o n�mero de control de n�mina" required="required"/>
                        <div class="error">Valor invalido</div>
                    </div>
                    <div class="large-4 columns">
                        <label>CURP</label>
                        <form:input path="curp" placeholder="CURP" title="Agrega una CURP valida"
                                    pattern="^[A-Z][AEIOUX][A-Z]{2}[0-9]{2}[0-1][0-9][0-3][0-9][MH][A-Z][BCDEFGHJKLMN�PQRSTVWXYZ]{4}[0-9A-Z][0-9]" required="required"/>
                        <div class="error">Valor invalido</div>
                    </div>
                </div>
                
                <div class="row">
                    <div class="large-6 columns">
                        <label>Cuenta de Dep�sito</label>
                        <form:input path="cuentaDeposito" placeholder="Cuenta de Dep�sito" required="required"/>
                        <div class="error">Valor invalido</div>
                    </div>
                    <div class="large-6 columns">
                        <label>Importe de Liquidaci�n</label>
                        <form:input type="number" path="importeLiquidacion" placeholder="Importe de Liquidaci�n" step=".01" min="0.00" required="required"/>
                        <div class="error">Valor invalido</div>
                    </div>
                </div>
                
                <hr/>
                
                <div class="row">
                    <div class="large-4 columns">
                        <label>Env�o Cheque</label>
                        <form:input path="envioCheque" placeholder="Env�o Cheque" required="required"/>
                        <div class="error">Valor invalido</div>
                    </div>
                    <div class="large-4 columns">
                        <label>Destino Cheque</label>
                        <form:input path="destinoEnvioCheque" placeholder="Destino Cheque" required="required"/>
                        <div class="error">Valor invalido</div>
                    </div>
                    <div class="large-4 columns">
                        <label>Tel�fono Cheque</label>
                        <form:input path="telEnvioCheque" placeholder="Tel�fono Cheque" required="required"/>
                        <div class="error">Valor invalido</div>
                    </div>
                </div>
                
                <div class="row">
                    <div class="large-6 columns">
                        <label>Correo Cheque</label>
                        <form:input path="correoEnvioCheque" placeholder="Env�o Cheque" required="required"/>
                        <div class="error">Valor invalido</div>
                    </div>
                    <div class="large-6 columns">
                        <label>Banco Extranjero</label>
                        <form:input path="bancoExtranjero" placeholder="Banco extranjero" required="required"/>
                        <div class="error">Valor invalido</div>
                    </div>
                </div>
                
                <div class="row">
                    <div class="large-6 columns">
                        <label>Domicilio Banco Extranjero</label>
                        <form:textarea path="domBancoExtranjero" placeholder="Domicilio Banco Extranjero" required="required"/>
                        <div class="error">Valor invalido</div>
                    </div>
                    <div class="large-6 columns">
                        <label>Pa�s Banco Extranjero</label>
                        <form:input path="paisBancoExtranjero" placeholder="Pa�s Banco Extranjero" required="required"/>
                        <div class="error">Valor invalido</div>
                    </div>
                </div>
                
                <div class="row">
                    <div class="large-6 columns">
                        <label>ABA/BIC</label>
                        <form:input path="abaBic" placeholder="ABA/BIC" required="required"/>
                        <div class="error">Valor invalido</div>
                    </div>
                    <div class="large-6 columns">
                        <label>Nombre en Banco Extranjero</label>
                        <form:input path="nombreFideiBancoExt" placeholder="Nombre en Banco Extranjero" required="required"/>
                        <div class="error">Valor invalido</div>
                    </div>
                </div>
                
                <div class="row">
                    <div class="large-6 columns">
                        <label>Direcci�n en Extranjero</label>
                        <form:textarea path="direccionFideiExt" placeholder="Direcci�n en Extranjero" required="required"/>
                        <div class="error">Valor invalido</div>
                    </div>
                    <div class="large-6 columns">
                        <label>Pa�s Fideicomisario</label>
                        <form:input path="paisFideiExt" placeholder="Pa�s Fideicomisario" required="required"/>
                        <div class="error">Valor invalido</div>
                    </div>
                </div>
                
                <div class="row">
                    <div class="large-6 columns">
                        <label>Tel�fono Fideicomisario</label>
                        <form:input path="telFideiExt" placeholder="Tel�fono Fideicomisario" required="required"/>
                        <div class="error">Valor invalido</div>
                    </div>
                    <div class="large-6 columns">
                        <label style="color: white;">lol</label>
                        <input class="button" type="submit" value="Guardar">
                    </div>
                </div>
                
            </fieldset>
        </form:form>
    </div>
    
    <script src="/gestionfideicomisos/resources/js/jquery.js"></script>
    <script src="/gestionfideicomisos/resources/js/Foundation/foundation.js"></script>
    <script src="/gestionfideicomisos/resources/js/Foundation/datePicker/foundation-datepicker.min.js"></script>
    <script src="/gestionfideicomisos/resources/js/Cliente/Manual.js" charset="utf-8"></script>
</body>
</html>