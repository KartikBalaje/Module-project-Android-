package com.example.equipmenthealth;

public class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private String designation;

    public User() {};
    public User(int id, String username, String password, String email, String designation) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.designation = designation;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getDesignation() {
        return designation;
    }
}
