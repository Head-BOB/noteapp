package me.noteapp.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initialize() throws SQLException {

        try(Connection conn = DbConnectionManager.getConnection();
            Statement stmt = conn.createStatement()) {

            String createUserTableSQL = "CREATE TABLE IF NOT EXISTS Users (" +
                    "user_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "username TEXT NOT NULL UNIQUE," +
                    "password TEXT NOT NULL," +
                    "email TEXT," +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ");";
            stmt.execute(createUserTableSQL);
            System.out.println("User created");

            String createNotesTableSQL = "CREATE TABLE IF NOT EXISTS Notes (" +
                    "note_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "user_id INTEGER NOT NULL," +
                    "title TEXT NOT NULL," +
                    "content TEXT," +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "is_archived INTEGER DEFAULT 0," +
                    "FOREIGN KEY (user_id) REFERENCES Users(user_id)" +
                    ");";
            stmt.execute(createNotesTableSQL);
            System.out.println("Notes created");

            String createCategoryTableSQL = "CREATE TABLE IF NOT EXISTS Categories (" +
                    "category_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "user_id INTEGER NOT NULL UNIQUE," +
                    "name TEXT NOT NULL UNIQUE," +
                    "description TEXT," +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "FOREIGN KEY (user_id) REFERENCES Users(user_id)"+
                    ");";
            stmt.execute(createCategoryTableSQL);
            System.out.println("Category created");

            String createNoteCategoriesTableSQL = "CREATE TABLE IF NOT EXISTS NoteCategories (" +
                    "note_id INTEGER NOT NULL," +
                    "category_id INTEGER NOT NULL," +
                    "PRIMARY KEY (note_id, category_id)," +
                    "FOREIGN KEY (note_id) REFERENCES Notes(note_id)," +
                    "FOREIGN KEY (category_id) REFERENCES Categories(category_id)" +
                    ");";
            stmt.execute(createNoteCategoriesTableSQL);
            System.out.println("NoteCategories created");

            String createAttachmentsTableSQL = "CREATE TABLE IF NOT EXISTS Attachments (" +
                    "attachment_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "note_id INTEGER NOT NULL," +
                    "filename TEXT NOT NULL," +
                    "file_path TEXT NOT NULL," +
                    "file_type TEXT," +
                    "uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "FOREIGN KEY (note_id) REFERENCES Notes(note_id)" +
                    ");";
            stmt.execute(createAttachmentsTableSQL);
            System.out.println("Attachements created");

        } catch (SQLException e) {

            System.err.println("Initialization error" + e.getMessage());

        }

        System.out.println("DB init complete.");

    }

    //database table creation etc ivde
}
