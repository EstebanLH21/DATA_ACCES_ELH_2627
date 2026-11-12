package com.dataaccess.finalactivity.ELH2627;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.util.Properties;

public class DatabaseManager {
    public static Connection getConnection() throws Exception{
        Properties props = new Properties();
        try(FileInputStream input = new FileInputStream("src/main/resources/db.properties")){
            props.load(input);


        }
        return DriverManager.getConnection(
                props.getProperty("db.url"),
                props.getProperty("db.user"),
                props.getProperty("db.password")
        );
    }
}
