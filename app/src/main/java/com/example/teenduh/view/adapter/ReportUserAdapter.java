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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teenduh.R;
import com.example.teenduh._util.FirebaseUtil;
import com.example.teenduh.model.report.ReportViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ReportUserAdapter extends RecyclerView.Adapter<ReportUserAdapter.ViewHolder> {
  private List<ReportViewModel> mReports;
  private Context context;
  private Runnable onReportDeleted, onUserBanned;

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

    banUser2Btn.setOnClickListener(v -> {
      showConfirmBanDialog(position);
    });

    dialog.show();
    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
    dialog.getWindow().setGravity(Gravity.BOTTOM);
  }

  public void showConfirmBanDialog(int position) {
    final Dialog dialog = new Dialog(context);
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setContentView(R.layout.ban_user_layout);

    ProgressBar progressBar = dialog.findViewById(R.id.progressBar);

    Button banButton = dialog.findViewById(R.id.ban);
    Button cancelButton = dialog.findViewById(R.id.discardButton);

    TextView baneeUserName = dialog.findViewById(R.id.baneeUserName);
    TextInputEditText reason = dialog.findViewById(R.id.reasonField);

    LinearLayout buttonLayout = dialog.findViewById(R.id.buttonLayout);

    baneeUserName.setText(mReports.get(position).getShortenedReceiverId());
    Spinner spinner = dialog.findViewById(R.id.banSpinner);

    cancelButton.setOnClickListener(v -> {
      dialog.dismiss();
    });

    onUserBanned = () -> {
      progressBar.setVisibility(View.GONE);
      buttonLayout.setVisibility(View.VISIBLE);
      dialog.dismiss();
    };

    banButton.setOnClickListener(v -> {
      String banPeriodString = spinner.getSelectedItem().toString();
      String banReason = reason.getText().toString();

      if (banReason.isEmpty()) {
        reason.setError("Please enter a reason");
        return;
      }

      buttonLayout.setVisibility(View.INVISIBLE);
      progressBar.setVisibility(View.VISIBLE);

      banUser(position, mReports.get(position).getReceiverId(), banReason, banPeriodString);
    });

    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
      context,
      R.array.ban_period,
      android.R.layout.simple_spinner_item
    );
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    spinner.setAdapter(adapter);

    dialog.show();
    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
    dialog.getWindow().setGravity(Gravity.BOTTOM);
  }

  private Date mapPeriodToDate(String banPeriodString) {
    LocalDate futureDate = LocalDate.now();

    switch (banPeriodString) {
      case "1 Month":
        futureDate = futureDate.plusMonths(1);
        break;
      case "1 Year":
        futureDate = futureDate.plusYears(1);
        break;
      case "Forever":
        futureDate = futureDate.plusYears(99);
        break;
    }

    return Date.from(futureDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
  }

  private void banUser(int position, String id, String banReason, String banPeriodString) {
    HashMap<String, Object> obj = new HashMap<String, Object>() {{
      put("userId", id);
      put("reason", banReason);
      put("deadline", mapPeriodToDate(banPeriodString));
    }};

    FirebaseUtil.getFirestore()
      .collection("users_ban")
      .add(obj)
      .addOnCompleteListener(task -> {
        if (task.isSuccessful()) {
          Toast.makeText(context, "User banned successfully!", Toast.LENGTH_SHORT).show();
          deleteReport(position);
        } else {
          Toast.makeText(context, "Failed to ban user. Please try again later.", Toast.LENGTH_SHORT).show();
        }
        onUserBanned.run();
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
