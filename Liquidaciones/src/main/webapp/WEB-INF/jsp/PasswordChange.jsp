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
                <title> Cambiar contraseña </title>
                <!-- Aqui importamos los estilos que necesitemos-->
                <link rel="stylesheet" type="text/css" href="css/formato.css">
                <!-- Aqui importamos los scripts que necesitemos-->
                <script language="javascript" type="text/javascript" src="scripts/login.js"></script>
                <title>cambio de contraseña</title>
            </head>

            <body <c:if test="${sessionScope.messageBean != null && sessionScope.messageBean.desc != ''}">
                    onLoad="alert('<c:out value="${sessionScope.messageBean.desc}"/>');"
                    <c:remove var="messageBean" scope="session" />
                </c:if>
                class="letraDefault"
                >
                <br>
                <form name="formChangePassword" method="post" action="ControllerUpload">
                    <input type="hidden" name="accion" value=""/>
                    <table width="100%">
                        <tr><td colspan="2">&nbsp;</td></tr>
                        <tr>
                            <td width="75%">&nbsp;</td>
                            <td align ="left" width="15%">
                                <b ><a href="" onclick="return setAction(formChangePassword,'cambiaPassword:9');" target="main"><u>regresar</u></a></b>
                            </td>
                        </tr>
                    </table>
                    <c:if test="${sessionScope.cambiaPassword == null }">
                        <table align="center" >
                            <tr><td >&nbsp;</td></tr>
                            <tr>
                                <td>
                                    <table align="center"  class="form-UserConfim" >
                                        <tr>  <td colspan="2"> &nbsp; </td></tr>
                                        <tr>
                                            <td  align="center" colspan="2" class="Titulo"> FAVOR DE CONFIRMAR USUARIO </td>
                                        </tr>
                                        <tr>  <td colspan="2"> &nbsp; </td></tr>
                                        <tr class="Subtitulo">
                                            <td align="right">Cliente</td>
                                            <td align="left">
                                                <input type="text" name="custNum" style="width:120px;"/>
                                            </td>
                                        </tr>

                                        <tr class="Subtitulo">
                                            <td align="right">Usuario</td>
                                            <td align="left">
                                                <input type="text" name="usuario" style="width:120px;"/>
                                            </td>
                                        </tr>

                                        <tr class="Subtitulo">
                                            <td align="right">Contraseña</td>
                                            <td align="left">
                                                <input type="password" name="contrasenna" style="width:120px;"/>

                                            </td>
                                        </tr>
                                        <tr>  <td colspan="2"> &nbsp; </td></tr>

                                        <tr align="center">
                                            <td colspan="2">
                                                <input type="button"  class="NormalBoton" name="login" value="Aceptar" onClick="return loginUsuario(formChangePassword,'cambiaPassword:7')"/>
                                            </td>
                                        </tr>
                                        <tr><td >&nbsp;</td></tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </c:if>

                    <c:if test="${sessionScope.cambiaPassword != null && sessionScope.cambiaPassword != ''}">

                        <table align="center" class="form-UserConfim" >
                            <tr><td colspan="">&nbsp;</td></tr>
                            <tr>
                                <td align="center" class="Titulo">&nbsp;&nbsp;&nbsp;&nbsp;NUEVA CONTRASEÑA&nbsp;&nbsp;&nbsp;&nbsp;
                                </td>
                            </tr>

                            <tr><td>&nbsp;</td></tr>

                            <tr>
                                <td>
                                    <table >
                                        <tr>
                                            <td class="Subtitulo">Nueva Contraseña</td>
                                            <td align="left">
                                                <input type="password" name="newPass1" style="width:120px;"/>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td class="Subtitulo">Confirma Contraseña</td>
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
        <c:redirect url="UserLogin.htm" />
    </c:otherwise>
</c:choose>
