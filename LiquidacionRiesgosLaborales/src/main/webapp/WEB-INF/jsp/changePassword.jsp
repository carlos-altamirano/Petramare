<%-- 
    Document   : changePassword
    Created on : 23/05/2011, 04:06:16 PM
    Author     : Luis Antio Valerio Gayosso
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<c:choose>
    <c:when test="${sessionScope.userApp != null}">
        <html>
            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <!-- Aqui importamos los estilos que necesitemos-->
                <link rel="stylesheet" type="text/css" href="css/formato.css">
                <!-- Aqui importamos los scripts que necesitemos-->
                <script language="javascript" type="text/javascript" src="scripts/login.js"></script>
                  <script language="javascript" type="text/javascript" src="scripts/liquidation.js"></script>
                <title>Cambio de Password</title>
                <link rel="shortcut icon" href="images/icono.png">
            </head>

            <body <c:if test="${sessionScope.messageBean != null && sessionScope.messageBean.desc != ''}">
                    onLoad="alert('<c:out value="${sessionScope.messageBean.desc}"/>');"
                    <c:remove var="messageBean" scope="session" />
                </c:if>
                >

                <br>

                <form name="formChangePassword" method="post" action="ControllerLiquidation">
                    <input type="hidden" name="accion" value=""/>

                    <c:if test="${sessionScope.cambiaPassword == null }">

                        <table width="100%">
                            <tr><td colspan="2">&nbsp;</td></tr>
                            <tr>
                                <td width="75%">&nbsp;</td>
                                <td align ="left" width="15%">
                                    <b ><a href="" onclick="return setAction(formChangePassword,'cambiaPassword:9');" target="main"><u>Regresar</u></a></b>
                                </td>
                            </tr>
                        </table>

                        <table align="center" bgcolor="#ddf8cc" style="border-radius: 4px;">

                            <tr><td >&nbsp;</td></tr>

                            <tr>
                                <td  align="center" class="Titulo">&nbsp;&nbsp;&nbsp;&nbsp;CONFIRMACIÓN DE DATOS&nbsp;&nbsp;&nbsp;&nbsp;
                                </td>
                            </tr>

                            <tr><td >&nbsp;</td></tr>

                            <tr>
                                <td>
                                    <table align="center">

                                        <tr class="Subtitulo">
                                            <td>Usuario</td>
                                            <td align="left">
                                                <input type="text" name="usuario" style="width:120px;"/>
                                            </td>
                                        </tr>

                                        <tr class="Subtitulo">
                                            <td>Password</td>
                                            <td align="left">
                                                <input type="password" name="contrasenna" style="width:120px;"/>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>

                            <tr><td >&nbsp;</td></tr>

                            <tr align="center">
                                <td >
                                    <input type="button"  class="NormalBoton" name="login" value="Aceptar" onClick="return loginUsuario(formChangePassword,'cambiaPassword:7')"/>
                                </td>
                            </tr>
                            <tr><td >&nbsp;</td></tr>
                        </table>
                    </c:if>

                    <c:if test="${sessionScope.cambiaPassword != null && sessionScope.cambiaPassword != ''}">

                        <table width="100%">
                            <tr><td colspan="2">&nbsp;</td></tr>
                            <tr>
                                <td width="75%">&nbsp;</td>
                                <td align ="left" width="15%">
                                    <b ><a href="" onclick="return setAction(formChangePassword,'cambiaPassword:9');" target="main"><u>Regresar</u></a></b>
                                </td>
                            </tr>
                        </table>

                        <table align="center" bgcolor="#ddf8cc" style="border-radius: 4px;" >
                            <tr><td colspan="">&nbsp;</td></tr>
                            <tr>
                                <td align="center" class="Titulo">&nbsp;&nbsp;&nbsp;&nbsp;NUEVA CONTRASEÑA&nbsp;&nbsp;&nbsp;&nbsp;
                                </td>
                            </tr>

                            <tr><td>&nbsp;</td></tr>

                            <tr>
                                <td>
                                    <table>
                                        <tr>
                                            <td class="Subtitulo">Nuevo Password</td>
                                            <td align="left">
                                                <input type="password" name="newPass1" style="width:120px;"/>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td class="Subtitulo">Confirma Password</td>
                                            <td align="left">
                                                <input type="password" name="newPass2" style="width:120px;"/>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>


                            <tr><td colspan="">&nbsp;</td></tr>

                            <tr align="center">
                                <td colspan="">
                                    <input type="button"  class="NormalBoton" name="login" value="Cambiar" onClick="return actualizaPassword(formChangePassword,'cambiaPassword:8')"/>
                                </td>
                            </tr>

                            <tr><td colspan="">&nbsp;</td></tr>
                        </table>
                    </c:if>
                </form>
            </body>
        </html>
    </c:when>
    <c:otherwise>
        <c:redirect url="login.htm" />
    </c:otherwise>
</c:choose>
