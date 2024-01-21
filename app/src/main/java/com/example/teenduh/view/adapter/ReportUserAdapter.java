package com.example.teenduh.view.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teenduh.R;
import com.example.teenduh._util.FirebaseUtil;
import com.example.teenduh.model.report.ReportViewModel;
import java.util.List;

public class ReportUserAdapter extends RecyclerView.Adapter<ReportUserAdapter.ViewHolder> {
  private List<ReportViewModel> mReports;
  private Context context;
  private Runnable onReportDeleted;

  public ReportUserAdapter(List<ReportViewModel> mReports, Context context) {
    this.mReports = mReports;
    this.context = context;
  }
  @NonNull
  @Override
  public ReportUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_report, parent, false);
    return new ReportUserAdapter.ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ReportUserAdapter.ViewHolder holder, int position) {
    ReportViewModel report = mReports.get(position);

    holder.container.setOnClickListener(v -> {
      showReportDialog(position);
    });

    holder.reporterId.setText(report.getShortenedReporterId());
    holder.reporteeId.setText(report.getShortenedReceiverId());
    holder.description.setText(report.getDescription());
    holder.date.setText(report.getPreviewDate());
  }

  @Override
  public int getItemCount() {
    return mReports.size();
  }

  public void showReportDialog(int position) {
    final Dialog dialog = new Dialog(context);
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setContentView(R.layout.admin_handle_report_layout);

    Button discardBtn = dialog.findViewById(R.id.discardButton);
    Button banUser1Btn = dialog.findViewById(R.id.banUser1);
    Button banUser2Btn = dialog.findViewById(R.id.banUser2);

    ProgressBar progressBar = dialog.findViewById(R.id.progressBar);
    LinearLayout buttonLayout = dialog.findViewById(R.id.buttonLayout);
    TextView reporteeId = dialog.findViewById(R.id.reporteeId);
    TextView reporterId = dialog.findViewById(R.id.reporterId);
    TextView description = dialog.findViewById(R.id.reportDescription);
    TextView date = dialog.findViewById(R.id.date);

    reporteeId.setText(mReports.get(position).getShortenedReceiverId());
    reporterId.setText(mReports.get(position).getShortenedReporterId());
    description.setText(mReports.get(position).getDescription());
    date.setText(mReports.get(position).getPreviewDate());

    onReportDeleted = () -> {
      progressBar.setVisibility(View.GONE);
      buttonLayout.setVisibility(View.VISIBLE);
      dialog.dismiss();
    };

    discardBtn.setOnClickListener(v -> {
      progressBar.setVisibility(View.VISIBLE);
      buttonLayout.setVisibility(View.GONE);
      deleteReport(position);
    });
    banUser1Btn.setOnClickListener(v -> {
      progressBar.setVisibility(View.VISIBLE);
      buttonLayout.setVisibility(View.GONE);
      banUser(position, mReports.get(position).getReceiverId());
    });
    banUser2Btn.setOnClickListener(v -> {
      progressBar.setVisibility(View.VISIBLE);
      buttonLayout.setVisibility(View.GONE);
      banUser(position, mReports.get(position).getReporterId());
    });

    dialog.show();
    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
    dialog.getWindow().setGravity(Gravity.BOTTOM);
  }

  private void banUser(int position, String id) {
    FirebaseUtil.getFirestore()
      .collection("users")
      .document(id)
      .update("banned", true)
      .addOnCompleteListener(task -> {
        if (task.isSuccessful()) {
          Toast.makeText(context, "User banned successfully!", Toast.LENGTH_SHORT).show();
          deleteReport(position);
        } else {
          Toast.makeText(context, "Failed to ban user. Please try again later.", Toast.LENGTH_SHORT).show();
        }
      });
  }

  private void deleteReport(int position) {
    ReportViewModel report = mReports.get(position);
    FirebaseUtil.getFirestore()
      .collection("reports")
      .document(report.getId())
      .update("viewed", true)
      .addOnCompleteListener(task -> {
        if (task.isSuccessful()) {
          Toast.makeText(context, "Report set as viewed successfully!", Toast.LENGTH_SHORT).show();
          mReports.remove(position);
          notifyDataSetChanged();
          onReportDeleted.run();
        } else {
          Toast.makeText(context, "Failed to delete report. Please try again later.", Toast.LENGTH_SHORT).show();
        }
      });
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    private TextView reporterId, reporteeId, description, date;
    private LinearLayout container;
    public ViewHolder(@NonNull View parent) {
      super(parent);

      container = parent.findViewById(R.id.reportContainer);
      reporterId = parent.findViewById(R.id.reporterId);
      reporteeId = parent.findViewById(R.id.reporteeId);
      description = parent.findViewById(R.id.descriptionPreview);
      date = parent.findViewById(R.id.date);
    }
  }
}
