package com.wat.tabularasa20.activities;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.wat.tabularasa20.R;

public class CreditCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card);

        Button back = findViewById(R.id.creditCardButtonBack);

        back.setOnClickListener(v -> finish());
    }
}
