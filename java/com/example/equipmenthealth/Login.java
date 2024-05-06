package com.example.equipmenthealth;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    private EditText usernameEditText, passwordEditText;

    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.loginBtn);
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);
        actionBar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.white)));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.back);
        actionBar.setDisplayShowTitleEnabled(false);

        TextView signupTextView = findViewById(R.id.ac1);
        signupTextView.setMovementMethod(LinkMovementMethod.getInstance());

        SpannableString spannableString = new SpannableString(signupTextView.getText());
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {

                Intent intent = new Intent(getApplicationContext(), Signup.class);
                startActivity(intent);
            }
        };
        int start = signupTextView.getText().toString().indexOf("Sign up");
        int end = start + "Sign up".length();
        spannableString.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        signupTextView.setText(spannableString);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Login.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();

                } else {

                    DBHelper dbHelper = new DBHelper(getApplicationContext());
                    User user = dbHelper.getUserDetails(username, password);

                    if (user != null) {
                        saveUserDetails(user);
                        Log.d("User Designation", user.getDesignation());
                        if(user.getDesignation().toLowerCase().equals("user")) {
                            Intent intent = new Intent(Login.this, Collegelist.class);
                            startActivity(intent);
                            finish();
                        } else if(user.getDesignation().toLowerCase().equals("manager")) {
                            Intent intent = new Intent(Login.this, MangerHome.class);
                            startActivity(intent);
                            finish();
                        }
                        else if(user.getDesignation().toLowerCase().equals("supervisor")) {
                            Intent intent = new Intent(Login.this, SupervisorHome.class);
                            startActivity(intent);
                            finish();
                        }
                        else if(user.getDesignation().toLowerCase().equals("technician")) {
                            Intent intent = new Intent(Login.this, TechnicianHome.class);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Toast.makeText(Login.this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            private void saveUserDetails(User user) {
                SharedPreferences sharedPreferences = getSharedPreferences("UserDetails", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("id", user.getId());
                editor.putString("username", user.getUsername());
                editor.putString("email", user.getEmail());
                editor.putString("designation", user.getDesignation());
                editor.apply();
            }
        });




    }
}