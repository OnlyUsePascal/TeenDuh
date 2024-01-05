package com.example.teenduh.view.adapter.message;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teenduh.R;
import com.example.teenduh.model.message.matches.NewMatchesViewModel;
import com.example.teenduh.util.AndroidUtil;
import com.example.teenduh.view.activity.TestSuccess;

import java.util.List;

public class NewMatchesAdapter extends RecyclerView.Adapter<NewMatchesAdapter.ViewHolder> {
  private List<NewMatchesViewModel> newMatchesList;
  private View mView;

  public NewMatchesAdapter(List<NewMatchesViewModel> newMatchesList, View mView) {
    this.newMatchesList = newMatchesList;
    this.mView = mView;
  }

  @NonNull
  @Override
  public NewMatchesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_chitchat_match, parent, false);
    return new NewMatchesAdapter.ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull NewMatchesAdapter.ViewHolder holder, int position) {
    holder.name.setText(newMatchesList.get(position).getName());

    holder.itemView.setOnClickListener(v -> {
      AndroidUtil._startActivity(mView.getContext(), TestSuccess.class);
    });
  }

  @Override
  public int getItemCount() {
    return newMatchesList.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    private TextView name;
    private ImageView image;
    public ViewHolder(@NonNull View itemView) {
      super(itemView);

      name = itemView.findViewById(R.id.name);
      image = itemView.findViewById(R.id.match_img);
    }
  }
}
