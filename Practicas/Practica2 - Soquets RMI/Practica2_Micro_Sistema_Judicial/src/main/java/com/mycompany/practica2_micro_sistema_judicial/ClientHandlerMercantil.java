/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.practica2_micro_sistema_judicial;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;

/**
 * Hilo independiente para atender a Justicia sin bloquear el Servidor TCP
 * @author cdk04
 */
public class ClientHandlerMercantil extends Thread {
    //atributos
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;
    private Map<String, String> baseDatosCuentas; //Referencia a la BD del servidor
    
    // constructor.  recibimos la conexiones y la bd e iniciamos el flujo
    public ClientHandlerMercantil(Socket socket, Map<String, String> baseDatosCuentas) throws  IOException {
        this.socket = socket;
        this.baseDatosCuentas = baseDatosCuentas;
        
        // Preparamos los canales de comunicacion 
       this.dis = new DataInputStream(socket.getInputStream());
       this.dos = new DataOutputStream(socket.getOutputStream());
    }
    
    // evitamos romper el servidor si 2 hilos buscan al mismo tiempo
    private synchronized String buscarCuenta(String ci){
     return baseDatosCuentas.getOrDefault(ci, ""); //retorna vacio si no halla el CI --- cuenta1-saldo1
    }
    
    // ciclo de vida del hilo
    @Override
    public void run(){
        try {
            // leemos el CI (DataInputStream)
            String ci = dis.readUTF(); // datos en bytes del socket a String
            System.out.println("CI recibido en Mercantil (TCP): " + ci);
            
            // buscamos la BD 
            String respuesta = buscarCuenta(ci);
            
            // enviamos la respuesta (DataOutputStream)
            dos.writeUTF(respuesta); // String a bytes y lo mandamos al socket 
            
        }  catch (IOException e){
            System.out.println("Error de entrada (Input) o (Output) en la Comunicacion: " + e.getMessage());
        } finally { // no importa los erroes o éxito, la puerta socket siempre se va a cerrar
            try{
                socket.close();
            } catch (IOException e) {}
        }
    }
}
