package com.example.equipmenthealth;

public class EquipmentModel {
    private String equipmentName, purchaseDate;
    private String otherDetails, status;

    private int equipmentId, price;

    public EquipmentModel(String equipmentName, String purchaseDate, String otherDetails, int equipmentId, int price, String status) {
        this.equipmentName = equipmentName;
        this.purchaseDate = purchaseDate;
        this.otherDetails = otherDetails;
        this.equipmentId = equipmentId;
        this.price = price;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getOtherDetails() {
        return otherDetails;
    }

    public void setOtherDetails(String otherDetails) {
        this.otherDetails = otherDetails;
    }

    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
