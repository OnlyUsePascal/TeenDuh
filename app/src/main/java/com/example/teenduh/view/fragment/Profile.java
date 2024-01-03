package com.example.teenduh.view.fragment;

import android.app.Dialog;
import android.content.Context;
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
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.teenduh.R;
import com.example.teenduh.model.Image;
import com.example.teenduh.util.AndroidUtil;
import com.example.teenduh.view.adapter.ImageAdapter;

import java.util.ArrayList;
import java.util.List;

public class Profile extends Fragment {
    Context context;
    Button buttonSuperLike;
    Button buttonSuperFind;
    boolean isProceedPayment;
    boolean isDeposit;
    ViewPager2 viewPager2;
    List<Image> imageList;
    private Handler sliderHandler = new Handler();

    public Profile(){
        context = AndroidUtil.getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        buttonSuperLike = view.findViewById(R.id.button10);
        buttonSuperFind = view.findViewById(R.id.button11);

        buttonSuperLike.setOnClickListener(v -> {
            Toast.makeText(context, "Super Like", Toast.LENGTH_SHORT).show();
            showSuperLikeDialog();
        });

        buttonSuperFind.setOnClickListener(v -> {
            Toast.makeText(context, "Super Find", Toast.LENGTH_SHORT).show();
            showSuperLikeDialog();
        });

        viewPager2 = view.findViewById(R.id.viewPager2);
        imageList = new ArrayList<>();

        Image image1 = new Image(R.drawable.ronaldo, "Cristiano Ronaldo");
        Image image2 = new Image(R.drawable.park_seo, "Park Seo Joon");
        Image image3 = new Image(R.drawable.modric, "Luka Modric");

        imageList.add(image1);
        imageList.add(image2);
        imageList.add(image3);



        viewPager2.setAdapter(new ImageAdapter(imageList, viewPager2));
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
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback(){
            @Override
            public void onPageSelected(int position){
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(runnable);
                sliderHandler.postDelayed(runnable, 3000);
            }
        });


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };

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
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }

    public interface PaymentDialogCallback {
        void onProceedClicked();
        void onCancelClicked();
        void onReturnClicked();
    }

    boolean checkCoin(int currentCoin, int price){
        if(currentCoin >= price) {
            isProceedPayment = false;
            showProceedPaymentDialog(price,new PaymentDialogCallback() {
                @Override
                public void onProceedClicked() {
                    // Handle proceed action
                    // You can return a value or perform any needed action here
                    Toast.makeText(context, "Sucessfully!", Toast.LENGTH_SHORT).show();
                    showSuperLikeDialog();
                    isProceedPayment = true;
                }

                @Override
                public void onCancelClicked() {
                    Toast.makeText(context, "You cancel the payment!", Toast.LENGTH_SHORT).show();
                    showSuperLikeDialog();
                    // Handle cancel action
                    isProceedPayment = false;
                }

                @Override
                public void onReturnClicked() {
                    Toast.makeText(context, "You return to payment!", Toast.LENGTH_SHORT).show();
                    showSuperLikeDialog();
                    // Handle cancel action
                    isProceedPayment = false;
                }
            });
            return isProceedPayment;
        } else {
            isDeposit = false;
            showDepositDialog(price,new PaymentDialogCallback() {
                @Override
                public void onProceedClicked() {
                    // Handle proceed action
                    // You can return a value or perform any needed action here
                    Toast.makeText(context, "You proceed to deposit payment!", Toast.LENGTH_SHORT).show();
                    showSuperLikeDialog();
                    isDeposit = true;
                }

                @Override
                public void onCancelClicked() {
                    Toast.makeText(context, "You cancel deposit!", Toast.LENGTH_SHORT).show();
                    showSuperLikeDialog();
                    // Handle cancel action
                    isDeposit = false;
                }
                @Override
                public void onReturnClicked() {
                    Toast.makeText(context, "You return to payment!", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(context, "You cancel the payment!", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(context, "Proceed", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
//                showProceedPaymentDialog(coin,callback);
                callback.onProceedClicked(); // Invoke the callback
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Cancel", Toast.LENGTH_SHORT).show();
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
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }

    private void showDepositDialog(int coin, PaymentDialogCallback callback) {

        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.deposit_layout);

        dialog.setOnCancelListener(dialogInterface -> {
            Toast.makeText(context, "You cancel deposit!", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(context, "Yes", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                callback.onProceedClicked(); // Invoke the callback
            }
        });

        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "No", Toast.LENGTH_SHORT).show();
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
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }

}


