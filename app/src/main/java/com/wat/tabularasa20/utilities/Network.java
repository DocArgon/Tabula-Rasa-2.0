package com.wat.tabularasa20.utilities;

import android.content.Context;
import android.net.ConnectivityManager;

public class Network {

    /**
     * Metoda sprawdzająca czy urządzenie jest połączone do internetu
     * @return informacja czy urządzenie ma dostęp do internetu
     */
    public static boolean isDeviceConnected (Context context) {
        // W logu jest warning o tym, że to przestarzała metoda, ale wg dokumentacji dopiero od Android 10
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager != null && connectivityManager.getActiveNetworkInfo() != null;
    }
}
