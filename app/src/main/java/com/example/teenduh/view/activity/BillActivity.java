package com.example.teenduh.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.teenduh.R;
import com.example.teenduh._util.AndroidUtil;
import com.example.teenduh._util.FirebaseUtil;
import com.example.teenduh.model.User;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BillActivity extends AppCompatActivity {
    private ImageView btnBack;
    private int price = 0;
    Spinner amountField;
    double totalPrice = -1;
    TextView totalField;
    private PaymentSheet paymentSheet;
    private String paymentIntentSecret;
    PaymentSheet.CustomerConfiguration customerConfiguration;
    String TAG = "Payment";
    boolean forSuperLike = true;
    double unitPrice = 4.99;
    public static int REQ_SUPERLIKE = 1001;
    public static int REQ_PREMIUM = 1002;
    TextView unitField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        btnBack = findViewById(R.id.back_button);
        amountField = findViewById(R.id.likeAmount1);
        totalField = findViewById(R.id.textView111);
        unitField = findViewById(R.id.textView11);

        int amountIdx = 0;
        Intent intent = getIntent();
        if (intent != null) {
            amountIdx = intent.getIntExtra("amount", 0);
            forSuperLike = intent.getBooleanExtra("forSuperLike", true);
        }

        if (forSuperLike) {
            // SUPER LIKE
            unitPrice = 4.99;
            List<String> options = Arrays.asList(new String[]{"5", "10", "50"});
            ArrayAdapter<String> adapter =
                    new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, options);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            amountField.setAdapter(adapter);

            amountField.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    getPriceSuperLike();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            amountField.setSelection(amountIdx);
            getPriceSuperLike();

        } else {
            // PREMIUM
            unitPrice = intent.getDoubleExtra("price", 14.99);
            List<String> options = Arrays.asList(new String[]{"1 Months", "3 Months", "5 Months"});
            ArrayAdapter<String> adapter =
                    new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, options);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            amountField.setAdapter(adapter);

            ViewGroup.LayoutParams params = amountField.getLayoutParams();
            params.width += 150;
            amountField.setLayoutParams(params);

            amountField.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    getPricePremium();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            amountField.setSelection(0);
            getPricePremium();
        }

        unitField.setText("$" + String.format("%.2f", unitPrice));
        // stripe
        paymentSheet = new PaymentSheet(this, this::onPaymentSheetResult);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void getPriceSuperLike() {
        int amount = Integer.parseInt(amountField.getSelectedItem().toString());
        totalPrice = (double) amount * unitPrice;
        totalField.setText("$" + String.format("%.2f", totalPrice));
    }

    public void getPricePremium() {
        String month = amountField.getSelectedItem().toString();
        int amount = Integer.parseInt(month.split(" ")[0]);
        totalPrice = (double) amount * unitPrice;
        totalField.setText("$" + String.format("%.2f", totalPrice));
    }

    public void proceedPayment(View view) {
            String url = "https://teenduh-stripe.onrender.com/payment-sheet";
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(120, TimeUnit.SECONDS) // Connection timeout
                    .writeTimeout(120, TimeUnit.SECONDS)   // Write timeout
                    .readTimeout(120, TimeUnit.SECONDS)    // Read timeout
                    .build();
            RequestBody body1 = new FormBody.Builder()
                    .addEncoded("price", Double.toString(totalPrice * 100))
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .post(body1)
                    .addHeader("ContentType", "application/x-www-form-urlencoded")
                    .addHeader("Connection", "close")
                    .build();

        try {
            client.newCall(request).
                    enqueue(new Callback() {
                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            System.out.println("> request success!");
                            initStripeConfig(response.body().string());
                        }

                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            System.out.println("> request success but failed!");
                            e.printStackTrace();
                        }
                    });
        } catch (Exception err) {
            System.out.println("> request failed");
            err.printStackTrace();
        }
    }

    public void initStripeConfig(String response) {
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

    public void initStripeSheet() {
        PaymentSheet.Configuration configuration1 =
                new PaymentSheet.Configuration.Builder("TeenDuh Inc")
                        .customer(customerConfiguration)
                        .allowsDelayedPaymentMethods(true)
                        .build();
        paymentSheet.presentWithPaymentIntent(paymentIntentSecret, configuration1);
    }

    public void onPaymentSheetResult(final PaymentSheetResult paymentSheetResult) {
        if (paymentSheetResult instanceof PaymentSheetResult.Canceled) {
            Log.d(TAG, "Canceled");
        } else if (paymentSheetResult instanceof PaymentSheetResult.Failed) {
            Log.e(TAG, "Got error: ", ((PaymentSheetResult.Failed) paymentSheetResult).getError());
        } else if (paymentSheetResult instanceof PaymentSheetResult.Completed) {
            // Display
            // for example, an order confirmation screen
            Log.d(TAG, "Completed");
            AndroidUtil.makeToast(this, "Payment Success!");

            User user = AndroidUtil.getCurUser();
            AndroidUtil.getCurUser().setVip(true);
            HashMap<String, Object> data = new HashMap<>();
            data.put("vipLevel", 1);
            FirebaseUtil.getFirestore().collection("users")
                    .document(user.getId())
                    .update(data);
            finish();
        }
    }
}