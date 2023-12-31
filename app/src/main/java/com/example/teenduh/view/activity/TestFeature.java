package com.example.teenduh.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.teenduh.R;
import com.example.teenduh.util.AndroidUtil;
import com.example.teenduh.view.activity.auth.AuthLayout;

public class TestFeature extends AppCompatActivity {
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_test_feature);
  }
  
  public void toMainLayout(View view){
    AndroidUtil._startActivity(this, MainLayout.class);
    
  }
  
  public void toAuthLayout(View view){
    AndroidUtil._startActivity(this, AuthLayout.class);
    
  }
}