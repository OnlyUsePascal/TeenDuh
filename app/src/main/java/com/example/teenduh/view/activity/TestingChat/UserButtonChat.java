package com.example.teenduh.view.activity.TestingChat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.teenduh.R;
import com.example.teenduh.view.activity.ChatActivity;
import com.example.teenduh.view.activity.LoginOtpActivity;
import com.example.teenduh.view.activity.LoginUsernameActivity;
import com.example.teenduh.view.activity.MainActivity;
import com.example.teenduh.view.activity.model.UserModel;
import com.example.teenduh.view.activity.utils.AndroidUtil;
import com.example.teenduh.view.activity.utils.FirebaseUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class UserButtonChat extends AppCompatActivity {

    Button buttonA;
    Button buttonB;
    Button buttonLogout;
    Button buttonLogin;
    Button buttonInfo;
    Long timeoutSeconds = 60L;
    String verificationCode;
    PhoneAuthProvider.ForceResendingToken  resendingToken;
    String phoneA = "+919999999922";
    String phoneB = "+919999999933";
    UserModel userA;
    UserModel userB;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    int curUser = 0; // 0 = userA, 1 = userB


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_button_chat);
        buttonA = findViewById(R.id.btn_user_a);
        buttonB = findViewById(R.id.btn_user_b);
        buttonLogout = findViewById(R.id.btn_logout);
        buttonLogin = findViewById(R.id.btn_login);
        buttonInfo = findViewById(R.id.btn_info);

        buttonA.setOnClickListener((v)->{
            loginUserA();
        });

        buttonB.setOnClickListener((v)->{
           loginUserB();
        });

//        buttonLogin.setOnClickListener((v)->{
//            Toast.makeText(this, "Login", Toast.LENGTH_SHORT).show();
//            if (FirebaseUtil.isLoggedIn() == false) {
//                Toast.makeText(this, "Please login first!", Toast.LENGTH_SHORT).show();
//            } else {
//                if (curUser == 0) {
//                    loginAsUserA();
//                } else {
//                    loginAsUserB();
//                }
//            }
//        });

        buttonLogin.setOnClickListener((v)->{
            PhoneAuthCredential phoneAuthCredential;
            if (curUser == 0) { //user A
                phoneAuthCredential = PhoneAuthProvider.getCredential(verificationCode, "222222");
            } else { // user B
                phoneAuthCredential = PhoneAuthProvider.getCredential(verificationCode, "333333");
            }
            signIn(phoneAuthCredential);
        });

        buttonLogout.setOnClickListener((v)->{
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
        });

        buttonInfo.setOnClickListener((v)->{
            getInfo();
        });
    }

    void loginAsUserA() {
        Intent intent = new Intent(UserButtonChat.this, ChatActivity.class);
        AndroidUtil.passUserModelAsIntent(intent,userB);
        startActivity(intent);
    }

    void loginAsUserB() {
        Intent intent = new Intent(UserButtonChat.this, ChatActivity.class);
        AndroidUtil.passUserModelAsIntent(intent,userA);
        startActivity(intent);
    }

    void getInfo() {
        System.out.println("User ID: " + FirebaseUtil.currentUserId());
        System.out.println("User A: " + userA);
        System.out.println("User B: " + userB);
    }

    void loginUserA() {
        curUser = 0;
        sendOtp(phoneA, false);
        System.out.println("User ID: " + FirebaseUtil.currentUserId());

        FirebaseUtil.getUserModelFromPhone(phoneA, new FirebaseUtil.OnUserDataReceivedListener() {
            @Override
            public void onReceived(UserModel userModel) {
                userA = userModel;
                System.out.println("User A: " + userA);
            }
        });
        FirebaseUtil.getUserModelFromPhone(phoneB, new FirebaseUtil.OnUserDataReceivedListener() {
            @Override
            public void onReceived(UserModel userModel) {
                userB = userModel;
                System.out.println("User B: " + userB);
            }
        });
    }

    void loginUserB() {
        curUser = 1;
        sendOtp(phoneB, false);
        System.out.println("User ID: " + FirebaseUtil.currentUserId());

        FirebaseUtil.getUserModelFromPhone(phoneA, new FirebaseUtil.OnUserDataReceivedListener() {
            @Override
            public void onReceived(UserModel userModel) {
                userA = userModel;
                System.out.println("User A: " + userA);
            }
        });
        FirebaseUtil.getUserModelFromPhone(phoneB, new FirebaseUtil.OnUserDataReceivedListener() {
            @Override
            public void onReceived(UserModel userModel) {
                userB = userModel;
                System.out.println("User B: " + userB);
            }
        });
    }

    void sendOtp(String phoneNumber,boolean isResend){
        setInProgress(true);
        PhoneAuthOptions.Builder builder =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)
                        .setTimeout(timeoutSeconds, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                signIn(phoneAuthCredential);
                                setInProgress(false);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                AndroidUtil.showToast(getApplicationContext(),"OTP verification failed");
                                setInProgress(false);
                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                verificationCode = s;
                                resendingToken = forceResendingToken;
                                AndroidUtil.showToast(getApplicationContext(),"OTP sent successfully");
                                setInProgress(false);
                            }
                        });
        if(isResend){
            PhoneAuthProvider.verifyPhoneNumber(builder.setForceResendingToken(resendingToken).build());
        }else{
            PhoneAuthProvider.verifyPhoneNumber(builder.build());
        }

    }

    void setInProgress(boolean inProgress){
        if(inProgress){
            buttonLogin.setVisibility(View.GONE);
        }else{
            buttonLogin.setVisibility(View.VISIBLE);
        }
    }

    void signIn(PhoneAuthCredential phoneAuthCredential) {
        mAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(UserButtonChat.this, "User logged in", Toast.LENGTH_SHORT).show();
                    // Now that the user is authenticated, fetch the user ID
                    System.out.println("User ID: " + FirebaseUtil.currentUserId());
                    if (curUser == 0) {
                        loginAsUserA();
                    } else {
                        loginAsUserB();

                    }
                } else {
                    Toast.makeText(UserButtonChat.this, "User not logged in", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

}