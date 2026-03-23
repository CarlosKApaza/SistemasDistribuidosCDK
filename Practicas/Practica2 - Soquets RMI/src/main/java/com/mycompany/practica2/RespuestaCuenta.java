/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.practica2;

import java.io.Serializable; // para los objetos
import java.util.ArrayList;

/**
 *
 * @author cdk04
 */
public class RespuestaCuenta implements Serializable{
    
    private Boolean error; // si fue exitoso o no
    private String mensaje; // para descripcion de errores
    private ArrayList<Cuenta> cuentas;  //objetos cuentas
    
    //public RespuestaCuenta(Boolean error, String mensaje, ArrayList<Cuenta> cuentas) {
    // contructor con valores por defecto
    public RespuestaCuenta() {
       this.cuentas = new ArrayList();
       this.mensaje = ""; 
       this.error = false;
    }
    
    // Getters y Setters
    public Boolean getError() { return error; }
    public void setError(Boolean error) { this.error = error; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public ArrayList<Cuenta> getCuentas() { return cuentas; }
    public void setCuentas(ArrayList<Cuenta> cuentas) { this.cuentas = cuentas; }
}
