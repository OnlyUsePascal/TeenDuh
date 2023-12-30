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
import com.example.teenduh.stores.auth.AuthPhoneCredentialsStore;
import com.example.teenduh.stores.auth.BaseAuthClientStore;
import com.example.teenduh.util.Util;
import com.example.teenduh.util.Validators;
import com.example.teenduh.view.activity.TestSuccess;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

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

    public PhoneFormFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View phoneFormView = inflater.inflate(R.layout.fragment_phone_form, container, false);

        Util.setContext(getActivity());

        phoneNumberTextInputLayout = (TextInputLayout)
                phoneFormView.findViewById(R.id.phone_number_form_phone_number_layout);
        phoneNumberEditText = (TextInputEditText)
                phoneFormView.findViewById(R.id.phone_number_form_phone_number);
        progressBar = (ProgressBar)
                phoneFormView.findViewById(R.id.phone_number_form_progress_bar);
        proceedWithOTPButton = (Button)
                phoneFormView.findViewById(R.id.phone_number_form_continue_button);

        proceedWithOTPButton.setOnClickListener(v -> {
            phoneNumber = phoneNumberEditText.getText().toString();
            String[] formattedPhoneNumber = Validators.homeNumberValid(phoneNumber);

            if (formattedPhoneNumber == null) {
                phoneNumberTextInputLayout.setError(getResources().getString(R.string.phone_number_invalid));
            } else {
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
                            .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                                    Log.d(TAG, "onVerificationCompleted:" + credential);

                                    Util._startActivity(getActivity(), TestSuccess.class);
                                    BaseAuthClientStore.setCredential(credential);
                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                    Log.w(TAG, "onVerificationFailed", e);

                                    if (e instanceof FirebaseAuthInvalidCredentialsException) {
                                        Log.d(TAG, "invalid credentials: " + e.getMessage());
                                    } else if (e instanceof FirebaseTooManyRequestsException) {
                                        Log.d(TAG, "too many requests: " + e.getMessage());
                                    } else if (e instanceof FirebaseAuthMissingActivityForRecaptchaException) {
                                        Log.d(TAG, "missing activity for recaptcha: " + e.getMessage());
                                    }

                                    progressBar.setVisibility(View.INVISIBLE);
                                    proceedWithOTPButton.setVisibility(View.VISIBLE);

                                    phoneNumberTextInputLayout.setError(getResources().getString(R.string.phone_number_invalid));
                                }

                                @Override
                                public void onCodeSent(@NonNull String verificationId,
                                                       @NonNull PhoneAuthProvider.ForceResendingToken token) {
                                    Log.d(TAG, "onCodeSent:" + verificationId);

                                    Fragment verifyOTPFragment = new VerifyOTPFragment();

                                    AuthPhoneCredentialsStore authPhoneCredentialsStore = new AuthPhoneCredentialsStore(verificationId, token, phoneNumber);

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
                            })
                            .build();

                    PhoneAuthProvider.verifyPhoneNumber(options);
                }
            }
        });

        return phoneFormView;
    }
}