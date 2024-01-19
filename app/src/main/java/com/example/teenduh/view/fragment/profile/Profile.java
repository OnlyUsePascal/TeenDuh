package com.example.teenduh.view.fragment.profile;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.teenduh.R;
import com.example.teenduh.model.Image;
import com.example.teenduh._util.AndroidUtil;
import com.example.teenduh.model.User;
import com.example.teenduh.view.adapter.SubscriptionAdapter;

import com.example.teenduh.view.activity.MainLayout;
import com.example.teenduh.view.activity.profile.EditProfile;
import com.example.teenduh.view.adapter.ImageAdapter;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Profile extends Fragment {
  private MainLayout mainLayout;
  private Activity activity;
  private Button buttonSuperLike, buttonSuperFind;
  private AppCompatImageButton editProfile;
  private boolean isProceedPayment;
  private boolean isDeposit;
  private ViewPager2 viewPager2;
  private CardView circle1, circle2;
  private List<Image> imageList;
  private CircularProgressIndicator progressIndicator;
  private Handler sliderHandler;
  private Runnable runnable = new Runnable() {
    @Override
    public void run() {
      viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
    }
  };
  
  public Profile() {
    activity = AndroidUtil.getActivity();
    sliderHandler = new Handler();
    
  }
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_profile, container, false);
    buttonSuperLike = view.findViewById(R.id.button10);
    buttonSuperFind = view.findViewById(R.id.button11);
    progressIndicator = view.findViewById(R.id.progressBar);
    viewPager2 = view.findViewById(R.id.viewPager2);
    circle1 = view.findViewById(R.id.circle1);
    circle2 = view.findViewById(R.id.circle2);
    editProfile = view.findViewById(R.id.editProfile);
    mainLayout = (MainLayout) getActivity();
  
    initBtn();
    initViewPager(viewPager2);
    initPersonalInfo(view);
    // List<View> viewList = new ArrayList<>();
    // View view1 = LayoutInflater.from(getContext()).inflate(R.layout.item_card, container, false);
    // View view2 = LayoutInflater.from(getContext()).inflate(R.layout.item_tinder_subsription, container, false);
    // viewList.add(view1);
    // viewList.add(view2);
    
    return view;
  }
  
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    
    (new Handler()).postDelayed(() -> {
      setProgressBar((double) 55 / 100);
    }, 1000);
  }
  
  private void initBtn() {
    buttonSuperLike.setOnClickListener(v -> {
      Toast.makeText(activity, "Super Like", Toast.LENGTH_SHORT).show();
      showSuperLikeDialog();
    });
    
    buttonSuperFind.setOnClickListener(v -> {
      Toast.makeText(activity, "Super Find", Toast.LENGTH_SHORT).show();
      showSuperLikeDialog();
    });
    
    editProfile.setOnClickListener(v -> {
      AndroidUtil._startActivity(getContext(), EditProfile.class);
    });
  }
  
  public void initViewPager(ViewPager2 viewPager2){
    viewPager2.bringToFront();
    viewPager2.setAdapter(new SubscriptionAdapter(viewPager2));
    viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
      @Override
      public void onPageSelected(int position) {
        super.onPageSelected(position);
        if (position == 0) {
          circle1.setForeground(getResources().getDrawable(R.drawable.btn_layout));
          circle2.setForeground(getResources().getDrawable(R.drawable.btn_unselected));
        } else if (position == 1) {
          circle2.setForeground(getResources().getDrawable(R.drawable.btn_layout));
          circle1.setForeground(getResources().getDrawable(R.drawable.btn_unselected));
        }
      }
    });
    viewPager2.setOffscreenPageLimit(3);
    viewPager2.setClipChildren(false);
    viewPager2.setClipToPadding(false);
    viewPager2.getChildAt(0).setOverScrollMode(ViewPager2.OVER_SCROLL_NEVER);
  
    CompositePageTransformer transformer = new CompositePageTransformer();
    transformer.addTransformer(new MarginPageTransformer(40));
    transformer.addTransformer((page, position) -> {
      float r = 1 - Math.abs(position);
      page.setScaleY(0.85f + r * 0.15f);
    });
    viewPager2.setPageTransformer(transformer);
  }
  
  public void initPersonalInfo(View view){
    User user = AndroidUtil.getCurUser();
    ((TextView) view.findViewById(R.id.textView7)).setText(user.getName() + " , " + user.getAge());
    // todo get image + new thread
  }
  
  private void showSuperLikeDialog() {
    final Dialog dialog = new Dialog(getContext());
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setContentView(R.layout.super_like_layout);
    
    CardView cardView1 = dialog.findViewById(R.id.card_view1);
    CardView cardView2 = dialog.findViewById(R.id.card_view2);
    CardView cardView3 = dialog.findViewById(R.id.card_view3);
    TextView textViewCoin = dialog.findViewById(R.id.textview_coin);
    ImageView cancelButton = dialog.findViewById(R.id.cancelButton);
    
    cardView1.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
//                Toast.makeText(context, "Card 1", Toast.LENGTH_SHORT).show();
        dialog.dismiss();
        checkCoin(Integer.valueOf(textViewCoin.getText().toString()), 5);
      }
    });
    
    cardView2.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
//                Toast.makeText(context, "Card 2", Toast.LENGTH_SHORT).show();
        dialog.dismiss();
        checkCoin(Integer.valueOf(textViewCoin.getText().toString()), 100);
      }
    });
    
    cardView3.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
