package com.gt.dev.lazaro.elcaldo.adaptadores;

/**
 * Created by Lazarus on 02/08/2016.
 */
public class MainClass {

    private String title;
    private int picture;

    public MainClass(String title, int picture){
        super();
        this.title = title;
        this.picture = picture;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }
}
