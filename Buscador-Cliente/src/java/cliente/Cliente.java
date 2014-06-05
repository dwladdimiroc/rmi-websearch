/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import interfacermi.InterfaceRMI;
import java.rmi.RemoteException;
import java.util.ArrayList;
import paginaweb.PaginaWeb;
import rmi.ConexionRMI;

/**
 *
 * @author daniel
 */
public class Cliente {

    public static int puerto = 4000; // Buscar Puertos abiertos
    public static String direccionServerBroker = "192.168.0.102"; //Por ahora es localhost se tendria que cambiar a la IP del servidor RMI
    public static String nombreReferenciaRemota = "Implementacion"; // Nombre de el objeto subido
    public ArrayList<PaginaWeb> datosConexion;
    public boolean uploadDatos = false;
    public boolean nuevoDato = false;
    public boolean estadoConexion = false;

    public ArrayList<PaginaWeb> conexionServidor(String busqueda) {

        InterfaceRMI objetoRemoto;

        //Se instancia el objeto que conecta con el servidor
        ConexionRMI conexion = new ConexionRMI();
        try {
            //Se conecta con el servidor
            conexion.iniciarRegistry(direccionServerBroker, puerto, nombreReferenciaRemota);

            //Se obtiene la referencia al objeto remoto
            objetoRemoto = conexion.getServidor();

            //Ahora es posible llamar métodos del objeto remoto
            String palabra = busqueda;

            //Este método se ejecuta en el servidor
            datosConexion = objetoRemoto.obtenerDatos(palabra);

        } catch (RemoteException e) {
            System.out.println("Ha ocurrido un error");
            return null;
        }

        return datosConexion;
    }

    public boolean uploadServidor(String url, String titulo, String archivo) {

        InterfaceRMI objetoRemoto;

        //Se instancia el objeto que conecta con el servidor
        ConexionRMI conexion = new ConexionRMI();
        try {
            //Se conecta con el servidor
            conexion.iniciarRegistry(direccionServerBroker, puerto, nombreReferenciaRemota);

            //Se obtiene la referencia al objeto remoto
            objetoRemoto = conexion.getServidor();

            //Este método se ejecuta en el servidor
            uploadDatos = objetoRemoto.uploadDatos(url, titulo, archivo);

        } catch (RemoteException e) {
            System.out.println("Ha ocurrido un error");
            return false;
        }

        return uploadDatos;
    }

    public boolean archivoNuevo(String titulo) {
        InterfaceRMI objetoRemoto;

        //Se instancia el objeto que conecta con el servidor
        ConexionRMI conexion = new ConexionRMI();
        try {
            //Se conecta con el servidor
            conexion.iniciarRegistry(direccionServerBroker, puerto, nombreReferenciaRemota);

            //Se obtiene la referencia al objeto remoto
            objetoRemoto = conexion.getServidor();

            //Este método se ejecuta en el servidor
            nuevoDato = objetoRemoto.archivoNuevoBD(titulo);

        } catch (RemoteException e) {
            System.out.println("Ha ocurrido un error");
            return false;
        }

        return nuevoDato;
    }

    public boolean verificarConexion() {
        InterfaceRMI objetoRemoto;

        //Se instancia el objeto que conecta con el servidor
        ConexionRMI conexion = new ConexionRMI();
        try {
            //Se conecta con el servidor
            conexion.iniciarRegistry(direccionServerBroker, puerto, nombreReferenciaRemota);
            
            if(!conexion.estadoConexion()){
                estadoConexion=false;
                return estadoConexion;
            }

            //Se obtiene la referencia al objeto remoto
            objetoRemoto = conexion.getServidor();

            //Este método se ejecuta en el servidor
            estadoConexion = objetoRemoto.estadoConexionServer();

        } catch (RemoteException e) {
            System.out.println("Ha ocurrido un error");
            return false;
        }
        
        return estadoConexion;
    }
}
