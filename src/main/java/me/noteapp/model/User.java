package me.noteapp.model;

import java.time.LocalDateTime;

public class User {

    private int id;
    private String username;
    private String password;
    private String email;
    private LocalDateTime createdAt;

    //constructor

    public User(int id, String username, String password, String email, LocalDateTime createdAt) {

        this.id =id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.createdAt = createdAt;

    }


    //getters
    public int getId(){
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public  String getEmail() {
        return email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    //setters


    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


    @Override
    public String toString(){
        return "User{id="+id+",username='" + username + "'}";
    }
}