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
public class AdaptadorDrawer extends BaseAdapter {

    private ArrayList<CategoriaDrawer> categoria;
    LayoutInflater inflater;
    Context contexto;

    /**
     * @param cat
     * @param con
     */
    public AdaptadorDrawer(ArrayList<CategoriaDrawer> cat, Context con) {
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
     * @return converView
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.drawer_adaptador, null);
        CategoriaDrawer cat = categoria.get(position);
        ImageView img = (ImageView) convertView.findViewById(R.id.item);
        TextView nombre = (TextView) convertView.findViewById(R.id.nombre);

        img.setImageDrawable(contexto.getResources().getDrawable(cat.getImagen()));
        nombre.setText(cat.getNombre());

        return convertView;
    }
}
