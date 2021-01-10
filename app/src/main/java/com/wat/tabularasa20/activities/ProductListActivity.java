package com.wat.tabularasa20.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.google.gson.JsonObject;
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
 * Aktywność listy produktów
 * doczytać https://bignerdranch.github.io/expandable-recycler-view/
 */
public class ProductListActivity extends AppCompatActivity implements ProductListAdapter.RowClickListener, TextWatcher, View.OnClickListener, ProductListAdapter.FavouriteChangeListener {

    ProductListAdapter adapter = null;
    RecyclerView recyclerView = null;
    ProductListAdapter.SortOrder sortOrder = ProductListAdapter.SortOrder.ASC;
    ArrayList<ProductListDescription> products = null;

    /**
     * Fłówna metoda klasy
     */
    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_browse);

        // Uzyskanie dostępu do elementów graficznych
        ImageButton back = findViewById(R.id.productsBrowseButtonBack);
        EditText filter = findViewById(R.id.productsBrowseEditTextSearchText);
        ImageButton sort = findViewById(R.id.productsBrowseImageButtonSort);
        recyclerView = findViewById(R.id.productsBrowseRecyclerViewProductsList);

        back.setOnClickListener(v -> finish());

        products = new ArrayList<>();

        // Pobranie informacji o wszystkich kasiążkach
        Downloader productDownloader = new Downloader();
        productDownloader.setOnResultListener(resultProducts -> {
            // Utworzenie obiektu JSON z danych pobranych z internetu
            assert resultProducts != null;
            try {
            resultProducts = Network.repairJson(resultProducts);
            //Toast.makeText(ProductListActivity.this, resultProducts, Toast.LENGTH_LONG).show();
            JsonObject productsJsonObject = JsonParser.parseString(resultProducts).getAsJsonObject();

            //String body = productsJsonObject.get("body").getAsString();
            //JsonArray productsJsonArray = JsonParser.parseString(body).getAsJsonArray();
            JsonArray productsJsonArray = productsJsonObject.get("body").getAsJsonArray();

            // Pobranie informacji o ulubionych
            Downloader favouriteDownloader = new Downloader();
            favouriteDownloader.setOnResultListener(resultFavourites -> {
                assert resultFavourites != null;
                resultFavourites = Network.repairJson(resultFavourites);
                if (resultFavourites.equals("-1"))
                    resultFavourites = "[]";
                JsonArray favouritesJsonArray = JsonParser.parseString(resultFavourites).getAsJsonArray();

                // przejście po wszystkich produktach ze sprawdzeniem czy ulubiony
                productsJsonArray.forEach(productJsonElement -> {
                    boolean contains = favouritesJsonArray.contains(productJsonElement);
                    products.add(new ProductListDescription(
                            productJsonElement.getAsJsonObject().get("Tytul").getAsString(),
                            productJsonElement.getAsJsonObject().get("Id_ksiazki").getAsInt(),
                            contains ? ProductListDescription.FavouriteStare.ON : ProductListDescription.FavouriteStare.OFF,
                            "", "", "",
                            productJsonElement.getAsJsonObject().get("Autor").getAsString(),
                            ProductListDescription.DEFAULT_OWNER_ID));
                });

                adapter = new ProductListAdapter(ProductListActivity.this, products);
                adapter.setRowClickListener(this);
                adapter.setFavouriteChangeListener(this);
                recyclerView.setAdapter(adapter);
            });
            favouriteDownloader.execute(Constants.FAVOURITES_URL + String.format("?Id_klienta=%d", Preferences.readClientID(ProductListActivity.this)));
            } catch (Exception e) {
                System.out.println(e);
            }
        });
        productDownloader.execute(Constants.BOOKS_GET_URL);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        filter.addTextChangedListener(this);
        sort.setOnClickListener(this);
    }

    /**
     * Akcja elementu listy
     */
    @Override
    public void onRowClick(View view, int position) {
        //Toast.makeText(this, "Dotknięto " + adapter.getItem(position).name + ", ulubiony " + adapter.getItem(position).favourite, Toast.LENGTH_SHORT).show();

        // TODO Przejść do wikoku wszystkich instancji zamiast detali

        Intent i = new Intent(ProductListActivity.this, ProductListCopyActivity.class);
        i.putExtra("book_id", adapter.getItem(position).productID);
        startActivity(i);
    }

    /**
     * Akcja przycisku ulubionych
     */
    @Override
    public void onFavouriteChange(View v, boolean isChecked, int position) {
        Toast.makeText(this, "Dotknięto * przy " + adapter.getItem(position).title + ", ulubiony " + isChecked, Toast.LENGTH_SHORT).show();
    }


    /**
     * Akcja pola filtrowania
     */
    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        adapter = new ProductListAdapter(this,
            ProductListAdapter.filter(charSequence.toString(), ProductListAdapter.sort(sortOrder, products)));
        //adapter.setRowClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    /**
     * Akcja przycisku sortowania
     */
    @Override
    public void onClick(View view) {
        sortOrder = sortOrder == ProductListAdapter.SortOrder.ASC ? ProductListAdapter.SortOrder.DESC : ProductListAdapter.SortOrder.ASC;
        ((ImageButton)view).setImageResource(
            sortOrder == ProductListAdapter.SortOrder.ASC ? android.R.drawable.arrow_down_float : android.R.drawable.arrow_up_float);
        ArrayList<ProductListDescription> filtered = new ArrayList<>(); // tylko obecnie pokazane elementy
        for (int i = 0; i < adapter.getItemCount(); i++)
            filtered.add(adapter.getItem(i));
        adapter = new ProductListAdapter(this, ProductListAdapter.sort(sortOrder, filtered));
        //adapter.setRowClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
    @Override public void afterTextChanged(Editable editable) {}
}
