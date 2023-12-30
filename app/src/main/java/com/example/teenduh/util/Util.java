package com.example.teenduh.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.teenduh.Entity.User;

public class Util {
  private static Context context;
  
  public static Context getContext() {
    return context;
  }
  
  public static void setContext(Context context) {
    Util.context = context;
  }
  public static User curUser = new User();
  
  public static void _startActivity(Context context, Class<?> target){
    Intent intent = new Intent(context, target);
    context.startActivity(intent);
  }
  
  public static void _startActivityForResult(Activity activity, Class<?> target
      , Bundle bundle, int code){
    Intent intent = new Intent(context, target);
    intent.putExtras(bundle);
    activity.startActivityForResult(intent, code);
  }
}
