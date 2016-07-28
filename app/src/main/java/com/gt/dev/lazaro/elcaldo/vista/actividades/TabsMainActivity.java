package com.gt.dev.lazaro.elcaldo.vista.actividades;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.gt.dev.lazaro.elcaldo.R;
import com.gt.dev.lazaro.elcaldo.controlador.Preferencias;
import com.gt.dev.lazaro.elcaldo.vista.coffe.CafeMainActivity;

public class TabsMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        } else if (id == R.id.nav_bebidas_tipicas) {
            startActivity(new Intent(TabsMainActivity.this, BebidasActivity.class));

        } else if (id == R.id.nav_preferencias) {
            startActivity(new Intent(TabsMainActivity.this, Preferencias.class));

        } else if (id == R.id.nav_comousar) {
            startActivity(new Intent(TabsMainActivity.this, ComoUsarActivity.class));

        } else if (id == R.id.nav_website) {
            String urlWeb = "http://elcaldo.net84.net";
            Intent iWeb = new Intent(Intent.ACTION_VIEW);
            iWeb.setData(Uri.parse(urlWeb));
            startActivity(iWeb);

        } else if (id == R.id.nav_twitter) {
            String urlTweet = "http://twitter.com/elcaldoapp";
            Intent iTweet = new Intent(Intent.ACTION_VIEW);
            iTweet.setData(Uri.parse(urlTweet));
            startActivity(iTweet);

        } else if (id == R.id.nav_facebook) {
            String urlFB = "http://facebook.com/elcaldoapp";
            Intent iFB = new Intent(Intent.ACTION_VIEW);
            iFB.setData(Uri.parse(urlFB));
            startActivity(iFB);

        } else if (id == R.id.nav_terminos) {
            startActivity(new Intent(TabsMainActivity.this, CopyrightActivity.class));

        } else if (id == R.id.nav_cafe) {
            startActivity(new Intent(TabsMainActivity.this, CafeMainActivity.class));

        } else if (id == R.id.nav_creditos) {
            startActivity(new Intent(TabsMainActivity.this, CreditosActivity.class));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
