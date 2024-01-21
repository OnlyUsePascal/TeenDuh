package com.example.teenduh.view.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.teenduh.R;
import com.example.teenduh._util.AndroidUtil;
import com.example.teenduh.model.Image;
import com.example.teenduh.model.User;
import com.example.teenduh.view.activity.Subscription;
import com.example.teenduh.view.adapter.MatchImageAdapter;

import java.util.ArrayList;
import java.util.List;


public class MatchFragment extends Fragment {
  View view;
  List<User> userList = new ArrayList<User>();
  RecyclerView recyclerView;
  SwipeRefreshLayout swipeRefreshLayout;
  TextView textview_who_like;

  MatchImageAdapter matchImageAdapter;
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    view = inflater.inflate(R.layout.fragment_match, container, false);
    recyclerView = view.findViewById(R.id.recycler_view_match);
    textview_who_like = view.findViewById(R.id.textview_who_like);
    userList = AndroidUtil.getUsers();
    System.out.println(userList.size());
    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
    matchImageAdapter = new MatchImageAdapter(userList, true, getContext(), getActivity());
    recyclerView.setAdapter(matchImageAdapter);
    swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        matchImageAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
      }
    });
    
    textview_who_like.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getContext(), Subscription.class);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
      }
    });
    return view;
  }
}