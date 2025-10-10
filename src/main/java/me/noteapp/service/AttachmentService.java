package me.noteapp.service;

import me.noteapp.db.DbConnectionManager;
import me.noteapp.model.Attachment;
import me.noteapp.model.Note;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AttachmentService {

    private static final String ATTACHMENT_DIR = "attachments";

    public AttachmentService() {
        try {
            Files.createDirectories(Paths.get(ATTACHMENT_DIR));
        } catch (IOException e) {
            System.err.println("Could not create attachment directory: " + e.getMessage());
        }
    }

    public Attachment addAttachment(Note note, File sourceFile) throws SQLException, IOException {
        String uniqueFileName = UUID.randomUUID() + "_" + sourceFile.getName();
        Path destinationPath = Paths.get(ATTACHMENT_DIR, uniqueFileName);

        Files.copy(sourceFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

        String sql = "INSERT INTO Attachments(note_id, filename, file_path, file_type) VALUES(?, ?, ?, ?)";
        try (Connection conn = DbConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, note.getId());
            pstmt.setString(2, sourceFile.getName());
            pstmt.setString(3, destinationPath.toString());
            pstmt.setString(4, getFileExtension(sourceFile.getName()));

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating attachment failed, no rows affected.");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    return new Attachment(id, note.getId(), sourceFile.getName(), destinationPath.toString(), getFileExtension(sourceFile.getName()), LocalDateTime.now());
                } else {
                    throw new SQLException("Creating attachment failed, no ID obtained.");
                }
            }
        }
    }

    public List<Attachment> getAttachmentsForNote(int noteId) throws SQLException {
        List<Attachment> attachments = new ArrayList<>();
        String sql = "SELECT * FROM Attachments WHERE note_id = ?";
        try (Connection conn = DbConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, noteId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                attachments.add(new Attachment(
                        rs.getInt("attachment_id"),
                        rs.getInt("note_id"),
                        rs.getString("filename"),
                        rs.getString("file_path"),
                        rs.getString("file_type"),
                        rs.getTimestamp("uploaded_at").toLocalDateTime()
                ));
            }
        }
        return attachments;
    }

    private String getFileExtension(String filename) {
        int lastIndexOf = filename.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return "";
        }
        return filename.substring(lastIndexOf + 1);
    }
    public void deleteAttachment(Attachment attachment) throws SQLException, IOException {
        Files.deleteIfExists(Paths.get(attachment.getFilepath()));

        String sql = "DELETE FROM Attachments WHERE attachment_id = ?";
        try (Connection conn = DbConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, attachment.getId());
            pstmt.executeUpdate();
        }
    }


}