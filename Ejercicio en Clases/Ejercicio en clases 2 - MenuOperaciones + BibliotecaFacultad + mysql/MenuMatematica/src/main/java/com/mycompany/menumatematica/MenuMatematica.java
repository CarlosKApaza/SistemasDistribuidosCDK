/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.menumatematica;

import java.util.Scanner;

/**
 *
 * @author cdk04
 */
public class MenuMatematica {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // Iniciamos con un valor por defecto (0 por ejemplo)
        OperacionesMatematicas motor = new OperacionesMatematicas(0);
        int opcion;
        
        do {
            System.out.println("\n\t------ MENU MATEMATICO -----");
            System.out.println("1. Introducir n: ");
            System.out.println("2. Calcular Fibonacci");
            System.out.println("3. Calcular Factorial");
            System.out.println("4. Calcular Sumatoria");
            System.out.println("5. Salir");
          
            opcion = sc.nextInt();
             System.out.println("\n\t----------------------------");
            
            switch (opcion){
                case 1:
                    System.out.print("Ingrese el valor de n: ");
                    motor.setN(sc.nextInt());
                    System.out.println("Valor de n actualizado");
                    break;
                case 2:
                    System.out.println("Fibonacci: " + motor.calcularFibonacci());
                    break;
                case 3:
                    System.out.println("Factorial: " + motor.calcularFactorial());
                    break;
                case 4:
                    System.out.println("Sumatoria: " + motor.calcularSumatoria());
                    break;
            }
        } while (opcion !=5);
        
        System.out.println("Finalizando programa :D");
        sc.close();
    }
}
