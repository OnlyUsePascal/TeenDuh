package com.example.teenduh.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.teenduh.R;
import com.example.teenduh._util.AndroidUtil;
import com.example.teenduh.view.activity.admin.AdminViewUserActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Database#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Database extends Fragment {

    private Button buttonViewUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_database, container, false);
        buttonViewUser = view.findViewById(R.id.view_user);

        buttonViewUser.setOnClickListener(v -> {
            AndroidUtil._startActivity(getContext(), AdminViewUserActivity.class);
        });

        return view;
    }
}