package com.gt.dev.lazaro.elcaldo.controlador;

import android.app.Application;

/**
 * Created by Lazarus on 14/08/2016.
 */
public class MyApplication extends Application {

    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConectivityReceiver.ConnectivityReceiverListener listener) {
        ConectivityReceiver.connectivityReceiverListener = listener;
    }

}
