package com.gt.dev.lazaro.elcaldo.vista.fragmentos.receta;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;

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


public class DatosReceta extends Fragment {

    private Spinner spinner;
    ArrayList<String> spinnerArray = new ArrayList<String>();
    private final static String CLICK = "click";

    private ArrayList<CategoriaCardView> categoria = new ArrayList<>();
    private GridView lvCalientes;
    public static final String KEY_PICTURE = "picture";
    private ProgressDialog pDialog;
    private Request.Priority priority = Request.Priority.IMMEDIATE;
    private View upSpiner;

    public DatosReceta() {
        //Debe estar vacio el constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_datos_receta, container, false);
        setupSpiner(v);

        return v;
    }


    public void setupSpiner(View v) {
        String [] values= getResources().getStringArray(R.array.regiones_gt);
        Spinner spinner = (Spinner) v.findViewById(R.id.gt);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
    }
}
