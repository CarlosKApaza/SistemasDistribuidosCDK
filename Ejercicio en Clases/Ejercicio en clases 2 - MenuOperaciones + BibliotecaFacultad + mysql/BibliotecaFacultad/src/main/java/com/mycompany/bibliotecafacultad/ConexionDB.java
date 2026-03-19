/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bibliotecafacultad;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author cdk04
 */

// usamos el patron singleton para asegurar una instancia de la conexion abierta. Optimizamos los recursos del servidor
public class ConexionDB {
    private static Connection connection;
    // Datos de XAMPP por defecto
    private static final String URL = "jdbc:mysql://localhost:3306/biblioteca_db";
    private static final String USER = "root";
    private static final String PASS = "";
    
    private ConexionDB() {} // Constructor privado para evitar 'new'
    
    public static Connection getInstancia() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASS); 
        } 
        return connection;
    }
}
