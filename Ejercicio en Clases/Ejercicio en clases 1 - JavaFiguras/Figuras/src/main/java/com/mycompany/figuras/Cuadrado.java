/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.figuras;

/**
 *
 * @author cdk04
 */
public class Cuadrado implements IFigura{
       private double lado;

    public Cuadrado(double lado) {
        this.lado = lado;
    }
       
    @Override 
    public double calcularArea() {
        return lado* lado;
    }
}
