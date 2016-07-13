package com.gt.dev.lazaro.elcaldo.vista.coffe;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.gt.dev.lazaro.elcaldo.R;

public class CafeMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView tvMainCoffe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.learn_about_coffee);
        //toolbar.setBackgroundColor(getResources().getColor(R.color.cafe_claro));
        tvMainCoffe = (TextView) findViewById(R.id.tv_coffe_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_coffe_main);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CafeMainActivity.this, CameraCoffeActivity.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        tvMainCoffe.setText(R.string.coffe_main);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.coffe_origin_id) {
            String tOrigen = getString(R.string.origin_coffe_nav_name);
            String origen = getString(R.string.origin_coffe_text);

            Bundle bundle = new Bundle();
            bundle.putString("titulo", tOrigen);
            bundle.putString("texto", origen);
            bundle.putInt("imagen", R.drawable.origencafe);
            startActivity(new Intent(CafeMainActivity.this, BurdelActivity.class).putExtras(bundle));

        } else if (id == R.id.consumo_cultivo_id) {
            String tCultivo = getString(R.string.cultivo_coffe);
            String cultivo = getString(R.string.consumo_cultivo_text);

            Bundle bundle = new Bundle();
            bundle.putString("titulo", tCultivo);
            bundle.putString("texto", cultivo);
            bundle.putInt("imagen", R.drawable.cultivo);
            startActivity(new Intent(CafeMainActivity.this, BurdelActivity.class).putExtras(bundle));
        } else if (id == R.id.recoleccion_id) {
            String tRecoleccion = getString(R.string.recolection_coffe);
            String recoleccion = getString(R.string.recoleccion_text);

            Bundle bundle = new Bundle();
            bundle.putString("titulo", tRecoleccion);
            bundle.putInt("imagen", R.drawable.recole);
            bundle.putString("texto", recoleccion);
            startActivity(new Intent(CafeMainActivity.this, BurdelActivity.class).putExtras(bundle));
        } else if (id == R.id.tueste_id) {
            String tTueste = getString(R.string.tueste_coffe);
            String tueste = getString(R.string.tueste_text);

            Bundle bundle = new Bundle();
            bundle.putInt("imagen", R.drawable.tostditos);
            bundle.putString("titulo", tTueste);
            bundle.putString("texto", tueste);
            startActivity(new Intent(CafeMainActivity.this, BurdelActivity.class).putExtras(bundle));
        } else if (id == R.id.cafe_mundo_id) {
            String tMundo = getString(R.string.coffe_in_world);
            String mundo = getString(R.string.cafe_mundo_text);

            Bundle bundle = new Bundle();
            bundle.putInt("imagen", R.drawable.mundo);
            bundle.putString("titulo", tMundo);
            bundle.putString("texto", mundo);
            startActivity(new Intent(CafeMainActivity.this, BurdelActivity.class).putExtras(bundle));
        } else if (id == R.id.mezcla_id) {
            String tMezcla = getString(R.string.coffe_mezcla);
            String mezcla = getString(R.string.mezcla_text);

            Bundle bundle = new Bundle();
            bundle.putInt("imagen", R.drawable.drawer_cafe);
            bundle.putString("titulo", tMezcla);
            bundle.putString("texto", mezcla);
            startActivity(new Intent(CafeMainActivity.this, BurdelActivity.class).putExtras(bundle));
        } else if (id == R.id.curioso_id) {
            String tCurioso = getString(R.string.datos_curiosos_coffe);
            String curioso = getString(R.string.curioso_text);

            Bundle bundle = new Bundle();
            bundle.putString("titulo", tCurioso);
            bundle.putInt("imagen", R.drawable.molidito);
            bundle.putString("texto", curioso);
            startActivity(new Intent(CafeMainActivity.this, BurdelActivity.class).putExtras(bundle));
        } else if (id == R.id.healt_id) {
            String tHealt = getString(R.string.coffe_health);
            String healt = getString(R.string.salud_text);

            Bundle bundle = new Bundle();
            bundle.putString("titulo", tHealt);
            bundle.putInt("imagen", R.drawable.salud);
            bundle.putString("texto", healt);
            startActivity(new Intent(CafeMainActivity.this, BurdelActivity.class).putExtras(bundle));
        } else if (id == R.id.guate_id) {
            String tGuate = getString(R.string.coffe_in_guate);
            String guate = getString(R.string.cafe_guate_text);
            Bundle bundle = new Bundle();
            bundle.putInt("imagen", R.drawable.guateencafe);
            bundle.putString("titulo", tGuate);
            bundle.putString("texto", guate);
            startActivity(new Intent(CafeMainActivity.this, BurdelActivity.class).putExtras(bundle));
        } else if (id == R.id.tipo_cafe_id) {
            String tCafetipo = getString(R.string.tipocafe_title);
            String cafetipo = getString(R.string.tipo_cafe_text);
            Bundle bundle = new Bundle();
            bundle.putInt("imagen", R.drawable.mezcla_pic);
            bundle.putString("titulo", tCafetipo);
            bundle.putString("texto", cafetipo);
            startActivity(new Intent(CafeMainActivity.this, BurdelActivity.class).putExtras(bundle));
        }
        if (id == R.id.dev_pitch) {
            String tDev = getString(R.string.dev_coment);
            String dev = getString(R.string.tip_dev_text);
            Bundle bundle = new Bundle();
            bundle.putInt("imagen", R.drawable.lazarus_coffe);
            bundle.putString("titulo", tDev);
            bundle.putString("texto", dev);
            startActivity(new Intent(CafeMainActivity.this, BurdelActivity.class).putExtras(bundle));
        }
        if (id == R.id.back_elcaldo) {
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
