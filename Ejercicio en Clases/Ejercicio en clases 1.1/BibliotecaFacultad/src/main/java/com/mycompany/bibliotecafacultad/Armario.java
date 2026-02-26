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
public class Armario {
    private String codigo;
    private MaterialArmario material;
    private ArrayList<IPublicacion> publicaciones;

    public Armario(String codigo, MaterialArmario material) {
        this.codigo = codigo;
        this.material = material;
        this.publicaciones = new ArrayList<>();
    }
    
    public void cargarPublicaciones(IPublicacion p) {
        publicaciones.add(p);
    }
    
    public void listarContenido(){
        System.out.println(" -> Armario [" + codigo + "] Material: " + material);
        for(IPublicacion p : publicaciones){
            System.out.println("        " + p.getDetalles());
        }
    }
    public String getCodigo(){
        return codigo;
    }
    
}
