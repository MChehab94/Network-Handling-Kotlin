package mchehab.com.networkhandling

import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiManager

/**
 * Created by muhammadchehab on 11/3/17.
 */
object NetworkUtil {
    /**
     * Checks if there's internet connection on the phone
     * @param context To initiate [ConnectivityManager]
     * @return True if network is available
     */
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager
                .activeNetworkInfo.isConnected
    }

    /**
     * Checks if Wifi is enabled
     * @param context To initiate [ConnectivityManager]
     * @return True if phone is using Wifi, false otherwise
     */
    fun isWifiNetwork(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as
                ConnectivityManager
        return connectivityManager.activeNetworkInfo.type == ConnectivityManager.TYPE_WIFI
    }

    /**
     * Helper method to enable Wifi
     * @param context To initiate [WifiManager]
     */
    fun enableWifi(context: Context) {
        val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as
                WifiManager
        wifiManager.isWifiEnabled = true
    }
}