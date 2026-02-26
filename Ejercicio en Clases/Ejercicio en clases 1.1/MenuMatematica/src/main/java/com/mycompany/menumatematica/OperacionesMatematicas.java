/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.menumatematica;

/**
 *
 * @author cdk04
 */
public class OperacionesMatematicas {
    private int n;

    public OperacionesMatematicas(int n) {
        this.n = n;
    }

    public int getN() {
        return n;
    }
    
    // para insertar un numero n
    public void setN(int n) {
        this.n = n;
    }
    
    // Factorial: n!
    public long calcularFactorial(){
        if (n<0) return 0;
        long resultado = 1;
        for (int i=1; i<=n; i++){
            resultado *=i;
        }
        return resultado;
    }
    
    // Fibonacci: F(n) = F(n-1) + F(n-2)
    public long calcularFibonacci(){
        if (n<=1) return n;
        long anterior = 0, actual = 1;
        for(int i = 2; i <= n; i++){
            long siguiente = anterior + actual;
            anterior = actual;
            actual = siguiente;
        }
        return actual;
    }
    
    
    // Sumatoria de los primeros n números
    public int calcularSumatoria() {
        return (n* (n+1)) / 2;
    }
}
