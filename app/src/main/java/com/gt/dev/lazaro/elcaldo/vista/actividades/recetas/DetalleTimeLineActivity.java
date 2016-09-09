package com.gt.dev.lazaro.elcaldo.vista.actividades.recetas;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.gt.dev.lazaro.elcaldo.R;
import com.gt.dev.lazaro.elcaldo.vista.fragmentos.timeline.ComentarioTimeLine;
import com.gt.dev.lazaro.elcaldo.vista.fragmentos.timeline.RecetaTimeLine;

import java.util.ArrayList;
import java.util.List;

public class DetalleTimeLineActivity extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton fab;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_time_line);
        startVars();
    }

    private void startVars() {

        fab = (FloatingActionButton) findViewById(R.id.fab_detalle_timeline);
        fab.setOnClickListener(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar_detalle_timeline);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString("recipename");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(title);

        viewPager = (ViewPager) findViewById(R.id.vp_detalle_timeline);
        setViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs_detalle_timeline);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setViewPager(ViewPager viewPager) {
        ViewPagerAdapterTime adapter = new ViewPagerAdapterTime(getSupportFragmentManager());
        adapter.addFragment(new RecetaTimeLine(), getString(R.string.receta_tab_title));
        adapter.addFragment(new ComentarioTimeLine(), getString(R.string.comentario_tab_title));
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_detalle_timeline:
                startActivity(new Intent(DetalleTimeLineActivity.this, AddCommentActivity.class));
                break;
        }
    }

    class ViewPagerAdapterTime extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentListTitle = new ArrayList<>();

        public ViewPagerAdapterTime(FragmentManager manager) {
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
