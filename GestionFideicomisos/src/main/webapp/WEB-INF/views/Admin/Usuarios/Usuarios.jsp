<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Petramare</title>
    <link rel="icon" type="image/png" href="/gestionfideicomisos/resources/img/icono.png" />
    <link rel="stylesheet" href="/gestionfideicomisos/resources/css/Foundation/foundation.min.css">
    <link rel="stylesheet" href="/gestionfideicomisos/resources/css/Foundation/foundation-icons/foundation-icons.css">
    <link rel="stylesheet" href="/gestionfideicomisos/resources/css/Admin/index.css">
</head>
<body>
    
    <jsp:include page="/WEB-INF/views/Layouts/menu.jsp"></jsp:include>
    <br>
    <div class="row">
        <div style="text-align: center;">
            <h4>Usuarios petramare &nbsp;&nbsp; <c:if test="${depa ne 'Cumplimiento'}"><a href="/gestionfideicomisos/adm/usuario/new"><button style="margin: 5px;" class="button small botonSofom">Nuevo</button></a></c:if></h4>
        </div>
        <hr>
        <table>
            <thead id="colorUsuarios">
                <tr>
                    <th>Editar</th>
                    <th>Nombre usuario</th>
                    <th>Departamento</th>
                    <th>Correo</th>
                    <th>Usuario</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${usuarios}" var="i">
                <tr>
                    <td class="centrar"><a href="/gestionfideicomisos/adm/usuario/${i.claveUsuario}/edit" style="margin: 5px;" class="botonSofom button">Ver</a></td>
                    <td>${i.nombreUsuario}</td>
                    <td>${i.departamento}</td>
                    <td>${i.correo}</td>
                    <td>${i.usuario}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>
