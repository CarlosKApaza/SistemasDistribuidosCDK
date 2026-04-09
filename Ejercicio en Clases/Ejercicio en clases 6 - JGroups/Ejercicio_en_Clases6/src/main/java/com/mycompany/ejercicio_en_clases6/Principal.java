/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ejercicio_en_clases6;

import java.util.Scanner;

/**
 *
 * @author cdk04
 */
public class Principal {
    public static void main(String[] args) {
        System.setProperty("java.net.preferIPv4Stack", "true");
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Selecciona: \n1- Ejercicio 1 - Sincronizacion \n2- Ejercicio 2 - Votacion");
            int opcion = sc.nextInt();
            sc.nextLine(); 
            if (opcion == 1) {
                new StateSyncCluster().start();
            } else if (opcion == 2) {
                new Votacion().start();
            }
        } catch (Exception ex) {
            System.getLogger(Principal.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
}
