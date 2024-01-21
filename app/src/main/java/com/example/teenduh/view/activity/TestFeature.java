package com.example.teenduh.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.teenduh.R;
import com.example.teenduh._util.AndroidUtil;
import com.example.teenduh._util.FirebaseUtil;
import com.example.teenduh.view.activity.auth.AuthLayout;
import com.example.teenduh.view.activity.payment.DropInClientActivity;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestFeature extends AppCompatActivity {
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_test_feature);

    FirebaseUtil.init();
    AndroidUtil.init(this);
  }
  
  public void toMainLayout(View view){
    AndroidUtil._startActivity(this, MainLayout.class);
    
  }
  
  public void toAuth(View view){
    AndroidUtil._startActivity(this, AuthLayout.class);
    
  }
  
  public void toWelcome(View view){
    AndroidUtil._startActivity(this, WelcomeActivity.class);
  }
  
  public void toPermit(View view){
    AndroidUtil._startActivity(this, AskPermission.class);
  }
  
  public void toSignUp(View view){
    AndroidUtil._startActivity(this, SignUpPage.class);
  }
  
  public void toPayment(View view){
    AndroidUtil._startActivity(this, DropInClientActivity.class);
  }
  public void toNotification(View view){
    makeNotification();
    //Add new User to Database
//    toLoadMultipleData();
  }

  public void makeNotification(){
    String channelID = "CHANNEL_ID_NOTIFICATION";
    NotificationCompat.Builder builder =  new NotificationCompat.Builder(getApplicationContext(), channelID)
            .setSmallIcon(R.drawable.baseline_notifications_24)
            .setContentTitle("Notification title")
            .setContentText("This is a test notification")
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT);
    Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    intent.putExtra("message", "This is a test notification");

    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent
                                , PendingIntent.FLAG_MUTABLE);

    builder.setContentIntent(pendingIntent);
    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    NotificationChannel notificationChannel =  notificationManager.getNotificationChannel(channelID);
    if (notificationChannel == null){
      int importance = NotificationManager.IMPORTANCE_HIGH;
      notificationChannel = new NotificationChannel(channelID, "NOTIFICATION_CHANNEL_NAME", importance);
      notificationChannel.setDescription("NOTIFICATION_CHANNEL_DESCRIPTION");
      notificationChannel.setLightColor(R.color.red);
      notificationChannel.enableVibration(true);
      notificationManager.createNotificationChannel(notificationChannel);

    }
    notificationManager.notify(0, builder.build());
  }
  public void toLoadData(){
//    HashMap<String, Object> data = new HashMap<>();
//    String id = "hmZfVijWRxc7yUoqt2oxuXoJr5X2";
//    data.put("fcm", "null");
//    data.put("name", "Nhan Truong");
//    data.put("gender", "male");
//    // birthdate to timestamp
//    Calendar calendar = Calendar.getInstance();
//    calendar.set(Calendar.YEAR, 2023);
//    calendar.set(Calendar.MONTH, Calendar.SEPTEMBER); // Note: Calendar months are zero-based
//    calendar.set(Calendar.DAY_OF_MONTH, 11);
//    calendar.set(Calendar.HOUR_OF_DAY, 0); // Midnight
//    calendar.set(Calendar.MINUTE, 0);
//    calendar.set(Calendar.SECOND, 0);
//    calendar.set(Calendar.MILLISECOND, 0);
//
//// Convert Calendar to Timestamp
//    Timestamp timestamp = new Timestamp(calendar.getTime());
//    data.put("bday", timestamp);
//    data.put("phone", "123456789");
//    data.put("Location", new LatLng(10.0, 10.0));
//    FirebaseUtil.updateUser(id,data, null);
  }
  public static  HashMap<String, Object> createUserData( String name, String gender, Timestamp birthdate, LatLng location){
    HashMap<String, Object> userData = new HashMap<>();
    userData.put("fcm", "null");
    userData.put("name", name);
    userData.put("gender", gender);
    userData.put("bday", birthdate);
    userData.put("Location", location);
    return userData;
  }
  private static Timestamp getDateTimestamp(int year, int month, int day) {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.YEAR, year);
    calendar.set(Calendar.MONTH, month); // Note: Calendar months are zero-based
    calendar.set(Calendar.DAY_OF_MONTH, day);
    calendar.set(Calendar.HOUR_OF_DAY, 0); // Midnight
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    return new Timestamp(calendar.getTime());
  }
  public static void addMultipleUsers(List<HashMap<String, Object>> usersData, String id) {

    for (HashMap<String, Object> userData : usersData) {
      String userId = id; // You need to implement a method to generate unique user IDs
      FirebaseUtil.updateUser(userId, userData, null);
    }
  }
  public static List<HashMap<String,Object>> getUserList(){
    List<HashMap<String,Object>> usersData = new ArrayList<HashMap<String, Object>>() {};
    usersData.add(createUserData("Huan Nguyen", "Male", getDateTimestamp(1995, 9, 11),  new LatLng(69.0, 69.0)));

    return usersData;
  }
  public void toLoadMultipleData(){
    List<HashMap<String,Object>> usersData = getUserList();
    System.out.println("usersData = " + usersData.size());

    ArrayList<String> ids = new ArrayList<String>(){};
    ids.add("6Fk0FWdszQamxJqeuz0ZrndimGG2");

    updateUsers(usersData, ids, 0);
  }
  private static void updateUsers(List<HashMap<String, Object>> usersData, List<String> ids, int index) {
    if (index < usersData.size()) {
      System.out.println("usersData = " + usersData.get(index));
      System.out.println("ids = " + ids.get(index));

      // Call updateUser with a Runnable that triggers the next iteration
      FirebaseUtil.createNewUser(ids.get(index), usersData.get(index), () -> updateUsers(usersData, ids, index + 1));
    } else {
      // All iterations are completed
      System.out.println("All updates completed");
    }
  }
}