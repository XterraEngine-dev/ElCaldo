package com.gt.dev.lazaro.elcaldo.adaptadores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import com.gt.dev.lazaro.elcaldo.R;

/**
 * Created by Lazaro on 9/24/2015.
 */
public class AdaptadorFavoritos extends RecyclerView.Adapter<AdaptadorFavoritos.MyViewHolder> {

    private LayoutInflater inflater;
    List<CategoriaFavoritos> data = Collections.emptyList();

    /**
     * @param context
     * @param data
     */
    public AdaptadorFavoritos(Context context, List<CategoriaFavoritos> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    /**
     * @param parent
     * @param viewType
     * @return holder
     */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.adaptador_favoritos, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    /**
     * Metodo donde manda a traer datos
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CategoriaFavoritos current = data.get(position);
        holder.title.setText(current.getTitle());
        holder.icon.setImageResource(current.getIcon());
    }

    /**
     * retorna la data por tamaño
     *
     * @return data.size
     */
    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView icon;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.listText);
            icon = (ImageView) itemView.findViewById(R.id.listIcon);
        }
    }
}

