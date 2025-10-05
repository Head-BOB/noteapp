package me.noteapp.model;

import java.time.LocalTime;

public class User {

    private int id;
    private String username,password,email;
    private LocalTime createdAt;


        public User(int id,String username,String password,String email,LocalTime createdAt){
            this.id=id;
            this.username=username;
            this.password=password;
            this.email=email;
            this.createdAt=createdAt;

        }
    public int getId(){
            return id;
    }
    public String getUsername(){
            return username;
    }
    public String getPassword(){
            return password;
    }
    public String getEmail(){
            return email;
    }
    public LocalTime getCreatedAt(){
            return createdAt;
    }
    public void setId(int id){
            this.id=id;
    }
    public void setUsername(String username){
        this.username=username;
    }
    public void setPassword(String password){
        this.password=password;
    }
    public void setEmail(String email){
        this.email=email;
    }
    public void setCreatedAt(LocalTime createdAt){
        this.createdAt=createdAt;
    }

}
