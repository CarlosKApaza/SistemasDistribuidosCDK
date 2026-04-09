/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.practica2_micro_sistema_judicial;


import java.io.Serializable;

/**
 * Representa una cuenta bancaria.
 * Implementa Serializable para poder viajar por RMI.
 * @author cdk04
 */
public class Cuenta implements Serializable{
    // atributos
    private Banco banco;  //enum
    private String nrocuenta;
    private String ci;
    private String nombres;
    private String apellidos;
    private Double saldo;

    // constructor
    public Cuenta(Banco banco, String nrocuenta, String ci, String nombres, String apellidos, Double saldo) {
        this.banco = banco;
        this.nrocuenta = nrocuenta;
        this.ci = ci;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.saldo = saldo;
    }
    
    // Getters para acceder a ellos
    public Banco getBanco() {    
        return banco;
    }
    
    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public String getNrocuenta() {
        return nrocuenta;
    }

    public void setNrocuenta(String nrocuenta) {
        this.nrocuenta = nrocuenta;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Double getSaldo() {
        return saldo;
    }
    
    public void setSaldo(Double saldo) {  
        this.saldo = saldo;
    }

    // Un toString para impresión en el cliente Juez
    @Override
    public String toString() {
        return banco + ": Cuenta " + nrocuenta + ", Saldo " + saldo;
    }
    
}
