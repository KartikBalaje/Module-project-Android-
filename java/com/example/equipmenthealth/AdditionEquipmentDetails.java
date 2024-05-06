package com.example.equipmenthealth;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AdditionEquipmentDetails extends AppCompatActivity {
    private DBHelper dbHelper;
    private EditText eqNameEditText;
    private EditText purchaseDateEditText;
    private EditText featuresEditText;
    private EditText otherDetailsEditText;
    private EditText priceEditText;
    private Spinner statusSpinner;
    private EditText servicesUndergoneEditText;
    private EditText endOfWarrantyEditText;
    private List<Lab> labsList;
    private Map<String, Integer> labIdMap;

    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    Spinner labNameSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addition_equipment_details);

        dbHelper = new DBHelper(this);

        eqNameEditText = findViewById(R.id.eqname);
        purchaseDateEditText = findViewById(R.id.st);
        featuresEditText = findViewById(R.id.ft);
        otherDetailsEditText = findViewById(R.id.srcu);
        priceEditText = findViewById(R.id.price);
        statusSpinner = findViewById(R.id.status);
        servicesUndergoneEditText = findViewById(R.id.servicesUndergone);
        endOfWarrantyEditText = findViewById(R.id.end_of_warranty);
        labNameSpinner = findViewById(R.id.labName);

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        endOfWarrantyEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        purchaseDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog2();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);
        actionBar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.white)));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.back);
        actionBar.setDisplayShowTitleEnabled(true);

        populateLabSpinner();

        Button addButton = findViewById(R.id.next);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eqName = eqNameEditText.getText().toString().trim();
                String purchaseDate = purchaseDateEditText.getText().toString().trim();
                String features = featuresEditText.getText().toString().trim();
                String otherDetails = otherDetailsEditText.getText().toString().trim();
                String priceStr = priceEditText.getText().toString().trim();
                String status = statusSpinner.getSelectedItem().toString();
                String servicesUndergone = servicesUndergoneEditText.getText().toString().trim();
                String endOfWarranty = endOfWarrantyEditText.getText().toString().trim();

                int labId = labIdMap.get(labNameSpinner.getSelectedItem().toString());

                // Check if any field is empty
                if (TextUtils.isEmpty(eqName) || TextUtils.isEmpty(purchaseDate) || TextUtils.isEmpty(features) ||
                        TextUtils.isEmpty(otherDetails) || TextUtils.isEmpty(priceStr) || TextUtils.isEmpty(servicesUndergone) ||
                        TextUtils.isEmpty(endOfWarranty)) {
                    Toast.makeText(AdditionEquipmentDetails.this, "All fields are required", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Convert price to double
                int price = Integer.parseInt(priceStr);


                Log.d("Additionl prie id", price+"");
                // Insert equipment details into the database
                boolean isInserted = dbHelper.addEquipmentDetails(eqName, purchaseDate, price, status, features, servicesUndergone, endOfWarranty, labId);
                if (isInserted) {
                    Toast.makeText(AdditionEquipmentDetails.this, "Equipment details added successfully", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(AdditionEquipmentDetails.this, MangerHome.class);
                    startActivity(i);
                    finish();

                    clearFields();
                } else {
                    Toast.makeText(AdditionEquipmentDetails.this, "Failed to add equipment details", Toast.LENGTH_SHORT).show();
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
                updateDateInView();
            }
        };

        new DatePickerDialog(this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateDateInView() {
        endOfWarrantyEditText.setText(dateFormat.format(calendar.getTime()));
    }

    private void showDatePickerDialog2() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateInView2();
            }
        };

        new DatePickerDialog(this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateDateInView2() {
        purchaseDateEditText.setText(dateFormat.format(calendar.getTime()));
    }

    private void clearFields() {
        eqNameEditText.setText("");
        purchaseDateEditText.setText("");
        featuresEditText.setText("");
        otherDetailsEditText.setText("");
        priceEditText.setText("");
        servicesUndergoneEditText.setText("");
        endOfWarrantyEditText.setText("");
    }

    private void populateLabSpinner() {
        labsList = dbHelper.getAllLabs();
        List<String> labNames = new ArrayList<>();
        labIdMap = new HashMap<>();
        for (Lab lab : labsList) {
            labNames.add(lab.getLabName());
            labIdMap.put(lab.getLabName(), lab.getLabId());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, labNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        labNameSpinner.setAdapter(adapter);
    }

}
