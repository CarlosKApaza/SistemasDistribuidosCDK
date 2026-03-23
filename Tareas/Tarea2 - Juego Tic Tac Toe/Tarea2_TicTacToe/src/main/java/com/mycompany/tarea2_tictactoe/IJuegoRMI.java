/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.tarea2_tictactoe;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Servidor cumple el contrato -> obligado a usar esos metodos
 * Cliente usa el contrato -> solo llamada a ese metodo, no sabe como funcioa el servidor por dentro
 * Flujo -> Definimos el Contrato y todos los metodos lanzaran por si acaso RemoteException en caso a cualquier error
 * o corte de conexion en el socket
 * @author cdk04
 */
public interface IJuegoRMI extends Remote {
    EstadoTablero jugarTurnoCliente(int fila, int columna) throws RemoteException;
    EstadoTablero reiniciarJuego() throws RemoteException;  // devuelve un objeto EstadoTablero
}
