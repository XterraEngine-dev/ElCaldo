package com.gt.dev.lazaro.elcaldo.vista.actividades;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.multidex.MultiDex;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.gt.dev.lazaro.elcaldo.R;
import com.gt.dev.lazaro.elcaldo.utilidades.ConexionVerify;
import com.gt.dev.lazaro.elcaldo.vista.fragmentos.BebidasCalientes;
import com.gt.dev.lazaro.elcaldo.vista.fragmentos.BebidasFriasRefrescos;

import java.util.ArrayList;
import java.util.List;

public class BebidasActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    public static GoogleAnalytics googleAnalytics;
    public static Tracker tracker;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        MultiDex.install(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bebidas);
        toolbar = (Toolbar) findViewById(R.id.toolbar_bebidas);
        setSupportActionBar(toolbar);
        verifyConnection();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setUpTabs();
        startAnalytics();
    }

    private void setUpTabs() {

        viewPager = (ViewPager) findViewById(R.id.vp_bebidas);
        setViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs_bebidas);
        tabLayout.setupWithViewPager(viewPager);
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

    private void startAnalytics() {
        //Analytics
        googleAnalytics = GoogleAnalytics.getInstance(this);
        googleAnalytics.setLocalDispatchPeriod(1800);

        tracker = googleAnalytics.newTracker("UA-69747362-1");
        tracker.enableAdvertisingIdCollection(true);
        tracker.enableAutoActivityTracking(true);
        tracker.enableExceptionReporting(true);
    }

    private void showAlertDialog(String title, String message, boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(BebidasActivity.this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.show();
    }

    private void verifyConnection() {
        if (!ConexionVerify.isNetworkAvailable(this)) {
            showAlertDialog(getString(R.string.title_not_connection), getString(R.string.message_not_connection), true);
            onStop();
        }
    }

    private void setViewPager(ViewPager viewPager) {
        ViewPagerAdapterDrinks adapter = new ViewPagerAdapterDrinks(getSupportFragmentManager());
        adapter.addFragment(new BebidasCalientes(), getString(R.string.bebidas_calientes));
        adapter.addFragment(new BebidasFriasRefrescos(), getString(R.string.bebidas_frias));
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapterDrinks extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentListTitle = new ArrayList<>();

        public ViewPagerAdapterDrinks(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentListTitle.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentListTitle.get(position);
        }
    }

}
