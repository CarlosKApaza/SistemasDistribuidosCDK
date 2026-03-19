/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany._juego_preguntas_respuestas;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author cdk04
 */
public class ServerJuego {

    // Lista para guardar a todos los jugadores conectados
    public static ArrayList<ClientHandlerJuego> jugadores = new ArrayList<>();
    //datos del game 
    // agregamos las preguntas para el game en un contenedor de String llamado preguntas
    public String[] preguntas
            = {
                "Cual es la capital de Bolivia?",
                "Que protocolo garantiza la entrega de mensajes?",
                "Cual es el apellido del Ingeniero de la materia",
                "Cuando es 22 x 5",};
    // las respuestas correctas en el mismo orden (en minusculas para facilitar la validacion )
    public String[] respuestas
            = {
                "sucre",
                "tcp",
                "montellano",
                "110",};

    // Variables de estado del servidor compartidas entre todos los hilos.
    public static int indicePreguntaActual = 0; //Controlara masomenos en que pregunta vamos
    public static boolean juegoActivo = false; //Bandera pa saber si inicio o termino el game

    public static void main(String[] args) {
        int port = 5002;

        try {
            // Instanciamos el ServerSocket para escuchar peticiones TCP en el puerto 
            ServerSocket server = new ServerSocket(port);
            System.out.println("Servidor de Preguntas iniciando en el puerto: " + port);
            System.out.println("Esperando a que los jugadores se conecten...");
            System.out.println("Escribe 'go' y presiona Enter para iniciar el game cuando todos esten listos");

            // hilo administrador - servidor
            // creamoj un hilo independiente donde el servidor lea los comandos de su propia consola
            // sin bloquear el hilo principal que va estar esperando conexiones (server.accept).
            Thread hiloAdmin = new Thread(() -> {
                Scanner sc = new Scanner(System.in);
                while (true) {
                    String comando = sc.nextLine();
                    // si el administrador escribe 'start' y el game no empezo. Arranca la partida
                    if (comando.equalsIgnoreCase("go") && !juegoActivo) {
                        juegoActivo = true;
                        enviarATodos("--------------------------------");
                        enviarATodos("!PARTIDA INICIADA! El primero en responder gana el punto.");
                        enviarATodos("--------------------------------");
                        // enviamos la primera pregunta
                        enviarPreguntaActual();
                    }
                }
            });
            hiloAdmin.start(); // iniciamos el hilo administrador

            // bucle principal de conexiones
            // el servidor queda escuchando infinitamente neuvas conexiones entrantes
            while (true) {
                // esperamos a que alguien se conecte
                Socket client = server.accept();
                System.out.println("Nuevo jugador conectado: " + client);

                // Por cada cliente instaniamos su propio manejador (hilo) pasandole su Socket
                ClientHandlerJuego handler = new ClientHandlerJuego(client);
                jugadores.add(handler); // lo guardamo s en la lista del game
                handler.start(); // iniciamos el hilo para que empiece a escuchar al jugador

            }

        } catch (IOException e) {
            System.out.println("Error critico en el servidor: " + e.getMessage());
        }
    }

    public static synchronized void enviarATodos(String mensaje) {
        for (ClientHandlerJuego jugador : jugadores) {
                
        }
    }
}
