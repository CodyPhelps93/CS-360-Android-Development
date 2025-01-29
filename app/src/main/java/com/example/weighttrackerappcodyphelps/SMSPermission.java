package com.example.weighttrackerappcodyphelps;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class SMSPermission extends AppCompatActivity {
    private static final int SMS_PERMISSION_CODE = 1001;
    public DBHelper myDB;
    private TextView smsTextView;
    private Button smsButton;
    private Handler handler = new Handler();
    private Runnable runnable;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_permission);

        myDB = new DBHelper(this);
        smsTextView = findViewById(R.id.smsViewTextView);
        smsButton = findViewById(R.id.smsPermissionBtn);

        smsButton.setOnClickListener(v -> requestSmsPermission());

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            smsTextView.setText("SMS Enabled");
        } else {
            smsTextView.setText("Click below to enable SMS to receive a text notification when you hit you goal weight!");

        }
    }


    void requestSmsPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SMS_PERMISSION_CODE);
        } else {
            smsTextView.setText("SMS Enabled");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == SMS_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                smsTextView.setText("SMS Enabled!");
            } else {
                smsTextView.setText("Click below to enable SMS to receive a text notification when you hit you goal weight!");
            }
        }
    }


    public void sendSMS() {

            String message = "Congratulations! You have reached your goal weight!";
            String phoneNumber = "5554";

            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            } catch (Exception e) {
                Toast.makeText(this, "SMS Failed to Send", Toast.LENGTH_SHORT).show();
                smsTextView.setText("SMS Failed to Send");
            }
    }

}


