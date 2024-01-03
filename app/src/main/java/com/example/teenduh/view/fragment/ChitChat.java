package com.example.teenduh.view.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.teenduh.R;
import com.example.teenduh.util.AndroidUtil;

public class ChitChat extends Fragment {
  private Context context;
  RecyclerView matches;
  RecyclerView messages;
  
  
  public ChitChat() {
    // Required empty public constructor
    context = AndroidUtil.getContext();
  }
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_chit_chat, container, false);
  }
  
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    matches = view.findViewById(R.id.matches);
    messages = view.findViewById(R.id.messages);
    
  }
}
