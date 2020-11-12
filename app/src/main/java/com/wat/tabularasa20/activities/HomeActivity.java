package com.wat.tabularasa20.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
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

        Intent incoming_intent = getIntent();
        Preferences.saveUID(this, incoming_intent.getStringExtra("result"));


        TextView homeTV = findViewById(R.id.homeAccountTextViewWelcome);
        final Button editAccount = findViewById(R.id.homeAccountButtonEditInfo);
        final Button addBook = findViewById(R.id.homeAccountButtonAddNewBook);
        final Button sendMessage = findViewById(R.id.homeAccountButtonMyMessages);
        final Button logout = findViewById(R.id.homeAccountButtonLogout);
        final Button close = findViewById(R.id.homeAccountButtonClose);
        final Button searchBook = findViewById(R.id.homeAccountButtonSearchForBook);


        ResideMenu resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.drawable.menu_bg);
        resideMenu.attachToActivity(this);

        ResideMenuItem itemHome     = new ResideMenuItem(this, R.mipmap.ic_launcher,     "Home");
        ResideMenuItem itemProfile  = new ResideMenuItem(this, R.mipmap.ic_launcher,  "Profile");
        ResideMenuItem itemCalendar = new ResideMenuItem(this, R.mipmap.ic_launcher,     "Chat");
        ResideMenuItem itemSettings = new ResideMenuItem(this, R.mipmap.ic_launcher, "Settings");

        itemHome.setOnClickListener(v -> Toast.makeText(HomeActivity.this, "1", Toast.LENGTH_SHORT).show());
        itemProfile.setOnClickListener(v -> Toast.makeText(HomeActivity.this, "2", Toast.LENGTH_SHORT).show());
        itemCalendar.setOnClickListener(v -> Toast.makeText(HomeActivity.this, "3", Toast.LENGTH_SHORT).show());
        itemSettings.setOnClickListener(v -> Toast.makeText(HomeActivity.this, "4", Toast.LENGTH_SHORT).show());

        resideMenu.addMenuItem(itemHome,     ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemProfile,  ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemCalendar, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(itemSettings, ResideMenu.DIRECTION_RIGHT);


        // Pobranie informacji o kliencie
        Downloader downloader = new Downloader();
        downloader.setOnResultListener(result -> {
            result = result.substring(1, result.length() - 1);
            //Toast.makeText(HomeActivity.this, result, Toast.LENGTH_LONG).show();
            JsonObject jsonObject = JsonParser.parseString(result).getAsJsonObject();
            homeTV.setText(getString(R.string.hello_msg_params,jsonObject.get("Imie").getAsString(), jsonObject.get("Nazwisko").getAsString()));
        });
        downloader.execute(Constants.ACCOUNT_GET_URL + String.format("?id_klienta=%s", Preferences.readUID(this)));


        // Przycisk wyloguj
        logout.setOnClickListener(v -> {
            Preferences.saveCredentials(HomeActivity.this, new Preferences.LoginCredentials("", ""));
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

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

        // Przycisk czatu
        sendMessage.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ChatActivity.class);
            startActivity(intent);
        });

        // Przycisk szukaj książki
        searchBook.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ProductListActivity.class);
            startActivity(intent);
        });

        // przycisk zamknij
        close.setOnClickListener(v -> finishAffinity());
    }
}
