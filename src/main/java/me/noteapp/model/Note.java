package me.noteapp.model;

import java.time.LocalDateTime;

public class Note {

    private int id;
    private int userid;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isArchieved;

    public Note(int id,int userid,String title,String content,LocalDateTime createdAt,LocalDateTime updatedAt,boolean isArchieved){
        this.id=id;
        this.userid=userid;
        this.title=title;
        this.content=content;
        this.createdAt=createdAt;
        this.updatedAt=updatedAt;
        this.isArchieved=isArchieved;

    }
    public int getId(){
        return id;
    }
    public int userId(){
        return userid;
    }
    public String getTitle(){
        return title;
    }
    public String getContent(){
        return content;
    }
    public LocalDateTime getCreatedAt(){
        return createdAt;
    }
    public LocalDateTime getUpdatedAt(){
        return updatedAt;
    }
    public boolean getIsArchieved(){
        return isArchieved;
    }
    public void setId(int id){
        this.id = id;
    }
    public void setUserid(int userid){
        this.userid=userid;
    }
    public void setTitle(String title){
        this.title=title;
    }
    public void setContent(String content){
        this.content=content;
    }
    public void setCreatedAt(LocalDateTime createdAt){
        this.createdAt=createdAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt){
        this.updatedAt=updatedAt;
    }
    public void setArchieved(boolean isArchieved){
        this.isArchieved=isArchieved;
    }
}


























































