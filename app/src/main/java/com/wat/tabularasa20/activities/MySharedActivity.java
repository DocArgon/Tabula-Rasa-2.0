package com.wat.tabularasa20.activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.wat.tabularasa20.R;
import com.wat.tabularasa20.data.Constants;
import com.wat.tabularasa20.data.ProductListAdapter;
import com.wat.tabularasa20.data.ProductListDescription;
import com.wat.tabularasa20.utilities.Downloader;
import com.wat.tabularasa20.utilities.Network;
import com.wat.tabularasa20.utilities.Preferences;
import java.util.ArrayList;

public class MySharedActivity extends AppCompatActivity implements ProductListAdapter.RowClickListener, TextWatcher, ProductListAdapter.RowLongClickListener {

    ProductListAdapter adapter = null;
    RecyclerView recyclerView = null;
    ArrayList<ProductListDescription> products = null;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_my_shared);

        ImageButton back = findViewById(R.id.productsMySharedButtonBack);
        EditText filter = findViewById(R.id.productsMySharedEditTextSearchText);
        recyclerView = findViewById(R.id.productsMySharedRecyclerViewProductList);

        back.setOnClickListener(v -> finish());

        products = new ArrayList<>();

        // Pobranie informacji o udostępnionych
        Downloader sharedDownloader = new Downloader();
        sharedDownloader.setOnResultListener(result -> {
            assert result != null;
            result = Network.repairJson(result);
            if (!result.equals("-1")) {
                JsonArray favouritesJsonArray = JsonParser.parseString(result).getAsJsonArray();
                favouritesJsonArray.forEach(productJsonElement -> products.add(new ProductListDescription(
                        productJsonElement.getAsJsonObject().get("Tytul").getAsString(),
                        productJsonElement.getAsJsonObject().get("Id_ksiazki").getAsInt(),
                        ProductListDescription.FavouriteStare.HIDDEN)));

                adapter = new ProductListAdapter(MySharedActivity.this, products);
                adapter.setRowClickListener(MySharedActivity.this);
                adapter.setRowLongClickListener(MySharedActivity.this);
                recyclerView.setAdapter(adapter);
            } else {
                Snackbar.make(back, "Brak udostępnionych książek", Snackbar.LENGTH_LONG).show();
            }
        });
        sharedDownloader.execute(Constants.SHARED_URL + String.format("?id_konta=%d", Preferences.readAccountID(MySharedActivity.this)));

        filter.addTextChangedListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Akcja elementu listy na dotknięcie
     */
    @Override
    public void onRowClick(View view, int position) {
        Intent i = new Intent(this, ProductDetailsActivity.class);
        i.putExtra("book_id", adapter.getItem(position).productID);
        i.putExtra("owner_id", Preferences.readAccountID(this));
        startActivity(i);
    }

    /**
     * Akcja elementu listy na przytrzymanie
     */
    @SuppressLint("DefaultLocale")
    @Override
    public void onRowLongClick(View view, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MySharedActivity.this);
        builder.setTitle("Usunąć " + adapter.getItem(position).title + "?");
        builder.setPositiveButton("Tak", (dialog, id) -> {
            Downloader productDownloader = new Downloader();
            productDownloader.execute(Constants.SHARED_REM + String.format("?id_konta=%d&id_ksiazki=%d", Preferences.readAccountID(MySharedActivity.this), adapter.getItem(position).productID));
            startActivity(new Intent(MySharedActivity.this, MySharedActivity.class));
            overridePendingTransition(0, 0);
            finish();
        });
        builder.setNegativeButton("Nie", (dialog, id) -> {});
        builder.create().show();
    }

    /**
     * Akcja pola filtrowania
     */
    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        adapter = new ProductListAdapter(this, ProductListAdapter.filter(charSequence.toString(), products));
        adapter.setRowClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
    @Override public void afterTextChanged(Editable editable) {}
}
