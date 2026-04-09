/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ejercicio_en_clases6;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author cdk04
 */
public class TemaVotacion {
    private String pregunta;
    private Map<String, Integer>  resultados;

    public TemaVotacion(String pregunta) {
        this.pregunta = pregunta;
        this.resultados = new HashMap<>();
    }

    // Getters para leer los datos 
    public String getPregunta() {
        return pregunta;
    }
    
    public Map<String, Integer> getResultados() {
        return resultados;
    }

    
}
