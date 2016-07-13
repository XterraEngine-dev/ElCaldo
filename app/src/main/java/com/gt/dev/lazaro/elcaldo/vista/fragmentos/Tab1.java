package com.gt.dev.lazaro.elcaldo.vista.fragmentos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gt.dev.lazaro.elcaldo.R;
import com.gt.dev.lazaro.elcaldo.adaptadores.AdaptadorCategoria;
import com.gt.dev.lazaro.elcaldo.adaptadores.Categoria;
import com.gt.dev.lazaro.elcaldo.modelo.DBManager;
import com.gt.dev.lazaro.elcaldo.vista.actividades.DetalleComidaScrollingActivity;

import java.util.ArrayList;

/**
 * Created by Lazaro on 15/02/2015.
 */
public class Tab1 extends Fragment {
    //DBManager dbManager;
    DBManager dbManager;
    private ListView lista;
    private ArrayList<Categoria> categoria;

    /**
     * onCreate
     * Metodo que crea
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //dbManager = new DBManager(getActivity());
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
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_1, container, false);

        lista = (ListView) v.findViewById(R.id.lv1);


        //8 platos tipicos en tamales
        categoria = new ArrayList<>();

        categoria.add(new Categoria("Caldo de gallina india", "Ciudad de Guatemala", R.drawable.caldodegallina));
        categoria.add(new Categoria("Caldo de res", "Ciudad de Guatemala", R.drawable.caldoderes));
        categoria.add(new Categoria("Caldo de pata", "Ciudad de Guatemala", R.drawable.pata));
        categoria.add(new Categoria("Caldo de mariscos", "Ciudad de Guatemala", R.drawable.mariscos));
        categoria.add(new Categoria("Sopa de tortuga", "Ciudad de Guatemala", R.drawable.tortuga));
        categoria.add(new Categoria("Kaq ik", "Ciudad de Guatemala", R.drawable.kakik));
        categoria.add(new Categoria("Jocón", "Ciudad de Guatemala", R.drawable.jocon));
        categoria.add(new Categoria("Pepián", "Ciudad de Guatemala", R.drawable.pepian));
        categoria.add(new Categoria("Pepián Indio", "Ciudad de Guatemala", R.drawable.pepianindio));
        categoria.add(new Categoria("Pollo en Amarillo", "Ciudad de Guatemala", R.drawable.polloenamarillo));
        categoria.add(new Categoria("Hilachas de res", "Ciudad de Guatemala", R.drawable.hilachas));
        categoria.add(new Categoria("Revolcado de cerdo", "Ciudad de Guatemala", R.drawable.revolcado));
        categoria.add(new Categoria("Pulique", "Ciudad de Guatemala", R.drawable.pulique));
        categoria.add(new Categoria("Gallo en Chicha", "Ciudad de Guatemala", R.drawable.galloenchicha));

        final AdaptadorCategoria adaptador = new AdaptadorCategoria(categoria, Tab1.this.getActivity());
        lista.setAdapter(adaptador);
        //Establecemos el adaptador que hemos creado


        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                /*Categoria imagen = categoria.get(position);
                Intent intento = new Intent(getActivity(), DetalleComidaActivity.class);
                intento.putExtra("image", imagen.getImagen());
                startActivity(intento);*/

                Categoria categoria = (Categoria) parent.getItemAtPosition(position);

                Bundle bundle = new Bundle();
                bundle.putString(DetalleComidaScrollingActivity.NOMBRE_PLATO, categoria.getTitulo());
                bundle.putInt("llave", categoria.getImagen());

                Intent intent = new Intent(getActivity(), DetalleComidaScrollingActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                //startActivity(new Intent(getActivity(), DetalleComidaActivity.class));
                //Toast.makeText(getActivity(), categoria.get(position).toString(), Toast.LENGTH_SHORT).show();
            }
        });


        return v;

    }


}