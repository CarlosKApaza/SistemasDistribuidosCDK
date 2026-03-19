/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany._juego_preguntas_respuestas;

import java.io.DataInputStream;  // Lectura(entrada), para recibir datos crudos y leer lo que viene del cliente  o servidor
import java.io.DataOutputStream; // Escritura (salida), sirve para enviar datos y mandar info al cliente o servidor
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author cdk04
 */
public class ClientHandlerJuego extends Thread {

    private Socket client; // la conexion del cliente
    private DataInputStream dis; // para leer lo que el cliente envia
    private DataOutputStream dos; // para enviar mensajes al cliente

    private String nombre; // nombre del jugador (lo enviamos al conectarse)
    private int puntos;

    // Constructor: recibe el Socket del cliente y crea los flujos de comunicación (entrada y salida)
    // a partir de él. No se reciben dis/dos por parámetro porque estos dependen directamente del Socket
    // y esta clase es responsable de inicializarlos correctamente.
    public ClientHandlerJuego(Socket client) throws IOException {
        this.client = client;
        this.puntos = 0; // inicia en el punto 0;

        // inicializando los flujos de comunicacion 
        // se instancian los objetos InputStream y OutPut para traducir los bytes crudeos del socket a texto (UTF)
        this.dis = new DataInputStream(client.getInputStream());  // recibe datos crudos (0 y 1 del socket) y los convierte en datos utiles ejemplito String msg = dis.readUTF();
        this.dos = new DataOutputStream(client.getOutputStream());  // toma los datos utiles, los convierte en bytes y los manda por el socket. ejemplito dos.writeUTF("Hola");
    }

    // getters
    public String getNombre() {
        return nombre;
    }

    public int getPuntos() {
        return puntos;
    }

    // sumar punto al jugador (llamando por el servidor cuando acierta)
    public void sumarPunto() {
        this.puntos++;
    }

    // enviar mensaje a la consola de este cliente especifico
    public void enviarMensaje(String mensaje) {
        try {
            dos.writeUTF(mensaje);  // convertimos en bytes y mandamos el mensaje por el socket
        } catch (IOException e) {
            System.out.println("Erro al enviar mensaje a " + nombre + ": " + e.getMessage());
        }
    }

    // Ciclo de vida del hilo que corre en paralelo
    @Override
    public void run() {
        try {
            // ==== FASE DE REGISTRO ====
            // Bloque el hilo esperando el primer mensaje, que será obligatoriamente el nombre
            this.nombre = dis.readUTF(); // lee el nombre crudo e iguala a string nombre
            System.out.println("Jugador conectado: " + nombre);

            enviarMensaje("Bienvenido " + nombre);
            enviarMensaje("Esperando inicio del juego...");

            // ==== FASE DE JUEGO ====
            // bucle infinito escuchando constantemente el teclado del jugador
            while (true) {
                String respuesta = dis.readUTF(); // recibe los 0 y 1 del Socket y los lee y convierte en String
                System.out.println("[" + nombre + "] respondio: " + respuesta);

                // delega la valdacion al servidor principal pasando la respuesta y su propia refencia (this)
                ServerJuego.verificarRespuesta(respuesta, this);
            }

        } catch (IOException e) {
            // se ejecuta si el cliente cierra la terminal bruscamente
            System.out.println("Jugador deconectado: " + nombre);

            // se elimina de la lista de jugadores usando el metodo seguro (encapsulamiento)
            ServerJuego.removerJugador(this);

            try {
                // libera el puerto y los recuersos del sistema
                client.close();
            } catch (IOException ex) {
                System.out.println("Error cerrando el socket " + ex.getMessage());
            }
        }
    }

}
