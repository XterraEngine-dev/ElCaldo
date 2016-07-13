package com.gt.dev.lazaro.elcaldo.vista.actividades;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.gt.dev.lazaro.elcaldo.R;
import com.gt.dev.lazaro.elcaldo.adaptadores.AdaptadorCreditos;
import com.gt.dev.lazaro.elcaldo.adaptadores.CategoriaCreditos;

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
        //boton = (Button) findViewById(R.id.btnAceptar);
        //lista = (ListView) findViewById(R.id.lvCreditos);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //categoria = new ArrayList<CategoriaCreditos>();

        //categoria.add(new CategoriaCreditos(getString(R.string.dev_about), "Fernando Lazaro", "www.facebook.com/lazarodev", R.drawable.lazaro));
        //categoria.add(new CategoriaCreditos(getString(R.string.agrada_text), "", "", R.drawable.lazarus_coffe));

        //AdaptadorCreditos adaptador = new AdaptadorCreditos(categoria, getApplication());
        //lista.setAdapter(adaptador);

        /*boton.setOnClickListener(new View.OnClickListener() {
            /**
             * @param view
             */
        //@Override
        //public void onClick (View view){
/*            finish();
        }
    }

    );*/
    }

}
