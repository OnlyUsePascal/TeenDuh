package com.example.teenduh.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.teenduh.R;
import com.example.teenduh._util.AndroidUtil;
import com.example.teenduh._util.FirebaseUtil;
import com.example.teenduh.view.activity.auth.AuthLayout;
import com.example.teenduh.view.activity.payment.DropInClientActivity;

public class TestFeature extends AppCompatActivity {
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_test_feature);

    FirebaseUtil.init();
    AndroidUtil.init(this);
  }
  
  public void toMainLayout(View view){
    AndroidUtil._startActivity(this, MainLayout.class);
    
  }
  
  public void toAuth(View view){
    AndroidUtil._startActivity(this, AuthLayout.class);
    
  }
  
  public void toWelcome(View view){
    AndroidUtil._startActivity(this, WelcomeActivity.class);
  }
  
  public void toPermit(View view){
    AndroidUtil._startActivity(this, AskPermission.class);
  }
  
  public void toSignUp(View view){
    AndroidUtil._startActivity(this, SignUpPage.class);
  }
  
  public void toPayment(View view){
    AndroidUtil._startActivity(this, DropInClientActivity.class);
  }
  
}