package com.wat.tabularasa20.activities;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.wat.tabularasa20.R;
import com.wat.tabularasa20.data.Constants;
import com.wat.tabularasa20.utilities.ActivityUtil;
import com.wat.tabularasa20.utilities.MathUtil;
import com.wat.tabularasa20.utilities.Uploader;

/**
 * Aktywność rejestracji nowego użytkownika
 */
public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityUtil.changeTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access_register);

        // Uzyskanie dostępu do graficznych elementów widoku
        Button edit = findViewById(R.id.accessRegisterButtonSendForm);
        CheckBox confirm = findViewById(R.id.accessRegisterCheckoxTermsOfService);
        Spinner sex_lst = findViewById(R.id.accessRegisterSpinnerSex);
        EditText login = findViewById(R.id.accessRegisterEditTextLogin);
        EditText email = findViewById(R.id.accessRegisterEditTextEmail);
        EditText password = findViewById(R.id.accessRegisterEditTextPassword);
        EditText passwd_rep = findViewById(R.id.accessRegisterEditTextPasswordRepeat);
        EditText name = findViewById(R.id.accessRegisterEditTextFirstName);
        EditText lname = findViewById(R.id.accessRegisterEditTextLastName);
        EditText city = findViewById(R.id.accessRegisterEditTextCity);
        EditText street = findViewById(R.id.accessRegisterEditTextStreet);
        EditText phone = findViewById(R.id.accessRegisterEditTextPhoneNumber);
        EditText bday = findViewById(R.id.accessRegisterEditTextBirthDate);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.sex));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sex_lst.setAdapter(adapter);

        // Akcja przycisku edycji danych
        edit.setOnClickListener(v -> {
            // Sprawdzenie dzy pola danych nie są puste
            if (login.getText().toString().isEmpty() ||
                    password.getText().toString().isEmpty() ||
                    passwd_rep.getText().toString().isEmpty() ||
                    name.getText().toString().isEmpty() ||
                    lname.getText().toString().isEmpty() ||
                    city.getText().toString().isEmpty() ||
                    bday.getText().toString().isEmpty()) {
                Snackbar.make(v, getString(R.string.fields_empty), Snackbar.LENGTH_LONG).show();
                return;
            }

            // Sprawdzenie czy pierwsze i powtórzone hasło są identyczne
            if (!password.getText().toString().equals(passwd_rep.getText().toString())) {
                Snackbar.make(v, getString(R.string.rep_passwd_problem), Snackbar.LENGTH_LONG).show();
                return;
            }

            // Sprawdzenie czy zaakceptowano warunki korzystania z aplikacji
            if (!confirm.isChecked()) {
                Snackbar.make(v, getString(R.string.register_info_not_conf), Snackbar.LENGTH_LONG).show();
                return;
            }

            // Utworzenie dokumentu JSON
            Gson gson = new Gson();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("login", login.getText().toString());
            jsonObject.addProperty("email", email.getText().toString());
            jsonObject.addProperty("password", MathUtil.sha(password.getText().toString()));
            jsonObject.addProperty("name", name.getText().toString());
            jsonObject.addProperty("last_name", lname.getText().toString());
            jsonObject.addProperty("gender", sex_lst.getSelectedItem().toString());
            jsonObject.addProperty("city", city.getText().toString());
            jsonObject.addProperty("street", street.getText().toString());
            jsonObject.addProperty("phone", phone.getText().toString());
            jsonObject.addProperty("birthday", bday.getText().toString());
            String data = gson.toJson(jsonObject);

            // Wysłanie danych do BD
            Uploader uploader = new Uploader();
            uploader.setOnResultListener(new Uploader.UploadActions() {
                @Override
                public void getResult(String result) {
                    Snackbar.make(edit, "Zarejestrowano użytkownika", Snackbar.LENGTH_LONG).show();
                    new CountDownTimer(3000, 3000) {
                        @Override public void onTick(long millisUntilFinished) {}
                        @Override
                        public void onFinish() {
                            finish();
                        }
                    }.start();
                }
                @Override
                public void getError(String error) {
                    Snackbar.make(edit, "Coś poszło nie tak", Snackbar.LENGTH_LONG).show();
                }
            });
            uploader.execute(this, Constants.REGISTER_URL, data);
        });
    }
}
