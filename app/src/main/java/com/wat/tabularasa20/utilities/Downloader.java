package com.wat.tabularasa20.utilities;

import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import android.util.Base64;

public class Downloader extends AsyncTask<Object, Void, String> {

    public interface DownloadActions {
        void getResult(String result);
    }

    private DownloadActions onResultListener = null;

    public void setOnResultListener(DownloadActions onResultListener) {
        this.onResultListener = onResultListener;
    }

    /**
     * @param params adres URL strony : String
     */
    @Override
    protected String doInBackground(Object... params) {
        try {
            StringBuilder sb = new StringBuilder();
            URL url = new URL((String) params[0]);
            /*
            String passwdstring = "USERNAME:PASSWORD";
            String encoding = Base64.encodeToString(passwdstring.getBytes(), Base64.DEFAULT);
            URLConnection urlConnection = url.openConnection();
            //urlConnection.setRequestProperty("Authorization", "Basic " + encoding);
            urlConnection.setRequestProperty("Authorization", "Authorization: AWS4-HMAC-SHA256 Credential=AKIAIOSFODNN7EXAMPLE/20130524/us-east-1/s3/aws4_request, SignedHeaders=host;range;x-amz-date, Signature=fe5f80f77d5fa3beca038a248ff027d0445342fe2855ddc963176630326f1024");
            InputStream is = urlConnection.getInputStream();
            //*/
            InputStream is = url.openStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            String inputLine;
            while ((inputLine = in.readLine()) != null)
                sb.append(inputLine);
            in.close();
            return sb.toString();
        } catch (Exception ignore) {}
        return null;
    }

    @Override
    protected void onPostExecute(String str) {
        if (onResultListener != null)
            onResultListener.getResult(str);
    }
}
