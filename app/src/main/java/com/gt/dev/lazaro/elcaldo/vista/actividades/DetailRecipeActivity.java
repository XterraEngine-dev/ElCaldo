package com.gt.dev.lazaro.elcaldo.vista.actividades;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.gt.dev.lazaro.elcaldo.R;
import com.gt.dev.lazaro.elcaldo.vista.fragmentos.detail.InfoRecipe;
import com.gt.dev.lazaro.elcaldo.vista.fragmentos.detail.Ingredientes;
import com.gt.dev.lazaro.elcaldo.vista.fragmentos.detail.Preparacion;

import java.util.ArrayList;
import java.util.List;

public class DetailRecipeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    public static GoogleAnalytics googleAnalytics;
    public static Tracker tracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_recipe);
        startVars();
    }

    private void startVars() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_detail_recipe);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.vp_detail_recipe);
        setViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs_detail_recipe);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setViewPager(ViewPager pager) {
        ViewPagerAdapterDetail adapter = new ViewPagerAdapterDetail(getSupportFragmentManager());
        adapter.addFragment(new Ingredientes(), getString(R.string.ingreidientes));
        adapter.addFragment(new Preparacion(), getString(R.string.preparacion));
        adapter.addFragment(new InfoRecipe(), "Info");
        viewPager.setAdapter(adapter);
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
        }
        return super.onOptionsItemSelected(item);
    }
}
