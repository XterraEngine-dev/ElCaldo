package com.gt.dev.lazaro.elcaldo.vista.fragmentos.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gt.dev.lazaro.elcaldo.R;

/**
 * Created by Lazarus on 10/9/2016.
 */
public class Preparacion extends Fragment {

    private String preparacion;
    private TextView tvPreparacion;

    public Preparacion() {
        //Constructor de la clase debe estar vacio
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.preparacion_detail_recipe, container, false);

        tvPreparacion = (TextView) v.findViewById(R.id.tv_preparacion_detail_recipe);
        getSource();

        return v;
    }

    private void getSource() {
        Bundle bundle = getActivity().getIntent().getExtras();

        preparacion = bundle.getString("preparacion");
        tvPreparacion.setText(preparacion);
    }

}
