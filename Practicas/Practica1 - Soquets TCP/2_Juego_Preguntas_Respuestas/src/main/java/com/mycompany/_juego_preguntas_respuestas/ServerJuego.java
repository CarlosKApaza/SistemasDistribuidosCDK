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

//import com.mycompany._juego_preguntas_respuestas.ClientHandlerJuego;
/**
 *
 * @author cdk04
 */
public class ServerJuego {

    // Lista o contenedor para guardar a todos los jugadores conectados
    private static ArrayList<ClientHandlerJuego> jugadores = new ArrayList<>();
    //datos del game 
    // agregamos las preguntas para el game en un contenedor de String llamado preguntas
    private static String[] preguntas = {
        "Cual es la capital de Bolivia?",
        "Que protocolo garantiza la entrega de mensajes?",
        "Cual es el apellido del Ingeniero de la materia",
        "Cuanto es 22 x 5",};

    // las respuestas correctas en el mismo orden (en minusculas para facilitar la validacion )
    private static String[] respuestas = {
        "sucre",
        "tcp",
        "montellano",
        "110",};

    // Variables de estado del servidor compartidas entre todos los hilos.
    private static int indicePreguntaActual = 0;  //Controlara masomenos en que pregunta vamos
    private static boolean juegoActivo = false;  //Bandera pa saber si inicio o termino el game

    public static void main(String[] args) {
        int port = 5002;

        try {
            // Instanciamos el ServerSocket para escuchar peticiones TCP en el puerto 
            ServerSocket server = new ServerSocket(port);
            System.out.println("Servidor de Preguntas iniciando en el puerto: " + port);
            System.out.println("Esperando a que los jugadores se conecten...");
            System.out.println("Escribe 'go' y presiona Enter para iniciar el game cuando todos esten listos");

            // hilo administrador - servidor
            // creamos un hilo independiente donde el servidor lea los comandos de su propia consola
            // sin bloquear el hilo principal que va estar esperando conexiones (server.accept).
            Thread hiloAdmin = new Thread(() -> {
                Scanner sc = new Scanner(System.in);
                while (true) {
                    String comando = sc.nextLine();
                    // si el administrador escribe 'go' y el game no empezo. Arranca el game
                    if (comando.equalsIgnoreCase("go") && !juegoActivo) {
                        juegoActivo = true;
                        enviarATodos("--------------------------------");
                        enviarATodos("!PARTIDA INICIADA! El primero en responder gana el punto.");
                        enviarATodos("--------------------------------");
                        // enviamos la primera pregunta a todos los clientes
                        enviarPreguntaActual();
                    }
                }
            });
            hiloAdmin.start(); // iniciamos el hilo administrador

            // BUCLE PRINCIPAL DE CONEXIONES
            // el servidor queda escuchando infinitamente neuvas conexiones entrantes
            while (true) {
                // esperamos a que alguien se conecte a nuestro servidor
                Socket client = server.accept();
                System.out.println("Nuevo jugador conectado: " + client); // toString para obtener todos los datos de client que Socket nos proporciona

                // como el constructor de ClientHandlerJuego lanza IOEXception, aqui usamos try-catch
                try {
                    // Por cada cliente instaniamos su propio hilo pasandole su Socket. necesitamos en el cosntructor un Socket client
                    ClientHandlerJuego handler = new ClientHandlerJuego(client);
                    jugadores.add(handler); // lo guardamo s en la lista del game
                    handler.start(); // iniciamos el hilo para que empiece a escuchar al jugador 
                } catch (IOException e) {
                    System.out.println("No se pudo inicializar al jugador: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Error critico en el servidor: " + e.getMessage());
        }
    }

    // --- Metodos La Logica Del Juego ---
    // enviar mensaje a todos los clientes (Broadcast)
    public static synchronized void enviarATodos(String mensaje) {
        for (ClientHandlerJuego jugador : jugadores) {
            jugador.enviarMensaje(mensaje); //Enviamos el mensaje a cada hilo
        }
    }

    // evalua en que parte estamos en el juego y lanza las preguntas
    public static synchronized void enviarPreguntaActual() {
        if (indicePreguntaActual < preguntas.length) {
            enviarATodos("PREGUNTA: " + preguntas[indicePreguntaActual]);
        } else {
            juegoActivo = false;
            enviarATodos("--------------------------------");
            enviarATodos("!EL JUEGO HA TERMINADO! Gracias por jugar pe.");
            mostrarMarcadorFinal();
        }
    }

    // Seccion critica = ARBITRO
    // 'synchronized' garantiza que si dos jugadores responden al mismo tiempo, se evaluen uno por uno.
    public static synchronized void verificarRespuesta(String respuesta, ClientHandlerJuego jugador) {
        // si el juego no esta activo, ignoramos cualquier texto que manden
        if (!juegoActivo) {
            return;
        }
        
        String respuestaCorrecta = respuestas[indicePreguntaActual]; // guardamos las respuestas correctas

        // comrpobamos  lo que escribio el jugador (sin espacios extra) con la respuesta correcta
        if (respuesta.trim().equalsIgnoreCase(respuestaCorrecta)) {
            jugador.sumarPunto(); // le damos el punto al jugador más rápido

            enviarATodos("!CORRECTO! " + jugador.getNombre() + " acerto la respuesta ('" + respuestaCorrecta + "')");
            enviarATodos("Puntaje actual de " + jugador.getNombre() + ": " + jugador.getPuntos() + " puntos.\n"); // salto de linea

            indicePreguntaActual++; // pasamos a la siguiente pregunta
           // pausamos 3 segundos para que los jugadores lean quien gano antes de la siguiente pregunta
            try{ Thread.sleep(3000); } catch (InterruptedException e) {}
            enviarPreguntaActual(); // Lanza la siguiente pregunta
        }
    }

    // imprime un total de puntos de todos los jugadores de la lista
    public static synchronized void mostrarMarcadorFinal() {
        enviarATodos("--- MARCADOR FINAL ----");
        for (ClientHandlerJuego j : jugadores) {
            enviarATodos(j.getNombre() + ": " + j.getPuntos() + " puntos");
        }
        enviarATodos("--------------------------------");
    }

    // metodo para que el Handler se elimine al desconectarse sin romper el encapsulamiento
    public static synchronized void removerJugador(ClientHandlerJuego jugador) {
        jugadores.remove(jugador);
    }
}
