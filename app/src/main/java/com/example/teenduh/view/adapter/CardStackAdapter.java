package com.example.teenduh.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.teenduh.R;
import com.example.teenduh.model.Image;
import com.example.teenduh.model.User;

import java.util.ArrayList;
import java.util.List;

public class CardStackAdapter extends RecyclerView.Adapter<CardStackAdapter.ViewHolder> {

  private final Context context;
  private List<User> userList;
  int diffElement = 0;

  public CardStackAdapter(List<User> userList, Context context) {
    this.userList = userList;
    this.context = context;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    View view = inflater.inflate(R.layout.item_card, parent, false);
    return new ViewHolder(view);
  }

  @SuppressLint("ClickableViewAccessibility")
  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    User user = userList.get(position);
    holder.setData(user);
    List<Image> imageList = new ArrayList<>();
    imageList.add(new Image(R.drawable.ronaldo, "Cristiano Ronaldo"));
    imageList.add(new Image(R.drawable.park_seo, "Park Seo Joon"));
    imageList.add(new Image(R.drawable.modric, "Luka Modric"));
    imageList.add(new Image(R.drawable.ronaldo, "Cristiano Ronaldo"));

    if (holder.cardViewList.size() > imageList.size()) {
      for (int i = holder.cardViewList.size() - 1; i >= imageList.size(); i--) {
        holder.cardViewList.get(i).setVisibility(View.GONE);
      }
    }
    diffElement = imageList.size();
    for(int i = 0; i < imageList.size(); i++){
      if(i == 0){
        ImageView imageView = (ImageView) holder.cardViewList.get(i).getChildAt(0);
        imageView.setImageResource(R.drawable.custom_dashline_layout);
      }else{
        ImageView imageView = (ImageView) holder.cardViewList.get(i).getChildAt(0);
        imageView.setImageResource(R.drawable.btn_unselected);
      }
    }
      holder.viewPager2.setAdapter(new ImageAdapter(imageList, holder.viewPager2));
      holder.viewPager2.setOffscreenPageLimit(3);
      holder.viewPager2.setClipChildren(false);
      holder.viewPager2.setClipToPadding(false);
      holder.viewPager2.getChildAt(0).setOverScrollMode(ViewPager2.OVER_SCROLL_NEVER);

  }

  @Override
  public int getItemCount() {
    return userList.size();
  }

  public List<User> getUserList() {
    return userList;
  }

  public void setItems(List<User> user) {
    this.userList = user;
  }

  public void remove(int position) {
    userList.remove(position);
  }

  class ViewHolder extends RecyclerView.ViewHolder {
    private final CardView cardView1;
    private final CardView cardView2;
    private final CardView cardView3;
    private final CardView cardView4;
    private final CardView cardView5;
    private final CardView cardView6;
    private final List<CardView> cardViewList = new ArrayList<>();
    //        ImageView image;
    TextView name, age, city;
    ViewPager2 viewPager2;
    FrameLayout test;


    @SuppressLint("ClickableViewAccessibility")
    ViewHolder(@NonNull View itemView) {
      super(itemView);
//            image = itemView.findViewById(R.id.item_image);
      name = itemView.findViewById(R.id.item_name);
      age = itemView.findViewById(R.id.item_age);
      city = itemView.findViewById(R.id.item_city);
      viewPager2 = itemView.findViewById(R.id.item_image);

      cardView1 = itemView.findViewById(R.id.firstStage);
      cardView2 = itemView.findViewById(R.id.secondStage);
      cardView3 = itemView.findViewById(R.id.thirdStage);
      cardView4 = itemView.findViewById(R.id.fourthStage);
      cardView5 = itemView.findViewById(R.id.fifthStage);
      cardView6 = itemView.findViewById(R.id.sixthStage);

      cardViewList.add(cardView1);
      cardViewList.add(cardView2);
      cardViewList.add(cardView3);
      cardViewList.add(cardView4);
      cardViewList.add(cardView5);
      cardViewList.add(cardView6);

      test = itemView.findViewById(R.id.top_overlay);


      test.setOnTouchListener(new View.OnTouchListener() {
        float initialX;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
          switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
              initialX = event.getX();
              return true;

            case MotionEvent.ACTION_UP:
              float finalX = event.getX();
              int cardWidth = v.getWidth();
              float cardCenterX = cardWidth / 2.0f; // Center of the card
              if (finalX > cardCenterX) {
                viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
                int currentItem = viewPager2.getCurrentItem();
                for (int i = 0; i < diffElement; i++) {
                  if (i == currentItem) {
                    ImageView imageView = (ImageView) cardViewList.get(i).getChildAt(0);
                    imageView.setImageResource(R.drawable.custom_dashline_layout);
                  }else{
                    ImageView imageView = (ImageView) cardViewList.get(i).getChildAt(0);
                    imageView.setImageResource(R.drawable.btn_unselected);
                  }
                }
              } else {
                viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
                int currentItem = viewPager2.getCurrentItem();
                for (int i = 0; i < diffElement; i++) {
                  if (i == currentItem) {
                    ImageView imageView = (ImageView) cardViewList.get(i).getChildAt(0);
                    imageView.setImageResource(R.drawable.custom_dashline_layout);
                  }
                  else {
                    ImageView imageView = (ImageView) cardViewList.get(i).getChildAt(0);
                    imageView.setImageResource(R.drawable.btn_unselected);
                  }
                }
              }
              break;
          }
          return false;
        }
      });


    }

    void setData(User data) {
//            Picasso.get()
//                    .load(data.getImage())
//                    .fit()
//                    .centerCrop()
//                    .into(image);
      name.setText(data.getName());
      age.setText(data.getAge());
      city.setText(data.getCity());
    }
  }


}