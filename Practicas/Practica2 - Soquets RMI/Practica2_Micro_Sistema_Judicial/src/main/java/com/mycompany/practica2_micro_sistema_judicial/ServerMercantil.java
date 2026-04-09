/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.practica2_micro_sistema_judicial;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Servidor TCP del Banco Mercantil - solo se encargara de acetar conexiones -
 * nodo central
 *
 * @author cdk04
 */
public class ServerMercantil {

    // atributos globales, // Base de datos encapsulada
    int port = 5001;
    private Map<String, String> baseDatosCuentas; // Map guarda datos en forma (clave - valor), private para seguridad de hilos

    // constrcutor iniciado
    public ServerMercantil() {
        this.baseDatosCuentas = new HashMap<>(); // HashMap para buscar, insertar o eliminar de Map
        // para prueba (ci --> cuenta-saldo)
        this.baseDatosCuentas.put("11021654", "1515-5200.0"); // put para agregar o actualizar un dato en Map
    }
    
    // Servidor TCP
    public void iniciar() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
               System.out.println("[Banco Mercantil] Servidor TCP iniciando en el puerto " + port);
               
               while (true){
                   try{
                       // espera la conexion
                       Socket clientSocket = serverSocket.accept();
                       System.out.println("Servidor Justicia conectado: " + clientSocket);
                       
                       // instanciamos el hilo pasandole el socket y la base de datos
                       ClientHandlerMercantil handler = new ClientHandlerMercantil(clientSocket, baseDatosCuentas);
                       handler.start(); // crea el hilo y ejecutamos run();
                       
                   } catch (IOException e){
                       System.err.println("Error al aceptar conexion TCP: " + e.getMessage());
                   }
               }
        } catch (IOException e) {
            System.err.println("Error en el Servidor Mercantil: " + e.getMessage());
        }
    }
    
    public static void main(String[] args){
       ServerMercantil miServidor = new ServerMercantil(); //creamos el objeto
       miServidor.iniciar(); // arrancamos
    }
}
