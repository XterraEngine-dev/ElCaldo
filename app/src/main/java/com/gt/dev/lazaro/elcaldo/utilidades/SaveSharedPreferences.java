package com.gt.dev.lazaro.elcaldo.utilidades;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by root on 13/06/16.
 */
public class SaveSharedPreferences {

    static final String PREF_RECIPE_NAME = "recipename";

    static SharedPreferences getSharedPreferences(Context ctx){
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setRecipeName(Context ctx, String recipeName, int picture){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_RECIPE_NAME, recipeName);
        //editor.putInt(PREF_RECIPE_NAME, picture);
        editor.commit();
    }

    public static String getRecipeName(Context ctx, int picture){
        return getSharedPreferences(ctx).getString(PREF_RECIPE_NAME, "");
        //return getSharedPreferences(ctx).getInt(PREF_RECIPE_NAME, null);
    }

}
