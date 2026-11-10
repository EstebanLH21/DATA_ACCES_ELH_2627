package com.da.jrgs2627.unit3;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.Scanner;

public class InsertarCursosApp {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String insertSQL = "INSERT INTO subjects (name, year) VALUES (?, ?)";
        try(Connection conn = ConnectionManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(insertSQL)){
            System.out.println("--- INTRODUCCIÓN DE CURSOS ---");
            System.out.println("Escribe 'salir' en el nombre para terminar.\n");
            boolean salir = false;
            while(!salir){
                System.out.print("Nombre del curso: ");

                String nombre = sc.nextLine();
                if(nombre.equals("salir")){
                    salir = true;
                    System.out.println("Saliendo...");
                }else{
                    System.out.println("Año del curso (1 o 2): ");
                    int anio = Integer.parseInt(sc.nextLine());
                    pstmt.setString(1, nombre);
                    pstmt.setInt(2, anio);
                    int filasAfectadas = pstmt.executeUpdate();
                    System.out.println("-> Curso insertado correctamente (" + filasAfectadas + " fila).\n");
                }
            }


        }catch (SQLException | ClassNotFoundException e){
            System.out.println("Error en la base de datos: " + e.getMessage());
        }
        sc.close();

    }
}
