package com.gt.dev.lazaro.elcaldo.adaptadores;

/**
 * Created by Lazaro on 10/9/2015.
 */
public class CategoriaDetalleComida {

    private String ingredientes;
    private String preparacion;


    /**
     * Constructor de la clase
     *
     * @param nombreComida
     * @param ingredientes
     * @param preparacion
     * @param imagen
     */
    public CategoriaDetalleComida(String nombreComida, String ingredientes, String preparacion, int imagen) {
        super();
        this.ingredientes = ingredientes;
        this.preparacion = preparacion;

    }

    /**
     * getters & setters
     *
     * @return
     */

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public String getPreparacion() {
        return preparacion;
    }

    public void setPreparacion(String preparacion) {
        this.preparacion = preparacion;
    }




}
