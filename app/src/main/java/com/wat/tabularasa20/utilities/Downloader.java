package com.wat.tabularasa20.utilities;

import android.os.AsyncTask;
import androidx.annotation.Nullable;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Klasa pozwalająca pobrać dokument tekstowy z internetu
 */
public class Downloader extends AsyncTask<Object, Void, String> {

    public interface DownloadActions {
        void getResult(@Nullable String result);
    }

    private DownloadActions onResultListener = null;
    private String response = null;

    public void setOnResultListener (DownloadActions onResultListener) {
        this.onResultListener = onResultListener;
    }

    /**
     * Motoda wykonywana gdy na obiekcie klasy zostanie wykonane <code>execute(String url)</code>
     * @param params adres URL strony jako <code>String</code>
     */
    @Override
    protected String doInBackground (Object... params) {
        try {
            StringBuilder sb = new StringBuilder();
            URL url = new URL((String) params[0]);
            //String passwdstring = "USERNAME:PASSWORD";
            URLConnection urlConnection = url.openConnection();
            //((HttpURLConnection) urlConnection).setRequestMethod("POST");
            //urlConnection.setRequestProperty("Authorization", "Authorization: AWS4-HMAC-SHA256 Credential=AKIAIOSFODNN7EXAMPLE/20130524/us-east-1/s3/aws4_request, SignedHeaders=host;range;x-amz-date, Signature=fe5f80f77d5fa3beca038a248ff027d0445342fe2855ddc963176630326f1024");
            int status = ((HttpURLConnection) urlConnection).getResponseCode();
            InputStream is = urlConnection.getInputStream();

            //InputStream is = url.openStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            String inputLine;
            while ((inputLine = in.readLine()) != null)
                sb.append(inputLine);
            in.close();
            return sb.toString();
        } catch (Exception ignore) {
            return ignore.toString();
        }
        //return null;
    }

    /**
     * Metoda wywołująca listener na zakończenie pobierania
     */
    @Override
    protected void onPostExecute (String str) {
        if (onResultListener != null)
            onResultListener.getResult(str);
    }

    /**
     * Getter ostatniej odpowiedzi
     * @return ostatnia odpowiedź jako <code>String</code>
     */
    @Nullable
    public String getResponse () {
        return response;
    }
}
