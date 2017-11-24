<!--
     Creado por:                   Luis Antio Valerio Gayosso
     Fecha:                        16/02/2011
     Descripción:                  Vista : "login.jsp" Pantalla de Bienvenida
     Responsable:                  Carlos Altamirano
-->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <!--<link rel="shortcut icon" href="images/icono.png">-->
        <title> Ingresar </title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- Aqui importamos los estilos que necesitemos-->
        <link rel="stylesheet" type="text/css" href="css/formato.css">
        <!-- Aqui importamos los scripts que necesitemos-->
        <script language="javascript" type="text/javascript" src="scripts/login.js"></script>
    </head>

    <body style=" background-color: rgb(247, 232, 227);"
          <c:if test="${sessionScope.messageBean != null && sessionScope.messageBean.desc != ''}">
              onLoad="alert('<c:out value="${sessionScope.messageBean.desc}"/>');"
              <c:remove var="messageBean" scope="session" />
          </c:if>
          class="letraDefault"
          >
        <c:remove var="userApp" scope="session" /> <br>
        <div id="agrupar">
            <header id="cabecera">
                <a href="http://fideicomisogds.mx/Liquidaciones" style="cursor:inherit"> 
                    <img src="images/logo.png" alt="Garante" height="120" width="250" /> 
                </a>
            </header>
            <article id="login">
                <form name="formLogin" method="post" action="ControllerUpload">
                    <input type="hidden" name="accion" value=""/>
                    <div style="margin-left: 10%;">
                        <!--<img width="250px" height="100px" src="images/logo.jpg"/>-->
                    </div>

                    <div align="center">
                        <table align="center"  class="form-login">
                            <tr><td>
                                    <table  align="center" >
                                        <tr><td colspan="2">&nbsp;</td></tr>
                                        <tr><td colspan="2" class="encabezado" align="center"> SISTEMA DE LIQUIDACIONES  </td></tr>
                                        <tr><td colspan="2">&nbsp;</td></tr>
                                        <tr>
                                            <td>Clave de Cliente</td>
                                            <td align="left">
                                                <input type="text" name="custNum"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>Nombre de Usuario</td>
                                            <td align="left">
                                                <input type="text" name="usuario"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>Contraseña</td>
                                            <td align="left">
                                                <input type="password" name="contrasenna"/>
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
                            </tr>
                        </table>
                    </div>
                </form>
            </article>
            <aside id="noticias"></aside>
            <footer id="pie">
                <p>Garante Desarrollo y Salud, S.A. de C.V. SOFOM E.N.R.</p>
                <p>Río Mississippi #49 Piso 14 Int 1405, Colonia Cuauhtémoc, Delegación Cuauhtémoc, Ciudad de México, 06500</p>
                <p>Tel&eacute;fono: (55) 4164 3210 </p>
                <p><a href="mailto:contacto@garante.mx">contacto@garante.mx</a></p>
                <p><a href="http://www.garante.mx/">www.garante.mx</a></p>
            </footer> 
        </div>
    </body>
</html>
