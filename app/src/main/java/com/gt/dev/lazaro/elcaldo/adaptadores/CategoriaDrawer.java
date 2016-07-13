package com.gt.dev.lazaro.elcaldo.adaptadores;

/**
 * Created by Lazaro on 10/9/2015.
 */
public class CategoriaDrawer {
    private int nombre;
    private int imagen;

    /**
     * @param nombre
     * @param imagen
     */
    public CategoriaDrawer(int nombre, int imagen) {
        super();
        this.nombre = nombre;
        this.imagen = imagen;
    }

    public int getNombre() {
        return nombre;
    }

    public void setNombre(int nombre) {
        this.nombre = nombre;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

}
