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
    private TextView tvUsername, tvComent;

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
        tvUsername = (TextView) convertView.findViewById(R.id.tv_username_detailtime);
        tvComent = (TextView) convertView.findViewById(R.id.tv_coment_comentdetail);

        tvUsername.setText(cat.getUsername());
        tvComent.setText(cat.getComent());

        return convertView;
    }
}
