package com.gt.dev.lazaro.elcaldo.vista.actividades;

import android.content.Context;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.gt.dev.lazaro.elcaldo.R;
import com.gt.dev.lazaro.elcaldo.adaptadores.ViewPagerAdapter2;
import com.gt.dev.lazaro.elcaldo.utilidades.SlidingTabLayout;

public class BebidasActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager pager;
    private ViewPagerAdapter2 adapter;
    private SlidingTabLayout tabs;
    CharSequence Titles[] = {"Bebidas calientes", "Bebidas frías"};
    int Numboftabs = 2;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        MultiDex.install(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bebidas);
        toolbar = (Toolbar) findViewById(R.id.tb_bebidas_activity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setUpTabs();
    }

    private void setUpTabs() {
        adapter = new ViewPagerAdapter2(getSupportFragmentManager(), Titles, Numboftabs);

        pager = (ViewPager) findViewById(R.id.pager_bebidas);
        pager.setAdapter(adapter);

        tabs = (SlidingTabLayout) findViewById(R.id.tabs_bebidas);

        tabs.setDistributeEvenly(true);
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

        //seteando viewpager para las slidestabs
        tabs.setViewPager(pager);
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
