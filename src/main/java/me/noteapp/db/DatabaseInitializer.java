package me.noteapp.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initialize() throws SQLException {

        try(Connection conn = DbConnectionManager.getConnection();
            Statement stmt = conn.createStatement()) {

            //db login here for creating tables


        } catch (SQLException e) {

            System.err.println("Initialization error" + e.getMessage());

        }

        System.out.println("DB init complete.");

    }

    //database table creation etc ivde
}
