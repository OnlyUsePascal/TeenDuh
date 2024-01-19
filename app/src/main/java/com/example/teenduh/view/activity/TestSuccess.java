
package com.example.teenduh.view.activity;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.teenduh.R;
import com.example.teenduh._util.AndroidUtil;
import com.example.teenduh._util.FirebaseUtil;
import com.example.teenduh.model.User;

import java.util.HashMap;

public class TestSuccess extends AppCompatActivity {
  public static int REQ_LOGIN = 12;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_test_success);
    
    int reqCode = getIntent().getIntExtra("reqCode", -1);
    // check if user exist
    
    if (reqCode == REQ_LOGIN)
      setupLogin();
  }
  
  private void setupLogin() {
    new Thread(() -> {
      User curUser = AndroidUtil.getUserWithId(FirebaseUtil.getCurUser().getUid());
      boolean exist = curUser != null;
      System.out.println("exists = " + exist + " -- " + FirebaseUtil.getCurUser().getUid());
      
      Runnable runnable = () -> {
        runOnUiThread(() -> {
          ((TextView) findViewById(R.id.textView)).setText("Exist = " + exist);
          AndroidUtil._startActivity(this, (exist) ? MainLayout.class : SignUpPage.class);
        });
      };
      if (!exist) {
        AndroidUtil.setCurNewUser(FirebaseUtil.getCurUser().getUid(), runnable); // not on firebase yet
      } else {
        AndroidUtil.setCurUser(curUser);
        // update fcm
        HashMap<String, Object> data = new HashMap<>();
        data.put("fcm", FirebaseUtil.getFcm());
        FirebaseUtil.updateUser(curUser.getId(), data, runnable);
      }
      
    }).run();
  }
}