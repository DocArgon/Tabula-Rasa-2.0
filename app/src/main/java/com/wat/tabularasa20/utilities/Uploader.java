package com.wat.tabularasa20.utilities;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;

/**
 * Klasa pozwalająca wysłać JSON na serwer
 */
public class Uploader {

    /**
     * Interfejs wumagany do utworzenia akcji na koniec wysyłania
     */
    public interface UploadActions {
        void getResult(String result);
        void getError(String error);
    }

    private UploadActions onResultListener = null;

    /**
     * Setter akcji na koniec wysyłania
     */
    public void setOnResultListener (UploadActions onResultListener) {
        this.onResultListener = onResultListener;
    }

    /**
     * Główna metoda pozwalająca wysłać JSON na wskazany adres sieciowy
     * @param url adres na który dane mają być wysłane
     * @param data dane do wysłania
     */
    public void execute(Context context, String url, String data) {
        try {
            RequestQueue queue = Volley.newRequestQueue(context);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(data),
                response -> {
                    if (onResultListener != null)
                        onResultListener.getResult(response.toString());
                },
                error -> {
                    if (onResultListener != null)
                        onResultListener.getError(error.getMessage());
                });
            queue.add(jsonObjectRequest);
            queue.start();
        } catch (Exception ignore) {}
    }
}
