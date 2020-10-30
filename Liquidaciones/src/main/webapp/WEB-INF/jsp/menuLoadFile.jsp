<%@ page import="java.text.DecimalFormat"%>
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
                <script language="javascript" type="text/javascript" src="scripts/cargaLayOut.js"></script>
                <script language="javascript" type="text/javascript" src="scripts/login.js"></script>
            </head>

            <body
                <c:if test="${sessionScope.messageBean != null && sessionScope.messageBean.desc != ''}">
                    onLoad="alert('<c:out value="${sessionScope.messageBean.desc}"/>');"
                    <c:remove var="messageBean" scope="session" />
                </c:if>

                <c:if test="${sessionScope.messageClean != null}">
                    onLoad="return limpiaSesion('eliminaBeans:100','messageClean;imprimeResumenMovimientos;fileLoad;fileName;errores;resumenMovimientos;confirmaUsuario','menuLoadFile.htm');"
                    <c:remove var="messageClean" scope="session" />
                </c:if>
                class="letraDefault"
                >

                <c:if test="${sessionScope.imprimeResumenMovimientos == null }">
                    <form name="formSalir" method="post" action="ControllerUpload">
                        <input type="hidden" name="accion" value=""/>
                        <input type="hidden" name="nombresObjetos" value=""/>
                        <input type="hidden" name="urlResponse" value=""/>

                        <table align ="center" width="100%" class="letraDefault">
                            <tr >
                                <td width="75%">&nbsp;</td>
                                <td align ="center" width="15%">
                                    <b ><a href="" onclick="return cambiaPassword(formSalir, 'cambiaPassword:6');" target="main"><u>cambiar contraseña</u></a></b>
                                </td>
                                <td>
                                    <b><a href="" onclick="return atras(formSalir, 'eliminaBeans:100', 'fileLoad;fileName;errores;resumenMovimientos', 'UserLogin.htm');" target="main"><u>salir</u></a></b>
                                </td>
                            </tr>
                        </table>
                    </form>
                </c:if>


                <form name="formLoad" method="post" enctype="multipart/form-data" action="ControllerUpload">
                    <input type="hidden" name="accion" value=""/>

                    <c:if test="${sessionScope.imprimeResumenMovimientos == null }">
                        <table align="center">
                            <tr >
                                <td colspan="3" class="Titulo" align="center">
                                    <c:out value="${sessionScope.userApp.cliente}"/>
                                </td>
                            </tr>

                            <tr><td colspan="3">&nbsp;</td></tr>

                            <tr>
                                <td colspan="3" class="Subtitulo" align="center">
                                    SISTEMA DE LIQUIDACIONES
                                </td>
                            </tr>

                            <tr><td colspan="3">&nbsp;</td></tr>

                            <tr>
                                <td colspan="3" align="center">
                                    <input type="file" style="" name="archivo"/>
                                </td>
                            </tr>

                            <tr><td colspan="3">&nbsp;</td></tr>

                            <tr align="center">
                                <td colspan="3">
                                    <table align="center">
                                        <tr>
                                            <td>
                                                <input type="button" class="NormalBoton" name="cargaArchivo" value=" Archivo " onClick="return cargaValor(formLoad, 'validaCargaLayOut:2');"/>
                                            </td>
                                            <td >
                                                <input type="button" class="NormalBoton" name="cargaManual" value=" Manual " onClick="return cargaValor(formLoad, 'cargaManual:10');"/>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>

                            <tr><td colspan="3">&nbsp;</td></tr>


                        </table>
                    </c:if>
                </form>

                <%-- Modulo de saldo --%>
                <c:if test="${(sessionScope.resumen_saldo != null) and (sessionScope.imprimeResumenMovimientos==null)}">
                    <form action="Saldo.do" name="dataSaldo" method="post">
                        <% DecimalFormat formatoDinero = new DecimalFormat("$ #,##0.00");%>
                        <table align="center" width="30%">
                            <tr><td colspan="4">&nbsp;</td></tr>
                            <tr>
                                <td colspan="2" class="Titulo" align="left">
                                    Patrimonio Fideicomitido
                                </td>
                                <td colspan="2" class="Subtitulo" align="right">
                                    <a href="" onclick="return generaSaldo(dataSaldo);" target="main"><u>Ver detalles</u></a>
                                </td>                            
                            </tr>
                            <tr><td colspan="4">&nbsp;</td></tr>
                            <tr colspan="4" align="center" ><td colspan="4">
                                    <table colspan="4" class="tabla_saldos" align="center" width="100%">
                                        <tbody>
                                            <tr align="left">
                                                <td>Patrimonio Disponible</td>
                                                <td>&nbsp;</td>
                                                <td style="width:130px;text-align: right;"> <c:out value='<%=(session.getAttribute("saldo_actual") == null ? ("$ 0.00") : (formatoDinero.format(session.getAttribute("saldo_actual"))))%>' default=" $ 0.00" /> </td>
                                            </tr>
                                            <%--                                      <tr>
                                                                                  <td>Saldo por pagar</td>
                                                                                  <td>&nbsp;</td>
                                                                                  <td style="width:130px;text-align: right;"> <c:out value='<%=(session.getAttribute("saldo_actual")==null?("$ 0.00"):(formatoDinero.format(session.getAttribute("saldo_por_pagar"))))%>' default=" $ 0.00" /> </td>
                                                                                  </tr>--%>
                                        </tbody>
                                    </table>
                                    <!--                                            </td>
                                                                            </tr>
                                                                        </table>-->
                                </td>
                            </tr>   
                        </table>
                    </form>
                </c:if>

                <form name="dataLoad" method="post" action="ControllerUpload">
                    <input type="hidden" name="accion" value=""/>
                    <input type="hidden" name="nombresObjetos" value=""/>
                    <input type="hidden" name="urlResponse" value=""/>

                    <c:if test="${sessionScope.fileLoad != null && sessionScope.confirmaUsuario==null}">
                        <c:if test="${sessionScope.errores != null && sessionScope.confirmaUsuario==null}">
                            <table align ="center">
                                <tr class="Titulo">
                                    <td>  Errores Obtenidos al Validar el Archivo </td>
                                </tr>
                                <tr> <td>&nbsp;</td> </tr>
                            </table>
                            <table align="center" width="70%" class="form-errorCapture">
                                <tr  class="Subtitulo">

                                    <td align="center">  Línea </td>

                                    <td align="center">  Campo  </td>

                                    <td align="center">  Descripción del campo </td>

                                    <td align="center">  Descripción corta del error </td>

                                </tr>
                                <c:set var="listaErrores" value="${sessionScope.errores}"/>
                                <c:forEach items="${listaErrores}" var="error" varStatus="status">

                                    <tr
                                        <c:choose>
                                            <c:when test="${ (status.count % 2) == 0 }">
                                                style="background-Color:#E3F2FE;"
                                            </c:when>
                                            <c:otherwise>
                                                style="background-Color:#FFFFFF;"
                                            </c:otherwise>
                                        </c:choose>
                                        >

                                        <td align="center"  ><c:out value="${error[0]}"/> </td>

                                        <td align="center" ><c:out value="${error[1]}"/> </td>

                                        <td align="left" ><c:out value="${error[2]}"/> </td>

                                        <td align="left" ><c:out value="${error[3]}"/> </td>

                                    </tr>
                                </c:forEach>
                            </table>
                        </c:if>
                    </c:if>


                    <c:if test="${sessionScope.resumenMovimientos != null && sessionScope.confirmaUsuario==null}">
                        <table align="center" >
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
                                            <td colspan="2" align="left"  style="background-Color:#E3F2FE;" >
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

                            <tr><td colspan="2">&nbsp;</td></tr>
                            <tr> <td>
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

                            <tr><td colspan="2">&nbsp;</td></tr>

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
                            <tr><td colspan="3">&nbsp;</td></tr>

                            <tr>
                                <td colspan="3" align="center">
                                    <input type="button" name="Confirmar" value="Confirmar" onClick="return confirmarUsuario(dataLoad, 'confirmaUsuario:5');"/>
                                    <input type="button" name="Cancelar" value="Cancelar" onClick="return atras(dataLoad, 'eliminaBeans:100', 'fileLoad;fileName;errores;resumenMovimientos', 'menuLoadFile.htm');"/>
                                </td>
                            </tr>
                        </table>
                    </c:if>



                    <%--
                         Confirmación para almacenar información
                    --%>
                    <br>
                    <c:if test="${sessionScope.confirmaUsuario != null && sessionScope.resumenMovimientos != null}">
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
                                <td>
                                    <input type="button" name="cancelar" value="Cancelar" onClick="return atras(dataLoad, 'eliminaBeans:100', 'fileLoad;fileName;errores;resumenMovimientos', 'menuLoadFile.htm');"/>
                                </td>                                     
                                <td>
                                    <input type="button" align="right" name="login" value="Enviar" onClick="return loginUsuario2(dataLoad, 'GuardaLayOut:3')"/>
                                </td>                           
                            </tr>
                            <tr>  <td colspan ="2"> &nbsp; </td></tr>
                        </table>
                    </c:if>
                </form>

                <%--
                    Información a Imprimir
                --%>
                <c:if test="${sessionScope.imprimeResumenMovimientos != null }">

                    <c:set var="resumenMovimientos" value="${sessionScope.imprimeResumenMovimientos}"/>

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

                        <tr><td colspan="2">&nbsp;</td></tr>
                        <tr> <td>
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

                        <tr><td colspan="2">&nbsp;</td></tr>

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

                    </table>
                </c:if>

            </body>
        </html>
    </c:when>
    <c:otherwise>
        <c:redirect url="UserLogin.htm" />
    </c:otherwise>
</c:choose>
