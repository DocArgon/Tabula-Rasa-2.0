package com.wat.tabularasa20.utilities;

import android.content.Context;
import android.net.ConnectivityManager;

import androidx.annotation.NonNull;

/**
 * Klasa pomocnica zawierająca metody związane z obsługą sieci
 */
public class Network {

    /**
     * Metoda sprawdzająca czy urządzenie jest połączone do internetu
     * @return informacja czy urządzenie ma dostęp do internetu
     */
    @SuppressWarnings("deprecation") // to celowe! nie używamy Android >= 10 w którym ta metoda zostanie uznana za przestarzałą
    public static boolean isDeviceConnected (@NonNull Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager != null && connectivityManager.getActiveNetworkInfo() != null;
    }

    /**
     * Metoda "naprawiająca" niepoprawne JSONy
     */
    public static String repairJson (@NonNull String json) {
        String result = json.replaceAll("\\\\\"", "\"");  // \" -> "
        result = result.replaceAll("\"\\[", "\\[").replaceAll("]\"", "]"); // "[ ]" -> [ ]
        result = result.replaceAll("\\\\\\\\", "\\\\"); // \\ -> \
        if (result.startsWith("\""))
            result = result.substring(1);
        if (result.endsWith("\""))
            result = result.substring(0, result.length() - 1);
        return result;
    }
}
