package com.example.teenduh._util;

import android.content.Context;
import android.os.Handler;

import com.example.teenduh.model.User;
import com.example.teenduh.model.message.Chat;
import com.example.teenduh.model.message.ChatRoom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class FirebaseUtil {
  private static Context context;
  private static FirebaseUser curUser;
  private static FirebaseFirestore firestore;
  private static FirebaseStorage storage;
  private static FirebaseMessaging messaging;
  private static FirebaseAuth auth;
  private static PhoneAuthCredential credential;
  private static String fcm;
  
  public static void init() {
    auth = FirebaseAuth.getInstance();
    messaging = FirebaseMessaging.getInstance();
    firestore = FirebaseFirestore.getInstance();
    storage = FirebaseStorage.getInstance("gs://teenduh-t1.appspot.com");

    firestore.setLoggingEnabled(true);

    auth.signOut();
    _getFcm();
  }
  
  public static void _getFcm() {
    messaging.getToken().addOnCompleteListener(task -> {
      if (!task.isSuccessful()) {
        task.getException().printStackTrace();
        return;
      }
      
      fcm = task.getResult();
      System.out.println("fcm:" + task.getResult());
    });
  }
  
  public static void setFcm(String fcm) {
    FirebaseUtil.fcm = fcm;
  }
  
  public static String getFcm() {
    return fcm;
  }
  
  public static void setCredential(PhoneAuthCredential _credential) {
    credential = _credential;
  }
  
  public static void setCurUser(FirebaseUser _user) {
    curUser = _user;
  }
  
  public static void loginEmail(String mail, String pwd, Runnable runnable) {
    auth.signOut();
    auth.signInWithEmailAndPassword(mail, pwd)
        .addOnCompleteListener(task -> {
          setCurUser(auth.getCurrentUser());
          runRunnable(runnable);
        });
  }
  
  public static PhoneAuthCredential getCredential() {
    return credential;
    
  }
  
  public static FirebaseAuth getAuth() {
    return auth;
    
  }
  
  public static FirebaseUser getCurUser() {
    return getAuth().getCurrentUser();
  }
  
  public static void updateChatRoom(HashMap<String, Object> data, Consumer<String> consumer) {
    ChatRoom chatRoom = AndroidUtil.getCurChatRoom();
    firestore.collection("chatRooms")
        .document(chatRoom.getId())
        .update(data).addOnCompleteListener(task -> {
          if (!task.isSuccessful()) {
            task.getException().printStackTrace();
            return;
          }
          
          consumer.accept(data.get("lastMessId").toString());
        });
  }
  
  public static void addChat(HashMap<String, Object> chat, Consumer<String> consumer) {
    ChatRoom chatRoom = AndroidUtil.getCurChatRoom();
    firestore.collection("chatRooms")
        .document(chatRoom.getId())
        .collection("chats")
        .add(chat).addOnCompleteListener(task -> {
          if (!task.isSuccessful()) {
            task.getException().printStackTrace();
            return;
          }
          
          HashMap<String, Object> data = new HashMap<>();
          data.put("lastMess", chat.get("mess"));
          data.put("lastActive", chat.get("time"));
          data.put("lastSender", chat.get("sender"));
          data.put("lastMessId", task.getResult().getId());
          updateChatRoom(data, consumer);
        });
  }
  
  public static void updateNewUser(Runnable runnable){
    User newUser = AndroidUtil.getCurUser();
    HashMap<String, Object> data = new HashMap<>();
    
    data.put("name", newUser.getName());
    data.put("fcm", newUser.getFcm());
    
    firestore.collection("users").document(newUser.getId())
        .set(data).addOnCompleteListener(task -> {
          if (!task.isSuccessful()){
            task.getException().printStackTrace();
            return;
          }
          
          runRunnable(runnable);
        });
  }
  public static void createNewUser(String id, HashMap<String, Object> data, Runnable runnable) {
    firestore.collection("users").document(id)
        .set(data).addOnCompleteListener(task -> {
          if (!task.isSuccessful()){
            task.getException().printStackTrace();
            return;
          }

          runRunnable(runnable);
        });
  }
  public static void updateUser(String id, HashMap<String, Object> data, Runnable runnable) {
    firestore.collection("users").document(id)
        .update(data)
        .addOnCompleteListener(task -> {
          if (!task.isSuccessful()) {
            task.getException().printStackTrace();
            return;
          }

          runRunnable(runnable);
        });
  }
  
  public static void fetchUsers(Consumer<List<DocumentSnapshot>> cb) {
    firestore.collection("users")
        .get()
        .addOnCompleteListener(task -> {
          if (!task.isSuccessful()) {
            task.getException().printStackTrace();
            return;
          }
          cb.accept(task.getResult().getDocuments());
        });
  }

  public static void fetchUsersBan(Consumer<List<DocumentSnapshot>> cb) {
        firestore.collection("users_ban")
                .get()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        task.getException().printStackTrace();
                        return;
                    }
                    cb.accept(task.getResult().getDocuments());
                });
    }
  
  public static void fetchChatRooms(Consumer<List<DocumentSnapshot>> cb) {
    firestore.collection("chatRooms")
        .whereArrayContains("users", AndroidUtil.getCurUser().getId())
        .orderBy("lastActive", Query.Direction.DESCENDING)
        .get()
        .addOnCompleteListener(task -> {
          if (!task.isSuccessful()) {
            task.getException().printStackTrace();
            return;
          }
          
          cb.accept(task.getResult().getDocuments());
        });
  }
  
  public static void fetchChats(Consumer<List<DocumentSnapshot>> cb) {
    firestore.collection("chatRooms")
        .document(AndroidUtil.getCurChatRoom().getId())
        .collection("chats")
        .orderBy("time", Query.Direction.ASCENDING)
        .get()
        .addOnCompleteListener(task -> {
          if (!task.isSuccessful()) {
            task.getException().printStackTrace();
            return;
          }
          
          cb.accept(task.getResult().getDocuments());
        });
  }
  
  public static void runRunnable(Runnable runnable){
    new Handler().postDelayed(() -> {
      if (runnable != null) runnable.run();
    }, 500);
  }

  public static void initStorage() {
    storage = FirebaseStorage.getInstance("gs://teenduh-t1.appspot.com");
  }

  public static FirebaseFirestore getFirestore() {
    return firestore;
  }

  public static StorageReference getStorageRef() {
    return storage.getReference();
  }
}
