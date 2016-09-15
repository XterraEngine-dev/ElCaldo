package com.gt.dev.lazaro.elcaldo.adaptadores;

/**
 * Created by Lazarus on 15/9/2016.
 */
public class Like {

    private String id;
    private int like;

    public Like(String id, int like) {
        super();
        this.id = id;
        this.like = like;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

}
