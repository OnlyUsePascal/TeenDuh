package com.example.teenduh.view.activity.payment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.teenduh.R;
import com.braintreepayments.api.DropInClient;
import com.braintreepayments.api.DropInListener;
import com.braintreepayments.api.DropInRequest;
import com.braintreepayments.api.DropInResult;
import com.braintreepayments.api.PayPalVaultRequest;
import com.braintreepayments.api.UserCanceledException;


public class DropInClientActivity extends AppCompatActivity
    implements DropInListener
{
  private DropInClient dropInClient;
  private PayPalVaultRequest payPalRequest;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_drop_in_client);

    dropInClient = new DropInClient(this,
        getResources().getString(R.string.paypal_tokenization_key));

    // Make sure to register listener in onCreate
    dropInClient.setListener(this);
  }

  public void launchDropIn(View view) {
    DropInRequest dropInRequest = new DropInRequest();
    dropInClient.launchDropIn(dropInRequest);

  }

  @Override
  public void onDropInSuccess(@NonNull DropInResult dropInResult) {
    String paymentMethodNonce = dropInResult.getPaymentMethodNonce().getString();
    System.out.println("drop in success:" + paymentMethodNonce);
    // use the result to update your UI and send the payment method nonce to your server
  }

  @Override
  public void onDropInFailure(@NonNull Exception error) {
    error.printStackTrace();
  }
}