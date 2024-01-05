package com.example.teenduh.view.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.teenduh.R;
import com.example.teenduh.util.AndroidUtil;
import com.example.teenduh.view.fragment.message.ChitChat;
import com.example.teenduh.view.fragment.Profile;
import com.example.teenduh.view.fragment.TeenDuh;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainLayout extends AppCompatActivity {
  private TeenDuh fragTeenDuh;
  private ChitChat fragChitChat;
  private Profile fragProfile;
  private FrameLayout frameLayout;
  private BottomNavigationView navBar;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main_layout);
    AndroidUtil.setContext(this);
    
    frameLayout = findViewById(R.id.container);
    navBar = findViewById(R.id.navbar);
    
    fragTeenDuh = new TeenDuh();
    fragChitChat = new ChitChat();
    fragProfile = new Profile();
    initNavBar();
  }
  
  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    System.out.println("on act result");
    super.onActivityResult(requestCode, resultCode, data);
    switch (resultCode) {
      case 123: {
        System.out.println("something");
        break;
      }
    }
  }
  
  public void changeFragment(Fragment fragment) {
    getSupportFragmentManager().beginTransaction()
        .replace(frameLayout.getId(), fragment).commit();
  }
  
  public void initNavBar(){
    navBar.setOnItemSelectedListener(item -> {
      int itemId = item.getItemId();
      if (itemId == R.id.menu_discover) {
        changeFragment(fragTeenDuh);
      } else if (itemId == R.id.menu_chat) {
        changeFragment(fragChitChat);
      } else if (itemId == R.id.menu_profile) {
        changeFragment(fragProfile);
      }
      return true;
    });
    changeFragment(fragTeenDuh);
  }
}