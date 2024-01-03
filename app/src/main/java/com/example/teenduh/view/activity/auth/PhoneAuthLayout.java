package com.example.teenduh.view.activity.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.teenduh.R;
import com.example.teenduh.view.fragment.auth.PhoneFormFragment;

public class PhoneAuthLayout extends AppCompatActivity {
    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth_layout);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.phone_auth_fragment_container, new PhoneFormFragment())
                .commit();
    }
}