package com.example.equipmenthealth;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class TechnicianHome extends AppCompatActivity {
    private RecyclerView recyclerView;
    private WorkStatusAdapter issueAdapter;
    private List<Issue> issueList;
    private List<WorkStatusModel> workStatusList;

    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technician_home);

        dbHelper = new DBHelper(this);

        recyclerView = findViewById(R.id.recyclerView);
        issueList = new ArrayList<>();
        workStatusList = new ArrayList<>();

        SharedPreferences sf = getSharedPreferences("UserDetails", MODE_PRIVATE);

        issueList = dbHelper.getAllIssues();
        for (Issue issue : issueList) {
            if(issue.getAssignedTo() == sf.getInt("id", -1)) {
                Equipment equipment = dbHelper.getEquipmentById(issue.getEquipmentId());
                User user = dbHelper.getUserById(issue.getAssignedTo());

                Lab lab = null;
                int labId = issue.getLabId();

                String username = "";
                if (user != null) {
                    username = user.getUsername();
                }
                lab = dbHelper.getLabById(labId);
                workStatusList.add(new WorkStatusModel(
                        equipment.getName(),
                        "TD" + issue.getEquipmentId(),
                        issue.getStatus(),
                        lab.getLabName(),
                        issue.getIssueDate(),
                        "",
                        username,
                        issue.getIssueId() + ""
                ));
            }
        }

        issueAdapter = new WorkStatusAdapter(workStatusList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(issueAdapter);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);
        actionBar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.white)));
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.spervisor_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_logout) {
            Intent i = new Intent(this, Index.class);
            getSharedPreferences("UserDetails", MODE_PRIVATE).edit().clear();
            startActivity(i);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}