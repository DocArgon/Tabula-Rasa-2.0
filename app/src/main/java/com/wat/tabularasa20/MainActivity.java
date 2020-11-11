package com.wat.tabularasa20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.material.snackbar.Snackbar;
import com.wat.tabularasa20.activities.HomeActivity;
import com.wat.tabularasa20.activities.RegisterActivity;
import com.wat.tabularasa20.activities.SplashActivity;
import com.wat.tabularasa20.data.Constants;
import com.wat.tabularasa20.utilities.Downloader;
import com.wat.tabularasa20.utilities.Network;
import com.wat.tabularasa20.utilities.Preferences;

public class MainActivity extends AppCompatActivity {

    EditText textName = null;
    EditText textPass = null;
    Button buttonLogin = null;
    Downloader downloader = new Downloader();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Wywołanie sprawdzenia uprawnień
         */
        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET},1234);
        }

        textName = findViewById(R.id.mainEditTextName);
        textPass = findViewById(R.id.mainEditTextPassword);
        buttonLogin = findViewById(R.id.mainButtonLogin);

        // Akcja przycisku
        buttonLogin.setOnClickListener(v -> {
            String strname = textName.getText().toString();
            String strpass = textPass.getText().toString();

            if (!Network.isDeviceConnected(MainActivity.this)) {
                Snackbar.make(v, getString(R.string.network_not_conn), Snackbar.LENGTH_LONG).show();
                return;
            }

            if (downloader.getStatus() == AsyncTask.Status.RUNNING) {
            	return;
			}

            // TODO przerobić zapytanie na hash
            if (!strname.isEmpty() && !strpass.isEmpty()) {
                String strurl = Constants.LOGIN_CHECK_URL + String.format("/?login=%s&haslo=%s", strname, strpass);
                downloader = new Downloader();
                downloader.setOnResultListener(this::login);
                downloader.execute(strurl);
            } else {
                Snackbar.make(v, getString(R.string.login_fields_empty), Snackbar.LENGTH_LONG).show();
            }
        });

        // Jeśli zapamiętano dane logowania to zaloguj automatycznie
        Preferences.LoginCredentials credentials = Preferences.readCredential(this);
        if (credentials != null) {
            textName.setText(credentials.login);
            textPass.setText(credentials.password);
            buttonLogin.performClick();
        }

        // Przykrycie ekranu logowanie gdy użytkownik ma zapisane dane logowania
        startActivity(new Intent(this, SplashActivity.class));
    }

    /**
     * Utworzenie menu
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Sprawdzenie która opcja z menu została wskazana
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mainMenuCreateAccount:
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                return true;
            case R.id.mainMenuAnotherID:
                Toast.makeText(MainActivity.this, "kliknięto niewiadomo co", Toast.LENGTH_LONG).show();
                return true;
            case R.id.mainMenuCloseApp:
                finishAffinity();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void login(String result) {
        if (Integer.parseInt(result.replaceAll("\"", "")) < 0) {
            Snackbar.make(findViewById(R.id.mainButtonLogin), "Nieprawidłowy login lub hasło", Snackbar.LENGTH_LONG).show();
            return;
        }

        Preferences.saveCredentials(MainActivity.this,
                new Preferences.LoginCredentials(textName.getText(), textPass.getText()));

        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        intent.putExtra("result", result.replaceAll("\"", ""));
        startActivity(intent);
        finish();
    }

    /**
     * Metoda odbierająca z systemu informację o przyznaniu uprawnień
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1234) {
            if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, getString(R.string.permission_not_granted), Toast.LENGTH_LONG).show();
                //finish();
            }
        }
    }
}
