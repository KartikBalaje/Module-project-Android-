package com.example.equipmenthealth;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class IssuesList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private IssueAdapter adapter;
    private List<Issue> issues;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issues_list);

        dbHelper = new DBHelper(this);

        recyclerView = findViewById(R.id.issueCards);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Dummy data for demonstration
        issues = new ArrayList<>();
        issues = dbHelper.getAllIssues();
        SharedPreferences sf = getSharedPreferences("UserDetails", MODE_PRIVATE);
        String des = sf.getString("designation", "");
        adapter = new IssueAdapter(issues, this);

        if(des.toLowerCase().equals("supervisor")) {
            List<Issue> filteredIssues = new ArrayList<>();
            for (Issue issue : issues) {
                if (issue.getStatus().equals("Not Approved")) {
                    filteredIssues.add(issue);
                }
            }

            adapter = new IssueAdapter(filteredIssues, this);
        }
        recyclerView.setAdapter(adapter);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);
        actionBar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.white)));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.back);
        actionBar.setDisplayShowTitleEnabled(false);
    }
}