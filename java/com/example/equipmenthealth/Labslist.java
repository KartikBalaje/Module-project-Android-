package com.example.equipmenthealth;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;


import java.util.ArrayList;
import java.util.List;

public class Labslist extends AppCompatActivity {
    private RecyclerView r1;

    private static final String COLUMN_LAB_ID = "lab_id";
    private static final String COLUMN_BUILDING_NAME = "building_name";
    private static final String COLUMN_FLOOR_NO = "floor_no";
    private static final String COLUMN_LAB_NAME = "lab_name";
    private static final String COLUMN_ROOM_NO = "room_no";
    private static final String COLUMN_TOTAL_ROOMS = "total_rooms";

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labslist);
        r1=findViewById(R.id.R1);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);
        actionBar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.white)));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.back);
        actionBar.setDisplayShowTitleEnabled(false);
        dbHelper = new DBHelper(this);

        Intent intent = getIntent();
        int collegeId = intent.getIntExtra("collegeId", -1);

        List<Lab> labList = dbHelper.getLabsListById(collegeId);

        LabCardAdapter adapter = new LabCardAdapter(this,labList);
        r1.setAdapter(adapter);
        r1.setLayoutManager(new GridLayoutManager(this, 2));
        SearchView searchView = findViewById(R.id.searchview);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return true;
            }
        });
    }
    @SuppressLint("Range")
    private List<LabCardModel> getAllLabsFromDatabase() {
        List<LabCardModel> labList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM lab_details", null);
        if (cursor.moveToFirst()) {
            do {
                int labId = cursor.getInt(cursor.getColumnIndex(COLUMN_LAB_ID));
                String buildingName = cursor.getString(cursor.getColumnIndex(COLUMN_BUILDING_NAME));
                int floorNo = cursor.getInt(cursor.getColumnIndex(COLUMN_FLOOR_NO));
                String labName = cursor.getString(cursor.getColumnIndex(COLUMN_LAB_NAME));
                int roomNo = cursor.getInt(cursor.getColumnIndex(COLUMN_ROOM_NO));
                int totalRooms = cursor.getInt(cursor.getColumnIndex(COLUMN_TOTAL_ROOMS));

                LabCardModel labCardModel = new LabCardModel(labName, labId, floorNo, totalRooms, buildingName, roomNo);
                labList.add(labCardModel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return labList;
    }
}