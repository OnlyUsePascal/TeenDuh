package com.example.teenduh.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teenduh.R;
import com.example.teenduh._util.AndroidUtil;
import com.example.teenduh._util.FirebaseUtil;
import com.example.teenduh.model.message.ChatRoom;
import com.example.teenduh.view.adapter.CustomLayoutManager;
import com.example.teenduh.view.adapter.message.ChatAdapter;
import com.google.firebase.Timestamp;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatActivity extends AppCompatActivity {
  private TextView otherUser;
  private RecyclerView chatView;
  private EditText messField;
  private Button stat;
  private ChatRoom chatRoom;
  private ChatAdapter adapter;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_chat);
    
    otherUser = findViewById(R.id.other_username);
    chatView = findViewById(R.id.chat_recycler_view);
    messField = findViewById(R.id.chat_message_input);
    stat = findViewById(R.id.button5);
    
    chatRoom = AndroidUtil.getCurChatRoom();
    String otherName = chatRoom.getOtherUser().getName();
    otherUser.setText(otherName);
    
    AndroidUtil.setActivity(this);
    AndroidUtil.setChatView(chatView);
    getChats();
  }
  
  public void getChats() {
    stat.setText("loading");

    AndroidUtil.fetchChats(() -> {
      stat.setText("oke");
      
      adapter = new ChatAdapter();
      chatView.setLayoutManager(new CustomLayoutManager(this));
      chatView.setAdapter(adapter);
      chatView.scrollToPosition(adapter.getItemCount()-1);
      
      AndroidUtil.setChatAdapter(adapter);
      AndroidUtil.setChatView(chatView);
    });
  }
  
  String messTxt;
  
  public void sendChat(View view) throws Exception {
    stat.setText("wait");
    
    messTxt = Integer.toString(new Random().nextInt());
    int sender = chatRoom.getSenderIndex(AndroidUtil.getCurUser().getId());
    Timestamp time = Timestamp.now();
  
    HashMap<String, Object> chatData = new HashMap<>();
    chatData.put("mess", messTxt);
    chatData.put("sender", sender);
    chatData.put("time", time);
    System.out.println(chatData);
    FirebaseUtil.addChat(chatData, this::sendNoti);
  }
  
  private void sendNoti() {
    if (true) return;
    try {
      String url = "https://fcm.googleapis.com/fcm/send";
      OkHttpClient client = new OkHttpClient();
      MediaType mediaType = MediaType.get("application/json; charset=utf-8");
      JSONObject jsonObject = new JSONObject();
      JSONObject dataObj = new JSONObject();
  
      dataObj.put("_from", AndroidUtil.getCurUser().getId());
      dataObj.put("mess", messTxt);
      jsonObject.put("data", dataObj);
      jsonObject.put("to", chatRoom.getOtherUser().getFcm());
      jsonObject.put("direct_boot_ok", true);
  
      RequestBody body = RequestBody.create(jsonObject.toString(), mediaType);
      Request request = new Request.Builder()
                            .url(url)
                            .post(body)
                            .header("Authorization", getResources().getString(R.string.cloud_mess_key))
                            .build();
  
      client.newCall(request).
          enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
              runOnUiThread(() -> stat.setText("oke"));
              System.out.println(response);
              System.out.println(response.body().string());
            }
        
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
              runOnUiThread(() -> stat.setText("failed"));
              e.printStackTrace();
          
            }
          });
    } catch (Exception err) {
      err.printStackTrace();
    }
  }
  
  public RecyclerView getChatView() {
    return chatView;
  }
  
  public void setChatView(RecyclerView chatView) {
    this.chatView = chatView;
  }
}
