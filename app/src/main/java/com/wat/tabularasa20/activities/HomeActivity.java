package com.wat.tabularasa20.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wat.tabularasa20.MainActivity;
import com.wat.tabularasa20.R;
import com.wat.tabularasa20.data.Constants;
import com.wat.tabularasa20.utilities.Downloader;
import com.wat.tabularasa20.utilities.Preferences;

public class HomeActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent incoming_intent = getIntent();
        final String login_result = incoming_intent.getStringExtra("result");

        TextView homeTV = findViewById(R.id.homeTextViewWelcome);

        // Przycisk wyloguj
        final Button back = findViewById(R.id.homeButtonBack);
        back.setOnClickListener(v -> {
            // TODO zmienić cofnij na wyloguj
            Preferences.saveCredentials(HomeActivity.this, new Preferences.LoginCredentials("", ""));
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        // Przycisk dodaj książkę
        final Button addBook = findViewById(R.id.homeButtonAddNew);
        addBook.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, AddBookActivity.class);
            startActivity(intent);
        });

        // Przycisk czatu
        final Button sendMessage = findViewById(R.id.homeButtonChat);
        sendMessage.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ChatActivity.class);
            startActivity(intent);
        });

        // Pobranie informacji o kliencie
        Downloader downloader = new Downloader();
        downloader.setOnResultListener(result -> {
            result = result.substring(1, result.length() - 1);
            //Toast.makeText(HomeActivity.this, result, Toast.LENGTH_LONG).show();
            JsonObject jsonObject = JsonParser.parseString(result).getAsJsonObject();
            homeTV.setText("Witaj " + jsonObject.get("Imie").getAsString() + " " + jsonObject.get("Nazwisko").getAsString());
        });
        downloader.execute(Constants.ACCOUNT_GET_URL + String.format("?id_klienta=%s", login_result));
    }
}
