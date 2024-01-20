package com.example.teenduh.view.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.teenduh.R;
import com.example.teenduh._util.AndroidUtil;
import com.example.teenduh._util.FirebaseUtil;
import com.example.teenduh.view.fragment.MatchFragment;
import com.example.teenduh.view.fragment.message.ChitChat;
import com.example.teenduh.view.fragment.profile.Profile;
import com.example.teenduh.view.fragment.TeenDuh;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainLayout extends AppCompatActivity {
  private TeenDuh fragTeenDuh;
  private ChitChat fragChitChat;
  private Profile fragProfile;
  private FrameLayout frameLayout;
  private BottomNavigationView navBar;
  private MatchFragment fragMatch;
  private int currentFragmentIndex = 0;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main_layout);
    AndroidUtil.setActivity(this);
    
    frameLayout = findViewById(R.id.container);
    navBar = findViewById(R.id.navbar);
    
    fragTeenDuh = new TeenDuh();
    fragChitChat = new ChitChat();
    fragProfile = new Profile();
    fragMatch = new MatchFragment();

    changeFragment(fragTeenDuh, 0);
    getWindow().setNavigationBarColor(getResources().getColor(R.color.secondary));

    initNavBar();
  
    FirebaseUtil.init();
    AndroidUtil.init(this);

    // TODO: temp login, change in prod
    AndroidUtil.loginEmail(R.id.button13, () -> {
      System.out.println("--temp login");
    });
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

  @Override
  public void onStop() {
    super.onStop();
    getWindow().setNavigationBarColor(getResources().getColor(R.color.md_theme_light_background));
  }

  @Override
  public void onResume() {
    super.onResume();
    getWindow().setNavigationBarColor(getResources().getColor(R.color.secondary));
  }
  
  public void changeFragment(Fragment fragment, int position) {
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

    if (position > currentFragmentIndex) {
      fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
    } else if (position < currentFragmentIndex) {
      fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    currentFragmentIndex = position;

    fragmentTransaction.replace(R.id.frame, fragment);
    fragmentTransaction.commit();
  }
  
  public void initNavBar() {
    navBar.setOnItemSelectedListener(item -> {
      int itemId = item.getItemId(), newPosition = 0;
      Fragment fragment = null;

      if (itemId == R.id.menu_discover) {
        fragment = fragTeenDuh;
        newPosition = 0;
      } else if (itemId == R.id.menu_chat) {
        fragment = fragChitChat;
        newPosition = 1;
      } else if (itemId == R.id.menu_matches) {
        fragment = fragMatch;
        newPosition = 2;
      } else if (itemId == R.id.menu_profile) {
        fragment = fragProfile;
        newPosition = 3;
      }

      changeFragment(fragment, newPosition);

      return true;
    });
  }
}