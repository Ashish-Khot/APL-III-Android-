package com.example.databasehandling;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db;
    EditText editId, editName;
    Button btnInsert, btnDisplay;
    TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editId = findViewById(R.id.editId);
        editName = findViewById(R.id.editName);
        btnInsert = findViewById(R.id.btnInsert);
        btnDisplay = findViewById(R.id.btnDisplay);
        txtResult = findViewById(R.id.txtResult);

        // ✅ Create / Open Database
        db = openOrCreateDatabase("StudentDB", MODE_PRIVATE, null);

        // ✅ Create Table
        db.execSQL("CREATE TABLE IF NOT EXISTS student(id INTEGER, name TEXT)");

        // ✅ Insert Data
        btnInsert.setOnClickListener(v -> {

            String id = editId.getText().toString();
            String name = editName.getText().toString();

            ContentValues values = new ContentValues();
            values.put("id", id);
            values.put("name", name);

            long result = db.insert("student", null, values);

            if (result != -1) {
                Toast.makeText(this, "Inserted Successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Insert Failed", Toast.LENGTH_SHORT).show();
            }
        });
Pr
        // ✅ Display Data
        btnDisplay.setOnClickListener(v -> {

            Cursor cursor = db.rawQuery("SELECT * FROM student", null);

            StringBuilder data = new StringBuilder();

            if (cursor.moveToFirst()) {
                do {
                    data.append("ID: ")
                            .append(cursor.getInt(0))
                            .append(" Name: ")
                            .append(cursor.getString(1))
                            .append("\n");
                } while (cursor.moveToNext());
            }

            txtResult.setText(data.toString());
            cursor.close();
        });
    }
}