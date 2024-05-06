package com.example.equipmenthealth;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class EmployeDetails extends AppCompatActivity {
    private EditText usernameEditText;
    private Spinner designationEditText;
    private EditText emailEditText;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employe_details);

        usernameEditText = findViewById(R.id.username);
        designationEditText = findViewById(R.id.designation);
        emailEditText = findViewById(R.id.mail);

        dbHelper = new DBHelper(this);

        Button addButton = findViewById(R.id.next);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = usernameEditText.getText().toString().trim();
                String designation = designationEditText.getSelectedItem().toString().trim();
                String email = emailEditText.getText().toString().trim();

                if (TextUtils.isEmpty(username) ||
                        TextUtils.isEmpty(designation) || TextUtils.isEmpty(email)) {
                    Toast.makeText(EmployeDetails.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else {

                    boolean isInserted = dbHelper.addUser(username, username, email, designation);
                    if (isInserted) {
                        Toast.makeText(EmployeDetails.this, "Employee details added successfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(EmployeDetails.this, MangerHome.class);
                        startActivity(i);
                        finish();
                        clearFields();
                    } else {
                        Toast.makeText(EmployeDetails.this, "Failed to add employee details", Toast.LENGTH_SHORT).show();
                    }


                    clearFields();
                }
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);
        actionBar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.white)));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.back);
        actionBar.setDisplayShowTitleEnabled(false);
    }


    private void clearFields() {
        usernameEditText.setText("");
        designationEditText.setId(0);
        emailEditText.setText("");
    }
}
