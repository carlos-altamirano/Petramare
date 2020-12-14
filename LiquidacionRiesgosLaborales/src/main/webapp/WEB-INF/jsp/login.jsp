<%@ page language="java" import="java.math.BigInteger" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <title> Ingresar </title>
        <link rel="shortcut icon" href="images/icono.png">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/formato.css">
        <script language="javascript" type="text/javascript" src="scripts/login.js"></script>
    </head>
    <body style="background-color: #fff;"
          <c:if test="${sessionScope.messageBean != null && sessionScope.messageBean.desc != ''}">
              onLoad="alert('<c:out value="${sessionScope.messageBean.desc}"/>');"
              <c:remove var="messageBean" scope="session" />
          </c:if> 
          >
        <c:remove var="userApp" scope="session" />
        <div id="agrupar">
            <header id="cabecera">
                <a href="/LiquidacionRiesgosLaborales" style="cursor:inherit"> 
                    <img src="images/logo.png" alt="Garante" height="120" width="250" /> 
                </a>
            </header>
            <article id="login">
                <form name="formLogin" method="post" action="ControllerLiquidation">
                    <input type="hidden" name="accion" value=""/>
                    <input type="hidden" name="pswd" value=""/>
                    <div style="margin-left: 10%;">
                        <!--<img width="250px" height="100px" src="images/logo.jpg"/>-->
                    </div>
                    <div align="center" >
                        <table align="center" class="form-login" >
                            <tr>
                                <td>
                                    <table  align="center" >
                                        <tr><td colspan="2">&nbsp;</td></tr>
                                        <tr>
                                            <td align="center" colspan="2"> ACCEDER AL SISTEMA </td>
                                        </tr>
                                        <tr><td colspan="2">&nbsp;</td></tr>
                                        <tr>
                                            <td  align="right">Nombre de Usuario</td>
                                            <td >
                                                <input type="text" name="usuario"  style="width:120px;"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td  align="right">Contraseña</td>
                                            <td >
                                                <input type="password" name="contrasenna"  style="width:120px;" />
                                            </td>
                                        </tr>
                                        <tr><td colspan="2">&nbsp;</td></tr>
                                        <tr align="right">
                                            <td colspan="2">
                                                <input type="button" name="login" value="Acceder" onClick="return loginUsuario(formLogin, 'loginUsuario:1')"/>
                                            </td>
                                        </tr>
                                        <tr><td colspan="2">&nbsp;</td></tr>
                                    </table>
                                </td>
                            <tr>
                        </table>
                    </div>
                </form>
            </article>
            <aside id="noticias"></aside>
            <footer id="pie">
                <p>Petramare Servicios Consolidados, S.A. de C.V. SOFOM E.N.R.</p>
				<p>Blvd. Manuel Ávila Camacho 92-A int 303 Piso 3, El Conde, Naucalpan de Juárez, Estado de México, 53500</p>
				<p>Tel&eacute;fono (55) 8848 3841 </p>
				<p><a href="mailto:contacto@petramare.com.mx">contacto@petramare.com.mx</a></p>
				<p><a href="http://www.petramare.com.mx/">www.petramare.com.mx</a></p>
            </footer>  
        </div>
    </body>
</html>
