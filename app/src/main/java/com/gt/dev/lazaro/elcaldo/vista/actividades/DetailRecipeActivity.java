package com.gt.dev.lazaro.elcaldo.vista.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.gt.dev.lazaro.elcaldo.R;
import com.gt.dev.lazaro.elcaldo.utilidades.Parametros;
import com.gt.dev.lazaro.elcaldo.vista.fragmentos.detail.InfoRecipe;
import com.gt.dev.lazaro.elcaldo.vista.fragmentos.detail.Ingredientes;
import com.gt.dev.lazaro.elcaldo.vista.fragmentos.detail.Preparacion;

import java.util.ArrayList;
import java.util.List;

public class DetailRecipeActivity extends AppCompatActivity implements AdListener {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private String title, region;

    //GoogleAnalytics vars
    public static GoogleAnalytics googleAnalytics;
    public static Tracker tracker;
    private String keyTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_recipe);
        startVars();
        setAnalytics();
    }

    private void startVars() {

        toolbar = (Toolbar) findViewById(R.id.toolbar_detail_recipe);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitleRecipe();

        viewPager = (ViewPager) findViewById(R.id.vp_detail_recipe);
        setViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs_detail_recipe);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setAnalytics() {
        //GoogleAnalytics instance
        googleAnalytics = GoogleAnalytics.getInstance(this);
        googleAnalytics.setLocalDispatchPeriod(1800);

        keyTracker = Parametros.TRACKER_ANALYTICS;

        tracker = googleAnalytics.newTracker(keyTracker);
        tracker.enableAdvertisingIdCollection(true);
        tracker.enableAutoActivityTracking(true);
        tracker.enableExceptionReporting(true);
    }

    private void setTitleRecipe() {
        Bundle bundle = getIntent().getExtras();

        title = bundle.getString("nombre");
        region = bundle.getString("region");
        getSupportActionBar().setTitle(title);
    }

    private void setViewPager(ViewPager pager) {
        ViewPagerAdapterDetail adapter = new ViewPagerAdapterDetail(getSupportFragmentManager());
        adapter.addFragment(new Ingredientes(), getString(R.string.ingreidientes));
        adapter.addFragment(new Preparacion(), getString(R.string.preparacion));
        adapter.addFragment(new InfoRecipe(), "Info");
        pager.setAdapter(adapter);
    }

    private void shareRecipe() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.detalle_compartir_title));
        shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.detalle_nombre_plato_title) + title + "\n"
                + getString(R.string.detalle_departamento_title) + region + "\n"
                + getString(R.string.via) + " https://play.google.com/store/apps/details?id=com.gt.dev.lazaro.elcaldo");

        startActivity(Intent.createChooser(shareIntent, getString(R.string.comparte_plato)));
    }

    class ViewPagerAdapterDetail extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentListTitle = new ArrayList<>();

        public ViewPagerAdapterDetail(FragmentManager manager) {
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.detail_shared_recipe:
                tracker.send(new HitBuilders.EventBuilder()
                        .setCategory("DetailRecipeActivity")
                        .setAction("Share Recipe")
                        .setLabel("Sharing recipe from DetailRecipe")
                        .build());
                shareRecipe();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detalle_comida_scrolling, menu);
        return true;
    }

    @Override
    public void onError(Ad ad, AdError adError) {
        Log.d("ERROR = " + adError.getErrorMessage(), "CODE = " + adError.getErrorCode());
    }

    @Override
    public void onAdLoaded(Ad ad) {
        //This was loaded in the startvars method
    }

    @Override
    public void onAdClicked(Ad ad) {
        Log.i("AD", ad.getPlacementId());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}
