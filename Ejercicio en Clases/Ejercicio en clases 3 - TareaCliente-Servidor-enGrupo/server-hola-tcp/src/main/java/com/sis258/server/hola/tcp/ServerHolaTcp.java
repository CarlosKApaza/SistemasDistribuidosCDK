/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.sis258.server.hola.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerHolaTcp {

    public static void main(String[] args) {
        // Definimos el puerto en el que el servidor estará escuchando
        int port = 5002;

        try {
            //Instanciamos el ServerSocket. Esto "abre" el puerto en tu PC
            ServerSocket server = new ServerSocket(port);
            System.out.println("Servidor iniciado en el puerto " + port);

            // Bucle infinito para que el servidor no se apague tras atender a un cliente
            while (true) { 
                // El hilo de ejecución se detiene aquí (bloqueo) hasta que llega un cliente
                Socket client = server.accept();
                System.out.println("Cliente conectado");

                // Usamos el patrón Decorator para convertir bytes en texto legible (capa de entrada le dicen o lectura)
                BufferedReader fromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
                
                // PrintStream facilita el envío de texto; .println() añade el \n necesario. (capa de salida o escritura)
                PrintStream toClient = new PrintStream(client.getOutputStream());

                // Leemos la cadena enviada por el cliente hasta encontrar un salto de línea
                String recibido = fromClient.readLine();
                System.out.println("Mensaje recibido: " + recibido);

                // INVERTIR CADENA humm aqui usamos StringBuilder para invertir la cadena de forma eficiente
                String invertida = new StringBuilder(recibido).reverse().toString();
                
                // Enviamos el resultado de vuelta al cliente
                toClient.println(invertida);

                // cerramos la conexión con el cliente actual para liberar el socket
                client.close();
                System.out.println("Cliente desconectado\n");
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}