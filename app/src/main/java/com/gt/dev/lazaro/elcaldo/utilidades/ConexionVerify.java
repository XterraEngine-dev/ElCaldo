package com.gt.dev.lazaro.elcaldo.utilidades;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by Lazarus on 14/08/2016.
 */
public class ConexionVerify {

    public static boolean isNetworkAvailable(Context context) {
        boolean connected = false;

        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            connected = networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
            Log.i("CONEXION", "RED HABILITADA" + connected);
        } catch (Exception e) {
            Log.i("CONEXION", "RED DESHABILITADA: FALSE: " + e.toString());
        }
        return connected;
    }
}
