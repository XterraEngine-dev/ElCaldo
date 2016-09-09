package com.gt.dev.lazaro.elcaldo.vista.fragmentos.timeline;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.gt.dev.lazaro.elcaldo.R;
import com.gt.dev.lazaro.elcaldo.adaptadores.Coment;
import com.gt.dev.lazaro.elcaldo.adaptadores.ComentAdapter;
import com.gt.dev.lazaro.elcaldo.controlador.AppController;
import com.gt.dev.lazaro.elcaldo.controlador.CustomRequest;
import com.gt.dev.lazaro.elcaldo.utilidades.Parametros;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lazarus on 8/9/2016.
 */
public class ComentarioTimeLine extends Fragment {

    private TextView tvUsername, tvLikes;
    private ImageView ivAvatar;
    private ListView lvComents;
    private String id, username, likes;
    private ArrayList<Coment> categoria = new ArrayList<>();

    public ComentarioTimeLine() {
        //Constructor debe estar vacio
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.comentario_detalle_timeline, container, false);
        lvComents = (ListView) v.findViewById(R.id.lv_detailtime);
        tvUsername = (TextView) v.findViewById(R.id.tv_username_detailtime);
        tvLikes = (TextView) v.findViewById(R.id.tv_likes_detailtime);
        ivAvatar = (ImageView) v.findViewById(R.id.iv_avatar_detailtime);
        getSource();
        showComents();
        return v;
    }

    private void getSource() {
        Bundle bundle = getActivity().getIntent().getExtras();

        id = bundle.getString("id");
        username = bundle.getString("username");
        likes = bundle.getString("like");

        tvUsername.setText(username);
        tvLikes.setText(likes);
    }

    private void setupAdapter(ArrayList<Coment> categoria) {
        this.lvComents.setAdapter(new ComentAdapter(categoria, getActivity()));
    }

    private void showComents() {
        String url = Parametros.URL_SHOW_COMENT + "/" + "75";

        CustomRequest comentRequest = new CustomRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("RESPONSE", response.toString());
                try {
                    JSONArray jsonArray = response.getJSONArray("data");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject coment = jsonArray.getJSONObject(i);

                        String id = coment.getString("id");
                        String usuario = coment.getString("usuario");
                        String comentario = coment.getString("comentario");
                        String idTimeline = coment.getString("id_timeline");

                        categoria.add(new Coment(usuario, comentario));
                        setupAdapter(categoria);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR COMENTARIO", "ERROR = " + error.getMessage());
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
        RetryPolicy policy = new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        comentRequest.setRetryPolicy(policy);
        AppController.getInstance().addToRequestQueue(comentRequest);
    }

}
