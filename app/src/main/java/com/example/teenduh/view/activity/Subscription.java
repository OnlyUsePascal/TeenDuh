package com.example.teenduh.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teenduh.R;
import com.example.teenduh.model.Image;
import com.example.teenduh.view.adapter.ImageAdapter;

import java.util.ArrayList;
import java.util.List;

public class Subscription extends AppCompatActivity {
  private ViewPager2 viewPager2;
  private List<Image> imageList;
  private CardView circle1,circle2;
  private ImageView cancelBtn;
  private CardView plusCardView1, goldCardView1, plusCardView2, goldCardView2;
  private TextView purchase;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_subscription);
    viewPager2 = findViewById(R.id.viewPager2);
    circle1 = findViewById(R.id.circle1);
    circle2 = findViewById(R.id.circle2);
    cancelBtn = findViewById(R.id.cancelBtn);

    plusCardView1 = findViewById(R.id.plus_card_1);
    plusCardView2 = findViewById(R.id.plus_card_2);
    goldCardView1 = findViewById(R.id.gold_card_1);
    goldCardView2 = findViewById(R.id.gold_card_2);
    purchase = findViewById(R.id.buttonSubscription);

    cancelBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
        overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
      }
    });
    viewPager2.bringToFront();
    imageList = new ArrayList<Image>();
    imageList.add(new Image(R.drawable.tinder_plus_cover, "nuill"));
    imageList.add(new Image(R.drawable.tidner_premium, "nuill"));
    viewPager2.setAdapter(new ImageAdapter(imageList));
    viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
      @Override
      public void onPageSelected(int position) {
        super.onPageSelected(position);
        if (position == 0) {
          circle1.setForeground(getResources().getDrawable(R.drawable.btn_layout));
          circle2.setForeground(getResources().getDrawable(R.drawable.dashline_unselected));
          plusCardView1.setVisibility(View.VISIBLE);
          plusCardView2.setVisibility(View.VISIBLE);
          goldCardView1.setVisibility(View.INVISIBLE);
          goldCardView2.setVisibility(View.INVISIBLE);
          purchase.setBackgroundResource(R.drawable.bg_layout_plus);
          purchase.setText("STARTING AT $9.99/MONTH");
          purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Toast.makeText(Subscription.this, "Coming soon!!!", Toast.LENGTH_SHORT).show();
              finish();
              overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
            }
          });

        } else if (position == 1) {
          circle2.setForeground(getResources().getDrawable(R.drawable.btn_layout));
          circle1.setForeground(getResources().getDrawable(R.drawable.dashline_unselected));
          plusCardView1.setVisibility(View.INVISIBLE);
          plusCardView2.setVisibility(View.INVISIBLE);
          goldCardView1.setVisibility(View.VISIBLE);
          goldCardView2.setVisibility(View.VISIBLE);
          purchase.setBackgroundResource(R.drawable.bg_layout_premium);
          purchase.setText("STARTING AT $14.99/MONTH");
          purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Toast.makeText(Subscription.this, "Coming soon", Toast.LENGTH_SHORT).show();
              finish();
              overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
            }
          });
        }
      }
    });
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
  }

}