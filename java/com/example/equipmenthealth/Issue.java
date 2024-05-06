package com.example.equipmenthealth;

public class Issue {
    private int issueId;
    private String issueDate;
    private int equipmentId;
    private String status;
    private int assignedTo;
    private String description;
    private int labId;

    public Issue() {}

    public Issue(int labId, int issueId, String issueDate, int equipmentId, String status, int assignedTo, String description) {
        this.issueId = issueId;
        this.issueDate = issueDate;
        this.equipmentId = equipmentId;
        this.status = status;
        this.assignedTo = assignedTo;
        this.description = description;
        this.labId = labId;
    }

    public int getIssueId() {
        return issueId;
    }

    public void setIssueId(int issueId) {
        this.issueId = issueId;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(int assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLabId() {
        return labId;
    }

    public void setLabId(int labId) {
        this.labId = labId;
    }

    // Getters and setters
}
