package com.example.teenduh.view.adapter.message;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teenduh.R;
import com.example.teenduh.model.message.MessageViewModel;
import com.example.teenduh.util.AndroidUtil;
import com.example.teenduh.view.activity.TestSuccess;

import java.util.List;

public class MessagesPreviewAdapter extends RecyclerView.Adapter<MessagesPreviewAdapter.ViewHolder> {
  private List<MessageViewModel> messagesList;
  private List<Integer> unreadAmountList;
  private View mView;

  public MessagesPreviewAdapter(List<MessageViewModel> messagesList, List<Integer> unreadAmountList, View view) {
    this.messagesList = messagesList;
    this.unreadAmountList = unreadAmountList;
    this.mView = view;
  }

  @NonNull
  @Override
  public MessagesPreviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_chitchat_messages, parent, false);
    return new MessagesPreviewAdapter.ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull MessagesPreviewAdapter.ViewHolder holder, int position) {
    MessageViewModel messageViewModel = messagesList.get(position);
    holder.name.setText(messageViewModel.getName());
    holder.messagePreview.setText(messageViewModel.getMessagePreview());
    holder.date.setText(messageViewModel.getPreviewDate());

    Integer unreadAmount = unreadAmountList.get(position);
    if (unreadAmount == 0) {
      holder.unreadContainer.setVisibility(View.GONE);
    } else {
      holder.unreadCount.setText(unreadAmount < 10 ? unreadAmount.toString() : "9+");
      holder.unreadContainer.setVisibility(View.VISIBLE);
    }

    holder.itemView.setOnClickListener(v -> {
      AndroidUtil._startActivity(mView.getContext(), TestSuccess.class);
    });
  }

  @Override
  public int getItemCount() {
    return messagesList.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    private TextView name;
    private TextView date;
    private TextView unreadCount;
    private TextView messagePreview;
    private ImageView image;
    private RelativeLayout unreadContainer;
    
    public ViewHolder(@NonNull View itemView) {
      super(itemView);

      name = itemView.findViewById(R.id.name);
      messagePreview = itemView.findViewById(R.id.messagePreview);
      image = itemView.findViewById(R.id.user_img);
      date = itemView.findViewById(R.id.date);
      unreadCount = itemView.findViewById(R.id.unreadCount);
      unreadContainer = itemView.findViewById(R.id.unreadContainer);
    }
  }
}
