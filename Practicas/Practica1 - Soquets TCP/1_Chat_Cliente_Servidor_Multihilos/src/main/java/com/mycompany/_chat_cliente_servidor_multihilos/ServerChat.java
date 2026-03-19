/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany._chat_cliente_servidor_multihilos;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author cdk04
 */
public class ServerChat {
    
    // Una lista para guardar todos los hilos de los clientes conectados
    public static ArrayList<ClientHandlerChat> clientesConectados = new ArrayList<>();
    
    public static void main(String[] args){
        int port = 5002;
        
        try{
            ServerSocket server = new ServerSocket(port);
            System.out.println("Servidor de chat iniciado en el puerto: " + port);
            
            while (true){
                // esperamos a que alguien se conecte
                Socket client = server.accept();
                System.out.println("Nuevo usuario conectado: " + client);
                
                //DataInputStream = new DataInputStream(client.getInputStream);
                //DataOutputStream = new DataOutputStream(client.getOutputStream);
                
                
                // creamos el manejador (hilo) para este cliente especifico
                //ClientHandlerChat handler = new ClientHandlerChat(client, dis, dos);
                ClientHandlerChat handler = new ClientHandlerChat(client);
                
                // agregamos este cliente a nuestra lista global
                clientesConectados.add(handler);
                
                // iniciamos el hilo para que empieze a escuchar a este cliente
                handler.start();
            }
        } catch (IOException e){
            System.out.println("Error en el servidor: " + e.getMessage());
        }
    }
}
