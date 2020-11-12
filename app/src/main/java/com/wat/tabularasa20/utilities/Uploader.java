package com.wat.tabularasa20.utilities;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.wat.tabularasa20.data.Constants;
import org.json.JSONObject;

public class Uploader {

    public interface UploadActions {
        void getResult(String result);
        void getError(String error);
    }

    private UploadActions onResultListener = null;

    public void setOnResultListener (UploadActions onResultListener) {
        this.onResultListener = onResultListener;
    }

    public void execute(Context contexr, String url, String data) {
        try {
            RequestQueue queue = Volley.newRequestQueue(contexr);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Constants.REGISTER_URL, new JSONObject(data),
                response -> {
                    if (onResultListener != null)
                        onResultListener.getResult(response.toString());
                },
                error -> {
                    if (onResultListener != null)
                        onResultListener.getError(error.toString());
                });
            queue.add(jsonObjectRequest);
            queue.start();
        } catch (Exception ignore) {}
    }
}
