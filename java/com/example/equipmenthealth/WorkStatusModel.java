package com.example.equipmenthealth;

public class WorkStatusModel {
    private String equipmentName;
    private String id;
    private String status;
    private String lab;
    private String date;
    private String time;
    private String technicianName;
    private String jobId;

    public WorkStatusModel(String equipmentName, String id, String status, String lab, String date, String time, String technicianName, String jobId) {
        this.equipmentName = equipmentName;
        this.id = id;
        this.status = status;
        this.lab = lab;
        this.date = date;
        this.time = time;
        this.technicianName = technicianName;
        this.jobId = jobId;
    }

    // Getters
    public String getEquipmentName() {
        return equipmentName;
    }

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getLab() {
        return lab;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getTechnicianName() {
        return technicianName;
    }

    public String getJobId() {
        return jobId;
    }
}

