package com.gt.dev.lazaro.elcaldo.vista.actividades;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.gt.dev.lazaro.elcaldo.R;
import com.gt.dev.lazaro.elcaldo.adaptadores.ViewPagerAdapter;
import com.gt.dev.lazaro.elcaldo.controlador.NavigationDrawerFragment;
import com.gt.dev.lazaro.elcaldo.controlador.OnScrollUpDownListener;
import com.gt.dev.lazaro.elcaldo.controlador.Preferencias;
import com.gt.dev.lazaro.elcaldo.utilidades.SlidingTabLayout;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;

import static com.nineoldandroids.view.ViewPropertyAnimator.animate;

/**
 * Created by Lazaro on 10/9/2015.
 */
public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[] = {"Caldos", "Tamales", "Postres"};
    int Numboftabs = 3;
    FloatingActionButton run;
    public static GoogleAnalytics googleAnalytics;
    public static Tracker tracker;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        MultiDex.install(this);
    }

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_appbar);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //this.setupFab();
        toolbar.animate().translationY(-toolbar.getBottom()).setInterpolator(new AccelerateInterpolator()).start();
        toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();

        //Analytics
        googleAnalytics = GoogleAnalytics.getInstance(this);
        googleAnalytics.setLocalDispatchPeriod(1800);

        tracker = googleAnalytics.newTracker("UA-69747362-1");
        tracker.enableAdvertisingIdCollection(true);
        tracker.enableAutoActivityTracking(true);
        tracker.enableExceptionReporting(true);


        run = new FloatingActionButton.Builder(this).setBackgroundDrawable(R.drawable.floatingactionbutton).build();

        run.setOnClickListener(new View.OnClickListener()

                               {
                                   @Override
                                   public void onClick(View v) {
                                       startActivity(new Intent(MainActivity.this, BuscarActivity.class));
                                   }
                               }


        );

        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragmento_yolo);

        drawerFragment.setUp(R.id.fragmento_yolo, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

        adapter = new ViewPagerAdapter(getSupportFragmentManager(), Titles, Numboftabs);


        pager = (ViewPager) findViewById(R.id.pager);

        pager.setAdapter(adapter);


        tabs = (SlidingTabLayout) findViewById(R.id.tabs);

        tabs.setDistributeEvenly(true);


        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
                                       @Override
                                       public int getIndicatorColor(int position) {
                                           return getResources().getColor(R.color.tabsScrollColor);
                                       }
                                   }
        );


        // seteando viewpager para las slidestabs
        tabs.setViewPager(pager);
    }

    /**
     * infla la actividad
     * y al toolbar
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.btnCamara:
                startActivity(new Intent(this, CamaraActivity.class));
                break;
            case R.id.creditos:
                startActivity(new Intent(this, CreditosActivity.class));
                break;
            case R.id.salir:
                finish();
                break;
            case R.id.preferencias:
                startActivity(new Intent(this, Preferencias.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Esconde el floatingButton 350 hacia la derecha
     *
     * @param slideOffset
     */
    public void onDrawerSlide(float slideOffset) {
        run.setTranslationX(slideOffset * 350);
    }

    OnScrollUpDownListener.Action scrollAction = new OnScrollUpDownListener.Action() {
        private boolean hidden = true;

        @Override
        public void up() {
            if (hidden) {
                hidden = false;
                animate(run)
                        .translationY(run.getHeight() +
                                getResources().getDimension(R.dimen.fab_elevation_lollipop))
                        .setInterpolator(new LinearInterpolator())
                        .setDuration(200);
            }
        }

        @Override
        public void down() {
            if (!hidden) {
                hidden = true;
                animate(run)
                        .translationY(0)
                        .setInterpolator(new LinearInterpolator())
                        .setDuration(200);
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
