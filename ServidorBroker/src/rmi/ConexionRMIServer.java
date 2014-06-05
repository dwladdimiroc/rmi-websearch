/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import interfacermi.InterfaceRMIServer;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author daniel
 */
public class ConexionRMIServer {

    private Registry registro;
    private boolean conectado;
    private InterfaceRMIServer servidor;

    public ConexionRMIServer() {
        this.conectado = false;
        this.registro = null;
        this.servidor = null;
    }

    public boolean estadoConexion() {
        return this.conectado;
    }

    public boolean iniciarRegistry(String IP, int PUERTO, String nombreObjRemoto) throws RemoteException {
        try {


            java.security.AllPermission a = new java.security.AllPermission();
            System.setProperty("java.security.policy", "rmi.policy");

            try {
                registro = LocateRegistry.getRegistry(IP, PUERTO);

            } catch (RemoteException e) {
                System.out.println(IP + ":" + PUERTO);
                System.out.println(e.getMessage());
                System.out.println(e.toString());
            }

            servidor = (InterfaceRMIServer) registro.lookup(nombreObjRemoto);

            this.conectado = true;
            return true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Error: No se posee conexión");
            this.conectado = false;
            return false;
        }
    }

    public InterfaceRMIServer getServidor() {
        return servidor;
    }
}
