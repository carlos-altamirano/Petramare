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
                <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
                <link rel="stylesheet" type="text/css" href="css/formato.css">
                <link rel="stylesheet" type="text/css" href="css/Saldo.css">
                <!--Aqui importamos los scripts que necesitemos-->
                <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
                <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
                <script language="javascript" type="text/javascript" src="scripts/login.js"></script>
                <script language="javascript" type="text/javascript" src="scripts/liquidation.js"></script>
                <script language="javascript" type="text/javascript" src="scripts/popCalendar.js"></script>
                <script language="javascript" type="text/javascript" src="scripts/Saldo.js"></script>
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
                            <td width="60%">&nbsp;</td>
                            <td align ="center">
                                <b ><button type="button" class="btn btn-outline-primary" onclick="cambiaSalarioMinimo();"><u>cambiar salario minimo</u></button></b>
                            </td>
                            <td align ="center" width="15%">
                                <b ><a href="" onclick="return cambiaPassword(formLiquidation, 'cambiaPassword:6');" target="main"><u>cambiar contraseña</u></a></b>
                            </td>
                            <td>
                                <b><a href="" onclick="return atras(formLiquidation, 'eliminaBeans:100', 'messageBean;userApp;movPending', 'login.htm');" target="main"><u>Salir</u></a></b>
                            </td>
                        </tr>
                    </table>

                    <!-- The Modal -->
                    <div class="modal fade" id="salaryModal">
                        <div class="modal-dialog modal-dialog-centered">
                            <div class="modal-content">

                                <!-- Modal Header -->
                                <div class="modal-header">
                                    <h4 class="modal-title">Salario Minimo</h4>
                                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                                </div>

                                <!-- Modal body -->
                                <div class="modal-body">
                                    <input type="text" id="salario_minimo" class="form-control">
                                </div>

                                <!-- Modal footer -->
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-primary" onclick="guardarSalarioMinimo()" data-dismiss="modal">Guardar</button>
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                                </div>

                            </div>
                        </div>
                    </div>

                    <!-- The Modal -->
                    <div class="modal fade" id="noticeModal">
                        <div class="modal-dialog modal-dialog-centered">
                            <div class="modal-content">

                                <!-- Modal Header -->
                                <div class="modal-header">
                                    <h4 class="modal-title"></h4>
                                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                                </div>

                                <!-- Modal body -->
                                <div class="modal-body text-center">
                                    <h3>Guardado exitosamente!</h3>
                                </div>

                                <!-- Modal footer -->
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-success" data-dismiss="modal">OK</button>
                                </div>

                            </div>
                        </div>
                    </div>

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

                    <%--Modulo: Inserción de aportaciones y restituciones  aportaciones_restituciones --%>
                    <c:if test="${sessionScope.confirmacionLote == null && cliMovConfirma==null && sessionScope.aportaciones_restituciones!=null && sessionScope.allMovimientos!=null && sessionScope.cancelacionParcial == null && sessionScope.cancelacionTotal == null}">
                        <% DecimalFormat formatoDinero = new DecimalFormat("$ #,##0.00");
                            DecimalFormat formatoSTD = new DecimalFormat("0.00");%>
                        <table align="center" class="form-noindentCaptureA" >
                            <% int accc = 0;%>
                            <tr><td colspan="4">&nbsp;</td></tr>
                            <tr align="center" ><td  class="Titulo" colspan="4"> APORTACIÓN Y RESTITUCIÓN A PATRIMONIO </td></tr>
                            <tr><td colspan="4">&nbsp;</td></tr>
                            <tr colspan="4">
                                <td align="right" class="SubTitulo" >Clave de Fideicomiso:</td>
                                <td colspan="3">
                                    <SELECT NAME="selComboClaveFiso" id="cambiaClaveFideicomiso" >
                                        <c:forEach items="${sessionScope.allMovimientos}" var="client" varStatus="status">
                                            <c:choose>
                                                <c:when test="${sessionScope.clave_contratoC != null }">
                                                    <c:choose>
                                                        <c:when test="${sessionScope.clave_contratoC == client }">
                                                            <OPTION selected VALUE="dataC<%=accc%>"><c:out value="${client}"/></OPTION>
                                                            </c:when>
                                                            <c:otherwise>
                                                            <OPTION VALUE="dataC<%=accc%>"><c:out value="${client}"/></OPTION>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:when>
                                                    <c:otherwise>
                                                    <OPTION VALUE="dataC<%=accc%>"><c:out value="${client}"/></OPTION>
                                                    </c:otherwise>
                                                </c:choose>
                                                <% accc++;%>
                                            </c:forEach>
                                    </SELECT>
                                </td>
                            </tr>
                            <c:if test="${sessionScope.cliente_c != null && sessionScope.saldo_actual != null }">
                                <tr colspan="4">
                                    <td align="right" class="SubTitulo">Fideicomitente:</td>
                                    <td align="left" colspan="3">
                                        <input class="LetraInput" type="text" disabled="disabled"  style="width:360px;" name="FideicomitenteC" value="<c:out value='${sessionScope.cliente_c}'/>" />
                                    </td>
                                </tr>
                                <tr colspan="4">
                                    <td align="right" class="SubTitulo">Saldo a la fecha:</td>
                                    <td align="left" colspan="3">
                                        <input class="LetraInput" type="text" disabled="disabled"  style="width:130px;text-align: right;" name="FideicomitenteC" value="<c:out value='<%=(session.getAttribute("saldo_actual") == null ? ("$ 0.00") : (formatoDinero.format(session.getAttribute("saldo_actual"))))%>' default=" $ 0.00" />" />
                                    </td>
                                </tr>      
                                <tr colspan="4"><td>&nbsp;</td></tr>
                                <tr align="center" colspan="4" ><td colspan="4" class="Titulo"> Registrar operaci&oacute;n </td></tr>
                                <tr colspan="4"><td colspan="4">&nbsp;</td></tr>
                                <%--
            <!--                                                    <tr>
                                                <td align="right" class="SubTitulo">Fecha de operaci&oacute;n: </td>
                                                <td>
                                                        <input type="text"  name="fecha_operacion" id="dateArrival"  value="<c:out value='${sessionScope.fecha_operacion}'/>" onClick="return popUpCalendar(this, formLiquidation.dateArrival, 'dd/mm/yyyy');" size="10">
                                                </td>   
                                                <td>
                                                    <input type="text" align="center" name="hora_operacion" value="<c:out value='${sessionScope.hora_operacion}' default="00" />" size="4" /> 
                                                    &nbsp;:&nbsp;
                                                    <input type="text" align="center"  name="min_operacion" value="<c:out value='${sessionScope.min_operacion}' default="00" />" size="4" /> 
                                                    &nbsp;hrs.&nbsp;&nbsp;&nbsp;&nbsp;
                                                </td>    
                                                <td>
                                                    <select name="am_pm">
                                                        <option value="AM">AM</option>
                                                        <option value="PM">PM</option>
                                                    </select>
                                                </td>  
                                            </tr>-->
                                
                                        <tr><td colspan="1">&nbsp;</td></tr>
                                --%>
                                <tr colspan="4">
                                    <td>&nbsp;</td>
                                    <td align="right" class="SubTitulo">Tipo de operaci&oacute;n: </td>
                                    <td align="left"> 
                                        <select name="tipo_operacion" onchange="return setActionComboOperacion(formLiquidation, 'AportacionRestitucionOperacion:36');" >
                                            <option value="seleccione">-- Seleccione --</option>
                                            <option value="aportacion" <c:out value='${sessionScope.selApo}' default="" /> > Aportaci&oacute;n </option>
                                            <option value="restitucion" <c:out value='${sessionScope.selRes}' default="" /> > Restituci&oacute;n </option>
                                        </select>
                                    </td>
                                    <td>&nbsp;</td>
                                </tr>                           
                            </c:if>
                            <tr><td>&nbsp;</td></tr>
                            <c:if test="${sessionScope.cliente_c != null && sessionScope.saldo_actual != null && sessionScope.aportacion!=null && sessionScope.restitucion==null}">
                                <tr align="center" ><td  class="Titulo" colspan="4"> Operación de aportaci&oacute;n </td></tr>
                                <tr><td colspan="1">&nbsp;</td></tr>
                                <tr align="center" colspan="4">
                                    <td align="right" colspan="2" class="SubTitulo"> Importe de  ${sessionScope.aportacion}  </td>
                                    <td align="left">
                                        <input type="text" align="right" style="width:130px;text-align: right;" name="importe_aportacion" value="<c:out value='<%=(session.getAttribute("importe_aportacion") == null ? ("0.00") : (formatoSTD.format(session.getAttribute("importe_aportacion"))))%>' default="0.00" />" size="15" />
                                    </td>
                                    <td align="left">
                                        <input type="submit" value="Calcular" name="calcular_aportacion"  onclick="return setImporteApo(formLiquidation, 'AportacionRestitucionCalculaApo:37');" />
                                    </td>                                         
                                </tr>
                                <tr align="center" colspan="4">
                                    <td align="right" colspan="2" class="SubTitulo"> Honorarios fiduciarios  </td>
                                    <td align="left" colspan="2">
                                        <b><input type="text" align="right" disabled="disabled" style="width:130px;text-align: right;"  name="honorarios_fiduciarios" value="<c:out value='<%=(session.getAttribute("honorarios_fiduciarios") == null ? ("$ 0.00") : (formatoDinero.format(session.getAttribute("honorarios_fiduciarios"))))%>' default="$ 0.00" />" size="15" /></b>
                                    </td>
                                </tr>   
                                <tr align="center" colspan="4">
                                    <td align="right" colspan="2" class="SubTitulo"> I.V.A de honorarios</td>
                                    <td align="left" colspan="2">
                                        <input type="text" align="right" disabled="disabled" style="width:130px;text-align: right;" name="iva_honorarios" value="<c:out value='<%=(session.getAttribute("iva_honorarios") == null ? ("$ 0.00") : (formatoDinero.format(session.getAttribute("iva_honorarios"))))%>' default="$ 0.00" />" size="15" />
                                    </td>
                                </tr> 
                                <tr><td colspan="4">&nbsp;</td></tr>
                                <tr align="center" colspan="4">
                                    <td align="right" colspan="2" class="Titulo"> Aportaci&oacute;n neta:  </td>
                                    <td align="left" colspan="2">
                                        <input type="text" align="right" class="LetraInput"  disabled="disabled" style="width:130px;text-align: right;" name="aportacion_neta" value="<c:out value='<%=(session.getAttribute("aportacion_neta") == null ? ("$ 0.00") : (formatoDinero.format(session.getAttribute("aportacion_neta"))))%>' default="$ 0.00" />" size="15" />
                                    </td>
                                </tr>                                       
                                <tr align="center" colspan="4">
                                    <td align="right" colspan="2" class="Titulo"> Nuevo saldo  </td>
                                    <td align="left" colspan="2">
                                        <input type="text" align="right" class="LetraInput"  disabled="disabled" style="width:130px;text-align: right;" name="nuevo_saldo_aportacion" value="<c:out value='<%=(session.getAttribute("nuevo_saldo_aportacion") == null ? ("$ 0.00") : (formatoDinero.format(session.getAttribute("nuevo_saldo_aportacion"))))%>' default="$ 0.00" />" size="15" />
                                    </td>
                                </tr>

                                <c:if test="${sessionScope.nuevo_saldo_aportacion != null }">
                                    <tr><td>&nbsp;</td></tr>
                                    <tr><td>&nbsp;</td></tr>
                                    <tr align="center" colspan="4">
                                        <td>&nbsp;</td>
                                        <td align="center" >
                                            <input type="submit" value="Cancelar" name="cancelar"  onclick="return setAction(formLiquidation, 'AportacionRestitucionCancelar:40');" />
                                        </td>
                                        <td align="center" >
                                            <%--<input type="submit" value="Guardar" name="almacenar_restitucion" onclick="return false;" ondblclick="return almacenaOperacion(formLiquidation,'AportacionRestitucionAlmacenaApo:39','aportacion');" />--%>
                                            <input type="submit" value="Guardar" name="almacenar_restitucion" onclick="return almacenaOperacion(formLiquidation, 'AportacionRestitucionAlmacenaApo:39', 'aportacion');" />
                                        </td>
                                        <td>&nbsp;</td>
                                    </tr>
                                </c:if>

                            </c:if>   
                            <c:if test="${sessionScope.cliente_c != null && sessionScope.saldo_actual != null && sessionScope.restitucion!=null && sessionScope.aportacion==null}">
                                <tr align="center" ><td  class="Titulo" colspan="4"> Operación de restituci&oacute;n </td></tr>
                                <!--<tr><td colspan="1">&nbsp;</td></tr>-->
                                <tr align="center" colspan="4">
                                    <td align="right" colspan="2" class="SubTitulo"> Importe de  ${sessionScope.restitucion} &nbsp; </td>
                                    <td align="left">
                                        <input type="text" align="right" style="width:130px;text-align: right;" name="importe_restitucion" value="<c:out value='<%=(session.getAttribute("importe_restitucion") == null ? ("0.00") : (formatoSTD.format(session.getAttribute("importe_restitucion"))))%>' default="0.00" />"  size="15" />
                                        <!--<input type="text" align="right" name="importe_restitucion" value="<c:out value='${sessionScope.importe_restitucion}' default="0.00" />" size="15" />-->
                                    </td>
                                    <td align="left">
                                        <input type="submit" value="Calcular" name="calcular_restitucion" onclick="return setImporteRes(formLiquidation, 'AportacionRestitucionCalculaRes:38');" />
                                    </td>                                        
                                </tr>                                       
                                <tr  colspan="4"><td>&nbsp;</td></tr>
                                <tr align="center" colspan="4">
                                    <td align="right" colspan="2" class="Titulo"> Nuevo saldo  </td>
                                    <td align="left" colspan="1">
                                        <input type="text" class="LetraInput"  align="right" style="width:130px;text-align: right;" name="nuevo_saldo_restitucion" value="<c:out value='<%=(session.getAttribute("nuevo_saldo_restitucion") == null ? ("$ 0.00") : (formatoDinero.format(session.getAttribute("nuevo_saldo_restitucion"))))%>' default="$ 0.00" />" size="15" />
                                    </td>
                                </tr>
                                <c:if test="${sessionScope.nuevo_saldo_restitucion != null }">
                                    <!--<tr><td>&nbsp;</td></tr>-->
                                    <!--<tr><td>&nbsp;</td></tr>-->
                                    <tr align="center" colspan="4">
                                        <td align="right" colspan="2" class="SubTitulo"> Observaciones: &nbsp; </td>
                                        <td align="left" colspan="2">
                                            <input type="text" align="right" style="width:130px;" name="observaciones" size="15" maxlength="50" value=" "/>
                                        </td>                                     
                                    </tr>   
                                    <tr  colspan="4"><td>&nbsp;</td></tr>
                                    <tr align="center" colspan="4">
                                        <td>&nbsp;</td>
                                        <td align="center" >
                                            <input type="submit" value="Cancelar" name="cancelar"  onclick="return setAction(formLiquidation, 'AportacionRestitucionCancelar:40');" />
                                        </td>
                                        <td align="center" >
                                            <%--<input type="submit" value="Guardar" name="almacenar_restitucion" onclick="return false;"  ondblclick="return almacenaOperacion(formLiquidation,'AportacionRestitucionAlmacenaRes:39','restitucion');" />--%>
                                            <input type="submit" value="Guardar" name="almacenar_restitucion" onclick="return almacenaOperacion(formLiquidation, 'AportacionRestitucionAlmacenaRes:39', 'restitucion');" />
                                        </td>
                                        <td>&nbsp;</td>
                                    </tr>
                                </c:if>                                   

                            </c:if>     
                            <tr><td colspan="4">&nbsp;</td></tr>
                        </table>                        
                    </c:if>

                    <%--Modulo: Modulo de busqueda de movimientos fiduciarios. --%>
                    <c:if test="${sessionScope.confirmacionLote == null && cliMovConfirma==null && sessionScope.aportaciones_restituciones==null && sessionScope.consultar_movimientos!=null  && sessionScope.allMovimientos!=null && sessionScope.cancelacionParcial == null && sessionScope.cancelacionTotal == null}">
                        <table align="center" >
                            <tr><td colspan="6">&nbsp;</td></tr>
                            <tr align="center" ><td  class="Titulo" style="background-color: #d6e8f9;" colspan="6"> B&uacute;squeda de movimientos Fiduciarios &nbsp; &nbsp;</td></tr>
                            <tr><td colspan="6">&nbsp;</td></tr>
                            <!--<tr align="center" ><td  class="SubTitulo" colspan="6"> <u style="cursor: pointer;" onclick="return generaAccion('DescargaArchivo');"> Descargar Todos </u></td></tr>-->
                            <tr align="center" ><td  class="SubTitulo" colspan="6"> <a href="DescargaArchivo" target="_blank">Descargar Todos</a> </td></tr>
                            <tr><td colspan="6">&nbsp;</td></tr>
                            <tr><td>
                                    <table>
                                        <tr align="center" colspan="6">
                                            <% int acc1 = 0;%>
                                            <td>&nbsp;</td>
                                            <td align="right" class="SubTitulo" colspan="2">Clave de Fideicomiso:</td>
                                            <td colspan="2" align="left" >
                                                <SELECT NAME="selComboClaveFiso" onchange="return setActionComboClaveFiso(formLiquidation, 'Consulta_movimientos_fiduciarios:42');">
                                                    <c:forEach items="${sessionScope.allMovimientos}" var="client" varStatus="status">
                                                        <c:choose>
                                                            <c:when test="${sessionScope.clave_contratoC != null }">
                                                                <c:choose>
                                                                    <c:when test="${sessionScope.clave_contratoC == client }">
                                                                        <OPTION selected VALUE="dataC<%=acc1%>"><c:out value="${client}"/></OPTION>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                        <OPTION VALUE="dataC<%=acc1%>"><c:out value="${client}"/></OPTION>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </c:when>
                                                                <c:otherwise>
                                                                <OPTION VALUE="dataC<%=acc1%>"><c:out value="${client}"/></OPTION>
                                                                </c:otherwise>
                                                            </c:choose>
                                                            <% acc1++;%>
                                                        </c:forEach>
                                                </SELECT>
                                            </td>
                                            <td>&nbsp;</td>
                                        </tr>
                                        <c:if test="${sessionScope.nombre_cliente != null && sessionScope.saldo_cliente != null }">
                                            <tr colspan="6" align="center">
                                                <td align="right" colspan="3" class="SubTitulo">Fideicomitente:</td>
                                                <td align="left" colspan="3">
                                                    <input class="LetraInput" type="text" disabled="disabled"  style="width:440px;" name="FideicomitenteC" value="<c:out value='${sessionScope.nombre_cliente}'/>" />
                                                </td>
                                            </tr>
                                            <tr colspan="6">
                                                <td align="right" colspan="3" class="SubTitulo">Domicilio:</td>
                                                <td align="left" colspan="3">
                                                    <input class="LetraInput" type="text" disabled="disabled"  style="width:440px;" name="FideicomitenteC" value="<c:out value='${sessionScope.domicilio_cliente}'/>" />
                                                </td>
                                            </tr>
                                            <tr colspan="6">
                                                <td align="right" colspan="3" class="SubTitulo">Saldo actual:</td>
                                                <td align="left" colspan="3">
                                                    <input class="LetraInput" type="text" disabled="disabled"  style="width:440px;" name="FideicomitenteC" value="<c:out value='${sessionScope.saldo_cliente}'/>" />
                                                </td>
                                            </tr> 

                                            <tr><td colspan="6">&nbsp;</td></tr>
                                            <tr><td colspan="6">&nbsp;</td></tr>
                                            <tr align="center" ><td  class="Titulo" style="background-color: #d6e8f9;" colspan="6"> Movimientos Fiduciarios </td></tr>
                                            <tr><td colspan="6">&nbsp;</td></tr>                                                   

                                            <tr align="center"> 
                                                <td colspan="6">
                                                    <table style="alignment-adjust: central">
                                                        <!--<tr><td  colspan="6"> &nbsp; </td></tr>-->                          
                                                        <tr>
                                                            <td class="Subtitulo" align="center">
                                                                Tipo de movimiento: &nbsp;  
                                                            </td>
                                                            <td  class="Subtitulo" align="center"  rowspan="2">
                                                                peri&oacute;do     de
                                                            </td>
                                                            <td  align="center"  rowspan="2">
                                                                <!--formato de fecha para Server Principal-->
                                                                <%--<input type="text" name="fecha_ini" id="fecha_ini_" value="<c:out value='${sessionScope.fecha_ini}' />" onClick="return popUpCalendar(this, saldo.fecha_ini_, 'yyyy-mm-dd');" />--%>
                                                                <input type="text" name="fecha_ini_" id="fecha_ini_" value="<c:out value='${sessionScope.fecha_ini}' />" onClick="return popUpCalendar(this, formLiquidation.fecha_ini_, 'yyyy-mm-dd');" />
                                                            </td>
                                                            <td  class="Subtitulo" align="center"  rowspan="2">
                                                                a
                                                            </td>  
                                                            <td  align="center"  rowspan="2">
                                                                <input type="text" name="fecha_fin_" id="fecha_fin_" value="<c:out value='${sessionScope.fecha_fin}' />" onClick="return popUpCalendar(this, formLiquidation.fecha_fin_, 'yyyy-mm-dd');" />
                                                            </td>        
                                                            <td class="buscar" align="center"  rowspan="2">
                                                                <!--<input type="submit" value="Consultar" name="buscar" onclick="generaAccion('','','Saldo.do');" />-->
                                                                <input type="submit" value="Consultar" name="buscar" onclick="return setAction(formLiquidation, 'Consulta_movimientos_fiduciarios:42');" target="main" />
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td align="center">
                                                                <select name="tipo_operacion" onchange="return setActionComboClaveFiso(formLiquidation, 'Consulta_movimientos_fiduciarios:42');">
                                                                    <option value=""            <c:out value='${sessionScope.selT}' default="" />>     TODOS   </option>
                                                                    <option value="RESTITUCION" <c:out value='${sessionScope.selR}' default="" />>   RESTITUCI&Oacute;N   </option>
                                                                    <option value="APORTACION"  <c:out value='${sessionScope.selA}' default="" />>   APORTACI&Oacute;N   </option>
                                                                    <option value="LIQUIDACION" <c:out value='${sessionScope.selL}' default="" />>   LIQUIDACI&Oacute;N   </option>
                                                                </select>
                                                            </td>
                                                            <td colspan="5"></td>
                                                        </tr> 

                                                        <tr><td colspan="6" ></td> </tr>

                                                        <tr  class="Subtitulo">
                                                            <td colspan="6" align="center">  &nbsp; Se han encontrado 
                                                                &nbsp;<font color="green"> <b> ${sessionScope.reporteSize} </b>  </font> 
                                                                &nbsp; registros. </td>
                                                            <!--<td colspan="4">&nbsp;</td>-->
                                                        </tr>
                                                        <c:if test="${sessionScope.reporte !=null}" >
                                                            <tr> 
                                                                <td colspan="6">
                                                                    <!--<table class="fixed_headers">-->
                                                                    <table class="tabla_saldo">
                                                                        <!--                                        <thead>
                                                                                                                    <tr>
                                                                                                                        <th>COLUMNA 1</th>
                                                                                                                        <th>COLUMNA 2</th>
                                                                                                                        <th>COLUMNA 3</th>
                                                                                                                        <th>COLUMNA 4</th>
                                                                                                                        <th>COLUMNA 5</th>
                                                                                                                        <th>COLUMNA 6</th>
                                                                                                                        <th>COLUMNA 7</th>
                                                                                                                    </tr>
                                                                                                                </thead>                                    -->
                                                                        <c:if test="${sessionScope.reporteSize ge 9}" >
                                                                            <tbody class="bodyMayor9">
                                                                            </c:if>   
                                                                            <c:if test="${sessionScope.reporteSize lt 9}" >
                                                                            <tbody class="bodyMenor9">
                                                                            </c:if>                                         
                                                                            <c:forEach items="${sessionScope.reporte}" var="movimiento" varStatus="index">
                                                                                <tr>
                                                                                    <c:if test="${index.count eq 1}" >
                                                                                        <c:forEach items="${movimiento}" var="campo" varStatus="mov">                                                 
                                                                                            <td style="text-align: center;"> ${campo} </td>                             
                                                                                        </c:forEach>                                                        
                                                                                    </c:if>
                                                                                    <c:if test="${index.count gt 1}" >        
                                                                                        <c:forEach items="${movimiento}" var="campo" varStatus="mov">                                                 
                                                                                            <c:if test="${(mov.count ge 4)}" >
                                                                                                <td style="text-align: right;"> ${campo} </td>
                                                                                            </c:if>
                                                                                            <c:if test="${(mov.count le 3)}" >
                                                                                                <td> ${campo}</td>
                                                                                            </c:if>                                                                
                                                                                        </c:forEach>
                                                                                    </c:if>
                                                                                </tr>                         
                                                                            </c:forEach>
                                                                        </tbody>
                                                                    </table>
                                                                </td>
                                                            </tr>
                                                        </c:if>                     

                                                    </table>
                                                </td>
                                            </tr>                            

                                            <tr><td colspan="6">&nbsp;</td></tr>
                                            <tr><td colspan="6">&nbsp;</td></tr>
                                            <tr align="center" ><td  class="Titulo" style="background-color: #d6e8f9;" colspan="6"> Liquidaciones Pendientes </td></tr>
                                            <tr align="center"> 
                                                <td colspan="6">
                                                    <table style="alignment-adjust: central">
                                                        <tr><td colspan="6" > &nbsp;</td></tr>
                                                        <tr  class="Subtitulo">
                                                            <td colspan="2" align="center">  &nbsp; Se han encontrado 
                                                                &nbsp; <font color="green"> <b> ${sessionScope.liquidacionesSize} </b>  </font> 
                                                                &nbsp; registros. </td>
                                                            <!--<td colspan="4">&nbsp;</td>-->
                                                        </tr>
                                                        <tr><td colspan="6" > &nbsp;</td></tr>
                                                        <c:if test="${sessionScope.liquidaciones !=null }" >
                                                            <tr>
                                                                <td colspan="6">
                                                                    <table class="tabla_liquidaciones">
                                                                        <c:if test="${sessionScope.liquidacionesSize ge 9}" >
                                                                            <tbody class="LiqBodyMayor9">                            
                                                                            </c:if>   
                                                                            <c:if test="${sessionScope.liquidacionesSize lt 9}">
                                                                            <tbody class="LiqBodyMenor9" >                            
                                                                            </c:if>                                         
                                                                            <c:forEach items="${sessionScope.liquidaciones}" var="movimiento" varStatus="index">
                                                                                <tr>
                                                                                    <c:if test="${index.count eq 1}" >
                                                                                        <c:forEach items="${movimiento}" var="campo" varStatus="mov">                                                 
                                                                                            <td style="text-align: center;"> ${campo} </td>                             
                                                                                        </c:forEach>                                                        
                                                                                    </c:if>
                                                                                    <c:if test="${index.count gt 1}" >        
                                                                                        <c:forEach items="${movimiento}" var="campo" varStatus="mov">                                                 
                                                                                            <c:if test="${mov.count ge 4}" >
                                                                                                <td style="text-align: right;"> ${campo} </td>
                                                                                            </c:if>
                                                                                            <c:if test="${mov.count le 3}" >
                                                                                                <td style="text-align: center;"> ${campo}</td>
                                                                                            </c:if>                                
                                                                                        </c:forEach>
                                                                                    </c:if>
                                                                                </tr>
                                                                            </c:forEach>
                                                                        </tbody>
                                                                    </table>
                                                                </td>
                                                                <!--<td  colspan="2"> &nbsp; </td>-->
                                                            </tr>
                                                            <%--                            <tr>
                                                                                            <td> <b>${sessionScope.suficiencia_total} </b> </td>
                                                                                        </tr>--%>
                                                        </c:if>
                                                    </table>
                                                </td>
                                            </tr>   

                                            <tr><td colspan="6">&nbsp;</td></tr>
                                            <tr align="center" ><td  class="Titulo" style="background-color: #d6e8f9;" colspan="6"> Suficiencia Patrimonial Requerida en Moneda Nacional</td></tr>                       
                                            <tr><td  colspan="6"> &nbsp; </td></tr>
                                            <tr>
                                                <!--<td> &nbsp; </td>-->
                                                <td colspan="6" align="center">
                                                    <table class="tabla_saldos">
                                                        <tbody>
                                                            <c:forEach items="${saldos}" var="campo" varStatus="mov">  
                                                                <c:if test="${mov.count lt 7}" >
                                                                    <c:if test="${(mov.count mod 2)!=0}" >
                                                                        <tr>
                                                                            <td style="text-align: left;"> ${campo} </td>
                                                                        </c:if>
                                                                        <c:if test="${(mov.count mod 2)==0}" >
                                                                            <td style="text-align: right;"> ${campo} </td>
                                                                        </tr>
                                                                    </c:if>  
                                                                </c:if> 
                                                                <c:if test="${mov.count ge 7}" >
                                                                    <c:if test="${(mov.count mod 2)!=0}" >
                                                                        <tr>
                                                                            <td style="text-align: left;"> <b>  ${campo} </b> </td>
                                                                        </c:if>
                                                                        <c:if test="${(mov.count mod 2)==0}" >
                                                                            <td style="text-align: right;color: darkgreen;"> <b>  ${campo} </b> </td> 
                                                                        </tr>
                                                                    </c:if>  
                                                                </c:if>                                            
                                                            </c:forEach>                                     
                                                        </tbody>
                                                    </table> 
                                                </td>
                                            </tr>                        

                                        </c:if>
                                    </table>
                            <tr><td>&nbsp;</td></tr>  
                            <tr><td colspan="6">&nbsp;</td></tr>
                        </table>                        
                    </c:if>  

                    <%--Modulo: Modulo de consulta ejecutiva. --%>
                    <c:if test="${sessionScope.consulta_ejecutiva != null && sessionScope.confirmacionLote == null && cliMovConfirma==null && sessionScope.aportaciones_restituciones==null && sessionScope.consultar_movimientos==null && sessionScope.cancelacionParcial == null && sessionScope.cancelacionTotal == null}">
                        <table align="center" >                     
                            <tr><td colspan="6">&nbsp;</td></tr>
                            <tr align="center" ><td  class="Titulo" style="background-color: #d6e8f9;" colspan="6"> Consulta Ejecutiva </td></tr>
                            <tr><td colspan="6">&nbsp;</td></tr>

                            <tr>
                                <td  class="Subtitulo" style="color:#006699; font-size: 15px;" align="center">
                                    Seleccionar peri&oacute;do: 
                                </td> 
                                <td  class="Subtitulo" align="center">
                                    de
                                </td>                                 
                                <td  align="center" >
                                    <!--formato de fecha para Server Principal-->
                                    <%--<input type="text" name="fecha_ini" id="fecha_ini_" value="<c:out value='${sessionScope.fecha_ini}' />" onClick="return popUpCalendar(this, saldo.fecha_ini_, 'yyyy-mm-dd');" />--%>
                                    <input type="text" maxlength="20"  name="fecha_ini_2" id="fecha_ini_2" value="<c:out value='${sessionScope.fecha_ini_2}' />" onClick="return popUpCalendar(this, formLiquidation.fecha_ini_2, 'yyyy-mm-dd');" />
                                </td>
                                <td  class="Subtitulo" align="center">
                                    a
                                </td>  
                                <td  align="center" >
                                    <input type="text" maxlength="20" name="fecha_fin_2" id="fecha_fin_2" value="<c:out value='${sessionScope.fecha_fin_2}' />" onClick="return popUpCalendar(this, formLiquidation.fecha_fin_2, 'yyyy-mm-dd');" />
                                </td>        
                                <td class="buscar" align="center">
                                    <!--<input type="submit" value="Consultar" name="buscar" onclick="generaAccion('','','Saldo.do');" />-->
                                    <input type="submit" value="Consultar" name="buscar" onclick="return setAction(formLiquidation, 'Consulta_ejecutiva_fideicomiso:45');" target="main" />
                                </td>
                            </tr>   

                            <tr><td colspan="6">&nbsp;</td></tr>                                                        
                            <tr align="center" colspan="6">
                                <%int acc3 = 0;%>
                                <!--<td>&nbsp;</td>-->
                                <td align="right" class="SubTitulo">Clave de Fideicomiso:</td>
                                <td align="left" >
                                    <SELECT NAME="selComboClaveFiso" onchange="return setActionComboClaveFiso(formLiquidation, 'Consulta_ejecutiva_fideicomiso:45');">
                                        <c:forEach items="${sessionScope.allMovimientos}" var="client" varStatus="status">
                                            <c:choose>
                                                <c:when test="${sessionScope.clave_contratoC != null }">
                                                    <c:choose>
                                                        <c:when test="${sessionScope.clave_contratoC == client }">
                                                            <OPTION selected VALUE="dataC<%=acc3%>"><c:out value="${client}"/></OPTION>
                                                            </c:when>
                                                            <c:otherwise>
                                                            <OPTION VALUE="dataC<%=acc3%>"><c:out value="${client}"/></OPTION>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:when>
                                                    <c:otherwise>
                                                    <OPTION VALUE="dataC<%=acc3%>"><c:out value="${client}"/></OPTION>
                                                    </c:otherwise>
                                                </c:choose>
                                                <% acc3++;%>
                                            </c:forEach>
                                    </SELECT>
                                </td>
                                <!--<td>&nbsp;</td>-->
                                <!--</tr>-->

                                <td>&nbsp;</td>                            
                                <% DecimalFormat decimal = new DecimalFormat("$ #,##0.00");%>
                                <!--<tr align="center" colspan="6">-->
                                <%int acc4 = 0;%>
                                <!--<td>&nbsp;</td>-->
                                <td align="right" class="SubTitulo">Cuenta de origen:</td>
                                <td align="left" >
                                    <SELECT NAME="selComboCtaOrigen" onchange="return setActionComboCtaOrigen(formLiquidation, 'Consulta_ejecutiva_fideicomiso:45');">
                                        <c:forEach items="${sessionScope.cuentasOrigen}" var="acount" varStatus="status">
                                            <c:choose>
                                                <c:when test="${sessionScope.cuenta_origen != null }">
                                                    <c:choose>
                                                        <c:when test="${sessionScope.cuenta_origen == acount }">
                                                            <OPTION selected VALUE="dataC<%=acc4%>"><c:out value="${acount}"/></OPTION>
                                                            </c:when>
                                                            <c:otherwise>
                                                            <OPTION VALUE="dataC<%=acc4%>"><c:out value="${acount}"/></OPTION>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:when>
                                                    <c:otherwise>
                                                    <OPTION VALUE="dataC<%=acc4%>"><c:out value="${acount}"/></OPTION>
                                                    </c:otherwise>
                                                </c:choose>
                                                <% acc4++;%>
                                            </c:forEach>
                                    </SELECT>
                                </td>
                                <td>&nbsp;</td>
                            </tr>                            


                            <tr align="center"> 
                                <td colspan="6">
                                    <table style="alignment-adjust: central">
                                        <tr><td colspan="6" > &nbsp;</td></tr>
                                        <c:if test="${sessionScope.vector_consulta !=null and sessionScope.vector_consulta_total !=null}" >                            
                                            <tr class="Subtitulo">
                                                <td colspan="2" align="center">  &nbsp; Se han encontrado 
                                                    &nbsp; <font color="green"> <b> ${sessionScope.vector_consultaSize} </b>  </font> 
                                                    &nbsp; registros. </td>
                                            </tr>
                                            <script>
                                                function consultaSaldos() {
                                                    var fecha = document.getElementById("fecha_fin_2").value;
                                                    var href='DescargaArchivo?vector_info=descarga_saldos&fechaFin=' + fecha;
                                                    document.getElementById('descargaSaldos').setAttribute("href", href);
                                                }                                                
                                            </script>
                                            <tr><td align="left" colspan="2" class="SubTitulo" style="color:#7f003f; font-size: 14px;"> <a href="DescargaArchivo?vector_info=movimientos_detalle" target="_blank">Descargar Detalle</a> </td>
                                                <td align="left" colspan="2" class="SubTitulo" style="color:#7f003f; font-size: 14px;"> <a id="descargaSaldos" href="#" onclick="consultaSaldos()" target="_blank">Descargar Saldos</a> </td>
                                                <td align="right" colspan="2" class="SubTitulo" style="color:#7f003f; font-size: 14px;"> <a href="DescargaArchivo?vector_info=vector_consulta" target="_blank">Descargar Selecci&oacute;n</a> </td></tr>
                                            <tr><td colspan="6" > &nbsp;</td></tr>
                                            <tr>
                                                <td colspan="6">
                                                    <table class="fixed_headers"> 
                                                        <thead>
                                                            <tr>
                                                                <th>FIDEICOMISO</th>
                                                                <th>APORTACIONES</th>
                                                                <th>RESTITUCIONES</th>
                                                                <th>LIQUIDACIONES</th>
                                                                <th>HONORARIOS</th>
                                                                <th>I.V.A.</th>
                                                                <!--<th>SALDO</th>-->
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <c:forEach items="${sessionScope.vector_consulta}" var="movimiento" varStatus="index">
                                                                <tr>
                                                                    <c:forEach items="${movimiento}" var="campo" varStatus="mov">                                                 
                                                                        <c:if test="${mov.count ge 2}" >
                                                                            <td style="text-align: right;"> <%= decimal.format((Double) pageContext.getAttribute("campo"))%> </td>
                                                                        </c:if>
                                                                        <c:if test="${mov.count le 1}" >
                                                                            <td style="text-align: left;"> ${campo}</td>
                                                                        </c:if>                                
                                                                    </c:forEach>
                                                                </tr>
                                                            </c:forEach>
                                                        </tbody>
                                                        <thead>
                                                            <tr><th style="text-align: center;">TOTAL</th>
                                                                    <%
                                                                        Vector totales1 = (Vector) session.getAttribute("vector_consulta_total");
                                                                        for (int ii = 0; ii < totales1.size(); ii++) {
                                                                            if ((ii % 2) == 0) {
                                                                                out.println("<th style=\"text-align: right;\">" + decimal.format((Double) totales1.get(ii + 1)) + "</th>");

                                                                            }
                                                                        }
                                                                    %>
                                                            </tr></thead>                                    
                                                    </table>
                                                </td>
                                                <!--<td  colspan="2"> &nbsp; </td>-->
                                            </tr>
                                        </c:if>
                                    </table>
                                </td>
                            </tr>           


                            <!--</table>-->
                            <tr><td>&nbsp;</td></tr>  
                            <tr><td colspan="6">&nbsp;</td></tr>
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

