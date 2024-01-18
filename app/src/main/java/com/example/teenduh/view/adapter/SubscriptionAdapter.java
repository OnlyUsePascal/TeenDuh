package com.example.teenduh.view.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.teenduh.R;

import java.util.List;

public class SubscriptionAdapter extends RecyclerView.Adapter<SubscriptionAdapter.SubscriptionViewHolder>{

    private ViewPager2 viewPager2;

    public SubscriptionAdapter(ViewPager2 viewPager2){
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public SubscriptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tinder_subsription, parent, false);
        return new SubscriptionViewHolder((ViewGroup) view);
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull SubscriptionViewHolder holder, int position) {
        System.out.println("position: " + position);
        if (position == 0) {
            holder.imageView.setImageResource(R.drawable.tinder_plus);
            holder.imageView2.setImageResource(R.drawable.baseline_lock_24);
            holder.imageView3.setImageResource(R.drawable.baseline_lock_24);
            holder.button.setTextColor(ContextCompat.getColor(holder.button.getContext(), R.color.white));
            holder.button.setBackgroundColor(ContextCompat.getColor(holder.button.getContext(), R.color.my_primary));
        } else {
            holder.imageView.setImageResource(R.drawable.tinder_gold);
            holder.imageView2.setImageResource(R.drawable.baseline_check_24_red);
            holder.imageView3.setImageResource(R.drawable.baseline_check_24_red);
            holder.button.setTextColor(ContextCompat.getColor(holder.button.getContext(), R.color.black));
            holder.button.setBackgroundColor(ContextCompat.getColor(holder.button.getContext(), R.color.my_secondary));
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class SubscriptionViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        ImageView imageView2;
        ImageView imageView3;
        Button button;
        public SubscriptionViewHolder(@NonNull ViewGroup parent) {
            super(parent);
            imageView = parent.findViewById(R.id.imageView);
            imageView2 = parent.findViewById(R.id.imageView2);
            imageView3 = parent.findViewById(R.id.imageView3);
            button = parent.findViewById(R.id.button);
        }
    }


}
