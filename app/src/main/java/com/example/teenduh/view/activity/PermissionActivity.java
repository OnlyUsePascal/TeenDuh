package com.example.teenduh.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.teenduh.R;

public class PermissionActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final int NOTIFICATION_PERMISSION_REQUEST_CODE = 1001;

    Button buttonLocation;
    Button buttonNotification;
    Button buttonContinue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        buttonLocation = findViewById(R.id.button_location);
        buttonNotification = findViewById(R.id.button_notification);
        buttonContinue = findViewById(R.id.button_continue);

        //allow share location
        buttonLocation.setOnClickListener(view -> requestLocationPermission());

        //allow notification
        buttonNotification.setOnClickListener(view -> requestNotificationPermission());

        buttonContinue.setOnClickListener(view -> {
            Intent intent = new Intent(PermissionActivity.this, TestSuccess.class);
            startActivity(intent);
        });
        
        // Check and request permissions
        requestLocationPermission();
        requestNotificationPermission();
        updateContinueButtonVisibility();

    }

    private void updateContinueButtonVisibility() {
        if (areAllPermissionsGranted()) {
            buttonContinue.setVisibility(View.VISIBLE);
        } else {
            buttonContinue.setVisibility(View.GONE);
        }
    }

    private boolean areAllPermissionsGranted() {
        boolean locationPermissionGranted = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        boolean notificationPermissionGranted = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            notificationPermissionGranted = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED;
        }

        if (locationPermissionGranted) {
            buttonLocation.setBackgroundColor(getResources().getColor(R.color.green));
        } else {
            buttonLocation.setBackgroundColor(getResources().getColor(R.color.red));
        }

        if (notificationPermissionGranted) {
            buttonNotification.setBackgroundColor(getResources().getColor(R.color.green));
        } else {
            buttonNotification.setBackgroundColor(getResources().getColor(R.color.red));
        }

        return locationPermissionGranted && notificationPermissionGranted;
    }

    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            // Permission has already been granted
            buttonLocation.setBackgroundColor(getResources().getColor(R.color.green));
            Toast.makeText(this, "Location permission granted", Toast.LENGTH_SHORT).show();
        }
    }

    private void requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        NOTIFICATION_PERMISSION_REQUEST_CODE);
            } else {
                // Permission has already been granted
                buttonNotification.setBackgroundColor(getResources().getColor(R.color.green));
                Toast.makeText(this, "Notification permission granted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // After any permission response, check all permissions again
        updateContinueButtonVisibility();
    }
}