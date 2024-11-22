package com.example.teenduh.view.activity.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.teenduh.R;
import com.example.teenduh._util.AndroidUtil;
import com.example.teenduh._util.FirebaseUtil;
import com.example.teenduh.view.activity.TestSuccess;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class AuthLayout extends AppCompatActivity {
  private final String TAG = "AuthLayout";
  private final int PHONE_AUTH_REQUEST_CODE = 1;
  private final int EMAIL_AUTH_REQUEST_CODE = 2;
  private GoogleSignInOptions googleSignInOptions;
  private GoogleSignInClient googleSignInClient;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_auth_layout);
    
    FirebaseUtil.init();
    AndroidUtil.init(this);
  }
  
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    System.out.println("> " + Integer.toString(requestCode) + " - " + Integer.toString(resultCode));
    switch (requestCode) {
      case PHONE_AUTH_REQUEST_CODE:
        // finish();
        // if (resultCode == RESULT_OK) handlePhoneAccount();
        break;
      case EMAIL_AUTH_REQUEST_CODE:
//        GoogleSignIn.getSignedInAccountFromIntent(data)
//            .addOnCompleteListener(this::handleGoogleAccount);
        break;
    }
    
  }
  
  public void googleLogin(View view) {
//    Toast.makeText(this, "google login", Toast.LENGTH_SHORT).show();;
//    googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                              .requestIdToken(getString(R.string.web_client_id))
//                              .requestEmail()
//                              .build();
//    googleSignInClient = GoogleSignIn.getClient(AuthLayout.this, googleSignInOptions);
//    googleSignInClient.signOut().addOnCompleteListener(task -> {
//      AndroidUtil._startActivityForResult(AuthLayout.this,
//          googleSignInClient.getSignInIntent(), GOOGLE_AUTH_REQUEST_CODE);
//    });
    AndroidUtil._startActivityForResult(AuthLayout.this, EmailAuthLayout.class, EMAIL_AUTH_REQUEST_CODE);
  }
  
  public void handleGoogleAccount(Task<GoogleSignInAccount> task) {
    if (!task.isSuccessful()) {
      task.getException().printStackTrace();
      return;
    }
    
    try {
      GoogleSignInAccount account = task.getResult(ApiException.class);
      AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
      
      FirebaseUtil.getAuth()
          .signInWithCredential(credential)
          .addOnCompleteListener(task1 -> {
            if (!task1.isSuccessful()){
              task1.getException().printStackTrace();
              return;
            }
            
            // FirebaseUtil.setCurUser(task1.getResult().getUser());
            FirebaseUtil.setCurUser(task1.getResult().getUser());
            AndroidUtil.setupLogin(this);
            System.out.println(FirebaseUtil.getCurUser());
          });
    } catch (Exception err) {
      err.printStackTrace();
    }
  }
  
  public void phoneLogin(View view) {
    AndroidUtil._startActivityForResult(AuthLayout.this, PhoneAuthLayout.class , PHONE_AUTH_REQUEST_CODE);
  }
  
  public void redirectTermsOfService(View view) {
    AndroidUtil._openExternalURL(AuthLayout.this, getResources().getString(R.string.terms_of_service_url));
  }
  
  public void redirectPrivacyPolicy(View view) {
    AndroidUtil._openExternalURL(AuthLayout.this, getResources().getString(R.string.privacy_policy_url));
  }
  
  public void handlePhoneAccount(){
    AndroidUtil._startActivity(AuthLayout.this, TestSuccess.class);
  }
}