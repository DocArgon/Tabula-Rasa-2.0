package com.wat.tabularasa20.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
public class ChatNewActivity extends AppCompatActivity {

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages_new);

        Intent inp = getIntent();
        int owner_id = inp.getIntExtra("owner_id", ProductListDescription.DEFAULT_OWNER_ID);
        int book_id = inp.getIntExtra("book_id", ProductListDescription.DEFAULT_PRODUCT_ID);

        ImageButton back = findViewById(R.id.messagesNewButtonBack);
        TextView user = findViewById(R.id.messagesNewTextViewUsername);
        EditText message = findViewById(R.id.messagesNewEditTextMessage);
        Button send = findViewById(R.id.messagesNewButtonSendForm);

        // Pobranie informacji o użytkowniku
        Downloader userDownloader = new Downloader();
        userDownloader.setOnResultListener(result -> {
            // Utworzenie obiektu JSON z danych pobranych z internetu
            assert result != null;
            JsonObject productsJsonObject = JsonParser.parseString(result).getAsJsonObject();
            String name = productsJsonObject.get("Imie").getAsString();
            user.setText(getString(R.string.chat_with, name));
        });

        Downloader bookDownloader = new Downloader();
        bookDownloader.setOnResultListener(result -> {
            // Utworzenie obiektu JSON z danych pobranych z internetu
            assert result != null;
            JsonArray productsJsonArray = JsonParser.parseString(result).getAsJsonArray();
            String name = productsJsonArray.get(0).getAsJsonObject().get("tytul").getAsString();

            // TODO przenieść do strings.xml
            message.setText("Witam, interesuje mnie wymiana za pozycję \"" + name + "\", w zamian mogę zaoferować ");
            message.setSelection(message.getText().length());
        });

        if (owner_id != ProductListDescription.DEFAULT_OWNER_ID) {
            userDownloader.execute(Constants.ACCOUNT_GET_URL + String.format("?id_klienta=%d&id_konta=%d", ProductListDescription.DEFAULT_OWNER_ID , owner_id));
        } else {
            Toast.makeText(this, "Niepoprawny identyfikator użytkownika", Toast.LENGTH_SHORT).show();
        }

        bookDownloader.execute(Constants.DETAILS_URL + String.format("?id_ksiazki=%d&id_konta=%d", book_id, owner_id));

        send.setOnClickListener(v -> {
            // TODO wysłać wiadomość od bazy
            /*
            Uplaoader ...

            finish()
            //*/

            Intent i = new Intent(ChatNewActivity.this, ChatSingleActivity.class);
            i.putExtra("owner_id", owner_id);
            i.putExtra("book_id", book_id);
            startActivity(i);
        });

        back.setOnClickListener(v -> finish());
    }
}
