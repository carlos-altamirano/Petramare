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
                <legend><h4>Usuario cliente</h4></legend>
                <div class="row">
                    <div class="large-4 columns">
                        <label>Clave contrato</label>
                        <form:select path="claveContrato" required="required">
                            <form:option value="" label="Selecciona"/>
                            <c:forEach items="${contratos}" var="i">
                                <option value="${i.claveContrato}" <c:if test="${user.claveContrato eq i.claveContrato}">selected</c:if>>${i.claveContrato}</option>
                            </c:forEach>
                        </form:select>
                    </div>
                    <div class="large-4 columns">
                        <label>Clave cliente</label>
                        <form:input path="claveCliente" placeholder="Clave cliente" disabled="true" required="required"/>
                    </div>
                    <div class="large-4 columns">
                        <label>Nombre del cliente</label>
                        <span id="nombreCliente"></span>
                    </div>
                </div>
                <div class="row">
                    <div class="large-4 columns">
                        <label>Nombre de usuario</label>
                        <form:input path="nombreUsuario" placeholder="Nombre de usuario" required="required"/>
                    </div>
                    <div class="large-4 columns">
                        <label>Puesto</label>
                        <form:input path="puesto" placeholder="puesto" required="required" />
                    </div>
                    <div class="large-4 columns">
                        <label>Teléfono de contácto</label>
                        <form:input path="telefono" pattern="[0-9]+" tittle="solo números" placeholder="Teléfono de contácto" required="required"/>
                    </div>
                </div>
                <div class="row">
                    <div class="large-4 columns">
                        <label>Correo electrónico</label>
                        <form:input path="contactoUsuario" type="email" placeholder="Correo electrónico" required="required"/>
                    </div>
                    <div class="large-4 columns">
                        <label>Usuario</label>
                        <form:input path="usuario" placeholder="Usuario" required="required"/>
                    </div>
                    <div class="large-4 columns">
                    <c:if test="${action ne '/gestionfideicomisos/adm/cliente/save'}">
                        <label>Estatus</label>
                        <form:select path="status" required="reuquired">
                            <form:option value="" label="Seleccione"/>
                            <form:option value="A" label="Activo"/>
                            <form:option value="B" label="Bloqueado"/>
                        </form:select>
                    </c:if>
                    <c:if test="${action eq '/gestionfideicomisos/adm/cliente/save'}">
                        <form:hidden path="status" value="A"/>
                    </c:if>
                    </div>
                    <div class="large-4 columns">
                    </div>
                </div>
                <c:if test="${action ne '/gestionfideicomisos/adm/cliente/save'}">
                <div class="row">
                    <div class="large-4 columns">
                        <label>Fecha de creación</label>
                        <input type="datetime-local" value="${fechaCapturaMostrar}" disabled="true"> 
                    </div>
                </div>
                </c:if>
                <form:hidden path="fechaAlta" value="${fechaCaptura}"/>
                <form:hidden path="fechaBloqueo" value="${fechaBloqueo}"/>
                <div class="row">
                    <div class="large-12 columns centrar" ><br>
                        <input type="submit" class="button botonSofom" value="Guardar">
                        <button class="button" id="nuevaContra" style="background-color: #032280; display: none;">Nueva contraseña</button>
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
    <div class="reveal" id="modal2" data-reveal style="text-align: center;">
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
            var action = $('#user').attr('action');
            var spt = action.split('/');
            if (spt.length > 5){
                $('#nuevaContra').fadeIn();
            }
        
            if ($('#claveContrato').val() !== '') {
                $.post('/gestionfideicomisos/adm/contrato/'+$('#claveContrato').val()+'/search', {}, function(data){
                    $('#nombreCliente').text(data);
                });
            }
            
            $('#claveContrato').change(function(){
                var claveContrato = $(this).val();
                $('#claveCliente').val(claveContrato.slice(0,9));
                $.post('/gestionfideicomisos/adm/contrato/'+claveContrato+'/search', {}, function(data){
                    $('#nombreCliente').text(data);
                });
            });
            
            $('#user').submit(function(){
                $('input').removeAttr('disabled');
                $('select').removeAttr('disabled');
            });
            
            var generaNuevoPass = function(){
                $('#generaNuevoPass').click(function(){
                    m.creaModal('modal2', 'success', 'Realizando petición','');
                    $('html').css({'cursor':'wait'});
                    $('#nuevaContra').fadeOut();
                    $('.botonSofom').fadeOut();
                    $.ajax({
                        url:'/gestionfideicomisos/adm/genera/contra',
                        type: 'POST',
                        data: {tipo:'cliente', id:$('#claveContrato').val(), username:$('#usuario').val()}
                    }).done(function(datos){
                        if (datos === 'error') {
                            window.location = "/gestionfideicomisos/adm/cliente/"+$('#claveContrato').val()+"/"+$('#usuario').val()+"/edit?contra=error";
                        } else {
                            window.location = "/gestionfideicomisos/adm/cliente/"+$('#claveContrato').val()+"/"+$('#usuario').val()+"/edit?contra=ok";
                        }
                    });
                });
            };
            
            $('#nuevaContra').click(function(e){
                e.preventDefault();
                m.creaModal('modal', 'warning', '¡Atención!', '');
                var miHtml = ' <h1 id="modalTitulo" style="color: rgb(255, 174, 0);">¡Atención!</h1>'
                +'<p class="lead" id="modalContenido">Estas a punto de generar una nueva contraseña ¿deseas continuar?</p>'
                +'<div id="areaBotones">'
                +'<a class="button alert" id="generaNuevoPass">Continuar</a>&nbsp&nbsp;'
                +'<a class="button success" id="modalBoton" data-close="">Cancelar</a>'
                +'</div>'
                +'<button class="close-button" data-close="" aria-label="Close reveal" type="button">'
                  +'<span aria-hidden="true">×</span>'
                +'</button>';
                $('#modal').html(miHtml);
                generaNuevoPass();
            });
            
            <c:if test="${action ne '/gestionfideicomisos/adm/cliente/save'}">
                $('select').attr('disabled', 'disabled');
                $('input[type=text]').attr('disabled', 'disabled');
                $('input[type=email]').attr('disabled', 'disabled');
                $('input[type=datetime-local]').attr('disabled', 'disabled');
                $('#status').removeAttr('disabled');
            </c:if>
                
            <c:if test="${depa eq 'Cumplimiento'}">
                $('input').attr('disabled','disabled');
                $('select').attr('disabled','disabled');
                $('#nuevaContra').fadeOut('fast');
                $('input[type=submit]').fadeOut('fast');
            </c:if>
            
        });
    </script>
    
</body>
</html>