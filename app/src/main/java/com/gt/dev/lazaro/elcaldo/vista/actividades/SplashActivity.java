package com.gt.dev.lazaro.elcaldo.vista.actividades;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gt.dev.lazaro.elcaldo.R;


/**
 * Created by Lazaro on 11/09/2015
 */
public class SplashActivity extends AppCompatActivity {

    protected static final long TIEMPO = 2000;
    private MediaPlayer miRola;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        MultiDex.install(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        miRola = MediaPlayer.create(SplashActivity.this, R.raw.splashcaldo);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean musica = sp.getBoolean("desactivar", true);

        if (musica == true)
            miRola.start();

        Thread timer = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(TIEMPO);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(new Intent(SplashActivity.this, WizardActivity.class));
                }
            }
        };
        timer.start();
    }


    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

}
