/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany._chat_cliente_servidor_multihilos;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author cdk04
 */
public class ClientChat {

    public static void main(String[] args) {

        String host = "localhost";
        int port = 5002;
        try {
            // conectamos al servidor
            Socket client = new Socket(host, port);

            // bytes interpretados como datos con DataInputStream en entrada y salida DataOutputStream
            DataInputStream dis = new DataInputStream(client.getInputStream());
            DataOutputStream dos = new DataOutputStream(client.getOutputStream());

            Scanner sc = new Scanner(System.in);

            // pedimos un nombre para identificar habla
            System.out.print("Ingresa tu nombre de usuario para el chat: ");
            String nombreUsuario = sc.nextLine();
            System.out.println(" - Conectado - Ahora si puedes empezar a escribir algo...: ");

            // creo un hilo para escuchar (recibir mensajes)
            Thread hiloEscucha = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            // Leemos lo que nos manda el servidor y lo mostramos
                            String mensajeRecibido = dis.readUTF();
                            System.out.println(mensajeRecibido);
                        }
                    } catch (IOException e) {
                        System.out.println("Te has desconectado del servidor.");
                    }
                }
            });
            hiloEscucha.start(); // Iniciamos el hilo que se queda escuchando de fondo

            // hilo principal (leer teclado)
            while (true) {
                String mensaje = sc.nextLine();
                // enviamos el mensaje al servidor con el nombre junto
                dos.writeUTF(nombreUsuario + ": " + mensaje);
            }
        } catch (IOException e) {
            System.out.println("No se pudo conectar al servidor: " + e.getMessage());
        }
    } // fin del main
} // fin de la clase
