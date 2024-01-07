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
import com.example.teenduh.model.AuthPhoneCredentialsStore;
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
  private static final String TAG = "PhoneFormFragment";
  private ProgressBar progressBar;
  private TextInputLayout phoneNumberTextInputLayout;
  private TextInputEditText phoneNumberEditText;
  private Button proceedWithOTPButton;
  private String phoneNumber;
  private CallbackHandler callbackHandler;
  
  public PhoneFormFragment() {
  }
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
  }
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View phoneFormView = inflater.inflate(R.layout.fragment_phone_form, container, false);
    AndroidUtil.setActivity(getActivity());
    initView(phoneFormView);
    callbackHandler = new CallbackHandler();
  
    proceedWithOTPButton.setOnClickListener(this::proceedOtp);
    return phoneFormView;
  }
  
  private void initView(View phoneFormView) {
    phoneNumberTextInputLayout = (TextInputLayout)
                                     phoneFormView.findViewById(R.id.phone_number_form_phone_number_layout);
    phoneNumberEditText = (TextInputEditText)
                              phoneFormView.findViewById(R.id.phone_number_form_phone_number);
    progressBar = (ProgressBar)
                      phoneFormView.findViewById(R.id.phone_number_form_progress_bar);
    proceedWithOTPButton = (Button)
                               phoneFormView.findViewById(R.id.phone_number_form_continue_button);
  }
  
  
  public void proceedOtp(View view){
    phoneNumber = phoneNumberEditText.getText().toString();
    String[] formattedPhoneNumber = Validators.homeNumberValid(phoneNumber);
  
    if (formattedPhoneNumber == null) {
      phoneNumberTextInputLayout.setError(getResources().getString(R.string.phone_number_invalid));
      return;
    }
  
    progressBar.setVisibility(View.VISIBLE);
    proceedWithOTPButton.setVisibility(View.INVISIBLE);
  
    if (fragments.containsKey(phoneNumber)) {
      Fragment verifyOTPFragment = fragments.get(phoneNumber);
    
      getActivity().getSupportFragmentManager().beginTransaction()
          .replace(R.id.phone_auth_fragment_container, verifyOTPFragment)
          .setReorderingAllowed(true)
          .addToBackStack(phoneNumber)
          .commit();
    } else {
      PhoneAuthOptions options = PhoneAuthOptions.newBuilder()
                                     .setPhoneNumber(formattedPhoneNumber[0] + formattedPhoneNumber[1])
                                     .setTimeout(60L, TimeUnit.SECONDS)
                                     .setActivity(getActivity())
                                     .setCallbacks(callbackHandler)
                                     .build();
    
      PhoneAuthProvider.verifyPhoneNumber(options);
    }
  }
  
  private class CallbackHandler extends OnVerificationStateChangedCallbacks {
    @Override
    public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
      Log.d(TAG, "onVerificationCompleted:" + credential);
      
      AndroidUtil._startActivity(getActivity(), TestSuccess.class);
      FirebaseUtil.setCredential(credential);
    }
    
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
  
      Fragment verifyOTPFragment = new VerifyOTPFragment();
  
      AuthPhoneCredentialsStore authPhoneCredentialsStore =
          new AuthPhoneCredentialsStore(verificationId, token, phoneNumber);
  
      Bundle bundle = new Bundle();
      bundle.putSerializable("credentials", authPhoneCredentialsStore);
  
      verifyOTPFragment.setArguments(bundle);
  
      fragments.put(phoneNumber, verifyOTPFragment);
  
      getActivity().getSupportFragmentManager().beginTransaction()
          .replace(R.id.phone_auth_fragment_container, verifyOTPFragment)
          .setReorderingAllowed(true)
          .addToBackStack(phoneNumber)
          .commit();
    }
  }
  
}
