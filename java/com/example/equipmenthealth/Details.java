package com.example.equipmenthealth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Details extends AppCompatActivity {
    EditText issueDateEditText, problemEditText;
    Button reportButton;
    Calendar calendar;
    private Spinner labSpinner;
    private Spinner equipmentSpinner;
    private List<Lab> labsList;
    private List<Equipment> equipmentList;

    private int selectedLabId = -1;
    private int selectedEquipmentId = -1;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        issueDateEditText = findViewById(R.id.iDate);
        problemEditText = findViewById(R.id.iP);
        reportButton = findViewById(R.id.signUpButton);
        calendar = Calendar.getInstance();

        labSpinner = findViewById(R.id.en);
        equipmentSpinner = findViewById(R.id.ln);

        dbHelper = new DBHelper(this);

        loadLabs();

        labSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    selectedLabId = labsList.get(position - 1).getLabId();
                    loadEquipment(selectedLabId);
                } else {
                    selectedLabId = -1;
                    equipmentSpinner.setAdapter(null);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        equipmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    selectedEquipmentId = equipmentList.get(position - 1).getId();
                } else {
                    selectedEquipmentId = -1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        issueDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String issueDate = issueDateEditText.getText().toString();
                String problemDescription = problemEditText.getText().toString();

                if (issueDate.isEmpty() || problemDescription.isEmpty()) {
                    Toast.makeText(Details.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    DBHelper dbHelper = new DBHelper(Details.this);
                    Log.d("SelectedLabId", selectedLabId+"");
                    boolean isInserted = dbHelper.insertIssue(issueDate, selectedLabId, selectedEquipmentId, "Not Approved", -1, problemDescription);

                    if (isInserted) {
                        Toast.makeText(Details.this, "Issue reported successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(Details.this, "Failed to report issue", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void showDatePickerDialog() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        new DatePickerDialog(this, dateSetListener, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        issueDateEditText.setText(sdf.format(calendar.getTime()));
    }

    private void loadLabs() {
        labsList = dbHelper.getAllLabs();

        List<String> collegeNames = new ArrayList<>();
        collegeNames.add("Select a lab");

        for (Lab l : labsList) {
            collegeNames.add(l.getLabName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, collegeNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        labSpinner.setAdapter(adapter);
    }

    private void loadEquipment(int labId) {
        equipmentList = dbHelper.getEquipmentsByLabId(labId);

        List<String> buildingNames = new ArrayList<>();
        buildingNames.add("Select the equipment");

        for (Equipment e : equipmentList) {
            buildingNames.add(e.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, buildingNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        equipmentSpinner.setAdapter(adapter);
    }
}
