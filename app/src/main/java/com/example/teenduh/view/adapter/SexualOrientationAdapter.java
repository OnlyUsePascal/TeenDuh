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

import java.util.List;

public class SexualOrientationAdapter extends RecyclerView.Adapter<SexualOrientationAdapter.ViewHolder> {
    private List<SexualOrientation> listOrientation;
    private View mView;
    public int selectionsCount = 0;
    public SexualOrientationAdapter(List<SexualOrientation> listOrientation, View mView) {
        this.listOrientation = listOrientation;
        this.mView = mView;
    }
    @NonNull
    @Override
    public SexualOrientationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_orientation_layout, parent, false);
        return new SexualOrientationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SexualOrientationAdapter.ViewHolder holder, int position) {
        SexualOrientation option = listOrientation.get(position);
        holder.name.setText(option.getName());


        if(selectionsCount == 0){
            holder.next.setEnabled(false);
        }
        else{
            holder.next.setEnabled(true);
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectionsCount < 3) {
                    if(!option.isSelected()){
                        holder.image.setVisibility(View.VISIBLE);
                        option.setSelected(true);
                        holder.name.setTypeface(null,Typeface.BOLD);
                        selectionsCount++;

                        holder.next.setBackground(mView.getResources().getDrawable(R.drawable.btn_layout));
                        holder.next.setTextColor(mView.getResources().getColor(R.color.white));
                        holder.next.setEnabled(true);

                    } else {
                        holder.image.setVisibility(View.INVISIBLE);
                        option.setSelected(false);
                        holder.name.setTypeface(null,Typeface.NORMAL);
                        selectionsCount--;
                        if(selectionsCount == 0){
                            holder.next.setBackground(mView.getResources().getDrawable(R.drawable.dashline_unselected));
                            holder.next.setTextColor(mView.getResources().getColor(R.color.black));
                            holder.next.setEnabled(false);
                        }


                    }
                } else {
                    if(option.isSelected()){
                        holder.image.setVisibility(View.INVISIBLE);
                        option.setSelected(false);
                        holder.name.setTypeface(null,Typeface.NORMAL);
                        selectionsCount--;
                        if(selectionsCount == 0){
                            holder.next.setBackground(mView.getResources().getDrawable(R.drawable.dashline_unselected));
                            holder.next.setTextColor(mView.getResources().getColor(R.color.black));
                            holder.next.setEnabled(false);
                        }
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return listOrientation.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView name;
        ImageView image;
        TextView next;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            name = itemView.findViewById(R.id.textView);
            image = itemView.findViewById(R.id.checkImage);
            next = mView.findViewById(R.id.nextButton);
        }
    }
}
