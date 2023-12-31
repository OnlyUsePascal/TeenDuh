package com.example.teenduh.view.fragment.signup;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;

import com.example.teenduh.R;
import com.example.teenduh.view.activity.SignUpPage;

public class BDayFragment extends Fragment {
    View view;
    NumberPicker dayPicker, monthPicker, yearPicker;
    String dayValues, monthValues, yearValues;
    RelativeLayout nextButton;
    SignUpPage signUpPage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_b_day, container, false);
        signUpPage = (SignUpPage) getActivity();
        // Inflate the layout for this fragment
        dayPicker = view.findViewById(R.id.numberPickerDay);
        monthPicker = view.findViewById(R.id.numberPickerMonth);
        yearPicker = view.findViewById(R.id.numberPickerYear);
        nextButton = view.findViewById(R.id.nextButton);
        initPicker();

        String day = String.valueOf(dayPicker.getValue());
        String month = String.valueOf(monthPicker.getValue());
        String year = String.valueOf(yearPicker.getValue());

        dayPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            dayValues = String.valueOf(newVal);
        });
        monthPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            monthValues = String.valueOf(newVal);
        });
        yearPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            yearValues = String.valueOf(newVal);
        });
        nextButton.setOnClickListener(v -> {
            System.out.println(dayValues + " " + monthValues + " " + yearValues);
            signUpPage.progressBar.setProgress(signUpPage.currentProgress += 30);
            signUpPage.replaceFragment(new GenderFragment());
        });
        return view;
    }
    public void initPicker(){
        dayPicker.setMinValue(1);
        dayPicker.setMaxValue(31);

        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);

        yearPicker.setMinValue(1900);
        yearPicker.setMaxValue(2024);

    }
}