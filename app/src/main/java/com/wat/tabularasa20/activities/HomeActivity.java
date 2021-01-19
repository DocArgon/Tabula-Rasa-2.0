package com.wat.tabularasa20.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mattkula.secrettextview.SecretTextView;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;
import com.wat.tabularasa20.MainActivity;
import com.wat.tabularasa20.R;
import com.wat.tabularasa20.data.Constants;
import com.wat.tabularasa20.data.ProductListDescription;
import com.wat.tabularasa20.utilities.Downloader;
import com.wat.tabularasa20.utilities.Network;
import com.wat.tabularasa20.utilities.Preferences;

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
        Button editAccount = findViewById(R.id.homeAccountButtonEditInfo);
        Button sharedBooks = findViewById(R.id.homeAccountButtonMySharedBooks);
        Button favourites = findViewById(R.id.homeAccountButtonMyFavouriteBooks);
        Button addBook = findViewById(R.id.homeAccountButtonAddNewBook);
        Button sendMessage = findViewById(R.id.homeAccountButtonRecommendMeBook);
        Button searchBook = findViewById(R.id.homeAccountButtonSearchForBook);

        Button logout = findViewById(R.id.homeAccountButtonLogout);
        Button close = findViewById(R.id.homeAccountButtonClose);
        FloatingActionButton fab = findViewById(R.id.homeAccountFloatingButtonClose);

        ResideMenu resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.drawable.menu_bg);
        resideMenu.attachToActivity(this);

        ResideMenuItem itemSearchBook  = new ResideMenuItem(this, android.R.drawable.ic_menu_search, getString(R.string.search_book));
        ResideMenuItem itemEditProfile = new ResideMenuItem(this, android.R.drawable.ic_menu_edit, getString(R.string.edit_account_data));
        ResideMenuItem itemOpenChats   = new ResideMenuItem(this, android.R.drawable.stat_notify_chat, getString(R.string.conversations));
        ResideMenuItem itemSettings    = new ResideMenuItem(this, android.R.drawable.ic_menu_preferences, getString(R.string.settinds));

        resideMenu.addMenuItem(itemSearchBook,  ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemOpenChats,   ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemEditProfile, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(itemSettings,    ResideMenu.DIRECTION_RIGHT);

        //ScrollView svl = findViewById(com.special.ResideMenu.R.id.sv_left_menu);
        //ScrollView svr = findViewById(com.special.ResideMenu.R.id.sv_right_menu);
        //Toast.makeText(HomeActivity.this, svr.getLayoutParams().width, Toast.LENGTH_LONG).show();

        // Pobranie informacji o kliencie
        Downloader downloader = new Downloader();
        downloader.setOnResultListener(result -> {
            //Toast.makeText(HomeActivity.this, result, Toast.LENGTH_LONG).show();
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

        itemOpenChats.setOnClickListener(v -> Toast.makeText(HomeActivity.this, "3", Toast.LENGTH_SHORT).show());
        itemSettings.setOnClickListener(v -> Toast.makeText(HomeActivity.this, "4", Toast.LENGTH_SHORT).show());

        // Przycisk wyloguj
        logout.setOnClickListener(v -> {
            Preferences.saveCredentials(HomeActivity.this, new Preferences.LoginCredentials("", ""));
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        // TODO usunąć
        // Edycja danych konta
        editAccount.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, EditUserDataActivity.class);
            startActivity(intent);
        });

        sharedBooks.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, MySharedActivity.class);
            startActivity(intent);
        });

        // Przycisk dodaj książkę
        addBook.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ProductAddActivity.class);
            startActivity(intent);
        });

        // Przycisk ulubionych
        favourites.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, FavouriteActivity.class);
            startActivity(intent);
        });

        // Przycisk czatu
        sendMessage.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ChatNewActivity.class);
            startActivity(intent);
        });

        // TODO usunąć
        // Przycisk szukaj książki
        searchBook.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ProductListActivity.class);
            startActivity(intent);
        });

        // przycisk zamknij
        // TODO usunąć statyczny, zoztawić pływający
        close.setOnClickListener(v -> finishAndRemoveTask());
        fab.setOnClickListener(v   -> finishAndRemoveTask());
    }


    @Override
    protected void onStart() {
        super.onStart();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }
}
