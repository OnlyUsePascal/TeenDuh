package com.example.teenduh._util;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teenduh.model.message.Chat;
import com.example.teenduh.view.adapter.message.ChatAdapter;
import com.google.firebase.Timestamp;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CloudMessagingListener extends FirebaseMessagingService {
  @Override
  public void onMessageReceived(@NonNull RemoteMessage message) {
    super.onMessageReceived(message);
    System.out.println("mess:" + message.getData());
    
    RecyclerView chatView = AndroidUtil.getChatView();
    ChatAdapter chatAdapter = AndroidUtil.getChatAdapter();
    Map<String, String> data = message.getData();
    
    String id = data.get("id").toString();
    int sender = Integer.parseInt(data.get("sender"));
    String mess = data.get("mess").toString();
    Timestamp timestamp = getTimestamp(data.get("time").toString());
    Chat newChat = new Chat(id, mess, timestamp, sender);
    List<Chat> chats = AndroidUtil.getCurChatRoom().getChats();
    
    chats.add(newChat);
    AndroidUtil.getActivity().runOnUiThread(() -> {
      chatAdapter.notifyItemInserted(chats.size()-1);
      chatView.smoothScrollToPosition(chats.size()-1);
    });
    
    //chat room
    // String chatRoomId = data.get("chatRoomId").
    
  }
  
  public static Timestamp getTimestamp(String timestampStr){
    long seconds = Long.parseLong(timestampStr.substring(timestampStr.indexOf("seconds=") + 8, timestampStr.indexOf(",")).trim());
    int nanoseconds = Integer.parseInt(timestampStr.substring(timestampStr.indexOf("nanoseconds=") + 12, timestampStr.indexOf(")")).trim());
    return new Timestamp(seconds, nanoseconds);
  }
  
  @Override
  public void onNewToken(@NonNull String token) {
    super.onNewToken(token);
    System.out.println("token:" + token);
  }
}
