package com.gt.dev.lazaro.elcaldo.adaptadores;


/**
 * created by Lazaro on 11/09/2015
 */
public class CategoriaFavoritos {
    private int icon;
    private String title;

    /**
     * Constructor de la clase
     *
     * @param icon
     * @param title
     */
    public CategoriaFavoritos(int icon, String title) {
        this.icon = icon;
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public String getTitle() {
        return title;
    }
}
