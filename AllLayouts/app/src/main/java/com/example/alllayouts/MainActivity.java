package com.example.alllayouts;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ListView listView;

    String[] layouts = {
            "LinearLayout",
            "TableLayout",
            "ConstraintLayout",
            "FrameLayout"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listLayouts);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                layouts
        );

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    // LinearLayout
                    Intent intent = new Intent(MainActivity.this, LinearLayoutActivity.class);
                    startActivity(intent);
                }
                else if (position == 1) {
                    // TableLayout
                    Intent intent = new Intent(MainActivity.this, TableLayoutActivity.class);
                    startActivity(intent);
                }
                else if (position == 2) {
                    startActivity(new Intent(MainActivity.this, ConstraintLayoutActivity.class));
                }
                else if (position == 3) {
                    startActivity(new Intent(MainActivity.this, FrameLayoutActivity.class));
                }
            }
        });
    }
}
