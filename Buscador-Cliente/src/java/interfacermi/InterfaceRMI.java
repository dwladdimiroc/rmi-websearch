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
public interface InterfaceRMI extends Remote {
    
    public ArrayList<PaginaWeb> obtenerDatos(String dato) throws RemoteException;
    public boolean uploadDatos(String url, String titulo, String archivo) throws RemoteException;
    public boolean archivoNuevoBD(String titulo) throws RemoteException;
    public boolean estadoConexionServer() throws RemoteException;
} 
