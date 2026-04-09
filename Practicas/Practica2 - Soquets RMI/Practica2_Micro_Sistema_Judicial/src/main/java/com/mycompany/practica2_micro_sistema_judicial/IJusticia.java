/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.practica2_micro_sistema_judicial;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *  Interfaz RMI que expone los servicios del Gateway de Justicia al Juez.
 * Creamos 2 metodos remotos
 * @author cdk04
 */
public interface IJusticia extends Remote {
    //  consultar cuentas bancarias en los nodos proveedores
    RespuestaCuenta ConsultarCuentas(String ci, String nombres, String apellidos) throws RemoteException; // devuelve un objeto RespuestaCuenta
       
    // emitir orden de congelamiento en el banco respectivo
    Boolean Congelar(Cuenta cuenta,Float monto) throws RemoteException; // ejectua la orden del congelamiento en el banco
    
    // los dos metodos lanzan RemoteException porque cualquier fallo de red durante la comunicacion RMI sera 
    // capturado a traves de esta excepcion.
              
}
