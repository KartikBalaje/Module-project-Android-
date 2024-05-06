package com.example.equipmenthealth;

import android.os.Parcelable;

import java.util.ArrayList;

public class Equipment {
    private int id, labId;
    private String name;
    private String purchaseDate;
    private int price;
    private String status;
    private String features;
    private String servicesUndergone;
    private String endOfWarranty;

    private boolean showView360Button = true;

    public Equipment(){}

    public Equipment(int id, int labId, String name, String purchaseDate, int price, String status, String features, String servicesUndergone, String endOfWarranty) {
        this.id = id;
        this.labId = labId;
        this.name = name;
        this.purchaseDate = purchaseDate;
        this.price = price;
        this.status = status;
        this.features = features;
        this.servicesUndergone = servicesUndergone;
        this.endOfWarranty = endOfWarranty;
    }


    public int getLabId() {
        return labId;
    }

    public void setLabId(int labId) {
        this.labId = labId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getServicesUndergone() {
        return servicesUndergone;
    }

    public void setServicesUndergone(String servicesUndergone) {
        this.servicesUndergone = servicesUndergone;
    }

    public String getEndOfWarranty() {
        return endOfWarranty;
    }

    public void setEndOfWarranty(String endOfWarranty) {
        this.endOfWarranty = endOfWarranty;
    }

    public boolean isShowView360Button() {
        return showView360Button;
    }

    public void setShowView360Button(boolean showView360Button) {
        this.showView360Button = showView360Button;
    }
}

