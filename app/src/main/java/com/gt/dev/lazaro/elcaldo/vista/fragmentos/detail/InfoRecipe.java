package com.gt.dev.lazaro.elcaldo.vista.fragmentos.detail;

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
 * Created by Lazarus on 10/9/2016.
 */
public class InfoRecipe extends Fragment {

    private NetworkImageView pictureRecipe;
    private TextView tvRegion, tvInfoRegion;
    private String picture, region;

    public InfoRecipe() {
        //Constructor debe estar vacio
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.info_detail_recipe, container, false);

        pictureRecipe = (NetworkImageView) v.findViewById(R.id.ni_info_detail_recipe);
        tvRegion = (TextView) v.findViewById(R.id.tv_region_detail_recipe);
        tvInfoRegion = (TextView) v.findViewById(R.id.tv_info_region_detail);
        getSource();
        return v;
    }

    private void getSource() {
        Bundle bundle = getActivity().getIntent().getExtras();

        region = bundle.getString("region");
        tvRegion.setText(region);
        picture = bundle.getString("imagen");
        pictureRecipe.setImageUrl(picture, VolleySingleton.getInstance().getImageLoader());
    }

}
