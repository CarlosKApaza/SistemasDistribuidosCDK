/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ejercicio_en_clases6;

/**
 *
 * @author cdk04
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;

public class Votacion {

    private JChannel channel;
    private List<TemaVotacion> temas = new ArrayList<>();
    //private List<String> preguntas = new ArrayList<>();
    //private List<Map<String, Integer>> resultados = new ArrayList<>();

    public void start() throws Exception {
        channel = new JChannel();
        channel.setReceiver(new ReceiverAdapter() {
            @Override
            public void receive(Message msg) {
                String content = (String) msg.getObject();
                
                if (content.startsWith("PREGUNTA:")) {
                    String q = content.substring(9).trim();
                    //preguntas.add(q);
                    //resultados.add(new HashMap<>());
                    temas.add(new TemaVotacion(q));
                    actualizarPantalla();
                    
                } else if (content.startsWith("VOTO:")) {
                    String[] partes = content.substring(5).split(":", 2);
                    if (partes.length == 2) {
                        try {
                            int indice = Integer.parseInt(partes[0]);
                            String opcion = partes[1].trim().toLowerCase();
                            
                            if (indice >= 0 && indice < temas.size()) {
                                TemaVotacion temaActual = temas.get(indice);
                                // Usamos el Getter para obtener el mapa y sumarle un voto
                                temaActual.getResultados().put(opcion, temaActual.getResultados().getOrDefault(opcion, 0) + 1);
                                //res.put(opcion, res.getOrDefault(opcion, 0) + 1);
                                actualizarPantalla();
                            }
                        } catch (NumberFormatException e) {
                        }
                    }
                }
            }
        });
        
        channel.connect("VotingCluster");
        actualizarPantalla();
        menu();
    }

    private void actualizarPantalla() {
        System.out.println("\n\n========================================");
        System.out.println("          SISTEMA DE VOTACION");
        System.out.println("========================================");
        
        if (temas.isEmpty()) {
            System.out.println("  No hay preguntas registradas todavia.");
        } else {
            for (int i = 0; i < temas.size(); i++) {
                TemaVotacion tema = temas.get(i);
               System.out.println(" " + (i + 1) + ". " + tema.getPregunta());
                System.out.println("    Resultados: " + tema.getResultados());
                System.out.println("");
            }
        }
        
        System.out.println("----------------------------------------");
        System.out.println(" 1. Iniciar Votacion | 2. Votar | 3. Salir");
        System.out.print("> ");
    }

    private void menu() throws Exception {
        Scanner sc = new Scanner(System.in);
        while (true) {
            String op = sc.nextLine();
            
            if (op.equals("1")) {
                System.out.print("Escribe tu pregunta: ");
                String q = sc.nextLine();
                channel.send(new Message(null, "PREGUNTA:" + q));
                
            } else if (op.equals("2")) {
                if (temas.isEmpty()) {
                    System.out.println("No hay preguntas para votar.");
                    System.out.print("> ");
                    continue;
                }
                
                System.out.print("Ingresa el numero de la pregunta a votar: ");
                try {
                    int numPregunta = Integer.parseInt(sc.nextLine()) - 1; 
                    
                    if (numPregunta >= 0 && numPregunta < temas.size()) {
                        System.out.print("Tu voto: ");
                        String v = sc.nextLine();
                        channel.send(new Message(null, "VOTO:" + numPregunta + ":" + v));
                    } else {
                        System.out.println("Numero de pregunta no valido.");
                        System.out.print("> ");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Por favor, ingresa solo el numero.");
                    System.out.print("> ");
                }
                
            } else if (op.equals("3")) {
                break;
            } else {
                actualizarPantalla();
            }
        }
        channel.close();
    }
}