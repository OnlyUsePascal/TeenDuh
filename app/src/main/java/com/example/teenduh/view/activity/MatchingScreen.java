package com.example.teenduh.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.teenduh.R;
import com.example.teenduh._util.AndroidUtil;
import com.example.teenduh._util.FirebaseUtil;
import com.example.teenduh.model.User;
import com.example.teenduh.model.message.Chat;
import com.example.teenduh.model.message.ChatRoom;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Timestamp;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    // sendBtn.setOnClickListener(v -> {
    //   System.out.println(message.getText().toString());
    //   overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
    //   finish();
    // });
    sendBtn.setOnClickListener(this::sendMess);
  }
  
  public void editImageCover() {
    if (!otherUser.getPics().isEmpty()) {
      imageCover.setImageURI(otherUser.getPics().get(0).getUri());
    }
  }
  
  public void sendMess(View view) {
    String messStr = message.getText().toString();
    if (messStr.isBlank()) {
      AndroidUtil.makeToast(this, "Message is blank!");
      return;
    }
    
    // create chat room
    // ChatRoom chatRoom = new ChatRoom()
    User curUser = AndroidUtil.getCurUser();
    Timestamp timestamp = Timestamp.now();
    
    Map<String, Object> data = new HashMap<>();
    data.put("users", Arrays.asList(new String[]{curUser.getId(), otherUser.getId()}));
    data.put("lastMess", messStr);
    data.put("lastSender", 0);
    data.put("lastActive", timestamp);
    
    FirebaseUtil.getFirestore().collection("chatRooms")
        .add(data)
        .addOnCompleteListener(task -> {
          if (!task.isSuccessful()) {
            task.getException().printStackTrace();
            return;
          }
          
          String roomId = task.getResult().getId();
          Map<String, Object> dataMess = new HashMap<>();
          dataMess.put("mess", messStr);
          dataMess.put("sender", 0);
          dataMess.put("time", timestamp);
          FirebaseUtil.getFirestore().collection("chatRooms").document(roomId)
              .collection("chats").add(dataMess)
              .addOnCompleteListener(task1 -> {
                if (!task1.isSuccessful()) {
                  task1.getException().printStackTrace();
                  return;
                }
                
                AndroidUtil.makeToast(this, "Message Sent!!");
                overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                finish();
              });
        });
    
  }
}