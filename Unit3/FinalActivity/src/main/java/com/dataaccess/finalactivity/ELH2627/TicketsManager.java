package com.dataaccess.finalactivity.ELH2627;
import java.sql.*;

public class TicketsManager {

    public static void buyTicket(String passport, String flightCode, String dateStr) {

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

            } catch (Exception e) {

                System.err.println("Error buying ticket: " + e.getMessage());
                conn.rollback();
            }
        } catch (Exception e) {
            System.err.println("Database connection error: " + e.getMessage());
        }
    }
}
