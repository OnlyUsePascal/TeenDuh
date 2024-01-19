package com.example.teenduh.view.activity.profile;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.teenduh.R;
import com.example.teenduh._util.AndroidUtil;
import com.example.teenduh._util.FirebaseUtil;
import com.example.teenduh.view.activity.MainLayout;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

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

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.fragment_edit_profile);
  
    initField();

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

  private void initField() {
    uploadProgressBar = findViewById(R.id.uploadProgressBar);
    saveButton = findViewById(R.id.saveButton);
    discardButton = findViewById(R.id.discardButton);
    imageGrid = findViewById(R.id.imageGrid);

    saveButton.setOnClickListener(v -> {
      uploadFilesToStorage();
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
}
