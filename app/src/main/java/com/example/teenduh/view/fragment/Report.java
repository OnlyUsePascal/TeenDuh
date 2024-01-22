package com.example.teenduh.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.teenduh.R;
import com.example.teenduh._util.FirebaseUtil;
import com.example.teenduh.model.report.ReportViewModel;
import com.example.teenduh.view.adapter.ReportUserAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Report extends Fragment {

  private Button viewUser;
  private RecyclerView reportList;
  private ProgressBar progressBar;
  private List<ReportViewModel> reports;

  @Override
  public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view =  inflater.inflate(R.layout.fragment_report, container, false);

    reports = new ArrayList<>();
    progressBar = view.findViewById(R.id.reportProgressBar);

    getReportList();

    reportList = view.findViewById(R.id.reportList);
    reportList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
    reportList.setAdapter(new ReportUserAdapter(reports, getContext()));

    return view;
  }

  public void getReportList() {
    progressBar.setVisibility(View.VISIBLE);
    FirebaseUtil.getFirestore()
      .collection("reports")
      // .whereEqualTo("viewed", false)
      .get()

      .addOnCompleteListener(task -> {
        if (task.isSuccessful()) {
          reports.clear();
          for (int i = 0; i < task.getResult().size(); i++) {
            String id = task.getResult().getDocuments().get(i).getId();
            String reporterId = task.getResult().getDocuments().get(i).getString("reporterId");
            String receiverId = task.getResult().getDocuments().get(i).getString("reporteeId");
            String description = task.getResult().getDocuments().get(i).getString("description");
            Boolean viewed = task.getResult().getDocuments().get(i).getBoolean("viewed");
            Date date = task.getResult().getDocuments().get(i).getDate("date");
            reports.add(new ReportViewModel(id, reporterId, receiverId, description, viewed, date));
          }
          reportList.getAdapter().notifyDataSetChanged();
          progressBar.setVisibility(View.GONE);
        }
      });
  }
}