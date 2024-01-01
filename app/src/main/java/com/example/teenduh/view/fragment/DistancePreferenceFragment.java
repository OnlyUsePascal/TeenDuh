package com.example.teenduh.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.teenduh.R;
import com.example.teenduh.util.Util;
import com.example.teenduh.view.activity.SignUpPage;

import org.w3c.dom.Text;


public class DistancePreferenceFragment extends Fragment {
    View view;
    SignUpPage signUpPage;
    TextView distancePreferenceTextView;
    SeekBar distancePreferenceSeekBar;
    String distancePreference;
    TextView nextButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_distance_preference, container, false);
        distancePreferenceTextView = view.findViewById(R.id.distance);
        distancePreferenceSeekBar = view.findViewById(R.id.seekBar);
        nextButton = view.findViewById(R.id.nextButton);
        distancePreferenceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int distance = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                distance = progress;
                distancePreferenceTextView.setText(distance + " km");
                distancePreference = String.valueOf(distance);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                distancePreferenceTextView.setText(distance + " km");
                distancePreference = String.valueOf(distance);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                distancePreferenceTextView.setText(distance + " km");
                distancePreference = String.valueOf(distance);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpPage = (SignUpPage) getActivity();
                Util.curUser.setDistancePreference(distancePreference);
                signUpPage.currentProgress += 10;
                signUpPage.progressBar.setProgress(signUpPage.currentProgress);
                signUpPage.replaceFragment(new DemandFragment());
            }
        });
        return view;
    }
}