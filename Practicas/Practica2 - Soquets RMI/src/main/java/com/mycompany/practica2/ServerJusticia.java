/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.practica2;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 *  Servidor que registra el objeto Justicia en RMI.
 *  Solo arranca el RMI
 * @author cdk04
 */ 
public class ServerJusticia {
    public static void main(String[] args){
        try{
            // Instanciamos el objeto remoto
            Justicia justicia = new Justicia();
            
            // levantamos RMI en el puerto por defecto
            LocateRegistry.createRegistry(1099); // levantamos el servidor de registro RMI
            
            // Publicamos el objeto para que el Juez lo encuentre
            Naming.bind("ServidorJusticia", justicia);
            System.out.println("Gateway Justicia RMI listo y esperando ordenes del Juez...");
       
        } catch (RemoteException ex){
            System.err.println("Error de red RMI: " + ex.getMessage());
        } catch (AlreadyBoundException ex){
            System.err.println("El nombre ya esta registrado: " + ex.getMessage());
        } catch (MalformedURLException ex){
            System.err.println("URL RMI mal formada: " + ex.getMessage());
        }
    }
}