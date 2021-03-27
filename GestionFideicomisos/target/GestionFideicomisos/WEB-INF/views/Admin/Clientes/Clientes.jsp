<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <style>
        .pagination li a, .pagination li a:visited {
            color: #000;
        }
        .pagination li a:hover {
            color: #000;
        }
    </style>
</head>
<body>
    
    <jsp:include page="/WEB-INF/views/Layouts/menu.jsp"></jsp:include>
    <br>
    <div class="row">
        <div style="text-align: center;">
            <h4>Usuarios clientes &nbsp;&nbsp; <c:if test="${depa ne 'Cumplimiento'}"><a href="/gestionfideicomisos/adm/cliente/new"><button style="margin: 5px;" class="button small botonSofom">Nuevo</button></a></c:if></h4>
        </div>
        
        <div class="large-8 large-centered centered columns">
            <form method="get" action="/gestionfideicomisos/adm/clientes">
                <fieldset class="fieldset">
                <legend><h4>Búsqueda</h4></legend>
                    <div class="row">
                        <div class="large-4 columns">
                            <label>Clave contrato</label>
                            <input type="text" name="claveContrato" id="claveContrato" maxlength="13" value="${claveContrato}">
                        </div>
                        <div class="large-4 columns">
                            <label>Nombre usuario</label>
                            <input type="text" name="usuario" id="usuario" value="${usuario}">
                        </div>
                        <div class="large-4 columns">
                            <label>Correo</label>
                            <input type="text" name="email" id="email" value="${email}">
                        </div>
                    </div>
                    <div class="row" style="text-align: center;">
                        <input type="submit" value="Buscar" class="button botonSofom">
                    </div>
                </fieldset>
            </form>
        </div>
        <br>
        
        <table>
            <thead id="colorClientes">
                <tr>
                    <th>Editar</th>
                    <th>Clave contrato</th>
                    <th style="width: 130px;">Clave cliente</th>
                    <th>Nombre usuario</th>
                    <th>Correo</th>
                    <th>Usuario</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${clientes}" var="i">
                <tr>
                    <td class="centrar"><a href="/gestionfideicomisos/adm/cliente/${i.claveContrato}/${i.usuario}/edit" style="margin: 5px;" class="botonSofom button">Ver</a></td>
                    <td>${i.claveContrato}</td>
                    <td>${i.claveCliente}</td>
                    <td>${i.nombreUsuario}</td>
                    <td>${i.contactoUsuario}</td>
                    <td>${i.usuario}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        
        <br>
        <div id="countClientes" style="display: none;">${countClientes}</div>
            <ul class="pagination text-center" id="paginacion" role="navigation" aria-label="Pagination">
                <li class="pagination-previous disabled">Previous <span class="show-for-sr">page</span></li>
                <li class="current">1</li>
                <li><a href="#">2</a></li>
                <li><a href="#">3</a></li>
                <li><a href="#">4</a></li>
                <li><a href="#">5</a></li>
                <li class="ellipsis" aria-hidden="true"></li>
                <li class="pagination-next"><a href="#" aria-label="Next page">Next <span class="show-for-sr">page</span></a></li>
            </ul>
            <br>
    </div>
                        
    <script src="/gestionfideicomisos/resources/js/jquery.js"></script>
    <script src="/gestionfideicomisos/resources/js/Foundation/foundation.js"></script>
    <script src="/gestionfideicomisos/resources/js/Foundation/app.js"></script>
    <script src="/gestionfideicomisos/resources/js/Main.js"></script>
    <script>
        $(function(){
            var m = new Main();
            var paginas = parseInt($('#countClientes').text()/10);
            var actual = "";
            var pag = 0;
            var miHtml = "";
            var pagQuery = m.getQueryString('pagina');
            if (pagQuery === '') {
                pagQuery = 0;
            }
            var claveContrato = $('#claveContrato').val();
            var usuario = $('#usuario').val();
            var email = $('#email').val();

            /*if (parseInt(pagQuery) === 0) {
                miHtml += "<li class='pagination'><a class='disabled' href='/gestionfideicomisos/adm/clientes?pagina=0&claveContrato="+claveContrato+"&usuario="+usuario+"&email="+email+"' aria-label='First page'>Primera</a></li>";
                miHtml += "<li class='pagination-previous'><a class='disabled' href='/gestionfideicomisos/adm/clientes?pagina=0&claveContrato="+claveContrato+"&usuario="+usuario+"&email="+email+"'>Anterior <span class='show-for-sr'>page</span></a></li>";
            } else {
                miHtml += "<li class='pagination'><a href='/gestionfideicomisos/adm/clientes?pagina=0&claveContrato="+claveContrato+"&usuario="+usuario+"&email="+email+"' aria-label='First page'>Primera</a></li>";
                miHtml += "<li class='pagination-previous'><a href='/gestionfideicomisos/adm/clientes?pagina="+(parseInt(pagQuery)-1)+"&claveContrato="+claveContrato+"&usuario="+usuario+"&email="+email+"'>Anterior <span class='show-for-sr'>page</span></a></li>";
            }*/

            for (var i = 0; i <= paginas; i++) {
                pag = i+1;
                if (pagQuery !== '') {
                    if (parseInt(pagQuery) === i) {
                        actual = " class='current'";
                    }
                }
                miHtml += "<li "+actual+"><a href='/gestionfideicomisos/adm/clientes?pagina="+i+"&claveContrato="+claveContrato+"&usuario="+usuario+"&email="+email+"'>"+(pag)+"</a></li>";
                actual = "";
            }

            /*miHtml += "<li class='ellipsis' aria-hidden='true'></li>";
            if (parseInt(pagQuery) === paginas) {
                miHtml += "<li class='pagination-next'><a class='disabled' href='/gestionfideicomisos/adm/clientes?pagina="+pagQuery+"&claveContrato="+claveContrato+"&usuario="+usuario+"&email="+email+"' aria-label='Next page'>Siguiente <span class='show-for-sr'>page</span></a></li>";
                miHtml += "<li class='pagination'><a class='disabled' href='/gestionfideicomisos/adm/clientes?pagina="+paginas+"&claveContrato="+claveContrato+"&usuario="+usuario+"&email="+email+"' aria-label='Last page'>Última</a></li>";
            } else {
                miHtml += "<li class='pagination-next'><a href='/gestionfideicomisos/adm/clientes?pagina="+(parseInt(pagQuery)+1)+"&claveContrato="+claveContrato+"&usuario="+usuario+"&email="+email+"' aria-label='Next page'>Siguiente <span class='show-for-sr'>page</span></a></li>";
                miHtml += "<li class='pagination'><a href='/gestionfideicomisos/adm/clientes?pagina="+paginas+"&claveContrato="+claveContrato+"&usuario="+usuario+"&email="+email+"' aria-label='Last page'>Última</a></li>";
            }*/
            $('#paginacion').html(miHtml);
        });
    </script>
                        
</body>
</html>
