package com.example.teenduh.view.activity.profile;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.teenduh.R;
import com.example.teenduh._util.AndroidUtil;
import com.example.teenduh._util.FirebaseUtil;
import com.example.teenduh.view.activity.MainLayout;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EditProfile extends AppCompatActivity {
  MainLayout mainLayout;
  ProgressBar uploadProgressBar;
  GridLayout imageGrid;
  ImageView[] imageList = new ImageView[6];
  TextView[] addPhotoList = new TextView[6];
  TextView[] deletePhotoList = new TextView[6];
  Uri[] imageUris = new Uri[6];
  Button saveButton, discardButton;
  int numberOfFetchesToDo = 0, numberOfFetchesDone = 0;
  boolean isProgrammaticChange = false;
  String educationEditProfile = "";
  String drinkHabit= "", workoutHabit = "", smokeHabit = "", petHabit = "";
  TextView selectDrink , selectWorkout, selectSmoke, selectPet;
  String communicationHabit= "", educationHabit = "", zodiacHabit = "";
  TextView selectCommunication, selectEducation, selectZodiac;
  TextInputEditText bioEditText;

  String bio = "";

  List<String> info = new ArrayList<>();


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.fragment_edit_profile);
  
    initField();
    bioEditText = findViewById(R.id.bioEditText);

    bioEditText.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {

      }

      @Override
      public void afterTextChanged(Editable s) {
        bio = s.toString();
        saveButton.setEnabled(true);
      }
    });
    selectDrink = findViewById(R.id.selectDrinking);
    selectDrink.setOnClickListener(v -> {
      selectLifeStyleOption();
    });
    selectWorkout = findViewById(R.id.selectWorkout);
    selectWorkout.setOnClickListener(v -> {
      selectLifeStyleOption();
    });
    selectSmoke = findViewById(R.id.selectSmoking);
    selectSmoke.setOnClickListener(v -> {
      selectLifeStyleOption();
    });
    selectPet = findViewById(R.id.selectPets);
    selectPet.setOnClickListener(v -> {
      selectLifeStyleOption();
    });
    selectCommunication = findViewById(R.id.selectCommunicationStyle);
    selectCommunication.setOnClickListener(v -> {
      selectMoreAboutMe();
    });
    selectEducation = findViewById(R.id.selectEducation);
    selectEducation.setOnClickListener(v -> {
      selectMoreAboutMe();
    });
    selectZodiac = findViewById(R.id.selectZodiac);
    selectZodiac.setOnClickListener(v -> {
      selectMoreAboutMe();
    });

    for (int i = 0; i < addPhotoList.length; i++) {
      final int index = i + 1;
      addPhotoList[i].setOnClickListener(v -> {
        ImagePicker.Companion.with(this)
          .crop()
          .compress(1024)
          .maxResultSize(1080, 1080)
          .start(index);
      });
    }

    try {
      downloadFilesFromStorage();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  
  }
  public void setButtonEnable(TextView next){
    next.setTextColor(getResources().getColor(R.color.red));
    next.setEnabled(true);
  }
  private void initField() {
    uploadProgressBar = findViewById(R.id.uploadProgressBar);
    saveButton = findViewById(R.id.saveButton);
    discardButton = findViewById(R.id.discardButton);
    imageGrid = findViewById(R.id.imageGrid);

    saveButton.setOnClickListener(v -> {
      uploadFilesToStorage();
      finish();
      //store data to firebase;
    });

    discardButton.setOnClickListener(v -> {
      finish();
    });

    for (int i = 0; i < imageList.length; i++) {
      final int index = i + 1;
      imageList[i] = findViewById(getResources()
        .getIdentifier("imageView" + index, "id", EditProfile.this.getPackageName()));
      
      addPhotoList[i] = findViewById(getResources()
        .getIdentifier("textViewButton" + index, "id", EditProfile.this.getPackageName()));
      
      deletePhotoList[i] = findViewById(getResources()
        .getIdentifier("deleteButton" + index, "id", EditProfile.this.getPackageName()));
      deletePhotoList[i].setOnClickListener(v -> {
        imageList[index - 1].setImageDrawable(null);
        imageList[index - 1].setScaleType(ImageView.ScaleType.CENTER);
        imageUris[index - 1] = null;
        transformDeleteButtonToAddButton(index - 1);
      });
    }
  }

  public void transformAddButtonToDeleteButton(int index, boolean isFromFetch) {
    addPhotoList[index].setVisibility(View.INVISIBLE);
    addPhotoList[index].setEnabled(false);
    deletePhotoList[index].setVisibility(View.VISIBLE);
    deletePhotoList[index].setEnabled(true);

    if (!isFromFetch) {
      saveButton.setEnabled(++numberOfFetchesToDo > 0);
    }
  }

  public void transformDeleteButtonToAddButton(int index) {
    addPhotoList[index].setVisibility(View.VISIBLE);
    addPhotoList[index].setEnabled(true);
    deletePhotoList[index].setVisibility(View.INVISIBLE);
    deletePhotoList[index].setEnabled(false);

    imageUris[index] = null;

    numberOfFetchesToDo++;

    saveButton.setEnabled(numberOfFetchesToDo > 0);
  }

  public void onUploadingComplete() {
    AndroidUtil.getCurUser().setImageUris(imageUris.clone());

    numberOfFetchesToDo = 0;
    numberOfFetchesDone = 0;

    saveButton.setEnabled(false);

    showImageGrid();
  }


  public void showUploadProgressBar() {
    uploadProgressBar.setVisibility(View.VISIBLE);
    imageGrid.setEnabled(false);
    imageGrid.setVisibility(View.INVISIBLE);
  }

  public void showImageGrid() {
    uploadProgressBar.setVisibility(View.INVISIBLE);
    imageGrid.setEnabled(true);
    imageGrid.setVisibility(View.VISIBLE);
  }

  public void uploadFilesToStorage() {
    showUploadProgressBar();
    for (int i = 0; i < imageUris.length; i++) {
      if (imageUris[i] != AndroidUtil.getCurUser().getImageListItem(i)) {
        if (imageUris[i] != null) {
          // TODO: modify the storage url
          UploadTask uploadTask = FirebaseUtil.getStorageRef()
            .child("users/test/" + AndroidUtil.getCurUser().getId() + "/" + i)
            .putFile(imageUris[i]);

          uploadTask.addOnFailureListener(exception -> {
            Log.e("TAG", "upload file onFailure: " + exception.getMessage());
          }).addOnSuccessListener(taskSnapshot -> {
            if (++numberOfFetchesDone == numberOfFetchesToDo) {
              onUploadingComplete();
            }
          });
        } else {
          // TODO: modify the storage url
          StorageReference fileToDeleteRef = FirebaseUtil.getStorageRef()
            .child("users/test/" + AndroidUtil.getCurUser().getId() + "/" + i);

          fileToDeleteRef.delete().addOnSuccessListener(aVoid -> {
            if (++numberOfFetchesDone == numberOfFetchesToDo) {
              onUploadingComplete();
            }
          }).addOnFailureListener(exception -> {
            Log.e("TAG", "delete file onFailure: " + exception.getMessage());
          });
        }
      }
    }

    String zodiacText = selectZodiac.getText().toString();
    String educationText = selectEducation.getText().toString();
    String communicationText = selectCommunication.getText().toString();
    String drinkText = selectDrink.getText().toString();
    String workoutText = selectWorkout.getText().toString();
    String smokeText = selectSmoke.getText().toString();
    String petText = selectPet.getText().toString();

    if (!zodiacText.equals("Select")) {
      info.add(zodiacText);
    }

    if (!educationText.equals("Select")) {
      info.add(educationText);
    }

    if (!communicationText.equals("Select")) {
      info.add(communicationText);
    }

    if (!drinkText.equals("Select")) {
      info.add(drinkText);
    }

    if (!workoutText.equals("Select")) {
      info.add(workoutText);
    }

    if (!smokeText.equals("Select")) {
      info.add(smokeText);
    }

    if (!petText.equals("Select")) {
      info.add(petText);
    }

    AndroidUtil.getCurUser().setInfo(info);
    HashMap<String, Object> data = new HashMap<>();
    data.put("info", info);
    FirebaseUtil.updateUser(AndroidUtil.getCurUser().getId(), data, null);

  }

  public void downloadFilesFromStorage() throws IOException {
    showUploadProgressBar();
    for (int i = 0; i < imageUris.length; i++) {
      final int index = i;
      File localFile = File.createTempFile("images", "jpg");

      // TODO: modify the storage url
      StorageReference fileToDownloadRef = FirebaseUtil.getStorageRef()
        .child("users/test/" + AndroidUtil.getCurUser().getId() + "/" + i);

      fileToDownloadRef.getFile(localFile).addOnSuccessListener(uri -> {
        Uri tempUri = Uri.fromFile(localFile);
        imageUris[index] = tempUri;
        imageList[index].setImageURI(tempUri);
        imageList[index].setScaleType(ImageView.ScaleType.CENTER_CROP);
        transformAddButtonToDeleteButton(index, true);

        if (index == 5) {
          AndroidUtil.getCurUser().setImageUris(imageUris.clone());
          onUploadingComplete();
        }
      }).addOnFailureListener(exception -> {
        if (index == 5) {
          AndroidUtil.getCurUser().setImageUris(imageUris.clone());
          onUploadingComplete();
        }
      });
    }
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == Activity.RESULT_OK) {
      Uri uri = data.getData();
      imageList[requestCode - 1].setImageURI(uri);
      imageList[requestCode - 1].setScaleType(ImageView.ScaleType.CENTER_CROP);
      imageUris[requestCode - 1] = uri;
      transformAddButtonToDeleteButton(requestCode - 1, false);
    } else if (resultCode == ImagePicker.RESULT_ERROR) {
      Toast.makeText(EditProfile.this, ImagePicker.Companion.getError(data), Toast.LENGTH_SHORT).show();
    }
  }

  public void selectMoreAboutMe(){
    final Dialog dialog = new Dialog(this);
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setContentView(R.layout.more_about_me_layout);

    RadioGroup radioGroupCommunication1 = dialog.findViewById(R.id.radioCommunication1);
    RadioGroup radioGroupCommunication2 = dialog.findViewById(R.id.radioCommunication2);
    RadioGroup radioGroupZodiac1 = dialog.findViewById(R.id.radioGroupZodiac1);
    RadioGroup radioGroupZodiac2 = dialog.findViewById(R.id.radioGroupZodiac2);
    RadioGroup radioGroupZodiac3 = dialog.findViewById(R.id.radioGroupZodiac3);
    RadioGroup radioGroupEducation1 = dialog.findViewById(R.id.radioGroupEducation1);
    RadioGroup radioGroupEducation2 = dialog.findViewById(R.id.radioGroupEducation2);
    ImageView closeButton = dialog.findViewById(R.id.closeButton);
    TextView doneButton = dialog.findViewById(R.id.nextButton);

    closeButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        dialog.dismiss();
      }
    });
    doneButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        updateData();
        dialog.dismiss();
      }
    });
    initCommunicationCard(radioGroupCommunication1, radioGroupCommunication2, doneButton);
    initEducationLevel(radioGroupEducation1, radioGroupEducation2, doneButton);
    initZodiacCard(radioGroupZodiac1, radioGroupZodiac2, radioGroupZodiac3, doneButton);

    dialog.show();
    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
    dialog.getWindow().setGravity(Gravity.BOTTOM);
  }

  public void initEducationLevel(RadioGroup radioGroupEducation1, RadioGroup radioGroupEducation2, TextView next){
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
          setButtonEnable(next);
        }
      }
    };

    radioGroupEducation1.setOnCheckedChangeListener(groupCheckedChangeListener);
    radioGroupEducation2.setOnCheckedChangeListener(groupCheckedChangeListener);
  }
  public void initZodiacCard(RadioGroup radioGroupZodiac1, RadioGroup radioGroupZodiac2, RadioGroup radioGroupZodiac3, TextView next){
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
          setButtonEnable(next);
        }
      }
    };

    radioGroupZodiac1.setOnCheckedChangeListener(groupCheckedChangeListener);
    radioGroupZodiac2.setOnCheckedChangeListener(groupCheckedChangeListener);
    radioGroupZodiac3.setOnCheckedChangeListener(groupCheckedChangeListener);
  }
  public void initCommunicationCard(RadioGroup radioGroupCommunication1, RadioGroup radioGroupCommunication2, TextView next){
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
          setButtonEnable(next);
        }
      }
    };

    radioGroupCommunication1.setOnCheckedChangeListener(groupCheckedChangeListener);
    radioGroupCommunication2.setOnCheckedChangeListener(groupCheckedChangeListener);
  }
  public void selectLifeStyleOption(){
    final Dialog dialog = new Dialog(this);
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setContentView(R.layout.lifestyle_layout_dialog);
    // to do
    RadioGroup radioGroupDrink1 = dialog.findViewById(R.id.radioGroupDrink1);
    RadioGroup radioGroupDrink2 = dialog.findViewById(R.id.radioGroupDrink2);
    RadioGroup radioWorkout = dialog.findViewById(R.id.radioGroupWorkout1);
    RadioGroup radioGroupSmoke1 = dialog.findViewById(R.id.radioGroupSmoke1);
    RadioGroup radioGroupSmoke2 = dialog.findViewById(R.id.radioGroupSmoke2);
    RadioGroup radioGroupPet1 = dialog.findViewById(R.id.radioGroupPet1);
    RadioGroup radioGroupPet2 = dialog.findViewById(R.id.radioGroupPet2);
    ImageView closeButton = dialog.findViewById(R.id.closeButton);
    TextView doneButton = dialog.findViewById(R.id.nextButton);


    initDrinkCardView(radioGroupDrink1, radioGroupDrink2, doneButton);
    initWorkOutCard(radioWorkout, doneButton);
    initSmokeCardView(radioGroupSmoke1,radioGroupSmoke2, doneButton);
    initPetCardView(radioGroupPet1,radioGroupPet2, doneButton);
    doneButton.setEnabled(false);

    closeButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        dialog.dismiss();
      }
    });
    doneButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        updateData();
        dialog.dismiss();

      }
    });

    dialog.show();
    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
    dialog.getWindow().setGravity(Gravity.BOTTOM);
  }
  public void initDrinkCardView(RadioGroup radioGroupDrink1, RadioGroup radioGroupDrink2, TextView next){
    RadioGroup.OnCheckedChangeListener groupCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (!isProgrammaticChange && checkedId != -1) {
          isProgrammaticChange = true;
          RadioButton rb = group.findViewById(checkedId);
          if (group == radioGroupDrink1) {
            radioGroupDrink2.clearCheck(); // Clear selection in the other RadioGroup
            drinkHabit = rb.getText().toString();
            setButtonEnable(next);

          } else if (group == radioGroupDrink2) {
            radioGroupDrink1.clearCheck(); // Clear selection in the other RadioGroup
            drinkHabit = rb.getText().toString();
            setButtonEnable(next);
          }
          isProgrammaticChange = false;

        }
      }
    };

    radioGroupDrink1.setOnCheckedChangeListener(groupCheckedChangeListener);
    radioGroupDrink2.setOnCheckedChangeListener(groupCheckedChangeListener);
  }
  public void initWorkOutCard(RadioGroup radioWorkout, TextView next){
    radioWorkout.setOnCheckedChangeListener((group,checkedId)->{
      RadioButton rb = group.findViewById(checkedId);
      if(rb!=null){
        workoutHabit = rb.getText().toString();
        setButtonEnable(next);
      }
    });
  }
  public void initSmokeCardView(RadioGroup radioGroupSmoke1, RadioGroup radioGroupSmoke2, TextView next){
    RadioGroup.OnCheckedChangeListener groupCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (!isProgrammaticChange && checkedId != -1) {
          isProgrammaticChange = true;
          RadioButton rb = group.findViewById(checkedId);
          if (group == radioGroupSmoke1) {
            radioGroupSmoke2.clearCheck(); // Clear selection in the other RadioGroup
            smokeHabit = rb.getText().toString();
            setButtonEnable(next);
          } else if (group == radioGroupSmoke2) {
            radioGroupSmoke1.clearCheck(); // Clear selection in the other RadioGroup
            smokeHabit = rb.getText().toString();
            setButtonEnable(next);
          }
          isProgrammaticChange = false;
        }
      }
    };

    radioGroupSmoke1.setOnCheckedChangeListener(groupCheckedChangeListener);
    radioGroupSmoke2.setOnCheckedChangeListener(groupCheckedChangeListener);
  }
  public void initPetCardView(RadioGroup radioGroupPet1, RadioGroup radioGroupPet2, TextView next){
    RadioGroup.OnCheckedChangeListener groupCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (!isProgrammaticChange && checkedId != -1) {
          isProgrammaticChange = true;
          RadioButton rb = group.findViewById(checkedId);
          if (group == radioGroupPet1) {
            radioGroupPet2.clearCheck(); // Clear selection in the other RadioGroup
            petHabit = rb.getText().toString();
            setButtonEnable(next);
          } else if (group == radioGroupPet2) {
            radioGroupPet1.clearCheck(); // Clear selection in the other RadioGroup
            petHabit = rb.getText().toString();
            setButtonEnable(next);
          }
          isProgrammaticChange = false;
//          setButtonEnable();
        }
      }
    };

    radioGroupPet1.setOnCheckedChangeListener(groupCheckedChangeListener);
    radioGroupPet2.setOnCheckedChangeListener(groupCheckedChangeListener);
  }
  public void updateData(){
    if(drinkHabit.equals("")){
      selectDrink.setText("Select");
    } else{
      selectDrink.setText(drinkHabit);
      saveButton.setEnabled(true);
    }
    if(workoutHabit.equals("")){
      selectWorkout.setText("Select");
    } else{
      selectWorkout.setText(workoutHabit);
      saveButton.setEnabled(true);
    }
    if(smokeHabit.equals("")){
      selectSmoke.setText("Select");
    } else{
      selectSmoke.setText(smokeHabit);
      saveButton.setEnabled(true);
    }
    if(petHabit.equals("")){
      selectPet.setText("Select");
    } else{
      selectPet.setText(petHabit);
      saveButton.setEnabled(true);
    }
    if(communicationHabit.equals("")){
      selectCommunication.setText("Select");
    } else{
      selectCommunication.setText(communicationHabit);
      saveButton.setEnabled(true);
    }
    if(educationHabit.equals("")){
      selectEducation.setText("Select");
    } else{
      selectEducation.setText(educationHabit);
      saveButton.setEnabled(true);
    }
    if(zodiacHabit.equals("")){
      selectZodiac.setText("Select");
    } else {
      selectZodiac.setText(zodiacHabit);
      saveButton.setEnabled(true);
    }
  }


}
