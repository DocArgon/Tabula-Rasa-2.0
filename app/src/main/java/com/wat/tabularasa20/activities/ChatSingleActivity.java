package com.wat.tabularasa20.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
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
import com.wat.tabularasa20.utilities.ActivityUtil;
import com.wat.tabularasa20.utilities.Downloader;
import com.wat.tabularasa20.utilities.MathUtil;
import com.wat.tabularasa20.utilities.Preferences;
import java.util.ArrayList;

/**
 * Aktywność pojedynczego czatu
 */
public class ChatSingleActivity extends AppCompatActivity {

    ProductListAdapter adapter = null;
    RecyclerView recyclerView = null;
    ArrayList<ProductListDescription> messages = null;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityUtil.changeTheme(this);
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
            int account = Preferences.readAccountID(ChatSingleActivity.this);

            messageJsonArray.forEach(messageJsonElement -> {
                int sender = messageJsonElement.getAsJsonObject().get("Id_wysylajacego").getAsInt();
                String msg = messageJsonElement.getAsJsonObject().get("Tresc").getAsString();
                if (sender != account)
                    messages.add(new ProductListDescription(MathUtil.fromBase64(msg), null));
                else
                    messages.add(new ProductListDescription(null, MathUtil.fromBase64(msg)));
            });

            adapter = new ProductListAdapter(ChatSingleActivity.this, messages);
            recyclerView.setAdapter(adapter);
        });

        userDownloader.execute(Constants.ACCOUNT_GET_URL + String.format("?id_klienta=%d&id_konta=%d", ProductListDescription.DEFAULT_OWNER_ID , owner_id));

        chatDownloader.execute(Constants.MESSAGES + String.format("?id_konta1=%d&id_konta2=%d", Preferences.readAccountID(this), owner_id));

        // Akcja przycisku wysyłania wiadomości
        send.setOnClickListener(v -> {
            Intent i = new Intent(ChatSingleActivity.this, ChatNewActivity.class);
            i.putExtra("book_id", book_id);
            i.putExtra("owner_id", owner_id);
            startActivity(i);
            finish();
        });

        // Zakończ konwersację
        delete.setOnClickListener(v -> {
            // TODO pokazać ekran potwierdzenia wymiany
            Downloader removeDownloader = new Downloader();
            removeDownloader.execute(Constants.MESSAGE_REMOVE + String.format("?id_konta1=%d&id_konta2=%d", Preferences.readAccountID(this), owner_id));
            finish();
        });

        back.setOnClickListener(v -> finish());
    }
}
