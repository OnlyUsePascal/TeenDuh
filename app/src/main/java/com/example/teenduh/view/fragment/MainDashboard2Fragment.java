package com.example.teenduh.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.example.teenduh.R;
import com.example.teenduh.model.User;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;


public class MainDashboard2Fragment extends Fragment {
    View view;
    List<User> userList;
    Button btnLike, btnCancel, btnSuperLike;

    private ArrayAdapter<User> arrayAdapter;
    SwipeFlingAdapterView flingAdapterView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(com.example.teenduh.R.layout.fragment_main_dashboard2, container, false);
        flingAdapterView = view.findViewById(R.id.swipe_view);
        userList = new ArrayList<>();
        userList.add(new User(R.drawable.ronaldo, "Ronaldo", "24", "Vung Tau"));
        userList.add(new User(R.drawable.messi, "Messi", "24", "HCM"));
        userList.add(new User(R.drawable.park_seo,"Park Seo Jun" , "24", "NYC"));

        arrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.item_card);
        flingAdapterView.setAdapter(arrayAdapter);

        flingAdapterView.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                userList.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object o) {
                Toast.makeText(getActivity(), "Dislike", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object o) {
                Toast.makeText(getActivity(), "Like", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int i) {

            }

            @Override
            public void onScroll(float v) {

            }


        });
        btnLike = view.findViewById(R.id.like_action);
        btnCancel = view.findViewById(R.id.cancel_action);
        btnSuperLike = view.findViewById(R.id.super_like_action);
        btnLike.setOnClickListener(v -> {
            System.out.println("like");
            flingAdapterView.getTopCardListener().selectRight();
        });
        btnCancel.setOnClickListener(v -> {
            System.out.println("cancel");
            flingAdapterView.getTopCardListener().selectLeft();
        });
//        btnSuperLike.setOnClickListener(v -> {
//            System.out.println("super like");
//            flingAdapterView.getTopCardListener().selectTop();
//        });


        return view;
    }
}