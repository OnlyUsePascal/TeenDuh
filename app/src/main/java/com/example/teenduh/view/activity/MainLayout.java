package com.example.teenduh.view.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
    initNavBar();
  
    // TODO RMB TO COMMENT THIS
    FirebaseUtil.init();
    AndroidUtil.init(this);
    // AndroidUtil.loginEmail(R.id.button13, () -> {
    //   System.out.println("--temp login");
    // });
    
    // todo check permission
    new Handler().postDelayed(() -> {
      if (!AskPermission.areAllPermissionsGranted(this)){
        runOnUiThread(() -> {
          Intent intent = new Intent(this, AskPermission.class);
          startActivity(intent);
          overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
        });
      }
    },1000);
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
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    fragmentTransaction.replace(R.id.container, fragment);
    fragmentTransaction.commit();
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
      } else if(itemId == R.id.menu_matches){
        changeFragment(fragMatch);
      }
      return true;
    });
    changeFragment(fragTeenDuh);
  }
}