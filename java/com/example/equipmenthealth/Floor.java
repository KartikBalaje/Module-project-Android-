package com.example.equipmenthealth;

public class Floor {
    private int floorId;
    private int floorNumber;
    private int numberOfRooms;
    private int numberOfLabs;
    private int collegeId;
    private int buildingId;

    public Floor() {}

    public Floor(int floorId, int floorNumber, int numberOfRooms, int numberOfLabs, int collegeId, int buildingId) {
        this.floorId = floorId;
        this.floorNumber = floorNumber;
        this.numberOfRooms = numberOfRooms;
        this.numberOfLabs = numberOfLabs;
        this.collegeId = collegeId;
        this.buildingId = buildingId;
    }

    public int getFloorId() {
        return floorId;
    }

    public void setFloorId(int floorId) {
        this.floorId = floorId;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public int getNumberOfLabs() {
        return numberOfLabs;
    }

    public void setNumberOfLabs(int numberOfLabs) {
        this.numberOfLabs = numberOfLabs;
    }

    public int getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(int collegeId) {
        this.collegeId = collegeId;
    }

    public int getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }
}
