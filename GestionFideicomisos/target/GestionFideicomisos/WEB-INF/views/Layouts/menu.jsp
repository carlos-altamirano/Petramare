<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row expanded">
    <div class="top-bar">
        <div class="top-bar-title">
            <strong><a href="/gestionfideicomisos" class="" style="color:white;">Gestion Fideicomisos</a></strong>
        </div>

        <div id="responsive-menu">
            <div class="top-bar-left">
                <ul class="dropdown menu" data-dropdown-menu></ul>
            </div>
        </div>

        <div class="top-bar-right">
            <ul class="menu">
                <li><strong id="usernameopera"><c:out value="${userName}"/></strong></li>
                <li><a href="/gestionfideicomisos/perfil/password">Cambiar contraseña</a></li>
                <li><a href="/gestionfideicomisos/logout">Salir</a></li>
            </ul>
        </div>
    </div>
</div>