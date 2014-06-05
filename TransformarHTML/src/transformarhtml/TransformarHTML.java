/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transformarhtml;

import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.extractors.ArticleExtractor;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xml.sax.InputSource;

/**
 *
 * @author daniel
 */
public class TransformarHTML {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {

        String direccion = "http://192.168.2.11:8888/pages/";
        String[] pages = {"Ronaldo", "América", "Chile", "Juegos_Olímpicos_de_la_Juventud_2018", "Estadio_Antonio_Vespucio_Liberti", "Club_Atlético_River_Plate", "David_Beckham", "Real_Madrid", "Buenos_Aires", "Marcelo_Salas"};
        String text = null;

        for (int i = 0; i < pages.length; i++) {
            URL url = null;
            try {
                url = new URL(direccion.concat(pages[i]));
            } catch (MalformedURLException ex) {
                Logger.getLogger(TransformarHTML.class.getName()).log(Level.SEVERE, null, ex);
            }

            InputSource is = new InputSource();
            is.setEncoding("UTF-8");
            try {
                is.setByteStream(url.openStream());
            } catch (IOException ex) {
                Logger.getLogger(TransformarHTML.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                text = ArticleExtractor.INSTANCE.getText(is);
            } catch (BoilerpipeProcessingException ex) {
                Logger.getLogger(TransformarHTML.class.getName()).log(Level.SEVERE, null, ex);
            }

            ConexionBD bd = new ConexionBD();
            text = text.replace("\"", " " );
            text = text.replace("'", " " );
            
            int pesoArchivo = 0;
            try {
                final byte[] utf8Bytes = text.getBytes("UTF-8");
                pesoArchivo = utf8Bytes.length;
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(TransformarHTML.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            
// create the java mysql update preparedstatement
            Statement consulta = bd.getConnection().createStatement();
                    consulta.executeUpdate("UPDATE Paginas SET Peso='"+pesoArchivo+"',Contenido='"+text+"' WHERE id="+(i+1));
            consulta.close();
            
            
            bd.desconectar();

//            System.out.println(pages[i]);
//            System.out.println(text);
//            System.out.println();
        }

    }

}
