/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.practica2_micro_sistema_judicial;

import java.io.Serializable; // para los objetos convertilos en una secuencia de bytes (serializado)
import java.util.ArrayList;

/**
 *
 * @author cdk04
 */
public class RespuestaCuenta implements Serializable {
    // atributos
    private Boolean error; // si fue exitoso o no
    private String mensaje; // para descripcion de errores
    private ArrayList<Cuenta> cuentas;  //objetos tipo cuentas

    // contructor con valores por defecto o inicializados
    public RespuestaCuenta() {
        this.error = false;
        this.mensaje = "Operacion exitosa";
        this.cuentas = new ArrayList<>();
    }
    
    //getters y setters
    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public ArrayList<Cuenta> getCuentas() {
        return cuentas;
    }

    // metodo para agregar cuenta a la lista
    public void addCuenta(Cuenta c){
        this.cuentas.add(c);
    }
}