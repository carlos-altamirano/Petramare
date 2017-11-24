<!--
     Creado por:                   Luis Antio Valerio Gayosso
     Fecha:                        9/12/2011, 10:46:56 AM
     Descripción:                  Vista : "menuLoadFile.jsp" vista del resumen de liquidación.
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
                <title>Liquidaciones</title>
                <!-- Aqui importamos los estilos que necesitemos-->
                <link rel="stylesheet" type="text/css" href="css/formato.css">
                <script language="javascript" type="text/javascript" src="scripts/login.js"></script>
                <script language="javascript" type="text/javascript" src="scripts/cargaLayOut.js"></script>
            </head>

            <body
                <c:if test="${sessionScope.messageBean != null && sessionScope.messageBean.desc != ''}">
                    onLoad="alert('<c:out value="${sessionScope.messageBean.desc}"/>');"
                    <c:remove var="messageBean" scope="session" />
                </c:if>
                class="letraDefault"
                >

                <form name="userConfirm" method="post" action="ControllerCapture">
                    <input type="hidden" name="accion" value=""/>
                    <input type="hidden" name="nombresObjetos" value=""/>
                    <input type="hidden" name="urlResponse" value=""/>


                    <c:if test="${sessionScope.resumenMovs != null && sessionScope.confirmaUsuario==null }">

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
                        <tr><td>&nbsp;</td></tr>
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

                            <tr><td colspan="2">&nbsp;</td></tr>

                            <tr >
                                <td colspan="3" align="center">
                                    <input type="button" name="Confirmar" value="Confirmar" onClick="return confirmarUsuario(userConfirm,'confirmaUsuario:4');"/>
                                    <input type="button" name="Cancelar" value="Cancelar" onClick="return atras(userConfirm,'cancelaOperacionManual:5','fecha_liquidacion;resumenMovs;confirmaUsuario','menuLoadFile.htm');"/>
                                </td>
                            </tr>
                        </table>
                    </c:if>

                    <%--
                         Confirmación para almacenar la información.
                    --%>
                    <c:if test="${sessionScope.confirmaUsuario != null && sessionScope.resumenMovs != null}">

                        <table align ="center" width="60%" >
                            <tr >
                                <td align="center" width="60%" >&nbsp;</td>
                                <td align="right">
                                    <b><a   href="" onclick="return atras(userConfirm,'eliminaBeans:100','confirmaUsuario;imprimeResumen;messageClean','LiquidationSummary.htm');" target="main"><u>regresar</u></a></b>
                                </td>

                            </tr>
                        </table>


                        <table align="center"  class="form-UserConfim">
                            <tr>  <td colspan="2"> &nbsp; </td></tr>

                            <tr align="center" class="Titulo">
                                <td colspan="2" align="center"> CONFIRMACIÓN </td>
                            </tr>

                            <tr>  <td colspan ="2"> &nbsp; </td></tr>

                            <tr>
                                <td align="right" class="Subtitulo">Usuario</td>
                                <td align="left">
                                    <input type="text" name="usuario" style="width:120px;"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="right" class="Subtitulo">Contraseña</td>
                                <td align="left">
                                    <input type="password" name="contrasenna" style="width:120px;"/>
                                </td>
                            </tr>
                            <tr><td colspan="2">&nbsp;</td></tr>
                            <tr align="center">
                                <!--<td> &nbsp;</td>-->
                                <td>
                                    <input type="button" name="login" value="Cancelar" onclick="return atras(userConfirm,'eliminaBeans:100','confirmaUsuario;imprimeResumen;messageClean','LiquidationSummary.htm');"/>
                                </td>                                   
                                <td>
                                    <input type="button" name="login" value="Enviar" onClick="return loginUsuario2(userConfirm,'GuardaLayOutManual:6')"/>
                                </td>
                                <!--<td> &nbsp;</td>-->  
                                <td> &nbsp;</td>
                            </tr>
                            <tr>  <td colspan ="2"> &nbsp; </td></tr>
                            <tr>  <td colspan ="2"> &nbsp; </td></tr>
                        </table>
                    </form>
                </c:if>

            </body>
        </html>
    </c:when>
    <c:otherwise>
        <c:redirect url="UserLogin.htm" />
    </c:otherwise>
</c:choose>
