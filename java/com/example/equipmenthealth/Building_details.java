package com.example.equipmenthealth;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class Building_details extends AppCompatActivity {

    private EditText buildingNameEditText;
    private EditText buildingIdEditText;
    private EditText noRoomsEditText;
    private EditText totalRoomsEditText;
    private EditText totalLabsEditText;
    Spinner collegeNameSpinner;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_details);

        dbHelper = new DBHelper(this);

        buildingNameEditText = findViewById(R.id.buildingname);
        buildingIdEditText = findViewById(R.id.bid);
        noRoomsEditText = findViewById(R.id.norooms);
        totalRoomsEditText = findViewById(R.id.totalrooms);
        totalLabsEditText = findViewById(R.id.totallabs);
        collegeNameSpinner = findViewById(R.id.collegeName);

        List<String> collegeNames = dbHelper.getAllCollegeNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, collegeNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        collegeNameSpinner.setAdapter(adapter);

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
                String buildingName = buildingNameEditText.getText().toString().trim();
                String buildingId = buildingIdEditText.getText().toString().trim();
                String noRooms = noRoomsEditText.getText().toString().trim();
                String totalRooms = totalRoomsEditText.getText().toString().trim();
                String totalLabs = totalLabsEditText.getText().toString().trim();

                String collegeName = collegeNameSpinner.getSelectedItem().toString();

                // Get college ID from the selected college name
                int collegeId = dbHelper.getCollegeIdByName(collegeName);

                // Check if any field is empty
                if (buildingName.isEmpty() || buildingId.isEmpty() || noRooms.isEmpty() || totalRooms.isEmpty() || totalLabs.isEmpty()) {
                    Toast.makeText(Building_details.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Insert building details into the database
                boolean isInserted = dbHelper.addBuildingDetails(buildingName, buildingId, noRooms, totalLabs, collegeId);
                if (isInserted) {
                    Toast.makeText(Building_details.this, "Building details added successfully", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Building_details.this, MangerHome.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(Building_details.this, "Failed to add building details", Toast.LENGTH_SHORT).show();

                    clearFields();
                }
            }
        });
    }
    private void clearFields() {
        buildingIdEditText.setText("");
        buildingIdEditText.setText("");
        noRoomsEditText.setText("");
        totalLabsEditText.setText("");
        totalLabsEditText.setText("");
    }

}
