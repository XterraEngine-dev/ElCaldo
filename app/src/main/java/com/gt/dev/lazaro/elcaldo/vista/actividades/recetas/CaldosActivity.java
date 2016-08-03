package com.gt.dev.lazaro.elcaldo.vista.actividades.recetas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.gt.dev.lazaro.elcaldo.R;
import com.gt.dev.lazaro.elcaldo.adaptadores.AdaptadorCategoria;
import com.gt.dev.lazaro.elcaldo.adaptadores.Categoria;
import com.gt.dev.lazaro.elcaldo.controlador.CustomRequest;
import com.gt.dev.lazaro.elcaldo.utilidades.Parametros;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CaldosActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RequestQueue requestQueue;
    private ArrayList<Categoria> categoria = new ArrayList<>();
    private ListView lvCaldos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caldos);
        startVars();
        getCaldos();
    }

    private void startVars() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_caldos_activity);
        lvCaldos = (ListView) findViewById(R.id.lv_caldos);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.caldos_title);
        getSupportActionBar().setSubtitle("Select a recipe");
        getSupportActionBar().setIcon(R.drawable.otrascomidas);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestQueue = Volley.newRequestQueue(this);
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
                        String id = caldos.getString("id");
                        int picture = R.drawable.caldodegallina;
                        categoria.add(new Categoria(name, region, id, picture));
                        setupAdapter(categoria);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

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
        requestQueue.add(caldosRequest);
    }

}
