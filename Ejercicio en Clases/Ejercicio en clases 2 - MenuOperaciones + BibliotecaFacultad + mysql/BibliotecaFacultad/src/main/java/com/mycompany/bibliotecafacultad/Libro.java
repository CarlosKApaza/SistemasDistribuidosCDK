/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bibliotecafacultad;

/**
 *
 * @author cdk04
 */
public class Libro implements IPublicacion {
    private String nombre, autor, editorial;
    private int anio;

    public Libro(String nombre, String autor, String editorial, int anio) {
        this.nombre = nombre;
        this.autor = autor;
        this.editorial = editorial;
        this.anio = anio;
    }
    
    @Override 
    public String getDetalles()  {
        return "[LIBRO]" + nombre + " | Autor: " + autor + " | Ed: " + editorial + "(" + anio + ")";
    }
    @Override
    public String getNombre() {
        return nombre;
    }
}
