/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bibliotecafacultad;
import java.util.ArrayList;

/**
 *
 * @author cdk04
 */
public class Biblioteca {
    private String nombre;
    private double metrosCuadrados;
    private ArrayList<Armario> armarios;

    public Biblioteca(String nombre, double metrosCuadrados) {
        this.nombre = nombre;
        this.metrosCuadrados = metrosCuadrados;
        this.armarios = new ArrayList<>();
    }

    public void agregarArmario(Armario a) {
        armarios.add(a);
    }

    public void mostrarTodo() {
        System.out.println("BIBLIOTECA: " + nombre + " | Superficie: " + metrosCuadrados + "m2");
        for (Armario a : armarios) {
            a.listarContenido();
        }
    }
}
