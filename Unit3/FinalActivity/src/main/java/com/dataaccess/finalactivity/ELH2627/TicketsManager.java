package com.dataaccess.finalactivity.ELH2627;
import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.*;

public class TicketsManager {

    public static void buyTicket(String passport, String flightCode, String dateStr, boolean print) {

        try (Connection conn = DatabaseManager.getConnection()) {
            conn.setAutoCommit(false);

            try {

                Date travelDate = Date.valueOf(dateStr);


                String checkPassengerSql = "SELECT COUNT(*) FROM tickets WHERE passportno = ? AND date_of_travel = ? AND date_of_cancellation IS NULL";
                try (PreparedStatement checkPstmt = conn.prepareStatement(checkPassengerSql)) {
                    checkPstmt.setString(1, passport);
                    checkPstmt.setDate(2, travelDate);

                    try (ResultSet rs = checkPstmt.executeQuery()) {
                        if (rs.next() && rs.getInt(1) > 0) {

                            throw new Exception("Passenger " + passport + " already has a ticket for this date.");
                        }
                    }
                }


                String checkSeatsSql = "SELECT (p.total_seats - COALESCE(COUNT(t.ticket_number), 0)) AS free_seats " +
                        "FROM flights f " +
                        "INNER JOIN planes p ON f.plane = p.plane_id " +
                        "LEFT JOIN tickets t ON t.flight_code = f.flight_code AND t.date_of_travel = ? AND t.date_of_cancellation IS NULL " +
                        "WHERE f.flight_code = ? " +
                        "GROUP BY p.total_seats";

                try (PreparedStatement seatsPstmt = conn.prepareStatement(checkSeatsSql)) {
                    seatsPstmt.setDate(1, travelDate);
                    seatsPstmt.setString(2, flightCode);

                    try (ResultSet rs = seatsPstmt.executeQuery()) {
                        if (rs.next()) {
                            int freeSeats = rs.getInt("free_seats");
                            if (freeSeats <= 0) {
                                throw new Exception("No seats available on flight " + flightCode);
                            }
                        } else {
                            throw new Exception("Flight " + flightCode + " does not exist.");
                        }
                    }
                }


                String insertSql = "INSERT INTO tickets (date_of_booking, date_of_travel, passportno, flight_code, price) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement insertPstmt = conn.prepareStatement(insertSql)) {
                    
                    insertPstmt.setDate(1, new Date(System.currentTimeMillis()));
                    insertPstmt.setDate(2, travelDate);
                    insertPstmt.setString(3, passport);
                    insertPstmt.setString(4, flightCode);
                    insertPstmt.setDouble(5, 150.0);

                    insertPstmt.executeUpdate();
                }



                conn.commit();
                System.out.println("Ticket successfully bought for passenger " + passport + " on flight " + flightCode + " for date " + dateStr);
                if(print) {
                    String ticketNumSQL = "SELECT ticket_number FROM tickets WHERE passportno = ? AND date_of_travel = ? AND date_of_cancellation IS NULL";
                    int ticketNum = 0;
                    try(PreparedStatement ticketNumPstmt = conn.prepareStatement(ticketNumSQL)) {
                        ticketNumPstmt.setString(1, passport);
                        ticketNumPstmt.setDate(2, travelDate);
                        try (ResultSet rs = ticketNumPstmt.executeQuery()) {
                            if (rs.next()) {
                                ticketNum = rs.getInt("ticket_number");

                            }
                        }


                    }
                    String fileName = "Ticket"+ticketNum+".txt";
                    File file = new File(fileName);


                    boolean append = true;


                    try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file, append)))) {

                        pw.println("Passport: " + passport + " | Flight: " + flightCode + " | Date: " + dateStr);


                        System.out.println("Saved!");
                    }

                }


            } catch (Exception e) {

                System.err.println("Error buying ticket: " + e.getMessage());
                conn.rollback();
            }
        } catch (Exception e) {
            System.err.println("Database connection error: " + e.getMessage());
        }
    }
        public static void cancelTicket(String passport, String dateStri){
        Date travelDate = Date.valueOf(dateStri);
        Date cancelDate = new Date(System.currentTimeMillis());
        String sql = "UPDATE tickets SET date_of_cancellation = ? WHERE passportno = ? AND date_of_travel = ?;";
        try(Connection conn = DatabaseManager.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setDate(1, cancelDate);
            pstmt.setString(2, passport);
            pstmt.setDate(3, travelDate);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Ticket for passenger " + passport + " has been cancelled.");
            }else{
                System.out.println("Error at canceling ticket for passenger " + passport);
            }




        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
