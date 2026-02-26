/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bibliotecafacultad;
import java.sql.*;

/**
 *
 * @author cdk04
 */
public class PublicacionDAO {
    
    public void insertar(IPublicacion p) {
    if (p == null) return; // Esto quita el error rojo del "Possible Null Pointer"

    String sql = "INSERT INTO publicaciones (tipo, nombre) VALUES (?, ?)";
    
    try (Connection conn = ConexionDB.getInstancia();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        String tipo = "LIBRO";
        if (p instanceof Revista) tipo = "REVISTA";
        if (p instanceof Periodico) tipo = "PERIODICO";

        ps.setString(1, tipo);
        ps.setString(2, p.getNombre());
        ps.executeUpdate();
        
        System.out.println("[DB] Guardado exitoso: " + p.getNombre());
        
    } catch (SQLException e) {
        System.err.println("Error técnico en MySQL: " + e.getMessage());
    }
}
}