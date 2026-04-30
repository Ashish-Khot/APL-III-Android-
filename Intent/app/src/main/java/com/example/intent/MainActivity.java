package com.example.intent;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnCall, btnSms,btnNetwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//     Binding the buttons
        btnCall = findViewById(R.id.btnCall);
        btnSms = findViewById(R.id.btnSms);
        btnNetwork = findViewById(R.id.btnNetwork);

//     1)  Call Button Click
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:9322994021"));

                startActivity(callIntent);
            }
        });

//         2) SMS Button Click
        btnSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Implicit intent to send SMS
                Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
                smsIntent.setData(Uri.parse("smsto:9322994021"));
                smsIntent.putExtra("sms_body", "Hello! Have a Good Day ");

                startActivity(smsIntent);
            }
        });

//        3) Network Details
        btnNetwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Implicit intent to open network settings
                Intent intent = new Intent(android.provider.Settings.ACTION_NETWORK_OPERATOR_SETTINGS);
                startActivity(intent);
            }
        });

    }
}
