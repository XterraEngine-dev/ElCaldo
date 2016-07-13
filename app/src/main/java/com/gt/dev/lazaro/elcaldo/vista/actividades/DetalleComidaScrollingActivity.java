package com.gt.dev.lazaro.elcaldo.vista.actividades;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.gt.dev.lazaro.elcaldo.R;
import com.gt.dev.lazaro.elcaldo.adaptadores.AdaptadorDetalle;
import com.gt.dev.lazaro.elcaldo.adaptadores.CategoriaDetalleComida;
import com.gt.dev.lazaro.elcaldo.modelo.DBManager;
import com.gt.dev.lazaro.elcaldo.modelo.Plato;
import com.gt.dev.lazaro.elcaldo.modelo.SaveSharedPreferences;

import java.util.ArrayList;

public class DetalleComidaScrollingActivity extends AppCompatActivity {

    public static final String NOMBRE_PLATO = "platocomida";
    public static final String IMAGEN = "img";

    private ListView listaDetalle;
    private ArrayList<CategoriaDetalleComida> categoria;
    private String nombrePlato;
    private int imagenPlato;
    private TextView tvNuevo, tvNuevo2;
    private boolean works;
    private CollapsingToolbarLayout toolbarLayout;
    private Button shareTweet;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_comida_scrolling);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        tvNuevo = (TextView) findViewById(R.id.tvNuevo);
        tvNuevo2 = (TextView) findViewById(R.id.tvNuevo2);

        Bundle bundle = getIntent().getExtras();
        nombrePlato = bundle.getString(NOMBRE_PLATO);
        //imagenPlato = bundle.getInt(NOMBRE_PLATO);
        imagenPlato = bundle.getInt("llave");
        toolbarLayout.setBackgroundResource(imagenPlato);
        toolbarLayout.setTitle(nombrePlato);
        //Facebook api

        ShareLinkContent content = new ShareLinkContent.Builder().setContentTitle(nombrePlato).setContentUrl(Uri.parse("https://play.google.com/store/apps/details?id=com.gt.dev.lazaro.elcaldo")).build();
        ShareButton shareButton = (ShareButton) findViewById(R.id.fb_share_button);
        shareButton.setShareContent(content);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Has compartido en facebook! :D", Snackbar.LENGTH_LONG).show();
            }
        });

        /*shareTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundlePlato = new Bundle();
                bundlePlato.putString("key", nombrePlato);
                startActivity(new Intent(DetalleComidaScrollingActivity.this, TweetComposerActivity.class).putExtras(bundlePlato));
            }
        });*/

        cargarPlato();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_detalle_scrolling);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                compartirPlato();
            }
        });
    }

    private void cargarPlato() {
        DBManager manager = new DBManager(this);

        Plato plato = manager.selectPlato(nombrePlato);

        if (plato != null) {
            //CategoriaDetalleComida detallePlato = new CategoriaDetalleComida(plato.getNombre(), plato.getIngredientes(), plato.getPreparacion(), imagenPlato);
            //ArrayList data = new ArrayList<CategoriaDetalleComida>();
            //data.add(detallePlato);
            //adaptador(data);
            tvNuevo.setText(plato.getIngredientes());
            tvNuevo2.setText(plato.getPreparacion());
        } else
            Toast.makeText(DetalleComidaScrollingActivity.this, R.string.conexion_fallida, Toast.LENGTH_SHORT).show();
    }

    private void adaptador(ArrayList<CategoriaDetalleComida> detallesComida) {
        categoria = detallesComida;

        AdaptadorDetalle adaptador = new AdaptadorDetalle(categoria, DetalleComidaScrollingActivity.this.getApplicationContext());
        listaDetalle.setAdapter(adaptador);
    }

    private void compartirPlato() {

        Intent intentCompartir = new Intent(Intent.ACTION_SEND);
        intentCompartir.setType("text/plain");
        intentCompartir.putExtra(Intent.EXTRA_SUBJECT, "Compartir en redes sociales");
        intentCompartir.putExtra(Intent.EXTRA_TEXT, "Nombre del plato : " + nombrePlato + "\n" +
                "Departamento : " + "Ciudad de Guatemala" + "\n" +
                getString(R.string.via) + " https://play.google.com/store/apps/details?id=com.gt.dev.lazaro.elcaldo");

        startActivity(Intent.createChooser(intentCompartir, getString(R.string.comparte_plato)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detalle_comida_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
            case R.id.abFav:
                SaveSharedPreferences.setPlatoName(this, nombrePlato);
                //SharedPreferences to save the data of all the favorites recipes from the user.
                String dale = SaveSharedPreferences.getPlatoName(this);

                Toast.makeText(DetalleComidaScrollingActivity.this, R.string.agregado_favorito + dale, Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }
}
