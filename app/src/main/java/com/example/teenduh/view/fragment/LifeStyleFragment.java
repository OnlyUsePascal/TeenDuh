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
import com.example.teenduh.view.activity.SignUpPage;


public class LifeStyleFragment extends Fragment {
    View view;
    RadioGroup radioGroupDrink1, radioGroupDrink2, radioWorkout;
    RadioGroup radioGroupSmoke1, radioGroupSmoke2;
    RadioGroup radioGroupPet1, radioGroupPet2;
    String drinkHabit= "", workoutHabit = "", smokeHabit = "", petHabit = "";
    TextView next;
    SignUpPage signUpPage;
    boolean isProgrammaticChange = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view  =  inflater.inflate(R.layout.fragment_life_style, container, false);
        signUpPage = (SignUpPage) getActivity();
        radioGroupDrink1 = view.findViewById(R.id.radioGroupDrink1);
        radioGroupDrink2 = view.findViewById(R.id.radioGroupDrink2);
        radioWorkout = view.findViewById(R.id.radioGroupWorkout1);
        radioGroupSmoke1 = view.findViewById(R.id.radioGroupSmoke1);
        radioGroupSmoke2 = view.findViewById(R.id.radioGroupSmoke2);
        radioGroupPet1 = view.findViewById(R.id.radioGroupPet1);
        radioGroupPet2 = view.findViewById(R.id.radioGroupPet2);

        next = view.findViewById(R.id.nextButton);
        initDrinkCardView();
        initWorkOutCard();
        initSmokeCardView();
        initPetCardView();

        next.setOnClickListener(v -> {
//            System.out.println("drink habit: " + drinkHabit);
//            System.out.println("workout habit: " + workoutHabit);
//            System.out.println("smoke habit: " + smokeHabit);
//            System.out.println("pet habit: " + petHabit);
            signUpPage.replaceFragment(new FurtherLifeStyleFragment());

        });

        return view;
    }
    public void setButtonEnable(){
        next.setBackground(getResources().getDrawable(R.drawable.btn_layout));
        next.setTextColor(getResources().getColor(R.color.white));
        next.setEnabled(true);
    }
    public void initDrinkCardView(){
        RadioGroup.OnCheckedChangeListener groupCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (!isProgrammaticChange && checkedId != -1) {
                    isProgrammaticChange = true;
                    RadioButton rb = group.findViewById(checkedId);
                    if (group == radioGroupDrink1) {
                        radioGroupDrink2.clearCheck(); // Clear selection in the other RadioGroup
                        drinkHabit = rb.getText().toString();

                    } else if (group == radioGroupDrink2) {
                        radioGroupDrink1.clearCheck(); // Clear selection in the other RadioGroup
                        drinkHabit = rb.getText().toString();
                    }
                    isProgrammaticChange = false;
                    setButtonEnable();
                }
            }
        };

        radioGroupDrink1.setOnCheckedChangeListener(groupCheckedChangeListener);
        radioGroupDrink2.setOnCheckedChangeListener(groupCheckedChangeListener);
    }
    public void initWorkOutCard(){
        radioWorkout.setOnCheckedChangeListener((group,checkedId)->{
            RadioButton rb = view.findViewById(checkedId);
            if(rb!=null){
                workoutHabit = rb.getText().toString();
                setButtonEnable();
            }
        });
    }
    public void initSmokeCardView(){
        RadioGroup.OnCheckedChangeListener groupCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (!isProgrammaticChange && checkedId != -1) {
                    isProgrammaticChange = true;
                    RadioButton rb = group.findViewById(checkedId);
                    if (group == radioGroupSmoke1) {
                        radioGroupSmoke2.clearCheck(); // Clear selection in the other RadioGroup
                        smokeHabit = rb.getText().toString();
                    } else if (group == radioGroupSmoke2) {
                        radioGroupSmoke1.clearCheck(); // Clear selection in the other RadioGroup
                        smokeHabit = rb.getText().toString();
                    }
                    isProgrammaticChange = false;
                    setButtonEnable();
                }
            }
        };

        radioGroupSmoke1.setOnCheckedChangeListener(groupCheckedChangeListener);
        radioGroupSmoke2.setOnCheckedChangeListener(groupCheckedChangeListener);
    }
    public void initPetCardView(){
        RadioGroup.OnCheckedChangeListener groupCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (!isProgrammaticChange && checkedId != -1) {
                    isProgrammaticChange = true;
                    RadioButton rb = group.findViewById(checkedId);
                    if (group == radioGroupPet1) {
                        radioGroupPet2.clearCheck(); // Clear selection in the other RadioGroup
                        petHabit = rb.getText().toString();
                    } else if (group == radioGroupPet2) {
                        radioGroupPet1.clearCheck(); // Clear selection in the other RadioGroup
                        petHabit = rb.getText().toString();
                    }
                    isProgrammaticChange = false;
                    setButtonEnable();
                }
            }
        };

        radioGroupPet1.setOnCheckedChangeListener(groupCheckedChangeListener);
        radioGroupPet2.setOnCheckedChangeListener(groupCheckedChangeListener);
    }
}