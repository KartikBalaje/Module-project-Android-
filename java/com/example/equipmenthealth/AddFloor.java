package com.example.equipmenthealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddFloor extends AppCompatActivity {
    private Spinner collegeSpinner;
    private Spinner buildingSpinner;
    private EditText floorNumberEditText;
    private EditText noOfRoomsEditText;
    private EditText noOfLabsEditText;
    private EditText otherDetailsEditText;
    private DBHelper dbHelper;
    private List<College> collegesList;
    private List<Building> buildingsList;

    private int selectedCollegeId = -1;
    private int selectedBuildingId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_floor);

        dbHelper = new DBHelper(this);

        collegeSpinner = findViewById(R.id.collegeName);
        buildingSpinner = findViewById(R.id.buildingname);
        floorNumberEditText = findViewById(R.id.fNumber);
        noOfRoomsEditText = findViewById(R.id.norooms);
        noOfLabsEditText = findViewById(R.id.totalLabs);
        otherDetailsEditText = findViewById(R.id.otherDetails);

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
                } else {
                    selectedBuildingId = -1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Button addButton = findViewById(R.id.next);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int floorNumber = Integer.parseInt(floorNumberEditText.getText().toString());
                int noOfRooms = Integer.parseInt(noOfRoomsEditText.getText().toString());
                int noOfLabs = Integer.parseInt(noOfLabsEditText.getText().toString());
                String otherDetails = otherDetailsEditText.getText().toString();

                if (selectedCollegeId == -1 || selectedBuildingId == -1) {
                    Toast.makeText(AddFloor.this, "Please select a college and building", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Add floor details to database
                boolean isInserted = dbHelper.addFloorDetails(floorNumber, noOfRooms, noOfLabs, selectedCollegeId, selectedBuildingId, otherDetails);
                if (isInserted) {

                    Toast.makeText(AddFloor.this, "Floor details added successfully", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(AddFloor.this, MangerHome.class);
                    startActivity(i);
                    finish();
                    clearFields();
                } else {
                    Toast.makeText(AddFloor.this, "Failed to add floor details", Toast.LENGTH_SHORT).show();
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

    private void clearFields() {
        floorNumberEditText.setText("");
        noOfRoomsEditText.setText("");
        noOfLabsEditText.setText("");
        otherDetailsEditText.setText("");
    }
}
