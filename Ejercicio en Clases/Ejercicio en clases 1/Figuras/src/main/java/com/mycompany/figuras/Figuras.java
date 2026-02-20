/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.figuras;

import java.util.ArrayList; // importamos la lista dinamica 
import java.util.Scanner;   // importamos la herramienta para leer datos del teclado

/**
 * Clase principal que gestiona la superficie plana.
 * Aqui aplicamos Polimorgismo: manejamos diferentes objetos a trabes de una interfaz.
 * @author cdk04
 */

public class Figuras {
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); // captura entrada
        // Creamos una lista que acepte solo objetos que cumplan el "compromiso" de IFigura
        ArrayList<IFigura> listaFiguras = new ArrayList<>(); // osea que implementen el metodo sin importar que objetos sean pero que hayan cumplido el metodo de la interface
        int opcion = 0;
        
        
        // bucle para mantener el programa activo hasta eligir 'salir'
        do {
            System.out.println("\n------- SISTEMA DE AREAS ---------");
            System.out.println("1. Agregar Elemento");
            System.out.println("2. Calcular Superficie Total");
            System.out.println("3. Salir");
            System.out.println("\n Seleccione una opcion papu: ");
            
            opcion = sc.nextInt(); // leemos la opcion del menu
            System.out.println("\n----------------------------------");
            
            switch (opcion) {
                case 1:
                    System.out.println("Elija figura: (1. Cuadrado, 2. Rectangulo, 3. Circulo");
                    int tipo = sc.nextInt();
                    
                    if (tipo == 1) {
                        System.out.print("Ingrese lado: ");
                        double lado = sc.nextDouble();
                        
                        // Instanciamos y agregamos directamente a la lista polimórfica
                        listaFiguras.add(new Cuadrado(lado));
                        
                    } else if (tipo == 2) {
                        System.out.print("Ingrese base: ");
                        double base = sc.nextDouble();
                        System.out.print("Ingrese altura: ");
                        double altura = sc.nextDouble();
                        // intanciamos y agregamos a la lista polimorfica
                        listaFiguras.add(new Rectangulo(base, altura));
                        
                    } else if (tipo == 3){
                        System.out.print("Ingrese radio: ");
                        double radio = sc.nextDouble();
                        listaFiguras.add(new Circulo(radio));
                    }
                    
                    System.out.println("\n\t----- Figura agregada con exito. -----");
                    break;
                    
                case 2:
                    double sumaAreas = 0;
                    /* POLIMORFISMO:
                       Recorremos la lista de interfaces. No nos importa si el objeto
                       físico es un Círculo o un Cuadrado; Java llamara al método 
                       'calcularArea' correcto de cada clase en tiempo de ejecución.
                    */
                    
                    for (IFigura figura : listaFiguras) { // transparencia, al bucle no le importa "quien" es el objeto, solo indica que calcule su tarea
                        sumaAreas += figura.calcularArea();
                    }
                    System.out.println("\nSuperficie total de todos los elementos: " + sumaAreas);
                    break;
                    
                case 3: 
                    System.out.println("\n\t---- Saliendo del sistema... ----");
                    break;
                    
                default:
                    System.out.println("\n\t---- Opcio no valida. -----");
                    break;
            }
        } while (opcion !=3); //si el cilo se cumple se cierra el programa
        
        // cerramos el scanner para liberar recursos de memoria por si acaso
        sc.close();
    }
}
