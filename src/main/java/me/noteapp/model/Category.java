package me.noteapp.model;

public class Category {

    private int id;
    private int userId;
    private String name;
    private String description;


    //constructor
    public Category(int id, int userId, String name, String description) {

        this.id = id;
        this.userId = userId;
        this.name = name;
        this.description = description;

    }

    //getters

    public int getId() {
        return id;
    }

    public int getUserId() {
        return  userId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    //setters

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Category{id=" + id + ", name='" + name + "', userId=" + userId + '}';
    }
}