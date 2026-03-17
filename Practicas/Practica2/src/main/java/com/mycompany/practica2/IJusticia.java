/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.practica2;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author cdk04
 */
public interface IJusticia extends Remote {
    
    // devuelve un objeto RespuestaCuenta
    RespuestaCuenta ConsultarCuentas(String ci, String nombres, String apellidos) throws RemoteException; 
       
    // metodo de la interfaz
    Boolean Congelar(Cuenta cuenta,Float monto); // ejectua la orden del congelamiento en el banco
              
}
