package com.example.equipmenthealth;

public class LabCardModel {
    public LabCardModel(String labName, int labId, int floorNo, int totalRooms, String buildingName, int roomNo) {
        this.labName = labName;
        this.labId = labId;
        this.roomNo = roomNo;
        this.totalRooms = totalRooms;
        this.buildingName = buildingName;
        this.floorNo = floorNo;
    }

    private String labName, buildingName;
    private int labId, floorNo, totalRooms, roomNo, collegeId;

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public int getFloorNo() {
        return floorNo;
    }

    public void setFloorNo(int floorNo) {
        this.floorNo = floorNo;
    }

    public int getTotalRooms() {
        return totalRooms;
    }

    public void setTotalRooms(int totalRooms) {
        this.totalRooms = totalRooms;
    }

    public int getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(int roomNo) {
        this.roomNo = roomNo;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }

    public int getLabId() {
        return labId;
    }

    public void setLabId(int labId) {
        this.labId = labId;
    }
}
