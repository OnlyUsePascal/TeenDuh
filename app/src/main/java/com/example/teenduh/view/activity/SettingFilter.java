package com.example.teenduh.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.teenduh.R;
import com.example.teenduh._util.AndroidUtil;
import com.google.android.material.slider.RangeSlider;

public class SettingFilter extends AppCompatActivity {
  TextView preferedDistanceTv;
  SeekBar preferedDistanceSb;
  SwitchCompat distanceSwitch;
  RadioGroup sexualOrientationRg;
  TextView preferedAgeTv;
  RangeSlider preferedAgeRs;
  SwitchCompat ageSwitch;
  TextView preferNumberPhotoTv;
  SeekBar preferNumberPhotoSb;
  SwitchCompat showBioSwitch;
  TextView preferEducationTv;
  boolean isProgrammaticChange = false;
  String educationSetting = "";
  String zodiacSetting = "";
  String genderSetting = "";
  ImageView backButton;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_setting_filter);
    backButton = findViewById(R.id.back_button);
    backButton.setOnClickListener(v -> {
      finish();
      overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
      if(!genderSetting.equals("")){
        System.out.println("Gender Setting:" + genderSetting);
        AndroidUtil.setGenderFilter(genderSetting);
      }
    });

    //CardView Distance
    initDistanceCardView();
    //CardView Sexual Orientation
    initSexualOrientationCardView();
    //CardView Age
    initAgeCardView();
    //Cardview Premium Discovery
    initPremiumDiscoveryCardView();
    if(AndroidUtil.getGenderFilter().equals("All")){
      sexualOrientationRg.check(R.id.otherRadioButton);
    } else if(AndroidUtil.getGenderFilter().equals("Female")){
      sexualOrientationRg.check(R.id.femaleRadioButton);
    } else{
      sexualOrientationRg.check(R.id.maleRadioButton);
    }

  }

  public void initDistanceCardView() {
    preferedDistanceTv = findViewById(R.id.distance);
    preferedDistanceSb = findViewById(R.id.seekBar);
    distanceSwitch = findViewById(R.id.switchDistance);
    preferedDistanceTv.setText(preferedDistanceSb.getProgress() + " km");
    preferedDistanceSb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
      @Override
      public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        preferedDistanceTv.setText(progress + " km");
      }

      @Override
      public void onStartTrackingTouch(SeekBar seekBar) {
        preferedDistanceTv.setText(seekBar.getProgress() + " km");
      }

      @Override
      public void onStopTrackingTouch(SeekBar seekBar) {
        preferedDistanceTv.setText(seekBar.getProgress() + " km");
      }
    });
    distanceSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
      if (isChecked) {
        System.out.println("Show in distance range");
      } else {
        System.out.println("Not show in distance range");
      }
    });
  }

  public void initSexualOrientationCardView() {
    sexualOrientationRg = findViewById(R.id.radioGroup);
    sexualOrientationRg.setOnCheckedChangeListener((group, checkedId) -> {
      RadioButton rb = findViewById(checkedId);
      if (rb != null) {
        genderSetting = rb.getText().toString();
        if(!genderSetting.equals("")){
          System.out.println("Gender Setting:" + genderSetting);
          AndroidUtil.setGenderFilter(genderSetting);
        }
      }
    });
  }

  public void initAgeCardView() {
    preferedAgeTv = findViewById(R.id.age_range_TextView);
    preferedAgeRs = findViewById(R.id.range_slider);
    ageSwitch = findViewById(R.id.switchAge);
    preferedAgeTv.setText(preferedAgeRs.getValues().get(0).intValue() + " - " + preferedAgeRs.getValues().get(1).intValue());
    preferedAgeRs.addOnChangeListener((slider, value, fromUser) -> {
      preferedAgeTv.setText(preferedAgeRs.getValues().get(0).intValue() + " - " + preferedAgeRs.getValues().get(1).intValue());
      //System.out.println(preferedAgeRs.getValues().get(0).intValue() + " - " + preferedAgeRs.getValues().get(1).intValue());
    });
    ageSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
      if (isChecked) {
        System.out.println("Show in age range");
      } else {
        System.out.println("Not show in age range");
      }
    });
  }

  public void initPremiumDiscoveryCardView() {
    preferNumberPhotoTv = findViewById(R.id.number_of_photos);
    preferNumberPhotoSb = findViewById(R.id.seekBarPhoto);
    showBioSwitch = findViewById(R.id.switchBio);
    preferNumberPhotoSb.setMax(6);
    preferNumberPhotoSb.setMin(1);
    preferNumberPhotoSb.setProgress(3);

    preferNumberPhotoTv.setText(preferNumberPhotoSb.getProgress() + "");
    preferNumberPhotoSb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
      @Override
      public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        preferNumberPhotoTv.setText(progress + "");
      }

      @Override
      public void onStartTrackingTouch(SeekBar seekBar) {
        preferNumberPhotoTv.setText(seekBar.getProgress() + "");
      }

      @Override
      public void onStopTrackingTouch(SeekBar seekBar) {
        preferNumberPhotoTv.setText(seekBar.getProgress() + "");
      }
    });

    showBioSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
      if (isChecked) {
        System.out.println("Show bio");
      } else {
        System.out.println("Not show bio");
      }
    });
    //CardView Education
    initEducationOption();
    //CardView Zodiac
    initZodiacOption();
  }

  public void initEducationOption() {
    preferEducationTv = findViewById(R.id.selectEducation);
    preferEducationTv.setOnClickListener(v -> {
      showDialogEducation();
    });
  }
  public void initZodiacOption() {
    preferEducationTv = findViewById(R.id.selectZodiac);
    preferEducationTv.setOnClickListener(v -> {
      showDialogZodiac();
    });
  }
  public void showDialogEducation() {
    final Dialog dialog = new Dialog(this);
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setContentView(R.layout.education_dialog_layout);

    ImageView cancelButton = dialog.findViewById(R.id.button_return);
    RadioGroup educationRg1 = dialog.findViewById(R.id.radioGroup1);
    RadioGroup educationRg2 = dialog.findViewById(R.id.radioGroup2);
    RadioGroup educationRg3 = dialog.findViewById(R.id.radioGroup3);
    TextView doneButton = dialog.findViewById(R.id.doneButton);
    RadioGroup.OnCheckedChangeListener groupCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (!isProgrammaticChange && checkedId != -1) {
          isProgrammaticChange = true;
          RadioButton rb = group.findViewById(checkedId);
          if (group == educationRg1) {
            educationRg2.clearCheck(); // Clear selection in the other RadioGroup
            educationRg3.clearCheck();
            educationSetting = rb.getText().toString();
          } else if (group == educationRg2) {
            educationRg1.clearCheck(); // Clear selection in the other RadioGroup
            educationRg3.clearCheck();
            educationSetting = rb.getText().toString();

          } else if (group == educationRg3) {
            educationRg1.clearCheck();
            educationRg2.clearCheck();
            educationSetting = rb.getText().toString();
          }
          isProgrammaticChange = false;
        }
      }
    };

    educationRg1.setOnCheckedChangeListener(groupCheckedChangeListener);
    educationRg2.setOnCheckedChangeListener(groupCheckedChangeListener);
    educationRg3.setOnCheckedChangeListener(groupCheckedChangeListener);

    cancelButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        dialog.dismiss();
      }
    });

    doneButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if(educationSetting.equals("")){
          System.out.println("No education setting");
        dialog.dismiss();
      } else {
          System.out.println(educationSetting);
          dialog.dismiss();
        }
      }
    });

    dialog.show();
    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
    dialog.getWindow().setGravity(Gravity.BOTTOM);
  }

  public void showDialogZodiac() {
    final Dialog dialog = new Dialog(this);
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setContentView(R.layout.zodiac_option_layout);

    ImageView cancelButton = dialog.findViewById(R.id.button_return);
    RadioGroup radioGroup1 = dialog.findViewById(R.id.radioGroup1);
    RadioGroup radioGroup2 = dialog.findViewById(R.id.radioGroup2);
    RadioGroup radioGroup3 = dialog.findViewById(R.id.radioGroup3);
    TextView doneButton = dialog.findViewById(R.id.doneButton);
    RadioGroup.OnCheckedChangeListener groupCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (!isProgrammaticChange && checkedId != -1) {
          isProgrammaticChange = true;
          RadioButton rb = group.findViewById(checkedId);
          if (group == radioGroup1) {
            radioGroup2.clearCheck(); // Clear selection in the other RadioGroup
            radioGroup3.clearCheck();
            zodiacSetting = rb.getText().toString();
          } else if (group == radioGroup2) {
            radioGroup1.clearCheck(); // Clear selection in the other RadioGroup
            radioGroup3.clearCheck();
            zodiacSetting = rb.getText().toString();

          } else if (group == radioGroup3) {
            radioGroup1.clearCheck();
            radioGroup2.clearCheck();
            zodiacSetting = rb.getText().toString();
          }
          isProgrammaticChange = false;
        }
      }
    };

    radioGroup1.setOnCheckedChangeListener(groupCheckedChangeListener);
    radioGroup2.setOnCheckedChangeListener(groupCheckedChangeListener);
    radioGroup3.setOnCheckedChangeListener(groupCheckedChangeListener);

    cancelButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        dialog.dismiss();
      }
    });

    doneButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if(educationSetting.equals("")){
          System.out.println("No education setting");
          dialog.dismiss();
        } else {
          System.out.println(educationSetting);
          dialog.dismiss();
        }
      }
    });

    dialog.show();
    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
    dialog.getWindow().setGravity(Gravity.BOTTOM);
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
  }
}