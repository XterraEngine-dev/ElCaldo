package com.gt.dev.lazaro.elcaldo.vista.actividades;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.gt.dev.lazaro.elcaldo.R;
import com.gt.dev.lazaro.elcaldo.adaptadores.AdaptadorCategoria;
import com.gt.dev.lazaro.elcaldo.adaptadores.Categoria;
import com.gt.dev.lazaro.elcaldo.controlador.AppController;
import com.gt.dev.lazaro.elcaldo.controlador.CustomRequest;
import com.gt.dev.lazaro.elcaldo.utilidades.ConexionVerify;
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

    private ListView lista;
    private ArrayList<Categoria> categoria = new ArrayList<>();
    private Toolbar toolbar;
    private FloatingActionButton boton;
    private ProgressDialog pDialog;

    /**
     * @param savedInstanceState Metodo nativo inicilazador de cada metodo y variable
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startVars();
        showOtrasList();
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

        //Iniciamos e instanciamos el progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Cargando...");
        pDialog.setCancelable(false);
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
                        String picture = otras.getString("imagen");
                        categoria.add(new Categoria(name, region, id, picture));
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
                String credentials = Base64.encodeToString(("cifuentes_estrada@hotmail.com" + ":" + "azazelxd").getBytes(), Base64.NO_WRAP);
                headers.put("Authorization", "Basic " + credentials);
                return headers;
            }
        };
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
        alertDialog.setTitle("No tiene conexión a internet");
        alertDialog.setMessage("Conectese a una red de internet");
        alertDialog.show();
    }

    /**
     * Metodo donde valida la conexiòn a internet
     */
    private void verifyConnection() {
        if (!ConexionVerify.isNetworkAvailable(this)) {
            showAlertDialog("Sin conexion a internet", "Debes conectarte a una red de internet", true);
            onStop();
        }
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
}
