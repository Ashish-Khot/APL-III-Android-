package com.example.internalstoragedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private EditText editTextData;
    private Button btnSave, btnRead;
    private TextView textViewData;
    private final String FILE_NAME = "myfile.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextData = findViewById(R.id.editTextData);
        btnSave = findViewById(R.id.btnSave);
        btnRead = findViewById(R.id.btnRead);
        textViewData = findViewById(R.id.textViewData);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = editTextData.getText().toString();
                if (!data.isEmpty()) {
                    saveToInternalStorage(data);
                } else {
                    Toast.makeText(MainActivity.this, "Enter some text first", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readFromInternalStorage();
            }
        });
    }

    private void saveToInternalStorage(String data) {
        try (FileOutputStream fos = openFileOutput(FILE_NAME, MODE_PRIVATE)) {
            fos.write(data.getBytes());
            Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show();
            editTextData.setText("");
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error saving data", Toast.LENGTH_SHORT).show();
        }
    }

    private void readFromInternalStorage() {
        try (FileInputStream fis = openFileInput(FILE_NAME)) {
            int size = fis.available();
            byte[] buffer = new byte[size];
            fis.read(buffer);
            String data = new String(buffer);
            textViewData.setText(data);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error reading data", Toast.LENGTH_SHORT).show();
        }
    }
}