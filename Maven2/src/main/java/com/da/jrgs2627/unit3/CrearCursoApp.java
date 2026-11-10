package com.da.jrgs2627.unit3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CrearCursoApp {
    public static void main(String[] args) {
        // 1. Sentencia para crear la tabla (sin parámetros)
        String createTableSQL = "CREATE TABLE IF NOT EXISTS courses (" +
                "code SERIAL PRIMARY KEY, " +
                "name VARCHAR(90) NOT NULL)";
        // 2. Sentencia para insertar (con un parámetro ? para el nombre)
        String insertSQL = "INSERT INTO courses (name) VALUES (?)";
        //Abrir conexión con try-with-resources
        try(Connection conn = ConnectionManager.getConnection()){
            //Crear tabla
            try (PreparedStatement pstmtCreate = conn.prepareStatement(createTableSQL)) {
                pstmtCreate.executeUpdate();
                System.out.println("Tabla 'courses' creada (o ya existía).");

            }
            //Insertar los valores
            try(PreparedStatement pstmtInsert = conn.prepareStatement(insertSQL)){
                pstmtInsert.setNString(1, "Multiplatform app development");
                int filas1 = pstmtInsert.executeUpdate();
                System.out.println("Insertado: 'Multiplatform app development' (" + filas1 + " fila).");

                //Segundo valor
                pstmtInsert.setNString(2, "Web development");
                int filas2 = pstmtInsert.executeUpdate();
                System.out.println("Insertado: 'Web development' (" + filas2 + " fila).");
            }

        }catch(SQLException | ClassNotFoundException e){
            System.out.println("Error en la base de datos: " + e.getMessage());
        }
    }
}
