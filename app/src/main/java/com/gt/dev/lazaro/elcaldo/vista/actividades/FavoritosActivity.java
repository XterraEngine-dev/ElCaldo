package com.gt.dev.lazaro.elcaldo.vista.actividades;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.gt.dev.lazaro.elcaldo.R;
import com.gt.dev.lazaro.elcaldo.adaptadores.AdaptadorFavoritos;
import com.gt.dev.lazaro.elcaldo.adaptadores.CategoriaFavoritos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lazaro on 9/10/2015.
 */
public class FavoritosActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private AdaptadorFavoritos adapter;

    /**
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);
        recyclerView = (RecyclerView) findViewById(R.id.rvFavs);
        toolbar = (Toolbar) findViewById(R.id.favoritos_toolbar);
        setSupportActionBar(toolbar);
        Toast.makeText(this, getString(R.string.working_that), Toast.LENGTH_LONG).show();

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapter = new AdaptadorFavoritos(FavoritosActivity.this, getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(FavoritosActivity.this));


    }

    /**
     * @return los datos
     */
    public static List<CategoriaFavoritos> getData() {
        List<CategoriaFavoritos> data = new ArrayList<>();
        int[] icons = {R.drawable.caldodegallina, R.drawable.caldoderes, R.drawable.frijolesconcrema, R.drawable.tamalcolorado
                , R.drawable.verduras, R.drawable.yuca};
        String[] titles = {"Caldo de gallina", "Rellenitos de platano", "Chicharron con yuca", "Nuegados", "Tamal negro", "Pepian"};
        for (int i = 0; i < titles.length && i < icons.length; i++) {
            //Information current = new Information();
            //current.icon = icons[i];
            //current.title = titles[i];
            data.add(new CategoriaFavoritos(icons[i], titles[i]));
        }
        return data;
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
