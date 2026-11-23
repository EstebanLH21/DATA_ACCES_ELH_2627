package com.dataaccess.finalactivity.ELH2627;
import java.sql.*;
import java.time.LocalDate;

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
    //--destinations
    public static void showDestinations(String origins) {
        String sql= "SELECT DISTINCT dest.iata FROM flights f " +
                "INNER JOIN airports orig ON f.source = orig.code " +
                "INNER JOIN airports dest ON f.destination = dest.code " +
                "WHERE orig.iata = ?";
        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, origins);
            try (ResultSet rs = pstmt.executeQuery()) {
                System.out.println("Destinations from " + origins + ":");

                boolean found = false;


                while (rs.next()) {
                    System.out.println("- " + rs.getString(1));
                    found = true;
                }
                if (!found) {
                    System.out.println("Destinations not found");
                }
            }

        }catch(Exception e){
            System.err.println("Error retrieving destinations: " + e.getMessage());
        }
    }

    //--flights
    public static void showFlights(String origins, String destinations, String dateStr) {
        String sql = "{call get_flights_elh(?,?,?)}";
        try(Connection conn = DatabaseManager.getConnection();
        CallableStatement cstmt = conn.prepareCall(sql)){
            cstmt.setString(1, origins);
            cstmt.setString(2, destinations);
            cstmt.setDate(3, Date.valueOf(dateStr));

            try (ResultSet rs = cstmt.executeQuery()) {
                System.out.println("Flights from " + origins + " to " + destinations + " on " + dateStr + ":");
                boolean found = false;
                while (rs.next()) {
                    found = true;
                    String flightCode = rs.getString("flight_code");
                    int freeSeats = rs.getInt("free_seats");
                    System.out.println("- Flight: " + flightCode + " | Free Seats: " + freeSeats);


                }
                if(!found){
                    System.out.println("No flights found for these criteria.");
                }
            }

        }catch(Exception e){
            System.err.println("Error retrieving flights: " + e.getMessage());
        }

    }




}



