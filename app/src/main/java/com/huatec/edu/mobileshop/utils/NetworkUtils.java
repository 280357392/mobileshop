package com.huatec.edu.mobileshop.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtils {

    public static final int WIFI = 2;
    public static final int MOBILE = 1;
    public static final int NOTNETWORK = 0;

    /**
     * WIFI/MOBILE/NOTNETWORK
     *
     * @param activity
     * @return
     */
    public static int isNetworkAvailable(Activity activity) {
        ConnectivityManager connMgr = (ConnectivityManager) activity.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        if (activeInfo != null && activeInfo.isConnected()) {
            boolean wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
            boolean mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            if (wifiConnected) {
                //WiFi连接
                return WIFI;
            } else if (mobileConnected) {
                //移动数据连接
                return MOBILE;
            }
        }
        //没有无线或移动连接。
        return NOTNETWORK;
    }
}
