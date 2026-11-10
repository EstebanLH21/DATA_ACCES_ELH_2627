package com.da.jrgs2627.unit3;
import java.sql.*;
import java.util.Scanner;


import java.sql.SQLException;

public class CallableApp {
    public static void main(String[] args) {
        // 1. URL de la base de datos 'employees'
        String urlEmployees = "jdbc:postgresql://localhost:5432/employees";

        Scanner sc = new Scanner(System.in);
        String nombreFuncion = "";
        String parametro = "";
        // 2. Mostramos el menú
        System.out.println("--- LLAMADA A PROCEDIMIENTOS (BD EMPLOYEES) ---");
        System.out.println("1. Buscar por trabajo (job)");
        System.out.println("2. Buscar por departamento");
        System.out.println("3. Buscar por patrón de nombre");
        System.out.print("Elige una opción (1-3): ");

        int opcion = Integer.parseInt(sc.nextLine());
        // 3. Pedimos el parámetro y asignamos la función
        switch (opcion) {
            case 1:
                System.out.print("Introduce el trabajo (ej: CLERK): ");
                parametro = sc.nextLine().toUpperCase();
                nombreFuncion = "employees_by_job";
                break;
            case 2:
                System.out.print("Introduce el departamento (ej: SALES): ");
                parametro = sc.nextLine().toUpperCase();
                nombreFuncion = "employees_by_department";
                break;
            case 3:
                System.out.print("Introduce el patrón (ej: A): ");
                parametro = sc.nextLine().toUpperCase();
                nombreFuncion = "employees_by_name_pattern";
                break;
            default:
                System.out.println("Opción no válida. Saliendo...");
                sc.close();
                return;
        }
        // 4. Conexión y ejecución
        // Usamos el metodo sobrecargado de TU clase ConnectionManager
        try(Connection conn = ConnectionManager.getConnection(urlEmployees)){
            String sqlCall = "{Call " + nombreFuncion + "('" + parametro + "')}";
            System.out.println("\nEjecutando: " + sqlCall);
            try(CallableStatement cstmt = conn.prepareCall(sqlCall)){
                // Ejecutamos la llamada y obtenemos el ResultSet
                try(ResultSet rs = cstmt.executeQuery()){
                    System.out.println("\n--- RESULTADOS ---");
                    System.out.println("EmpNo\t Nombre\t\t Job\t\t DeptNo");
                    System.out.println("---------------------------------------------------------");

                    boolean hayDatos = false;
                    while (rs.next()) {
                        hayDatos = true;
                        System.out.println(rs.getInt("empno") + "\t " +
                                rs.getString("ename") + "\t\t " +
                                rs.getString("job") + "\t\t " +
                                rs.getInt("deptno"));
                    }
                    if (!hayDatos) {
                        System.out.println("No se han encontrado empleados con ese criterio.");
                    }
                }
            }

        }catch(SQLException  ex){
            System.out.println("Error en la base de datos: " + ex.getMessage());
        }
        sc.close();


    }
}

