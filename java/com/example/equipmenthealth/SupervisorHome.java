package com.example.equipmenthealth;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class SupervisorHome extends AppCompatActivity {

    Button supervisorBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supervisor_home);

        supervisorBtn = findViewById(R.id.workStatusBtn);
        supervisorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SupervisorHome.this, CheckWorkStatus.class);
                startActivity(intent);
            }
        });

        Button issuesBtn  = findViewById(R.id.isssuesBtn);

        issuesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SupervisorHome.this, IssuesList.class);
                startActivity(intent);
            }
        });


        Button notifyIssuesButton = findViewById(R.id.Technicianloginbtn);
        notifyIssuesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SupervisorHome.this, NotifyIssues.class);
                startActivity(intent);
            }
        });
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