<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
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
    <br>
    <div class="row">
        <div style="text-align: center;">
            <h4>Cuentas &nbsp;&nbsp; <c:if test="${depa ne 'Cumplimiento'}"><a href="/gestionfideicomisos/adm/cuenta/new"><button style="margin: 5px;" class="button small botonSofom">Nueva</button></a></c:if></h4>
        </div>
        <hr>
        <table>
            <thead id="colorCuentas">
                <tr>
                    <th>Cuenta Origen</th>
                    <th>Número Cuenta</th>
                    <th>CLABE</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${cuentas}" var="i">
                <tr>
                    <td>${i.cuentaOrigen}</td>
                    <td>${i.numCuenta}</td>
                    <td>${i.claveCuenta}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>
