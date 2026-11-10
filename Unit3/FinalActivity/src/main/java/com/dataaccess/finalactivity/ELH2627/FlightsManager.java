package com.dataaccess.finalactivity.ELH2627;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class FlightsManager {
    public static void showOrigins() {
        String sql = "SELECT DISTINCT a.iata FROM flights f INNER JOIN airports a ON f.source = a.code";
        //Conexión  y ejecución
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("Origins: ");
            while (rs.next()) {
                System.out.println("- " + rs.getString(1));
            }

        }catch(Exception e){
            System.err.println("Error retrieving origins: " + e.getMessage());
        }



    }
}



