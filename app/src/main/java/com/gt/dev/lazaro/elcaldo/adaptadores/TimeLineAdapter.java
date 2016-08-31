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
 * Created by Lazarus on 05/08/2016.
 */
public class TimeLineAdapter extends BaseAdapter {

    private ArrayList<TimeLine> categoria;
    LayoutInflater inflater;
    Context contexto;
    private ImageView ivAvatar;
    private NetworkImageView ivPicture;
    private TextView tvUsername, tvRecipename, tvRegion, tvLikes;

    public TimeLineAdapter(ArrayList<TimeLine> cat, Context con) {
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
        convertView = inflater.inflate(R.layout.timeline_adapter, null);
        TimeLine cat = categoria.get(position);

        ivAvatar = (ImageView) convertView.findViewById(R.id.iv_username_timeline);
        ivPicture = (NetworkImageView) convertView.findViewById(R.id.iv_plate_timeline);
        tvUsername = (TextView) convertView.findViewById(R.id.tv_username_timeline);
        tvRegion = (TextView) convertView.findViewById(R.id.tv_region_timeline);
        tvRecipename = (TextView) convertView.findViewById(R.id.tv_platename_timeline);
        tvLikes = (TextView) convertView.findViewById(R.id.tv_likes_timeline);

        ivAvatar.setBackgroundResource(cat.getAvatar());
        ivPicture.setImageUrl(cat.getPicture(), VolleySingleton.getInstance().getImageLoader());

        tvUsername.setText(cat.getUsername());
        tvRecipename.setText(cat.getRecipename());
        tvRegion.setText(cat.getRegion());
        tvLikes.setText(cat.getLikes());

        return convertView;
    }
}
