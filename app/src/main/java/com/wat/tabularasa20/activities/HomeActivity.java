package com.wat.tabularasa20.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wat.tabularasa20.MainActivity;
import com.wat.tabularasa20.R;
import com.wat.tabularasa20.data.Constants;
import com.wat.tabularasa20.utilities.Downloader;
import com.wat.tabularasa20.utilities.Preferences;

public class HomeActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TextView homeTV = findViewById(R.id.textView2);
        final Button back = findViewById(R.id.back);
        back.setOnClickListener(v -> {
            // TODO zmienić cofnij na wyloguj
            Preferences.saveCredentials(HomeActivity.this, new Preferences.LoginCredentials("", ""));
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        Intent intent = getIntent();
        final String login_result = intent.getStringExtra("result");

        // Pobranie informacji o kliencie
        Downloader downloader = new Downloader();
        downloader.setOnResultListener(result -> {
            result = result.substring(1, result.length() - 1); //result.replace("[", "{").replace("]", "}");
            //Toast.makeText(HomeActivity.this, result, Toast.LENGTH_LONG).show();
            JsonObject jsonObject = JsonParser.parseString(result).getAsJsonObject();
            homeTV.setText("Witaj " + jsonObject.get("Imie").getAsString() + " " + jsonObject.get("Nazwisko").getAsString());
        });
        downloader.execute(Constants.ACCOUNT_GET_URL + String.format("?id_klienta=%s", login_result));

        //JsonObject jsonObject = JsonParser.parseString("{\"result\": " + login_result + "}").getAsJsonObject();
        //homeTV.setText("Witaj " + name + "\nserwer odpowiedział: " +  jsonObject.get("result").getAsString());
    }
}
