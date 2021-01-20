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
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wat.tabularasa20.R;
import com.wat.tabularasa20.data.Constants;
import com.wat.tabularasa20.data.ProductListDescription;
import com.wat.tabularasa20.utilities.Downloader;
import com.wat.tabularasa20.utilities.MathUtil;
import com.wat.tabularasa20.utilities.Preferences;
import com.wat.tabularasa20.utilities.Uploader;

import java.util.Iterator;
import java.util.Set;

/**
 * Aktywność pisania nowej wiadomości
 */
public class ChatNewActivity extends AppCompatActivity {

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages_new);

        Intent inp = getIntent();
        int owner_id = inp.getIntExtra("owner_id", ProductListDescription.DEFAULT_OWNER_ID);
        int book_id = inp.getIntExtra("book_id", ProductListDescription.DEFAULT_PRODUCT_ID);
        boolean first_message = inp.getBooleanExtra("first", false);

        ImageButton back = findViewById(R.id.messagesNewButtonBack);
        TextView user = findViewById(R.id.messagesNewTextViewUsername);
        EditText message = findViewById(R.id.messagesNewEditTextMessage);
        Button send = findViewById(R.id.messagesNewButtonSendForm);

        // Pobranie informacji o użytkowniku
        Downloader userDownloader = new Downloader();
        userDownloader.setOnResultListener(result -> {
            // Utworzenie obiektu JSON z danych pobranych z internetu
            assert result != null;
            JsonObject productsJsonObject = JsonParser.parseString(result).getAsJsonObject();
            String name = productsJsonObject.get("Imie").getAsString();
            user.setText(getString(R.string.chat_with, name));
        });

        // wykona się jeśli pierwsza wiadomość
        Downloader bookDownloader = new Downloader();
        bookDownloader.setOnResultListener(result -> {
            // Utworzenie obiektu JSON z danych pobranych z internetu
            assert result != null;
            JsonArray productsJsonArray = JsonParser.parseString(result).getAsJsonArray();
            String name = productsJsonArray.get(0).getAsJsonObject().get("tytul").getAsString();

            // TODO przenieść do strings.xml
            message.setText(getResources().getString(R.string.first_message, name));
            message.setSelection(message.getText().length());
        });

        if (owner_id != ProductListDescription.DEFAULT_OWNER_ID) {
            userDownloader.execute(Constants.ACCOUNT_GET_URL + String.format("?id_klienta=%d&id_konta=%d", ProductListDescription.DEFAULT_OWNER_ID , owner_id));
        } else {
            Toast.makeText(this, "Niepoprawny identyfikator użytkownika", Toast.LENGTH_SHORT).show();
        }

        if (first_message)
            bookDownloader.execute(Constants.DETAILS_URL + String.format("?id_ksiazki=%d&id_konta=%d", book_id, owner_id));

        send.setOnClickListener(v -> {
            String msg = message.getText().toString();
            if (msg.isEmpty())
                return;

            Gson gson = new Gson();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("Id_ksiazki", book_id);
            jsonObject.addProperty("Id_wysylajacego", Preferences.readAccountID(ChatNewActivity.this));
            jsonObject.addProperty("Id_odbierajacego", owner_id);
            jsonObject.addProperty("Tresc", MathUtil.toBase64(msg));
            String data = gson.toJson(jsonObject);

            Uploader uploader = new Uploader();
            uploader.setOnResultListener(new Uploader.UploadActions() {
                @Override
                public void getResult(String result) {
                    Snackbar.make(message, "Wysłano wiadomość", Snackbar.LENGTH_LONG).show();
                    new CountDownTimer(1000, 1000) {
                        @Override public void onTick(long millisUntilFinished) {}
                        @Override
                        public void onFinish() {
                            // TODO dorobić zliczanie
                            /*
                            Set<Preferences.ChatInfo> chats = Preferences.readChatInfo(ChatNewActivity.this);
                            Preferences.ChatInfo template = new Preferences.ChatInfo(20, 3, 0);
                            if (!chats.contains(template)) {
                                chats.add(new Preferences.ChatInfo(20, 3, 0));
                            } else {
                                for (Iterator<Preferences.ChatInfo> it = chats.iterator(); it.hasNext();) {
                                    Preferences.ChatInfo info = it.next();
                                    if (info.equals(template))
                                        info.msgCount++;
                                }
                            }
                            Preferences.saveChatInfo(ChatNewActivity.this, chats);
                            //*/

                            Intent i = new Intent(ChatNewActivity.this, ChatSingleActivity.class);
                            i.putExtra("owner_id", owner_id);
                            i.putExtra("book_id", book_id);
                            startActivity(i);
                            finish();
                        }
                    }.start();
                }
                @Override
                public void getError(String error) {
                    Snackbar.make(message, "Coś poszło nie tak", Snackbar.LENGTH_LONG).show();
                }
            });
            uploader.execute(this, Constants.MESSAGE_SEND, data);
        });


        back.setOnClickListener(v -> {
            if (!first_message) {
                Intent i = new Intent(ChatNewActivity.this, ChatSingleActivity.class);
                i.putExtra("owner_id", owner_id);
                i.putExtra("book_id", book_id);
                startActivity(i);
            }
            finish();
        });
    }
}
