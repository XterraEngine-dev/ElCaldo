package com.gt.dev.lazaro.elcaldo.vista.actividades;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSettings;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.gt.dev.lazaro.elcaldo.R;
import com.gt.dev.lazaro.elcaldo.utilidades.ConexionVerify;
import com.gt.dev.lazaro.elcaldo.utilidades.Parametros;
import com.gt.dev.lazaro.elcaldo.vista.fragmentos.BebidasCalientes;
import com.gt.dev.lazaro.elcaldo.vista.fragmentos.BebidasFriasRefrescos;

import java.util.ArrayList;
import java.util.List;

public class BebidasActivity extends AppCompatActivity implements InterstitialAdListener {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private String fbPlace;
    private InterstitialAd facebookAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        verifyConnection();
        startVars();
        setUpTabs();
    }

    private void startVars() {
        setContentView(R.layout.activity_bebidas);
        toolbar = (Toolbar) findViewById(R.id.toolbar_bebidas);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fbPlace = Parametros.FB_PLACEMENT_ID;
        AdSettings.addTestDevice(getString(R.string.facebook_app_id));
        facebookAd = new InterstitialAd(this, fbPlace);
        facebookAd.setAdListener(BebidasActivity.this);
        facebookAd.loadAd();
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

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (facebookAd != null) {
            facebookAd.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onInterstitialDisplayed(Ad ad) {
        onPause();
    }

    @Override
    public void onInterstitialDismissed(Ad ad) {
        onResume();
    }

    @Override
    public void onError(Ad ad, AdError adError) {
        Log.d("ERROR FB", "MESSAGE = " + adError.getErrorMessage());
    }

    @Override
    public void onAdLoaded(Ad ad) {
        facebookAd.show();
    }

    @Override
    public void onAdClicked(Ad ad) {
        Log.i("AD", ad.getPlacementId());
    }
}
