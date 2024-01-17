
package com.example.teenduh.view.activity;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.teenduh.R;
import com.example.teenduh._util.AndroidUtil;
import com.example.teenduh._util.FirebaseUtil;

public class TestSuccess extends AppCompatActivity {
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_test_success);
    
    // check if user exist
    new Thread(() -> {
      boolean exist = AndroidUtil.getUser(FirebaseUtil.getCurUser().getUid()) != null;
      runOnUiThread(() -> {
        ((TextView) findViewById(R.id.textView)).setText("Exist = " + exist);
      });
    }).run();
  }
}