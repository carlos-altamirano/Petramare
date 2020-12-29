<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
    <c:when test="${sessionScope.sesionUser != null}">
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Archivos</title>
        <link rel="shortcut icon" href="images/icono.png">
        <style>
           * {
                font-family: sans-serif;
            } 
        </style>
    </head>
    <body>
        <div style="width: 85%; color: #006699;" align="right">
            Bienvenido: <%=session.getAttribute("sesionName")%>
            <a href="/Liquidaciones/CambiaContra.htm"><button>Cambiar contraseña</button></a>
            <a href="/Liquidaciones/ControllerEmpleado?accion=logout:4"><button>Cerrar sesión</button></a>
        </div>
        <div style="width: 100%;" align="center">
            <h2>Comprobante Fiscal Digital por Internet</h2>
            <br>
            <table style=" background-color: #e3f2fe;color: #006699; padding: 5px;">
                <thead>
                    <tr>
                        <th style="width: 150px;">Año</th>
                        <th style="width: 150px;">Periodo</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td style="text-align: center;">
                            <select id="anio" style="width: 50%;">
                                <option value="">Seleccione</option>
                            </select>
                        </td>
                        <td style="text-align: center;">
                            <select class="selArchivo">
                                <option value="">Seleccione</option>
                            </select>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </body>
</html>
<script src="scripts/jquery.min.js"></script>
<script>
    $(function(){
        $.post('ControllerEmpleado', {accion:'buscarArchivos:10'}, function(data){
            var datos = JSON.parse(data);
            var opciones = "<option value=''>Seleccione</option>";
            var arreglo2 = [];
            for (var i = 0; i < datos.length; i++) {
                opciones += "<option value='"+datos[i]+"'>"+datos[i]+"</option>";
            }
            $('#anio').html(opciones);
        });

        var cambiaPeriodos = function() {
            $.post('ControllerEmpleado', {accion:'buscarArchivos:7', anio:$('#anio').val()}, function(data){
                var meses = ["Enero.zip", "Febrero.zip", "Marzo.zip", "Abril.zip", "Mayo.zip", 
                    "Junio.zip", "Julio.zip", "Agosto.zip", "Septiembre.zip", "Octubre.zip", "Nobiembre.zip", "Diciembre.zip"];
                var datos = JSON.parse(data);
                var opciones = "<option value=''>Seleccione</option>";
                var arreglo2 = [];
                for (var i = 0; i < datos.length; i++) {
                    arreglo2[i] = datos[i];
                }
                for (var j = 0; j < meses.length; j++) {
                    for (var x = 0; x < arreglo2.length; x++) {
                        if (meses[j] === arreglo2[x]) {
                            if (meses[j] === 'Nobiembre.zip') {
                                opciones += "<option value='"+meses[j]+"'>Noviembre.zip</option>";
                            } else {
                                opciones += "<option value='"+meses[j]+"'>"+meses[j]+"</option>";
                            }
                        }
                    }
                }
                $('.selArchivo').html(opciones);
            });
        };

        $('#anio').change(function(){
            cambiaPeriodos();
        });
        
        $('.selArchivo').change(function(){
            var archivo = $(this).val();
            if (archivo !== '') {
                window.open('ControllerEmpleado?accion=descarga:6&archivo='+archivo+"&anio="+$('#anio').val());
            }
        });
    });
</script>
</c:when>
    <c:otherwise>
        <c:redirect url="EmpleadoLogin.htm?login=no" />
    </c:otherwise>
</c:choose>
