package com.gt.dev.lazaro.elcaldo.controlador;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.gt.dev.lazaro.elcaldo.utilidades.LruBitmapCache;

/**
 * Created by Lazarus on 08/08/2016.
 */
public class AppController extends Application {


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static final String TAG = AppController.class.getSimpleName();


    private RequestQueue mRequesstQueue;
    private ImageLoader mImageLoader;
    private Request.Priority priority;

    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;


        analytics = GoogleAnalytics.getInstance(this);

        tracker = analytics.newTracker("UA-69747362-1");

        // Provide unhandled exceptions reports. Do that first after creating the tracker
        tracker.enableExceptionReporting(true);

        // Enable Remarketing, Demographics & Interests reports
        // https://developers.google.com/analytics/devguides/collection/android/display-features
        tracker.enableAdvertisingIdCollection(true);

        // Enable automatic activity tracking for your app
        tracker.enableAutoActivityTracking(true);
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

    public void setPriority(Request.Priority priority) {
        this.priority = priority;
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequesstQueue != null) {
            mRequesstQueue.cancelAll(tag);
        }
    }

    public static Context getAppContext() {
        return mInstance.getApplicationContext();
    }

    /**
     * The Analytics singleton. The field is set in onCreate method override when the application
     * class is initially created.
     */
    private static GoogleAnalytics analytics;

    /**
     * The default app tracker. The field is from onCreate callback when the application is
     * initially created.
     */
    private static Tracker tracker;

    /**
     * Access to the global Analytics singleton. If this method returns null you forgot to either
     * set android:name="&lt;this.class.name&gt;" attribute on your application element in
     * AndroidManifest.xml or you are not setting this.analytics field in onCreate method override.
     */
    public static GoogleAnalytics analytics() {
        return analytics;
    }

    /**
     * The default app tracker. If this method returns null you forgot to either set
     * android:name="&lt;this.class.name&gt;" attribute on your application element in
     * AndroidManifest.xml or you are not setting this.tracker field in onCreate method override.
     */
    public static Tracker tracker() {
        return tracker;
    }


}
