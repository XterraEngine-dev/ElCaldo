package com.gt.dev.lazaro.elcaldo.adaptadores;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.gt.dev.lazaro.elcaldo.vista.fragmentos.Tab1;
import com.gt.dev.lazaro.elcaldo.vista.fragmentos.Tab2;
import com.gt.dev.lazaro.elcaldo.vista.fragmentos.Tab3;

/**
 * Created by Lazaro on 10/9/2015.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    CharSequence Titles[]; //esto va almacenar los titulos de los tabs cuales seran pasados cuando el ViewPagerAdapter es creado
    int NumbOfTabs; //Almacena el numero de tabs, estos seran tambien pasaran cuando el ViewPagerAdapter es creado.

    /**
     * ViewPagerAdapter
     * Construye un Constructor y asigna los valores pasados a los valores apropiados en la clase.
     *
     * @param fm
     * @param mTitles
     * @param mNumbOfTabsumb
     */

    public ViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    /**
     * getItem
     * Este metodo return el fragmento por cada posicion en el ViewPager
     *
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {

        if (position == 0) // if the position is 0 we are returning the First tab
        {
            Tab1 tab1 = new Tab1();
            return tab1;
        }
        if (position == 1) {
            Tab2 tab2 = new Tab2();
            return tab2;
        }
        if (position == 2) {
            Tab3 tab3 = new Tab3();
            return tab3;
        }
        return null;
    }

    // This method return the titles for the Tabs in the Tab Strip

    /**
     * @param position
     * @return
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    /**
     * @return
     */
    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}
