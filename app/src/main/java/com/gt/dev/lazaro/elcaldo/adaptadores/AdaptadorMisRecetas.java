package com.gt.dev.lazaro.elcaldo.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gt.dev.lazaro.elcaldo.R;

import java.util.ArrayList;

/**
 * Created by root on 13/11/15.
 */
public class AdaptadorMisRecetas extends BaseAdapter {

    private ArrayList<CategoriaMisRecetas> categoria;
    LayoutInflater inflater;
    Context contexto;

    public AdaptadorMisRecetas(ArrayList<CategoriaMisRecetas> cat, Context con) {
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

        convertView = inflater.inflate(R.layout.titulo_adaptador, null);
        CategoriaMisRecetas cat = categoria.get(position);
        TextView titulo = (TextView) convertView.findViewById(R.id.tvTitulo);
        titulo.setText(cat.getTitulo());

        return convertView;
    }
}
