package com.wat.tabularasa20.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.wat.tabularasa20.utilities.ActivityUtil;
import com.wat.tabularasa20.utilities.Downloader;
import com.wat.tabularasa20.utilities.Network;
import com.wat.tabularasa20.utilities.Preferences;
import java.util.ArrayList;

/**
 * Aktywność listy ulubionuch
 */
public class FavouriteActivity extends AppCompatActivity implements TextWatcher, ProductListAdapter.FavouriteChangeListener {

    ProductListAdapter adapter = null;
    RecyclerView recyclerView = null;
    ArrayList<ProductListDescription> products = null;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityUtil.changeTheme(this);
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
            assert resultFavourites != null;
            resultFavourites = Network.repairJson(resultFavourites);
            if (!resultFavourites.equals("-1")) {
                JsonArray favouritesJsonArray = JsonParser.parseString(resultFavourites).getAsJsonArray();
                favouritesJsonArray.forEach(productJsonElement -> products.add(new ProductListDescription(
                        productJsonElement.getAsJsonObject().get("Tytul").getAsString(),
                        productJsonElement.getAsJsonObject().get("Id_ksiazki").getAsInt(),
                        ProductListDescription.FavouriteStare.ON))); // Zawsze zapalona

                adapter = new ProductListAdapter(FavouriteActivity.this, products);
                adapter.setFavouriteChangeListener(this);
                recyclerView.setAdapter(adapter);
            } else {
                Snackbar.make(back, "Nie masz nic na liście ulubionych", Snackbar.LENGTH_LONG).show();
            }
        });
        favouriteDownloader.execute(Constants.FAVOURITES_URL + String.format("?id_konta=%d", Preferences.readAccountID(FavouriteActivity.this)));

        filter.addTextChangedListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Akcja przycisku ulubionych
     */
    @SuppressLint("DefaultLocale")
    @Override
    public void onFavouriteChange(View v, boolean isChecked, int position) {
        Downloader favouriteRemover = new Downloader();
        favouriteRemover.execute(Constants.FAVOURITES_REM + String.format("?id_ksiazki=%d&id_konta=%d", adapter.getItem(position).productID, Preferences.readAccountID(FavouriteActivity.this)));
        ActivityUtil.refreshActivity(FavouriteActivity.this);
    }

    /**
     * Akcja pola filtrowania
     */
    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        adapter = new ProductListAdapter(this, ProductListAdapter.filter(charSequence.toString(), products));
        recyclerView.setAdapter(adapter);
    }

    @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
    @Override public void afterTextChanged(Editable editable) {}
}
