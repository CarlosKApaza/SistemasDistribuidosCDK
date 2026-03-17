/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.practica2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
/**
 *
 * @author cdk04
 */
public class ServerMercantil {
      public static void main(String[] args) {
        int port = 5002;
        ServerSocket server;
        try {
            // Se instancia un ServerSocket
            server = new ServerSocket(port);
            System.out.println("Servidos Banco Mercantil (TCP) iniciando" + port);
            while (true) {
                // El servidor espera un (accept) hasta que Justicia se conecte
                Socket client = server.accept();
                System.out.println("Servidor Justicia conectado.");                
                
                // preparamos los canales de lectura y escritura
                BufferedReader fromClient = new BufferedReader(new InputStreamReader(client.getInputStream())); // el lector
                PrintStream toClient = new PrintStream(client.getOutputStream()));
                
                // Leemos el CI que nos envia justicia
                String recibido = fromClient.readLine();
                System.out.println("CI recibido");
                
                String respuesta = ""; // por defecto si no hay datos para devolver
            
                // Verificamos el caso de prueba obligatorio
                if (recibido != null && recibido.contains("11021654")) {
                    // Formato exigido: cuenta-saldo
                    respuesta = "1515-5200.0"; 
                }
            }
        } catch (IOException ex) {
            System.out.print(ex.getMessage());
        }
    }
}
