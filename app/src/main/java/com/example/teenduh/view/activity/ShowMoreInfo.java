package com.example.teenduh.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.teenduh.R;
import com.example.teenduh._util.AndroidUtil;
import com.example.teenduh.model.Image;
import com.example.teenduh.model.User;
import com.example.teenduh.view.adapter.ImageAdapter;
import com.example.teenduh.view.adapter.WelcomeAdapter;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class ShowMoreInfo extends AppCompatActivity {
  ViewPager2 viewPager2;
  private CardView cardView1;
  private  CardView cardView2;
  private  CardView cardView3;
  private  CardView cardView4;
  private  CardView cardView5;
  private  CardView cardView6;
  private TextView lookinFor, distance, age, sexual, zodiac, education, diet, drink, smoke ;
  private TextView cancelBtn, likeBtn, superLikeBtn;
  List<CardView> cardViewList = new ArrayList<>();

  int diffElement = 0;
  private LinearLayout linearLayout;
  private ImageView backButton;
  private String position;
  ArrayList<Image> imageList;
  @SuppressLint("ClickableViewAccessibility")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_show_more_info);
    viewPager2 = findViewById(R.id.viewPager2);
    cardView1 = findViewById(R.id.firstStage);
    cardView2 = findViewById(R.id.secondStage);
    cardView3 = findViewById(R.id.thirdStage);
    cardView4 = findViewById(R.id.fourthStage);
    cardView5 = findViewById(R.id.fifthStage);
    cardView6 = findViewById(R.id.sixthStage);
    backButton = findViewById(R.id.iconDown);

    linearLayout = findViewById(R.id.linearLayout);
    cardViewList.add(cardView1);
    cardViewList.add(cardView2);
    cardViewList.add(cardView3);
    cardViewList.add(cardView4);
    cardViewList.add(cardView5);
    cardViewList.add(cardView6);
    linearLayout.bringToFront();
    imageList = new ArrayList<>();
    imageList.add(new Image(R.drawable.ronaldo, "Cristiano Ronaldo"));
    imageList.add(new Image(R.drawable.park_seo, "Park Seo Joon"));
    imageList.add(new Image(R.drawable.modric, "Luka Modric"));

    lookinFor = findViewById(R.id.lookingFor);
    distance = findViewById(R.id.distance);
    age = findViewById(R.id.age);
    sexual = findViewById(R.id.sexual);
    zodiac = findViewById(R.id.zodiac);
    education = findViewById(R.id.education);
    diet = findViewById(R.id.diet);
    drink = findViewById(R.id.drinks);
    smoke = findViewById(R.id.smokes);

    lookinFor.setText("Looking for short" );
    distance.setText("Distance: 10 miles");
    age.setText("Single, 24");
    sexual.setText("Woman");
    zodiac.setText("Aries");
    education.setText("University");
    diet.setText("Vegetarian");
    drink.setText("Socially");
    smoke.setText("Never");

    cancelBtn = findViewById(R.id.cancel_action);
    superLikeBtn = findViewById(R.id.super_like_action);
    likeBtn = findViewById(R.id.like_action);


    backButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
        overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
      }
    });

    cancelBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        AndroidUtil.setFlagMatch("cancel");
        finish();
        overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
      }
    });
    superLikeBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        AndroidUtil.setFlagMatch("superlike");
        finish();
        overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
      }
    });

    likeBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        AndroidUtil.setFlagMatch("like");
        finish();
        overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
      }
    });



    if (cardViewList.size() > imageList.size()) {
      for (int i = cardViewList.size() - 1; i >= imageList.size(); i--) {
        cardViewList.get(i).setVisibility(View.GONE);
      }
    }
    diffElement = imageList.size();
    for(int i = 0; i < imageList.size(); i++){
      if(i == 0){
        ImageView imageView = (ImageView) cardViewList.get(i).getChildAt(0);
        imageView.setImageResource(R.drawable.custom_dashline_layout);
      }else{
        ImageView imageView = (ImageView) cardViewList.get(i).getChildAt(0);
        imageView.setImageResource(R.drawable.btn_unselected);
      }
    }
    viewPager2.setAdapter(new ImageAdapter(imageList, viewPager2));
    viewPager2.setOffscreenPageLimit(3);
    viewPager2.setClipChildren(false);
    viewPager2.setClipToPadding(false);
    viewPager2.setUserInputEnabled(false);
    viewPager2.getChildAt(0).setOverScrollMode(ViewPager2.OVER_SCROLL_NEVER);


   viewPager2.setOnTouchListener(new View.OnTouchListener() {
     float initialX;

     @Override
     public boolean onTouch(View v, MotionEvent event) {
       switch (event.getAction()) {
         case MotionEvent.ACTION_DOWN:
           initialX = event.getX();
           return true;

         case MotionEvent.ACTION_UP:
           float finalX = event.getX();
           int cardWidth = v.getWidth();
           float cardCenterX = cardWidth / 2.0f; // Center of the card
           if (finalX > cardCenterX) {
             viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
             int currentItem = viewPager2.getCurrentItem();
             for (int i = 0; i < diffElement; i++) {
               if (i == currentItem) {
                 ImageView imageView = (ImageView) cardViewList.get(i).getChildAt(0);
                 imageView.setImageResource(R.drawable.custom_dashline_layout);
               }else{
                 ImageView imageView = (ImageView) cardViewList.get(i).getChildAt(0);
                 imageView.setImageResource(R.drawable.btn_unselected);
               }
             }
           } else {
             viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
             int currentItem = viewPager2.getCurrentItem();
             for (int i = 0; i < diffElement; i++) {
               if (i == currentItem) {
                 ImageView imageView = (ImageView) cardViewList.get(i).getChildAt(0);
                 imageView.setImageResource(R.drawable.custom_dashline_layout);
               }
               else {
                 ImageView imageView = (ImageView) cardViewList.get(i).getChildAt(0);
                 imageView.setImageResource(R.drawable.btn_unselected);
               }
             }
           }
           break;
       }
       return false;
     }
   });
  }
  protected void onResume() {
    super.onResume();
    if(getIntent().getExtras() != null){
      Bundle bundle = getIntent().getExtras();
      position = bundle.getString("position");
      viewPager2.setCurrentItem(Integer.parseInt(position));
        for(int i = 0; i < imageList.size(); i++){
          if(i == Integer.parseInt(position)){
            ImageView imageView = (ImageView) cardViewList.get(i).getChildAt(0);
            imageView.setImageResource(R.drawable.custom_dashline_layout);
          }else{
            ImageView imageView = (ImageView) cardViewList.get(i).getChildAt(0);
            imageView.setImageResource(R.drawable.btn_unselected);
          }
        }
    }
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
  }
}