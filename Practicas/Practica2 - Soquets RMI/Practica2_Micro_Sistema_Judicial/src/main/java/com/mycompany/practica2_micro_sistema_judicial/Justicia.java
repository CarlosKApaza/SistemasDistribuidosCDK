/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.practica2_micro_sistema_judicial;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Servidor que registra el objeto Justicia en RMI. 
 * 
 * Lógica del Gateway.
 * Implementa los métodos del Juez y consulta a los bancos.
 *
 * @author cdk04
 */
public class Justicia extends UnicastRemoteObject implements IJusticia {    
    // constructor obligatorio que lanza RemoteException
    public Justicia() throws RemoteException {
        super();
    }
    
    @Override
    public RespuestaCuenta ConsultarCuentas(String ci, String nombres, String apellidos) throws RemoteException {
        System.out.println("\n[JUEZ] Solicita consulta para CI: " + ci);
        RespuestaCuenta respuesta = new RespuestaCuenta();
        
        try {
            // 1. Preguntamos al Banco Mercantil (Actuando como Cliente TCP)
            String resMercantil = preguntarMercantilTCP(ci);
            if (!resMercantil.isEmpty()) {
                String[] partes = resMercantil.split("-"); // Formato esperado: cuenta-saldo
                Cuenta c = new Cuenta(Banco.MERCANTIL, partes[0], ci, nombres, apellidos, Double.valueOf(partes[1]));
                respuesta.addCuenta(c);
            }

            // 2. Preguntamos al Banco BCP (Actuando como Cliente UDP)
            String resBCP = preguntarBCPUDP(ci);
            if (!resBCP.isEmpty()) {
                String[] partes = resBCP.split("-"); // Formato esperado: cuenta-saldo
                Cuenta c = new Cuenta(Banco.BCP, partes[0], ci, nombres, apellidos, Double.valueOf(partes[1]));
                respuesta.addCuenta(c);
            }

            // Si llegamos aquí, la operación de red fue exitosa
            respuesta.setError(false);
            respuesta.setMensaje("Busqueda finalizada en todos los bancos conectados.");

        } catch (Exception e) {
            // Avisamos al Juez sin colapsar el Gateway RMI
            respuesta.setError(true);
            respuesta.setMensaje("Error de conexión con uno o más bancos: " + e.getMessage());
        }

        return respuesta;
    }
    @Override
    public Boolean Congelar(Cuenta cuenta, Float monto) throws RemoteException {
        System.out.println("Orden de congelamiento para la cuenta: " + cuenta.getNrocuenta());
        return true; // simulacion 
}
// --- MÉTODOS PRIVADOS DE RED ---

    private String preguntarMercantilTCP(String ci) throws IOException {
        String resultado = "";
        // Try-with-resources: Cierra los flujos automáticamente
        try (Socket socket = new Socket("localhost", 5001);
             DataInputStream dis = new DataInputStream(socket.getInputStream());
             DataOutputStream dos = new DataOutputStream(socket.getOutputStream())) {
            
            dos.writeUTF(ci); // Mandamos el CI crudo
            resultado = dis.readUTF(); // Recibimos la respuesta
        } catch (IOException e) {
            System.err.println("Banco Mercantil no responde: " + e.getMessage());
        }
        return resultado;
    }

    private String preguntarBCPUDP(String ci) throws IOException {
        String resultado = "";
        try (DatagramSocket socket = new DatagramSocket()) {
            String mensaje = "Buscar:" + ci; // Formato estricto del PDF
            byte[] bufferSalida = mensaje.getBytes();
            InetAddress ip = InetAddress.getByName("localhost");
            
            // Paquete de ida
            DatagramPacket packetOut = new DatagramPacket(bufferSalida, bufferSalida.length, ip, 5002);
            socket.send(packetOut);

            // Paquete de vuelta
            byte[] bufferEntrada = new byte[1024];
            DatagramPacket packetIn = new DatagramPacket(bufferEntrada, bufferEntrada.length);
            socket.receive(packetIn);
            
            resultado = new String(packetIn.getData(), 0, packetIn.getLength());
        } catch (IOException e) {
            System.err.println("Banco BCP no responde: " + e.getMessage());
        }
        return resultado;
    }
}