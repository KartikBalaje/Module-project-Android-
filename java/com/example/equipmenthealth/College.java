package com.example.equipmenthealth;

public class College {
    private int collegeId;
    private String name;

    public College() {}
    public College(int collegeId, String name) {
        this.collegeId = collegeId;
        this.name = name;
    }

    public int getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(int collegeId) {
        this.collegeId = collegeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
