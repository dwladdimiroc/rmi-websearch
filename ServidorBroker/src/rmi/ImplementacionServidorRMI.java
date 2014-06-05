/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import interfacermi.InterfaceRMI;
import interfacermi.InterfaceRMIServer;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import paginaweb.PaginaWeb;

/**
 *
 * @author daniel
 */
public class ImplementacionServidorRMI extends UnicastRemoteObject implements InterfaceRMI {

    Logger logueador; //Este objeto funciona como println

    public static int puertoBD1 = 4001; // Buscar Puertos abiertos
    public static int puertoBD2 = 4002; // Buscar Puertos abiertos
    public static String direccionServerBD1 = "192.168.0.102"; //Por ahora es localhost se tendria que cambiar a la IP del servidor RMI
    public static String direccionServerBD2 = "192.168.0.102";
    public static String nombreReferenciaRemota = "Implementacion"; // Nombre de el objeto subido

    public ImplementacionServidorRMI() throws RemoteException {

        logueador = Logger.getLogger(getClass().getName()); // Indico que quiero utilizar este logger para la clase
        logueador.log(Level.INFO, "Instanciando la clase que implementa las funciones remotas");
    }

    @Override
    public ArrayList<PaginaWeb> obtenerDatos(String dato) throws RemoteException {
        logueador.log(Level.INFO, "Alguien ha hecho una consulta"); // se imprime en el servidor RMI cada vez que alguien lo ejecute

        InterfaceRMIServer objetoRemotoServerBD1, objetoRemotoServerBD2;
        ArrayList<PaginaWeb> datosDB1 = null, datosDB2 = null;

        //Se instancia el objeto que conecta con el servidor
        ConexionRMIServer conexionDB1 = new ConexionRMIServer();

        try {
            //Se conecta con el servidor
            if (conexionDB1.iniciarRegistry(direccionServerBD1, puertoBD1, nombreReferenciaRemota)) {

                //Se obtiene la referencia al objeto remoto
                objetoRemotoServerBD1 = (InterfaceRMIServer) conexionDB1.getServidor();

                //Este método se ejecuta en el servidor
                datosDB1 = objetoRemotoServerBD1.busquedaDatos(dato);
            }
        } catch (RemoteException e) {
            System.out.println("Ha ocurrido un error DB1: " + e);
        }

        ConexionRMIServer conexionDB2 = new ConexionRMIServer();

        try {
            //Se conecta con el servidor

            if (conexionDB2.iniciarRegistry(direccionServerBD2, puertoBD2, nombreReferenciaRemota)) {
                //Se obtiene la referencia al objeto remoto
                objetoRemotoServerBD2 = (InterfaceRMIServer) conexionDB2.getServidor();

                //Este método se ejecuta en el servidor
                datosDB2 = objetoRemotoServerBD2.busquedaDatos(dato);
            }
        } catch (RemoteException e) {
            System.out.println("Ha ocurrido un error DB2: " + e);
        }

        return joinPaginaWeb(datosDB1, datosDB2, conexionDB1.estadoConexion(), conexionDB2.estadoConexion());
    }

    public ArrayList<PaginaWeb> joinPaginaWeb(ArrayList<PaginaWeb> BD1, ArrayList<PaginaWeb> BD2, boolean conexionDB1, boolean conexionDB2) {
        ArrayList<PaginaWeb> BDFinal;

        if ((!conexionDB1) && (conexionDB2)) {
            BDFinal = (ArrayList<PaginaWeb>) BD2.clone();
        } else if ((conexionDB1) && (!conexionDB2)) {
            BDFinal = (ArrayList<PaginaWeb>) BD1.clone();
        } else {
            BDFinal = (ArrayList<PaginaWeb>) BD1.clone();
            for (int i = 0; i < BD2.size(); i++) {
                if (sinDato(BD1, BD2.get(i))) {
                    BDFinal.add(BD2.get(i));
                }
            }
        }
        return BDFinal;
    }

