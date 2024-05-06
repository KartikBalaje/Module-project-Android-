package com.example.equipmenthealth;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class EqupimentList extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Button healthCheckBtn;
    TextView totalEquipments;
    DBHelper dbHelper;
    List<Equipment> equipmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equpiment_list);

        dbHelper = new DBHelper(this);

        healthCheckBtn = findViewById(R.id.healthCheckBtn);

        recyclerView = findViewById(R.id.R1);
        totalEquipments = findViewById(R.id.totalEquipments);
        equipmentList = new ArrayList<>();

        Intent i = getIntent();
        int labId = i.getIntExtra("labId", -1);

        Log.d("LabId", labId+"");

        equipmentList = dbHelper.getEquipmentsByLabId(labId);

        totalEquipments.setText("Total Equipments: "+ equipmentList.size());

        Log.d("size", equipmentList.size()+"");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);



        EquipmentAdapter adapter = new EquipmentAdapter(this, equipmentList);
        recyclerView.setAdapter(adapter);
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

        healthCheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(healthCheckBtn.getText().toString().toLowerCase().equals("health check")) {
                    for (Equipment equipment : equipmentList) {
                        equipment.setShowView360Button(false);
                    }
                    adapter.notifyDataSetChanged();
                    healthCheckBtn.setText("Next");
                } else {
                    Intent i = new Intent(EqupimentList.this, Details.class);
                    startActivity(i);
                    for (Equipment equipment : equipmentList) {
                        equipment.setShowView360Button(true);
                    }
                    adapter.notifyDataSetChanged();
                    healthCheckBtn.setText("Health Check");

                }
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);
        actionBar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.white)));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.back);
        actionBar.setDisplayShowTitleEnabled(false);
    }
}
