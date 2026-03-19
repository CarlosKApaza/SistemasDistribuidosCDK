/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany._juego_preguntas_respuestas;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author cdk04
 */
public class ClientJuego {
    public static void main(String[] args){
        // configuramos a donde vamos a conectar
        String host = "localhost";
        int port = 5002;
        
        try{
            //1. Conexion TCP con el servidor
            // ejecuta server.accept() y nos deja pasar
            Socket client = new Socket(host, port);
            
            //2.  Iniciamos los flujos de comunicación
            DataInputStream dis = new DataInputStream(client.getInputStream()); // para escuchar al servidor
            DataOutputStream dos = new DataOutputStream(client.getOutputStream()); // para hablarle al servidor
            Scanner sc = new Scanner(System.in); // para leer nuestro teclado
            
            // 3. Fase de Registro
            // apenas nos conectamos. el ClientHandler en el servidor esta bloqueado esperando nuestro nombre. 
            // Se lo mandamos al rato
            System.out.println("Ingresa tu nombre para entrar al juego: ");
            String nombreUsuario = sc.nextLine();
            dos.writeUTF(nombreUsuario); // mandamos nuestro nombreUsuario en bytes y se lo mandamos por el socket
            
            // 4. Hilo de Escucha (Recepcion asincrona)
            //creamos el hilo en segundo plano solo para escuchar al servidor
            // si no usamos el hilo, no podriamos  ver las preguntas escribimos una respuesta.
            Thread hiloEscucha = new Thread(() ->{
               try{
                   while(true){
                       // imprimimos todo lo que el servidor mande (Preguntas, quien gano, marcadores, etc.)
                       String mensajeDelServidor = dis.readUTF(); // leemos todos los datos crudos (bytes) y convertimos en String
                       System.out.println(mensajeDelServidor);
                   }
               } catch (IOException e){
                     System.out.println("\n[Desconectando] El servidor cerro el juego o se perdio la conexión.");
               } 
            });
            hiloEscucha.start();
            
            // 5. Bucle Principal (envio de respuestas)
            // Hilo principal (main) se queda atrapado en este bucle infinito
            // Solo leera lo que tecleamos y mandarlo al servidor
            while(true) {
                String respuesta = sc.nextLine();
                // mandamos la respuesta en bytes. El servidor ya sabe quienes somos gracias a la Fase de Registro
                dos.writeUTF(respuesta); 
            }
        } catch (IOException e){
            System.out.println("Error en la conexión: No se encontro un servidor activo en " + host +":" + port);
            System.out.println("Asegurate de encender el ServerJuego primero");
        }
    }
}
