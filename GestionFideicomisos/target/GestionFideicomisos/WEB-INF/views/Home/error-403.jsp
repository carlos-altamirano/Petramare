<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sin permisos</title>
    <link rel="icon" type="image/png" href="/gestionfideicomisos/resources/img/icono.png" />
    <link rel="stylesheet" href="/gestionfideicomisos/resources/css/Foundation/foundation.min.css">
    <link rel="stylesheet" href="/gestionfideicomisos/resources/css/Foundation/foundation-icons/foundation-icons.css">
    <link rel="stylesheet" href="/gestionfideicomisos/resources/css/Home/error.css">
</head>
<body>
    <div class="row expanded">
        <div class="top-bar">
            <div class="top-bar-title">
                <strong><a href="/gestionfideicomisos" style="color:white;">Gestion Fideicomisos</a></strong>
                <!--img src="/resources/img/fideifuturo.jpg" alt="Fideifuturo"-->
            </div>

            <div id="responsive-menu">
                <div class="top-bar-left">
                    <ul class="dropdown menu" data-dropdown-menu></ul>
                </div>
            </div>

            <div class="top-bar-right">
                <ul class="menu">
                    <li><a href="/gestionfideicomisos/logout">Salir</a></li>
                </ul>
            </div>
        </div>
    </div>

    <div class="large-8 large-centered centered columns">
        <fieldset class="fieldset" style="text-align: center;">
            <legend><h4>No tienes permisos para ver esta pagina comunicate con un administrador</h4></legend>
            <a href="/gestionfideicomisos"><button class="button">Regresar</button></a>
        </fieldset>
    </div>
    <br>
</body>
</html>