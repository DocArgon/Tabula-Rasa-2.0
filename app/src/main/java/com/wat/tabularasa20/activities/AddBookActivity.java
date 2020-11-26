package com.wat.tabularasa20.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.wat.tabularasa20.R;

/**
 * Aktywność dodawania nowej książki
 */
public class AddBookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_add);

        // Uzyskanie dostępu do elementów graficznych
        ImageButton back = findViewById(R.id.productsAddButtonBack);
        Button add = findViewById(R.id.productsAddButtonSendForm);
        EditText title = findViewById(R.id.productsAddEditTextTitle);
        EditText author = findViewById(R.id.productsAddEditTextAuthor);
        EditText year = findViewById(R.id.productsAddEditTextYear);
        EditText publisher = findViewById(R.id.productsAddEditTextPublisher);
        EditText info = findViewById(R.id.productsAddEditTextAdditionalInfo);

        back.setOnClickListener(v -> finish());

        // Akcja przycisku dodaj
        add.setOnClickListener(v -> {
            // Sprawdzenie czy nie są puste wymagane pola
            if (title.getText().toString().isEmpty() || author.getText().toString().isEmpty() ||
                    publisher.getText().toString().isEmpty()) {
                Snackbar.make(v, getString(R.string.fields_empty), Snackbar.LENGTH_LONG).show();
                return;
            }

            // Utworzenie dokumentu JSON
            Gson gson = new Gson();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("title", title.getText().toString());
            jsonObject.addProperty("author", author.getText().toString());
            jsonObject.addProperty("year", year.getText().toString());
            jsonObject.addProperty("publisher", publisher.getText().toString());
            jsonObject.addProperty("description", info.getText().toString());

            String data = gson.toJson(jsonObject);
            Toast.makeText(AddBookActivity.this, data, Toast.LENGTH_LONG).show();
        });
    }
}
