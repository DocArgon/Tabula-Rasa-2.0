package com.wat.tabularasa20.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mattkula.secrettextview.SecretTextView;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;
import com.wat.tabularasa20.R;
import com.wat.tabularasa20.data.Constants;
import com.wat.tabularasa20.data.ProductListDescription;
import com.wat.tabularasa20.utilities.Downloader;
import com.wat.tabularasa20.utilities.Network;
import com.wat.tabularasa20.utilities.Preferences;
import java.util.ArrayList;

/**
 * Aktywność ekranu głównego aplikacji
 */
public class HomeActivity extends AppCompatActivity {

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_account);

        if (!Preferences.readHelpState(this)) {
            Preferences.saveHelpState(this);
            startActivity(new Intent(this, HelpActivity.class));
        }

        Intent incoming_intent = getIntent();
        Preferences.saveClientID(this, incoming_intent.getStringExtra("result"));

        SecretTextView welcome = findViewById(R.id.homeAccountTextViewWelcome);
        Button sendMessage = findViewById(R.id.homeAccountButtonRecommendMeBook);
        FloatingActionButton fab = findViewById(R.id.homeAccountFloatingButtonClose);

        ResideMenu resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.drawable.menu_bg);
        resideMenu.attachToActivity(this);

        ResideMenuItem itemSearchBook  = new ResideMenuItem(this, android.R.drawable.ic_menu_search, getString(R.string.search_book));
        ResideMenuItem itemAddBook     = new ResideMenuItem(this, android.R.drawable.ic_menu_add, getString(R.string.add_book));
        ResideMenuItem itemMyShared    = new ResideMenuItem(this, android.R.drawable.ic_menu_set_as, getString(R.string.my_shared_title));
        ResideMenuItem itemFavourites  = new ResideMenuItem(this, android.R.drawable.star_big_on, getString(R.string.favourites));
        ResideMenuItem itemEditProfile = new ResideMenuItem(this, android.R.drawable.ic_menu_edit, getString(R.string.edit_account_data));
        ResideMenuItem itemOpenChats   = new ResideMenuItem(this, android.R.drawable.stat_notify_chat, getString(R.string.conversations));
        ResideMenuItem itemSettings    = new ResideMenuItem(this, android.R.drawable.ic_menu_preferences, getString(R.string.settinds));

        resideMenu.addMenuItem(itemSearchBook,  ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemAddBook,     ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemMyShared,    ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemFavourites,  ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemOpenChats,   ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(itemEditProfile, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(itemSettings,    ResideMenu.DIRECTION_RIGHT);

        //ScrollView svl = findViewById(com.special.ResideMenu.R.id.sv_left_menu);
        //ScrollView svr = findViewById(com.special.ResideMenu.R.id.sv_right_menu);
        //Toast.makeText(HomeActivity.this, svr.getLayoutParams().width, Toast.LENGTH_LONG).show();

        // Pobranie informacji o kliencie
        Downloader downloader = new Downloader();
        downloader.setOnResultListener(result -> {
            assert result != null;
            result = Network.repairJson(result);
            JsonObject jsonObject = JsonParser.parseString(result).getAsJsonObject();
            Preferences.saveAccountID(this, jsonObject.get("id_konta").getAsString());
            welcome.setIsVisible(false);
            welcome.setDuration(3500);
            welcome.setText(getString(R.string.hello_msg_params, jsonObject.get("Imie").getAsString()));
            welcome.show();
        });
        downloader.execute(Constants.ACCOUNT_GET_URL + String.format("?id_klienta=%d&id_konta=%d", Preferences.readClientID(this), ProductListDescription.DEFAULT_OWNER_ID));

        // Akcja przycisku menu wyświetlenia listy produktów
        itemSearchBook.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ProductListActivity.class);
            startActivity(intent);
        });

        // Akcja przycisku menu edycji danych użytkownika
        itemEditProfile.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, EditUserDataActivity.class);
            startActivity(intent);
        });

        // Akcja przycisku czatów
        itemOpenChats.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ChatAllActivity.class);
            startActivity(intent);
        });

        // Akcja przycisku menu ustawień
        itemSettings.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        // Akcja przycisku moich udostępnionych
        itemMyShared.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, MySharedActivity.class);
            startActivity(intent);
        });

        // Akcja przycisku dodaj książkę
        itemAddBook.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ProductAddActivity.class);
            startActivity(intent);
        });

        // Akcja przycisku ulubione
        itemFavourites.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, FavouriteActivity.class);
            startActivity(intent);
        });

        // POLEć MI COś
        sendMessage.setOnClickListener(v -> readMyPreferences());

        fab.setOnClickListener(v   -> finishAndRemoveTask());
    }


    @Override
    protected void onStart() {
        super.onStart();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    @SuppressLint("DefaultLocale")
    private void readMyPreferences () {
        ArrayList<ProductListDescription> products = new ArrayList<>();
        //*
        Downloader productDownloader = new Downloader();
        productDownloader.setOnResultListener(resultProducts -> {
            assert resultProducts != null;
            try {
                resultProducts = Network.repairJson(resultProducts);
                JsonObject productsJsonObject = JsonParser.parseString(resultProducts).getAsJsonObject();
                JsonArray productsJsonArray = productsJsonObject.get("body").getAsJsonArray();

                Downloader sharedDownloader = new Downloader();
                sharedDownloader.setOnResultListener(resultFavourites -> {
                    assert resultFavourites != null;
                    resultFavourites = Network.repairJson(resultFavourites);
                    if (resultFavourites.equals("-1"))
                        resultFavourites = "[]";
                    JsonArray favouritesJsonArray = JsonParser.parseString(resultFavourites).getAsJsonArray();

                    productsJsonArray.forEach(productJsonElement -> {
                        boolean contains = favouritesJsonArray.contains(productJsonElement);
                        products.add(new ProductListDescription(
                                "",
                                productJsonElement.getAsJsonObject().get("Id_ksiazki").getAsInt(),
                                ProductListDescription.FavouriteStare.HIDDEN,
                                "", "", "", "",
                                productJsonElement.getAsJsonObject().get("Autor").getAsInt(),
                                ""));
                    });

                    System.out.println(products);
                });
                sharedDownloader.execute(Constants.SHARED_URL + String.format("?id_konta=%d", Preferences.readAccountID(HomeActivity.this)));
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        });

        Downloader favouriteDownloader = new Downloader();
        favouriteDownloader.setOnResultListener(resultFavourites -> {
            assert resultFavourites != null;
            resultFavourites = Network.repairJson(resultFavourites);
            if (resultFavourites.equals("-1")) {
                Toast toast = Toast.makeText(this, "Lista ulubionych jest pusta, nie możemy niczego polecić", Toast.LENGTH_SHORT);
                TextView v = toast.getView().findViewById(android.R.id.message);
                if (v != null) v.setGravity(Gravity.CENTER);
                toast.show();
            } else {
                productDownloader.execute(Constants.BOOKS_GET_URL);
            }
        });
        favouriteDownloader.execute(Constants.FAVOURITES_URL + String.format("?id_konta=%d", Preferences.readAccountID(HomeActivity.this)));
    }
}
