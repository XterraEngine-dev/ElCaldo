package com.gt.dev.lazaro.elcaldo.vista.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.gt.dev.lazaro.elcaldo.R;
import com.gt.dev.lazaro.elcaldo.adaptadores.AdaptadorCategoria;
import com.gt.dev.lazaro.elcaldo.adaptadores.Categoria;
import com.gt.dev.lazaro.elcaldo.modelo.DBManager;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;

import java.util.ArrayList;

/**
 * Created by Fernnado Lazaro
 */
public class OtrasComidasActivity extends AppCompatActivity {

    DBManager dbManager;
    private ListView lista;
    private ArrayList<Categoria> categoria;
    private Toolbar toolbar;
    private FloatingActionButton boton;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otras_comidas);
        dbManager = new DBManager(this);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        lista = (ListView) findViewById(R.id.lv4);

        /*boton = new FloatingActionButton.Builder(this).setBackgroundDrawable(R.drawable.plusmaterial).build();
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OtrasComidasActivity.this, AgregarRecetaActivity.class));
            }
        });*/

        categoria = new ArrayList<Categoria>();
        //16 platos tipicos en otrascomidas
        categoria.add(new Categoria("Tortillas de maíz", "Ciudad de Guatemala", R.drawable.tormaiz));
        categoria.add(new Categoria("Churrasco", "Ciudad de Guatemala", R.drawable.churrasco));
        categoria.add(new Categoria("Chirmol", "Ciudad de Guatemala", R.drawable.chirmol));
        categoria.add(new Categoria("Chiles rellenos", "Ciudad de Guatemala", R.drawable.rellenos));
        categoria.add(new Categoria("Chancletas", "Ciudad de Guatemala", R.drawable.chanquetla));
        categoria.add(new Categoria("Frijoles colorados", "Ciudad de Guatemala", R.drawable.colados));
        categoria.add(new Categoria("Yuca con chicharron", "Ciudad de Guatemala", R.drawable.yucaconchicharron));
        categoria.add(new Categoria("Verduras en escabeche", "Ciudad de Guatemala", R.drawable.verduras));
        categoria.add(new Categoria("Enchiladas", "Ciudad de Guatemala", R.drawable.enchi));
        categoria.add(new Categoria("Frijoles volteados", "Ciudad de Guatemala", R.drawable.volteados));
        categoria.add(new Categoria("Frijoles negros colados", "Ciudad de Guatemala", R.drawable.colados));
        categoria.add(new Categoria("Mojarra frita", "Ciudad de Guatemala", R.drawable.mojarra));
        categoria.add(new Categoria("Ceviche", "Ciudad de Guatemala", R.drawable.cevichon));
        categoria.add(new Categoria("Guacamol", "Ciudad de Guatemala", R.drawable.guacamol));
        categoria.add(new Categoria("Pan de banano", "Ciudad de Guatemala", R.drawable.panbanano));
        categoria.add(new Categoria("Pan de elote", "Ciudad de Guatemala", R.drawable.eloteloco));

        AdaptadorCategoria adaptador = new AdaptadorCategoria(categoria, getApplication());
        lista.setAdapter(adaptador);
        //Establecemos el adaptador

        //lista.setOnScrollListener(new OnScrollUpDownListener(lista, 8, scrollAction));

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Categoria categoria = (Categoria) parent.getItemAtPosition(position);

                Bundle bundle = new Bundle();
                bundle.putString(DetalleComidaActivity.NOMBRE_PLATO, categoria.getTitulo());
                bundle.putInt("llave", categoria.getImagen());

                Intent intento = new Intent(getApplicationContext(), DetalleComidaScrollingActivity.class);
                intento.putExtras(bundle);

                startActivity(intento);
            }
        });
    }


    /*OnScrollUpDownListener.Action scrollAction = new OnScrollUpDownListener.Action() {
        private boolean hidden = true;

        @Override
        public void up() {
            if (hidden) {
                hidden = false;
                animate(boton)
                        .translationY(boton.getHeight() +
                                getResources().getDimension(R.dimen.fab_elevation_lollipop))
                        .setInterpolator(new LinearInterpolator())
                        .setDuration(200);
            }
        }

        @Override
        public void down() {
            if (!hidden) {
                hidden = true;
                animate(boton)
                        .translationY(0)
                        .setInterpolator(new LinearInterpolator())
                        .setDuration(200);
            }
        }
    };*/

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
