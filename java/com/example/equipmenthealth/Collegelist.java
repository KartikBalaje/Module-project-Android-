package com.example.equipmenthealth;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class Collegelist extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CollegeAdapter adapter;
    private List<College> collegeList;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collegelist);

        dbHelper = new DBHelper(this);

        // Initialize the RecyclerView and layout manager
        recyclerView = findViewById(R.id.collegeCards);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Populate the college list (you'll need to replace this with your actual list of colleges)
        collegeList = new ArrayList<>();
        collegeList = dbHelper.getAllColleges();
        adapter = new CollegeAdapter(this, collegeList);

        // Set the adapter on the RecyclerView
        recyclerView.setAdapter(adapter);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);
        actionBar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.white)));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.back);
        actionBar.setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.student_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nots) {
            Intent i = new Intent(this, IssuesList.class);
            startActivity(i);
            return true;
        } else if(id == R.id.action_logout) {
            Intent i = new Intent(this, Index.class);
            getSharedPreferences("UserDetails", MODE_PRIVATE).edit().clear();
            startActivity(i);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
