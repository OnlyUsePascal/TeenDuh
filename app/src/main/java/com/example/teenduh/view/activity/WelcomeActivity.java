package com.example.teenduh.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.os.Handler;

import com.example.teenduh.R;
import com.example.teenduh.model.Image;
import com.example.teenduh.view.adapter.ImageAdapter;
import com.example.teenduh.view.adapter.WelcomeAdapter;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends AppCompatActivity {
    private ViewPager2 viewPager2;
    private List<Image> imageList;
    private WelcomeAdapter imageAdapter;

    private Handler sliderHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        viewPager2 = findViewById(R.id.viewPager2);
        viewPager2.bringToFront();
        imageList = new ArrayList<>();
        imageList.add(new Image(R.drawable.ronaldo, "Cristiano Ronaldo"));
        imageList.add(new Image(R.drawable.park_seo, "Park Seo Joon"));
        imageList.add(new Image(R.drawable.modric, "Luka Modric"));


        viewPager2.setAdapter(new WelcomeAdapter(imageList, viewPager2));
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.setClipChildren(false);
        viewPager2.setClipToPadding(false);

        viewPager2.getChildAt(0).setOverScrollMode(ViewPager2.OVER_SCROLL_NEVER);

        CompositePageTransformer transformer = new CompositePageTransformer();
        transformer.addTransformer(new MarginPageTransformer(40));
        transformer.addTransformer((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleY(0.85f + r * 0.15f);
        });
        viewPager2.setPageTransformer(transformer);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback(){
            @Override
            public void onPageSelected(int position){
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(runnable);
                sliderHandler.postDelayed(runnable, 3000);
            }
        });
    }
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };
    @Override
    protected void onPause(){
        super.onPause();
        sliderHandler.removeCallbacks(runnable);
    }
    @Override
    protected void onResume(){
        super.onResume();
        sliderHandler.postDelayed(runnable, 3000);
    }
}