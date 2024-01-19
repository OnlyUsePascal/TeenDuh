package com.example.teenduh.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.teenduh.R;
import com.example.teenduh._util.AndroidUtil;
import com.example.teenduh.view.activity.admin.PieChartActivity;

public class Statistic extends Fragment {

    private View view;
    private Button buttonPieChart;

    public Statistic() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_statistic, container, false);
        buttonPieChart = view.findViewById(R.id.pie_chart);

        buttonPieChart.setOnClickListener(v -> {
            AndroidUtil._startActivity(getContext(), PieChartActivity.class);
        });

        return view;
    }
}