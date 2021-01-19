package com.wat.tabularasa20.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.wat.tabularasa20.utilities.Network;
import com.wat.tabularasa20.utilities.Preferences;
import com.wat.tabularasa20.utilities.Uploader;

public class CreditCardActivity extends AppCompatActivity {

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card);

        ImageButton back = findViewById(R.id.creditCardButtonBack);
        Button edit = findViewById(R.id.creditCardButtonUpdateSend);

        EditText name = findViewById(R.id.creditCardEditTextFirstName);
        EditText lname = findViewById(R.id.creditCardEditTextLastName);
        EditText number = findViewById(R.id.creditCardEditTextCardNumber);
        EditText ccv = findViewById(R.id.creditCardEditTextCcvCode);
        EditText date = findViewById(R.id.creditCardEditTextExpirationDate);

        // Pobranie informacji o użytkowniku
        Downloader downloader = new Downloader();
        downloader.setOnResultListener(result -> {
            assert result != null;
            result = Network.repairJson(result);
            JsonObject jsonObject = JsonParser.parseString(result).getAsJsonObject();
            name.setText(jsonObject.get("Imie").getAsString());
            lname.setText(jsonObject.get("Nazwisko").getAsString());
            String nr = jsonObject.get("Nr_karty").getAsString();
            number.setText(nr.equals("None") ? "" : nr);
            String cc = jsonObject.get("ccv").getAsString();
            ccv.setText(cc.equals("None") ? "" : cc);
            String dt = jsonObject.get("Data_waznosci").getAsString();
            date.setText(dt.equals("None") ? "" : dt);
        });
        downloader.execute(Constants.ACCOUNT_GET_URL + String.format("?id_klienta=%d&id_konta=%d", Preferences.readClientID(this), ProductListDescription.DEFAULT_OWNER_ID));

        // Akcja przycisku modyfikacji
        edit.setOnClickListener(v -> {
            // Sprawdzenie czy pola nie są puste
            if (name.getText().toString().isEmpty() || lname.getText().toString().isEmpty() ||
                    number.getText().toString().isEmpty() || ccv.getText().toString().isEmpty() ||
                    date.getText().toString().isEmpty()) {
                Snackbar.make(v, getString(R.string.fields_empty), Snackbar.LENGTH_LONG).show();
                return;
            }

            // Utworzenie obiektu JSON
            Gson gson = new Gson();
            JsonObject jsonObject = new JsonObject();
            //jsonObject.addProperty("Imie", name.getText().toString());
            //jsonObject.addProperty("Nazwisko", lname.getText().toString());
            jsonObject.addProperty("Id_konta", Preferences.readAccountID(this));
            jsonObject.addProperty("Nr_karty", number.getText().toString());
            jsonObject.addProperty("Ccv", ccv.getText().toString());
            jsonObject.addProperty("Data_waznosci", date.getText().toString());
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
                            startActivity(new Intent(CreditCardActivity.this, CreditCardActivity.class));
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
            uploader.execute(this, Constants.CARD_DATA_EDIT, data);
        });

        back.setOnClickListener(v -> finish());
    }
}
