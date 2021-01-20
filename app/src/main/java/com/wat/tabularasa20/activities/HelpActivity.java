package com.wat.tabularasa20.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageButton;
import com.wat.tabularasa20.R;

/**
 * Aktywność okna pomocy
 */
public class HelpActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        ImageButton back = findViewById(R.id.helpButtonBack);
        back.setOnClickListener(v -> finish());
    }
}
