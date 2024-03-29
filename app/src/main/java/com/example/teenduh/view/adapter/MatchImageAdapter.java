package com.example.teenduh.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teenduh.R;
import com.example.teenduh._util.AndroidUtil;
import com.example.teenduh.model.Image;
import com.example.teenduh.model.User;
import com.example.teenduh.view.activity.ShowMoreInfo;
import com.example.teenduh.view.activity.Subscription;

import java.util.List;

public class MatchImageAdapter extends RecyclerView.Adapter<MatchImageAdapter.MatchImageHolder>{
  private List<User> userList;
  private Boolean isVip;
  private Context context;
  private FragmentActivity mActivity;
  private User _tempUser;
  public MatchImageAdapter(List<User> userList, Boolean isVip, Context context, FragmentActivity mainLayout){
    this.userList = userList;
    this.isVip = isVip;
    this.context = context;
    this.mActivity = mainLayout;

  }

  @NonNull
  @Override
  public MatchImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_item_card_view, parent, false);
    return new MatchImageHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull MatchImageHolder holder, int position) {
    User user = userList.get(position);

    holder.imageView.setImageResource(R.drawable.modric);
    for(Image image: user.getPics()){
      if(image != null){
        holder.imageView.setImageURI(image.getUri());
        break;
      }
    }
    holder.cardView1.setVisibility(View.GONE);
    holder.cardView2.setVisibility(View.GONE);
    holder.nameTextView.setVisibility(View.VISIBLE);
    holder.likeTimesTextView.setVisibility(View.VISIBLE);
//    holder.nameTextView.setText(image.getHeading());
    holder.nameTextView.setText(user.getName());
    holder.likeTimesTextView.setText("10 mins ago");
    holder.seeMoreCardView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(context, ShowMoreInfo.class);
        _tempUser = user;
        AndroidUtil.set_tempUser(_tempUser);
        context.startActivity(intent);
        mActivity.overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
      }
    });
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
      if (!isVip){
        holder.imageView.setRenderEffect(RenderEffect.createBlurEffect(100, 100, Shader.TileMode.MIRROR));
        holder.cardView1.setVisibility(View.VISIBLE);
        holder.cardView2.setVisibility(View.VISIBLE);
        holder.nameTextView.setVisibility(View.GONE);
        holder.likeTimesTextView.setVisibility(View.GONE);
        holder.seeMoreCardView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Intent intent = new Intent(context, Subscription.class);
            context.startActivity(intent);
            mActivity.overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
          }
        });
       }
    }


  }

  @Override
  public int getItemCount() {
    return userList.size();
  }

  public class MatchImageHolder extends RecyclerView.ViewHolder{
    private ImageView imageView;
    CardView seeMoreCardView;
    CardView cardView1, cardView2;
    TextView nameTextView, likeTimesTextView;
    public MatchImageHolder(@NonNull View itemView) {
      super(itemView);
     imageView = itemView.findViewById(R.id.imageView);
     seeMoreCardView = itemView.findViewById(R.id.card_view);
      cardView1 = itemView.findViewById(R.id.card_view_1);
      cardView2 = itemView.findViewById(R.id.card_view_2);
      nameTextView = itemView.findViewById(R.id.tv_match_name);
      likeTimesTextView = itemView.findViewById(R.id.tv_like_time);
    }
  }

}
