package me.noteapp.model;

import java.time.LocalDateTime;

public class Note {

    private int id;
    private int userId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isArchived;

    //constructor
    public Note(int id, int userId, String title, String content, LocalDateTime createdAt, LocalDateTime updatedAt, boolean isArchived) {

        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isArchived = isArchived;

    }
    //getter
    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public  LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public boolean isArchived() {
        return isArchived;
    }

    //setter

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setArchived(boolean archived) {
        isArchived = archived;
    }

    @Override
    public String toString() {
        return "Note{id=" + id + ", title='" + title + "', userId=" + userId + ", archived=" + isArchived + '}';
    }

}