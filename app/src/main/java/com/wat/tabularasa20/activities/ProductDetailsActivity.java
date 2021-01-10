package com.wat.tabularasa20.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wat.tabularasa20.R;
import com.wat.tabularasa20.data.Constants;
import com.wat.tabularasa20.data.ProductListDescription;
import com.wat.tabularasa20.utilities.Downloader;

public class ProductDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        Intent inp = getIntent();
        int id = inp.getIntExtra("book_id", ProductListDescription.DEFAULT_PRODUCY_ID);

        ImageView photo = findViewById(R.id.productsDetailsImageViewCover);
        ImageButton back = findViewById(R.id.productsDetailsButtonBack);
        Button contact = findViewById(R.id.productsDetailsButtonSendForm);
        TextView title = findViewById(R.id.productsDetailsTextViewTitle);
        TextView author = findViewById(R.id.productsDetailsTextViewAuthor);
        TextView year = findViewById(R.id.productsDetailsTextViewYear);
        TextView publisher = findViewById(R.id.productsDetailsTextViewPublisher);
        TextView info = findViewById(R.id.productsDetailsTextViewAdditionalInfo);

        // TODO owner !
        final int ownerID = 1;

        Downloader productDownloader = new Downloader();
        productDownloader.setOnResultListener(resultProducts -> {
            // Utworzenie obiektu JSON z danych pobranych z internetu
            //Toast.makeText(ProductListActivity.this, result, Toast.LENGTH_LONG).show();
            assert resultProducts != null;
            //Toast.makeText(ProductDetailsActivity.this, resultProducts, Toast.LENGTH_LONG).show();
            JsonObject productsJsonObject = JsonParser.parseString(resultProducts).getAsJsonObject();
            String body = productsJsonObject.get("body").getAsString();
            JsonArray productsJsonArray = JsonParser.parseString(body).getAsJsonArray();

            for (JsonElement productJsonElement : productsJsonArray) {
                if (productJsonElement.getAsJsonObject().get("Id_ksiazki").getAsInt() == id) {
                    title.setText(productJsonElement.getAsJsonObject().get("Tytul").getAsString());
                    //.setText(productJsonElement.getAsJsonObject().get("Gatunek").getAsString());
                    author.setText(productJsonElement.getAsJsonObject().get("Autor").getAsString());
                    year.setText(productJsonElement.getAsJsonObject().get("Rok_wydania").getAsString());
                    publisher.setText(productJsonElement.getAsJsonObject().get("Wydawnictwo").getAsString());
                    //info.setText(productJsonElement.getAsJsonObject().get("").getAsString());
                }
            }
        });

        if (id != ProductListDescription.DEFAULT_PRODUCY_ID) {
            productDownloader.execute(Constants.BOOKS_GET_URL);
        } else {
            Toast.makeText(this, "Nieprawidłowy identyfikator", Toast.LENGTH_LONG).show();
        }

        contact.setOnClickListener(view -> {
            Intent i = new Intent(ProductDetailsActivity.this, ChatActivity.class);
            i.putExtra("user_id", ownerID);
            startActivity(i);
        });

        back.setOnClickListener(view -> finish());
    }
}
