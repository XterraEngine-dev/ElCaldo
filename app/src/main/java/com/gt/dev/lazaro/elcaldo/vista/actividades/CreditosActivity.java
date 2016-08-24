package com.gt.dev.lazaro.elcaldo.vista.actividades;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;

import com.gt.dev.lazaro.elcaldo.R;
import com.gt.dev.lazaro.elcaldo.adaptadores.CategoriaCreditos;

import java.util.ArrayList;

/**
 * Created by Lazaro on 10/9/2015.
 */

/**
 * Clase CreditosActivity que extiende de la superclase AppCompatActivity
 */
public class CreditosActivity extends AppCompatActivity {

    private Button boton;
    private Toolbar toolbar;
    private ListView lista;
    private ArrayList<CategoriaCreditos> categoria;

    /**
     * onCreate
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creditos);
        toolbar = (Toolbar) findViewById(R.id.toolbar_creditos);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
}
