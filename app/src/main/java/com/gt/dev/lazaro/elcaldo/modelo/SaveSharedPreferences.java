package com.gt.dev.lazaro.elcaldo.modelo;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by root on 3/07/16.
 */
public class SaveSharedPreferences {

    static final String PREF_PLATO_NAME = "plato";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setPlatoName(Context ctx, String platoName) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.commit();
    }

    public static String getPlatoName(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_PLATO_NAME, "");
    }

}
