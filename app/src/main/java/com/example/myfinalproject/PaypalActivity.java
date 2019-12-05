package com.example.myfinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;


import org.json.JSONException;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PaypalActivity extends Activity implements MyCartFragment.SendTotal {


    int total;
    String poNumber;


    List<String> poNumberList = new ArrayList<String>();
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;

    // note that these credentials will differ between live & sandbox
    // environments.
    private static final String CONFIG_CLIENT_ID = "AXSawH1K_C4ioBd8zu-Iuo8JnXndE09WicuSgj2rI4aO0mcFQNFQFihN7-YITyrF1UzasIJN48JVE3pK";

    private static final int REQUEST_CODE_PAYMENT = 1;
    private static final int REQUEST_CODE_FUTURE_PAYMENT = 2;

    TextView textView;
    TextView textTax;
    TextView textTotal;
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID);

    PayPalPayment thingToBuy;
    public String priceTotal;
    public String dTotalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paypal);

        textView = (TextView)findViewById(R.id.textView1);
        textTax = (TextView)findViewById(R.id.txtTax);
        textTax.setText("10%");
        textTotal = (TextView)findViewById(R.id.txtTotalPrice);
        Intent i = getIntent();
        priceTotal = i.getStringExtra("Total_Price");

        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();


        final Double priceSubTotal = Double.valueOf(priceTotal);

        dTotalPrice = currencyFormatter.format(priceSubTotal + (priceSubTotal * .10));

        String sPriceSubTotal = currencyFormatter.format(priceSubTotal);

        final String payPalPrice = String.valueOf(priceSubTotal + (priceSubTotal * .10));


        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);
        findViewById(R.id.order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                thingToBuy = new PayPalPayment(new BigDecimal(payPalPrice) , "USD",
                        "Rappid Shopping Total:", PayPalPayment.PAYMENT_INTENT_SALE);

                Intent intent = new Intent(PaypalActivity.this,
                        PaymentActivity.class);

                intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
                //intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuyTwo);

                startActivityForResult(intent, REQUEST_CODE_PAYMENT);
            }
        });



        textView.setText(sPriceSubTotal);
        textTotal.setText(dTotalPrice);

    }

    public void onFuturePaymentPressed(View pressed) {
        Intent intent = new Intent(PaypalActivity.this,
                PayPalFuturePaymentActivity.class);

        startActivityForResult(intent, REQUEST_CODE_FUTURE_PAYMENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm = data
                        .getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    try {
                        System.out.println(confirm.toJSONObject().toString(4));
                        System.out.println(confirm.getPayment().toJSONObject()
                                .toString(4));

                        UUID uuid = UUID.randomUUID();
                        poNumberList.add(String.valueOf(uuid));
                        MainActivity mContext = new MainActivity();
                        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext.getContext());
                        SharedPreferences.Editor spEditor = sp.edit();

                        int tt = 0;
                        if(poNumberList.size() != 0){
                            for (int i=0; i < poNumberList.size();i++){

                                Log.d("PO", String.valueOf(poNumberList.size()));

                                spEditor.putString("poNumber" + i, poNumberList.get(i));
                                tt++;
                            }
                            int i2 = 0;

                            int value = tt;

                            spEditor.putInt("poTotal", value);
                            spEditor.commit();

                        }


                        Intent myIntent = new Intent(PaypalActivity.this, MainActivity.class);
                        //myIntent.putExtra("key", value); //Optional parameters
                        PaypalActivity.this.startActivity(myIntent);
                        Toast.makeText(getApplicationContext(), "Order placed",
                                Toast.LENGTH_LONG).show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                System.out.println("The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                System.out
                        .println("An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        } else if (requestCode == REQUEST_CODE_FUTURE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PayPalAuthorization auth = data
                        .getParcelableExtra(PayPalFuturePaymentActivity.EXTRA_RESULT_AUTHORIZATION);
                if (auth != null) {
                    try {
                        Log.i("FuturePaymentExample", auth.toJSONObject()
                                .toString(4));

                        String authorization_code = auth.getAuthorizationCode();
                        Log.i("FuturePaymentExample", authorization_code);

                        sendAuthorizationToServer(auth);
                        Toast.makeText(getApplicationContext(),
                                "Future Payment code received from PayPal",
                                Toast.LENGTH_LONG).show();

                    } catch (JSONException e) {
                        Log.e("FuturePaymentExample",
                                "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("FuturePaymentExample", "The user canceled.");
            } else if (resultCode == PayPalFuturePaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("FuturePaymentExample",
                        "Probably the attempt to previously start the PayPalService had an invalid PayPalConfiguration. Please see the docs.");
            }
        }
    }

    private void sendAuthorizationToServer(PayPalAuthorization authorization) {

    }

    public void onFuturePaymentPurchasePressed(View pressed) {
        // Get the Application Correlation ID from the SDK
        String correlationId = PayPalConfiguration
                .getApplicationCorrelationId(this);

        Log.i("FuturePaymentExample", "Application Correlation ID: "
                + correlationId);

        // TODO: Send correlationId and transaction details to your server for
        // processing with
        // PayPal...
        Toast.makeText(getApplicationContext(),
                "App Correlation ID received from SDK", Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void onDestroy() {
        // Stop service when done
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    @Override
    public void sendTotalToPayPal(Double total) {
        Log.d("Send", "Paypal 77777777");
        textView.setText(String.valueOf(total));
    }


}
