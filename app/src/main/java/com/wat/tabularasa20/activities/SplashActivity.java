package com.wat.tabularasa20.activities;

import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.appcompat.app.AppCompatActivity;
import com.mattkula.secrettextview.SecretTextView;
import com.wat.tabularasa20.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final SecretTextView secretTextView = findViewById(R.id.splashTextViewHello);
        secretTextView.setIsVisible(false);
        secretTextView.setDuration(1000);
        secretTextView.setText(getString(R.string.hello_msg));
        new CountDownTimer(2100, 1000) {
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
