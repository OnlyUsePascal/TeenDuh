package com.example.teenduh._util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.teenduh.R;
import com.example.teenduh.model.User;
import com.example.teenduh.model.message.Chat;
import com.example.teenduh.model.message.ChatRoom;
import com.example.teenduh.view.activity.MainLayout;
import com.example.teenduh.view.activity.SignUpPage;
import com.example.teenduh.view.adapter.message.ChatAdapter;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class AndroidUtil {
  private static Activity activity;
  private static User curUser;
  private static ChatRoom curChatRoom;
  private static List<User> users;
  private static List<ChatRoom> chatRooms;
  private static List<Chat> chats;
  private static RecyclerView chatView;
  private static ChatAdapter chatAdapter;
  private static String flagMatch;

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
    chatRooms = new ArrayList<>();
    
    // curUser = new User();
    fetchUsers(null);
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
  
  public static void setCurNewUser(String id, Runnable runnable){
    User newUser = new User(id, "__blank", FirebaseUtil.getFcm());
    setCurUser(newUser);
    if (runnable != null) runnable.run();
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
        
        if (fcm == null) fcm = "blank";
        User user = new User(uid, name, fcm);
        users.add(user);
      }
      
      System.out.println(users);
      if (runnable != null) runnable.run();
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
      if (runnable != null) runnable.run();
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
      setCurNewUser(FirebaseUtil.getCurUser().getUid(), runnable); // not on firebase yet
    } else {
      setCurUser(user);
      // update fcm
      HashMap<String, Object> data = new HashMap<>();
      data.put("fcm", FirebaseUtil.getFcm());
      FirebaseUtil.updateUser(user.getId(), data, runnable);
    }
  }
  
}