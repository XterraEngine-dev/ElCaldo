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
 * Created by Fernando on 31/08/2016.
 */
public class AdaptadorBuscar extends BaseAdapter{
    private ArrayList<ArrayBuscador> buscar;
    LayoutInflater inflater;
    Context contexto;

    /**
     * @param cat
     * @param con
     */
    public AdaptadorBuscar(ArrayList<ArrayBuscador> cat, Context con) {
        this.buscar = cat;
        inflater = LayoutInflater.from(con);
        this.contexto = con;
    }

    /**
     * @return size y position
     */
    @Override
    public int getCount() {
        return buscar.size();
    }

    @Override
    public Object getItem(int position) {
        return buscar.get(position);
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

        convertView = inflater.inflate(R.layout.adaptador_lista, null);
        ArrayBuscador cat = buscar.get(position);
        ImageView img = (ImageView) convertView.findViewById(R.id.imagen);
        TextView titulo = (TextView) convertView.findViewById(R.id.titulo);
        TextView subtitulo = (TextView) convertView.findViewById(R.id.subtitulo);

        img.setBackgroundResource(cat.getImagen());
        titulo.setText(cat.getTitulo());
        subtitulo.setText(cat.getSubtitulo());

        return convertView;
    }
}
