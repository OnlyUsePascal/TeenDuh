package com.example.teenduh._util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.teenduh.R;
import com.example.teenduh.model.User;
import com.example.teenduh.model.UserBan;
import com.example.teenduh.model.message.Chat;
import com.example.teenduh.model.message.ChatRoom;
import com.example.teenduh.view.activity.MainLayout;
import com.example.teenduh.view.activity.SignUpPage;
import com.example.teenduh.view.adapter.message.ChatAdapter;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class AndroidUtil {
  private static Activity activity;
  private static User curUser;
  private static User _tempUser;
  private static ChatRoom curChatRoom;
  private static List<User> users;
  private static List<UserBan> usersBan;
  private static List<ChatRoom> chatRooms;
  private static List<Chat> chats;
  private static RecyclerView chatView;
  private static ChatAdapter chatAdapter;
  private static String flagMatch;
  private static boolean isAdmin = false;
  private static String genderFilter = "All";
  private static int filterFlag = 0;
  private static boolean isFromMoreInfo = false;
  
  public static boolean isIsFromMoreInfo() {
    return isFromMoreInfo;
  }
  
  public static void setIsFromMoreInfo(boolean isFromMoreInfo) {
    AndroidUtil.isFromMoreInfo = isFromMoreInfo;
  }
  
  public static int getFilterFlag() {
    return filterFlag;
  }

  public static void setFilterFlag(int filterFlag) {
    AndroidUtil.filterFlag = filterFlag;
  }

  public static String getGenderFilter() {
    return genderFilter;
  }

  public static void setGenderFilter(String genderFilter) {
    AndroidUtil.genderFilter = genderFilter;
  }

  public static boolean checkIsAdmin() {
    return curUser.getName().equals("Dat Pham");
  }

  public static void setAdmin(boolean admin) {
    isAdmin = admin;
  }

  public static String getFlagMatch() {
    return flagMatch;
  }

  public static void setFlagMatch(String flagMatch) {
    AndroidUtil.flagMatch = flagMatch;
  }

  public static ChatAdapter getChatAdapter() {
    return chatAdapter;
  }
  
  public static void setChatAdapter(ChatAdapter chatAdapter) {
    AndroidUtil.chatAdapter = chatAdapter;
  }
  
  public static List<Chat> getChats() {
    return chats;
  }
  
  public static void setChats(List<Chat> chats) {
    AndroidUtil.chats = chats;
  }
  
  public static RecyclerView getChatView() {
    return chatView;
  }
  
  public static void setChatView(RecyclerView chatView) {
    AndroidUtil.chatView = chatView;
  }
  
  public static Activity getActivity() { return activity; }
  
  public static void setActivity(Activity activity) {
    AndroidUtil.activity = activity;
  }

  
  public static void init(Activity activity) {
    if (activity != null) AndroidUtil.activity = activity;
    users = new ArrayList<>();
    usersBan = new ArrayList<>();
    chatRooms = new ArrayList<>();
    
    // curUser = new User();
    fetchUsers(null);
    fetchUsersBan(null);
  }
  
  public static ChatRoom getCurChatRoom() {
    return curChatRoom;
  }
  
  public static void setCurChatRoom(ChatRoom curChatRoom) {
    AndroidUtil.curChatRoom = curChatRoom;
  }
  
  public static List<User> getUsers() {
    return users;
  }

  public static List<UserBan> getUsersBan() {
    return usersBan;
  }
  
  public static void setUsers(List<User> users) {
    AndroidUtil.users = users;
  }
  
  public static List<ChatRoom> getChatRooms() {
    return chatRooms;
  }
  
  public static void setChatRooms(List<ChatRoom> chatRooms) {
    AndroidUtil.chatRooms = chatRooms;
  }
  
  public static User getCurUser() {
    return curUser;
  }
  
  public static void setCurUser(User curUser) {
    AndroidUtil.curUser = curUser;
  }
  
  public static void setCurUserWithId(String uid){
    System.out.println("cur user " + uid);
    for (User user : users) {
      if (user.getId().equals(uid)) {
        curUser = user;
        return;
      }
    }
  }
  
  public static User getUserWithId(String id){
    for (User user : users){
      if (user.getId().equals(id)){
        return user;
      }
    }
    
    return null;
  }
  
  public static User get_tempUser() {
    return _tempUser;
  }
  
  public static void set_tempUser(User _tempUser) {
    AndroidUtil._tempUser = _tempUser;
  }
  
  public static void setCurNewUser(String id, Runnable runnable){
    User newUser = new User(id, "__blank", FirebaseUtil.getFcm(), LocalDate.of(2000,1,1));
    setCurUser(newUser);
    FirebaseUtil.runRunnable(runnable);
  }
  
  
  public static void _startActivity(Context context, Class<?> target) {
    Intent intent = new Intent(context, target);
    context.startActivity(intent);
  }
  
  public static void _startActivityForResult(Activity activity, Class<?> target, int code) {
    Intent intent = new Intent(activity, target);
    intent.putExtra("reqCode", code);
    activity.startActivityForResult(intent, code);
  }
  
  public static void _startActivityForResult(Activity activity, Intent intent, int code) {
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
  
  public static Date parseDate(int year, int month, int day, int hour, int minute) {
    return Date.from(LocalDateTime.of(year, month, day, hour, minute).atZone(
        java.time.ZoneId.systemDefault()).toInstant());
  }
  
  public static void fetchUsers(Runnable runnable){
    FirebaseUtil.fetchUsers(documentSnapshots -> {
      for (DocumentSnapshot documentSnapshot : documentSnapshots){
        String name = documentSnapshot.getString("name");
        String fcm = documentSnapshot.getString("fcm");
        String uid = documentSnapshot.getId();
        Timestamp bday = documentSnapshot.getTimestamp("bday");
        String pic = documentSnapshot.getString("pic");
        LatLng location = null;
        String gender = documentSnapshot.getString("gender");
        String drink = documentSnapshot.getString("drinkHabit");
        String workout = documentSnapshot.getString("workoutHabit");
        String smoke = documentSnapshot.getString("smokeHabit");
        String pet = documentSnapshot.getString("petHabit");
        String communication = documentSnapshot.getString("communicationHabit");
        String education = documentSnapshot.getString("educationHabit");
        String zodiac = documentSnapshot.getString("zodiacHabit");


        if (drink == null) drink = "";
        if (workout == null) workout = "";
        if (smoke == null) smoke = "";
        if (pet == null) pet = "";
        if (communication == null) communication = "";
        if (education == null) education = "";
        if (zodiac == null) zodiac = "";

        // Use the localDate as needed
        Date date = bday.toDate();
        LocalDate bdayLocal = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        List<Integer> picIdxes = new ArrayList<>();
        if (pic == null) pic = "";
        for (String picIdx1 : pic.split(" ")) {
          try {
            picIdxes.add(Integer.parseInt(picIdx1));
          } catch (Exception e) {
            e.printStackTrace();
          }
        }

        if (fcm == null) fcm = "blank";
        Object locationObject = documentSnapshot.get("location");
        if (locationObject != null) {
          HashMap<String, Double> locationMap = (HashMap<String, Double>) locationObject;
          location = new LatLng(locationMap.get("latitude"), locationMap.get("longitude"));
        }
        System.out.println("location = " + location);

        List<String> info = (List<String>) documentSnapshot.get("info");
        if (info == null) {
          info = new ArrayList<>();
        }

        User user = new User(uid, name, fcm, bdayLocal, location, gender, drink, workout, smoke, pet, communication, education, zodiac);
        user.setPicIdxes(picIdxes);
        // user.fetchPics();
        users.add(user);
      }
      
      System.out.println(users);
      FirebaseUtil.runRunnable(runnable);
    });
  }

  public static void fetchUsersBan(Runnable runnable){
    FirebaseUtil.fetchUsersBan(documentSnapshots -> {
      for (DocumentSnapshot documentSnapshot : documentSnapshots){
        String id = documentSnapshot.getString("userId");
        String reason = documentSnapshot.getString("reason");
        Timestamp deadline = documentSnapshot.getTimestamp("deadline");
        LocalDate deadlineLocal = deadline.toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        UserBan user = new UserBan(id, reason, deadlineLocal);
        usersBan.add(user);
      }

      System.out.println(usersBan);
      FirebaseUtil.runRunnable(runnable);
    });
  }

  public static void fetchChatRooms(Runnable runnable){
    chatRooms = new ArrayList<>();
    FirebaseUtil.fetchChatRooms(documentSnapshots -> {
      for (DocumentSnapshot documentSnapshot : documentSnapshots){
        Timestamp lastAct = documentSnapshot.getTimestamp("lastActive");
        String lastMess = documentSnapshot.getString("lastMess");
        int lastSender =  documentSnapshot.getDouble("lastSender").intValue();
        List<String> uids = (List<String>) documentSnapshot.get("users");
        String id = documentSnapshot.getId();
        
        ChatRoom chatRoom = new ChatRoom(id, uids, lastAct, lastMess, lastSender);
        chatRooms.add(chatRoom);
      }
      
      System.out.println(chatRooms);
      runnable.run();
    });
  }
  
  public static void fetchChats(Runnable runnable){
    FirebaseUtil.fetchChats(documentSnapshots -> {
      List<Chat> chats = new ArrayList<>();
      for (DocumentSnapshot documentSnapshot : documentSnapshots){
        String id = documentSnapshot.getId();
        int user = documentSnapshot.getDouble("sender").intValue();
        Timestamp timestamp = documentSnapshot.getTimestamp("time");
        String mess = documentSnapshot.getString("mess");
        
        Chat chat = new Chat(id, mess, timestamp, user);
        chats.add(chat);
      }
      
      curChatRoom.setChats(chats);
      FirebaseUtil.runRunnable(runnable);
    });
  }
  
  public static void loginEmail(int btnId, Runnable runnable){
    String mail = (btnId == R.id.button13) ? "usera@mail.com" : "userb@mail.com";
    String pwd = "123123";
    
    FirebaseUtil.loginEmail(mail, pwd, () -> {
      setCurUserWithId(FirebaseUtil.getCurUser().getUid());
      HashMap<String, Object> data = new HashMap<>();
      data.put("fcm", FirebaseUtil.getFcm());
      FirebaseUtil.updateUser(curUser.getId(), data, runnable);
    });
  }
  
  public static void setupLogin(Activity activity1){
    User user = getUserWithId(FirebaseUtil.getCurUser().getUid());
    boolean exist = user != null;
    System.out.println("exists = " + exist + " -- " + FirebaseUtil.getCurUser().getUid());
  
    Runnable runnable = () -> {
      activity1.runOnUiThread(() -> {
        // ((TextView) findViewById(R.id.textView)).setText("Exist = " + exist);
        _startActivity(activity1, (exist) ? MainLayout.class : SignUpPage.class);
      });
    };
    if (!exist) {
      // users.add(user);
      setCurNewUser(FirebaseUtil.getCurUser().getUid(), runnable); // not on firebase yet
    } else {
      setCurUser(user);
      // update fcm
      HashMap<String, Object> data = new HashMap<>();
      data.put("fcm", FirebaseUtil.getFcm());
      FirebaseUtil.updateUser(user.getId(), data, runnable);
    }
  }
  
  public static void setupRegister(Runnable runnable){
    //take place after login
    users.add(curUser);
    FirebaseUtil.updateNewUser(runnable);
  }
  
  public static void makeToast(Context context, String mess){
    Toast.makeText(context, mess, Toast.LENGTH_SHORT).show();
  }

  public static double calculateDistance(LatLng latLng1, LatLng latLng2) {
    double lat1 = latLng1.latitude;
    double lon1 = latLng1.longitude;
    double lat2 = latLng2.latitude;
    double lon2 = latLng2.longitude;

    final int R = 6371; // Radius of the earth in kilometers

    double latDistance = Math.toRadians(lat2 - lat1);
    double lonDistance = Math.toRadians(lon2 - lon1);
    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
            + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    double distance = R * c; // Distance in kilometers

    return distance;
  }

}