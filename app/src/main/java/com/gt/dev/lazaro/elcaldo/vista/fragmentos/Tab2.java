package com.gt.dev.lazaro.elcaldo.vista.fragmentos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
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
import com.gt.dev.lazaro.elcaldo.adaptadores.Categoria;
import com.gt.dev.lazaro.elcaldo.controlador.CustomRequest;
import com.gt.dev.lazaro.elcaldo.modelo.DBManager;
import com.gt.dev.lazaro.elcaldo.utilidades.Parametros;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lazaro on 15/02/2015.
 */
public class Tab2 extends Fragment {

    DBManager dbManager;
    private ListView lista;
    private ArrayList<Categoria> categoria;
    private RequestQueue requestQueue;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbManager = new DBManager(getActivity());
    }

    /**
     * onCreateView
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_2, container, false);

        lista = (ListView) v.findViewById(R.id.lv2);

        //categoria = new ArrayList<Categoria>();
        //21 platos tipicos en postres


        /*categoria.add(new Categoria("Tamal colorado", "Ciudad de Guatemala", R.drawable.tamalcolorado));
        categoria.add(new Categoria("Tamal negro", "Ciudad de Guatemala", R.drawable.negro));
        categoria.add(new Categoria("Tamales de Chipilin", "Ciudad de Guatemala", R.drawable.chipilin));
        categoria.add(new Categoria("Tamales de Cambray", "Ciudad de Guatemala", R.drawable.cambray));
        categoria.add(new Categoria("Chepes o tayuyos", "Ciudad de Guatemala", R.drawable.chepes));
        categoria.add(new Categoria("Tamalitos de elote", "Ciudad de Guatemala", R.drawable.elote));
        categoria.add(new Categoria("Tamales de pache", "Ciudad de Guatemala", R.drawable.pache));
        categoria.add(new Categoria("Chuchitos", "Ciudad de Guatemala", R.drawable.chuchitos));*/

        //final AdaptadorCategoria adaptador = new AdaptadorCategoria(categoria, Tab2.this.getActivity());
        //lista.setAdapter(adaptador);
        //Establecemos el adaptador que hemos creado


        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*Categoria categoria = (Categoria) parent.getItemAtPosition(position);

                Bundle bundle = new Bundle();
                bundle.putString(DetalleComidaScrollingActivity.NOMBRE_PLATO, categoria.getTitulo());
                bundle.putInt("llave", categoria.getImagen());
                Intent intento = new Intent(getActivity(), DetalleComidaScrollingActivity.class);
                intento.putExtras(bundle);

                startActivity(intento);*/
            }
        });


        return v;
    }

    private void showTamalesLIst() {
        String url = Parametros.URL_SHOW_TAMALES;

        requestQueue = Volley.newRequestQueue(getActivity());

        CustomRequest jreq = new CustomRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("tamales");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject tamales = jsonArray.getJSONObject(i);

                        String name = tamales.getString("nombre");
                        String region = tamales.getString("region");
                        int picture = R.drawable.elcaldoicono;
                        categoria.add(new Categoria(name, region, picture));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

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

}