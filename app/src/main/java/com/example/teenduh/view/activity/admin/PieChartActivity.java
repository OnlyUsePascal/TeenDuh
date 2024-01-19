package com.example.teenduh.view.activity.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.example.teenduh.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

public class PieChartActivity extends AppCompatActivity {

    private PieChart pieChartAge;
    private PieChart pieChartGender;
    private PieChart pieChartMembership;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);

        pieChartAge = findViewById(R.id.pie_chart_age);
        pieChartGender = findViewById(R.id.pie_chart_gender);
        pieChartMembership = findViewById(R.id.pie_chart_membership);
        btnBack = findViewById(R.id.back_button);
//
        displayAgeDistribution();
        displayGenderDistribution();
        displayMembershipDistribution();

        btnBack.setOnClickListener(v -> {
            finish();
        });
    }

    private void displayAgeDistribution() {

        List<PieEntry> pieEntries = new ArrayList<>();

        //calculate data
        pieEntries.add(new PieEntry(18.5f, "16 - 18"));
        pieEntries.add(new PieEntry(26.7f, "18 - 22"));
        pieEntries.add(new PieEntry(24.0f, "23 - 26"));
        pieEntries.add(new PieEntry(30.8f, "26 - 30"));
        pieEntries.add(new PieEntry(19.0f, "30+"));

        //fit chart to screen and display
        pieChartAge.setUsePercentValues(true);
        pieChartAge.getDescription().setEnabled(false);
        pieChartAge.setExtraOffsets(5,10,5,5);

        //set animations
        pieChartAge.animateXY(1000,1000);

        //set legend
        pieChartAge.getLegend().setEnabled(true);
//
        //set entry label
        pieChartAge.setEntryLabelColor(R.color.black);
        pieChartAge.setEntryLabelTextSize(15f);

        PieDataSet pieDataSet = new PieDataSet(pieEntries, "Age");

        PieData pieData = new PieData(pieDataSet);
        pieChartAge.setData(pieData);
//
        //set colors
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(getResources().getColor(R.color.distribution_1));
        colors.add(getResources().getColor(R.color.distribution_2));
        colors.add(getResources().getColor(R.color.distribution_3));
        colors.add(getResources().getColor(R.color.distribution_4));
        colors.add(getResources().getColor(R.color.distribution_5));
        pieDataSet.setColors(colors);

        //set hole
        pieChartAge.setDrawHoleEnabled(true);
        pieChartAge.setHoleColor(getResources().getColor(R.color.white));
        pieChartAge.setHoleRadius(50f);
        pieChartAge.setTransparentCircleRadius(55f);

        //set rotation
        pieChartAge.setRotationAngle(0);
        pieChartAge.setRotationEnabled(true);

        //set center text
        pieChartAge.setCenterText("Age");
        pieChartAge.setCenterTextSize(15f);
        pieChartAge.setCenterTextColor(getResources().getColor(R.color.black));
        pieChartAge.setCenterTextRadiusPercent(100f);

        //set value text
        pieDataSet.setValueTextSize(15f);
        pieDataSet.setValueTextColor(getResources().getColor(R.color.black));
        pieDataSet.setValueLineColor(getResources().getColor(R.color.black));
        pieDataSet.setValueLinePart1Length(0.5f);
        pieDataSet.setValueLinePart2Length(0.5f);
        pieDataSet.setValueLinePart1OffsetPercentage(80f);
        pieDataSet.setValueLinePart1Length(0.2f);
        pieDataSet.setValueLineVariableLength(true);
        pieDataSet.setValueLinePart2Length(0.4f);

        //display
        pieChartAge.invalidate();
    }

    private void displayMembershipDistribution() {

        List<PieEntry> pieEntries = new ArrayList<>();

        //calculate data
        pieEntries.add(new PieEntry(40.5f, "Member"));
        pieEntries.add(new PieEntry(59.5f, "Plus"));
        pieEntries.add(new PieEntry(109.5f, "Premium"));

        //fit chart to screen and display
        pieChartMembership.setUsePercentValues(true);
        pieChartMembership.getDescription().setEnabled(false);
        pieChartMembership.setExtraOffsets(5,10,5,5);

        //set animations
        pieChartMembership.animateXY(1000,1000);

        //set legend
        pieChartMembership.getLegend().setEnabled(true);
//
        //set entry label
        pieChartMembership.setEntryLabelColor(R.color.black);
        pieChartMembership.setEntryLabelTextSize(15f);

        PieDataSet pieDataSet = new PieDataSet(pieEntries, "Membership");

        PieData pieData = new PieData(pieDataSet);
        pieChartMembership.setData(pieData);
//
        //set colors
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(getResources().getColor(R.color.chat_color_sender));
        colors.add(getResources().getColor(R.color.seed));
        colors.add(getResources().getColor(R.color.distribution_3));
        pieDataSet.setColors(colors);

        //set hole
        pieChartMembership.setDrawHoleEnabled(true);
        pieChartMembership.setHoleColor(getResources().getColor(R.color.white));
        pieChartMembership.setHoleRadius(50f);
        pieChartMembership.setTransparentCircleRadius(55f);

        //set rotation
        pieChartMembership.setRotationAngle(0);
        pieChartMembership.setRotationEnabled(true);

        //set center text
        pieChartMembership.setCenterText("Membership");
        pieChartMembership.setCenterTextSize(15f);
        pieChartMembership.setCenterTextColor(getResources().getColor(R.color.black));
        pieChartMembership.setCenterTextRadiusPercent(100f);

        //set value text
        pieDataSet.setValueTextSize(15f);
        pieDataSet.setValueTextColor(getResources().getColor(R.color.black));
        pieDataSet.setValueLineColor(getResources().getColor(R.color.black));
        pieDataSet.setValueLinePart1Length(0.5f);
        pieDataSet.setValueLinePart2Length(0.5f);
        pieDataSet.setValueLinePart1OffsetPercentage(80f);
        pieDataSet.setValueLinePart1Length(0.2f);
        pieDataSet.setValueLineVariableLength(true);
        pieDataSet.setValueLinePart2Length(0.4f);

        //display
        pieChartMembership.invalidate();
    }

    private void displayGenderDistribution() {

        List<PieEntry> pieEntries = new ArrayList<>();

        //calculate data
        pieEntries.add(new PieEntry(40.5f, "Male"));
        pieEntries.add(new PieEntry(59.5f, "Female"));

        //fit chart to screen and display
        pieChartGender.setUsePercentValues(true);
        pieChartGender.getDescription().setEnabled(false);
        pieChartGender.setExtraOffsets(5,10,5,5);

        //set animations
        pieChartGender.animateXY(1000,1000);

        //set legend
        pieChartGender.getLegend().setEnabled(true);
//
        //set entry label
        pieChartGender.setEntryLabelColor(R.color.black);
        pieChartGender.setEntryLabelTextSize(15f);

        PieDataSet pieDataSet = new PieDataSet(pieEntries, "Gender");

        PieData pieData = new PieData(pieDataSet);
        pieChartGender.setData(pieData);
//
        //set colors
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(getResources().getColor(R.color.distribution_1));
        colors.add(getResources().getColor(R.color.distribution_2));
        pieDataSet.setColors(colors);

        //set hole
        pieChartGender.setDrawHoleEnabled(true);
        pieChartGender.setHoleColor(getResources().getColor(R.color.white));
        pieChartGender.setHoleRadius(50f);
        pieChartGender.setTransparentCircleRadius(55f);

        //set rotation
        pieChartGender.setRotationAngle(0);
        pieChartGender.setRotationEnabled(true);

        //set center text
        pieChartGender.setCenterText("Gender");
        pieChartGender.setCenterTextSize(15f);
        pieChartGender.setCenterTextColor(getResources().getColor(R.color.black));
        pieChartGender.setCenterTextRadiusPercent(100f);

        //set value text
        pieDataSet.setValueTextSize(15f);
        pieDataSet.setValueTextColor(getResources().getColor(R.color.black));
        pieDataSet.setValueLineColor(getResources().getColor(R.color.black));
        pieDataSet.setValueLinePart1Length(0.5f);
        pieDataSet.setValueLinePart2Length(0.5f);
        pieDataSet.setValueLinePart1OffsetPercentage(80f);
        pieDataSet.setValueLinePart1Length(0.2f);
        pieDataSet.setValueLineVariableLength(true);
        pieDataSet.setValueLinePart2Length(0.4f);

        //display
        pieChartGender.invalidate();
    }

}