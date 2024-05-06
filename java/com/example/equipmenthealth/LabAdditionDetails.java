package com.example.equipmenthealth;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class LabAdditionDetails extends AppCompatActivity {
    private Spinner collegeSpinner;
    private Spinner buildingSpinner;
    private Spinner floorSpinner;
    private EditText labNameEditText;
    private EditText roomNoEditText;
    private EditText totalRoomsEditText;
    private EditText otherDetailsEditText;
    private DBHelper dbHelper;
    private List<College> collegesList;
    private List<Building> buildingsList;
    private List<Floor> floorsList;

    private int selectedCollegeId = -1;
    private int selectedBuildingId = -1;
    private int selectedFloorId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_addition_details);

        dbHelper = new DBHelper(this);

        collegeSpinner = findViewById(R.id.collegeName);
        buildingSpinner = findViewById(R.id.buildingname);
        floorSpinner = findViewById(R.id.flno);
        labNameEditText = findViewById(R.id.labname);
        roomNoEditText = findViewById(R.id.roomno);
        totalRoomsEditText = findViewById(R.id.totalroom);
        otherDetailsEditText = findViewById(R.id.otherDetails);

        // Load colleges into collegeSpinner
        loadColleges();

        collegeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    selectedCollegeId = collegesList.get(position - 1).getCollegeId();
                    loadBuildings(selectedCollegeId);
                } else {
                    selectedCollegeId = -1;
                    buildingSpinner.setAdapter(null);
                    floorSpinner.setAdapter(null);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        buildingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    selectedBuildingId = buildingsList.get(position - 1).getBuildingId();
                    loadFloors(selectedBuildingId);
                } else {
                    selectedBuildingId = -1;
                    floorSpinner.setAdapter(null);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        floorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    selectedFloorId = floorsList.get(position - 1).getFloorId();
                } else {
                    selectedFloorId = -1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);
        actionBar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.white)));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.back);
        actionBar.setDisplayShowTitleEnabled(false);

        Button addButton = findViewById(R.id.next);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String labName = labNameEditText.getText().toString().trim();
                int roomNo = Integer.parseInt(roomNoEditText.getText().toString().trim());
                int totalRooms = Integer.parseInt(totalRoomsEditText.getText().toString().trim());
                String otherDetails = otherDetailsEditText.getText().toString().trim();

                Log.d("labname", labName);
                Log.d("roomNo", roomNo+"");
                Log.d("totalROoms", totalRooms+"");
                Log.d("other", otherDetails);
                Log.d(selectedCollegeId+"", "Selected college");
                Log.d(selectedBuildingId+"", "Selected building");

                if (TextUtils.isEmpty(labName) || TextUtils.isEmpty(roomNo+"") || TextUtils.isEmpty(totalRooms+"") || TextUtils.isEmpty(otherDetails) || selectedCollegeId == -1 || selectedBuildingId == -1) {
                    Toast.makeText(LabAdditionDetails.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else {
                    addLabDetails(selectedCollegeId, selectedBuildingId, labName, roomNo, totalRooms, otherDetails, selectedFloorId);
                }
            }
        });
    }

    private void loadColleges() {
        collegesList = dbHelper.getAllColleges();

        List<String> collegeNames = new ArrayList<>();
        collegeNames.add("Select a college");

        for (College college : collegesList) {
            collegeNames.add(college.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, collegeNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        collegeSpinner.setAdapter(adapter);
    }

    private void loadBuildings(int collegeId) {
        buildingsList = dbHelper.getBuildingsByCollegeId(collegeId);

        List<String> buildingNames = new ArrayList<>();
        buildingNames.add("Select a building");

        for (Building building : buildingsList) {
            buildingNames.add(building.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, buildingNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        buildingSpinner.setAdapter(adapter);
    }

    private void loadFloors(int buildingId) {
        floorsList = dbHelper.getFloorsByBuildingId(buildingId);

        List<String> floorNames = new ArrayList<>();
        floorNames.add("Select a floor");

        for (Floor floor : floorsList) {
            floorNames.add(floor.getFloorNumber()+"");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, floorNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        floorSpinner.setAdapter(adapter);
    }

    private void addLabDetails(int selectedCollegeId, int buildingId, String labName, int roomNo, int totalRooms, String otherDetails, int floorId) {
        boolean result = dbHelper.addLabDetails(selectedCollegeId, buildingId, labName, floorId, totalRooms, otherDetails,roomNo);
        if (result) {
            Toast.makeText(LabAdditionDetails.this, "Lab details added successfully", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(LabAdditionDetails.this, MangerHome.class);
            startActivity(i);
            finish();
            clearFields();
        } else {
            Toast.makeText(LabAdditionDetails.this, "Failed to add lab details", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFields() {
        labNameEditText.setText("");
        roomNoEditText.setText("");
        totalRoomsEditText.setText("");
        otherDetailsEditText.setText("");
    }
}
