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
import android.widget.GridView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
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

public class TimeLineActivity extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton fab;
    private ProgressDialog pDialog;
    private String TAG = TimeLineActivity.class.getSimpleName();
    private String nombre, region, usuario;
    private GridView lvTimeline;
    private ArrayList<TimeLine> categoria = new ArrayList<>();

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
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Cargando...");
        pDialog.setCancelable(false);
        getnewRecipes();
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
                        usuario = timeline.getString("like");

                        categoria.add(new TimeLine(usuario, nombre, region, region, R.drawable.cerdo, R.drawable.cevichon));
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
                HashMap<String, String> headers = new HashMap<>();
                String credentials = Base64.encodeToString(("cifuentes_estrada@hotmail.com" + ":" + "azazelxd").getBytes(), Base64.NO_WRAP);
                headers.put("Authorization", "Basic " + credentials);
                return headers;
            }
        };
        AppController.getInstance().addToRequestQueue(timelineRequest, tag_json_obj);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_timeline:
                startActivity(new Intent(TimeLineActivity.this, AddRecipeActivity.class));
                break;
        }
    }

    private void showAlertDialog(String title, String message, boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(TimeLineActivity.this).create();
        alertDialog.setTitle("No tiene conexión a internet");
        alertDialog.setMessage("Conectar a internet");
        alertDialog.show();
    }

    private void verifyConnection() {
        if (!ConexionVerify.isNetworkAvailable(this)) {
            showAlertDialog("No tiene conexión a internet", "Conectar a internet", true);
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
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
