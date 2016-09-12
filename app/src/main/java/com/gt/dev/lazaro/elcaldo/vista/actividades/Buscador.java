package com.gt.dev.lazaro.elcaldo.vista.actividades;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;
import com.gt.dev.lazaro.elcaldo.R;
import com.gt.dev.lazaro.elcaldo.adaptadores.AdaptadorBuscar;
import com.gt.dev.lazaro.elcaldo.adaptadores.ArrayBuscador;
import com.gt.dev.lazaro.elcaldo.controlador.AppController;
import com.gt.dev.lazaro.elcaldo.controlador.CustomRequest;
import com.gt.dev.lazaro.elcaldo.utilidades.Parametros;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Buscador extends AppCompatActivity implements SearchView.OnQueryTextListener {

    /**
     * Variables de buscador
     *
     * @param savedInstanceState
     */
    private ArrayList<ArrayBuscador> buscador = new ArrayList<>();
    private GridView lvBuscar;
    public static String consultaApi;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscador);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupVars();

    }

    /**
     * Inicio de variables del buscador
     */

    private void setupVars() {
        lvBuscar = (GridView) findViewById(R.id.listBuscar);
        searchView = (SearchView) findViewById(R.id.action_search);
    }

    /**
     * Menu de busqueda
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

        return true;
    }

    /**
     * Adaptador
     *
     * @param buscador
     */
    private void setupAdapter(ArrayList<ArrayBuscador> buscador) {
        this.lvBuscar.setAdapter(new AdaptadorBuscar(buscador, this));
    }

    /**
     * Obtiene la consulta a API por metodo GET
     */

    private void getBuscador() {

        CustomRequest caldosRequest = new CustomRequest(CustomRequest.Method.GET, consultaApi, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONObject jsonObject = response.getJSONObject("nombre");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject nombre = jsonArray.getJSONObject(i);
                        String name = nombre.getString("nombre");
                        String region = nombre.getString("region");
                        String id = nombre.getString("id");
                        String picture = nombre.getString("imagen");
                        buscador.add(new ArrayBuscador(name, region, id, picture));
                        setupAdapter(buscador);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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
        };
        RetryPolicy policy = new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        caldosRequest.setRetryPolicy(policy);
        AppController.getInstance().addToRequestQueue(caldosRequest);
    }

    /**
     * Cuando el texto es ingresado ejecuta la busqueda
     *
     * @param query
     * @return
     */
    @Override
    public boolean onQueryTextSubmit(String query) {

        Log.i("search", "ingreso texto");

        // Ver que URL esta consultando
        Log.i("url", "" + consultaApi);

        // Crea el URL para la consulta
        consultaApi = Parametros.URL_SHOW_BUSCAR + "/" + query;

        // Crea la consulta por metodo get a API restful
        getBuscador();
        return false;
    }

    /**
     * C
     *
     * @param newText
     * @return
     */
    @Override
    public boolean onQueryTextChange(String newText) {
        Log.i("search", "ingreso cambio texto");

        //Cuando el texto cambia el Array se limpia para nueva consulta
        buscador.clear();
        return false;
    }
}
