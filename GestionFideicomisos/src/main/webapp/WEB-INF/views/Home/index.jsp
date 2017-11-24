<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Garante</title>
    <link rel="icon" type="image/png" href="/gestionfideicomisos/resources/img/icono.png" />
    <link rel="stylesheet" href="/gestionfideicomisos/resources/css/Foundation/foundation.min.css">
    <link rel="stylesheet" href="/gestionfideicomisos/resources/css/Foundation/foundation-icons/foundation-icons.css">
    <link rel="stylesheet" href="/gestionfideicomisos/resources/css/Home/index.css">
</head>
<body>
    <div class="top-bar">
        <div class="top-bar-title">
            <strong><a href="/gestionfideicomisos" style="color:white;">Garante</a></strong>
            <!--img src="/resources/img/fideifuturo.jpg" alt="Fideifuturo"-->
        </div>

        <div id="responsive-menu">
            <div class="top-bar-left">
                <ul class="dropdown menu" data-dropdown-menu></ul>
            </div>
        </div>

        <div class="top-bar-right">
            <ul class="menu">
            </ul>
        </div>
    </div>

    <div class="large-5 large-centered centered columns">
        <div class="login-box">
            <div class="row">
                <form action="/gestionfideicomisos/login" method="post">
                    <div class="row">
                        <div class="columns">
                            <h5 style="text-align: center;">SISTEMA DE LIQUIDACIONES</h5>
                            <label id="error-auth"></label><br>
                        </div>
                    </div>
                    <c:if test="${tipoForm eq 'cliente'}">
                    <div class="row">
                        <div class="large-12 columns input-group">
                            <span class="input-group-label"><i class="fi-compass"></i></span>
                            <input type="text" class="input-group-field" name="clave" placeholder="Clave cliente" required>
                        </div>
                    </div>
                    </c:if>
                    <div class="row">
                        <div class="large-12 columns input-group">
                            <span class="input-group-label"><i class="fi-torso"></i></span>
                            <input type="text" class="input-group-field" name="usuario" placeholder="Usuario" required>
                        </div>
                    </div>
                    <div class="row">
                        <div class="large-12 columns input-group">
                            <span class="input-group-label"><i class="fi-key"></i></span>
                            <input type="password" id="contra" class="input-group-field" name="password" placeholder="Contraseña" required>
                            <span class="input-group-label" data-tooltip aria-haspopup="true" class="has-tip" id="mostrarContra" value="true" title="Mostrar contraseña"><i id="cambiaIcono" class="fi-lock"></i></span>
                        </div>
                    </div>
                    <div class="row">
                        <div class="large-12 large-centered columns">
                            <input type="submit" class="button expanded" value="Login">
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script src="/gestionfideicomisos/resources/js/jquery.js"></script>
    <script src="/gestionfideicomisos/resources/js/Foundation/foundation.js"></script>
    <script src="/gestionfideicomisos/resources/js/Home/index.js"></script>
</body>
</html>