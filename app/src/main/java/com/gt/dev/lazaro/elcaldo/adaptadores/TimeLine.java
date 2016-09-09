package com.gt.dev.lazaro.elcaldo.adaptadores;

/**
 * Created by Lazarus on 05/08/2016.
 */
public class TimeLine {

    private String username, recipename, region, ingredientes, preparacion, id, like, picture;
    private int avatar;

    public TimeLine(String username, String recipename, String region, String ingredientes, String preparacion, String id, String likes, String picture, int avatar) {
        this.username = username;
        this.recipename = recipename;
        this.region = region;
        this.ingredientes = ingredientes;
        this.preparacion = preparacion;
        this.id = id;
        this.like = likes;
        this.avatar = avatar;
        this.picture = picture;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRecipename() {
        return recipename;
    }

    public void setRecipename(String recipename) {
        this.recipename = recipename;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
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
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getLikes() {
        return like;
    }

    public void setLikes(String likes) {
        this.like = likes;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
