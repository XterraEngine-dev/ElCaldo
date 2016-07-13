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
public class BebidasFriasRefrescos extends Fragment {

    DBManager dbManager;
    private ListView lista;
    private ArrayList<CategoriaCardView> categoria;
    public static final String KEY_INT = "picint";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbManager = new DBManager(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_bebidas_frias, container, false);

        lista = (ListView) v.findViewById(R.id.lv_bebidas_frias);

        categoria = new ArrayList<>();

        categoria.add(new CategoriaCardView("Fresco de chilacayote", "Guatemala", R.drawable.frescodechilacayote));
        categoria.add(new CategoriaCardView("Fresco de tiste", "Guatemala", R.drawable.frescodetiste));
        categoria.add(new CategoriaCardView("Fresco de chan", "Guatemala", R.drawable.frescodechan));
        categoria.add(new CategoriaCardView("Fresco de suchiles", "Guatemala", R.drawable.frescodesuchiles));
        categoria.add(new CategoriaCardView("Batido de leche", "Guatemala", R.drawable.batidodeleche));
        categoria.add(new CategoriaCardView("Batido caliente", "Guatemala", R.drawable.batidocaliente));
        categoria.add(new CategoriaCardView("Horchata de arroz", "Guatemala", R.drawable.horchatadearroz));
        categoria.add(new CategoriaCardView("Boj", "Guatemala", R.drawable.boj));

        final AdaptadorCardView adapter = new AdaptadorCardView(categoria, getActivity());

        lista.setAdapter(adapter);

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
}
