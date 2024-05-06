package com.example.equipmenthealth;

public class Lab {
    private int labId;
    private String labName;
    private int college_id;
    private int floorId;
    private int totalRooms;
    private int numberOfLabs;
    private String otherDetails;

    public Lab() {};

    public Lab(int labId, String labName, int college_id, int floorId, int totalRooms, int numberOfLabs, String otherDetails) {
        this.labId = labId;
        this.labName = labName;
        this.floorId = floorId;
        this.totalRooms = totalRooms;
        this.numberOfLabs = numberOfLabs;
        this.otherDetails = otherDetails;
        this.college_id = college_id;
    }

    public int getCollege_id() {
        return college_id;
    }

    public void setCollege_id(int college_id) {
        this.college_id = college_id;
    }

    public int getLabId() {
        return labId;
    }

    public void setLabId(int labId) {
        this.labId = labId;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }

    public int getFloorId() {
        return floorId;
    }

    public void setFloorId(int floorId) {
        this.floorId = floorId;
    }

    public int getTotalRooms() {
        return totalRooms;
    }

    public void setTotalRooms(int totalRooms) {
        this.totalRooms = totalRooms;
    }

    public int getNumberOfLabs() {
        return numberOfLabs;
    }

    public void setNumberOfLabs(int numberOfLabs) {
        this.numberOfLabs = numberOfLabs;
    }

    public String getOtherDetails() {
        return otherDetails;
    }

    public void setOtherDetails(String otherDetails) {
        this.otherDetails = otherDetails;
    }
}
