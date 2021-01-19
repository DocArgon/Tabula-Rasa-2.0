package com.wat.tabularasa20.activities;

import android.annotation.SuppressLint;
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
import com.google.gson.JsonParser;
import com.wat.tabularasa20.R;
import com.wat.tabularasa20.data.Constants;
import com.wat.tabularasa20.data.ProductListDescription;
import com.wat.tabularasa20.utilities.Downloader;
import com.wat.tabularasa20.utilities.MathUtil;
import com.wat.tabularasa20.utilities.Network;

public class ProductDetailsActivity extends AppCompatActivity {

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        Intent inp = getIntent();
        int book_id = inp.getIntExtra("book_id", ProductListDescription.DEFAULT_PRODUCT_ID);
        int owner_id = inp.getIntExtra("owner_id", ProductListDescription.DEFAULT_PRODUCT_ID);

        ImageView photo = findViewById(R.id.productsDetailsImageViewCover);
        ImageButton back = findViewById(R.id.productsDetailsButtonBack);
        Button contact = findViewById(R.id.productsDetailsButtonSendForm);
        TextView title = findViewById(R.id.productsDetailsTextViewTitle);
        TextView author = findViewById(R.id.productsDetailsTextViewAuthor);
        TextView year = findViewById(R.id.productsDetailsTextViewYear);
        TextView publisher = findViewById(R.id.productsDetailsTextViewPublisher);
        TextView genre = findViewById(R.id.productsDetailsTextViewGenre);
        TextView info = findViewById(R.id.productsDetailsTextViewAdditionalInfo);

        Downloader productDownloader = new Downloader();
        productDownloader.setOnResultListener(resultProducts -> {
            // Utworzenie obiektu JSON z danych pobranych z internetu
            //Toast.makeText(ProductListActivity.this, result, Toast.LENGTH_LONG).show();
            assert resultProducts != null;
            resultProducts = Network.repairJson(resultProducts);
            //Toast.makeText(ProductDetailsActivity.this, resultProducts, Toast.LENGTH_LONG).show();
            //JsonObject productsJsonObject = JsonParser.parseString(resultProducts).getAsJsonObject();
            //String body = productsJsonObject.get("body").getAsString();
            //JsonArray productsJsonArray = JsonParser.parseString(body).getAsJsonArray();
            JsonArray productsJsonArray = JsonParser.parseString(resultProducts).getAsJsonArray();

            for (JsonElement productJsonElement : productsJsonArray) {
                String bm = productJsonElement.getAsJsonObject().get("zdjecie").getAsString();
                if (bm != null && !bm.isEmpty())
                    photo.setImageBitmap(MathUtil.fromBase64(bm));
                title.setText(productJsonElement.getAsJsonObject().get("tytul").getAsString());
                author.setText(productJsonElement.getAsJsonObject().get("autor").getAsString());
                year.setText(productJsonElement.getAsJsonObject().get("rok_wydania").getAsString());
                publisher.setText(productJsonElement.getAsJsonObject().get("wydawnictwo").getAsString());
                genre.setText(productJsonElement.getAsJsonObject().get("gatunek").getAsString());
                info.setText(productJsonElement.getAsJsonObject().get("opis").getAsString());
            }
        });

        if (book_id != ProductListDescription.DEFAULT_PRODUCT_ID && owner_id != ProductListDescription.DEFAULT_OWNER_ID) {
            productDownloader.execute(Constants.DETAILS_URL + String.format("?id_ksiazki=%d&id_konta=%d", book_id, owner_id));
        } else {
            Toast.makeText(this, "Nieprawidłowy identyfikator", Toast.LENGTH_LONG).show();
        }

        contact.setOnClickListener(view -> {
            Intent i = new Intent(ProductDetailsActivity.this, ChatNewActivity.class);
            i.putExtra("owner_id", owner_id);
            i.putExtra("book_id", book_id);
            i.putExtra("first", true);
            startActivity(i);
        });

        back.setOnClickListener(view -> finish());
    }
}
