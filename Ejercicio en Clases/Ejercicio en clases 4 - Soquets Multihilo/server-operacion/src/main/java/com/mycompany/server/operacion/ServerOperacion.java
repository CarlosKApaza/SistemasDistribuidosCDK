/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.server.operacion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 *
 * @author cdk04
 */
public class ServerOperacion {

    public static void main(String[] args) {
        int port = 5002;
        // Se crea una sola vez para abrir el puerto y dejar el servidor escuchando
        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("Se inicio el servidor de operaciones con exito en puerto: " + port);

            while (true) {
                Socket client = null;
                //try-with-resources para el ServerSocket (se cierra automáticamente al salir).
                try {
                    // Espera hasta que un cliente se conecte
                    client = server.accept();
                    System.out.println("Nuevo cliente conectado: " + client); // imprimimos el socket porque socket tiene un toString() que muestra la informacion
                    //System.out.println("Nuevo cliente conectado: " + client.getInetAddress()); // Eso solo muestra la IP

                    System.out.println("Asignando nuevo hilo para este cliente");

                    
                    // Obtener los flujos de entrada y salida 
                    DataInputStream dis = new DataInputStream(client.getInputStream()); // entrada
                    DataOutputStream dos = new DataOutputStream(client.getOutputStream()); // salida
                    
                     // crear un handler para pararle el socket y flujos
                    ClientHandlerOperacion handler = new ClientHandlerOperacion(client, dis, dos);
                    
                    handler.start();    // aquí se inicia el hilo de run() (método start() de Thread)
                    
                    // posible erro aqui
                    //crear un handler pasandole socket y flujos
                    //ClientHandlerOperacion handler = new ClientHandlerOperacion(client, dis, dos);
                    //handler.start();  // aquí se inicia el hilo (método start() de Thread)
                    // hasta aqui

                } catch (IOException e) {
                    if (client != null) {
                        client.close(); // libera el puerto
                    }
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            System.out.println("Error en el servidor: " + e.getMessage());
        }
    }
}
