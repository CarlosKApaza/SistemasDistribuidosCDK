/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.operacionesrmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author cdk04
 */
// contrato de operadciones, le dira al cliente que metodos existen
public interface IOperaciones extends Remote{
    // todos los metodos lanzan una excepcion por si hay fallos 
    int factorial(int n) throws RemoteException; 
    int fibonacci(int n) throws RemoteException;
    int sumatoria(int n) throws RemoteException;
}
