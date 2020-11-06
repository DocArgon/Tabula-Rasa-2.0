package com.wat.tabularasa20;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
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

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            //Toast.makeText(this, "Nie przyznano uprawnień", Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},
                    1234);
        }

        final EditText name = findViewById(R.id.editName);
        final EditText passwd = findViewById(R.id.editPassword);
        Button login = findViewById(R.id.buttonLogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    HttpURLConnection conn = null;
                    String strname = name.getText().toString();
                    String strurl = "https://uzb34f06ol.execute-api.eu-west-1.amazonaws.com/v1/?imie=" + strname.substring(strname.length() - 1);
                    Toast.makeText(MainActivity.this, strurl, Toast.LENGTH_LONG).show();
                    URL url = new URL(strurl);
                    /*
                    conn = (HttpURLConnection) url.openConnection();
                    //conn.setReadTimeout(100000);
                    //conn.setConnectTimeout(150000);
                    conn.setRequestMethod("GET");
                    //conn.setDoInput(true);
                    conn.connect();
                    InputStream is = url.openStream(); //conn.getInputStream();
                    //*/
                    InputStream is = urlToInputStream(url);
                    ByteArrayOutputStream result = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = is.read(buffer)) != -1)
                        result.write(buffer, 0, length);
                    String out = result.toString("UTF-8");
                    Toast.makeText(MainActivity.this, out, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    // https://stackoverflow.com/questions/6932369/inputstream-from-a-url
    private InputStream urlToInputStream(URL url) {
        HttpURLConnection con = null;
        InputStream inputStream = null;
        try {
            con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(15000);
            con.setReadTimeout(15000);
            con.connect();
            int responseCode = con.getResponseCode();
            if (responseCode < 400 && responseCode > 299) {
                String redirectUrl = con.getHeaderField("Location");
                try {
                    URL newUrl = new URL(redirectUrl);
                    return urlToInputStream(newUrl);
                } catch (MalformedURLException e) {
                    URL newUrl = new URL(url.getProtocol() + "://" + url.getHost() + redirectUrl);
                    return urlToInputStream(newUrl);
                }
            }
            inputStream = con.getInputStream();
            return inputStream;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1234: {
                if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    //finish();
                } else {
                }
            }
        }
    }
}
