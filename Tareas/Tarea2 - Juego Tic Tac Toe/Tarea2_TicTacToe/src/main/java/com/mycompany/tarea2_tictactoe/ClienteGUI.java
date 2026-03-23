/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tarea2_tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Delega las peticiones RMI a un hilo asincronico 
 * buscan objeto remoto en la red mediante Naming.lookup o Registry.lookup y enviarle peticiones al servidor
 * envuelve la llamada RMI dentro de un hilo secundario (new Thread()) 
 * @author cdk04
 */
public class ClienteGUI extends JFrame {
    private IJuegoRMI servicioJuego;
    private JButton[][] botonesTablero;
    private JLabel etiquetaEstado;

    public ClienteGUI() {
        conectarServidor();
        configurarVentana();
    }
    
    private void conectarServidor() {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            servicioJuego = (IJuegoRMI) registry.lookup("TicTacToeService");
        } catch (Exception e){
            JOptionPane.showMessageDialog(this, "Error de conexion al servidor RMI.", "Fallo de Red", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
    
    // motificamos el JFrame
    private void configurarVentana() {
        setTitle("Tic Tac Toe - RMI Client");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        etiquetaEstado = new JLabel("Conectado. Tu turno (X).", SwingConstants.CENTER);
        etiquetaEstado.setFont(new Font("Arial", Font.BOLD, 14));
        add(etiquetaEstado, BorderLayout.NORTH);

        JPanel panelTablero = new JPanel(new GridLayout(3, 3));
        botonesTablero = new JButton[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                botonesTablero[i][j] = new JButton("-");
                botonesTablero[i][j].setFont(new Font("Arial", Font.BOLD, 40));
                int fila = i;
                int col = j;
                
                botonesTablero[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ejecutarMovimientoAsincrono(fila, col);
                    }
                });
                panelTablero.add(botonesTablero[i][j]);
            }
        }
        add(panelTablero, BorderLayout.CENTER);

        JButton btnReiniciar = new JButton("Reiniciar Juego");
        btnReiniciar.addActionListener(e -> reiniciarAsincrono());
        add(btnReiniciar, BorderLayout.SOUTH);
    }
    private void ejecutarMovimientoAsincrono(int fila, int columna) {
        new Thread(() -> {
            try {
                EstadoTablero estado = servicioJuego.jugarTurnoCliente(fila, columna);
                actualizarInterfaz(estado);
            } catch (Exception ex) {
                actualizarEstado("Error al comunicar con el servidor.");
            }
        }).start();
    }

    private void reiniciarAsincrono() {
        new Thread(() -> {
            try {
                EstadoTablero estado = servicioJuego.reiniciarJuego();
                actualizarInterfaz(estado);
            } catch (Exception ex) {
                actualizarEstado("Error al reiniciar.");
            }
        }).start();
    }

    private void actualizarEstado(String mensaje) {
        SwingUtilities.invokeLater(() -> etiquetaEstado.setText(mensaje));
    }

    // PROTECCIÓN DEL EDT (Event Dispatch Thread): Swing no es Thread-Safe.
    // Una vez que el hilo secundario recibe el DTO del tablero,, 'SwingUtilities.invokeLater' 
    // inyecta de forma segura los cambios visuales de vuelta al hilo principal de la GUI.
    private void actualizarInterfaz(EstadoTablero estado) {
        SwingUtilities.invokeLater(() -> {
            char[][] matriz = estado.getMatriz();
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    botonesTablero[i][j].setText(String.valueOf(matriz[i][j]));
                }
            }
            etiquetaEstado.setText(estado.getMensajeEstado());
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ClienteGUI cliente = new ClienteGUI();
            cliente.setVisible(true);
        });
    }
}
