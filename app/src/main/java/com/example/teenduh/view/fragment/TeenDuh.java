package com.example.teenduh.view.fragment;

import android.app.Activity;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DiffUtil;

import com.example.teenduh.R;
import com.example.teenduh._util.FirebaseUtil;
import com.example.teenduh._util.algo.EloRecommendationSystem;
import com.example.teenduh._util.algo.UserProfile;
import com.example.teenduh.model.User;
import com.example.teenduh._util.AndroidUtil;
import com.example.teenduh.view.activity.MatchingScreen;
import com.example.teenduh.view.activity.SettingFilter;
import com.example.teenduh.view.activity.Subscription;
import com.example.teenduh.view.adapter.discovery.CardStackAdapter;
import com.example.teenduh.view.adapter.discovery.CardStackCallback;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.Duration;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;
import pl.bclogic.pulsator4droid.library.PulsatorLayout;
import pl.droidsonroids.gif.GifImageView;

public class TeenDuh extends Fragment {
  private Activity activity;
  private CardStackLayoutManager manager;
  private CardStackAdapter stackAdapter;
  private CardStackView cardStackView;
  private TextView btnLike, btnSuperLike, btnCancel;
  private PulsatorLayout loadingCircleAnim;
  private GifImageView gifCancel;
  private GifImageView gifLike, gifSuperLike;
  private ImageView btnFilter;
  private ImageView showMoreInfo;
  private CircleImageView loadingImage;
  private int countLike = 0;
  private ImageView imgOutLike;
  private TextView buttonProceed;
  
