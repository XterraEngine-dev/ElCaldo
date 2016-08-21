package com.gt.dev.lazaro.elcaldo.vista.actividades;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

import java.util.ArrayList;

public class TabsMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, AdapterView.OnItemClickListener {

    FloatingActionButton fab;
    public static GoogleAnalytics googleAnalytics;
    public static Tracker tracker;
    private ListView lista;
    private ArrayList<MainClass> categoria = new ArrayList<>();

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        MultiDex.install(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs_main);
        startAnalytics();
        startVars();
        verifyConnection();
    }

    private void showAlertDialog(String title, String message, boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(TabsMainActivity.this).create();
        alertDialog.setTitle("No tiene conexión a internet");
        alertDialog.setMessage("Conectar a internet");
        alertDialog.show();
    }

    private void verifyConnection() {
        if (!ConexionVerify.isNetworkAvailable(this)) {
            showAlertDialog("No tiene conexión a internet", "Conectar a internet", true);
            onStop();
        }
    }

    private void startVars() {

        fab = (FloatingActionButton) findViewById(R.id.fab_main_elcaldo);
        fab.setOnClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        lista = (ListView) findViewById(R.id.lv_main);

        categoria.add(new MainClass(getString(R.string.caldos_title), R.drawable.caldodegallina));
        categoria.add(new MainClass(getString(R.string.tamales_title), R.drawable.tamalcolorado));
        categoria.add(new MainClass(getString(R.string.postres_title), R.drawable.rellenitos));
        categoria.add(new MainClass(getString(R.string.bebidas_activity), R.drawable.atoldeelote));
        categoria.add(new MainClass(getString(R.string.cocina_usuario), R.drawable.cafe_banner_gt));

        MainAdapter adapter = new MainAdapter(categoria, this);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(this);
    }

    private void startAnalytics() {
        //Analytics
        googleAnalytics = GoogleAnalytics.getInstance(this);
        googleAnalytics.setLocalDispatchPeriod(1800);

        tracker = googleAnalytics.newTracker("UA-69747362-1");
        tracker.enableAdvertisingIdCollection(true);
        tracker.enableAutoActivityTracking(true);
        tracker.enableExceptionReporting(true);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tabs_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_otras_comidas) {
            startActivity(new Intent(TabsMainActivity.this, OtrasComidasActivity.class));
        } else if (id == R.id.nav_preferencias) {
            startActivity(new Intent(TabsMainActivity.this, Preferencias.class));

        } else if (id == R.id.nav_comousar) {
            startActivity(new Intent(TabsMainActivity.this, ComoUsarActivity.class));

        } else if (id == R.id.nav_website) {
            String urlWeb = "http://elcaldo.net84.net";
            Intent iWeb = new Intent(Intent.ACTION_VIEW);
            iWeb.setData(Uri.parse(urlWeb));
            startActivity(iWeb);

        } else if (id == R.id.nav_recetassemana) {
            startActivity(new Intent(TabsMainActivity.this, TopRecipesActivity.class));
        } else if (id == R.id.nav_creditos) {
            startActivity(new Intent(TabsMainActivity.this, CreditosActivity.class));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_main_elcaldo:
                startActivity(new Intent(TabsMainActivity.this, CamaraActivity.class));
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                startActivity(new Intent(TabsMainActivity.this, CaldosActivity.class));
                break;
            case 1:
                startActivity(new Intent(TabsMainActivity.this, TamalesActivity.class));
                break;
            case 2:
                startActivity(new Intent(TabsMainActivity.this, PostresActivity.class));
                break;
            case 3:
                startActivity(new Intent(TabsMainActivity.this, BebidasActivity.class));
                break;
            case 4:
                startActivity(new Intent(TabsMainActivity.this, TimeLineActivity.class));
                break;
        }
    }
}
