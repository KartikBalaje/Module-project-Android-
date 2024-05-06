package com.example.equipmenthealth;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

public class InProgress extends AppCompatActivity {
    ToggleButton toggleButton;
    SharedPreferences sharedPreferences;

    TextView collegeName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_progress);
        ToggleButton toggleButton = findViewById(R.id.toggleButton);
        TextView textView = findViewById(R.id.textView);
        collegeName = findViewById(R.id.collegeName);

        sharedPreferences = getSharedPreferences("UserDetails", MODE_PRIVATE);
        String designation = sharedPreferences.getString("designation", "");


        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    textView.setMaxLines(Integer.MAX_VALUE);
                } else {
                    textView.setMaxLines(3);
                }
            }
        });

        Intent intent = getIntent();
        int issueId = intent.getIntExtra("issueId", 0); // Default value 0, you can change it as needed
        String issueDate = intent.getStringExtra("issueDate");
        int equipmentId = intent.getIntExtra("equipmentId", 0);
        String status = intent.getStringExtra("status");
        int assignedTo = intent.getIntExtra("assignedTo", 0);
        String description = intent.getStringExtra("description");
        int labId = intent.getIntExtra("labId", -1);

        DBHelper dbHelper = new DBHelper(this);
        String collegeNameText = dbHelper.getCollegeNameByLabId(labId);
        Log.d("CollegeName", labId+" !!!"+collegeNameText);
        collegeName.setText(collegeNameText);
        textView.setText(description);
        Button approveButton = findViewById(R.id.approveButton);
        Button rejectButton = findViewById(R.id.rejectButton);

        Button callTech = findViewById(R.id.calltech);
        Button complete = findViewById(R.id.complete);

        callTech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(InProgress.this, TutorialsAndGuideLines.class);
                startActivity(i);
            }
        });

        TextView statusTv = findViewById(R.id.status);

        TextView t1 = findViewById(R.id.t1);

        if(designation.equals("supervisor")) {
            callTech.setVisibility(View.GONE);
            complete.setVisibility(View.GONE);
        } else {
            approveButton.setVisibility(View.GONE);
            rejectButton.setVisibility(View.GONE);
        }

        statusTv.setText(status);
        t1.setText("Equipment"+equipmentId);

        approveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateIssueStatus(issueId, "Pending");
                // You can also finish the activity or perform any other action here
            }
        });

        rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateIssueStatus(issueId, "Rejected");
                // You can also finish the activity or perform any other action here
            }
        });


        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);
        actionBar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.white)));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.back);
        actionBar.setDisplayShowTitleEnabled(false);

    }
    private void updateIssueStatus(int issueId, String status) {
        DBHelper dbHelper = new DBHelper(this);
        dbHelper.updateIssueStatus(issueId, status);

        Intent i = new Intent(InProgress.this, SupervisorHome.class);
        startActivity(i);
        finish();
    }
}