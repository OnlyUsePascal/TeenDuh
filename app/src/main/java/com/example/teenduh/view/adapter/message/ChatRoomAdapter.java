package com.example.teenduh.view.adapter.message;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teenduh.R;
import com.example.teenduh._util.AndroidUtil;
import com.example.teenduh.model.message.ChatRoom;
import com.example.teenduh.view.activity.ChatActivity;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ChatRoomAdapter extends RecyclerView.Adapter<ChatRoomAdapter.ViewHolder> {
  Context context;
  List<ChatRoom> chatRooms;
  
  public ChatRoomAdapter() {
    context = AndroidUtil.getActivity();
    chatRooms = AndroidUtil.getChatRooms();
  }
  
  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context)
                    .inflate(R.layout.adapter_chatroom, parent,false);
    return new ViewHolder(view);
  }
  
  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    ChatRoom chatRoom = chatRooms.get(position);
    String otherName = chatRoom.getOtherUser().getName();
    LocalDateTime time = LocalDateTime.ofInstant(chatRoom.getLastAct().toDate().toInstant(), ZoneId.systemDefault());
    String timeStr = time.format(DateTimeFormatter.ofPattern("MMM dd"));
    
    holder.usernameText.setText(otherName);
    holder.lastMessageText.setText(chatRoom.getLastMess());
    holder.lastMessageTime.setText(timeStr);
    holder.profilePic.setImageURI(chatRoom.getPicUri());
    holder.itemView.setOnClickListener(view -> {
      AndroidUtil.setCurChatRoom(chatRoom);
      context.startActivity(new Intent(context, ChatActivity.class));
    });
  }
  
  @Override
  public int getItemCount() {
    return chatRooms.size();
  }
  
  public class ViewHolder extends RecyclerView.ViewHolder {
    TextView usernameText;
    TextView lastMessageText;
    TextView lastMessageTime;
   ImageView profilePic;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      usernameText = itemView.findViewById(R.id.name);
      lastMessageText = itemView.findViewById(R.id.messagePreview);
      lastMessageTime = itemView.findViewById(R.id.date);
       profilePic = itemView.findViewById(R.id.profile_pic_image_view);
    }
  }
}
