package com.wat.tabularasa20.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wat.tabularasa20.R;
import com.wat.tabularasa20.data.Constants;
import com.wat.tabularasa20.data.ProductListAdapter;
import com.wat.tabularasa20.data.ProductListDescription;
import com.wat.tabularasa20.utilities.Downloader;
import com.wat.tabularasa20.utilities.Preferences;

/**
 * Aktywność pisania nowej wiadomości
 */
public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages_new);

        Intent inp = getIntent();
        int id = inp.getIntExtra("user_id", -1);

        ImageButton back = findViewById(R.id.messagesNewButtonBack);
        TextView user = findViewById(R.id.messagesNewTextViewUsername);

        // Pobranie informacji o wszystkich kasiążkach
        Downloader downloader = new Downloader();
        downloader.setOnResultListener(result -> {
            // Utworzenie obiektu JSON z danych pobranych z internetu
            assert result != null;
            //Toast.makeText(ProductListActivity.this, resultProducts, Toast.LENGTH_LONG).show();
            JsonObject productsJsonObject = JsonParser.parseString(result).getAsJsonObject();
            String name = productsJsonObject.get("Imie").getAsString();
            user.setText(getString(R.string.chat_with, name));
        });

        if (id != -1) {
            downloader.execute(Constants.ACCOUNT_GET_URL + String.format("?id_klienta=%s", id));
        } else {
            Toast.makeText(this, "Niepoprawny identyfikator użytkownika", Toast.LENGTH_SHORT).show();
        }


        back.setOnClickListener(v -> finish());
    }
}
