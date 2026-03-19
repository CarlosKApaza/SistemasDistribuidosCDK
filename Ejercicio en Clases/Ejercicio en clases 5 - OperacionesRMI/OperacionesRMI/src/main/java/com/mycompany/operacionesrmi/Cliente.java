/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.operacionesrmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

/**
 *
 * @author cdk04
 */
public class Cliente {

    public static void main(String[] args) {
        try {
            // casteamos la interfaz para crear el Stub
            IOperaciones operaciones = (IOperaciones) Naming.lookup("rmi://localhost/Operaciones");
            Scanner scanner = new Scanner(System.in);
            int opcion = 0;
            
            // menu pa que el cliente escoja la operacion
            do {
                System.out.println("\n--- MENU DE OPERACIONES RMI ---");
                System.out.println("1. Calcular Factorial");
                System.out.println("2. Calcular Fibonacci");
                System.out.println("3. Calcular Sumatoria");
                System.out.println("4. Salir");
                System.out.print("Introduzca una opcion: ");
                opcion = scanner.nextInt();

                if (opcion >= 1 && opcion <= 3) {
                    System.out.print("Ingresa el numero a calcular: ");
                    int num = scanner.nextInt();
                    
                    // mmmm llamadas a distancia 
                    switch (opcion) {
                        case 1:
                            System.out.println("Resultado Factorial: " + operaciones.factorial(num));
                            break;
                        case 2:
                            System.out.println("Resultado Fibonacci: " + operaciones.fibonacci(num));
                            break;
                        case 3:
                            System.out.println("Resultado Sumatoria: " + operaciones.sumatoria(num));
                            break;
                    }
                } else if (opcion != 4) {
                    System.out.println("Opción no valida.");
                }

            } while (opcion != 4);
            
            System.out.println("Hasta luego... PROGRAMA CERRADO");
            scanner.close();
       } catch (NotBoundException ex) {
            System.getLogger(Cliente.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (MalformedURLException ex) {
            System.getLogger(Cliente.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (RemoteException ex) {
            System.getLogger(Cliente.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
}