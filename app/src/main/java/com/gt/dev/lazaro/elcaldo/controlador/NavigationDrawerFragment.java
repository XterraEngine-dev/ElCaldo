package com.gt.dev.lazaro.elcaldo.controlador;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gt.dev.lazaro.elcaldo.R;
import com.gt.dev.lazaro.elcaldo.adaptadores.AdaptadorDrawer;
import com.gt.dev.lazaro.elcaldo.adaptadores.CategoriaDrawer;
import com.gt.dev.lazaro.elcaldo.vista.actividades.AgregarRecetaActivity;
import com.gt.dev.lazaro.elcaldo.vista.actividades.BebidasActivity;
import com.gt.dev.lazaro.elcaldo.vista.actividades.ComoUsarActivity;
import com.gt.dev.lazaro.elcaldo.vista.actividades.CopyrightActivity;
import com.gt.dev.lazaro.elcaldo.vista.actividades.CreditosActivity;
import com.gt.dev.lazaro.elcaldo.vista.actividades.FavoritosActivity;
import com.gt.dev.lazaro.elcaldo.vista.actividades.MainActivity;
import com.gt.dev.lazaro.elcaldo.vista.actividades.OtrasComidasActivity;
import com.gt.dev.lazaro.elcaldo.vista.coffe.CafeMainActivity;

import java.util.ArrayList;

/**
 * Created by Lazaro on 10/9/2015.
 */
public class NavigationDrawerFragment extends Fragment {
    public static final String PREF_FILE_NAME = "testpref";
    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;
    private View containerView;
    private ListView lista;
    private ArrayList<CategoriaDrawer> categoria;

    /**
     * Se requiere que el constructor este vacio.
     */
    public NavigationDrawerFragment() {
    }

    /**
     * Metodo donde se crean.
     * onCreate
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUserLearnedDrawer = Boolean.valueOf(readFromPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, "false"));
        if (savedInstanceState != null) {
            mFromSavedInstanceState = true;
        }
    }

    /**
     * onCreateView
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return retorna la vista
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        lista = (ListView) v.findViewById(R.id.listdrawer);

        categoria = new ArrayList<CategoriaDrawer>();
        categoria.add(new CategoriaDrawer(R.string.otras_comidas, R.drawable.otrascomidas));
        categoria.add(new CategoriaDrawer(R.string.bebidas, R.drawable.glass));
        categoria.add(new CategoriaDrawer(R.string.prefs, R.drawable.preferencias));
        categoria.add(new CategoriaDrawer(R.string.como_usar, R.drawable.comousar));
        categoria.add(new CategoriaDrawer(R.string.web_site, R.drawable.website));
        categoria.add(new CategoriaDrawer(R.string.twitter, R.drawable.twitterpng));
        categoria.add(new CategoriaDrawer(R.string.facebook, R.drawable.facebookpng));
        categoria.add(new CategoriaDrawer(R.string.terminos_condiciones, R.drawable.terminoscopy));
        categoria.add(new CategoriaDrawer(R.string.elcafe, R.drawable.glass));
        categoria.add(new CategoriaDrawer(R.string.creditos_drawer, R.drawable.creditos));


        AdaptadorDrawer adaptador = new AdaptadorDrawer(categoria, NavigationDrawerFragment.this.getActivity());
        lista.setAdapter(adaptador);
        //Establecemos el adaptador que hemos creado.

        /*lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(getActivity(), OtrasComidasActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(getActivity(), Preferencias.class));
                        break;
                    case 2:
                        startActivity(new Intent(getActivity(), ComoUsarActivity.class));
                        break;
                    case 3:
                        String urlWeb = "http://elcaldo.net84.net/";
                        Intent iWeb = new Intent(Intent.ACTION_VIEW);
                        iWeb.setData(Uri.parse(urlWeb));
                        startActivity(iWeb);
                        break;
                    case 4:
                        String urlTwitter = "https://twitter.com/elcaldoapp";
                        Intent iTwitter = new Intent(Intent.ACTION_VIEW);
                        iTwitter.setData(Uri.parse(urlTwitter));
                        startActivity(iTwitter);
                        break;
                    case 5:
                        String urlFacebook = "https://facebook.com/ElCaldo-167420940082879/timeline/";
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(urlFacebook));
                        startActivity(i);
                        break;
                    case 6:
                        startActivity(new Intent(getActivity(), CopyrightActivity.class));
                        break;
                    case 7:
                        startActivity(new Intent(getActivity(), CreditosActivity.class));
                        break;
                }
            }
        });*/
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(getActivity(), OtrasComidasActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(getActivity(), BebidasActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(getActivity(), Preferencias.class));
                        break;
                    case 3:
                        startActivity(new Intent(getActivity(), ComoUsarActivity.class));
                        break;
                    case 4:
                        String urlWeb = "http://elcaldo.net84.net";
                        Intent iWeb = new Intent(Intent.ACTION_VIEW);
                        iWeb.setData(Uri.parse(urlWeb));
                        startActivity(iWeb);
                        break;
                    case 5:
                        String urlTwitter = "https://twitter.com/elcaldoapp";
                        Intent iTwitter = new Intent(Intent.ACTION_VIEW);
                        iTwitter.setData(Uri.parse(urlTwitter));
                        startActivity(iTwitter);
                        break;
                    case 6:
                        String urlFacebook = "https://facebook.com/ElCaldo-167420940082879/timeline/";
                        Intent iFacebook = new Intent(Intent.ACTION_VIEW);
                        iFacebook.setData(Uri.parse(urlFacebook));
                        startActivity(iFacebook);
                        break;
                    case 7:
                        startActivity(new Intent(getActivity(), CopyrightActivity.class));
                        break;
                    case 8:
                        startActivity(new Intent(getActivity(), CafeMainActivity.class));
                        break;
                    case 9:
                        startActivity(new Intent(getActivity(), CreditosActivity.class));
                        break;
                }
            }
        });
        return v;
    }

    /**
     * setUp
     *
     * @param fragmentId
     * @param drawerLayout
     * @param toolbar
     */
    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {

        containerView = getActivity().findViewById(fragmentId);

        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            /**
             * Metodo que enfatisa cuando el drawer es abierto
             * @param drawerView
             */
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    saveIoPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer + "");
                }
                getActivity().invalidateOptionsMenu();
            }

            /**
             * Metodo que enfatisa cuando el drawer es cerrado
             * @param drawerView
             */
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            /**
             * Metodo que enfatisa cuando el drawer es deslizado hacia la dirección establecida
             * @param drawerView
             * @param slideOffset
             */
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                ((MainActivity) getActivity()).onDrawerSlide(slideOffset);
                toolbar.setAlpha(1 - slideOffset / 4);
            }
        };
        if (mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(containerView);
        }

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

    /**
     * saveIoPreferences
     *
     * @param contexto
     * @param preferenceName
     * @param preferenceValue
     */
    public static void saveIoPreferences(Context contexto, String preferenceName, String preferenceValue) {
        SharedPreferences sharedPreferences = contexto.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();
    }

    /**
     * readFromPreferences
     *
     * @param contexto
     * @param preferenceName
     * @param defaultValue
     * @return
     */
    public static String readFromPreferences(Context contexto, String preferenceName, String defaultValue) {
        SharedPreferences sharedPreferences = contexto.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName, defaultValue);
    }
}
