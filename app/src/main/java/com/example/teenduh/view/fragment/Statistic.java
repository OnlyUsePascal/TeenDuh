package com.example.teenduh.view.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teenduh.R;
import com.example.teenduh._util.AndroidUtil;
import com.example.teenduh.view.activity.admin.BarChartActivity;
import com.example.teenduh.view.activity.admin.PieChartActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.stripe.android.model.Card;

import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Statistic extends Fragment {

    private View view;
    private CardView cardView1;
    private CardView cardView2;
    private BarChart barChart;
    private Button buttonView;
    private TextView tvMatches;
    String month, year;

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

        cardView1 = view.findViewById(R.id.cardView1);
        cardView2 = view.findViewById(R.id.cardView2);
        barChart = view.findViewById(R.id.bar_chart);
        buttonView = view.findViewById(R.id.button_view);
        tvMatches = view.findViewById(R.id.tv_matches);

        cardView1.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), PieChartActivity.class);
            intent.putExtra("type", "age");
            startActivity(intent);
        });

        cardView2.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), PieChartActivity.class);
            intent.putExtra("type", "membership");
            startActivity(intent);
        });

        buttonView.setOnClickListener(v -> {
            showDatePickerDialog();
        });
        displayData();


        return view;
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

        final Dialog dialog = new Dialog(getContext());
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
            month = String.format("Month: %s", months[newVal]);
        });

        yearPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            tvYear.setText(String.format("Year: %s", newVal));
            year = String.valueOf(newVal);
        });

        btnView.setOnClickListener(v -> {
            tvMatches.setText("Number of matches in " + month + "/" + year);
            dialog.dismiss();
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }
}