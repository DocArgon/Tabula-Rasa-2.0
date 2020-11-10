package com.wat.tabularasa20.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import com.wat.tabularasa20.R;

public class AddBookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        final Button back = findViewById(R.id.addBookButtonBack);
        back.setOnClickListener(v -> finish());
    }
}
