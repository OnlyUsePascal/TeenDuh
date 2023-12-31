package com.example.teenduh.util;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;

public class FirebaseUtil {
  private static FirebaseUser user;
  private static FirebaseAuth firebaseAuth;
  private static PhoneAuthCredential credential;
  
  public static void setCredential(PhoneAuthCredential _credential) {
    credential = _credential;
  }
  
  public static void setUser(FirebaseUser _user) {
    user = _user;
  }
  
  public static PhoneAuthCredential getCredential() {
    return credential;
    
  }
  
  public static FirebaseAuth getFirebaseAuth() {
    if (firebaseAuth == null) {
      firebaseAuth = FirebaseAuth.getInstance();
    }
    return firebaseAuth;
  }
}
