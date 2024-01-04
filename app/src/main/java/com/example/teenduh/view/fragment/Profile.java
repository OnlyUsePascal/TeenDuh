package com.example.teenduh.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.teenduh.R;
import com.example.teenduh.util.AndroidUtil;
import com.google.android.material.progressindicator.CircularProgressIndicator;

public class Profile extends Fragment {
  Context context;
  private CircularProgressIndicator progressIndicator;
  
  public Profile(){
    context = AndroidUtil.getContext();
  }
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_profile, container, false);
    progressIndicator = view.findViewById(R.id.progressBar);
    return view;
  }
  
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    
    (new Handler()).postDelayed(() -> {
      setProgressBar((double) 55 / 100);
    }, 1000);
  }
  
  public void setProgressBar(double percent){
    // 0 - 73
    int progress = (int) (73 * percent);
    System.out.println(progress);
    progressIndicator.setProgress(progress, true);
  }
}
