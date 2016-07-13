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
 * Created by Lazaro on 10/9/2015.
 */
public class AdaptadorDetalle extends BaseAdapter {

    private ArrayList<CategoriaDetalleComida> categoria;
    LayoutInflater inflater;
    Context contexto;

    /**
     * Constructor de la clase
     *
     * @param cat
     * @param con
     */
    public AdaptadorDetalle(ArrayList<CategoriaDetalleComida> cat, Context con) {
        this.categoria = cat;
        inflater = LayoutInflater.from(con);
        this.contexto = con;
    }

    /**
     * @return size y position
     */
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

    /**
     * @param position
     * @param convertView
     * @param parent
     * @return convertView
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.detalle_adaptador, null);
        CategoriaDetalleComida cat = categoria.get(position);
        TextView ingredientes = (TextView) convertView.findViewById(R.id.tvIngredientes);
        TextView preparacion = (TextView) convertView.findViewById(R.id.tvPreparacion);
        ingredientes.setText(cat.getIngredientes());
        preparacion.setText(cat.getPreparacion());

        return convertView;

    }

}
