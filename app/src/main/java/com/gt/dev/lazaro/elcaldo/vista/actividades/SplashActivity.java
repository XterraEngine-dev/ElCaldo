package com.gt.dev.lazaro.elcaldo.vista.actividades;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;

import com.gt.dev.lazaro.elcaldo.R;


/**
 * Created by Lazaro on 11/09/2015
 */
public class SplashActivity extends AppCompatActivity {

    protected static final long TIEMPO = 2000;
    private MediaPlayer miRola;

    /**
     * Metodo para las compativilidad con versiones inferiores a la api 21
     *
     * @param newBase
     */
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        MultiDex.install(this);
    }

    /**
     * Metodo que inicliaza metodo
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        laRola();
        caldoTiomer();
    }

    /**
     * Metodo para la canción mp3 en el splash
     */
    private void laRola() {
        miRola = MediaPlayer.create(SplashActivity.this, R.raw.splashcaldo);

        //Obtenemos el sharedPreferences de activación de musica
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean musica = sp.getBoolean("desactivar", true);

        //si musica esta activada segun sp entonces miRola inicia -> start
        if (musica == true)
            miRola.start();
    }

    /**
     * Metodo donde ejecutamos el timer para determinar su tiempo de vida
     */
    private void caldoTiomer() {
        Thread timer = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    //2 segundos
                    sleep(TIEMPO);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }
            }
        };
        timer.start();
    }

    /**
     * Metodo que realiza cuando entre en pausa
     */
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    /**
     * Indica que realiza cuando el usuario presiona hacia atras
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
