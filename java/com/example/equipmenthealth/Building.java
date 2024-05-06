package com.example.equipmenthealth;

public class Building {
    private int buildingId;
    private String name;
    private int numberOfRooms;
    private int totalLabs;
    private int collegeId;

    public Building() {}

    public Building(int buildingId, String name, int numberOfRooms, int totalLabs, int collegeId) {
        this.buildingId = buildingId;
        this.name = name;
        this.numberOfRooms = numberOfRooms;
        this.totalLabs = totalLabs;
        this.collegeId = collegeId;
    }

    public int getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public int getTotalLabs() {
        return totalLabs;
    }

    public void setTotalLabs(int totalLabs) {
        this.totalLabs = totalLabs;
    }

    public int getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(int collegeId) {
        this.collegeId = collegeId;
    }
}

