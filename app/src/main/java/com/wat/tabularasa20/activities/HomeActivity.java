package com.wat.tabularasa20.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wat.tabularasa20.MainActivity;
import com.wat.tabularasa20.R;
import com.wat.tabularasa20.utilities.Preferences;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TextView homeTV = findViewById(R.id.textView2);
        final Button back = findViewById(R.id.back);
        back.setOnClickListener(v -> {
            Preferences.saveCredentials(HomeActivity.this, new Preferences.LoginCredentials("", ""));
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String result = intent.getStringExtra("result");

        // Tak, to nie ma sensu, to tylko test JSON
        JsonObject jsonObject = JsonParser.parseString("{\"result\": " + result + "}").getAsJsonObject();

        homeTV.setText("Witaj " + name + "\nserwer odpowiedział: " +  jsonObject.get("result").getAsString());
    }
}
