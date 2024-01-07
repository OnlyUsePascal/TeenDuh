package com.example.teenduh._util;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teenduh.model.message.Chat;
import com.example.teenduh.view.adapter.message.ChatAdapter;
import com.google.firebase.Timestamp;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.List;
import java.util.Random;

public class CloudMessagingListener extends FirebaseMessagingService {
  @Override
  public void onMessageReceived(@NonNull RemoteMessage message) {
    super.onMessageReceived(message);
    System.out.println("mess:" + message.getData());
    
    // TODO: NOTI + CHATROOM CHANGE
    
    RecyclerView chatView = AndroidUtil.getChatView();
    ChatAdapter chatAdapter = AndroidUtil.getChatAdapter();
    List<Chat> chats = AndroidUtil.getCurChatRoom().getChats();

    AndroidUtil.getActivity().runOnUiThread(() -> {
      Random random = new Random();
      Chat chat = new Chat("testId", random.nextInt() + "", Timestamp.now(), 1);
      chats.add(chat);
      chatAdapter.notifyDataSetChanged();
      chatView.smoothScrollToPosition(chats.size()-1);
    });
  }
  
  @Override
  public void onNewToken(@NonNull String token) {
    super.onNewToken(token);
    System.out.println("token:" + token);
  }
}
