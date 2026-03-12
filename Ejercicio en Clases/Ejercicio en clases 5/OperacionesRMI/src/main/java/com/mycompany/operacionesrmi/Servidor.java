/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.operacionesrmi;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 *
 * @author cdk04
 */
public class Servidor {
    public static void main(String[] args){
        try{
            // intanciamos la clase operaciones 
            Operaciones op = new Operaciones();
            // levamos RMI
            LocateRegistry.createRegistry(1099); // levantar el servidor registro
            // el cliente buscara nuestro objeto con un nombre publico de Operaciones
            Naming.bind("Operaciones", op);
            
            System.out.println("Server de operaciones lito");
            
        } catch (RemoteException ex){
            System.getLogger(Servidor.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (AlreadyBoundException ex){
            System.getLogger(Servidor.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (MalformedURLException ex){
            System.getLogger(Servidor.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
}
