package com.gt.dev.lazaro.elcaldo.adaptadores;

/**
 * Created by Lazarus on 8/9/2016.
 */
public class Coment {

    private String username;
    private String coment;
    private String id;
    private String idTimeline;

    public Coment(String username, String coment, String id, String idTimeline ) {
        super();
        this.username = username;
        this.coment = coment;
        this.id = id;
        this.idTimeline = idTimeline;
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

    public String getId(){return id;}

    public void setId(String id) {
        this.id = id;
    }

    public String getIdTimeline() {return idTimeline;}

    public void setIdTimeline(String idTimeline) {
        this.idTimeline = idTimeline;
    }

}
