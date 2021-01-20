package com.wat.tabularasa20.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.wat.tabularasa20.R;
import com.wat.tabularasa20.data.Constants;
import com.wat.tabularasa20.utilities.MathUtil;
import com.wat.tabularasa20.utilities.Preferences;
import com.wat.tabularasa20.utilities.Uploader;

import java.util.Objects;

/**
 * Aktywność dodawania nowej książki
 */
public class ProductAddActivity extends AppCompatActivity {

    private final int CAMERA_REQUEST = 1002;
    private final int GALLERY_REQUEST = 1003;
    Bitmap bitmap = null;
    ImageView photo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_add);

        // Uzyskanie dostępu do elementów graficznych
        ImageButton back = findViewById(R.id.productsAddButtonBack);
        photo = findViewById(R.id.productDetailsImageViewCover);
        Button add = findViewById(R.id.productsAddButtonSendForm);
        EditText title = findViewById(R.id.productsAddEditTextTitle);
        EditText author = findViewById(R.id.productsAddEditTextAuthor);
        EditText year = findViewById(R.id.productsAddEditTextYear);
        EditText publisher = findViewById(R.id.productsAddEditTextPublisher);
        EditText genre = findViewById(R.id.productsAddEditTextGenre);
        EditText info = findViewById(R.id.productsAddEditTextAdditionalInfo);

        back.setOnClickListener(v -> finish());

        photo.setOnClickListener(view -> {
            /*
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(new Date().toString().replace(" ", "_"), true);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
            //*/
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "Wybierz zdjęcie"), GALLERY_REQUEST);
        });

        // Akcja przycisku dodaj
        add.setOnClickListener(v -> {
            // Sprawdzenie czy nie są puste wymagane pola
            if (title.getText().toString().isEmpty() || author.getText().toString().isEmpty()) {
                Snackbar.make(v, getString(R.string.fields_empty), Snackbar.LENGTH_LONG).show();
                return;
            }

            // Utworzenie dokumentu JSON
            Gson gson = new Gson();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("title", title.getText().toString());
            jsonObject.addProperty("author", author.getText().toString());
            jsonObject.addProperty("genre", genre.getText().toString());
            jsonObject.addProperty("year", year.getText().toString());
            jsonObject.addProperty("publisher", publisher.getText().toString());
            jsonObject.addProperty("id_konta", Preferences.readAccountID(this));
            jsonObject.addProperty("description", info.getText().toString());
            if (bitmap != null) {
                jsonObject.addProperty("photo", MathUtil.bitmapToBase64(bitmap));
            } else {
                jsonObject.addProperty("photo", "");
            }
            String data = gson.toJson(jsonObject);

            // Wysłanie danych do BD
            Uploader uploader = new Uploader();
            uploader.setOnResultListener(new Uploader.UploadActions() {
                @Override
                public void getResult(String result) {
                    Snackbar.make(add, "Dodano książkę", Snackbar.LENGTH_LONG).show();
                    new CountDownTimer(3000, 3000) {
                        @Override public void onTick(long millisUntilFinished) {}
                        @Override
                        public void onFinish() {
                            finish();
                        }
                    }.start();
                }
                @Override
                public void getError(String error) {
                    Snackbar.make(add, "Coś poszło nie tak", Snackbar.LENGTH_LONG).show();
                }
            });
            uploader.execute(this, Constants.BOOK_ADD_URL, data);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            bitmap = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
            photo.setImageDrawable(new BitmapDrawable(getResources(), bitmap));
        }
        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            photo.setImageURI(uri);
            BitmapDrawable drawable = (BitmapDrawable) photo.getDrawable();
            bitmap = drawable.getBitmap();
        }
    }
}