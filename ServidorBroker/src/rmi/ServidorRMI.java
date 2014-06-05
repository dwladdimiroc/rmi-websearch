/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;


import interfacermi.InterfaceRMI;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author daniel
 */
public class ServidorRMI {

    private Registry registro;
    private boolean conectado;

    public ServidorRMI() {
        this.conectado = false;
        this.registro = null;
    }

    public boolean isConectado() {
        return this.conectado;
    }

    /*
     * Devuelve el registry, lo inicia en caso de no estár instanciado
     */
    private Registry getRegistry(int puerto) throws RemoteException {
        if (this.registro == null) {
            startRegistry(puerto);
        }
        return registro;
    }

    /*
     * Inicia el registry
     */
    private void startRegistry(int Puerto) throws RemoteException {
        try {
            registro = LocateRegistry.getRegistry(Puerto); // retorna el objeto remoto
            registro.list();  // retorna string de nombres de los objetos remotos

            conectado = true;
        } catch (RemoteException e) {
            registro = LocateRegistry.createRegistry(Puerto); // crea el registro en el localhost
            registro.list();  // retorna string de nombres de los objetos remotos

            conectado = true;
        }
    }

    public boolean iniciarConexion(InterfaceRMI objetoAPublicar, String nombreUsado, int puerto) {

        try {
            this.registro = getRegistry(puerto);
            /* Ahora hay que hacerlo remoto, para ello se registra en el Registry
             * con el método "rebind" que lo registra con un nombre para poder ser 
             * visto en ese espacio en este caso se le dio el nombre "Implementacion"
             */
            registro.rebind(nombreUsado, objetoAPublicar);
        } catch (RemoteException re) {
            System.out.println(re.getMessage()); //Muestro el error
            return false;
        }
        return true;
    }

    /*
     * Deja no disponible el objeto local ante las conexiones remotas.
     */
    public boolean detener(String nombreUsado) throws RemoteException {
        try {
            //Se saca de rmiregistry el objeto "Implementacion"
            //Ya no esta disponible para ser consumido
            registro.unbind(nombreUsado);
            conectado = false;
        } catch (NotBoundException | AccessException ex) {
            Logger.getLogger(ServidorRMI.class.getName()).log(Level.SEVERE, null, ex);
            conectado = true;
            return false;
        }
        return true;
    }
}
