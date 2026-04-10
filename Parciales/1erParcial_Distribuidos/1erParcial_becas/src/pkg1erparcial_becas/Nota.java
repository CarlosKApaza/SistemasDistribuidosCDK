/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkg1erparcial_becas;

import java.io.Serializable;

/**
 *  Viajara por RMI entonces uso Serializable para empaquetar 
 * @author cdk04
 */
public class Nota implements Serializable{
    private String materia; 
    private int calificacion;

    public Nota(String materia, int calificacion) {
        this.materia = materia;
        this.calificacion = calificacion;
    }
    
    public String getMateria(){
        return materia;
    }
    
    public int getCalificacion(){
        return calificacion;
    }
    
}
