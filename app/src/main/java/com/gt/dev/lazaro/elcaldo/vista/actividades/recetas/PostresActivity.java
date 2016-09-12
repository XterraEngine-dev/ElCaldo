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

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
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

public class PostresActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private Toolbar toolbar;
    private ArrayList<Categoria> categoria = new ArrayList<>();
    private GridView lvPostres;
    private ProgressDialog pDialog;
    private Request.Priority priority = Request.Priority.IMMEDIATE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postres);
        verifyConnection();
        startVars();
        getPostres();
    }

    private void startVars() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_postres_activity);
        setSupportActionBar(toolbar);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage(getString(R.string.message_dialog));
        pDialog.setCancelable(false);
        lvPostres = (GridView) findViewById(R.id.lv_postres);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.postres_title);
        getSupportActionBar().setSubtitle(getString(R.string.select_recipe));
        getSupportActionBar().setIcon(R.drawable.otrascomidas);
        lvPostres.setOnItemClickListener(this);
    }

    private void setupAdapter(ArrayList<Categoria> categoria) {
        this.lvPostres.setAdapter(new AdaptadorCategoria(categoria, this));
    }

    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }

    private void getPostres() {

        showProgressDialog();

        String url = Parametros.URL_SHOW_POSTRES;

        CustomRequest postresRequest = new CustomRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("postres");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject postres = jsonArray.getJSONObject(i);

                        String name = postres.getString("nombre");
                        String region = postres.getString("region");
                        String ingredientes = postres.getString("ingredientes");
                        String preparacion = postres.getString("preparacion");
                        String id = postres.getString("id");
                        String picture = postres.getString("imagen");
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
                Log.d("ERROR RESPONSE", "POSTRESS REQUEST");
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
        postresRequest.setRetryPolicy(policy);
        AppController.getInstance().setPriority(priority);
        AppController.getInstance().addToRequestQueue(postresRequest);
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
        AlertDialog alertDialog = new AlertDialog.Builder(PostresActivity.this).create();
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
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Categoria cat = (Categoria) parent.getItemAtPosition(position);

        Bundle bundle = new Bundle();

        bundle.putString("nombre", cat.getTitulo());
        bundle.putString("ingredientes", cat.getIngredientes());
        bundle.putString("preparacion", cat.getPreparacion());
        bundle.putString("region", cat.getSubtitulo());

        startActivity(new Intent(PostresActivity.this, DetailRecipeActivity.class).putExtras(bundle));
    }
}
