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
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.gt.dev.lazaro.elcaldo.R;
import com.gt.dev.lazaro.elcaldo.controlador.AppController;
import com.gt.dev.lazaro.elcaldo.utilidades.Parametros;
import com.gt.dev.lazaro.elcaldo.vista.fragmentos.timeline.ComentarioTimeLine;
import com.gt.dev.lazaro.elcaldo.vista.fragmentos.timeline.RecetaTimeLine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetalleTimeLineActivity extends AppCompatActivity implements View.OnClickListener, AdListener {

    private FloatingActionButton fab;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;

    //GoogleAnalytics vars
    public static GoogleAnalytics googleAnalytics;
    public static Tracker tracker;
    private String keyTracker;

    private AdView adView;
    private String idPlacement;

    //Patch de like
    private String Key_UNAME = "unombre";
    private String KEY_NAME = "nombre";
    private String KEY_INGREDIENTES = "ingredientes";
    private String KEY_PREPARACION = "preparacion";
    private String KEY_REGION = "region";
    private String KEY_IMAGEN = "imagen";
    private String KEY_LIKE = "like";
    private String KEY_AVATAR = "avatar";
    private String parametroUrl;

    //Variables para put

    private String nombreReceta, nombreUsuario, ingredientes, preparacion, region, imagen, like, likePlus;
    private int avatar;

    private int contador = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_time_line);
        startVars();
        setAnalytics();
    }

    private void startVars() {
        //FAcebok instance vars
        idPlacement = Parametros.FB_PLACEMENT_BANNER;
        adView = new AdView(this, idPlacement, AdSize.BANNER_HEIGHT_50);
        LinearLayout linear = (LinearLayout) findViewById(R.id.linear_detail_timeline);
        linear.addView(adView);
        adView.setAdListener(this);
        adView.loadAd();

        fab = (FloatingActionButton) findViewById(R.id.fab_detalle_timeline);
        fab.setOnClickListener(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar_detalle_timeline);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        String idUrl = bundle.getString("id");
        String nombreUsuarioB = bundle.getString("username");
        String title = bundle.getString("recipename");
        String ingredientesB = bundle.getString("ingredientes");
        String preparacionB = bundle.getString("preparacion");
        String regionB = bundle.getString("region");
        String imagenB = bundle.getString("imagen");
        String likeB = bundle.getString("like");
        int avatarB = bundle.getInt("avatar");

        nombreUsuario = nombreUsuarioB;
        ingredientes = ingredientesB;
        preparacion = preparacionB;
        region = regionB;
        imagen = imagenB;
        like = likeB;
        avatar = avatarB;
        nombreReceta = title;
        parametroUrl = idUrl;

        contador++;

        int megusta = 0;
        megusta = Integer.parseInt(like);
        int newLike = megusta + contador;

        likePlus = String.valueOf(newLike);

        Log.d("**LIKEPLUS**", likePlus);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(title);

        viewPager = (ViewPager) findViewById(R.id.vp_detalle_timeline);
        setViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs_detalle_timeline);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setAnalytics() {
        googleAnalytics = GoogleAnalytics.getInstance(this);
        googleAnalytics.setLocalDispatchPeriod(1800);

        keyTracker = Parametros.TRACKER_ANALYTICS;

        tracker = googleAnalytics.newTracker(keyTracker);
        tracker.enableAdvertisingIdCollection(true);
        tracker.enableAutoActivityTracking(true);
        tracker.enableExceptionReporting(true);
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

    @Override
    public void onError(Ad ad, AdError adError) {
        Log.d("ERROR = " + adError.getErrorMessage(), "CODE = " + adError.getErrorCode());
    }

    @Override
    public void onAdLoaded(Ad ad) {
        //This was load in the starvars method
    }

    @Override
    public void onAdClicked(Ad ad) {
        Log.i("AD", ad.getPlacementId());
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

    /**
     * Infle el toolbar con el LIKE
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail_timeline, menu);
        return true;
    }

    /**
     * Aqui definen las acciones a los botones del toolbar.
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            case R.id.btn_like_timeline:
                enviarLike();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void enviarLike() {


        String url = Parametros.URL_SHOW_TIMELINE + "/" + parametroUrl;

        Log.i("URL", "NUEVA URL  " + url);
        StringRequest uploadRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("**RESPONSE**", "MESSAGE = " + response);
                if (response.equals("{\"timeline\":\"editado\"}")) {
                    Toast.makeText(DetalleTimeLineActivity.this, getString(R.string.like_recipe_timeline), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR RESPONSE", "MESSAGE: " + error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put(Key_UNAME, nombreUsuario);
                params.put(KEY_NAME, nombreReceta);
                params.put(KEY_INGREDIENTES, ingredientes);
                params.put(KEY_PREPARACION, preparacion);
                params.put(KEY_REGION, region);
                params.put(KEY_IMAGEN, imagen);
                params.put(KEY_LIKE, likePlus);
                String imagenAvatar = String.valueOf(avatar);
                params.put(KEY_AVATAR, imagenAvatar);


                Log.i("PARAMETROS",
                        "TODO"
                                + nombreUsuario + "  "
                                + nombreReceta + "  "
                                + ingredientes + "  "
                                + preparacion + "  "
                                + region + "  "
                                + imagen + "  "
                                //              + masLike + "  "
                                + likePlus + " "
                                + imagenAvatar + "  "

                );


                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String user = Parametros.USER;
                String pass = Parametros.PASS;
                HashMap<String, String> headers = new HashMap<>();
                String credentials = Base64.encodeToString((user + ":" + pass).getBytes(), Base64.NO_WRAP);
                headers.put("Authorization", "Basic " + credentials);
                return headers;
            }
        };
        AppController.getInstance().addToRequestQueue(uploadRequest);
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
        adView.destroy();
        super.onDestroy();
    }
}
