/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import conexionmysql.ConexionBD;
import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.extractors.ArticleExtractor;
import interfacermi.InterfaceRMIServer;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xml.sax.InputSource;
import paginaweb.PaginaWeb;

/**
 *
 * @author daniel
 */
public class ImplementacionServidorRMI extends UnicastRemoteObject implements InterfaceRMIServer {

    Logger logueador; //Este objeto funciona como println

    public ImplementacionServidorRMI() throws RemoteException {

        logueador = Logger.getLogger(getClass().getName()); // Indico que quiero utilizar este logger para la clase
        logueador.log(Level.INFO, "Instanciando la clase que implementa las funciones remotas");
    }

    @Override
    public ArrayList<PaginaWeb> busquedaDatos(String dato) throws RemoteException {
        logueador.log(Level.INFO, "Alguien ha hecho una consulta"); // se imprime en el servidor RMI cada vez que alguien lo ejecute
        ArrayList<PaginaWeb> informacion = new ArrayList<PaginaWeb>();
        PaginaWeb consultada;
        ConexionBD db = new ConexionBD();
        try {
            try (PreparedStatement consulta = db.getConnection().prepareStatement("SELECT * FROM Paginas WHERE Contenido LIKE '%" + dato + "%' ORDER BY Fecha Desc");
                    ResultSet res = consulta.executeQuery()) {

                while (res.next()) {
                    consultada = new PaginaWeb(Integer.parseInt(res.getString("Peso")), res.getString("Titulo"), res.getString("Link"), res.getString("Fecha"), acotarTexto(res.getString("Contenido"), dato));
                    informacion.add(consultada);
                }

            }

        } catch (Exception e) {
            System.out.print("No se pudo consultar a la base de datos\n" + e);
        }

        db.desconectar();
        return informacion;
    }

    public String acotarTexto(String texto, String busqueda) {

        String textoFinal = null;
        String[] textoAcotado = texto.split("\\.");
        for (int i = 0; i < textoAcotado.length; i++) {
            if (textoAcotado[i].toLowerCase().contains(busqueda.toLowerCase())) {
                textoFinal = "...".concat(textoAcotado[i]).concat("...");
                break;
            }
        }

        return textoFinal;

    }

    @Override
    public boolean uploadBaseDatos(String url, String titulo, String archivo) throws RemoteException {
        logueador.log(Level.INFO, "Alguien ha hecho un upload"); // se imprime en el servidor RMI cada vez que alguien lo ejecute
        boolean uploadDato = false;
        ConexionBD db = new ConexionBD();
        try {
            try (Statement update = db.getConnection().createStatement()) {
                PaginaWeb paginaUpdate = new PaginaWeb(0, " ", " ", " ", " ");
                paginaUpdate = recuperarArchivo(paginaUpdate, url, titulo, archivo);
                update.executeUpdate("INSERT INTO Paginas (Peso,Titulo,Link,Fecha,Contenido) VALUES ('" + paginaUpdate.getPeso() + "','" + paginaUpdate.getTitulo() + "','" + paginaUpdate.getLink() + "','" + paginaUpdate.getFecha() + "','" + paginaUpdate.getContenido() + "')");
            }
        } catch (SQLException e) {
            System.out.print("No se pudo consultar a la base de datos\n" + e);
            return uploadDato;
        }

        db.desconectar();
        
        uploadDato=true;
        return uploadDato;
    }

    public PaginaWeb recuperarArchivo(PaginaWeb paginaUpdate, String url, String titulo, String archivo) {
        try {
            String[] cmd = {"wget", "-P", "/var/www/pagesServer2", url};
            Runtime.getRuntime().exec(cmd);
        } catch (IOException ioe) {
            System.out.println(ioe);
        }

        String text = null;
        String direccion = "http://192.168.0.102/pagesServer2/";
        URL urlBase = null;

        paginaUpdate.setLink(direccion.concat(archivo));

        paginaUpdate.setTitulo(titulo);

        try {
            urlBase = new URL(paginaUpdate.getLink());
        } catch (MalformedURLException ex) {
            Logger.getLogger(PaginaWeb.class.getName()).log(Level.SEVERE, null, ex);
        }

        InputSource is = new InputSource();
        is.setEncoding("UTF-8");

        try {
            is.setByteStream(urlBase.openStream());
        } catch (IOException ex) {
            Logger.getLogger(PaginaWeb.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            text = ArticleExtractor.INSTANCE.getText(is);
        } catch (BoilerpipeProcessingException ex) {
            Logger.getLogger(PaginaWeb.class.getName()).log(Level.SEVERE, null, ex);
        }

        text = text.replace("\"", " ");
        text = text.replace("'", " ");
        paginaUpdate.setContenido(text);

        byte[] utf8Bytes = null;
        try {
            utf8Bytes = text.getBytes("UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(PaginaWeb.class.getName()).log(Level.SEVERE, null, ex);
        }
        paginaUpdate.setPeso(utf8Bytes.length);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        //System.out.println(dateFormat.format(date)); //2013/10/15 16:16:39
        paginaUpdate.setFecha(dateFormat.format(date));

        return paginaUpdate;

    }

    @Override
    public boolean busquedaCoincidenciaTitulo(String titulo) throws RemoteException {
        logueador.log(Level.INFO, "Alguien ha hecho una consulta"); // se imprime en el servidor RMI cada vez que alguien lo ejecute
        ConexionBD db = new ConexionBD();
        try {
            try (PreparedStatement consulta = db.getConnection().prepareStatement("SELECT * FROM Paginas WHERE Titulo='"+titulo+"'");
                    ResultSet res = consulta.executeQuery()) {
                while (res.next()) {
                    if(titulo.compareTo(res.getString("Titulo"))==0){
                        return false;
                    }
                        
                }

            }

        } catch (Exception e) {
            System.out.print("No se pudo consultar a la base de datos\n" + e);
        }

        db.desconectar();
        return true;
    }
}
