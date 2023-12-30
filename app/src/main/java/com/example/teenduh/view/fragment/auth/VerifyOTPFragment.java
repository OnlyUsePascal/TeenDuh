package com.example.teenduh.view.fragment.auth;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teenduh.R;
import com.example.teenduh.stores.auth.AuthPhoneCredentialsStore;
import com.example.teenduh.stores.auth.BaseAuthClientStore;
import com.example.teenduh.stores.auth.UserStore;
import com.example.teenduh.util.Util;
import com.example.teenduh.view.activity.MainLayout;
import com.example.teenduh.view.activity.TestSuccess;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.ArrayList;
import java.util.Arrays;

public class VerifyOTPFragment extends Fragment {
    private final String TAG = "VerifyOTPFragment";
    private boolean isInitial = true;
    private AuthPhoneCredentialsStore authPhoneCredentialsStore;
    private ProgressBar progressBar;
    private View verifyOTPView;
    private ArrayList<TextInputEditText> otpEditTexts;
    private TextView timerTextView, errorTextView;
    private LinearLayout timerLayout;
    private FrameLayout actionLayout;
    private Button resendOTPButton;
    private CountDownTimer countDownTimer;
    public VerifyOTPFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        authPhoneCredentialsStore = (AuthPhoneCredentialsStore) bundle.getSerializable("credentials");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        verifyOTPView = inflater.inflate(R.layout.fragment_verify_o_t_p, container, false);

        timerLayout = (LinearLayout) verifyOTPView.findViewById(R.id.timer_layout);
        timerTextView = (TextView) verifyOTPView.findViewById(R.id.timer);
        errorTextView = (TextView) verifyOTPView.findViewById(R.id.error);
        resendOTPButton = (Button) verifyOTPView.findViewById(R.id.resend_otp);
        progressBar = (ProgressBar) verifyOTPView.findViewById(R.id.progress_bar);
        actionLayout = (FrameLayout) verifyOTPView.findViewById(R.id.action_layout);

        if (isInitial) {
            countDownTimer = new CountDownTimer(60000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timerTextView.setText(String.format("%02d", millisUntilFinished / 1000));
                }

                @Override
                public void onFinish() {
                    timerLayout.setVisibility(View.INVISIBLE);
                    resendOTPButton.setVisibility(View.VISIBLE);
                }
            }.start();
            isInitial = false;
        }

        resendOTPButton.setOnClickListener(v -> {
            resendVerificationCode();
        });

        otpEditTexts = new ArrayList<>(Arrays.asList(
            (TextInputEditText) verifyOTPView.findViewById(R.id.otp_1),
            (TextInputEditText) verifyOTPView.findViewById(R.id.otp_2),
            (TextInputEditText) verifyOTPView.findViewById(R.id.otp_3),
            (TextInputEditText) verifyOTPView.findViewById(R.id.otp_4),
            (TextInputEditText) verifyOTPView.findViewById(R.id.otp_5),
            (TextInputEditText) verifyOTPView.findViewById(R.id.otp_6)
        ));

        for (int i = 0; i < otpEditTexts.size(); i++) {
            otpEditTexts.get(i).addTextChangedListener(new VerifyOTPFormController(i));
            otpEditTexts.get(i).setOnKeyListener(new VerifyOTPPinOnKeyListener(i));
        }

        return verifyOTPView;
    }

    private String generateLocalOTP() {
        StringBuilder localOTP = new StringBuilder();
        for (TextInputEditText otpEditText : otpEditTexts) {
            localOTP.append(otpEditText.getText().toString());
        }
        return localOTP.toString();
    }

    private void verifyOTP() {
        String otp = generateLocalOTP();
        PhoneAuthCredential credential =
            PhoneAuthProvider.getCredential(authPhoneCredentialsStore.getVerificationID(), otp);

        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        actionLayout.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        BaseAuthClientStore.getFirebaseAuth().signInWithCredential(credential)
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Log.d(TAG, "signInWithCredential:success");

                    UserStore.setUser(task.getResult().getUser());

                    Util._startActivity(getContext(), TestSuccess.class);

                    Intent intent = new Intent();
                    getActivity().setResult(RESULT_OK, intent);
                    getActivity().finish();
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.getException());

                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        errorTextView.setText(getResources().getString(R.string.verify_otp_invalid));
                    }

                    progressBar.setVisibility(View.INVISIBLE);
                    actionLayout.setVisibility(View.VISIBLE);
                }
            });
    }

    private void resendVerificationCode() {
        progressBar.setVisibility(View.INVISIBLE);
        actionLayout.setVisibility(View.VISIBLE);

        PhoneAuthOptions options = PhoneAuthOptions.newBuilder()
            .setPhoneNumber(authPhoneCredentialsStore.getPhoneNumber())
            .setTimeout(60L, java.util.concurrent.TimeUnit.SECONDS)
            .setActivity(getActivity())
            .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onCodeSent(@NonNull String verificationId,
                                       @NonNull PhoneAuthProvider.ForceResendingToken token) {
                    Log.d(TAG, "onCodeSent:" + verificationId);

                    progressBar.setVisibility(View.INVISIBLE);
                    actionLayout.setVisibility(View.VISIBLE);
                    countDownTimer.start();

                    Toast.makeText(getContext(), "OTP Resent", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {}

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {}
            })
            .setForceResendingToken(authPhoneCredentialsStore.getResendingToken())
            .build();

        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    // reference: https://stackoverflow.com/questions/38872546/edit-text-for-otp-with-each-letter-in-separate-positions
    class VerifyOTPFormController implements TextWatcher {
        private int index;
        private boolean isFirst, isLast;
        private String value = "";

        VerifyOTPFormController(int index) {
            this.index = index;

            isFirst = index == 0;
            isLast = index == otpEditTexts.size() - 1;
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            value = s.subSequence(start, start + count).toString().trim();
        }

        @Override
        public void afterTextChanged(Editable s) {
            String text = value;

            if (text.length() > 1)
                text = String.valueOf(text.charAt(0));

            otpEditTexts.get(index).removeTextChangedListener(this);
            otpEditTexts.get(index).setText(text);
            otpEditTexts.get(index).setSelection(text.length());
            otpEditTexts.get(index).addTextChangedListener(this);

            if (text.length() == 1)
                moveToNext();
            else if (text.length() == 0)
                moveToPrevious();
        }

        private void moveToNext() {
            if (!isLast) {
                otpEditTexts.get(index + 1).requestFocus();
            }

            if (isAllEditTextsFilled()) {
                otpEditTexts.get(index).clearFocus();
                Util._hideKeyboardFrom(getContext(), verifyOTPView);
                verifyOTP();
            }
        }

        private void moveToPrevious() {
            if (!isFirst) {
                otpEditTexts.get(index - 1).requestFocus();
            }
        }

        private boolean isAllEditTextsFilled() {
            return !otpEditTexts
                    .stream()
                    .anyMatch(editText -> editText.getText().toString().trim().length() == 0);
        }
    }

    class VerifyOTPPinOnKeyListener implements View.OnKeyListener {
        private int index;

        VerifyOTPPinOnKeyListener(int currentIndex) {
            this.index = currentIndex;
        }

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                if (otpEditTexts.get(index).getText().toString().isEmpty() && index != 0)
                    otpEditTexts.get(index - 1).requestFocus();
            }
            return false;
        }
    }
}