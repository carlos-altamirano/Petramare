<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
<c:when test="${sessionScope.sesionUser != null}">
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cambiar contraseña</title>
        <link rel="shortcut icon" href="images/icono.png">
        <style>
            * {
                font-family: sans-serif;
            }
        </style>
    </head>
    <body>
        <div style="width: 85%; color: #006699;" align="right">
            <a href="/Liquidaciones/ArchivosEmpleado.htm"><button>Regresar</button></a>
        </div>
        <div id="validado" style="text-align: center;"></div>
        <div style="width: 100%;" align="center">
            <form action="ControllerEmpleado" style="width: 35%; background-color: #e3f2fe; margin-top: 30px;" method="post">
                <fieldset>
                    <legend><h3>Cambiar contraseña</h3></legend>
                    <input type="hidden" name="accion" value="cambiar:5">
                    <label>Contraseña actual:</label> <input style="margin-left: 10px;" type="password" name="actual" placeholder="Contraseña actual" required><br><br>
                    <label>Nueva contraseña:</label> <input style="margin-left: 10px;" type="password" name="nueva" placeholder="Nueva contraseña" required><br><br>
                    <label style="margin-left: -45px;">Repetir nueva contraseña:</label> <input style="margin-left: 10px;" type="password" name="nueva2" placeholder="Repetir nueva contraseña" required><br><br>
                    <input type="submit" value="Cambiar">
                </fieldset>
            </form>
        </div>
    </body>
    <script src="scripts/jquery.min.js"></script>
    <script>
        $(function(){
            var getQueryString = function (name) {
                name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
                var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
                        results = regex.exec(location.search);
                return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
            };

            if (getQueryString('contraActual') === 'error') {
                $('#validado').css('padding-top', '15px');
                $('#validado').text('Error al escribir tu contraseña actual');
                $('#validado').css('color', 'red');
                $('#validado').css('font-size', '1.5em');
            }
            if (getQueryString('newPass') === 'error') {
                $('#validado').css('padding-top', '15px');
                $('#validado').text('La nueva contraseña no es la misma');
                $('#validado').css('color', 'red');
                $('#validado').css('font-size', '1.5em');
            }
            if (getQueryString('cambio') === 'error') {
                $('#validado').css('padding-top', '15px');
                $('#validado').text('Error al cambiar contraseña');
                $('#validado').css('color', 'red');
                $('#validado').css('font-size', '1.5em');
            }
            if (getQueryString('cambio') === 'ok') {
                $('#validado').css('padding-top', '15px');
                $('#validado').text('La contraseña fue cambiada exitosamente y se envio un email con tus nuevas credenciales ');
                $('#validado').css('color', 'green');
                $('#validado').css('font-size', '1.5em');
            }
        });
    </script>
</html>
</c:when>
    <c:otherwise>
        <c:redirect url="EmpleadoLogin.htm?login=no" />
    </c:otherwise>
</c:choose>