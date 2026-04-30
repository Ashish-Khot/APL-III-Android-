package com.example.inputcontrols;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etName;
    CheckBox cbReading, cbTraveling, cbGaming;
    RadioGroup radioGroupGender;
    Switch switchTerms;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Linking UI Components
        etName = findViewById(R.id.etName);
        cbReading = findViewById(R.id.cbReading);
        cbTraveling = findViewById(R.id.cbTraveling);
        cbGaming = findViewById(R.id.cbGaming);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        switchTerms = findViewById(R.id.switchTerms);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = etName.getText().toString();

                // CheckBox values
                String hobbies = "";
                if (cbReading.isChecked()) hobbies += "Reading ";
                if (cbTraveling.isChecked()) hobbies += "Traveling ";
                if (cbGaming.isChecked()) hobbies += "Gaming ";

                // RadioButton value
                int selectedId = radioGroupGender.getCheckedRadioButtonId();
                RadioButton selectedRadio = findViewById(selectedId);
                String gender = "";
                if (selectedRadio != null) {
                    gender = selectedRadio.getText().toString();
                }

                // Switch value
                boolean isAccepted = switchTerms.isChecked();

                if (!isAccepted) {
                    Toast.makeText(MainActivity.this,
                            "Please accept Terms & Conditions",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                String result = "Name: " + name +
                        "\nGender: " + gender +
                        "\nHobbies: " + hobbies;

                Toast.makeText(MainActivity.this,
                        result,
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
