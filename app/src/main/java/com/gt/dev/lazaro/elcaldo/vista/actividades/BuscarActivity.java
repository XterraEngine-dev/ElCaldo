package com.gt.dev.lazaro.elcaldo.vista.actividades;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSettings;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.gt.dev.lazaro.elcaldo.R;
import com.gt.dev.lazaro.elcaldo.adaptadores.AdaptadorBuscar;
import com.gt.dev.lazaro.elcaldo.adaptadores.ArrayBuscador;
import com.gt.dev.lazaro.elcaldo.controlador.AppController;
import com.gt.dev.lazaro.elcaldo.controlador.CustomRequest;
import com.gt.dev.lazaro.elcaldo.utilidades.Parametros;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BuscarActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, InterstitialAdListener {

    private GridView gridLista;
    private EditText etSearch;
    private ImageButton btnBack;
    public static String consultaApi, fbPlace;
    private ArrayList<ArrayBuscador> buscador = new ArrayList<>();
    private InterstitialAd facebookAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);
        startVars();
    }

    private void startVars() {
        etSearch = (EditText) findViewById(R.id.et_search);
        btnBack = (ImageButton) findViewById(R.id.btn_back_search);
        gridLista = (GridView) findViewById(R.id.grid_buscar);
        gridLista.setOnItemClickListener(this);

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == EditorInfo.IME_ACTION_DONE) || ((event.getKeyCode() == KeyEvent.KEYCODE_ENTER) && (event.getAction() == KeyEvent.ACTION_DOWN))) {
                    InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                    inputManager.hideSoftInputFromWindow(etSearch.getApplicationWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);

                    getBuscador();
                    return true;
                } else {
                    return false;
                }
            }
        });

        btnBack.setOnClickListener(this);

        fbPlace = Parametros.FB_PLACEMENT_ID;
        AdSettings.addTestDevice(getString(R.string.facebook_app_id));
        facebookAd = new InterstitialAd(this, fbPlace);
        facebookAd.setAdListener(BuscarActivity.this);
        facebookAd.loadAd();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back_search:
                finish();
                break;
        }
    }

    private void setupAdapter(ArrayList<ArrayBuscador> buscador) {
        this.gridLista.setAdapter(new AdaptadorBuscar(buscador, this));
    }

    private void getBuscador() {
        Toast.makeText(BuscarActivity.this, getString(R.string.searching_loading), Toast.LENGTH_SHORT).show();

        consultaApi = Parametros.URL_SHOW_BUSCAR + "/" + etSearch.getText().toString().trim();

        CustomRequest caldosRequest = new CustomRequest(Request.Method.GET, consultaApi, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject = response.getJSONObject("nombre");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject nombre = jsonArray.getJSONObject(i);
                        String name = nombre.getString("nombre");
                        String region = nombre.getString("region");
                        String ingredientes = nombre.getString("ingredientes");
                        String preparacion = nombre.getString("preparacion");
                        String id = nombre.getString("id");
                        String picture = nombre.getString("imagen");
                        buscador.add(new ArrayBuscador(name, region, ingredientes, preparacion, id, picture));
                        setupAdapter(buscador);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("ERROR" + "" + error.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String user = Parametros.USER;
                String pass = Parametros.PASS;
                HashMap<String, String> headers = new HashMap<>();
                String credentials = Base64.encodeToString((user + ":" + pass).getBytes(), Base64.NO_WRAP);
                headers.put("Authorization", "Basic " + credentials);
                return headers;
            }
        };
        RetryPolicy policy = new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        caldosRequest.setRetryPolicy(policy);
        AppController.getInstance().addToRequestQueue(caldosRequest);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ArrayBuscador cat = (ArrayBuscador) parent.getItemAtPosition(position);

        Bundle bundle = new Bundle();

        bundle.putString("nombre", cat.getTitulo());
        bundle.putString("region", cat.getSubtitulo());
        bundle.putString("ingredientes", cat.getIngredientes());
        bundle.putString("preparacion", cat.getPreparacion());
        bundle.putString("imagen", cat.getImagen());

        startActivity(new Intent(BuscarActivity.this, DetailRecipeActivity.class).putExtras(bundle));
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
    protected void onDestroy() {
        if (facebookAd != null) {
            facebookAd.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onInterstitialDisplayed(Ad ad) {
        onPause();
    }

    @Override
    public void onInterstitialDismissed(Ad ad) {
        onResume();
    }

    @Override
    public void onError(Ad ad, AdError adError) {
        Log.d("ERROR FB", "MESSAGE = " + adError.getErrorMessage());
    }

    @Override
    public void onAdLoaded(Ad ad) {
        facebookAd.show();
    }

    @Override
    public void onAdClicked(Ad ad) {
        Log.i("AD", ad.getPlacementId());
    }
}
