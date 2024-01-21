package com.example.teenduh.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.teenduh.R;

public class NotificationActivity extends AppCompatActivity {
  TextView textView;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_notification);
    textView = findViewById(R.id.textView);
    String data = getIntent().getStringExtra("message");
    textView.setText(data);
  }
}