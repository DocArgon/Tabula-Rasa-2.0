package com.wat.tabularasa20.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ToggleButton;
import androidx.appcompat.app.AppCompatActivity;
import com.wat.tabularasa20.MainActivity;
import com.wat.tabularasa20.R;
import com.wat.tabularasa20.utilities.ActivityUtil;
import com.wat.tabularasa20.utilities.Preferences;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityUtil.changeTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button logout = findViewById(R.id.settingsButtonLogout);
        ImageButton back = findViewById(R.id.settingsButtonBack);
        ToggleButton theme = findViewById(R.id.settingsToggleButton);

        boolean stare = Preferences.readTheme(this);
        theme.setChecked(stare);
        theme.setOnClickListener(v -> {
            Preferences.saveTheme(SettingsActivity.this, theme.isChecked());
            ActivityUtil.refreshActivity(SettingsActivity.this);
        });

        logout.setOnClickListener(v -> {
            Preferences.saveCredentials(SettingsActivity.this, new Preferences.LoginCredentials("", ""));
            Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finishAffinity();
        });

        back.setOnClickListener(v -> finish());
    }
}
