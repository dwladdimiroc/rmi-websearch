/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorbd2;

import conexionmysql.ConexionBD;
import java.rmi.RemoteException;
import java.util.Scanner;
import javax.swing.*;
import java.awt.event.*;
import rmi.ImplementacionServidorRMI;
import rmi.ServidorRMI;

/**
 *
 * @author daniel
 */
public class ServidorBD2 extends JFrame implements ActionListener {

    JButton botonSwitchEncender;
    JButton botonSwitchApagar;

    public ServidorBD2() {
        setLayout(null);
        botonSwitchEncender = new JButton("Encender");
        botonSwitchEncender.setBounds(20, 20, 180, 60);
        add(botonSwitchEncender);
        botonSwitchEncender.addActionListener(this);

        setLayout(null);
        botonSwitchApagar = new JButton("Apagar");
        botonSwitchApagar.setBounds(20, 100, 180, 60);
        add(botonSwitchApagar);
        botonSwitchApagar.addActionListener(this);
        
        this.setTitle("Servidor BD2");
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonSwitchEncender) {
            activarServidor();
        }
        if (e.getSource() == botonSwitchApagar) {
            desconectarServidor();
        }
    }

    public static void main(String[] args) {
        ServidorBD2 panel = new ServidorBD2();
        panel.setBounds(650, 350, 250, 250);
        panel.setVisible(true);
    }

    public static ServidorRMI server;
    public static int puertoEscucha = 4002;
    public static ImplementacionServidorRMI objetoLocal;
    public static String nombreReferenciaRemota = "Implementacion";

    /**
     * **** Función principal *****
     */
    public void activarServidor() {

        //Instancio la clase que puede ser llamada remotamente
        try {
            objetoLocal = new ImplementacionServidorRMI();
        } catch (RemoteException re) {
            System.out.println(re.getMessage()); //Muestro el error
        }

        /*
         * Se deja disponible hacia la red la utilización del objeto remoto
         */
        System.out.println("Se va a conectar...");
        server = new ServidorRMI();

        boolean resultadoConexion = server.iniciarConexion(objetoLocal, nombreReferenciaRemota, puertoEscucha);
        if (resultadoConexion) {
            System.out.println("Se ha establecido la conexión correctamente");
        } else {
            System.out.println("Ha ocurrido un error al conectarse");
        }

        System.out.println("El servidor esta con conexion: " + server.isConectado());

    }

    public void desconectarServidor() {
        try {
            //bd.desconectar();
            server.detener(nombreReferenciaRemota);
        } catch (RemoteException re) {
            System.out.println(re.getMessage()); //Muestro el error
        }
        System.out.println("El servidor esta con conexion: " + server.isConectado());
    }
}