  public TeenDuh() {
    activity = AndroidUtil.getActivity();
  }
  
  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_teenduh, container, false);
    
    // new
    btnLike = view.findViewById(R.id.like_action);
    btnCancel = view.findViewById(R.id.cancel_action);
    btnSuperLike = view.findViewById(R.id.super_like_action);
    btnFilter = view.findViewById(R.id.filter_button);
    loadingCircleAnim = view.findViewById(R.id.pulsator);
    loadingImage = view.findViewById(R.id.profile_image);
    imgOutLike = view.findViewById(R.id.imageRunOutOfLike);
    cardStackView = view.findViewById(R.id.card_stack_view);
    gifCancel = view.findViewById(R.id.nope);
    gifLike = view.findViewById(R.id.like);
    gifSuperLike = view.findViewById(R.id.superLike);
    buttonProceed = view.findViewById(R.id.buttonProceed);
    
    imgOutLike.setVisibility(View.INVISIBLE);
    AndroidUtil.setGenderFilter("All");
    AndroidUtil.setFilterFlag(0);
    
    initBtn();
    loadingStart();
    initCardStack();
    // filterByGender(AndroidUtil.getGenderFilter());
    return view;
  }
  
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }
  
  @Override
  public void onResume() {
    super.onResume();
    if (AndroidUtil.getFlagMatch() != null) {
      if (AndroidUtil.getFlagMatch().equals("cancel")) {
        String flag = AndroidUtil.getFlagMatch();
        // System.out.println("flag: " + flag);
        swipeLeft(gifCancel);
      } else if (AndroidUtil.getFlagMatch().equals("like")) {
        String flag = AndroidUtil.getFlagMatch();
        // System.out.println("flag: " + flag);
        swipeRight(gifLike);
      } else if (AndroidUtil.getFlagMatch().equals("superlike")) {
        String flag = AndroidUtil.getFlagMatch();
        // System.out.println("flag: " + flag);
        swipeTop(gifSuperLike);
      }
    }
    
    System.out.println("filter = " + AndroidUtil.getFilterFlag() + "//more info =" + AndroidUtil.isIsFromMoreInfo());
    if (AndroidUtil.getFilterFlag() == 1) {
      if (AndroidUtil.isIsFromMoreInfo()) {
        AndroidUtil.setIsFromMoreInfo(false);
        return;
      }
      paginate();
    }
  }
  
  public void setButtonToInvisible() {
    btnLike.setVisibility(View.GONE);
    btnCancel.setVisibility(View.GONE);
    btnSuperLike.setVisibility(View.GONE);
  }
  
  public void setBtnToVisible() {
    btnLike.setVisibility(View.VISIBLE);
    btnCancel.setVisibility(View.VISIBLE);
    btnSuperLike.setVisibility(View.VISIBLE);
  }
  
  public void loadingStart() {
    cardStackView.setVisibility(View.INVISIBLE);
    setButtonToInvisible();
    
    loadingCircleAnim.bringToFront();
    loadingImage.bringToFront();
    loadingCircleAnim.setVisibility(View.VISIBLE);
    loadingImage.setVisibility(View.VISIBLE);
    loadingCircleAnim.start();
  }
  
  public void loadingStop() {
    cardStackView.setVisibility(View.VISIBLE);
    setBtnToVisible();
    
    // cardStackView.bringToFront();
    loadingCircleAnim.setVisibility(View.GONE);
    loadingImage.setVisibility(View.GONE);
    loadingCircleAnim.stop();
  }
  
  private void initBtn() {
    buttonProceed.setOnClickListener(v -> {
      Intent intent = new Intent(getContext(), Subscription.class);
      startActivity(intent);
      getActivity().overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
    });
    
    btnFilter.setOnClickListener(v -> {
      Intent intent = new Intent(getContext(), SettingFilter.class);
      startActivity(intent);
      getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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
  
  private void paginate() {
    loadingStart();
    
    new Thread(() -> {
      // todo new list = (last elements of old list + new list)
      List<User> oldUsers = stackAdapter.getUserList();
      // List<User> newUsers = addList();
      List<User> newUsers = addListOnElo();
      
      DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(
          new CardStackCallback(oldUsers, newUsers));
      stackAdapter.setItems(newUsers);
      
      try {
        Thread.sleep(1500);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      
      getActivity().runOnUiThread(() -> {
        diffResult.dispatchUpdatesTo(stackAdapter);
        // stackAdapter.notifyDataSetChanged();
        loadingStop();
      });
    }).start();
  }
  
  private List<User> addList() {
    System.out.println(AndroidUtil.getFilterFlag() + "--" + AndroidUtil.getGenderFilter());
    List<User> usersOrigin = AndroidUtil.getUsers();
    if (AndroidUtil.getGenderFilter().equals("All") || AndroidUtil.getFilterFlag() == 0)
      return usersOrigin;
    
    List<User> usersNew = new ArrayList<>();
    String requiredGender = AndroidUtil.getGenderFilter();
    usersOrigin.forEach(user -> {
      if (user.getGender().equals(requiredGender)
              && !user.getId().equals(AndroidUtil.getCurUser().getId())) {
        usersNew.add(user);
      }
    });
    return usersNew;
  }
  
  private void initCardStack() {
    manager = new CardStackLayoutManager(getContext(), new StackListener());
    manager.setStackFrom(StackFrom.None);
    manager.setVisibleCount(3);
    manager.setTranslationInterval(12.0f);
    manager.setScaleInterval(0.95f);
    manager.setSwipeThreshold(0.3f);
    manager.setMaxDegree(20.0f);
    manager.setDirections(Direction.FREEDOM);
    manager.setCanScrollHorizontal(true);
    manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual);
    manager.setOverlayInterpolator(new LinearInterpolator());
    
    new Thread(() -> {
//      List<User> users = addList();
      List<User> users = addListOnElo();
      getActivity().runOnUiThread(() -> {
        stackAdapter = new CardStackAdapter(users, getContext(), getActivity());
        cardStackView.setLayoutManager(manager);
        cardStackView.setAdapter(stackAdapter);
        cardStackView.setItemAnimator(new DefaultItemAnimator());
        loadingStop();
      });
    }).start();
  }
  
  private List<User> addListOnElo() {
    List<User> users = addList();
    List<UserProfile> profiles = new ArrayList<>();
    for (User user : users) {
      if (user.getId().equals(AndroidUtil.getCurUser().getId())) {
        continue;
      }
      Set<String> interests = user.getInterests();
//      String userId = user.getId();
      Double score = user.getElo();
      UserProfile userProfile = new UserProfile(user, score, interests);
      profiles.add(userProfile);
    }
    
    UserProfile curUser = new UserProfile(AndroidUtil.getCurUser(), AndroidUtil.getCurUser().getElo(),
        AndroidUtil.getCurUser().getInterests());
    EloRecommendationSystem eloRecommendationSystem = new EloRecommendationSystem(curUser, profiles);
    List<User> topProfiles = eloRecommendationSystem.getTopProfiles(10);
    
    return topProfiles;
    
  }
  
  public void swipeLeft(GifImageView gifImage) {
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
  
  public void rewindManually() {
    manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual);
    manager.setSwipeAnimationSetting(new SwipeAnimationSetting.Builder()
                                         .setDirection(Direction.Left)
                                         .setDuration(Duration.Normal.duration)
                                         .setInterpolator(new AccelerateInterpolator())
                                         .build());
    cardStackView.rewind();
  }
  
  public void swipeRight(GifImageView gifImage) {
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
  
  
  class StackListener implements CardStackListener {
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
    
    User curCardUser;
    
    @Override
    public void onCardSwiped(Direction direction) {
      Log.d(TAG, "onCardSwiped: p=" + manager.getTopPosition() + " d=" + direction);
      Log.d(TAG, "Adapter size: " + stackAdapter.getItemCount());
      if (direction == Direction.Right) {
        // add liked users
        if (curCardUser != null) {
          AndroidUtil.getCurUser().likeUser(curCardUser.getId());
          if (curCardUser.isLikeUser(AndroidUtil.getCurUser())) {
            AndroidUtil.set_tempUser(curCardUser);
            AndroidUtil._startActivity(getActivity(), MatchingScreen.class);
          }
        }
        
        // elo
        countLike++;
        EloRecommendationSystem.updateElo(AndroidUtil.getCurUser(),
            stackAdapter.getUserList().get(manager.getTopPosition() - 1), 1);
        HashMap<String, Object> map = new HashMap<>();
        map.put("elo", AndroidUtil.getCurUser().getElo());
        FirebaseUtil.updateUser(AndroidUtil.getCurUser().getId(), map, null);
      }
      if (direction == Direction.Top) {
      }
      if (direction == Direction.Left) {
        // elo
        EloRecommendationSystem.updateElo(AndroidUtil.getCurUser(),
            stackAdapter.getUserList().get(manager.getTopPosition() - 1), 0);
        HashMap<String, Object> map = new HashMap<>();
        map.put("elo", AndroidUtil.getCurUser().getElo());
        FirebaseUtil.updateUser(AndroidUtil.getCurUser().getId(), map, null);
      }
      if (direction == Direction.Bottom) {
      }
      
      // Paginating
      if (manager.getTopPosition() == stackAdapter.getItemCount()) {
        manager.setTopPosition(0);
        paginate();
      }
    }
    
    @Override
    public void onCardRewound() {
    }
    
    @Override
    public void onCardCanceled() {
      setBtnToVisible();
    }
    
    
    @Override
    public void onCardAppeared(View view, int position) {
      setBtnToVisible();
      curCardUser = stackAdapter.getUserList().get(position);
      // System.out.println(curCardUser);
      checkVip();
    }
    
    @Override
    public void onCardDisappeared(View view, int position) {
      TextView tv = view.findViewById(R.id.item_name);
    }
    
    public void checkVip() {
      if (!AndroidUtil.getCurUser().isVip() && countLike >= 3) {
        // todo also on resume
        imgOutLike.setVisibility(View.VISIBLE);
        imgOutLike.bringToFront();
        buttonProceed.setVisibility(View.VISIBLE);
        buttonProceed.bringToFront();
      } else {
        imgOutLike.setVisibility(View.GONE);
        // imgOutLike.bringToFront();
        buttonProceed.setVisibility(View.GONE);
        // buttonProceed.bringToFront();
      }
    }
  }
  
}
