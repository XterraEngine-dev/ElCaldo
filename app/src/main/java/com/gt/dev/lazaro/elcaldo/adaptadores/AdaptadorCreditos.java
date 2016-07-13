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
public class AdaptadorCreditos extends BaseAdapter {
    private ArrayList<CategoriaCreditos> categoria;
    LayoutInflater inflater;
    Context contexto;

    /**
     * Constructor de la clase
     *
     * @param cat
     * @param con
     */
    public AdaptadorCreditos(ArrayList<CategoriaCreditos> cat, Context con) {
        this.categoria = cat;
        inflater = LayoutInflater.from(con);
        this.contexto = con;
    }

    /**
     * getters
     *
     * @return
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
        convertView = inflater.inflate(R.layout.adaptador_creditos, null);
        CategoriaCreditos cat = categoria.get(position);
        ImageView imagen = (ImageView) convertView.findViewById(R.id.ivImagen);
        TextView ocupacion = (TextView) convertView.findViewById(R.id.tvOcupacion);
        TextView nombre = (TextView) convertView.findViewById(R.id.tvNombre);
        TextView cuenta = (TextView) convertView.findViewById(R.id.tvCuenta);

        imagen.setImageDrawable(contexto.getResources().getDrawable(cat.getImagen()));
        ocupacion.setText(cat.getOcupacion());
        nombre.setText(cat.getNombre());
        cuenta.setText(cat.getCuenta());
        return convertView;
    }
}

