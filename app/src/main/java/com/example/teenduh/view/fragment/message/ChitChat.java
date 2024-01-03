package com.example.teenduh.view.fragment.message;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.teenduh.R;
import com.example.teenduh.model.message.MessageViewModel;
import com.example.teenduh.model.message.MessagesPreviewStore;
import com.example.teenduh.model.message.NewMatchesStore;
import com.example.teenduh.model.message.NewMatchesViewModel;
import com.example.teenduh.util.AndroidUtil;
import com.example.teenduh.view.adapter.message.MessagesPreviewAdapter;
import com.example.teenduh.view.adapter.message.NewMatchesAdapter;

import java.util.ArrayList;
import java.util.List;

public class ChitChat extends Fragment {
  private Context context;
  RecyclerView matches;
  RecyclerView messages;
  List<NewMatchesViewModel> matchesList = new ArrayList<>();
  List<MessageViewModel> messagesList = new ArrayList<>();
  List<Integer> unreadAmountList = new ArrayList<>();
  
  
  public ChitChat() {
    // Required empty public constructor
    context = AndroidUtil.getContext();
  }
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view =  inflater.inflate(R.layout.fragment_chit_chat, container, false);

    matches = view.findViewById(R.id.matches);
    messages = view.findViewById(R.id.messages);

    matches.setLayoutManager(new
      LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

    NewMatchesStore.fetch();
    matchesList = NewMatchesStore.getMatchesList();

    matches.setAdapter(new NewMatchesAdapter(matchesList, view));

    messages.setLayoutManager(new
      LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

    MessagesPreviewStore.fetch();
    messagesList = MessagesPreviewStore.getMessagesList();
    unreadAmountList = MessagesPreviewStore.getUnreadAmountList();

    messages.setAdapter(new MessagesPreviewAdapter(messagesList, unreadAmountList, view));

    return view;
  }
  
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    matches = view.findViewById(R.id.matches);
    messages = view.findViewById(R.id.messages);
    
  }
}
