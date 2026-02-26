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

        int port = 5002;

        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("Servidor iniciado en el puerto " + port);

            while (true) {  //  para que no se apague

                Socket client = server.accept();
                System.out.println("Cliente conectado");

                BufferedReader fromClient = new BufferedReader(
                        new InputStreamReader(client.getInputStream()));

                PrintStream toClient = new PrintStream(client.getOutputStream());

                String recibido = fromClient.readLine();
                System.out.println("Mensaje recibido: " + recibido);

                // INVERTIR CADENA
                String invertida = new StringBuilder(recibido)
                        .reverse()
                        .toString();

                toClient.println(invertida);

                client.close();
                System.out.println("Cliente desconectado\n");
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}