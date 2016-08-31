package com.gt.dev.lazaro.elcaldo.adaptadores;

/**
 * Created by root on 18/03/16.
 */
public class CategoriaCardView {

    private String nombre;
    private String lugar;
    private String imagen;

    public CategoriaCardView(String nombre, String lugar, String imagen) {
        this.nombre = nombre;
        this.lugar = lugar;
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String  imagen) {
        this.imagen = imagen;
    }
}
