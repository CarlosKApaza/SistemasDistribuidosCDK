/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.practica2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;
import java.util.Map;

/**
 * Servidor UDP del Banco BCP. UDP no es orientado a conexión. Recibe un
 * paquete, lo procesa y responde inmediatamente en el mismo hilo. No usamos
 * serverSocket, ni socket, ni handler
 *
 * @author cdk04
 */
public class ServerBCP {

    // atributos global 
    int port = 5002;
    private Map<String, String> baseDatosCuentas; // referencia 

    public ServerBCP() {
        this.baseDatosCuentas = new HashMap<>(); // HashMap para buscar, insertar o eliminar de Map
        //prueba
        this.baseDatosCuentas.put("11021654", "657654-6000.0"); // CI --->cuenta-saldo
    }

    /**
     * Sección crítica: Busca el CI en el mapa de forma segura.
     *
     * @param ci Cédula de identidad a buscar.
     * @return Cadena con formato cuenta-saldo o vacío si no existe.
     */
    private synchronized String buscarCuenta(String ci) {
        return baseDatosCuentas.getOrDefault(ci, "");
    }

    public void iniciar() {
        // Abrimos el DatagramSocket en el puerto 5002 
        // reservamos esta puerta socket para recibir y enviar paquetes UDP
        try (DatagramSocket socket = new DatagramSocket(port)) {
            System.out.println("[Banco BCP] Servidor UPD iniciando en el puerto " + port);

            // Buffer o espacio de memorio temporal de 1024 bytes que llegan de la red
            byte[] buffer = new byte[1024];

            while (true) {
                try {
                    // instanciamos packetIn vacío para que el socket deposite los datos recibidos.
                    DatagramPacket packetIn = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packetIn);

                    // convertimos bytes a string
                    String recibido = new String(packetIn.getData(), 0, packetIn.getLength());
                    System.out.println("Solicitud UDP recibida: " + recibido);

                    // procesamos el formato Operacion:ci"
                    String[] partes = recibido.split(":");
                    String respuesta = "";

                    if (partes.length == 2 && partes[0].equalsIgnoreCase("Buscar")) {
                        respuesta = buscarCuenta(partes[1]);
                    }

                    // enviamos el resultado --> cuenta-saldo
                    byte[] dataOut = respuesta.getBytes();
                    // creamos el paquete 
                    DatagramPacket packetOut = new DatagramPacket(dataOut, dataOut.length, packetIn.getAddress(), packetIn.getPort());

                    socket.send(packetOut);
                    System.err.println("Respuesta enviada a Justicia: " + (respuesta.isEmpty() ? "(Vacío)" : respuesta));

                } catch (IOException e) {
                    // capturamos errores de paquetes individuales sin tumbar el servidor
                    System.err.println("Error procesando datagrama: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error en el Socket UDP: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // iniciamos el servidor
        ServerBCP serverbcp = new ServerBCP();
        serverbcp.iniciar();
    }
}
