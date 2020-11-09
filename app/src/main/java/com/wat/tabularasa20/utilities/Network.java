package com.wat.tabularasa20.utilities;

import android.content.Context;
import android.net.ConnectivityManager;

public class Network {

    public static boolean isDeviceConnected (Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager != null && connectivityManager.getActiveNetworkInfo() != null;
    }
}
