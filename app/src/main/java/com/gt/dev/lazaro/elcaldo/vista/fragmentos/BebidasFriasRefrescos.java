package com.gt.dev.lazaro.elcaldo.vista.fragmentos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.gt.dev.lazaro.elcaldo.R;
import com.gt.dev.lazaro.elcaldo.adaptadores.AdaptadorCardView;
import com.gt.dev.lazaro.elcaldo.adaptadores.CategoriaCardView;
import com.gt.dev.lazaro.elcaldo.controlador.AppController;
import com.gt.dev.lazaro.elcaldo.controlador.CustomRequest;
import com.gt.dev.lazaro.elcaldo.utilidades.Parametros;
import com.gt.dev.lazaro.elcaldo.vista.actividades.DetailRecipeActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 20/03/16.
 */
public class BebidasFriasRefrescos extends Fragment implements AdapterView.OnItemClickListener {

    private GridView lista;
    private ArrayList<CategoriaCardView> categoria = new ArrayList<>();
    private ProgressDialog pDialog;
    private Request.Priority priority = Request.Priority.IMMEDIATE;
    public static GoogleAnalytics googleAnalytics;
    public static Tracker tracker;
    private String keyTracker;

    public BebidasFriasRefrescos() {
        //Debe estar vacio el constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_bebidas_frias, container, false);

        lista = (GridView) v.findViewById(R.id.lv_bebidas_frias);
        lista.setOnItemClickListener(this);

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage(getString(R.string.message_dialog));
        pDialog.setCancelable(false);

        showFriasList();
        setAnalytics();

        return v;
    }

    private void setAnalytics() {
        googleAnalytics = GoogleAnalytics.getInstance(getActivity());
        googleAnalytics.setLocalDispatchPeriod(1800);

        keyTracker = Parametros.TRACKER_ANALYTICS;

        tracker = googleAnalytics.newTracker(keyTracker);
        tracker.enableAdvertisingIdCollection(true);
        tracker.enableAutoActivityTracking(true);
        tracker.enableExceptionReporting(true);
    }

    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }

    private void setupAdapter(ArrayList<CategoriaCardView> categoria) {
        this.lista.setAdapter(new AdaptadorCardView(categoria, getActivity()));
    }

    private void showFriasList() {

        showProgressDialog();

        String url = Parametros.URL_SHOW_BEBIDAS_FRIAS;

        CustomRequest friasRequest = new CustomRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("frias");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject frias = jsonArray.getJSONObject(i);

                        String name = frias.getString("nombre");
                        String region = frias.getString("region");
                        String ingredientes = frias.getString("ingredientes");
                        String preparacion = frias.getString("preparacion");
                        String imagen = frias.getString("imagen");
                        categoria.add(new CategoriaCardView(name, region, preparacion, ingredientes, imagen));
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
                Log.e("FRIAS", "ERROR = " + error.toString());
                Toast.makeText(getActivity(), "Something its wrong", Toast.LENGTH_SHORT);
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
        friasRequest.setRetryPolicy(policy);
        AppController.getInstance().setPriority(priority);
        AppController.getInstance().addToRequestQueue(friasRequest);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CategoriaCardView cat = (CategoriaCardView) parent.getItemAtPosition(position);

        Bundle bundle = new Bundle();

        bundle.putString("nombre", cat.getNombre());
        bundle.putString("ingredientes", cat.getIngredientes());
        bundle.putString("preparacion", cat.getPreparacion());
        bundle.putString("region", cat.getLugar());
        bundle.putString("imagen", cat.getImagen());

        startActivity(new Intent(getActivity(), DetailRecipeActivity.class).putExtras(bundle));
    }
}
