package com.example.teenduh.view.activity.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.teenduh.R;
import com.example.teenduh.util.AndroidUtil;
import com.example.teenduh.util.FirebaseUtil;
import com.example.teenduh.view.activity.TestSuccess;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class AuthLayout extends AppCompatActivity {
  private final String TAG = "AuthLayout";
  private final int PHONE_AUTH_REQUEST_CODE = 1;
  private final int GOOGLE_AUTH_REQUEST_CODE = 2;
  private GoogleSignInOptions googleSignInOptions;
  private GoogleSignInClient googleSignInClient;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_auth_layout);

    AndroidUtil.setContext(AuthLayout.this);

    googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
      .requestIdToken(getString(R.string.default_web_client_id))
      .requestEmail()
      .build();

    googleSignInClient = GoogleSignIn.getClient(AuthLayout.this, googleSignInOptions);

    FirebaseUser firebaseUser = FirebaseUtil.getUser();
    if (firebaseUser != null) {
      AndroidUtil._startActivity(AuthLayout.this, TestSuccess.class);
      finish();
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    switch (requestCode) {
      case PHONE_AUTH_REQUEST_CODE:
        finish();
        break;
      case GOOGLE_AUTH_REQUEST_CODE:
        Task<GoogleSignInAccount> signInAccountTask =
          GoogleSignIn.getSignedInAccountFromIntent(data)
            .addOnCompleteListener(task -> {
              try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                  AuthCredential authCredential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

                  FirebaseUtil.getFirebaseAuth()
                    .signInWithCredential(authCredential)
                    .addOnCompleteListener(authResultTask -> {
                      if (authResultTask.isSuccessful()) {
                        FirebaseUtil.setUser(authResultTask.getResult().getUser());
                        AndroidUtil._startActivity(AuthLayout.this, TestSuccess.class);
                        finish();
                      } else {
                        Log.d("TAG", "onComplete: " + authResultTask.getException().getMessage());
                      }
                    });
                }

                Toast.makeText(this, "google sign in success", Toast.LENGTH_SHORT).show();
              } catch (ApiException e) {
                Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
                Toast.makeText(this, "google sign in failed", Toast.LENGTH_SHORT).show();
              }
            });
        break;
    }

  }

  public void googleLogin(View view) {
    Toast.makeText(this, "google login", Toast.LENGTH_SHORT).show();
    AndroidUtil._startActivityForResult(AuthLayout.this,
      googleSignInClient.getSignInIntent(), GOOGLE_AUTH_REQUEST_CODE);
  }

  public void phoneLogin(View view) {
    AndroidUtil._startActivityForResult(AuthLayout.this, PhoneAuthLayout.class, null, PHONE_AUTH_REQUEST_CODE);
  }

  public void redirectTermsOfService(View view) {
    AndroidUtil._openExternalURL(AuthLayout.this, getResources().getString(R.string.terms_of_service_url));
  }

  public void redirectPrivacyPolicy(View view) {
    AndroidUtil._openExternalURL(AuthLayout.this, getResources().getString(R.string.privacy_policy_url));
  }
}