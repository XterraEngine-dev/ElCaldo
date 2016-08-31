package com.gt.dev.lazaro.elcaldo.adaptadores;

/**
 * Created by Lazarus on 05/08/2016.
 */
public class TimeLine {

    private String username, recipename, region, like,picture;
    private int avatar;

    public TimeLine(String username, String recipename, String region, String likes, String picture, int avatar) {
        this.username = username;
        this.recipename = recipename;
        this.region = region;
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
