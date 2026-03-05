/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.clientoperacion;

/**
 *
 * @author cdk04
 */
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClientOperacion {

    public static void main(String[] args) {
        
        String host = "localhost";
        int port = 5002;

        try {Socket client = new Socket(host, port);
        
            // los bytes se interpretan directamente como datos con DataInputStream (HUMMM SOLO CAMBIAMOS ESTO)
            DataInputStream dis = new DataInputStream(client.getInputStream());
            DataOutputStream dos = new DataOutputStream(client.getOutputStream());
            
            // esto es para manejo de caracteres a bytes para lo del tcp
            //BufferedReader fromServer = new BufferedReader(new InputStreamReader(client.getInputStream()));
            //PrintStream toServer = new PrintStream(client.getOutputStream());
            
            Scanner sc = new Scanner(System.in);{
            
            System.out.print("Ingrese primer numero: ");
            String n1 = sc.nextLine();

            System.out.print("Ingrese segundo numero: ");
            String n2 = sc.nextLine();

            System.out.print("Operacion (1=suma, 2=resta, 3=multi, 4=div): ");
            String op = sc.nextLine();
            
            
            // Enviar todo en una sola linea 
            String mensaje = n1 + "," + n2 + "," + op;
            dos.writeUTF(mensaje); // ese metodo maneja la longitud automaticamente;
            
            //toServer.println(mensaje);
            
            // Leer resultado
            System.out.println(dis.readUTF()); // readUTF() esto reconstruye el String original
               
            // Leer resultado
            //System.out.println(fromServer.readLine());
        }
            
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}