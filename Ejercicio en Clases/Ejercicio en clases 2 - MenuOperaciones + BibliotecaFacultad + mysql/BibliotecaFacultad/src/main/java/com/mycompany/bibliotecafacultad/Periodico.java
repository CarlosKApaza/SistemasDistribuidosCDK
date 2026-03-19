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
public class Periodico implements IPublicacion {
    private String nombre, fecha;
    private ArrayList<String> suplementos;

    public Periodico(String nombre, String fecha) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.suplementos = new ArrayList<>();
    }
    
    public void agregarSuplemento(String s){
        suplementos.add(s);
    }
    
    @Override
    public String getDetalles(){
        return "[PERIDIOCO] " + nombre + " | Fecha: " + fecha + " | Suplementos: " + suplementos;
    }
    @Override
    public String getNombre() {
        return nombre;
    }
}
