package com.example.teenduh.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.teenduh.R;
import com.example.teenduh.util.Util;
import com.example.teenduh.view.fragment.TinDuh;

public class MainLayout extends AppCompatActivity {
  private TinDuh fragTinDuh;
  private FrameLayout frameLayout;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main_layout);
    Util.setContext(this);
    
    frameLayout = findViewById(R.id.container);
    fragTinDuh = new TinDuh();
    changeFragment(fragTinDuh);
  }
  
  public void changeFragment(Fragment fragment) {
    getSupportFragmentManager().beginTransaction()
        .replace(frameLayout.getId(), fragment).commit();
  }
}