package com.gt.dev.lazaro.elcaldo.vista.actividades.recetas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.gt.dev.lazaro.elcaldo.R;
import com.gt.dev.lazaro.elcaldo.adaptadores.AdaptadorCategoria;
import com.gt.dev.lazaro.elcaldo.adaptadores.Categoria;
import com.gt.dev.lazaro.elcaldo.controlador.AppController;
import com.gt.dev.lazaro.elcaldo.controlador.CustomRequest;
import com.gt.dev.lazaro.elcaldo.utilidades.ConexionVerify;
import com.gt.dev.lazaro.elcaldo.utilidades.Parametros;
import com.gt.dev.lazaro.elcaldo.vista.actividades.DetailRecipeActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CaldosActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdListener {

    private Toolbar toolbar;
    private RequestQueue requestQueue;
    private ArrayList<Categoria> categoria = new ArrayList<>();
    private GridView lvCaldos;
    private ProgressDialog pDialog;
    private String idPlate;
    private Request.Priority priority = Request.Priority.IMMEDIATE;

    public static GoogleAnalytics googleAnalytics;
    public static Tracker tracker;
    private String keyTracker;

    private AdView adView;
    private String idPlacement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caldos);
        verifyConnection();
        startVars();
        getCaldos();
        setAnalytics();
    }

    private void startVars() {

        //Facebook instances starts
        AdSettings.addTestDevice(getString(R.string.facebook_app_id));
        idPlacement = Parametros.FB_PLACEMENT_BANNER;
        adView = new AdView(this, idPlacement, AdSize.BANNER_HEIGHT_50);
        LinearLayout linear = (LinearLayout) findViewById(R.id.linear_caldos);
        linear.addView(adView);
        adView.setAdListener(this);
        adView.loadAd();

        toolbar = (Toolbar) findViewById(R.id.toolbar_caldos_activity);
        lvCaldos = (GridView) findViewById(R.id.lv_caldos);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.caldos_title);
        getSupportActionBar().setSubtitle(getString(R.string.select_recipe));
        getSupportActionBar().setIcon(R.drawable.otrascomidas);
        requestQueue = Volley.newRequestQueue(this);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage(getString(R.string.message_dialog));
        pDialog.setCancelable(false);
        lvCaldos.setOnItemClickListener(this);
    }

    private void setAnalytics() {
        //Google Analytcis instances
        googleAnalytics = GoogleAnalytics.getInstance(this);
        googleAnalytics.setLocalDispatchPeriod(1800);

        keyTracker = Parametros.TRACKER_ANALYTICS;

        tracker = googleAnalytics.newTracker(keyTracker);
        tracker.enableAdvertisingIdCollection(true);
        tracker.enableAutoActivityTracking(true);
        tracker.enableExceptionReporting(true);
    }

    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
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

    private void setupAdapter(ArrayList<Categoria> categoria) {
        this.lvCaldos.setAdapter(new AdaptadorCategoria(categoria, this));
    }


    private void getCaldos() {

        showProgressDialog();

        String url = Parametros.URL_SHOW_CALDOS;

        CustomRequest caldosRequest = new CustomRequest(CustomRequest.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("caldos");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject caldos = jsonArray.getJSONObject(i);

                        String name = caldos.getString("nombre");
                        String region = caldos.getString("region");
                        String ingredientes = caldos.getString("ingredientes");
                        String preparacion = caldos.getString("preparacion");
                        idPlate = caldos.getString("id");
                        String picture = caldos.getString("imagen");
                        categoria.add(new Categoria(name, region, ingredientes, preparacion, idPlate, picture));
                        setupAdapter(categoria);
                        hideProgressDialog();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgressDialog();
                VolleyLog.d("", "" + error.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                String credentials = Base64.encodeToString(("dev@elcaldogt.com" + ":" + "azazelxd").getBytes(), Base64.NO_WRAP);
                headers.put("Authorization", "Basic " + credentials);
                return headers;
            }

            @Override
            public Priority getPriority() {
                return priority;
            }
        };
        RetryPolicy policy = new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        caldosRequest.setRetryPolicy(policy);
        AppController.getInstance().setPriority(priority);
        AppController.getInstance().addToRequestQueue(caldosRequest);
    }

    private void showAlertDialog(String title, String message, boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(CaldosActivity.this).create();
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Categoria cat = (Categoria) parent.getItemAtPosition(position);

        Bundle bundle = new Bundle();

        bundle.putString("nombre", cat.getTitulo());
        bundle.putString("preparacion", cat.getPreparacion());
        bundle.putString("ingredientes", cat.getIngredientes());
        bundle.putString("imagen", cat.getImagen());
        bundle.putString("region", cat.getSubtitulo());

        //bundle.putInt("picture", picture);
        startActivity(new Intent(CaldosActivity.this, DetailRecipeActivity.class).putExtras(bundle));
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        adView.destroy();
        super.onDestroy();
    }

    @Override
    public void onError(Ad ad, AdError adError) {
        Log.d("ERRROR FACEBOOK", "MESSAGE = " + adError.getErrorMessage() + "CODE = " + adError.getErrorCode());
    }

    @Override
    public void onAdLoaded(Ad ad) {
        //Ad was load in startVars();
    }

    @Override
    public void onAdClicked(Ad ad) {
        Log.i("AD", ad.getPlacementId());
    }
}
