package com.example.teenduh.view.fragment.signup;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.teenduh.R;
import com.example.teenduh.view.activity.SignUpPage;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;


public class DemandFragment extends Fragment {
    View view;
    SignUpPage signUpPage;
    MaterialCardView longTermCard, longTermToShortCard, shortTermToLongCard;
    MaterialCardView shorTermCard, newFriendCard, thinkingCard;
    List<MaterialCardView> cardList = new ArrayList<>();
    TextView nextButton;
    private MaterialCardView selectedCard = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_demand, container, false);
        signUpPage = (SignUpPage) getActivity();
        longTermCard = view.findViewById(R.id.longTermRelationship);
        longTermToShortCard = view.findViewById(R.id.longTermToShort);
        shortTermToLongCard = view.findViewById(R.id.shortTermOpenToLong);
        shorTermCard = view.findViewById(R.id.shortTermFun);
        newFriendCard = view.findViewById(R.id.newFriends);
        thinkingCard = view.findViewById(R.id.stillFigureOut);
        cardList.add(longTermCard);
        cardList.add(longTermToShortCard);
        cardList.add(shortTermToLongCard);
        cardList.add(shorTermCard);
        cardList.add(newFriendCard);
        cardList.add(thinkingCard);
        nextButton = view.findViewById(R.id.nextButton);
       for(MaterialCardView card: cardList) {
           card.setOnClickListener(v -> {
               if(v!=null) {
                   nextButton.setBackground(getResources().getDrawable(R.drawable.btn_layout));
                   nextButton.setTextColor(getResources().getColor(R.color.white));
                   nextButton.setEnabled(true);
                   selectedCard = (MaterialCardView) v;
                   selectedCard.setStrokeColor(getResources().getColor(R.color.red));
                   selectedCard.setStrokeWidth(10);
                   for(MaterialCardView card1: cardList) {
                       if(card1!=selectedCard) {
                           card1.setStrokeWidth(0);
                       }
                   }
               }
           });
       }
       nextButton.setOnClickListener(v -> {
           if(selectedCard!=null) {
               LinearLayout linearLayout = (LinearLayout) selectedCard.getChildAt(0);
                TextView textView = (TextView) linearLayout.getChildAt(1);
                String demand = textView.getText().toString();
                //Store data

               //Replace fragment
               signUpPage.currentProgress += 10;
               signUpPage.progressBar.setProgress(signUpPage.currentProgress);
               signUpPage.replaceFragment(new LifeStyleFragment());
           }
         });
        return view;
    }
}