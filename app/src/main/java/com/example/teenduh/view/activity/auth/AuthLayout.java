package com.example.teenduh.view.activity.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.teenduh.R;
import com.example.teenduh.util.AndroidUtil;

public class AuthLayout extends AppCompatActivity {
    private final String TAG = "AuthLayout";
    private final int PHONE_AUTH_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_layout);

        AndroidUtil.setContext(AuthLayout.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        finish();
    }

    public void facebookLogin(View view) {
        Toast.makeText(this, "facebook login", Toast.LENGTH_SHORT).show();
    }

    public void googleLogin(View view) {
        Toast.makeText(this, "google login", Toast.LENGTH_SHORT).show();
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