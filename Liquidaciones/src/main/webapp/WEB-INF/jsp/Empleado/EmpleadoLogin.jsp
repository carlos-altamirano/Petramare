<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title> Liquidaciones </title>
        <link rel="shortcut icon" href="images/icono.png">
        <style>
           * {
                font-family: sans-serif;
            } 
        </style>
    </head>
    <body>
        <div style="width: 100%;" align="center">
            <div id="validado" ></div>
            <form action="ControllerEmpleado" method="post" style="width: 500px; background-color: #e3f2fe; margin-top: 30px; padding: 7px; padding-bottom: 10px;">
                <input type="hidden" name="accion" value="iniciaSesion:3">
                <fieldset>
                    <legend style="color: #006699; font-size: 1.5em;">Kiosco recuperación CFDI</legend>
                    <table>
                        <tr><br>
                            <th><label>RFC:</label></th>
                            <td><input type="text" name="user" maxlength="13" pattern="[A-Z,Ñ,&]{3,4}[0-9]{2}[0-1][0-9][0-3][0-9][A-Z,0-9]{2}([A-Z,0-9]?)" title="Escribe un rfc valido con homo-clave" required></td>
                        </tr>
                        <tr>
                            <th><label>Contraseña:</label></th>
                            <td><input type="password" name="password" required></td>
                        </tr>
                        <tr>
                            <td colspan="2" style="text-align: center;"><br>
                                <input type="submit" value="Acceder">
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2" style="text-align: center; color: #5e5e5e"><br>¿No tienes cuenta?, crea una nueva</td>
                        </tr>
                        <tr>
                            <td colspan="2" style="text-align: center;"><br>
                                <input type="button" id="nuevaCuenta" value="Crear nueva">
                            </td>
                        </tr>
                    </table>
                </fieldset>
            </form>
        </div>
        <script src="scripts/jquery.min.js"></script>
        <script>
            $(function(){
                var getQueryString = function (name) {
                    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
                    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
                            results = regex.exec(location.search);
                    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
                };
                
                if (getQueryString('login') === 'ok') {
                    $('#validado').css('padding-top', '15px');
                    $('#validado').text('Sesión cerrada correctamente');
                    $('#validado').css('color', 'green');
                    $('#validado').css('font-size', '1.5em');
                }
                if (getQueryString('login') === 'no') {
                    $('#validado').css('padding-top', '15px');
                    $('#validado').text('Debes iniciar sesión para poder continuar');
                    $('#validado').css('color', 'red');
                    $('#validado').css('font-size', '1.5em');
                }
                if (getQueryString('login') === 'error') {
                    $('#validado').css('padding-top', '15px');
                    $('#validado').text('Error en el usuario y/o contraseña');
                    $('#validado').css('color', 'red');
                    $('#validado').css('font-size', '1.5em');
                }
                if (getQueryString('activa') === '4') {
                    $('#validado').css('padding-top', '15px');
                    $('#validado').html('Cuenta creada correctamente,<br> revisa tu email para obtener tus credenciales e ingresarlas aqui');
                    $('#validado').css('color', 'green');
                    $('#validado').css('font-size', '1.5em');
                }
            });
            $('#nuevaCuenta').click(function(e){
                e.preventDefault();
                window.location.assign('/Liquidaciones/NuevaCuentaEmpleado.htm');
            });
        </script>
    </body>
</html>
