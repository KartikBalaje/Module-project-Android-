package com.example.equipmenthealth;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Ratings extends AppCompatActivity {

    DBHelper dbHelper;
    private List<Rating> ratings;
    private RatingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings);

        dbHelper = new DBHelper(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);
        actionBar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.white)));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.back);
        actionBar.setDisplayShowTitleEnabled(false);

        RecyclerView recyclerView = findViewById(R.id.individualRating);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        List<String> usernames = dbHelper.getUsernamesFromAssignedTo();
        ratings = new ArrayList<>();
        for (String username : usernames) {
            int userId = dbHelper.getUserIdByUsername(username);
            ratings.add(new Rating(userId, 0.0f));
        }

        adapter = new RatingAdapter(Ratings.this, ratings);
        recyclerView.setAdapter(adapter);

        // Handle submit button click
        Button submitButton = findViewById(R.id.submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < ratings.size(); i++) {
                    Rating rating = ratings.get(i);
                    dbHelper.insertOrUpdateRating(rating.getUserId(), rating.getRating());
                }

                Intent i = new Intent(Ratings.this, MangerHome.class);
                startActivity(i);
                finish();
                Toast.makeText(Ratings.this, "Submitted", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
