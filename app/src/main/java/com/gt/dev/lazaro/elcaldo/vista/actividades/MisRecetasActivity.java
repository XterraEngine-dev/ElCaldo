package com.gt.dev.lazaro.elcaldo.vista.actividades;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.gt.dev.lazaro.elcaldo.R;
import com.gt.dev.lazaro.elcaldo.adaptadores.AdaptadorMisRecetas;
import com.gt.dev.lazaro.elcaldo.adaptadores.CategoriaMisRecetas;
import com.gt.dev.lazaro.elcaldo.modelo.HotSpot;

import java.util.ArrayList;

public class MisRecetasActivity extends AppCompatActivity {

    private TextView tvPrueba;

    private ArrayList<CategoriaMisRecetas> categoria;
    private ListView lista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_recetas);
        inciaVariables();
    }

    private void inciaVariables() {
        tvPrueba = (TextView) findViewById(R.id.tvPrueba);
        categoria = new ArrayList<>();
        lista = (ListView) findViewById(R.id.lvMisRecetas);

        HotSpot instanciaVer = new HotSpot(this);

        try {
            instanciaVer.abrir();
            String retorno = instanciaVer.getDataInfo();
            instanciaVer.cerrar();
            //arreglo.add(retorno);
            //arreglo.add("pruebas");
            //tvPrueba.setText(retorno);
            categoria.add(new CategoriaMisRecetas(retorno));

            AdaptadorMisRecetas adaptador = new AdaptadorMisRecetas(categoria, this);
            lista.setAdapter(adaptador);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
