package com.gt.dev.lazaro.elcaldo.vista.fragmentos;

import android.support.v4.app.Fragment;

/**
 * Created by Lazaro on 10/9/2015.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import com.gt.dev.lazaro.elcaldo.R;
import com.gt.dev.lazaro.elcaldo.adaptadores.AdaptadorCategoria;
import com.gt.dev.lazaro.elcaldo.adaptadores.Categoria;
import com.gt.dev.lazaro.elcaldo.modelo.DBManager;
import com.gt.dev.lazaro.elcaldo.vista.actividades.DetalleComidaScrollingActivity;

/**
 * Created by Lazaro on 27/07/2015.
 */
public class Tab3 extends Fragment {

    DBManager dbManager;
    private ListView lista;
    private ArrayList<Categoria> categoria;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbManager = new DBManager(getActivity());
    }

    /**
     * onCreateView
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_3, container, false);

        lista = (ListView) v.findViewById(R.id.lv3);
        //14 platos tipicos en caldos
        categoria = new ArrayList<Categoria>();


        categoria.add(new Categoria("Rellenitos de platano", "Ciudad de Guatemala", R.drawable.rellenitos));
        categoria.add(new Categoria("Camote en dulce", "Ciudad de Guatemala", R.drawable.camote));
        categoria.add(new Categoria("Pan de manteca", "Ciudad de Guatemala", R.drawable.manteca));
        categoria.add(new Categoria("Champurradas", "Ciudad de Guatemala", R.drawable.champus));
        categoria.add(new Categoria("Chiquiadores", "Ciudad de Guatemala", R.drawable.chiquiadores));
        categoria.add(new Categoria("Barquillos", "Ciudad de Guatemala", R.drawable.barquillo));
        categoria.add(new Categoria("Quesadillas", "Ciudad de Guatemala", R.drawable.quesadilla));
        categoria.add(new Categoria("Platanos en mole", "Ciudad de Guatemala", R.drawable.mole));
        categoria.add(new Categoria("Platanos en Gloria", "Ciudad de Guatemala", R.drawable.gloria));
        categoria.add(new Categoria("Durazno en miel", "Ciudad de Guatemala", R.drawable.durasno));
        categoria.add(new Categoria("Ayote en dulce", "Ciudad de Guatemala", R.drawable.ayote));
        categoria.add(new Categoria("Nances en miel", "Ciudad de Guatemala", R.drawable.nance));
        categoria.add(new Categoria("Buñuelos", "Ciudad de Guatemala", R.drawable.bunuelos));
        categoria.add(new Categoria("Nuegados", "Ciudad de Guatemala", R.drawable.nuegados));
        categoria.add(new Categoria("Torrejas", "Ciudad de Guatemala", R.drawable.torrejas));
        categoria.add(new Categoria("Molletes", "Ciudad de Guatemala", R.drawable.molletes));
        categoria.add(new Categoria("Bocadillos de coco", "Ciudad de Guatemala", R.drawable.coco));
        categoria.add(new Categoria("Alborotos", "Ciudad de Guatemala", R.drawable.alboroto));
        categoria.add(new Categoria("Algodones de azucar", "Ciudad de Guatemala", R.drawable.algodones));
        categoria.add(new Categoria("Chilacayote en dulce", "Ciudad de Guatemala", R.drawable.chilacayote));
        categoria.add(new Categoria("Mazapán", "Ciudad de Guatemala", R.drawable.mazapan));

        final AdaptadorCategoria adaptador = new AdaptadorCategoria(categoria, Tab3.this.getActivity());
        lista.setAdapter(adaptador);
        //Establecemos el adaptador que hemos creado


        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Categoria categoria = (Categoria) parent.getItemAtPosition(position);

                Bundle bundle = new Bundle();
                bundle.putString(DetalleComidaScrollingActivity.NOMBRE_PLATO, categoria.getTitulo());
                bundle.putInt("llave", categoria.getImagen());
                Intent intento = new Intent(getActivity(), DetalleComidaScrollingActivity.class);
                intento.putExtras(bundle);

                startActivity(intento);
            }
        });

        return v;
    }
}
