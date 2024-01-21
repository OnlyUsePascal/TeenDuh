package com.example.teenduh.view.fragment.signup;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teenduh.R;
import com.example.teenduh._util.AndroidUtil;
import com.example.teenduh._util.FirebaseUtil;
import com.example.teenduh.view.activity.MainLayout;
import com.example.teenduh.view.activity.SignUpPage;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class ImageFragment extends Fragment {
  ImageView[] imageList = new ImageView[6];
  TextView[] addPhotoList = new TextView[6];
  TextView[] deletePhotoList = new TextView[6];
  Uri[] imageUriList = new Uri[6];
  TextView progressPhotoText, next, tipTextView;
  ProgressBar progressBarPhoto, uploadProgressBar;
  int numberOfFetchesToDo = 0;
  int numberOfFetchesDone = 0;
  SignUpPage signUpPage;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_image, container, false);
    signUpPage = (SignUpPage) getActivity();

    // FirebaseUtil.initStorage();

    initField(view);

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

    initDeleteButton();

    progressBarPhoto.setProgress(0);
    progressPhotoText.setText("0/6");

    next.setOnClickListener(v -> {
      uploadFilesToStorage();
    });

    return view;
  }

  private void initField(View view) {
    progressBarPhoto = view.findViewById(R.id.progressBar);
    progressPhotoText = view.findViewById(R.id.progressBarNumberText);
    next = view.findViewById(R.id.nextButton);
    tipTextView = view.findViewById(R.id.tipTextView);
    uploadProgressBar = view.findViewById(R.id.uploadProgressBar);

    for (int i = 0; i < imageList.length; i++) {
      final int index = i + 1;
      imageList[i] = view.findViewById(getResources()
        .getIdentifier("imageView" + index, "id", getActivity().getPackageName()));
      addPhotoList[i] = view.findViewById(getResources()
        .getIdentifier("textViewButton" + index, "id", getActivity().getPackageName()));
      deletePhotoList[i] = view.findViewById(getResources()
        .getIdentifier("deleteButton" + index, "id", getActivity().getPackageName()));
    }
  }

  public void updateProgressBar() {
    progressPhotoText.setText(numberOfFetchesToDo + "/6");
    progressBarPhoto.setProgress(numberOfFetchesToDo);

    if (numberOfFetchesToDo >= 2) {
      tipTextView.setText("Pro tip: Upload more photos to get more matches!");
      next.setBackground(getResources().getDrawable(R.drawable.btn_layout));
      next.setTextColor(getResources().getColor(R.color.white));
      next.setEnabled(true);
    } else {
      tipTextView.setText("Pro tip: Upload at least 2 photos to get more matches!");
      next.setBackground(getResources().getDrawable(R.drawable.dashline_unselected));
      next.setTextColor(getResources().getColor(R.color.white));
      next.setEnabled(false);
    }

  }

  public void initDeleteButton(){
    for (int i = 0; i < deletePhotoList.length; i++) {
      final int index = i;
      deletePhotoList[i].setOnClickListener(v -> {
        imageList[index].setImageDrawable(null);
        imageList[index].setScaleType(ImageView.ScaleType.CENTER);
        transformDeleteButtonToAddButton(index);
      });
    }
  }

  public void showUploadProgressBar() {
    uploadProgressBar.setVisibility(View.VISIBLE);
    next.setEnabled(false);
    next.setVisibility(View.INVISIBLE);
  }

  public void showNextButton() {
    uploadProgressBar.setVisibility(View.INVISIBLE);
    next.setEnabled(true);
    next.setVisibility(View.VISIBLE);
  }

  public void transformAddButtonToDeleteButton(int index) {
    addPhotoList[index].setVisibility(View.INVISIBLE);
    addPhotoList[index].setEnabled(false);
    deletePhotoList[index].setVisibility(View.VISIBLE);
    deletePhotoList[index].setEnabled(true);

    numberOfFetchesToDo++;
    updateProgressBar();
  }

  public void transformDeleteButtonToAddButton(int index) {
    addPhotoList[index].setVisibility(View.VISIBLE);
    addPhotoList[index].setEnabled(true);
    deletePhotoList[index].setVisibility(View.INVISIBLE);
    deletePhotoList[index].setEnabled(false);

    imageUriList[index] = null;

    if (AndroidUtil.getCurUser().getIsInitial()) {
      numberOfFetchesToDo--;
    } else {
      numberOfFetchesToDo++;
    }
    updateProgressBar();
  }

  public void onUploadingComplete() {
    if (AndroidUtil.getCurUser().getIsInitial()) {
      AndroidUtil.getCurUser().setFirstFetchDone();
    }
    AndroidUtil.getCurUser().setImageUris(imageUriList);
    
    numberOfFetchesToDo = 0;
    numberOfFetchesDone = 0;
    
    AndroidUtil.setupRegister(() -> {
      getActivity().runOnUiThread(() -> {
        signUpPage.currentProgress += 10;
        signUpPage.progressBar.setProgress(signUpPage.currentProgress);
        getActivity().finish();

        HashMap<String, Object> data = new HashMap<>();
        data.put("info", AndroidUtil.getCurUser().getInfo());
        data.put("drinkHabit", AndroidUtil.getCurUser().getDrinkHabit());
        data.put("workoutHabit", AndroidUtil.getCurUser().getWorkoutHabit());
        data.put("smokeHabit", AndroidUtil.getCurUser().getSmokeHabit());
        data.put("petHabit", AndroidUtil.getCurUser().getPetHabit());
        data.put("communicationHabit", AndroidUtil.getCurUser().getCommunicationHabit());
        data.put("educationHabit", AndroidUtil.getCurUser().getEducationHabit());
        data.put("zodiacHabit", AndroidUtil.getCurUser().getZodiacHabit());

        FirebaseUtil.updateUser(AndroidUtil.getCurUser().getId(), data, null);

        AndroidUtil._startActivity(getContext(), MainLayout.class);
        showNextButton();
      });
    });
  }

  public void uploadFilesToStorage() {
    showUploadProgressBar();
    for (int i = 0; i < imageUriList.length; i++) {
      if (imageUriList[i] != AndroidUtil.getCurUser().getImageListItem(i)) {
        if (imageUriList[i] != null) {
          // TODO: modify the storage url
          UploadTask uploadTask = FirebaseUtil.getStorageRef()
            .child("users/test/" + AndroidUtil.getCurUser().getId() + "/" + i)
            .putFile(imageUriList[i]);

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
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == Activity.RESULT_OK) {
      Uri uri = data.getData();
      imageList[requestCode - 1].setImageURI(uri);
      imageList[requestCode - 1].setScaleType(ImageView.ScaleType.CENTER_CROP);
      imageUriList[requestCode - 1] = uri;
      transformAddButtonToDeleteButton(requestCode - 1);
    } else if (resultCode == ImagePicker.RESULT_ERROR) {
      Toast.makeText(getContext(), ImagePicker.Companion.getError(data), Toast.LENGTH_SHORT).show();
    }
  }
}