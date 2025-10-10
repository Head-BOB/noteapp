package me.noteapp.service;

import me.noteapp.db.DbConnectionManager;
import me.noteapp.model.Category;
import me.noteapp.util.SessonManager;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CategoryService {

    public Category createCategory(String name) throws SQLException {
        String sql = "INSERT INTO Categories(user_id, name) VALUES(?, ?)";
        int userId = SessonManager.getCurrentUserId();

        try (Connection conn = DbConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, userId);
            pstmt.setString(2, name);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating category failed, no rows affected.");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    return new Category(id, userId, name, null, LocalDateTime.now());
                } else {
                    throw new SQLException("Creating category failed, no ID obtained.");
                }
            }
        }
    }

    public List<Category> getCategoriesForUser() throws SQLException {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM Categories WHERE user_id = ?";
        int userId = SessonManager.getCurrentUserId();

        try (Connection conn = DbConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("category_id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
                categories.add(new Category(id, userId, name, description, createdAt));
            }
        }
        return categories;
    }
    public List<Category> getCategoriesForNote(int noteId) throws SQLException {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT c.category_id, c.name, c.description, c.created_at, c.user_id FROM Categories c " +
                "JOIN NoteCategories nc ON c.category_id = nc.category_id " +
                "WHERE nc.note_id = ?";

        try (Connection conn = DbConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, noteId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                categories.add(new Category(
                        rs.getInt("category_id"),
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                ));
            }
        }
        return categories;
    }

    public void addNoteToCategory(int noteId, int categoryId) throws SQLException {
        String sql = "INSERT INTO NoteCategories(note_id, category_id) VALUES(?, ?)";
        try (Connection conn = DbConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, noteId);
            pstmt.setInt(2, categoryId);
            pstmt.executeUpdate();
        }
    }

    public void removeNoteFromCategory(int noteId, int categoryId) throws SQLException {
        String sql = "DELETE FROM NoteCategories WHERE note_id = ? AND category_id = ?";
        try (Connection conn = DbConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, noteId);
            pstmt.setInt(2, categoryId);
            pstmt.executeUpdate();
        }
    }

}