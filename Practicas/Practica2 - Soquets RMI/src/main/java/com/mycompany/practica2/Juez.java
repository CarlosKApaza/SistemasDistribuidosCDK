/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.practica2;


import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

/**
 *
 * Cliente RMI (Juez).
 * Interfaz de usuario para emitir órdenes al Servidor Justicia.
 * puse pasos 1,2,3 para guiarme
 * @author cdk04
 */
public class Juez {
    
 public static void main(String[] args) {
        try {
            // 1. Buscamos el objeto remoto (Stub)
            IJusticia justicia = (IJusticia) Naming.lookup("rmi://localhost/ServidorJusticia");
            Scanner scanner = new Scanner(System.in);
            int opcion = 0;

            do {
                System.out.println("\n=== SISTEMA JUDICIAL - CONSULTA DE CUENTAS ===");
                System.out.println("1. Emitir Consulta a Entidades Financieras");
                System.out.println("2. Salir");
                System.out.print("Seleccione una opcion: ");
                
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer

                if (opcion == 1) {
                    System.out.print("Ingrese CI del investigado: ");
                    String ci = scanner.nextLine();
                    System.out.print("Ingrese Nombres: ");
                    String nombres = scanner.nextLine();
                    System.out.print("Ingrese Apellidos: ");
                    String apellidos = scanner.nextLine();

                    System.out.println("\n[Procesando orden en la red neuronal bancaria...]");
                    
                    // 2. Llamada RMI al Gateway
                    RespuestaCuenta respuesta = justicia.ConsultarCuentas(ci, nombres, apellidos);

                    // 3. Procesamos e imprimimos la respuesta
                    if (respuesta.getError()) {
                        System.err.println("ERROR DEL SISTEMA: " + respuesta.getMensaje());
                    } else {
                        System.out.println("ESTADO: " + respuesta.getMensaje());
                        System.out.println("--- RESULTADOS HALLADOS ---");
                        
                        if (respuesta.getCuentas().isEmpty()) {
                            System.out.println("No se encontraron cuentas para el CI: " + ci);
                        } else {
                            for (Cuenta c : respuesta.getCuentas()) {
                                System.out.println(c.toString());
                            }
                        }
                        System.out.println("---------------------------");
                    }
                } else if (opcion != 2) {
                    System.out.println("Opcion no valida.");
                }

            } while (opcion != 2);

            System.out.println("Cerrando sesion del Juez...");
            scanner.close();

        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            System.err.println("Error de conexion RMI: " + ex.getMessage());
        }
    }
}
