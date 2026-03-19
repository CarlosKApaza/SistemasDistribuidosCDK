/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany._sistema_gestion_tareas;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author cdk04
 */
public class ClientHandlerTareas extends Thread {

    private Socket client;
    private DataInputStream dis;
    private DataOutputStream dos;
    private String nombre;

    public ClientHandlerTareas(Socket client) throws IOException {
        this.client = client;
        this.dis = new DataInputStream(client.getInputStream());
        this.dos = new DataOutputStream(client.getOutputStream());
    }

    public void enviarMensaje(String mensaje) {
        try {
            dos.writeUTF(mensaje);
        } catch (IOException e) {
            System.out.println("Error enviando mensaje: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            this.nombre = dis.readUTF();
            System.out.println(this.nombre + " ha ingresado al sistema.");
            enviarMensaje("Bienvenido al Gestor de Tareas, " + this.nombre);
            
            ServerTareas.actualizarListaATodos();

            while (true) {
                String comando = dis.readUTF(); 
                
                if (comando.startsWith("ADD:")) {
                    String tarea = comando.substring(4); 
                    ServerTareas.agregarTarea(tarea, this.nombre);
                    
                } else if (comando.startsWith("DEL:")) {
                    try {
                        int indice = Integer.parseInt(comando.substring(4)); 
                        ServerTareas.eliminarTarea(indice, this.nombre);
                    } catch (NumberFormatException e) {
                        enviarMensaje("Error: El indice debe ser un numero.");
                    }
                    
                // Atrapamos la petición de listar
                } else if (comando.startsWith("LIST:")) {
                    ServerTareas.enviarListaACliente(this);
                }
            }

        } catch (IOException e) {
            System.out.println(this.nombre + " se ha desconectado.");
            ServerTareas.removerCliente(this);
            try { client.close(); } catch (IOException ex) {}
        }
    }
}