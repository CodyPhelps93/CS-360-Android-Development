package com.example.weighttrackerappcodyphelps;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class create_account extends AppCompatActivity {

    EditText name_input, username_input, userPass_input, userWeight_input, userAge_input, date_input, userGoal_input;
    Button create_account_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);

        name_input = findViewById(R.id.addUserName);
        username_input = findViewById(R.id.addUserId);
        userAge_input = findViewById(R.id.addAge);
        userPass_input = findViewById(R.id.addUserPassword);
        userWeight_input = findViewById(R.id.addCurrWeight);
        date_input = findViewById(R.id.dateInput);
        userGoal_input = findViewById(R.id.goalWeight);
        create_account_button = findViewById(R.id.createAccountButton);

        create_account_button.setOnClickListener(v -> {
            try {
                String name = name_input.getText().toString().trim();
                String username = username_input.getText().toString().trim();
                String ageStr = userAge_input.getText().toString().trim();
                String password = userPass_input.getText().toString().trim();
                String weightStr = userWeight_input.getText().toString().trim();
                String date = date_input.getText().toString().trim();
                String goalStr = userGoal_input.getText().toString().trim();

                if (name.isEmpty() || username.isEmpty() || ageStr.isEmpty() || password.isEmpty() || weightStr.isEmpty() || date.isEmpty() || goalStr.isEmpty()) {
                    throw new Exception("Please make sure all fields are entered");
                }

                int age = Integer.valueOf(ageStr);
                int weight = Integer.valueOf(weightStr);
                int goal = Integer.valueOf(goalStr);

                DBHelper myDB = new DBHelper(create_account.this);
                if (!myDB.usernameExist(username)) {
                    myDB.addUser(username, password);
                    myDB.addUserInfo(name, age, weight, date, goal);
                } else {
                    Toast.makeText(create_account.this, "Username already exists", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(create_account.this, "Invalid input format", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(create_account.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
