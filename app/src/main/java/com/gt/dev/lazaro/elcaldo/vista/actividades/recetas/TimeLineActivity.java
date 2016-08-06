package com.gt.dev.lazaro.elcaldo.vista.actividades.recetas;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.gt.dev.lazaro.elcaldo.R;
import com.gt.dev.lazaro.elcaldo.controlador.CustomRequest;
import com.gt.dev.lazaro.elcaldo.utilidades.Parametros;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TimeLineActivity extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton fab;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        startVars();
    }

    private void startVars() {
        fab = (FloatingActionButton) findViewById(R.id.fab_timeline);
        fab.setOnClickListener(this);
        requestQueue = Volley.newRequestQueue(this);
    }

    private void getnewRecipes() {
        String url = Parametros.URL_SHOW_TAMALES;

        CustomRequest timelineRequest = new CustomRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("RESPONSE TIMELINE", response.toString().trim());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR RESONSETIMELINE", "Message: " + error.getMessage().toString());
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_timeline:
                startActivity(new Intent(TimeLineActivity.this, AddRecipeActivity.class));
                break;
        }
    }
}
