/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tarea2_tictactoe;

import java.io.Serializable;
/**
 * para la transferencia de datos
 * Flujo -> viaja por la red mediante RMI al imprementarse Seriablizable y eso permite:
 * Marshalling (aplanamiento de bytes) y Unmarshalling en el Cliente a través del canal RMI.
 * 
 * @author cdk04
 */
public class EstadoTablero implements Serializable{
    
    private char[][] matriz;
    private String mensajeEstado;
    protected Boolean juegoTerminado;

    // constructor vacio -> iniciamos con valores definidos ya para cuando instanciemos un objeto 
    // antes de ir por la red
    public EstadoTablero() {
        this.matriz = new char[3][3]; 
        this.mensajeEstado = "";
        this.juegoTerminado = false;
    }
    
    // constructor completo -> va ser usado por el servidor para empaquetar el estado del juego y pasarlo por la red (motorJuego)
    public EstadoTablero(char[][] matriz, String mensajeEstado, Boolean juegoTerminado) {
        this.matriz = matriz;
        this.mensajeEstado = mensajeEstado;
        this.juegoTerminado = juegoTerminado;
    }
    
    // geters y seters
    public char[][] getMatriz() { return matriz; }
    public void setMatriz(char[][] matriz) { this.matriz = matriz; }

    public String getMensajeEstado() {
        return mensajeEstado;
    }
    public void setMensajeEstado(String mensajeEstado) {
        this.mensajeEstado = mensajeEstado;
    }

    public Boolean getJuegoTerminado() {
        return juegoTerminado;
    }
    public void setJuegoTerminado(Boolean juegoTerminado) {
        this.juegoTerminado = juegoTerminado;
    }
    
}
