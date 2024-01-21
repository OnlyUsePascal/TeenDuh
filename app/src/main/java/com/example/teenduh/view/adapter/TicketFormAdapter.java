package com.example.teenduh.view.adapter;


import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teenduh.R;
import com.example.teenduh.model.SexualOrientation;
import com.example.teenduh.model.TicketForm;

import java.util.List;

public class TicketFormAdapter extends RecyclerView.Adapter<TicketFormAdapter.ViewHolder> {
  private List<TicketForm> ticketFormList;
  public TicketFormAdapter(List<TicketForm> ticketFormList) {
    this.ticketFormList = ticketFormList;
  }
  @NonNull
  @Override
  public TicketFormAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_form, parent, false);
    return new TicketFormAdapter.ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull TicketFormAdapter.ViewHolder holder, int position) {
     TicketForm ticketForm = ticketFormList.get(position);
      holder.formNumber.setText("Form: " + ticketForm.getNumber());
      holder.formCreatedDate.setText(ticketForm.getDate());
      holder.formCreator.setText(ticketForm.getName());
      holder.formDescription.setText(ticketForm.getReason());
//      cardView.setOnClickListener(new View.OnClickListener() {
//          @Override
//          public void onClick(View v) {
//              Intent intent = new Intent(v.getContext(), Subscription.class);
//              v.getContext().startActivity(intent);
//              mActivity.overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
//          }
//      });
  }

  @Override
  public int getItemCount() {
    return ticketFormList.size();
  }
  public class ViewHolder extends RecyclerView.ViewHolder{
    private TextView formNumber;
    private TextView formCreatedDate;
    private TextView formCreator;
    private TextView formStatus;
    private TextView formDescription;
    private CardView cardView;
    TextView next;
    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      formNumber = itemView.findViewById(R.id.ticketFormNumber);
      formCreatedDate = itemView.findViewById(R.id.dateForm);
      formCreator = itemView.findViewById(R.id.creator);
      formDescription = itemView.findViewById(R.id.description);
      cardView = itemView.findViewById(R.id.cardView);


    }
  }
}
