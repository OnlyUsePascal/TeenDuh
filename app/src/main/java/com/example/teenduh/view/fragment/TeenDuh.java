package com.example.teenduh.view.fragment;

import static android.content.ContentValues.TAG;

import android.content.Context;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teenduh.R;
import com.example.teenduh.model.User;
import com.example.teenduh.util.AndroidUtil;
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

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class TeenDuh extends Fragment {
  Context context;
  View view;
  private CardStackLayoutManager manager;
  private CardStackAdapter adapter;
  private CardStackView cardStackView;
  Button  btnLike, btnCancel, btnSuperLike;
  GifImageView imageCancel;
  GifImageView gifLike, gifSuperLike;

  public TeenDuh() {
    context = AndroidUtil.getContext();
  }
  
  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_tinduh, container, false);
    init(view);
    btnLike = view.findViewById(R.id.like_action);
    btnCancel = view.findViewById(R.id.cancel_action);
    btnSuperLike = view.findViewById(R.id.super_like_action);


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

          cardStackView.swipe();
          }, 1000);


    });
    btnCancel.setOnClickListener(v -> {
      Handler handler = new Handler();
        imageCancel.setVisibility(View.VISIBLE);
        imageCancel.bringToFront();
      handler.postDelayed(() -> {
        manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual);
        manager.setSwipeAnimationSetting(new SwipeAnimationSetting.Builder()
                .setDirection(Direction.Left)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(new AccelerateInterpolator())
                .build());
        cardStackView.swipe();
        imageCancel.setVisibility(View.INVISIBLE);
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


    return view;
  }
  
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    
  }
  private void init(View root) {
    cardStackView = root.findViewById(R.id.card_stack_view);
    imageCancel = root.findViewById(R.id.nope);
    gifLike = root.findViewById(R.id.like);
    gifSuperLike = root.findViewById(R.id.superLike);
    gifLike.bringToFront();
    imageCancel.bringToFront();
    gifSuperLike.bringToFront();
    manager = new CardStackLayoutManager(getContext(), new CardStackListener() {
      @Override
      public void onCardDragging(Direction direction, float ratio) {
      }

      @Override
      public void onCardSwiped(Direction direction) {
        Log.d(TAG, "onCardSwiped: p=" + manager.getTopPosition() + " d=" + direction);
        if (direction == Direction.Right){
//          Toast.makeText(getContext(), "Direction Right", Toast.LENGTH_SHORT).show();
        }
        if (direction == Direction.Top){
//          Toast.makeText(getContext(), "Direction Top", Toast.LENGTH_SHORT).show();
        }
        if (direction == Direction.Left){
//          Toast.makeText(getContext(), "Direction Left", Toast.LENGTH_SHORT).show();
        }
        if (direction == Direction.Bottom){
//          Toast.makeText(getContext(), "Direction Bottom", Toast.LENGTH_SHORT).show();
        }

        // Paginating
        if (manager.getTopPosition() == adapter.getItemCount() - 5){
          paginate();
        }

      }

      @Override
      public void onCardRewound() {

      }

      @Override
      public void onCardCanceled() {
        Log.d(TAG, "onCardRewound: " + manager.getTopPosition());
      }

      @Override
      public void onCardAppeared(View view, int position) {
        TextView tv = view.findViewById(R.id.item_name);
        Log.d(TAG, "onCardAppeared: " + position + ", nama: " + tv.getText());
      }

      @Override
      public void onCardDisappeared(View view, int position) {
        TextView tv = view.findViewById(R.id.item_name);
        Log.d(TAG, "onCardAppeared: " + position + ", nama: " + tv.getText());
      }
    });
    manager.setStackFrom(StackFrom.None);
    manager.setVisibleCount(3);
    manager.setTranslationInterval(8.0f);
    manager.setScaleInterval(0.95f);
    manager.setSwipeThreshold(0.3f);
    manager.setMaxDegree(20.0f);
    manager.setDirections(Direction.FREEDOM);
    manager.setCanScrollHorizontal(true);
    manager.setSwipeableMethod(SwipeableMethod.Manual);
    manager.setOverlayInterpolator(new LinearInterpolator());
    adapter = new CardStackAdapter(addList());
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
    users.add(new User(R.drawable.ronaldo, "Ronaldo", "24", "Vung Tau"));
    users.add(new User(R.drawable.messi, "Messi", "24", "HCM"));
    users.add(new User(R.drawable.park_seo,"Park Seo Jun" , "24", "NYC"));
    users.add(new User(R.drawable.park_seo,"Park Seo Jun" , "24", "NYC"));
    users.add(new User(R.drawable.park_seo,"Park Seo Jun" , "24", "NYC"));
    users.add(new User(R.drawable.park_seo,"Park Seo Jun" , "24", "NYC"));
    users.add(new User(R.drawable.park_seo,"Park Seo Jun" , "24", "NYC"));

    return users;
  }
}
