<%-- 
    Document   : subirArchivo
    Created on : 25/08/2014, 12:23:42 PM
    Author     : Luis Antio Valerio Gayosso
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Subir Estado de Cuenta</title>
        <link rel="stylesheet" type="text/css" href="css/formato.css">  
        <link rel="shortcut icon" href="images/icono.png">
    </head>
    <body 

        <c:if test="${sessionScope.messageBean != null  }">
            onLoad="alert('<c:out value="${sessionScope.messageBean.desc}"/>');"
            <c:remove var="messageBean" scope="session" />
        </c:if>
        >


        <form name="subirConjuntoArchivos" action="SubirArchivo" method="POST" enctype="multipart/form-data">
            <input type="hidden" name="accion" value="subirConjuntoArchivos"/>
            <table align="center" width="90%">
                <tbody>                        
                    <!-- Formulario para subir conjunto de estados de cuenta en un archivo comprimido--->
                    <tr><td colspan="6">&nbsp;</td></tr>
                    <tr align="center" ><td  class="Titulo" style="background-color: #d6e8f9;" colspan="6"> Subir conjunto de estados de cuenta </td></tr>
                    <tr><td colspan="6">&nbsp;</td></tr>
                    <tr>
                        <td colspan="3" align="center">
                            <input type="file" style="" name="conjuntoArchivos"/>
                        </td>
                    </tr>
                    <tr><td colspan="3">&nbsp;</td></tr>
                    <tr align="center">
                        <td colspan="3">
                            <table align="center">
                                <tr>
                                    <td>
                                        <!--<input type="button" name="subirConjuntoArchivos" value="Subir" onclick="return cargaValor(subirConjuntoArchivos,'subirConjuntoArchivos');"/>-->
                                        <input type="submit" value="Subir Conjunto Archivos" name="subirArchivo" />
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>  
                </tbody>                    
            </table>                    
        </form>
        
        <c:if test="${sessionScope.ordenamiento != null  }">            
        <form name="formLiquidation" method="post" action="ControllerLiquidation">
            <input type="hidden" name="accion" value="ordenarEdoCta:46"/>
            <table align="center" width="90%">
                <tbody>                        
                    <!-- Formulario para subir conjunto de estados de cuenta en un archivo comprimido--->
                    <tr><td colspan="6">&nbsp;</td></tr>
                    <tr align="center" ><td  class="Titulo" style="background-color: #d6e8f9;color: #db4e4e; " colspan="6"> ${sessionScope.ordenamiento} </td></tr>
                    <tr><td colspan="6">&nbsp;</td></tr>
                    <c:if test="${sessionScope.sucesos != null  }"> 
                        <tr align="center" ><td  class="Titulo" style="background-color: #d6e8f9;color: #db4e4e; " colspan="6"> Lista de archivos con errores: </td></tr>
                        <tr align="center">
                            <td align="center" colspan="6">
                                <table style="background-color: #c8f7c8;" class="tabla_saldos">
                               <thead>
                                   <tr>
                                       <td>No.</td>
                                       <td>Tipo</td>
                                       <td>Archivo</td>
                                       <td>Descripcion</td>
                                   </tr>
                               </thead>
                               <c:forEach items='${sucesos}' varStatus='cont' var='messageError'>
                                   <tr>
                                       <td>${cont.count}</td>                                       
                                       <td>${messageError.type}</td>
                                       <td>${messageError.field}</td>
                                       <td>${messageError.desc}</td>
                                   </tr>
                               </c:forEach>
                            </table>        
                        </td>
                        </tr>
                    </c:if>
                    <tr><td colspan="6">&nbsp;</td></tr>
                    <tr><td colspan="2">&nbsp;</td>
                        <td align="center"><input type="submit" value="Procesar Archivo" name="procesar"/></td>
                        <td align="center"><input type="button" value="Cancelar" onclick="window.close()"/></td>
                        <td colspan="2">&nbsp;</td>
                    </tr>
                
                </tbody>                    
            </table>                    
        </form>   
             <c:remove var="ordenamiento" scope="session" />
                </c:if>

        <br><br>
        <form name="subirArchivo" action="SubirArchivo" method="POST" enctype="multipart/form-data">
            <input type="hidden" name="accion" value="subirArchivo"/>
            <table align="center" width="90%">
                <tbody>                        
                    <!-- Formulario para subir estado de cuenta individual --->
                    <tr><td colspan="6">&nbsp;</td></tr>
                    <tr align="center" ><td  class="Titulo" style="background-color: #d6e8f9;" colspan="6"> Subir estado de cuenta individual </td></tr>
                    <tr><td colspan="6">&nbsp;</td></tr>
                    <tr>
                        <td colspan="3" align="center">
                            <input type="file" style="" name="archivo"/>
                        </td>
                    </tr>

                    <tr><td colspan="3">&nbsp;</td></tr>

                    <tr align="center">
                        <td colspan="3">
                            <table align="center">
                                <tr>
                                    <td>
                                        <!--<input type="button" name="subirArchivo" value="Subir" onclick="return cargaValor(subirArchivo,'subirArchivo');"/>-->
                                        <input type="submit" value="Subir Archivo" name="subirArchivo" />
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </tbody>                    
            </table>                        
        </form>                 

    </body>
</html>
