package com.example.checkbox;
  // keep your package name same

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etName, etEmail;
    CheckBox cbJava, cbAndroid, cbWeb;
    RadioGroup radioGroupGender;
    Spinner spinnerCountry;
    Switch switchNotification;
    ToggleButton toggleStatus;
    RatingBar ratingBar;
    SeekBar seekBar;
    TextView tvSeekValue;
    Button btnDate, btnTime, btnSubmit;

    String selectedDate = "", selectedTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        cbJava = findViewById(R.id.cbJava);
        cbAndroid = findViewById(R.id.cbAndroid);
        cbWeb = findViewById(R.id.cbWeb);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        spinnerCountry = findViewById(R.id.spinnerCountry);
        switchNotification = findViewById(R.id.switchNotification);
        toggleStatus = findViewById(R.id.toggleStatus);
        ratingBar = findViewById(R.id.ratingBar);
        seekBar = findViewById(R.id.seekBar);
        tvSeekValue = findViewById(R.id.tvSeekValue);
        btnDate = findViewById(R.id.btnDate);
        btnTime = findViewById(R.id.btnTime);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Spinner Data
        String[] countries = {"India", "USA", "UK", "Canada"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_dropdown_item, countries);
        spinnerCountry.setAdapter(adapter);

        // SeekBar Listener
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvSeekValue.setText("Experience: " + progress + " Years");
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Date Picker
        btnDate.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            DatePickerDialog dp = new DatePickerDialog(this,
                    (view, year, month, day) ->
                            selectedDate = day + "/" + (month + 1) + "/" + year,
                    c.get(Calendar.YEAR),
                    c.get(Calendar.MONTH),
                    c.get(Calendar.DAY_OF_MONTH));
            dp.show();
        });

        // Time Picker
        btnTime.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            TimePickerDialog tp = new TimePickerDialog(this,
                    (view, hour, minute) ->
                            selectedTime = hour + ":" + minute,
                    c.get(Calendar.HOUR_OF_DAY),
                    c.get(Calendar.MINUTE),
                    true);
            tp.show();
        });

        // Submit
        btnSubmit.setOnClickListener(v -> {

            String name = etName.getText().toString();
            String email = etEmail.getText().toString();

            String skills = "";
            if (cbJava.isChecked()) skills += "Java ";
            if (cbAndroid.isChecked()) skills += "Android ";
            if (cbWeb.isChecked()) skills += "Web ";

            int selectedId = radioGroupGender.getCheckedRadioButtonId();
            RadioButton rb = findViewById(selectedId);
            String gender = (rb != null) ? rb.getText().toString() : "";

            String country = spinnerCountry.getSelectedItem().toString();
            boolean notification = switchNotification.isChecked();
            boolean status = toggleStatus.isChecked();
            float rating = ratingBar.getRating();
            int experience = seekBar.getProgress();

            String result = "Name: " + name +
                    "\nEmail: " + email +
                    "\nGender: " + gender +
                    "\nSkills: " + skills +
                    "\nCountry: " + country +
                    "\nNotifications: " + notification +
                    "\nStatus: " + status +
                    "\nRating: " + rating +
                    "\nExperience: " + experience +
                    "\nDate: " + selectedDate +
                    "\nTime: " + selectedTime;

            Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        });
    }
}
