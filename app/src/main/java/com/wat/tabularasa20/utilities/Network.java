package com.wat.tabularasa20.utilities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;

public class Network {

    /**
     * Metoda sprawdzająca czy urządzenie jest połączone do internetu
     * @return informacja czy urządzenie ma dostęp do internetu
     */
    @SuppressWarnings("deprecation") // to celowe! nie używamy Android >= 10 w którym ta metoda zostanie uznana za przestarzałą
    public static boolean isDeviceConnected (Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager != null && connectivityManager.getActiveNetworkInfo() != null;
    }
}
