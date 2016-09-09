package com.gt.dev.lazaro.elcaldo.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gt.dev.lazaro.elcaldo.R;

import java.util.ArrayList;

/**
 * Created by Lazarus on 8/9/2016.
 */
public class ComentAdapter extends BaseAdapter {

    private ArrayList<Coment> categoria;
    LayoutInflater inflater;
    Context contexto;
    private TextView tvUsername, tvComent, tvId, tvIdComentario;

    public ComentAdapter(ArrayList<Coment> cat, Context con) {
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

        convertView = inflater.inflate(R.layout.coments_adapter_detail, null);
        Coment cat = categoria.get(position);
        tvUsername = (TextView) convertView.findViewById(R.id.tv_name_comentdetail);
        tvComent = (TextView) convertView.findViewById(R.id.tv_coment_comentdetail);
        tvId =(TextView)convertView.findViewById(R.id.tv_id);
        tvIdComentario=(TextView)convertView.findViewById(R.id.tv_idComentario);

        tvId.setText(cat.getId());
        tvUsername.setText(cat.getUsername());
        tvComent.setText(cat.getComent());
        tvIdComentario.setText(cat.getIdTimeline());

        return convertView;
    }
}
