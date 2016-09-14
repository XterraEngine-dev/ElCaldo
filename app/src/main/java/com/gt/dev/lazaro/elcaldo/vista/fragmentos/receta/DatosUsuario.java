package com.gt.dev.lazaro.elcaldo.vista.fragmentos.receta;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.android.volley.Request;
import com.gt.dev.lazaro.elcaldo.R;
import com.gt.dev.lazaro.elcaldo.adaptadores.CategoriaCardView;

import java.util.ArrayList;


public class DatosUsuario extends Fragment {

    private ArrayList<CategoriaCardView> categoria = new ArrayList<>();
    private GridView lvCalientes;
    public static final String KEY_PICTURE = "picture";
    private ProgressDialog pDialog;
    private Request.Priority priority = Request.Priority.IMMEDIATE;

    public DatosUsuario() {
        //Debe estar vacio el constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_datos_usuario, container, false);


        return v;
    }
}