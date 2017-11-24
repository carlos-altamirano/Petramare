<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cambio de contraseña</title>
    <link rel="icon" type="image/png" href="/gestionfideicomisos/resources/img/icono.png" />
    <link rel="stylesheet" href="/gestionfideicomisos/resources/css/Foundation/foundation.min.css">
    <link rel="stylesheet" href="/gestionfideicomisos/resources/css/Foundation/foundation-icons/foundation-icons.css">
    <link rel="stylesheet" href="/gestionfideicomisos/resources/css/Home/error.css">
</head>
<body>
    <jsp:include page="/WEB-INF/views/Layouts/menu.jsp"></jsp:include>
    
    <div class="large-8 large-centered centered columns">
        <form action="/gestionfideicomisos/perfil/password/edit" method="post">
            <fieldset class="fieldset" style="text-align: center;">
                <legend><h4>Cambia tu contraseña</h4></legend>
                <div class="row">
                    <div class="large-4 columns">
                        <label>Contraseña actual</label>
                        <input type="password" name="vieja" placeholder="Contraseña actual">
                    </div>
                    <div class="large-4 columns">
                        <label>Nueva contraseña</label>
                        <input type="password" name="nueva1" id="nueva1" placeholder="Nueva contraseña">
                    </div>
                    <div class="large-4 columns">
                        <label>Repetir contraseña nueva</label>
                        <input type="password" name="nueva2" id="nueva2" placeholder="Repetir contraseña nueva">
                    </div>
                </div>
                <div class="row">
                    <div class="large-12 columns centrar" id="botoEnviar" style="display: none;"><br>
                        <input type="submit" class="button" value="Guardar">
                    </div>
                </div>
            </fieldset>
        </form>
    </div>
    <br>
    
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
    <script>
        $(function(){
            var m = new Main();
            
            if (m.getQueryString('cambio') === 'ok') {
                m.creaModal('modal', 'success', '¡Atención!', 'Se cambio la contraseña correctamente');
            }
            if (m.getQueryString('cambio') === 'error') {
                m.creaModal('modal', 'success', '¡Atención!', 'Error al cambiar contraseña');
            }
            
            $('#nueva2').on('keyup', function(){
                if ($('#nueva2').val() === $('#nueva1').val()) {
                    $('#nueva1').css('border-color','#00ff8b');
                    $('#nueva2').css('border-color','#00ff8b');
                    $('#botoEnviar').fadeIn('slow');
                } else {
                    $('#nueva1').css('border-color','#f00');
                    $('#nueva2').css('border-color','#f00');
                    $('#botoEnviar').fadeOut('slow');
                }
            });
            
        });
    </script>
</body>
</html>
