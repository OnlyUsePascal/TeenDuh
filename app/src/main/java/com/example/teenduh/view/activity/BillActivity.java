package com.example.teenduh.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.teenduh.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BillActivity extends AppCompatActivity {
  private ImageView btnBack;
  private int price = 0;
  Spinner likeAmountField;
  TextView totalField;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_bill);
    btnBack = findViewById(R.id.back_button);
    likeAmountField = findViewById(R.id.likeAmount1);
    totalField = findViewById(R.id.textView111);
    
    int amountIdx = 0;
    Intent intent = getIntent();
    if (intent != null) {
      amountIdx = intent.getIntExtra("amount", -1);
    }
    
    List<String> options = Arrays.asList(new String[]{"5", "10", "50"});
    ArrayAdapter<String> adapter =
        new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, options);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    likeAmountField.setAdapter(adapter);
    
    likeAmountField.setSelection(amountIdx);
    
    btnBack.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
  }
  
  public void getPrice(){
  
  }
}