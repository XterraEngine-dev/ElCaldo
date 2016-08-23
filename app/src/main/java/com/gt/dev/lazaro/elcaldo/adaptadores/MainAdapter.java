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
 * Created by Lazarus on 02/08/2016.
 */
public class MainAdapter extends BaseAdapter {

    private ArrayList<MainClass> categoria;
    LayoutInflater inflater;
    Context contexto;

    public MainAdapter(ArrayList<MainClass> cat, Context con) {
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

        convertView = inflater.inflate(R.layout.main_adapter_list, null);
        MainClass cat = categoria.get(position);
        ImageView picture = (ImageView) convertView.findViewById(R.id.iv_main_list);

        picture.setBackgroundResource(cat.getPicture());

        return convertView;
    }
}
