package me.noteapp.service;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import me.noteapp.db.DbConnectionManager;
import me.noteapp.model.Note;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NoteService {

    public List<Note> getNotesForUser(int userId) throws SQLException {

        List<Note> notes = new ArrayList<>();
        String sql = "SELECT * FROM Notes WHERE user_id = ?";


        try (Connection conn = DbConnectionManager.getConnection(); // [cite: 40]
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("note_id");
                    String title = rs.getString("title");
                    String content = rs.getString("content");
                    LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
                    LocalDateTime updatedAt = rs.getTimestamp("updated_at").toLocalDateTime();
                    boolean isArchived = rs.getInt("is_archived") == 1;

                    Note note = new Note(id, userId, title, content, createdAt, updatedAt, isArchived); // [cite: 63]
                    notes.add(note);
                }
            }
        }
        return notes;
    }

    public Note addNote(String title, String content, int userId) throws SQLException {
        String sql = "INSERT INTO Notes(user_id, title, content, created_at, updated_at) VALUES(?,?,?,?,?)";

        try (Connection conn = DbConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {

            LocalDateTime now = LocalDateTime.now();
            pstmt.setInt(1, userId);
            pstmt.setString(2, title);
            pstmt.setString(3, content);
            pstmt.setTimestamp(4, java.sql.Timestamp.valueOf(now));
            pstmt.setTimestamp(5, java.sql.Timestamp.valueOf(now));

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating note failed, no rows affected.");
            }

            try (java.sql.ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    return new Note(id, userId, title, content, now, now, false);
                } else {
                    throw new SQLException("Creating note failed no ID obtained");
                }
            }
        }
    }
}
