package com.gt.dev.lazaro.elcaldo.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.gt.dev.lazaro.elcaldo.R;
import com.gt.dev.lazaro.elcaldo.utilidades.VolleySingleton;

import java.util.ArrayList;

/**
 * Created by root on 18/03/16.
 */
public class AdaptadorCardView extends BaseAdapter {

    private ArrayList<CategoriaCardView> categoria;
    LayoutInflater inflater;
    Context contexto;
    private TextView tvNombre, tvLugar;
    private NetworkImageView ivImagen;

    public AdaptadorCardView(ArrayList<CategoriaCardView> cat, Context con) {
        this.categoria = cat;
        inflater = LayoutInflater.from(con);
        this.contexto = con;
    }

    @Override

    public int getCount() {
        return categoria.size();
    }

    @Override
    public Object getItem(int position) {
        return categoria.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.adaptador_lista, null);
        CategoriaCardView cat = categoria.get(position);
        tvNombre = (TextView) convertView.findViewById(R.id.titulo);
        tvLugar = (TextView) convertView.findViewById(R.id.subtitulo);
        ivImagen = (NetworkImageView) convertView.findViewById(R.id.imagen);

        tvNombre.setText(cat.getNombre());
        tvLugar.setText(cat.getLugar());
        ivImagen.setImageUrl(cat.getImagen(), VolleySingleton.getInstance().getImageLoader());
        return convertView;
    }
}
