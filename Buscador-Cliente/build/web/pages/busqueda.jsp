<%-- 
    Document   : busqueda
    Created on : 02-05-2014, 01:49:00 AM
    Author     : daniel
--%>

<%@page import="paginaweb.PaginaWeb"%>
<%@page import="java.util.ArrayList"%>
<%@page import="cliente.Cliente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String busquedaJSP = request.getParameter("buscador");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">
        <link rel="shortcut icon" href="../../assets/ico/favicon.ico">

        <title>Busqueda</title>

        <!-- Bootstrap core CSS -->
        <link type="text/css" href="../css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="#">Esto no es google</a>
                </div>
                <div class="collapse navbar-collapse">
                    <ul class="nav navbar-nav">
                        <li><a href="../index.jsp">Buscar</a></li>
                        <li><a href="upload.jsp">Upload</a></li>
                    </ul>
                </div><!--/.nav-collapse -->
            </div>
        </div>

        <br><br><br>
        <h1>Busqueda: <%=busquedaJSP%></h1>
        <div class="container">
            <div class="text-center">
                <%
                    Cliente cliente = new Cliente();
                    ArrayList<PaginaWeb> datosConexion = cliente.conexionServidor(busquedaJSP);

                    if (datosConexion != null) {
                        for (int i = 0; i < datosConexion.size(); i++) {
                            out.print("<a href=" + datosConexion.get(i).getLink() + ">");
                            out.print(datosConexion.get(i).getTitulo() + " Fecha: " + datosConexion.get(i).getFecha());
                            out.print("</a>");
                %>         <br>
                <%
                    out.print(datosConexion.get(i).getContenido());
                %>
                <br> <br>
                <%
                        }
                    } else {
                        out.print("No existen archivos");
                    }
                %>
            </div>
        </div>
    </body>
</html>
