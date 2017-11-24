<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="es">
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
    
    <div class="large-8 large-centered centered columns">
        <form:form commandName="user" action="${action}" method="post">
        <fieldset class="fieldset">
                <legend><h4>Usuario garante</h4></legend>
                <div class="row">
                    <div class="large-4 columns">
                        <label>Nombre</label>
                        <form:input path="nombreUsuario" placeholder="Nombre completo" required="required"/>
                    </div>
                    <div class="large-4 columns">
                        <label>Departamento</label>
                        <form:select path="departamento" required="required">
                            <form:option value="" label="Selecciona"/>
                            <form:option value="GERENCIA DE TESORERIA" label="GERENCIA DE TESORERIA"/>
                            <form:option value="GERENCIA DE OPERACION" label="GERENCIA DE OPERACION"/>
                            <form:option value="GERENCIA" label="GERENCIA"/>
                            <form:option value="TESORERIA" label="TESORERIA"/>
                            <form:option value="OPERACION" label="OPERACION"/>
                            <form:option value="FACTURACION" label="FACTURACION"/>
                            <form:option value="CONSULTA" label="CONSULTA"/>
                            <form:option value="SISTEMAS" label="SISTEMAS"/>
                            <form:option value="Cumplimiento" label="CUMPLIMIENTO"/>
                        </form:select>
                    </div>
                    <div class="large-4 columns">
                        <label>Correo electrónico</label>
                        <form:input path="correo" type="email" placeholder="Correo electrónico" required="required"/>
                    </div>
                </div>
                <div class="row">
                    <div class="large-4 columns">
                        <label>Usuario</label>
                        <form:input path="usuario" placeholder="Nombre de usuario" required="required"/>
                    </div>
                    <div class="large-4 columns">
                        <label>Rol</label>
                        <form:select path="tipoCuenta" required="required">
                            <form:option value="" label="Selecciona"/>
                            <form:option value="ADM  " label="Administrador"/>
                            <form:option value="STD  " label="Estandar"/>
                        </form:select>
                    </div>
                    <div class="large-4 columns">
                    <c:if test="${action eq '/adm/usuario/save'}">
                        <form:hidden path="status" value="A"/>
                    </c:if>
                    <c:if test="${action ne '/adm/usuario/save'}">
                        <label>Estatus</label>
                        <form:select path="status" required="required">
                            <form:option value="" label="Selecciona"/>
                            <form:option value="A" label="Activo"/>
                            <form:option value="B" label="Bloqueado"/>
                        </form:select>
                    </c:if>
                    </div>
                </div>
                <form:hidden path="claveUsuario"/>
                <div class="row">
                    <div class="large-12 columns centrar" ><br>
                        <input type="submit" class="button botonSofom" value="Guardar">
                        <c:if test="${action eq '/adm/usuario/update'}">
                            <button class="button" id="nuevaContra" style="background-color: #032280;">Nueva contraseña</button>
                        </c:if>
                    </div>
                </div>
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
    <div class="reveal" data-options="closeOnBackgroundClick:false" id="modal2" data-reveal style="text-align: center;">
        <h1 id="modal2Titulo">Awesome!</h1>
        <p class="lead" id="modal2Contenido">I have another modal inside of me!</p>
    </div>

    <script src="/gestionfideicomisos/resources/js/jquery.js"></script>
    <script src="/gestionfideicomisos/resources/js/Foundation/foundation.js"></script>
    <script src="/gestionfideicomisos/resources/js/Foundation/app.js"></script>
    <script src="/gestionfideicomisos/resources/js/Main.js"></script>
    <script src="/gestionfideicomisos/resources/js/Admin/index.js"></script>
    <script>
        $(function(){
            var m = new Main();
            
            var generaNuevoPass = function(){
                $('#generaNuevoPass').click(function(){
                    m.creaModal('modal2', 'success', 'Realizando petición','');
                    $('html').css({'cursor':'wait'});
                    $('#nuevaContra').fadeOut();
                    $('.botonSofom').fadeOut();
                    $.ajax({
                        url:'/gestionfideicomisos/adm/genera/contra',
                        type: 'POST',
                        data: {tipo:'sofom', id:$('#claveUsuario').val(), username:''}
                    }).done(function(datos){
                        if (datos === 'error') {
                            window.location = "/gestionfideicomisos/adm/usuario/"+$('#claveUsuario').val()+"/edit?contra=error";
                        } else {
                            window.location = "/gestionfideicomisos/adm/usuario/"+$('#claveUsuario').val()+"/edit?contra=ok";
                        }
                    });
                });
            };
            
            $('#nuevaContra').click(function(e){
                e.preventDefault();
                m.creaModal('modal', 'warning', '¡Atención!', '');
                var miHtml = ' <h1 id="modalTitulo" style="color: rgb(255, 174, 0);">¡Atención!</h1>'
                +'<p class="lead" id="modalContenido">Estas a punto de generar una nueva contraseña ¿deseas continuar?</p>'
                +'<a class="button alert" id="generaNuevoPass">Continuar</a>&nbsp&nbsp;'
                +'<a class="button success" id="modalBoton" data-close="">Cancelar</a>'
                +'<button class="close-button" data-close="" aria-label="Close reveal" type="button">'
                  +'<span aria-hidden="true">×</span>'
                +'</button>';
                $('#modal').html(miHtml);
                generaNuevoPass();
            });
            
        <c:if test="${depa eq 'Cumplimiento'}">
            $('input').attr('disabled','disabled');
            $('select').attr('disabled','disabled');
            $('input[type=submit]').fadeOut('fast');
        </c:if>
            
        });
    </script>
</body>
</html>