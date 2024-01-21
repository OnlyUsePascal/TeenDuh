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
import com.example.teenduh.view.activity.payment.PaymentActivity;

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
    AndroidUtil._startActivity(this, PaymentActivity.class);
  }
  public void toNotification(View view){
    makeNotification();
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
  
}