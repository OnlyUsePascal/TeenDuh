package com.example.teenduh.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.teenduh.R;
import com.example.teenduh.view.fragment.GenderFragment;
import com.example.teenduh.view.fragment.NameFragment;

import pl.droidsonroids.gif.GifImageView;

public class SignUpPage extends AppCompatActivity {
    public int currentProgress = 0;
    public ProgressBar progressBar;
    public TextView progressText;

    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        replaceFragment(new NameFragment());

        progressBar = findViewById(R.id.progressBar);
        back = findViewById(R.id.backButton);
        progressBar.setMax(100);

        back.setOnClickListener(v -> {
            currentProgress -= 10;
            progressBar.setProgress(currentProgress);
            backFragment();
        });

    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
        //Add to back stack
        fragmentTransaction.addToBackStack(fragment.getClass().getName());
    }
    public void backFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
    }
}
