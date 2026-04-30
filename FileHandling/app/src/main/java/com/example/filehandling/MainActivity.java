package com.example.filehandling;



import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    TextView textView;
    Button saveBtn, loadBtn;

    String fileName = "mydata.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView2);
        saveBtn = findViewById(R.id.button);
        loadBtn = findViewById(R.id.button2);

        // Save Data to File
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String data = editText.getText().toString();

                try {
                    FileOutputStream fout = openFileOutput(fileName, MODE_PRIVATE);
                    fout.write(data.getBytes());
                    fout.close();

                    Toast.makeText(MainActivity.this, "File Saved", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // Read Data from File
        loadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    FileInputStream fin = openFileInput(fileName);

                    int c;
                    String temp = "";

                    while ((c = fin.read()) != -1) {
                        temp = temp + Character.toString((char) c);
                    }

                    textView.setText(temp);
                    fin.close();

                    Toast.makeText(MainActivity.this, "File Loaded", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}