/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.server.operacion;

import java.io.*;
import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 *
 * @author cdk04
 */
public class ClientHandlerOperacion extends Thread {

    private Socket client;
    private DataInputStream dis;
    private DataOutputStream dos;

    // Constructor adicional que recibe socket y flujos 
    public ClientHandlerOperacion(Socket client, DataInputStream dis, DataOutputStream dos) {
        this.client = client;
        this.dis = dis;
        this.dos = dos;
    }

    @Override
    public void run() {
        try {
            // Leer las lineas enviadas por el cliente
            String recibido = dis.readUTF(); //usamos readUTF() porque dataInputStream      readUTF() esto reconstruye el String original
            System.out.println("Mensaje recibido: " + recibido);

            // procesar respuesta con la clase operaciones
            String respuesta = Operaciones.procesarSolicitud(recibido);

            // enviamos el resultado
            dos.writeUTF(respuesta); //ese metodo maneja la longitud automaticamente;

            // cerrar recursoas
            dis.close();
            dos.close();
            client.close();
            System.out.println("Cliente desconectado: " + client); // imprimimos el socket porque socket tiene un toString() que muestra la informacion
                                                                   // podria ser client.getInetAddresss() pero eso solo devuelve la IP y ya 

        } catch (IOException e) {
            System.out.println("Error con el cliente" + e.getMessage());
        }
    }
}
