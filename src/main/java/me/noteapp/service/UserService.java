package me.noteapp.service;

import me.noteapp.db.DbConnectionManager;
import me.noteapp.model.User;

import java.sql.*;
import java.time.LocalDateTime;

public class UserService {

    public User registerUser(String username, String password, String email)throws SQLException {

        String sql = "INSERT INTO Users(username, password, email, created_at) VALUES (?,?,?,?)";

        try(Connection conn = DbConnectionManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, email);
            pstmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));

            int affectedRows = pstmt.executeUpdate();
            if(affectedRows == 0){
                throw new SQLException("User creation failed no rows affected");
            }

            try(ResultSet generatedKeys = pstmt.getGeneratedKeys()){
                if(generatedKeys.next()){
                    int generatedId = generatedKeys.getInt(1);
                    LocalDateTime now = LocalDateTime.now();
                    return new User(generatedId, username, password, email, now);
                } else {
                    throw new SQLException("Creating user failed no iid obtained");
                }

            }
        }

    }

    public User loginUser(String username, String password) throws SQLException {

        String sql = "SELECT user_id, username, password, email, created_at FROM Users WHERE username = ?";

        try(Connection conn = DbConnectionManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()) {
                    String storedPassword = rs.getString("password");
                    if(storedPassword.equals(password)){
                        int id = rs.getInt("user_id");
                        String userEmail = rs.getString("email");
                        LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
                        return new User(id, username, storedPassword, userEmail,createdAt);

                    }
                }
            }
        }

        return null;
    }

    public User getUserById(int userId)throws SQLException {

        String sql = "SELECT user_id, username, password, email, createdAt FROM Users WHERE user_id = ?";

        try(Connection conn = DbConnectionManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            try(ResultSet rs = pstmt.executeQuery()) {
                if(rs.next()) {
                    int id = rs.getInt("user_id");
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String email = rs.getString("email");
                    LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
                    return new User(id, username, password, email, createdAt);
                }
            }

        }
        return null;
    }
}