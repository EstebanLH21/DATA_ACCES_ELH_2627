package com.da.jrgs2627.unit3;

import java.sql.*;

class ConnectionManager  {
    private static Connection connection;

    public static synchronized Connection getConnection() throws SQLException, ClassNotFoundException {
        if (connection == null || connection.isClosed()) {
            String url = "jdbc:postgresql://localhost:5432/VTInstitute";
            String user = "postgres";
            String password = "54891036";
            connection = DriverManager.getConnection(url, user, password);
        }
        return connection;
    }
    public static Connection getConnection(String url) throws SQLException {
        if (url == null || url.isEmpty()) {
            throw new SQLException("La URL de conexión no puede estar vacía.");
        }
        String user = "postgres";
        String password = "54891036";
        return DriverManager.getConnection(url, user, password);
    }
}

public class App {
    public static void main(String[] args) {

        // 1. Abrimos la conexión una sola vez para todo el programa
        try (Connection conn = ConnectionManager.getConnection()) {

            // ==========================================
            // EJERCICIO 1: Listado
            // ==========================================
            System.out.println("--- EJERCICIO 1 ---");
            // Bloque try-with-resources para el Statement y el ResultSet
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT code, name, year FROM subjects ORDER BY code")) {

                System.out.println("Code\t Name\t\t\t\t Year");
                System.out.println("---------------------------------------------------------");
                while (rs.next()) {
                    System.out.println(rs.getInt("code") + "\t " +
                            rs.getString("name") + "\t\t " +
                            rs.getInt("year"));
                }
            } // Aquí se cierran stmt y rs automáticamente

            // ==========================================
            // EJERCICIO 2: Insertar
            // ==========================================
            System.out.println("\n--- EJERCICIO 2 ---");
            // Bloque try-with-resources solo para el Statement (no hay ResultSet)
            try (Statement stmt = conn.createStatement()) {
                String insertSQL = "INSERT INTO subjects (name, year) VALUES ('Markup Languages', 1)";
                int filasInsertadas = stmt.executeUpdate(insertSQL);
                System.out.println("Valor devuelto por executeUpdate: " + filasInsertadas);
            } // Aquí se cierra stmt automáticamente

            // ==========================================
            // EJERCICIO 3: Modificar tabla
            // ==========================================
            System.out.println("\n--- EJERCICIO 3 ---");
            // Bloque try-with-resources solo para el Statement (no hay ResultSet)
            try (Statement stmt = conn.createStatement()) {
                String alterSQL = "ALTER TABLE subjects ADD COLUMN Hours INTEGER";
                int resultado = stmt.executeUpdate(alterSQL);
                System.out.println("Valor devuelto por executeUpdate: " + resultado);
            } // Aquí se cierra stmt automáticamente

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error en la base de datos: " + e.getMessage());
        }
        // Al salir del try principal, Java cierra la conexión (conn) automáticamente
    }
}