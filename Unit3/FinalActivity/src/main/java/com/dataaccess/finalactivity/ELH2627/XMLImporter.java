package com.dataaccess.finalactivity.ELH2627;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class XMLImporter {
    public static void importPassengers(String filename) {
        try (Connection conn = DatabaseManager.getConnection()){
            conn.setAutoCommit(false);
            try{
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder loader = factory.newDocumentBuilder();
                Document doc =  loader.parse(filename);

                doc.getDocumentElement().normalize();

                NodeList nList = doc.getElementsByTagName("passenger");

                String sql = "INSERT INTO passengers (passportno, firstname, lastname, address, phone) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                for(int i = 0; i < nList.getLength(); i++){
                    Node nNode = nList.item(i);
                    if(nNode.getNodeType() == Node.ELEMENT_NODE){
                        Element elem = (Element) nNode;
                        String passport = elem.getElementsByTagName("passportno").item(0).getTextContent();
                        String firstname = elem.getElementsByTagName("firstname").item(0).getTextContent();
                        String lastname = elem.getElementsByTagName("lastname").item(0).getTextContent();

                        String addres = null;
                        NodeList addressNodes = elem.getElementsByTagName("address");
                        if(addressNodes.getLength() > 0){
                            addres = addressNodes.item(0).getTextContent();

                        }
                        String phone = null;
                        NodeList phoneNodes = elem.getElementsByTagName("phone");
                        if(phoneNodes.getLength() > 0){
                            phone = phoneNodes.item(0).getTextContent();
                        }
                        if (passport == null || passport.trim().isEmpty() ||
                                firstname == null || firstname.trim().isEmpty() ||
                                lastname == null || lastname.trim().isEmpty()) {
                            throw new Exception("Mandatory fields missing for passenger.");
                        }
                        if (!passport.matches("^[A-Z][0-9]{7}$")) {
                            throw new Exception("Invalid passport format: " + passport);
                        }
                        if (phone != null && !phone.trim().isEmpty() && !phone.matches("^[0-9]{9}$")) {
                            throw new Exception("Invalid phone format: " + phone);
                        }
                        pstmt.setString(1, passport);
                        pstmt.setString(2, firstname);
                        pstmt.setString(3, lastname);
                        pstmt.setString(4, addres);
                        pstmt.setString(5, phone);
                        pstmt.executeUpdate();

                    }
                }
                conn.commit();
                System.out.println("All passengers successfully added to the database.");

            }catch(Exception e){
                System.err.println("Error adding passengers: " + e.getMessage());
                conn.rollback();
            }

        }catch(Exception e){
            System.err.println("Database connection error: " + e.getMessage());
        }
    }
}
