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
        <div style="width: 85%; color: #006699;" align="right">
            <a href="/Liquidaciones/EmpleadoLogin.htm"><button>Regresar</button></a>
        </div>
        <div style="width: 100%;" align="center">
            <div id="validado" ></div>
            <form action="ControllerEmpleado" method="post" style="width: 500px; background-color: #e3f2fe; margin-top: 30px;">
                <input type="hidden" name="accion" value="validaDatos:2">
                <fieldset>
                    <br>
                    <table>
                        <tr>
                            <th><label>Escribir RFC</label></th>
                            <td><input type="search" maxlength="13" pattern="[A-Z,Ñ,&]{3,4}[0-9]{2}[0-1][0-9][0-3][0-9][A-Z,0-9]{2}([A-Z,0-9]?)" title="Escribe un rfc valido con homo-clave" name="buequeda" id="rfc"></td>
                            <td><input type="button" value="Buscar" id="buscaRFC"></td>
                        </tr>
                    </table>
                    <table id="parte1" style="display: none; padding-top: 10px; padding-bottom: 10px;">
                        <tr>
                            <input type="hidden" name="idEmpleado" id="idEmp">
                            <th>Nombre</th>
                            <td id="nombre"></td>
                        </tr>
                        <tr>
                            <th>Apellido Paterno</th>
                            <td id="appat"></td>
                        </tr>
                        <tr>
                            <th>Apellido Materno</th>
                            <td id="apmat"></td>
                        </tr>
                    </table>
                    <table id="parte2" style="display: none; padding-bottom: 10px;">
                        <tr>
                            <th colspan="2">¿Su información es correcta?</th>
                        </tr>
                        <tr>
                            <th><input type="button" id="botonSi" value="Si">&nbsp;&nbsp;<input type="button" id="botonNo" value="No"></th>
                        </tr>
                    </table>
                    <table id="parte3" style="display: none; padding-bottom: 10px;">
                        <tr>
                            <th><label>CURP:</label></th>
                            <td><input type="text" name="curp" title="Ingresa una CURP valida" maxlength="18" pattern="^[A-Z][AEIOUX][A-Z]{2}[0-9]{2}[0-1][0-9][0-3][0-9][MH][A-Z][BCDEFGHJKLMNÑPQRSTVWXYZ]{4}[0-9A-Z][0-9]" required></td>
                        </tr>
                        <tr>
                            <th><label>Email:</label></th>
                            <td><input type="email" name="email" required></td>
                        </tr>
                        <tr style="padding-top: 10px;">
                            <td colspan="2" style="text-align: center;"><input type="submit" value="Crear"></td>
                        </tr>
                    </table>
                </fieldset>
            </form>
        </div>
            
        <script src="scripts/jquery.min.js"></script>
        <script>
            $(function(){
                
                var ocultaPartes = function() {
                    $('#parte1').fadeOut();
                    $('#parte2').fadeOut();
                    $('#parte3').fadeOut();
                    $('#idEmp').val('');
                };
                
                var getQueryString = function (name) {
                    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
                    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
                            results = regex.exec(location.search);
                    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
                };
                
                if (getQueryString('activa') === '1') {
                    $('#validado').css('padding-top', '15px');
                    $('#validado').text('Fideicomisario no encontrado, vuelve a intentarlo');
                    $('#validado').css('color', 'red');
                    $('#validado').css('font-size', '1.5em');
                }
                if (getQueryString('activa') === '2') {
                    $('#validado').css('padding-top', '15px');
                    $('#validado').html('Error de coincidencia,<br> vuelve a ingresar tus datos para validarlos');
                    $('#validado').css('color', 'red');
                    $('#validado').css('font-size', '1.5em');
                }
                if (getQueryString('activa') === '3') {
                    $('#validado').css('padding-top', '15px');
                    $('#validado').text('Error al activar cuenta');
                    $('#validado').css('color', 'red');
                    $('#validado').css('font-size', '1.5em');
                }
                
                $('#buscaRFC').click(function(){
                    //ocultaPartes();
                    $.post('/Liquidaciones/ControllerEmpleado', {rfc:$('#rfc').val(), accion:'buscaRFC:8'}, function(data){
                        if (data.trim() === 'CORRECTO') {
                            $.ajax({
                                url:'/Liquidaciones/ControllerEmpleado',
                                data:{accion:'buscaRFC:1', rfc:$('#rfc').val()},
                                type:'post'
                            }).done(function(data){
                                if (data.trim() === 'null') {
                                    $('#validado').css('padding-top', '15px');
                                    $('#validado').text('Fideicomisario no encontrado, vuelve a intentar ingresar los datos');
                                    $('#validado').css('color', 'red');
                                    $('#validado').css('font-size', '1.5em');
                                } else {
                                    var datos = JSON.parse(data);
                                    $('#parte1').fadeIn();
                                    $('#parte2').fadeIn();
                                    $('#idEmp').val(datos.id);
                                    $('#nombre').text(datos.nombre);
                                    $('#appat').text(datos.appat);
                                    $('#apmat').text(datos.apmat);
                                }
                            });
                        } else {
                            $('#validado').css('padding-top', '15px');
                            $('#validado').text('RFC no valido, vuelve a intentar ingresar los datos');
                            $('#validado').css('color', 'red');
                            $('#validado').css('font-size', '1.5em');
                        }
                    });
                });
                $('#botonNo').click(function(){
                    ocultaPartes();
                });
                $('#botonSi').click(function(){
                    $('#parte3').fadeIn();
                });
            });
        </script>
    </body>
</html>
