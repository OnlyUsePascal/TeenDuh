package com.example.teenduh.view.fragment.message;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.teenduh.R;
import com.example.teenduh._util.AndroidUtil;
import com.example.teenduh._util.FirebaseUtil;
import com.example.teenduh.view.adapter.message.ChatRoomAdapter;

public class ChitChat extends Fragment {
  private Activity activity;
  private RecyclerView chatRoomViews;
  private RecyclerView matchViews;
  private ChatRoomAdapter adapter;
  private TextView user;
  private Button stat;

  
  public ChitChat() {
    // matches = new ArrayList<>();
  }
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view =  inflater.inflate(R.layout.fragment_chit_chat, container, false);
  
    user = view.findViewById(R.id.textView2);
    chatRoomViews = view.findViewById(R.id.messages);
    matchViews = view.findViewById(R.id.matches);
    stat = view.findViewById(R.id.button7);
    view.findViewById(R.id.button13).setOnClickListener(this::testLogin);
    view.findViewById(R.id.button14).setOnClickListener(this::testLogin);
    view.findViewById(R.id.button15).setOnClickListener(this::getChatRooms);
    
    activity = AndroidUtil.getActivity();
    // getChatRooms();
    
    // user.setText(AndroidUtil.getCurUser().getName());
    
    // match
    // matchViews = view.findViewById(R.id.matches);
    // matchViews.setLayoutManager(new
    //   LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
    // matchViews.setAdapter(new NewMatchesAdapter(matches, view));
    
    // chat room
    // ChatRoomAdapter adapter = new ChatRoomAdapter();
    // chatRoomViews.setLayoutManager(new
    //   LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
    // unreadAmountList = MessagesPreviewStore.getUnreadAmountList();
    // messages.setAdapter(new MessagesPreviewAdapter(messagesList, unreadAmountList, view));
    
    return view;
  }
  
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }
  
  public void getChatRooms(View view){
    if (AndroidUtil.getCurUser() == null) {
      System.out.println("cur user null");
      return;
    }
    
    stat.setText("Stand by");
    AndroidUtil.fetchChatRooms(() -> {
      stat.setText("Have fun");
      
      adapter = new ChatRoomAdapter();
      chatRoomViews.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.VERTICAL, false));
      chatRoomViews.setAdapter(adapter);
    });
  }
  
  public void testLogin(View view){
    int btnId = view.getId();
    stat.setText("wait a minute");
  
    AndroidUtil.loginEmail(btnId, () -> {
      stat.setText("user:" + AndroidUtil.getCurUser().getName());
    });
  }
  
  // TODO ON RESUME FOR FRAGMENT
}
