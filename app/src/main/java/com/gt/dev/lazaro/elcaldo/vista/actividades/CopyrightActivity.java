package com.gt.dev.lazaro.elcaldo.vista.actividades;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.gt.dev.lazaro.elcaldo.R;

/**
 * Created by Lazaro on 10/9/2015.
 */
public class CopyrightActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button boton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terminosycondiciones);
        toolbar = (Toolbar) findViewById(R.id.terminos_toolbar);
        boton = (Button) findViewById(R.id.bCondicion);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adMob();

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void adMob() {
        AdView adview = (AdView) findViewById(R.id.adViewCopyRigth);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        adview.loadAd(adRequest);
    }

}
