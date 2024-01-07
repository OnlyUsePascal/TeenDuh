package com.example.teenduh.view.adapter.message;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teenduh.R;
import com.example.teenduh._util.AndroidUtil;
import com.example.teenduh.model.message.Chat;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
  Activity activity;
  List<Chat> chats;
  
  public ChatAdapter() {
    this.activity = AndroidUtil.getActivity();
    this.chats = AndroidUtil.getCurChatRoom().getChats();
  }
  
  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(activity)
                    .inflate(R.layout.adapter_chat, parent,false);
    return new ChatAdapter.ViewHolder(view);
  }
  
  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    Chat chat = chats.get(position);
    holder.chatText.setText(chat.getMess());
    boolean isYou = AndroidUtil.getCurChatRoom()
                        .getUsers()
                        .get(chat.getUser())
                        .equals(AndroidUtil.getCurUser().getId());
    holder.chatFrame.setCardBackgroundColor((isYou) ? Color.parseColor("#D9EDBF") :
                                               Color.parseColor("#FFB996"));
    if (isYou) {
      RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.chatFrame.getLayoutParams();
      params.addRule(RelativeLayout.ALIGN_PARENT_END);
      holder.chatFrame.setLayoutParams(params);
    }
  }
  
  @Override
  public int getItemCount() {
    return chats.size();
  }
  
  class ViewHolder extends RecyclerView.ViewHolder {
    // Button mess;
    MaterialCardView chatFrame;
    TextView chatText;
    
    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      chatFrame = itemView.findViewById(R.id.chatFrame);
      chatText = itemView.findViewById(R.id.chatText);
      
    }
  }
  
}
