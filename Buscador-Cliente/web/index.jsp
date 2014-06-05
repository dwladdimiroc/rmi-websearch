<%@page import="cliente.Cliente"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">
        <link rel="shortcut icon" href="../../assets/ico/favicon.ico">

        <title>Google</title>

        <!-- Bootstrap core CSS -->
        <link type="text/css" href="css/bootstrap.min.css" rel="stylesheet">


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
                        <li class="active"><a href="index.jsp">Buscar</a></li>
                        <li><a href="pages/upload.jsp">Upload</a></li>
                    </ul>
                </div><!--/.nav-collapse -->
            </div>
        </div>

        <br><br><br>

        <div class="container">
            <div class="text-center">
                <h3 class="text-center">
                    <img src="img/google3.gif" alt="" title="" border="0" /><!-- gif google -->
                </h3>
                <%  Cliente cliente = new Cliente();
                    if(cliente.verificarConexion()){%>
                    <form>
                    <input title="Ingrese una búsqueda" type="text" name="buscador" id="buscador" pattern="[a-zA-Z0-9]+" required/><br/>
                    <input type="submit" value="Buscar" name="buscar" 
                           onclick="document.forms[0].action = 'pages/busqueda.jsp';
                                   return true;" />
                </form>
                <%}else{%>
                    <h1>Lo sentios, los servidores en este momento no están disponibles...</h1>
                <%}%>        
            </div>
        </div><!-- /.container -->

    </body>
</html>
