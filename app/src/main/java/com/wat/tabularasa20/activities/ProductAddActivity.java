package com.wat.tabularasa20.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.wat.tabularasa20.R;
import com.wat.tabularasa20.data.Constants;
import com.wat.tabularasa20.utilities.Uploader;

import java.io.ByteArrayOutputStream;
import java.util.Date;

/**
 * Aktywność dodawania nowej książki
 */
public class ProductAddActivity extends AppCompatActivity {

    private final int CAMERA_REQUEST = 1002;
    Bitmap bitmap = null;
    ImageView photo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_add);

        // Uzyskanie dostępu do elementów graficznych
        ImageButton back = findViewById(R.id.productsAddButtonBack);
        photo = findViewById(R.id.productsAddImageViewCover);
        Button add = findViewById(R.id.productsAddButtonSendForm);
        EditText title = findViewById(R.id.productsAddEditTextTitle);
        EditText author = findViewById(R.id.productsAddEditTextAuthor);
        EditText year = findViewById(R.id.productsAddEditTextYear);
        EditText publisher = findViewById(R.id.productsAddEditTextPublisher);
        EditText info = findViewById(R.id.productsAddEditTextAdditionalInfo);

        back.setOnClickListener(v -> finish());

        photo.setOnClickListener(view -> {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(new Date().toString().replace(" ", "_"), true);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        });

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
            if (bitmap != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] imageBytes = baos.toByteArray();
                String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                jsonObject.addProperty("picture", imageString);
            } else {
                jsonObject.addProperty("picture", "");
            }
            String data = gson.toJson(jsonObject);

            // Wysłanie danych do BD
            Uploader uploader = new Uploader();
            uploader.setOnResultListener(new Uploader.UploadActions() {
                @Override
                public void getResult(String result) {
                    Toast.makeText(ProductAddActivity.this, "Echo " + result, Toast.LENGTH_LONG).show();
                }
                @Override
                public void getError(String error) {
                    Toast.makeText(ProductAddActivity.this, error, Toast.LENGTH_LONG).show();
                }
            });
            uploader.execute(this, Constants.BOOK_ADD_URL, data);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            bitmap = (Bitmap) data.getExtras().get("data");
            photo.setImageDrawable(new BitmapDrawable(getResources(), bitmap));
        }
    }
}
