/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tarea2_tictactoe;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Servidor que registra el objeto MotorJuego en RMI.
 * Levanta el RMI en el puerto 1099 - bucle principal de red 
 * @author cdk04
 */
public class Servidor {
    public static void main(String[] args) {
        try{
            // instanciamos el motor logico del juego (el objeto remoto)
            MotorJuego motor = new MotorJuego();
            
            // Levantamos el servidor de registro RMI en el puerto 1099
            Registry registry = LocateRegistry.createRegistry(1099);
            
            // Publicamos el objeto usando rebind para evitar AlreadyBoundException
            registry.rebind("TicTacToeService", motor);
            System.out.println("[+] Servidor Tic Tac Toe iniciado y en espera de jugadores...");
            
        } catch (RemoteException ex){
            System.err.println("[-] Error de la red RMI al iniciar el servidor " + ex.getMessage());
        } catch (Exception ex) {
            System.err.println("[-] Error critico inesperado: " + ex.getMessage());
        }
    }
}
