package com.example.teenduh.view.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.teenduh.R;
import com.example.teenduh._util.AndroidUtil;
import com.example.teenduh._util.FirebaseUtil;
import com.example.teenduh.model.User;
import com.example.teenduh.model.UserBan;
import com.example.teenduh.view.fragment.Database;
import com.example.teenduh.view.fragment.MatchFragment;
import com.example.teenduh.view.fragment.Report;
import com.example.teenduh.view.fragment.Statistic;
import com.example.teenduh.view.fragment.message.ChitChat;
import com.example.teenduh.view.fragment.profile.Profile;
import com.example.teenduh.view.fragment.TeenDuh;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.ktx.Firebase;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class MainLayout extends AppCompatActivity {
  private TeenDuh fragTeenDuh;
  private ChitChat fragChitChat;
  private Profile fragProfile;
  private FrameLayout frameLayout;
  private BottomNavigationView navBar;
  private MatchFragment fragMatch;
  private int currentFragmentIndex = 0;
  private Database fragDatabase;
  private Statistic fragStatistic;
  private Report fragReport;
  private boolean isBan = false;

  private LatLng location;

  private final int REQUEST_CODE_PERMISSIONS = 100;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main_layout);
    AndroidUtil.setActivity(this);
    
    frameLayout = findViewById(R.id.container);
    navBar = findViewById(R.id.navbar);

    //chance menu based on Role
    if (AndroidUtil.checkIsAdmin()) {
      navBar.getMenu().clear(); // Clear the old menu
      navBar.inflateMenu(R.menu.navbar_menu_admin); // Inflate the new menu
    } else {
      navBar.getMenu().clear(); // Clear the old menu
      navBar.inflateMenu(R.menu.navbar_menu); // Inflate the new menu
    }

    fragTeenDuh = new TeenDuh();
    fragChitChat = new ChitChat();
    fragProfile = new Profile();
    fragMatch = new MatchFragment();

    getWindow().setNavigationBarColor(getResources().getColor(R.color.secondary));

    fragDatabase = new Database();
    fragStatistic = new Statistic();
    fragReport = new Report();

    initNavBar();

    new Handler().postDelayed(() -> {
      if (checkIsBan()) {
        openBanDialog();
      }
    }, 1000);

    checkAndRequestPermissions();

  }
  
  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    System.out.println("on act result");
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == REQUEST_CODE_PERMISSIONS) {
      // Check again if permissions are granted after returning from AskPermission activity
      if (AskPermission.areAllPermissionsGranted(this)) {
        getLocation(); // Now we can safely get the location
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

    fragmentTransaction.replace(R.id.container, fragment);
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
      } else if (itemId == R.id.menu_database) {
        fragment = fragDatabase;
        newPosition = 4;
      } else if (itemId == R.id.menu_statistic) {
        fragment = fragStatistic;
        newPosition = 5;
      } else if (itemId == R.id.menu_report) {
        fragment = fragReport;
        newPosition = 6;
      }

      changeFragment(fragment, newPosition);

      return true;
    });

    if (AndroidUtil.checkIsAdmin()) {
      changeFragment(fragDatabase, 4);
    } else {
      changeFragment(fragTeenDuh, 0);
    }
  }

  private boolean checkIsBan() {
    User curUser = AndroidUtil.getCurUser();
    List<UserBan> usersBan = AndroidUtil.getUsersBan();
    for (UserBan user: usersBan) {
      if (user.getId().equals(curUser.getId()) && user.getDeadline().isAfter(LocalDate.now()) ) {
        return true;
      }
    }
    return false;
  }

  private void checkAndRequestPermissions() {
    // todo check permission
    new Handler().postDelayed(() -> {
      if (!AskPermission.areAllPermissionsGranted(this)){
        runOnUiThread(() -> {
          Intent intent = new Intent(this, AskPermission.class);
          startActivityForResult(intent, REQUEST_CODE_PERMISSIONS);
          overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
        });
      } else {
        getLocation();
      }
    },1000);
  }

  private void getLocation() {
    LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

    String locationProvider = LocationManager.GPS_PROVIDER; // Or, use LocationManager.NETWORK_PROVIDER

    try {
      Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);

      if (lastKnownLocation != null) {
        double latitude = lastKnownLocation.getLatitude();
        double longitude = lastKnownLocation.getLongitude();
        location = new LatLng(latitude, longitude);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("location", location);
        FirebaseUtil.updateUser(AndroidUtil.getCurUser().getId(), hashMap, null);

        // Use the latitude and longitude as needed
      } else {
        // Handle location not available
      }
    } catch (SecurityException e) {
      // Handle the exception
    }
  }

  private void openBanDialog() {
    final Dialog dialog = new Dialog(this);
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setContentView(R.layout.ban_dialog_layout);

    Button confirmButton = dialog.findViewById(R.id.button_confirm);

    confirmButton.setOnClickListener(v -> {
      dialog.dismiss();
      finish();
    });

    dialog.setCancelable(false);

    dialog.show();
    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    dialog.getWindow().setGravity(Gravity.CENTER);

  }
}