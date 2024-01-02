package com.example.teenduh.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.teenduh.R;
import com.example.teenduh.view.activity.TestingChat.UserButtonChat;

public class TestFeature extends AppCompatActivity {

  Button button5;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_test_feature);
    button5 = findViewById(R.id.button5);
    button5.setOnClickListener(v -> {
      Intent intent = new Intent(this, UserButtonChat.class);
        startActivity(intent);
    });
  }
}