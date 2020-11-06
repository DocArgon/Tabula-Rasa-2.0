package com.wat.tabularasa20.utilities;

import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class Downloader extends AsyncTask<Object, Void, String> {

    public interface DownloadActions {
        void getResult(String result);
    }

    private DownloadActions onResultListener = null;

    public void setOnResultListener(DownloadActions onResultListener) {
        this.onResultListener = onResultListener;
    }

    /**
     * @param params przyjmuje jeden argument, adres URL jako String
     */
    @Override
    protected String doInBackground(Object... params) {
        try {
            StringBuilder sb = new StringBuilder();
            URL url = new URL((String) params[0]);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
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
