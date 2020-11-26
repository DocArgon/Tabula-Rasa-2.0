package com.wat.tabularasa20.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import com.wat.tabularasa20.R;

/**
 * Aktywność pisania nowej wiadomości
 */
public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages_new);

        ImageButton back = findViewById(R.id.messagesNewButtonBack);
        back.setOnClickListener(v -> finish());
    }
}
