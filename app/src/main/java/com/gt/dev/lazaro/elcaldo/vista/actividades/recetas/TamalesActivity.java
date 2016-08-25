package com.gt.dev.lazaro.elcaldo.vista.actividades.recetas;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.GridView;
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

public class TamalesActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RequestQueue requestQueue;
    private ArrayList<Categoria> categoria = new ArrayList<>();
    private GridView lvTamales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tamales);
        verifyConnection();
        startVars();
        getTamales();
    }

    private void startVars() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_tamales_activity);
        lvTamales = (GridView) findViewById(R.id.lv_tamales);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.tamales_title);
        getSupportActionBar().setSubtitle("Select a recipe");
        getSupportActionBar().setIcon(R.drawable.otrascomidas);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //requestQueue = Volley.newRequestQueue(this);
    }

    private void setupAdapter(ArrayList<Categoria> categoria) {
        this.lvTamales.setAdapter(new AdaptadorCategoria(categoria, this));
    }

    private void getTamales() {
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
                        String id = tamales.getString("id");
                        int picture = R.drawable.tamalcolorado;
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
                Log.d("ERROR RESPONSE", "TAMALES JSONARRAY");
                Toast.makeText(TamalesActivity.this, "Verifica tu conexión a internet", Toast.LENGTH_SHORT).show();
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
        //requestQueue.add(tamalesRequest);
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

}
