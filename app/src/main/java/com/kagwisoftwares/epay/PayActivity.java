package com.kagwisoftwares.epay;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.kagwisoftwares.epay.STK.AccessToken;
import com.kagwisoftwares.epay.STK.CustomerPayBillOnline;

public class PayActivity extends AppCompatActivity {

    private EditText amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payfare);

        Toolbar toolbar = findViewById(R.id.toolbar_pay);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Pay");

        AccessToken getPush = new AccessToken(PayActivity.this);
        Button payFare = findViewById(R.id.payFare);
        payFare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amount = findViewById(R.id.amount_pay);
                Log.d("AMOUNT: ", amount.getText().toString());
                getPush.requestToken(amount.getText().toString());
            }
        });
    }
}