    public boolean sinDato(ArrayList<PaginaWeb> BD1, PaginaWeb BD2) {
        for (int i = 0; i < BD1.size(); i++) {
            System.out.println(BD1.get(i).equals(BD2));
            if (BD1.get(i).getTitulo().compareTo(BD2.getTitulo()) == 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean uploadDatos(String url, String titulo, String archivo) throws RemoteException {
        logueador.log(Level.INFO, "Alguien ha hecho un upload"); // se imprime en el servidor RMI cada vez que alguien lo ejecute

        InterfaceRMIServer objetoRemotoServerBD1, objetoRemotoServerBD2;
        boolean uploadDatosDB1 = false, uploadDatosDB2 = false;

        //Se instancia el objeto que conecta con el servidor
        ConexionRMIServer conexionDB1 = new ConexionRMIServer();

        try {
            //Se conecta con el servidor
            if (conexionDB1.iniciarRegistry(direccionServerBD1, puertoBD1, nombreReferenciaRemota)) {

                //Se obtiene la referencia al objeto remoto
                objetoRemotoServerBD1 = (InterfaceRMIServer) conexionDB1.getServidor();

                //Este método se ejecuta en el servidor
                uploadDatosDB1 = objetoRemotoServerBD1.uploadBaseDatos(url, titulo, archivo);
            }
        } catch (RemoteException e) {
            System.out.println("Ha ocurrido un error DB1: " + e);
        }

        ConexionRMIServer conexionDB2 = new ConexionRMIServer();

        try {
            //Se conecta con el servidor

            if (conexionDB2.iniciarRegistry(direccionServerBD2, puertoBD2, nombreReferenciaRemota)) {
                //Se obtiene la referencia al objeto remoto
                objetoRemotoServerBD2 = (InterfaceRMIServer) conexionDB2.getServidor();

                //Este método se ejecuta en el servidor
                uploadDatosDB1 = objetoRemotoServerBD2.uploadBaseDatos(url, titulo, archivo);
            }
        } catch (RemoteException e) {
            System.out.println("Ha ocurrido un error DB2: " + e);
        }
        return (uploadDatosDB1||uploadDatosDB2);
    }

    @Override
    public boolean archivoNuevoBD(String titulo) throws RemoteException {
        logueador.log(Level.INFO, "Alguien ha hecho una consulta"); // se imprime en el servidor RMI cada vez que alguien lo ejecute

        InterfaceRMIServer objetoRemotoServerBD1, objetoRemotoServerBD2;
        boolean datosDB1 = false, datosDB2 = false;

        //Se instancia el objeto que conecta con el servidor
        ConexionRMIServer conexionDB1 = new ConexionRMIServer();

        try {
            //Se conecta con el servidor
            if (conexionDB1.iniciarRegistry(direccionServerBD1, puertoBD1, nombreReferenciaRemota)) {

                //Se obtiene la referencia al objeto remoto
                objetoRemotoServerBD1 = (InterfaceRMIServer) conexionDB1.getServidor();

                //Este método se ejecuta en el servidor
                datosDB1 = objetoRemotoServerBD1.busquedaCoincidenciaTitulo(titulo);
            }
        } catch (RemoteException e) {
            System.out.println("Ha ocurrido un error DB1: " + e);
        }

        ConexionRMIServer conexionDB2 = new ConexionRMIServer();

        try {
            //Se conecta con el servidor

            if (conexionDB2.iniciarRegistry(direccionServerBD2, puertoBD2, nombreReferenciaRemota)) {
                //Se obtiene la referencia al objeto remoto
                objetoRemotoServerBD2 = (InterfaceRMIServer) conexionDB2.getServidor();

                //Este método se ejecuta en el servidor
                datosDB2 = objetoRemotoServerBD2.busquedaCoincidenciaTitulo(titulo);
            }
        } catch (RemoteException e) {
            System.out.println("Ha ocurrido un error DB2: " + e);
        }

        return (datosDB1||datosDB2);
    
    }

    @Override
    public boolean estadoConexionServer() throws RemoteException {
        boolean estadoDB1 = false, estadoDB2 = false;
        logueador.log(Level.INFO, "Probando servidores"); // se imprime en el servidor RMI cada vez que alguien lo ejecute

        //Se instancia el objeto que conecta con el servidor
        ConexionRMIServer conexionDB1 = new ConexionRMIServer();

        try {
            //Se conecta con el servidor
            if (conexionDB1.iniciarRegistry(direccionServerBD1, puertoBD1, nombreReferenciaRemota)) {

                if(conexionDB1.estadoConexion()){
                    estadoDB1=true;
                }
            }
        } catch (RemoteException e) {
            System.out.println("Ha ocurrido un error DB1: " + e);
        }
        
        //Se instancia el objeto que conecta con el servidor
        ConexionRMIServer conexionDB2 = new ConexionRMIServer();

        try {
            //Se conecta con el servidor
            if (conexionDB2.iniciarRegistry(direccionServerBD2, puertoBD2, nombreReferenciaRemota)) {

                if(conexionDB2.estadoConexion()){
                    estadoDB2=true;
                }
            }
        } catch (RemoteException e) {
            System.out.println("Ha ocurrido un error DB2: " + e);
        }
        
        return (estadoDB1||estadoDB2);
    }
}
