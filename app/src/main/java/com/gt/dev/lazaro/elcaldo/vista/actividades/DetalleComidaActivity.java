package com.gt.dev.lazaro.elcaldo.vista.actividades;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gt.dev.lazaro.elcaldo.R;
import com.gt.dev.lazaro.elcaldo.adaptadores.AdaptadorDetalle;
import com.gt.dev.lazaro.elcaldo.adaptadores.CategoriaDetalleComida;
import com.gt.dev.lazaro.elcaldo.modelo.DBManager;
import com.gt.dev.lazaro.elcaldo.modelo.Plato;

import java.util.ArrayList;

/**
 * created by Lazaro on 11/09/2015
 */

public class DetalleComidaActivity extends AppCompatActivity {

    public static final String NOMBRE_PLATO = "platocomida";
    public static final String IMAGEN = "img";

    private ListView listaDetalle;
    private ArrayList<CategoriaDetalleComida> categoria;
    private String nombrePlato;
    private int imagenPlato;
    private Toolbar toolbar;
    private TextView tvNombrePlato;
    //private ArrayList<Plato> plato;

    /**
     * onCreate
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_comida);
        toolbar = (Toolbar) findViewById(R.id.detalle_toolbar);
        listaDetalle = (ListView) findViewById(R.id.lvDetalle);
        tvNombrePlato = (TextView) findViewById(R.id.tvNombrePlato);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Bundle bundle = getIntent().getExtras();
        nombrePlato = bundle.getString(NOMBRE_PLATO);
        //imagenPlato = bundle.getInt(NOMBRE_PLATO);
        imagenPlato = bundle.getInt("llave");

        cargarPlato();


    }

    private void cargarPlato() {
        DBManager manager = new DBManager(this);

        Plato plato = manager.selectPlato(nombrePlato);

        if (plato != null) {
            CategoriaDetalleComida detallePlato = new CategoriaDetalleComida(plato.getNombre(), plato.getIngredientes(), plato.getPreparacion(), imagenPlato);

            ArrayList data = new ArrayList<CategoriaDetalleComida>();
            data.add(detallePlato);
            adaptador(data);
        } else
            Toast.makeText(DetalleComidaActivity.this, R.string.conexion_fallida, Toast.LENGTH_SHORT).show();
    }

    private void adaptador(ArrayList<CategoriaDetalleComida> detallesComida) {
        categoria = detallesComida;

        AdaptadorDetalle adaptador = new AdaptadorDetalle(categoria, DetalleComidaActivity.this.getApplicationContext());
        listaDetalle.setAdapter(adaptador);
    }

    private void compartirPlato() {

        Intent intentCompartir = new Intent(Intent.ACTION_SEND);
        intentCompartir.setType("text/plain");
        intentCompartir.putExtra(Intent.EXTRA_SUBJECT, "Compartir en redes sociales");
        intentCompartir.putExtra(Intent.EXTRA_TEXT, "Nombre del plato : " + nombrePlato + "\n" +
                "Departamento : " + "Ciudad de Guatemala" + "\n" +
                getString(R.string.via));
        startActivity(Intent.createChooser(intentCompartir, getString(R.string.comparte_plato)));
    }

    /**
     * infla la actividad
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detalle_comida, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
            case R.id.abFav:
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                boolean presionado = sp.getBoolean("presionado", true);
                if (presionado == true) {
                    Toast.makeText(DetalleComidaActivity.this, "Agregado como favorito :)", Toast.LENGTH_SHORT).show();
                }

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
