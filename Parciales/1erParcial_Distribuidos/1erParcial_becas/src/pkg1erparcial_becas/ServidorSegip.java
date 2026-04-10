/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkg1erparcial_becas;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * COMUNICACION TCP SERVIDOR TCP
 * @author cdk04
 */
public class ServidorSegip {
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(5000)) {
            System.out.println("[SEGIP TCP] Escuchando en puerto 5000...");
            while (true) {
                try (Socket cliente = server.accept();
                     DataInputStream in = new DataInputStream(cliente.getInputStream());
                     DataOutputStream out = new DataOutputStream(cliente.getOutputStream())) {
                    
                    String peticion = in.readUTF(); // verificar:ci-nombres-apellidos
                    // Caso de prueba quemado para Ana Gomez
                    if (peticion.equals("verificar:1234567-Ana-Gomez")) {
                        out.writeUTF("resultado:encontrado");
                    } else {
                        out.writeUTF("resultado:no-encontrado");
                    }
                }
            }
        } catch (IOException e) { e.printStackTrace(); }
    }
}
