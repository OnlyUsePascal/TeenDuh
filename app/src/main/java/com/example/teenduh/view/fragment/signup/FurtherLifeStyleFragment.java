package com.example.teenduh.view.fragment.signup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.teenduh.R;
import com.example.teenduh._util.AndroidUtil;
import com.example.teenduh.view.activity.SignUpPage;


public class FurtherLifeStyleFragment extends Fragment {
    View view;
    RadioGroup radioGroupCommunication1, radioGroupCommunication2;
    RadioGroup radioGroupEducation1, radioGroupEducation2;
    RadioGroup radioGroupZodiac1, radioGroupZodiac2, radioGroupZodiac3;
    String communicationHabit= "", educationHabit = "", zodiacHabit = "";
    TextView next;
    SignUpPage signUpPage;
    boolean isProgrammaticChange = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view  =  inflater.inflate(R.layout.fragment_further_life_style, container, false);
        signUpPage = (SignUpPage) getActivity();
        radioGroupCommunication1 = view.findViewById(R.id.radioCommunication1);
        radioGroupCommunication2 = view.findViewById(R.id.radioCommunication2);
        radioGroupZodiac1 = view.findViewById(R.id.radioGroupZodiac1);
        radioGroupZodiac2 = view.findViewById(R.id.radioGroupZodiac2);
        radioGroupZodiac3 = view.findViewById(R.id.radioGroupZodiac3);
        radioGroupEducation1 = view.findViewById(R.id.radioGroupEducation1);
        radioGroupEducation2 = view.findViewById(R.id.radioGroupEducation2);

        next = view.findViewById(R.id.nextButton);
        initCommunicationCard();
        initEducationLevel();
        initZodiacCard();
        next.setEnabled(true);
        next.setOnClickListener(v -> {
//            System.out.println("communication habit: " + communicationHabit);
//            System.out.println("education habit: " + educationHabit);
//            System.out.println("zodiac habit: " + zodiacHabit);

            if (!communicationHabit.equals("")) {
                AndroidUtil.getCurUser().addInfoData(communicationHabit);
            }
            if (!educationHabit.equals("")) {
                AndroidUtil.getCurUser().addInfoData(educationHabit);
            }
            if (!zodiacHabit.equals("")) {
                AndroidUtil.getCurUser().addInfoData(zodiacHabit);
            }

            signUpPage.currentProgress += 10;
            signUpPage.progressBar.setProgress(signUpPage.currentProgress);
            signUpPage.replaceFragment(new ImageFragment());
        });

        return view;
    }
    public void setButtonEnable(){
        next.setBackground(getResources().getDrawable(R.drawable.btn_layout));
        next.setTextColor(getResources().getColor(R.color.white));
        next.setEnabled(true);
    }
    public void initCommunicationCard(){
        RadioGroup.OnCheckedChangeListener groupCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (!isProgrammaticChange && checkedId != -1) {
                    isProgrammaticChange = true;
                    RadioButton rb = group.findViewById(checkedId);
                    if (group == radioGroupCommunication1) {
                        radioGroupCommunication2.clearCheck(); // Clear selection in the other RadioGroup
                        communicationHabit = rb.getText().toString();
                    } else if (group == radioGroupCommunication2) {
                        radioGroupCommunication1.clearCheck(); // Clear selection in the other RadioGroup
                        communicationHabit = rb.getText().toString();
                    }
                    isProgrammaticChange = false;
                    setButtonEnable();
                }
            }
        };

        radioGroupCommunication1.setOnCheckedChangeListener(groupCheckedChangeListener);
        radioGroupCommunication2.setOnCheckedChangeListener(groupCheckedChangeListener);
    }
    public void initEducationLevel(){
        RadioGroup.OnCheckedChangeListener groupCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (!isProgrammaticChange && checkedId != -1) {
                    isProgrammaticChange = true;
                    RadioButton rb = group.findViewById(checkedId);
                    if (group == radioGroupEducation1) {
                        radioGroupEducation2.clearCheck(); // Clear selection in the other RadioGroup
                        educationHabit = rb.getText().toString();
                    } else if (group == radioGroupEducation2) {
                        radioGroupEducation1.clearCheck(); // Clear selection in the other RadioGroup
                        educationHabit = rb.getText().toString();
                    }
                    isProgrammaticChange = false;
                    setButtonEnable();
                }
            }
        };

        radioGroupEducation1.setOnCheckedChangeListener(groupCheckedChangeListener);
        radioGroupEducation2.setOnCheckedChangeListener(groupCheckedChangeListener);
    }
    public void initZodiacCard(){
        RadioGroup.OnCheckedChangeListener groupCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (!isProgrammaticChange && checkedId != -1) {
                    isProgrammaticChange = true;
                    RadioButton rb = group.findViewById(checkedId);
                    if (group == radioGroupZodiac1) {
                        radioGroupZodiac2.clearCheck(); // Clear selection in the other RadioGroup
                        radioGroupZodiac3.clearCheck();
                        zodiacHabit = rb.getText().toString();
                    } else if (group == radioGroupZodiac2) {
                        radioGroupZodiac1.clearCheck(); // Clear selection in the other RadioGroup
                        radioGroupZodiac3.clearCheck();
                        zodiacHabit = rb.getText().toString();
                    } else if (group == radioGroupZodiac3) {
                        radioGroupZodiac1.clearCheck(); // Clear selection in the other RadioGroup
                        radioGroupZodiac2.clearCheck();
                        zodiacHabit = rb.getText().toString();
                    }
                    isProgrammaticChange = false;
                    setButtonEnable();
                }
            }
        };

        radioGroupZodiac1.setOnCheckedChangeListener(groupCheckedChangeListener);
        radioGroupZodiac2.setOnCheckedChangeListener(groupCheckedChangeListener);
        radioGroupZodiac3.setOnCheckedChangeListener(groupCheckedChangeListener);
    }
}