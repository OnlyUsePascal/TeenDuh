package com.example.teenduh.view.activity;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.teenduh.R;
import com.example.teenduh._util.AndroidUtil;

public class AskPermission extends AppCompatActivity {
  
  private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
  private static final int NOTIFICATION_PERMISSION_REQUEST_CODE = 1001;
  
  Button buttonLocation;
  Button buttonNotification;
  Button buttonContinue;
  
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_ask_permission);
    
    buttonLocation = findViewById(R.id.button_location);
    buttonNotification = findViewById(R.id.button_notification);
    buttonContinue = findViewById(R.id.button_continue);
    
    buttonLocation.setOnClickListener(view -> requestLocationPermission());
    buttonNotification.setOnClickListener(view -> requestNotificationPermission());
    buttonContinue.setOnClickListener(view -> {
      finish();
    });
    
    updateBtnVisible();
    
    getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
      @Override
      public void handleOnBackPressed() {
        AndroidUtil.makeToast(AskPermission.this, "Please Grant Permission!");
      }
    });
  }
  
  private void updateBtnVisible() {
    if (isLocationPermissionGranted(this)){
      buttonLocation.setBackgroundColor(getResources().getColor(R.color.green));
    }
    
    if (isNotificationPermissionGranted(this)){
      buttonNotification.setBackgroundColor(getResources().getColor(R.color.green));
    }
    
    if (areAllPermissionsGranted(this)) {
      buttonContinue.setVisibility(View.VISIBLE);
    } else {
      buttonContinue.setVisibility(View.INVISIBLE);
    }
  }
  
  public static boolean areAllPermissionsGranted(Context _context) {
    boolean locationPermissionGranted = isLocationPermissionGranted(_context);
    boolean notificationPermissionGranted = isNotificationPermissionGranted(_context);

    return locationPermissionGranted && notificationPermissionGranted;
  }
  
  private static boolean isNotificationPermissionGranted(Context _context) {
    boolean notificationPermissionGranted = ContextCompat.checkSelfPermission(_context,
          Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED;
    return notificationPermissionGranted;
  }
  
  private static boolean isLocationPermissionGranted(Context _context) {
    boolean locationPermissionGranted = ContextCompat.checkSelfPermission(_context,
        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    return locationPermissionGranted;
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
    // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    // }
  }
  
  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    // After any permission response, check all permissions again
    updateBtnVisible();
  }
}