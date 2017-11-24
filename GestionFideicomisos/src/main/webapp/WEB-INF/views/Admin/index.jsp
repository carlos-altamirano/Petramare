<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <link rel="stylesheet" href="/gestionfideicomisos/resources/css/Admin/index.css">
</head>
<body>
    <jsp:include page="/WEB-INF/views/Layouts/menu.jsp"></jsp:include>
    
    <br><br><br><br>
    
    <div class="large-8 large-centered centered columns">
        <div class="row">
            <div class="large-6 columns">
                <a href="/gestionfideicomisos/adm/usuarios" class="button large expanded" style="background-color: #032280;">
                    <i class="step fi-address-book size-72 iconoTam"></i><br>
                    Usuarios garante
                </a>
            </div>
            <div class="large-6 columns">
                <a href="/gestionfideicomisos/adm/contratos" class="button large expanded" style="background-color: #032280;">
                    <i class="step fi-page-copy size-72 iconoTam"></i><br>
                    Contratos
                </a>
            </div>
        </div>
        <div class="row">
            <div class="large-6 columns">
                <a href="/gestionfideicomisos/adm/clientes" class="button large expanded" style="background-color: #032280;">
                    <i class="step fi-results-demographics size-72 iconoTam"></i><br>
                    Usuarios clientes
                </a>
            </div>
            <div class="large-6 columns">
                <a href="/gestionfideicomisos/adm/cuentas" class="button large expanded" style="background-color: #032280;">
                    <i class="step fi-dollar-bill size-72 iconoTam"></i><br>
                    Cuentas Bancarias
                </a>
            </div>
        </div>
    </div>
    
    <br>
    
</body>
</html>