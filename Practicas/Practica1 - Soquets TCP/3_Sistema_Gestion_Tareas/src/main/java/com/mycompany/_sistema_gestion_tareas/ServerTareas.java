/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany._sistema_gestion_tareas;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author cdk04
 */
public class ServerTareas {

    private static ArrayList<ClientHandlerTareas> clientes = new ArrayList<>();
    private static ArrayList<String> listaTareas = new ArrayList<>(); 

    public static void main(String[] args) {
        int port = 5003; 

        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("Servidor de Tareas iniciado en el puerto: " + port);
            System.out.println("Esperando clientes...");

            while (true) {
                Socket client = server.accept();
                System.out.println("Nuevo cliente conectado: " + client);

                try {
                    ClientHandlerTareas handler = new ClientHandlerTareas(client);
                    clientes.add(handler);
                    handler.start();
                } catch (IOException e) {
                    System.out.println("No se pudo conectar al cliente: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Error critico en el servidor: " + e.getMessage());
        }
    }

    // --- MÉTODOS SINCRONIZADOS ---

    public static synchronized void removerCliente(ClientHandlerTareas cliente) {
        clientes.remove(cliente);
    }

    public static synchronized void enviarATodos(String mensaje) {
        for (ClientHandlerTareas cliente : clientes) {
            cliente.enviarMensaje(mensaje);
        }
    }

    // Actualiza a todos cuando hay un cambio
    public static synchronized void actualizarListaATodos() {
        enviarATodos("\n--- LISTA DE TAREAS ACTUALIZADA ---");
        if (listaTareas.isEmpty()) {
            enviarATodos("La lista esta vacia. ¡Todo limpio!");
        } else {
            for (int i = 0; i < listaTareas.size(); i++) {
                enviarATodos("[" + i + "] " + listaTareas.get(i));
            }
        }
        enviarATodos("-----------------------------------\n");
    }

    // Envía la lista a un solo cliente cuando este lo solicita
    public static synchronized void enviarListaACliente(ClientHandlerTareas cliente) {
        cliente.enviarMensaje("\n--- LISTA DE TAREAS (SOLICITADA) ---");
        if (listaTareas.isEmpty()) {
            cliente.enviarMensaje("La lista esta vacia.");
        } else {
            for (int i = 0; i < listaTareas.size(); i++) {
                cliente.enviarMensaje("[" + i + "] " + listaTareas.get(i));
            }
        }
        cliente.enviarMensaje("------------------------------------\n");
    }

    public static synchronized void agregarTarea(String tarea, String nombreAutor) {
        listaTareas.add(tarea);
        enviarATodos(">> [SISTEMA] '" + nombreAutor + "' agrego una tarea.");
        actualizarListaATodos(); 
    }

    public static synchronized void eliminarTarea(int indice, String nombreAutor) {
        if (indice >= 0 && indice < listaTareas.size()) {
            String tareaEliminada = listaTareas.remove(indice);
            enviarATodos(">> [SISTEMA] '" + nombreAutor + "' elimino la tarea: " + tareaEliminada);
            actualizarListaATodos(); 
        } else {
            enviarATodos(">> [ERROR para " + nombreAutor + "] Indice invalido.");
        }
    }
}