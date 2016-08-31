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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.gt.dev.lazaro.elcaldo.R;
import com.gt.dev.lazaro.elcaldo.adaptadores.AdaptadorCardView;
import com.gt.dev.lazaro.elcaldo.adaptadores.CategoriaCardView;
import com.gt.dev.lazaro.elcaldo.controlador.AppController;
import com.gt.dev.lazaro.elcaldo.controlador.CustomRequest;
import com.gt.dev.lazaro.elcaldo.modelo.DBManager;
import com.gt.dev.lazaro.elcaldo.utilidades.Parametros;
import com.gt.dev.lazaro.elcaldo.vista.actividades.DetalleComidaScrollingActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 20/03/16.
 */
public class BebidasCalientes extends Fragment {

    private ArrayList<CategoriaCardView> categoria = new ArrayList<>();
    private GridView lvCalientes;
    public static final String KEY_PICTURE = "picture";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showCalientesList();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_bebidas_calientes, container, false);
        lvCalientes = (GridView) v.findViewById(R.id.lv_bebidas_calientes);

        return v;
    }


    private void setupAdapter(ArrayList<CategoriaCardView> categoria) {
        this.lvCalientes.setAdapter(new AdaptadorCardView(categoria, getActivity()));
    }

    private void showCalientesList() {

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
                        String imagen = calientes.getString ("imagen");
                        String id = calientes.getString("id");

                        categoria.add(new CategoriaCardView(name, region, imagen));
                        setupAdapter(categoria);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("BEBIDAS", "Errro = " + error.toString());
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
        AppController.getInstance().addToRequestQueue(calientesRequest);
    }

}
