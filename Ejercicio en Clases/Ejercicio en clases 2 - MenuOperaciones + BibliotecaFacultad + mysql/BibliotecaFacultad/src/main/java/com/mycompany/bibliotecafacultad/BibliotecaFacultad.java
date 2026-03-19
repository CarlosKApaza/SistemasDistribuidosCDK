/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.bibliotecafacultad;

import java.util.ArrayList;

/**
 *
 * @author cdk04
 */
public class BibliotecaFacultad {
    public static void main(String[] args) {
        // 1. Creamos la biblioteca
        Biblioteca miBiblioteca = new Biblioteca("Ciencias de la Computacion - USFX", 450.5);
        
        // 2. Creamos un armario
        Armario a1 = new Armario("A-001", MaterialArmario.METALICO);
        
        // 3. Creampos publicaciones (polimor)
        IPublicacion libro1 = new Libro("Java a fondo", "Pablo Mamani", "Alfaomega", 2021);
        IPublicacion revista1 = new Revista("IEEE Software", 2, 2026, TipoRevista.TECNICA);
        
        // El periodico es especial por su suplementos
        Periodico p1 = new Periodico("Correo del Sur", "24/02/2026");
        p1.agregarSuplemento("Panorama");
        p1.agregarSuplemento("Ecos");
        
        // 4. Cargamos el armario (aqui aplicamos la interfaz IPublicacion)
        a1.cargarPublicaciones(libro1);
        a1.cargarPublicaciones(revista1);
        a1.cargarPublicaciones(p1);
        
        //5. Agregamos el armario a la biblioteca y mostramos
        miBiblioteca.agregarArmario(a1);
        miBiblioteca.mostrarTodo();
        
        //6. Guardar en BD 
        PublicacionDAO dao = new PublicacionDAO();
        dao.insertar(libro1);
        dao.insertar(revista1);
        dao.insertar(p1);
    }
}