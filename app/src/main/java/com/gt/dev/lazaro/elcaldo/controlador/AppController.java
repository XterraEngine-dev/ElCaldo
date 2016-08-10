package com.gt.dev.lazaro.elcaldo.controlador;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.gt.dev.lazaro.elcaldo.utilidades.LruBitmapCache;

/**
 * Created by Lazarus on 08/08/2016.
 */
public class AppController extends Application {


    public static final String TAG = AppController.class.getSimpleName();

    private RequestQueue mRequesstQueue;
    private ImageLoader mImageLoader;

    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequesstQueue == null) {
            mRequesstQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequesstQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequesstQueue, new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequesstQueue != null) {
            mRequesstQueue.cancelAll(tag);
        }
    }

}
