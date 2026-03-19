/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany._sistema_gestion_tareas;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author cdk04
 */
public class ClientTareas {

    public static void main(String[] args) {
        String host = "localhost";
        int port = 5003; 

        try {
            Socket client = new Socket(host, port);
            DataInputStream dis = new DataInputStream(client.getInputStream());
            DataOutputStream dos = new DataOutputStream(client.getOutputStream());
            Scanner sc = new Scanner(System.in);

            System.out.print("Ingresa tu nombre de usuario: ");
            String nombre = sc.nextLine();
            dos.writeUTF(nombre);

            Thread hiloEscucha = new Thread(() -> {
                try {
                    while (true) {
                        System.out.println(dis.readUTF());
                    }
                } catch (IOException e) {
                    System.out.println("\nDesconectado del servidor de tareas.");
                    System.exit(0);
                }
            });
            hiloEscucha.start();

            while (true) {
                try { Thread.sleep(500); } catch (InterruptedException e) {}
                
                // NUEVO: Agregamos la opción 3 al menú
                System.out.println("\n--- MENU DE TAREAS ---");
                System.out.println("1. Agregar nueva tarea");
                System.out.println("2. Eliminar tarea existente");
                System.out.println("3. Ver lista de tareas");
                System.out.print("Elige una opcion: ");
                
                String opcion = sc.nextLine();

                if (opcion.equals("1")) {
                    System.out.print("Escribe la nueva tarea: ");
                    String nuevaTarea = sc.nextLine();
                    dos.writeUTF("ADD:" + nuevaTarea); 
                } 
                else if (opcion.equals("2")) {
                    System.out.print("Ingresa el numero [indice] de la tarea a borrar: ");
                    String indiceStr = sc.nextLine();
                    dos.writeUTF("DEL:" + indiceStr);
                } 
                // Mandamos el comando LIST: al servidor
                else if (opcion.equals("3")) {
                    dos.writeUTF("LIST:");
                }
                else {
                    System.out.println("Opcion no valida.");
                }
            }

        } catch (IOException e) {
            System.out.println("Error: No se encontro el servidor en " + host + ":" + port);
        }
    }
}