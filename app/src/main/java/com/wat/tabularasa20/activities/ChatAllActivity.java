package com.wat.tabularasa20.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.wat.tabularasa20.R;
import com.wat.tabularasa20.data.Constants;
import com.wat.tabularasa20.data.ProductListAdapter;
import com.wat.tabularasa20.data.ProductListDescription;
import com.wat.tabularasa20.utilities.ActivityUtil;
import com.wat.tabularasa20.utilities.Downloader;
import com.wat.tabularasa20.utilities.Network;
import com.wat.tabularasa20.utilities.Preferences;
import java.util.ArrayList;

/**
 * Aktywność listy wszystkich aktywnych czatów
 */
public class ChatAllActivity extends AppCompatActivity implements ProductListAdapter.RowClickListener {

    ProductListAdapter adapter = null;
    RecyclerView recyclerView = null;
    ArrayList<ProductListDescription> conversations = null;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityUtil.changeTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages_browse);

        ImageButton back = findViewById(R.id.messagesBrowseButtonBack);
        recyclerView = findViewById(R.id.messagesBrowseRecyclerViewMessagesList);

        back.setOnClickListener(v -> finish());

        conversations = new ArrayList<>();

        // Pobranie informacji o wszystkich kasiążkach
        Downloader chatsDownloader = new Downloader();
        chatsDownloader.setOnResultListener(resultChats -> {
            // Utworzenie obiektu JSON z danych pobranych z internetu
            assert resultChats != null;
            try {
                resultChats = Network.repairJson(resultChats);
                JsonArray chatsJsonArray = JsonParser.parseString(resultChats).getAsJsonArray();

                chatsJsonArray.forEach(productJsonElement ->
                    conversations.add(new ProductListDescription(
                            productJsonElement.getAsJsonObject().get("Tytul").getAsString(),
                            productJsonElement.getAsJsonObject().get("Id_ksiazki").getAsInt(),
                            ProductListDescription.FavouriteStare.HIDDEN,
                            "",
                            productJsonElement.getAsJsonObject().get("Login").getAsString(),
                            "",
                            "",
                            productJsonElement.getAsJsonObject().get("Id_konta").getAsInt(),
                            ""))
                );

                adapter = new ProductListAdapter(ChatAllActivity.this, conversations);
                adapter.setRowClickListener(this);
                recyclerView.setAdapter(adapter);

            } catch (Exception e) {
                System.out.println(e.toString());
            }
        });
        chatsDownloader.execute(Constants.CONVERSATIONS + String.format("?id_konta=%d", Preferences.readAccountID(ChatAllActivity.this)));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Akcja elementu listy
     */
    @Override
    public void onRowClick(View view, int position) {
        Intent i = new Intent(ChatAllActivity.this, ChatSingleActivity.class);
        i.putExtra("book_id", adapter.getItem(position).productID);
        i.putExtra("owner_id", adapter.getItem(position).ownerID);
        startActivity(i);
    }
}
