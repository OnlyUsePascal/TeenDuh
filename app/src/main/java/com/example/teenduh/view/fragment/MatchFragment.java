package com.example.teenduh.view.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.teenduh.R;
import com.example.teenduh._util.AndroidUtil;
import com.example.teenduh._util.FirebaseUtil;
import com.example.teenduh.model.Image;
import com.example.teenduh.model.User;
import com.example.teenduh.view.activity.Subscription;
import com.example.teenduh.view.adapter.MatchImageAdapter;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class MatchFragment extends Fragment {
  View view;
  List<User> userList = new ArrayList<User>();
  RecyclerView recyclerView;
  SwipeRefreshLayout swipeRefreshLayout;
  TextView textview_who_like, upgradeText;
  

  MatchImageAdapter matchImageAdapter;
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    
    // Inflate the layout for this fragment
    view = inflater.inflate(R.layout.fragment_match, container, false);
    textview_who_like = view.findViewById(R.id.textview_who_like);
    recyclerView = view.findViewById(R.id.recycler_view_match);
    upgradeText = view.findViewById(R.id.upgradeText);

    FirebaseUtil.getFirestore()
            .collection("users")
            .whereArrayContains("likePeople", FirebaseUtil.getCurUser().getUid())
            .get()
            .addOnSuccessListener(queryDocumentSnapshots -> {
              userList.clear();
              if (!queryDocumentSnapshots.isEmpty()) {
                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                  User user = AndroidUtil.documentToUser(document);
                  userList.add(user);
                }
              } else {
                Log.d("Firebase", "No users found who like you.");
                upgradeText.setText("No users liked you yet.");
              }
            })
            .addOnFailureListener(e -> {
              Log.w("Firebase", "Error getting user.", e);
            });

    // System.out.println(userList.size());
    swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        // matchImageAdapter.notifyDataSetChanged();
        initMatchAdapter();
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
    initMatchAdapter();
    
    
    
    return view;
  }
  
  public void initMatchAdapter(){
    boolean isVip = AndroidUtil.getCurUser().isVip();
    if (isVip){
      textview_who_like.setVisibility(View.GONE);
      upgradeText.setText("See users who liked you.");
    }
    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
    matchImageAdapter = new MatchImageAdapter(userList, isVip, getContext(), getActivity());
    recyclerView.setAdapter(matchImageAdapter);
  }
  
  @Override
  public void onResume() {
    super.onResume();
    
  }
}