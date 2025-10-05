package me.noteapp.model;

import java.time.LocalDateTime;

public class Attachment {
        private int id;
        private int notId;
        private String filename;
        private String filepath;
        private String filetype;
        private LocalDateTime uploadAt;



        public Attachment(int id,int notId, String filename,String filepath,String filetype,LocalDateTime uploadAt){
            this.id=id;
            this.notId=notId;
            this.filename=filename;
            this.filepath=filepath;
            this.filetype=filetype;
            this.uploadAt=uploadAt;
        }


        public int getId(){
            return id;
        }

    public int getNotId() {
        return notId;
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

    public String getFiletype(String filetype) {
        return filetype;
    }

    public LocalDateTime getUploadAt() {
        return uploadAt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNotId(int notId) {
        this.notId = notId;
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

    public void setUploadAt(LocalDateTime uploadAt) {
        this.uploadAt = uploadAt;
    }
    //all data related to a single attachment here image pdf allel enthelum videos
}
