/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bibliotecafacultad;

/**
 *
 * @author cdk04
 */
public class Revista implements IPublicacion {
    private String nombre;
    private int mes, anio;
    private TipoRevista tipo;

    public Revista(String nombre, int mes, int anio, TipoRevista tipo) {
        this.nombre = nombre;
        this.mes = mes;
        this.anio = anio;
        this.tipo = tipo;
    }
    
    @Override
    public String getDetalles() {
        return "[REVISTA] " + nombre + " | Tipo: " + tipo + " (" + mes + "/" + anio + ")";
    }
    
    @Override
    public String getNombre() {
        return nombre;
    }
}
