package com.gt.dev.lazaro.elcaldo.adaptadores;

/**
 * Created by Lazaro on 10/9/2015.
 */
public class CategoriaCreditos {


    private String ocupacion;
    private String nombre;
    private String cuenta;
    private int imagen;

    /**
     * Constructor de la clase
     *
     * @param ocupacion
     * @param nombre
     * @param cuenta
     * @param imagen
     */
    public CategoriaCreditos(String ocupacion, String nombre, String cuenta, int imagen) {
        this.ocupacion = ocupacion;
        this.nombre = nombre;
        this.cuenta = cuenta;
        this.imagen = imagen;
    }

    /**
     * getters & setters
     *
     * @return
     */
    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }


    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }
}

