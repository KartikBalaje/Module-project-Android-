package com.example.equipmenthealth;

public class ManagerHomeModel {
    private String name;
    private String collegename;

    public ManagerHomeModel(String name, String collegename) {
        this.name = name;
        this.collegename = collegename;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCollegename() {
        return collegename;
    }

    public void setCollegename(String collegename) {
        this.collegename = collegename;
    }
}

