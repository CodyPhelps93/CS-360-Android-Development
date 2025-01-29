package com.example.weighttrackerappcodyphelps;


import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
import static com.example.weighttrackerappcodyphelps.DBHelper.TABLE_USER_INFO;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.Manifest;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import java.util.ArrayList;

public class WeightDataTracking extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayout linearLayout;
    LineChart lineChart;
    DBHelper myDB;
    RecyclerAdapter recyclerAdapter;
    EditText currWeightET, dateTime;
    Button addWeight, deleteWeight;
    ArrayList<String> userID, currWeight, prevWeight, date, goal;
    String firstName;
    Integer userAge, userGoal, userCurrWeight;
    SMSPermission smsPermission;

    private GestureDetectorCompat gestureDetectorCompat;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_sms_permission) {
            Intent sms = new Intent(this, SMSPermission.class);
            startActivity(sms);
            return true;
        }
        if (item.getItemId() == R.id.logout) {
            Intent LogOut = new Intent(this, MainActivity.class);
            startActivity(LogOut);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupLineChart() {
        ArrayList<Entry> entries = new ArrayList<>();

        for (int i = 0; i < currWeight.size(); i++) {
            float weight = Float.parseFloat(currWeight.get(i));
            entries.add(new Entry(i, weight));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Weight Data");
        LineData lineData = new LineData(dataSet);

        lineChart.setData(lineData);
        lineChart.invalidate(); // refresh
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weight_data_tracking);

        // variables initialization
        recyclerView = findViewById(R.id.weightRecyclerView);
        lineChart = findViewById(R.id.lineChart);
        currWeightET = findViewById(R.id.currentWeightEditText);
        dateTime = findViewById(R.id.dateAndTimeEditText);
        myDB = new DBHelper(WeightDataTracking.this);
        addWeight = findViewById(R.id.btnAddWeight);
        deleteWeight = findViewById(R.id.btnRemoveWeight);
        firstName = myDB.getUserName();
        userAge = myDB.getUserAge();
        userGoal = myDB.getUserGoal();
        userCurrWeight = myDB.getCurrentWeight();
        Log.d(TAG, "userCurrWeight: " + userCurrWeight);
        smsPermission = new SMSPermission();


        // Gestures
        linearLayout = findViewById(R.id.linearLayout);
        SwipeGestureListener swipeGestureListener = new SwipeGestureListener(this);
        gestureDetectorCompat = new GestureDetectorCompat(this, swipeGestureListener);


        linearLayout.setOnTouchListener((v, event) -> {
            Log.d("Touch", "onTouch: " + event);
            return gestureDetectorCompat.onTouchEvent(event);

        });

        // Add weight button logic
        addWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String editCurrWeight = currWeightET.getText().toString().trim();
                String editCurrDate = dateTime.getText().toString().trim();
                if (!editCurrWeight.isEmpty() && !editCurrDate.isEmpty()) {
                    long result = myDB.updateUserInfo(firstName, userAge, Integer.valueOf(currWeightET.getText().toString().trim()),
                            dateTime.getText().toString().trim(), userGoal);
                    if (result != -1) {
                        recyclerAdapter.notifyDataSetChanged();
                        // checks goal weight to send the sms
                        if (ContextCompat.checkSelfPermission(WeightDataTracking.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                            if (userCurrWeight <= userGoal) {

                                smsPermission.sendSMS();
                            } else {
                                Toast.makeText(WeightDataTracking.this, "SMS not enabled!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        currWeightET.getText().clear();
                        dateTime.getText().clear();
                        deleteDisplayData();
                        displayData();
                        setupLineChart();
                    }
                } else {
                    Toast.makeText(WeightDataTracking.this, "Please enter current weight and date!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Delete Button
        deleteWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = recyclerAdapter.getSelectedItemPosition();
                if (position != RecyclerView.NO_POSITION) {
                    long result = myDB.deleteItem(position); // Delete item from database
                    if (result != -1) {
                        recyclerAdapter.setSelectedItemPosition(RecyclerView.NO_POSITION);
                        Toast.makeText(WeightDataTracking.this, "Item deleted successfully", Toast.LENGTH_SHORT).show();
                        deleteDisplayData(); // clears array list so they can be repopulated.
                        displayData();
                        recyclerAdapter.notifyDataSetChanged();
                        setupLineChart();
                        if (myDB.getCount() == 1) {
                            myDB.resetIncrement(TABLE_USER_INFO);
                        }
                    } else {
                        Toast.makeText(WeightDataTracking.this, "Failed to delete item", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(WeightDataTracking.this, "Please select an item to delete", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Array for recycler view and line chart
        userID = new ArrayList<>();
        currWeight = new ArrayList<>();
        prevWeight = new ArrayList<>();
        date = new ArrayList<>();
        goal = new ArrayList<>();

        displayData();
        setupLineChart();


        // Initialize and set up the RecyclerView after populating the data
        recyclerAdapter = new RecyclerAdapter(WeightDataTracking.this, userID, currWeight, prevWeight, date, goal);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    void displayData() {
        Cursor cursor = myDB.readWeightData();
        if (cursor == null) {
            Toast.makeText(WeightDataTracking.this, "Error reading data", Toast.LENGTH_SHORT).show();
            return;
        }

        if (cursor.getCount() == 0) {
            Toast.makeText(WeightDataTracking.this, "No data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                userID.add(cursor.getString(0));
                currWeight.add(cursor.getString(1));
                prevWeight.add(cursor.getString(2));
                date.add(cursor.getString(3));
                goal.add(cursor.getString(4));
            }
        }
        cursor.close();

        // Log the data for testing
        Log.d("WeightDataTracking", "Current Weight: " + currWeight.toString());
        Log.d("WeightDataTracking", "Previous Weight: " + prevWeight.toString());
        Log.d("WeightDataTracking", "Date: " + date.toString());
    }
    void deleteDisplayData() {
        userID.clear();
        currWeight.clear();
        prevWeight.clear();
        date.clear();
        goal.clear();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean gestureDetected = gestureDetectorCompat.onTouchEvent(event);
        Log.d("SwipeGesture", "onTouchEvent: Gesture detected - " + gestureDetected);
        return super.onTouchEvent(event);
    }


}
