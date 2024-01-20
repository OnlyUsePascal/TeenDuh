package com.example.teenduh.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.teenduh.R;
import com.stripe.android.model.Card;

import java.util.Arrays;
import java.util.List;

public class BillActivity extends AppCompatActivity {

    private CardView cardView1;
    private CardView cardView2;
    private CardView cardView3;
    private RelativeLayout relativeLayout1;
    private RelativeLayout relativeLayout2;
    private RelativeLayout relativeLayout3;
    private Button btnPay;
    private ImageView btnBack;
    private int price = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        cardView1 = findViewById(R.id.card_view1);
        cardView2 = findViewById(R.id.card_view2);
        cardView3 = findViewById(R.id.card_view3);
        relativeLayout1 = findViewById(R.id.relativeLayout1);
        relativeLayout2 = findViewById(R.id.relativeLayout2);
        relativeLayout3 = findViewById(R.id.relativeLayout3);
        btnPay = findViewById(R.id.process_payment);
        btnBack = findViewById(R.id.back_button);

        Intent intent = getIntent();

        if (intent != null) {
            price = intent.getIntExtra("price", 0);
        }

        if (price == 5) {
            highlightSelectedCard(cardView1);
        } else if (price == 10) {
            highlightSelectedCard(cardView2);
        } else if (price == 50) {
            highlightSelectedCard(cardView3);
        }

        View.OnClickListener cardClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highlightSelectedCard(v);
            }
        };

        cardView1.setOnClickListener(cardClickListener);
        cardView2.setOnClickListener(cardClickListener);
        cardView3.setOnClickListener(cardClickListener);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BillActivity.this, "You select " + price, Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    private void highlightSelectedCard(View selectedCard) {
        List<CardView> cardViews = Arrays.asList(cardView1, cardView2, cardView3);
        List<RelativeLayout> relativeLayouts = Arrays.asList(relativeLayout1, relativeLayout2, relativeLayout3);

        for (int i = 0; i < cardViews.size(); i++) {
            CardView card = cardViews.get(i);
            RelativeLayout relativeLayout = relativeLayouts.get(i);
            if (card == selectedCard) {
//                // Highlight the selected card
                card.setAlpha(1.0f); // Full opacity
                card.setCardBackgroundColor(getResources().getColor(R.color.card_selected));
                relativeLayout.setBackgroundColor(getResources().getColor(R.color.card_selected));
                card.setCardElevation(8);

            } else {
//                // Dim other cards
                card.setAlpha(0.5f); // Half opacity or any value that indicates it's not selected
                card.setCardBackgroundColor(getResources().getColor(R.color.card_unselected));
                relativeLayout.setBackgroundColor(getResources().getColor(R.color.seed));
                card.setCardElevation(2);
            }
        }
    }

}