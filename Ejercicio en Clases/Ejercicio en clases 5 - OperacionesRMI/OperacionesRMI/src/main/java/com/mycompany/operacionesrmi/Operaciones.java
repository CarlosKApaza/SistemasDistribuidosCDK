/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.operacionesrmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author cdk04
 */

//Unicast le da poder de red e implementamos los metodos de la interfaz
public class Operaciones extends UnicastRemoteObject implements IOperaciones {

    public Operaciones() throws RemoteException { // lanzamos una excepcion porque super abre a la red creo 
        super(); 
    }

   @Override
    public int factorial(int n) throws RemoteException {
        if (n <= 1) return 1;
        int fact = 1;
        // bucle de 2 hasta n
        for (int i = 2; i <= n; i++) {
            fact *= i;
        }
        return fact;
    }
    
    @Override
    public int fibonacci(int n) throws RemoteException {
        if (n <= 0) return 0;
        if (n == 1) return 1;
        int a = 0, b = 1, c = 0;
        // calculamos la suma de los 2 anteriores iterando 
        for (int i = 2; i <= n; i++) {
            c = a + b;
            a = b;
            b = c;
        }
        return c;
    }
    
    @Override
    public int sumatoria(int n) throws RemoteException {
        // Fórmula de Gauss 
        return n * (n + 1) / 2; 
    }

}
