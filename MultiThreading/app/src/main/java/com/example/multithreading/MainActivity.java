package com.example.multithreading;



import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    Button startBtn;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        startBtn = findViewById(R.id.startBtn);
        textView = findViewById(R.id.textView);

        startBtn.setOnClickListener(v -> {

            Thread thread = new Thread(() -> {

                for (int i = 0; i <= 100; i++) {

                    int progress = i;

                    runOnUiThread(() -> {
                        progressBar.setProgress(progress);
                        textView.setText("Downloading: " + progress + "%");
                    });

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                runOnUiThread(() -> textView.setText("Download Complete"));

            });

            thread.start();
        });
    }
}