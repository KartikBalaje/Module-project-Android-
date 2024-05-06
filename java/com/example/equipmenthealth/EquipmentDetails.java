package com.example.equipmenthealth;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.TextView;

import com.example.equipmenthealth.R;

public class EquipmentDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_details);

        Intent intent = getIntent();
        if (intent != null) {
            String name = intent.getStringExtra("name");
            String features = intent.getStringExtra("features");
            String purchaseDate = intent.getStringExtra("date_of_purchase");
            String servicesUndergone = intent.getStringExtra("services_undergone");
            String totalServices = intent.getStringExtra("total_services");
            int price = intent.getIntExtra("price", 100);
            String workingCondition = intent.getStringExtra("workingCondition");
            String endOfWarranty = intent.getStringExtra("eow");

            TextView nameTextView = findViewById(R.id.t1);
            TextView featuresTextView = findViewById(R.id.f2);
            TextView purchaseDateTextView = findViewById(R.id.f3);
            TextView servicesUndergoneTextView = findViewById(R.id.f4);
            TextView totalServicesTextView = findViewById(R.id.f7);
            TextView priceTextView = findViewById(R.id.f8);
            TextView workingConditionTextView = findViewById(R.id.f9);
            TextView endOfWarrantyTextView = findViewById(R.id.f10);

            nameTextView.setText(name);
            featuresTextView.setText("Features: " + features);
            purchaseDateTextView.setText("Date of purchase: " + purchaseDate);
            servicesUndergoneTextView.setText("Services Undergone: " + servicesUndergone);
            totalServicesTextView.setText("Total services: " + totalServices);
            priceTextView.setText("Price: " + price);
            workingConditionTextView.setText("Working Condition: " + workingCondition);
            endOfWarrantyTextView.setText("End of warranty: " + endOfWarranty);
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);
        actionBar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.white)));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.back);
        actionBar.setDisplayShowTitleEnabled(false);
    }

}