package com.example.teenduh.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.teenduh.R;

public class AboutUs extends AppCompatActivity {


    TextView tv1, tv2, tv3, tv4, tv5;
    ImageView button1, button2, button3, button4, button5;
    ImageView buttonReturn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        tv1 = findViewById(R.id.text1);
        tv2 = findViewById(R.id.text2);
        tv3 = findViewById(R.id.text3);
        tv4 = findViewById(R.id.text4);
        tv5 = findViewById(R.id.text5);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4= findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        buttonReturn = findViewById(R.id.back_button);
//
        button1.setOnClickListener(v -> {
            if (tv1.getVisibility() == TextView.GONE) {
                tv1.setVisibility(TextView.VISIBLE);
                button1.setImageResource(R.drawable.baseline_remove_24);
            } else {
                tv1.setVisibility(TextView.GONE);
                button1.setImageResource(R.drawable.baseline_add_24);
            }
        });

        button2.setOnClickListener(v -> {
            if (tv2.getVisibility() == TextView.GONE) {
                tv2.setVisibility(TextView.VISIBLE);
                button2.setImageResource(R.drawable.baseline_remove_24);
            } else {
                tv2.setVisibility(TextView.GONE);
                button2.setImageResource(R.drawable.baseline_add_24);
            }
        });

        button3.setOnClickListener(v -> {
            if (tv3.getVisibility() == TextView.GONE) {
                tv3.setVisibility(TextView.VISIBLE);
                button3.setImageResource(R.drawable.baseline_remove_24);
            } else {
                tv3.setVisibility(TextView.GONE);
                button3.setImageResource(R.drawable.baseline_add_24);
            }
        });

        button4.setOnClickListener(v -> {
            if (tv4.getVisibility() == TextView.GONE) {
                tv4.setVisibility(TextView.VISIBLE);
                button4.setImageResource(R.drawable.baseline_remove_24);
            } else {
                tv4.setVisibility(TextView.GONE);
                button4.setImageResource(R.drawable.baseline_add_24);
            }
        });

        button5.setOnClickListener(v -> {
            if (tv5.getVisibility() == TextView.GONE) {
                tv5.setVisibility(TextView.VISIBLE);
                button5.setImageResource(R.drawable.baseline_remove_24);
            } else {
                tv5.setVisibility(TextView.GONE);
                button5.setImageResource(R.drawable.baseline_add_24);
            }
        });

        buttonReturn.setOnClickListener(v -> {
            finish();
        });
    }
}