//                Toast.makeText(context, "Card 3", Toast.LENGTH_SHORT).show();
        dialog.dismiss();
        checkCoin(Integer.valueOf(textViewCoin.getText().toString()), 500);
      }
    });
    
    
    cancelButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        dialog.dismiss();
      }
    });
    
    dialog.show();
    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
    dialog.getWindow().setGravity(Gravity.BOTTOM);
    
  }
  
  public interface PaymentDialogCallback {
    void onProceedClicked();
    
    void onCancelClicked();
    
    void onReturnClicked();
  }
  
  boolean checkCoin(int currentCoin, int price) {
    if (currentCoin >= price) {
      isProceedPayment = false;
      showProceedPaymentDialog(price, new PaymentDialogCallback() {
        @Override
        public void onProceedClicked() {
          // Handle proceed action
          // You can return a value or perform any needed action here
          Toast.makeText(activity, "Sucessfully!", Toast.LENGTH_SHORT).show();
          showSuperLikeDialog();
          isProceedPayment = true;
        }
        
        @Override
        public void onCancelClicked() {
          Toast.makeText(activity, "You cancel the payment!", Toast.LENGTH_SHORT).show();
          showSuperLikeDialog();
          // Handle cancel action
          isProceedPayment = false;
        }
        
        @Override
        public void onReturnClicked() {
          Toast.makeText(activity, "You return to payment!", Toast.LENGTH_SHORT).show();
          showSuperLikeDialog();
          // Handle cancel action
          isProceedPayment = false;
        }
      });
      return isProceedPayment;
    } else {
      isDeposit = false;
      showDepositDialog(price, new PaymentDialogCallback() {
        @Override
        public void onProceedClicked() {
          // Handle proceed action
          // You can return a value or perform any needed action here
          Toast.makeText(activity, "You proceed to deposit payment!", Toast.LENGTH_SHORT).show();
          showSuperLikeDialog();
          isDeposit = true;
        }
        
        @Override
        public void onCancelClicked() {
          Toast.makeText(activity, "You cancel deposit!", Toast.LENGTH_SHORT).show();
          showSuperLikeDialog();
          // Handle cancel action
          isDeposit = false;
        }
        
        @Override
        public void onReturnClicked() {
          Toast.makeText(activity, "You return to payment!", Toast.LENGTH_SHORT).show();
          showSuperLikeDialog();
          // Handle cancel action
          isDeposit = false;
        }
      });
      return isDeposit;
    }
    
  }
  
  private void showProceedPaymentDialog(int coin, PaymentDialogCallback callback) {
    
    final Dialog dialog = new Dialog(getContext());
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setContentView(R.layout.proceed_payment_layout);
    
    dialog.setOnCancelListener(dialogInterface -> {
      Toast.makeText(activity, "You cancel the payment!", Toast.LENGTH_SHORT).show();
      showSuperLikeDialog();
    });
    
    Button buttonProceed = dialog.findViewById(R.id.button_proceed);
    Button buttonCancel = dialog.findViewById(R.id.button_cancel);
    TextView textViewCoin = dialog.findViewById(R.id.textview_coin);
    ImageView buttonReturn = dialog.findViewById(R.id.button_return);
    
    textViewCoin.setText(String.valueOf(coin));
    
    buttonProceed.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Toast.makeText(activity, "Proceed", Toast.LENGTH_SHORT).show();
        dialog.dismiss();
//                showProceedPaymentDialog(coin,callback);
        callback.onProceedClicked(); // Invoke the callback
      }
    });
    
    buttonCancel.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Toast.makeText(activity, "Cancel", Toast.LENGTH_SHORT).show();
        dialog.dismiss();
//                showProceedPaymentDialog(coin,callback);
        callback.onCancelClicked(); // Invoke the callback
      }
    });
    
    buttonReturn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        dialog.dismiss();
        callback.onReturnClicked(); // Invoke the callback
      }
    });
    
    dialog.show();
    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
    dialog.getWindow().setGravity(Gravity.BOTTOM);
    
  }
  
  private void showDepositDialog(int coin, PaymentDialogCallback callback) {
    
    final Dialog dialog = new Dialog(getContext());
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setContentView(R.layout.deposit_layout);
    
    dialog.setOnCancelListener(dialogInterface -> {
      Toast.makeText(activity, "You cancel deposit!", Toast.LENGTH_SHORT).show();
      showSuperLikeDialog();
    });
    
    Button buttonYes = dialog.findViewById(R.id.button_yes);
    Button buttonNo = dialog.findViewById(R.id.button_no);
    TextView textViewCoin = dialog.findViewById(R.id.textview_coin);
    ImageView buttonReturn = dialog.findViewById(R.id.button_return);
    
    textViewCoin.setText(String.valueOf(coin));
    
    buttonYes.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Toast.makeText(activity, "Yes", Toast.LENGTH_SHORT).show();
        dialog.dismiss();
        callback.onProceedClicked(); // Invoke the callback
      }
    });
    
    buttonNo.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Toast.makeText(activity, "No", Toast.LENGTH_SHORT).show();
        dialog.dismiss();
        callback.onCancelClicked(); // Invoke the callback
      }
    });
    
    buttonReturn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        dialog.dismiss();
        callback.onReturnClicked(); // Invoke the callback
      }
    });
    
    dialog.show();
    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
    dialog.getWindow().setGravity(Gravity.BOTTOM);
    
  }
  
  public void setProgressBar(double percent) {
    // 0 - 73
    int progress = (int) (73 * percent);
    System.out.println(progress);
    progressIndicator.setProgress(progress, true);
  }
}


