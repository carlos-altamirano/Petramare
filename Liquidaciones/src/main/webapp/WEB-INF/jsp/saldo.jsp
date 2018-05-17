<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.Vector"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<c:choose>
    <c:when test="${sessionScope.userApp != null}">
        <html>
            <head>
                <link rel="shortcut icon" href="images/icono.png">
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <link rel="stylesheet" type="text/css" href="css/Saldo.css">
                <script language="javascript" type="text/javascript" src="scripts/popCalendar.js"></script>
                <script language="javascript" type="text/javascript" src="scripts/Saldo.js"></script>
                <title>Consulta de movimientos</title>
            </head>
            <body 
                <c:if test="${sessionScope.messageBean != null && sessionScope.messageBean.desc != ''}">
                    onLoad="alert('<c:out value="${sessionScope.messageBean.desc}"/>');"
                    <c:remove var="messageBean" scope="session" />
                </c:if>
                >
                <%--<c:if test="${sessionScope.imprimeResumenMovimientos == null }">--%>
                <form name="saldo" method="post" action="Saldo.do">
                    <input type="hidden" name="nombreRL" value=""/>
                    <input type="hidden" name="fecha_liquidacion" value=""/>
                    <input type="hidden" name="accion" value=""/>
                    <input type="hidden" name="nombresObjetos" value=""/>
                    <input type="hidden" name="urlResponse" value=""/>

                    <table  width="100%" align ="center">
                        <tr>
                            <!--<td width="75%">&nbsp;</td>-->
                            <td align="right">
                                <b><a href="" onclick="return generaAccion(saldo, 'fecha_ini;fecha_fin;reporte;liquidaciones;reporteSize;liquidacionesSize', 'Regresar-Principal:100');" target="main" ><u>regresar</u></a></b>
                            </td>
                        </tr>
                        <tr><td>&nbsp;</td></tr>
                        <tr>
                            <td class="Titulo" align="center">
                                <c:out value="${sessionScope.userApp.cliente}"/>
                            </td>
                        </tr>

                        <tr><td colspan="6" >&nbsp;</td> </tr>
                        <tr>
                            <td>
                                <table width="80%" align="center">
                                    <tr><td  class="Titulo" align="center" colspan="6">
                                            Movimientos Fiduciarios
                                            <!--<a href="" onclick=" return generaAccion('muestraPDF.do');" target="main"><u>Movimientos fiduciarios</u></a>-->                                    
                                        </td>
                                        <%--<jsp:forward page="muestraPDF.do" />--%>
                                    </tr> 
                                </table>
                            </td>
                        </tr>                                                      

                        <tr align="center"> 
                            <td colspan="6">
                                <table style="alignment-adjust: central">
                                    <!--<tr><td  colspan="6"> &nbsp; </td></tr>-->                          
                                    <tr><td  colspan="6"> &nbsp; </td></tr>
                                    <tr>
                                        <td class="Subtitulo" align="center">
                                            Tipo de movimiento: &nbsp;  
                                        </td>
                                        <td  class="Subtitulo" align="center"  rowspan="2">
                                            peri&oacute;do     de
                                        </td>
                                        <td  align="center"  rowspan="2">
                                            <!--formato de fecha para Server Principal-->
                                            <input type="text" name="fecha_ini" id="fecha_ini_" value="<c:out value='${sessionScope.fecha_ini}' />" onClick="return popUpCalendar(this, saldo.fecha_ini_, 'yyyy-mm-dd');" />
                                            <%--<input type="text" name="fecha_ini" id="fecha_ini_" value="<c:out value='${sessionScope.fecha_ini}' />" onClick="return popUpCalendar(this, saldo.fecha_ini_, 'dd/mm/yyyy');" />--%>
                                        </td>
                                        <td  class="Subtitulo" align="center"  rowspan="2">
                                            a
                                        </td>  
                                        <td  align="center"  rowspan="2">
                                            <%--<input type="text" name="fecha_fin" id="fecha_fin_" value="<c:out value='${sessionScope.fecha_fin}' />" onClick="return popUpCalendar(this, saldo.fecha_fin_, 'dd/mm/yyyy');" />--%>
                                            <input type="text" name="fecha_fin" id="fecha_fin_" value="<c:out value='${sessionScope.fecha_fin}' />" onClick="return popUpCalendar(this, saldo.fecha_fin_, 'yyyy-mm-dd');" />
                                        </td>        
                                        <td class="buscar" align="center"  rowspan="2">
                                            <input type="submit" value="Consultar" name="buscar" onclick="generaAccion('', '', 'Saldo.do');" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="center">
                                            <select name="tipo_operacion" onchange="generaAccion('', '', 'Saldo.do')">
                                                <option value=""            <c:out value='${requestScope.selT}' default="" />>     TODOS   </option>
                                                <option value="RESTITUCION" <c:out value='${requestScope.selR}' default="" />>   RESTITUCI&Oacute;N   </option>
                                                <option value="APORTACION"  <c:out value='${requestScope.selA}' default="" />>   APORTACI&Oacute;N   </option>
                                                <option value="LIQUIDACION" <c:out value='${requestScope.selL}' default="" />>   LIQUIDACI&Oacute;N   </option>
                                            </select>
                                        </td>
                                        <td colspan="5"></td>
                                    </tr> 
                                    <td colspan="6" >&nbsp;</td> 
                                    <td colspan="6" >&nbsp;</td>                            

                                    <tr  class="Subtitulo">
                                        <td colspan="6" align="center">  &nbsp; Se han encontrado 
                                            &nbsp;<font color="green"> <b> ${sessionScope.reporteSize} </b>  </font> 
                                            &nbsp; registros. </td>
                                        <!--<td colspan="4">&nbsp;</td>-->
                                    </tr>
                                    <c:if test="${sessionScope.reporte !=null}" >
                                        <tr> 
                                            <td colspan="6">
                                                <table class="tabla_saldo">
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
                                                                        <c:if test="${(mov.count ge 4) and (mov.count ne 2) and (mov.count ne 7)}" >
                                                                            <td style="text-align: right;"> ${campo} </td>
                                                                        </c:if>
                                                                        <c:if test="${(mov.count le 3) and (mov.count ne 2) and (mov.count ne 7)}" >
                                                                            <td> ${campo}</td>
                                                                        </c:if>
                                                                        <c:if test="${(mov.count eq 2) and (mov.count ne 7)}" >
                                                                            <td>
                                                                                <c:if test="${fn:contains(campo, 'ORDEN')}">
                                                                                    <!--                                                                <table class="tabla_saldo">
                                                                                                                                                        <tr>
                                                                                                                                                        <td>-->
                                                                                    ${campo} &nbsp; &nbsp; &nbsp;
                                                                                    <!--                                                                    </td>                                                                        
                                                                                                                                                        <td>                                                                        -->
                                                                                    <%--  ${campo}  &nbsp;  --%>
                                                                                    <%--<div style="color: green; cursor: pointer;" onclick="return generaAccion('${movimiento[0]}','${movimiento[6]}','muestraPDF.do');" > Descargar archivo</div>--%>
                                                                                    <img src="./images/pdf.jpg" style="cursor: pointer;" onclick=" return generaAccion('${movimiento[0]}', '${movimiento[6]}', 'muestraPDF.do');" width="32" height="32" alt="downloadPDF"/>                                                                  
                                                                                    <!--                                                                    </td>                                                                    
                                                                                                                                                        </tr>
                                                                                                                                                        </table>-->
                                                                                </c:if>    
                                                                                <c:if test="${not fn:contains(campo, 'ORDEN')}">
                                                                                    ${campo}
                                                                                </c:if>                                                                   
                                                                            </td>
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


                        <tr>
                            <td>
                                &nbsp;
                            </td>
                        </tr>  
                        <tr>
                            <td>
                                <table width="80%" align="center">
                                    <tr><td  class="Titulo" align="center" colspan="6">
                                            Liquidaciones pendientes
                                    </tr></td> 
                    </table>
                </td>
            </tr>                                                           
            <tr align="center"> 
                <td colspan="6">
                    <table style="alignment-adjust: central">
                        <tr><td colspan="6" > &nbsp;</td></tr>
                        <tr  class="Subtitulo">
                            <td colspan="2" align="rigth">  &nbsp; Se han encontrado 
                                &nbsp; <font color="green"> <b> ${sessionScope.liquidacionesSize} </b>  </font> 
                                &nbsp; registros. </td>
                            <td colspan="4">&nbsp;</td>
                        </tr>
                        <!--<tr><td colspan="6" > &nbsp;</td></tr>-->
                        <c:if test="${sessionScope.liquidaciones !=null }" >
                            <tr>
                                <!--<td colspan="2" >&nbsp;</td>-->
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

            <tr><td colspan="6"> &nbsp; </td></tr>         
            <tr>
                <td>
                    <table width="80%" align="center">
                        <tr><td  class="Titulo" align="center" colspan="6">
                                Suficiencia Patrimonial Requerida en Moneda Nacional
                        </tr></td> 
                </table>
                </td>
            </tr>                         
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
            <%--                        <tr><td>&nbsp;</td></tr>
                                    <tr>
                                        <td colspan="6" align="center">
                                            <table class="tabla_saldos">
                                             <tbody>
                                               <c:forEach items="${saldos}" var="campo" varStatus="mov">  
                                                   <c:if test="${mov.count ge 7}" >
                                                   <c:if test="${(mov.count mod 2)!=0}" >
                                                   <tr>
                                                           <td style="text-align: left;"> <b>  ${campo} </b> </td>
                                                   </c:if>
                                                   <c:if test="${(mov.count mod 2)==0}" >
                                                       <td style="text-align: right;color: red;"> <b>  ${campo} </b> </td> 
                                                       </tr>
                                                   </c:if>  
                                                   </c:if>                                            
                                               </c:forEach>                                     
                                             </tbody>                                    
                                            </table>
                                        </td>
                                        </tr>--%>

            <tr><td colspan="6">&nbsp;</td></tr>

            <%-- MODULO DE DESCARGA DE ESTADO DE CUENTA DE UN MES ESPECIFICO --%>
            <tr>
                <td>
                    <table width="80%" align="center">
                        <tr><td  class="Titulo" align="center" colspan="6">
                                Estado de cuenta
                                <!--<a href="" onclick=" return generaAccion('muestraPDF.do');" target="main"><u>Movimientos fiduciarios</u></a>-->                                    
                            </td>
                            <%-- <jsp:forward page="muestraPDF.do" />--%>
                        </tr> 
                    </table>
                </td>
            </tr>  
            <%-- MOSTRAR ESTADOS DE CUENTA DISPONIBLES --%>
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
                                <td class="button" onclick=" return generaAccion('', '', 'muestraEdoCta');" align="right">Descargar</td>
                            </tr>
                        </c:if>
                    </table>
                </td>
            </tr>   
            <%-- TERMINA MODULO DE DESCARGA DE ESTADO DE CUENTA--%>              
            <tr><td  colspan="6"> &nbsp; </td></tr>
            <tr><td  colspan="6"> &nbsp; </td></tr>

        </table>
    </form>
    <script>
        function generaAccion(fecha_autorizacion, nombre_archivo, accionDeseada) {
            document.saldo.action = accionDeseada;
            if (accionDeseada == 'muestraPDF.do') {
                document.saldo.fecha_liquidacion.value = fecha_autorizacion;
                document.saldo.nombreRL.value = nombre_archivo;
                document.saldo.accion.value = 'descargaReporteLQ';
            }
            if (accionDeseada == 'muestraEdoCta') {
                var indice = document.saldo.selComboPeriodo.selectedIndex;
                var valor = document.saldo.selComboPeriodo.options[indice].value;
                document.saldo.accion.value = 'descargaEdoCta';
                document.saldo.nombreRL.value = valor;
                document.saldo.action = 'muestraPDF.do';
            }
            if (accionDeseada == 'Regresar-Principal:100') {
                document.saldo.action = 'ControllerUpload';
                document.saldo.accion.value = accionDeseada;
                document.saldo.nombresObjetos.value = nombre_archivo;
                document.saldo.urlResponse.value = 'menuLoadFile.htm';
                document.saldo.submit();
                return false;
            }
            document.saldo.submit();
        }
    </script>
    <script>
        function recarga() {
            window.location.reload();
        }
    </script>         


</body>
</html>
</c:when>
<c:otherwise>
    <c:redirect url="UserLogin.htm" />
</c:otherwise>
</c:choose>

