package com.example.teenduh.view.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.teenduh.R;
import com.example.teenduh.view.activity.Subscription;

import java.util.List;

public class SubscriptionAdapter extends RecyclerView.Adapter<SubscriptionAdapter.SubscriptionViewHolder>{

    private ViewPager2 viewPager2;
    private FragmentActivity mActivity;

    public SubscriptionAdapter(ViewPager2 viewPager2, FragmentActivity mainLayout){
        this.viewPager2 = viewPager2;
        this.mActivity = mainLayout;
    }

    @NonNull
    @Override
    public SubscriptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subscription_profile, parent, false);
        return new SubscriptionAdapter.SubscriptionViewHolder(view);
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull SubscriptionViewHolder holder, int position) {
        System.out.println("position: " + position);
        if (position == 0) {
            holder.imageView.setImageResource(R.drawable.teenduh_plus);
        } else {
            holder.imageView.setImageResource(R.drawable.teenduh_premium);
        }
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Subscription.class);
                v.getContext().startActivity(intent);
                mActivity.overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class SubscriptionViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
//        Button button;
        public SubscriptionViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_profile);


        }
    }


}
