package com.example.teenduh.view.activity.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teenduh.R;
import com.example.teenduh.view.fragment.profile.Profile;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BarChartActivity extends AppCompatActivity {

    private BarChart barChart;
    private Button buttonView;
    private ImageView backButton;
    private TextView tvMatches;
    String month, year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);

        barChart = findViewById(R.id.bar_chart);
        buttonView = findViewById(R.id.button_view);

        backButton = findViewById(R.id.back_button);
        tvMatches = findViewById(R.id.tv_matches);

        buttonView.setOnClickListener(v -> {
                    showDatePickerDialog();
                });
        displayData();

        backButton.setOnClickListener(v -> {
            finish();
        });

        year = Year.now().toString();

    }

    private void displayData() {
        List<BarEntry> barEntries = new ArrayList<>();

        barEntries.add(new BarEntry(0, 40)); // Monday
        barEntries.add(new BarEntry(1, 50)); //Tuesday
        barEntries.add(new BarEntry(2, 60)); //Wednesday
        barEntries.add(new BarEntry(3, 70)); //Thursday
        barEntries.add(new BarEntry(4, 80)); //Friday
        barEntries.add(new BarEntry(5, 90)); //Saturday
        barEntries.add(new BarEntry(6, 100)); //Sunday

        BarDataSet barDataSet = new BarDataSet(barEntries, "Dates");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(R.color.black);
        barDataSet.setValueTextSize(12f);

        BarData barData = new BarData(barDataSet);

        barChart.setFitBars(true);
        barChart.setData(barData);
        //barChart.getDescription().setText("Bar Chart Example");

        //fit chart to screen and display
        barChart.getDescription().setEnabled(false);
        barChart.setExtraOffsets(5,10,5,5);

        //set animations
        barChart.animateXY(1000,1000);

        //disable zoom
        barChart.setScaleEnabled(false);

        //add legends
        barChart.getLegend().setEnabled(false);

        //add labels
        barChart.getXAxis().setDrawLabels(true);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.getXAxis().setDrawAxisLine(false);

        // Set custom labels for the x-axis
        String[] days = new String[]{"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(days));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);

        barChart.invalidate();
    }


    private void showDatePickerDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_date_picker);

        dialog.setOnCancelListener(dialogInterface -> {
            dialog.dismiss();
        });

        String[] months = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep",
                "Oct", "Nov", "Dec"};

        // Inflate the layout for this fragment
        NumberPicker monthPicker = dialog.findViewById(R.id.month_picker);
        NumberPicker yearPicker = dialog.findViewById(R.id.year_picker);
        TextView tvMonth = dialog.findViewById(R.id.tv_month);
        TextView tvYear = dialog.findViewById(R.id.tv_year);
        Button btnView = dialog.findViewById(R.id.button_view);

        // Get current month and year
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH); // January is 0

        // Pick month
        monthPicker.setMinValue(0);
        monthPicker.setMaxValue(11);
        monthPicker.setDisplayedValues(months);
        monthPicker.setValue(currentMonth); // Set to current month
        tvMonth.setText(String.format("Month: %s", months[monthPicker.getValue()]));

        // Pick year
        yearPicker.setMinValue(1900);
        yearPicker.setMaxValue(Year.now().getValue());
        yearPicker.setValue(currentYear); // Set to current year
        tvYear.setText(String.format("Year: %s", yearPicker.getValue()));

        // Set on change listener
        monthPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            tvMonth.setText(String.format("Month: %s", months[newVal]));
        });

        yearPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            tvYear.setText(String.format("Year: %s", newVal));
        });

        btnView.setOnClickListener(v -> {
            Toast.makeText(this, "ASD", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }

}