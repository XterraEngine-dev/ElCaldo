package com.gt.dev.lazaro.elcaldo.adaptadores;

/**
 * Created by Lazarus on 02/08/2016.
 */
public class MainClass {

    private int picture;
    private String title;

    public MainClass(int picture, String title) {
        super();
        this.picture = picture;
        this.title = title;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
