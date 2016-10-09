package com.gt.dev.lazaro.elcaldo.vista.actividades;

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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Fernnado Lazaro
 */
public class OtrasComidasActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdListener {

    private ListView lista;
    private ArrayList<Categoria> categoria = new ArrayList<>();
    private Toolbar toolbar;
    private ProgressDialog pDialog;
    private Request.Priority priority = Request.Priority.IMMEDIATE;

    //GoogleAnalytics var
    public static GoogleAnalytics googleAnalytics;
    public static Tracker tracker;
    private String keyTracker;

    /**
     * @param savedInstanceState Metodo nativo inicilazador de cada metodo y variable
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startVars();
        showOtrasList();
        setAnalytics();
    }

    /**
     * Metodo que inicializa e instancea las variables, widgets, metodos, etc.
     */
    private void startVars() {
        setContentView(R.layout.activity_otras_comidas);

        //Seteamos el toolbar
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Llamamos al metodo para la verificaciòn de internet
        verifyConnection();

        //Casteamos la lista
        lista = (ListView) findViewById(R.id.lv4);
        lista.setOnItemClickListener(this);

        //Iniciamos e instanciamos el progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setMessage(getString(R.string.message_dialog));
        pDialog.setCancelable(false);
    }

    private void setAnalytics() {
        googleAnalytics = GoogleAnalytics.getInstance(this);
        googleAnalytics.setLocalDispatchPeriod(1800);

        keyTracker = Parametros.TRACKER_ANALYTICS;

        tracker = googleAnalytics.newTracker(keyTracker);
        tracker.enableAdvertisingIdCollection(true);
        tracker.enableAutoActivityTracking(true);
        tracker.enableExceptionReporting(true);
    }

    /**
     * Muestra el ProgressDialog
     */
    private void showProgressDialog() {
        //Si pDialog es distinto de isShowing entonces lo muestra -> show
        if (!pDialog.isShowing())
            pDialog.show();
    }

    /**
     * Oculta el progressDialog
     */
    private void hideProgressDialog() {
        //Si pDialog se esta mostrando -> isShowing entonces ocultarlo -> hide
        if (pDialog.isShowing())
            pDialog.hide();
    }

    /**
     * Metodo donde indicamos la acciòn de cada item
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Muestra la lista de otras comidas
     */
    private void showOtrasList() {

        showProgressDialog();

        //Obtenemos el URL de la clase parametros
        String url = Parametros.URL_SHOW_OTRAS;

        //Lllamamos a nuestra clase Customizada para los json request
        CustomRequest jreq = new CustomRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //capturaremos nuestro JsonArray en un try-catch para asegurarnos de su
                //traida de data y tambien si no es asi.
                try {
                    JSONArray jsonArray = response.getJSONArray("otras");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject otras = jsonArray.getJSONObject(i);

                        String name = otras.getString("nombre");
                        String region = otras.getString("region");
                        String id = otras.getString("id");
                        String ingredientes = otras.getString("ingredientes");
                        String preparacion = otras.getString("preparacion");
                        String picture = otras.getString("imagen");
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
                Toast.makeText(OtrasComidasActivity.this, "Something its wrong", Toast.LENGTH_SHORT).show();
                Log.d("ERROR OTRAS", error.toString());
            }
        }) {
            /**
             * Validamos credenciales para realizar transacciòn con basic auth
             * @return
             * @throws AuthFailureError
             */
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
        jreq.setRetryPolicy(policy);
        AppController.getInstance().setPriority(priority);
        AppController.getInstance().addToRequestQueue(jreq);
    }

    /**
     * Metodo donde setamos el adaptador a utilizar en la lista
     *
     * @param categoria
     */
    private void setupAdapter(ArrayList<Categoria> categoria) {
        this.lista.setAdapter(new AdaptadorCategoria(categoria, this));
    }

    /**
     * Alert Dialog para mostrar la falta de conexiòn a internet
     *
     * @param title
     * @param message
     * @param status
     */
    private void showAlertDialog(String title, String message, boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(OtrasComidasActivity.this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.show();
    }

    /**
     * Metodo donde valida la conexiòn a internet
     */
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

        startActivity(new Intent(OtrasComidasActivity.this, DetailRecipeActivity.class).putExtras(bundle));
    }

    /**
     * Cuando entra en pausa la actividad-background
     */
    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * Cuando la actividad vuelve a la acciòn :D
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onError(Ad ad, AdError adError) {
        Log.d("ERROR = " + adError.getErrorMessage(), "CODE = " + adError.getErrorCode());
    }

    @Override
    public void onAdLoaded(Ad ad) {
        //This was loaded in startvars method
    }

    @Override
    public void onAdClicked(Ad ad) {
        Log.i("AD", ad.getPlacementId());
    }
}
