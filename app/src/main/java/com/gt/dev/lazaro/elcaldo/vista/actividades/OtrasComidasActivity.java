package com.gt.dev.lazaro.elcaldo.vista.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.gt.dev.lazaro.elcaldo.R;
import com.gt.dev.lazaro.elcaldo.adaptadores.AdaptadorCategoria;
import com.gt.dev.lazaro.elcaldo.adaptadores.Categoria;
import com.gt.dev.lazaro.elcaldo.controlador.CustomRequest;
import com.gt.dev.lazaro.elcaldo.modelo.DBManager;
import com.gt.dev.lazaro.elcaldo.utilidades.Parametros;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Fernnado Lazaro
 */
public class OtrasComidasActivity extends AppCompatActivity {

    DBManager dbManager;
    private ListView lista;
    private ArrayList<Categoria> categoria = new ArrayList<>();
    private Toolbar toolbar;
    private FloatingActionButton boton;
    private RequestQueue requestQueue;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otras_comidas);
        dbManager = new DBManager(this);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        lista = (ListView) findViewById(R.id.lv4);

        requestQueue = Volley.newRequestQueue(this);
        showOtrasList();
        //startArrayList();

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Categoria categoria = (Categoria) parent.getItemAtPosition(position);

                Bundle bundle = new Bundle();
                bundle.putString(DetalleComidaScrollingActivity.NOMBRE_PLATO, categoria.getTitulo());
                bundle.putInt("llave", categoria.getImagen());

                Intent intento = new Intent(getApplicationContext(), DetalleComidaScrollingActivity.class);
                intento.putExtras(bundle);

                startActivity(intento);
            }
        });
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

    private void showOtrasList() {
        String url = Parametros.URL_SHOW_OTRAS;

        CustomRequest jreq = new CustomRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("otras");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject otras = jsonArray.getJSONObject(i);

                        String name = otras.getString("nombre");
                        String region = otras.getString("region");
                        String id = otras.getString("id");
                        int picture = R.drawable.elcaldoicono;
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
                Toast.makeText(OtrasComidasActivity.this, "Something its wrong", Toast.LENGTH_SHORT).show();
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
        requestQueue.add(jreq);
    }

    private void setupAdapter(ArrayList<Categoria> categoria) {
        this.lista.setAdapter(new AdaptadorCategoria(categoria, this));
    }

}
