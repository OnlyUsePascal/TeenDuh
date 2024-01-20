package com.example.teenduh.view.activity.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.example.teenduh.R;
import com.example.teenduh._util.AndroidUtil;
import com.example.teenduh.model.User;
import com.example.teenduh.view.adapter.AdminUserAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class AdminViewUserActivity extends AppCompatActivity {

    ScrollView scrollView;
    ImageView returnButton;
    AdminUserAdapter userAdapter;
    RecyclerView recyclerView;
    List<User> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_user);

        recyclerView = findViewById(R.id.rcv_user);
        returnButton = findViewById(R.id.back_button);

        returnButton.setOnClickListener(v -> {
            finish();
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        getAllUserList();

        userAdapter = new AdminUserAdapter(this, userList);
        recyclerView.setAdapter(userAdapter);

    }

    public void getAllUserList(){
        userList = AndroidUtil.getUsers();
        for (User user : userList) {
            System.out.println(user.getName() + " " + user.getGender() + " " + user.getAge());
        }
    }
}