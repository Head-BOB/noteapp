package me.noteapp.model;

import java.time.LocalDateTime;

public class Attachment {

    private int id;
    private int noteId;
    private String filename;
    private String filepath;
    private String filetype;
    private LocalDateTime uploadedAt;


    public Attachment(int id, int noteId, String filename, String filepath, String filetype, LocalDateTime uploadedAt) {

        this.id = id;
        this.noteId = noteId;
        this.filename = filename;
        this.filepath = filepath;
        this.filetype = filetype;
        this.uploadedAt = uploadedAt;

    }


    //getters
    public int getId() {
        return id;
    }

    public int getNoteId() {
        return noteId;
    }

    public String getFilename() {
        return filename;
    }

    public String getFilepath() {
        return filepath;
    }

    public String getFiletype() {
        return filetype;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    //setters


    public void setId(int id) {
        this.id = id;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    @Override
    public String toString() {
        return "Attachment{id=" + id + ", filename='" + filename + "', noteId=" + noteId + '}';

    }

}