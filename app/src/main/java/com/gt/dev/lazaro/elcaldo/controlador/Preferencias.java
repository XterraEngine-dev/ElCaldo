package com.gt.dev.lazaro.elcaldo.controlador;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

import com.gt.dev.lazaro.elcaldo.R;

/**
 * Created by Lazaro on 16/08/15.
 */
public class Preferencias extends PreferenceActivity {

    /**
     * onCreate
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
    }

    /**
     * Clase estatica de tipo publica que extiende de la clase abstracta PreferenceFragment
     */
    public static class MyPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.prefs);
        }
    }
}
