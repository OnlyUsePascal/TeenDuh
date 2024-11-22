package com.example.teenduh.view.fragment.auth;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teenduh.R;
import com.example.teenduh._util.AndroidUtil;
import com.example.teenduh._util.FirebaseUtil;

import java.util.function.Consumer;

public class EmailFormFragment extends Fragment {
    private ProgressBar progressBar;
    private EditText emailInput, passwordInput;
    private TextView emailError, passwordError;
    private Button loginButton, signupButton;
    private LinearLayout buttonLayout;
    private String email, password;

    Consumer<String> loginFailureConsumer = errorCode -> {
        switch (errorCode) {
            case "ERROR_INVALID_EMAIL":
                Toast.makeText(getContext(), "Invalid email format.", Toast.LENGTH_SHORT).show();
                break;
            case "ERROR_WRONG_PASSWORD":
                Toast.makeText(getContext(), "Wrong password.", Toast.LENGTH_SHORT).show();
                break;
            case "ERROR_USER_NOT_FOUND":
                Toast.makeText(getContext(), "No user found with this email.", Toast.LENGTH_SHORT).show();
                break;
            case "ERROR_INVALID_CREDENTIAL":
                Toast.makeText(getContext(), "Wrong email or password.", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(getContext(), "Error code: " + errorCode, Toast.LENGTH_SHORT).show();
        }

        progressBar.setVisibility(View.INVISIBLE);
        buttonLayout.setVisibility(View.VISIBLE);
    };

    Consumer<String> signUpFailureConsumer = errorCode -> {
        switch (errorCode) {
            case "ERROR_INVALID_EMAIL":
                Toast.makeText(getContext(), "Invalid email format.", Toast.LENGTH_SHORT).show();
                break;
            case "ERROR_EMAIL_ALREADY_IN_USE":
                Toast.makeText(getContext(), "Email is already registered.", Toast.LENGTH_SHORT).show();
                break;
            case "ERROR_WEAK_PASSWORD":
                Toast.makeText(getContext(), "Password is too weak.", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(getContext(), "Error code: " + errorCode, Toast.LENGTH_SHORT).show();
        }

        progressBar.setVisibility(View.INVISIBLE);
        buttonLayout.setVisibility(View.VISIBLE);
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View emailFormView = inflater.inflate(R.layout.fragment_email_form, container, false);

        AndroidUtil.setActivity(getActivity());
        initView(emailFormView);

        signupButton.setOnClickListener(this::signUp);
        loginButton.setOnClickListener(this::login);

        return emailFormView;
    }

    public boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";
        return email.matches(emailPattern);
    }

    private boolean formHasErrors() {
        email = emailInput.getText().toString();
        password = passwordInput.getText().toString();

        boolean hasFormatErrors = false;

        if (!isValidEmail(email)) {
            hasFormatErrors = true;
            emailError.setText("Please enter a valid email address.");
        } else {
            emailError.setText("");
        }

        if (password.length() < 6 || password.length() > 30) {
            hasFormatErrors = true;
            passwordError.setText("Password must be from 6 to 30 symbols.");
        } else {
            passwordError.setText("");
        }

        if (hasFormatErrors) {
            passwordInput.setText("");
        }

        return hasFormatErrors;
    }

    public void login(View view) {
        if (formHasErrors()) {
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        buttonLayout.setVisibility(View.INVISIBLE);

        FirebaseUtil.loginEmail(email, password, () -> {
            Log.d("EmailSignUp", "User exists with ID: " + email);

            progressBar.setVisibility(View.INVISIBLE);
            buttonLayout.setVisibility(View.VISIBLE);

            AndroidUtil.setupLogin(getActivity());
        }, loginFailureConsumer);
    }

    public void signUp(View view) {
        if (formHasErrors()) {
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        buttonLayout.setVisibility(View.INVISIBLE);

        FirebaseUtil.registerEmail(email, password, () -> {
            Log.d("EmailSignUp", "User exists with ID: " + email);

            progressBar.setVisibility(View.INVISIBLE);
            buttonLayout.setVisibility(View.VISIBLE);

            AndroidUtil.setupLogin(getActivity());
        }, signUpFailureConsumer);
    }

    private void initView(View phoneFormView) {
        emailInput = phoneFormView.findViewById(R.id.emailInput);
        passwordInput = phoneFormView.findViewById(R.id.passwordInput);
        emailError = phoneFormView.findViewById(R.id.emailError);
        passwordError = phoneFormView.findViewById(R.id.passwordError);
        progressBar = phoneFormView.findViewById(R.id.form_progress_bar);
        loginButton = phoneFormView.findViewById(R.id.login_button);
        signupButton = phoneFormView.findViewById(R.id.sign_up_button);
        buttonLayout = phoneFormView.findViewById(R.id.sign_up_button_layout);
    }
}