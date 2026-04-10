/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkg1erparcial_becas;

import java.rmi.Naming;
import java.util.Scanner;

/**
 *
 * @author cdk04
 */
public class ClienteUniversitario {
    public static void main(String[] args){
        try{
            // invocamos el metodo solicitar beca y levantamos el servidorUniversitario
            IUniversitario servidor = (IUniversitario) Naming.lookup("rmi://localhost:1099/ServidorUniversitario"); // levantamos el servidor
            Scanner sc = new Scanner(System.in);
            
            System.out.println("\n=== Sistema de Becas Alimentarias === ");
            System.out.print("Ingrese CI: ");
            String ci = sc.nextLine();
            System.out.print("Ingrese Nombres: ");
            String nombres = sc.nextLine();
            System.out.print("Ingrese Apellidos: ");
            String apellidos = sc.nextLine();
            
            System.out.println("\nEnviando solicitud al Servidor Universitario (Orquestador)...");
            RespuestaBeca respuesta = servidor.SolicitarBeca(ci, nombres, apellidos);
            
            System.out.println("\n--- RESULTADO DE LA BECA ---"); // el servidor verifica
            System.out.println(respuesta.toString()); // y obtengo los datos del metodo SolicitarBeca
            
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
