<!--
     Creado por:                   Luis Antio Valerio Gayosso
     Fecha:                        23/11/2011, 12:55:31 PM
     Descripción:                  Vista : "menuCaptureMovs.jsp" vista para cargar Movimientos.
     Responsable:                  Carlos Altamirano
-->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<c:choose>
    <c:when test="${sessionScope.userApp != null}">
        <html>
            <head>
                <link rel="shortcut icon" href="images/icono.png">
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <title> Liquidaciones </title>
                <!-- Aqui importamos los estilos que necesitemos-->
                <link rel="stylesheet" type="text/css" href="css/formato.css">
                <script language="javascript" type="text/javascript" src="scripts/dataCapture.js"></script>
                <script language="javascript" type="text/javascript" src="scripts/popCalendar.js"></script>
                <!--vamos a utilizar otra forma para importar codigo en javascript-->
                <%--<%@include file="scripts/popCalendar.js" %>--%>
            </head>

            <body
                <c:if test="${sessionScope.messageBean != null && sessionScope.messageBean.desc != ''}">
                    onLoad="alert('<c:out value="${sessionScope.messageBean.desc}"/>');"
                    <c:remove var="messageBean" scope="session" />
                </c:if>
                <c:if test="${sessionScope.messageClean != null}">
                    onLoad="return limpiaSesion('eliminaBeans:100', 'fecha_liquidacion;confirmaUsuario;imprimeResumen;messageClean;resumenMovs', 'menuLoadFile.htm');"
                    <c:remove var="messageClean" scope="session" />
                </c:if>
                class="letraDefault"
                >

                <form name="captureData" method="post" action="ControllerCapture">
                    <input type="hidden" name="accion" value=""/>
                    <input type="hidden" name="nombresObjetos" value=""/>
                    <input type="hidden" name="urlResponse" value=""/>
                    <input type="hidden" name="fecha_liquidacion" value=""/>
                    <input type="hidden" name="tipo_movimiento" value="<c:out value='${sessionScope.tipo_movimiento}'/>"/>
                    <input type="hidden" name="clave_banco" value="<c:out value='${sessionScope.clave_banco}'/>"/>
                    <input type="hidden" name="clave_moneda" value="<c:out value='${sessionScope.clave_moneda}'/>"/>

                    <table align ="center" width="60%" >
                        <tr>
                            <td align="center" width="60%" >&nbsp;</td>
                            <c:if test="${ sessionScope.resumenMovs == null && sessionScope.imprimeResumen == null }">
                                <td align="right">
                                    <b><a   href="" onclick="return atras(captureData, 'eliminaBeans:100', 'fecha_liquidacion;tipo_movimiento;clave_banco;clave_moneda;nombreFideicomisario;apellidoPaterno;apellidoMaterno;fechaIngreso;puesto;departamento;claveFideicomisario;CURP;RFC;cuentaDeposito;importe_liquidacion;envioCheque;destinoCheque;telefonoCheque;correoCheque;bancoExtranjero;dirBancoExtranjero;paisBancoExtranjero;ABA_BIC;nombreFideiBancoExtranjero;dirFideiBancoExtranjero;paisFideiBancoExtranjero;telFideiBancoExtranjero;resumenMovs;imprimeResumen;messageClean', 'menuLoadFile.htm');" target="main"><u>regresar</u></a></b>
                                </td>
                            </c:if>

                        </tr>
                    </table>

                    <c:if test="${ sessionScope.imprimeResumen==null }">
                        <table align="center" >
                            <tr>
                                <td align="center" >&nbsp;</td>
                            </tr>
                            <tr>
                                <td>
                                    <table  align="center"  class="form-noindentCapture"  >

                                        <tr>
                                            <td align="center" >&nbsp;</td>
                                            <td colspan="3">
                                                <table>
                                                    <tr class="Subtitulo">
                                                        <td> Clave de Contrato </td>
                                                        <td>Tipo de Movimiento</td>
                                                        <td>Fecha de Liquidación</td>
                                                    </tr>

                                                    <tr>
                                                        <td align="left">
                                                            <input class="LetraInput" style="width:150px;" type="text" name="claveContrato" readonly="readonly" value="<c:out value='${sessionScope.userApp.clave_contrato}'/>" />
                                                        </td>
                                                        <% int i = 0;%>

                                                        <td align="left">
                                                            <SELECT NAME="selComboMovs" onchange="return setActionComboMovs(captureData, 'seleccionaTipoMov:1');">
                                                                <c:forEach items="${sessionScope.tiposMovimiento}" var="mov" varStatus="status">
                                                                    <c:choose>
                                                                        <c:when test="${sessionScope.tipo_movimiento != null }">
                                                                            <c:choose>
                                                                                <c:when test="${sessionScope.tipo_movimiento == mov }">
                                                                                    <OPTION selected VALUE="mov<%=i%>"><c:out value="${mov}"/></OPTION>
                                                                                    </c:when>
                                                                                    <c:otherwise>
                                                                                    <OPTION VALUE="mov<%=i%>"><c:out value="${mov}"/></OPTION>
                                                                                    </c:otherwise>
                                                                                </c:choose>
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                            <OPTION VALUE="mov<%=i%>"><c:out value="${mov}"/></OPTION>
                                                                            </c:otherwise>
                                                                        </c:choose>
                                                                        <% i++;%>
                                                                    </c:forEach>
                                                            </SELECT>
                                                        </td>

                                                        <td  align="center">
                                                            <input name="fechaLiquidacion" type="text"  id="dateArrival"  value="<c:out value='${sessionScope.fecha_liquidacion}'/>" onClick="return popUpCalendar(this, captureData.dateArrival, 'dd/mm/yyyy');" size="10">
                                                        </td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>

                                        <% int k = 0;%>
                                        <% int j = 0;%>
                                        <tr class="Subtitulo">
                                            <td align="center" >&nbsp;</td>
                                            <td  colspan="3" >
                                                <table >
                                                    <tr class="Subtitulo">
                                                        <td colspan="2">Clave de Banco</td>
                                                        <td>Clave de Moneda</td>
                                                    </tr>

                                                    <tr class="Subtitulo">
                                                        <td colspan="2" align="left">
                                                            <SELECT NAME="selComboKeyBank"  onchange="return setActionKeyBank(dataManualLoad, 'seleccionaClaveBanco:2');">
                                                                <c:forEach items="${sessionScope.clavesBanco}" var="keyBank" varStatus="status">
                                                                    <c:choose>
                                                                        <c:when test="${sessionScope.clave_banco != null }">
                                                                            <c:choose>
                                                                                <c:when test="${sessionScope.clave_banco == keyBank }">
                                                                                    <OPTION selected VALUE="keyBank<%=j%>"><c:out value="${keyBank}"/></OPTION>
                                                                                    </c:when>
                                                                                    <c:otherwise>
                                                                                    <OPTION VALUE="keyBank<%=j%>"><c:out value="${keyBank}"/></OPTION>
                                                                                    </c:otherwise>
                                                                                </c:choose>
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                            <OPTION VALUE="keyBank<%=j%>"><c:out value="${keyBank}"/></OPTION>
                                                                            </c:otherwise>
                                                                        </c:choose>
                                                                        <% j++;%>
                                                                    </c:forEach>
                                                            </SELECT>
                                                        </td>

                                                        <td align="left">
                                                            <SELECT NAME="selComboKeyCoin" onchange="return setActionComboKeyCoin(dataManualLoad, 'seleccionaClaveMoneda:3');">
                                                                <c:forEach items="${sessionScope.clavesMoneda}" var="keyCoin" varStatus="status">
                                                                    <c:choose>
                                                                        <c:when test="${sessionScope.clave_moneda != null }">
                                                                            <c:choose>
                                                                                <c:when test="${sessionScope.clave_moneda == keyCoin }">
                                                                                    <OPTION selected VALUE="keyCoin<%=k%>"><c:out value="${keyCoin}"/></OPTION>
                                                                                    </c:when>
                                                                                    <c:otherwise>
                                                                                    <OPTION VALUE="keyCoin<%=k%>"><c:out value="${keyCoin}"/></OPTION>
                                                                                    </c:otherwise>
                                                                                </c:choose>
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                            <OPTION VALUE="keyCoin<%=k%>"><c:out value="${keyCoin}"/></OPTION>
                                                                            </c:otherwise>
                                                                        </c:choose>
                                                                        <% k++;%>
                                                                    </c:forEach>
                                                            </SELECT>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>


                                        <tr class="Subtitulo">
                                            <td align="center" >&nbsp;</td>
                                            <td  align="left">Nombres de Fideicomisario</td>
                                            <td  align="left">Apellido Paterno</td>
                                            <td  align="left">Apellido Materno</td>
                                        </tr>

                                        <tr class="Subtitulo">
                                            <td align="center" >&nbsp;</td>
                                            <td align="left">
                                                <input type="text" name="nombreFideicomisario" style="width:150px;" maxlength="40" value="<c:out value='${sessionScope.nombreFideicomisario}'/>" onkeypress="return validaInputLetter(event)"/>
                                            </td>
                                            <td align="left">
                                                <input type="text" name="apellidoPaterno" style="width:150px;" maxlength="40" value="<c:out value='${sessionScope.apellidoPaterno}'/>" onkeypress="return validaInputLetter(event)"/>
                                            </td>
                                            <td align="left">
                                                <input type="text" name="apellidoMaterno" style="width:150px;" maxlength="40" value="<c:out value='${sessionScope.apellidoMaterno}'/>" onkeypress="return validaInputLetter(event)"/>
                                            </td>
                                        </tr>


                                        <tr class="Subtitulo">
                                            <td align="center" >&nbsp;</td>
                                            <td  align="left">Fecha de Ingreso</td>
                                            <td  align="left">Puesto</td>
                                            <td  align="left">Departamento</td>
                                        </tr>

                                        <tr>
                                            <td align="center" >&nbsp;</td>
                                            <td align="left">
                                                <input name="fechaIngreso" type="text" id="dateArrivalIngreso" value="<c:out value='${sessionScope.fechaIngreso}'/>" onClick="popUpCalendar(this, captureData.dateArrivalIngreso, 'dd/mm/yyyy');" size="10">
                                            </td>

                                            <td align="left">
                                                <input type="text" name="puesto" maxlength="50" value="<c:out value='${sessionScope.puesto}'/>" style="width:150px;" />
                                            </td>
                                            <td align="left">
                                                <input type="text" name="departamento" maxlength="45" value="<c:out value='${sessionScope.departamento}'/>" style="width:150px;"/>
                                            </td>
                                        </tr>

                                        <tr class="Subtitulo">
                                            <td align="center" >&nbsp;</td>
                                            <td  align="left">ID del Fideicomisario</td>
                                            <td  align="left">CURP</td>
                                            <td  align="left">RFC</td>
                                        </tr>

                                        <tr class="Subtitulo">
                                            <td align="center" >&nbsp;</td>
                                            <td align="left">
                                                <input type="text" name="claveFideicomisario" maxlength="40" value="<c:out value='${sessionScope.claveFideicomisario}'/>" style="width:150px;"/>
                                            </td>
                                            <td align="left">
                                                <input type="text" name="CURP" id="curp" maxlength="18" value="<c:out value='${sessionScope.CURP}'/>" style="width:150px;" onchange="validaCURP()"/>
                                            </td>
                                            <td align="left">
                                                <input type="text" name="RFC" id="rfc" maxlength="13" value="<c:out value='${sessionScope.RFC}'/>" style="width:150px;" onchange="validaRFC()"/>
                                            </td>
                                        </tr>

                                        <tr class="Subtitulo">
                                            <td align="center" >&nbsp;</td>
                                            <td  align="left">Cuenta de Depósito</td>
                                            <td  align="left">Importe de Liquidación</td>
                                        </tr>

                                        <tr class="Subtitulo">
                                            <td align="center" >&nbsp;</td>
                                            <td align="left">
                                                <input type="text" name="cuentaDeposito" style="width:150px;" maxlength="45" value="<c:out value='${sessionScope.cuentaDeposito}'/>" onkeypress="return validaInputNum(event)"/>
                                            </td>
                                            <td align="left">
                                                <input type="text" name="importeLiquidacion" style="width:150px;" maxlength="30" value="<c:out value='${sessionScope.importe_liquidacion}'/>" onkeypress="return validaInputNum(event)"/>
                                            </td>
                                        </tr>


                                        <tr class="Subtitulo">
                                            <td align="center" >&nbsp;</td>
                                            <td  align="left" >Envío Cheque</td>
                                            <td  colspan="2" align="left" >Destino Cheque</td>
                                        </tr>


                                        <tr class="Subtitulo">
                                            <td align="center" >&nbsp;</td>
                                            <td align="left">
                                                <input type="text" name="envioCheque" style="width:150px;" maxlength="60" value="<c:out value='${sessionScope.envioCheque}'/>" />
                                            </td>
                                            <td colspan="2" align="left">
                                                <input type="text" name="destinoCheque" style="width:370px;" maxlength="60" value="<c:out value='${sessionScope.destinoCheque}'/>" />
                                            </td>
                                        </tr>


                                        <tr class="Subtitulo">
                                            <td align="center" >&nbsp;</td>
                                            <td  align="left">Teléfono Cheque</td>
                                            <td  align="left">Correo Cheque</td>
                                        </tr>

                                        <tr class="Subtitulo">
                                            <td align="center" >&nbsp;</td>
                                            <td align="left">
                                                <input type="text" name="telefonoCheque" style="width:150px;" maxlength="30" value="<c:out value='${sessionScope.telefonoCheque}'/>" />
                                            </td>
                                            <td align="left">
                                                <input type="text" name="correoCheque" style="width:150px;" maxlength="45" value="<c:out value='${sessionScope.correoCheque}'/>" />
                                            </td>
                                        </tr>


                                        <tr class="Subtitulo">
                                            <td align="center" >&nbsp;</td>
                                            <td  align="left">Banco Extranjero</td>
                                            <td  colspan="2" align="left"> Domicilio Banco Extranjero</td>

                                        </tr>

                                        <tr class="Subtitulo">
                                            <td align="center" >&nbsp;</td>
                                            <td align="left">
                                                <input type="text" name="bancoExtranjero" style="width:150px;" maxlength="45" value="<c:out value='${sessionScope.bancoExtranjero}'/>" />
                                            </td>
                                            <td colspan="2" align="left">
                                                <input type="text" name="dirBancoExtranjero" style="width:370px;" maxlength="60" value="<c:out value='${sessionScope.dirBancoExtranjero}'/>" />
                                            </td>
                                        </tr>


                                        <tr class="Subtitulo">
                                            <td align="center" >&nbsp;</td>
                                            <td align="left"> País Banco Extranjero</td>
                                            <td colspan="2" align="left"> ABA/BIC</td>
                                        </tr>

                                        <tr class="Subtitulo">
                                            <td align="center" >&nbsp;</td>
                                            <td align="left">
                                                <input type="text" name="paisBancoExtranjero" style="width:150px;" maxlength="45" value="<c:out value='${sessionScope.paisBancoExtranjero}'/>" />
                                            </td>
                                            <td colspan="2" align="left">
                                                <input type="text" name="ABA_BIC" style="width:150px;" maxlength="45" value="<c:out value='${sessionScope.ABA_BIC}'/>" />
                                            </td>
                                        </tr>


                                        <tr class="Subtitulo">
                                            <td align="center" >&nbsp;</td>
                                            <td  align="left"> Nombre en Banco Extranjero</td>
                                            <td  colspan="2" align="left"> Dirección en Extranjero</td>

                                        </tr>

                                        <tr class="Subtitulo">
                                            <td align="center" >&nbsp;</td>
                                            <td align="left">
                                                <input type="text" name="nombreFideiBancoExtranjero" style="width:150px;" maxlength="45" value="<c:out value='${sessionScope.nombreFideiBancoExtranjero}'/>" />
                                            </td>
                                            <td colspan="2" align="left">
                                                <input type="text" name="dirFideiBancoExtranjero" style="width:370px;" maxlength="60" value="<c:out value='${sessionScope.dirFideiBancoExtranjero}'/>" />
                                            </td>
                                        </tr>


                                        <tr class="Subtitulo">
                                            <td align="center" >&nbsp;</td>
                                            <td  align="left"> País Fideicomisario</td>
                                            <td  colspan="2" align="left"> Teléfono Fideicomisario</td>
                                        </tr>

                                        <tr class="Subtitulo">
                                            <td align="center" >&nbsp;</td>
                                            <td align="left">
                                                <input type="text" name="paisFideiBancoExtranjero" style="width:150px;" maxlength="45" value="<c:out value='${sessionScope.paisFideiBancoExtranjero}'/>" />
                                            </td>
                                            <td colspan="2" align="left">
                                                <input type="text" name="telFideiBancoExtranjero" style="width:150px;" maxlength="30" value="<c:out value='${sessionScope.telFideiBancoExtranjero}'/>" />
                                            </td>
                                        </tr>

                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" >&nbsp;</td>
                            </tr>

                            <tr align="center">
                                <td colspan="3">
                                    <input type="button" name="guardar" value="Cargar" onClick="return guardarMovimiento(captureData, 'cargaMovimientoManual:2')"/>
                                    <input type="button" name="terminar" value="Finalizar" onClick="return setAction(captureData, 'almacenaLayOutManual:3')"/>
                                </td>
                            </tr>

                            <tr>
                                <td align="center" >&nbsp;</td>
                            </tr>
                        </table>
                    </c:if>

                    <c:if test="${ sessionScope.resumenMovs != null && sessionScope.imprimeResumen != null }">
                        <c:set var="resumenMovimientos" value="${sessionScope.resumenMovs}"/>
                        <table align="center">
                            <tr>
                                <td align="left" colspan="2">
                                    <IMG SRC="./images/logo.jpg" ALT="Logo" width="200px" height="80px">
                                </td>
                            </tr>                        
                            <tr><td colspan="2" height="90px">&nbsp;</td></tr>                        
                            <tr>
                                <td align="center" colspan="2" style=" font-family:Arial; font-size:19px;">
                                    <b> <c:out value="${sessionScope.userApp.cliente}"/></b>
                                </td>
                            </tr>

                            <tr><td colspan="2">&nbsp;</td></tr>
                            <tr>
                                <td align="center"  colspan="2"  style=" font-family:Arial; font-size:16px;">
                                    <b> Liquidación del <c:out value="${resumenMovimientos.fecha_liquidacion}"/>
                                        Contrato <c:out value="${sessionScope.userApp.clave_contrato}"/>
                                    </b>
                                </td>
                            </tr>
                            <tr><td colspan="2">&nbsp;</td></tr>                            
                            <tr>
                                <td>
                                    <table class="LetraReporte" border ="1" width="100%">
                                        <tr>
                                            <td align="left" style="background-Color:#E3F2FE;"  width="60%" >
                                                <c:out value="${resumenMovimientos.desc_pago_mov_tipo1}"/>
                                            </td>

                                            <td align="right" style="background-Color:#E3F2FE;"  width="10%" >
                                                <c:out value="${resumenMovimientos.total_movs_tipo1}"/>
                                            </td>

                                            <td align="right" style="background-Color:#E3F2FE;"  >
                                                <c:out value="${resumenMovimientos.formato_importe_tipo1}"/>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td align="left"  style="background-Color:#FFFFFF;" >
                                                <c:out value="${resumenMovimientos.desc_pago_mov_tipo2}"/>
                                            </td>

                                            <td align="right" style="background-Color:#FFFFFF;"  >
                                                <c:out value="${resumenMovimientos.total_movs_tipo2}"/>
                                            </td>

                                            <td align="right"  style="background-Color:#FFFFFF;" >
                                                <c:out value="${resumenMovimientos.formato_importe_tipo2}"/>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td align="left"  style="background-Color:#E3F2FE;" >
                                                <c:out value="${resumenMovimientos.desc_pago_mov_tipo3}"/>
                                            </td>

                                            <td align="right" style="background-Color:#E3F2FE;"  >
                                                <c:out value="${resumenMovimientos.total_movs_tipo3}"/>
                                            </td>

                                            <td align="right"  style="background-Color:#E3F2FE;" >
                                                <c:out value="${resumenMovimientos.formato_importe_tipo3}"/>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td align="left"  style="background-Color:#FFFFFF;" >
                                                <c:out value="${resumenMovimientos.desc_pago_mov_tipo4}"/>
                                            </td>

                                            <td align="right" style="background-Color:#FFFFFF;"  >
                                                <c:out value="${resumenMovimientos.total_movs_tipo4}"/>
                                            </td>

                                            <td align="right"  style="background-Color:#FFFFFF;" >
                                                <c:out value="${resumenMovimientos.formato_importe_tipo4}"/>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td colspan="2" align="right"  style="background-Color:#E3F2FE;" >
                                                <b>
                                                    Total de movimientos : <c:out value="${resumenMovimientos.total_movimientos}"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                    <c:out value="${resumenMovimientos.des_importe_total}"/>:</b>
                                            </td>

                                            <td align="right"  style="background-Color:#E3F2FE;" >
                                                <b><c:out value="${resumenMovimientos.formato_importe_totalMXP}"/></b>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>

                            <tr><td colspan="3">&nbsp;</td></tr>

                            <tr><td>
                                    <table class="LetraReporte" border ="1" width="100%">
                                        <tr>
                                            <td colspan="2" align="left" style="background-Color:#E3F2FE;" width="70%">Patrimonio disponible</td>
                                            <td align="right" style="background-Color:#E3F2FE;">
                                                <c:out value="${resumenMovimientos.saldo_actual}"/>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td colspan="2" align="left" style="background-Color:#FFFFFF;">Importe de liquidación</td>
                                            <td align="right"style="background-Color:#FFFFFF;">
                                                <c:out value="${resumenMovimientos.formato_importe_totalMXP}"/>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td colspan="2" align="left" style="background-Color:#E3F2FE;">Liquidaciones pendientes</td>
                                            <td align="right"style="background-Color:#E3F2FE;">
                                                <c:out value="${resumenMovimientos.liquidaciones_pendientes}"/>
                                            </td>
                                        </tr> 

                                        <tr>
                                            <td colspan="2" align="left" style="background-Color:#FFFFFF;">  <c:out value="${resumenMovimientos.txt_Nuevo_saldo}"/> </td>
                                            <td align="right"style="background-Color:#FFFFFF;">
                                                <c:out value="${resumenMovimientos.nuevo_saldo}"/>
                                            </td>
                                        </tr>                                           
                                    </table>
                                </td></tr>

                            <tr><td colspan="3">&nbsp;</td></tr>

                            <c:if test="${resumenMovimientos.aportacion_minima_requerida ne '$ 0.00'}" > 
                                <tr> <td>
                                        <table class="LetraReporte" border ="1" width="100%">
                                            <tr>
                                                <td colspan="2" align="left" style="background-Color:#E3F2FE;" width="70%"> <b>Aportaci&oacute;n m&iacute;nima requerida </b></td>
                                                <td align="right" style="background-Color:#E3F2FE; ">
                                                    <b>  <c:out value="${resumenMovimientos.aportacion_minima_requerida}"/>  </b>
                                                </td>
                                            </tr>                                                                              
                                        </table>
                                    </td></tr>                                            
                                </c:if>    
                            <tr><td colspan="2">&nbsp;</td></tr>
                            <tr><td>
                                    <table  class="LetraReporte" border ="1" width="100%">
                                        <tr>
                                            <td colspan="3"  align="center" width="100%"><b>Traspasos de Bancomer a Bancos Extranjeros</b></td>
                                        </tr>
                                        <tr>
                                            <td  align="left" width="60%">Pendientes</td>

                                            <td align="right" width="10%">
                                                <c:out value="${resumenMovimientos.total_movs_tipo5_pend}"/>
                                            </td>

                                            <td  align="right">
                                                <c:out value="${resumenMovimientos.formato_importe_tipo5_pend}"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td  align="left" width="60%">Orden de liquidaci&oacute;n</td>

                                            <td align="right" width="10%">
                                                <c:out value="${resumenMovimientos.total_movs_tipo5}"/>
                                            </td>

                                            <td  align="right">
                                                <c:out value="${resumenMovimientos.formato_importe_tipo5}"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td colspan="3" >
                                                El importe de traspasos a Bancos Extranjeros es nominativo en moneda extranjera y se<br>
                                                determinará el monto de afectación al patrimonio fideicomitido en el momento en que se<br>
                                                realicen las transacciones correspondientes al tipo de cambio vigente ese día.
                                            </td>
                                        </tr>
                                    </table>
                                </td></tr>                             

                        </table>
                    </c:if>

                </form>
            </body>
        </html>
    </c:when>
    <c:otherwise>
        <c:redirect url="UserLogin.htm" />
    </c:otherwise>
</c:choose>
