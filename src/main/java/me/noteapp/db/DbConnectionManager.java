package me.noteapp.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnectionManager {

    //manages database connections

    private static final String DB_URL = "jdbc:sqlite:notesapp.db";


    public static Connection getConnection() throws SQLException {



            // CONNECtion logic ehere




        return DriverManager.getConnection(DB_URL);
    }


    public static void closeConnection(Connection conn) {

        if(conn != null) {

            try{
                conn.close();
            } catch (SQLException e) {

                System.err.println("Error closing connection: " + e.getMessage());

            }
        }

    }
}
