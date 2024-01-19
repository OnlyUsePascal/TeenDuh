package com.example.teenduh.view.fragment;

import android.app.Activity;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DiffUtil;

import com.example.teenduh.R;
import com.example.teenduh.model.User;
import com.example.teenduh._util.AndroidUtil;
import com.example.teenduh.view.activity.SettingFilter;
import com.example.teenduh.view.adapter.CardStackAdapter;
import com.example.teenduh.view.adapter.CardStackCallback;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.Duration;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import pl.bclogic.pulsator4droid.library.PulsatorLayout;
import pl.droidsonroids.gif.GifImageView;

public class TeenDuh extends Fragment {
  Activity activity;
  Context context;
  View view;
  private CardStackLayoutManager manager;
  private CardStackAdapter adapter;
  private CardStackView cardStackView;
  TextView btnLike, btnSuperLike, btnCancel;
  PulsatorLayout pulsatorLayout;
  GifImageView gifCancel;
  GifImageView gifLike, gifSuperLike;
  Button btnFilter;
  ImageView showMoreInfo;
  CircleImageView circleImageView;
  
  public TeenDuh() {
    activity = AndroidUtil.getActivity();
  }
  
  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_teenduh, container, false);

    btnLike = view.findViewById(R.id.like_action);
    btnCancel = view.findViewById(R.id.cancel_action);
    btnSuperLike = view.findViewById(R.id.super_like_action);
    btnFilter = view.findViewById(R.id.filter_button);
    pulsatorLayout = view.findViewById(R.id.pulsator);
    circleImageView = view.findViewById(R.id.profile_image);
    pulsatorLayout.bringToFront();
    circleImageView.bringToFront();
  
    initBtn();
    getLoading(view);
    return view;
  }
  
  
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }
  
  @Override
  public void onResume() {
    super.onResume();
    if(AndroidUtil.getFlagMatch()!= null){
      if(AndroidUtil.getFlagMatch().equals("cancel")){
        String flag = AndroidUtil.getFlagMatch();
        System.out.println("flag: " + flag);
        swipeLeft(gifCancel);
      }else if(AndroidUtil.getFlagMatch().equals("like")){
        String flag = AndroidUtil.getFlagMatch();
        System.out.println("flag: " + flag);
        swipeRight(gifLike);
      } else if(AndroidUtil.getFlagMatch().equals("superlike")) {
        String flag = AndroidUtil.getFlagMatch();
        System.out.println("flag: " + flag);
        swipeTop(gifSuperLike);
      }
    }
  }
  
  private void getLoading(View view) {
    pulsatorLayout.start();
    new Handler().postDelayed(() -> {
      pulsatorLayout.stop();
      pulsatorLayout.setVisibility(View.GONE);
      circleImageView.setVisibility(View.GONE);
      init(view);
    }, 5000);
  }
  
  private void initBtn() {
    btnFilter.setOnClickListener(v -> {
      Intent intent = new Intent(getContext(), SettingFilter.class);
      startActivity(intent);
    });
    
    btnLike.setOnClickListener(v -> {
      Handler handler = new Handler();
      gifLike.setVisibility(View.VISIBLE);
      gifLike.bringToFront();
      handler.postDelayed(() -> {
        manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual);
        manager.setSwipeAnimationSetting(new SwipeAnimationSetting.Builder()
                                             .setDirection(Direction.Right)
                                             .setDuration(Duration.Normal.duration)
                                             .setInterpolator(new AccelerateInterpolator())
                                             .build());
        gifLike.setVisibility(View.INVISIBLE);
        btnCancel.setVisibility(View.VISIBLE);
        btnSuperLike.setVisibility(View.VISIBLE);
        cardStackView.swipe();
      }, 1000);
      
      
    });
    
    btnCancel.setOnClickListener(v -> {
      Handler handler = new Handler();
      gifCancel.setVisibility(View.VISIBLE);
      gifCancel.bringToFront();
      handler.postDelayed(() -> {
        manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual);
        manager.setSwipeAnimationSetting(new SwipeAnimationSetting.Builder()
                                             .setDirection(Direction.Left)
                                             .setDuration(Duration.Normal.duration)
                                             .setInterpolator(new AccelerateInterpolator())
                                             .build());
        gifCancel.setVisibility(View.INVISIBLE);
        cardStackView.swipe();
      }, 1000);
      
    });
    
    btnSuperLike.setOnClickListener(v -> {
      Handler handler = new Handler();
      gifSuperLike.setVisibility(View.VISIBLE);
      gifSuperLike.bringToFront();
      handler.postDelayed(() -> {
        manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual);
        manager.setSwipeAnimationSetting(new SwipeAnimationSetting.Builder()
                                             .setDirection(Direction.Top)
                                             .setDuration(Duration.Normal.duration)
                                             .setInterpolator(new AccelerateInterpolator())
                                             .build());
        gifSuperLike.setVisibility(View.INVISIBLE);
        cardStackView.swipe();
      }, 1000);
    });
  }
  
  
  
  
  private void init(View root) {
    cardStackView = root.findViewById(R.id.card_stack_view);
    gifCancel = root.findViewById(R.id.nope);
    gifLike = root.findViewById(R.id.like);
    gifSuperLike = root.findViewById(R.id.superLike);
    gifLike.bringToFront();
    gifCancel.bringToFront();
    gifSuperLike.bringToFront();
    manager = new CardStackLayoutManager(getContext(), new CardStackListener() {
      @Override
      public void onCardDragging(Direction direction, float ratio) {
        if (direction == Direction.Right) {
          btnLike.setVisibility(View.VISIBLE);
          btnCancel.setVisibility(View.INVISIBLE);
          btnSuperLike.setVisibility(View.INVISIBLE);
        } else if (direction == Direction.Left) {
          btnSuperLike.setVisibility(View.INVISIBLE);
          btnCancel.setVisibility(View.VISIBLE);
          btnLike.setVisibility(View.INVISIBLE);
        } else if (direction == Direction.Top) {
          btnSuperLike.setVisibility(View.VISIBLE);
          btnLike.setVisibility(View.INVISIBLE);
          btnCancel.setVisibility(View.INVISIBLE);
        }
      }
      
      @Override
      public void onCardSwiped(Direction direction) {
        Log.d(TAG, "onCardSwiped: p=" + manager.getTopPosition() + " d=" + direction);
        Log.d(TAG, "Adapter size: " + adapter.getItemCount());
        if (direction == Direction.Right) {
//          Toast.makeText(getContext(), "Direction Right", Toast.LENGTH_SHORT).show();
        }
        if (direction == Direction.Top) {
//          Toast.makeText(getContext(), "Direction Top", Toast.LENGTH_SHORT).show();
        }
        if (direction == Direction.Left) {
        }
        if (direction == Direction.Bottom) {
//          Toast.makeText(getContext(), "Direction Bottom", Toast.LENGTH_SHORT).show();
        }
        
        // Paginating
        if (manager.getTopPosition() == adapter.getItemCount() - 1) {
          manager.setTopPosition(0);
          paginate();
        }
        
      }
      
      @Override
      public void onCardRewound() {
        btnCancel.setVisibility(View.VISIBLE);
        btnSuperLike.setVisibility(View.VISIBLE);
        btnLike.setVisibility(View.VISIBLE);
      }
      
      @Override
      public void onCardCanceled() {
        btnCancel.setVisibility(View.VISIBLE);
        btnSuperLike.setVisibility(View.VISIBLE);
        btnLike.setVisibility(View.VISIBLE);
      }
      
      @Override
      public void onCardAppeared(View view, int position) {
        btnCancel.setVisibility(View.VISIBLE);
        btnSuperLike.setVisibility(View.VISIBLE);
        btnLike.setVisibility(View.VISIBLE);
      }
      
      @Override
      public void onCardDisappeared(View view, int position) {
        TextView tv = view.findViewById(R.id.item_name);
      }
    });
    
    manager.setStackFrom(StackFrom.None);
    manager.setVisibleCount(3);
    manager.setTranslationInterval(12.0f);
    manager.setScaleInterval(0.95f);
    manager.setSwipeThreshold(0.3f);
    manager.setMaxDegree(20.0f);
    manager.setDirections(Direction.FREEDOM);
    manager.setCanScrollHorizontal(true);
    manager.setSwipeableMethod(SwipeableMethod.Manual);
    manager.setOverlayInterpolator(new LinearInterpolator());
    adapter = new CardStackAdapter(addList(), getContext(), getActivity());
    cardStackView.setLayoutManager(manager);
    cardStackView.setAdapter(adapter);
    cardStackView.setItemAnimator(new DefaultItemAnimator());
  }
  
  private void paginate() {
    List<User> old = adapter.getUserList();
    List<User> newList = new ArrayList<>(addList());
    CardStackCallback callback = new CardStackCallback(old, newList);
    DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(callback);
    System.out.println("New List: " + newList.size());
    adapter.setItems(newList);
    diffResult.dispatchUpdatesTo(adapter);
  }
  
  private List<User> addList() {
    List<User> users = new ArrayList<>();
    users.add(new User(R.drawable.ronaldo, "Ronaldo", "Vung Tau", LocalDate.of(2003, 1, 3)));
    users.add(new User(R.drawable.messi, "Messi", "HCM", LocalDate.of(2000, 1, 3)));
    users.add(new User(R.drawable.park_seo, "Park Seo Jun", "NYC", LocalDate.of(2009, 1, 3)));
    
    // users.add(new User(R.drawable.ronaldo, "Ronaldo", "24", "Vung Tau"));
    // users.add(new User(R.drawable.park_seo, "Park Seo Jun", "24", "NYC"));
    // users.add(new User(R.drawable.park_seo, "Park Seo Jun", "24", "NYC"));
    // users.add(new User(R.drawable.park_seo, "Park Seo Jun", "24", "NYC"));
    // users.add(new User(R.drawable.park_seo, "Park Seo Jun", "24", "NYC"));
    // users.add(new User(R.drawable.park_seo, "Park Seo Jun", "24", "NYC"));
    
    return users;
  }

  
  public void swipeLeft(GifImageView gifImage){
    Handler handler = new Handler();
    gifImage.setVisibility(View.VISIBLE);
    gifImage.bringToFront();
    handler.postDelayed(() -> {
      manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual);
      manager.setSwipeAnimationSetting(new SwipeAnimationSetting.Builder()
          .setDirection(Direction.Left)
          .setDuration(Duration.Normal.duration)
          .setInterpolator(new AccelerateInterpolator())
          .build());
      cardStackView.swipe();
      gifImage.setVisibility(View.INVISIBLE);
      AndroidUtil.setFlagMatch(null);
    }, 1000);
  }
  
  public void swipeRight(GifImageView gifImage){
    Handler handler = new Handler();
    gifImage.setVisibility(View.VISIBLE);
    gifImage.bringToFront();
    handler.postDelayed(() -> {
      manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual);
      manager.setSwipeAnimationSetting(new SwipeAnimationSetting.Builder()
          .setDirection(Direction.Right)
          .setDuration(Duration.Normal.duration)
          .setInterpolator(new AccelerateInterpolator())
          .build());
      gifImage.setVisibility(View.INVISIBLE);
      cardStackView.swipe();
      AndroidUtil.setFlagMatch(null);
    }, 1000);
  }
  public void swipeTop(GifImageView gifImage) {
    Handler handler = new Handler();
    gifImage.setVisibility(View.VISIBLE);
    gifImage.bringToFront();
    handler.postDelayed(() -> {
      manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual);
      manager.setSwipeAnimationSetting(new SwipeAnimationSetting.Builder()
          .setDirection(Direction.Top)
          .setDuration(Duration.Normal.duration)
          .setInterpolator(new AccelerateInterpolator())
          .build());
      gifImage.setVisibility(View.INVISIBLE);
      cardStackView.swipe();
    }, 1000);
  }
}
