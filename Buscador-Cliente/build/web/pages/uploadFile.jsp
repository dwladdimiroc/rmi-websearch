<%-- 
    Document   : uploadFile
    Created on : 02-05-2014, 10:23:13 AM
    Author     : luc
--%>
<%@ page import="org.apache.commons.fileupload.FileItem" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.fileupload.*" %>
<%@ page import="org.apache.commons.fileupload.disk.*" %>
<%@ page import="org.apache.commons.fileupload.servlet.*" %>
<%@ page import="org.apache.commons.io.*" %>
<%@ page import="java.io.*" %> 
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

                <%

                    String ubicacionArchivo = "/var/www/pagesCliente";
                    DiskFileItemFactory factory = new DiskFileItemFactory();
                    factory.setRepository(new File(ubicacionArchivo));
                    factory.setSizeThreshold(1024);

                    ServletFileUpload upload = new ServletFileUpload(factory);

                    try {
                        List<FileItem> partes = upload.parseRequest(request);

                        for (FileItem item : partes) {
                            File file = new File(ubicacionArchivo, item.getName());
                            item.write(file);
                        }
                        out.write("El archivo se a subido correctamente");
                    } catch (FileUploadException ex) {
                        out.write("Error al subir archivo " + ex.getMessage());
                    }

                %> 

                <form>
                    Titulo: <input type="text" name="titulo" id="titulo" pattern="[a-zA-Z0-9_]+" required /><br/>
                    Nombre del archivo: <input type="text" name="archivo" id="archivo" pattern="[a-zA-Z0-9_]+" required /><br/>
                    <input type="submit" value="Continuar upload" name="Continuar upload" 
                           onclick="document.forms[0].action = 'uploadFileTitle.jsp';
                                   return true;" />
                </form>

            </div>
        </div>
    </body>
</html>