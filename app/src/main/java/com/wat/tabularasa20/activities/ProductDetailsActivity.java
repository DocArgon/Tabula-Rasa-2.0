package com.wat.tabularasa20.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.wat.tabularasa20.R;

public class ProductDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);


        Intent inp = getIntent();
        String name = inp.getStringExtra("name");

        TextView nameText = findViewById(R.id.productsDetailsTextViewTitle);
        if (!name.isEmpty())
            nameText.setText(name);

        Button back = findViewById(R.id.productsDetailsButtonBack);

        back.setOnClickListener(view -> finish());

    }
}
