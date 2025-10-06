package me.noteapp.service;

import me.noteapp.db.DbConnectionManager;
import me.noteapp.model.Note;

import java.sql.SQLException;

public class NoteService {

    public Note createNote(int userId, String title, String content) throws SQLException {

        String sql = "INSERT INTO Notes (user_id, title, content, created_at, updated_at, is_archived) VALUES (?, ?, ?, ?, ?, ?)";
        //try(Connection conn = DbConnectionManager.getConnection())
    }
}
