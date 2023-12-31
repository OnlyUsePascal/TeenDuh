package com.example.teenduh.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.teenduh.model.User;

public class AndroidUtil {
  private static Context context;
  private static User curUser = new User();
  
  
  public static Context getContext() {
    return context;
    
  }
  
  public static void setContext(Context context) {
    AndroidUtil.context = context;
    
  }
  
  public static void initData() {
  
  }
  
  public static User getCurUser(){
    return curUser;
  }
  
  public static void _startActivity(Context context, Class<?> target) {
    Intent intent = new Intent(context, target);
    context.startActivity(intent);
  }
  
  public static void _startActivityForResult(Activity activity, Class<?> target
      , Bundle bundle, int code) {
    Intent intent = new Intent(context, target);
    if (bundle != null) {
      intent.putExtras(bundle);
    }
    activity.startActivityForResult(intent, code);
  }
  
  public static void _hideKeyboardFrom(Context context, View view) {
    InputMethodManager imm = (InputMethodManager)
                                 context.getSystemService(Activity.INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
  }
  
  public static void _openExternalURL(Context context, String url) {
    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
    context.startActivity(intent);
  }
}
