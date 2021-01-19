package com.wat.tabularasa20.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.wat.tabularasa20.R;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        TextView help = findViewById(R.id.helpTextView);
        ImageButton back = findViewById(R.id.helpButtonBack);

        back.setOnClickListener(v -> finish());
    }
}
