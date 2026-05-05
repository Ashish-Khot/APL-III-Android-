package com.example.widgets;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText editTextName;
    Button buttonSubmit;
    CheckBox checkBoxAgree;
    RadioGroup radioGroupGender;
    Switch switchNotify;
    SeekBar seekBarVolume;
    TextView textSeekBarValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find views
        editTextName = findViewById(R.id.editTextName);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        checkBoxAgree = findViewById(R.id.checkBoxAgree);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        switchNotify = findViewById(R.id.switchNotify);
        seekBarVolume = findViewById(R.id.seekBarVolume);
        textSeekBarValue = findViewById(R.id.textSeekBarValue);

        // Button click
        buttonSubmit.setOnClickListener(v -> {
            String name = editTextName.getText().toString();
            Toast.makeText(MainActivity.this, "Hello " + name, Toast.LENGTH_SHORT).show();
        });

        // CheckBox change
        checkBoxAgree.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Toast.makeText(MainActivity.this,
                    "Checkbox is " + (isChecked ? "checked" : "unchecked"), Toast.LENGTH_SHORT).show();
        });

        // RadioGroup change
        radioGroupGender.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton selected = findViewById(checkedId);
            Toast.makeText(MainActivity.this,
                    "Selected Gender: " + selected.getText(), Toast.LENGTH_SHORT).show();
        });

        // Switch change
        switchNotify.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Toast.makeText(MainActivity.this,
                    "Notifications " + (isChecked ? "Enabled" : "Disabled"), Toast.LENGTH_SHORT).show();
        });

        // SeekBar change
        seekBarVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textSeekBarValue.setText("Volume: " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Optional
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(MainActivity.this,
                        "Volume set to " + seekBar.getProgress(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}