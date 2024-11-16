package com.example.teenduh.view.fragment.auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.teenduh.R;
import com.example.teenduh._util.PhoneCredentialUtil;
import com.example.teenduh._util.AndroidUtil;
import com.example.teenduh._util.FirebaseUtil;
import com.example.teenduh._util.Validators;
import com.example.teenduh.view.activity.TestSuccess;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class PhoneFormFragment extends Fragment {
  HashMap<String, Fragment> fragments = new HashMap<>();
  HashMap<String, PhoneAuthProvider.ForceResendingToken> phoneToResendToken = new HashMap<>();
  private static final String TAG = "PhoneFormFragment";
  private ProgressBar progressBar;
  private TextInputLayout phoneNumberTextInputLayout;
  private TextInputEditText phoneNumberEditText;
  private Button proceedWithOTPButton;
  private String phoneNumber;
  
  public PhoneFormFragment() {
  }
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View phoneFormView = inflater.inflate(R.layout.fragment_phone_form, container, false);
    
    AndroidUtil.setActivity(getActivity());
    initView(phoneFormView);
    
    proceedWithOTPButton.setOnClickListener(this::proceedOtp);
    return phoneFormView;
  }
  
  private void initView(View phoneFormView) {
    phoneNumberTextInputLayout = phoneFormView.findViewById(R.id.phone_number_form_phone_number_layout);
    phoneNumberEditText = phoneFormView.findViewById(R.id.phone_number_form_phone_number);
    progressBar = phoneFormView.findViewById(R.id.phone_number_form_progress_bar);
    proceedWithOTPButton = phoneFormView.findViewById(R.id.phone_number_form_continue_button);
  }
  
  
  public void proceedOtp(View view) {
    phoneNumber = phoneNumberEditText.getText().toString();
//    phoneNumber = "+1 333-393-1222";
//    phoneNumber = "+15 551234567";
    String[] formattedPhoneNumber = Validators.homeNumberValid(phoneNumber);
    
    if (formattedPhoneNumber == null) {
      phoneNumberTextInputLayout.setError(getResources().getString(R.string.phone_number_invalid));
      return;
    }
    
    progressBar.setVisibility(View.VISIBLE);
    proceedWithOTPButton.setVisibility(View.INVISIBLE);
    
    PhoneAuthOptions.Builder optionsBuilder = PhoneAuthOptions.newBuilder()
                                   .setPhoneNumber(formattedPhoneNumber[0] + formattedPhoneNumber[1])
                                   .setTimeout(60L, TimeUnit.SECONDS)
                                   .setActivity(getActivity())
                                   .setCallbacks(new CallbackHandler());
    PhoneAuthProvider.ForceResendingToken resendToken = phoneToResendToken.get(phoneNumber);
    if (resendToken != null) optionsBuilder.setForceResendingToken(resendToken);
    PhoneAuthProvider.verifyPhoneNumber(optionsBuilder.build());
    // }
  }
  
  private class CallbackHandler extends OnVerificationStateChangedCallbacks {
    @Override
    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {}
    
    @Override
    public void onVerificationFailed(@NonNull FirebaseException e) {
      Log.w(TAG, "onVerificationFailed", e);
      progressBar.setVisibility(View.INVISIBLE);
      proceedWithOTPButton.setVisibility(View.VISIBLE);
      phoneNumberTextInputLayout.setError(getResources().getString(R.string.phone_number_invalid));
    }
    
    @Override
    public void onCodeSent(@NonNull String verificationId,
                           @NonNull PhoneAuthProvider.ForceResendingToken token) {
      Log.d(TAG, "onCodeSent:" + verificationId);
      PhoneCredentialUtil phoneCredentialUtil = new PhoneCredentialUtil(verificationId, token, phoneNumber);
      Bundle bundle = new Bundle();
      bundle.putSerializable("credentials", phoneCredentialUtil);
      
      Fragment verifyOTPFragment = new VerifyOTPFragment();
      verifyOTPFragment.setArguments(bundle);
      
      phoneToResendToken.put(phoneNumber, token);
      changeFragment(verifyOTPFragment);
    }
  
  }
  
  public void changeFragment(Fragment fragment) {
    getActivity().getSupportFragmentManager().beginTransaction()
        .replace(R.id.phone_auth_fragment_container, fragment)
        .setReorderingAllowed(true)
        .addToBackStack(phoneNumber)
        .commit();
  }
  
}
