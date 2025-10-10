package me.noteapp.service;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import me.noteapp.db.DbConnectionManager;
import me.noteapp.model.Attachment;
import me.noteapp.model.Note;

import java.io.IOException;
import java.sql.*;
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
        System.out.println("DEBUG: NoteService.addNote() called.");
        String sql = "INSERT INTO Notes(user_id, title, content, created_at, updated_at) VALUES(?,?,?,?,?)";

        try (Connection conn = DbConnectionManager.getConnection(); //
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            LocalDateTime now = LocalDateTime.now();
            pstmt.setInt(1, userId);
            pstmt.setString(2, title);
            pstmt.setString(3, content);
            pstmt.setTimestamp(4, Timestamp.valueOf(now));
            pstmt.setTimestamp(5, Timestamp.valueOf(now));

            int affectedRows = pstmt.executeUpdate();
            System.out.println("DEBUG: INSERT query executed, " + affectedRows + " rows affected.");

            if (affectedRows == 0) {
                throw new SQLException("Creating note failed, no rows affected.");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    System.out.println("DEBUG: New note created with ID: " + id);
                    return new Note(id, userId, title, content, now, now, false); //
                } else {
                    throw new SQLException("Creating note failed, no ID obtained.");
                }
            }
        }
    }

    public void updateNote(Note note) throws SQLException {
        System.out.println("DEBUG: NoteService.updateNote() called for note ID: " + note.getId());
        String sql = "UPDATE Notes SET title = ?, content = ?, updated_at = ? WHERE note_id = ?";

        try (Connection conn = DbConnectionManager.getConnection(); //
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, note.getTitle());
            pstmt.setString(2, note.getContent());
            pstmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.setInt(4, note.getId());

            int affectedRows = pstmt.executeUpdate();
            System.out.println("DEBUG: UPDATE query executed, " + affectedRows + " rows affected.");
        }
    }

    public void deleteNote(Note note) throws SQLException, IOException {
        int noteId = note.getId();
        String deleteNoteCategoriesSql = "DELETE FROM NoteCategories WHERE note_id = ?";
        String deleteNoteSql = "DELETE FROM Notes WHERE note_id = ?";

        try (Connection conn = DbConnectionManager.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement pstmtNoteCategories = conn.prepareStatement(deleteNoteCategoriesSql);
                 PreparedStatement pstmtNote = conn.prepareStatement(deleteNoteSql)) {


                AttachmentService attachmentService = new AttachmentService();
                List<Attachment> attachments = attachmentService.getAttachmentsForNote(noteId);
                for (Attachment attachment : attachments) {
                    attachmentService.deleteAttachment(attachment);
                }

                pstmtNoteCategories.setInt(1, noteId);
                pstmtNoteCategories.executeUpdate();

                pstmtNote.setInt(1, noteId);
                pstmtNote.executeUpdate();

                conn.commit();
                System.out.println("DEBUG: Note ID " + noteId + " and all related data deleted successfully.");

            } catch (SQLException | IOException e) {

                conn.rollback();
                System.err.println("Transaction rolled back for note deletion.");
                throw e;
            }
        }
    }
}

