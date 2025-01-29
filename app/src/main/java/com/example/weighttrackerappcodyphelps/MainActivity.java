package com.example.weighttrackerappcodyphelps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText username, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginBtn = findViewById(R.id.btnLogin);
        Button createAcct = findViewById(R.id.btnCreateAccount);

        loginBtn.setOnClickListener(this);
        createAcct.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        DBHelper myDB = new DBHelper(MainActivity.this);
        username = findViewById(R.id.etUsername);
        password = findViewById(R.id.etPassword);

        if (v.getId() == R.id.btnLogin) {
            // Handle login button click
            if (!username.getText().toString().isEmpty() && !password.getText().toString().isEmpty()) {
                if (myDB.checkUser(username.getText().toString().trim(), password.getText().toString().trim())) {
                    Intent loginIntent = new Intent(MainActivity.this, WeightDataTracking.class);
                    startActivity(loginIntent);
                } else {
                    Toast.makeText(MainActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "Please Enter Username Or Password", Toast.LENGTH_SHORT).show();
            }
        } else if (v.getId() == R.id.btnCreateAccount) {
            Intent createAccountIntent = new Intent(MainActivity.this, create_account.class);
            startActivity(createAccountIntent);
        }
    }
}