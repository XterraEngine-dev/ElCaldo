package com.gt.dev.lazaro.elcaldo.modelo;

/**
 * Created by Lazaro on 10/9/2015.
 */
public class Plato {

    private int id;
    private String nombre;
    private String ingredientes;
    private String preparacion;
    //private int imagen;



    /**
     * El constructor de la clase
     *
     * @param nombre
     * @param ingredientes
     * @param preparacion
     */
    public Plato(String nombre, String ingredientes, String preparacion) {
        this.nombre = nombre;
        this.ingredientes = ingredientes;
        this.preparacion = preparacion;
       // this.imagen = imagen;
    }

    /**
     * setters & getters
     *
     * @return
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

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
