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

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
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
public class BebidasCalientes extends Fragment implements AdapterView.OnItemClickListener {

    private ArrayList<CategoriaCardView> categoria = new ArrayList<>();
    private GridView lvCalientes;
    public static final String KEY_PICTURE = "picture";
    private ProgressDialog pDialog;
    private Request.Priority priority = Request.Priority.IMMEDIATE;

    public BebidasCalientes() {
        //Debe estar vacio el constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_bebidas_calientes, container, false);

        lvCalientes = (GridView) v.findViewById(R.id.lv_bebidas_calientes);
        lvCalientes.setOnItemClickListener(this);
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage(getString(R.string.message_dialog));
        pDialog.setCancelable(false);

        showCalientesList();

        return v;
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
        this.lvCalientes.setAdapter(new AdaptadorCardView(categoria, getActivity()));
    }

    private void showCalientesList() {

        showProgressDialog();

        String url = Parametros.URL_SHOW_BEBIDAS_CALIENTES;

        CustomRequest calientesRequest = new CustomRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("calientes");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject calientes = jsonArray.getJSONObject(i);

                        String name = calientes.getString("nombre");
                        String region = calientes.getString("region");
                        String ingredientes = calientes.getString("ingredientes");
                        String preparacion = calientes.getString("preparacion");
                        String imagen = calientes.getString("imagen");

                        categoria.add(new CategoriaCardView(name, region, ingredientes, preparacion, imagen));
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
                Log.e("BEBIDAS", "Errro = " + error.toString());
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
        calientesRequest.setRetryPolicy(policy);
        AppController.getInstance().setPriority(priority);
        AppController.getInstance().addToRequestQueue(calientesRequest);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CategoriaCardView cat = (CategoriaCardView) parent.getItemAtPosition(position);

        Bundle bundle = new Bundle();

        bundle.putString("nombre", cat.getNombre());
        bundle.putString("ingredientes", cat.getIngredientes());
        bundle.putString("preparacion", cat.getPreparacion());
        bundle.putString("region", cat.getLugar());

        startActivity(new Intent(getActivity(), DetailRecipeActivity.class).putExtras(bundle));
    }
}
