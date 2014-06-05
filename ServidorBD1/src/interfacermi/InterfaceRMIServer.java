/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package interfacermi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import paginaweb.PaginaWeb;

/**
 *
 * @author daniel
 */
public interface InterfaceRMIServer extends Remote {
    //Busqueda de la pregunta solicitada en la BD enviada desde el broker
    public ArrayList<PaginaWeb> busquedaDatos(String dato) throws RemoteException;
    public boolean uploadBaseDatos(String url, String titulo, String archivo) throws RemoteException;
    public boolean busquedaCoincidenciaTitulo(String titulo) throws RemoteException;
}
