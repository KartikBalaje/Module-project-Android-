package com.example.equipmenthealth;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NotifyIssues extends AppCompatActivity {

    private Spinner progressSpinner;
    private Spinner eidSpinner;
    private Button datePickerButton;
    private DatePickerDialog datePickerDialog;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify_issues);

        progressSpinner = findViewById(R.id.progressSpinner);
        eidSpinner = findViewById(R.id.eid);
        datePickerButton = findViewById(R.id.datepicker);
        calendar = Calendar.getInstance();

        // Set initial visibility of date picker button
        datePickerButton.setVisibility(View.GONE);

        progressSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedStatus = parent.getItemAtPosition(position).toString();
                if (selectedStatus.equals("Completed")) {
                    datePickerButton.setVisibility(View.VISIBLE);
                } else {
                    datePickerButton.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                datePickerButton.setVisibility(View.GONE);
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);
        actionBar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.white)));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.back);
        actionBar.setDisplayShowTitleEnabled(false);
        // Populate EID spinner with values from the Issue table
        DBHelper dbHelper = new DBHelper(this);
        List<Integer> equipmentIds = dbHelper.getPendingEquipmentIds();
        List<String> equipmentIdStrings = new ArrayList<>();
        for (Integer equipmentId : equipmentIds) {
            equipmentIdStrings.add(String.valueOf(equipmentId));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, equipmentIdStrings);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eidSpinner.setAdapter(adapter);

        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(NotifyIssues.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                // Handle the selected date
                                String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                                datePickerButton.setText(selectedDate);
                            }
                        }, year, month, dayOfMonth);

                datePickerDialog.show();
            }
        });

        Button submitButton = findViewById(R.id.signUpButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedStatus = progressSpinner.getSelectedItem().toString();
                int equipmentId = Integer.parseInt(eidSpinner.getSelectedItem().toString());
                String deliveryDate = datePickerButton.getText().toString();

                if (selectedStatus.equals("Completed") && !deliveryDate.isEmpty()) {
                    dbHelper.updateIssueStatusByEquipmentId(equipmentId, "Completed", deliveryDate);
                } else {
                    dbHelper.updateIssueStatusByEquipmentId(equipmentId, "Pending", null);
                }

                Intent i = new Intent(NotifyIssues.this, SupervisorHome.class);
                startActivity(i);
                finish();
            }
        });
    }
}
