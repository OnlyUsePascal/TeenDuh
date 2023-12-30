package com.example.teenduh.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.teenduh.R;
import com.example.teenduh.util.Util;
import com.example.teenduh.view.activity.SignUpPage;


public class InterestGender extends Fragment {
    RadioGroup genderGroup;
    View view;
    String gender = "";
    TextView next;
    RadioButton buttonShowInfo;
    Boolean isShowInfo = false;
    SignUpPage signUpPage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_interest_gender, container, false);
        signUpPage = (SignUpPage) getActivity();
        // Inflate the layout for this fragment
        genderGroup = view.findViewById(R.id.radioGroup);
        next = view.findViewById(R.id.nextButton);
        buttonShowInfo = view.findViewById(R.id.radioButtonShowInfo);
        genderGroup.setOnCheckedChangeListener((group,checkedId)->{
            RadioButton rb = view.findViewById(checkedId);
            if(rb!=null){
                System.out.println(rb.getText().toString());
                gender = rb.getText().toString();
                transformButton();
            }});
        next.setOnClickListener(v -> {
            if(gender!= null){
                Util.curUser.setInterestedIn(gender);
                System.out.println("here");
                signUpPage.replaceFragment(new DistancePreferenceFragment());
            }

        });

        return view;

    }
    public void transformButton(){
        next.setBackground(getResources().getDrawable(R.drawable.btn_layout));
        next.setTextColor(getResources().getColor(R.color.white));
        next.setEnabled(true);
    }
}