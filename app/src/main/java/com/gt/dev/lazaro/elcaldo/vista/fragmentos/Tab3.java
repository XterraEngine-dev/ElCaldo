package com.gt.dev.lazaro.elcaldo.vista.fragmentos;

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
import android.widget.ListView;

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
import com.gt.dev.lazaro.elcaldo.vista.actividades.DetalleComidaScrollingActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lazaro on 10/9/2015.
 */

/**
 * Created by Lazaro on 27/07/2015.
 */
public class Tab3 extends Fragment {

    DBManager dbManager;
    private ListView lista;
    private ArrayList<Categoria> categoria = new ArrayList<>();
    private RequestQueue requestQueue;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbManager = new DBManager(getActivity());
        requestQueue = Volley.newRequestQueue(getActivity());
        showPostresLIst();
    }

    /**
     * onCreateView
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_3, container, false);

        lista = (ListView) v.findViewById(R.id.lv3);
        //14 platos tipicos en caldos
        //categoria = new ArrayList<Categoria>();

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Categoria categoria = (Categoria) parent.getItemAtPosition(position);

                //Ahora solo necesitaremos el ID de plato seleccionado ya que por medio de la apirest solo
                //disparamos al mismo url añandiendo el ID y nos devolvera todos los datos que necesitamos.
                Bundle bundle = new Bundle();
                bundle.putString("id", categoria.getId());
                bundle.putString(DetalleComidaScrollingActivity.NOMBRE_PLATO, categoria.getTitulo());
                bundle.putInt("llave", categoria.getImagen());
                Intent intento = new Intent(getActivity(), DetalleComidaScrollingActivity.class);
                intento.putExtras(bundle);

                startActivity(intento);
            }
        });

        return v;
    }

    private void showPostresLIst() {
        String url = Parametros.URL_SHOW_POSTRES;

        requestQueue = Volley.newRequestQueue(getActivity());

        CustomRequest jreq = new CustomRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("postres");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject postres = jsonArray.getJSONObject(i);

                        String name = postres.getString("nombre");
                        String region = postres.getString("region");
                        String id = postres.getString("id");
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
                Log.d("ERROR RESPONSSE", "SAYS" + error.toString());
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
        this.lista.setAdapter(new AdaptadorCategoria(categoria, getActivity()));
    }

}
