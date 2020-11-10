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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.material.snackbar.Snackbar;
import com.wat.tabularasa20.activities.HomeActivity;
import com.wat.tabularasa20.data.Constants;
import com.wat.tabularasa20.utilities.Downloader;
import com.wat.tabularasa20.utilities.Network;
import com.wat.tabularasa20.utilities.Preferences;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET},1234);
        }

        final EditText textName = findViewById(R.id.loginEditTextName);
        final EditText textPass = findViewById(R.id.loginEditTextPassword);
        final Button buttonLogin = findViewById(R.id.loginButtonLogin);

        // Akcja downloadera
        Downloader downloader = new Downloader();
        downloader.setOnResultListener(result -> {
            // TODO sprawdzenie czy odpowiedź zezwala na dostęp

            Preferences.saveCredentials(MainActivity.this,
                    new Preferences.LoginCredentials(textName.getText(), textPass.getText()));

            //Toast.makeText(MainActivity.this, "is null " + (result == null), Toast.LENGTH_SHORT).show();
            Toast.makeText(MainActivity.this, "\"" + result + "\"", Toast.LENGTH_LONG).show();
            //*
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            intent.putExtra("name", textName.getText().toString());
            intent.putExtra("result", result);
            startActivity(intent);
            finish(); // Klasy Downloader nie wykorzystywać 2 raz - wymaga reinicjalizacji dlatego kończę aktywność
            //*/
        });

        // Akcja przycisku
        buttonLogin.setOnClickListener(v -> {
            String strname = textName.getText().toString();
            String strpass = textPass.getText().toString();

            if (!Network.isDeviceConnected(MainActivity.this)) {
                Snackbar.make(v, getString(R.string.network_not_conn), Snackbar.LENGTH_LONG).show();
                return;
            }

            if (downloader.getStatus() == AsyncTask.Status.RUNNING || downloader.getStatus() == AsyncTask.Status.FINISHED) {
            	return;
			}

            // TODO przerobić zapytanie
            if (!strname.isEmpty() && !strpass.isEmpty()) {
                String strurl = Constants.LOGIN_CHECK_URL + String.format("/?login=%s&haslo=%s", strname, strpass);
                //Toast.makeText(MainActivity.this, strurl, Toast.LENGTH_LONG).show();
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
    }

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
