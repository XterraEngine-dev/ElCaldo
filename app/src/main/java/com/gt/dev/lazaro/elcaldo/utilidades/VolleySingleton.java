package com.gt.dev.lazaro.elcaldo.utilidades;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.gt.dev.lazaro.elcaldo.controlador.AppController;

public class VolleySingleton {

	public static final String TAG = VolleySingleton.class.getName();

	private Context mContext;
	private RequestQueue mRequestQueue;
	private ImageLoader mImageLoader;

	private static VolleySingleton mInstance;

	private VolleySingleton() {

		mRequestQueue = Volley.newRequestQueue(AppController.getAppContext());
		mImageLoader = new ImageLoader(this.mRequestQueue, new ImageLoader.ImageCache() {
			private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);
			public void putBitmap(String url, Bitmap bitmap) {
				mCache.put(url, bitmap);
			}
			public Bitmap getBitmap(String url) {
				return mCache.get(url);
			}
		});

	}

	public static VolleySingleton getInstance() {
		if (mInstance == null) {
			mInstance = new VolleySingleton();
		}
		return mInstance;
	}

	public void init(Context context) {
		mContext = context;
	}

	public RequestQueue getRequestQueue() {
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(mContext);
		}
		return mRequestQueue;
	}

	public ImageLoader getImageLoader() {
		getRequestQueue();
		if (mImageLoader == null) {
			mImageLoader = new ImageLoader(this.mRequestQueue,
					new LruBitmapCache());
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
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}


}
