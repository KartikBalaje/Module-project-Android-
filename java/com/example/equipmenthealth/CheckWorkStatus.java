package com.example.equipmenthealth;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class CheckWorkStatus extends AppCompatActivity {
    private RecyclerView recyclerView;
    private WorkStatusAdapter adapter;
    private List<WorkStatusModel> workStatusList;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_work_status);

        dbHelper = new DBHelper(this);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Pending"));
        tabLayout.addTab(tabLayout.newTab().setText("Completed"));

        recyclerView = findViewById(R.id.workcards);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        workStatusList = new ArrayList<>();

        adapter = new WorkStatusAdapter(workStatusList, CheckWorkStatus.this);
        recyclerView.setAdapter(adapter);

        loadIssues("Pending");
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        loadIssues("Pending");
                        break;
                    case 1:
                        loadIssues("Completed");
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });


        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);
        actionBar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.white)));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.back);
        actionBar.setDisplayShowTitleEnabled(false);

    }

    private void loadIssues(String status) {
        workStatusList.clear();
        List<Issue> issues = dbHelper.getAllIssues();

        for (Issue issue : issues) {
            Log.d("statussss", issue.getStatus());
            if (issue.getStatus().equals(status)) {
                Equipment equipment = dbHelper.getEquipmentById(issue.getEquipmentId());
                User user = dbHelper.getUserById(issue.getAssignedTo());

                Lab lab = null;
                int labId =  issue.getLabId();

                String username = "";
                if(user != null) {
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
                        issue.getIssueId()+""
                ));
            }
        }

        adapter.notifyDataSetChanged();
    }
}
