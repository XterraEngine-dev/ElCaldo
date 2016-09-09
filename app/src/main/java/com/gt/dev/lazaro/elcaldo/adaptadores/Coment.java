package com.gt.dev.lazaro.elcaldo.adaptadores;

/**
 * Created by Lazarus on 8/9/2016.
 */
public class Coment {

    private String username;
    private String coment;

    public Coment(String username, String coment) {
        super();
        this.username = username;
        this.coment = coment;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getComent() {
        return coment;
    }

    public void setComent(String coment) {
        this.coment = coment;
    }

}
