package com.gt.dev.lazaro.elcaldo.adaptadores;

/**
 * Created by Fernando on 22/08/2016.
 */
public class ArrayBuscador {
    private String titulo;
    private String subtitulo;
    private String ingredientes;
    private String preparacion;
    private String id;
    private String imagen;


    /**
     * @param titulo
     * @param subtitulo
     * @param imagen
     */
    public ArrayBuscador(String titulo, String subtitulo, String ingredientes, String preparacion, String id, String imagen) {
        super();
        this.titulo = titulo;
        this.subtitulo = subtitulo;
        this.ingredientes = ingredientes;
        this.preparacion = preparacion;
        this.id = id;
        this.imagen = imagen;
    }

    /**
     * @return titulo, subtitulo, imagen
     */

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public void setSubtitulo(String id) {
        this.id = id;
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

    public String getId() {
        return subtitulo;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
