package com.wat.tabularasa20.activities;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.mattkula.secrettextview.SecretTextView;
import com.wat.tabularasa20.R;

/**
 * Aktywność pokazująca ekran powitalny
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        SecretTextView secretTextView = findViewById(R.id.splashTextViewHello);
        int interval = 1200;

        secretTextView.setIsVisible(false);
        secretTextView.setDuration(interval);
        secretTextView.setText(getString(R.string.hello_msg));

        new CountDownTimer(2 * interval + 100, interval) {
            @Override
            public void onTick(long millisUntilFinished) {
                secretTextView.toggle();
            }
            @Override
            public void onFinish() {
                finish();
            }
        }.start();
    }
}
