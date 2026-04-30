package com.example.menus;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    RelativeLayout layout;
    TextView textView;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout = findViewById(R.id.mainLayout);
        textView = findViewById(R.id.textView);
        btn = findViewById(R.id.btnMenu);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // ✅ Register Context Menu
        registerForContextMenu(textView);

        // ✅ Popup Menu
        btn.setOnClickListener(view -> {
            PopupMenu popup = new PopupMenu(MainActivity.this, btn);
            popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

            popup.setOnMenuItemClickListener(item -> {
                Toast.makeText(MainActivity.this,
                        "Popup: " + item.getTitle(),
                        Toast.LENGTH_SHORT).show();
                return true;
            });

            popup.show();
        });
    }

    // ✅ OPTION MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.red){
            layout.setBackgroundColor(Color.RED);
        }else if( id == R.id.green){
            layout.setBackgroundColor(Color.GREEN);
        }else if(id == R.id.blue){
            layout.setBackgroundColor(Color.BLUE);
        }else {
            return super.onOptionsItemSelected(item);
        }

        return true;

    }

    // ✅ CONTEXT MENU
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle("Choose Color");
        menu.add(0, 1, 0, "Yellow");
        menu.add(0, 2, 0, "Gray");
        menu.add(0, 3, 0, "Cyan");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if (item.getTitle().equals("Yellow")) {
            layout.setBackgroundColor(Color.YELLOW);
        } else if (item.getTitle().equals("Gray")) {
            layout.setBackgroundColor(Color.GRAY);
        } else if (item.getTitle().equals("Cyan")) {
            layout.setBackgroundColor(Color.CYAN);
        }

        return true;
    }
}