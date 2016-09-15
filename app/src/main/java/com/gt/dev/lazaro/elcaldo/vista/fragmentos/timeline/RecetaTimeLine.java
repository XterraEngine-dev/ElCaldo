package com.gt.dev.lazaro.elcaldo.vista.fragmentos.timeline;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.gt.dev.lazaro.elcaldo.R;
import com.gt.dev.lazaro.elcaldo.utilidades.VolleySingleton;

/**
 * Created by Lazarus on 8/9/2016.
 */
public class RecetaTimeLine extends Fragment {

    private TextView tvRecipename, tvRegion, tvIngredientes, tvPreparacion;
    private NetworkImageView niPicture;
    private String recipename, region, ingredientes, preparacion, picture, id;
    private static String idComentariosReceta;

    public RecetaTimeLine() {
        //Requiere que el constructor este vacio.
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.receta_detalle_timeline, container, false);

        tvRecipename = (TextView) v.findViewById(R.id.tv_recipename_detailtime);
        tvRegion = (TextView) v.findViewById(R.id.tv_region_detailtime);
        tvIngredientes = (TextView) v.findViewById(R.id.tv_ingredientes_detailtime);
        tvPreparacion = (TextView) v.findViewById(R.id.tv_preparacion_detailtime);
        niPicture = (NetworkImageView) v.findViewById(R.id.ni_detalle_timeline);
        getSource();

        return v;
    }

    private void getSource() {
        Bundle bundle = getActivity().getIntent().getExtras();

        id = bundle.getString("id");
        recipename = bundle.getString("recipename");
        region = bundle.getString("region");
        ingredientes = bundle.getString("ingredientes");
        preparacion = bundle.getString("preparacion");
        picture = bundle.getString("imagen");

        tvRecipename.setText(recipename);
        tvRegion.setText(region);
        tvIngredientes.setText(ingredientes);
        tvPreparacion.setText(preparacion);
        idComentariosReceta=id;
        niPicture.setImageUrl(picture, VolleySingleton.getInstance().getImageLoader());

    }

    public static String getIdComentariosReceta() {
        return idComentariosReceta;
    }



}
