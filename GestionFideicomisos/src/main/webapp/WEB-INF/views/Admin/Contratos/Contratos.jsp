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
            <h4>Contratos &nbsp;&nbsp; <c:if test="${depa ne 'Cumplimiento'}"><a href="/gestionfideicomisos/adm/contrato/new"><button style="margin: 5px;" class="button small botonSofom">Nuevo</button></a></c:if></h4>
        </div>
        <hr>
        <table>
            <thead id="colorContratos">
                <tr>
                    <th>Editar</th>
                    <th>Clave Contrato</th>
                    <th style="width: 40%;">Nombre Cliente</th>
                    <th>Grupo</th>
                    <th>RFC</th>
                    <th>Tipo Honorario</th>
                    <th>Estatus</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${contratos}" var="i">
                <tr>
                    <td class="centrar"><a href="/gestionfideicomisos/adm/contrato/${i.claveContrato}/edit" style="margin: 5px;" class="botonSofom button">Ver</a></td>
                    <td>${i.claveContrato}</td>
                    <td>${i.nombreCliente}</td>
                    <td>${i.grupo}</td>
                    <td>${i.rfc}</td>
                    <td>${i.tipoHonorario}</td>
                    <td>${i.status}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    
        <br>
        <div id="countContratos" style="display: none;">${countContratos}</div>
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
    <script src="/gestionfideicomisos/resources/js/Main.js"></script>
    <script>
        $(function(){
            var m = new Main();
            var paginas = parseInt($('#countContratos').text()/10);
            var actual = "";
            var pag = 0;
            var miHtml = "";
            var pagQuery = m.getQueryString('pagina');
            if (pagQuery === '') {
                pagQuery = 0;
            }

            /*if (parseInt(pagQuery) === 0) {
                miHtml += "<li class='pagination'><a class='disabled' href='/gestionfideicomisos/adm/contratos?pagina=0' aria-label='First page'>Primera</a></li>";
                miHtml += "<li class='pagination-previous'><a class='disabled' href='/gestionfideicomisos/adm/contratos?pagina=0'>Anterior <span class='show-for-sr'>page</span></a></li>";
            } else {
                miHtml += "<li class='pagination'><a href='/gestionfideicomisos/adm/contratos?pagina=0' aria-label='First page'>Primera</a></li>";
                miHtml += "<li class='pagination-previous'><a href='/gestionfideicomisos/adm/contratos?pagina="+(parseInt(pagQuery)-1)+"&claveContrato="+claveContrato+"&usuario="+usuario+"&email="+email+"'>Anterior <span class='show-for-sr'>page</span></a></li>";
            }*/

            for (var i = 0; i <= paginas; i++) {
                pag = i+1;
                if (pagQuery !== '') {
                    if (parseInt(pagQuery) === i) {
                        actual = " class='current'";
                    }
                }
                miHtml += "<li "+actual+"><a href='/gestionfideicomisos/adm/contratos?pagina="+i+"'>"+(pag)+"</a></li>";
                actual = "";
            }

            /*miHtml += "<li class='ellipsis' aria-hidden='true'></li>";
            if (parseInt(pagQuery) === paginas) {
                miHtml += "<li class='pagination-next'><a class='disabled' href='/gestionfideicomisos/adm/contratos?pagina="+pagQuery+"' aria-label='Next page'>Siguiente <span class='show-for-sr'>page</span></a></li>";
                miHtml += "<li class='pagination'><a class='disabled' href='/gestionfideicomisos/adm/contratos?pagina="+paginas+"&claveContrato="+claveContrato+"&usuario="+usuario+"&email="+email+"' aria-label='Last page'>Última</a></li>";
            } else {
                miHtml += "<li class='pagination-next'><a href='/gestionfideicomisos/adm/contratos?pagina="+(parseInt(pagQuery)+1)+"' aria-label='Next page'>Siguiente <span class='show-for-sr'>page</span></a></li>";
                miHtml += "<li class='pagination'><a href='/gestionfideicomisos/adm/contratos?pagina="+paginas+"' aria-label='Last page'>Última</a></li>";
            }*/
            $('#paginacion').html(miHtml);
        });
    </script>
    
</body>
</html>
