package com.wat.tabularasa20.activities;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_account);

        Intent incoming_intent = getIntent();
        final String login_result = incoming_intent.getStringExtra("result");

        TextView homeTV = findViewById(R.id.homeAccountTextViewWelcome);
        final Button addBook = findViewById(R.id.homeAccountButtonAddNewBook);
        final Button sendMessage = findViewById(R.id.homeAccountButtonMyMessages);
        final Button logout = findViewById(R.id.homeAccountButtonLogout);
        final Button close = findViewById(R.id.homeAccountButtonClose);
        final Button searchBook = findViewById(R.id.homeAccountButtonSearchForBook);

        // Pobranie informacji o kliencie
        Downloader downloader = new Downloader();
        downloader.setOnResultListener(result -> {
            result = result.substring(1, result.length() - 1);
            //Toast.makeText(HomeActivity.this, result, Toast.LENGTH_LONG).show();
            JsonObject jsonObject = JsonParser.parseString(result).getAsJsonObject();
            homeTV.setText(getString(R.string.hello_msg_params,jsonObject.get("Imie").getAsString(), jsonObject.get("Nazwisko").getAsString()));
        });
        downloader.execute(Constants.ACCOUNT_GET_URL + String.format("?id_klienta=%s", login_result));

        // Przycisk wyloguj
        logout.setOnClickListener(v -> {
            Preferences.saveCredentials(HomeActivity.this, new Preferences.LoginCredentials("", ""));
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        // Przycisk dodaj książkę
        addBook.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, AddBookActivity.class);
            startActivity(intent);
        });

        // Przycisk czatu
        sendMessage.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ChatActivity.class);
            startActivity(intent);
        });

        // Przycisk szukaj książki
        searchBook.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ProductListActivity.class);
            startActivity(intent);
        });

        // przycisk zamknij
        close.setOnClickListener(v -> finishAffinity());
    }
}
