/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkg1erparcial_becas;


import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 *  ServidorFinanciero ---> UPD
 * @author cdk04
 */
public class ServidorFinaciero {
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(6000)) {
            System.out.println("[FINANCIERO UDP] Escuchando en puerto 6000...");
            byte[] buffer = new byte[1024];
            while (true) {
                DatagramPacket peticion = new DatagramPacket(buffer, buffer.length);
                socket.receive(peticion);
                String datos = new String(peticion.getData(), 0, peticion.getLength());
                
                String respuesta = ""; 
                // deuda:ci
                if (datos.equals("deuda:1234567")) {
                    respuesta = ""; // Caso prueba: cadena vacía = sin deuda
                } else {
                    respuesta = "500"; // Si es otro CI, tiene deuda de 500
                }

                byte[] dataOut = respuesta.getBytes();
                DatagramPacket response = new DatagramPacket(dataOut, dataOut.length, peticion.getAddress(), peticion.getPort());
                socket.send(response);
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
}
