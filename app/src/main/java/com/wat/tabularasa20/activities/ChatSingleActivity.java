package com.wat.tabularasa20.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wat.tabularasa20.R;
import com.wat.tabularasa20.data.Constants;
import com.wat.tabularasa20.data.ProductListAdapter;
import com.wat.tabularasa20.data.ProductListDescription;
import com.wat.tabularasa20.utilities.Downloader;
import java.util.ArrayList;

public class ChatSingleActivity extends AppCompatActivity {

    ProductListAdapter adapter = null;
    RecyclerView recyclerView = null;
    ArrayList<ProductListDescription> messages = null;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages_single_chat);

        Intent inp = getIntent();
        int owner_id = inp.getIntExtra("owner_id", ProductListDescription.DEFAULT_OWNER_ID);
        int book_id = inp.getIntExtra("book_id", ProductListDescription.DEFAULT_PRODUCT_ID);

        FloatingActionButton delete = findViewById(R.id.messagesSingleChatFloatingActionButtonRemoveConversation);
        ImageButton back = findViewById(R.id.messagesSingleChatButtonBack);
        TextView title = findViewById(R.id.messagesSingleChatTextViewTitle);
        recyclerView = findViewById(R.id.messagesSingleChatRecyclerViewMessagesList);
        Button send = findViewById(R.id.messagesSingleChatButtonMessageNew);

        messages = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Pobranie informacji o użytkowniku
        Downloader userDownloader = new Downloader();
        userDownloader.setOnResultListener(result -> {
            // Utworzenie obiektu JSON z danych pobranych z internetu
            assert result != null;
            JsonObject productsJsonObject = JsonParser.parseString(result).getAsJsonObject();
            String name = productsJsonObject.get("Imie").getAsString();
            title.setText(getString(R.string.chat_with, name));
        });

        Downloader chatDownloader = new Downloader();
        chatDownloader.setOnResultListener(result -> {
            // Utworzenie obiektu JSON z danych pobranych z internetu
            assert result != null;
            JsonArray messageJsonArray = JsonParser.parseString(result).getAsJsonArray();

            messageJsonArray.forEach(messageJsonElement -> {
                // TODO zaimplemetować czat
                messages.add(new ProductListDescription("Treść wiadomości 1", null));
            });

            adapter = new ProductListAdapter(ChatSingleActivity.this, messages);
            recyclerView.setAdapter(adapter);
        });

        if (owner_id != ProductListDescription.DEFAULT_OWNER_ID) {
            userDownloader.execute(Constants.ACCOUNT_GET_URL + String.format("?id_klienta=%d&id_konta=%d", ProductListDescription.DEFAULT_OWNER_ID , owner_id));
        } else {
            Toast.makeText(this, "Niepoprawny identyfikator użytkownika", Toast.LENGTH_SHORT).show();
        }

        //chatDownloader.execute(Constants.DETAILS_URL + String.format("?id_ksiazki=%d&id_konta=%d", book_id, owner_id));

        messages.add(new ProductListDescription("Treść wiadomości 1\nnowa linnia", null));
        messages.add(new ProductListDescription(null, "Wiadomość 2"));
        messages.add(new ProductListDescription("Treść wiadomości 3 jkauyhjvabfagyuwfbvakjvuaykvwd", null));
        messages.add(new ProductListDescription(null, "Treść wiadomości 4 aboiavbwiuabwcahcwhuavwocv ahg"));
        adapter = new ProductListAdapter(ChatSingleActivity.this, messages);
        recyclerView.setAdapter(adapter);

        send.setOnClickListener(v -> {
            // TODO wysłać wiadomość od bazy
            Toast.makeText(this, "Wysyłanie", Toast.LENGTH_LONG).show();
        });

        delete.setOnClickListener(v -> {
            // TODO usunąć ten czat
            Toast.makeText(this, "Usuwanie", Toast.LENGTH_LONG).show();
        });

        back.setOnClickListener(v -> finish());
    }
}
