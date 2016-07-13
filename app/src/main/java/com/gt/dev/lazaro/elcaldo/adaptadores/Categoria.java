package com.gt.dev.lazaro.elcaldo.adaptadores;

/**
 * Created by Lazaro on 10/9/2015.
 */
public class Categoria {
    private String titulo;
    private String subtitulo;
    private int imagen;

    /**
     * @param titulo
     * @param subtitulo
     * @param imagen
     */
    public Categoria(String titulo, String subtitulo, int imagen) {
        super();
        this.titulo = titulo;
        this.subtitulo = subtitulo;
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

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }
}
