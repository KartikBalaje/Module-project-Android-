package com.example.equipmenthealth;

public class Rating {
    private int userId;
    private float rating;

    public Rating(int userId, float rating) {
        this.userId = userId;
        this.rating = rating;
    }

    public int getUserId() {
        return userId;
    }

    public float getRating() {
        return rating;
    }
}

