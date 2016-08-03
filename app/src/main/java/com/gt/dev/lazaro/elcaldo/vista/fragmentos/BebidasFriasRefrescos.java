package com.gt.dev.lazaro.elcaldo.vista.fragmentos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.gt.dev.lazaro.elcaldo.adaptadores.AdaptadorCardView;
import com.gt.dev.lazaro.elcaldo.adaptadores.CategoriaCardView;
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
public class BebidasFriasRefrescos extends Fragment {

    DBManager dbManager;
    private ListView lista;
    private ArrayList<CategoriaCardView> categoria = new ArrayList<>();
    private RequestQueue requestQueue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbManager = new DBManager(getActivity());
        requestQueue = Volley.newRequestQueue(getActivity());
        showFriasList();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_bebidas_frias, container, false);

        lista = (ListView) v.findViewById(R.id.lv_bebidas_frias);


        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                CategoriaCardView categoria = (CategoriaCardView) parent.getItemAtPosition(position);

                Bundle bundle = new Bundle();
                bundle.putString(DetalleComidaScrollingActivity.NOMBRE_PLATO, categoria.getNombre());
                bundle.putInt("llave", categoria.getImagen());

                startActivity(new Intent(getActivity(), DetalleComidaScrollingActivity.class).putExtras(bundle));
            }
        });

        return v;
    }

    private void setupAdapter(ArrayList<CategoriaCardView> catgoria) {
        this.lista.setAdapter(new AdaptadorCardView(categoria, getActivity()));
    }

    private void showFriasList() {
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
                        String id = frias.getString("id");
                        int picture = R.drawable.elcaldoicono;
                        categoria.add(new CategoriaCardView(name, region, picture));
                        setupAdapter(categoria);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Something its wrong", Toast.LENGTH_SHORT);
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
        requestQueue.add(friasRequest);
    }

}
