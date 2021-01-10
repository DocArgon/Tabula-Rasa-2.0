package com.wat.tabularasa20.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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

/**
 * Aktywność listy ulubionuch
 */
public class FavouriteActivity extends AppCompatActivity implements ProductListAdapter.RowClickListener, TextWatcher {

    ProductListAdapter adapter = null;
    RecyclerView recyclerView = null;
    ArrayList<ProductListDescription> products = null;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_favourite);

        ImageButton back = findViewById(R.id.productsFavouriteButtonBack);
        EditText filter = findViewById(R.id.productsFavouriteEditTextSearchText);
        recyclerView = findViewById(R.id.productsFavouriteRecyclerViewProductList);

        back.setOnClickListener(v -> finish());

        products = new ArrayList<>();

        // Pobranie informacji o ulubionych
        Downloader favouriteDownloader = new Downloader();
        favouriteDownloader.setOnResultListener(resultFavourites -> {
            resultFavourites = Network.repairJson(resultFavourites);
            if (!resultFavourites.equals("-1")) {
                JsonArray favouritesJsonArray = JsonParser.parseString(resultFavourites).getAsJsonArray();
                favouritesJsonArray.forEach(productJsonElement -> products.add(new ProductListDescription(
                        productJsonElement.getAsJsonObject().get("Tytul").getAsString(),
                        productJsonElement.getAsJsonObject().get("Id_ksiazki").getAsInt(),
                        ProductListDescription.FavouriteStare.ON))); // Zawsze zapalona

                adapter = new ProductListAdapter(FavouriteActivity.this, products);
                adapter.setRowClickListener(FavouriteActivity.this);
                recyclerView.setAdapter(adapter);
            } else {
                // TODO Snackbar - lista ulubionych pusta
            }
        });
        favouriteDownloader.execute(Constants.FAVOURITES_URL + String.format("?Id_klienta=%d", Preferences.readClientID(FavouriteActivity.this)));

        filter.addTextChangedListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductListAdapter(this, products);
        adapter.setRowClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    /**
     * Akcja elementu listy
     */
    @Override
    public void onRowClick(View view, int position) {
        Toast.makeText(this, "Dotknięto " + adapter.getItem(position).name + ", ulubiony " + adapter.getItem(position).favourite, Toast.LENGTH_SHORT).show();
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
