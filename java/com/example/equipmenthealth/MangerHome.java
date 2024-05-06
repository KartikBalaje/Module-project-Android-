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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MangerHome extends AppCompatActivity {
    private boolean isAddMenuOpen = false;
    DBHelper dbHelper;

    Button ratingBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manger_home);
        RecyclerView recyclerViewManager = findViewById(R.id.managerHomeRecyclerView);

        ratingBtn = findViewById(R.id.ratingsBtn);

        ratingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MangerHome.this, Ratings.class);
                startActivity(i);
            }
        });

        dbHelper = new DBHelper(this);
        List<User> supervisorList = new ArrayList<>();
//        managerList.add(new ManagerHomeModel("John Doe", "University of XYZ"));
//        managerList.add(new ManagerHomeModel("Jane Smith", "College ABC"));

        supervisorList = dbHelper.getUsersByDesignation("supervisor");

        ManagerHomeAdapter adapter = new ManagerHomeAdapter(this, supervisorList);
        recyclerViewManager.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewManager.setAdapter(adapter);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);
        actionBar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.white)));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.back);
        actionBar.setDisplayShowTitleEnabled(false);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.manager_home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add) {
            return true;
        } else if (id == R.id.sub_employee) {
            startActivity(new Intent(this, EmployeDetails.class));
            return true;
        } else if (id == R.id.sub_equipment) {
            startActivity(new Intent(this, AdditionEquipmentDetails.class));
            return true;
        } else if (id == R.id.sub_lab) {
            startActivity(new Intent(this, LabAdditionDetails.class));
            return true;
        } else if (id == R.id.sub_building) {
            startActivity(new Intent(this, Building_details.class));
            return true;
        } else if (id == R.id.sub_floor) {
            startActivity(new Intent(this, AddFloor.class));
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