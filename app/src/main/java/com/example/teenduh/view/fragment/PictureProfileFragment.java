package com.example.teenduh.view.fragment;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teenduh.R;
import com.example.teenduh.view.activity.SignUpPage;
import com.github.dhaval2404.imagepicker.ImagePicker;

public class PictureProfileFragment extends Fragment {
    View view;
    ImageView image1, image2, image3, image4, image5, image6;
    TextView addPhoto1, addPhoto2, addPhoto3, addPhoto4, addPhoto5, addPhoto6;
    TextView deletePhoto1, deletePhoto2, deletePhoto3, deletePhoto4, deletePhoto5, deletePhoto6;
    TextView[] addPhotoList = new TextView[6];
    TextView[] deletePhotoList = new TextView[6];
    TextView progressPhotoText, next, tipTextView;
    ProgressBar progressBarPhoto;
    int numberOfPhotoUploaded = 0;
    SignUpPage signUpPage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_picture_profile, container, false);
        signUpPage = (SignUpPage) getActivity();

        progressBarPhoto = view.findViewById(R.id.progressBar);
        progressPhotoText = view.findViewById(R.id.progressBarNumberText);
        next = view.findViewById(R.id.nextButton);
        tipTextView = view.findViewById(R.id.tipTextView);

        image1 = view.findViewById(R.id.imageView1);
        image2 = view.findViewById(R.id.imageView2);
        image3 = view.findViewById(R.id.imageView3);
        image4 = view.findViewById(R.id.imageView4);
        image5 = view.findViewById(R.id.imageView5);
        image6 = view.findViewById(R.id.imageView6);

        addPhoto1 = view.findViewById(R.id.textViewButton1);
        addPhoto2 = view.findViewById(R.id.textViewButton2);
        addPhoto3 = view.findViewById(R.id.textViewButton3);
        addPhoto4 = view.findViewById(R.id.textViewButton4);
        addPhoto5 = view.findViewById(R.id.textViewButton5);
        addPhoto6 = view.findViewById(R.id.textViewButton6);

        deletePhoto1 = view.findViewById(R.id.deleteButton1);
        deletePhoto2 = view.findViewById(R.id.deleteButton2);
        deletePhoto3 = view.findViewById(R.id.deleteButton3);
        deletePhoto4 = view.findViewById(R.id.deleteButton4);
        deletePhoto5 = view.findViewById(R.id.deleteButton5);
        deletePhoto6 = view.findViewById(R.id.deleteButton6);



        addPhotoList[0] = addPhoto1;
        addPhotoList[1] = addPhoto2;
        addPhotoList[2] = addPhoto3;
        addPhotoList[3] = addPhoto4;
        addPhotoList[4] = addPhoto5;
        addPhotoList[5] = addPhoto6;

        deletePhotoList[0] = deletePhoto1;
        deletePhotoList[1] = deletePhoto2;
        deletePhotoList[2] = deletePhoto3;
        deletePhotoList[3] = deletePhoto4;
        deletePhotoList[4] = deletePhoto5;
        deletePhotoList[5] = deletePhoto6;


        for (int i = 0; i < addPhotoList.length; i++) {
            final int index = i + 1;
            addPhotoList[i].setOnClickListener(v -> {
                ImagePicker.Companion.with(this)
                        .crop()
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .start(index*10); // Pass the index as the result
            });
        }
        initDeleteButton();
        progressBarPhoto.setProgress(0);
        progressPhotoText.setText("0/6");
        next.setOnClickListener(v -> {
            signUpPage.currentProgress += 10;
            signUpPage.progressBar.setProgress(signUpPage.currentProgress);
            Toast.makeText(getContext(), "Het roi nhin cai loz!", Toast.LENGTH_SHORT).show();
        });
        // Inflate the layout for this fragment
        return view;
    }
    public void updateProgressBar(){
        progressPhotoText.setText(numberOfPhotoUploaded + "/6");
        progressBarPhoto.setProgress(numberOfPhotoUploaded);
        if(numberOfPhotoUploaded >= 2){
            tipTextView.setText("Pro tip: Upload more photos to get more matches!");
            next.setBackground(getResources().getDrawable(R.drawable.btn_layout));
            next.setTextColor(getResources().getColor(R.color.white));
            next.setEnabled(true);
        }else {
            tipTextView.setText("Pro tip: Upload at least 2 photos to\n get more matches!");
            next.setBackground(getResources().getDrawable(R.drawable.btn_unselected));
            next.setTextColor(getResources().getColor(R.color.white));
            next.setEnabled(false);
        }

    }
    public void initDeleteButton(){
        for(int i = 0; i < deletePhotoList.length; i++){
            final int index = i + 1;
            deletePhotoList[i].setOnClickListener(v -> {
                switch (index){
                    case 1:
                        image1.setImageDrawable(null);
                        image1.setScaleType(ImageView.ScaleType.CENTER);
                        transformDeleteButtonToAddButton(0);
                        break;
                    case 2:
                        image2.setImageDrawable(null);
                        image2.setScaleType(ImageView.ScaleType.CENTER);
                        transformDeleteButtonToAddButton(1);
                        break;
                    case 3:
                        image3.setImageDrawable(null);
                        image3.setScaleType(ImageView.ScaleType.CENTER);
                        transformDeleteButtonToAddButton(2);
                        break;
                    case 4:
                        image4.setImageDrawable(null);
                        image4.setScaleType(ImageView.ScaleType.CENTER);
                        transformDeleteButtonToAddButton(3);
                        break;
                    case 5:
                        image5.setImageDrawable(null);
                        image5.setScaleType(ImageView.ScaleType.CENTER);
                        transformDeleteButtonToAddButton(4);
                        break;
                    case 6:
                        image6.setImageDrawable(null);
                        image6.setScaleType(ImageView.ScaleType.CENTER);
                        transformDeleteButtonToAddButton(5);
                        break;
                }
            });
        }
    }
    public void transformAddButtonToDeleteButton(int index){
        addPhotoList[index].setVisibility(View.INVISIBLE);
        addPhotoList[index].setEnabled(false);
        deletePhotoList[index].setVisibility(View.VISIBLE);
        deletePhotoList[index].setEnabled(true);
        numberOfPhotoUploaded++;
        updateProgressBar();
    }
    public void transformDeleteButtonToAddButton(int index){
        addPhotoList[index].setVisibility(View.VISIBLE);
        addPhotoList[index].setEnabled(true);
        deletePhotoList[index].setVisibility(View.INVISIBLE);
        deletePhotoList[index].setEnabled(false);
        numberOfPhotoUploaded--;
        updateProgressBar();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 10){
            Uri uri = data.getData();
            image1.setImageURI(uri);
            image1.setScaleType(ImageView.ScaleType.CENTER_CROP);
            transformAddButtonToDeleteButton(0);
        } else if(requestCode == 20){
            Uri uri = data.getData();
            image2.setImageURI(uri);
            image2.setScaleType(ImageView.ScaleType.CENTER_CROP);
            transformAddButtonToDeleteButton(1);

        } else if(requestCode == 30){
            Uri uri = data.getData();
            image3.setImageURI(uri);
            image3.setScaleType(ImageView.ScaleType.CENTER_CROP);
            transformAddButtonToDeleteButton(2);
        } else if(requestCode == 40){
            Uri uri = data.getData();
            image4.setImageURI(uri);
            image4.setScaleType(ImageView.ScaleType.CENTER_CROP);
            transformAddButtonToDeleteButton(3);
        } else if(requestCode == 50){
            Uri uri = data.getData();
            image5.setImageURI(uri);
            image5.setScaleType(ImageView.ScaleType.CENTER_CROP);
            transformAddButtonToDeleteButton(4);
        } else if(requestCode == 60){
            Uri uri = data.getData();
            image6.setImageURI(uri);
            image6.setScaleType(ImageView.ScaleType.CENTER_CROP);
            transformAddButtonToDeleteButton(5);
        }

    }
}