<%-- 
    Document   : uploadFileTitle
    Created on : 03-05-2014, 02:52:24 AM
    Author     : daniel
--%>
<%@page import="cliente.Cliente"%>
<%
    String titulo = request.getParameter("titulo");
    String archivo = request.getParameter("archivo");
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">
        <link rel="shortcut icon" href="../../assets/ico/favicon.ico">

        <title>Google - Upload</title>

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
                        <li class="active"><a href="upload.jsp">Upload</a></li>
                    </ul>
                </div><!--/.nav-collapse -->
            </div>
        </div>

        <br><br><br>

        <div class="container">
            <div class="text-center">
                <h1>Titulo: <%=titulo%></h1>
                <h1>Nombre del archivo: <%=archivo%></h1>
                <%
                    Cliente cliente = new Cliente();

                    if (cliente.archivoNuevo(titulo)) {
                        if (cliente.uploadServidor("http://192.168.0.102/pagesCliente/".concat(archivo), titulo, archivo)) {
                            out.print("Upload finalizado");
                        } else {
                            out.print("Se produjo un error en el upload");
                        }
                    } else {
                        out.print("Ya existe el archivo en la base de datos");
                    }
                %>
            </div>
        </div>
    </body>
</html>
