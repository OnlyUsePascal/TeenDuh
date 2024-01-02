package com.example.teenduh.view.activity.TestingChat.utils;

import com.example.teenduh.view.activity.TestingChat.model.UserModel;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.List;

public class FirebaseUtil {

    public static String currentUserId(){
        return FirebaseAuth.getInstance().getUid();
    }

    public static boolean isLoggedIn(){
        if(currentUserId()!=null){
            return true;
        }
        return false;
    }

    public static DocumentReference currentUserDetails(){
        return FirebaseFirestore.getInstance().collection("users").document(currentUserId());
    }

    public static CollectionReference allUserCollectionReference(){
        return FirebaseFirestore.getInstance().collection("users");
    }

    public static DocumentReference getChatroomReference(String chatroomId){
        return FirebaseFirestore.getInstance().collection("chatrooms").document(chatroomId);
    }

    public static CollectionReference getChatroomMessageReference(String chatroomId){
        return getChatroomReference(chatroomId).collection("chats");
    }

    public static String getChatroomId(String userId1,String userId2){
        if(userId1.hashCode()<userId2.hashCode()){
            return userId1+"_"+userId2;
        }else{
            return userId2+"_"+userId1;
        }
    }

    public static CollectionReference allChatroomCollectionReference(){
        return FirebaseFirestore.getInstance().collection("chatrooms");
    }

    public static DocumentReference getOtherUserFromChatroom(List<String> userIds){
        if(userIds.get(0).equals(FirebaseUtil.currentUserId())){
            return allUserCollectionReference().document(userIds.get(1));
        }else{
            return allUserCollectionReference().document(userIds.get(0));
        }
    }

    public static String timestampToString(Timestamp timestamp){
        return new SimpleDateFormat("HH:MM").format(timestamp.toDate());
    }

    public static void logout(){
        FirebaseAuth.getInstance().signOut();
    }

    public static StorageReference  getCurrentProfilePicStorageRef(){
        return FirebaseStorage.getInstance().getReference().child("profile_pic")
                .child(FirebaseUtil.currentUserId());
    }

    public static StorageReference  getOtherProfilePicStorageRef(String otherUserId){
        return FirebaseStorage.getInstance().getReference().child("profile_pic")
                .child(otherUserId);
    }

//    public static void getUserModelFromPhone(String phone, OnUserDataReceivedListener listener){
//        //search query
//        UserModel userModel = new UserModel();
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        db.collection("users").whereEqualTo("phone",phone).get().addOnCompleteListener(task -> {
//            if(task.isSuccessful()){
//                if(task.getResult().size()>0){
//                    userModel.setPhone(task.getResult().getDocuments().get(0).getString("phone"));
//                    userModel.setUsername(task.getResult().getDocuments().get(0).getString("username"));
//                    userModel.setUserId(task.getResult().getDocuments().get(0).getString("userId"));
//                    userModel.setFcmToken(task.getResult().getDocuments().get(0).getString("fcmToken"));
//                }
//            }
//        });
//        listener.onReceived(userModel);
////        return userModel;
//    }

    public static void getUserModelFromPhone(String phone, OnUserDataReceivedListener listener) {
        if (phone == null) {
            // Handle the case where phone is null
            listener.onReceived(null);
            return;
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").whereEqualTo("phone", phone).get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && !task.getResult().isEmpty()) {
                DocumentSnapshot document = task.getResult().getDocuments().get(0);
                UserModel userModel = new UserModel();
                userModel.setPhone(document.getString("phone"));
                userModel.setUsername(document.getString("username"));
                userModel.setUserId(document.getString("userId"));
                userModel.setFcmToken(document.getString("fcmToken"));
                System.out.println("userModel123123: " + userModel);
                listener.onReceived(userModel);

            } else {
                // Handle the case where the task is not successful or no results are found
                System.out.println("task unsuccessful");
                listener.onReceived(null);
            }
        });
    }


    public static interface OnUserDataReceivedListener {
        void onReceived(UserModel userModel);
    }


}










