package com.gt.dev.lazaro.elcaldo.vista.actividades.recetas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSettings;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.gt.dev.lazaro.elcaldo.R;
import com.gt.dev.lazaro.elcaldo.adaptadores.TimeLine;
import com.gt.dev.lazaro.elcaldo.adaptadores.TimeLineAdapter;
import com.gt.dev.lazaro.elcaldo.controlador.AppController;
import com.gt.dev.lazaro.elcaldo.controlador.CustomRequest;
import com.gt.dev.lazaro.elcaldo.utilidades.ConexionVerify;
import com.gt.dev.lazaro.elcaldo.utilidades.Parametros;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TimeLineActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, InterstitialAdListener {

    private FloatingActionButton fab;
    private ProgressDialog pDialog;
    private String TAG = TimeLineActivity.class.getSimpleName();
    private String nombre, region, ingredientes, preparacion, usuario, imagen, like, id, fbPlace;
    private int avatar;
    private GridView lvTimeline;
    private ArrayList<TimeLine> categoria = new ArrayList<>();
    private Request.Priority priority = Request.Priority.IMMEDIATE;
    private InterstitialAd facebookAd;

    //GoogleAnalytics vars
    public static GoogleAnalytics googleAnalytics;
    public static Tracker tracker;
    private String keyTracker;

    private String tag_json_obj = "jsonbj_req", tag_json_array = "jarray_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        verifyConnection();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        startVars();
        setAnalytics();
    }

    private void setupAdater(ArrayList<TimeLine> categoria) {
        this.lvTimeline.setAdapter(new TimeLineAdapter(categoria, this));
    }

    private void showProgresDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideporgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }

    private void startVars() {

        fab = (FloatingActionButton) findViewById(R.id.fab_timeline);
        fab.setOnClickListener(this);
        lvTimeline = (GridView) findViewById(R.id.lv_timeline);
        lvTimeline.setOnItemClickListener(this);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage(getString(R.string.message_dialog));
        pDialog.setCancelable(false);
        getnewRecipes();

        fbPlace = Parametros.FB_PLACEMENT_ID;
        AdSettings.addTestDevice(getString(R.string.facebook_app_id));
        facebookAd = new InterstitialAd(this, fbPlace);
        facebookAd.setAdListener(TimeLineActivity.this);
        facebookAd.loadAd();
    }

    private void setAnalytics() {
        //Google Analytics intances
        googleAnalytics = GoogleAnalytics.getInstance(this);
        googleAnalytics.setLocalDispatchPeriod(1800);

        keyTracker = Parametros.TRACKER_ANALYTICS;

        tracker = googleAnalytics.newTracker(keyTracker);
        tracker.enableAdvertisingIdCollection(true);
        tracker.enableAutoActivityTracking(true);
        tracker.enableExceptionReporting(true);
    }

    private void getnewRecipes() {

        showProgresDialog();

        String url = Parametros.URL_SHOW_TMELINE;

        CustomRequest timelineRequest = new CustomRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("RESPONSE TIMELINE", response.toString().trim());
                try {
                    JSONArray jsonArray = response.getJSONArray("timeline");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject timeline = jsonArray.getJSONObject(i);

                        nombre = timeline.getString("nombre");
                        region = timeline.getString("region");
                        ingredientes = timeline.getString("ingredientes");
                        preparacion = timeline.getString("preparacion");
                        imagen = timeline.getString("imagen");
                        usuario = timeline.getString("unombre");
                        like = timeline.getString("like");
                        id = timeline.getString("id");
                        avatar = Integer.parseInt(timeline.getString("avatar"));

                        categoria.add(new TimeLine(usuario, nombre, region, ingredientes, preparacion, id, like, imagen, avatar));
                        setupAdater(categoria);
                        hideporgressDialog();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideporgressDialog();
                VolleyLog.d(TAG, "ERROR: " + error.getMessage());
                Log.d("ERROR RESPONSE", "ERROR: " + error.getMessage());
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
        timelineRequest.setRetryPolicy(policy);
        AppController.getInstance().setPriority(priority);
        AppController.getInstance().addToRequestQueue(timelineRequest, tag_json_obj);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_timeline:
                startActivity(new Intent(TimeLineActivity.this, AgregarRecetaActivityOne.class));
                break;
        }
    }

    private void showAlertDialog(String title, String message, boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(TimeLineActivity.this).create();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        //Inicializamos cat para obtener las variables y sus varoles segun el seleccionado.
        TimeLine cat = (TimeLine) parent.getItemAtPosition(position);

        //Instanceamos nuestra canasta para
        //almacenar cada uno de nuestros parametros.
        Bundle bundle = new Bundle();

        //Seteamos a nuestra canasta cada uno de sus parametros con
        //sus respectivos key y value.
        bundle.putString("recipename", cat.getRecipename());
        bundle.putString("region", cat.getRegion());
        bundle.putString("ingredientes", cat.getIngredientes());
        bundle.putString("preparacion", cat.getPreparacion());
        bundle.putString("username", cat.getUsername());
        bundle.putString("like", cat.getLikes());
        bundle.putString("id", cat.getId());
        bundle.putString("imagen", cat.getPicture());
        bundle.putInt("avatar", cat.getAvatar());

        //Realizamos el intento al detalle mandando todos nuestros parametros en la canasta.
        startActivity(new Intent(TimeLineActivity.this, DetalleTimeLineActivity.class).putExtras(bundle));
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
