package com.example.teenduh.view.adapter.discovery;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.teenduh.R;
import com.example.teenduh._util.AndroidUtil;
import com.example.teenduh.model.Image;
import com.example.teenduh.model.User;
import com.example.teenduh.view.activity.ShowMoreInfo;
import com.example.teenduh.view.adapter.ImageAdapter;

import java.util.ArrayList;
import java.util.List;

public class CardStackAdapter extends RecyclerView.Adapter<CardStackAdapter.ViewHolder> {
  
  private final Context context;
  private List<User> userList;
  private FragmentActivity mActivity;
  // int currentPosition;
  
  
  public CardStackAdapter(List<User> userList, Context context, FragmentActivity mainLayout) {
    this.userList = userList;
    this.context = context;
    this.mActivity = mainLayout;
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
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  public class ViewHolder extends RecyclerView.ViewHolder {
    private final List<CardView> cardViewList = new ArrayList<>();
    TextView name, age, city;
    ViewPager2 viewPager2;
    FrameLayout frameLayout;
    ImageView showMoreInfo;
    int imageListSize;
    
    
    int curPicIdx;
    User _tempUser;
    
    ViewHolder(@NonNull View itemView) {
      super(itemView);
      name = itemView.findViewById(R.id.item_name);
      age = itemView.findViewById(R.id.item_age);
      city = itemView.findViewById(R.id.item_city);
      viewPager2 = itemView.findViewById(R.id.item_image);
      
      CardView cardView1 = itemView.findViewById(R.id.firstStage);
      CardView cardView2 = itemView.findViewById(R.id.secondStage);
      CardView cardView3 = itemView.findViewById(R.id.thirdStage);
      CardView cardView4 = itemView.findViewById(R.id.fourthStage);
      CardView cardView5 = itemView.findViewById(R.id.fifthStage);
      CardView cardView6 = itemView.findViewById(R.id.sixthStage);
      cardViewList.add(cardView1);
      cardViewList.add(cardView2);
      cardViewList.add(cardView3);
      cardViewList.add(cardView4);
      cardViewList.add(cardView5);
      cardViewList.add(cardView6);
  
      initMoreInfo(itemView);
      initImgChange(itemView);
    }
    
    @SuppressLint("ClickableViewAccessibility")
    private void initImgChange(@NonNull View itemView){
      frameLayout = itemView.findViewById(R.id.top_overlay);
      frameLayout.setOnTouchListener((view, event) -> {
        switch (event.getAction()) {
          case MotionEvent.ACTION_DOWN:
            float initialX = event.getX();
            return true;
          
          case MotionEvent.ACTION_UP:
            float finalX = event.getX();
            int cardWidth = view.getWidth();
            float cardCenterX = cardWidth / 2.0f; // Center of the card
            
            if (finalX > cardCenterX) {
              // slide right
              viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
            } else {
              // slide left
              viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
            }
            
            curPicIdx = viewPager2.getCurrentItem();
            for (int i = 0; i < imageListSize; i++) {
              ImageView imageView = (ImageView) cardViewList.get(i).getChildAt(0);
              int imgId = (i == curPicIdx) ? R.drawable.dashline_selected : R.drawable.dashline_unselected;
              imageView.setImageResource(imgId);
            }
            break;
        }
        return false;
      });
    }
    
    private void initMoreInfo(@NonNull View itemView) {
      showMoreInfo = itemView.findViewById(R.id.showInfo);
      showMoreInfo.bringToFront();
      showMoreInfo.setOnClickListener((v) -> {
        Intent intent = new Intent(context, ShowMoreInfo.class);
        String currentPositionStr = String.valueOf(curPicIdx);
        intent.putExtra("position", currentPositionStr);
        AndroidUtil.set_tempUser(_tempUser);
        
        context.startActivity(intent);
        mActivity.overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
      });
    }
  
    void setData(User user) {
      _tempUser = user;
      name.setText(user.getName());
      age.setText(user.getAge() + "");
      city.setText(user.getCity());
      
      List<Image> imageList = user.getPics();
      curPicIdx = 0;
      imageListSize = imageList.size();
      if (cardViewList.size() > imageList.size()) {
        for (int i = cardViewList.size() - 1; i >= imageList.size(); i--) {
          cardViewList.get(i).setVisibility(View.GONE);
        }
      }
  
      System.out.println(user.getName() + " -- " + imageListSize);
      for (int i = 0; i < imageListSize; i++) {
        ImageView imageView = (ImageView) cardViewList.get(i).getChildAt(0);
        int imgId = (i == curPicIdx) ? R.drawable.dashline_selected : R.drawable.dashline_unselected;
        imageView.setImageResource(imgId);
      }
  
      viewPager2.setAdapter(new ImageAdapter(imageList));
      viewPager2.setOffscreenPageLimit(3);
      viewPager2.setClipChildren(false);
      viewPager2.setClipToPadding(false);
      viewPager2.getChildAt(0).setOverScrollMode(ViewPager2.OVER_SCROLL_NEVER);
    }
  }
}