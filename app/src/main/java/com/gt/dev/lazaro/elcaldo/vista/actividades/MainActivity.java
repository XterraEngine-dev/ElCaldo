package com.gt.dev.lazaro.elcaldo.vista.actividades;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.multidex.MultiDex;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.gt.dev.lazaro.elcaldo.R;
import com.gt.dev.lazaro.elcaldo.adaptadores.MainAdapter;
import com.gt.dev.lazaro.elcaldo.adaptadores.MainClass;
import com.gt.dev.lazaro.elcaldo.controlador.Preferencias;
import com.gt.dev.lazaro.elcaldo.utilidades.ConexionVerify;
import com.gt.dev.lazaro.elcaldo.vista.actividades.recetas.CaldosActivity;
import com.gt.dev.lazaro.elcaldo.vista.actividades.recetas.PostresActivity;
import com.gt.dev.lazaro.elcaldo.vista.actividades.recetas.TamalesActivity;
import com.gt.dev.lazaro.elcaldo.vista.actividades.recetas.TimeLineActivity;
import com.gt.dev.lazaro.elcaldo.vista.actividades.recetas.TopRecipesActivity;
import com.stephentuso.welcome.WelcomeScreenHelper;
import com.stephentuso.welcome.ui.WelcomeActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, AdapterView.OnItemClickListener {

    FloatingActionButton fab;
    public static GoogleAnalytics googleAnalytics;
    public static Tracker tracker;
    private ListView lista;
    private ArrayList<MainClass> categoria = new ArrayList<>();
    WelcomeScreenHelper welcomeScreen;

    /**
     * Compatibilidad con versiones inferiores al api 21
     *
     * @param newBase
     */
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        MultiDex.install(this);
    }

    /**
     * Metodo donde se crea todas nuestras variables, constantes, metodos, etc.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs_main);

        //Inicializamos Analytics
        startAnalytics();
        //Inicializamos las variables y metodos
        startVars();

        welcomeScreen = new WelcomeScreenHelper(this, CaldoWelcomeActivity.class);
        welcomeScreen.show(savedInstanceState);

        //Verficamos la conexiòn a internet llamando a nuestro metodo
        verifyConnection();
    }

    /**
     * Metodo para mostrar una dialog de alerta por la falta de internet
     *
     * @param title
     * @param message
     * @param status
     */
    private void showAlertDialog(String title, String message, boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("No tiene conexión a internet");
        alertDialog.setMessage("Conectar a internet");
        alertDialog.show();
    }

    /**
     * Metodo donde hacemos la verificacion de conexión a internet
     */
    private void verifyConnection() {
        if (!ConexionVerify.isNetworkAvailable(this)) {
            showAlertDialog("No tiene conexión a internet", "Conectar a internet", true);
            onStop();
        }
    }

    /**
     * Metodo donde instanciamos e iniclaizamo slas variables y widgets
     */
    private void startVars() {

        //FloatingActionButton starts
        fab = (FloatingActionButton) findViewById(R.id.fab_main_elcaldo);
        fab.setOnClickListener(this);

        //Toolbar starts
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //NavDrawer starts
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Listview starts
        lista = (ListView) findViewById(R.id.lv_main);

        //Cardview de Caldos
        categoria.add(new MainClass(R.drawable.caldos_broths_main_card, getString(R.string.caldos_title)));
        //Cardview de tamales
        categoria.add(new MainClass(R.drawable.tamales_cardview_main, getString(R.string.tamales_title)));
        //Cardview de postres
        categoria.add(new MainClass(R.drawable.desserts_cardview_main, getString(R.string.postres_title)));
        //Cardview de bebidas tipicas
        categoria.add(new MainClass(R.drawable.bebidas_main_cardview_elcaldo, getString(R.string.bebidas)));
        //Cadview de cocina del usuario
        categoria.add(new MainClass(R.drawable.kitchenuser_cardview_main, getString(R.string.cocina_usuario)));

        MainAdapter adapter = new MainAdapter(categoria, this);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(this);

    }

    /**
     * Metodo inicializador para Google Analytics
     */
    private void startAnalytics() {
        //Analytics
        googleAnalytics = GoogleAnalytics.getInstance(this);
        googleAnalytics.setLocalDispatchPeriod(1800);

        //Tracker starts
        tracker = googleAnalytics.newTracker("UA-69747362-1");
        tracker.enableAdvertisingIdCollection(true);
        tracker.enableAutoActivityTracking(true);
        tracker.enableExceptionReporting(true);
    }

    /**
     * Metodo cuando presiona el boton de back de la UI de Android el usuario.
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Inflando el menu de ElCaldo
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Metodo nativo de selección de item
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {

            case R.id.preferencias:
                startActivity(new Intent(MainActivity.this, Preferencias.class));
                break;

            case R.id.creditos:
                startActivity(new Intent(MainActivity.this, CreditosActivity.class));
                break;

            case R.id.btnCamara:
                startActivity(new Intent(MainActivity.this, CamaraActivity.class));
                break;

            case R.id.salir:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_otras_comidas) {
            startActivity(new Intent(MainActivity.this, OtrasComidasActivity.class));
        } else if (id == R.id.nav_preferencias) {
            startActivity(new Intent(MainActivity.this, Preferencias.class));

        } else if (id == R.id.nav_comousar) {
            Toast.makeText(MainActivity.this, "Como usar activity", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_website) {
            String urlWeb = "http://elcaldo.net84.net";
            Intent iWeb = new Intent(Intent.ACTION_VIEW);
            iWeb.setData(Uri.parse(urlWeb));
            startActivity(iWeb);

        } else if (id == R.id.nav_recetassemana) {
            startActivity(new Intent(MainActivity.this, TopRecipesActivity.class));
        } else if (id == R.id.nav_creditos) {
            startActivity(new Intent(MainActivity.this, CreditosActivity.class));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Seteamos un listener para cada boton
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_main_elcaldo:
                startActivity(new Intent(MainActivity.this, Buscador.class));
                break;
        }
    }

    /**
     * Seteamos un listener a cada item de la lista y hacia donde apunta cada intento
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                startActivity(new Intent(MainActivity.this, CaldosActivity.class));
                break;
            case 1:
                startActivity(new Intent(MainActivity.this, TamalesActivity.class));
                break;
            case 2:
                startActivity(new Intent(MainActivity.this, PostresActivity.class));
                break;
            case 3:
                startActivity(new Intent(MainActivity.this, BebidasActivity.class));
                break;
            case 4:
                startActivity(new Intent(MainActivity.this, TimeLineActivity.class));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == WelcomeScreenHelper.DEFAULT_WELCOME_SCREEN_REQUEST) {
            String welcomeKey = data.getStringExtra(WelcomeActivity.WELCOME_SCREEN_KEY);
            if (resultCode == RESULT_OK) {
                Toast.makeText(MainActivity.this, "completed", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        welcomeScreen.onSaveInstanceState(outState);
    }

    /**
     * Metodo cuando el usuario regresa a esta actividad
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Metodo cuando entre la actividad en pausa-background
     */
    @Override
    protected void onPause() {
        super.onPause();
    }

}
