package com.wat.tabularasa20.activities;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import com.wat.tabularasa20.R;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        final Button back = findViewById(R.id.backMessage);
        back.setOnClickListener(v -> finish());
    }
}
