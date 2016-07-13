package com.gt.dev.lazaro.elcaldo.vista.fragmentos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gt.dev.lazaro.elcaldo.R;
import com.gt.dev.lazaro.elcaldo.adaptadores.AdaptadorCardView;
import com.gt.dev.lazaro.elcaldo.adaptadores.Categoria;
import com.gt.dev.lazaro.elcaldo.adaptadores.CategoriaCardView;
import com.gt.dev.lazaro.elcaldo.modelo.DBManager;
import com.gt.dev.lazaro.elcaldo.vista.actividades.DetalleComidaScrollingActivity;

import java.util.ArrayList;

/**
 * Created by root on 20/03/16.
 */
public class BebidasCalientes extends Fragment {

    private ArrayList<CategoriaCardView> categoria;
    private ListView lvCalientes;
    DBManager dbManager;
    public static final String KEY_PICTURE = "picture";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbManager = new DBManager(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_bebidas_calientes, container, false);

        lvCalientes = (ListView) v.findViewById(R.id.lv_bebidas_calientes);

        categoria = new ArrayList<>();

        categoria.add(new CategoriaCardView("Atol de elote", "Guatemala", R.drawable.atoldeelote));
        categoria.add(new CategoriaCardView("Arroz en leche", "Guatemala", R.drawable.arrozenleche));
        categoria.add(new CategoriaCardView("Atol de tres cocimientos", "Guatemala", R.drawable.atoltrescocimientos));
        categoria.add(new CategoriaCardView("Atol shuco", "Guatemala", R.drawable.atolshuco));

        final AdaptadorCardView adaptador = new AdaptadorCardView(categoria, getActivity());
        lvCalientes.setAdapter(adaptador);

        lvCalientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CategoriaCardView categoria = (CategoriaCardView) parent.getItemAtPosition(position);

                Bundle bundle = new Bundle();
                bundle.putInt("llave", categoria.getImagen());
                bundle.putString(DetalleComidaScrollingActivity.NOMBRE_PLATO, categoria.getNombre());

                startActivity(new Intent(getActivity(), DetalleComidaScrollingActivity.class).putExtras(bundle));
            }
        });

        return v;
    }
}
