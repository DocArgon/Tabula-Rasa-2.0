package com.wat.tabularasa20.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.wat.tabularasa20.R;

public class AddBookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_add);

        final Button back = findViewById(R.id.productsDetailsButtonBack);
        final Button add = findViewById(R.id.productsDetailsButtonSendForm);
        final EditText title = findViewById(R.id.productsDetailsEditTextTitle);
        final EditText author = findViewById(R.id.productsDetailsEditTextAuthor);
        final EditText year = findViewById(R.id.productsDetailsEditTextYear);
        final EditText publisher = findViewById(R.id.productsDetailsEditTextPublisher);
        final EditText info = findViewById(R.id.productsDetailsEditTextAdditionalInfo);

        back.setOnClickListener(v -> finish());

        add.setOnClickListener(v -> {
            if (title.getText().toString().isEmpty() || author.getText().toString().isEmpty() ||
                    publisher.getText().toString().isEmpty()) {
                Snackbar.make(v, getString(R.string.fields_empty), Snackbar.LENGTH_LONG).show();
                return;
            }
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
