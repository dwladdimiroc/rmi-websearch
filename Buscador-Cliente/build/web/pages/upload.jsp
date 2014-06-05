<%-- 
    Document   : upload
    Created on : 02-05-2014, 11:39:43 PM
    Author     : daniel
--%>

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
                <form action="uploadFile.jsp" method="post" enctype="multipart/form-data">
                    <input type="file" name="file" id="file"/>
                    <br />
                    <input type="submit" value="Subir archivo" />
                </form>
            </div>
        </div>
    </body>
</html>
