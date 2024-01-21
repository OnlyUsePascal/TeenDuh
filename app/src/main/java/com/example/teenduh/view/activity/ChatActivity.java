package com.example.teenduh.view.activity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teenduh.R;
import com.example.teenduh._util.AndroidUtil;
import com.example.teenduh._util.FirebaseUtil;
import com.example.teenduh.model.message.Chat;
import com.example.teenduh.model.message.ChatRoom;
import com.example.teenduh.view.adapter.CustomLayoutManager;
import com.example.teenduh.view.adapter.message.ChatAdapter;
import com.google.android.material.textfield.TextInputEditText;
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
  private AppCompatImageButton sendBtn, reportBtn;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_chat);

    otherUser = findViewById(R.id.other_username);
    chatView = findViewById(R.id.chat_recycler_view);
    messField = findViewById(R.id.chat_message_input);
    sendBtn = findViewById(R.id.message_send_btn);
    reportBtn = findViewById(R.id.reportUserBtn);
    stat = findViewById(R.id.button5);

    reportBtn.setOnClickListener(v -> {
      showReportDialog();
    });

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
  Timestamp time;
  int sender;
  
  public void sendChat(View view) throws Exception {
    stat.setText("wait");
    
    sender = chatRoom.getSenderIndex(AndroidUtil.getCurUser().getId());
    time = Timestamp.now();
    messTxt = messField.getText().toString();

    if (messTxt.isEmpty()) {
      return;
    }

    messField.setText("");

    HashMap<String, Object> chatData = new HashMap<>();
    chatData.put("mess", messTxt);
    chatData.put("sender", sender);
    chatData.put("time", time);
    // firestore: chat, chatroom -> noti
    FirebaseUtil.addChat(chatData, this::sendNoti);

    // change messTxt
    Chat chat = new Chat("---", messTxt, Timestamp.now(),
      chatRoom.getSenderIndex(AndroidUtil.getCurUser().getId()));
    // create chat object [messtxt, userIndex (use chatRoom.getSenderindx), time],
     chatRoom.getChats().add(chat);
     adapter.notifyItemInserted(chatRoom.getChats().size() - 1);
     chatView.smoothScrollToPosition(chatRoom.getChats().size() - 1);
  }
  
  private void sendNoti(String chatId) {
    // receiver
    stat.setText("send noti");
    try {
      String url = "https://fcm.googleapis.com/fcm/send";
      OkHttpClient client = new OkHttpClient();
      MediaType mediaType = MediaType.get("application/json; charset=utf-8");
      JSONObject jsonObject = new JSONObject();
      JSONObject dataObj = new JSONObject();
  
      String id = AndroidUtil.getCurUser().getId();
      dataObj.put("sender", sender);
      dataObj.put("mess", messTxt);
      dataObj.put("time", time.toString());
      dataObj.put("id", chatId);
      
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
  
  public void reload(View view){
    sendNoti(null);
  }
  
  public RecyclerView getChatView() {
    return chatView;
  }
  
  public void setChatView(RecyclerView chatView) {
    this.chatView = chatView;
  }

  public void showReportDialog() {
    final Dialog dialog = new Dialog(this);
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setContentView(R.layout.report_user_layout);

    Button discardBtn = dialog.findViewById(R.id.discardButton);
    Button reportBtn = dialog.findViewById(R.id.submit);
    ProgressBar progressBar = dialog.findViewById(R.id.progressBar);
    LinearLayout reportLayout = dialog.findViewById(R.id.buttonLayout);
    TextView reportee = dialog.findViewById(R.id.reporteeUserName);

    reportee.setText(chatRoom.getOtherUser().getName());

    discardBtn.setOnClickListener(v -> dialog.dismiss());
    reportBtn.setOnClickListener(v -> {
      TextInputEditText reportText = dialog.findViewById(R.id.reasonField);
      String report = reportText.getText().toString();

      if (report.isEmpty()) {
        reportText.setError("Please don't leave it blank");
        return;
      }

      progressBar.setVisibility(View.VISIBLE);
      reportLayout.setVisibility(View.GONE);

      FirebaseUtil.getFirestore().collection("reports")
        .add(new HashMap<String, Object>() {{
          put("reporterId", AndroidUtil.getCurUser().getId());
          put("description", report);
          put("reporteeId", chatRoom.getOtherUser().getId());
          put("time", Timestamp.now());
        }})
        .addOnCompleteListener(task -> {
          progressBar.setVisibility(View.INVISIBLE);
          reportLayout.setVisibility(View.VISIBLE);
          dialog.dismiss();

          if (task.isSuccessful()) {
            Toast.makeText(this, "Report sent successfully!", Toast.LENGTH_SHORT).show();
          } else {
            Toast.makeText(this, "Failed to send report. Please try again later.", Toast.LENGTH_SHORT).show();
          }
        });
    });

    dialog.show();
    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
    dialog.getWindow().setGravity(Gravity.BOTTOM);
  }
}
