package com.wat.tabularasa20.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wat.tabularasa20.R;
import com.wat.tabularasa20.data.Constants;
import com.wat.tabularasa20.data.ProductListDescription;
import com.wat.tabularasa20.utilities.Downloader;
import com.wat.tabularasa20.utilities.MathUtil;
import com.wat.tabularasa20.utilities.Network;
import com.wat.tabularasa20.utilities.Preferences;
import com.wat.tabularasa20.utilities.Uploader;

/**
 * Aktywność modyfikacji danych użytkownika
 */
public class EditUserDataActivity extends AppCompatActivity {

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access_edit);

        Button edit = findViewById(R.id.accessEditButtonSaveChanges);
        Button card = findViewById(R.id.accessEditButtonGoCreditCard);
        ImageButton back = findViewById(R.id.accessEditButtonGoBack);
        EditText login = findViewById(R.id.accessEditEditTextLogin);
        EditText email = findViewById(R.id.accessEditEditTextEmail);
        EditText password = findViewById(R.id.accessEditEditTextPassword);
        EditText passwd_rep = findViewById(R.id.accessEditEditTextPasswordRepeat);
        EditText name = findViewById(R.id.accessEditEditTextFirstName);
        EditText lname = findViewById(R.id.accessEditEditTextLastName);
        EditText city = findViewById(R.id.accessEditEditTextCity);
        EditText street = findViewById(R.id.accessEditEditTextStreet);
        TextView phone = findViewById(R.id.accessEditEditTextPhoneNumber);
        TextView bday = findViewById(R.id.accessEditTextViewBirthDate);

        // Pobranie informacji o użytkowniku
        Downloader downloader = new Downloader();
        downloader.setOnResultListener(result -> {
            Preferences.LoginCredentials credentials = Preferences.readCredential(EditUserDataActivity.this);
            assert credentials != null;
            login.setText(credentials.login);
            password.setText(credentials.password);
            passwd_rep.setText(credentials.password);

            assert result != null;
            result = Network.repairJson(result);
            JsonObject jsonObject = JsonParser.parseString(result).getAsJsonObject();
            email.setText(jsonObject.get("e_mail").getAsString());
            name.setText(jsonObject.get("Imie").getAsString());
            lname.setText(jsonObject.get("Nazwisko").getAsString());
            city.setText(jsonObject.get("Miasto").getAsString());
            street.setText(jsonObject.get("Ulica").getAsString());
            phone.setText(jsonObject.get("Nr_telefonu").getAsString());
            bday.setText(jsonObject.get("Data_urodzenia").getAsString());
        });
        downloader.execute(Constants.ACCOUNT_GET_URL + String.format("?id_klienta=%d&id_konta=%d", Preferences.readClientID(this), ProductListDescription.DEFAULT_OWNER_ID));

        // akcja przycisku cofnij
        back.setOnClickListener(view -> finish());

        // akcja przycisku konta premium
        card.setOnClickListener(view -> startActivity(new Intent(EditUserDataActivity.this, CreditCardActivity.class)));

        // Akcja przycisku modyfikacji
        edit.setOnClickListener(v -> {
            // Sprawdzenie czy pola nie są puste
            if (login.getText().toString().isEmpty() || password.getText().toString().isEmpty() ||
                    passwd_rep.getText().toString().isEmpty() || name.getText().toString().isEmpty() ||
                    city.getText().toString().isEmpty()) {
                Snackbar.make(v, getString(R.string.fields_empty), Snackbar.LENGTH_LONG).show();
                return;
            }

            // Sprawdzenie czy pierwsze i powtórzone hasło jest takie samo
            if (!password.getText().toString().equals(passwd_rep.getText().toString())) {
                Snackbar.make(v, getString(R.string.rep_passwd_problem), Snackbar.LENGTH_LONG).show();
                return;
            }

            // Utworzenie obiektu JSON
            Gson gson = new Gson();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("Id_konta", Preferences.readAccountID(EditUserDataActivity.this));
            jsonObject.addProperty("Id_klienta", Preferences.readClientID(EditUserDataActivity.this));
            jsonObject.addProperty("Login", login.getText().toString());
            jsonObject.addProperty("Email", email.getText().toString());
            jsonObject.addProperty("Haslo", MathUtil.sha(password.getText().toString()));
            jsonObject.addProperty("Imie", name.getText().toString());
            jsonObject.addProperty("Nazwisko", lname.getText().toString());
            jsonObject.addProperty("Miasto", city.getText().toString());
            jsonObject.addProperty("Ulica", street.getText().toString());
            jsonObject.addProperty("Nr_telefonu", phone.getText().toString());
            // jsonObject.addProperty("birthday", bday.getText().toString()); nie modyfikowalne
            // plec niemodyfikowalna
            String data = gson.toJson(jsonObject);


            Uploader uploader = new Uploader();
            uploader.setOnResultListener(new Uploader.UploadActions() {
                @Override
                public void getResult(String result) {
                    Snackbar.make(edit, "Zmieniono dane", Snackbar.LENGTH_LONG).show();
                    new CountDownTimer(3000, 3000) {
                        @Override public void onTick(long millisUntilFinished) {}
                        @Override
                        public void onFinish() {
                            startActivity(new Intent(EditUserDataActivity.this, EditUserDataActivity.class));
                            overridePendingTransition(0, 0);
                            finish();
                        }
                    }.start();
                }
                @Override
                public void getError(String error) {
                    Snackbar.make(edit, "Coś poszło nie tak", Snackbar.LENGTH_LONG).show();
                }
            });
            uploader.execute(this, Constants.ACCOUNT_EDIT, data);
        });
    }
}
