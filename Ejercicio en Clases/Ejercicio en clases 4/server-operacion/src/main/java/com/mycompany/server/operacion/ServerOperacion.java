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
                try {
                    // Espera hasta que un cliente se conecte
                    client = server.accept();
                    System.out.println("Nuevo cliente conectado: " + client.getInetAddress());

                    System.out.println("Asignando nuevo hilo para este cliente");

                    // pasando cliente + salida y entrada
                    //ClientHandlerOperacion handler = new ClientHandlerOperacion(client);
                    //handler.start();

                    // Obtener los flujos de entrada y salida 
                    DataInputStream dis = new DataInputStream(client.getInputStream()); // entrada
                    DataOutputStream dos = new DataOutputStream(client.getOutputStream()); // salida
                    
                     // 
                    ClientHandlerOperacion handler = new ClientHandlerOperacion(client, dis, dos);
                    handler.start();
                    // posible erro aqui
                    //crear un handler pasandole socket y flujos
                    //ClientHandlerOperacion handler = new ClientHandlerOperacion(client, dis, dos);
                    //handler.start();  // aquí se inicia el hilo (método start() de Thread)
                    // hasta aqui
                    // Flujo de entrada: recibe datos del cliente
                    //BufferedReader fromClient =
                    //       new BufferedReader(
                    //            new InputStreamReader(client.getInputStream()));
                    // Flujo de salida: envía datos al cliente
                    //PrintStream toClient =
                    //      new PrintStream(client.getOutputStream());
                    // Leer mensaje completo (ejemplo: 5,3,1)
                    //String recibido = fromClient.readLine();
                    //System.out.println("Mensaje recibido: " + recibido);
                    // Llamar a la clase externa para procesar
                    //String respuesta = Operaciones.procesarSolicitud(recibido);
                    // Enviar resultado al cliente
                    //toClient.println(respuesta);
                    //client.close();
                    //System.out.println("Cliente desconectado\n");
                } catch (IOException e) {
                    if (client != null) {
                        client.close();
                    }
                    e.getMessage();
                }
            }

        } catch (IOException e) {
            System.out.println("Error en el servidor: " + e.getMessage());
        }
    }
}
