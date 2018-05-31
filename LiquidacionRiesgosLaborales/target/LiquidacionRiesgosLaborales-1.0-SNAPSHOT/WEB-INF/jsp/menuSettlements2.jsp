<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.Vector"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<c:choose>
    <c:when test="${sessionScope.userApp != null}">

        <html>

            <head>
                <title> Liquidaciones </title>
                <link rel="shortcut icon" href="images/icono.png">
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <!-- Aqui importamos los estilos que necesitemos-->
                <link rel="stylesheet" type="text/css" href="css/formato.css">
                <link rel="stylesheet" type="text/css" href="css/Saldo.css">
                <!--Aqui importamos los scripts que necesitemos-->
                <script language="javascript" type="text/javascript" src="scripts/login.js"></script>
                <script language="javascript" type="text/javascript" src="scripts/liquidation.js"></script>
                <script language="javascript" type="text/javascript" src="scripts/popCalendar.js"></script>
                <script language="javascript" type="text/javascript" src="scripts/Saldo.js"></script>
                <script language="javascript" type="text/javascript" src="scripts/jquery.min.js"></script>
                <script language="javascript" type="text/javascript" src="scripts/validaPLD.js"></script>
            </head>

            <body 

                <c:if test="${sessionScope.messageBean != null  }">
                    onLoad="alert('<c:out value="${sessionScope.messageBean.desc}"/>');"
                    <c:remove var="messageBean" scope="session" />
                </c:if>
                >
                <form name="formLiquidation" id="formLiquidation" method="post" action="ControllerLiquidation">
                    <input type="hidden" name="accion" id="accion" value=""/>
                    <input type="hidden" name="cliente" id="cliente" value=""/>
                    <input type="hidden" name="operacion" value=""/>
                    <input type="hidden" name="clave_contrato" value=""/>
                    <input type="hidden" name="fecha_liquidacion" value=""/>
                    <input type="hidden" name="nombre_archivo" value=""/>
                    <input type="hidden" name="nombre_fideicomisario" value=""/>
                    <input type="hidden" name="importe_MXP" value=""/>
                    <input type="hidden" name="nombresObjetos" value=""/>
                    <input type="hidden" name="urlResponse" value=""/>
                    <input type="hidden" name="opcionC" value=""/>
                    <input type="hidden" name="motivo_cancelacion" value=""/>

                    <table align ="center" width="100%">
                        <tr >
                            <td width="75%">&nbsp;</td>
                            <td align ="center" width="15%">
                                <b ><a href="" onclick="return cambiaPassword(formLiquidation, 'cambiaPassword:6');" target="main"><u>cambiar contraseña</u></a></b>
                            </td>
                            <td>
                                <b><a href="" onclick="return atras(formLiquidation, 'eliminaBeans:100', 'messageBean;userApp;movPending', 'login.htm');" target="main"><u>Salir</u></a></b>
                            </td>
                        </tr>
                    </table>

                    <c:if test="${sessionScope.cancela_movs == null }">
                        <div align="center">
                            <fieldset class="TamDirBienv">
                                <legend class="Subtitulo"></legend>
                                <table align="center" >
                                    <tr><td class="Titulo" align="center"> BIENVENIDO </td></tr>

                                    <tr >
                                        <td  class="Titulo" align="center" > <c:out value="${sessionScope.userApp.nombre_usuario}"/></td>
                                    </tr>

                                    <tr>
                                        <td>
                                            <table align="center" >
                                                <tr>
                                                    <td>
                                                        <a href="" onclick="return setAction(formLiquidation, 'menuLiqPend:2');" target="main">
                                                            <img src="./Imagen/icono01.jpg" title="Generar Liquidaciones Pendientes" width="45" height="45" />
                                                        </a>
                                                    </td>

                                                    <td>
                                                        <a href="" onclick="return setAction(formLiquidation, 'menuModAutoriza:17');" target="main">
                                                            <img src="./Imagen/cancelar.jpg" title="Cancelar Movimientos" width="40" height="45"/>
                                                        </a>
                                                    </td>
                                                    <td>
                                                        <a href="" onclick="return setAction(formLiquidation, 'menuModActualiza:11');" target="main">
                                                            <img src="./Imagen/euro.jpg" title="Actualizar Movimientos a Bancos Extranjeros" width="45" height="45" />
                                                        </a>

                                                    </td>

                                                    <td align="center">
                                                        <a href="" onclick="return setAction(formLiquidation, 'confirmacionMovimientos:18');" target="main">
                                                            <img src="./Imagen/confirmar.jpg" title="Autorizar Movimientos" width="45" height="45"  />
                                                        </a>
                                                    </td>
                                                    <td align="center">
                                                        <a href="" onclick="return setAction(formLiquidation, 'Aportacion_Restitucion:34');" target="main">
                                                            <img src="./Imagen/saldo.png" title="Aportaciones y Restituciones" width="45" height="45"  />
                                                        </a>
                                                    </td>  
                                                    <td align="center">
                                                        <a href="" onclick="return setAction(formLiquidation, 'buscar_movimientos:41');" target="main">
                                                            <img src="./Imagen/buscar_icono.png" title="Buscar movimientos" width="45" height="45"  />
                                                        </a>
                                                    </td>                                                      
                                                    <td align="center">
                                                        <a href="" onclick="return setAction(formLiquidation, 'consulta_ejecutiva:43');" target="main">
                                                            <img src="./Imagen/conculta_ejecutiva.jpg" title="Consulta Ejecutiva" width="45" height="45"  />
                                                        </a>
                                                    </td> 
                                                    <td align="center">
                                                        <a href="" onclick="return setAction(formLiquidation, 'estados_de_cuenta:47');" target="main">
                                                            <img src="./Imagen/estado_de_cuenta.png" title="Estados de Cuenta" width="45" height="45"  />
                                                        </a>    
                                                    </td> 
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                </table>
                            </fieldset>
                        </div>
                    </c:if>
                    <%--Módulo:Generación de Lay-Out's--%>
                    <c:if test="${sessionScope.movPending != null }">
                        <table align="center"  class="LetraInput" >
                            <% int i = 0;%>
                            <tr><td colspan="2">&nbsp;</td></tr>
                            <tr align="center" class="Titulo"><td colspan="2">LIQUIDACIONES PENDIENTES POR OPERAR</td></tr>
                            <tr><td colspan="2">&nbsp;</td></tr>
                            <tr>
                                <td class="SubTitulo" align="right">Clave de Fideicomiso:</td>
                                <td>
                                    <SELECT NAME="selComboData" onchange="return setActionComboCli(formLiquidation, 'DataClient:3');">
                                        <c:forEach items="${sessionScope.cliMovPend}" var="client" varStatus="status">
                                            <c:choose>
                                                <c:when test="${sessionScope.clave_contrato != null }">
                                                    <c:choose>
                                                        <c:when test="${sessionScope.clave_contrato == client }">
                                                            <OPTION selected VALUE="data<%=i%>"><c:out value="${client}"/></OPTION>
                                                            </c:when>
                                                            <c:otherwise>
                                                            <OPTION VALUE="data<%=i%>"><c:out value="${client}"/></OPTION>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:when>
                                                    <c:otherwise>
                                                    <OPTION VALUE="data<%=i%>"><c:out value="${client}"/></OPTION>
                                                    </c:otherwise>
                                                </c:choose>
                                                <% i++;%>
                                            </c:forEach>
                                    </SELECT>
                                </td>
                            </tr>

                            <tr><td colspan="2">&nbsp;</td></tr>
                        </table>

                        <c:if test="${sessionScope.dateLiquidation != null && sessionScope.dataClient != null }">
                            <% int k = 0;%>
                            <% int id = 0;%>
                            <c:set var="InfoGeneral" value="${sessionScope.dataClient}"/>
                            <table align="center" class="Subtitulo" >
                                <tr>
                                    <td align="left">
                                        Información General:
                                    </td>
                                </tr>

                                <tr>
                                    <td align="center">
                                        <table  align="center" class="form-noindent">
                                            <tr>
                                                <td align="right">Fideicomitente:</td>
                                                <td align="left">
                                                    <input class="LetraInput" type="text" disabled="disabled" style="width:380px;" name="Campo<%=k%>" value="<c:out value='${InfoGeneral[0]}'/>" />
                                                </td>
                                            </tr>

                                            <tr>
                                                <td align="right">Dirección:</td>
                                                <td align="left">
                                                    <textarea  class="LetraInput" disabled="disabled" rows="3" cols="60"><c:out value='${InfoGeneral[1]}'/></textarea>
                                                </td>
                                            </tr>

                                            <tr>
                                                <td align="right">RFC:</td>
                                                <td align="left">
                                                    <input class="LetraInput" type="text" disabled="disabled" name="Campo<%=k%>" value="<c:out value='${InfoGeneral[2]}'/>" />
                                                </td>
                                            </tr>

                                            <tr>
                                                <td align="right">Grupo:</td>
                                                <td align="left">
                                                    <input class="LetraInput" type="text" disabled="disabled" name="Campo<%=k%>" value="<c:out value='${InfoGeneral[3]}'/>" />
                                                </td>
                                            </tr>

                                            <tr >
                                                <td align="right">Clave:</td>
                                                <td align="left">
                                                    <input class="LetraInput" type="text" disabled="disabled" name="Campo<%=k%>" value="<c:out value='${InfoGeneral[4]}'/>" />
                                                </td>
                                            </tr>
                                            <tr><td colspan="2">&nbsp;</td></tr>
                                        </table>
                                    </td>
                                </tr>
                            </table>

                            <c:if test="${sessionScope.dateLiquidation != null }">
                                <br>
                                <table align="center" class="LetraInput">
                                    <tr>
                                        <td class="SubTitulo">Fecha de Liquidación:</td>
                                        <td colspan="2" >
                                            <SELECT NAME="selComboDate" onchange="return setActionComboDate(formLiquidation, 'DateClient:10');">
                                                <c:forEach items="${sessionScope.dateLiquidation}" var="date" varStatus="status">
                                                    <c:choose>
                                                        <c:when test="${sessionScope.fecha_liquidacion != null }">
                                                            <c:choose>
                                                                <c:when test="${sessionScope.fecha_liquidacion == date }">
                                                                    <OPTION selected VALUE="date<%=k%>"><c:out value="${date}"/></OPTION>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                    <OPTION VALUE="date<%=k%>"><c:out value="${date}"/></OPTION>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:when>
                                                            <c:otherwise>
                                                            <OPTION VALUE="date<%=k%>"><c:out value="${date}"/></OPTION>
                                                            </c:otherwise>
                                                        </c:choose>
                                                        <% k++;%>
                                                    </c:forEach>
                                            </SELECT>
                                        </td>
                                    </tr>

                                    <c:if test="${sessionScope.filesName != null }">
                                        <tr>
                                            <td class="SubTitulo"> Lay-Out Cargado:</td>
                                            <td colspan="2" >
                                                <SELECT NAME="selComboFileName" onchange="return setActionComboFileName(formLiquidation, 'fileName:4');">
                                                    <c:forEach items="${sessionScope.filesName}" var="file" varStatus="status">
                                                        <c:choose>
                                                            <c:when test="${sessionScope.nombre_archivo != null }">
                                                                <c:choose>
                                                                    <c:when test="${sessionScope.nombre_archivo == file }">
                                                                        <OPTION selected VALUE="file<%=id%>"><c:out value="${file}"/></OPTION>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                        <OPTION VALUE="file<%=id%>"><c:out value="${file}"/></OPTION>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </c:when>
                                                                <c:otherwise>
                                                                <OPTION VALUE="file<%=id%>"><c:out value="${file}"/></OPTION>
                                                                </c:otherwise>
                                                            </c:choose>
                                                            <% id++;%>
                                                        </c:forEach>
                                                </SELECT>
                                            </td>
                                        </tr>
                                    </c:if>

                                    <c:if test="${ sessionScope.date_Capt != null && sessionScope.date_Capt != ''}">
                                        <tr>
                                            <td class="SubTitulo">Fecha de Recepción:</td>
                                            <td>
                                                <input class="LetraInput" type="text" disabled="disabled" name="DayCap" value="<c:out value='${sessionScope.date_Capt}'/>" />
                                            </td>
                                        </tr>

                                        <tr><td colspan="2">&nbsp;</td></tr>

                                        <tr>
                                            <td colspan="2" align="center">
                                                <input type="button" class="NormalBoton" name="generaReportes" value="Generar" onClick="setAction(formLiquidation, 'GenerateReports:5')"/>
                                            </td>
                                        </tr>
                                    </c:if>
                                    <tr><td colspan="2">&nbsp;</td></tr>
                                </table>
                            </c:if>
                        </c:if>
                    </c:if>

                    <%--Módulo:Cancelación de Movimientos--%>
                    <c:if test="${sessionScope.modAutoriza != null}">
                        <table align="center" >
                            <tr>
                                <td align="center">
                                    <a href="" onclick="return setAction(formLiquidation, 'CancelacionTotalTransacciones:23');" target="main">
                                        <img src="./Imagen/total_c.jpg" alt="Cancelación Total" width="45" height="45" />
                                    </a>
                                </td>
                                <td align="center">
                                    <a href="" onclick="return setAction(formLiquidation, 'CancelacionParcialTransacciones:25');" target="main">
                                        <img src="./Imagen/parcial_c.jpg" alt="Cancelación Parcial" width="45" height="45" />
                                    </a>
                                </td>
                            </tr>
                        </table>

                        <%--Módulo:Cancelación Parcial de Movimientos--%>
                        <c:if test="${sessionScope.cancelacionParcial != null && sessionScope.cancelacionTotal == null && sessionScope.confirmacionLote == null }">
                            <table align="center" class="form-inWhite" style="background-color: #ddf8cc;">
                                <% int cpc = 0;%>
                                <tr align="center" ><td  class="Titulo" colspan="4">CANCELACIÓN DE MOVIMIENTOS</td></tr>
                                <tr><td colspan="4">&nbsp;</td></tr>
                                <tr>
                                    <td align="right" class="SubTitulo">Clave de Fideicomiso:</td>
                                    <td colspan="3" >
                                        <SELECT NAME="selComboClaveFiso"  onchange="return setActionComboClaveFiso(formLiquidation, 'AutorizaLoteCliente:19');">
                                            <c:forEach items="${sessionScope.cliMovConfirma}" var="client" varStatus="status">
                                                <c:choose>
                                                    <c:when test="${sessionScope.clave_contratoC != null }">
                                                        <c:choose>
                                                            <c:when test="${sessionScope.clave_contratoC == client }">
                                                                <OPTION selected VALUE="dataC<%=cpc%>"><c:out value="${client}"/></OPTION>
                                                                </c:when>
                                                                <c:otherwise>
                                                                <OPTION VALUE="dataC<%=cpc%>"><c:out value="${client}"/></OPTION>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </c:when>
                                                        <c:otherwise>
                                                        <OPTION VALUE="dataC<%=cpc%>"><c:out value="${client}"/></OPTION>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <% cpc++;%>
                                                </c:forEach>
                                        </SELECT>
                                    </td>
                                </tr>
                                <c:if test="${sessionScope.cliente_c != null && sessionScope.fechas_liquidacionC != null }">
                                    <tr>
                                        <td align="right" class="SubTitulo"><b>Fideicomitente:</b></td>
                                        <td align="left" colspan="3" >
                                            <input  type="text" disabled="disabled"  class="LetraInput" style="width:370px;" name="FideicomitenteC" value="<c:out value='${sessionScope.cliente_c}'/>" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <% int cpf = 0;%>
                                        <td align="right" class="SubTitulo">Fecha Liquidación:</td>
                                        <td  >
                                            <SELECT NAME="selComboDateC"  onchange="return setActionComboDateC(formLiquidation, 'AutorizaLoteFecha:20');">
                                                <c:forEach items="${sessionScope.fechas_liquidacionC}" var="date" varStatus="status">
                                                    <c:choose>
                                                        <c:when test="${sessionScope.fecha_c != null }">
                                                            <c:choose>
                                                                <c:when test="${sessionScope.fecha_c == date }">
                                                                    <OPTION selected VALUE="dateC<%=cpf%>"><c:out value="${date}"/></OPTION>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                    <OPTION VALUE="dateC<%=cpf%>"><c:out value="${date}"/></OPTION>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:when>
                                                            <c:otherwise>
                                                            <OPTION VALUE="dateC<%=cpf%>"><c:out value="${date}"/></OPTION>
                                                            </c:otherwise>
                                                        </c:choose>
                                                        <% cpf++;%>
                                                    </c:forEach>
                                            </SELECT>
                                        </td>
                                        <c:if test="${sessionScope.lotes_c != null }">
                                            <% int cpl = 0;%>
                                            <td align="right" class="SubTitulo">Nombre Lote:</td>
                                            <td >
                                                <SELECT NAME="selComboFileNameC"  onchange="return setActionComboFileNameC(formLiquidation, 'AutorizaLoteNombreArchivo:26');">
                                                    <c:forEach items="${sessionScope.lotes_c}" var="date" varStatus="status">
                                                        <c:choose>
                                                            <c:when test="${sessionScope.lote_c != null }">
                                                                <c:choose>
                                                                    <c:when test="${sessionScope.lote_c == date }">
                                                                        <OPTION selected VALUE="loteC<%=cpl%>"><c:out value="${date}"/></OPTION>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                        <OPTION VALUE="loteC<%=cpl%>"><c:out value="${date}"/></OPTION>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </c:when>
                                                                <c:otherwise>
                                                                <OPTION VALUE="loteC<%=cpl%>"><c:out value="${date}"/></OPTION>
                                                                </c:otherwise>
                                                            </c:choose>
                                                            <% cpl++;%>
                                                        </c:forEach>
                                                </SELECT>
                                            </td>
                                        </c:if>
                                    </tr>
                                </c:if>

                                <c:if test="${sessionScope.fecha_capturaC != null && sessionScope.opciones_c != null }">
                                    <tr>
                                        <td  align="right" class="SubTitulo">Fecha Carga:</td>
                                        <td  align="left" >
                                            <input  type="text"  class="LetraInput" disabled="disabled"  style="width:160px;" name="LayOutC" value="<c:out value='${sessionScope.fecha_capturaC}'/>" />
                                        </td>
                                        <td align="left" class="SubTitulo">Movimientos:</td>
                                        <td align="left">
                                            <input  type="text"  class="LetraInput" disabled="disabled"  style="width:80px;" name="totalMovs" value="<c:out value='${sessionScope.total_movimientos}'/>" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <% int ocp = 0;%>
                                        <td align="right"></td>
                                        <td  colspan="3" align="left" class="SubTitulo">Tipo de Operación:
                                            <SELECT NAME="selComboOpcion" onchange="return setActionComboOption(formLiquidation, 'selComboOpcion:27');">
                                                <c:forEach items="${sessionScope.opciones_c}" var="date" varStatus="status">
                                                    <c:choose>
                                                        <c:when test="${sessionScope.opcion_c != null }">
                                                            <c:choose>
                                                                <c:when test="${sessionScope.opcion_c == date }">
                                                                    <OPTION selected VALUE="opcionC<%=ocp%>"><c:out value="${date}"/></OPTION>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                    <OPTION VALUE="opcionC<%=ocp%>"><c:out value="${date}"/></OPTION>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:when>
                                                            <c:otherwise>
                                                            <OPTION VALUE="opcionC<%=ocp%>"><c:out value="${date}"/></OPTION>
                                                            </c:otherwise>
                                                        </c:choose>
                                                        <% ocp++;%>
                                                    </c:forEach>
                                            </SELECT>
                                        </td>
                                    </tr>
                                </c:if>
                            </table>
                            <br>

                            <c:if test="${sessionScope.opcionC != null && sessionScope.opcionH == null }">

                                <table align="center" class="form-inWhite" style="background-color: #ddf8cc;">
                                    <tr>
                                        <td align="right" class="SubTitulo">Cuenta Depósito:</td>
                                        <td>
                                            <input class="LetraInput" type="text"  name="cuenta_deposito" style="width:160px;"  value="<c:out value='${sessionScope.mov_cancelado.cuenta_deposito}'/>" onkeypress="pulsar_enter(event, formLiquidation, 'CuentaDeposito:28')"/>
                                        </td>
                                        <td align="right" class="SubTitulo">Nombre(s):</td>
                                        <td>
                                            <input class="LetraInput" type="text" style="width:160px;" name="nombre_empleado" value="<c:out value='${sessionScope.mov_cancelado.nombre_empleado}'/>" onkeypress="pulsar_enter(event, formLiquidation, 'CuentaDeposito:28')" />
                                        </td>
                                    </tr>

                                    <tr>
                                        <td align="right" class="SubTitulo">Apellido Paterno:</td>
                                        <td>
                                            <input class="LetraInput" type="text" style="width:160px;" name="apellidoP_empleado" value="<c:out value='${sessionScope.mov_cancelado.apellidoP_empleado}'/>" onkeypress="pulsar_enter(event, formLiquidation, 'CuentaDeposito:28')" />
                                        </td>
                                        <td align="right" class="SubTitulo">Apellido Materno:</td>
                                        <td>
                                            <input class="LetraInput" type="text" style="width:160px;" name="apellidoM_empleado" value="<c:out value='${sessionScope.mov_cancelado.apellidoM_empleado}'/>" onkeypress="pulsar_enter(event, formLiquidation, 'CuentaDeposito:28')" />
                                        </td>
                                    </tr>

                                    <tr>
                                        <td align="right" class="SubTitulo">Identificador:</td>
                                        <td>
                                            <input  type="text"  style="width:160px;" name="id_empleado" value="<c:out value='${sessionScope.mov_cancelado.numero_empleado}'/>" onkeypress="pulsar_enter(event, formLiquidation, 'CuentaDeposito:28')"/>
                                        </td>
                                        <td align="right" class="SubTitulo">Puesto:</td>
                                        <td>
                                            <input  type="text"  style="width:160px;" name="puesto_empleado" value="<c:out value='${sessionScope.mov_cancelado.puesto_empleado}'/>" onkeypress="pulsar_enter(event, formLiquidation, 'CuentaDeposito:28')"/>
                                        </td>
                                    </tr>

                                    <tr>
                                        <td align="right" class="SubTitulo">Departamento:</td>
                                        <td  >
                                            <input  type="text"  style="width:160px;" name="depto_empleado" value="<c:out value='${sessionScope.mov_cancelado.departamento_empleado}'/>" onkeypress="pulsar_enter(event, formLiquidation, 'CuentaDeposito:28')"/>
                                        </td>
                                        <td align="right" class="SubTitulo">Fecha Ingreso:</td>
                                        <td>
                                            <input type="text"  name="fecha_ingreso" id="dateArrival"  value="<c:out value='${sessionScope.mov_cancelado.fecha_ingreso}'/>" onClick="return popUpCalendar(this, formLiquidation.dateArrival, 'yyyy-mm-dd');" size="10">
                                        </td>
                                    </tr>

                                    <tr>
                                        <td align="right" class="SubTitulo">Movimiento:</td>
                                        <td >
                                            <input type="text" style="width:160px;" name="tipo_movimiento" value="<c:out value='${sessionScope.mov_cancelado.tipo_movimiento}'/>" onkeypress="pulsar_enter(event, formLiquidation, 'CuentaDeposito:28')"/>
                                        </td>
                                        <td align="right" class="SubTitulo">Clave de Banco:</td>
                                        <td >
                                            <input  type="text"  style="width:160px;" name="clave_banco" value="<c:out value='${sessionScope.mov_cancelado.clave_banco}'/>" onkeypress="pulsar_enter(event, formLiquidation, 'CuentaDeposito:28')"/>
                                        </td>
                                    </tr>

                                    <tr>
                                        <td align="right" class="SubTitulo">Importe:</td>
                                        <td >
                                            <input  type="text"  style="width:160px;" name="importe_liquidacion" value="<c:out value='${sessionScope.mov_cancelado.importe_liquidacion}'/>" onkeypress="pulsar_enter(event, formLiquidation, 'CuentaDeposito:28')"/>
                                        </td>
                                        <td align="right" class="SubTitulo">Moneda:</td>
                                        <td  >
                                            <input  type="text"  style="width:160px;" name="tipo_moneda" value="<c:out value='${sessionScope.mov_cancelado.clave_moneda}'/>" onkeypress="pulsar_enter(event, formLiquidation, 'CuentaDeposito:28')"/>
                                        </td>
                                    </tr>

                                    <tr>
                                        <td align="right" class="SubTitulo">Envio Cheque:</td>
                                        <td>
                                            <input  type="text"  style="width:160px;" name="envio_cheque" value="<c:out value='${sessionScope.mov_cancelado.nombre_receptor_cheque}'/>" onkeypress="pulsar_enter(event, formLiquidation, 'CuentaDeposito:28')"/>
                                        </td>
                                        <td align="right" class="SubTitulo">Destino Cheque:</td>
                                        <td>
                                            <input  type="text"  style="width:290px;" name="destino_cheque" value="<c:out value='${sessionScope.mov_cancelado.domicilio_destino_cheque}'/>" onkeypress="pulsar_enter(event, formLiquidation, 'CuentaDeposito:28')"/>
                                        </td>
                                    </tr>


                                    <tr>
                                        <td align="right" class="SubTitulo">Teléfono Cheque:</td>
                                        <td>
                                            <input  type="text"  style="width:160px;" name="telefono_cheque" value="<c:out value='${sessionScope.mov_cancelado.tel_destino_cheque}'/>" onkeypress="pulsar_enter(event, formLiquidation, 'CuentaDeposito:28')"/>
                                        </td>
                                        <td align="right" class="SubTitulo">Correo Cheque:</td>
                                        <td>
                                            <input  type="text"  style="width:160px;" name="correo_cheque" value="<c:out value='${sessionScope.mov_cancelado.correo_destino_cheque}'/>" onkeypress="pulsar_enter(event, formLiquidation, 'CuentaDeposito:28')"/>
                                        </td>
                                    </tr>

                                    <tr>
                                        <td align="right" class="SubTitulo">Banco Extranjero:</td>
                                        <td>
                                            <input  type="text"  style="width:160px;" name="banco_extranjero" value="<c:out value='${sessionScope.mov_cancelado.nombre_banco_extranjero}'/>" onkeypress="pulsar_enter(event, formLiquidation, 'CuentaDeposito:28')"/>
                                        </td>
                                        <td align="right" class="SubTitulo">Dir Banco Extranjero:</td>
                                        <td>
                                            <input  type="text"  style="width:290px;" name="domicilio_extranjero" value="<c:out value='${sessionScope.mov_cancelado.domicilio_banco_extranjero}'/>" onkeypress="pulsar_enter(event, formLiquidation, 'CuentaDeposito:28')"/>
                                        </td>
                                    </tr>

                                    <tr>
                                        <td align="right" class="SubTitulo"> Pais Banco Extranjero:</td>
                                        <td>
                                            <input  type="text"  style="width:160px;" name="pais_extranjero" value="<c:out value='${sessionScope.mov_cancelado.pais_banco_extranjero}'/>" onkeypress="pulsar_enter(event, formLiquidation, 'CuentaDeposito:28')"/>
                                        </td>
                                        <td align="right" class="SubTitulo">ABA/BIC:</td>
                                        <td>
                                            <input  type="text"  style="width:160px;" name="aba_bic" value="<c:out value='${sessionScope.mov_cancelado.ABA_BIC}'/>" onkeypress="pulsar_enter(event, formLiquidation, 'CuentaDeposito:28')"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="right" class="SubTitulo"> Nombre Fideicomisario:</td>
                                        <td>
                                            <input  type="text"  style="width:160px;" name="nombre_fidei_extranjero" value="<c:out value='${sessionScope.mov_cancelado.nombre_empleado_banco_extranjero}'/>" onkeypress="pulsar_enter(event, formLiquidation, 'CuentaDeposito:28')"/>
                                        </td>
                                        <td align="right" class="SubTitulo">Dirección Extranjero:</td>
                                        <td>
                                            <input  type="text"  style="width:290px;" name="direccion_fidei_extranjero" value="<c:out value='${sessionScope.mov_cancelado.dom_empleado_banco_extranjero}'/>" onkeypress="pulsar_enter(event, formLiquidation, 'CuentaDeposito:28')"/>
                                        </td>
                                    </tr>

                                    <tr>
                                        <td align="right" class="SubTitulo"> País Extranjero:</td>
                                        <td>
                                            <input  type="text"  style="width:160px;" name="pais_fidei_extranjero" value="<c:out value='${sessionScope.mov_cancelado.pais_empleado_banco_extranjero}'/>" onkeypress="pulsar_enter(event, formLiquidation, 'CuentaDeposito:28')"/>
                                        </td>
                                        <td align="right" class="SubTitulo">Teléfono Extranjero:</td>
                                        <td>
                                            <input  type="text"  style="width:160px;" name="tel_fidei_extranjero" value="<c:out value='${sessionScope.mov_cancelado.tel_empleado_banco_extranjero}'/>" onkeypress="pulsar_enter(event, formLiquidation, 'CuentaDeposito:28')"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>&nbsp; </td>
                                    </tr>
                                    <tr>
                                        <td align="center"  colspan="4"  class="Subtitulo" style="font-size:12px;"><b>Motivo de Cancelación:</b></td>
                                    </tr>
                                    <tr>
                                        <td  class="LetraInput" colspan="4" align="center">
                                            <SELECT class="LetraInput" NAME="selComboMotivoCancelacion"  onchange="return setActionComboMotivoCancelacion(formLiquidation, 'MotivoCancelación:19');">
                                                <c:forEach items="${sessionScope.motivosCancelacion}" var="motivo" varStatus="status">
                                                    <c:choose>
                                                        <c:when test="${sessionScope.motivo_c != null }">
                                                            <c:choose>
                                                                <c:when test="${sessionScope.motivo_c == motivo }">
                                                                    <OPTION selected VALUE="dataC<%=cpc%>"><c:out value="${motivo}"/></OPTION>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                    <OPTION VALUE="dataC<%=cpc%>"><c:out value="${motivo}"/></OPTION>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:when>
                                                            <c:otherwise>
                                                            <OPTION VALUE="dataC<%=cpc%>"><c:out value="${motivo}"/></OPTION>
                                                            </c:otherwise>
                                                        </c:choose>
                                                        <% cpc++;%>
                                                    </c:forEach>
                                            </SELECT>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>&nbsp; </td>
                                    </tr>
                                    <tr>
                                        <td colspan="4" align="center">
                                            <input type="button"  name="cancelaMovsPacial" class="NormalBoton" value="Cancelar Movimiento" onClick="setActionCancel(formLiquidation, 'CancelarMovimiento:29')"/>
                                            <input type="button"  name="exitMov" class="NormalBoton" value="Cancelar" onClick="setAction(formLiquidation, 'LimpiaPantallaC:32')"/>
                                        </td>
                                    </tr>
                                    <tr><td colspan="4">&nbsp;</td></tr>
                                </table>
                            </c:if>

                            <c:if test="${sessionScope.opcionH != null && sessionScope.opcionC == null }">
                                <table align="center"  class="form-inWhite" style="background-color: #ddf8cc;">
                                    <tr>
                                        <td align="right" class="SubTitulo">Cuenta Depósito:</td>
                                        <td>
                                            <input type="text" class="LetraInput" name="cuenta_deposito" value="<c:out value='${sessionScope.mov_cancelado.cuenta_deposito}'/>" onkeypress="pulsar_enter(event, formLiquidation, 'CuentaDeposito:30')"/>
                                        </td>
                                        <td  align="right" class="SubTitulo">Nombre(s):</td>
                                        <td>
                                            <input type="text" class="LetraInput" name="nombre_empleado" value="<c:out value='${sessionScope.mov_cancelado.nombre_empleado}'/>" onkeypress="pulsar_enter(event, formLiquidation, 'CuentaDeposito:30')"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="right" class="SubTitulo">Apellido Paterno:</td>
                                        <td>
                                            <input type="text" class="LetraInput" name="apellidoP_empleado" value="<c:out value='${sessionScope.mov_cancelado.apellidoP_empleado}'/>" onkeypress="pulsar_enter(event, formLiquidation, 'CuentaDeposito:30')"/>
                                        </td>
                                        <td align="right" class="SubTitulo">Apellido Materno:</td>
                                        <td>
                                            <input type="text" class="LetraInput" name="apellidoM_empleado" value="<c:out value='${sessionScope.mov_cancelado.apellidoM_empleado}'/>" onkeypress="pulsar_enter(event, formLiquidation, 'CuentaDeposito:30')"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="right" class="SubTitulo">Identificador:</td>
                                        <td>
                                            <input type="text" class="LetraInput" name="numero_empleado" value="<c:out value='${sessionScope.mov_cancelado.numero_empleado}'/>" onkeypress="pulsar_enter(event, formLiquidation, 'CuentaDeposito:30')"/>
                                        </td>
                                        <td align="right" class="SubTitulo">Puesto:</td>
                                        <td>
                                            <input type="text" class="LetraInput" name="puesto_empleado" value="<c:out value='${sessionScope.mov_cancelado.puesto_empleado}'/>" onkeypress="pulsar_enter(event, formLiquidation, 'CuentaDeposito:30')"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="right" class="SubTitulo">Departamento:</td>
                                        <td>
                                            <input type="text" class="LetraInput" name="departamento_empleado" value="<c:out value='${sessionScope.mov_cancelado.departamento_empleado}'/>" onkeypress="pulsar_enter(event, formLiquidation, 'CuentaDeposito:30')"/>
                                        </td>
                                        <td align="right" class="SubTitulo">Fecha Ingreso:</td>
                                        <td>
                                            <input type="text" class="LetraInput" name="fecha_ingreso" id="dateArrival" value="<c:out value='${sessionScope.mov_cancelado.fecha_ingreso}'/>" onClick="return popUpCalendar(this, formLiquidation.dateArrival, 'yyyy-mm-dd');" size="10"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="right" class="SubTitulo">Movimiento:</td>
                                        <td>
                                            <input type="text" class="LetraInput" name="tipo_movimiento" value="<c:out value='${sessionScope.mov_cancelado.tipo_movimiento}'/>" onkeypress="pulsar_enter(event, formLiquidation, 'CuentaDeposito:30')"/>
                                        </td>
                                        <td align="right" class="SubTitulo">Clave de Banco:</td>
                                        <td>
                                            <input type="text" class="LetraInput" name="clave_banco" value="<c:out value='${sessionScope.mov_cancelado.clave_banco}'/>" onkeypress="pulsar_enter(event, formLiquidation, 'CuentaDeposito:30')"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="right" class="SubTitulo">Importe:</td>
                                        <td>
                                            <input type="text" class="LetraInput" name="importe_liquidacion" value="<c:out value='${sessionScope.mov_cancelado.importe_liquidacion}'/>" onkeypress="pulsar_enter(event, formLiquidation, 'CuentaDeposito:30')"/>
                                        </td>
                                        <td align="right" class="SubTitulo">Moneda:</td>
                                        <td>
                                            <input type="text" class="LetraInput" name="clave_moneda" value="<c:out value='${sessionScope.mov_cancelado.clave_moneda}'/>" onkeypress="pulsar_enter(event, formLiquidation, 'CuentaDeposito:30')"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="right" class="SubTitulo">Envío Cheque:</td>
                                        <td>
                                            <input type="text" class="LetraInput" name="nombre_receptor_cheque" value="<c:out value='${sessionScope.mov_cancelado.nombre_receptor_cheque}'/>" onkeypress="pulsar_enter(event, formLiquidation, 'CuentaDeposito:30')"/>
                                        </td>
                                        <td align="right" class="SubTitulo">Destino Cheque:</td>
                                        <td>
                                            <input type="text" class="LetraInput" name="domicilio_destino_cheque" style="width:290px;" value="<c:out value='${sessionScope.mov_cancelado.domicilio_destino_cheque}'/>" onkeypress="pulsar_enter(event, formLiquidation, 'CuentaDeposito:30')"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="right" class="SubTitulo">Teléfono Cheque:</td>
                                        <td>
                                            <input type="text" class="LetraInput" name="tel_destino_cheque" value="<c:out value='${sessionScope.mov_cancelado.tel_destino_cheque}'/>" onkeypress="pulsar_enter(event, formLiquidation, 'CuentaDeposito:30')"/>
                                        </td>
                                        <td align="right" class="SubTitulo">Correo Cheque:</td>
                                        <td>
                                            <input type="text" class="LetraInput" name="correo_destino_cheque" value="<c:out value='${sessionScope.mov_cancelado.correo_destino_cheque}'/>" onkeypress="pulsar_enter(event, formLiquidation, 'CuentaDeposito:30')"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="right" class="SubTitulo">Banco Extranjero:</td>
                                        <td>
                                            <input type="text" class="LetraInput" name="nombre_banco_extranjero" value="<c:out value='${sessionScope.mov_cancelado.nombre_banco_extranjero}'/>" onkeypress="pulsar_enter(event, formLiquidation, 'CuentaDeposito:30')"/>
                                        </td>
                                        <td align="right" class="SubTitulo">Domicilio Banco Extranjero:</td>
                                        <td>
                                            <input type="text" class="LetraInput" name="domicilio_banco_extranjero" style="width:290px;" value="<c:out value='${sessionScope.mov_cancelado.domicilio_banco_extranjero}'/>" onkeypress="pulsar_enter(event, formLiquidation, 'CuentaDeposito:30')"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="right" class="SubTitulo">País Banco Extranjero:</td>
                                        <td>
                                            <input type="text" class="LetraInput" name="pais_banco_extranjero" value="<c:out value='${sessionScope.mov_cancelado.pais_banco_extranjero}'/>" onkeypress="pulsar_enter(event, formLiquidation, 'CuentaDeposito:30')"/>
                                        </td>
                                        <td align="right" class="SubTitulo">ABA/BIC:</td>
                                        <td>
                                            <input type="text" class="LetraInput" name="ABA_BIC" value="<c:out value='${sessionScope.mov_cancelado.ABA_BIC}'/>" onkeypress="pulsar_enter(event, formLiquidation, 'CuentaDeposito:30')"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="right" class="SubTitulo">Nombre Fideicomisario:</td>
                                        <td>
                                            <input type="text" class="LetraInput" name="nombre_empleado_banco_extranjero" value="<c:out value='${sessionScope.mov_cancelado.nombre_empleado_banco_extranjero}'/>" onkeypress="pulsar_enter(event, formLiquidation, 'CuentaDeposito:30')"/>
                                        </td>
                                        <td align="right" class="SubTitulo">Dirección Extranjero:</td>
                                        <td>
                                            <input type="text" class="LetraInput" name="dom_empleado_banco_extranjero" style="width:290px;" value="<c:out value='${sessionScope.mov_cancelado.dom_empleado_banco_extranjero}'/>" onkeypress="pulsar_enter(event, formLiquidation, 'CuentaDeposito:30')"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="right" class="SubTitulo">País Extranjero:</td>
                                        <td>
                                            <input type="text" class="LetraInput" name="pais_empleado_banco_extranjero" value="<c:out value='${sessionScope.mov_cancelado.pais_empleado_banco_extranjero}'/>" onkeypress="pulsar_enter(event, formLiquidation, 'CuentaDeposito:30')"/>
                                        </td>
                                        <td align="right" class="SubTitulo">Teléfono Extranjero:</td>
                                        <td>
                                            <input type="text" class="LetraInput" name="tel_empleado_banco_extranjero" value="<c:out value='${sessionScope.mov_cancelado.tel_empleado_banco_extranjero}'/>" onkeypress="pulsar_enter(event, formLiquidation, 'CuentaDeposito:30')"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="right" class="SubTitulo">Motivo Cancelación:</td>
                                        <td colspan ="3">
                                            <input type="text" class="LetraInput" name="m_cancelacion" style="width:370px;" value="<c:out value='${sessionScope.mov_cancelado.motivo_cancelacion}'/>" onkeypress="pulsar_enter(event, formLiquidation, 'CuentaDeposito:30')"/>
                                        </td>
                                    </tr>
                                    <tr><td colspan="4">&nbsp;</td></tr>
                                    <tr>
                                        <td colspan="4" align="center">
                                            <input type="button"  name="habilitarMov" class="NormalBoton" value="Habilitar" onClick="setAction(formLiquidation, 'HabilitarMovimiento:31')" />
                                            <input type="button"  name="exitMov" class="NormalBoton" value="Salir" onClick="setAction(formLiquidation, 'LimpiaPantallaH:32')"/>
                                        </td>
                                    </tr>
                                    <tr><td colspan="4">&nbsp;</td></tr>
                                </table>
                            </c:if>

                        </c:if>
                        <%--Módulo:Cancelación Total de Movimientos--%>
                        <c:if test="${sessionScope.cancelacionTotal != null && sessionScope.confirmacionLote == null && sessionScope.cancelacionParcial == null}">
                            <table align="center" class="form-noindentCaptureCT">
                                <% int ctc = 0;%>
                                <tr><td colspan="4">&nbsp;</td></tr>
                                <tr align="center" ><td  class="Titulo" colspan="4">CANCELACIÓN DE LOTE</td></tr>
                                <tr><td colspan="4">&nbsp;</td></tr>
                                <tr>
                                    <td  align="right" class="SubTitulo">Clave de Fideicomiso:</td>
                                    <td colspan="3">
                                        <SELECT NAME="selComboClaveFiso" onchange="return setActionComboClaveFiso(formLiquidation, 'AutorizaLoteCliente:19');">
                                            <c:forEach items="${sessionScope.cliMovConfirma}" var="client" varStatus="status">
                                                <c:choose>
                                                    <c:when test="${sessionScope.clave_contratoC != null }">
                                                        <c:choose>
                                                            <c:when test="${sessionScope.clave_contratoC == client }">
                                                                <OPTION selected VALUE="dataC<%=ctc%>"><c:out value="${client}"/></OPTION>
                                                                </c:when>
                                                                <c:otherwise>
                                                                <OPTION VALUE="dataC<%=ctc%>"><c:out value="${client}"/></OPTION>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </c:when>
                                                        <c:otherwise>
                                                        <OPTION VALUE="dataC<%=ctc%>"><c:out value="${client}"/></OPTION>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <% ctc++;%>
                                                </c:forEach>
                                        </SELECT>
                                    </td>
                                </tr>

                                <c:if test="${sessionScope.cliente_c != null && sessionScope.fechas_liquidacionC != null }">
                                    <tr>
                                        <td  align="right" class="SubTitulo">Fideicomitente:</td>
                                        <td align="left" colspan="3">
                                            <input class="LetraInput" type="text" disabled="disabled"  style="width:360px;" name="FideicomitenteC" value="<c:out value='${sessionScope.cliente_c}'/>" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <% int fct = 0;%>
                                        <td  align="right" class="SubTitulo">Fecha de Liquidación:</td>
                                        <td colspan="3" >
                                            <SELECT NAME="selComboDateC" onchange="return setActionComboDateC(formLiquidation, 'AutorizaLoteFecha:20');">
                                                <c:forEach items="${sessionScope.fechas_liquidacionC}" var="date" varStatus="status">
                                                    <c:choose>
                                                        <c:when test="${sessionScope.fecha_c != null }">
                                                            <c:choose>
                                                                <c:when test="${sessionScope.fecha_c == date }">
                                                                    <OPTION selected VALUE="dateC<%=fct%>"><c:out value="${date}"/></OPTION>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                    <OPTION VALUE="dateC<%=fct%>"><c:out value="${date}"/></OPTION>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:when>
                                                            <c:otherwise>
                                                            <OPTION VALUE="dateC<%=fct%>"><c:out value="${date}"/></OPTION>
                                                            </c:otherwise>
                                                        </c:choose>
                                                        <% fct++;%>
                                                    </c:forEach>
                                            </SELECT>
                                        </td>
                                    </tr>
                                </c:if>


                                <c:if test="${sessionScope.lotes_c != null }">
                                    <tr>
                                        <% int lct = 0;%>
                                        <td  align="right" class="SubTitulo">LayOut Cargado:</td>
                                        <td colspan="3" >
                                            <SELECT NAME="selComboFileNameC" onchange="return setActionComboFileNameC(formLiquidation, 'AutorizaLoteNombreArchivo:21');">
                                                <c:forEach items="${sessionScope.lotes_c}" var="date" varStatus="status">
                                                    <c:choose>
                                                        <c:when test="${sessionScope.lote_c != null }">
                                                            <c:choose>
                                                                <c:when test="${sessionScope.lote_c == date }">
                                                                    <OPTION selected VALUE="loteC<%=lct%>"><c:out value="${date}"/></OPTION>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                    <OPTION VALUE="loteC<%=lct%>"><c:out value="${date}"/></OPTION>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:when>
                                                            <c:otherwise>
                                                            <OPTION VALUE="loteC<%=lct%>"><c:out value="${date}"/></OPTION>
                                                            </c:otherwise>
                                                        </c:choose>
                                                        <% lct++;%>
                                                    </c:forEach>
                                            </SELECT>
                                        </td>
                                    </tr>
                                </c:if>

                                <c:if test="${sessionScope.fecha_capturaC != null }">
                                    <tr>
                                        <td  align="right" class="SubTitulo">Fecha de Carga:</td>
                                        <td align="left">
                                            <input class="LetraInput" type="text" disabled="disabled"  style="width:150px;" name="LayOutC" value="<c:out value='${sessionScope.fecha_capturaC}'/>" />
                                        </td>
                                        <td  align="right" class="SubTitulo">Movimientos:</td>
                                        <td align="left">
                                            <input class="LetraInput" type="text" disabled="disabled"  style="width:80px;" name="totalMovs" value="<c:out value='${sessionScope.total_movimientos}'/>" />
                                        </td>
                                    </tr>
                                    <tr><td colspan="4">&nbsp;</td></tr>
                                    <tr>
                                        <td colspan="4" align="center">
                                            <input type="button"  name="CancelaTotal" value="Aceptar" onClick="setAction(formLiquidation, 'CancelacionTotalLote:24')"/>
                                        </td>
                                    </tr>
                                    <tr><td colspan="4">&nbsp;</td></tr>
                                    <tr> </tr>
                                </c:if>
                            </table>
                        </c:if>
                    </c:if>

                    <%--Módulo:Actualización de movimientos a bancos extranjeros--%>
                    <c:if test="${sessionScope.modActualiza != null && sessionScope.cliMov5Pend != null}">
                        <table align="center" class="LetraInput" >
                            <% int j = 0;%>
                            <tr><td colspan="2">&nbsp;</td></tr>
                            <tr align="center" class="Titulo"><td colspan="2"> MOVIMIENTOS A BANCOS EXTRANJEROS </td></tr>
                            <tr><td colspan="2">&nbsp;</td></tr>
                            <c:if test="${param.accionImporteM5 == null}">
                                <tr id="seleccionInicio">
                                    <td><input type="button" class="botonSeleccionAccion" onclick="return setAction(formLiquidation, 'ActualizarMovsExtranjeros:11');" value="Actualizar" style="cursor: pointer;"></td>
                                    <td><input type="button" class="botonSeleccionAccion" onclick="return setAction(formLiquidation, 'ModificarMovsExtranjeros:11');" value="Modificar" style="cursor: pointer;"></td>
                                </tr>
                            </c:if>
                            <c:if test="${param.accionImporteM5 != null}">
                                <td align='center' colspan='2'>
                                    <c:if test="${param.accionImporteM5 eq 'actualizaImporteM5'}">
                                        <button disabled class='botonSeleccionAccion' style="border-style: hidden;"> Actualizar </button>
                                        <input type='hidden' name='accionImporteM5' value='actualizaImporteM5'/>
                                    </c:if>
                                    <c:if test="${param.accionImporteM5 eq 'modificaImporteM5'}">
                                        <button disabled class='botonSeleccionAccion' style="border-style: hidden;"> Modificar </button>
                                        <input type='hidden' name='accionImporteM5' value='modificaImporteM5'/>
                                    </c:if>
                                </td>
                            </c:if>
                            <tr><td colspan="2">&nbsp;</td></tr>
                            <tr>
                                <td colspan="2">
                                    <table align="center" >
                                        <c:if test="${param.accionImporteM5 != null}">
                                            <tr id="inicio">
                                                <td class="SubTitulo" align="right">Clave de Fideicomiso:</td>
                                                <td>
                                                    <SELECT NAME="selComboCliMov5" onchange="return setActionComboCliMov5(formLiquidation, 'SelCliMov5:12');">
                                                        <c:forEach items="${sessionScope.cliMov5Pend}" var="client" varStatus="status">
                                                            <c:choose>
                                                                <c:when test="${sessionScope.c_contrato != null }">
                                                                    <c:choose>
                                                                        <c:when test="${sessionScope.c_contrato == client }">
                                                                            <OPTION selected VALUE="client<%=j%>"><c:out value="${client}"/></OPTION>
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                            <OPTION VALUE="client<%=j%>"><c:out value="${client}"/></OPTION>
                                                                            </c:otherwise>
                                                                        </c:choose>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                    <OPTION VALUE="client<%=j%>"><c:out value="${client}"/></OPTION>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                                <% j++;%>
                                                            </c:forEach>
                                                    </SELECT>
                                                </td>
                                            </tr>
                                        </c:if>
                                        <c:if test="${sessionScope.nombre_fideicomitente != null}">
                                            <tr>
                                                <td class="SubTitulo" align="right">Fideicomitente:</td>
                                                <td  align="left">
                                                    <input  type="text" class="LetraInput" disabled="disabled" style="width:380px;" name="n_fideicomitente" value="<c:out value='${sessionScope.nombre_fideicomitente}'/>" />
                                                </td>
                                            </tr>
                                        </c:if>

                                        <c:if test="${sessionScope.dateLiqMov5 != null}">
                                            <tr>
                                                <td class="SubTitulo" align="right">Fecha de Liquidación:</td>
                                                <td colspan="2" >
                                                    <SELECT NAME="selComboDateMov5" onchange="return setActionComboDateMov5(formLiquidation, 'selDateMov5:13');">
                                                        <c:forEach items="${sessionScope.dateLiqMov5}" var="date" varStatus="status">
                                                            <c:choose>
                                                                <c:when test="${sessionScope.selDateLiqMov5 != null }">
                                                                    <c:choose>
                                                                        <c:when test="${sessionScope.selDateLiqMov5 == date }">
                                                                            <OPTION selected VALUE="dateMov5<%=j%>"><c:out value="${date}"/></OPTION>
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                            <OPTION VALUE="dateMov5<%=j%>"><c:out value="${date}"/></OPTION>
                                                                            </c:otherwise>
                                                                        </c:choose>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                    <OPTION VALUE="dateMov5<%=j%>"><c:out value="${date}"/></OPTION>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                                <% j++;%>
                                                            </c:forEach>
                                                    </SELECT>
                                                </td>
                                            </tr>
                                        </c:if>

                                        <c:if test="${sessionScope.filesNameMov5 != null}">
                                            <tr>
                                                <td class="SubTitulo" align="right">Archivo cargado</td>
                                                <td colspan="2">
                                                    <SELECT NAME="selComboFileNameMov5" onchange="return setActionComboFileNameMov5(formLiquidation, 'selFileNameMov5:14');">
                                                        <c:forEach items="${sessionScope.filesNameMov5}" var="file" varStatus="status">
                                                            <c:choose>
                                                                <c:when test="${sessionScope.selFileNameMov5 != null }">
                                                                    <c:choose>
                                                                        <c:when test="${sessionScope.selFileNameMov5 == file }">
                                                                            <OPTION selected VALUE="fileMov5<%=j%>"><c:out value="${file}"/></OPTION>
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                            <OPTION VALUE="fileMov5<%=j%>"><c:out value="${file}"/></OPTION>
                                                                            </c:otherwise>
                                                                        </c:choose>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                    <OPTION VALUE="fileMov5<%=j%>"><c:out value="${file}"/></OPTION>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                                <% j++;%>
                                                            </c:forEach>
                                                    </SELECT>
                                                </td>
                                            </tr>
                                        </c:if>

                                        <c:if test="${sessionScope.fechaUsuarioOpera != null && sessionScope.usuario_opera_lote != null}">
                                            <tr>
                                                <td class="SubTitulo">Asesor Opera:</td>
                                                <td>
                                                    <input class="LetraInput" type="text" size="32"  disabled="disabled" name="nameAsesor" value="<c:out value='${sessionScope.usuario_opera_lote}'/>" />
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="SubTitulo">Fecha Transacción:</td>
                                                <td>
                                                    <input class="LetraInput" type="text"  disabled="disabled" name="DayGenTran" value="<c:out value='${sessionScope.fechaUsuarioOpera}'/>" />
                                                </td>
                                            </tr>
                                            <tr><td colspan="2">&nbsp;</td></tr>
                                        </c:if>
                                    </table>

                                    <c:if test="${sessionScope.fechaUsuarioOpera != null}">
                                        <div align="center">
                                            <fieldset class="TamDivMov5">
                                                <legend class="Subtitulo"> Importe </legend>
                                                <table align="center"  >
                                                    <tr>
                                                        <td class="SubTitulo">Fideicomisario en Extranjero:</td>
                                                        <td colspan="2">
                                                            <input class="LetraInput" type="text" disabled="disabled" name="selComboNameFideiMov5" value="<c:out value='${sessionScope.namesFideiMov5}'/>" />
                                                        </td>
                                                    </tr>
                                                    <c:if test="${sessionScope.selNameFideiMov5 != null }">
                                                        <c:if test="${sessionScope.importeMonedaExtranjera != null}">
                                                            <tr>
                                                                <td class="SubTitulo">Moneda Extranjera:</td>
                                                                <td>
                                                                    <input class="LetraInput" type="text"  size="20" disabled="disabled" name="Monto_ME" id="Monto_ME" value="<c:out value='${sessionScope.importeMonedaExtranjera}'/>" />
                                                                </td>
                                                                <td>
                                                                    <input class="LetraInput" type="text"  size="8" disabled="disabled" name="tipo_moneda" value=" <c:out value='${sessionScope.tipo_moneda}'/>" />
                                                                </td>
                                                            </tr>
                                                            <tr id="ingresarValorDivisa">
                                                                <td class="SubTitulo">Ingresar valor de divisa:</td>
                                                                <td>
                                                                    <input class="LetraInput" type="text" size="20"  name="valorDivisa" id="valorDivisa" onkeyup="eventoComas('valorDivisa');"/>
                                                                </td>
                                                                <td>
                                                                    <input type="button" class="NormalBoton" value="Calcular" onClick="calcularValorMXP();"/>
                                                                </td>
                                                            </tr>
                                                        </c:if>
                                                        <tr>
                                                            <td class="SubTitulo">Moneda Nacional:</td>
                                                            <td>
                                                                <input class="LetraInput" type="text"  id="monto_mxp"  onkeypress="return validaInputNum(event)" value="<c:out value='${requestScope.monto_mxp}'/>"/>
                                                            </td>
                                                            <td>
                                                                <input class="LetraInput" type="text"  size="8" disabled="disabled" value="MXP" />
                                                            </td>
                                                        </tr>

                                                        <tr><td colspan="2">&nbsp;</td></tr>

                                                        <div class="check">
                                                            <tr>
                                                                <td id="btnModificar" align="center">
                                                                    <input type="button" class="NormalBoton" name="Modificar" value="Modificar" onClick="habilitarModificacion()"/>
                                                                </td>
                                                                <td id="btnActualizar" align="center">
                                                                    <input type="button" class="NormalBoton" name="Actualiza" value="Actualizar" onClick="setActualiza(formLiquidation, 'ActualizaMov5:16')"/>
                                                                </td>
                                                                <td id="btnCancelar" align="center">
                                                                    <input type="button" class="NormalBoton" value="Cancelar" onClick="cancelarModificacion()"/>
                                                                </td>
                                                            </tr>
                                                        </div>
                                                        <c:if test="${param.accionImporteM5 != null && param.accionImporteM5 eq 'modificaImporteM5'}">
                                                            <script>
                                                                var monto_mxp;
                                                                window.onload = function () {
                                                                    monto_mxp = document.getElementById('monto_mxp').value;
                                                                    document.getElementById('ingresarValorDivisa').style.display = 'none';
                                                                    document.getElementById('btnActualizar').style.display = 'none';
                                                                    document.getElementById('btnCancelar').style.display = 'none';
                                                                    document.getElementById('btnModificar').setAttribute('colspan', '3');
                                                                    document.getElementById('monto_mxp').setAttribute('disabled', 'disabled');
                                                                };
                                                                function habilitarModificacion() {
                                                                    document.getElementById('ingresarValorDivisa').style.display = '';
                                                                    document.getElementById('btnActualizar').style.display = '';
                                                                    document.getElementById('btnCancelar').style.display = '';
                                                                    document.getElementById('btnModificar').style.display = 'none';
                                                                    document.getElementById('monto_mxp').removeAttribute('disabled');
                                                                    document.getElementById("valorDivisa").value = '0';
                                                                }
                                                                function cancelarModificacion() {
                                                                    document.getElementById('ingresarValorDivisa').style.display = 'none';
                                                                    document.getElementById('btnActualizar').style.display = 'none';
                                                                    document.getElementById('btnCancelar').style.display = 'none';
                                                                    document.getElementById('btnModificar').style.display = '';
                                                                    document.getElementById('monto_mxp').value = monto_mxp;
                                                                    document.getElementById('monto_mxp').setAttribute('disabled', 'disabled');
                                                                }
                                                            </script>
                                                        </c:if>
                                                        <c:if test="${param.accionImporteM5 != null && param.accionImporteM5 eq 'actualizaImporteM5'}">
                                                            <script>
                                                                window.onload = function () {
                                                                    document.getElementById('btnModificar').style.display = 'none';
                                                                    document.getElementById('btnCancelar').style.display = 'none';
                                                                    document.getElementById('btnActualizar').setAttribute('colspan', '3');
                                                                };
                                                            </script>
                                                        </c:if>
                                                    </c:if>
                                                </table>
                                            </fieldset>
                                        </div>
                                    </c:if>

                                </td>
                            </tr>
                        </table>
                    </c:if>
                    <%--Módulo:Autorización de Movimientos--%>
                    <br>
                    <c:if test="${sessionScope.confirmacionLote != null && cliMovConfirma!=null && sessionScope.cancelacionParcial == null && sessionScope.cancelacionTotal == null}">
                        <table align="center" class="form-noindentCaptureA" >
                            <% int acc = 0;%>
                            <tr><td colspan="4">&nbsp;</td></tr>
                            <tr align="center" ><td  class="Titulo" colspan="4"> AUTORIZACIÓN DE LOTE </td></tr>
                            <tr><td colspan="4">&nbsp;</td></tr>
                            <tr>
                                <td align="right" class="SubTitulo" >Clave de Fideicomiso:</td>
                                <td colspan="3">
                                    <SELECT NAME="selComboClaveFiso" onchange="return setActionComboClaveFiso(formLiquidation, 'AutorizaLoteCliente:19');">
                                        <c:forEach items="${sessionScope.cliMovConfirma}" var="client" varStatus="status">
                                            <c:choose>
                                                <c:when test="${sessionScope.clave_contratoC != null }">
                                                    <c:choose>
                                                        <c:when test="${sessionScope.clave_contratoC == client }">
                                                            <OPTION selected VALUE="dataC<%=acc%>"><c:out value="${client}"/></OPTION>
                                                            </c:when>
                                                            <c:otherwise>
                                                            <OPTION VALUE="dataC<%=acc%>"><c:out value="${client}"/></OPTION>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:when>
                                                    <c:otherwise>
                                                    <OPTION VALUE="dataC<%=acc%>"><c:out value="${client}"/></OPTION>
                                                    </c:otherwise>
                                                </c:choose>
                                                <% acc++;%>
                                            </c:forEach>
                                    </SELECT>
                                </td>
                            </tr>
                            <c:if test="${sessionScope.cliente_c != null && sessionScope.fechas_liquidacionC != null }">
                                <tr>
                                    <td align="right" class="SubTitulo">Fideicomitente:</td>
                                    <td align="left" colspan="3">
                                        <input class="LetraInput" type="text" disabled="disabled"  style="width:360px;" name="FideicomitenteC" value="<c:out value='${sessionScope.cliente_c}'/>" />
                                    </td>
                                </tr>
                                <tr>
                                    <% int acf = 0;%>
                                    <td align="right" class="SubTitulo" >Fecha de Liquidación:</td>
                                    <td colspan="3" >
                                        <SELECT NAME="selComboDateC" onchange="return setActionComboDateC(formLiquidation, 'AutorizaLoteFecha:20');">
                                            <c:forEach items="${sessionScope.fechas_liquidacionC}" var="date" varStatus="status">
                                                <c:choose>
                                                    <c:when test="${sessionScope.fecha_c != null }">
                                                        <c:choose>
                                                            <c:when test="${sessionScope.fecha_c == date }">
                                                                <OPTION selected VALUE="dateC<%=acf%>"><c:out value="${date}"/></OPTION>
                                                                </c:when>
                                                                <c:otherwise>
                                                                <OPTION VALUE="dateC<%=acf%>"><c:out value="${date}"/></OPTION>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </c:when>
                                                        <c:otherwise>
                                                        <OPTION VALUE="dateC<%=acf%>"><c:out value="${date}"/></OPTION>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <% acf++;%>
                                                </c:forEach>
                                        </SELECT>
                                    </td>
                                </tr>
                            </c:if>
                            <c:if test="${sessionScope.lotes_c != null }">
                                <tr>
                                    <% int acl = 0;%>
                                    <td align="right" class="SubTitulo">LayOut Cargado:</td>
                                    <td colspan="3" >
                                        <SELECT NAME="selComboFileNameC" onchange="return setActionComboFileNameC(formLiquidation, 'AutorizaLoteNombreArchivo:33');">
                                            <c:forEach items="${sessionScope.lotes_c}" var="date" varStatus="status">
                                                <c:choose>
                                                    <c:when test="${sessionScope.lote_c != null }">
                                                        <c:choose>
                                                            <c:when test="${sessionScope.lote_c == date }">
                                                                <OPTION selected VALUE="loteC<%=acl%>"><c:out value="${date}"/></OPTION>
                                                                </c:when>
                                                                <c:otherwise>
                                                                <OPTION VALUE="loteC<%=acl%>"><c:out value="${date}"/></OPTION>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </c:when>
                                                        <c:otherwise>
                                                        <OPTION VALUE="loteC<%=acl%>"><c:out value="${date}"/></OPTION>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <% acl++;%>
                                                </c:forEach>
                                        </SELECT>
                                    </td>
                                </tr>
                            </c:if>
                            <c:if test="${sessionScope.fecha_capturaC != null }">
                                <tr>
                                    <td align="right" class="SubTitulo">Fecha de Recepción:</td>
                                    <td align="left">
                                        <input class="LetraInput" type="text" disabled="disabled"  style="width:150px;" name="LayOutC" value="<c:out value='${sessionScope.fecha_capturaC}'/>" />
                                    </td>
                                    <td align="left" class="SubTitulo">Movimientos:</td>
                                    <td align="left">
                                        <input class="LetraInput" type="text" disabled="disabled"  style="width:80px;" name="totalMovs" value="<c:out value='${sessionScope.total_movimientos}'/>" />
                                    </td>
                                </tr>

                                <tr><td colspan="4">&nbsp;</td></tr>

                                <tr>
                                    <td colspan="4" align="center">
                                        <input type="button"  name="AutorizarC" value="Aceptar" onClick="setAction(formLiquidation, 'AutorizaLote:22')"/>
                                    </td>
                                </tr>

                                <tr><td colspan="4">&nbsp;</td></tr>

                                <tr> </tr>
                            </c:if>
                        </table>
                    </c:if>

                </form>

                <%--Modulo: Consulta de estados de cuenta de fideicomisos --%>
                <c:if test="${sessionScope.estados_de_cuenta!=null && sessionScope.allMovimientos!=null}">
                    <form name="consulta_edo_cta" method="POST" action="ControllerLiquidation">
                        <input type="hidden" name="accion" value="descargaEdoCta"/>
                        <input type="hidden" name="nombre_archivo" value=""/>
                        <input type="hidden" name="cliente" value=""/>
                        <table align="center" width="40%">
                            <tr><td colspan="4">&nbsp;</td></tr>
                            <tr align="center" ><td  class="Titulo" style="background-color: #d6e8f9;" colspan="4"> Subir estados de cuenta </td></tr>
                            <tr><td colspan="4">&nbsp;</td></tr>   
                            <tr>
                                <td align="center" colspan="4">
                                    <button href="" onclick="return newWindow();">
                                        <img src="./Imagen/subir.png" title="Subir Estados de Cuenta" width="45" height="45"  />
                                    </button>    
                                </td>                             
                            </tr>
                            <tr><td colspan="4">&nbsp;</td></tr>
                            <tr align="center" ><td  class="Titulo" style="background-color: #d6e8f9;" colspan="4"> Estados de cuenta </td></tr>
                            <tr><td colspan="4">&nbsp;</td></tr>                            
                            <% int ec = 0;%>
                            <tr colspan="4">
                                <td align="right" class="SubTitulo" >Clave de Fideicomiso:</td>
                                <td colspan="3">
                                    <SELECT NAME="selComboClaveFiso" ${habilitado} onchange="return setActionComboClaveFiso(consulta_edo_cta, 'estados_De_cuenta:48');">
                                        <c:forEach items="${sessionScope.allMovimientos}" var="client" varStatus="status">
                                            <c:choose>
                                                <c:when test="${sessionScope.clave_contratoC != null }">
                                                    <c:choose>
                                                        <c:when test="${sessionScope.clave_contratoC == client }">
                                                            <OPTION selected VALUE="dataC<%=ec%>"><c:out value="${client}"/></OPTION>
                                                            </c:when>
                                                            <c:otherwise>
                                                            <OPTION VALUE="dataC<%=ec%>"><c:out value="${client}"/></OPTION>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:when>
                                                    <c:otherwise>
                                                    <OPTION VALUE="dataC<%=ec%>"><c:out value="${client}"/></OPTION>
                                                    </c:otherwise>
                                                </c:choose>
                                                <% ec++;%>
                                            </c:forEach>
                                    </SELECT>
                                </td>
                            </tr>
                            <c:if test="${sessionScope.cliente_c != null}">
                                <input type="hidden" name="nombreRL" value="" />
                                <tr colspan="4">
                                    <td align="right" class="SubTitulo">Fideicomitente:</td>
                                    <td align="left" colspan="3">
                                        <input class="LetraInput" type="text" disabled="disabled"  style="width:360px;" name="FideicomitenteC" value="<c:out value='${sessionScope.cliente_c}'/>" />
                                    </td>
                                </tr> 
                                <tr align="center"> 
                                    <td colspan="6">
                                        <table style="alignment-adjust: central">
                                            <tr><td  colspan="6"> &nbsp; </td></tr>

                                            <c:if test="${sessionScope.nombresEdoCta!=null && (fn:length(sessionScope.nombresEdoCta) gt 0)}" >
                                                <tr>
                                                    <td class="Subtitulo" align="right">Periodo: &nbsp;</td>
                                                    <td>
                                                        <SELECT NAME="selComboPeriodo">
                                                            <c:forEach items="${sessionScope.nombresEdoCta['0']}" var="mes" varStatus="status">
                                                                <c:choose>
                                                                    <c:when test="${sessionScope.mesSelected != null }">
                                                                        <c:choose>
                                                                            <c:when test="${sessionScope.mesSelected eq mes }">
                                                                                <OPTION selected VALUE="${sessionScope.nombresEdoCta['1'][status.index]}"><c:out value="${mes}"/></OPTION>
                                                                                </c:when>
                                                                                <c:otherwise>
                                                                                <OPTION VALUE="${sessionScope.nombresEdoCta['1'][status.index]}"><c:out value="${mes}"/></OPTION>
                                                                                </c:otherwise>
                                                                            </c:choose>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                        <OPTION VALUE="${sessionScope.nombresEdoCta['1'][status.index]}"><c:out value="${mes}"/></OPTION>
                                                                        </c:otherwise>                                                               
                                                                    </c:choose>                                                         
                                                                </c:forEach>
                                                        </SELECT>
                                                    </td>
                                                    <td>&nbsp;</td>
                                                    <td>
                                                        <input type="button" value="Descargar" name="EdoCta" onclick="return generaAccion('muestraEdoCta');" /> 
                                                    </td>
                                                </tr>
                                            </c:if>
                                        </table>
                                    </td>
                                </tr>                                  
                            </c:if>                            
                            <tr><td colspan="4">&nbsp;</td></tr>
                        </table>
                    </form>                                        
                </c:if>                    

                <script>
                    function generaAccion(accionDeseada) {
                        if (accionDeseada == 'muestraEdoCta') {
                            var indice = document.consulta_edo_cta.selComboPeriodo.selectedIndex;
                            var valor = document.consulta_edo_cta.selComboPeriodo.options[indice].value;
                            document.consulta_edo_cta.accion.value = 'descargaEdoCta';
                            document.consulta_edo_cta.nombreRL.value = valor;
                            document.consulta_edo_cta.action = 'DescargaEdoCta';
                            document.consulta_edo_cta.submit();
                            return false;
                        } else {
                            document.formLiquidation.action = accionDeseada;
                            document.formLiquidation.submit();
                            return false;
                        }
                    }

                    function newWindow() {
                        newwindow = window.open("subirArchivo.htm", "Subir Estados de Cuenta", "location=si, resizable=yes,scrollbars=yes, tittlebar=yes,width=540,height=450,top=0, left=0");
                        //window.open(url, "nuevo", "directories=no, location=no, menubar=no, scrollbars=yes, statusbar=no, tittlebar=no, width=400, height=400");
                        newwindow.creator = self;
                        return false;
                    }

                </script>                  
            </body>
        </html>

    </c:when>
    <c:otherwise>
        <c:redirect url="login.htm" />
    </c:otherwise>
</c:choose>

