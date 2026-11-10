package com.da.jrgs2627.unit3;

import java.sql.*;
import java.util.Scanner;

public class InsertarEmpleadosApp {
    public static void main(String[] args) {
        // URL de conexión a la base de datos 'employees'
        String url = "jdbc:postgresql://localhost:5432/employees";
        Scanner sc = new Scanner(System.in);
        // 1. RECOGIDA DE DATOS POR TECLADO
        // ==========================================
        System.out.println("--- ALTA DE EMPLEADO ---");
        System.out.print("Nombre del empleado (ej: ESTEBAN): ");
        String eName = sc.nextLine().toUpperCase();

        System.out.print("Trabajo (ej: CLERK, MANAGER): ");
        String eJob = sc.nextLine().toUpperCase();
        System.out.print("Número de departamento (ej: 10, 20, 50): ");
        int deptNum = Integer.parseInt(sc.nextLine());

        // 2. CONEXIÓN Y GESTIÓN DE LA TRANSACCIÓN
        // ==========================================
        try(Connection conn = ConnectionManager.getConnection(url)){
            conn.setAutoCommit(false);

            try{
                // 3. COMPROBAR SI EL DEPARTAMENTO YA EXISTE
                boolean deptExiste = false;
                try(PreparedStatement checkStmt = conn.prepareStatement("SELECT COUNT(*) FROM dept WHERE deptno = ?")){
                    checkStmt.setInt(1, deptNum);
                    try(ResultSet rs = checkStmt.executeQuery()){
                        if(rs.next()){
                            // rs.getInt(1) lee la primera columna del SELECT (el COUNT)
                            // Si es mayor que 0, el departamento ya existe.
                            deptExiste = (rs.getInt(1) > 0);
                        }
                    }
                }
                if(!deptExiste){
                    System.out.println("El departamento " + deptNum + " no existe. Creándolo automáticamente...");
                    // Como solo tenemos el número, inventamos un nombre y localización genéricos
                    String dnamePorDefecto = "DEPT " + deptNum;
                    String locPorDefecto = "UNKNOWN";

                    try(PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO dept VALUES (?, ?, ?)")){
                        insertStmt.setInt(1, deptNum);               // ? 1: número de depto
                        insertStmt.setString(2, dnamePorDefecto);   // ? 2: nombre genérico
                        insertStmt.setString(3, locPorDefecto);     // ? 3: localización genérica
                        insertStmt.executeUpdate();
                    }
                }else {
                    System.out.println("El departamento " + deptNum + " ya existe en la base de datos.");
                }
                // 5. CALCULAR EL SIGUIENTE NÚMERO DE EMPLEADO (EMPNO)
                // ==========================================
                // Como la tabla 'employee' no tiene el empno autoincremental,
                // tenemos que buscar el número más alto y sumarle 1.
                // COALESCE(MAX(empno), 7000) devuelve el máximo, o 7000 si la tabla está vacía
                int nuevoEmpno = 0;
                try(Statement stmt = conn.createStatement()){
                    ResultSet rs = stmt.executeQuery("SELECT COALESCE(MAX(empno), 7000) + 1 FROM employee");
                    if(rs.next()){
                        nuevoEmpno = rs.getInt(1);
                    }
                }
                // 6. INSERTAR EL EMPLEADO
                System.out.println("Insertando empleado...");
                try (PreparedStatement insertEmpStmt = conn.prepareStatement("INSERT INTO employee (empno, ename, job, deptno) VALUES (?, ?, ?, ?)")) {
                    insertEmpStmt.setInt(1, nuevoEmpno);   // ? 1: el empno calculado
                    insertEmpStmt.setString(2, eName);     // ? 2: nombre del empleado
                    insertEmpStmt.setString(3, eJob);       // ? 3: trabajo
                    insertEmpStmt.setInt(4, deptNum);       // ? 4: el número de depto
                    insertEmpStmt.executeUpdate();         // Ejecutamos el INSERT
                }
                // 7. CONFIRMAR LA TRANSACCIÓN (COMMIT)
                // ==========================================
                // Si hemos llegado hasta aquí, TODO ha ido bien (el depto existe o se creó,
                // y el empleado se ha insertado correctamente).
                // Hacemos COMMIT para guardar los cambios de forma permanente.
                conn.commit();
                System.out.println("¡Transacción completada! Empleado " + eName + " (EmpNo: " + nuevoEmpno + ") insertado en el depto " + deptNum);





            }catch (SQLException e){
                System.out.println("¡Error en la transacción! Deshaciendo todos los cambios...");
                System.out.println("Detalle del error: " + e.getMessage());
                conn.rollback();

            }





        } catch (SQLException e) {

            System.out.println("Error: " + e.getMessage());
        }


    }
}
