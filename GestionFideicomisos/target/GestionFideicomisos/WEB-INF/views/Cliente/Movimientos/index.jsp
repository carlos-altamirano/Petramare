<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Petramare</title>
    <link rel="icon" type="image/png" href="/gestionfideicomisos/resources/img/icono.png" />
    <link rel="stylesheet" href="/gestionfideicomisos/resources/css/Foundation/foundation.min.css">
    <link rel="stylesheet" href="/gestionfideicomisos/resources/css/Foundation/foundation-icons/foundation-icons.css">
    <link rel="stylesheet" href="/gestionfideicomisos/resources/css/Cliente/index.css">
</head>
<body>
    <jsp:include page="/WEB-INF/views/Layouts/menu.jsp"></jsp:include>
                    
    <div class="large-6 large-centered centered columns"><br/><br/>
        <form action="/cte/guarda/liquidacion/archivo" method="post" enctype="multipart/form-data">
            <div class="columns">
                <div class="row">
                    <div class="columns centrar">
                        <h3>SISTEMA DE LIQUIDACIONES</h3>
                    </div>
                    <hr/>
                </div>
                <div class="row centrar">
                    <div class="columns">
                        <button class="file-upload secondary button">
                            <input type="file" class="file-input" name="archivo" accept=".txt" id="archivo" required>Escoge el archivo
                        </button>
                    </div>
                    <div class="columns">
                        <div class="columns">
                            <span class="success label" id="nombreArchivo"></span>
                        </div>
                    </div>
                </div>
                <div class="row centrar">
                    <div class="columns"><br/>
                        <input class="button" type="submit" value="Subir">
                        <a href="/cte/liquidacion/manual" class="button">Manual</a>
                    </div>
                </div>
            </div>
        </form>
    </div>

    <script src="/gestionfideicomisos/resources/js/jquery.js"></script>
    <script src="/gestionfideicomisos/resources/js/Foundation/foundation.js"></script>
    <script src="/gestionfideicomisos/resources/js/Cliente/index.js"></script>
</body>
</html>