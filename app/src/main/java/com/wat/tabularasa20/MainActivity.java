package com.wat.tabularasa20;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.wat.tabularasa20.activities.HomeActivity;
import com.wat.tabularasa20.data.Constants;
import com.wat.tabularasa20.utilities.Downloader;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);


		int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
		if (permission != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET},1234);
		}

		// TODO sprawdzić połączenie internetowe

		final ImageView iv = findViewById(R.id.profilePicture);
		final EditText name = findViewById(R.id.mainEditTextName);
		final EditText pass = findViewById(R.id.mainEditTextPassword);
		final Button login = findViewById(R.id.mainButtonLogin);

		// Akcja downloadera
		Downloader downloader = new Downloader();
		downloader.setOnResultListener(new Downloader.DownloadActions() {
			@Override
			public void getResult(String result) {
				Intent intent = new Intent(MainActivity.this, HomeActivity.class);
				intent.putExtra("name", name.getText().toString());
				intent.putExtra("result", result);
				startActivity(intent);
				finish(); // Klasy Downloader nie wykorzystywać 2 raz - wymaga reinicjalizacji dlatego kończę aktywność
			}
		});

		// Akcja przycisku
		login.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String strname = name.getText().toString();
				String strpass = pass.getText().toString();

				// Usunę to, tak na wszelki
                /*
                if (strpass.equals("2137")) {
                    iv.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.flan2));
                    new CountDownTimer(100, 50) {
                        public void onTick(long millisUntilFinished) {}
                        public void onFinish() {
                            iv.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), android.R.drawable.btn_star_big_off));
                        }
                    }.start();
                }
                //*/

				if (!strname.isEmpty() && !strpass.isEmpty()) {
					String strurl = Constants.WELCOME_URL + strname.substringname.length() - 1);
					downloader.execute(strurl);
				} else {
					// TODO powiadomienie o wymogu danych logowania
				}
			}
		});
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
		switch (requestCode) {
			case 1234: {
				if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
					Toast.makeText(this, "Nie przyznano uprawnień", Toast.LENGTH(str_LONG).show();
					//finish();
				}
			}
		}
	}
}
