package com.wat.tabularasa20;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
/*
import com.microsoft.graph.models.extensions.IGraphServiceClient;
import com.microsoft.graph.models.extensions.List;
import com.microsoft.graph.models.extensions.PublicClientApplication;
import com.microsoft.graph.requests.extensions.GraphServiceClient;
import com.microsoft.identity.client.IAccount;
//*/

public class MainActivity extends AppCompatActivity {

    /*
    String[] SCOPES = {"https://graph.microsoft.com/.default"};
    PublicClientApplication sampleApp = new PublicClientApplication(
            this.getApplicationContext(),
            R.raw.auth_config);

    sampleApp.getAccounts(new PublicClientApplication.AccountsLoadedCallback() {
        @Override
        public void onAccountsLoaded(final List<IAccount> accounts) {

            if (accounts.isEmpty() && accounts.size() == 1) {
                sampleApp.acquireTokenSilentAsync(SCOPES, accounts.get(0), getAuthSilentCallback());
            } else {
            }
        }
    });
    //*/
    //sampleApp.acquireToken(getActivity(), SCOPES, getAuthInteractiveCallback());
    //*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //IGraphServiceClient graphClient = GraphServiceClient.builder().authenticationProvider( authProvider ).buildClient();
        //List list = graphClient.sites("TabulaRasa20").lists("Klient").buildRequest().get();

        final EditText name = findViewById(R.id.editName);
        final EditText passwd = findViewById(R.id.editPassword);
        Button login = findViewById(R.id.buttonLogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    HttpURLConnection conn = null;
                    String strname = name.getText().toString();
                    URL url = new URL("https://uzb34f06ol.execute-api.eu-west-1.amazonaws.com/v1/?imie=" + strname.substring(strname.length() - 2, strname.length() - 1));
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(100000); //Milliseconds
                    conn.setConnectTimeout(150000); //Milliseconds
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    int len = is.available();
                    byte[] bytes = new byte[len];
                    is.read(bytes, 0, len);
                    String out = new String(bytes);
                    Toast.makeText(MainActivity.this, out, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
