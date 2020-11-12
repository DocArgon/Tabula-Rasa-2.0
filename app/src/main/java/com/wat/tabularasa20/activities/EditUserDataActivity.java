package com.wat.tabularasa20.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wat.tabularasa20.R;
import com.wat.tabularasa20.data.Constants;
import com.wat.tabularasa20.utilities.Downloader;
import com.wat.tabularasa20.utilities.Preferences;

public class EditUserDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access_edit);

        Button edit = findViewById(R.id.accessEditButtonSendForm);
        final EditText login = findViewById(R.id.accessEditEditTextLogin);
        final EditText email = findViewById(R.id.accessEditEditTextEmail);
        final EditText password = findViewById(R.id.accessEditEditTextPassword);
        final EditText passwd_rep = findViewById(R.id.accessEditEditTextPasswordRepeat);
        final EditText name = findViewById(R.id.accessEditEditTextFirstName);
        final EditText lname = findViewById(R.id.accessEditEditTextLastName);
        final EditText city = findViewById(R.id.accessEditEditTextCity);
        final EditText street = findViewById(R.id.accessEditEditTextStreet);
        final EditText phone = findViewById(R.id.accessEditEditTextPhoneNumber);
        final EditText bday = findViewById(R.id.accessEditEditTextBirthDate);

        Downloader downloader = new Downloader();
        downloader.setOnResultListener(result -> {
            result = result.substring(1, result.length() - 1);

            Preferences.LoginCredentials credentials = Preferences.readCredential(EditUserDataActivity.this);
            assert credentials != null;
            login.setText(credentials.login);
            password.setText(credentials.password);
            passwd_rep.setText(credentials.password);

            JsonObject jsonObject = JsonParser.parseString(result).getAsJsonObject();
            //email.setText();
            name.setText(jsonObject.get("Imie").getAsString());
            lname.setText(jsonObject.get("Nazwisko").getAsString());
            city.setText(jsonObject.get("Misto").getAsString());
            street.setText(jsonObject.get("Ulica").getAsString());
            //phone.setText();
            //bday.setText();
            //jsonObject.get("Nr domu").getAsString()
            //jsonObject.get("Plec").getAsString()
        });
        downloader.execute(Constants.ACCOUNT_GET_URL + String.format("?id_klienta=%s", Preferences.readUID(this)));

        edit.setOnClickListener(v -> {
            if (login.getText().toString().isEmpty() || password.getText().toString().isEmpty() ||
                    passwd_rep.getText().toString().isEmpty() || name.getText().toString().isEmpty() ||
                    city.getText().toString().isEmpty() || bday.getText().toString().isEmpty()) {
                Snackbar.make(v, getString(R.string.fields_empty), Snackbar.LENGTH_LONG).show();
                return;
            }

            if (!password.getText().toString().equals(passwd_rep.getText().toString())) {
                Snackbar.make(v, "Pole hasło i potwierdź hasło mają różne wartości", Snackbar.LENGTH_LONG).show();
                return;
            }

            Gson gson = new Gson();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("login", login.getText().toString());
            jsonObject.addProperty("email", email.getText().toString());
            jsonObject.addProperty("password", password.getText().toString());
            jsonObject.addProperty("name", name.getText().toString());
            jsonObject.addProperty("last_name", lname.getText().toString());
            jsonObject.addProperty("city", city.getText().toString());
            jsonObject.addProperty("street", street.getText().toString());
            jsonObject.addProperty("phone", phone.getText().toString());
            jsonObject.addProperty("birthday", bday.getText().toString());
            String data = gson.toJson(jsonObject);
            Toast.makeText(EditUserDataActivity.this, data, Toast.LENGTH_LONG).show();
        });
    }
}
