package com.example.teenduh.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.teenduh.R;
import com.example.teenduh._util.AndroidUtil;
import com.example.teenduh.model.User;
import com.google.android.material.textfield.TextInputEditText;

public class MatchingScreen extends AppCompatActivity {
  TextInputEditText message;
  ImageView sendBtn;
  ImageView imageCover;
  User otherUser;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_matching_screen);
    message = findViewById(R.id.message);
    sendBtn = findViewById(R.id.sendButton);
    imageCover = findViewById(R.id.imageCover);
    
    otherUser = AndroidUtil.get_tempUser();
    editImageCover();
    sendBtn.setOnClickListener(v -> {
      System.out.println(message.getText().toString());
      overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
      finish();
    });
  }
  
  public void editImageCover(){
    if (!otherUser.getPics().isEmpty()) {
      imageCover.setImageURI(otherUser.getPics().get(0).getUri());
    }
  }
}