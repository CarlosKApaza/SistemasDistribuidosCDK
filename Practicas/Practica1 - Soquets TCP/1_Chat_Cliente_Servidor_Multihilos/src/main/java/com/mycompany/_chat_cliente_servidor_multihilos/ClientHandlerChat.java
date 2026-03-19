/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany._chat_cliente_servidor_multihilos;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author cdk04
 */
public class ClientHandlerChat extends Thread {

    private Socket client;
    private DataInputStream dis;
    private DataOutputStream dos;

    // constructor que recibe socket y flujos
    //public ClientHandlerChat(Socket client, DataInputStream dis, DataOutputStream dos) {
       // this.client = client;
        //this.dis = dis;
      //  this.dos = dos;
    //}
    
    // constructor que delega con throws IOException la responsabilidad a ServerChat
    public ClientHandlerChat(Socket client) throws IOException {
        this.client = client;
        this.dis = new DataInputStream(client.getInputStream());
        this.dos = new DataOutputStream(client.getOutputStream());
    }

    // metodo para enviar un mensaje hacia este cliente
    public void enviarMensaje(String mensaje) {
        try{
            dos.writeUTF(mensaje);
        } catch (IOException e){
            System.out.println("Error al enviar mensaje: " + e.getMessage());
        }
    }

    
        @Override
    public void run() {
    try {
            // bucle infinito para escuchar los mensajes que manda este cliente
            while (true) {
                String mensajeRecibido = dis.readUTF();
                System.out.println("Log del servidor: " + mensajeRecibido);

                // recorremos la lsita de todos los clientes conectados
                for (ClientHandlerChat destinatario : ServerChat.clientesConectados) {
                    // evitamos enviarle el mensaje al mismo cliente que lo escribio
                    if (destinatario != this) { // this para que mis propios mensajes no reboten
                        destinatario.enviarMensaje(mensajeRecibido);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Un cliente se ha desconectado.");
            // lo eliminamos de la lista al desconectarse
            ServerChat.clientesConectados.remove(this);
            try {
                client.close();
            } catch (IOException es) {
                System.out.println("Error cerrando el socket: " + es.getMessage());
            }
        }
    }
}
