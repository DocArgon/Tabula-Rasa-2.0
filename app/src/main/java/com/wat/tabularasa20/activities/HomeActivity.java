package com.wat.tabularasa20.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;
import com.wat.tabularasa20.MainActivity;
import com.wat.tabularasa20.R;
import com.wat.tabularasa20.data.Constants;
import com.wat.tabularasa20.utilities.Downloader;
import com.wat.tabularasa20.utilities.Preferences;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_account);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE);

        Intent incoming_intent = getIntent();
        Preferences.saveUID(this, incoming_intent.getStringExtra("result"));

        TextView homeTV = findViewById(R.id.homeAccountTextViewWelcome);
        final Button editAccount = findViewById(R.id.homeAccountButtonEditInfo);
        //final Button sharedBooks = findViewById();
        final Button favourites = findViewById(R.id.homeAccountButtonMyFavouriteBooks);
        final Button addBook = findViewById(R.id.homeAccountButtonAddNewBook);
        final Button sendMessage = findViewById(R.id.homeAccountButtonMyMessages);
        final Button searchBook = findViewById(R.id.homeAccountButtonSearchForBook);

        final Button logout = findViewById(R.id.homeAccountButtonLogout);
        final Button close = findViewById(R.id.homeAccountButtonClose);
        final FloatingActionButton fab = findViewById(R.id.homeAccountFloatingButtonClose);

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
            JsonObject jsonObject = JsonParser.parseString(result).getAsJsonObject();
            homeTV.setText(getString(R.string.hello_msg_params, jsonObject.get("Imie").getAsString()));
        });
        downloader.execute(Constants.ACCOUNT_GET_URL + String.format("?id_klienta=%s", Preferences.readUID(this)));

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

        // Przycisk dodaj książkę
        addBook.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, AddBookActivity.class);
            startActivity(intent);
        });

        // Przycisk ulubionych
        favourites.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, FavouriteActivity.class);
            startActivity(intent);
        });

        // Przycisk czatu
        sendMessage.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ChatActivity.class);
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
        close.setOnClickListener(v -> finishAffinity());
        fab.setOnClickListener(v   -> finishAffinity());
    }
}
