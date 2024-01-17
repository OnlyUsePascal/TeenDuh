package com.example.teenduh.view.activity.payment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.teenduh.R;
import com.braintreepayments.api.DropInClient;
import com.braintreepayments.api.DropInListener;
import com.braintreepayments.api.DropInRequest;
import com.braintreepayments.api.DropInResult;
import com.braintreepayments.api.PayPalVaultRequest;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class DropInClientActivity extends AppCompatActivity implements DropInListener {
  // paypal
  private DropInClient dropInClient;
  private PayPalVaultRequest payPalRequest;
  // stripe
  private PaymentSheet paymentSheet;
  private String paymentIntentSecret;
  PaymentSheet.CustomerConfiguration customerConfiguration;
  private final String TAG = "CheckoutActivity";
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_drop_in_client);

    // paypal
    dropInClient = new DropInClient(this,
        getResources().getString(R.string.paypal_tokenization_key));
    dropInClient.setListener(this);
    
    // stripe
    paymentSheet = new PaymentSheet(this, this::onPaymentSheetResult);
  }
 
  public void launchStripe(View view){
    initStripeApi();
  }
  
  public void initStripeApi(){
    try {
      String url = "http://localhost:3000/payment-sheet";
      OkHttpClient client = new OkHttpClient();
      MediaType mediaType = MediaType.get("application/json; charset=utf-8");
      JSONObject bodyContent = new JSONObject();
      bodyContent.put("price", 12);
      bodyContent.put("name", "premium gold");
      RequestBody body = RequestBody.create(bodyContent.toString(), mediaType);
      Request request = new Request.Builder()
                            .url(url)
                            .post(body)
                            .build();
    
      client.newCall(request).
          enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
              initStripeConfig(response.body().string());
            }
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
              e.printStackTrace();
            }
          });
    } catch (Exception err) {
      err.printStackTrace();
    }
  }
  
  public void initStripeConfig(String response){
    // System.out.println(response);
    try {
      JSONObject responseJson = new JSONObject(response);
      customerConfiguration = new PaymentSheet.CustomerConfiguration(
          responseJson.getString("customer"),
          responseJson.getString("ephemeralKey")
      );
      paymentIntentSecret = responseJson.getString("paymentIntent");
      PaymentConfiguration.init(this, responseJson.getString("publishableKey"));
      initStripeSheet();
    } catch (Exception err) {
      err.printStackTrace();
    }
  }
  
  public void initStripeSheet(){
    PaymentSheet.Configuration configuration1 =
        new PaymentSheet.Configuration.Builder("TeenDuh Inc")
            .customer(customerConfiguration)
            .allowsDelayedPaymentMethods(true)
            .build();
    paymentSheet.presentWithPaymentIntent(paymentIntentSecret, configuration1);
  }
  
  public void onPaymentSheetResult(final PaymentSheetResult paymentSheetResult){
    if (paymentSheetResult instanceof PaymentSheetResult.Canceled) {
      Log.d(TAG, "Canceled");
    } else if (paymentSheetResult instanceof PaymentSheetResult.Failed) {
      Log.e(TAG, "Got error: ", ((PaymentSheetResult.Failed) paymentSheetResult).getError());
    } else if (paymentSheetResult instanceof PaymentSheetResult.Completed) {
      // Display for example, an order confirmation screen
      Log.d(TAG, "Completed");
    }
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
  
  public void launchPaypal(View view) {
    DropInRequest dropInRequest = new DropInRequest();
    dropInClient.launchDropIn(dropInRequest);

  }
  
}