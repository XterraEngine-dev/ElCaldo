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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
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

import com.facebook.ads.*;

public class TamalesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdListener {

    private Toolbar toolbar;
    private ArrayList<Categoria> categoria = new ArrayList<>();
    private GridView lvTamales;
    private ProgressDialog pDialog;
    private Request.Priority priority = Request.Priority.IMMEDIATE;

    //Analytics vars
    public static GoogleAnalytics googleAnalytics;
    public static Tracker tracker;
    private String keyTracker;

    private AdView adView;
    private String idPlacement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tamales);
        verifyConnection();
        startVars();
        getTamales();
        setAnalytics();
    }

    private void startVars() {
        //Facebook instance vars
        idPlacement = Parametros.FB_PLACEMENT_BANNER;
        adView = new AdView(this, idPlacement, AdSize.BANNER_HEIGHT_50);
        LinearLayout linear = (LinearLayout) findViewById(R.id.linear_tamales);
        linear.addView(adView);
        adView.setAdListener(this);
        adView.loadAd();

        toolbar = (Toolbar) findViewById(R.id.toolbar_tamales_activity);
        lvTamales = (GridView) findViewById(R.id.lv_tamales);
        lvTamales.setOnItemClickListener(this);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage(getString(R.string.message_dialog));
        pDialog.setCancelable(false);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.tamales_title);
        getSupportActionBar().setSubtitle(getString(R.string.select_recipe));
        getSupportActionBar().setIcon(R.drawable.otrascomidas);
    }

    private void setAnalytics() {
        //Google Analytics instances
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

    private void setupAdapter(ArrayList<Categoria> categoria) {
        this.lvTamales.setAdapter(new AdaptadorCategoria(categoria, this));
    }

    private void getTamales() {

        showProgressDialog();

        String url = Parametros.URL_SHOW_TAMALES;

        CustomRequest tamalesRequest = new CustomRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("RESPONSE ARRAY", response.toString());
                try {
                    JSONArray jsonArray = response.getJSONArray("tamales");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject tamales = jsonArray.getJSONObject(i);

                        String name = tamales.getString("nombre");
                        String region = tamales.getString("region");
                        String ingredientes = tamales.getString("ingredientes");
                        String preparacion = tamales.getString("preparacion");
                        String id = tamales.getString("id");
                        String picture = tamales.getString("imagen");
                        categoria.add(new Categoria(name, region, ingredientes, preparacion, id, picture));
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
                Log.d("ERROR RESPONSE", "TAMALES JSONARRAY");
                Toast.makeText(TamalesActivity.this, "Verifica tu conexión a internet", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String user = Parametros.USER;
                String pass = Parametros.PASS;
                HashMap<String, String> headers = new HashMap<>();
                String credentials = Base64.encodeToString((user + ":" + pass).getBytes(), Base64.NO_WRAP);
                headers.put("Authorization", "Basic " + credentials);
                return headers;
            }

            @Override
            public Priority getPriority() {
                return priority;
            }
        };
        RetryPolicy policy = new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        tamalesRequest.setRetryPolicy(policy);
        AppController.getInstance().setPriority(priority);
        AppController.getInstance().addToRequestQueue(tamalesRequest);
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
        AlertDialog alertDialog = new AlertDialog.Builder(TamalesActivity.this).create();
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
        bundle.putString("ingredientes", cat.getIngredientes());
        bundle.putString("preparacion", cat.getPreparacion());
        bundle.putString("region", cat.getSubtitulo());

        startActivity(new Intent(TamalesActivity.this, DetailRecipeActivity.class).putExtras(bundle));
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

    @Override
    public void onError(Ad ad, AdError adError) {
        Log.d("ERROR", "MESSAGE = " + adError.getErrorMessage() + "CODE = " + adError.getErrorCode());
    }

    @Override
    public void onAdLoaded(Ad ad) {
        //Was load in the startvars()method
    }

    @Override
    public void onAdClicked(Ad ad) {
        Log.i("AD", ad.getPlacementId());
    }
}
