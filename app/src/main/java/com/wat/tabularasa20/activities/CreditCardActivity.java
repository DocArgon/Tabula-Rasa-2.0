package com.wat.tabularasa20.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wat.tabularasa20.R;
import com.wat.tabularasa20.data.Constants;
import com.wat.tabularasa20.data.ProductListDescription;
import com.wat.tabularasa20.utilities.ActivityUtil;
import com.wat.tabularasa20.utilities.Downloader;
import com.wat.tabularasa20.utilities.Network;
import com.wat.tabularasa20.utilities.Preferences;
import com.wat.tabularasa20.utilities.Uploader;

/**
 * Aktywność edycji danych karty kredytowej
 */
public class CreditCardActivity extends AppCompatActivity {

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Preferences.readTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card);

        ImageButton back = findViewById(R.id.creditCardButtonBack);
        Button edit = findViewById(R.id.creditCardButtonUpdateSend);

        EditText number = findViewById(R.id.creditCardEditTextCardNumber);
        EditText ccv = findViewById(R.id.creditCardEditTextCcvCode);
        EditText date = findViewById(R.id.creditCardEditTextExpirationDate);

        // Pobranie informacji o użytkowniku
        Downloader downloader = new Downloader();
        downloader.setOnResultListener(result -> {
            assert result != null;
            result = Network.repairJson(result);
            JsonObject jsonObject = JsonParser.parseString(result).getAsJsonObject();
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
            if (number.getText().toString().isEmpty() || ccv.getText().toString().isEmpty() || date.getText().toString().isEmpty()) {
                Snackbar.make(v, getString(R.string.fields_empty), Snackbar.LENGTH_LONG).show();
                return;
            }

            // Utworzenie obiektu JSON
            Gson gson = new Gson();
            JsonObject jsonObject = new JsonObject();
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
                    ActivityUtil.refreshActivity(CreditCardActivity.this);
                }
                @Override
                public void getError(String error) {
                    Snackbar.make(edit, "Coś poszło nie tak", Snackbar.LENGTH_LONG).show();
                }
            });
            uploader.execute(this, Constants.CARD_DATA_EDIT, data);
        });

        // Akcja przycisku cofnij
        back.setOnClickListener(v -> finish());
    }
}
