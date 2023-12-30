package com.example.teenduh.view.fragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.teenduh.R;
import com.example.teenduh.util.Util;
import com.example.teenduh.view.activity.SignUpPage;
import com.google.android.material.textfield.TextInputEditText;

import pl.droidsonroids.gif.GifImageView;

public class NameFragment extends Fragment {
    SignUpPage signUpPage;
    View view;
    TextView next;
    TextInputEditText firstName;
    RelativeLayout nextButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        signUpPage = (SignUpPage) getActivity();
        view = inflater.inflate(R.layout.fragment_name, container, false);
        System.out.println(signUpPage);
        nextButton = view.findViewById(R.id.nextButton);
        firstName = view.findViewById(R.id.firstName);
        nextButton.setOnClickListener(v -> {
            if(firstName.getText().toString().isEmpty()){
                firstName.setError("Please enter your first name");
                return;
            }
            showWelcomeDialog(firstName.getText().toString());
        });
        return view;
    }
    public void showWelcomeDialog(String firstName) {
        Dialog dialog = new Dialog(signUpPage); // 'this' refers to your Activity or Context

        // Set the custom layout to the dialog
        dialog.setContentView(R.layout.layout_dialog_name);

        // Find views inside the dialog layout
        GifImageView gifIcon = dialog.findViewById(R.id.gifIcon);
        TextView welcomeMessage = dialog.findViewById(R.id.welcomeMessage);
        TextView okButton = dialog.findViewById(R.id.okButton);

        // Set the welcome message with the entered first name
        welcomeMessage.setText("Welcome, " + firstName + "!");

        // Load and display the GIF using android-gif-drawable library
//        gifIcon.setImageResource(R.drawable); // Replace with your GIF resource

        // Handle button click
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss the dialog when the OK button is clicked
                dialog.dismiss();
                signUpPage.currentProgress += 30;
                signUpPage.progressBar.setProgress(signUpPage.currentProgress);
                signUpPage.progressBar.setMax(100);
                Util.curUser.setName(firstName);
                signUpPage.replaceFragment(new BDayFragment());
            }
        });

        // Show the dialog
        dialog.show();
    }
}