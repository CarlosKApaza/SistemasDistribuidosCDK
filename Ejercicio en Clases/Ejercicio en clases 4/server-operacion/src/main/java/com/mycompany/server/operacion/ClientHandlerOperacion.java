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

    // Constructor adicional que recibe socket y flujos (estilo ingeniero)
    public ClientHandlerOperacion(Socket client, DataInputStream dis, DataOutputStream dos) {
        this.client = client;
        this.dis = dis;
        this.dos = dos;
    }

    // Constructor que recibira el socket del cliente
    public ClientHandlerOperacion(Socket client) {
        this.client = client;
        try {
            // Crear los flujos de entrrada y salida
            this.dis = new DataInputStream(client.getInputStream());
            this.dos = new DataOutputStream(client.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            // Leer las lineas enviadas por el cliente
            String recibido = dis.readUTF(); //usamos readUTF() porque dataInputStream
            System.out.println("Mensaje recibido: " + recibido);

            // procesar respuesta con la clase operaciones
            String respuesta = Operaciones.procesarSolicitud(recibido);

            // enviamos el resultado
            dos.writeUTF(respuesta);

            // cerrar recursoas
            dis.close();
            dos.close();
            client.close();
            System.out.println("Cliente desconectado: " + client.getInetAddress());

        } catch (IOException e) {
            System.out.println("Error con el cliente" + e.getMessage());
        }
    }
}
