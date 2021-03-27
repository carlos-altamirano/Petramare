<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <link rel="stylesheet" href="/gestionfideicomisos/resources/css/Admin/index.css">
</head>
<body>
    <jsp:include page="/WEB-INF/views/Layouts/menu.jsp"></jsp:include>
    
    <div class="large-8 large-centered centered columns">
        <form:form commandName="cuentaBanco" action="${action}" method="post">
        <fieldset class="fieldset">
                <legend><h4>Cuentas bancarias</h4></legend>
                <div class="row">
                    <div class="large-4 columns">
                        <label>Cuenta origen</label>
                        <form:input path="cuentaOrigen" placeholder="Cuenta origen" required="required"/>
                    </div>
                    <div class="large-4 columns">
                        <label>Número de cuenta</label>
                        <form:input path="numCuenta" placeholder="Número de cuenta" required="required"/>
                    </div>
                    <div class="large-4 columns">
                        <label>CLABE</label>
                        <form:input path="claveCuenta" placeholder="Clave cuenta" required="required"/>
                    </div>
                </div>
                <div class="row">
                    <div class="large-4 columns">
                        <c:if test="${action eq '/gestionfideicomisos/adm/cuenta/save'}">
                            <form:hidden path="status" value="A"/>
                        </c:if>
                        <c:if test="${action ne '/gestionfideicomisos/adm/cuenta/save'}">
                        <label>Estatus</label>
                        <form:select path="status" required="required">
                            <form:option value="" label="Seleccione"/>
                            <form:option value="A" label="Activa"/>
                            <form:option value="B" label="Bloqueada"/>
                        </form:select>
                        </c:if>
                    </div>
                </div>
                <c:if test="${editable}">
                <div class="row centrar">
                    <input type="submit" class="button botonSofom" value="Guardar">
                </div>
                </c:if>
        </fieldset>
        </form:form>
    </div>
    
    <!-- Modal -->
    <div class="reveal" id="modal" data-reveal style="text-align: center;">
        <h1 id="modalTitulo">Awesome!</h1>
        <p class="lead" id="modalContenido">I have another modal inside of me!</p>
        <a class="button " id="modalBoton" data-close>Aceptar</a>
        <button class="close-button" data-close aria-label="Close reveal" type="button">
          <span aria-hidden="true">&times;</span>
        </button>
    </div>
    
    <script src="/gestionfideicomisos/resources/js/jquery.js"></script>
    <script src="/gestionfideicomisos/resources/js/Foundation/foundation.js"></script>
    <script src="/gestionfideicomisos/resources/js/Foundation/app.js"></script>
    <script src="/gestionfideicomisos/resources/js/Main.js"></script>
    <script src="/gestionfideicomisos/resources/js/Admin/index.js"></script>
    <script>
        $(function(){
            <c:if test="${depa eq 'Cumplimiento'}">
                $('input').attr('disabled','disabled');
                $('select').attr('disabled','disabled');
                $('input[type=submit]').fadeOut('fast');
            </c:if>
        });
    </script>
    
</body>
</html>