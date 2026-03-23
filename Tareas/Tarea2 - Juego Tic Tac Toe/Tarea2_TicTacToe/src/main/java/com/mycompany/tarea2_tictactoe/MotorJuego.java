/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tarea2_tictactoe;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;

/**
 * este seria el handler - objeto distribuidos central
 * Mantiene el estado del juego en la memoria del servidor.
 * @author cdk04
 */
public class MotorJuego extends UnicastRemoteObject  implements IJuegoRMI{
    private char[][] tablero;
    private boolean terminado;

    public MotorJuego() throws RemoteException{
        super();
        inicializarTablero();
    }
    
    private void inicializarTablero() {
        tablero = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tablero[i][j] = '-';
            }
    }
        terminado = false;
    }
    
    
    // 'synchronized' bloquea el monitor del objeto. 
   // Garantiza que si dos clientes (o dos hilos) intentan reiniciar o jugar al mismo tiempo,
    // se formen en una cola ordenada, evitando la corrupción de la matriz (Race Conditions)
    @Override
    public synchronized EstadoTablero reiniciarJuego() throws RemoteException {
        inicializarTablero();
        return new EstadoTablero(clonarTablero(), "Juego reiniciado. Tu turno (X).", terminado);
    }

    @Override
    public synchronized EstadoTablero jugarTurnoCliente(int fila, int columna) throws RemoteException {
        if (terminado || tablero[fila][columna] != '-') {
            return new EstadoTablero(clonarTablero(), "Movimiento invalido o juego terminado.", terminado);
        }

        // Turno del Cliente (X)
        tablero[fila][columna] = 'X';
        if (verificarGanador('X')) {
            terminado = true;
            return new EstadoTablero(clonarTablero(), "¡Felicidades, ganaste!", terminado);
        }
        if (tableroLleno()) {
            terminado = true;
            return new EstadoTablero(clonarTablero(), "Empate.", terminado);
        }

        // Turno del Servidor (O)
        jugarTurnoServidor();
        if (verificarGanador('O')) {
            terminado = true;
            return new EstadoTablero(clonarTablero(), "El Servidor ha ganado.", terminado);
        }
        if (tableroLleno()) {
            terminado = true;
            return new EstadoTablero(clonarTablero(), "Empate.", terminado);
        }

        return new EstadoTablero(clonarTablero(), "Turno del Servidor completado. Tu turno (X).", terminado);
    }

    private void jugarTurnoServidor() {
        Random rand = new Random();
        int f, c;
        do {
            f = rand.nextInt(3);
            c = rand.nextInt(3);
        } while (tablero[f][c] != '-');
        tablero[f][c] = 'O';
    }

    private boolean verificarGanador(char jugador) {
        for (int i = 0; i < 3; i++) {
            if (tablero[i][0] == jugador && tablero[i][1] == jugador && tablero[i][2] == jugador) return true;
            if (tablero[0][i] == jugador && tablero[1][i] == jugador && tablero[2][i] == jugador) return true;
        }
        if (tablero[0][0] == jugador && tablero[1][1] == jugador && tablero[2][2] == jugador) return true;
        if (tablero[0][2] == jugador && tablero[1][1] == jugador && tablero[2][0] == jugador) return true;
        return false;
    }

    private boolean tableroLleno() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tablero[i][j] == '-') return false;
            }
        }
        return true;
    }

    // ENCAPSULAMIENTO DE RED: Retorna una copia de la matriz. 
    // Esto asegura que la referencia original en la memoria del servidor quede protegida 
    // y no sea serializada directamente.
    private char[][] clonarTablero() {
        char[][] copia = new char[3][3];
        for (int i = 0; i < 3; i++) {
            System.arraycopy(tablero[i], 0, copia[i], 0, 3);
        }
        return copia;
    }
    
}


