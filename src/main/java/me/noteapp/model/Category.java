package me.noteapp.model;

import java.time.LocalDateTime;

public class Category {
    private int id;
    private int userId;
    private String name;
    private String Description;
    private LocalDateTime createdAt;



    public Category(int id,int userId,String name,String Description,LocalDateTime createdAt){


        this.id=id;
        this.userId=userId;
        this.name=name;
        this.Description=Description;
        this.createdAt=createdAt;

    }

    public int getId() {
        return id;

    }

    public int getUserId() {
        return userId;
    }


    public String getName() {
        return name;
    }

    public String getDescription() {
        return Description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        name = name;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString(){
        return name;
    }
}